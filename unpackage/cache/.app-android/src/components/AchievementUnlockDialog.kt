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
                return _uM("unlock-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.65)", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 1000)), "unlock-card" to _pS(_uM("width" to "80%", "maxWidth" to 380, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "paddingTop" to 24, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "flexDirection" to "column", "alignItems" to "center", "borderTopWidth" to 3, "borderRightWidth" to 3, "borderBottomWidth" to 3, "borderLeftWidth" to 3, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#F39C12", "borderRightColor" to "#F39C12", "borderBottomColor" to "#F39C12", "borderLeftColor" to "#F39C12")), "unlock-fire" to _pS(_uM("fontSize" to 48, "marginBottom" to 8)), "unlock-title" to _pS(_uM("fontSize" to 18, "fontWeight" to "bold", "color" to "#D35400", "marginBottom" to 16)), "unlock-badge" to _pS(_uM("width" to 88, "height" to 88, "borderTopLeftRadius" to 44, "borderTopRightRadius" to 44, "borderBottomRightRadius" to 44, "borderBottomLeftRadius" to 44, "backgroundColor" to "#FEF5E7", "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 14, "borderTopWidth" to 3, "borderRightWidth" to 3, "borderBottomWidth" to 3, "borderLeftWidth" to 3, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#F39C12", "borderRightColor" to "#F39C12", "borderBottomColor" to "#F39C12", "borderLeftColor" to "#F39C12")), "unlock-icon" to _pS(_uM("fontSize" to 48)), "unlock-name" to _pS(_uM("fontSize" to 22, "fontWeight" to "bold", "color" to "#2C3E50", "marginBottom" to 6)), "unlock-desc" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D", "textAlign" to "center", "marginBottom" to 12, "lineHeight" to 1.5)), "unlock-reward" to _pS(_uM("fontSize" to 13, "color" to "#27AE60", "fontWeight" to "bold", "marginBottom" to 20)), "unlock-buttons" to _pS(_uM("flexDirection" to "row", "width" to "100%")), "unlock-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 42, "borderTopLeftRadius" to 21, "borderTopRightRadius" to 21, "borderBottomRightRadius" to 21, "borderBottomLeftRadius" to 21, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#ECF0F1", "marginRight" to 8)), "unlock-btn-primary" to _pS(_uM("backgroundColor" to "#E67E22", "marginRight" to 0, "marginLeft" to 8)), "unlock-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#2C3E50")), "unlock-btn-text-primary" to _pS(_uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#FFFFFF")))
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
