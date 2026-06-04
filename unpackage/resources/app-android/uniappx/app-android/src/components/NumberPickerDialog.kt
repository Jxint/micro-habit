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
                return _uM("dialog-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 999)), "dialog-card" to _pS(_uM("width" to "80%", "maxWidth" to 360, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "flexDirection" to "column")), "dialog-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 8, "paddingLeft" to 0)), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50")), "dialog-close" to _pS(_uM("width" to 28, "height" to 28, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#F0F3F4", "alignItems" to "center", "justifyContent" to "center")), "dialog-close-text" to _pS(_uM("fontSize" to 18, "color" to "#7F8C8D", "fontWeight" to "bold")), "hint-block" to _pS(_uM("paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 16, "paddingLeft" to 4)), "hint-text" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D")), "picker-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 12, "paddingRight" to 8, "paddingBottom" to 12, "paddingLeft" to 8)), "picker-btn" to _pS(_uM("width" to 44, "height" to 44, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "#EBF5FB", "alignItems" to "center", "justifyContent" to "center")), "picker-btn-text" to _pS(_uM("fontSize" to 24, "color" to "#2980B9", "fontWeight" to "bold")), "picker-value-box" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end")), "picker-value" to _pS(_uM("fontSize" to 36, "fontWeight" to "bold", "color" to "#2C3E50")), "picker-unit" to _pS(_uM("fontSize" to 14, "color" to "#7F8C8D", "marginLeft" to 4)), "quick-row" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "paddingTop" to 8, "paddingRight" to 0, "paddingBottom" to 16, "paddingLeft" to 0)), "quick-chip" to _pS(_uM("paddingTop" to 6, "paddingRight" to 12, "paddingBottom" to 6, "paddingLeft" to 12, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#F0F3F4", "marginRight" to 8, "marginBottom" to 8)), "quick-chip-active" to _pS(_uM("backgroundColor" to "#3498DB")), "quick-chip-text" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D")), "quick-chip-text-active" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold")), "action-row" to _pS(_uM("flexDirection" to "row", "marginTop" to 4)), "action-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center", "marginTop" to 0, "marginRight" to 4, "marginBottom" to 0, "marginLeft" to 4)), "action-cancel" to _pS(_uM("backgroundColor" to "#F0F3F4")), "action-confirm" to _pS(_uM("backgroundColor" to "#3498DB")), "action-cancel-text" to _pS(_uM("fontSize" to 14, "color" to "#7F8C8D", "fontWeight" to "bold")), "action-confirm-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF", "fontWeight" to "bold")))
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
