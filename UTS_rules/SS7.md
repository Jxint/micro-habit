
```ts
import { dbManager, type SqlRow } from './DatabaseManager'
import { ActionLog, ActionResult } from '../models/ActionLog'

export function insertActionLog(log: ActionLog): number {
  const row: SqlRow = {
    columns: [
      'action_id', 'action_type', 'result', 'skip_reason',
      'trigger_type', 'trigger_level', 'duration_ms', 'target_ms',
      'triggered_at', 'completed_at', 'created_at'
    ],
    values: [
      log.action_id, log.action_type, log.result, log.skip_reason,
      log.trigger_type, log.trigger_level, log.duration_ms, log.target_ms,
      log.triggered_at, log.completed_at, log.created_at
    ]
  }
  return dbManager.insert('action_logs', row)
}
```

**正确示例**（update：`database/SettingsDao.uts`）：

```ts
export function putSetting(key: string, value: string): void {
  const now = Math.floor(Date.now() / 1000)
  const existing = dbManager.queryOne('SELECT key FROM user_settings WHERE key = ?', [key])
  if (existing != null) {
    const row: SqlRow = {
      columns: ['value', 'updated_at'],
      values: [value, now]
    }
    dbManager.update('user_settings', row, 'key = ?', [key])
  } else {
    const row: SqlRow = {
      columns: ['key', 'value', 'updated_at'],
      values: [key, value, now]
    }
    dbManager.insert('user_settings', row)
  }
}
```

**错误示例**（全部禁止使用）：

```ts
// ❌ 错误 1: Map<string, any> → 内部 data.keys()/data.values() 触发 Kotlin iterator 歧义
const data = new Map<string, any>()
data.set('action_id', log.action_id)
dbManager.insert('action_logs', data)

// ❌ 错误 2: 对象字面量直接传参 → 推断为 UTSJSONObject，与 SqlRow 不匹配
dbManager.insert('action_logs', {
  columns: ['action_id'],
  values: [log.action_id]
})

// ❌ 错误 3: JSON 字符串拼接 → 走废弃的 dataToKVs 解析器
const data = '{"action_id":"' + log.action_id + '"}'
dbManager.insert('action_logs', data)

// ❌ 错误 4: SqlRow 字面量无类型注解 → §18 推断失败
dbManager.insert('action_logs', { columns: ['action_id'], values: [log.action_id] })
// 正确：const row: SqlRow = { ... }; dbManager.insert('action_logs', row)
```

**原因**：
- `Map<K, V>` 在 UTS 中映射到 Kotlin `MutableMap`，`.keys`/`.values` 是 Kotlin 属性不是 JS 方法，迭代触发 `iterator()` 6 候选歧义
- 对象字面量在 UTS 中推断为 `UTSJSONObject`，与 `SqlRow` 形参类型不匹配
- `const row: SqlRow = {...}` 显式类型注解让编译器把字面量识别为 `SqlRow`，无任何 UTSJSONObject 推断
- 平行数组下标访问零歧义，`columns[i]` 和 `values[i]` 严格对应

**适用范围**：
- 所有 `dbManager.insert(table, row)` 调用点（8 个 DAO）
- 所有 `dbManager.update(table, row, where, whereArgs)` 调用点（4 个 DAO）
- **不适用**：`dbManager.execSql(sql, params)` — 该 API 接受 SQL 字符串 + 参数数组