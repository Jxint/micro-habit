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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
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
            val guardMinutes = computed<Number>(fun(): Number {
                return store.guardMinutes.value
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
            val summaryOneLiner = computed<String>(fun(): String {
                val s = dailySummary.value
                return if (s != null && s.one_liner.length > 0) {
                    s.one_liner
                } else {
                    "今日小结待生成"
                }
            }
            )
            val summaryBody = computed<String>(fun(): String {
                val s = dailySummary.value
                return if (s != null) {
                    s.summary
                } else {
                    ""
                }
            }
            )
            val summaryGoal = computed<String>(fun(): String {
                val s = dailySummary.value
                return if (s != null) {
                    s.tomorrow_goal
                } else {
                    ""
                }
            }
            )
            val summaryEncourage = computed<String>(fun(): String {
                val s = dailySummary.value
                return if (s != null) {
                    s.encourage
                } else {
                    ""
                }
            }
            )
            val summaryHour = computed<Number>(fun(): Number {
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
            )
            val summaryPlaceholder = computed<String>(fun(): String {
                if (dailySummaryLoading.value) {
                    return "正在生成今日小结…"
                }
                val cnt = store.todayCompletedCount.value
                if (cnt === 0) {
                    return "完成一个微动作，开启今日小结"
                }
                return "已完成" + cnt + "次，" + summaryHour.value + ":00 后自动小结"
            }
            )
            val greeting = computed<String>(fun(): String {
                val h = currentHour()
                if (h < 6) {
                    return "夜深了 🌙"
                }
                if (h < 9) {
                    return "早上好 ☀️"
                }
                if (h < 12) {
                    return "上午好 ☀️"
                }
                if (h < 14) {
                    return "中午好 🌤"
                }
                if (h < 18) {
                    return "下午好 ⛅"
                }
                if (h < 22) {
                    return "晚上好 🌆"
                }
                return "夜深了 🌙"
            }
            )
            onPageShow(fun(){
                store.refreshHomeData()
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
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "greeting-bar"), _uA(
                            _cE("text", _uM("class" to "greeting-text"), _tD(unref(greeting)), 1),
                            _cE("text", _uM("class" to "settings-icon", "onClick" to goSettings), "⚙")
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
                        _cE("view", _uM("class" to "guard-section"), _uA(
                            _cE("text", _uM("class" to "guard-text"), "今日已为你守护 " + _tD(unref(guardMinutes)) + " 分钟", 1)
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
                            _cE("text", _uM("class" to "section-title"), "🎯 今日推荐"),
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
                                    _cV(unref(GenComponentsActionCardClass), _uM("action-id" to action.id, "action-name" to action.name, "action-duration" to Math.ceil(action.defaultDurationMs / 1000), "onActionTap" to startAction), null, 8, _uA(
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
                                    _cV(unref(GenComponentsActionCardClass), _uM("action-id" to action.id, "action-name" to action.name, "action-duration" to Math.ceil(action.defaultDurationMs / 1000), "onActionTap" to startAction), null, 8, _uA(
                                        "action-id",
                                        "action-name",
                                        "action-duration"
                                    ))
                                ))
                            }
                            ), 128)
                        ))
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F5F6FA")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "greeting-bar" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 50, "paddingRight" to 20, "paddingBottom" to 16, "paddingLeft" to 20)), "greeting-text" to _pS(_uM("fontSize" to 22, "fontWeight" to "bold", "color" to "#2C3E50")), "settings-icon" to _pS(_uM("fontSize" to 22, "color" to "#7F8C8D")), "guard-section" to _pS(_uM("alignItems" to "center", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16)), "guard-text" to _pS(_uM("fontSize" to 18, "fontWeight" to "bold", "color" to "#2ECC71")), "penetration-section" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "paddingTop" to 8, "paddingRight" to 16, "paddingBottom" to 8, "paddingLeft" to 16, "marginTop" to 4, "marginRight" to 16, "marginBottom" to 4, "marginLeft" to 16, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10)), "section-label" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D", "width" to 80)), "penetration-track" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 6, "backgroundColor" to "#ECF0F1", "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3, "overflow" to "hidden")), "penetration-fill" to _pS(_uM("height" to 6, "backgroundColor" to "#9B59B6", "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "penetration-value" to _pS(_uM("fontSize" to 13, "color" to "#9B59B6", "width" to 40, "textAlign" to "right")), "summary-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "marginTop" to 12, "marginRight" to 16, "marginBottom" to 12, "marginLeft" to 16, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "borderLeftWidth" to 4, "borderLeftColor" to "#3498DB", "borderLeftStyle" to "solid", "flexDirection" to "column")), "summary-empty" to _pS(_uM("borderLeftColor" to "#BDC3C7", "alignItems" to "center")), "summary-eyebrow" to _pS(_uM("fontSize" to 12, "color" to "#3498DB", "fontWeight" to "bold", "marginBottom" to 6)), "summary-oneliner" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50", "marginBottom" to 8)), "summary-body" to _pS(_uM("fontSize" to 14, "color" to "#34495E", "lineHeight" to "20px", "marginBottom" to 8)), "summary-goal-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#F8F9FA", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 8, "paddingRight" to 10, "paddingBottom" to 8, "paddingLeft" to 10, "marginBottom" to 8)), "summary-goal-label" to _pS(_uM("fontSize" to 12, "color" to "#7F8C8D", "marginRight" to 8)), "summary-goal-text" to _pS(_uM("fontSize" to 13, "color" to "#2C3E50", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "summary-encourage" to _pS(_uM("fontSize" to 13, "color" to "#E67E22", "fontStyle" to "italic", "marginTop" to 4)), "summary-empty-text" to _pS(_uM("fontSize" to 14, "color" to "#7F8C8D", "marginBottom" to 4)), "summary-empty-hint" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6")), "recommend-section" to _pS(_uM("marginTop" to 8, "paddingBottom" to 12)), "library-section" to _pS(_uM("marginTop" to 8, "paddingBottom" to 24)), "library-header" to _pS(_uM("flexDirection" to "column", "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 4, "paddingLeft" to 16)), "library-hint" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginTop" to 2)), "library-item" to _pS(_uM("marginTop" to 0, "marginRight" to 0, "marginBottom" to 8, "marginLeft" to 0)), "section-title" to _pS(_uM("fontSize" to 16, "fontWeight" to "bold", "color" to "#2C3E50", "paddingTop" to 8, "paddingRight" to 16, "paddingBottom" to 8, "paddingLeft" to 16)), "empty-recommend" to _pS(_uM("paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "alignItems" to "center")), "empty-text" to _pS(_uM("fontSize" to 14, "color" to "#BDC3C7")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
