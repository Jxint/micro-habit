@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNIB805CE2
import io.dcloud.uniapp.*
import io.dcloud.uniapp.extapi.*
import io.dcloud.uniapp.framework.*
import io.dcloud.uniapp.runtime.*
import io.dcloud.uniapp.vue.*
import io.dcloud.uniapp.vue.shared.*
import io.dcloud.unicloud.*
import io.dcloud.uts.*
import io.dcloud.uts.Map
import io.dcloud.uts.Set
import io.dcloud.uts.UTSAndroid
import kotlin.properties.Delegates
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
open class GenPagesDebugLogs : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesDebugLogs) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesDebugLogs
            val _cache = __ins.renderCache
            val triggerLogs = ref(_uA<TriggerLog>())
            val actionLogs = ref(_uA<ActionLog>())
            val completedCount = ref<Number>(0)
            val skippedCount = ref<Number>(0)
            val currentTab = ref<String>("trigger")
            val KNOWN_TABLES = _uA(
                "action_logs",
                "trigger_logs",
                "user_settings",
                "daily_summaries",
                "app_usage_snapshots",
                "tts_cache",
                "heartbeat_logs",
                "llm_cache",
                "llm_history",
                "app_tags",
                "effective_rules"
            ) as UTSArray<String>
            val dbTableInfos = ref(_uA<DbTableInfo>())
            val dbRecentActions = ref(_uA<ActionLog>())
            val configList = ref(_uA<ConfigItem>())
            fun gen_pad2_fn(n: Number): String {
                return if (n < 10) {
                    "0" + n
                } else {
                    "" + n
                }
            }
            val pad2 = ::gen_pad2_fn
            fun gen_formatTime_fn(t: Number): String {
                if (t == null || t <= 0) {
                    return "-"
                }
                val d = Date(t * 1000)
                val yyyy = d.getFullYear()
                val mm = d.getMonth() + 1
                val dd = d.getDate()
                val hh = d.getHours()
                val mi = d.getMinutes()
                val ss = d.getSeconds()
                return yyyy + "-" + pad2(mm) + "-" + pad2(dd) + " " + pad2(hh) + ":" + pad2(mi) + ":" + pad2(ss)
            }
            val formatTime = ::gen_formatTime_fn
            fun gen_formatDuration_fn(ms: Number): String {
                if (ms == null || ms <= 0) {
                    return "0s"
                }
                val s = Math.floor(ms / 1000)
                if (s < 60) {
                    return s + "s"
                }
                val m = Math.floor(s / 60)
                return m + "m" + (s % 60) + "s"
            }
            val formatDuration = ::gen_formatDuration_fn
            fun gen_formatStr_fn(s: String?): String {
                if (s == null || s.length === 0) {
                    return "-"
                }
                return s
            }
            val formatStr = ::gen_formatStr_fn
            fun gen_formatTriggerType_fn(t: String): String {
                if (t === "app_duration") {
                    return "APP时长"
                }
                if (t === "manual") {
                    return "手动"
                }
                if (t === "scheduled") {
                    return "定时"
                }
                if (t === "time_period") {
                    return "时段"
                }
                if (t === "usage_threshold") {
                    return "阈值"
                }
                if (t === "app_switch") {
                    return "切换"
                }
                return t
            }
            val formatTriggerType = ::gen_formatTriggerType_fn
            fun gen_formatActionId_fn(id: String?): String {
                if (id == null || id.length === 0) {
                    return "-"
                }
                return id
            }
            val formatActionId = ::gen_formatActionId_fn
            fun gen_formatUserAction_fn(ua: UserAction?): String {
                if (ua == null) {
                    return "未响应"
                }
                if (ua === "completed") {
                    return "完成"
                }
                if (ua === "self_reported") {
                    return "自我报告"
                }
                if (ua === "skipped") {
                    return "跳过"
                }
                if (ua === "feedback") {
                    return "反馈"
                }
                if (ua === "ignored") {
                    return "忽略"
                }
                if (ua === "timeout") {
                    return "超时"
                }
                if (ua === "busy_remind_later") {
                    return "忙，稍后"
                }
                return ua
            }
            val formatUserAction = ::gen_formatUserAction_fn
            fun gen_formatResult_fn(r: ActionResult): String {
                if (r === "completed") {
                    return "完成"
                }
                if (r === "self_reported") {
                    return "已做"
                }
                if (r === "skipped") {
                    return "跳过"
                }
                if (r === "feedback") {
                    return "反馈"
                }
                return r
            }
            val formatResult = ::gen_formatResult_fn
            fun gen_loadDbOverview_fn(): Unit {
                val infos: UTSArray<DbTableInfo> = _uA()
                run {
                    var i: Number = 0
                    while(i < KNOWN_TABLES.length){
                        val t = KNOWN_TABLES[i]
                        try {
                            val row = dbManager.queryOne("SELECT COUNT(*) AS cnt FROM " + t, _uA())
                            val rawCnt = if (row != null) {
                                row.get("cnt")
                            } else {
                                null
                            }
                            val cnt: Number = if (rawCnt != null) {
                                parseFloat("" + rawCnt)
                            } else {
                                0
                            }
                            infos.push(DbTableInfo(name = t, count = cnt))
                        }
                         catch (e: Throwable) {
                            console.error("[debug] loadDbOverview count error for " + t + ": " + e, " at pages/debug/logs.uvue:253")
                            infos.push(DbTableInfo(name = t, count = 0))
                        }
                        i++
                    }
                }
                dbTableInfos.value = infos
                try {
                    val raw = dbManager.query("SELECT * FROM action_logs ORDER BY created_at DESC LIMIT 5", _uA())
                    val logs: UTSArray<ActionLog> = _uA()
                    run {
                        var i: Number = 0
                        while(i < raw.length){
                            val r = raw[i]
                            logs.push(ActionLog(id = r.get("id") as Number, action_id = r.get("action_id") as String, action_type = r.get("action_type") as String, result = r.get("result") as ActionResult, skip_reason = r.get("skip_reason") as String?, trigger_type = r.get("trigger_type") as String, trigger_level = r.get("trigger_level") as String, duration_ms = r.get("duration_ms") as Number, target_ms = r.get("target_ms") as Number, triggered_at = r.get("triggered_at") as Number, completed_at = r.get("completed_at") as Number, created_at = r.get("created_at") as Number))
                            i++
                        }
                    }
                    dbRecentActions.value = logs
                }
                 catch (_: Throwable) {
                    dbRecentActions.value = _uA()
                }
            }
            val loadDbOverview = ::gen_loadDbOverview_fn
            fun gen_loadConfig_fn(): Unit {
                try {
                    val raw = dbManager.query("SELECT key, value FROM user_settings ORDER BY key ASC", _uA())
                    val items: UTSArray<ConfigItem> = _uA()
                    run {
                        var i: Number = 0
                        while(i < raw.length){
                            val r = raw[i]
                            items.push(ConfigItem(key = r.get("key") as String, value = r.get("value") as String))
                            i++
                        }
                    }
                    configList.value = items
                }
                 catch (_: Throwable) {
                    configList.value = _uA()
                }
            }
            val loadConfig = ::gen_loadConfig_fn
            fun gen_refresh_fn(): Unit {
                try {
                    triggerLogs.value = getTodayTriggerLogs()
                    console.log("[debug] triggerLogs count=" + triggerLogs.value.length, " at pages/debug/logs.uvue:302")
                }
                 catch (e: Throwable) {
                    console.error("[debug] getTodayTriggerLogs error: " + e, " at pages/debug/logs.uvue:304")
                    triggerLogs.value = _uA()
                }
                try {
                    actionLogs.value = getTodayLogs()
                    console.log("[debug] actionLogs count=" + actionLogs.value.length, " at pages/debug/logs.uvue:309")
                }
                 catch (e: Throwable) {
                    console.error("[debug] getTodayLogs error: " + e, " at pages/debug/logs.uvue:311")
                    actionLogs.value = _uA()
                }
                try {
                    completedCount.value = countCompletedToday()
                }
                 catch (e: Throwable) {
                    console.error("[debug] countCompletedToday error: " + e, " at pages/debug/logs.uvue:314")
                    completedCount.value = 0
                }
                try {
                    skippedCount.value = countSkippedToday()
                }
                 catch (e: Throwable) {
                    console.error("[debug] countSkippedToday error: " + e, " at pages/debug/logs.uvue:315")
                    skippedCount.value = 0
                }
                loadDbOverview()
                loadConfig()
            }
            val refresh = ::gen_refresh_fn
            fun gen_switchTab_fn(tab: String): Unit {
                currentTab.value = tab
            }
            val switchTab = ::gen_switchTab_fn
            fun gen_goBack_fn(): Unit {
                uni_navigateBack(NavigateBackOptions())
            }
            val goBack = ::gen_goBack_fn
            onPageShow(fun(){
                console.log("[debug] onPageShow firing refresh", " at pages/debug/logs.uvue:329")
                refresh()
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "page-bg"), _uA(
                        _cE("image", _uM("class" to "page-bg-img", "src" to "/static/images/dream-gradient-bg.png", "mode" to "aspectFill"))
                    )),
                    _cE("view", _uM("class" to "header"), _uA(
                        _cE("view", _uM("class" to "header-left", "onClick" to goBack), _uA(
                            _cE("text", _uM("class" to "header-back"), "‹")
                        )),
                        _cE("text", _uM("class" to "header-title"), "调试日志"),
                        _cE("view", _uM("class" to "header-right", "onClick" to refresh), _uA(
                            _cE("text", _uM("class" to "header-refresh"), "↻")
                        ))
                    )),
                    _cE("view", _uM("class" to "stats-row"), _uA(
                        _cE("view", _uM("class" to "stat-card"), _uA(
                            _cE("text", _uM("class" to "stat-num"), _tD(unref(triggerLogs).length), 1),
                            _cE("text", _uM("class" to "stat-label"), "今日触发")
                        )),
                        _cE("view", _uM("class" to "stat-card"), _uA(
                            _cE("text", _uM("class" to "stat-num"), _tD(unref(actionLogs).length), 1),
                            _cE("text", _uM("class" to "stat-label"), "今日动作")
                        )),
                        _cE("view", _uM("class" to "stat-card"), _uA(
                            _cE("text", _uM("class" to "stat-num"), _tD(unref(completedCount)), 1),
                            _cE("text", _uM("class" to "stat-label"), "完成数")
                        )),
                        _cE("view", _uM("class" to "stat-card"), _uA(
                            _cE("text", _uM("class" to "stat-num"), _tD(unref(skippedCount)), 1),
                            _cE("text", _uM("class" to "stat-label"), "跳过数")
                        ))
                    )),
                    _cE("view", _uM("class" to "tab-bar"), _uA(
                        _cE("view", _uM("class" to _nC(_uA(
                            "tab",
                            _uM("active" to (unref(currentTab) === "trigger"))
                        )), "onClick" to fun(){
                            switchTab("trigger")
                        }
                        ), _uA(
                            _cE("text", _uM("class" to "tab-text"), "触发日志")
                        ), 10, _uA(
                            "onClick"
                        )),
                        _cE("view", _uM("class" to _nC(_uA(
                            "tab",
                            _uM("active" to (unref(currentTab) === "action"))
                        )), "onClick" to fun(){
                            switchTab("action")
                        }
                        ), _uA(
                            _cE("text", _uM("class" to "tab-text"), "动作日志")
                        ), 10, _uA(
                            "onClick"
                        )),
                        _cE("view", _uM("class" to _nC(_uA(
                            "tab",
                            _uM("active" to (unref(currentTab) === "db"))
                        )), "onClick" to fun(){
                            switchTab("db")
                        }
                        ), _uA(
                            _cE("text", _uM("class" to "tab-text"), "数据库")
                        ), 10, _uA(
                            "onClick"
                        )),
                        _cE("view", _uM("class" to _nC(_uA(
                            "tab",
                            _uM("active" to (unref(currentTab) === "config"))
                        )), "onClick" to fun(){
                            switchTab("config")
                        }
                        ), _uA(
                            _cE("text", _uM("class" to "tab-text"), "配置")
                        ), 10, _uA(
                            "onClick"
                        ))
                    )),
                    _cE("scroll-view", _uM("class" to "log-scroll", "scroll-y" to "true"), _uA(
                        if (unref(currentTab) === "trigger") {
                            _cE("view", _uM("key" to 0), _uA(
                                if (unref(triggerLogs).length === 0) {
                                    _cE("view", _uM("key" to 0, "class" to "empty"), _uA(
                                        _cE("text", _uM("class" to "empty-text"), "今日暂无触发记录"),
                                        _cE("text", _uM("class" to "empty-hint"), "去使用某个 App 1 分钟以上会触发提醒")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                },
                                _cE(Fragment, null, RenderHelpers.renderList(unref(triggerLogs), fun(log, idx, __index, _cached): Any {
                                    return _cE("view", _uM("key" to log.id, "class" to "log-item"), _uA(
                                        _cE("view", _uM("class" to "log-row1"), _uA(
                                            _cE("text", _uM("class" to "log-time"), _tD(formatTime(log.created_at)), 1),
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "log-tag",
                                                "tag-" + log.trigger_type
                                            ))), _tD(formatTriggerType(log.trigger_type)), 3),
                                            _cE("text", _uM("class" to "log-level"), _tD(log.trigger_level), 1)
                                        )),
                                        _cE("view", _uM("class" to "log-row2"), _uA(
                                            _cE("text", _uM("class" to "log-field-label"), "动作："),
                                            _cE("text", _uM("class" to "log-field-val"), _tD(formatActionId(log.action_id)), 1)
                                        )),
                                        _cE("view", _uM("class" to "log-row2"), _uA(
                                            _cE("text", _uM("class" to "log-field-label"), "App："),
                                            _cE("text", _uM("class" to "log-field-val"), _tD(formatStr(log.app_package)), 1),
                                            _cE("text", _uM("class" to "log-field-label", "style" to _nS(_uM("margin-left" to "12px"))), "连续：", 4),
                                            _cE("text", _uM("class" to "log-field-val"), _tD(log.continuous_minutes) + "分", 1)
                                        )),
                                        _cE("view", _uM("class" to "log-row3"), _uA(
                                            _cE("text", _uM("class" to "log-field-label"), "用户操作："),
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "log-user-action",
                                                "ua-" + log.user_action
                                            ))), _tD(formatUserAction(log.user_action)), 3)
                                        ))
                                    ))
                                }), 128)
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (unref(currentTab) === "action") {
                            _cE("view", _uM("key" to 1), _uA(
                                if (unref(actionLogs).length === 0) {
                                    _cE("view", _uM("key" to 0, "class" to "empty"), _uA(
                                        _cE("text", _uM("class" to "empty-text"), "今日暂无动作记录"),
                                        _cE("text", _uM("class" to "empty-hint"), "点击首页\"全部微动作库\"开始第一次动作")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                },
                                _cE(Fragment, null, RenderHelpers.renderList(unref(actionLogs), fun(log, idx, __index, _cached): Any {
                                    return _cE("view", _uM("key" to log.id, "class" to "log-item"), _uA(
                                        _cE("view", _uM("class" to "log-row1"), _uA(
                                            _cE("text", _uM("class" to "log-time"), _tD(formatTime(log.created_at)), 1),
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "log-tag",
                                                "tag-" + log.trigger_type
                                            ))), _tD(formatTriggerType(log.trigger_type)), 3),
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "log-result",
                                                "res-" + log.result
                                            ))), _tD(formatResult(log.result)), 3)
                                        )),
                                        _cE("view", _uM("class" to "log-row2"), _uA(
                                            _cE("text", _uM("class" to "log-field-label"), "动作ID："),
                                            _cE("text", _uM("class" to "log-field-val"), _tD(log.action_id), 1),
                                            _cE("text", _uM("class" to "log-field-label", "style" to _nS(_uM("margin-left" to "12px"))), "分类：", 4),
                                            _cE("text", _uM("class" to "log-field-val"), _tD(log.action_type), 1)
                                        )),
                                        _cE("view", _uM("class" to "log-row2"), _uA(
                                            _cE("text", _uM("class" to "log-field-label"), "用时："),
                                            _cE("text", _uM("class" to "log-field-val"), _tD(formatDuration(log.duration_ms)) + " / " + _tD(formatDuration(log.target_ms)), 1),
                                            _cE("text", _uM("class" to "log-field-label", "style" to _nS(_uM("margin-left" to "12px"))), "等级：", 4),
                                            _cE("text", _uM("class" to "log-field-val"), _tD(log.trigger_level), 1)
                                        )),
                                        if (isTrue(log.skip_reason != null && log.skip_reason!!.length > 0)) {
                                            _cE("view", _uM("key" to 0, "class" to "log-row3"), _uA(
                                                _cE("text", _uM("class" to "log-field-label"), "跳过原因："),
                                                _cE("text", _uM("class" to "log-skip-reason"), _tD(log.skip_reason), 1)
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ))
                                }), 128)
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (unref(currentTab) === "db") {
                            _cE("view", _uM("key" to 2), _uA(
                                _cE("view", _uM("class" to "section-header"), _uA(
                                    _cE("text", _uM("class" to "section-header-text"), "数据库表概览")
                                )),
                                _cE(Fragment, null, RenderHelpers.renderList(unref(dbTableInfos), fun(info, idx, __index, _cached): Any {
                                    return _cE("view", _uM("key" to idx, "class" to "db-table-card"), _uA(
                                        _cE("view", _uM("class" to "db-table-row"), _uA(
                                            _cE("text", _uM("class" to "db-table-name"), _tD(info.name), 1),
                                            _cE("text", _uM("class" to "db-table-count"), _tD(info.count) + " 行", 1)
                                        ))
                                    ))
                                }), 128),
                                _cE("view", _uM("class" to "section-header"), _uA(
                                    _cE("text", _uM("class" to "section-header-text"), "最新动作日志（最近5条）")
                                )),
                                _cE(Fragment, null, RenderHelpers.renderList(unref(dbRecentActions), fun(log, idx, __index, _cached): Any {
                                    return _cE("view", _uM("key" to ("ra-" + idx), "class" to "log-item"), _uA(
                                        _cE("view", _uM("class" to "log-row1"), _uA(
                                            _cE("text", _uM("class" to "log-time"), _tD(formatTime(log.created_at)), 1),
                                            _cE("text", _uM("class" to "log-tag tag-manual"), _tD(log.action_id), 1),
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "log-result",
                                                "res-" + log.result
                                            ))), _tD(formatResult(log.result)), 3)
                                        )),
                                        _cE("view", _uM("class" to "log-row2"), _uA(
                                            _cE("text", _uM("class" to "log-field-val"), _tD(log.action_type) + " | " + _tD(formatDuration(log.duration_ms)) + " | " + _tD(log.trigger_type), 1)
                                        ))
                                    ))
                                }), 128)
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (unref(currentTab) === "config") {
                            _cE("view", _uM("key" to 3), _uA(
                                _cE("view", _uM("class" to "section-header"), _uA(
                                    _cE("text", _uM("class" to "section-header-text"), "用户设置")
                                )),
                                if (unref(configList).length === 0) {
                                    _cE("view", _uM("key" to 0, "class" to "empty"), _uA(
                                        _cE("text", _uM("class" to "empty-text"), "暂无配置数据")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                },
                                _cE(Fragment, null, RenderHelpers.renderList(unref(configList), fun(item, idx, __index, _cached): Any {
                                    return _cE("view", _uM("key" to idx, "class" to "config-item"), _uA(
                                        _cE("view", _uM("class" to "config-row"), _uA(
                                            _cE("text", _uM("class" to "config-key"), _tD(item.key), 1),
                                            _cE("text", _uM("class" to "config-val"), _tD(item.value), 1)
                                        ))
                                    ))
                                }), 128)
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                    ))
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#D8C8FF", "position" to "relative", "overflow" to "hidden")), "page-bg" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "width" to "100%", "height" to "100%", "zIndex" to 0)), "page-bg-img" to _pS(_uM("width" to "100%", "height" to "100%")), "header" to _pS(_uM("position" to "relative", "zIndex" to 1, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 50, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "backgroundColor" to "#FFFFFF", "borderBottomWidth" to 1, "borderBottomColor" to "#ECF0F1", "borderBottomStyle" to "solid")), "stats-row" to _pS(_uM("position" to "relative", "zIndex" to 1, "flexDirection" to "row", "paddingTop" to 12, "paddingRight" to 8, "paddingBottom" to 12, "paddingLeft" to 8, "backgroundColor" to "#FFFFFF", "marginBottom" to 8)), "tab-bar" to _pS(_uM("position" to "relative", "zIndex" to 1, "flexDirection" to "row", "backgroundColor" to "#FFFFFF", "borderBottomWidth" to 1, "borderBottomColor" to "#ECF0F1", "borderBottomStyle" to "solid")), "log-scroll" to _pS(_uM("position" to "relative", "zIndex" to 1, "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 8, "paddingRight" to 12, "paddingBottom" to 8, "paddingLeft" to 12)), "log-list" to _pS(_uM("position" to "relative", "zIndex" to 1)), "header-left" to _pS(_uM("width" to 32, "height" to 32, "alignItems" to "center", "justifyContent" to "center")), "header-back" to _pS(_uM("fontSize" to 28, "color" to "#2C3E50")), "header-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50")), "header-right" to _pS(_uM("width" to 32, "height" to 32, "alignItems" to "center", "justifyContent" to "center")), "header-refresh" to _pS(_uM("fontSize" to 22, "color" to "#3498DB")), "stat-card" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "alignItems" to "center", "paddingTop" to 4, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0)), "stat-num" to _pS(_uM("fontSize" to 20, "fontWeight" to "bold", "color" to "#3498DB", "marginBottom" to 2)), "stat-label" to _pS(_uM("fontSize" to 11, "color" to "#7F8C8D")), "tab" to _uM("" to _uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 12, "paddingRight" to 0, "paddingBottom" to 12, "paddingLeft" to 0, "alignItems" to "center", "borderBottomWidth" to 3, "borderBottomColor" to "rgba(0,0,0,0)", "borderBottomStyle" to "solid"), ".active" to _uM("borderBottomColor" to "#3498DB")), "tab-text" to _uM("" to _uM("fontSize" to 14, "color" to "#7F8C8D"), ".tab.active " to _uM("color" to "#3498DB", "fontWeight" to "bold")), "empty" to _pS(_uM("alignItems" to "center", "paddingTop" to 40, "paddingRight" to 20, "paddingBottom" to 40, "paddingLeft" to 20)), "empty-text" to _pS(_uM("fontSize" to 14, "color" to "#95A5A6", "marginBottom" to 6)), "empty-hint" to _pS(_uM("fontSize" to 12, "color" to "#BDC3C7")), "log-item" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "marginBottom" to 8)), "log-row1" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 4)), "log-time" to _pS(_uM("fontSize" to 12, "color" to "#7F8C8D", "fontFamily" to "monospace", "marginRight" to 8)), "log-tag" to _pS(_uM("fontSize" to 10, "paddingTop" to 2, "paddingRight" to 6, "paddingBottom" to 2, "paddingLeft" to 6, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "backgroundColor" to "#ECF0F1", "color" to "#34495E", "marginRight" to 6)), "tag-app_duration" to _pS(_uM("backgroundColor" to "#FCF3CF", "color" to "#B7950B")), "tag-manual" to _pS(_uM("backgroundColor" to "#D5F5E3", "color" to "#239B56")), "tag-scheduled" to _pS(_uM("backgroundColor" to "#D6EAF8", "color" to "#2874A6")), "log-level" to _pS(_uM("fontSize" to 10, "color" to "#95A5A6", "marginLeft" to "auto")), "log-result" to _pS(_uM("fontSize" to 11, "fontWeight" to "bold", "marginLeft" to "auto")), "res-completed" to _pS(_uM("color" to "#27AE60")), "res-self_reported" to _pS(_uM("color" to "#16A085")), "res-skipped" to _pS(_uM("color" to "#E74C3C")), "res-feedback" to _pS(_uM("color" to "#8E44AD")), "log-row2" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 2)), "log-row3" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 4, "backgroundColor" to "#FDF2E9", "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "paddingTop" to 4, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8)), "log-field-label" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6")), "log-field-val" to _pS(_uM("fontSize" to 12, "color" to "#2C3E50", "fontFamily" to "monospace")), "log-user-action" to _pS(_uM("fontSize" to 12, "fontWeight" to "bold")), "ua-completed" to _pS(_uM("color" to "#27AE60")), "ua-self_reported" to _pS(_uM("color" to "#16A085")), "ua-skipped" to _pS(_uM("color" to "#E74C3C")), "ua-busy_remind_later" to _pS(_uM("color" to "#E67E22")), "ua-ignored" to _pS(_uM("color" to "#95A5A6")), "ua-timeout" to _pS(_uM("color" to "#7F8C8D")), "log-skip-reason" to _pS(_uM("fontSize" to 12, "color" to "#E67E22")), "section-header" to _pS(_uM("paddingTop" to 12, "paddingRight" to 4, "paddingBottom" to 4, "paddingLeft" to 4)), "section-header-text" to _pS(_uM("fontSize" to 13, "fontWeight" to "bold", "color" to "#95A5A6")), "db-table-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "marginBottom" to 6)), "db-table-row" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "db-table-name" to _pS(_uM("fontSize" to 13, "color" to "#2C3E50", "fontFamily" to "monospace")), "db-table-count" to _pS(_uM("fontSize" to 13, "fontWeight" to "bold", "color" to "#3498DB")), "config-item" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "marginBottom" to 4)), "config-row" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "config-key" to _pS(_uM("fontSize" to 12, "color" to "#2C3E50", "fontFamily" to "monospace", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "config-val" to _pS(_uM("fontSize" to 12, "color" to "#7F8C8D", "fontFamily" to "monospace", "textAlign" to "right", "maxWidth" to 160)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
