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
open class GenComponentsLlmRuleReviewDialog : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var visible: Boolean by `$props`
    open var historyId: Number by `$props`
    open var decision: String by `$props`
    open var targetRuleId: Number by `$props`
    open var reasoning: String by `$props`
    open var confidence: Number by `$props`
    open var suggestedRule: Any by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsLlmRuleReviewDialog) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsLlmRuleReviewDialog
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val decisionText = computed<String>(fun(): String {
                val d: String = props.decision
                if (d == "create_rule") {
                    return "新增规则"
                }
                if (d == "modify_rule") {
                    return "修改规则"
                }
                if (d == "delete_rule") {
                    return "删除规则"
                }
                if (d == "no_change") {
                    return "保持现状"
                }
                return if (d.length > 0) {
                    d
                } else {
                    "建议"
                }
            }
            )
            val decisionBadge = computed<String>(fun(): String {
                val d: String = props.decision
                if (d == "create_rule") {
                    return "create"
                }
                if (d == "modify_rule") {
                    return "modify"
                }
                if (d == "delete_rule") {
                    return "delete"
                }
                return "noop"
            }
            )
            val confidencePercent = computed<String>(fun(): String {
                val c: Number = props.confidence
                if (c <= 0) {
                    return ""
                }
                val pct: Number = Math.floor(c * 100)
                return "" + pct
            }
            )
            fun gen_actionNameOf_fn(r: EffectiveTriggerRule): String {
                val a = getActionById(r.actionId)
                return if (a != null) {
                    a.name
                } else {
                    r.actionId
                }
            }
            val actionNameOf = ::gen_actionNameOf_fn
            fun gen_timeWindowOf_fn(r: EffectiveTriggerRule): String {
                if (r.timeWindow == null) {
                    return ""
                }
                return r.timeWindow!!.start + " ~ " + r.timeWindow!!.end
            }
            val timeWindowOf = ::gen_timeWindowOf_fn
            fun gen_thresholdOf_fn(r: EffectiveTriggerRule): String {
                val t: Number = r.timeThresholdMinutes
                if (t <= 0) {
                    return ""
                }
                return "连续前台 ≥ " + t + " 分钟"
            }
            val thresholdOf = ::gen_thresholdOf_fn
            fun gen_appPackagesOf_fn(r: EffectiveTriggerRule): String {
                if (r.screenConditions == null) {
                    return ""
                }
                if (r.screenConditions!!.appPackages.length < 1) {
                    return if (r.screenConditions!!.includeHome) {
                        "任意 App（含桌面）"
                    } else {
                        "任意 App"
                    }
                }
                return r.screenConditions!!.appPackages.join("、")
            }
            val appPackagesOf = ::gen_appPackagesOf_fn
            val actionName = computed<String>(fun(): String {
                val r = props.suggestedRule
                if (r == null) {
                    return ""
                }
                return actionNameOf(r as EffectiveTriggerRule)
            }
            )
            val timeWindowText = computed<String>(fun(): String {
                val r = props.suggestedRule
                if (r == null) {
                    return ""
                }
                return timeWindowOf(r as EffectiveTriggerRule)
            }
            )
            val thresholdText = computed<String>(fun(): String {
                val r = props.suggestedRule
                if (r == null) {
                    return ""
                }
                return thresholdOf(r as EffectiveTriggerRule)
            }
            )
            val appPackagesText = computed<String>(fun(): String {
                val r = props.suggestedRule
                if (r == null) {
                    return ""
                }
                return appPackagesOf(r as EffectiveTriggerRule)
            }
            )
            fun gen_close_fn(): Unit {
                emit("close")
            }
            val close = ::gen_close_fn
            fun gen_onMaskTap_fn(): Unit {
                close()
            }
            val onMaskTap = ::gen_onMaskTap_fn
            fun gen_onAccept_fn(): Unit {
                if (props.historyId > 0) {
                    markAccepted(props.historyId)
                }
                emit("accept", _uO("historyId" to props.historyId, "decision" to props.decision, "targetRuleId" to props.targetRuleId))
                close()
            }
            val onAccept = ::gen_onAccept_fn
            fun gen_onReject_fn(): Unit {
                if (props.historyId > 0) {
                    markRejected(props.historyId)
                }
                emit("reject", _uO("historyId" to props.historyId, "decision" to props.decision, "targetRuleId" to props.targetRuleId))
                close()
            }
            val onReject = ::gen_onReject_fn
            return fun(): Any? {
                return if (isTrue(_ctx.visible)) {
                    _cE("view", _uM("key" to 0, "class" to "dialog-mask", "onClick" to onMaskTap), _uA(
                        _cE("view", _uM("class" to "dialog-card", "onClick" to withModifiers(fun(){}, _uA(
                            "stop"
                        ))), _uA(
                            _cE("view", _uM("class" to "dialog-header"), _uA(
                                _cE("text", _uM("class" to "dialog-title"), "AI 建议审核"),
                                _cE("view", _uM("class" to "dialog-close", "onClick" to close), _uA(
                                    _cE("text", _uM("class" to "dialog-close-text"), "×")
                                ))
                            )),
                            _cE("view", _uM("class" to "dialog-body"), _uA(
                                _cE("view", _uM("class" to "row-tag"), _uA(
                                    _cE("text", _uM("class" to _nC(_uA(
                                        "row-tag-text",
                                        "tag-" + unref(decisionBadge)
                                    ))), _tD(unref(decisionText)), 3),
                                    if (unref(confidencePercent).length > 0) {
                                        _cE("text", _uM("key" to 0, "class" to "row-confidence"), "把握度 " + _tD(unref(confidencePercent)) + "%", 1)
                                    } else {
                                        _cC("v-if", true)
                                    }
                                )),
                                if (unref(actionName).length > 0) {
                                    _cE("text", _uM("key" to 0, "class" to "row-section-title"), "建议动作")
                                } else {
                                    _cC("v-if", true)
                                },
                                if (unref(actionName).length > 0) {
                                    _cE("text", _uM("key" to 1, "class" to "row-value"), _tD(unref(actionName)), 1)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (unref(timeWindowText).length > 0) {
                                    _cE("text", _uM("key" to 2, "class" to "row-section-title"), "生效时间段")
                                } else {
                                    _cC("v-if", true)
                                },
                                if (unref(timeWindowText).length > 0) {
                                    _cE("text", _uM("key" to 3, "class" to "row-value"), _tD(unref(timeWindowText)), 1)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (unref(thresholdText).length > 0) {
                                    _cE("text", _uM("key" to 4, "class" to "row-section-title"), "时间阈值")
                                } else {
                                    _cC("v-if", true)
                                },
                                if (unref(thresholdText).length > 0) {
                                    _cE("text", _uM("key" to 5, "class" to "row-value"), _tD(unref(thresholdText)), 1)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (unref(appPackagesText).length > 0) {
                                    _cE("text", _uM("key" to 6, "class" to "row-section-title"), "命中 App")
                                } else {
                                    _cC("v-if", true)
                                },
                                if (unref(appPackagesText).length > 0) {
                                    _cE("text", _uM("key" to 7, "class" to "row-value"), _tD(unref(appPackagesText)), 1)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (_ctx.reasoning.length > 0) {
                                    _cE("text", _uM("key" to 8, "class" to "row-section-title"), "AI 理由")
                                } else {
                                    _cC("v-if", true)
                                },
                                if (_ctx.reasoning.length > 0) {
                                    _cE("text", _uM("key" to 9, "class" to "row-value reasoning"), _tD(_ctx.reasoning), 1)
                                } else {
                                    _cC("v-if", true)
                                }
                            )),
                            _cE("view", _uM("class" to "dialog-buttons"), _uA(
                                _cE("view", _uM("class" to "btn btn-secondary", "onClick" to onReject), _uA(
                                    _cE("text", _uM("class" to "btn-text"), "拒绝")
                                )),
                                _cE("view", _uM("class" to "btn btn-primary", "onClick" to onAccept), _uA(
                                    _cE("text", _uM("class" to "btn-text"), "接受")
                                ))
                            ))
                        ), 8, _uA(
                            "onClick"
                        ))
                    ))
                } else {
                    _cC("v-if", true)
                }
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("dialog-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 999)), "dialog-card" to _pS(_uM("width" to "90%", "maxWidth" to 420, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "flexDirection" to "column")), "dialog-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 12, "paddingLeft" to 4)), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50")), "dialog-close" to _pS(_uM("width" to 28, "height" to 28, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#F0F3F4", "alignItems" to "center", "justifyContent" to "center")), "dialog-close-text" to _pS(_uM("fontSize" to 18, "color" to "#7F8C8D", "fontWeight" to "bold")), "dialog-body" to _pS(_uM("paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4, "marginBottom" to 12)), "row-tag" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 8)), "row-tag-text" to _pS(_uM("fontSize" to 11, "fontWeight" to "bold", "paddingTop" to 3, "paddingRight" to 8, "paddingBottom" to 3, "paddingLeft" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "marginRight" to 8)), "tag-create" to _pS(_uM("backgroundColor" to "#D5F5E3", "color" to "#239B56")), "tag-modify" to _pS(_uM("backgroundColor" to "#FCF3CF", "color" to "#B7950B")), "tag-delete" to _pS(_uM("backgroundColor" to "#FADBD8", "color" to "#C0392B")), "tag-noop" to _pS(_uM("backgroundColor" to "#ECF0F1", "color" to "#7F8C8D")), "row-confidence" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6")), "row-section-title" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6", "marginTop" to 10, "marginBottom" to 4)), "row-value" to _uM("" to _uM("fontSize" to 14, "color" to "#2C3E50", "fontWeight" to "bold", "lineHeight" to 1.4), ".reasoning" to _uM("fontWeight" to "normal", "color" to "#34495E", "fontSize" to 13, "lineHeight" to 1.5)), "dialog-buttons" to _pS(_uM("flexDirection" to "row", "marginBottom" to 4)), "btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center")), "btn-secondary" to _pS(_uM("backgroundColor" to "#ECF0F1", "marginRight" to 8)), "btn-primary" to _pS(_uM("backgroundColor" to "#3498DB", "marginLeft" to 8)), "btn-text" to _uM("" to _uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#FFFFFF"), ".btn-secondary " to _uM("color" to "#2C3E50")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("close" to null, "accept" to null, "reject" to null)
        var props = _nP(_uM("visible" to _uM("type" to "Boolean", "required" to true, "default" to false), "historyId" to _uM("type" to "Number", "required" to true, "default" to 0), "decision" to _uM("type" to "String", "required" to true, "default" to ""), "targetRuleId" to _uM("type" to "Number", "required" to true, "default" to 0), "reasoning" to _uM("type" to "String", "required" to true, "default" to ""), "confidence" to _uM("type" to "Number", "required" to true, "default" to 0), "suggestedRule" to _uM("required" to true, "default" to null)))
        var propsNeedCastKeys = _uA(
            "visible",
            "historyId",
            "decision",
            "targetRuleId",
            "reasoning",
            "confidence",
            "suggestedRule"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
