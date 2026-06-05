### 24. UTS 严格数值类型：**无隐式 Number→Int / Double→Float 转换**
```uts
// ❌ 编译失败：
//    "参数类型不匹配：实际类型为 'Number'，预期类型为 'Int'。"
//    "参数类型不匹配：实际类型为 'Double'，预期类型为 'Float'。"

private dp(value: number): number {        // 返回 number=Number，setPadding 期望 Int
  try {
    return (value * density + 0.5).toInt()
  } catch (_) {}
  return value                             // ❌ Number，不能赋给 Int 形参
}
content.setPadding(dp(20), dp(20), dp(20), dp(20))   // ❌ 4 个错误

title.setTextSize(22.0)                    // ❌ Double，setTextSize 期望 Float

// ✅ 正确：函数返回类型 + 所有返回路径都精确匹配
private dp(value: number): Int {            // 返回 Int（Kotlin Int）
  try {
    return (value * density + 0.5).toInt()
  } catch (_) {}
  return value.toInt()                       // ✅ 强制转换
}
content.setPadding(dp(20), dp(20), dp(20), dp(20))

title.setTextSize(22.0.toFloat())           // ✅ Double → Float
title.setTextSize(22.0f)                    // ✅ Kotlin Float 字面量也可
```


### 25. 类型为 `number` 的字段/参数传给 Android Int 形参需 `.toInt()`
```uts
// ❌ 编译失败：
//    "参数类型不匹配：实际类型为 'Number'，预期类型为 'Int'。"

export type OverlayConfig = {
  ...
  durationMs: number         // ❌ number=Number，传给 setMax(Int) 失败
}
class FloatingOverlayManager {
  private remainingMs: number = 0   // ❌ 同样
  private totalMs: number = 0       // ❌ 同样
}

progressBar.setMax(config.durationMs)              // ❌ Number → Int
bar.setProgress(total - self.remainingMs)          // ❌ Number - Number = Number → Int

// ✅ 方案 A（局部修复，推荐用于已发布的公共 API）：Android 调用处加 .toInt()
progressBar.setMax(config.durationMs.toInt())
bar.setProgress((total - self.remainingMs).toInt())

// ✅ 方案 B（重构，将字段类型改为 Int）：类型层面就严格
export type OverlayConfig = { ...; durationMs: Int }
class FloatingOverlayManager {
  private remainingMs: Int = 0
  private totalMs: Int = 0
}
progressBar.setMax(config.durationMs)              // ✅ Int → Int
bar.setProgress(total - self.remainingMs)          // ✅ Int - Int = Int
```