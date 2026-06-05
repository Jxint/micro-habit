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
