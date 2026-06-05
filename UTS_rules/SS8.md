**统一修复模式**：**所有 DAO 函数通过 `utils/DbRowUtils.uts` 提供的 null-safe getter 取值**：

```uts
// utils/DbRowUtils.uts
export function getNum(row: any, col: string): number {
  if (row == null) return 0
  const v = row.get(col)
  if (v == null) return 0
  return v as number
}

export function getStr(row: any, col: string): string {
  if (row == null) return ''
  const v = row.get(col)
  if (v == null) return ''
  return v as string
}

export function getStrOrNull(row: any, col: string): string | null {
  if (row == null) return null
  const v = row.get(col)
  if (v == null) return null
  return v as string
}
```

**所有 DAO 必须用这些 getter**，禁止直接 `row.get(col) as number`：

```uts
// ❌ 反模式（运行时 NPE 风险）
const sec = r.get("total_foreground_sec") as number
const pkg = r.get("package_name") as string

// ✅ 正确
const sec = getNum(r, 'total_foreground_sec')
const pkg = getStr(r, 'package_name')
```

**本项目已修复的 DAO**（全部 9 个）：
- `database/AppUsageDao.uts` — 新增本地 `getNum/getStr` 辅助函数（与 utils 版本重复但保留作为 DAO 局部 fallback）
- `database/ActionLogDao.uts` — 所有 `row.get(...) as T` → `getNum(row, '...')` / `getStr(row, '...')` / `getStrOrNull(row, '...')`
- `database/DailySummaryDao.uts` — mapRow 全部 getter 化
- `database/HeartbeatDao.uts` — mapRow getter 化
- `database/LlmCacheDao.uts` — mapRow getter 化
- `database/SettingsDao.uts` — getSetting 加默认值兜底
- `database/RuleDao.uts` — mapRow getter 化（含 expires_at nullable）
- `database/TriggerLogDao.uts` — 所有 count 函数 + mapRow getter 化
- `database/TtsCacheDao.uts` — mapRow getter 化
