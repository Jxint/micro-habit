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
open class GenComponentsStatusIndicator : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var title: String by `$props`
    open var percent: Number by `$props`
    open var status: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsStatusIndicator) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsStatusIndicator
            val _cache = __ins.renderCache
            val props = __props
            fun gen_getDisplayPercent_fn(): Number {
                if (props.percent < 0) {
                    return 0
                }
                return Math.min(props.percent, 100)
            }
            val getDisplayPercent = ::gen_getDisplayPercent_fn
            fun gen_getBarColor_fn(): String {
                if (props.status === "no_data") {
                    return "#D5D8DC"
                }
                if (props.status === "good") {
                    return "#2ECC71"
                }
                if (props.status === "normal") {
                    return "#F39C12"
                }
                return "#E74C3C"
            }
            val getBarColor = ::gen_getBarColor_fn
            fun gen_getTextColor_fn(): String {
                if (props.status === "no_data") {
                    return "#95A5A6"
                }
                if (props.status === "good") {
                    return "#27AE60"
                }
                if (props.status === "normal") {
                    return "#E67E22"
                }
                return "#C0392B"
            }
            val getTextColor = ::gen_getTextColor_fn
            fun gen_getStatusText_fn(): String {
                if (props.status === "no_data") {
                    return "暂无数据"
                }
                if (props.status === "good") {
                    return "良好"
                }
                if (props.status === "normal") {
                    return "一般"
                }
                return "需关注"
            }
            val getStatusText = ::gen_getStatusText_fn
            val displayPercent = computed<Number>(getDisplayPercent)
            val barColor = computed<String>(getBarColor)
            val textColor = computed<String>(getTextColor)
            val statusText = computed<String>(getStatusText)
            return fun(): Any? {
                return _cE("view", _uM("class" to "status-card"), _uA(
                    _cE("view", _uM("class" to "status-header"), _uA(
                        _cE("text", _uM("class" to "status-title"), _tD(_ctx.title), 1),
                        _cE("text", _uM("class" to "status-label", "style" to _nS(_uM("color" to unref(textColor)))), _tD(unref(statusText)), 5)
                    )),
                    _cE("view", _uM("class" to "progress-track"), _uA(
                        _cE("view", _uM("class" to "progress-fill", "style" to _nS(_uM("width" to (unref(displayPercent) + "%"), "backgroundColor" to unref(barColor)))), null, 4)
                    ))
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
                return _uM("status-card" to _pS(_uM("flexDirection" to "column", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "marginTop" to 6, "marginRight" to 16, "marginBottom" to 6, "marginLeft" to 16, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.04)")), "status-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 8)), "status-title" to _pS(_uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#2C3E50")), "status-label" to _pS(_uM("fontSize" to 12, "fontWeight" to "bold")), "progress-track" to _pS(_uM("height" to 8, "backgroundColor" to "#F0F3F4", "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "overflow" to "hidden")), "progress-fill" to _pS(_uM("height" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("title" to _uM("type" to "String", "required" to true), "percent" to _uM("type" to "Number", "required" to true), "status" to _uM("type" to "String", "required" to true)))
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
