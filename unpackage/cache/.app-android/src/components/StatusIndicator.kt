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
    open var variant: String by `$props`
    open var bgImage: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsStatusIndicator) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsStatusIndicator
            val _cache = __ins.renderCache
            val props = __props
            var animActive: Boolean = false
            var orbEmoji: String = "·"
            var displayPercent: Number = 0
            var ringColor: String = "rgba(219, 200, 237, 0.34)"
            fun gen_clamp_fn(): Unit {
                if (props.percent < 0) {
                    displayPercent = 0
                    return
                }
                if (props.percent > 100) {
                    displayPercent = 100
                    return
                }
                displayPercent = Math.floor(props.percent)
            }
            val clamp = ::gen_clamp_fn
            fun gen_getBarColor_fn(): String {
                if (props.status === "no_data") {
                    return "rgba(255, 255, 255, 0.34)"
                }
                if (props.status === "good") {
                    return "#7EF0C2"
                }
                if (props.status === "normal") {
                    return "#DBC8ED"
                }
                return "#FF8FA6"
            }
            val getBarColor = ::gen_getBarColor_fn
            fun gen_getRingColor_fn(): String {
                if (props.status === "no_data") {
                    return "rgba(255, 255, 255, 0.18)"
                }
                if (props.status === "good") {
                    return "#7EF0C2"
                }
                if (props.status === "normal") {
                    return "#DBC8ED"
                }
                return "#FF8FA6"
            }
            val getRingColor = ::gen_getRingColor_fn
            fun gen_getTextColor_fn(): String {
                if (props.status === "no_data") {
                    return "rgba(255, 255, 255, 0.58)"
                }
                if (props.status === "good") {
                    return "#7EF0C2"
                }
                if (props.status === "normal") {
                    return "#DBC8ED"
                }
                return "#FF8FA6"
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
            fun gen_resolveEmoji_fn(): Unit {
                if (props.title == "护眼") {
                    orbEmoji = "◐"
                } else if (props.title == "体态") {
                    orbEmoji = "◌"
                } else if (props.title == "活力") {
                    orbEmoji = "⌁"
                } else {
                    orbEmoji = "·"
                }
            }
            val resolveEmoji = ::gen_resolveEmoji_fn
            fun gen_startAnim_fn(): Unit {
                setTimeout(fun(): Unit {
                    animActive = true
                }
                , 60)
            }
            val startAnim = ::gen_startAnim_fn
            val barColor = computed<String>(getBarColor)
            val textColor = computed<String>(getTextColor)
            val statusText = computed<String>(getStatusText)
            onMounted(fun(): Unit {
                clamp()
                resolveEmoji()
                ringColor = getRingColor()
                if (props.variant == "orb") {
                    startAnim()
                }
            }
            )
            watch(fun(): Number {
                return props.percent
            }
            , fun(_v: Number): Unit {
                clamp()
                ringColor = getRingColor()
                if (props.variant == "orb") {
                    animActive = false
                    startAnim()
                }
            }
            )
            return fun(): Any? {
                return if (_ctx.variant == "bar") {
                    _cE("view", _uM("key" to 0, "class" to "status-card"), _uA(
                        _cE("view", _uM("class" to "status-header"), _uA(
                            _cE("text", _uM("class" to "status-title"), _tD(_ctx.title), 1),
                            _cE("text", _uM("class" to "status-label", "style" to _nS(_uM("color" to unref(textColor)))), _tD(unref(statusText)), 5)
                        )),
                        _cE("view", _uM("class" to "progress-track"), _uA(
                            _cE("view", _uM("class" to "progress-fill", "style" to _nS(_uM("width" to (unref(displayPercent) + "%"), "backgroundColor" to unref(barColor)))), null, 4)
                        ))
                    ))
                } else {
                    _cE("view", _uM("key" to 1, "class" to "orb-card"), _uA(
                        _cE("view", _uM("class" to "orb-outer"), _uA(
                            if (_ctx.bgImage.length > 0) {
                                _cE("image", _uM("key" to 0, "class" to "orb-bg", "src" to _ctx.bgImage, "mode" to "aspectFill"), null, 8, _uA(
                                    "src"
                                ))
                            } else {
                                _cE("view", _uM("key" to 1, "class" to "orb-bg-emoji"), _uA(
                                    _cE("text", _uM("class" to "orb-bg-emoji-text"), _tD(unref(orbEmoji)), 1)
                                ))
                            }
                            ,
                            _cE("view", _uM("class" to "orb-ring", "style" to _nS(_uM("borderColor" to unref(ringColor)))), _uA(
                                _cE("view", _uM("class" to "orb-ring-fill", "style" to _nS(_uM("height" to if (unref(animActive)) {
                                    unref(displayPercent) + "%"
                                } else {
                                    "0%"
                                }
                                , "backgroundColor" to unref(barColor)))), null, 4)
                            ), 4),
                            if (isTrue(unref(animActive) && unref(displayPercent) >= 50)) {
                                _cE("view", _uM("key" to 2, "class" to "orb-shine"))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE("view", _uM("class" to "orb-text"), _uA(
                                _cE("text", _uM("class" to "orb-percent"), _tD(unref(displayPercent)), 1),
                                _cE("text", _uM("class" to "orb-percent-sign"), "%")
                            ))
                        )),
                        _cE("view", _uM("class" to "orb-footer"), _uA(
                            _cE("text", _uM("class" to "orb-title"), _tD(_ctx.title), 1),
                            _cE("text", _uM("class" to "orb-status", "style" to _nS(_uM("color" to unref(textColor)))), _tD(unref(statusText)), 5)
                        ))
                    ))
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
                return _uM("status-card" to _pS(_uM("flexDirection" to "column", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "marginTop" to 7, "marginRight" to 12, "marginBottom" to 7, "marginLeft" to 12, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.16)", "borderRightColor" to "rgba(219,200,237,0.16)", "borderBottomColor" to "rgba(219,200,237,0.16)", "borderLeftColor" to "rgba(219,200,237,0.16)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "status-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 8)), "status-title" to _pS(_uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#FFFFFF")), "status-label" to _pS(_uM("fontSize" to 12, "fontWeight" to "bold")), "progress-track" to _pS(_uM("height" to 8, "backgroundColor" to "rgba(255,255,255,0.12)", "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "overflow" to "hidden")), "progress-fill" to _pS(_uM("height" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4)), "orb-card" to _pS(_uM("flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to 10, "paddingRight" to 6, "paddingBottom" to 10, "paddingLeft" to 6)), "orb-outer" to _pS(_uM("width" to 88, "height" to 88, "borderTopLeftRadius" to 44, "borderTopRightRadius" to 44, "borderBottomRightRadius" to 44, "borderBottomLeftRadius" to 44, "backgroundColor" to "rgba(29,25,62,0.6)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.34)", "borderRightColor" to "rgba(219,200,237,0.34)", "borderBottomColor" to "rgba(219,200,237,0.34)", "borderLeftColor" to "rgba(219,200,237,0.34)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "alignItems" to "center", "justifyContent" to "center", "overflow" to "hidden", "position" to "relative")), "orb-bg" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "right" to 0, "bottom" to 0, "opacity" to 0.45)), "orb-bg-emoji" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "right" to 0, "bottom" to 0, "alignItems" to "center", "justifyContent" to "center")), "orb-bg-emoji-text" to _pS(_uM("fontSize" to 30, "color" to "rgba(255,255,255,0.32)")), "orb-ring" to _pS(_uM("position" to "absolute", "left" to 6, "top" to 6, "right" to 6, "bottom" to 6, "borderTopLeftRadius" to 38, "borderTopRightRadius" to 38, "borderBottomRightRadius" to 38, "borderBottomLeftRadius" to 38, "borderTopWidth" to 4, "borderRightWidth" to 4, "borderBottomWidth" to 4, "borderLeftWidth" to 4, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "overflow" to "hidden", "alignItems" to "flex-end", "justifyContent" to "center")), "orb-ring-fill" to _pS(_uM("width" to "100%", "transitionProperty" to "height", "transitionDuration" to "0.9s", "transitionTimingFunction" to "cubic-bezier(0.4,0,0.2,1)", "opacity" to 0.78)), "orb-shine" to _pS(_uM("position" to "absolute", "width" to 12, "height" to 12, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "backgroundColor" to "rgba(255,255,255,0.4)", "right" to 18, "top" to 12)), "orb-text" to _pS(_uM("position" to "absolute", "alignItems" to "center", "flexDirection" to "row")), "orb-percent" to _pS(_uM("fontSize" to 18, "color" to "#FFFFFF", "fontWeight" to "bold")), "orb-percent-sign" to _pS(_uM("fontSize" to 10, "color" to "rgba(255,255,255,0.7)", "marginLeft" to 1, "marginTop" to 4)), "orb-footer" to _pS(_uM("flexDirection" to "column", "alignItems" to "center", "marginTop" to 6)), "orb-title" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "fontWeight" to "bold")), "orb-status" to _pS(_uM("fontSize" to 10, "marginTop" to 2)), "@TRANSITION" to _uM("orb-ring-fill" to _uM("property" to "height", "duration" to "0.9s", "timingFunction" to "cubic-bezier(0.4,0,0.2,1)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("title" to _uM("type" to "String", "required" to true), "percent" to _uM("type" to "Number", "required" to true), "status" to _uM("type" to "String", "required" to true), "variant" to _uM("type" to "String", "required" to false, "default" to "bar"), "bgImage" to _uM("type" to "String", "required" to false, "default" to "")))
        var propsNeedCastKeys = _uA(
            "variant",
            "bgImage"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
