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
open class GenComponentsFeedbackDialog : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var visible: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsFeedbackDialog) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsFeedbackDialog
            val _cache = __ins.renderCache
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val reasons = _uA(
                "正在忙",
                "动作太难",
                "时机不对",
                "不想做",
                "其他"
            ) as UTSArray<String>
            val selectedIndex = ref(-1)
            val showCustom = ref(false)
            val customText = ref("")
            fun gen_selectReason_fn(index: Number): Unit {
                selectedIndex.value = index
                showCustom.value = index === (reasons.length - 1)
            }
            val selectReason = ::gen_selectReason_fn
            fun gen_onCancel_fn(): Unit {
                selectedIndex.value = -1
                showCustom.value = false
                customText.value = ""
                emit("cancel")
            }
            val onCancel = ::gen_onCancel_fn
            fun gen_onConfirm_fn(): Unit {
                var reason = ""
                if (selectedIndex.value >= 0) {
                    if (selectedIndex.value === reasons.length - 1) {
                        val ct = customText.value
                        reason = if (ct.length > 0) {
                            ct
                        } else {
                            "其他"
                        }
                    } else {
                        reason = reasons[selectedIndex.value]
                    }
                }
                selectedIndex.value = -1
                showCustom.value = false
                customText.value = ""
                emit("confirm", reason)
            }
            val onConfirm = ::gen_onConfirm_fn
            return fun(): Any? {
                return if (isTrue(_ctx.visible)) {
                    _cE("view", _uM("key" to 0, "class" to "dialog-overlay"), _uA(
                        _cE("view", _uM("class" to "dialog-content"), _uA(
                            _cE("text", _uM("class" to "dialog-title"), "跳过原因"),
                            _cE("view", _uM("class" to "reason-list"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(reasons, fun(item, index, __index, _cached): Any {
                                    return _cE("view", _uM("key" to index, "class" to _nC(_uA(
                                        "reason-item",
                                        _uM("selected" to (unref(selectedIndex) === index))
                                    )), "onClick" to fun(){
                                        selectReason(index)
                                    }), _uA(
                                        _cE("text", _uM("class" to "reason-text"), _tD(item), 1)
                                    ), 10, _uA(
                                        "onClick"
                                    ))
                                }), 64)
                            )),
                            if (isTrue(unref(showCustom))) {
                                _cE("input", _uM("key" to 0, "class" to "custom-input", "modelValue" to unref(customText), "onInput" to fun(`$event`: UniInputEvent){
                                    trySetRefValue(customText, `$event`.detail.value)
                                }, "placeholder" to "输入其他原因..."), null, 40, _uA(
                                    "modelValue"
                                ))
                            } else {
                                _cC("v-if", true)
                            },
                            _cE("view", _uM("class" to "dialog-actions"), _uA(
                                _cE("button", _uM("class" to "cancel-btn", "onClick" to onCancel), "取消"),
                                _cE("button", _uM("class" to "confirm-btn", "onClick" to onConfirm), "确定")
                            ))
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
                return _uM("dialog-overlay" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "justifyContent" to "center", "alignItems" to "center", "backgroundColor" to "rgba(0,0,0,0.4)", "zIndex" to 999)), "dialog-content" to _pS(_uM("width" to 280, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "flexDirection" to "column")), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50", "textAlign" to "center", "marginBottom" to 16)), "reason-list" to _pS(_uM("flexDirection" to "column")), "reason-item" to _uM("" to _uM("paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "marginTop" to 3, "marginRight" to 0, "marginBottom" to 3, "marginLeft" to 0, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F8F9FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#E8E8E8", "borderRightColor" to "#E8E8E8", "borderBottomColor" to "#E8E8E8", "borderLeftColor" to "#E8E8E8", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid"), ".selected" to _uM("backgroundColor" to "#EBF5FB", "borderTopColor" to "#3498DB", "borderRightColor" to "#3498DB", "borderBottomColor" to "#3498DB", "borderLeftColor" to "#3498DB")), "reason-text" to _pS(_uM("fontSize" to 14, "color" to "#2C3E50")), "custom-input" to _pS(_uM("borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#E0E0E0", "borderRightColor" to "#E0E0E0", "borderBottomColor" to "#E0E0E0", "borderLeftColor" to "#E0E0E0", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 8, "paddingRight" to 12, "paddingBottom" to 8, "paddingLeft" to 12, "fontSize" to 14, "marginTop" to 8)), "dialog-actions" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "marginTop" to 16)), "cancel-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "marginRight" to 8, "paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 10, "paddingLeft" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#ECF0F1", "color" to "#7F8C8D", "fontSize" to 14, "textAlign" to "center")), "confirm-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "marginLeft" to 8, "paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 10, "paddingLeft" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#3498DB", "color" to "#FFFFFF", "fontSize" to 14, "textAlign" to "center")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("confirm" to null, "cancel" to null)
        var props = _nP(_uM("visible" to _uM("type" to "Boolean", "required" to true)))
        var propsNeedCastKeys = _uA(
            "visible"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
