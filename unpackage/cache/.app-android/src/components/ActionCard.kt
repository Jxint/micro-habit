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
    open var variant: String by `$props`
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
            fun gen_getCardClass_fn(): String {
                return if (props.variant === "recommend") {
                    "card-recommend"
                } else {
                    "card-library"
                }
            }
            val getCardClass = ::gen_getCardClass_fn
            fun gen_getIconWrapClass_fn(): String {
                return if (props.variant === "recommend") {
                    "icon-wrap-recommend"
                } else {
                    "icon-wrap-library"
                }
            }
            val getIconWrapClass = ::gen_getIconWrapClass_fn
            fun gen_getArrowClass_fn(): String {
                return if (props.variant === "recommend") {
                    "arrow-recommend"
                } else {
                    "arrow-library"
                }
            }
            val getArrowClass = ::gen_getArrowClass_fn
            val cardClass = computed<String>(getCardClass)
            val iconWrapClass = computed<String>(getIconWrapClass)
            val arrowClass = computed<String>(getArrowClass)
            fun gen_onTap_fn(): Unit {
                emit("actionTap", props.actionId)
            }
            val onTap = ::gen_onTap_fn
            return fun(): Any? {
                return _cE("view", _uM("class" to _nC(_uA(
                    "action-card",
                    unref(cardClass)
                )), "onClick" to onTap), _uA(
                    _cE("view", _uM("class" to _nC(_uA(
                        "action-icon-wrap",
                        unref(iconWrapClass)
                    ))), _uA(
                        _cE("text", _uM("class" to "action-icon"), _tD(iconEmoji))
                    ), 2),
                    _cE("view", _uM("class" to "action-info"), _uA(
                        _cE("text", _uM("class" to "action-name"), _tD(props.actionName), 1),
                        _cE("text", _uM("class" to "action-duration-text"), _tD(props.actionDuration) + "秒", 1)
                    )),
                    _cE("text", _uM("class" to _nC(_uA(
                        "action-arrow",
                        unref(arrowClass)
                    ))), "›", 2)
                ), 2)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("action-card" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "marginTop" to 4, "marginRight" to 16, "marginBottom" to 4, "marginLeft" to 16, "backgroundColor" to "#F8F9FA", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0, "boxShadow" to "0 1px 4px rgba(0, 0, 0, 0.03)")), "card-recommend" to _pS(_uM("backgroundColor" to "#F0F9F4", "borderLeftWidth" to 4, "borderLeftColor" to "#2ECC71", "borderLeftStyle" to "solid")), "card-library" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#ECF0F1", "borderRightColor" to "#ECF0F1", "borderBottomColor" to "#ECF0F1", "borderLeftColor" to "#ECF0F1", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "action-icon-wrap" to _pS(_uM("width" to 40, "height" to 40, "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "alignItems" to "center", "justifyContent" to "center", "marginRight" to 12)), "icon-wrap-recommend" to _pS(_uM("backgroundColor" to "#D5F5E3")), "icon-wrap-library" to _pS(_uM("backgroundColor" to "#F0F3F4")), "action-icon" to _pS(_uM("fontSize" to 22)), "action-info" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "action-name" to _pS(_uM("fontSize" to 15, "fontWeight" to "bold", "color" to "#2C3E50")), "action-duration-text" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6", "marginTop" to 2)), "action-arrow" to _pS(_uM("fontSize" to 22, "fontWeight" to "bold")), "arrow-recommend" to _pS(_uM("color" to "#2ECC71")), "arrow-library" to _pS(_uM("color" to "#BDC3C7")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("actionTap" to null)
        var props = _nP(_uM("actionId" to _uM("type" to "String", "required" to true), "actionName" to _uM("type" to "String", "required" to true), "actionDuration" to _uM("type" to "Number", "required" to true), "variant" to _uM("type" to "String", "required" to false, "default" to "library")))
        var propsNeedCastKeys = _uA(
            "variant"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
