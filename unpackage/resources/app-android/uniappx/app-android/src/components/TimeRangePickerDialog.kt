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
open class GenComponentsTimeRangePickerDialog : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var visible: Boolean by `$props`
    open var title: String by `$props`
    open var hint: String by `$props`
    open var initialStartHour: Number by `$props`
    open var initialEndHour: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsTimeRangePickerDialog) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsTimeRangePickerDialog
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val startHour = ref<Number>(props.initialStartHour)
            val endHour = ref<Number>(props.initialEndHour)
            fun gen_pad2_fn(n: Number): String {
                if (n < 10) {
                    return "0" + n
                }
                return "" + n
            }
            val pad2 = ::gen_pad2_fn
            fun gen_clampHour_fn(v: Number): Number {
                if (v < 0) {
                    return 23
                }
                if (v > 23) {
                    return 0
                }
                return v
            }
            val clampHour = ::gen_clampHour_fn
            fun gen_onStartMinus_fn(): Unit {
                startHour.value = clampHour(startHour.value - 1)
            }
            val onStartMinus = ::gen_onStartMinus_fn
            fun gen_onStartPlus_fn(): Unit {
                startHour.value = clampHour(startHour.value + 1)
            }
            val onStartPlus = ::gen_onStartPlus_fn
            fun gen_onEndMinus_fn(): Unit {
                endHour.value = clampHour(endHour.value - 1)
            }
            val onEndMinus = ::gen_onEndMinus_fn
            fun gen_onEndPlus_fn(): Unit {
                endHour.value = clampHour(endHour.value + 1)
            }
            val onEndPlus = ::gen_onEndPlus_fn
            fun gen_close_fn(): Unit {
                emit("close")
            }
            val close = ::gen_close_fn
            fun gen_confirm_fn(): Unit {
                emit("confirm", startHour.value, endHour.value)
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
                    startHour.value = clampHour(props.initialStartHour)
                    endHour.value = clampHour(props.initialEndHour)
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
                            _cE("view", _uM("class" to "range-block"), _uA(
                                _cE("view", _uM("class" to "range-cell"), _uA(
                                    _cE("text", _uM("class" to "range-label"), "开始"),
                                    _cE("view", _uM("class" to "time-picker"), _uA(
                                        _cE("view", _uM("class" to "time-step", "onClick" to onStartMinus), _uA(
                                            _cE("text", _uM("class" to "time-step-text"), "−")
                                        )),
                                        _cE("view", _uM("class" to "time-value-box"), _uA(
                                            _cE("text", _uM("class" to "time-value"), _tD(pad2(unref(startHour))), 1),
                                            _cE("text", _uM("class" to "time-colon"), ":"),
                                            _cE("text", _uM("class" to "time-value"), _tD("00"))
                                        )),
                                        _cE("view", _uM("class" to "time-step", "onClick" to onStartPlus), _uA(
                                            _cE("text", _uM("class" to "time-step-text"), "+")
                                        ))
                                    ))
                                )),
                                _cE("view", _uM("class" to "range-cell"), _uA(
                                    _cE("text", _uM("class" to "range-label"), "结束"),
                                    _cE("view", _uM("class" to "time-picker"), _uA(
                                        _cE("view", _uM("class" to "time-step", "onClick" to onEndMinus), _uA(
                                            _cE("text", _uM("class" to "time-step-text"), "−")
                                        )),
                                        _cE("view", _uM("class" to "time-value-box"), _uA(
                                            _cE("text", _uM("class" to "time-value"), _tD(pad2(unref(endHour))), 1),
                                            _cE("text", _uM("class" to "time-colon"), ":"),
                                            _cE("text", _uM("class" to "time-value"), _tD("00"))
                                        )),
                                        _cE("view", _uM("class" to "time-step", "onClick" to onEndPlus), _uA(
                                            _cE("text", _uM("class" to "time-step-text"), "+")
                                        ))
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "preview-block"), _uA(
                                _cE("text", _uM("class" to "preview-text"), "免打扰时段：" + _tD(pad2(unref(startHour))) + ":00 — " + _tD(pad2(unref(endHour))) + ":00", 1)
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
                return _uM("dialog-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 999)), "dialog-card" to _pS(_uM("width" to "80%", "maxWidth" to 360, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "flexDirection" to "column")), "dialog-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 8, "paddingLeft" to 0)), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50")), "dialog-close" to _pS(_uM("width" to 28, "height" to 28, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#F0F3F4", "alignItems" to "center", "justifyContent" to "center")), "dialog-close-text" to _pS(_uM("fontSize" to 18, "color" to "#7F8C8D", "fontWeight" to "bold")), "hint-block" to _pS(_uM("paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 12, "paddingLeft" to 4)), "hint-text" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D")), "range-block" to _pS(_uM("paddingTop" to 4, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0)), "range-cell" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 10, "paddingRight" to 4, "paddingBottom" to 10, "paddingLeft" to 4, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "range-label" to _pS(_uM("fontSize" to 14, "color" to "#2C3E50")), "time-picker" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "time-step" to _pS(_uM("width" to 32, "height" to 32, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "backgroundColor" to "#EBF5FB", "alignItems" to "center", "justifyContent" to "center")), "time-step-text" to _pS(_uM("fontSize" to 18, "color" to "#2980B9", "fontWeight" to "bold")), "time-value-box" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end", "marginTop" to 0, "marginRight" to 12, "marginBottom" to 0, "marginLeft" to 12, "minWidth" to 80, "justifyContent" to "center")), "time-value" to _pS(_uM("fontSize" to 22, "fontWeight" to "bold", "color" to "#2C3E50")), "time-colon" to _pS(_uM("fontSize" to 22, "color" to "#2C3E50", "marginTop" to 0, "marginRight" to 2, "marginBottom" to 0, "marginLeft" to 2)), "preview-block" to _pS(_uM("paddingTop" to 16, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4)), "preview-text" to _pS(_uM("fontSize" to 13, "color" to "#3498DB", "fontWeight" to "bold")), "action-row" to _pS(_uM("flexDirection" to "row", "marginTop" to 4)), "action-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center", "marginTop" to 0, "marginRight" to 4, "marginBottom" to 0, "marginLeft" to 4)), "action-cancel" to _pS(_uM("backgroundColor" to "#F0F3F4")), "action-confirm" to _pS(_uM("backgroundColor" to "#3498DB")), "action-cancel-text" to _pS(_uM("fontSize" to 14, "color" to "#7F8C8D", "fontWeight" to "bold")), "action-confirm-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("close" to null, "confirm" to null)
        var props = _nP(_uM("visible" to _uM("type" to "Boolean", "required" to true, "default" to false), "title" to _uM("type" to "String", "required" to true, "default" to "免打扰时段"), "hint" to _uM("type" to "String", "required" to true, "default" to "设置免打扰的起止小时"), "initialStartHour" to _uM("type" to "Number", "required" to true, "default" to 22), "initialEndHour" to _uM("type" to "Number", "required" to true, "default" to 7)))
        var propsNeedCastKeys = _uA(
            "visible",
            "title",
            "hint",
            "initialStartHour",
            "initialEndHour"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
