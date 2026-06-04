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
open class GenComponentsCountdownBar : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var total: Number by `$props`
    open var remaining: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsCountdownBar) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsCountdownBar
            val _cache = __ins.renderCache
            val props = __props
            fun gen_getProgressPercent_fn(): Number {
                if (props.total <= 0) {
                    return 0
                }
                return ((props.total - props.remaining) / props.total) * 100
            }
            val getProgressPercent = ::gen_getProgressPercent_fn
            fun gen_getRemainingText_fn(): String {
                val secs = Math.ceil(props.remaining / 1000)
                return secs + "s / " + Math.ceil(props.total / 1000) + "s"
            }
            val getRemainingText = ::gen_getRemainingText_fn
            val progressPercent = computed<Number>(getProgressPercent)
            val remainingText = computed<String>(getRemainingText)
            return fun(): Any? {
                return _cE("view", _uM("class" to "countdown-container"), _uA(
                    _cE("view", _uM("class" to "countdown-track"), _uA(
                        _cE("view", _uM("class" to "countdown-fill", "style" to _nS(_uM("width" to (unref(progressPercent) + "%")))), null, 4)
                    )),
                    _cE("text", _uM("class" to "countdown-text"), _tD(unref(remainingText)), 1)
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("countdown-container" to _pS(_uM("flexDirection" to "column", "alignItems" to "center", "paddingTop" to 8, "paddingRight" to 16, "paddingBottom" to 8, "paddingLeft" to 16)), "countdown-track" to _pS(_uM("width" to "100%", "height" to 10, "backgroundColor" to "#ECF0F1", "borderTopLeftRadius" to 5, "borderTopRightRadius" to 5, "borderBottomRightRadius" to 5, "borderBottomLeftRadius" to 5, "overflow" to "hidden")), "countdown-fill" to _pS(_uM("height" to 10, "backgroundColor" to "#3498DB", "borderTopLeftRadius" to 5, "borderTopRightRadius" to 5, "borderBottomRightRadius" to 5, "borderBottomLeftRadius" to 5)), "countdown-text" to _pS(_uM("fontSize" to 14, "color" to "#7F8C8D", "marginTop" to 6)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("total" to _uM("type" to "Number", "required" to true), "remaining" to _uM("type" to "Number", "required" to true)))
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
