### 27. `SQLiteStatement` 比 `ContentValues + execSQL` 更稳：避免 null 字段和 `changesNumber` 坑

**【最高优先级】UTS 操作 Android SQLite 时，优先使用 `SQLiteStatement` + 手动 `bindXxx` 路径，避免 `ContentValues` 隐式 null 字段 + `d.changesNumber`（不存在）问题。**

**坑 A**：`ContentValues.put(key, null)` 编译失败
```uts
// ❌ 编译失败：null 不能传给 ContentValues.put 的 V 形参
val values = ContentValues()
values.put('col', null)  // "参数类型不匹配：实际 Any? 期望 V"
```

**坑 B**：`d.changesNumber` / `d.changes()` 在 UTS 中不可用
```uts
// ❌ 编译失败："找不到名称 'changesNumber'"
val rows = d.insert('t', null, values)
val n = d.changesNumber  // 错误：应为 changes() 方法
```

**坑 C**：使用 `execSQL` 拼 SQL 时，null/数字/字符串混合传参需要手动 `bindXxx`，很容易出错

**正确模板**（本项目 `uts-sqlite-store` 的 `SqliteStore.execSql`）：
```uts
import SQLiteStatement from 'android.database.sqlite.SQLiteStatement'

// execSql with params
execSql(sql: string, params: any[] = []): void {
  const d = this.db
  if (d == null) return
  if (params.length === 0) {
    d.execSQL(sql)
    return
  }
  const stmt: SQLiteStatement = d.compileStatement(sql)
  try {
    for (let i = 0; i < params.length; i++) {
      const v = params[i]
      if (v == null) {
        stmt.bindNull((i + 1).toInt())
      } else {
        stmt.bindString((i + 1).toInt(), '' + v)
      }
    }
    stmt.execute()
  } finally {
    stmt.close()
  }
}

// insert
insert(table: string, data: any): number {
  // ... 构造 sql = 'INSERT INTO t (k1, k2) VALUES (?, ?)'
  const stmt = d.compileStatement(sql)
  try {
    for (let i = 0; i < pairs.length; i++) {
      this.bindValue(stmt, i + 1, pairs[i].v)  // null→bindNull / else→bindString
    }
    return stmt.executeInsert()  // 返回 Long（可赋给 number）
  } finally {
    stmt.close()
  }
}

// update / delete
update(...): number {
  // ...
  return stmt.executeUpdateDelete()  // 返回 Int（受影响行数）
}
```
