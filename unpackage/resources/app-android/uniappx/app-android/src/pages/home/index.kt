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
import io.dcloud.uniapp.extapi.`$on` as uni__on
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesHomeIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesHomeIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesHomeIndex
            val _cache = __ins.renderCache
            val store = useAppStore()
            val eyeScore = computed<Number>(fun(): Number {
                return store.eyeScore.value
            }
            )
            val eyeStatus = computed<String>(fun(): String {
                return store.eyeStatus.value
            }
            )
            val postureScore = computed<Number>(fun(): Number {
                return store.postureScore.value
            }
            )
            val postureStatus = computed<String>(fun(): String {
                return store.postureStatus.value
            }
            )
            val vitalityScore = computed<Number>(fun(): Number {
                return store.vitalityScore.value
            }
            )
            val vitalityStatus = computed<String>(fun(): String {
                return store.vitalityStatus.value
            }
            )
            val guardCount = computed<Number>(fun(): Number {
                return store.guardCount.value
            }
            )
            val penetration = computed<Number>(fun(): Number {
                return store.penetration.value
            }
            )
            val penetrationStr = computed<String>(fun(): String {
                return penetration.value.toFixed(1)
            }
            )
            val recommendedActions = computed<UTSArray<MicroAction>>(fun(): UTSArray<MicroAction> {
                return store.recommendedActions.value
            }
            )
            val allActions = computed<UTSArray<MicroAction>>(fun(): UTSArray<MicroAction> {
                return getEnabledActions()
            }
            )
            val dailySummary = computed<DailyOutput?>(fun(): DailyOutput? {
                return store.dailySummary.value
            }
            )
            val dailySummaryLoading = computed<Boolean>(fun(): Boolean {
                return store.dailySummaryLoading.value
            }
            )
            fun gen_getSummaryOneLiner_fn(): String {
                val s = dailySummary.value
                return if (s != null && s.one_liner.length > 0) {
                    s.one_liner
                } else {
                    "今日小结待生成"
                }
            }
            val getSummaryOneLiner = ::gen_getSummaryOneLiner_fn
            fun gen_getSummaryBody_fn(): String {
                val s = dailySummary.value
                return if (s != null) {
                    s.summary
                } else {
                    ""
                }
            }
            val getSummaryBody = ::gen_getSummaryBody_fn
            fun gen_getSummaryGoal_fn(): String {
                val s = dailySummary.value
                return if (s != null) {
                    s.tomorrow_goal
                } else {
                    ""
                }
            }
            val getSummaryGoal = ::gen_getSummaryGoal_fn
            fun gen_getSummaryEncourage_fn(): String {
                val s = dailySummary.value
                return if (s != null) {
                    s.encourage
                } else {
                    ""
                }
            }
            val getSummaryEncourage = ::gen_getSummaryEncourage_fn
            fun gen_getSummaryHour_fn(): Number {
                val raw = getSetting("daily_summary_time", "21:00")
                val sep = raw.indexOf(":")
                val h = parseInt(if (sep > 0) {
                    raw.substring(0, sep)
                } else {
                    raw
                }
                )
                if (h < 0 || h > 23) {
                    return 21
                }
                return h
            }
            val getSummaryHour = ::gen_getSummaryHour_fn
            fun gen_getSummaryPlaceholder_fn(): String {
                if (dailySummaryLoading.value) {
                    return "正在生成今日小结…"
                }
                val cnt = store.todayCompletedCount.value
                if (cnt < 1) {
                    return "完成一个微动作，开启今日小结"
                }
                return "已完成" + cnt + "次，" + getSummaryHour() + ":00 后自动小结"
            }
            val getSummaryPlaceholder = ::gen_getSummaryPlaceholder_fn
            val summaryOneLiner = computed<String>(getSummaryOneLiner)
            val summaryBody = computed<String>(getSummaryBody)
            val summaryGoal = computed<String>(getSummaryGoal)
            val summaryEncourage = computed<String>(getSummaryEncourage)
            val summaryHour = computed<Number>(getSummaryHour)
            val summaryPlaceholder = computed<String>(getSummaryPlaceholder)
            fun gen_getGreeting_fn(): String {
                val h = currentHour()
                if (h < 6) {
                    return "夜深了"
                }
                if (h < 9) {
                    return "早上好"
                }
                if (h < 12) {
                    return "上午好"
                }
                if (h < 14) {
                    return "中午好"
                }
                if (h < 18) {
                    return "下午好"
                }
                if (h < 22) {
                    return "晚上好"
                }
                return "夜深了"
            }
            val getGreeting = ::gen_getGreeting_fn
            fun gen_getGreetingEmoji_fn(): String {
                val h = currentHour()
                if (h < 6) {
                    return "🌙"
                }
                if (h < 9) {
                    return "☀️"
                }
                if (h < 12) {
                    return "☀️"
                }
                if (h < 14) {
                    return "🌤"
                }
                if (h < 18) {
                    return "⛅"
                }
                if (h < 22) {
                    return "🌆"
                }
                return "🌙"
            }
            val getGreetingEmoji = ::gen_getGreetingEmoji_fn
            val greeting = computed<String>(getGreeting)
            val greetingEmoji = computed<String>(getGreetingEmoji)
            val phoneMinutes = computed<Number>(fun(): Number {
                return store.phoneMinutes.value
            }
            )
            fun gen_getPhoneUsageHint_fn(): String {
                if (phoneMinutes.value < 1) {
                    return "👆 点击查看手机使用详情"
                }
                return "今日已使用 " + phoneMinutes.value + " 分钟，点击查看详情 →"
            }
            val getPhoneUsageHint = ::gen_getPhoneUsageHint_fn
            val phoneUsageHint = computed<String>(getPhoneUsageHint)
            val showPhoneDialog = ref<Boolean>(false)
            val showRuleDialog = ref<Boolean>(false)
            val pendingRule = ref<EffectiveTriggerRule?>(null)
            val pendingReasoning = ref<String>("")
            val ruleActionName = computed<String>(fun(): String {
                val r = pendingRule.value
                if (r == null) {
                    return ""
                }
                val a = getActionById(r.actionId)
                return if (a != null) {
                    a.name
                } else {
                    r.actionId
                }
            }
            )
            val ruleTimeWindowStart = computed<String>(fun(): String {
                val r = pendingRule.value
                if (r == null || r.timeWindow == null) {
                    return ""
                }
                return r.timeWindow!!.start
            }
            )
            val ruleTimeWindowEnd = computed<String>(fun(): String {
                val r = pendingRule.value
                if (r == null || r.timeWindow == null) {
                    return ""
                }
                return r.timeWindow!!.end
            }
            )
            val ruleTimeThresholdMinutes = computed<Number>(fun(): Number {
                val r = pendingRule.value
                if (r == null) {
                    return 0
                }
                return r.timeThresholdMinutes
            }
            )
            val ruleScreenCondText = computed<String>(fun(): String {
                val r = pendingRule.value
                if (r == null || r.screenConditions == null) {
                    return ""
                }
                val sc = r.screenConditions!!
                val parts: UTSArray<String> = _uA()
                if (sc.includeHome) {
                    parts.push("桌面")
                }
                if (sc.appPackages.length > 0) {
                    parts.push(sc.appPackages.join(", "))
                }
                return if (parts.length > 0) {
                    parts.join(" / ")
                } else {
                    "任意"
                }
            }
            )
            val llmHistoryEntries = computed<UTSArray<LlmHistoryEntry>>(fun(): UTSArray<LlmHistoryEntry> {
                return store.llmHistory.value
            }
            )
            fun gen_openPhoneDialog_fn(): Unit {
                showPhoneDialog.value = true
            }
            val openPhoneDialog = ::gen_openPhoneDialog_fn
            fun gen_closePhoneDialog_fn(): Unit {
                showPhoneDialog.value = false
            }
            val closePhoneDialog = ::gen_closePhoneDialog_fn
            fun gen_closeRuleDialog_fn(): Unit {
                showRuleDialog.value = false
                pendingRule.value = null
                pendingReasoning.value = ""
            }
            val closeRuleDialog = ::gen_closeRuleDialog_fn
            fun gen_acceptRule_fn(): Unit {
                showRuleDialog.value = false
                pendingRule.value = null
                pendingReasoning.value = ""
                uni_showToast(ShowToastOptions(title = "规则已接受（持久化待 Day 5）", icon = "none"))
            }
            val acceptRule = ::gen_acceptRule_fn
            fun gen_neverAskRule_fn(): Unit {
                putBool("llm_trigger_ask_each_time", false)
                closeRuleDialog()
                uni_showToast(ShowToastOptions(title = "已关闭\"每次问询\"", icon = "none"))
            }
            val neverAskRule = ::gen_neverAskRule_fn
            fun gen_getGreetingClass_fn(): String {
                val h = currentHour()
                if (h < 6) {
                    return "bg-night"
                }
                if (h < 9) {
                    return "bg-morning"
                }
                if (h < 14) {
                    return "bg-noon"
                }
                if (h < 18) {
                    return "bg-afternoon"
                }
                if (h < 22) {
                    return "bg-evening"
                }
                return "bg-night"
            }
            val getGreetingClass = ::gen_getGreetingClass_fn
            fun gen_getDateStr_fn(): String {
                val d = Date()
                val m = d.getMonth() + 1
                val day = d.getDate()
                val weekDays = _uA(
                    "日",
                    "一",
                    "二",
                    "三",
                    "四",
                    "五",
                    "六"
                ) as UTSArray<String>
                return m + "月" + day + "日 · 星期" + weekDays[d.getDay()]
            }
            val getDateStr = ::gen_getDateStr_fn
            val greetingClass = computed<String>(getGreetingClass)
            val dateStr = computed<String>(getDateStr)
            onPageShow(fun(){
                store.refreshHomeData()
                store.refreshLlmHistory()
            }
            )
            fun gen_startAction_fn(actionId: String): Unit {
                setActionIdForNextPage(actionId)
                uni_navigateTo(NavigateToOptions(url = "/pages/action/execute?actionId=" + actionId))
            }
            val startAction = ::gen_startAction_fn
            fun gen_goSettings_fn(): Unit {
                uni_switchTab(SwitchTabOptions(url = "/pages/settings/index"))
            }
            val goSettings = ::gen_goSettings_fn
            onLoad(fun(_options): Unit {
                try {
                    uni__on("showTriggerRuleDialog", fun(data: Any): Unit {
                        try {
                            if (data == null) {
                                return
                            }
                            val obj = data as UTSJSONObject
                            if (obj == null) {
                                return
                            }
                            val ruleRaw = obj["rule"]
                            val reasoningRaw = obj["reasoning"]
                            val ruleStr = JSON.stringify(ruleRaw)
                            val rule = JSON.parse(ruleStr) as EffectiveTriggerRule
                            if (rule == null) {
                                return
                            }
                            pendingRule.value = rule
                            val reasoning = if (UTSAndroid.`typeof`(reasoningRaw) === "string") {
                                (reasoningRaw as String)
                            } else {
                                ""
                            }
                            pendingReasoning.value = reasoning
                            showRuleDialog.value = true
                        }
                         catch (e: Throwable) {
                            console.warn("[home] showTriggerRuleDialog 处理异常: " + JSON.stringify(e))
                        }
                    }
                    )
                }
                 catch (e: Throwable) {
                    console.warn("[home] \$on showTriggerRuleDialog 异常: " + JSON.stringify(e))
                }
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to _nC(_uA(
                            "greeting-bar",
                            unref(greetingClass)
                        ))), _uA(
                            _cE("view", _uM("class" to "greeting-header"), _uA(
                                _cE("view", _uM("class" to "greeting-left"), _uA(
                                    _cE("text", _uM("class" to "greeting-text"), _tD(unref(greeting)), 1),
                                    _cE("text", _uM("class" to "greeting-date"), _tD(unref(dateStr)), 1)
                                )),
                                _cE("view", _uM("class" to "greeting-right"), _uA(
                                    _cE("view", _uM("class" to "emoji-button", "onClick" to openPhoneDialog), _uA(
                                        _cE("text", _uM("class" to "emoji-button-text"), _tD(unref(greetingEmoji)), 1)
                                    )),
                                    _cE("view", _uM("class" to "settings-icon", "onClick" to goSettings), _uA(
                                        _cE("text", _uM("class" to "settings-icon-text"), "⚙")
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "phone-usage-hint", "onClick" to openPhoneDialog), _uA(
                                _cE("text", _uM("class" to "phone-usage-hint-text"), _tD(unref(phoneUsageHint)), 1)
                            ))
                        ), 2),
                        _cE("view", _uM("class" to "guard-card"), _uA(
                            _cE("text", _uM("class" to "guard-eyebrow"), "今日已完成"),
                            _cE("view", _uM("class" to "guard-row"), _uA(
                                _cE("text", _uM("class" to "guard-number"), _tD(unref(guardCount)), 1),
                                _cE("text", _uM("class" to "guard-unit"), "次")
                            )),
                            _cE("text", _uM("class" to "guard-sub"), "微动作累计 " + _tD(unref(guardCount)) + " 次 · 每一次都是对健康的投资", 1)
                        )),
                        _cV(unref(GenComponentsStatusIndicatorClass), _uM("title" to "护眼", "percent" to unref(eyeScore), "status" to unref(eyeStatus)), null, 8, _uA(
                            "percent",
                            "status"
                        )),
                        _cV(unref(GenComponentsStatusIndicatorClass), _uM("title" to "体态", "percent" to unref(postureScore), "status" to unref(postureStatus)), null, 8, _uA(
                            "percent",
                            "status"
                        )),
                        _cV(unref(GenComponentsStatusIndicatorClass), _uM("title" to "活力", "percent" to unref(vitalityScore), "status" to unref(vitalityStatus)), null, 8, _uA(
                            "percent",
                            "status"
                        )),
                        _cE("view", _uM("class" to "penetration-section"), _uA(
                            _cE("text", _uM("class" to "section-label"), "习惯渗透度"),
                            _cE("view", _uM("class" to "penetration-track"), _uA(
                                _cE("view", _uM("class" to "penetration-fill", "style" to _nS(_uM("width" to ((if (unref(penetration) * 10 > 100) {
                                    100
                                } else {
                                    unref(penetration) * 10
                                }
                                ) + "%")))), null, 4)
                            )),
                            _cE("text", _uM("class" to "penetration-value"), _tD(unref(penetrationStr)), 1)
                        )),
                        if (unref(dailySummary) != null) {
                            _cE("view", _uM("key" to 0, "class" to "summary-card"), _uA(
                                _cE("text", _uM("class" to "summary-eyebrow"), "✨ 今日小结"),
                                _cE("text", _uM("class" to "summary-oneliner"), _tD(unref(summaryOneLiner)), 1),
                                if (unref(summaryBody).length > 0) {
                                    _cE("text", _uM("key" to 0, "class" to "summary-body"), _tD(unref(summaryBody)), 1)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (unref(summaryGoal).length > 0) {
                                    _cE("view", _uM("key" to 1, "class" to "summary-goal-row"), _uA(
                                        _cE("text", _uM("class" to "summary-goal-label"), "🎯 明日目标"),
                                        _cE("text", _uM("class" to "summary-goal-text"), _tD(unref(summaryGoal)), 1)
                                    ))
                                } else {
                                    _cC("v-if", true)
                                },
                                if (unref(summaryEncourage).length > 0) {
                                    _cE("text", _uM("key" to 2, "class" to "summary-encourage"), _tD(unref(summaryEncourage)), 1)
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        } else {
                            _cE("view", _uM("key" to 1, "class" to "summary-card summary-empty"), _uA(
                                _cE("text", _uM("class" to "summary-empty-text"), _tD(unref(summaryPlaceholder)), 1),
                                _cE("text", _uM("class" to "summary-empty-hint"), "完成更多动作，" + _tD(unref(summaryHour)) + ":00 后自动生成", 1)
                            ))
                        }
                        ,
                        _cE("view", _uM("class" to "recommend-section"), _uA(
                            _cE("view", _uM("class" to "section-header-row"), _uA(
                                _cE("text", _uM("class" to "section-title"), "🎯 今日推荐")
                            )),
                            if (unref(recommendedActions).length === 0) {
                                _cE("view", _uM("key" to 0, "class" to "empty-recommend"), _uA(
                                    _cE("text", _uM("class" to "empty-text"), "暂无推荐")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE(Fragment, null, RenderHelpers.renderList(unref(recommendedActions), fun(action, idx, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("r-" + idx)), _uA(
                                    _cV(unref(GenComponentsActionCardClass), _uM("action-id" to action.id, "action-name" to action.name, "action-duration" to Math.ceil(action.defaultDurationMs / 1000), "variant" to "recommend", "onActionTap" to startAction), null, 8, _uA(
                                        "action-id",
                                        "action-name",
                                        "action-duration"
                                    ))
                                ))
                            }
                            ), 128)
                        )),
                        _cE("view", _uM("class" to "library-section"), _uA(
                            _cE("view", _uM("class" to "library-header"), _uA(
                                _cE("text", _uM("class" to "section-title"), "📚 全部微动作库（" + _tD(unref(allActions).length) + "）", 1),
                                _cE("text", _uM("class" to "library-hint"), "点选任意动作立即开始")
                            )),
                            _cE(Fragment, null, RenderHelpers.renderList(unref(allActions), fun(action, idx, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("a-" + idx), "class" to "library-item"), _uA(
                                    _cV(unref(GenComponentsActionCardClass), _uM("action-id" to action.id, "action-name" to action.name, "action-duration" to Math.ceil(action.defaultDurationMs / 1000), "variant" to "library", "onActionTap" to startAction), null, 8, _uA(
                                        "action-id",
                                        "action-name",
                                        "action-duration"
                                    ))
                                ))
                            }
                            ), 128)
                        )),
                        _cV(unref(GenComponentsLlmHistoryCardClass), _uM("entries" to unref(llmHistoryEntries)), null, 8, _uA(
                            "entries"
                        )),
                        _cE("view", _uM("class" to "bottom-spacer"))
                    )),
                    _cV(unref(GenComponentsPhoneUsageDialogClass), _uM("visible" to unref(showPhoneDialog), "onClose" to closePhoneDialog), null, 8, _uA(
                        "visible"
                    )),
                    _cV(unref(GenComponentsTriggerRuleDialogClass), _uM("visible" to unref(showRuleDialog), "action-name" to unref(ruleActionName), "time-window-start" to unref(ruleTimeWindowStart), "time-window-end" to unref(ruleTimeWindowEnd), "time-threshold-minutes" to unref(ruleTimeThresholdMinutes), "screen-cond-text" to unref(ruleScreenCondText), "reasoning" to unref(pendingReasoning), "onClose" to closeRuleDialog, "onAccept" to acceptRule, "onDecline" to closeRuleDialog, "onNeverAsk" to neverAskRule), null, 8, _uA(
                        "visible",
                        "action-name",
                        "time-window-start",
                        "time-window-end",
                        "time-threshold-minutes",
                        "screen-cond-text",
                        "reasoning"
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F5F6FA")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "greeting-bar" to _pS(_uM("flexDirection" to "column", "paddingTop" to 56, "paddingRight" to 20, "paddingBottom" to 0, "paddingLeft" to 20, "backgroundColor" to "#2C3E50")), "greeting-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingBottom" to 12)), "bg-morning" to _pS(_uM("backgroundColor" to "#A93226")), "bg-noon" to _pS(_uM("backgroundColor" to "#1A5490")), "bg-afternoon" to _pS(_uM("backgroundColor" to "#8B4513")), "bg-evening" to _pS(_uM("backgroundColor" to "#1A237E")), "bg-night" to _pS(_uM("backgroundColor" to "#1B2631")), "greeting-left" to _pS(_uM("flexDirection" to "column")), "greeting-text" to _pS(_uM("fontSize" to 24, "fontWeight" to "bold", "color" to "#FFFFFF")), "greeting-date" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.85)", "marginTop" to 4)), "greeting-right" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "emoji-button" to _pS(_uM("width" to 44, "height" to 44, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.25)", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 10)), "emoji-button-text" to _pS(_uM("fontSize" to 24)), "settings-icon" to _pS(_uM("width" to 36, "height" to 36, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "backgroundColor" to "rgba(255,255,255,0.25)", "alignItems" to "center", "justifyContent" to "center")), "settings-icon-text" to _pS(_uM("fontSize" to 18, "color" to "#FFFFFF")), "phone-usage-hint" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to 10, "paddingRight" to 16, "paddingBottom" to 10, "paddingLeft" to 16, "marginTop" to 0, "marginRight" to -20, "marginBottom" to 0, "marginLeft" to -20, "backgroundColor" to "rgba(0,0,0,0.15)")), "phone-usage-hint-text" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "fontWeight" to 500)), "guard-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "marginTop" to 8, "marginRight" to 16, "marginBottom" to 8, "marginLeft" to 16, "paddingTop" to 18, "paddingRight" to 20, "paddingBottom" to 18, "paddingLeft" to 20, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "flexDirection" to "column", "boxShadow" to "0 4px 12px rgba(0, 0, 0, 0.06)")), "guard-eyebrow" to _pS(_uM("fontSize" to 12, "color" to "#7F8C8D", "marginBottom" to 4)), "guard-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end", "marginBottom" to 6)), "guard-number" to _pS(_uM("fontSize" to 36, "fontWeight" to "bold", "color" to "#2ECC71")), "guard-unit" to _pS(_uM("fontSize" to 16, "color" to "#2ECC71", "fontWeight" to "bold", "marginLeft" to 4)), "guard-sub" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6")), "penetration-section" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "marginTop" to 8, "marginRight" to 16, "marginBottom" to 8, "marginLeft" to 16, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.04)")), "section-label" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D", "width" to 80)), "penetration-track" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 6, "backgroundColor" to "#F0E6F6", "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3, "overflow" to "hidden")), "penetration-fill" to _pS(_uM("height" to 6, "backgroundImage" to "linear-gradient(90deg, #9B59B6 0%, #E91E63 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "penetration-value" to _pS(_uM("fontSize" to 13, "color" to "#9B59B6", "fontWeight" to "bold", "width" to 40, "textAlign" to "right")), "summary-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "marginTop" to 12, "marginRight" to 16, "marginBottom" to 12, "marginLeft" to 16, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "borderLeftWidth" to 4, "borderLeftColor" to "#3498DB", "borderLeftStyle" to "solid", "flexDirection" to "column", "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.04)")), "summary-empty" to _pS(_uM("borderLeftColor" to "#BDC3C7", "alignItems" to "center")), "summary-eyebrow" to _pS(_uM("fontSize" to 12, "color" to "#3498DB", "fontWeight" to "bold", "marginBottom" to 6)), "summary-oneliner" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50", "marginBottom" to 8)), "summary-body" to _pS(_uM("fontSize" to 14, "color" to "#34495E", "lineHeight" to "20px", "marginBottom" to 8)), "summary-goal-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#F8F9FA", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 8, "paddingRight" to 10, "paddingBottom" to 8, "paddingLeft" to 10, "marginBottom" to 8)), "summary-goal-label" to _pS(_uM("fontSize" to 12, "color" to "#7F8C8D", "marginRight" to 8)), "summary-goal-text" to _pS(_uM("fontSize" to 13, "color" to "#2C3E50", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "summary-encourage" to _pS(_uM("fontSize" to 13, "color" to "#E67E22", "fontStyle" to "italic", "marginTop" to 4)), "summary-empty-text" to _pS(_uM("fontSize" to 14, "color" to "#7F8C8D", "marginBottom" to 4)), "summary-empty-hint" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6")), "recommend-section" to _pS(_uM("marginTop" to 12, "paddingBottom" to 12)), "section-header-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "paddingTop" to 8, "paddingRight" to 16, "paddingBottom" to 4, "paddingLeft" to 16)), "library-section" to _pS(_uM("marginTop" to 12, "paddingBottom" to 12)), "library-header" to _pS(_uM("flexDirection" to "column", "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 4, "paddingLeft" to 16)), "library-hint" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginTop" to 2)), "library-item" to _pS(_uM("marginTop" to 0, "marginRight" to 0, "marginBottom" to 8, "marginLeft" to 0)), "section-title" to _pS(_uM("fontSize" to 16, "fontWeight" to "bold", "color" to "#2C3E50")), "empty-recommend" to _pS(_uM("paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "alignItems" to "center")), "empty-text" to _pS(_uM("fontSize" to 14, "color" to "#BDC3C7")), "bottom-spacer" to _pS(_uM("height" to 40)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
