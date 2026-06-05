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
open class GenComponentsTriggerRuleDialog : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var visible: Boolean by `$props`
    open var actionName: String by `$props`
    open var timeWindowStart: String by `$props`
    open var timeWindowEnd: String by `$props`
    open var timeThresholdMinutes: Number by `$props`
    open var screenCondText: String by `$props`
    open var reasoning: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsTriggerRuleDialog) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsTriggerRuleDialog
            val _cache = __ins.renderCache
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            fun gen_close_fn(): Unit {
                emit("close")
            }
            val close = ::gen_close_fn
            fun gen_onAccept_fn(): Unit {
                emit("accept")
            }
            val onAccept = ::gen_onAccept_fn
            fun gen_onDecline_fn(): Unit {
                emit("decline")
            }
            val onDecline = ::gen_onDecline_fn
            fun gen_onNeverAsk_fn(): Unit {
                emit("neverAsk")
            }
            val onNeverAsk = ::gen_onNeverAsk_fn
            fun gen_onMaskTap_fn(): Unit {
                close()
            }
            val onMaskTap = ::gen_onMaskTap_fn
            return fun(): Any? {
                return if (isTrue(_ctx.visible)) {
                    _cE("view", _uM("key" to 0, "class" to "dialog-mask", "onClick" to onMaskTap), _uA(
                        _cE("view", _uM("class" to "dialog-card", "onClick" to withModifiers(fun(){}, _uA(
                            "stop"
                        ))), _uA(
                            _cE("view", _uM("class" to "dialog-header"), _uA(
                                _cE("text", _uM("class" to "dialog-title"), "AI 触发建议"),
                                _cE("view", _uM("class" to "dialog-close", "onClick" to close), _uA(
                                    _cE("text", _uM("class" to "dialog-close-text"), "×")
                                ))
                            )),
                            _cE("view", _uM("class" to "dialog-body"), _uA(
                                _cE("text", _uM("class" to "rule-section-title"), "建议动作"),
                                _cE("text", _uM("class" to "rule-value"), _tD(_ctx.actionName), 1),
                                if (_ctx.timeWindowStart.length > 0) {
                                    _cE("text", _uM("key" to 0, "class" to "rule-section-title"), "生效时间段")
                                } else {
                                    _cC("v-if", true)
                                },
                                if (_ctx.timeWindowStart.length > 0) {
                                    _cE("text", _uM("key" to 1, "class" to "rule-value"), _tD(_ctx.timeWindowStart) + " ~ " + _tD(_ctx.timeWindowEnd), 1)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (_ctx.timeThresholdMinutes > 0) {
                                    _cE("text", _uM("key" to 2, "class" to "rule-section-title"), "时间阈值")
                                } else {
                                    _cC("v-if", true)
                                },
                                if (_ctx.timeThresholdMinutes > 0) {
                                    _cE("text", _uM("key" to 3, "class" to "rule-value"), "连续前台 ≥ " + _tD(_ctx.timeThresholdMinutes) + " 分钟", 1)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (_ctx.screenCondText.length > 0) {
                                    _cE("text", _uM("key" to 4, "class" to "rule-section-title"), "屏幕状态")
                                } else {
                                    _cC("v-if", true)
                                },
                                if (_ctx.screenCondText.length > 0) {
                                    _cE("text", _uM("key" to 5, "class" to "rule-value"), _tD(_ctx.screenCondText), 1)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (_ctx.reasoning.length > 0) {
                                    _cE("text", _uM("key" to 6, "class" to "rule-section-title"), "建议理由")
                                } else {
                                    _cC("v-if", true)
                                },
                                if (_ctx.reasoning.length > 0) {
                                    _cE("text", _uM("key" to 7, "class" to "rule-value reasoning"), _tD(_ctx.reasoning), 1)
                                } else {
                                    _cC("v-if", true)
                                }
                            )),
                            _cE("view", _uM("class" to "dialog-buttons"), _uA(
                                _cE("view", _uM("class" to "btn btn-secondary", "onClick" to onDecline), _uA(
                                    _cE("text", _uM("class" to "btn-text"), "拒绝")
                                )),
                                _cE("view", _uM("class" to "btn btn-primary", "onClick" to onAccept), _uA(
                                    _cE("text", _uM("class" to "btn-text btn-text-primary"), "接受")
                                ))
                            )),
                            _cE("view", _uM("class" to "never-ask-row", "onClick" to onNeverAsk), _uA(
                                _cE("text", _uM("class" to "never-ask-text"), "不再询问")
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
                return _uM("dialog-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.58)", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 999)), "dialog-card" to _pS(_uM("width" to "90%", "maxWidth" to 420, "maxHeight" to 560, "backgroundColor" to "rgba(29,25,62,0.96)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.94) 0%, rgba(84, 65, 124, 0.82) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "flexDirection" to "column", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.2)", "borderRightColor" to "rgba(219,200,237,0.2)", "borderBottomColor" to "rgba(219,200,237,0.2)", "borderLeftColor" to "rgba(219,200,237,0.2)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "dialog-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 12, "paddingLeft" to 4)), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#FFFFFF")), "dialog-close" to _pS(_uM("width" to 30, "height" to 30, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "rgba(255,255,255,0.1)", "alignItems" to "center", "justifyContent" to "center")), "dialog-close-text" to _pS(_uM("fontSize" to 18, "color" to "rgba(255,255,255,0.72)", "fontWeight" to "bold")), "hint-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "marginBottom" to 14)), "summary-row" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "ai-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "preview-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "picker-value-box" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "time-value-box" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "history-rule" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "achv-progress-wrap" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "hint-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "summary-stat-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "legend-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "legend-percent" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "legend-minutes" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-hint" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "ai-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "ai-placeholder-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "preview-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "range-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "picker-unit" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "achv-desc" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "achv-date" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "achv-history" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "action-row" to _pS(_uM("flexDirection" to "row", "marginTop" to 16)), "dialog-buttons" to _pS(_uM("flexDirection" to "row", "marginTop" to 16, "justifyContent" to "space-between")), "unlock-buttons" to _pS(_uM("flexDirection" to "row", "marginTop" to 16)), "action-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "unlock-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "action-cancel" to _pS(_uM("marginRight" to 8, "backgroundColor" to "rgba(255,255,255,0.1)")), "btn-secondary" to _pS(_uM("marginRight" to 8, "backgroundColor" to "rgba(255,255,255,0.1)")), "action-confirm" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "btn-primary" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "unlock-btn-primary" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "action-cancel-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "btn-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "unlock-btn-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "action-confirm-text" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")), "unlock-btn-text-primary" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")), "dialog-body" to _pS(_uM("paddingTop" to 4, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0)), "rule-section-title" to _pS(_uM("fontSize" to 12, "color" to "#DBC8ED", "fontWeight" to "bold", "marginTop" to 10)), "rule-value" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF", "lineHeight" to "20px", "marginTop" to 4)), "reasoning" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "btn-text-primary" to _pS(_uM("color" to "#161A33", "fontWeight" to "bold")), "never-ask-row" to _pS(_uM("alignItems" to "center", "paddingTop" to 12, "paddingRight" to 0, "paddingBottom" to 2, "paddingLeft" to 0)), "never-ask-text" to _pS(_uM("color" to "rgba(255,255,255,0.58)", "fontSize" to 12)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("close" to null, "accept" to null, "decline" to null, "neverAsk" to null)
        var props = _nP(_uM("visible" to _uM("type" to "Boolean", "required" to true, "default" to false), "actionName" to _uM("type" to "String", "required" to true, "default" to ""), "timeWindowStart" to _uM("type" to "String", "required" to true, "default" to ""), "timeWindowEnd" to _uM("type" to "String", "required" to true, "default" to ""), "timeThresholdMinutes" to _uM("type" to "Number", "required" to true, "default" to 0), "screenCondText" to _uM("type" to "String", "required" to true, "default" to ""), "reasoning" to _uM("type" to "String", "required" to true, "default" to "")))
        var propsNeedCastKeys = _uA(
            "visible",
            "actionName",
            "timeWindowStart",
            "timeWindowEnd",
            "timeThresholdMinutes",
            "screenCondText",
            "reasoning"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
