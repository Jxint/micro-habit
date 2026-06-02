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
open class GenComponentsActionCard : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var actionId: String by `$props`
    open var actionName: String by `$props`
    open var actionDuration: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsActionCard) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsActionCard
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            fun gen_iconEmoji_fn(): String {
                if (props.actionId === "eye_blink") {
                    return "👁"
                }
                if (props.actionId === "eye_rotate") {
                    return "👀"
                }
                if (props.actionId === "neck_rotate") {
                    return "🔄"
                }
                if (props.actionId === "shoulder_roll") {
                    return "💪"
                }
                if (props.actionId === "neck_stretch") {
                    return "🧘"
                }
                if (props.actionId === "core_tighten") {
                    return "🔥"
                }
                if (props.actionId === "heel_raise") {
                    return "🦶"
                }
                if (props.actionId === "deep_breath") {
                    return "🌬"
                }
                if (props.actionId === "far_gaze") {
                    return "🏔"
                }
                return "✓"
            }
            val iconEmoji = ::gen_iconEmoji_fn
            fun gen_onTap_fn(): Unit {
                emit("actionTap", props.actionId)
            }
            val onTap = ::gen_onTap_fn
            return fun(): Any? {
                return _cE("view", _uM("class" to "action-card", "onClick" to onTap), _uA(
                    _cE("text", _uM("class" to "action-icon"), _tD(iconEmoji)),
                    _cE("view", _uM("class" to "action-info"), _uA(
                        _cE("text", _uM("class" to "action-name"), _tD(props.actionName), 1),
                        _cE("text", _uM("class" to "action-duration-text"), _tD(props.actionDuration) + "秒", 1)
                    )),
                    _cE("text", _uM("class" to "action-arrow"), "›")
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
                return _uM("action-card" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "marginTop" to 4, "marginRight" to 16, "marginBottom" to 4, "marginLeft" to 16, "backgroundColor" to "#F8F9FA", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#E8E8E8", "borderRightColor" to "#E8E8E8", "borderBottomColor" to "#E8E8E8", "borderLeftColor" to "#E8E8E8", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "action-icon" to _pS(_uM("fontSize" to 24, "width" to 36, "textAlign" to "center")), "action-info" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "marginLeft" to 8)), "action-name" to _pS(_uM("fontSize" to 15, "fontWeight" to "bold", "color" to "#2C3E50")), "action-duration-text" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6", "marginTop" to 2)), "action-arrow" to _pS(_uM("fontSize" to 20, "color" to "#BDC3C7")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("actionTap" to null)
        var props = _nP(_uM("actionId" to _uM("type" to "String", "required" to true), "actionName" to _uM("type" to "String", "required" to true), "actionDuration" to _uM("type" to "Number", "required" to true)))
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
