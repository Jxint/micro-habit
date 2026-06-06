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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesGuideIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesGuideIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesGuideIndex
            val _cache = __ins.renderCache
            val usageEnabled = ref<Boolean>(isUsagePermissionGranted())
            val overlayEnabled = ref<Boolean>(hasOverlayPermission())
            val selectedFocus = ref<String>("balance")
            val demoStarted = ref<Boolean>(false)
            fun gen_selectFocus_fn(v: String): Unit {
                selectedFocus.value = v
            }
            val selectFocus = ::gen_selectFocus_fn
            fun gen_startDemo_fn(): Unit {
                demoStarted.value = true
            }
            val startDemo = ::gen_startDemo_fn
            fun gen_refreshPermissionStates_fn(): Unit {
                usageEnabled.value = isUsagePermissionGranted()
                overlayEnabled.value = hasOverlayPermission()
            }
            val refreshPermissionStates = ::gen_refreshPermissionStates_fn
            fun gen_openUsageAccess_fn(): Unit {
                console.log("[guide] openUsageAccess 被点击", " at pages/guide/index.uvue:219")
                try {
                    openUsageAccessSettings()
                }
                 catch (e: Throwable) {
                    console.warn("[guide] openUsageAccessSettings 异常: " + JSON.stringify(e), " at pages/guide/index.uvue:223")
                }
                setTimeout(refreshPermissionStates, 800)
            }
            val openUsageAccess = ::gen_openUsageAccess_fn
            fun gen_openOverlay_fn(): Unit {
                console.log("[guide] openOverlay 被点击", " at pages/guide/index.uvue:229")
                try {
                    requestOverlayPermission()
                }
                 catch (e: Throwable) {
                    console.warn("[guide] requestOverlayPermission 异常: " + JSON.stringify(e), " at pages/guide/index.uvue:233")
                }
                setTimeout(refreshPermissionStates, 800)
            }
            val openOverlay = ::gen_openOverlay_fn
            fun gen_completeGuide_fn(): Unit {
                putInt("guide_completed", 1)
                uni_reLaunch(ReLaunchOptions(url = "/pages/home/index"))
            }
            val completeGuide = ::gen_completeGuide_fn
            fun gen_startApp_fn(): Unit {
                refreshPermissionStates()
                if (!usageEnabled.value) {
                    uni_showToast(ShowToastOptions(title = "请先开启使用情况权限", icon = "none"))
                    return
                }
                if (!overlayEnabled.value) {
                    uni_showToast(ShowToastOptions(title = "请先开启悬浮窗权限", icon = "none"))
                    return
                }
                completeGuide()
            }
            val startApp = ::gen_startApp_fn
            fun gen_skipGuide_fn(): Unit {
                completeGuide()
            }
            val skipGuide = ::gen_skipGuide_fn
            onShow(fun(): Unit {
                refreshPermissionStates()
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "page-bg"), _uA(
                        _cE("image", _uM("class" to "page-bg-img", "src" to "/static/images/micro-habit-page-bg.png", "mode" to "aspectFill"))
                    )),
                    _cE("view", _uM("class" to "bg-glow glow-purple")),
                    _cE("view", _uM("class" to "bg-glow glow-blue")),
                    _cE("view", _uM("class" to "bg-glow glow-pink")),
                    _cE("view", _uM("class" to "sky-star star-a")),
                    _cE("view", _uM("class" to "sky-star star-b")),
                    _cE("view", _uM("class" to "sky-star star-c")),
                    _cE("view", _uM("class" to "sky-star star-d")),
                    _cE("view", _uM("class" to "sky-star star-e")),
                    _cE("view", _uM("class" to "sky-star star-f")),
                    _cE("view", _uM("class" to "sky-star star-g")),
                    _cE("view", _uM("class" to "sky-star star-h")),
                    _cE("view", _uM("class" to "sky-star star-i")),
                    _cE("view", _uM("class" to "sky-star star-j")),
                    _cE("view", _uM("class" to "sky-star star-k")),
                    _cE("view", _uM("class" to "sky-star star-l")),
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "hero-section"), _uA(
                            _cE("view", _uM("class" to "hero-planet-wrap"), _uA(
                                _cE("view", _uM("class" to "hero-planet-glow")),
                                _cE("image", _uM("class" to "hero-mascot", "src" to "/static/images/micro-planet-mascot.png", "mode" to "aspectFit"))
                            )),
                            _cE("view", _uM("class" to "hero-copy"), _uA(
                                _cE("text", _uM("class" to "hero-brand"), "微憩星球"),
                                _cE("text", _uM("class" to "hero-title"), "轻轻守护，从 10 秒开始"),
                                _cE("text", _uM("class" to "hero-desc"), "它会观察你的使用节奏，在合适的时候提醒你眨眨眼、松松肩、慢慢呼吸。")
                            )),
                            _cE("view", _uM("class" to "hero-chip-row"), _uA(
                                _cE("view", _uM("class" to "hero-chip"), _uA(
                                    _cE("text", _uM("class" to "hero-chip-text"), "不说教")
                                )),
                                _cE("view", _uM("class" to "hero-chip"), _uA(
                                    _cE("text", _uM("class" to "hero-chip-text"), "不打扰")
                                )),
                                _cE("view", _uM("class" to "hero-chip"), _uA(
                                    _cE("text", _uM("class" to "hero-chip-text"), "10 秒见效")
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "card card-accent ai-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "AI 健康大脑"),
                            _cE("text", _uM("class" to "section-subtitle"), "不是催你打卡，而是把使用节奏、完成反馈和健康状态一起看懂。"),
                            _cE("view", _uM("class" to "ai-core-row"), _uA(
                                _cE("image", _uM("class" to "ai-icon-img", "src" to "/static/images/elements/ai-brain_001.jpg", "mode" to "aspectFit")),
                                _cE("view", _uM("class" to "ai-core-copy"), _uA(
                                    _cE("text", _uM("class" to "ai-core-title"), "先判断，再轻轻提醒"),
                                    _cE("text", _uM("class" to "ai-core-desc"), "本地规则引擎实时看连续使用时长，推荐算法选择当下更适合的护眼、体态或呼吸动作。")
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "card value-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "它为你做什么"),
                            _cE("view", _uM("class" to "value-grid"), _uA(
                                _cE("view", _uM("class" to "value-card"), _uA(
                                    _cE("image", _uM("class" to "value-icon-img", "src" to "/static/images/elements/icon-rest_001.jpg", "mode" to "aspectFit")),
                                    _cE("text", _uM("class" to "value-label"), "解决忘休息"),
                                    _cE("text", _uM("class" to "value-desc"), "刷久了才提醒，不靠你自己想起来")
                                )),
                                _cE("view", _uM("class" to "value-card"), _uA(
                                    _cE("image", _uM("class" to "value-icon-img", "src" to "/static/images/elements/icon-compass_001.jpg", "mode" to "aspectFit")),
                                    _cE("text", _uM("class" to "value-label"), "推荐做什么"),
                                    _cE("text", _uM("class" to "value-desc"), "按时间段和历史完成情况选微动作")
                                )),
                                _cE("view", _uM("class" to "value-card"), _uA(
                                    _cE("image", _uM("class" to "value-icon-img", "src" to "/static/images/elements/icon-book_001.jpg", "mode" to "aspectFit")),
                                    _cE("text", _uM("class" to "value-label"), "AI 小结报告"),
                                    _cE("text", _uM("class" to "value-desc"), "每日一句话、每周建议和下周小目标")
                                )),
                                _cE("view", _uM("class" to "value-card"), _uA(
                                    _cE("image", _uM("class" to "value-icon-img", "src" to "/static/images/elements/icon-heart-brain_001.jpg", "mode" to "aspectFit")),
                                    _cE("text", _uM("class" to "value-label"), "越用越懂你"),
                                    _cE("text", _uM("class" to "value-desc"), "完成、跳过、忙一下，都会帮助调节频率")
                                ))
                            )),
                            _cE("view", _uM("class" to "privacy-bar"), _uA(
                                _cE("text", _uM("class" to "privacy-text"), "只识别你正在使用哪个 App 和连续用了多久，不读取聊天内容、密码或屏幕文字。")
                            ))
                        )),
                        _cE("view", _uM("class" to "card card-accent focus-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "选择守护重点"),
                            _cE("text", _uM("class" to "section-subtitle"), "之后可以随时修改，现在先选一个你最想照顾的方向。"),
                            _cE("view", _uM("class" to "focus-grid"), _uA(
                                _cE("view", _uM("class" to _nC(_uA(
                                    "focus-card",
                                    if (unref(selectedFocus) === "eye") {
                                        "focus-active"
                                    } else {
                                        ""
                                    }
                                )), "onClick" to fun(){
                                    selectFocus("eye")
                                }
                                ), _uA(
                                    _cE("view", _uM("class" to "focus-icon-wrap focus-icon-blue"), _uA(
                                        _cE("text", _uM("class" to "focus-icon-text"), "目")
                                    )),
                                    _cE("text", _uM("class" to "focus-label"), "护眼优先")
                                ), 10, _uA(
                                    "onClick"
                                )),
                                _cE("view", _uM("class" to _nC(_uA(
                                    "focus-card",
                                    if (unref(selectedFocus) === "posture") {
                                        "focus-active"
                                    } else {
                                        ""
                                    }
                                )), "onClick" to fun(){
                                    selectFocus("posture")
                                }
                                ), _uA(
                                    _cE("view", _uM("class" to "focus-icon-wrap focus-icon-purple"), _uA(
                                        _cE("text", _uM("class" to "focus-icon-text"), "颈")
                                    )),
                                    _cE("text", _uM("class" to "focus-label"), "肩颈优先")
                                ), 10, _uA(
                                    "onClick"
                                )),
                                _cE("view", _uM("class" to _nC(_uA(
                                    "focus-card",
                                    if (unref(selectedFocus) === "breath") {
                                        "focus-active"
                                    } else {
                                        ""
                                    }
                                )), "onClick" to fun(){
                                    selectFocus("breath")
                                }
                                ), _uA(
                                    _cE("view", _uM("class" to "focus-icon-wrap focus-icon-pink"), _uA(
                                        _cE("text", _uM("class" to "focus-icon-text"), "息")
                                    )),
                                    _cE("text", _uM("class" to "focus-label"), "减压呼吸")
                                ), 10, _uA(
                                    "onClick"
                                )),
                                _cE("view", _uM("class" to _nC(_uA(
                                    "focus-card",
                                    if (unref(selectedFocus) === "balance") {
                                        "focus-active"
                                    } else {
                                        ""
                                    }
                                )), "onClick" to fun(){
                                    selectFocus("balance")
                                }
                                ), _uA(
                                    _cE("view", _uM("class" to "focus-icon-wrap focus-icon-green"), _uA(
                                        _cE("text", _uM("class" to "focus-icon-text"), "衡")
                                    )),
                                    _cE("text", _uM("class" to "focus-label"), "均衡发展")
                                ), 10, _uA(
                                    "onClick"
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "card steps-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "开启轻轻守护"),
                            _cE("text", _uM("class" to "section-subtitle"), "权限只用于识别 App 和连续使用时长，不读取聊天内容、密码或屏幕文字。"),
                            _cE("view", _uM("class" to "step-row"), _uA(
                                _cE("view", _uM("class" to "step-badge badge-blue"), _uA(
                                    _cE("text", _uM("class" to "step-badge-text"), "1")
                                )),
                                _cE("view", _uM("class" to "step-body"), _uA(
                                    _cE("text", _uM("class" to "step-name"), "开启使用情况权限"),
                                    _cE("text", _uM("class" to "step-hint"), "用来检测你正在使用哪个 App，以及连续使用了多久"),
                                    _cE("view", _uM("class" to "step-actions"), _uA(
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "step-status",
                                            if (unref(usageEnabled)) {
                                                "status-on"
                                            } else {
                                                "status-off"
                                            }
                                        ))), _tD(if (unref(usageEnabled)) {
                                            "已开启"
                                        } else {
                                            "未开启"
                                        }
                                        ), 3),
                                        if (isTrue(!unref(usageEnabled))) {
                                            _cE("view", _uM("key" to 0, "class" to "step-btn", "onClick" to openUsageAccess), _uA(
                                                _cE("text", _uM("class" to "step-btn-text"), "立即开启")
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "step-row"), _uA(
                                _cE("view", _uM("class" to "step-badge badge-purple"), _uA(
                                    _cE("text", _uM("class" to "step-badge-text"), "2")
                                )),
                                _cE("view", _uM("class" to "step-body"), _uA(
                                    _cE("text", _uM("class" to "step-name"), "开启悬浮窗权限"),
                                    _cE("text", _uM("class" to "step-hint"), "用来在其他 App 上方显示微动作提醒"),
                                    _cE("view", _uM("class" to "step-actions"), _uA(
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "step-status",
                                            if (unref(overlayEnabled)) {
                                                "status-on"
                                            } else {
                                                "status-off"
                                            }
                                        ))), _tD(if (unref(overlayEnabled)) {
                                            "已开启"
                                        } else {
                                            "未开启"
                                        }
                                        ), 3),
                                        if (isTrue(!unref(overlayEnabled))) {
                                            _cE("view", _uM("key" to 0, "class" to "step-btn", "onClick" to openOverlay), _uA(
                                                _cE("text", _uM("class" to "step-btn-text"), "立即开启")
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "step-row"), _uA(
                                _cE("view", _uM("class" to "step-badge badge-pink"), _uA(
                                    _cE("text", _uM("class" to "step-badge-text"), "3")
                                )),
                                _cE("view", _uM("class" to "step-body"), _uA(
                                    _cE("text", _uM("class" to "step-name"), "后台守护"),
                                    _cE("text", _uM("class" to "step-hint"), "用来保持提醒服务稳定运行，不打扰你的正常使用")
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "card card-accent demo-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "演示一次"),
                            _cE("text", _uM("class" to "section-subtitle"), "假设你刷屏久了一点，小星球会这样提醒你。"),
                            _cE("view", _uM("class" to "demo-card"), _uA(
                                _cE("view", _uM("class" to "demo-planet-circle"), _uA(
                                    _cE("image", _uM("class" to "demo-mascot", "src" to "/static/images/micro-planet-mascot.png", "mode" to "aspectFit"))
                                )),
                                _cE("view", _uM("class" to "demo-copy"), _uA(
                                    _cE("text", _uM("class" to "demo-copy-title"), "星球信号提醒"),
                                    _cE("text", _uM("class" to "demo-copy-text"), "检测到眼睛电量有点低，要不要眨眼 10 秒？")
                                )),
                                _cE("view", _uM("class" to "demo-btn", "onClick" to startDemo), _uA(
                                    _cE("text", _uM("class" to "demo-btn-text"), _tD(if (unref(demoStarted)) {
                                        "演示中"
                                    } else {
                                        "好啊"
                                    }
                                    ), 1)
                                ))
                            )),
                            if (isTrue(unref(demoStarted))) {
                                _cE("view", _uM("key" to 0, "class" to "demo-flow"), _uA(
                                    _cE("view", _uM("class" to "demo-progress-bar"), _uA(
                                        _cE("view", _uM("class" to "demo-progress-fill"))
                                    )),
                                    _cE("view", _uM("class" to "demo-steps-flow"), _uA(
                                        _cE("view", _uM("class" to "demo-flow-step"), _uA(
                                            _cE("view", _uM("class" to "demo-flow-dot dot-done")),
                                            _cE("text", _uM("class" to "demo-flow-label"), "收到提醒")
                                        )),
                                        _cE("view", _uM("class" to "demo-flow-line")),
                                        _cE("view", _uM("class" to "demo-flow-step"), _uA(
                                            _cE("view", _uM("class" to "demo-flow-dot dot-done")),
                                            _cE("text", _uM("class" to "demo-flow-label"), "点\"好啊\"")
                                        )),
                                        _cE("view", _uM("class" to "demo-flow-line")),
                                        _cE("view", _uM("class" to "demo-flow-step"), _uA(
                                            _cE("view", _uM("class" to "demo-flow-dot dot-active")),
                                            _cE("text", _uM("class" to "demo-flow-label"), "跟着做")
                                        ))
                                    )),
                                    _cE("text", _uM("class" to "demo-result-text"), "完成后，小星球会点亮一次，并把这次照顾记录进健康轨迹。")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                        )),
                        _cE("view", _uM("class" to "cta-section"), _uA(
                            _cE("view", _uM("class" to "cta-primary", "onClick" to startApp), _uA(
                                _cE("text", _uM("class" to "cta-primary-text"), "开始我的小星球")
                            )),
                            _cE("view", _uM("class" to "cta-secondary", "onClick" to skipGuide), _uA(
                                _cE("text", _uM("class" to "cta-secondary-text"), "先逛逛")
                            ))
                        )),
                        _cE("view", _uM("class" to "footer"), _uA(
                            _cE("text", _uM("class" to "footer-text"), "v1.0.0 · 守护你的每一次温柔停顿")
                        ))
                    ))
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0,
                styles1
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#162038", "position" to "relative", "overflow" to "hidden")), "page-bg" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "width" to "100%", "height" to "100%", "zIndex" to 0)), "page-bg-img" to _pS(_uM("width" to "100%", "height" to "100%")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "zIndex" to 1)), "bg-glow" to _pS(_uM("position" to "absolute", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "zIndex" to 0)), "glow-purple" to _pS(_uM("width" to 210, "height" to 210, "left" to -82, "top" to -54, "backgroundColor" to "#776B97", "opacity" to 0.5)), "glow-blue" to _pS(_uM("width" to 168, "height" to 168, "right" to -60, "top" to 104, "backgroundColor" to "#C2E0EE", "opacity" to 0.24)), "glow-pink" to _pS(_uM("width" to 178, "height" to 178, "left" to 42, "bottom" to 88, "backgroundColor" to "#C68DC0", "opacity" to 0.18)), "sky-star" to _pS(_uM("position" to "absolute", "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "rgba(255,255,255,0.74)", "zIndex" to 0)), "star-a" to _pS(_uM("left" to 36, "top" to 88)), "star-b" to _pS(_uM("left" to 118, "top" to 144, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-c" to _pS(_uM("right" to 52, "top" to 116)), "star-d" to _pS(_uM("right" to 26, "top" to 310, "width" to 5, "height" to 5, "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "star-e" to _pS(_uM("left" to 54, "top" to 520)), "star-f" to _pS(_uM("right" to 86, "top" to 420, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-g" to _pS(_uM("left" to 138, "top" to 610)), "star-h" to _pS(_uM("right" to 36, "top" to 720, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-i" to _pS(_uM("left" to 76, "top" to 214, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-j" to _pS(_uM("right" to 148, "top" to 276, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-k" to _pS(_uM("left" to 178, "top" to 392, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-l" to _pS(_uM("right" to 64, "top" to 560, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "card" to _pS(_uM("marginTop" to 14, "marginRight" to 16, "marginBottom" to 14, "marginLeft" to 16, "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)")), "card-accent" to _pS(_uM("borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34), inset 0 1px 0 rgba(255, 255, 255, 0.06)")), "section-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 20, "fontWeight" to "bold", "lineHeight" to "26px")), "section-subtitle" to _pS(_uM("color" to "rgba(255,255,255,0.68)", "fontSize" to 13, "lineHeight" to "20px", "marginTop" to 6)), "hero-section" to _pS(_uM("marginTop" to 22, "marginRight" to 16, "marginBottom" to 0, "marginLeft" to 16, "paddingTop" to 28, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "borderTopLeftRadius" to 32, "borderTopRightRadius" to 32, "borderBottomRightRadius" to 32, "borderBottomLeftRadius" to 32, "backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(160deg, rgba(48, 42, 94, 0.96) 0%, rgba(84, 65, 124, 0.78) 40%, rgba(198, 141, 192, 0.24) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34), inset 0 1px 0 rgba(255, 255, 255, 0.06)", "alignItems" to "center")), "hero-planet-wrap" to _pS(_uM("position" to "relative", "width" to 140, "height" to 140, "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 16)), "hero-planet-glow" to _pS(_uM("position" to "absolute", "width" to 160, "height" to 160, "borderTopLeftRadius" to 80, "borderTopRightRadius" to 80, "borderBottomRightRadius" to 80, "borderBottomLeftRadius" to 80, "backgroundColor" to "rgba(219,200,237,0.14)", "boxShadow" to "0 0 40px rgba(219, 200, 237, 0.18)")), "hero-mascot" to _pS(_uM("width" to 120, "height" to 100)), "hero-copy" to _pS(_uM("alignItems" to "center", "marginBottom" to 16)), "hero-brand" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 12, "fontWeight" to "bold", "letterSpacing" to 2, "marginBottom" to 10)), "hero-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 26, "fontWeight" to "bold", "lineHeight" to "32px", "textAlign" to "center", "marginBottom" to 10)), "hero-desc" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14, "lineHeight" to "22px", "textAlign" to "center")), "hero-chip-row" to _pS(_uM("flexDirection" to "row", "justifyContent" to "center")), "hero-chip" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.12)", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "paddingTop" to 5, "paddingRight" to 12, "paddingBottom" to 5, "paddingLeft" to 12, "marginTop" to 0, "marginRight" to 4, "marginBottom" to 0, "marginLeft" to 4)), "hero-chip-text" to _pS(_uM("color" to "rgba(255,255,255,0.88)", "fontSize" to 12)), "ai-section" to _pS(_uM("marginTop" to 14)), "ai-core-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 16, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.08)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "ai-icon-img" to _pS(_uM("width" to 56, "height" to 56, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "marginRight" to 14)), "ai-core-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "ai-core-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 15, "fontWeight" to "bold", "marginBottom" to 5)), "ai-core-desc" to _pS(_uM("color" to "rgba(255,255,255,0.68)", "fontSize" to 12, "lineHeight" to "19px")), "value-section" to _pS(_uM("marginTop" to 14)), "value-grid" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 16)), "value-card" to _pS(_uM("width" to "48%", "marginRight" to "2%", "marginBottom" to 10, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.08)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "alignItems" to "center")), "value-icon-img" to _pS(_uM("width" to 52, "height" to 52, "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26, "marginBottom" to 10)), "value-label" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14, "fontWeight" to "bold", "marginBottom" to 5)), "value-desc" to _pS(_uM("color" to "rgba(255,255,255,0.65)", "fontSize" to 12, "lineHeight" to "18px", "textAlign" to "center")), "privacy-bar" to _pS(_uM("marginTop" to 12, "paddingTop" to 10, "paddingRight" to 14, "paddingBottom" to 10, "paddingLeft" to 14, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "rgba(143,240,195,0.08)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(143,240,195,0.18)", "borderRightColor" to "rgba(143,240,195,0.18)", "borderBottomColor" to "rgba(143,240,195,0.18)", "borderLeftColor" to "rgba(143,240,195,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "privacy-text" to _pS(_uM("color" to "rgba(143,240,195,0.82)", "fontSize" to 11, "lineHeight" to "17px")), "focus-section" to _pS(_uM("marginTop" to 14)), "focus-grid" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 16)), "focus-card" to _pS(_uM("width" to "48%", "marginRight" to "2%", "marginBottom" to 10, "paddingTop" to 16, "paddingRight" to 12, "paddingBottom" to 16, "paddingLeft" to 12, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.08)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "alignItems" to "center")), "focus-active" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.18)", "borderTopColor" to "rgba(219,200,237,0.62)", "borderRightColor" to "rgba(219,200,237,0.62)", "borderBottomColor" to "rgba(219,200,237,0.62)", "borderLeftColor" to "rgba(219,200,237,0.62)", "boxShadow" to "0 0 16px rgba(219, 200, 237, 0.28)")), "focus-icon-wrap" to _pS(_uM("width" to 44, "height" to 44, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 8)), "focus-icon-blue" to _pS(_uM("backgroundColor" to "rgba(194,224,238,0.22)")), "focus-icon-purple" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.22)")), "focus-icon-pink" to _pS(_uM("backgroundColor" to "rgba(241,207,237,0.22)")), "focus-icon-green" to _pS(_uM("backgroundColor" to "rgba(143,240,195,0.22)")), "focus-icon-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 18, "fontWeight" to "bold")), "focus-label" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 13, "fontWeight" to "bold")), "steps-section" to _pS(_uM("marginTop" to 14)), "step-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-start", "marginTop" to 16, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.06)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.12)", "borderRightColor" to "rgba(219,200,237,0.12)", "borderBottomColor" to "rgba(219,200,237,0.12)", "borderLeftColor" to "rgba(219,200,237,0.12)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "step-badge" to _pS(_uM("width" to 36, "height" to 36, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "alignItems" to "center", "justifyContent" to "center", "marginRight" to 14, "marginTop" to 2)), "badge-blue" to _pS(_uM("backgroundColor" to "rgba(194,224,238,0.28)")), "badge-purple" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.28)")), "badge-pink" to _pS(_uM("backgroundColor" to "rgba(241,207,237,0.28)")), "step-badge-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 15, "fontWeight" to "bold")), "step-body" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "step-name" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 16, "fontWeight" to "bold", "marginBottom" to 4)), "step-hint" to _pS(_uM("color" to "rgba(255,255,255,0.6)", "fontSize" to 12, "lineHeight" to "18px", "marginBottom" to 10)), "step-actions" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "step-status" to _pS(_uM("fontSize" to 12, "fontWeight" to "bold", "paddingTop" to 5, "paddingRight" to 12, "paddingBottom" to 5, "paddingLeft" to 12, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12)), "status-on" to _pS(_uM("color" to "#8FF0C3", "backgroundColor" to "rgba(143,240,195,0.12)")), "status-off" to _pS(_uM("color" to "rgba(255,255,255,0.55)", "backgroundColor" to "rgba(255,255,255,0.08)")), "step-btn" to _pS(_uM("marginLeft" to 10, "paddingTop" to 6, "paddingRight" to 14, "paddingBottom" to 6, "paddingLeft" to 14, "backgroundColor" to "rgba(18,19,52,0.92)", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.24)", "borderRightColor" to "rgba(219,200,237,0.24)", "borderBottomColor" to "rgba(219,200,237,0.24)", "borderLeftColor" to "rgba(219,200,237,0.24)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "step-btn-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 12, "fontWeight" to "bold")), "demo-section" to _pS(_uM("marginTop" to 14)), "demo-card" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 16, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.08)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "demo-planet-circle" to _pS(_uM("width" to 52, "height" to 52, "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26, "backgroundColor" to "rgba(219,200,237,0.18)", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 12, "overflow" to "hidden")), "demo-mascot" to _pS(_uM("width" to 44, "height" to 44)), "demo-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "demo-copy-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14, "fontWeight" to "bold")), "demo-copy-text" to _pS(_uM("color" to "rgba(255,255,255,0.65)", "fontSize" to 12, "lineHeight" to "18px", "marginTop" to 3)), "demo-btn" to _pS(_uM("backgroundColor" to "#DBC8ED", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 8, "paddingRight" to 14, "paddingBottom" to 8, "paddingLeft" to 14, "marginLeft" to 8, "alignItems" to "center", "justifyContent" to "center")), "demo-btn-text" to _pS(_uM("color" to "#161A33", "fontSize" to 12, "fontWeight" to "bold")), "demo-flow" to _pS(_uM("marginTop" to 14, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.06)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.12)", "borderRightColor" to "rgba(219,200,237,0.12)", "borderBottomColor" to "rgba(219,200,237,0.12)", "borderLeftColor" to "rgba(219,200,237,0.12)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "demo-progress-bar" to _pS(_uM("height" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "backgroundColor" to "rgba(255,255,255,0.1)", "overflow" to "hidden")), "demo-progress-fill" to _pS(_uM("width" to "64%", "height" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "backgroundImage" to "linear-gradient(90deg, #C2E0EE, #DBC8ED)")), "demo-steps-flow" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "marginTop" to 14)), "demo-flow-step" to _pS(_uM("alignItems" to "center")), "demo-flow-dot" to _pS(_uM("width" to 24, "height" to 24, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "marginBottom" to 4)), "dot-done" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.5)")), "dot-active" to _pS(_uM("backgroundColor" to "#DBC8ED", "boxShadow" to "0 0 10px rgba(219, 200, 237, 0.4)")), "demo-flow-label" to _pS(_uM("color" to "rgba(255,255,255,0.7)", "fontSize" to 11)), "demo-flow-line" to _pS(_uM("width" to 32, "height" to 2, "backgroundColor" to "rgba(219,200,237,0.3)", "marginTop" to 0, "marginRight" to 4, "marginBottom" to 18, "marginLeft" to 4)), "demo-result-text" to _pS(_uM("color" to "rgba(219,200,237,0.82)", "fontSize" to 12, "lineHeight" to "18px", "marginTop" to 14)), "cta-section" to _pS(_uM("paddingTop" to 22, "paddingRight" to 24, "paddingBottom" to 0, "paddingLeft" to 24, "alignItems" to "center")), "cta-primary" to _pS(_uM("width" to "100%", "height" to 52, "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26, "backgroundColor" to "#DBC8ED", "boxShadow" to "0 8px 24px rgba(219, 200, 237, 0.28)", "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 12)), "cta-primary-text" to _pS(_uM("color" to "#161A33", "fontSize" to 16, "fontWeight" to "bold")))
            }
        val styles1: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("cta-secondary" to _pS(_uM("paddingTop" to 9, "paddingRight" to 20, "paddingBottom" to 9, "paddingLeft" to 20, "backgroundColor" to "rgba(255,255,255,0.08)", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "cta-secondary-text" to _pS(_uM("color" to "rgba(255,255,255,0.65)", "fontSize" to 14)), "footer" to _pS(_uM("alignItems" to "center", "paddingTop" to 28, "paddingRight" to 0, "paddingBottom" to 36, "paddingLeft" to 0)), "footer-text" to _pS(_uM("color" to "rgba(255,255,255,0.35)", "fontSize" to 11)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
