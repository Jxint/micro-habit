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
open class GenComponentsNumberPickerDialog : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var visible: Boolean by `$props`
    open var title: String by `$props`
    open var hint: String by `$props`
    open var initialValue: Number by `$props`
    open var minValue: Number by `$props`
    open var maxValue: Number by `$props`
    open var step: Number by `$props`
    open var unit: String by `$props`
    open var quickValues: UTSArray<Number> by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsNumberPickerDialog) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsNumberPickerDialog
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val currentValue = ref<Number>(props.initialValue)
            fun gen_clamp_fn(v: Number): Number {
                if (v < props.minValue) {
                    return props.minValue
                }
                if (v > props.maxValue) {
                    return props.maxValue
                }
                return v
            }
            val clamp = ::gen_clamp_fn
            fun gen_setValue_fn(v: Number): Unit {
                currentValue.value = clamp(v)
            }
            val setValue = ::gen_setValue_fn
            fun gen_onMinus_fn(): Unit {
                setValue(currentValue.value - props.step)
            }
            val onMinus = ::gen_onMinus_fn
            fun gen_onPlus_fn(): Unit {
                setValue(currentValue.value + props.step)
            }
            val onPlus = ::gen_onPlus_fn
            fun gen_close_fn(): Unit {
                emit("close")
            }
            val close = ::gen_close_fn
            fun gen_confirm_fn(): Unit {
                emit("confirm", currentValue.value)
            }
            val confirm = ::gen_confirm_fn
            fun gen_onMaskTap_fn(): Unit {
                close()
            }
            val onMaskTap = ::gen_onMaskTap_fn
            watch(fun(): Boolean {
                return props.visible
            }
            , fun(v: Boolean): Unit {
                if (v) {
                    currentValue.value = clamp(props.initialValue)
                }
            }
            )
            return fun(): Any? {
                return if (isTrue(_ctx.visible)) {
                    _cE("view", _uM("key" to 0, "class" to "dialog-mask", "onClick" to onMaskTap), _uA(
                        _cE("view", _uM("class" to "dialog-card", "onClick" to withModifiers(fun(){}, _uA(
                            "stop"
                        ))), _uA(
                            _cE("view", _uM("class" to "dialog-header"), _uA(
                                _cE("text", _uM("class" to "dialog-title"), _tD(_ctx.title), 1),
                                _cE("view", _uM("class" to "dialog-close", "onClick" to close), _uA(
                                    _cE("text", _uM("class" to "dialog-close-text"), "×")
                                ))
                            )),
                            _cE("view", _uM("class" to "hint-block"), _uA(
                                _cE("text", _uM("class" to "hint-text"), _tD(_ctx.hint), 1)
                            )),
                            _cE("view", _uM("class" to "picker-row"), _uA(
                                _cE("view", _uM("class" to "picker-btn", "onClick" to onMinus), _uA(
                                    _cE("text", _uM("class" to "picker-btn-text"), "−")
                                )),
                                _cE("view", _uM("class" to "picker-value-box"), _uA(
                                    _cE("text", _uM("class" to "picker-value"), _tD(unref(currentValue)), 1),
                                    if (_ctx.unit.length > 0) {
                                        _cE("text", _uM("key" to 0, "class" to "picker-unit"), _tD(_ctx.unit), 1)
                                    } else {
                                        _cC("v-if", true)
                                    }
                                )),
                                _cE("view", _uM("class" to "picker-btn", "onClick" to onPlus), _uA(
                                    _cE("text", _uM("class" to "picker-btn-text"), "+")
                                ))
                            )),
                            _cE("view", _uM("class" to "quick-row"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(_ctx.quickValues, fun(q, qi, __index, _cached): Any {
                                    return _cE("view", _uM("key" to ("q-" + qi), "class" to _nC(_uA(
                                        "quick-chip",
                                        if (q === unref(currentValue)) {
                                            "quick-chip-active"
                                        } else {
                                            ""
                                        }
                                    )), "onClick" to fun(){
                                        setValue(q)
                                    }), _uA(
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "quick-chip-text",
                                            if (q === unref(currentValue)) {
                                                "quick-chip-text-active"
                                            } else {
                                                ""
                                            }
                                        ))), _tD(q), 3)
                                    ), 10, _uA(
                                        "onClick"
                                    ))
                                }), 128)
                            )),
                            _cE("view", _uM("class" to "action-row"), _uA(
                                _cE("view", _uM("class" to "action-btn action-cancel", "onClick" to close), _uA(
                                    _cE("text", _uM("class" to "action-cancel-text"), "取消")
                                )),
                                _cE("view", _uM("class" to "action-btn action-confirm", "onClick" to confirm), _uA(
                                    _cE("text", _uM("class" to "action-confirm-text"), "确认")
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
                return _uM("dialog-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.58)", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 999)), "dialog-card" to _pS(_uM("width" to "90%", "maxWidth" to 420, "maxHeight" to 560, "backgroundColor" to "rgba(29,25,62,0.96)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.94) 0%, rgba(84, 65, 124, 0.82) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "flexDirection" to "column", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.2)", "borderRightColor" to "rgba(219,200,237,0.2)", "borderBottomColor" to "rgba(219,200,237,0.2)", "borderLeftColor" to "rgba(219,200,237,0.2)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "dialog-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 12, "paddingLeft" to 4)), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#FFFFFF")), "dialog-close" to _pS(_uM("width" to 30, "height" to 30, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "rgba(255,255,255,0.1)", "alignItems" to "center", "justifyContent" to "center")), "dialog-close-text" to _pS(_uM("fontSize" to 18, "color" to "rgba(255,255,255,0.72)", "fontWeight" to "bold")), "hint-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "marginBottom" to 14)), "summary-row" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "ai-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "preview-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "picker-value-box" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "minWidth" to 112, "height" to 58, "marginTop" to 0, "marginRight" to 12, "marginBottom" to 0, "marginLeft" to 12, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "time-value-box" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "history-rule" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "achv-progress-wrap" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "hint-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "summary-stat-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "legend-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "legend-percent" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "legend-minutes" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-hint" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "ai-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "ai-placeholder-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "preview-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "range-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "picker-unit" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "marginLeft" to 4)), "achv-desc" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "achv-date" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "achv-history" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "action-row" to _pS(_uM("flexDirection" to "row", "marginTop" to 16)), "dialog-buttons" to _pS(_uM("flexDirection" to "row", "marginTop" to 16)), "unlock-buttons" to _pS(_uM("flexDirection" to "row", "marginTop" to 16)), "action-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "unlock-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "action-cancel" to _pS(_uM("marginRight" to 8, "backgroundColor" to "rgba(255,255,255,0.1)")), "btn-secondary" to _pS(_uM("marginRight" to 8, "backgroundColor" to "rgba(255,255,255,0.1)")), "action-confirm" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "btn-primary" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "unlock-btn-primary" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "action-cancel-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "btn-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "unlock-btn-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "action-confirm-text" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")), "unlock-btn-text-primary" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")), "picker-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "marginTop" to 18, "marginRight" to 0, "marginBottom" to 18, "marginLeft" to 0)), "picker-btn" to _pS(_uM("width" to 44, "height" to 44, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.1)", "alignItems" to "center", "justifyContent" to "center")), "picker-btn-text" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 24, "fontWeight" to "bold")), "picker-value" to _pS(_uM("fontSize" to 28, "color" to "#FFFFFF", "fontWeight" to "bold")), "quick-row" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "quick-chip" to _pS(_uM("paddingTop" to 7, "paddingRight" to 12, "paddingBottom" to 7, "paddingLeft" to 12, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "rgba(255,255,255,0.1)", "marginRight" to 8, "marginBottom" to 8)), "quick-chip-active" to _pS(_uM("backgroundColor" to "#DBC8ED")), "quick-chip-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12)), "quick-chip-text-active" to _pS(_uM("color" to "#161A33", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("close" to null, "confirm" to null)
        var props = _nP(_uM("visible" to _uM("type" to "Boolean", "required" to true, "default" to false), "title" to _uM("type" to "String", "required" to true, "default" to "调整数值"), "hint" to _uM("type" to "String", "required" to true, "default" to "请选择合适的数值"), "initialValue" to _uM("type" to "Number", "required" to true, "default" to 1), "minValue" to _uM("type" to "Number", "required" to true, "default" to 1), "maxValue" to _uM("type" to "Number", "required" to true, "default" to 100), "step" to _uM("type" to "Number", "required" to true, "default" to 1), "unit" to _uM("type" to "String", "required" to true, "default" to ""), "quickValues" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        )))
        var propsNeedCastKeys = _uA(
            "visible",
            "title",
            "hint",
            "initialValue",
            "minValue",
            "maxValue",
            "step",
            "unit",
            "quickValues"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
