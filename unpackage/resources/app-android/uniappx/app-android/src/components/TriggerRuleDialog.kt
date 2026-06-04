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
                                    _cE("text", _uM("class" to "btn-text"), "接受")
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
                return _uM("dialog-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 999)), "dialog-card" to _pS(_uM("width" to "90%", "maxWidth" to 420, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "flexDirection" to "column")), "dialog-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 12, "paddingLeft" to 4)), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50")), "dialog-close" to _pS(_uM("width" to 28, "height" to 28, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#F0F3F4", "alignItems" to "center", "justifyContent" to "center")), "dialog-close-text" to _pS(_uM("fontSize" to 18, "color" to "#7F8C8D", "fontWeight" to "bold")), "dialog-body" to _pS(_uM("paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4, "marginBottom" to 12)), "rule-section-title" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6", "marginTop" to 10, "marginBottom" to 4)), "rule-value" to _uM("" to _uM("fontSize" to 14, "color" to "#2C3E50", "fontWeight" to "bold", "lineHeight" to 1.4), ".reasoning" to _uM("fontWeight" to "normal", "color" to "#34495E", "fontSize" to 13, "lineHeight" to 1.5)), "dialog-buttons" to _pS(_uM("flexDirection" to "row", "marginBottom" to 8)), "btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center")), "btn-secondary" to _pS(_uM("backgroundColor" to "#ECF0F1", "marginRight" to 8)), "btn-primary" to _pS(_uM("backgroundColor" to "#3498DB", "marginLeft" to 8)), "btn-text" to _uM("" to _uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#FFFFFF"), ".btn-secondary " to _uM("color" to "#2C3E50")), "never-ask-row" to _pS(_uM("paddingTop" to 8, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0, "alignItems" to "center")), "never-ask-text" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6")))
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
