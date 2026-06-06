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
open class GenComponentsRightSidebar : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var visible: Boolean by `$props`
    open var title: String by `$props`
    open var width: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsRightSidebar) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsRightSidebar
            val _cache = __ins.renderCache
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            fun gen_onMaskTap_fn(): Unit {
                emit("close")
            }
            val onMaskTap = ::gen_onMaskTap_fn
            fun gen_onCloseTap_fn(): Unit {
                emit("close")
            }
            val onCloseTap = ::gen_onCloseTap_fn
            return fun(): Any? {
                return if (isTrue(_ctx.visible)) {
                    _cE("view", _uM("key" to 0, "class" to _nC(_uA(
                        "sidebar-mask",
                        if (_ctx.visible) {
                            "sidebar-mask-active"
                        } else {
                            ""
                        }
                    )), "onClick" to onMaskTap), _uA(
                        _cE("view", _uM("class" to _nC(_uA(
                            "sidebar-card",
                            if (_ctx.visible) {
                                "sidebar-card-active"
                            } else {
                                ""
                            }
                        )), "onClick" to withModifiers(fun(){}, _uA(
                            "stop"
                        ))), _uA(
                            _cE("view", _uM("class" to "sidebar-header"), _uA(
                                _cE("text", _uM("class" to "sidebar-title"), _tD(_ctx.title), 1),
                                _cE("view", _uM("class" to "sidebar-close", "onClick" to onCloseTap), _uA(
                                    _cE("text", _uM("class" to "sidebar-close-text"), "×")
                                ))
                            )),
                            _cE("scroll-view", _uM("class" to "sidebar-body", "scroll-y" to "true"), _uA(
                                renderSlot(_ctx.`$slots`, "default")
                            )),
                            if (isTrue(_ctx.`$slots`["footer"])) {
                                _cE("view", _uM("key" to 0, "class" to "sidebar-footer"), _uA(
                                    renderSlot(_ctx.`$slots`, "footer")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                        ), 10, _uA(
                            "onClick"
                        ))
                    ), 2)
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
                return _uM("sidebar-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(5,6,20,0)", "zIndex" to 999, "flexDirection" to "row", "justifyContent" to "flex-end", "transitionProperty" to "backgroundColor", "transitionDuration" to "0.32s", "transitionTimingFunction" to "cubic-bezier(0.4,0,0.2,1)")), "sidebar-mask-active" to _pS(_uM("backgroundColor" to "rgba(5,6,20,0.62)")), "sidebar-card" to _pS(_uM("width" to "86%", "height" to "100%", "backgroundColor" to "rgba(29,25,62,0.98)", "backgroundImage" to "linear-gradient(180deg, rgba(48, 42, 94, 0.96) 0%, rgba(84, 65, 124, 0.86) 50%, rgba(198, 141, 192, 0.34) 100%)", "borderLeftWidth" to 1, "borderLeftColor" to "rgba(219,200,237,0.34)", "borderLeftStyle" to "solid", "flexDirection" to "column", "transform" to "translateX(100%)", "transitionProperty" to "transform", "transitionDuration" to "0.32s", "transitionTimingFunction" to "cubic-bezier(0.4,0,0.2,1)")), "sidebar-card-active" to _pS(_uM("transform" to "translateX(0%)")), "sidebar-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 18, "paddingRight" to 20, "paddingBottom" to 12, "paddingLeft" to 20, "borderBottomWidth" to 1, "borderBottomColor" to "rgba(219,200,237,0.18)", "borderBottomStyle" to "solid")), "sidebar-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#FFFFFF")), "sidebar-close" to _pS(_uM("width" to 32, "height" to 32, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "backgroundColor" to "rgba(255,255,255,0.12)", "alignItems" to "center", "justifyContent" to "center")), "sidebar-close-text" to _pS(_uM("fontSize" to 22, "color" to "rgba(255,255,255,0.78)", "fontWeight" to "bold")), "sidebar-body" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 16, "paddingRight" to 20, "paddingBottom" to 16, "paddingLeft" to 20)), "sidebar-footer" to _pS(_uM("paddingTop" to 12, "paddingRight" to 20, "paddingBottom" to 18, "paddingLeft" to 20, "borderTopWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "flexDirection" to "row")), "@TRANSITION" to _uM("sidebar-mask" to _uM("property" to "backgroundColor", "duration" to "0.32s", "timingFunction" to "cubic-bezier(0.4,0,0.2,1)"), "sidebar-card" to _uM("property" to "transform", "duration" to "0.32s", "timingFunction" to "cubic-bezier(0.4,0,0.2,1)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("close" to null)
        var props = _nP(_uM("visible" to _uM("type" to "Boolean", "required" to true, "default" to false), "title" to _uM("type" to "String", "required" to true), "width" to _uM("type" to "String", "required" to false, "default" to "86%")))
        var propsNeedCastKeys = _uA(
            "visible",
            "width"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
