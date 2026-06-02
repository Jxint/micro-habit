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
