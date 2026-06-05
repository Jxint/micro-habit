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
open class GenComponentsEncourageToast : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var visible: Boolean by `$props`
    open var text: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsEncourageToast) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsEncourageToast
            val _cache = __ins.renderCache
            return fun(): Any? {
                return if (isTrue(_ctx.visible)) {
                    _cE("view", _uM("key" to 0, "class" to "toast-overlay"), _uA(
                        _cE("view", _uM("class" to "toast-content"), _uA(
                            _cE("text", _uM("class" to "toast-check"), "✓"),
                            _cE("text", _uM("class" to "toast-text"), _tD(_ctx.text), 1)
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
                return _uM("toast-overlay" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "justifyContent" to "center", "alignItems" to "center", "backgroundColor" to "rgba(0,0,0,0.46)", "zIndex" to 999)), "toast-content" to _pS(_uM("width" to 210, "height" to 150, "flexDirection" to "column", "justifyContent" to "center", "alignItems" to "center", "backgroundColor" to "rgba(29,25,62,0.94)", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.22)", "borderRightColor" to "rgba(219,200,237,0.22)", "borderBottomColor" to "rgba(219,200,237,0.22)", "borderLeftColor" to "rgba(219,200,237,0.22)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "toast-check" to _pS(_uM("fontSize" to 40, "color" to "#DBC8ED", "marginBottom" to 8)), "toast-text" to _pS(_uM("fontSize" to 16, "color" to "#FFFFFF", "textAlign" to "center", "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("visible" to _uM("type" to "Boolean", "required" to true), "text" to _uM("type" to "String", "required" to true)))
        var propsNeedCastKeys = _uA(
            "visible"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
