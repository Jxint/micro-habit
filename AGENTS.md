# AGENTS.md — 微习惯健康伴侣 (micro-habit)

uni-app x 项目，目标平台 APP-ANDROID，Vue 3 + UTS。

---
注意！每次写完代码后必须根据下面的规则自检有没有犯错！！
## 项目结构

```
components/          5个可复用组件 (ActionCard, CountdownBar, EncourageToast, FeedbackDialog, StatusIndicator)
pages/               6个页面 (guide, home, action/execute, data/dashboard, settings, index/demo)
database/            11个文件: DatabaseManager(核心内存DB) + DatabaseHelper(建表迁移) + 9个DAO
models/             12个type定义 (MicroAction, ActionLog, TriggerRule, DailySummary, ThreeStatus等)
services/            8个业务服务 (TriggerEngine状态机, RuleEngine, Recommendation, ScoreCalculator等)
constants/           4个常量 (Actions预定义动作, DefaultSettings, EncourageTexts, CategoryMapping)
stores/              appStore.uts 全局状态 (ref驱动的三项评分、推荐动作、每日摘要)
utils/               4个工具 (TimeUtils, HashUtils, NetworkUtils, VibrateUtils)
uni_modules/         3个原生插件 (accessibility-service, audio-player, floating-overlay)
```

## 架构分层

**页面 → Store/Service → DAO → DatabaseManager (内存Map结构 + uni Storage持久化)**

- `TriggerEngine` 状态机驱动触发提醒 → `Recommendation` 推荐动作 → `RuleEngine` 频率控制
- `ScoreCalculator` 从动作日志计算三项评分(护眼/体态/活力)
- 数据库通过 `uni.getStorageSync/setStorageSync` 序列化 `RowEntry[]` 到 JSON 实现持久化

## 关键构建/运行

| 命令 | 说明 |
|------|------|
| HBuilderX `运行 → 运行到手机或模拟器` | 运行到 Android |
| `hbuilderx_cli_path` | `D:\Program Files (x86)\HBuilderX\cli.exe` |
| `node_exec_path` | `D:\Program Files (x86)\HBuilderX\plugins\node\node.exe` |

**无 lint / 无测试 / 无 package.json**。语法检查通过 HBuilderX 内置编译器完成。

## UTS 关键约束（基于实战总结）

### 类型系统
- **`type` 代替 `interface`**：对象字面量赋值给类型时需使用 `type` 定义
- **禁止内联对象字面量类型**：`function f(x: {a:number})` → 提取为命名 `type`
- **`any` 不可方括号访问/点号访问**：`row["key"]` 和 `row.key` 均不支持。必须用 `Map<string,any>` + `.get("key")`
- **`any = null` 需 `any | null`**
- **`String(x)` 仅接受 CharArray/StringBuffer/StringBuilder**；转换数字用 `'' + n`
- **`Number()` 是抽象类**；解析数字用 `parseFloat()` 或 `parseInt()`
- **`parseFloat()` 仅接受 `String`**；`parseInt()` 同理
- **`JSON.parse()` 仅接受 `String`**；从 `uni.getStorageSync` 取值需 `JSON.parse(raw as string)`
- **数组索引返回可空**：`arr[0]` 返回 `T?`，需 `as string` 等断言
- **regex 捕获组 `match[1]` 返回 `String?`**，需 `as string`
- **`charAt()` 返回 `Char` 类型**，不能与 `String` 字面量 `===` 比较；用 `substring(i,i+1)`
- **`Infinity` 不存在**；用大数值(999999999)替代

### 对象操作
- **禁止 `obj["key"] = val` 方括号赋值**；通过 `JSON.parse(jsonStr)` 构造新对象或 `Map.set()`
- **禁止 `Object.keys()`**；改用内部自维护的 `cols: string[]` 数组
- **`.map(funcRef)` 函数引用不支持**；必须包装为箭头：`rows.map((r: any): T => mapRow(r))`
- **模板字面量 `${expr}` 不支持**；改为 `'str' + expr + 'str'` 字符串拼接
- **对象展开 `{...obj}` 有风险**；数组展开 `[...a, ...b]` **禁止使用**，改用 `.concat()`
- **`JSON.parse()` 返回 `any`**，直接访问属性（如 `obj.key`）编译失败。必须 `JSON.parse(str) as NamedType` 转换后再访问

### API 差异
- **无 `plus.xxx`**；用 `uni.xxx` 或标准 `setTimeout`
- **`uni.getStorageSync` 返回 `any`**；判空后需 `as string` 转换再操作
- **数据库返回 `Map<string,any>[]`**；通过 `row.get("column_name")` 取值
- **`uniCloud.callFunction` API 不兼容 UTS**：参数 `data` 要求 `UTSJSONObject`（`any` 不匹配），且回调无 `success`/`fail` 字段（UTS 不接受 JS 风格回调对象）。云函数调用改用 `uni.request` HTTP 直连。
- **`uni.request` success/fail 回调参数不要标 `any`**：移除类型标注让编译器从框架类型定义推断，参数类型自动为 `UniRequestCallbackResult`，`res.data` 可直接访问

### UVUE 组件规范
- Props 中使用导入的复杂 type 时需 **内联类型定义** 到组件内
- 模板访问嵌套属性需通过 **计算属性中转**（`computed`），不可直接 `{{ props.action.name }}`
- CSS 仅支持 **class 选择器**；`display:flex` 必须显式 `flex-direction`
- App 端滚动需 `#ifdef APP` + `<scroll-view>`

## 命名约定

- **文件**：PascalCase.uts（类型定义），camelCase.uts（工具/服务）  
- **DAO**：`get*By*(...)`、`insert*`、`update*`、`count*`、`cleanOldData()`
- **组件**：PascalCase.uvue，props 用 `withDefaults(defineProps<{...}>(), {})`
- **Model type**：`type Xxx = { field: Type; ... }`
- **数据库列名**：snake_case；返回 Map 键名使用 snake_case 原始列名

## 错误处理

- DAO 函数吞异常为静默错误（返回默认值或空数组）
- DatabaseManager 异常通过 `console.error` 输出
- 关键初始化在 `App.uvue` 的 `onLaunch` 中 try-catch

## UI 组件通信

- Props down / Emits up：`defineEmits<{(e:'eventName', payload:Type):void}>()`
- 全局状态通过 `stores/appStore.uts` 的 `ref` 暴露，页面 `import { useAppStore }` 调用 `refreshHomeData()`

## Day 3 实战教训：常见编译错误与修复

### 1. `any` 变量上的 `.` / `[]` 访问 → 编译失败
```uts
// ❌ 错误
function foo(data: any): void { data.name }     // any 不可点号访问
const obj = JSON.parse(str); obj.title           // JSON.parse 返回 any

// ✅ 正确
function foo(data: NamedType): void { data.name }
const obj = JSON.parse(str) as NamedType; obj.title
// 或 JSON.parse → JSON.stringify → JSON.parse（打破 any 链）→ as 转换
```

### 2. `uniCloud.callFunction` → 完全不兼容
```uts
// ❌ 错误：data 类型不匹配（any ≠ UTSJSONObject）、无 success/fail 字段
uniCloud.callFunction({ name: 'fn', data: payload, success: (res) => {}, fail: () => {} })

// ✅ 改为 uni.request HTTP 直连
uni.request({ url: '...', data: JSON.stringify(payload), success(res) {}, fail(err) {} })
// 注意：success/fail 回调参数不要标 any，让编译器推断类型
```

### 3. 模板字面量 `` `${...}` `` → 编译失败
```uts
// ❌
const s = `value is ${x}`
// ✅
const s = 'value is ' + x
```

### 4. 数组展开 `[...a, ...b]` → 编译失败
```uts
// ❌
const c = [...a, ...b]
// ✅
const c = a.concat(b)
```

### 5. JSON.parse 结果的正确处理链
```uts
// 完整安全模式：stringify → parse → as NamedType
const jsonStr = JSON.stringify(responseData)       // 将任何值转为 string
const obj = JSON.parse(jsonStr) as ExpectedType     // parse + 转换类型
if (obj.field != null) { ... }                       // 现在可以 . 访问
```

### 6. UVUE 页面必须用 Composition API（`<script setup>`）
```uts
// ❌ Options API — this.xxx 返回 any，模板 any 泄漏
export default { data() { return { x: [] } }, methods: { f() { this.x... } } }

// ✅ Composition API — 显式类型，computed 中转模板
const store = useAppStore()
const data = ref<NamedType[]>([])
const count = computed<number>(() => store.count.value)
```
模板中**所有嵌套属性必须通过 computed 暴露**，不可直接 `{{ obj.nested.field }}`。

### 7. 避免 `any` 在函数参数/返回值中传播
```uts
// ❌ getStore() 返回 any → 调用方 this.getStore().method() 编译失败
function getStore(): any { return useAppStore() }

// ✅ 直接使用 store 变量，不使用包装函数
const store = useAppStore()
store.refreshHomeData()  // 类型正确
```

### 8. Canvas API — 正确用法
```uts
// ❌ uni.createCanvasContext — 不存在（uni-app x 无此老版 API）
// ❌ ctx.setFillStyle / ctx.setFontSize / ctx.setStrokeStyle — 非标准方法，不存在

// ✅ 正确：uni.createCanvasContextAsync + W3C 标准 Canvas 2D API
// onReady 生命周期中获取上下文
onReady(() => {
  uni.createCanvasContextAsync({
    id: 'canvas',
    component: getCurrentInstance().proxy,  // 或 Options API 中的 this
    success: (context: CanvasContext) => {
      const ctx = context.getContext('2d')!  // 标准 2d context
      const canvas = ctx.canvas
      const dpr = uni.getDeviceInfo().devicePixelRatio ?? 1
      canvas.width = canvas.offsetWidth * dpr   // HiDPI 适配
      canvas.height = canvas.offsetHeight * dpr
      ctx.scale(dpr, dpr)
      // W3C 标准 API：
      ctx.fillStyle = '#4CAF50'      // 属性赋值，不是 setFillStyle()
      ctx.font = '14px sans-serif'   // CSS font 字符串
      ctx.textAlign = 'center'
      ctx.fillRect(x, y, w, h)
      ctx.clearRect(x, y, w, h)
      ctx.beginPath(); ctx.arc(...); ctx.stroke(); ctx.fill()
      ctx.fillText(text, x, y)
    }
  })
})
```
Canvas 是独立模块（Android 4.25+ 内置），不使用时会被摇树优化。

### 9. UTS script setup 中函数不提升（no hoisting）
```uts
// ❌ 函数 A 调用函数 B，B 定义在 A 之后 → 编译失败 "找不到名称"
function draw() { buildEmpty() }   // buildEmpty 尚未定义
function buildEmpty() { ... }

// ✅ 所有被调用函数必须定义在调用者之前
function buildEmpty() { ... }      // 先定义
function draw() { buildEmpty() }    // 后调用
```
此规则适用于 `<script setup lang="uts">` 顶层函数。`onReady` lambda 中调用的函数同样需要提前定义。

### 10. 编辑后必须检查：模块级声明和导入是否完整
```uts
// 当从已有组件复制代码时，常遗漏模块级声明
// ❌ HeatmapCalendar 忘记加这两行，导致 canvasId/canvasH 找不到
const canvasId = 'heat_' + Math.floor(Math.random() * 100000)
const canvasH = props.height

// ❌ TriggerLogDao 的 mapRow 中用 UserAction，但 import 遗漏
import { TriggerLog, UserAction } from '../models/TriggerLog'
                    ^^^^^^^^^^ 必须补上

// 编辑完成后的自查 checklist：
// 1. 模块顶层 const/let 声明是否完整
// 2. import 导入的名称是否全部覆盖了使用了的导出
// 3. 被调用函数是否定义在调用者之前（见 §9）
// 4. 模板中使用的所有标识符是否有对应的 const/ref/computed 声明
```

### 11. `ref<any[]>` / `computed<any[]>` → 具体类型
```uts
// ❌ any[] 类型无法接收具体类型数组，且模板中 item.name 编译失败
const data = ref<any[]>([])
const data = computed<any[]>(() => store.data)  // UTSArray<DailyCount> ≠ UTSArray<any>
// 模板: v-for="(item) in data" → {{ item.name }} → 找不到名称 name

// ✅ 必须使用具体类型
import { DailyCount } from '../models/DailyCount'
const data = ref<DailyCount[]>([])
const data = computed<DailyCount[]>(() => store.data)

// 类似地, 响应式函数参数和返回值的类型必须精确匹配

### 12. 跨文件类型必须 `export`，否则调用方无法命名
```uts
// ❌ ScoreCalculator 未导出 HomepageData → 调用方无法引用该类型
type HomepageData = { ... }
export function getHomepageData(date: string): HomepageData { return {...} }

// ✅ 导出后调用方可以直接使用
export type HomepageData = { ... }

// UTS 中类型按名称匹配，结构相同但名字不同 = 不同类型
// ❌ UTSArray<ActionDetail>  ≠ UTSArray<ActionDetailType>
// ✅ 必须跨文件 import 同一类型
// ✅ 导出所有被导出函数中引用的类型

### 13. 子组件对象 prop + computed 字段访问 → 运行时 ClassCastException
```uts
// ❌ props.action 是 reactive proxy( MicroActionReactiveObject )，computed 中访问字段抛异常
const props = defineProps<{ action: MicroAction }>()
const name = computed(() => props.action.name)  // ClassCastException

// ✅ 改为原始类型 prop，避免 reactive proxy 包装
const props = defineProps<{ actionId: string; actionName: string }>()
const name = computed(() => props.actionName)  // 基础类型没问题

// 规则：
// 子组件接收对象 prop + computed 中访问字段 = 运行时崩溃
// 子组件只接收基础类型(string/number/boolean) prop = 安全
// 在非 computed 的普通函数中访问对象 prop 字段 = 安全
```

### 14. `||` 运算符严格要求 Boolean 操作数
```uts
// ❌ Map.get() 返回 Number?（可空），|| 期望 Boolean 操作数 → 编译失败
//    "Condition type mismatch: inferred type is 'Number?' but 'Boolean' was expected."
//    "Return type mismatch: expected 'Number', actual 'Boolean'."
return actionWeights.get(a.id) || 1.0

// ❌ 字符串 || 字符串同样失败："inferred type is 'String' but 'Boolean' was expected."
reason = customText.value || '其他'

// ✅ 正确：用 if/else 或三元运算符 + null 检查
const w = actionWeights.get(a.id)
if (w != null) { return w }
return 1.0

// 或者
const existing = actionWeights.get(a.id)
return existing != null ? existing : 1.0

// 对于字符串长度判断：
const ct = customText.value
reason = ct.length > 0 ? ct : '其他'
```

**规则**：
- `||` 运算符（以及 `&&`、`!`）的左操作数必须是严格的 `Boolean` 类型
- **`Map<K, V>.get(key)` 返回 `V | null`**，不能直接用 `||` 短路
- **`arr[i]` 返回 `T | null`**（数组索引结果可空），同样不能直接用 `||`
- **可选字段 `T | null` 也不能用 `||`**，必须显式 `if (x != null)` 或三元
- 替代方案：显式 null 检查、null 合并模式（`x != null ? x : default`）、或扩展函数（`x ?? default`，但 UTS 不支持 `??`）

### 15. `uni.vibrateShort` 必须传 `type` 参数
```uts
// ❌ 编译失败："No value passed for parameter 'type'."
uni.vibrateShort({})

// ✅ 正确：必须传 type 字段（'light' | 'medium' | 'heavy'）
uni.vibrateShort({ type: 'medium' })
uni.vibrateLong({})  // vibrateLong 不需要 type，可保持空对象
```

**规则**：
- `uni.vibrateShort(options)` 的 options 必填 `type: 'light' | 'medium' | 'heavy'`
- `uni.vibrateLong(options)` 的 options 没有强制必填字段，但传空对象仍然合法
- **UTS 调用 uni.xxx API 时严格检查所有必填参数**，TS 风格的"可选"标记在 UTS 不可信

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

**规则**：
- Java 风格的 `new X.OnXxxListener({...})` 在 UTS 中**不需要 `new`**
- **场景 A**：`setOnXxxListener` 等实例方法 → `X.OnXxxListener({ onEvent(args) { ... } })` 工厂调用可用（`setOnClickListener` 可用纯函数或 SAM 对象）
- **场景 B**：构造函数 + 监听器参数（如 `TextToSpeech(ctx, listener)`）→ **必须传纯函数** `function(args): T { ... }`，不能用 SAM 对象字面量
  - 错误示例：`TextToSpeech(ctx, TextToSpeech.OnInitListener({onInit}))` → "实际类型为 'UTSJSONObject'，预期类型为 'Function1<Int, Unit>'"
  - 正确：`TextToSpeech(ctx, function(status: number): void { ... })`
- 原因：UTS 的 `Function1<T, R>` 期望 Kotlin 函数类型，对象字面量 `{onInit: ...}` 在构造函数参数位置不触发 SAM 转换
- 适用于所有 `MediaPlayer.On*Listener`、`TextToSpeech.OnInitListener` 等 SAM 接口

### 17. 模块级 `let`/类 `private` 可变属性访问需用 `!!`（smart cast 失效）
```uts
// ❌ 编译失败：
//    "Smart cast to 'AppMonitorService' is impossible, 
//     because 'service' is a mutable property that could be mutated concurrently"
//    "Only safe (?.) or non-null asserted (!!.) calls are allowed 
//     on a nullable receiver of type 'Handler?'"

let service: AppMonitorService | null = null
class Foo {
  private handler: Handler | null = null
}

if (service != null) { service.setCallbacks(c) }   // 失败
if (this.handler != null) { this.handler.postDelayed(...) }  // 失败
```

**规则**：
- UTS 编译器对**模块级 `let` 变量**和**类 `private` 可变属性**不做 smart cast
- 即使在 `if (x != null)` 块内、`x != null && x.method()` 短路中、或是三元表达式中，都需要显式 `!!`
- **必须用 `!!` 模式**：
  ```uts
  if (service != null) { service!!.setCallbacks(c) }
  if (this.handler != null) { this.handler!!.postDelayed(...) }
  return service != null ? service!!.getInfo() : null
  return service != null && service!!.isConnected()
  ```
- **替代方案**：先 `const x = this.handler; if (x != null) x.method()`（局部 val 可触发 smart cast）
- 重要：该规则与 Kotlin 类似，但 UTS 编译器更严格，连 `if (x != null)` 块内都不做 smart cast

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

**规则**：
- 当函数签名声明了具体类型（如 `MyType | null`），`return { ... }` 推断为 `UTSJSONObject` 而非 `MyType`
- 当回调参数声明了具体类型，**传参位置**直接传 `{...}` 也会推断为 `UTSJSONObject`
- **必须先赋值给带类型注解的局部 const，再返回/传参**：
  ```uts
  function getInfo(): AppForegroundInfo | null {
    if (this.pkg.isEmpty()) return null
    const info: AppForegroundInfo = {
      packageName: this.pkg,
      startTime: this.startTime,
      continuousMs: this.elapsed
    }
    return info
  }

  // 传参同样：
  const info: AppForegroundInfo = { packageName: pkg, startTime: t, continuousMs: e }
  cbs.onAppDurationTrigger(info)
  ```
- 关键：局部 `const x: NamedType = {...}` 让编译器把对象字面量识别为 `NamedType`，避免 `UTSJSONObject` 推断
- **前提：NamedType 必须用 `type` 定义**，**禁止用 `interface`**（详见 §19 UTS110111163）

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

**规则**：
- **UTS 中所有"会被对象字面量赋值/传参"的类型必须用 `type` 定义**
- 错误码：**UTS110111163**（Object literals only support object types defined by construction type, and do not support interfaces）
- **interface 的限制**：
  - 不能直接接收对象字面量（`const x: IFoo = {...}` 失败）
  - 不能作为函数参数类型接收对象字面量（`f({...})` 失败）
  - 不能作为函数返回类型被 `return {...}` 直接返回
- **type 适用所有场景**（包含 interface 全部能力，结构相同）
- **写法转换（机械替换）**：
  ```uts
  // 前
  export interface XxxConfig { a: string; b: number }
  // 后
  export type XxxConfig = { a: string; b: number }
  ```
  仅替换 `interface XxxConfig` → `type XxxConfig =`，花括号、分号、字段完全不动
- **自查命令**：
  ```powershell
  Select-String -Path "uni_modules/**/*.uts" -Pattern "^export interface"
  ```
  任何命中都必须改为 `type`
- **本项目已修复位置**（3 个文件 5 处）：
  - `uni_modules/uts-accessibility-service/utssdk/app-android/AppMonitorService.uts`：`AppForegroundInfo`、`MonitorCallbacks`
  - `uni_modules/uts-floating-overlay/utssdk/app-android/FloatingOverlay.uts`：`OverlayConfig`、`OverlayCallbacks`
  - `uni_modules/uts-audio-player/utssdk/app-android/AudioPlayer.uts`：`AudioPlayerCallbacks`
- **项目内 `models/`、`services/`、`database/`、`stores/` 等目录已全部使用 `type`**，保持一致

### 20. Java `Intent` 类必须导入，不能只用全限定名混搭
```uts
// ❌ 编译失败："找不到名称'Intent'"（error18）
const intent = android.content.Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)  // Intent 未导入！

// ✅ 正确：导入 Intent 后统一使用短名
import Intent from 'android.content.Intent'
const intent = Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
```

**规则**：
- 使用 `Intent` 类的任何成员（包括 `Intent()`、`Intent.FLAG_ACTIVITY_NEW_TASK` 等常量）都必须先 `import Intent from 'android.content.Intent'`
- **禁止混搭**：全限定名 `android.content.Intent(...)` 创建对象 → 短名 `Intent.X` 访问常量（必须先导入）
- **统一风格**：要么全部用全限定名 `android.content.Intent(...)`、`android.content.Intent.FLAG_ACTIVITY_NEW_TASK`，要么全部用短名（推荐短名 + 导入）
- **自查命令**：
  ```powershell
  Select-String -Path "uni_modules/**/*.uts" -Pattern "\bIntent\." | 
    Where-Object { $_.Line -notmatch "android\.content\.Intent" -and 
                  $_.Line -notmatch "^import" }
  ```

### 21. `X_SERVICE` 常量在 `Context` 上，不在 `Service` / `WindowManager` / `Activity` 上
```uts
// ❌ 编译失败："找不到名称'WINDOW_SERVICE'"（error18）
this.wm = ctx.getSystemService(WindowManager.WINDOW_SERVICE) as WindowManager

// ❌ 同样失败：Service.NOTIFICATION_SERVICE、Service.POWER_SERVICE
const manager = this.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
const pm = this.getSystemService(Service.POWER_SERVICE) as PowerManager

// ✅ 正确：所有 *_SERVICE 常量都在 Context 上
import Context from 'android.content.Context'
this.wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
const manager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
const pm = this.getSystemService(Context.POWER_SERVICE) as PowerManager
```

**规则**：
- Android `Context` 类定义了所有 `*_SERVICE` 字符串常量（`WINDOW_SERVICE`、`NOTIFICATION_SERVICE`、`POWER_SERVICE`、`CONNECTIVITY_SERVICE` 等）
- **静态常量不通过继承**：虽然 `Service`、`WindowManager`、`Activity` 都"继承"自 `Context` 或其包装类，但 Java/Kotlin 静态字段**不参与继承**
- 必须 `import Context from 'android.content.Context'` 然后用 `Context.X_SERVICE`
- 错误码：**error18 找不到名称**
- **本项目已修复位置**（3 处）：
  - `FloatingOverlay.uts:108`：`WindowManager.WINDOW_SERVICE` → `Context.WINDOW_SERVICE`
  - `MonitorForegroundService.uts:40`：`Service.NOTIFICATION_SERVICE` → `Context.NOTIFICATION_SERVICE`
  - `MonitorForegroundService.uts:60`：`Service.POWER_SERVICE` → `Context.POWER_SERVICE`

### 22. Android 视图/服务构造函数参数必须是 `Context`，**禁止 `any`**
```uts
// ❌ 编译失败：
//    "参数类型不匹配：实际类型为 'Any'，预期类型为 'Context'。"
private buildSelectPhase(ctx: any, config: OverlayConfig, cbs: OverlayCallbacks): FrameLayout | null {
  const root = FrameLayout(ctx)   // ctx: any → FrameLayout(ctx: Context) 失败
  // ...
}

// ✅ 正确：导入 Context，参数类型用 Context
import Context from 'android.content.Context'
private buildSelectPhase(ctx: Context, config: OverlayConfig, cbs: OverlayCallbacks): FrameLayout | null {
  const root = FrameLayout(ctx)   // ctx: Context ✓
  // ...
}
```

**规则**：
- 所有需要 `Context` 的 Android 构造函数：`FrameLayout(ctx)`、`LinearLayout(ctx)`、`TextView(ctx)`、`Button(ctx)`、`ProgressBar(ctx)`、`Intent(ctx, ...)`、`Toast.makeText(ctx, ...)` 等
- 函数/方法签名中传入 `Context` 的参数**必须是 `Context` 类型**，不能用 `any`
- **`UTSAndroid.getAppContext()` 直接返回 `Context`（非 any）**，可放心赋给 `ctx: Context` 变量
- **错误码**：类型不匹配 `'Any'` vs `'Context'`
- **本项目已修复位置**（2 处）：
  - `FloatingOverlay.uts:140`：`buildSelectPhase(ctx: any, ...)` → `buildSelectPhase(ctx: Context, ...)`
  - `FloatingOverlay.uts:232`：`switchToExec(ctx: any, ...)` → `switchToExec(ctx: Context, ...)`
- **注意**：`context: any` 作为跨平台工具函数占位参数（如 `CloudService.uts:75`）是允许的，因为它不传给 Android 构造函数

### 23. 模块级 `let` 可变变量跨 if 块访问同样需 `!!`
```uts
// ❌ 编译失败：
//    "Only safe (?.) or non-null asserted (!!.) calls are allowed 
//     on a nullable receiver of type 'FloatingOverlayManager?'."
let overlayManager: FloatingOverlayManager | null = null
export function showOverlay(config: OverlayConfig, cbs: OverlayCallbacks): void {
  if (overlayManager == null) {
    overlayManager = FloatingOverlayManager()
  }
  overlayManager.show(config, cbs)  // ❌ 编译器不知道 if 块里已赋值
}

// ✅ 正确：if 块后访问需 `!!`
export function showOverlay(config: OverlayConfig, cbs: OverlayCallbacks): void {
  if (overlayManager == null) {
    overlayManager = FloatingOverlayManager()
  }
  overlayManager!!.show(config, cbs)  // ✅
}
```

**规则**：
- 模块级 `let` 变量即使在同一函数内 `if (x == null) { x = new T() }` 之后访问，仍需 `!!`
- 编译器不做跨代码块的 smart cast（与 §17 的"类 private 属性 + 跨 if" 行为一致）
- **本项目已修复位置**：
  - `FloatingOverlay.uts:45`：`overlayManager.show(...)` → `overlayManager!!.show(...)`
  - 配合 §17（handler / service / wakeLock 等 `private` 属性同样需 `!!`）
- **完整修复模板**（跨 `if-else/early-return` 的 mutable 变量）：
  ```uts
  if (x == null) return       // 早退
  x!!.method()                 // 强制非空断言
  // 或者
  if (x == null) { x = ... }  // 块内赋值
  x!!.method()                 // 块外访问仍需 !!
  ```

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

**规则**：
- UTS 数值类型严格区分（继承自 Kotlin）：
  - `number` ≡ `kotlin.Number`（抽象类，**不直接用**作形参/返回值）
  - `Int` ≡ `kotlin.Int`、`Double` ≡ `kotlin.Double`、`Float` ≡ `kotlin.Float`、`Long` ≡ `kotlin.Long`
  - **Java/Kotlin 隐式转换不存在**：`Number → Int`、`Double → Float`、`Int → Long` 都需显式 `.toInt()`/`.toFloat()`/`.toLong()`
- **Android API 形参类型分布**：
  - 期望 `Int`：`setPadding(Int,Int,Int,Int)`、`setMax(Int)`、`setProgress(Int)`、`setTextColor(Int)`、`setBackgroundColor(Int)`
  - 期望 `Float`：`setTextSize(Float)`、`setX(Float)`、`setY(Float)`、`setTranslationX(Float)`、`setScaleX(Float)`、`setRotation(Float)`、`setAlpha(Float)`
  - 期望 `Long`：`Handler.postDelayed(Runnable, Long delayMillis)`
- **工具函数返回类型必须精确**：
  - 用于 `setPadding`/`setMax` 的 dp/sp 函数：`function dp(v: number): Int` + 末尾 `return v.toInt()`
  - 用于 `setTextSize` 的 sp 函数：`function sp(v: number): Float` + 末尾 `return v.toFloat()`
  - **不能**用 `: number` 通用类型（会返回 `Number` 抽象类）
- **字面量类型推断**：
  - `20` → `Int`、`20.0` → `Double`、`20.0f` → `Float`、`20L` → `Long`
  - `dp(20)` 中 `20` 是 Int，可赋给 `number` 形参；`dp(2.5)` 中 `2.5` 是 Double，也可赋给 `number`
  - 算术运算：`Int * Float = Float`、`Int * Double = Double`、`Float + Double = Double`（类型提升遵循 Kotlin 规则）
- **本项目已修复位置**（12 处）：
  - `FloatingOverlay.uts:372`：`dp(value: number): number` → `dp(value: number): Int` + `return value.toInt()`
  - `FloatingOverlay.uts:155,163,175,192,211,244,252,266,274,324,332`（11 个 `setTextSize(x.x)`）→ `setTextSize(x.x.toFloat())`
- **自查命令**：
  ```powershell
  # 查找 Android Float 形参 API 的 Double 字面量调用
  Select-String -Path "uni_modules/**/*.uts" -Pattern "setTextSize|setX|setY|setTranslationX|setScale|setRotation|setAlpha" |
    Where-Object { $_.Line -notmatch "\.toFloat\(\)|\.0f\b" }
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

**规则**：
- UTS 字段/参数声明 `number` ⇒ 实际为 Kotlin `Number`（抽象类），**不与 `Int` 自动转换**
- 即使字段来自 `type`/`interface` 定义、即使赋值是 Int 字面量（如 `defaultDurationMs: 10000`），读出时仍是 `Number`
- **Android Int 形参 API 触发点**：
  - `ProgressBar.setMax(Int)`、`setProgress(Int)`、`SeekBar.setProgress(Int)`
  - `View.setVisibility(Int)`、`View.setLayoutParams(...)` 内部参数
  - `setMax(Int)`/`setProgress(Int)`/`setSelection(Int)`（`AbsListView`、`TextView` 等）
- **两种修复方案**：
  - **方案 A（最小侵入）**：在 Android API 调用处加 `.toInt()`，字段/参数类型保持 `number`（API 更灵活）
  - **方案 B（类型严格）**：将字段/参数类型改为 `Int`，需同步更新所有调用方（破坏性变更）
- **本项目决策**：用方案 A，理由：
  - 公共 API（如 `OverlayConfig.durationMs`、`MicroAction.defaultDurationMs`、`AppForegroundInfo.continuousMs`）保持 `number` 灵活
  - 内部 Android 边界处统一加 `.toInt()`，错误局部化
  - 避免大规模改动破坏调用方（`insertActionLog` 的 `target_ms: number` 等）
- **本项目已修复位置**（2 处）：
  - `FloatingOverlay.uts:259`：`setMax(config.durationMs)` → `setMax(config.durationMs.toInt())`
  - `FloatingOverlay.uts:296`：`setProgress(total - self.remainingMs)` → `setProgress((total - self.remainingMs).toInt())`
- **自查命令**：
  ```powershell
  # 找 "number 类型字段" 直接传给 Android Int 形参的位置
  # 关注 durationMs / remainingMs / totalMs / elapsed / startTime / continuousMs / ms
  Select-String -Path "uni_modules/**/*.uts" -Pattern "setMax\(|setProgress\(" |
    Where-Object { $_.Line -notmatch "\.toInt\(\)|\b0\b" }
  ```
- **关联规则**：§24 工具函数返回类型（`dp(): Int`）是同一类问题的另一面
```

### 26. HBuilderX 5.x uts 插件合并机制：`config.json` 字段不全生效，必须用 `AndroidManifest.xml`
**【最高优先级】Android Service/Receiver/Provider 等需要 manifest 注册的组件，必须放在 `utssdk/app-android/AndroidManifest.xml` 才会被合并。**

**陷阱**：
- `config.json` 里的 `permissions` 数组 → **完全忽略**（不写入最终 manifest）
- `config.json` 里的 `services` 数组 → **完全忽略**
- `config.json` 里的 `receivers` 数组 → **完全忽略**
- `config.json` 里 `id`/`name`/`version`/`dependencies` → 生效
- `manifest.json`（应用级）里的 `android.permissions` → 生效
- `manifest.json`（应用级）里的 `android.minSdkVersion`/`targetSdkVersion` → 生效

**真实案例**（本项目 2026-06-01 教训）：
- 用户反馈 OPPO 无障碍列表看不到本应用
- 调研：发现 `config.json` 已正确写 `services[AppMonitorService]`/`receivers[BootReceiver]`/`permissions[BIND_ACCESSIBILITY_SERVICE...]`
- 云打包后用 `aapt2 dump xmltree` 看真实 APK manifest：
  - ✓ `minSdkVersion=24`、`targetSdkVersion=34`（来自 `manifest.json`）
  - ✗ 完全找不到 `AppMonitorService`/`MonitorForegroundService`/`BootReceiver`
  - ✗ 完全找不到 `BIND_ACCESSIBILITY_SERVICE`/`FOREGROUND_SERVICE` 等权限
- 对比 `audio-player`/`floating-overlay` 的 `config.json`：它们也是空的 `services`/`receivers`，但能跑是因为**它们用 Java API 直接调（MediaPlayer/WindowManager），不需要 manifest 注册**
- 真正需要 manifest 注册的 Service（AccessibilityService），config.json 路径**完全失效**

**正确做法**（以本项目 `uts-accessibility-service` 为例）：
- 在 `uni_modules/uts-accessibility-service/utssdk/app-android/AndroidManifest.xml` 写标准 manifest 片段
- 文件模板（**不要用 `config.json` 声明 service/receiver/permission**）：
  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <manifest xmlns:android="http://schemas.android.com/apk/res/android"
      package="uts.sdk.utsAccessibilityService">
      <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
      <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
      <!-- ... 其他权限 ... -->
      <application>
          <service
              android:name="uts.sdk.utsAccessibilityService.AppMonitorService"
              android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
              android:exported="true">
              <intent-filter>
                  <action android:name="android.accessibilityservice.AccessibilityService" />
              </intent-filter>
              <meta-data android:name="android.accessibilityservice">
                  <meta-data android:name="accessibilityEventTypes" android:value="typeWindowStateChanged" />
                  <meta-data android:name="accessibilityFeedbackType" android:value="feedbackGeneric" />
                  <meta-data android:name="accessibilityFlags" android:value="flagReportViewIds" />
                  <meta-data android:name="canRetrieveWindowContent" android:value="true" />
                  <meta-data android:name="notificationTimeout" android:value="100" />
              </meta-data>
          </service>
          <service
              android:name="uts.sdk.utsAccessibilityService.MonitorForegroundService"
              android:exported="false"
              android:foregroundServiceType="specialUse" />
          <receiver
              android:name="uts.sdk.utsAccessibilityService.BootReceiver"
              android:exported="true">
              <intent-filter>
                  <action android:name="android.intent.action.BOOT_COMPLETED" />
              </intent-filter>
          </receiver>
      </application>
  </manifest>
  ```
- AccessibilityService 的 meta-data 用 **inline 嵌套**（不要用 `android:resource="@xml/xxx"` 引用，HBuilderX 5.x 不支持 uts 插件携带自定义 res 资源）
- `canRetrieveWindowContent=true` **必须写** —— 否则系统不把 service 列在"无障碍"列表中

**验证方法**（打包后必做）：
1. 找到云端下载的 APK：`unpackage/release/__UNI__*.apk`
2. `aapt2 dump xmltree <APK> --file AndroidManifest.xml`（HBuilderX 自带 aapt2 在 `plugins/uts-development-android/static/win/aapt2.exe`）
3. grep 关键字段：
   - `<service ... AppMonitorService` 出现
   - `<receiver ... BootReceiver` 出现
   - `<uses-permission ... BIND_ACCESSIBILITY_SERVICE` 出现
   - `canRetrieveWindowContent` 出现在 meta-data 块中
4. 验证 `aapt2 dump packagename <APK>` 看包名是 `micro.habit`

**自查命令**（确认本项目所有 uts 插件都走 AndroidManifest.xml 而非 config.json）：
```powershell
Get-ChildItem -Path "uni_modules" -Recurse -Filter "AndroidManifest.xml" -ErrorAction SilentlyContinue
# 必须每个需要注册 Android 组件的 uts 插件都对应一个 AndroidManifest.xml
```

**关于 `manifest.json` vs `AndroidManifest.xml` 字段优先级**：
- `manifest.json` 顶层 `app.distribute.android.permissions` 仍会合并（已验证）—— 重复声明不影响合并
- `manifest.json` 顶层 `app.distribute.android.minSdkVersion` 等会合并（已验证）
- 因此**最佳实践**：
  - 公共权限（多个插件共用）放在 `manifest.json` 的 `app.distribute.android.permissions`
  - 插件专有权限/组件放在 `uni_modules/<plugin>/utssdk/app-android/AndroidManifest.xml`
  - `config.json` 只放 `id`/`name`/`version`/`dependencies`，**不放** permissions/services/receivers

**关联规则**：§24/§25 是 UTS 编译期问题，本条是**打包期 / 平台期问题**（HBuilderX 5.x 合并机制）

