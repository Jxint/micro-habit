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
            val accEnabled = ref<Boolean>(isAccessibilityServiceEnabled())
            val overlayEnabled = ref<Boolean>(hasOverlayPermission())
            fun gen_openAccessibility_fn(): Unit {
                openAccessibilitySettings()
                setTimeout(fun(): Unit {
                    accEnabled.value = isAccessibilityServiceEnabled()
                }
                , 800)
            }
            val openAccessibility = ::gen_openAccessibility_fn
            fun gen_openOverlay_fn(): Unit {
                requestOverlayPermission()
                setTimeout(fun(): Unit {
                    overlayEnabled.value = hasOverlayPermission()
                }
                , 800)
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
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "hero-block"), _uA(
                            _cE("view", _uM("class" to "hero-icon-wrap"), _uA(
                                _cE("text", _uM("class" to "hero-icon"), "🌿")
                            )),
                            _cE("text", _uM("class" to "hero-title"), "微习惯健康伴侣"),
                            _cE("text", _uM("class" to "hero-subtitle"), "一个在你不经意间，悄悄改善健康的 AI 微习惯伙伴")
                        )),
                        _cE("view", _uM("class" to "steps-block"), _uA(
                            _cE("view", _uM("class" to "step-card"), _uA(
                                _cE("view", _uM("class" to "step-num-wrap step-num-1"), _uA(
                                    _cE("text", _uM("class" to "step-num"), "1")
                                )),
                                _cE("view", _uM("class" to "step-content"), _uA(
                                    _cE("text", _uM("class" to "step-title"), "开启无障碍服务"),
                                    _cE("text", _uM("class" to "step-desc"), "检测你正在使用的应用，在恰当时提醒休息"),
                                    _cE("view", _uM("class" to "step-status-row"), _uA(
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "step-status",
                                            if (unref(accEnabled)) {
                                                "step-status-on"
                                            } else {
                                                "step-status-off"
                                            }
                                        ))), _tD(if (unref(accEnabled)) {
                                            "✅ 已开启"
                                        } else {
                                            "⚪ 未开启"
                                        }
                                        ), 3),
                                        if (isTrue(!unref(accEnabled))) {
                                            _cE("view", _uM("key" to 0, "class" to "step-action-btn", "onClick" to openAccessibility), _uA(
                                                _cE("text", _uM("class" to "step-action-btn-text"), "立即开启")
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "step-card"), _uA(
                                _cE("view", _uM("class" to "step-num-wrap step-num-2"), _uA(
                                    _cE("text", _uM("class" to "step-num"), "2")
                                )),
                                _cE("view", _uM("class" to "step-content"), _uA(
                                    _cE("text", _uM("class" to "step-title"), "开启悬浮窗权限"),
                                    _cE("text", _uM("class" to "step-desc"), "在其他应用上方显示微动作提醒"),
                                    _cE("view", _uM("class" to "step-status-row"), _uA(
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "step-status",
                                            if (unref(overlayEnabled)) {
                                                "step-status-on"
                                            } else {
                                                "step-status-off"
                                            }
                                        ))), _tD(if (unref(overlayEnabled)) {
                                            "✅ 已开启"
                                        } else {
                                            "⚪ 未开启"
                                        }
                                        ), 3),
                                        if (isTrue(!unref(overlayEnabled))) {
                                            _cE("view", _uM("key" to 0, "class" to "step-action-btn", "onClick" to openOverlay), _uA(
                                                _cE("text", _uM("class" to "step-action-btn-text"), "立即开启")
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "step-card step-card-info"), _uA(
                                _cE("view", _uM("class" to "step-num-wrap step-num-3"), _uA(
                                    _cE("text", _uM("class" to "step-num"), "3")
                                )),
                                _cE("view", _uM("class" to "step-content"), _uA(
                                    _cE("text", _uM("class" to "step-title"), "完成自动守护"),
                                    _cE("text", _uM("class" to "step-desc"), "检测到长时间使用 App，会自动弹出微动作提醒（眨眼、转颈、远眺）")
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "cta-block"), _uA(
                            _cE("view", _uM("class" to "cta-btn-primary", "onClick" to goHome), _uA(
                                _cE("text", _uM("class" to "cta-btn-primary-text"), "开始使用")
                            )),
                            _cE("view", _uM("class" to "cta-btn-secondary", "onClick" to goHome), _uA(
                                _cE("text", _uM("class" to "cta-btn-secondary-text"), "先逛逛")
                            ))
                        )),
                        _cE("view", _uM("class" to "footer-block"), _uA(
                            _cE("text", _uM("class" to "footer-text"), "v1.0.0 · 守护你的每一次专注")
                        ))
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#1E2A38")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "hero-block" to _pS(_uM("alignItems" to "center", "paddingTop" to 60, "paddingRight" to 24, "paddingBottom" to 32, "paddingLeft" to 24, "backgroundColor" to "#1E2A38")), "hero-icon-wrap" to _pS(_uM("width" to 96, "height" to 96, "borderTopLeftRadius" to 48, "borderTopRightRadius" to 48, "borderBottomRightRadius" to 48, "borderBottomLeftRadius" to 48, "backgroundColor" to "rgba(46,204,113,0.15)", "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 20)), "hero-icon" to _pS(_uM("fontSize" to 56)), "hero-title" to _pS(_uM("fontSize" to 24, "fontWeight" to "bold", "color" to "#FFFFFF", "marginBottom" to 8)), "hero-subtitle" to _pS(_uM("fontSize" to 13, "color" to "#95A5A6", "textAlign" to "center", "lineHeight" to 1.6, "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16)), "steps-block" to _pS(_uM("paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16)), "step-card" to _pS(_uM("flexDirection" to "row", "backgroundColor" to "#2C3E50", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "marginBottom" to 12, "alignItems" to "flex-start")), "step-card-info" to _pS(_uM("backgroundColor" to "rgba(46,204,113,0.12)")), "step-num-wrap" to _pS(_uM("width" to 32, "height" to 32, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center", "marginRight" to 12, "marginTop" to 2, "flexShrink" to 0)), "step-num-1" to _pS(_uM("backgroundColor" to "#3498DB")), "step-num-2" to _pS(_uM("backgroundColor" to "#9B59B6")), "step-num-3" to _pS(_uM("backgroundColor" to "#2ECC71")), "step-num" to _pS(_uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#FFFFFF")), "step-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "step-title" to _pS(_uM("fontSize" to 15, "fontWeight" to "bold", "color" to "#FFFFFF", "marginBottom" to 4)), "step-desc" to _pS(_uM("fontSize" to 12, "color" to "#BDC3C7", "lineHeight" to 1.6, "marginBottom" to 10)), "step-status-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "step-status" to _pS(_uM("fontSize" to 12, "fontWeight" to 500, "paddingTop" to 4, "paddingRight" to 10, "paddingBottom" to 4, "paddingLeft" to 10, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10)), "step-status-on" to _pS(_uM("color" to "#2ECC71", "backgroundColor" to "rgba(46,204,113,0.15)")), "step-status-off" to _pS(_uM("color" to "#BDC3C7", "backgroundColor" to "rgba(255,255,255,0.05)")), "step-action-btn" to _pS(_uM("marginLeft" to 10, "paddingTop" to 5, "paddingRight" to 12, "paddingBottom" to 5, "paddingLeft" to 12, "backgroundColor" to "#3498DB", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12)), "step-action-btn-text" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "fontWeight" to 500)), "cta-block" to _pS(_uM("paddingTop" to 20, "paddingRight" to 24, "paddingBottom" to 0, "paddingLeft" to 24, "alignItems" to "center")), "cta-btn-primary" to _pS(_uM("width" to "100%", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "backgroundColor" to "#2ECC71", "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26, "alignItems" to "center", "justifyContent" to "center", "boxShadow" to "0 4px 12px rgba(46, 204, 113, 0.3)", "marginBottom" to 10)), "cta-btn-primary-text" to _pS(_uM("fontSize" to 16, "color" to "#FFFFFF", "fontWeight" to "bold")), "cta-btn-secondary" to _pS(_uM("paddingTop" to 8, "paddingRight" to 16, "paddingBottom" to 8, "paddingLeft" to 16)), "cta-btn-secondary-text" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D")), "footer-block" to _pS(_uM("alignItems" to "center", "paddingTop" to 24, "paddingRight" to 0, "paddingBottom" to 32, "paddingLeft" to 0)), "footer-text" to _pS(_uM("fontSize" to 11, "color" to "#4A5A6A")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
