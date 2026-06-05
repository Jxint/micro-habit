# AGENTS.md — 微习惯健康伴侣 (micro-habit)

uni-app x 项目，目标平台 **APP-ANDROID**，Vue 3 + UTS。

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

---

## 架构分层

**页面 → Store/Service → DAO → DatabaseManager (内存Map结构 + uni Storage持久化)**

- `TriggerEngine` 状态机驱动触发提醒 → `Recommendation` 推荐动作 → `RuleEngine` 频率控制
- `ScoreCalculator` 从动作日志计算三项评分(护眼/体态/活力)
- 数据库通过 `uni.getStorageSync/setStorageSync` 序列化 `RowEntry[]` 到 JSON 实现持久化

---

## 关键构建/运行

| 命令 | 说明 |
|------|------|
| HBuilderX `运行 → 运行到手机或模拟器` | 运行到 Android |
| `hbuilderx_cli_path` | `D:\Program Files (x86)\HBuilderX\cli.exe` |
| `node_exec_path` | `D:\Program Files (x86)\HBuilderX\plugins\node\node.exe` |

**无 lint / 无测试 / 无 package.json**。语法检查通过 HBuilderX 内置编译器完成。

---

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

---

## 命名约定

- **文件**：PascalCase.uts（类型定义），camelCase.uts（工具/服务）  
- **DAO**：`get*By*(...)`、`insert*`、`update*`、`count*`、`cleanOldData()`
- **组件**：PascalCase.uvue，props 用 `withDefaults(defineProps<{...}>(), {})`
- **Model type**：`type Xxx = { field: Type; ... }`
- **数据库列名**：snake_case；返回 Map 键名使用 snake_case 原始列名

---

## 错误处理

- DAO 函数吞异常为静默错误（返回默认值或空数组）
- DatabaseManager 异常通过 `console.error` 输出
- 关键初始化在 `App.uvue` 的 `onLaunch` 中 try-catch

---

## UI 组件通信

- Props down / Emits up：`defineEmits<{(e:'eventName', payload:Type):void}>()`
- 全局状态通过 `stores/appStore.uts` 的 `ref` 暴露，页面 `import { useAppStore }` 调用 `refreshHomeData()`

> ⚠️ 每次写完代码**必须**根据下方索引自检。详情见 `agents_rules/S{N}.md`。

## 关键路径

| 资源 | 路径 |
|------|------|
| HBuilderX CLI | `D:\Program Files (x86)\HBuilderX\cli.exe` |
| Node | `D:\Program Files (x86)\HBuilderX\plugins\node\node.exe` |
| 项目元信息/结构/架构/命名/错误处理 | `agents_rules/S0_overview.md` |
| 规则详情（§1 ~ §54）+ 完整代码/错误码案例 | `agents_rules/S{N}.md` |
| UTS_rules/ | 已合并 → `agents_rules/S{N}.md`（保留作历史备份，详见 `UTS_rules/README.md`） |

**无 lint / 无测试 / 无 package.json**。语法检查 = HBuilderX 内置编译器。

---

## 规则索引（按出错频率排，必看标 ★）

### 编译/类型核心

| § | 关键错误标识 | 修复模式 | 详情 |
|---|------------|---------|------|
| ★1 | `any` 上 `.`/`[]` 访问 → 编译失败 | 用 `Map<string,any>` + `.get(k)` / `as NamedType` | [S1](agents_rules/S1.md) |
| ★50 | 函数参数 `any` 上 `.field` → error18 | 形参改具体 `type` | [S50](agents_rules/S50.md) |
| ★19 | `interface` 接收字面量 → UTS110111163 | 机械替换 `type X = { ... }` | [S19](agents_rules/S19.md) |
| ★46 | 函数参数内联 `{ a: 1 }` → UTS110111101 | 抽到独立 `type` 定义 | [S46](agents_rules/S46.md) |
| ★18 | `return { ... }` 推断为 `UTSJSONObject` | `const x: T = {...}; return x` | [S18](agents_rules/S18.md) |
| ★12 | 跨文件类型未 `export` | `export type Xxx = { ... }` | [S12](agents_rules/S12.md) |
| 11 | `ref<any[]>` 模板 `item.name` 找不到 | 改具体类型 `ref<NamedType[]>` | [S11](agents_rules/S11.md) |
| 7 | `getStore(): any` 包装函数致 any 传播 | 直接用 `const store = useAppStore()` | [S7](agents_rules/S7.md) |

### 语法/写法硬伤

| § | 关键错误标识 | 修复模式 | 详情 |
|---|------------|---------|------|
| ★3 | 模板字面量 `` `${x}` `` → 编译失败 | `'str' + x + 'str'` | [S3](agents_rules/S3.md) |
| ★4 | 数组展开 `[...a, ...b]` → 编译失败 | `a.concat(b)` | [S4](agents_rules/S4.md) |
| ★5 | `JSON.parse(s).field` 编译失败 | `JSON.parse(s) as NamedType` | [S5](agents_rules/S5.md) |
| 51 | 对象字面量函数简写 `fn,` → 推断 Unit | 改 `fn(): void { ... }` 显式方法 | [S51](agents_rules/S51.md) |
| 9 | `<script setup>` 函数不提升 | 被调函数必须在调用者**之前**定义 | [S9](agents_rules/S9.md) |
| 10 | 复制代码漏 const/import | 保存前 grep 4 项 checklist | [S10](agents_rules/S10.md) |
| 47 | services 本地空函数覆盖 plugin import | 改别名 `import { x as pluginX }` | [S47](agents_rules/S47.md) |
| 54 | 模块级函数访问类 `private` 字段 | 改 `public` 或在类内加 getter | [S54](agents_rules/S54.md) |
| 45 | `components/ → models/` 误用 `../../` | 改 `../`（少一级） | [S45](agents_rules/S45.md) |

### 空值/smart cast/异步守卫

| § | 关键错误标识 | 修复模式 | 详情 |
|---|------------|---------|------|
| ★37 | `row.get("k") as number` 传给 Android Int → NPE | 走 `utils/DbRowUtils` null-safe getter | [S37](agents_rules/S37.md) |
| ★40 | `getNum(row: any, ...)` → receiver type mismatch | 形参改 `Map<string,any> \| null` | [S40](agents_rules/S40.md) |
| ★17 | `if (x != null) x.method()` 编译失败 | 用 `x!!.method()` | [S17](agents_rules/S17.md) |
| 23 | 跨 `if` 块后访问模块级 `let` | 块外访问加 `!!` | [S23](agents_rules/S23.md) |
| 14A | `Map.get(k) \|\| default`（非 Boolean） | 改 `if (v != null) ... else default` | [S14](agents_rules/S14.md) |
| 14B | `v === 0` / `arr.length === 0` boxing 警告 | 改 `v < 1` / `arr.length < 1` | [S14](agents_rules/S14.md) |
| ★39 | 异步回调 NPE → "Unhandled Promise Rejection" | `setTimeout`/`Handler`/`uni.request` 回调必须 try-catch | [S39](agents_rules/S39.md) |
| 38 | `ref<T\|null>(null)` 模板首次渲染 NPE | 必填完整结构初始值 | [S38](agents_rules/S38.md) |
| 44 | 异步回调中 `getHomepageData('')` 错数据 | 显式 `today()` 缓存 | [S44](agents_rules/S44.md) |
| 42 | 跨页面/原生层通信 | `uni.$emit` / `uni.$on` + `onUnload` 反注册 | [S42](agents_rules/S42.md) |

### Android 原生 API 边界

| § | 关键错误标识 | 修复模式 | 详情 |
|---|------------|---------|------|
| ★20 | `Intent.FLAG_X` 找不到 → error18 | `import Intent from 'android.content.Intent'` | [S20](agents_rules/S20.md) |
| ★21 | `WindowManager.WINDOW_SERVICE` 找不到 | `Context.WINDOW_SERVICE` | [S21](agents_rules/S21.md) |
| ★22 | `FrameLayout(ctx: any)` 类型不匹配 | 形参改 `Context` | [S22](agents_rules/S22.md) |
| ★15 | `uni.vibrateShort({})` 缺 type 编译失败 | 必传 `{ type: 'medium' }` | [S15](agents_rules/S15.md) |
| 16 | `new X.OnXxxListener({...})` | 工厂 `X.OnXxxListener({...})`；构造 + listener → 纯函数 | [S16](agents_rules/S16.md) |
| 2 | `uniCloud.callFunction` UTS 不兼容 | 改 `uni.request` HTTP 直连 | [S2](agents_rules/S2.md) |

### 数值类型 (number/Int/Float)

| § | 关键错误标识 | 修复模式 | 详情 |
|---|------------|---------|------|
| ★24 | `Number → Int` 隐式转换不存在 | `.toInt()`/`.toFloat()`/`.toLong()` 显式 | [S24](agents_rules/S24.md) |
| ★25 | `setMax(numberMs)` 编译/运行失败 | Android 边界处加 `.toInt()` | [S25](agents_rules/S25.md) |

### UVUE 组件 / 模板 / CSS

| § | 关键错误标识 | 修复模式 | 详情 |
|---|------------|---------|------|
| ★13 | 对象 prop `props.obj.field` 运行时 ClassCastException | 拆为 `id/name/duration` 多个基础 prop | [S13](agents_rules/S13.md) |
| ★49 | 对象 prop 模板访问 → error18（编译期） | 同 §13：扁平基础 prop | [S49](agents_rules/S49.md) |
| 13 | `withDefaults({labels:[]})` 推断 Array<Any> | 改 `labels: () => []` 箭头函数 | [S13](agents_rules/S13.md) |
| 6 | UVUE 页面用 Options API → any 泄漏 | 强制 `<script setup lang="uts">` | [S6](agents_rules/S6.md) |
| 30A | `align-items: baseline` UVUE 不支持 | 改 `flex-end` | [S30](agents_rules/S30.md) |
| 30B | `linear-gradient` 渲染白屏 | 前加 `background-color` fallback | [S30](agents_rules/S30.md) |
| 34A | CSS 单位 `vh/vw/em/rem` 不支持 | 改 `px` / 数字 / `%` | [S34](agents_rules/S34.md) |
| 34B | 函数返回内联 `{...}[]` → UTS110111101 | 抽 `type SvgSegment = { ... }` | [S34](agents_rules/S34.md) |
| 34C | `<script setup>` 函数顺序错误 | 调用方在被调方**之后** | [S34](agents_rules/S34.md) |
| 35 | `watch(() => props.x, ...)` 推断为 Boolean | 改 `() : boolean => props.x` | [S35](agents_rules/S35.md) |
| 33A | 模块内函数重名 → 报位置错乱 | grep 自查 `^function` | [S33](agents_rules/S33.md) |
| 33B | 多行 lambda `() : T => {...}` 触发 parser bug | 去掉 `:T`，靠 `computed<T>` 锚定 | [S33](agents_rules/S33.md) |
| 32A | `watch(props.x, ...)` 不会解包 | 改 `() : T => props.x` | [S32](agents_rules/S32.md) |
| 32B | 弹窗标准结构 | `v-if` + `@tap.stop` + `position:fixed` | [S32](agents_rules/S32.md) |
| 36 | `<svg>`/`<path>`/`<circle>` UVUE 不识别 | 图表 100% 改用 view | [S36](agents_rules/S36.md) |
| 31 | Canvas 渲染陷阱（offsetWidth=0 / HiDPI） | 改 view + 动态 style | [S31](agents_rules/S31.md) |
| 8 | Canvas 正确用法（备用，目前项目改 view） | `uni.createCanvasContextAsync` + W3C 2D | [S8](agents_rules/S8.md) |

### extends 抽象类 / SQLite 平台

| § | 关键错误标识 | 修复模式 | 详情 |
|---|------------|---------|------|
| ★26 | `extends` 子类 override 签名不匹配父类 | 逐项精确匹配（Context! / SQLiteDatabase! / Int / String?） | [S26](agents_rules/S26.md) |
| ★27 | ContentValues + `d.changesNumber` 各种坑 | 统一 `SQLiteStatement` + `bindXxx` | [S27](agents_rules/S27.md) |
| 28 | `SQLiteOpenHelper` onCreate 在 getWritableDatabase 同步触发 | wrapper 接收 db 参数透传 | [S28](agents_rules/S28.md) |
| ★29 | DAO 传 `Map<string,any>`/对象字面量 → 编译失败或数据丢失 | 统一 `const row: SqlRow = { columns, values }` | [S29](agents_rules/S29.md) |
| 53 | LLM 调用统一入口 | 走 `CloudService.callMinimaxChat()` | [S53](agents_rules/S53.md) |

---

## 自检流程（写完代码必走）

1. 报错时按 **错误码 / error18 / 编译失败提示** 命中本索引 → 打开对应 `agents_rules/S{N}.md`
2. 跨多条规则时按 **关联规则** 链路逐条排查
3. 打包后用 `aapt2 dump xmltree` 验证 manifest（详见 `S0_overview.md` 关键构建/运行节）
4. 重命名/移动文件后跑 `S45` 自查（`components/` 不能用 `../../`）
5. 涉及 DAO/数据库时跑 `S29` 自查（禁止 `new Map<string, any>()`）

---

**编号说明**：原 §41/§43/§48/§52 已被删除或合并到相邻条目，本索引从 §1 连续到 §54，跳号不补。
