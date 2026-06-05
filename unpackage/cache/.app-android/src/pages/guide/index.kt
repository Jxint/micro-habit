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
                openUsageAccessSettings()
                setTimeout(fun(): Unit {
                    refreshPermissionStates()
                }
                , 800)
            }
            val openUsageAccess = ::gen_openUsageAccess_fn
            fun gen_openOverlay_fn(): Unit {
                requestOverlayPermission()
                setTimeout(fun(): Unit {
                    refreshPermissionStates()
                }
                , 800)
            }
            val openOverlay = ::gen_openOverlay_fn
            fun gen_completeGuide_fn(): Unit {
                putInt("guide_completed", 1)
                val now = Math.floor(Date.now() / 1000)
                putInt("newbie_start_date", now)
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
                        _cE("view", _uM("class" to "hero-block"), _uA(
                            _cE("view", _uM("class" to "hero-copy"), _uA(
                                _cE("text", _uM("class" to "hero-brand"), "微憩星球"),
                                _cE("text", _uM("class" to "hero-title"), "轻轻守护，从 10 秒开始"),
                                _cE("text", _uM("class" to "hero-subtitle"), "它会观察你的使用节奏，在合适的时候提醒你眨眨眼、松松肩、慢慢呼吸。")
                            )),
                            _cE("view", _uM("class" to "hero-planet-card"), _uA(
                                _cE("view", _uM("class" to "hero-orbit")),
                                _cE("view", _uM("class" to "hero-icon-wrap"), _uA(
                                    _cE("text", _uM("class" to "hero-icon"), "◐")
                                )),
                                _cE("text", _uM("class" to "hero-chip"), "不说教 · 不打扰")
                            ))
                        )),
                        _cE("view", _uM("class" to "value-block"), _uA(
                            _cE("text", _uM("class" to "block-title"), "AI 健康大脑在做什么"),
                            _cE("text", _uM("class" to "block-subtitle"), "不是催你打卡，而是把使用节奏、完成反馈和健康状态一起看懂。"),
                            _cE("view", _uM("class" to "ai-core-card"), _uA(
                                _cE("view", _uM("class" to "ai-core-icon"), _uA(
                                    _cE("text", _uM("class" to "ai-core-icon-text"), "AI")
                                )),
                                _cE("view", _uM("class" to "ai-core-copy"), _uA(
                                    _cE("text", _uM("class" to "ai-core-title"), "先判断，再轻轻提醒"),
                                    _cE("text", _uM("class" to "ai-core-text"), "本地规则引擎实时看连续使用时长，推荐算法选择当下更适合的护眼、体态或呼吸动作。")
                                ))
                            )),
                            _cE("view", _uM("class" to "value-grid"), _uA(
                                _cE("view", _uM("class" to "value-card"), _uA(
                                    _cE("text", _uM("class" to "value-icon value-icon-blue"), "◌"),
                                    _cE("text", _uM("class" to "value-title"), "解决忘休息"),
                                    _cE("text", _uM("class" to "value-text"), "刷久了才提醒，不靠你自己想起来")
                                )),
                                _cE("view", _uM("class" to "value-card value-card-even"), _uA(
                                    _cE("text", _uM("class" to "value-icon value-icon-purple"), "✦"),
                                    _cE("text", _uM("class" to "value-title"), "推荐做什么"),
                                    _cE("text", _uM("class" to "value-text"), "按时间段和历史完成情况选微动作")
                                )),
                                _cE("view", _uM("class" to "value-card"), _uA(
                                    _cE("text", _uM("class" to "value-icon value-icon-pink"), "⌁"),
                                    _cE("text", _uM("class" to "value-title"), "AI 小结报告"),
                                    _cE("text", _uM("class" to "value-text"), "每日一句话、每周建议和下周小目标")
                                )),
                                _cE("view", _uM("class" to "value-card value-card-even"), _uA(
                                    _cE("text", _uM("class" to "value-icon value-icon-blue"), "◐"),
                                    _cE("text", _uM("class" to "value-title"), "越用越懂你"),
                                    _cE("text", _uM("class" to "value-text"), "完成、跳过、忙一下，都会帮助调节频率")
                                ))
                            )),
                            _cE("text", _uM("class" to "value-privacy"), "只识别你正在使用哪个 App 和连续用了多久，不读取聊天内容、密码或屏幕文字。")
                        )),
                        _cE("view", _uM("class" to "focus-block"), _uA(
                            _cE("text", _uM("class" to "block-title"), "选择守护重点"),
                            _cE("text", _uM("class" to "block-subtitle"), "之后可以随时修改，现在先选一个你最想照顾的方向。"),
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
                                    _cE("text", _uM("class" to "focus-icon"), "◐"),
                                    _cE("text", _uM("class" to "focus-title"), "护眼优先")
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
                                    _cE("text", _uM("class" to "focus-icon"), "◌"),
                                    _cE("text", _uM("class" to "focus-title"), "肩颈优先")
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
                                    _cE("text", _uM("class" to "focus-icon"), "⌁"),
                                    _cE("text", _uM("class" to "focus-title"), "减压呼吸")
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
                                    _cE("text", _uM("class" to "focus-icon"), "✧"),
                                    _cE("text", _uM("class" to "focus-title"), "均衡发展")
                                ), 10, _uA(
                                    "onClick"
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "steps-block"), _uA(
                            _cE("text", _uM("class" to "block-title"), "开启轻轻守护"),
                            _cE("text", _uM("class" to "block-subtitle"), "权限只用于识别 App 和连续使用时长，不读取聊天内容、密码或屏幕文字。"),
                            _cE("view", _uM("class" to "step-card"), _uA(
                                _cE("view", _uM("class" to "step-num-wrap step-num-1"), _uA(
                                    _cE("text", _uM("class" to "step-num"), "1")
                                )),
                                _cE("view", _uM("class" to "step-content"), _uA(
                                    _cE("text", _uM("class" to "step-title"), "开启使用情况权限"),
                                    _cE("text", _uM("class" to "step-desc"), "用来检测你正在使用哪个 App，以及连续使用了多久"),
                                    _cE("view", _uM("class" to "step-status-row"), _uA(
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "step-status",
                                            if (unref(usageEnabled)) {
                                                "step-status-on"
                                            } else {
                                                "step-status-off"
                                            }
                                        ))), _tD(if (unref(usageEnabled)) {
                                            "✅ 已开启"
                                        } else {
                                            "⚪ 未开启"
                                        }
                                        ), 3),
                                        if (isTrue(!unref(usageEnabled))) {
                                            _cE("view", _uM("key" to 0, "class" to "step-action-btn", "onClick" to openUsageAccess), _uA(
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
                                    _cE("text", _uM("class" to "step-desc"), "用来在其他 App 上方显示微动作提醒"),
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
                                    _cE("text", _uM("class" to "step-title"), "后台守护"),
                                    _cE("text", _uM("class" to "step-desc"), "用来保持提醒服务稳定运行，不打扰你的正常使用")
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "demo-block"), _uA(
                            _cE("text", _uM("class" to "block-title"), "演示一次"),
                            _cE("text", _uM("class" to "block-subtitle"), "假设你刷屏久了一点，小星球会这样提醒你。"),
                            _cE("view", _uM("class" to "demo-card"), _uA(
                                _cE("view", _uM("class" to "demo-planet"), _uA(
                                    _cE("text", _uM("class" to "demo-planet-text"), "◐")
                                )),
                                _cE("view", _uM("class" to "demo-copy"), _uA(
                                    _cE("text", _uM("class" to "demo-title"), "星球信号提醒"),
                                    _cE("text", _uM("class" to "demo-text"), "检测到眼睛电量有点低，要不要眨眼 10 秒？")
                                )),
                                _cE("view", _uM("class" to "demo-action", "onClick" to startDemo), _uA(
                                    _cE("text", _uM("class" to "demo-action-text"), _tD(if (unref(demoStarted)) {
                                        "演示中"
                                    } else {
                                        "好啊"
                                    }
                                    ), 1)
                                ))
                            )),
                            if (isTrue(unref(demoStarted))) {
                                _cE("view", _uM("key" to 0, "class" to "demo-flow"), _uA(
                                    _cE("view", _uM("class" to "demo-stage"), _uA(
                                        _cE("view", _uM("class" to "demo-stage-planet"), _uA(
                                            _cE("view", _uM("class" to "demo-stage-orbit")),
                                            _cE("text", _uM("class" to "demo-stage-icon"), "◐")
                                        )),
                                        _cE("view", _uM("class" to "demo-stage-copy"), _uA(
                                            _cE("text", _uM("class" to "demo-stage-title"), "进入 10 秒微动作"),
                                            _cE("text", _uM("class" to "demo-stage-text"), "真实使用时会进入动作页，显示引导文案、倒计时和跳过按钮。")
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "demo-progress"), _uA(
                                        _cE("view", _uM("class" to "demo-progress-fill"))
                                    )),
                                    _cE("view", _uM("class" to "demo-steps"), _uA(
                                        _cE("view", _uM("class" to "demo-step"), _uA(
                                            _cE("text", _uM("class" to "demo-step-num"), "1"),
                                            _cE("text", _uM("class" to "demo-step-text"), "收到提醒")
                                        )),
                                        _cE("view", _uM("class" to "demo-step"), _uA(
                                            _cE("text", _uM("class" to "demo-step-num"), "2"),
                                            _cE("text", _uM("class" to "demo-step-text"), "点“好啊”")
                                        )),
                                        _cE("view", _uM("class" to "demo-step"), _uA(
                                            _cE("text", _uM("class" to "demo-step-num"), "3"),
                                            _cE("text", _uM("class" to "demo-step-text"), "跟着倒计时做完")
                                        ))
                                    )),
                                    _cE("text", _uM("class" to "demo-result"), "完成后，小星球会点亮一次，并把这次照顾记录进健康轨迹。")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                        )),
                        _cE("view", _uM("class" to "cta-block"), _uA(
                            _cE("view", _uM("class" to "cta-btn-primary", "onClick" to startApp), _uA(
                                _cE("text", _uM("class" to "cta-btn-primary-text"), "开始我的小星球")
                            )),
                            _cE("view", _uM("class" to "cta-btn-secondary", "onClick" to skipGuide), _uA(
                                _cE("text", _uM("class" to "cta-btn-secondary-text"), "先逛逛")
                            ))
                        )),
                        _cE("view", _uM("class" to "footer-block"), _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#162038", "backgroundImage" to "linear-gradient(180deg, #131832 0%, #283252 45%, #4F587D 100%)", "position" to "relative", "overflow" to "hidden")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "zIndex" to 1)), "bg-glow" to _pS(_uM("position" to "absolute", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "zIndex" to 0)), "glow-purple" to _pS(_uM("width" to 210, "height" to 210, "left" to -82, "top" to -54, "backgroundColor" to "#776B97", "opacity" to 0.5)), "glow-blue" to _pS(_uM("width" to 168, "height" to 168, "right" to -60, "top" to 104, "backgroundColor" to "#C2E0EE", "opacity" to 0.24)), "glow-pink" to _pS(_uM("width" to 178, "height" to 178, "left" to 42, "bottom" to 88, "backgroundColor" to "#C68DC0", "opacity" to 0.18)), "sky-star" to _pS(_uM("position" to "absolute", "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "rgba(255,255,255,0.74)", "zIndex" to 0)), "star-a" to _pS(_uM("left" to 36, "top" to 88)), "star-b" to _pS(_uM("left" to 118, "top" to 144, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-c" to _pS(_uM("right" to 52, "top" to 116)), "star-d" to _pS(_uM("right" to 26, "top" to 310, "width" to 5, "height" to 5, "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "star-e" to _pS(_uM("left" to 54, "top" to 520)), "star-f" to _pS(_uM("right" to 86, "top" to 420, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-g" to _pS(_uM("left" to 138, "top" to 610)), "star-h" to _pS(_uM("right" to 36, "top" to 720, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-i" to _pS(_uM("left" to 76, "top" to 214, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-j" to _pS(_uM("right" to 148, "top" to 276, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-k" to _pS(_uM("left" to 178, "top" to 392, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-l" to _pS(_uM("right" to 64, "top" to 560, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "hero-block" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "marginTop" to 22, "marginRight" to 14, "marginBottom" to 0, "marginLeft" to 14, "paddingTop" to 24, "paddingRight" to 18, "paddingBottom" to 24, "paddingLeft" to 18, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "flexDirection" to "row", "alignItems" to "center")), "value-block" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "marginTop" to 14, "marginRight" to 16, "marginBottom" to 14, "marginLeft" to 16, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26)), "focus-block" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "marginTop" to 14, "marginRight" to 16, "marginBottom" to 14, "marginLeft" to 16, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26)), "demo-block" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "marginTop" to 14, "marginRight" to 16, "marginBottom" to 14, "marginLeft" to 16, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26)), "step-card" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "flexDirection" to "row", "alignItems" to "flex-start", "marginBottom" to 12, "paddingTop" to 17, "paddingRight" to 17, "paddingBottom" to 17, "paddingLeft" to 17, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24)), "hero-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "paddingRight" to 12)), "hero-brand" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 13, "fontWeight" to "bold", "marginBottom" to 8)), "ai-core-icon-text" to _pS(_uM("color" to "#FFFFFF")), "cta-btn-primary-text" to _pS(_uM("color" to "#161A33", "fontSize" to 16, "fontWeight" to "bold")), "step-action-btn-text" to _pS(_uM("color" to "#FFFFFF")), "step-num" to _pS(_uM("color" to "#FFFFFF")), "demo-action-text" to _pS(_uM("color" to "#161A33", "fontSize" to 12, "fontWeight" to "bold")), "hero-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 25, "fontWeight" to "bold", "lineHeight" to "31px", "marginBottom" to 8)), "hero-subtitle" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 13, "lineHeight" to "21px", "textAlign" to "left", "paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 0, "paddingLeft" to 0)), "block-subtitle" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "19px", "marginTop" to 6)), "ai-core-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "value-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "lineHeight" to "17px")), "value-privacy" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "lineHeight" to "17px")), "step-desc" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "18px", "marginBottom" to 10)), "demo-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "18px", "marginTop" to 3)), "demo-stage-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "demo-step-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "cta-btn-secondary-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "footer-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "hero-planet-card" to _pS(_uM("position" to "relative", "width" to 112, "height" to 136, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "backgroundColor" to "rgba(255,255,255,0.14)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "alignItems" to "center", "justifyContent" to "center")), "hero-orbit" to _pS(_uM("position" to "absolute", "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "transform" to "rotate(-20deg)", "width" to 86, "height" to 34, "borderTopLeftRadius" to 43, "borderTopRightRadius" to 43, "borderBottomRightRadius" to 43, "borderBottomLeftRadius" to 43)), "demo-stage-orbit" to _pS(_uM("position" to "absolute", "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "transform" to "rotate(-20deg)", "width" to 78, "height" to 28, "borderTopLeftRadius" to 39, "borderTopRightRadius" to 39, "borderBottomRightRadius" to 39, "borderBottomLeftRadius" to 39)), "hero-icon-wrap" to _pS(_uM("width" to 68, "height" to 68, "borderTopLeftRadius" to 34, "borderTopRightRadius" to 34, "borderBottomRightRadius" to 34, "borderBottomLeftRadius" to 34, "backgroundColor" to "rgba(219,200,237,0.18)", "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 12)), "hero-icon" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 34)), "hero-chip" to _pS(_uM("color" to "#FFFFFF", "backgroundColor" to "rgba(255,255,255,0.12)", "borderTopLeftRadius" to 13, "borderTopRightRadius" to 13, "borderBottomRightRadius" to 13, "borderBottomLeftRadius" to 13, "paddingTop" to 5, "paddingRight" to 9, "paddingBottom" to 5, "paddingLeft" to 9, "fontSize" to 11)), "block-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 19, "fontWeight" to "bold")), "ai-core-card" to _pS(_uM("marginTop" to 14, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.16)", "borderRightColor" to "rgba(219,200,237,0.16)", "borderBottomColor" to "rgba(219,200,237,0.16)", "borderLeftColor" to "rgba(219,200,237,0.16)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "flexDirection" to "row", "alignItems" to "center")), "demo-flow" to _pS(_uM("marginTop" to 14, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.16)", "borderRightColor" to "rgba(219,200,237,0.16)", "borderBottomColor" to "rgba(219,200,237,0.16)", "borderLeftColor" to "rgba(219,200,237,0.16)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "ai-core-icon" to _pS(_uM("width" to 50, "height" to 50, "borderTopLeftRadius" to 25, "borderTopRightRadius" to 25, "borderBottomRightRadius" to 25, "borderBottomLeftRadius" to 25, "backgroundColor" to "rgba(18,19,52,0.92)", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 12)), "ai-core-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "ai-core-title" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 15, "marginBottom" to 5)), "value-title" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 13, "marginBottom" to 5)), "focus-title" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 13, "marginTop" to 6)), "step-title" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 16, "marginBottom" to 5)), "demo-title" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 14)), "demo-stage-title" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 15, "marginBottom" to 5)), "value-grid" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 14)), "focus-grid" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 14)), "value-card" to _pS(_uM("width" to "48%", "marginRight" to "2%", "marginBottom" to 10, "paddingTop" to 13, "paddingRight" to 11, "paddingBottom" to 13, "paddingLeft" to 11, "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.16)", "borderRightColor" to "rgba(219,200,237,0.16)", "borderBottomColor" to "rgba(219,200,237,0.16)", "borderLeftColor" to "rgba(219,200,237,0.16)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "minHeight" to 120)), "focus-card" to _pS(_uM("width" to "48%", "marginRight" to "2%", "marginBottom" to 10, "paddingTop" to 13, "paddingRight" to 11, "paddingBottom" to 13, "paddingLeft" to 11, "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.16)", "borderRightColor" to "rgba(219,200,237,0.16)", "borderBottomColor" to "rgba(219,200,237,0.16)", "borderLeftColor" to "rgba(219,200,237,0.16)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "alignItems" to "center")), "value-card-even" to _pS(_uM("marginRight" to 0)), "value-icon" to _pS(_uM("width" to 36, "height" to 36, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "textAlign" to "center", "lineHeight" to "36px", "fontSize" to 18, "marginBottom" to 8)), "value-icon-blue" to _pS(_uM("color" to "#C2E0EE", "backgroundColor" to "rgba(194,224,238,0.14)")), "value-icon-purple" to _pS(_uM("color" to "#DBC8ED", "backgroundColor" to "rgba(219,200,237,0.14)")), "value-icon-pink" to _pS(_uM("color" to "#F1CFED", "backgroundColor" to "rgba(198,141,192,0.14)")), "focus-active" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.2)", "borderTopColor" to "rgba(219,200,237,0.42)", "borderRightColor" to "rgba(219,200,237,0.42)", "borderBottomColor" to "rgba(219,200,237,0.42)", "borderLeftColor" to "rgba(219,200,237,0.42)")), "focus-icon" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 24)), "steps-block" to _pS(_uM("paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16)), "step-num-wrap" to _pS(_uM("width" to 36, "height" to 36, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "alignItems" to "center", "justifyContent" to "center", "marginRight" to 12, "marginTop" to 2)), "step-num-1" to _pS(_uM("backgroundColor" to "rgba(194,224,238,0.28)")), "step-num-2" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.28)")), "step-num-3" to _pS(_uM("backgroundColor" to "#DBC8ED")), "step-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "step-status-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "step-status" to _pS(_uM("fontSize" to 12, "fontWeight" to "bold", "paddingTop" to 5, "paddingRight" to 10, "paddingBottom" to 5, "paddingLeft" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12)), "step-status-on" to _pS(_uM("color" to "#8FF0C3", "backgroundColor" to "rgba(143,240,195,0.12)")), "step-status-off" to _pS(_uM("color" to "rgba(255,255,255,0.6)", "backgroundColor" to "rgba(255,255,255,0.08)")), "step-action-btn" to _pS(_uM("marginLeft" to 10, "paddingTop" to 6, "paddingRight" to 13, "paddingBottom" to 6, "paddingLeft" to 13, "backgroundColor" to "rgba(18,19,52,0.92)", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16)), "demo-card" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 14, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.16)", "borderRightColor" to "rgba(219,200,237,0.16)", "borderBottomColor" to "rgba(219,200,237,0.16)", "borderLeftColor" to "rgba(219,200,237,0.16)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "demo-stage" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "demo-planet" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.18)", "alignItems" to "center", "justifyContent" to "center", "width" to 48, "height" to 48, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "marginRight" to 12)), "demo-stage-planet" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.18)", "alignItems" to "center", "justifyContent" to "center", "position" to "relative", "width" to 68, "height" to 68, "borderTopLeftRadius" to 34, "borderTopRightRadius" to 34, "borderBottomRightRadius" to 34, "borderBottomLeftRadius" to 34, "marginRight" to 12)), "demo-planet-text" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 22)), "demo-stage-icon" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 29)), "demo-result" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 12, "lineHeight" to "18px", "marginTop" to 12)), "demo-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "demo-stage-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "demo-action" to _pS(_uM("backgroundColor" to "#DBC8ED", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 8, "paddingRight" to 12, "paddingBottom" to 8, "paddingLeft" to 12, "marginLeft" to 8, "alignItems" to "center", "justifyContent" to "center")), "demo-progress" to _pS(_uM("height" to 9, "borderTopLeftRadius" to 5, "borderTopRightRadius" to 5, "borderBottomRightRadius" to 5, "borderBottomLeftRadius" to 5, "backgroundColor" to "rgba(255,255,255,0.1)", "marginTop" to 14, "overflow" to "hidden")), "demo-progress-fill" to _pS(_uM("width" to "64%", "height" to 9, "borderTopLeftRadius" to 5, "borderTopRightRadius" to 5, "borderBottomRightRadius" to 5, "borderBottomLeftRadius" to 5, "backgroundColor" to "#DBC8ED")), "demo-steps" to _pS(_uM("flexDirection" to "row", "marginTop" to 12)), "demo-step" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "row", "alignItems" to "center", "marginRight" to 6)), "demo-step-num" to _pS(_uM("width" to 20, "height" to 20, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "backgroundColor" to "#DBC8ED", "color" to "#161A33", "fontSize" to 11, "lineHeight" to "20px", "textAlign" to "center", "marginRight" to 5)), "cta-block" to _pS(_uM("paddingTop" to 20, "paddingRight" to 24, "paddingBottom" to 0, "paddingLeft" to 24, "alignItems" to "center")), "cta-btn-primary" to _pS(_uM("width" to "100%", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "backgroundColor" to "#DBC8ED", "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26, "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 10)), "cta-btn-secondary" to _pS(_uM("paddingTop" to 9, "paddingRight" to 18, "paddingBottom" to 9, "paddingLeft" to 18, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.16)", "borderRightColor" to "rgba(219,200,237,0.16)", "borderBottomColor" to "rgba(219,200,237,0.16)", "borderLeftColor" to "rgba(219,200,237,0.16)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")))
            }
        val styles1: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("footer-block" to _pS(_uM("alignItems" to "center", "paddingTop" to 24, "paddingRight" to 0, "paddingBottom" to 32, "paddingLeft" to 0)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
