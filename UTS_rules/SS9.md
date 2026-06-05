**反模式**：
```ts
// ❌ 在 onShow 注册 → 每次进入页面都注册一次，泄漏
onShow((): void => {
  uni.$on('foo', () => { ... })
})

// ❌ 监听回调不做 null 守卫
uni.$on('foo', (data: any) => { data.rule.actionId })  // data 可能 null

// ❌ 监听回调里 throw 异常 → 整个页面 crash
uni.$on('foo', (data: any) => {
  const x = data.field  // 没有 null 守卫
  if (x == null) throw new Error('x is null')  // 异步回调里 throw 外层 try-catch 接不到
})
```

**正确示例**（主页 home/index.uvue）：
```ts
onLoad((): void => {
  try {
    uni.$on('showTriggerRuleDialog', (data: any): void => {
      try {
        if (data == null) return
        const obj = data as UTSJSONObject
        if (obj == null) return
        const ruleRaw = obj['rule']
        if (ruleRaw == null) return
        const ruleStr = JSON.stringify(ruleRaw)
        const rule = JSON.parse(ruleStr) as EffectiveTriggerRule
        pendingRule.value = rule
        showRuleDialog.value = true
      } catch (e) {
        console.warn('[home] showTriggerRuleDialog 异常: ' + JSON.stringify(e))
      }
    })
  } catch (e) {
    console.warn('[home] $on 异常: ' + JSON.stringify(e))
  }
})
```

**MiniMax / LLM 跨层通信特殊场景**：
- `App.uvue` 触发 LLM 评估（无障碍服务回调）→ LLM 返回 `suggestedRule` → 需要显示在主页弹窗
- `execute.uvue` 完成微动作 → emit `llmActionCompleted` → App.uvue 监听 → 调 `evaluatePost`
- **两个 emit 方向都通过 `uni.$emit` 完成**，避免传参/全局 store 在 App 层和 Page 层之间复制

**自查**：
```powershell
# 找所有 uni.$emit / uni.$on 用法，确认 onLoad/onUnload 配对
Get-ChildItem -Recurse -Filter "*.uvue" |
  Select-String -Pattern "uni.\$(emit|on|off)\("
```

**关联规则**：
- §39 异步回调必须 try-catch — `uni.$on` 回调也是异步的，必须 try-catch
- §35 watch/onXxx lambda 显式返回类型 — `uni.$on` 回调参数无类型标注（`any`），但内部访问字段需断言
