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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F8F7FC", "position" to "relative", "overflow" to "hidden")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "zIndex" to 1)), "hero-block" to _pS(_uM("alignItems" to "center", "paddingTop" to 22, "paddingRight" to 18, "paddingBottom" to 22, "paddingLeft" to 18, "backgroundColor" to "#FFFFFF", "borderBottomLeftRadius" to 30, "borderBottomRightRadius" to 30, "marginTop" to 18, "marginRight" to 16, "marginBottom" to 0, "marginLeft" to 16, "borderTopLeftRadius" to 30, "borderTopRightRadius" to 30, "backgroundImage" to "linear-gradient(135deg, #FFFFFF 0%, #FFF7FB 44%, #F1F7FF 100%)", "boxShadow" to "0 18px 38px rgba(44, 35, 80, 0.08)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEEAF6", "borderRightColor" to "#EEEAF6", "borderBottomColor" to "#EEEAF6", "borderLeftColor" to "#EEEAF6", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "flexDirection" to "row")), "hero-icon-wrap" to _pS(_uM("width" to 68, "height" to 68, "borderTopLeftRadius" to 34, "borderTopRightRadius" to 34, "borderBottomRightRadius" to 34, "borderBottomLeftRadius" to 34, "backgroundColor" to "#EEF7FF", "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 12, "boxShadow" to "0 14px 26px rgba(109, 174, 255, 0.2)")), "hero-icon" to _pS(_uM("fontSize" to 34, "color" to "#8D6BFF")), "hero-title" to _pS(_uM("fontSize" to 25, "fontWeight" to "bold", "color" to "#191528", "marginBottom" to 8, "lineHeight" to "31px")), "hero-subtitle" to _pS(_uM("fontSize" to 13, "color" to "#7B758C", "textAlign" to "left", "lineHeight" to "21px", "paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 0, "paddingLeft" to 0)), "steps-block" to _pS(_uM("paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16)), "step-card" to _pS(_uM("flexDirection" to "row", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "paddingTop" to 17, "paddingRight" to 17, "paddingBottom" to 17, "paddingLeft" to 17, "marginBottom" to 12, "alignItems" to "flex-start", "boxShadow" to "0 14px 30px rgba(44, 35, 80, 0.06)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEEAF6", "borderRightColor" to "#EEEAF6", "borderBottomColor" to "#EEEAF6", "borderLeftColor" to "#EEEAF6", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "step-card-info" to _pS(_uM("backgroundColor" to "#F7FBFA")), "step-num-wrap" to _pS(_uM("width" to 36, "height" to 36, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "alignItems" to "center", "justifyContent" to "center", "marginRight" to 12, "marginTop" to 2, "flexShrink" to 0)), "step-num-1" to _pS(_uM("backgroundColor" to "#CFE7FF")), "step-num-2" to _pS(_uM("backgroundColor" to "#CBB8FF")), "step-num-3" to _pS(_uM("backgroundColor" to "#1F1933")), "step-num" to _pS(_uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#FFFFFF")), "step-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "step-title" to _pS(_uM("fontSize" to 16, "fontWeight" to "bold", "color" to "#191528", "marginBottom" to 5)), "step-desc" to _pS(_uM("fontSize" to 12, "color" to "#7B758C", "lineHeight" to "18px", "marginBottom" to 10)), "step-status-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "step-status" to _pS(_uM("fontSize" to 12, "fontWeight" to "bold", "paddingTop" to 5, "paddingRight" to 10, "paddingBottom" to 5, "paddingLeft" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12)), "step-status-on" to _pS(_uM("color" to "#4FCB8D", "backgroundColor" to "#EAF8F1")), "step-status-off" to _pS(_uM("color" to "#A9A3B8", "backgroundColor" to "#F4F2FA")), "step-action-btn" to _pS(_uM("marginLeft" to 10, "paddingTop" to 6, "paddingRight" to 13, "paddingBottom" to 6, "paddingLeft" to 13, "backgroundColor" to "#1F1933", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16)), "step-action-btn-text" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "fontWeight" to "bold")), "cta-block" to _pS(_uM("paddingTop" to 20, "paddingRight" to 24, "paddingBottom" to 0, "paddingLeft" to 24, "alignItems" to "center")), "cta-btn-primary" to _pS(_uM("width" to "100%", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "backgroundColor" to "#1F1933", "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26, "alignItems" to "center", "justifyContent" to "center", "boxShadow" to "0 12px 26px rgba(31, 25, 51, 0.22)", "marginBottom" to 10)), "cta-btn-primary-text" to _pS(_uM("fontSize" to 16, "color" to "#FFFFFF", "fontWeight" to "bold")), "cta-btn-secondary" to _pS(_uM("paddingTop" to 9, "paddingRight" to 18, "paddingBottom" to 9, "paddingLeft" to 18, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEEAF6", "borderRightColor" to "#EEEAF6", "borderBottomColor" to "#EEEAF6", "borderLeftColor" to "#EEEAF6", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "cta-btn-secondary-text" to _pS(_uM("fontSize" to 13, "color" to "#7B758C")), "footer-block" to _pS(_uM("alignItems" to "center", "paddingTop" to 24, "paddingRight" to 0, "paddingBottom" to 32, "paddingLeft" to 0)), "footer-text" to _pS(_uM("fontSize" to 11, "color" to "#A9A3B8")), "focus-block" to _pS(_uM("marginTop" to 14, "marginRight" to 16, "marginBottom" to 14, "marginLeft" to 16, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26, "backgroundColor" to "#FFFFFF", "boxShadow" to "0 14px 30px rgba(44, 35, 80, 0.06)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEEAF6", "borderRightColor" to "#EEEAF6", "borderBottomColor" to "#EEEAF6", "borderLeftColor" to "#EEEAF6", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "block-title" to _pS(_uM("fontSize" to 19, "fontWeight" to "bold", "color" to "#191528")), "block-subtitle" to _pS(_uM("fontSize" to 12, "color" to "#7B758C", "lineHeight" to "19px", "marginTop" to 6)), "focus-grid" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 12)), "focus-card" to _pS(_uM("width" to "48%", "marginRight" to "2%", "marginBottom" to 10, "paddingTop" to 14, "paddingRight" to 10, "paddingBottom" to 14, "paddingLeft" to 10, "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "backgroundColor" to "#FBFAFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEEAF6", "borderRightColor" to "#EEEAF6", "borderBottomColor" to "#EEEAF6", "borderLeftColor" to "#EEEAF6", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "alignItems" to "center")), "focus-active" to _pS(_uM("backgroundColor" to "#F1ECFF", "borderTopColor" to "#CBB8FF", "borderRightColor" to "#CBB8FF", "borderBottomColor" to "#CBB8FF", "borderLeftColor" to "#CBB8FF")), "focus-icon" to _pS(_uM("fontSize" to 24, "color" to "#8D6BFF")), "focus-title" to _pS(_uM("fontSize" to 13, "color" to "#191528", "fontWeight" to "bold", "marginTop" to 6)), "demo-block" to _pS(_uM("marginTop" to 14, "marginRight" to 16, "marginBottom" to 14, "marginLeft" to 16, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26, "backgroundColor" to "#FFFFFF", "boxShadow" to "0 14px 30px rgba(44, 35, 80, 0.06)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEEAF6", "borderRightColor" to "#EEEAF6", "borderBottomColor" to "#EEEAF6", "borderLeftColor" to "#EEEAF6", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "demo-card" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 14, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "#F7FBFA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEF4F2", "borderRightColor" to "#EEF4F2", "borderBottomColor" to "#EEF4F2", "borderLeftColor" to "#EEF4F2", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "demo-planet" to _pS(_uM("width" to 48, "height" to 48, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "backgroundColor" to "#F1ECFF", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 12)), "demo-planet-text" to _pS(_uM("color" to "#8D6BFF", "fontSize" to 22)), "demo-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "demo-title" to _pS(_uM("fontSize" to 14, "color" to "#191528", "fontWeight" to "bold")), "demo-text" to _pS(_uM("fontSize" to 12, "color" to "#7B758C", "lineHeight" to "18px", "marginTop" to 3)), "demo-action" to _pS(_uM("backgroundColor" to "#1F1933", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 8, "paddingRight" to 12, "paddingBottom" to 8, "paddingLeft" to 12, "marginLeft" to 8, "color" to "#FFFFFF", "alignItems" to "center", "justifyContent" to "center")), "hero-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "paddingRight" to 12)), "hero-brand" to _pS(_uM("fontSize" to 13, "color" to "#8D6BFF", "fontWeight" to "bold", "marginBottom" to 8)), "hero-planet-card" to _pS(_uM("position" to "relative", "width" to 112, "height" to 136, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "backgroundColor" to "rgba(255,255,255,0.78)", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEEAF6", "borderRightColor" to "#EEEAF6", "borderBottomColor" to "#EEEAF6", "borderLeftColor" to "#EEEAF6", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "hero-orbit" to _pS(_uM("position" to "absolute", "width" to 86, "height" to 34, "borderTopLeftRadius" to 43, "borderTopRightRadius" to 43, "borderBottomRightRadius" to 43, "borderBottomLeftRadius" to 43, "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(141,107,255,0.32)", "borderRightColor" to "rgba(141,107,255,0.32)", "borderBottomColor" to "rgba(141,107,255,0.32)", "borderLeftColor" to "rgba(141,107,255,0.32)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "transform" to "rotate(-20deg)")), "hero-chip" to _pS(_uM("fontSize" to 11, "color" to "#7B758C", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 13, "borderTopRightRadius" to 13, "borderBottomRightRadius" to 13, "borderBottomLeftRadius" to 13, "paddingTop" to 5, "paddingRight" to 9, "paddingBottom" to 5, "paddingLeft" to 9)), "value-block" to _pS(_uM("marginTop" to 14, "marginRight" to 16, "marginBottom" to 14, "marginLeft" to 16, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEEAF6", "borderRightColor" to "#EEEAF6", "borderBottomColor" to "#EEEAF6", "borderLeftColor" to "#EEEAF6", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 14px 30px rgba(44, 35, 80, 0.06)")), "ai-core-card" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 14, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "#F7F4FF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEEAF6", "borderRightColor" to "#EEEAF6", "borderBottomColor" to "#EEEAF6", "borderLeftColor" to "#EEEAF6", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "ai-core-icon" to _pS(_uM("width" to 50, "height" to 50, "borderTopLeftRadius" to 25, "borderTopRightRadius" to 25, "borderBottomRightRadius" to 25, "borderBottomLeftRadius" to 25, "backgroundColor" to "#1F1933", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 12)), "ai-core-icon-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 15, "fontWeight" to "bold")), "ai-core-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "ai-core-title" to _pS(_uM("color" to "#191528", "fontSize" to 15, "fontWeight" to "bold", "marginBottom" to 5)), "ai-core-text" to _pS(_uM("color" to "#7B758C", "fontSize" to 12, "lineHeight" to "18px")), "value-grid" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 14)), "value-card" to _pS(_uM("width" to "48%", "minHeight" to 120, "marginRight" to "2%", "marginBottom" to 10, "paddingTop" to 13, "paddingRight" to 11, "paddingBottom" to 13, "paddingLeft" to 11, "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "backgroundColor" to "#FBFAFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEEAF6", "borderRightColor" to "#EEEAF6", "borderBottomColor" to "#EEEAF6", "borderLeftColor" to "#EEEAF6", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "value-card-even" to _pS(_uM("marginRight" to 0)), "value-icon" to _pS(_uM("width" to 36, "height" to 36, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "textAlign" to "center", "lineHeight" to "36px", "fontSize" to 18, "marginBottom" to 8)), "value-icon-blue" to _pS(_uM("color" to "#6DAEFF", "backgroundColor" to "#EEF7FF")), "value-icon-purple" to _pS(_uM("color" to "#8D6BFF", "backgroundColor" to "#F1ECFF")), "value-icon-pink" to _pS(_uM("color" to "#D880A3", "backgroundColor" to "#FFF0F6")), "value-title" to _pS(_uM("color" to "#191528", "fontSize" to 13, "fontWeight" to "bold", "marginBottom" to 5)), "value-text" to _pS(_uM("color" to "#7B758C", "fontSize" to 11, "lineHeight" to "17px")), "value-privacy" to _pS(_uM("color" to "#A9A3B8", "fontSize" to 11, "lineHeight" to "17px", "marginTop" to 2)), "demo-action-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 12, "fontWeight" to "bold")), "demo-flow" to _pS(_uM("marginTop" to 12, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "#FBFAFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#EEEAF6", "borderRightColor" to "#EEEAF6", "borderBottomColor" to "#EEEAF6", "borderLeftColor" to "#EEEAF6", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "demo-stage" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "demo-stage-planet" to _pS(_uM("position" to "relative", "width" to 68, "height" to 68, "borderTopLeftRadius" to 34, "borderTopRightRadius" to 34, "borderBottomRightRadius" to 34, "borderBottomLeftRadius" to 34, "backgroundColor" to "#EEF7FF", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 12, "boxShadow" to "0 14px 26px rgba(109, 174, 255, 0.18)")), "demo-stage-orbit" to _pS(_uM("position" to "absolute", "width" to 78, "height" to 28, "borderTopLeftRadius" to 39, "borderTopRightRadius" to 39, "borderBottomRightRadius" to 39, "borderBottomLeftRadius" to 39, "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(141,107,255,0.28)", "borderRightColor" to "rgba(141,107,255,0.28)", "borderBottomColor" to "rgba(141,107,255,0.28)", "borderLeftColor" to "rgba(141,107,255,0.28)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "transform" to "rotate(-19deg)")), "demo-stage-icon" to _pS(_uM("color" to "#8D6BFF", "fontSize" to 29)), "demo-stage-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "demo-stage-title" to _pS(_uM("color" to "#191528", "fontSize" to 15, "fontWeight" to "bold", "marginBottom" to 5)), "demo-stage-text" to _pS(_uM("color" to "#7B758C", "fontSize" to 12, "lineHeight" to "18px")), "demo-progress" to _pS(_uM("height" to 9, "borderTopLeftRadius" to 5, "borderTopRightRadius" to 5, "borderBottomRightRadius" to 5, "borderBottomLeftRadius" to 5, "backgroundColor" to "#F1ECFF", "marginTop" to 14, "overflow" to "hidden")), "demo-progress-fill" to _pS(_uM("width" to "64%", "height" to 9, "borderTopLeftRadius" to 5, "borderTopRightRadius" to 5, "borderBottomRightRadius" to 5, "borderBottomLeftRadius" to 5, "backgroundColor" to "#CBB8FF")), "demo-steps" to _pS(_uM("flexDirection" to "row", "marginTop" to 12)), "demo-step" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "row", "alignItems" to "center", "marginRight" to 6)), "demo-step-num" to _pS(_uM("width" to 20, "height" to 20, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "backgroundColor" to "#1F1933", "color" to "#FFFFFF", "fontSize" to 11, "lineHeight" to "20px", "textAlign" to "center", "marginRight" to 5)), "demo-step-text" to _pS(_uM("color" to "#7B758C", "fontSize" to 11)), "demo-result" to _pS(_uM("color" to "#8D6BFF", "fontSize" to 12, "lineHeight" to "18px", "marginTop" to 12)), "bg-glow" to _pS(_uM("position" to "absolute", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "zIndex" to 0)), "glow-purple" to _pS(_uM("width" to 210, "height" to 210, "left" to -86, "top" to -54, "backgroundColor" to "#CBB8FF", "opacity" to 0.34)), "glow-blue" to _pS(_uM("width" to 166, "height" to 166, "right" to -62, "top" to 116, "backgroundColor" to "#CFE7FF", "opacity" to 0.44)), "glow-pink" to _pS(_uM("width" to 178, "height" to 178, "left" to 42, "bottom" to 92, "backgroundColor" to "#FFE7F1", "opacity" to 0.52)), "sky-star" to _pS(_uM("position" to "absolute", "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "#FFFFFF", "opacity" to 0.64, "zIndex" to 0)), "star-a" to _pS(_uM("left" to 36, "top" to 88)), "star-b" to _pS(_uM("left" to 118, "top" to 144)), "star-c" to _pS(_uM("right" to 52, "top" to 116)), "star-d" to _pS(_uM("right" to 26, "top" to 310)), "star-e" to _pS(_uM("left" to 54, "top" to 520)), "star-f" to _pS(_uM("right" to 86, "top" to 420)), "star-g" to _pS(_uM("left" to 138, "top" to 610)), "star-h" to _pS(_uM("right" to 36, "top" to 720)), "star-i" to _pS(_uM("left" to 76, "top" to 214)), "star-j" to _pS(_uM("right" to 148, "top" to 276)))
            }
        val styles1: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("star-k" to _pS(_uM("left" to 178, "top" to 392)), "star-l" to _pS(_uM("right" to 64, "top" to 560)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
