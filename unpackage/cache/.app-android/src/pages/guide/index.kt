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
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
open class GenPagesGuideIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesGuideIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesGuideIndex
            val _cache = __ins.renderCache
            val accEnabled = ref(isAccessibilityServiceEnabled())
            val overlayEnabled = ref(hasOverlayPermission())
            fun gen_openAccessibility_fn(): Unit {
                openAccessibilitySettings()
                setTimeout(fun(): Unit {
                    accEnabled.value = isAccessibilityServiceEnabled()
                }
                , 500)
            }
            val openAccessibility = ::gen_openAccessibility_fn
            fun gen_openOverlay_fn(): Unit {
                requestOverlayPermission()
                setTimeout(fun(): Unit {
                    overlayEnabled.value = hasOverlayPermission()
                }
                , 500)
            }
            val openOverlay = ::gen_openOverlay_fn
            fun gen_goHome_fn(): Unit {
                putInt("guide_completed", 1)
                val now = Math.floor(Date.now() / 1000)
                putInt("newbie_start_date", now)
                uni_reLaunch(ReLaunchOptions(url = "/pages/home/index"))
            }
            val goHome = ::gen_goHome_fn
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "guide-content"), _uA(
                        _cE("text", _uM("class" to "guide-title"), "欢迎使用"),
                        _cE("text", _uM("class" to "guide-subtitle"), "微习惯健康伴侣"),
                        _cE("view", _uM("class" to "guide-desc"), "一个在你不经意间，悄悄改善健康的 AI 微习惯伙伴"),
                        _cE("view", _uM("class" to "permission-section"), _uA(
                            _cE("view", _uM("class" to "permission-item"), _uA(
                                _cE("text", _uM("class" to "perm-status"), _tD(if (unref(accEnabled)) {
                                    "✅"
                                } else {
                                    "❌"
                                }
                                ), 1),
                                _cE("view", _uM("class" to "perm-info"), _uA(
                                    _cE("text", _uM("class" to "perm-title"), "无障碍服务"),
                                    _cE("text", _uM("class" to "perm-desc"), "检测你正在使用的应用，在恰当时提醒休息")
                                )),
                                _cE("button", _uM("class" to "perm-btn", "onClick" to openAccessibility), "开启")
                            )),
                            _cE("view", _uM("class" to "permission-item"), _uA(
                                _cE("text", _uM("class" to "perm-status"), _tD(if (unref(overlayEnabled)) {
                                    "✅"
                                } else {
                                    "❌"
                                }
                                ), 1),
                                _cE("view", _uM("class" to "perm-info"), _uA(
                                    _cE("text", _uM("class" to "perm-title"), "悬浮窗权限"),
                                    _cE("text", _uM("class" to "perm-desc"), "在其他应用上方显示微动作提醒")
                                )),
                                _cE("button", _uM("class" to "perm-btn", "onClick" to openOverlay), "开启")
                            ))
                        )),
                        _cE("button", _uM("class" to "start-btn", "onClick" to goHome), "开始使用")
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#2C3E50", "justifyContent" to "center", "alignItems" to "center")), "guide-content" to _pS(_uM("alignItems" to "center", "paddingTop" to 40, "paddingRight" to 40, "paddingBottom" to 40, "paddingLeft" to 40)), "guide-title" to _pS(_uM("fontSize" to 28, "fontWeight" to "bold", "color" to "#FFFFFF", "marginBottom" to 8)), "guide-subtitle" to _pS(_uM("fontSize" to 18, "color" to "#3498DB", "marginBottom" to 16)), "guide-desc" to _pS(_uM("fontSize" to 14, "color" to "#BDC3C7", "textAlign" to "center", "lineHeight" to "22px", "marginBottom" to 24)), "permission-section" to _pS(_uM("width" to "100%", "marginBottom" to 24)), "permission-item" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#34495E", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 8)), "perm-status" to _pS(_uM("fontSize" to 20, "width" to 36, "textAlign" to "center")), "perm-info" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "marginTop" to 0, "marginRight" to 8, "marginBottom" to 0, "marginLeft" to 8)), "perm-title" to _pS(_uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#FFFFFF")), "perm-desc" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginTop" to 2)), "perm-btn" to _pS(_uM("paddingTop" to 8, "paddingRight" to 16, "paddingBottom" to 8, "paddingLeft" to 16, "backgroundColor" to "#3498DB", "color" to "#FFFFFF", "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "fontSize" to 12)), "start-btn" to _pS(_uM("width" to 200, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "backgroundColor" to "#27AE60", "color" to "#FFFFFF", "borderTopLeftRadius" to 25, "borderTopRightRadius" to 25, "borderBottomRightRadius" to 25, "borderBottomLeftRadius" to 25, "fontSize" to 16, "textAlign" to "center")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
