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
                return _uM("dialog-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.58)", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 999)), "dialog-card" to _pS(_uM("width" to "90%", "maxWidth" to 420, "maxHeight" to 560, "backgroundColor" to "rgba(29,25,62,0.96)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.94) 0%, rgba(84, 65, 124, 0.82) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "flexDirection" to "column", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.2)", "borderRightColor" to "rgba(219,200,237,0.2)", "borderBottomColor" to "rgba(219,200,237,0.2)", "borderLeftColor" to "rgba(219,200,237,0.2)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "dialog-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 12, "paddingLeft" to 4)), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#FFFFFF")), "dialog-close" to _pS(_uM("width" to 30, "height" to 30, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "rgba(255,255,255,0.1)", "alignItems" to "center", "justifyContent" to "center")), "dialog-close-text" to _pS(_uM("fontSize" to 18, "color" to "rgba(255,255,255,0.72)", "fontWeight" to "bold")), "hint-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "marginBottom" to 14)), "summary-row" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "ai-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "preview-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "marginTop" to 4)), "picker-value-box" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "time-value-box" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "height" to 56, "minWidth" to 140, "marginTop" to 0, "marginRight" to 10, "marginBottom" to 0, "marginLeft" to 10, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "history-rule" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "achv-progress-wrap" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "hint-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "summary-stat-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "legend-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "legend-percent" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "legend-minutes" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-hint" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "ai-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "ai-placeholder-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "preview-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "textAlign" to "center")), "range-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "marginBottom" to 8)), "picker-unit" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "achv-desc" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "achv-date" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "achv-history" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "action-row" to _pS(_uM("flexDirection" to "row", "marginTop" to 16)), "dialog-buttons" to _pS(_uM("flexDirection" to "row", "marginTop" to 16)), "unlock-buttons" to _pS(_uM("flexDirection" to "row", "marginTop" to 16)), "action-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "unlock-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "action-cancel" to _pS(_uM("marginRight" to 8, "backgroundColor" to "rgba(255,255,255,0.1)")), "btn-secondary" to _pS(_uM("marginRight" to 8, "backgroundColor" to "rgba(255,255,255,0.1)")), "action-confirm" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "btn-primary" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "unlock-btn-primary" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "action-cancel-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "btn-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "unlock-btn-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "action-confirm-text" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")), "unlock-btn-text-primary" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")), "range-block" to _pS(_uM("flexDirection" to "column")), "range-cell" to _pS(_uM("marginBottom" to 14)), "time-picker" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "time-step" to _pS(_uM("width" to 42, "height" to 42, "borderTopLeftRadius" to 21, "borderTopRightRadius" to 21, "borderBottomRightRadius" to 21, "borderBottomLeftRadius" to 21, "backgroundColor" to "rgba(255,255,255,0.1)", "alignItems" to "center", "justifyContent" to "center")), "time-step-text" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 22, "fontWeight" to "bold")), "time-value" to _pS(_uM("fontSize" to 24, "color" to "#FFFFFF", "fontWeight" to "bold")), "time-colon" to _pS(_uM("fontSize" to 24, "color" to "#FFFFFF", "fontWeight" to "bold", "marginTop" to 0, "marginRight" to 4, "marginBottom" to 0, "marginLeft" to 4)))
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
