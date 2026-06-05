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
open class GenComponentsAchievementUnlockDialog : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var visible: Boolean by `$props`
    open var achievementIcon: String by `$props`
    open var achievementName: String by `$props`
    open var achievementDescription: String by `$props`
    open var rewardText: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsAchievementUnlockDialog) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsAchievementUnlockDialog
            val _cache = __ins.renderCache
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            fun gen_onDismiss_fn(): Unit {
                emit("close")
            }
            val onDismiss = ::gen_onDismiss_fn
            fun gen_onMaskTap_fn(): Unit {
                emit("close")
            }
            val onMaskTap = ::gen_onMaskTap_fn
            fun gen_onViewDetail_fn(): Unit {
                emit("viewDetail")
            }
            val onViewDetail = ::gen_onViewDetail_fn
            return fun(): Any? {
                return if (isTrue(_ctx.visible)) {
                    _cE("view", _uM("key" to 0, "class" to "unlock-mask", "onClick" to onMaskTap), _uA(
                        _cE("view", _uM("class" to "unlock-card", "onClick" to withModifiers(fun(){}, _uA(
                            "stop"
                        ))), _uA(
                            _cE("text", _uM("class" to "unlock-fire"), "🎉"),
                            _cE("text", _uM("class" to "unlock-title"), "解锁成就"),
                            _cE("view", _uM("class" to "unlock-badge"), _uA(
                                _cE("text", _uM("class" to "unlock-icon"), _tD(_ctx.achievementIcon), 1)
                            )),
                            _cE("text", _uM("class" to "unlock-name"), _tD(_ctx.achievementName), 1),
                            _cE("text", _uM("class" to "unlock-desc"), _tD(_ctx.achievementDescription), 1),
                            _cE("text", _uM("class" to "unlock-reward"), "🏅 奖励：" + _tD(_ctx.rewardText), 1),
                            _cE("view", _uM("class" to "unlock-buttons"), _uA(
                                _cE("view", _uM("class" to "unlock-btn", "onClick" to onDismiss), _uA(
                                    _cE("text", _uM("class" to "unlock-btn-text"), "知道了")
                                )),
                                _cE("view", _uM("class" to "unlock-btn unlock-btn-primary", "onClick" to onViewDetail), _uA(
                                    _cE("text", _uM("class" to "unlock-btn-text-primary"), "查看详情")
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
                return _uM("unlock-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.62)", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 999)), "unlock-card" to _pS(_uM("width" to "82%", "backgroundColor" to "rgba(29,25,62,0.96)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.94) 0%, rgba(84, 65, 124, 0.82) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "paddingTop" to 24, "paddingRight" to 20, "paddingBottom" to 24, "paddingLeft" to 20, "alignItems" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.22)", "borderRightColor" to "rgba(219,200,237,0.22)", "borderBottomColor" to "rgba(219,200,237,0.22)", "borderLeftColor" to "rgba(219,200,237,0.22)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "unlock-fire" to _pS(_uM("fontSize" to 30, "marginBottom" to 8)), "unlock-title" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 15, "fontWeight" to "bold", "marginBottom" to 14)), "unlock-badge" to _pS(_uM("width" to 88, "height" to 88, "borderTopLeftRadius" to 44, "borderTopRightRadius" to 44, "borderBottomRightRadius" to 44, "borderBottomLeftRadius" to 44, "backgroundColor" to "rgba(219,200,237,0.18)", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.34)", "borderRightColor" to "rgba(219,200,237,0.34)", "borderBottomColor" to "rgba(219,200,237,0.34)", "borderLeftColor" to "rgba(219,200,237,0.34)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "unlock-icon" to _pS(_uM("fontSize" to 42)), "unlock-name" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 20, "fontWeight" to "bold", "marginTop" to 16)), "unlock-desc" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 13, "lineHeight" to "20px", "textAlign" to "center", "marginTop" to 8)), "unlock-reward" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 13, "marginTop" to 12, "textAlign" to "center")), "unlock-buttons" to _pS(_uM("width" to "100%", "flexDirection" to "row", "marginTop" to 20)), "unlock-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(255,255,255,0.1)", "marginRight" to 8)), "unlock-btn-primary" to _pS(_uM("backgroundColor" to "#DBC8ED", "marginRight" to 0, "marginLeft" to 8)), "unlock-btn-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "unlock-btn-text-primary" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("close" to null, "viewDetail" to null)
        var props = _nP(_uM("visible" to _uM("type" to "Boolean", "required" to true, "default" to false), "achievementIcon" to _uM("type" to "String", "required" to true, "default" to "🏅"), "achievementName" to _uM("type" to "String", "required" to true, "default" to ""), "achievementDescription" to _uM("type" to "String", "required" to true, "default" to ""), "rewardText" to _uM("type" to "String", "required" to true, "default" to "")))
        var propsNeedCastKeys = _uA(
            "visible",
            "achievementIcon",
            "achievementName",
            "achievementDescription",
            "rewardText"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
