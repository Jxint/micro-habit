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

### 13. 子组件 prop 必须用基础类型 / 平行数组，**对象 prop + computed 字段访问 = 运行时 ClassCastException**

**【最高优先级】** UTS 子组件 props 在被读取时会被包装成 reactive proxy，访问对象内部字段时与原始 Kotlin/Java 实例 ClassCast 失败。

**反模式表**：

| Prop 类型 | template 访问 | script 访问 | 风险 |
|----------|--------------|-------------|------|
| `MicroAction`（对象）| `{{ props.action.name }}` | `props.action.name` | ❌ ClassCastException |
| `LineSeries[]`（对象数组）| `v-for="s in props.series" {{ s.name }}` | `props.series[0].name` | ❌ ClassCastException |
| `string[]` / `number[]` / `string[][]` | `v-for="x in props.list" {{ x }}` | `props.list[0]` | ✅ 安全 |
| `string` / `number` / `boolean` | ✅ | ✅ | 安全 |

**强制规则**：
- **强烈建议子组件 prop 全部用基础类型**（`string` / `number` / `boolean` / 基础类型数组）
- 对象展示组件（如 `MicroAction` 详情）→ 拆成 `actionId / actionName / actionDuration` 多个基础类型 prop
- 列表展示组件 → 拆成多个**平行数组 prop**（`labels: string[] / values: number[]`）
- 在非 computed 的普通函数中访问对象 prop 字段 = 安全
- 组件内部 `ref<X[]>([])` 的 ref 数组（在 script 内部）→ 元素访问安全

**withDefaults 默认值必须用箭头函数**：
```ts
// ❌ 默认值字面量推断为 Array<Any>，赋给 string[] 失败
const props = withDefaults(defineProps<{ labels: string[] }>(), { labels: [] })

// ✅ 箭头函数返回，类型 = string[]
const props = withDefaults(defineProps<{ labels: string[] }>(), { labels: () => [] })
```
**所有数组/对象 prop 默认值都要用箭头函数**。

**自查**：
```powershell
Get-ChildItem "components" -Recurse -Filter "*.uvue" | 
  Select-String -Pattern "defineProps|withDefaults" -Context 0,8 |
  Where-Object { $_ -match ":\s*\w+\[\]" }
```

### 14. 严格比较运算符：`||` 严格 Boolean + `=== 0` 触发 Number-Int boxing 警告

**【最高优先级】** 两类常见反模式：

**(A) `||` 运算符要求严格 Boolean**：
```uts
// ❌ Map.get() 返回 Number?（可空），|| 期望 Boolean
return actionWeights.get(a.id) || 1.0
// ✅ 显式 null 检查
const w = actionWeights.get(a.id)
if (w != null) { return w }
return 1.0
```
- `Map<K, V>.get(key)` 返回 `V | null`，不能直接用 `||`
- `arr[i]` 返回 `T | null`，同样不能用 `||`
- 替代：显式 `if (x != null)` / 三元 `x != null ? x : default`（UTS 不支持 `??`）

**(B) `Number === Int` 触发 boxing 警告**：
```
warning: Identity equality for arguments of types 'Number' and 'Int' can be unstable because of implicit boxing.
```
- UTS `number` = Kotlin `Number`（抽象类），整数字面量 `0`/`1` = `Int`
- `Number === Int` 装箱比较触发 JVM identity 警告
- **`Array.length` 返回 `Number`**（不是 `Int`），同样触发

**修复**（**所有 `=== 0` 必须改 `< 1`**）：
```ts
// ❌
if (v === 0) return -1
if (arr.length === 0) return []

// ✅ 改用范围
if (v < 1) return -1
if (arr.length < 1) return []

// ✅ 或用非严格 ==
if (v == 0) return -1
```

**注意 `String.length` 不触发**（Kotlin `String.length : Int`，非 `Number`）：
```ts
const s: string = "hello"
if (s.length === 0) ...  // ✅ 不警告
```

**自查**：
```powershell
Get-ChildItem -Recurse -Include "*.uts","*.uvue" |
  Select-String -Pattern "\.length\s*(===|!==)\s*0|[a-zA-Z_]\w*\s*(===|!==)\s*[01]\b" |
  Where-Object { $_.Line -notmatch "string\.length" }
# 命中改为 < 1 / > 0 / <= 或 ==
```

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
- **自查命令**：
  ```powershell
  # 找 "number 类型字段" 直接传给 Android Int 形参的位置
  # 关注 durationMs / remainingMs / totalMs / elapsed / startTime / continuousMs / ms
  Select-String -Path "uni_modules/**/*.uts" -Pattern "setMax\(|setProgress\(" |
    Where-Object { $_.Line -notmatch "\.toInt\(\)|\b0\b" }
  ```
- **关联规则**：§24 工具函数返回类型（`dp(): Int`）是同一类问题的另一面
```

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

### 26. UTS `extends Java/Kotlin 抽象类` 的 override 签名必须精确匹配父类

**【最高优先级】实现 Java/Kotlin 抽象类（如 `SQLiteOpenHelper`）时，子类 `override` 方法的每个参数类型必须与父类签名逐项匹配，UTS 编译器不做"近似"匹配。**

**案例**：本项目 `uts-sqlite-store` 的 `MicroHabitSqliteHelper extends SQLiteOpenHelper`：

```uts
// ❌ 编译失败（5+1 个错误）：
class MicroHabitSqliteHelper extends SQLiteOpenHelper {
  constructor(ctx: Context | null, name: string, version: number, ...) {
    super(ctx!!, name, null as any, version.toInt())  // 错误 A
  }
  override onCreate(db: SQLiteDatabase | null): void { ... }      // 错误 B
  override onUpgrade(db: SQLiteDatabase | null, oldV: number, newV: number): void { ... }  // 错误 C
}
// 调用 new 时还报 "实际 Context? 期望 Context" 错误 D
// rawQuery 报 "实际 Any 期望 Array<out String!>?" 错误 E
// Cursor.getColumnNames() 数组 .length 找不到 错误 F
```

**错误 1**（onUpgrade 找不到候选）：
```
Class 'MicroHabitSqliteHelper' is not abstract and does not implement abstract base class member:
fun onUpgrade(p0: SQLiteDatabase!, p1: Int, p2: Int): Unit
at SqliteStore.uts:6:0
```
父类 Kotlin 签名是 `(SQLiteDatabase!, Int, Int)` 三个参数，子类写了 `(SQLiteDatabase | null, number, number)` → 父类两个 `Int` 参数没实现。

**修复**：子类必须用 **Int**（不是 `number`）+ **SQLiteDatabase**（非空，因为父类是 `SQLiteDatabase!` 平台类型，子类可以声明为非空）：
```uts
override onUpgrade(db: SQLiteDatabase, oldV: Int, newV: Int): void { ... }
```

**错误 2**（'onUpgrade' overrides nothing. Potential signatures for overriding）：
```
'onUpgrade' overrides nothing. Potential signatures for overriding:
fun onUpgrade(p0: SQLiteDatabase!, p1: Int, p2: Int): Unit
at SqliteStore.uts:27:2
```
说明 UTS 编译器**只接受精确匹配**：父类 `Int` ≠ 子类 `number`、`SQLiteDatabase!` ≠ `SQLiteDatabase | null`。

**错误 3**（super 第 4 参数 null 找不到候选）：
```
None of the following candidates is applicable:
constructor(p0: Context?, p1: String?, p2: SQLiteDatabase.CursorFactory?, p3: Int): SQLiteOpenHelper
constructor(p0: Context?, p1: String?, p2: Int, p3: SQLiteDatabase.OpenParams): SQLiteOpenHelper
at SqliteStore.uts:17:4
```
- `null as any` 推断为 `Any`，与 `SQLiteDatabase.CursorFactory?` 不匹配
- **修复**：直接用裸 `null` 字面量，UTS 推断为 nullable CursorFactory：
  ```uts
  super(ctx, name, null, version.toInt())
  ```

**错误 4**（new 调用处 "实际 Context? 期望 Context"）：
```
参数类型不匹配：实际类型为 'Context?'，预期类型为 'Context'
at SqliteStore.uts:47:45
```
- 调用方传入的 `ctx: Context`（来自 `const ctx: Context = UTSAndroid.getAppContext()`）类型在某些上下文被推断为 Kotlin 平台类型 `Context?`
- 构造器签名是 `ctx: Context | null`，编译器认为两者不等价
- **修复**：让构造器签名直接是 `Context`（非空），调用前用 `ctx!!` 断言：
  ```uts
  constructor(ctx: Context, name: string, version: number, ...) {  // 不要 | null
    super(ctx, name, null, version.toInt())  // ctx: Context 精确匹配
  }
  
  // 调用处
  const ctx: Context = UTSAndroid.getAppContext()  // 显式标注非空
  this.helper = new MicroHabitSqliteHelper(ctx, name, version, onCreate, onUpgrade)
  ```

**错误 5**（rawQuery 参数 Any vs Array<out String!>?）：
```
参数类型不匹配：实际类型为 'Any'，预期类型为 'Array<(out) String!>?'
at SqliteStore.uts:71:35
```
- `args as any` 把 `Array<String>` 强制转 `Any`，丢失 variance 信息
- **修复**：用精确的 variance cast：
  ```uts
  const args: Array<String> = this.toStringArgs(params)
  const cursor = d.rawQuery(sql, args as Array<out String!>)
  ```
  `out` 是 Kotlin 协变标记，平台类型 `String!` 在 `Array<out String!>` 中兼容

**错误 6**（Cursor.getColumnNames() 数组 .length 找不到）：
```
找不到名称 "length"
at SqliteStore.uts:75:29
```
- `Cursor.getColumnNames()` 返回 Java `String[]`，在 UTS 中映射为带平台类型的数组
- UTS 数组没有 `.length` 属性（Kotlin 数组用 `.size`，但 UTS 也不能直接用）
- **修复**：完全绕开 `getColumnNames()`，用 `Cursor` 自带 API：
  ```uts
  const nCols: Int = cursor.getColumnCount()
  for (let c: Int = 0; c < nCols; c++) {
    const name: string = cursor.getColumnName(c) as string
    // ...
  }
  ```
  `getColumnCount()` 返回 `Int`（明确类型），`getColumnName(c)` 返回 `String?`（平台类型 → UTS `String` + `as string` 断言）
**规则汇总**（`extends Java/Kotlin 抽象类` 必看）：

| 父类签名 | 子类必须用 | 不能用 |
|---------|-----------|--------|
| `Context!`（平台类型） | `Context`（非空）| `Context \| null`（子类反而报"实际 Context? 期望 Context"）|
| `SQLiteDatabase!` | `SQLiteDatabase` | `SQLiteDatabase \| null` |
| `Int` | `Int` | `number`（=Number 抽象类，类型提升失败）|
| `String?` | `string` | `string \| null`（其实可以，但 `as string` 断言更安全）|
| 构造器第 2/3/4 参数含 nullable Java interface | 裸 `null` 字面量 | `null as any`（丢失 nullable 信息）|
| 数组形参 `String[]` | 强类型 `as Array<out X!>` cast | `as any`（丢 variance，编译失败）|
| `Cursor.getColumnNames()` 拿列名 | `cursor.getColumnCount() + cursor.getColumnName(c)` | `colNames.length`（UTS 数组无 length）|

**自查命令**（项目内其他 `extends` 类是否合规）：
```powershell
Get-ChildItem "uni_modules" -Recurse -Filter "*.uts" |
  Select-String -Pattern "^\s*override\s+\w+\s*\(" |
  ForEach-Object {
    $line = $_.Line
    if ($line -match "number\)|null,.*number\)") {
      Write-Host "$($_.Path):$($_.LineNumber) : $line"
    }
  }
# 必须没有任何 "number)" 或 "... | null, ... number)" 的 override
```
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

**关键边界转换**：
- `(i + 1).toInt()` — 数组索引 + 1 是 Int 运算，但传 `bindNull/bindString` 需要明确 `Int`，显式 `.toInt()` 防止类型提升到 `Number`
- `stmt.executeInsert()` 返回 Java `long`（Kotlin `Long`），可直接 return 给 `number` 函数
- `stmt.executeUpdateDelete()` 返回 Java `int`（Kotlin `Int`），可直接 return
- `stmt.execute()` 无返回值

**Cursor 列名/列数**：
```uts
// ❌ Cursor.getColumnNames() 返回 Java String[]，在 UTS 中 .length 失败
const colNames = cursor.getColumnNames()
const n = colNames.length  // 错误

// ✅ 用 Cursor 自带 API（更直接）
const nCols: Int = cursor.getColumnCount()
for (let c: Int = 0; c < nCols; c++) {
  const name: string = cursor.getColumnName(c) as string  // 返回 String? → as string
  const idx: Int = c
  if (cursor.isNull(idx)) {
    // 字段为 NULL
  } else {
    const s = cursor.getString(idx) as string
  }
}
```

**SQLiteStatement 路径优势**：
1. **完全支持 null 字段** — `bindNull` 显式处理，编译期不报错
2. **返回行数/rowId 是 Java 标准 API** — `executeInsert` 返回 `long`，`executeUpdateDelete` 返回 `int`，不依赖 UTS 映射神秘字段
3. **API 表面小且稳定** — 只需要 `bindNull/bindString/bindLong/bindDouble/blindBlob`，避免 ContentValues.put 的 7 个重载歧义
4. **预编译 + 绑定分离** — 与 prepared statement 语义一致，性能更好

**适用于**：
- 所有 INSERT/UPDATE/DELETE 操作（统一用 `compileStatement` + `bindXxx`）
- 不需要批量事务的场景
- 对 null 字段有需求的接口（DAO 的 data 对象可能省略字段 → null bind）

**不适用**：
- 纯 DDL（CREATE TABLE） → 用 `d.execSQL(sql)` 即可
- 大量批量操作 → 用 `transaction` + 多次 `compileStatement`

**本项目已采用**：`uni_modules/uts-sqlite-store/utssdk/app-android/SqliteStore.uts` 全部用 `SQLiteStatement` 路径。

### 28. extends 的子类回调时机：父类 `onXxx` 在 `getXxx()` 调用过程中同步触发，**不能用"先 get 后赋 this"模式**

**【最高优先级】实现 Java/Kotlin "Helper/Manager 模式"抽象类（典型如 `SQLiteOpenHelper`、`TextureView.SurfaceTextureListener`、`ScaleTypeDetector` 等）时，子类的 `override onXxx(...)` 回调会在父类的 `getXxx()` / `acquireXxx()` 方法**调用栈中**同步触发，不是在调用返回后。**

如果子类的"用户回调"需要在 `override onXxx` 中访问 `this.someField`，**而这个 `this.someField` 是在 `getXxx()` 调用返回后才赋值的**——回调执行时 `this.someField` 仍是 `null`，用户代码会拿到错误的 null。

**案例**：本项目 `MicroHabitSqliteHelper extends SQLiteOpenHelper`：

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

**通用规则**：

| 场景 | 父类行为 | 修复模式 |
|------|----------|---------|
| `SQLiteOpenHelper` 子类 | `onCreate(db)` / `onUpgrade(db, oldV, newV)` 在 `getWritableDatabase()` 内部同步触发 | wrapper 接收 `db` 参数 → `this.db = db` → 调用户 callback |
| `Handler.Callback` 子类 | `handleMessage(msg)` 在 `handler.sendMessage()` / `post()` 内部排队，**异步**触发 | 通常不需要特殊处理（异步触发，调用栈已返回）|
| `TextToSpeech.OnInitListener` | `onInit(status)` 在 `TextToSpeech(ctx, listener)` 内部**异步**触发 | 同样不需要（异步），但用户代码仍需判 status |
| `TextureView.SurfaceTextureListener` | `onSurfaceTextureAvailable(...)` 在 `setSurfaceTextureListener()` 设置后，**异步**触发 | 同样不需要（异步）|
| `DialogInterface.OnClickListener` | `onClick(dialog, which)` 在用户点击时触发 | 同样不需要（事件驱动）|

**关键判断标准**：
- 如果父类 `onXxx` 在 `getXxx()` / `acquireXxx()` / `setXxx()` **同一个方法调用栈中同步触发** → 必须用 wrapper + 参数透传模式
- 如果 `onXxx` 是**异步**触发（Handler/Listener/Event 等） → 通常无问题，正常写即可

**自查命令**（找出可能命中此模式的代码）：
```powershell
# 查找"先 helper.getXxx() 再 this.X = ..."模式
Get-ChildItem "uni_modules" -Recurse -Filter "*.uts" | 
  Select-String -Pattern "this\.\w+\s*=\s*this\.\w+!!\.get\w+\(" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中后再检查该文件是否有"override onXxx"调用"this.X.method()"
```

**关联规则**：
- §26 extends Java 抽象类（精确匹配父类签名）— 此条是该规则在"回调时机"维度的补充
- §17 类私有可变属性 smart cast 失效 — wrapper 内 `this.db = db` 没有 smart cast 问题，因为是赋值不是读取
- §24/§25 number vs Int — wrapper 类型用 `number`（UTS 风格），内部 override 用 `Int`（Kotlin 签名精确匹配）

### 29. `SqliteStore.insert/update` 数据 API 必须用 `SqlRow` 自定义类型，禁止 `Map<string, any>` / 对象字面量 / 字符串拼接

**【最高优先级】项目内所有 DAO 调用 `dbManager.insert/update` 时，`row` 参数必须是 `SqlRow` 类型（`{ columns: string[], values: any[] }`）。绝对禁止传 `Map<string, any>`、对象字面量 `{...}`、或 JSON 字符串拼接。**

**历史教训**（3 个阶段 4 个已确认失败方案）：

**阶段 1**：`SqliteStore.insert(table, data: any)` + DAO 传对象字面量 + 内部走 `JSON.stringify` → 60 行自研 JSON 解析器（`dataToKVs` / `splitTopLevelCommas` / `findTopLevelColon`）。该解析器有 **3 个已确认 bug**：
1. **字符串值带嵌入引号**：`"eye_blink"` → `"\"eye_blink\""`（10 字符含引号）
2. **`null` 被拆为字面量字符串**：`null` → `"null"`（4 字符）而不是真正的 null
3. **所有值丢失类型**：`0`/`1.5`/boolean 全部变成 string，SQLite 类型推断失效

实际症状：`insertActionLog returned id=-1`，debug 页 `action_logs count=0`，数据完全丢失。

**阶段 2**：改用 `Map<string, any>` + 链式 `.set()`。`SqliteStore` 内部用 `for (const k of data.keys())` / `for (const v of data.values())` 迭代。**编译失败**：
- `data.keys` 是 Kotlin `MutableSet<String>` **属性**（不是方法），`data.keys()` 触发 "Expression 'keys' of type 'MutableSet<String>' cannot be invoked as a function"
- `for...of` 翻译需要 `iterator()`，但 Kotlin 集合上 `iterator()` 有 **6 个歧义候选**（`Enumeration` / `Iterator` / `Map` / `MutableMap` / `CharSequence` / `BufferedInputStream`），编译失败 "Method 'iterator()' is ambiguous"
- **根因**：UTS `Map<K, V>` 映射到 Kotlin `MutableMap`，`keys`/`values` 是 Kotlin 属性而非方法，迭代模式与 JS 完全不同

**阶段 3（当前采用）**：`SqlRow` 自定义类型 + 平行数组。`columns: string[]` 和 `values: any[]` 一一对应，纯数组下标访问零歧义。

**强制规则**：

1. `SqlRow` 类型定义在 `uni_modules/uts-sqlite-store/utssdk/app-android/SqliteStore.uts`：
   ```ts
   export type SqlRow = {
     columns: string[]
     values: (any | null)[]   // 必须允许 null，因为 DAO 字段如 skip_reason 是 string | null
   }
   ```
   **`values` 必须用 `(any | null)[]` 而不是 `any[]`**：UTS `any[]` = `UTSArray<Any>`（非空元素），DAO 字面量 `[v1, v2, null]` 推断为 `UTSArray<Serializable?>`（Kotlin 最宽父类型 + null），无法赋给 `UTSArray<Any>`（error17）。改用 `(any | null)[]` = `UTSArray<Any | null>` 后推断成功。
2. `DatabaseManager.uts` re-export：`export { type SqlRow }`（让 DAO 可统一从 `./DatabaseManager` 导入）
3. DAO 内必须用 `const row: SqlRow = { columns: [...], values: [...] }` 构造 + 一次性传给 `dbManager.insert/update`
   - **重要**：`values: [...]` 字面量可能推断为 `UTSArray<Serializable?>`，需 SqlRow 的 `values` 字段是 `(any | null)[]` 才能赋值成功（详见第 1 条）
4. `SqliteStore.bindValue` 签名是 `v: any | null`（不是 `v: any`），函数体已处理 null 走 `stmt.bindNull` 分支
4. **必须**用 `const row: SqlRow = {...}` 带类型注解的形式，**禁止**直接传对象字面量 `{columns: [...], values: [...]}`（会推断为 `UTSJSONObject` 而非 `SqlRow`，触发 §18 的"Return type mismatch"）
5. `columns` 和 `values` 数组长度必须严格相等（SqliteStore 内部有 `values.length !== n` 校验，否则返回 -1/0）
6. **禁止** `Map<string, any>` / `new Map()` / `for...of` on Kotlin collection / 自研 JSON 解析器
7. `LlmCacheDao.uts` 是唯一例外：走 `dbManager.execSql('INSERT OR REPLACE...')` 路径，不经 `insert()`

**正确示例**（`database/ActionLogDao.uts`）：

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

**自查命令**：

```powershell
# 找出项目中所有"Map<string, any>"反模式（已采用 SqlRow，禁止使用）
Get-ChildItem "database" -Filter "*.uts" |
  Select-String -Pattern "new Map<string, any>\(\)" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中必须改为 const row: SqlRow = { columns: [...], values: [...] }

# 找出"对象字面量直接传给 dbManager.insert/update"的反模式
Get-ChildItem "database" -Filter "*.uts" |
  Select-String -Pattern "dbManager\.(insert|update)\([^,]+,\s*\{" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中必须用 const row: SqlRow = { ... }; dbManager.insert('table', row)
```

**关联规则**：
- §18 函数返回类型 + 对象字面量 → 必须用 `const x: Type = {...}; return x` 模式 — `const row: SqlRow = {...}` 是该规则在 DAO 层的应用
- §19 对象类型必须用 `type` 禁止 `interface` — `SqlRow` 必须是 `type` 定义
- §13 子组件对象 prop + computed 字段访问 → 运行时 ClassCastException — 类似的"UTS 对象包装陷阱"
- §17 模块级 `let` smart cast 失效 — 局部 `const row` 可正常 smart cast，无需 `!!`

### 30. UVUE CSS 子集限制：`baseline` / `linear-gradient` 兼容性

**【最高优先级】** UVUE 的 CSS 子集**比 web 标窄**，两类常见兼容性问题：

**(A) `align-items: baseline` 编译失败**：
```
[plugin:uni:app-uvue-css] ERROR: property value `baseline` is not supported for `align-items`
```
- UVUE 仅支持 4 个值：`center` | `flex-start` | `flex-end` | `stretch`
- **必须用 `flex-end` 替代 `baseline`**（数字底部对齐 ≈ baseline 效果）

**(B) `background: linear-gradient(...)` 兼容性差**：
- 早期 HBuilderX / Android 4.x webview 不渲染渐变 → "白底白字看起来一片空白"
- **必须配 `background-color` fallback**（写在渐变**之前**）：
  ```css
  .greeting-bar {
    background-color: #ED8F03;       /* fallback：渐变不支持时用纯色 */
    background: linear-gradient(135deg, #FFB75E 0%, #ED8F03 100%);
  }
  ```
  `background` 会覆盖 `background-color`，但不支持时 `background` 整体忽略，`background-color` 仍生效

**自查**：
```powershell
Get-ChildItem -Recurse -Include "*.uvue" |
  Select-String -Pattern "align-items:\s*baseline|background:\s*linear-gradient" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# align-items: baseline 改 flex-end
# background: linear-gradient 前加 background-color
```

**关联规则**：
- §13 强制 prop 基础类型（同类"UVUE 子集 vs web 标"问题）
- §31 放弃 Canvas 改 view（同类 Canvas API 子集问题）

### 31. 放弃 Canvas 改 view + SVG 写图表

**【最高优先级】** 实测发现 `uni.createCanvasContextAsync` + Canvas 2D 在 UVUE 中多个渲染陷阱：
- `<scroll-view>` 内 `onReady` 时 `canvas.offsetWidth = 0` → 图表完全空白
- `canvas.width = w * dpr` + `ctx.scale(dpr, dpr)` 后仍用逻辑坐标 → HiDPI 错位
- `Math.random()` 生成 canvasId **1/100000 概率碰撞**
- 内部异常被静默吞掉

**修复模式**：**图表组件 100% 改用 view + SVG**：

| 图表 | 实现 |
|------|------|
| 柱状图 | 每列 `<view>` 高度 `style="height: (val/maxVal)*100%"` |
| 折线图 | `<svg viewBox="0 0 100 100">` + `<path>` + `<circle>` + `<text>` |
| 热力图 | 7×N `<view>` 网格，按 count 着色（白→浅黄→浅绿→深绿）|
| 饼图 | `<svg>` + `<path>` 画扇形（百分比转弧度算 `d`） |

**SVG 饼图扇形 path 算法**：
```ts
function arcPath(startPct: number, endPct: number): string {
  const startRad = (startPct / 100) * 2 * Math.PI - Math.PI / 2
  const endRad = (endPct / 100) * 2 * Math.PI - Math.PI / 2
  const cx = 50, cy = 50, r = 40
  const x1 = cx + r * Math.cos(startRad)
  const y1 = cy + r * Math.sin(startRad)
  const x2 = cx + r * Math.cos(endRad)
  const y2 = cy + r * Math.sin(endRad)
  const largeArc = (endPct - startPct) > 50 ? 1 : 0
  return 'M ' + cx + ' ' + cy + ' L ' + x1.toFixed(2) + ' ' + y1.toFixed(2) +
         ' A ' + r + ' ' + r + ' 0 ' + largeArc + ' 1 ' + x2.toFixed(2) + ' ' + y2.toFixed(2) + ' Z'
}
```

**SVG 关键约束**：
- `viewBox` 用 `0 0 100 100`（标准化坐标）
- 颜色用 `:fill` / `:stroke` 动态属性（**不能**用 `:class`）
- **不要**用 `<foreignObject>`（UVUE 不支持）
- 坐标用 `.toFixed(2)` 保留精度

**自查**：
```powershell
Get-ChildItem "components" -Recurse -Filter "*.uvue" |
  Select-String -Pattern "createCanvasContextAsync|getContext\(" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中 = 漏改，立即重写
```

**关联规则**：
- §8 Canvas API 正确用法（保留作为参考，但**新组件不要用 canvas**）
- §13 图表 prop 必须扁平基础类型

### 32. UVUE 常用模板：watch lambda + 弹窗组件

**【最高优先级】** 2 个高频模板：

**(A) `watch` 监听 prop 变化**：
```ts
// 单个 prop
watch(
  () : boolean => props.visible,
  (v: boolean) : void => {
    if (v) { refresh() }
  }
)
// 多个 prop
watch(
  [() : number => props.startHour, () : number => props.endHour],
  ([s, e]: [number, number]) : void => { /* ... */ }
)
```
**不要**写 `watch(props.visible, ...)` —— UTS 不会自动解包。

**(B) 弹窗组件标准结构**（`NumberPickerDialog` / `TimeRangePickerDialog` / `PhoneUsageDialog`）：
```vue
<template>
  <view v-if="visible" class="dialog-mask" @tap="onMaskTap">
    <view class="dialog-card" @tap.stop="">
      <!-- 内容 -->
    </view>
  </view>
</template>
```
- `v-if="visible"` 控制显隐
- `position: fixed; top/left/right/bottom: 0; z-index: 999` 覆盖全屏
- **`@tap.stop` 在 dialog-card 阻止冒泡**（避免点内容区也关闭）
- Props: `visible: boolean` + 业务参数
- Emits: `close` + 业务 `confirm(value)`

**父组件调用**：
```ts
const showDialog = ref<boolean>(false)
function onConfirm(v: number): void {
  showDialog.value = false
  // 处理 v
}
```
```vue
<MyDialog :visible="showDialog" @close="showDialog = false" @confirm="onConfirm" />
```

### 33. 模块内函数不能重复 + `app_usage_snapshots` 累加时序 + `<script setup>` 多行 lambda 显式返回类型注解触发 parser bug

**【最高优先级】** 三类高频坑：

**(A) 模块内函数不能重复声明**——报错位置经常错乱（指向下一个函数）：
```ts
function barHPercent(...): ... { ... }   // ← 编译器报这里 "expected ','"
function shouldShowLabel(...): ... { ... }   // 实际是这里重复
function shouldShowLabel(...): ... { ... }   // ← 真正的重复声明
```
- UTS 不允许同名函数重复声明
- 编辑多个文件时，**每次保存前 grep 自检**：
  ```powershell
  Get-ChildItem -Recurse -Include "*.uts","*.uvue" | ForEach-Object {
    $funcs = (Get-Content $_.FullName) | Select-String -Pattern "^function\s+(\w+)" |
      ForEach-Object { $_.Matches[0].Groups[1].Value }
    $dupes = $funcs | Group-Object | Where-Object { $_.Count -gt 1 }
    if ($dupes) { Write-Host "$($_.Path): duplicate: $($dupes.Name -join ', ')" }
  }
  ```

**(B) `<script setup>` 中多行 lambda `() : T => { ... }` 触发 parser bug**：

错误信息位置错乱：
```
[plugin:uni:app-uvue] Unexpected token, expected "," (62:0)
59 |    return m
60 |  }
61 |  
62 |  function barHPercent(v: number): number {   // ← 报这里
   |  ^
```
**实际是 line 52 多行 lambda 解析失败**，错误位置错乱到下一个函数。

**修复**：去掉多行 lambda 显式返回类型注解，依赖 context typing：
```ts
// ❌ 触发 parser bug（多行 block body + 显式 :T）
const maxVal = computed<number>(() : number => {
  let m = 0
  for (...) { ... }
  return m
})

// ✅ 多行去掉注解（依赖 computed<number> 锚定）
const maxVal = computed<number>(() => {
  let m = 0
  for (...) { ... }
  return m
})

// ✅ 单行保留 :T 注解（不会触发 bug，Kotlin 风格精确）
const eyeScore = computed<number>(() : number => store.eyeScore.value)
const allActions = computed<MicroAction[]>(() : MicroAction[] => getEnabledActions())
```

**判定规则**：
- **单行** `() : T => expr`（末尾是 `=>` 直接跟表达式）→ **保留** `:T` 注解
- **多行** `() : T => { ... }`（末尾是 `=>` 后接 `{`）→ **去掉** `:T` 注解

**原理**：UTS 强类型仍生效（`computed<T>` 是 type anchor，lambda 内部 return 受 T 约束），只是**显式标注**和**上下文推断**两种合法写法中，多行 block body 形式有 parser bug，只能用上下文推断。

**自查**：
```powershell
# 找出"多行 block body lambda 显式返回类型注解"反模式
Get-ChildItem -Recurse -Include "*.uvue" |
  Select-String -Pattern "computed<.*>\(\) : .* => \{$|watch\(.*\(\) : .* => \{$" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中 = 去掉 : T 注解
```

**注意 watch/getter 必须显式标注返回类型**——`watch(() => props.visible, ...)` ❌：
```
error: Return type mismatch: expected 'Function', actual 'Boolean'.
at NumberPickerDialog.uvue:112
```
UTS 编译器对 `() => expr`（无类型注解）会把整个 lambda 推断为 `expr` 的类型（即 `Boolean`），而不是 `Function`。
**必须用** `() : T => props.visible`（单行 OK 不会触发 parser bug）。

```ts
// ❌ UTS 推断 lambda 返回 = Boolean，watch 期望 Function
watch(() => props.visible, (v: boolean) : void => { ... })

// ✅ 显式 : boolean，lambda 是 Function
watch(() : boolean => props.visible, (v: boolean) : void => { ... })
```

**(C) `app_usage_snapshots` 累加时序**：
`AppMonitorService.uts.startCheck` 每 1 秒调 `cbs.onAppDurationTrigger(info)`，**不能传 `info.continuousMs` 给 `insertOrUpdateSnapshot`**（会重复累加，每秒 +1000ms × N = 真实时间 × N）。

**正确：累加 1 秒**：
```ts
// App.uvue onAppDurationTrigger callback 中：
onAppDurationTrigger: (info: AppForegroundInfo): void => {
  try { insertOrUpdateSnapshot(info.packageName, info.packageName, 1) } catch (_) {}
  // 每次回调 = 1 秒（Handler.postDelayed(..., 1000)），累加 1 = 真实时长
  // 不需要维护 lastContinuousMs 状态
}
```

**关联规则**：
- §9 script setup 函数不提升（同名函数第二次声明**也不允许**）
- §29 SqlRow 数据 API

### 34. CSS 长度单位 / 函数返回类型 / 函数调用顺序

**【最高优先级】** 三类高频坑：

**(A) UVUE CSS 长度单位仅支持 `number` / `pixel`**——`vh` / `vw` / `em` / `rem` 全部不支持：

```
[plugin:uni:app-uvue-css] ERROR: property value `85vh` is not supported for `max-height`
(supported values are: `number`|`pixel`)
```

| 写法 | 状态 |
|------|------|
| `width: 90%` | ✅ 支持（容器尺寸） |
| `max-width: 420px` | ✅ 支持 |
| `max-height: 85vh` | ❌ 改 `max-height: 560px` |
| `padding: 1em` | ❌ 改 `padding: 16px` |
| `font-size: 0.9rem` | ❌ 改 `font-size: 14px` |

**(B) 函数返回类型禁用内联 Object Literal**——错误码 UTS110111101：
```ts
// ❌ Direct declaration of Object Literal Type is not supported
function getSvgSegments(): { d: string, color: string }[] { ... }
const obj: { name: string } = { name: 'x' }   // 函数返回 OK，但 inline 注解不允许

// ✅ 必须用 type 抽出
type SvgSegment = { d: string, color: string }
function getSvgSegments(): SvgSegment[] { ... }
const obj: NamedType = { name: 'x' }
```

**(C) `<script setup>` 块内函数必须先定义后调用**（§9 强化）：

```ts
function getSvgSegments(): SvgSegment[] {
  for (let i = 0; i < segments.value.length; i++) {
    const s = segments.value[i]
    const d = arcPath(s.startAngle, s.endAngle)  // ❌ arcPath 在 getSvgSegments 之后定义
    ...
  }
}
function arcPath(startPct: number, endPct: number): string { ... }  // 实际定义
```

UTS module-level 函数有 hoisting，**但 `<script setup>` 块内没有**。被调函数必须**在调用前**出现。

template 中的 `@tap="close"` 是字符串引用（runtime 解析），不触发该错误；**只有 script 块内的实际函数调用**才会触发。

**修复**：把所有被调函数提到调用者之前（"自顶向下"写法）。

**自查**：
```powershell
# 1. CSS 长度单位
Get-ChildItem -Recurse -Filter "*.uvue" |
  Select-String -Pattern "max-(height|width):\s*\d+(vh|vw|%)" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber)" }

# 2. 函数返回内联 Object Literal
Get-ChildItem -Recurse -Include "*.uvue","*.uts" |
  Select-String -Pattern "function \w+\(\):\s*\{" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber)" }

# 3. <script setup> 块内函数顺序（仅 .uvue）
# 自定义 awk 脚本：在 <script>...</script> 块内搜"调用在定义前"
```

**关联规则**：
- §9 script setup 函数不提升
- §19 对象类型必须用 type 禁止 interface
- §30 UVUE CSS 子集限制

### 35. watch / computed / onXxx 等 lifecycle 的 lambda 第一个参数必须显式标注返回类型

**【最高优先级】** UTS 编译器对 `() => expr`（无类型注解）的 lambda 会把**整个 lambda 推断为 `expr` 的类型**（即 `Boolean`），而不是 `Function`。导致：
```
error: Return type mismatch: expected 'Function', actual 'Boolean'.
at NumberPickerDialog.uvue:112
```

**错误写法**：
```ts
// ❌ () => props.visible → 推断为 () => Boolean，整体被当 Boolean
watch(() => props.visible, (v: boolean) : void => { ... })
```

**正确写法**：
```ts
// ✅ () : boolean => props.visible → 显式标注 lambda 返回 boolean，整体是 Function
watch(() : boolean => props.visible, (v: boolean) : void => { ... })

// ✅ 同样：onXxx 生命周期
onLoad((option) => { ... })   // option 是 any，单参数可省略 : type
onLoad((option: OnLoadOption) => { ... })   // 也 OK
```

**多参数也适用**：
```ts
// ❌ watch([() => props.a, () => props.b], ...) 可能推断为 ([Boolean, Boolean])，不是 Array<Function>
// ✅ watch([() : number => props.a, () : number => props.b], ...)
```

**自查**：
```powershell
# 找出 watch/onXxx 中无类型注解的 lambda
Get-ChildItem -Recurse -Filter "*.uvue" |
  Select-String -Pattern "watch\(\(\) =>|onLoad\(\(\w+\) =>|onReady\(\(\w+\) =>|onShow\(\(\w+\) =>" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中 = 添加 : T 标注
```

**关联规则**：
- §33(B) lambda 类型注解（多行 block body 不要 `:T`，单行表达式要 `:T`）

---

### 36. UVUE **不支持** `<svg>`/`<circle>`/`<path>`/`<text>` 标签

**【最高优先级】** 实际测试证实 §31 中"图表组件 100% 改用 view + SVG"在 UVUE 中**不可用**。HBuilderX 5.x 的 UVUE 编译器：

- **不识别** `<svg>`、`<circle>`、`<path>`、`<text>` 等 SVG 标签
- 编译期不报错，**运行时**在控制台打印 `Failed to resolve component: svg/path/circle/text. Unrecognized components will be treated as "view"`
- 静默降级为 `<view>` → 任何依赖 SVG 渲染的特性（viewBox、stroke、fill、path d=）**全部失效**
- **即使 v-if="false"（不可见），只要该 .uvue 文件被 import，模板就参与编译**，警告就会刷屏

**实测场景**：`PhoneUsageDialog.uvue` 用 `<svg viewBox="0 0 100 100"><circle/><path/></svg>` 画饼图。打开对话框时，控制台秒刷 7-10 条警告 + 饼图完全不可见（圈变成空 view）。

**修复**（已采用）：**所有图表 100% 改用 view 写**：
| 图表 | 实现 | 备注 |
|------|------|------|
| 柱状图 | 每列 `<view>` 高度 `style="height: (val/maxVal)*180px"` | 见 `BarChart.uvue` |
| 折线图 | 散点 `<view>` + 数值 `<text>` 标签 | 见 `LineChart.uvue`（UVUE 无 transform） |
| 热力图 | 7×N `<view>` 网格，按 count 着色 | 见 `HeatmapCalendar.uvue` |
| 饼图/环形图 | **横向堆叠条**（一行多个 view，flexGrow 比例分配）+ 图例 | 见 `PhoneUsageDialog.uvue`（替代 SVG 饼图） |

**PhoneUsageDialog 重写后的饼图方案**（`stacked-bar` 横向堆叠条）：
```vue
<view class="stacked-bar">
  <view
    v-for="(seg, si) in segments"
    :key="'sg-' + si"
    class="stacked-seg"
    :style="{ flexGrow: seg.minutes > 0 ? seg.minutes : 0, backgroundColor: seg.color }"
  ></view>
</view>
```
```css
.stacked-bar { width: 100%; height: 16px; flex-direction: row; border-radius: 4px; overflow: hidden; background-color: #F0F3F4; }
.stacked-seg { height: 100%; }
```
每段 app 占的宽度比例 = `flexGrow: 它的 minutes 值`，flex 容器自动按比例分配。配合右侧图例（每行 `<view dot><text label><text percent><text minutes>`），效果类似饼图但纯 view。

**自查**：
```powershell
Get-ChildItem -Recurse -Filter "*.uvue" |
  Select-String -Pattern "<svg|<circle|<path[^/]|<text " |
  Where-Object { $_.Line -notmatch "text class" } |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中 = 必须改写为 view-based
```

**关联规则**：
- §30 UVUE CSS 子集限制（同类"UVUE 标签子集 vs web 标"问题）
- §31 §31 设计原则需要修正：图表组件 100% 改用 **view only**，不再依赖 SVG

---

### 37. `Map.get()` 返回 `any | null`：`as number` / `as string` 不会去掉 null，Android Int 形参会 NPE

**【最高优先级】** Java/Kotlin 平台类型的 `Map<K, V>` 在 UTS 中映射为 KMM `MutableMap`。`row.get(col)` 返回 `any | null`（KMM `Any?`）。**`as number` / `as string` 是类型断言，不会真的把 null 转成 number/string**。直接传给 Android Int 形参 API 时，Kotlin 自动拆箱 `Number?` → 遇到 null 抛 `NullPointerException`。

**根因时序**：
```kotlin
// Java 侧期望
fun setTextColor(@ColorInt color: Int)   // primitive int，unbox 时 null 会 NPE

// UTS 侧传的
val textColor: Any? = map.get("text_color")  // 可能 null
view.setTextColor(textColor as Int)          // ❌ unbox null → NullPointerException
```

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

**自查**：
```powershell
# 找出 .get("...") 直接 as number/string 的反模式
Get-ChildItem "database" -Recurse -Filter "*.uts" |
  Select-String -Pattern '\.get\("[^"]+"\)\s+as\s+(number|string|number\s*\|\s*null)' |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中必须改为 getNum(row, '...') / getStr(row, '...') / getStrOrNull(row, '...')
```

**关联规则**：
- §25 `number` 字段传给 Android Int 形参 — 同一类问题，§25 解决类型声明，§37 解决运行时 null
- §18 函数返回类型 + 对象字面量 — 都涉及"UTS 强类型 vs Kotlin 平台类型"的边界处理

---

### 38. `ref<T | null>` 重要 ref 必须有非 null 初始值，模板首次渲染即用

**【最高优先级】** Dialog/弹窗组件的 `ref<T>` 如果初值是 `null` 或 `undefined`，模板首次渲染时**直接 NPE**（即使 `v-if="visible"` 隐藏，模板编译期就会尝试读取 ref.value 的字段类型）。

**反模式**：
```uts
const summary = ref<PhoneUsageSummary | null>(null)  // 初始 null
const segments = ref<PieSegment[]>([])
// 模板中：{{ summary.hourText }}  ← summary.value 是 null，访问 .hourText NPE
```

**修复**：
```uts
const summary = ref<PhoneUsageSummary>({
  totalMinutes: 0, totalSeconds: 0,
  hourText: '0 小时', minuteText: '0 分钟', appCount: 0
})
const segments = ref<PieSegment[]>([])  // 数组可以空但不能 null
```

**规则**：
- 任何"模板里要读取字段"的 ref 必须有**完整结构初始值**（即使字段都是 0/''）
- 数组 ref 用 `ref<T[]>([])` 即可（`v-for` 能正确处理空数组）
- 真正可空的数据用 `ref<T | null>(null)` + 模板 `v-if="xxx != null"` 显式守卫

**自查**：
```powershell
# 找"ref<X | null>(null)" 后模板未加 v-if 守卫的位置（需要逐文件判断）
Get-ChildItem -Recurse -Filter "*.uvue" |
  Select-String -Pattern "ref<\w+\s*\|\s*null>\(null\)"
```

**关联规则**：
- §18 函数返回类型 + 对象字面量 — 都涉及"严格非空"的兜底
- §37 Map.get() null 安全 — 同一类"边界处必须显式守卫"思想

---

### 39. `setTimeout` / `Handler.postDelayed` / `uni.request` 异步回调必须 try-catch，否则 NPE 触发 "Possible Unhandled Promise Rejection"

**【最高优先级】** UTS 异步回调（包括 `setTimeout` lambda、`Handler.postDelayed` lambda、uniCloud/uni.request 回调、watch callback）抛出异常时：

- 外层的 `try-catch` **捕获不到**（因为回调在不同调用栈）
- UTS 运行时打印 `java.lang.NullPointerException` + `at io.dcloud.uts.UTSPromiseKt.callFunction` + `at io.dcloud.uts.UTSTimerKt$setTimeout$runnable$1.doSth`
- 标记为 `Possible Unhandled Promise Rejection`，虽然不闪退但控制台刷屏 + 部分后续逻辑中断

**实测案例**（本项目）：`App.uvue` 的 `onAppDurationTrigger` 回调中 `insertOrUpdateSnapshot` 访问 SQLite 行 Map 字段时遇到 null（见 §37），异步回调 NPE → 控制台 50+ 行堆栈。用户看到的现象是"过几秒闪一下错误"。

**修复模式**：**所有异步 callback 函数体必须有 try-catch**：

```uts
// ❌ 反模式
onAppDurationTrigger: (info: AppForegroundInfo): void => {
  insertOrUpdateSnapshot(info.packageName, info.packageName, 1)  // 抛 NPE 时外层 try-catch 抓不到
  ...
}

// ✅ 正确：每个易错调用都包 try-catch
onAppDurationTrigger: (info: AppForegroundInfo): void => {
  try { insertOrUpdateSnapshot(info.packageName, info.packageName, 1) } catch (e) {
    console.error('snapshot error: ' + JSON.stringify(e))
  }
  try {
    const decision = shouldTrigger({...})
    if (decision != null) { ... }
  } catch (e) {
    console.error('trigger error: ' + JSON.stringify(e))
  }
}
```

**PhoneUsageDialog.refresh()**（重写时已加）：
```uts
function refresh(): void {
  try {
    summary.value = getTodayPhoneUsageSummary()
  } catch (e) {
    console.error('PhoneUsageDialog refresh summary: ' + JSON.stringify(e))
  }
  try {
    const segs = getTodayPieSegments()
    segments.value = segs != null ? segs : []
  } catch (e) {
    console.error('PhoneUsageDialog refresh segments: ' + JSON.stringify(e))
    segments.value = []
  }
}
```

**自查**：
```powershell
# 找出 setTimeout/uni.request/Handler 回调中无 try-catch 的 lambda
Get-ChildItem -Recurse -Include "*.uts","*.uvue" |
  Select-String -Pattern "setTimeout\(\(|postDelayed\(|\.request\(" |
  Where-Object { $_.Line -notmatch "try \{" }   # 简化：紧邻行无 try
```

**关联规则**：
- §37 Map.get() null 安全 — 同一类"边界处必须显式守卫"
- §17 模块级 let smart cast 失效 — 异步回调中 this.field 也需 !! 守卫

---

### 40. null-safe getter 工具函数参数必须用 `Map<string, any> | null`，**禁止**用 `any` 形参

**【最高优先级】** 上一节（§37）的 `getNum/getStr` 工具函数如果用 `row: any` 形参，**编译失败**：

```
error: Unresolved reference. None of the following candidates is applicable 
because of a receiver type mismatch:
fun String.get(index: Number): Char
at utils/DbRowUtils.uts:8:16
  const v = row.get(col)
                 ^
```

**根因**：UTS 编译器对 `any` 类型的 `.get(...)` 方法调用做候选匹配时，找到了**两个候选**：
1. `Map<string, any>.get(key: string): any` ← 期望匹配
2. `String.get(index: Number): Char` ← 字符串索引扩展（**优先级意外高于 Map.get**）

由于 `col: string` 可以同时匹配 `key: string`（需转 Number）和 `index: Number`（直接匹配），编译器把 `String.get` 当主候选，**抛 receiver type mismatch**。**`as number` / `as string` 等类型断言不会改变 receiver type**，所以这个错误无法通过 `as` 修复。

**修复**（已采用）：**`row` 形参必须声明为 `Map<string, any> | null`**：
```uts
// ❌ 反模式（receiver 类型歧义，编译失败）
export function getNum(row: any, col: string): number {
  const v = row.get(col)  // 编译器不知 row 是 Map，误判为 String.get
  ...
}

// ✅ 正确
export function getNum(row: Map<string, any> | null, col: string): number {
  if (row == null) return 0
  const v = row.get(col)  // row 类型明确，调用 Map.get
  if (v == null) return 0
  return v as number
}
```

**为什么 dao 函数需要这种 helper**：`dbManager.queryOne()` 实际返回类型就是 `Map<string, any> | null`，但**直接 `row.get('col') as number` 不安全**（见 §37）。所以中间必须经工具函数处理 null。**helper 形参类型用 `Map<string, any> | null` 是连接 §37 修复模式与 dao 代码的桥梁**。

**错误传播链**（一处形参错，调用方全挂）：
```
utils/DbRowUtils.uts: getNum(row: any, ...)  ❌ 编译失败
  ↓ 被 dao 调用
database/SettingsDao.uts: getStr(row, 'value')  ❌ "实际 Map<String, Any>? 预期 Any"
database/AppUsageDao.uts: 同上
database/ActionLogDao.uts: 同上
... (其他 8 个 dao)
```

**自查**：
```powershell
# 找错误的 any 形参 helper
Get-ChildItem -Recurse -Filter "*.uts" |
  Select-String -Pattern "function\s+(getNum|getStr|getStrOrNull)\s*\(\s*row:\s*any" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中必须改为 row: Map<string, any> | null
```

**关联规则**：
- §37 Map.get() 的 null 安全 — 同一组修复模式，§40 修正 §37 实现细节
- §18 函数返回类型 + 对象字面量 — UTS 强类型 vs Kotlin 平台类型的边界处理思路一致

---

### 41. `llm_history` 持久化表 vs `llm_cache` 临时缓存：用途/数据结构/清理策略不同

**【最高优先级】** 两个表都和"LLM 输出"相关，但**不是同一概念**：

| 表 | 用途 | 数据结构 | 清理策略 | 典型大小 |
|----|------|---------|---------|---------|
| `llm_cache` | LLM 响应**临时缓存**（避免重复请求） | `cache_key + cache_type + response + expires_at` | 过期自动清理（24h/7d） | 几十条 |
| `llm_history` | LLM 评估**完整历史**（审计+回显+给用户看） | `stage + context_json + ai_raw_response + parsed_result_json + adhoc_text + suggested_rule_json + reasoning + created_at` | **手动清理**（30 天滚动），**可保留更久** | 几百条 |

**关键差异**：
- `llm_cache` 是"为了快"（避免重复调 LLM）
- `llm_history` 是"为了看"（用户/调试/AI 反馈训练）
- 混用会导致：要么 history 表被过期清理搞丢（用户看不到上周 LLM 怎么说），要么 cache 表无限增长（占空间）

**强制规则**：
- 任何"展示给用户的历史 LLM 输出"（如主页"AI 历次判断"卡片）→ 写 `llm_history`
- 任何"避免重复 LLM 请求的 key-value 缓存"（如同一天同种小节的每日小结）→ 写 `llm_cache`
- 阶段 1 评估结果（adhocText）→ 写 `llm_history.stage='pre'`
- 阶段 3 评估结果（suggestedRule）→ 写 `llm_history.stage='post'`
- `cleanOldHistory(30)` 在每次 App.uvue onAppShow 时调用（**不**走 expires_at 过期机制）
- `cleanExpired()` 清理 `llm_cache` 的过期记录

**参考 `database/LlmHistoryDao.uts`** 实现。

**自查**：
```powershell
# 找 LLM 输出是否写到 llm_cache（应该是缓存的 key-value）
Get-ChildItem -Recurse -Filter "*.uts" |
  Select-String -Pattern "llm_cache.*insert|llm_cache.*save" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 写入 llm_cache 的只能是 daily_summary / weekly_report / rule_suggest / one_liner 这类"避免重复请求"的内容
# pre_trigger / post_action 必须写到 llm_history
```

**关联规则**：
- §32(B) 弹窗组件标准结构 — 主页的 LlmHistoryCard 用同样的 v-if + dialog-mask 模式
- §29 SqlRow 数据 API — LlmHistoryDao.insert 全部用 SqlRow + getNum/getStr/getStrOrNull

---

### 42. 全局事件总线（`uni.$emit` / `uni.$on`）用于跨页面/原生层通信

**【最高优先级】** 跨页面/跨层通信三种方式：

| 场景 | 推荐方式 |
|------|---------|
| 父子页面 props/emit | `defineProps` + `defineEmits` |
| 页面间互不相关 | `uni.$emit` / `uni.$on`（全局事件总线） |
| App.uvue ↔ 任意页面 | `uni.$emit` / `uni.$on`（App.uvue 在 onLaunch 注册，任意页面 $emit） |

**典型场景**（本项目 Day 4 实战）：
- `App.uvue` 的 `onAppDurationTrigger` 回调（无障碍服务触发）→ 计算 LLM 建议触发规则 → 需要弹窗给用户 → 但**当前用户可能在任意页面**
- **解法**：`App.uvue` `uni.$emit('showTriggerRuleDialog', {rule, reasoning})`，**所有页面**的 `onLoad` 都 `uni.$on('showTriggerRuleDialog', ...)` 监听
- 当前在哪个页面就哪个页面的监听器处理（其他页面的监听器被挂起不响应）
- 用户点"接受"后 emit 一条 close 事件 / 直接更新 store

**关键规则**：
- `uni.$emit` 参数**必须能 JSON 序列化**（string / number / boolean / object / array），不能传 UTS 内部类型
- `uni.$on` 回调参数类型用 `any`（UTS 编译器会推断为 `any`，但实际传入的是反序列化的 object），访问字段前**必须**做 null 检查
- 监听器**必须在 `onLoad` 中注册**，不能放 `onShow`（否则会重复注册）
- 多个 `onLoad` 注册可以用相同的事件名（不冲突），但**不**会自动反注册，需要在 `onUnload` 中 `uni.$off`

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

---

### 43. LLM JSON 输出三级容错解析

**【最高优先级】** LLM 经常返回非标准 JSON（多余文字、格式错误、Markdown 包裹）。所有 LLM 解析必须经过容错：

**三级策略**：
1. **Level 1（直 parse）**：直接 `JSON.parse(raw)` 解析完整字符串
2. **Level 2（正则提取）**：失败则用正则 `/\{[\s\S]*\}/` 提取第一个 `{...}` 块再 parse
3. **Level 3（fallback 字段）**：仍失败则返回空对象，由调用方决定 fallback

**为什么是这三层**：
- Level 1 处理 80% 正常情况
- Level 2 处理 15% LLM 偶尔加的解释性文字（"好的，以下是您要的 JSON：" 前缀）
- Level 3 处理 5% 完全乱码情况

**典型 LLM 输出模式**（实测）：
```
✅ {"adhocText": "眼睛累了，眨一眨吧", "stateDescription": "检测到久坐"}
⚠️ 好的，以下是您要的 JSON：
    {"adhocText": "眼睛累了", "stateDescription": "检测到久坐"}
⚠️ ```json
    {"adhocText": "眼睛累了", "stateDescription": "检测到久坐"}
    ```
⚠️ {"adhocText": "眼睛累了"}（字段缺失）
```

**正确模板**（见 `services/LlmTriggerFlow.uts:parseJsonThreeLevels`）：
```ts
function parseJsonThreeLevels(raw: string): UTSJSONObject | null {
  // Level 1: 直接 parse
  try {
    return JSON.parse(raw) as UTSJSONObject
  } catch (_) {}
  // Level 2: 正则提取 {...}
  try {
    const match = raw.match(/\{[\s\S]*\}/)
    if (match != null) {
      const m = match[0]
      if (m != null && m.length > 0) {
        return JSON.parse(m) as UTSJSONObject
      }
    }
  } catch (_) {}
  // Level 3: 完全失败
  return null
}
```

**字段兜底**（即使 parse 成功，也要逐字段 null 检查）：
```ts
function parsePreResponse(raw: string): ParsedLlmResult {
  const obj = parseJsonThreeLevels(raw)
  const result: ParsedLlmResult = {}
  if (obj == null) return result
  const adhoc = obj['adhocText']
  if (typeof adhoc === 'string' && (adhoc as string).length > 0) {
    result.adhocText = adhoc as string
  }
  const stateDesc = obj['stateDescription']
  if (typeof stateDesc === 'string' && (stateDesc as string).length > 0) {
    result.stateDescription = stateDesc as string
  }
  return result
}
```

**调用方处理 fallback**：
```ts
const adhoc = parsed.adhocText != null ? parsed.adhocText : getFallbackAdhoc()
const stateDesc = parsed.stateDescription != null ? parsed.stateDescription : '检测到需要休息'
```

**反模式**：
```ts
// ❌ 直接 JSON.parse 一次就完事
const obj = JSON.parse(raw) as ParsedLlmResult  // LLM 输出稍不规范就崩

// ❌ 字段直接用，不 null 检查
result.adhocText = obj['adhocText'] as string  // obj['adhocText'] 是 any | undefined

// ❌ 解析失败抛异常让调用方处理
try { ... } catch (e) { throw e }  // 异步回调 throw 接不到
```

**自查**：
```powershell
# 找 LLM 解析点，确认三级容错
Get-ChildItem -Recurse -Include "*.uts","*.uvue" |
  Select-String -Pattern "JSON\.parse" |
  Where-Object { $_.Line -notmatch "import|localStorage|getStorage" }
# 命中如果不是 llm 解析，可以保留 1 级；如果是 llm 解析，必须 3 级
```

**关联规则**：
- §18 函数返回类型 + 对象字面量 — `const result: ParsedLlmResult = {}; return result` 模式
- §39 异步回调必须 try-catch — LLM 解析在异步回调中，三级容错避免外层 try-catch 抓不到

---

### 44. `getHomepageData` 等"无参版本调用"在 App.uvue 异步回调中使用会触发"必须有 1 个参数"错误

**【最高优先级】** `getHomepageData(date: string)` 签名要求传 date 参数，但某些场景（如 LLM 触发流中只需要"今日概览数据"）调用方没有可用的 date 变量。

**反模式**：
```ts
// ❌ 没有 date 变量，硬传空串 → 编译可能通过但运行时可能拿到昨天数据
const homeData = getHomepageData('')
```

**正确做法**：调用方提前计算并缓存 date：
```ts
// ✅ 异步回调开头先取一次 today date，缓存复用
function safeToday(): string {
  try {
    const d = new Date()
    return d.getFullYear() + '-' +
      ('' + (d.getMonth() + 1)).padStart(2, '0') + '-' +
      ('' + d.getDate()).padStart(2, '0')
  } catch (_) {
    return ''
  }
}
```

或直接 import 已有 `today()` from TimeUtils：
```ts
import { today } from '../utils/TimeUtils'
const homeData = getHomepageData(today())
```

**为什么**：
- LLM 触发流（`App.uvue onAppDurationTrigger` 回调）在异步上下文，date 不能假设是"今天"
- 即使逻辑上应该是"今天"，也建议显式 `today()` 调用，保证时间窗对齐
- `getHomepageData('')` 这种"传空串"是反模式，调用方应该承担计算责任

**关联规则**：
- §39 异步回调 try-catch — LLM 触发流的"安全默认值"必须 try-catch
- §25 number/Int 边界 — `getHomepageData` 返回的 todayCompletedCount 等字段都是 number，传给其他 API 时要 .toInt()


---

### 45. UTS import 相对路径级别：`components/` → `models/` 是 `../`，**不是** `../../`

**【最高优先级】** HBuilderX 5.x 编译 `Could not resolve` 错误的常见原因 — 相对路径级别数错了。

**关键规则**：

| 文件所在 | 目标位置 | 相对路径 |
|---------|---------|---------|
| `components/X.uvue` | `models/Y.uts` / `services/Z.uts` / `constants/W.uts` | `../`（一级） |
| `pages/X.uvue` | 同上 | `../../`（两级） |
| `pages/X/Y.uvue` | 同上 | `../../../`（三级） |
| `App.uvue` | 同上 | `./`（同级） |

**反模式**（本项目已犯过一次）：
```ts
// ❌ components/LlmHistoryCard.uvue 错误写成：
import { LlmHistoryEntry } from '../../models/LlmHistoryEntry'  // 编译失败

// ✅ 正确：
import { LlmHistoryEntry } from '../models/LlmHistoryEntry'
```

**为什么容易错**：
- `pages/home/index.uvue`（3 层）从 `../../models/` 找 → 正确
- `components/StatusIndicator.uvue`（1 层）从 `../../models/` 找 → **错误**（多了一级）
- 思维惯性：写 `pages` 的 import 习惯了 `../../`，搬到 `components` 忘了减少一级

**参考**：
- `PhoneUsageDialog.uvue:76` 的正确写法是 `from '../services/PhoneUsageService'`（用 `../`）
- 这是项目里**唯一**一个 components 层 import 范例，必须照抄

**自查**：
```powershell
# 找出 components/ 里所有相对路径，确认都是 ../ 开头
Get-ChildItem "components" -Recurse -Filter "*.uvue" | ForEach-Object {
  Select-String -Path $_.FullName -Pattern "from '(\.\./|/)" |
    Where-Object { $_.Matches[0].Groups[1].Value -eq "../../" }
}
# 命中 = 路径级别错误，立即改
```

**修复模式**：
1. 找到错文件
2. 改 `../../models/` → `../models/`（去掉一级）
3. 改 `../../services/` → `../services/`
4. 改 `../../constants/` → `../constants/`
5. 重新打包

**关联规则**：
- §13 子组件 prop 必须基础类型 — `components/` 是 UI 组件层，主要用 props 不需要 import models 太多
- §9 script setup 函数不提升 — 即使路径对了，类型 import 还要配合 `import type`（UTS 自动推断）


---

### 46. 函数**参数**类型也禁用内联对象字面量（UTS110111101 错误码）

**【最高优先级】** §34(B) 提到"函数返回类型禁用内联 Object Literal"（错误码 UTS110111101），**但同样适用于函数参数类型**。

**反模式**：
```ts
// ❌ UTS110111101: Direct declaration of Object Literal Type is not supported
function formatScreenConditions(c: { appPackages: string[], includeHome: boolean }): string {
  ...
}

// ❌ 同理回调参数
function onChange(cbs: { onAgree: () => void, onSkip: () => void }): void {
  ...
}

// ❌ 数组元素类型
function getItems(): { name: string, count: number }[] { ... }
```

**正确做法**：**抽到独立 `type` 定义**：
```ts
// models/EffectiveTriggerRule.uts
export type ScreenConditions = {
  appPackages: string[]
  includeHome: boolean
}

// components/TriggerRuleDialog.uvue
import { EffectiveTriggerRule, ScreenConditions } from '../models/EffectiveTriggerRule'
function formatScreenConditions(c: ScreenConditions): string { ... }
```

**关键差异**（与 §34(B) 互补）：
- §34(B)：函数**返回**类型不能用内联
- §46：函数**参数**类型也不能用内联
- 共同点：凡是"类型注解位置的对象字面量 `{ a: 1 }`"都不允许
- 合法：函数**体内部** `const x: NamedType = { a: 1 }; x.method()`（`as NamedType` 类型的字面量是 OK 的）

**与 §18 / §19 配合**：
- §18：`return { ... }` 字面量需要 `const x: T = {...}; return x` 模式
- §19：对象类型用 `type` 而非 `interface`
- §46：参数类型用 `type` 而非内联 `{ }`

**自查命令**（项目级扫描）：
```powershell
# 找所有"函数参数/返回用内联对象字面量"
Get-ChildItem -Recurse -Include "*.uvue","*.uts" |
  Select-String -Pattern "function\s+\w+\([^)]*\{[a-zA-Z_]+\s*:|:\s*\{[a-zA-Z_]+\s*[,\}]" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中必须抽到独立 type 定义
```

**类型定义文件组织**：
- 跨文件用 → 放到 `models/Xxx.uts` 并 `export`
- 单文件内部用 → 放到同文件顶部

**关联规则**：
- §34(B) 函数返回内联 Object Literal
- §18 函数返回类型 + 对象字面量
- §19 type vs interface
- §45 import 相对路径级别（修了 import 路径后又遇到内联类型问题，**两类问题经常连发**）


---

### 47. 服务层本地空函数覆盖 plugin 导入 = 死代码陷阱

**【最高优先级】** UTS 模块级**函数名解析**遵循"先 import → 再本地声明"的查找顺序。如果 `services/X.uts` 里有：

```ts
import { speakSystemTts } from '../uni_modules/uts-audio-player/utssdk/app-android/index'  // 导入

function speakSystemTts(text: string, volume: number): void {  // 本地空函数
  // ❌ 空实现，覆盖了上面的 import！
}
```

**陷阱**：
- 编译**不报错**（UTS 允许"先 import 再声明同名函数"——后者覆盖前者）
- 同模块内所有 `speakSystemTts(...)` 调用**都走空函数**（无声播放）
- 但 `App.uvue` 直接 `import { speakSystemTts } from '...plugin/...'` 不受此覆盖影响（独立 import 路径），所以主流程 TTS 看似正常
- **结果**：测试时主流程 TTS 正常（App.uvue 自己 import），但 `TtsService.speakAdhoc / speakFixedGuide` 等内部函数永远静默，调试半天不知道哪里坏了

**正确模式**：

```ts
// 1. 导入并改名为 pluginSpeakSystemTts（防止和本地函数名冲突）
import { speakSystemTts as pluginSpeakSystemTts } from '../uni_modules/uts-audio-player/utssdk/app-android/index'

// 2. 内部所有调用都走 pluginSpeakSystemTts(...) —— 永远是插件真实实现
export function speakAdhoc(text: string): void {
  try { pluginSpeakSystemTts(text, volume) } catch (e) {
    console.warn('plugin 调用失败: ' + JSON.stringify(e))
  }
}

// 3. 不要在本模块内再次声明 speakSystemTts（即使是空函数）
```

**自查命令**：
```powershell
# 扫描 services/ 和 database/ 里所有"先 import 再同名声明"的反模式
Get-ChildItem "services","database" -Recurse -Filter "*.uts" | ForEach-Object {
  $imports = (Get-Content $_.FullName) | Select-String -Pattern "import\s*\{[^}]*(\w+)\s*\}\s*from" |
    ForEach-Object { $_.Matches[0].Groups[1].Value }
  $fns = (Get-Content $_.FullName) | Select-String -Pattern "^function\s+(\w+)" |
    ForEach-Object { $_.Matches[0].Groups[1].Value }
  $dupes = $imports | Where-Object { $fns -contains $_ }
  if ($dupes) { Write-Host "$($_.Path): 本地函数覆盖了 plugin import: $($dupes -join ', ')" }
}
# 命中必须改名为 xxx + as pluginXxx 模式
```

**本项目已修复**：
- `services/TtsService.uts:43` — 原 `function speakSystemTts(text, volume): void {}` 是空 stub
- 修复后 import 别名 `pluginSpeakSystemTts`，所有调用走插件真实实现

**正确模板**（调用插件 API 的 service 文件）：
```ts
import { somePluginApi as pluginSomeApi } from '../uni_modules/xxx/utssdk/yyy'

export function publicApi(arg: string): void {
  try {
    pluginSomeApi(arg)  // ← 永远走插件
  } catch (e) {
    console.warn('plugin 调用失败: ' + JSON.stringify(e))
  }
}
```

**关联规则**：
- §17 模块级 let smart cast 失效 — 函数查找遵循类似的"先到先得"规则
- §39 异步回调 try-catch — plugin 调用必须 try-catch 包裹

---

### 48. 重复导出函数导致 UTS bundler 内部 panic（Rust thread panic）

**【最高优先级】** 同一 `.uts` 文件中如果存在两个同名 `export function`（例如合并代码时遗留），UTS bundler 的 Rust 层会直接 panic，错误信息极其不直观：

```
thread '<unnamed>' panicked at crates/uts_bundler/src/inline.rs:47:13:
internal error: entered unreachable code: Multiple identifiers equivalent up to span hygiene found: getRecentLogs#674
First = getRecentLogs#673
Second = getRecentLogs#673
note: run with RUST_BACKTRACE=1 environment variable to display a backtrace.
```

**症状**：
- 不是"编译错误"而是 **Rust panic**（thread panicked / aborting）
- 错误信息中只出现函数名和 hygiene 编号（`#673`/`#674`），**不提示文件名和行号**
- Bundler panic 后可能导致 `unpackage/dist/dev/.uvue/app-android/main.uts` 生成失败 → 后续编译也报 "系统找不到指定的文件 (os error 2)"

**案例**（本项目）：
```ts
// database/ActionLogDao.uts — 两处同名导出函数

// 第 105 行
export function getRecentLogs(count: number): ActionLog[] {
  const rows = dbManager.query('SELECT * FROM action_logs ORDER BY created_at DESC LIMIT ?', [count])
  // ...
}

// 第 146 行（合并代码时遗留的重复）
export function getRecentLogs(limit: number): ActionLog[] {
  const rows = dbManager.query(
    'SELECT * FROM action_logs ORDER BY created_at DESC LIMIT ?',
    [limit]
  )
  // ...
}
```

**根因**：
- UTS 不允许同名函数重复声明（§33(A) 已记录）
- 但 bundler Rust 层对此场景的处理是 panic 而非友好的编译错误
- **panic 位置在 bundler inline 阶段**（`crates/uts_bundler/src/inline.rs`），远在语义分析之前，所以没有行号信息

**修复**：删除重复定义，保留其中一个即可。

**与 §33(A) 的区别**：
- §33(A) 记录的是"本地 `function` 重复"（编译器报错位置错乱，但至少有报错）
- §48 记录的是"**导出** `function` 重复"（bundler Rust 层直接 panic，无行号信息）
- 两者根因相同（§33(A)），但错误表现更严重（§48），必须额外警惕

**自查命令**（与 §33(A) 共用）：
```powershell
Get-ChildItem -Recurse -Include "*.uts","*.uvue" | ForEach-Object {
  $funcs = (Get-Content $_.FullName) | Select-String -Pattern "^\s*(export\s+)?function\s+(\w+)" |
    ForEach-Object { $_.Matches[0].Groups[2].Value }
  $dupes = $funcs | Group-Object | Where-Object { $_.Count -gt 1 }
  if ($dupes) { Write-Host "$($_.Name): duplicate: $($dupes.Name -join ', ')" }
}
```

**关联规则**：
- §33(A) 模块内函数不能重复声明 — §48 是 §33(A) 的"bundler panic"极端表现
- §47 服务层本地空函数覆盖 plugin 导入 — 同类"函数名冲突"问题

---

### 49. 子组件对象 prop 在模板中直接访问字段 → 编译失败 error18

**【最高优先级】** 即使 §13 已经强调了运行时 ClassCastException 风险，**编译期也会报错**。当子组件 prop 是对象类型（如 `EffectiveTriggerRule | null`），模板中直接 `{{ props.obj.field }}` 或 `{{ obj.field }}` 会触发：

```
error: 找不到名称"field"。参考: error18
at components/TriggerRuleDialog.uvue:13:64
{{ suggestedRule.actionId }}
                    ^
```

**编译期 vs 运行期双重风险**：
- **编译期**：UTS 编译器对模板中对象 prop 的嵌套字段访问报 error18（"找不到名称"）
- **运行期**：即使编译通过（如用 computed 中转），reactive proxy 包装导致 ClassCastException（§13）

**双重安全方案 = 拆为扁平基础类型 props**：

```vue
<!-- ❌ 对象 prop → 编译期 error18 + 运行期 ClassCastException -->
<script setup lang="uts">
const props = defineProps<{
  suggestedRule: EffectiveTriggerRule | null
}>()
</script>
<template>
  <text>{{ suggestedRule.actionId }}</text>
</template>

<!-- ✅ 扁平基础类型 props → 编译通过 + 运行安全 -->
<script setup lang="uts">
const props = withDefaults(defineProps<{
  actionName: string
  timeWindowStart: string
  timeWindowEnd: string
  timeThresholdMinutes: number
  screenCondText: string
  reasoning: string
}>(), {
  actionName: '',
  timeWindowStart: '',
  timeWindowEnd: '',
  timeThresholdMinutes: 0,
  screenCondText: '',
  reasoning: ''
})
</script>
<template>
  <text>{{ actionName }}</text>
</template>
```

**父组件配合修改**：
- 父组件用 `computed` 从对象中提取扁平字段传给子组件
- computed 内部对 `ref<T | null>` 做 null 守卫

```ts
// 父组件
const pendingRule = ref<EffectiveTriggerRule | null>(null)

const ruleActionName = computed<string>(() : string => {
  const r = pendingRule.value
  if (r == null) return ''
  const a = getActionById(r.actionId)
  return a != null ? a.name : r.actionId
})
const ruleTimeThresholdMinutes = computed<number>(() : number => {
  const r = pendingRule.value
  if (r == null) return 0
  return r.timeThresholdMinutes
})
```

```vue
<!-- 父组件模板 -->
<TriggerRuleDialog
  :visible="showRuleDialog"
  :action-name="ruleActionName"
  :time-threshold-minutes="ruleTimeThresholdMinutes"
  :reasoning="pendingReasoning"
/>
```

**自查**：
```powershell
Get-ChildItem -Recurse -Filter "*.uvue" |
  Select-String -Pattern "\{\{.*\.\w+\.\w+.*\}\}" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中 = 模板中嵌套属性访问，必须拆为扁平 props 或 computed 中转
```

**关联规则**：
- §13 子组件 prop 必须用基础类型 — §49 补充了编译期 error18 的问题
- §6 模板访问嵌套属性需通过 computed 中转 — §49 强调子组件场景必须用扁平 props 而非 computed 中转
- §32(B) 弹窗组件标准结构 — 对话框组件所有数据通过扁平 props 传入

---

### 50. 函数参数 `any` 类型上访问 `.field` → 编译失败 error18（§1 强化）

**【最高优先级】** AGENTS.md §1 已记录"`any` 不可方括号访问/点号访问"，但本条强调一个**极高频犯错场景**：函数参数声明为 `any`，函数体内访问 `.field`。

**案例 1**（函数参数 `any` + `.field` 访问）：
```ts
// ❌ App.uvue:146 — decision: any 上访问 .actionId / .triggerLevel / .ttsText / .durationMs
function showOverlayWithAdhoc(decision: any, adhocText: string, fallback: boolean): void {
  triggeredActionId = decision.actionId        // error18: 找不到名称"actionId"
  pendingDecisionLevel = decision.triggerLevel  // error18: 找不到名称"triggerLevel"
}

// ✅ 正确：用具体类型 TriggerDecision
import { TriggerDecision } from './services/TriggerEngine'
function showOverlayWithAdhoc(decision: TriggerDecision, adhocText: string, fallback: boolean): void {
  triggeredActionId = decision.actionId        // ✅ TriggerDecision.actionId 存在
}
```

**案例 2**（`let x: any = null` 后赋具体类型值）：
```ts
// ❌
let decision: any = null
decision = shouldTrigger(ctx)  // shouldTrigger 返回 TriggerDecision | null
if (decision == null) return
decision.actionId  // error18: any 上访问字段

// ✅
let decision: TriggerDecision | null = null
decision = shouldTrigger(ctx)
if (decision == null) return
decision.actionId  // ✅ smart cast 到非空 TriggerDecision
```

**根因**：UTS 编译器对 `any` 类型**完全禁止** `.field` 和 `["key"]` 访问（§1）。这与 TypeScript 的 `any` 行为完全不同（TS 允许 `any.field`）。

**修复模式**：
1. **函数参数**：用具体类型（`TriggerDecision`、`Map<string, any>` 等），不用 `any`
2. **局部变量**：声明为 `具体类型 | null`，不用 `any`
3. **确实需要 any 的场景**（如 `uni.$on` 回调参数、`uni.request` 回调）：先用 `as UTSJSONObject` 转换，再用 `obj['key']` 访问

**例外**（允许 `any` 但不访问字段）：
- `uni.$emit` 的 data 参数 → 只传不读
- `db: any` 只透传给 `execSql` → 不访问字段
- `data: any` 只 `JSON.stringify(data)` → 不访问字段

**自查**：
```powershell
# 找"函数参数 any" 后函数体内有 .field 访问
Get-ChildItem -Recurse -Include "*.uts","*.uvue" |
  Select-String -Pattern "function\s+\w+\([^)]*:\s*any[^]]" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中后检查函数体内是否有 .field 访问 → 必须改为具体类型
```

**关联规则**：
- §1 `any` 不可方括号/点号访问 — §50 是 §1 在函数参数场景的强化
- §17 模块级 let smart cast 失效 — `let x: Type | null = null` 赋值后仍需 `!!`
- §18 函数返回类型 + 对象字面量 — `const ctx: Type = {...}; shouldTrigger(ctx)` 模式

---

### 51. 对象字面量中函数属性简写（shorthand）在 UTS 中不合法

**【最高优先级】** UTS 对象字面量中，**不允许用函数名简写**（`refreshLlmHistory,`），必须显式写成方法形式（`refreshLlmHistory(): void { ... }`）。

**案例**（本项目 `stores/appStore.uts`）：
```ts
// ❌ 错误：函数名简写 → 实际类型为 'Unit'，预期 'Function0<Unit>'
const store: AppStore = {
  llmHistory,
  refreshLlmHistory,    // ❌ 简写，UTS 推断为 Unit（函数调用返回值）
  refreshHomeData(): void { ... }
}

// ✅ 正确：显式方法定义
const store: AppStore = {
  llmHistory,
  refreshLlmHistory(): void {  // ✅ 显式方法签名
    refreshLlmHistory()         // 调用模块级函数
  },
  refreshHomeData(): void { ... }
}
```

**根因**：UTS 对象字面量中 `fnName,` 等同于 `fnName: fnName`，编译器尝试将**函数本身**（`Function0<Unit>`）赋值给接口属性。但 UTS 不支持这种 JavaScript 风格的对象属性简写用于函数。

**规则**：
- 对象字面量中**引用 ref/computed** 可以用简写（`eyeScore,` → `eyeScore: eyeScore`），因为它们是值类型
- 对象字面量中**引用函数** 必须写成显式方法（`fn(): void { ... }`），不能用简写
- 箭头函数属性也不支持简写：`myFn,` ❌ → `myFn: () => { ... }` 或 `myFn(): void { ... }` ✅

**自查**：
```powershell
# 找对象字面量中的函数简写（模块级函数名后跟逗号，非 ref/computed）
Get-ChildItem -Recurse -Include "*.uts" |
  Select-String -Pattern "^\s{4}\w+,$" |
  ForEach-Object { Write-Host "$($_.Path):$($_.LineNumber) : $($_.Line.Trim())" }
# 命中后判断该标识符是否是函数 → 必须改为显式方法
```

**关联规则**：
- §9 script setup 函数不提升 — 同类"UTS 限制 vs JS 灵活性"问题
- §33(A) 模块内函数不能重复 — `refreshLlmHistory` 在模块级和对象方法中同名是合法的（不同作用域）

---

### 52. MiniMax 推理模型：使用 `reasoning_effort: "low"` 参数控制推理深度

**【最高优先级】** MiniMax-M2.7 是推理模型（类似 OpenAI o1），默认情况下：
```json
{
  "choices": [{
    "message": {
      "content": "",
      "reasoning_content": "实际思考过程...",
      "reasoning_details": [...]
    },
    "finish_reason": "length"
  }],
  "usage": {
    "completion_tokens": 400,
    "completion_tokens_details": { "reasoning_tokens": 400 }
  }
}
```
- `content` 为空，所有 token 被 reasoning 消耗
- `finish_reason: "length"` 表示 token 用完

**实测解决方案**：在请求体中加 `"reasoning_effort": "low"` 参数，让模型减少推理：

```ts
const reqBody = {
  model: 'MiniMax-M2.7',
  messages: [...],
  temperature: 0.7,
  max_completion_tokens: 500,
  reasoning_effort: 'low'  // ← 关键参数
}
```

**效果对比**（同一 prompt "User did 3 micro-actions, eye score 80"）：

| 配置 | content | finish_reason | reasoning_tokens | total_tokens |
|------|---------|---------------|-----------------|--------------|
| 默认（无参数）| 空字符串 | `length` | 400 | 400（全推理）|
| `reasoning_effort: "low"` | `{"one_liner":"...","summary":"...","tomorrow_goal":"...","encourage":"..."}` | `stop` | 51 | 213 |

**为什么不直接换非推理模型**：实测 `abab6.5s-chat` / `abab7-chat-preview` / `MiniMax-Text-01` 等非推理模型：
- 输出冗余（不严格遵循 "JSON only" 指令，常带解释文字）
- 需要更强 prompt 工程约束
- 输出质量（中文表达/JSON 严格度）不如 M2.7 + `reasoning_effort: "low"`

**经验**：M2.7 + `reasoning_effort: "low"` 是**最佳平衡点**（模型质量 + 输出可控性 + token 效率）。

**预防措施**：
- `max_completion_tokens` 设 400-600（实测够用）
- 监听 `finish_reason`，若为 `length` 说明 token 不够，记录 warn
- 保留 `reasoning_content` fallback（防御性，万一 `content` 仍为空）

**uni.request 发送 HTTP POST 的机制**：
```ts
uni.request({
  url: 'https://api.minimax.chat/v1/text/chatcompletion_v2',
  method: 'POST',
  header: {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + API_KEY
  },
  data: JSON.stringify(reqBody),  // 字符串化后的 JSON body
  timeout: 8000,
  success(res) { /* res.data = 响应体 */ },
  fail(err) { /* 网络错误 */ }
})
```
- `data: JSON.stringify(reqBody)` — UTS 中必须手动序列化，`uni.request` 不会自动 stringify
- `success(res)` 回调中 `res.data` 是自动解析的响应体（JSON → 对象）
- UTS 中 success/fail 回调参数不要手动标注类型（让编译器推断）

**关联规则**：
- §2 uniCloud.callFunction → uni.request HTTP 直连 — `uni.request` 是唯一可行的远程调用方式
- §39 异步回调必须 try-catch — success 回调内必须有 try-catch

---

### 53. LLM 调用统一架构：`callMinimaxChat` 公共函数 + 避免循环依赖

**【最高优先级】** 项目中所有 LLM 调用（daily summary / weekly report / pre-trigger / post-action）统一走 `CloudService.callMinimaxChat()` 公共函数。

**架构**：

```
callMinimaxChat(systemPrompt, userPrompt, maxTokens, onOK, onFail)
  ├─ callDaily()       → SYSTEM_PROMPT_DAILY + buildDailySummaryPrompt
  ├─ callWeekly()      → SYSTEM_PROMPT_WEEKLY + buildWeeklyReportPrompt
  └─ callLlmEvaluate() → SYSTEM_PROMPT_EVALUATE + buildPreTriggerPrompt / buildPostActionPrompt
```

**循环依赖陷阱**：
- `CloudService.uts` import `LlmPrompts.uts`（prompt 函数）
- `LlmPrompts.uts` 如果 import `CloudService.uts`（取 DailyData 类型）→ **循环依赖**
- **解决方案**：`DailyData` 类型定义移到 `models/DailySummary.uts`，CloudService 和 LlmPrompts 都从 models 导入

**规则**：
- 类型定义放 `models/`，不放 `services/`（避免 service 之间的循环依赖）
- prompt 构建函数放 `constants/LlmPrompts.uts`（输入类型化数据，输出 string）
- API 调用逻辑放 `services/CloudService.uts`（唯一负责 `uni.request` 的文件）
- 每个 LLM 场景需要 3 件东西：`SYSTEM_PROMPT_XXX` + `buildXxxPrompt()` + `callXxx()` 入口函数
- `parseJsonThreeLevels` 在 CloudService 和 LlmTriggerFlow 中各有一份（模块私有，不冲突）

**关联规则**：
- §12 跨文件类型必须 export — `DailyData` 从 models 导出
- §2 uniCloud.callFunction → uni.request HTTP 直连
- §52 MiniMax M2.7 reasoning_content 兜底

---

### 54. DAO `mapRow` 内部 JSON 解析抛异常 → 整批查询失败 + 渲染 NPE

**【最高优先级】** 任何 DAO 函数在 `mapRow` 内部调用 `JSON.parse` 都需要 try-catch + 单行级 fallback。**一行脏数据不应该让整批查询失败，进而导致上层 ref 异常 + Vue 渲染 NPE**。

**问题链路**：
```
数据库中 llm_history 有脏数据（如 parsed_result_json = "null" 或损坏 JSON）
  → mapRow 调用 JSON.parse("null") 返回 null
  → as ParsedLlmResult (null 强转非空类型) → 实际变量是 null
  → LlmHistoryCard 模板访问 entry.parsedResult.adhocText → NPE
  → 渲染管线抛 NPE，触发 "Possible Unhandled Promise Rejection"
  → 同时上层 try-catch 捕获 refreshLlmHistory 失败 → console.warn("失败: {}")
```

**修复模式**：**`mapRows` 中每行单独 try-catch**：

```ts
function mapRows(rows: Map<string, any>[]): LlmHistoryEntry[] {
  const result: LlmHistoryEntry[] = []
  for (let i = 0; i < rows.length; i++) {
    const r = rows[i]
    if (r == null) continue
    try {
      result.push(mapRow(r))
    } catch (e) {
      console.warn('[DaoName] mapRow skip dirty row, err: ' + JSON.stringify(e))
    }
  }
  return result
}
```

**`mapRow` 内部 JSON.parse 也需要 null 守卫**：
```ts
// ❌ 危险：JSON.parse("null") 返回 null，强转非空类型后变量是 null
parsed = JSON.parse(parsedJson) as ParsedLlmResult

// ✅ 安全：null 检查后才赋值
let parsed: ParsedLlmResult = {}
if (parsedJson != null && parsedJson.length > 0) {
  try {
    const p = JSON.parse(parsedJson)
    if (p != null) parsed = p as ParsedLlmResult
  } catch (_) { parsed = {} }
}
```

**修复后的双层防护**：
1. `mapRow` 内部对每个 JSON.parse 加 `if (p != null)` 守卫（防止特殊字符串 `"null"` 攻击）
2. `mapRows` 循环中 try-catch（防止单行意外崩溃）

**模板中对象 prop 字段访问也要 null 守卫**：

```ts
// ❌ 危险：s.one_liner 可能是 null（NPE）
return s != null && s.one_liner.length > 0 ? s.one_liner : 'fallback'

// ✅ 安全：先取出 s.one_liner 到局部变量，再 null 检查
if (s == null) return 'fallback'
const v = s.one_liner
return v != null && v.length > 0 ? v : 'fallback'
```

**为什么 `s != null && s.one_liner.length > 0` 不够安全**：
- UTS 编译器对**通过计算属性链式访问**的字段类型推断可能不够精确
- 即使 `s != null` 守卫了，访问 `s.one_liner` 时 UTS 仍可能推断为非空 string
- 实际运行中 `one_liner` 可能是 null（LLM 输出字段缺失或 JSON `null`）
- 显式赋值到局部变量 `const v = s.one_liner` 让 UTS 推断为 `string | null`，强制 null 检查

**自查**：
```powershell
# 找 DAO 中无 try-catch 的 JSON.parse
Get-ChildItem "database" -Recurse -Filter "*.uts" |
  Select-String -Pattern "JSON\.parse" |
  Where-Object { $_.Line -notmatch "try|as" }

# 找模板/computed 中链式字段访问
Get-ChildItem "pages" -Recurse -Filter "*.uvue" |
  Select-String -Pattern "s\.\w+\.length" |
  Where-Object { $_.Line -notmatch "s\s*!=\s*null" }
```

**关联规则**：
- §37 Map.get() null 安全 — 同一类"边界处必须显式守卫"
- §38 ref<T | null> 重要 ref 必须有非空初始值 — 上下层都需要保护
- §39 异步回调必须 try-catch — DAO 函数虽然非异步，但被异步调用时单行崩溃会传播
- §43 LLM JSON 三级容错解析 — `JSON.parse` 边界检查是 LLM 解析的子集

---

### 55. 实战结论：LLM 推理模型 (M2.7) 即使 `reasoning_effort: "low"` 仍倾向把 JSON 当字符串输出 → **改用纯文本方案更稳**

**【最高优先级】** 实战中反复确认：

| 配置 | LLM 实际 `content` 输出 | 结果 |
|------|------------------------|------|
| prompt 要求"裸 JSON 对象" | `"content":"{\"title\":\"...\"}"`（**字符串内嵌 JSON**）| `JSON.parse` 第一次拿到 `string`，第二次才能拿到 object |
| prompt 要求"纯文本/Markdown" | 直接是自然语言文本 | 简单 `replace` 一下 `\n` 即可使用 |
| `temperature: 0.0` | 仍可能字符串内嵌 | 无效 |
| `reasoning_effort: "low"` | 仍可能字符串内嵌 | 无效 |

**实测现象**（来自项目日志）：
```json
{
  "finish_reason": "stop",
  "content": "{\"title\":\"本周守护报告\",\"highlight\":\"...\"}"
}
```
LLM 即使被 prompt 明确告知"不要把 JSON 包在字符串里"，仍然倾向于把 JSON 文本转义为字符串输出。这是 M2.7 推理模型的固有行为。

**修复方案（已采用）**：

1. **长文本（周报）改用纯文本/Markdown**：
   - prompt 改为"回复必须是纯中文文本（不要 JSON、不要 Markdown 标题/列表）"
   - `WeeklyReport` 类型简化为 `{ bodyMarkdown: string, stats: WeeklyReportStats }`
   - `callWeekly` 直接返回 raw 字符串，调用方 `cleanMarkdown` 简单处理（去 ```/##/****等标记，规范化换行）
   - 模板用 `<text class="report-body">{{ wkBody }}</text>` + `white-space: pre-wrap` 直接显示
   - 优点：彻底解决解析难题，LLM 100% 稳定输出，调用方代码极简

2. **短文本（每日小结/pre/post 评估）仍用 JSON**：
   - 因为 LLM 在短 prompt 下 JSON 行为更稳定
   - 用 §54 增强的 `parseJsonThreeLevels`（含二次 parse）兜底

**`cleanMarkdown` 标准实现**：
```ts
function cleanMarkdown(raw: string): string {
  if (raw == null) return ''
  let text = raw
  text = text.replace(/^```[\s\S]*?\n/, '')     // 去掉开头代码块标记
  text = text.replace(/```$/, '')                // 去掉结尾代码块标记
  text = text.replace(/^#+\s*/gm, '')            // 去掉标题 # 
  text = text.replace(/\*\*([^*]+)\*\*/g, '$1')   // 去掉加粗 **
  text = text.replace(/\*([^*]+)\*/g, '$1')       // 去掉斜体 *
  text = text.replace(/`([^`]+)`/g, '$1')         // 去掉行内代码 `
  text = text.replace(/[ \t]+\n/g, '\n')          // 去掉行尾空白
  text = text.replace(/\n{3,}/g, '\n\n')          // 合并多余空行
  return text.trim()
}
```

**决策矩阵**（什么场景用什么格式）：
| 输出长度 | 推荐格式 | 理由 |
|---------|---------|------|
| 1-3 句话（如 adhocText） | 单字段 JSON | 简单，LLM 100% 稳定 |
| 1-2 段（如 summary） | JSON or 纯文本 | 都行，按团队习惯 |
| 3+ 段（如周报） | **纯文本** | LLM 长 prompt 容易内嵌字符串 |
| 包含结构化建议（如 suggestions 列表） | JSON | 列表用 JSON 解析比 split 简单 |

**关联规则**：
- §52 MiniMax M2.7 reasoning_effort — 同模型的另一类问题（token 消耗）
- §43 LLM JSON 三级容错解析 — 短文本场景保留
- §54 DAO mapRow 容错 — 解析失败的下游容错

---

### 56. 实测确认：M2.7 + `reasoning_effort: "low"` + `max_completion_tokens: 1500` 是日/周报稳定配置

**【最高优先级】** 3 次实测后的稳态配置：

| Run | finish | completion | reasoning | contentLen | reasoning 占比 |
|-----|--------|------------|-----------|-----------|--------------|
| 1 | stop | 276 | 73 | 412 字符 | **26%** |
| 2 | stop | 246 | 91 | 314 字符 | **37%** |
| 3 | stop | 281 | 110 | 350 字符 | **39%** |

**关键发现**：
- `reasoning_effort: "low"` 实际把 reasoning 占比从 77% 降到 26-39%
- `max_completion_tokens: 1500`（周报 2000）足够容纳 reasoning + content
- `finish_reason: stop`（非 `length`）稳定
- **不需要**换非推理模型（M2.7 + low 推理的输出质量仍优于 abab6.5s）

**之前项目的失败案例**（max_completion_tokens=500-600，prompt 上下文长）：
- 实际 reasoning 仍占 358/466 = 77%
- `finish_reason: "length"` 频繁
- `content` 为空或被截断

**教训**：
- **`max_completion_tokens` 至少 1500**（周报 2000），给 reasoning 留 buffer
- **`reasoning_effort: "low"` 必须保留**（不加时 reasoning 接近 100%）
- **实测数据比理论配置更可靠**——M2.7 推理深度与 prompt 长度相关
- **`uni.request` timeout 至少 30000ms**（之前 15000ms 频繁超时）
  - M2.7 推理 + 输出 1500 token 实际网络耗时 8-15s
  - 实测日志：多次 `SocketTimeoutException: Read timed out` + `SocketException: Socket closed`
  - 15s 太短，30s 是合理上限

**最终配置**（已采用）：
```ts
const reqBody = {
  model: 'MiniMax-M2.7',
  messages: [...],
  temperature: 0.7,
  max_completion_tokens: 1500,   // daily / evaluate
  // max_completion_tokens: 2000, // weekly
  reasoning_effort: 'low'         // 关键
}

// uni.request
uni.request({
  url: ...,
  timeout: 30000,                 // 30s
  ...
})
```

**为什么之前"调高 max_completion_tokens"没解决 parsing 问题**：
- parsing 问题（"字符串内嵌 JSON"）是 **prompt 风格**问题，不是 token 数问题
- M2.7 即使 token 充足也倾向把 JSON 当字符串输出
- 解决 parsing 只能靠 **改 prompt 为纯文本**（§55）+ **简化类型**（`{ bodyText: string }`）
- 高 token 只解决"content 被截断"问题

**关联规则**：
- §52 reasoning_effort: "low" — 同条规则的 token 优化版本
- §55 改用纯文本方案 — 解决 parsing 问题
- §39 异步回调必须 try-catch — uni.request fail 回调必须 try-catch

