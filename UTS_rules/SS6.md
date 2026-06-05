
```uts
// ❌ 运行时 bug（编译通过，逻辑崩）：no such table: user_settings
class SqliteStore {
  private db: SQLiteDatabase | null = null

  openDatabase(name, version, onCreate, onUpgrade): void {
    this.helper = new MicroHabitSqliteHelper(ctx, name, version, onCreate, onUpgrade)
    //                                                              ↑ 父类只"存储"回调，不触发
    this.db = this.helper!!.getWritableDatabase()
    //↑ 这一行返回后，SQLiteOpenHelper 内部检测到"db 不存在"→ 同步调用 onCreate(db) 回调
    //  → 回调里用户的 onCreate() 执行 CREATE TABLE 语句
    //  → 但此时 SqliteStore.this.db 仍然 null（要等 getWritableDatabase() 返回后才赋值）
    //  → execSql 看到 this.db == null → 静默 return → CREATE TABLE 全部丢失
  }
}
```

**根因时序图**：
```
getWritableDatabase() 调用栈
├─ SQLiteOpenHelper.getWritableDatabase()
│  ├─ 检测 db 文件不存在
│  ├─ 同步调用子类 override onCreate(db)   ← 用户的 onCreate 在这里执行
│  │  └─ runSql → sqliteStore.execSql(sql)
│  │     └─ if (this.db == null) return   ← this.db 还没赋值！
│  └─ 返回 db 实例
└─ 回到 openDatabase 第四行
   this.db = helper.getWritableDatabase()   ← 赋值太晚了
```

**正确修复**（已采用）：**让回调参数携带状态**，wrapper 内部先 `this.db = db` 再调用户 callback：

```uts
// ✅ SqliteStore.uts 修复版
class MicroHabitSqliteHelper extends SQLiteOpenHelper {
  private onCreateCallback: ((db: SQLiteDatabase) => void) | null = null
  private onUpgradeCallback: ((db: SQLiteDatabase, oldV: number, newV: number) => void) | null = null

  constructor(
    ctx: Context,
    name: string,
    version: number,
    onCreate: (db: SQLiteDatabase) => void,
    onUpgrade: (db: SQLiteDatabase, oldV: number, newV: number) => void
  ) {
    super(ctx, name, null, version.toInt())
    this.onCreateCallback = onCreate
    this.onUpgradeCallback = onUpgrade
  }

  override onCreate(db: SQLiteDatabase): void {
    const cb = this.onCreateCallback
    if (cb != null) cb(db)   // ← 把 db 作为参数透传给 wrapper
  }

  override onUpgrade(db: SQLiteDatabase, oldV: Int, newV: Int): void {
    const cb = this.onUpgradeCallback
    if (cb != null) cb(db, oldV, newV)   // ← 同样透传
  }
}

class SqliteStore {
  private db: SQLiteDatabase | null = null

  openDatabase(name, version, onCreate, onUpgrade): void {
    const ctx: Context = UTSAndroid.getAppContext()!!
    this.helper = new MicroHabitSqliteHelper(ctx, name, version,
      (db: SQLiteDatabase): void => {
        this.db = db          // ← wrapper 内部先赋值
        onCreate()            // ← 然后才调用户 onCreate
      },
      (db: SQLiteDatabase, oldV: number, newV: number): void => {
        this.db = db
        onUpgrade(oldV, newV)
      }
    )
    this.db = this.helper!!.getWritableDatabase()  // 此时 this.db 已被 wrapper 设过，再赋一次同值（无副作用）
  }
}
```

**时序修复后**：
```
getWritableDatabase() 调用栈
├─ SQLiteOpenHelper.getWritableDatabase()
│  ├─ 检测 db 不存在
│  ├─ 同步调用子类 override onCreate(db)
│  │  └─ cb(db) → wrapper
│  │     ├─ this.db = db            ← 现在赋值了！
│  │     └─ onCreate()
│  │        └─ runSql → execSql(sql)
│  │           └─ this.db 非空 → execSQL() 成功
│  └─ 返回 db
└─ openDatabase 最后一行 this.db = ... 重复赋值同值（no-op）
```