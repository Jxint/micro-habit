### 16. Java 监听器接口（OnXxxListener）不需要 `new` 关键字
```uts
// ❌ 编译失败：
//    "Type mismatch: actual type is 'UTSJSONObject', 
//     but 'Function1<Int, Unit>' (or similar SAM interface) was expected"
const listener = new TextToSpeech.OnInitListener({ onInit(status: number) {} })
this.mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener({ onPrepared(mp) {} }))

// ✅ 正确：UTS 直接调用 SAM 工厂方法（用于 setOnXxxListener）
const listener = TextToSpeech.OnInitListener({ onInit(status: number) {} })
this.mp!!.setOnPreparedListener(MediaPlayer.OnPreparedListener({ onPrepared(mp) {} }))
this.mp!!.setOnCompletionListener(MediaPlayer.OnCompletionListener({ onCompletion(mp) {} }))
this.mp!!.setOnErrorListener(MediaPlayer.OnErrorListener({ onError(mp, what, extra) {} }))

// ✅ 正确：构造函数 + 监听器 → 必须传**纯函数**（不是 SAM 对象）
ttsInstance = TextToSpeech(ctx, function(status: number): void {
  if (status == TextToSpeech.SUCCESS) { ... }
})
```

### 18. 函数返回类型 + 对象字面量 → 必须用 `const x: Type = {...}; return x` 模式
```uts
// ❌ 编译失败：
//    "Return type mismatch: expected 'AppForegroundInfo?', 
//     actual 'UTSJSONObject'."
function getInfo(): AppForegroundInfo | null {
  if (this.pkg.isEmpty()) return null
  return { packageName: this.pkg, startTime: this.startTime, continuousMs: this.elapsed }
}

// ❌ 同样失败：传参时直接传对象字面量
cbs.onAppDurationTrigger({ packageName: pkg, startTime: t, continuousMs: e })
```
### 19. 对象类型必须用 `type`，**禁止 `interface`**（UTS110111163）
```uts
// ❌ 编译失败：UTS110111163
//    "Object literals only support object types defined by construction type, 
//     and do not support interfaces."
export interface AppForegroundInfo {
  packageName: string
  startTime: number
  continuousMs: number
}
const info: AppForegroundInfo = { packageName: 'com.app', startTime: 0, continuousMs: 0 }
//    ^                       ^ UTS110111163 触发点：对象字面量赋值给 interface

// ✅ 正确：用 `type` 定义
export type AppForegroundInfo = {
  packageName: string
  startTime: number
  continuousMs: number
}
const info: AppForegroundInfo = { packageName: 'com.app', startTime: 0, continuousMs: 0 }
//    ^                       ^ 类型匹配，编译通过

// ❌ 函数参数：interface 同样失败
overlay.show({
  level: 'gentle', actionName: 'a', ttsText: 'b',
  durationMs: 0, lottieAssetPath: null, encourageText: 'c'
})  // OverlayConfig 是 interface → UTS110111163
```