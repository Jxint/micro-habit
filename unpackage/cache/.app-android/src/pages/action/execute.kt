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
import io.dcloud.uniapp.extapi.`$emit` as uni__emit
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesActionExecute : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesActionExecute) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesActionExecute
            val _cache = __ins.renderCache
            val actionId = ref<String>("")
            val action = ref<MicroAction?>(null)
            val isExecuting = ref<Boolean>(false)
            val isCompleted = ref<Boolean>(false)
            val showEncourage = ref<Boolean>(false)
            val encourageText = ref<String>("")
            val showFeedback = ref<Boolean>(false)
            val remainingMs = ref<Number>(0)
            val lottiePlaying = ref<Boolean>(true)
            val lottieLoop = ref<Boolean>(true)
            var countdownTimerId: Number? = null
            val showButtons = computed<Boolean>(fun(): Boolean {
                val a = action.value
                val exec = isExecuting.value
                val done = isCompleted.value
                return a != null && exec === false && done === false
            }
            )
            val showCountdown = computed<Boolean>(fun(): Boolean {
                return isExecuting.value === true
            }
            )
            val showSkip = computed<Boolean>(fun(): Boolean {
                val exec = isExecuting.value
                val done = isCompleted.value
                return exec === true && done === false
            }
            )
            val actionName = computed<String>(fun(): String {
                val a = action.value
                return if (a != null) {
                    a.name
                } else {
                    "未找到动作"
                }
            }
            )
            val actionTtsText = computed<String>(fun(): String {
                val a = action.value
                return if (a != null) {
                    a.ttsText
                } else {
                    ""
                }
            }
            )
            val actionDuration = computed<Number>(fun(): Number {
                val a = action.value
                return if (a != null) {
                    a.defaultDurationMs
                } else {
                    0
                }
            }
            )
            val iconEmoji = computed<String>(fun(): String {
                val a = action.value
                if (a == null) {
                    return "?"
                }
                val id = a.id
                if (id === "eye_blink") {
                    return "👁"
                }
                if (id === "eye_rotate") {
                    return "👀"
                }
                if (id === "neck_rotate") {
                    return "🔄"
                }
                if (id === "shoulder_roll") {
                    return "💪"
                }
                if (id === "neck_stretch") {
                    return "🧘"
                }
                if (id === "core_tighten") {
                    return "🔥"
                }
                if (id === "heel_raise") {
                    return "🦶"
                }
                if (id === "deep_breath") {
                    return "🌬"
                }
                if (id === "far_gaze") {
                    return "🏔"
                }
                return "?"
            }
            )
            val executeIntro = computed<String>(fun(): String {
                return getExecuteIntro(actionId.value)
            }
            )
            val executeGuide = computed<String>(fun(): String {
                return getExecuteGuide(actionId.value)
            }
            )
            val ritualLabel = computed<String>(fun(): String {
                return if (isExecuting.value) {
                    "正在点亮小星球"
                } else {
                    "10 秒健康仪式"
                }
            }
            )
            fun gen_getActionCueText_fn(id: String): String {
                if (id === "eye_blink") {
                    return "轻闭双眼 2 秒后睁开，重复眨眼，让眼表重新湿润"
                }
                if (id === "eye_rotate") {
                    return "眼球缓慢画圈，顺时针一圈后再逆时针一圈"
                }
                if (id === "neck_rotate") {
                    return "肩膀放松，头部慢慢转向一侧，停 3 秒后回正"
                }
                if (id === "shoulder_roll") {
                    return "双肩向上提起，再向后画圈放下，保持动作缓慢"
                }
                if (id === "neck_stretch") {
                    return "坐直身体，轻轻侧屈头颈，感受肩颈外侧被拉长"
                }
                if (id === "core_tighten") {
                    return "收紧腹部和骨盆底，保持自然呼吸，不要憋气"
                }
                if (id === "heel_raise") {
                    return "双脚踩稳，缓慢踮起脚跟，再控制速度落回地面"
                }
                if (id === "deep_breath") {
                    return "鼻吸 4 秒让腹部鼓起，口呼 6 秒把气息慢慢送出"
                }
                if (id === "far_gaze") {
                    return "看向 6 米外的固定目标，放松眉心和眼周肌肉"
                }
                return "跟随节奏完成这个微动作，保持放松、缓慢和可控"
            }
            val getActionCueText = ::gen_getActionCueText_fn
            val lottieStateText = computed<String>(fun(): String {
                return if (lottiePlaying.value) {
                    getActionCueText(actionId.value)
                } else {
                    "动作已暂停，准备好后继续跟着节奏完成"
                }
            }
            )
            fun gen_toggleLottiePlaying_fn(): Unit {
                lottiePlaying.value = !lottiePlaying.value
            }
            val toggleLottiePlaying = ::gen_toggleLottiePlaying_fn
            fun gen_toggleLottieLoop_fn(): Unit {
                lottieLoop.value = !lottieLoop.value
            }
            val toggleLottieLoop = ::gen_toggleLottieLoop_fn
            fun gen_goBack_fn(): Unit {
                uni_navigateBack(NavigateBackOptions())
            }
            val goBack = ::gen_goBack_fn
            fun gen_getActionIdFromOption_fn(source: Any): String {
                if (source == null) {
                    return ""
                }
                try {
                    val obj = source as UTSJSONObject
                    if (obj == null) {
                        return ""
                    }
                    val raw = obj["actionId"]
                    if (raw == null) {
                        return ""
                    }
                    val str = raw as String
                    return str
                }
                 catch (_: Throwable) {
                    return ""
                }
            }
            val getActionIdFromOption = ::gen_getActionIdFromOption_fn
            fun gen_clearCountdown_fn(): Unit {
                val tid = countdownTimerId
                if (tid != null) {
                    clearInterval(tid)
                    countdownTimerId = null
                }
            }
            val clearCountdown = ::gen_clearCountdown_fn
            fun gen_emitLlmActionCompleted_fn(a: MicroAction, durationMs: Number, result: String): Unit {
                try {
                    uni__emit("llmActionCompleted", _uO("actionId" to a.id, "actionName" to a.name, "actionCategory" to a.category, "durationMs" to durationMs, "result" to result))
                }
                 catch (e: Throwable) {
                    console.warn("[execute] emitLlmActionCompleted 失败: " + JSON.stringify(e), " at pages/action/execute.uvue:205")
                }
            }
            val emitLlmActionCompleted = ::gen_emitLlmActionCompleted_fn
            fun recordAction(result: String, skipReason: String = ""): Unit {
                console.log("[execute] recordAction enter, result=" + result, " at pages/action/execute.uvue:210")
                val a = action.value
                if (a == null) {
                    console.log("[execute] recordAction SKIP: action.value is null", " at pages/action/execute.uvue:213")
                    return
                }
                val nowMs = Date.now()
                val dur = if (result === "completed") {
                    a.defaultDurationMs
                } else {
                    Math.floor(a.defaultDurationMs - remainingMs.value)
                }
                val insertId = insertManualActionLog(a.id, a.category, result, if (skipReason.length > 0) {
                    skipReason
                } else {
                    null
                }
                , dur, a.defaultDurationMs, nowMs - 30000, nowMs, Math.floor(nowMs / 1000))
                console.log("[execute] insertActionLog 返回 id=" + insertId, " at pages/action/execute.uvue:229")
                emitLlmActionCompleted(a, dur, result)
            }
            fun gen_showEncourageNow_fn(): Unit {
                val a = action.value
                val cat = if (a != null) {
                    a.category
                } else {
                    ""
                }
                encourageText.value = getRandomEncourage(cat)
                showEncourage.value = true
                isCompleted.value = true
                setTimeout(fun(): Unit {
                    showEncourage.value = false
                    uni_navigateBack(NavigateBackOptions())
                }
                , 1500)
            }
            val showEncourageNow = ::gen_showEncourageNow_fn
            fun gen_startCountdown_fn(): Unit {
                val interval: Number = 100
                console.log("[execute] startCountdown total=" + remainingMs.value + "ms", " at pages/action/execute.uvue:249")
                countdownTimerId = setInterval(fun(): Unit {
                    remainingMs.value -= interval
                    if (remainingMs.value <= 0) {
                        remainingMs.value = 0
                        clearCountdown()
                        recordAction("completed")
                        val a = action.value
                        onCompleted(if (a != null) {
                            a.category
                        } else {
                            ""
                        }
                        )
                        showEncourageNow()
                    }
                }
                , interval)
            }
            val startCountdown = ::gen_startCountdown_fn
            fun gen_handleAgree_fn(): Unit {
                console.log("[execute] handleAgree", " at pages/action/execute.uvue:264")
                val a = action.value
                if (a == null) {
                    uni_showToast(ShowToastOptions(title = "未找到动作", icon = "none"))
                    return
                }
                isExecuting.value = true
                remainingMs.value = a.defaultDurationMs
                console.log("[execute] countdown start, ms=" + a.defaultDurationMs, " at pages/action/execute.uvue:272")
                startCountdown()
                shortVibrate(1)
            }
            val handleAgree = ::gen_handleAgree_fn
            fun gen_handleSelfReported_fn(): Unit {
                recordAction("self_reported")
                showEncourageNow()
            }
            val handleSelfReported = ::gen_handleSelfReported_fn
            fun gen_handleBusy_fn(): Unit {
                recordAction("skipped", "busy")
                setTimeout(fun(): Unit {
                    uni_navigateBack(NavigateBackOptions())
                }
                , 300)
            }
            val handleBusy = ::gen_handleBusy_fn
            fun gen_handleSkipDuringExec_fn(): Unit {
                clearCountdown()
                showFeedback.value = true
            }
            val handleSkipDuringExec = ::gen_handleSkipDuringExec_fn
            fun gen_onFeedbackConfirm_fn(reason: String): Unit {
                showFeedback.value = false
                recordAction("skipped", reason)
                val a = action.value
                onConsecutiveSkip(if (a != null) {
                    a.category
                } else {
                    ""
                }
                )
                showEncourageNow()
            }
            val onFeedbackConfirm = ::gen_onFeedbackConfirm_fn
            fun gen_onFeedbackCancel_fn(): Unit {
                showFeedback.value = false
                isExecuting.value = false
                uni_navigateBack(NavigateBackOptions())
            }
            val onFeedbackCancel = ::gen_onFeedbackCancel_fn
            onLoad(fun(option){
                console.log("[execute] onLoad begin", " at pages/action/execute.uvue:309")
                var aid = takeActionId()
                console.log("[execute] from helper aid=" + aid, " at pages/action/execute.uvue:311")
                if (aid.length < 1) {
                    aid = getActionIdFromOption(option)
                    console.log("[execute] from option aid=" + aid, " at pages/action/execute.uvue:314")
                }
                actionId.value = aid
                if (aid.length > 0) {
                    val found = getActionById(aid)
                    action.value = found
                    console.log("[execute] found action=" + (if (found != null) {
                        found.name
                    } else {
                        "null"
                    }), " at pages/action/execute.uvue:320")
                } else {
                    console.log("[execute] no actionId", " at pages/action/execute.uvue:322")
                }
            }
            )
            onUnload(fun(): Unit {
                clearCountdown()
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
                    _cE("view", _uM("class" to "back-button", "onClick" to goBack), _uA(
                        _cE("text", _uM("class" to "back-icon"), "‹")
                    )),
                    _cE("view", _uM("class" to "action-container"), _uA(
                        _cE("text", _uM("class" to "ritual-label"), _tD(unref(ritualLabel)), 1),
                        _cE("text", _uM("class" to "action-title"), _tD(unref(actionName)), 1),
                        _cE("text", _uM("class" to "action-subtitle"), _tD(unref(executeIntro)), 1),
                        _cE("view", _uM("class" to _nC(_uA(
                            "lottie-stage",
                            if (unref(lottiePlaying)) {
                                "lottie-stage-playing"
                            } else {
                                "lottie-stage-paused"
                            }
                        ))), _uA(
                            _cE("view", _uM("class" to "lottie-glow"), _uA(
                                _cE("view", _uM("class" to "lottie-orbit")),
                                _cE("view", _uM("class" to "icon-display"), _uA(
                                    _cE("text", _uM("class" to "action-icon"), _tD(unref(iconEmoji)), 1)
                                ))
                            )),
                            _cE("text", _uM("class" to "lottie-placeholder"), _tD(unref(lottieStateText)), 1),
                            _cE("view", _uM("class" to "lottie-controls"), _uA(
                                _cE("view", _uM("class" to "lottie-chip", "onClick" to toggleLottiePlaying), _uA(
                                    _cE("text", _uM("class" to "lottie-chip-text"), _tD(if (unref(lottiePlaying)) {
                                        "暂停"
                                    } else {
                                        "播放"
                                    }
                                    ), 1)
                                )),
                                _cE("view", _uM("class" to _nC(_uA(
                                    "lottie-chip",
                                    if (unref(lottieLoop)) {
                                        "lottie-chip-active"
                                    } else {
                                        ""
                                    }
                                )), "onClick" to toggleLottieLoop), _uA(
                                    _cE("text", _uM("class" to _nC(_uA(
                                        "lottie-chip-text",
                                        if (unref(lottieLoop)) {
                                            "lottie-chip-text-active"
                                        } else {
                                            ""
                                        }
                                    ))), _tD(if (unref(lottieLoop)) {
                                        "循环中"
                                    } else {
                                        "循环"
                                    }
                                    ), 3)
                                ), 2)
                            ))
                        ), 2),
                        if (isTrue(!unref(showCountdown))) {
                            _cE("text", _uM("key" to 0, "class" to "tts-text"), _tD(unref(actionTtsText)), 1)
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(showCountdown))) {
                            _cE("text", _uM("key" to 1, "class" to "tts-text execute-guide"), _tD(unref(executeGuide)), 1)
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(showCountdown))) {
                            _cE("view", _uM("key" to 2), _uA(
                                _cV(unref(GenComponentsCountdownBarClass), _uM("total" to unref(actionDuration), "remaining" to unref(remainingMs)), null, 8, _uA(
                                    "total",
                                    "remaining"
                                ))
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(showButtons))) {
                            _cE("view", _uM("key" to 3, "class" to "button-group"), _uA(
                                _cE("button", _uM("class" to "action-btn action-primary", "onClick" to handleAgree), "好啊"),
                                _cE("button", _uM("class" to "action-btn action-secondary", "onClick" to handleSelfReported), "我做过了"),
                                _cE("button", _uM("class" to "action-btn action-ghost", "onClick" to handleBusy), "现在忙，晚点提醒我")
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(showSkip))) {
                            _cE("view", _uM("key" to 4, "class" to "button-group"), _uA(
                                _cE("button", _uM("class" to "action-btn action-ghost", "onClick" to handleSkipDuringExec), "跳过这次")
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                    )),
                    _cV(unref(GenComponentsEncourageToastClass), _uM("visible" to unref(showEncourage), "text" to unref(encourageText)), null, 8, _uA(
                        "visible",
                        "text"
                    )),
                    _cV(unref(GenComponentsFeedbackDialogClass), _uM("visible" to unref(showFeedback), "onConfirm" to onFeedbackConfirm, "onCancel" to onFeedbackCancel), null, 8, _uA(
                        "visible"
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#162038", "backgroundImage" to "linear-gradient(180deg, #131832 0%, #283252 45%, #4F587D 100%)", "position" to "relative", "overflow" to "hidden", "justifyContent" to "flex-start", "alignItems" to "center", "paddingTop" to 70)), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "zIndex" to 1)), "bg-glow" to _pS(_uM("position" to "absolute", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "zIndex" to 0)), "glow-purple" to _pS(_uM("width" to 210, "height" to 210, "left" to -82, "top" to -54, "backgroundColor" to "#776B97", "opacity" to 0.5)), "glow-blue" to _pS(_uM("width" to 168, "height" to 168, "right" to -60, "top" to 104, "backgroundColor" to "#C2E0EE", "opacity" to 0.24)), "glow-pink" to _pS(_uM("width" to 178, "height" to 178, "left" to 42, "bottom" to 88, "backgroundColor" to "#C68DC0", "opacity" to 0.18)), "sky-star" to _pS(_uM("position" to "absolute", "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "rgba(255,255,255,0.74)", "zIndex" to 0)), "star-a" to _pS(_uM("left" to 36, "top" to 88)), "star-b" to _pS(_uM("left" to 118, "top" to 144, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-c" to _pS(_uM("right" to 52, "top" to 116)), "star-d" to _pS(_uM("right" to 26, "top" to 310, "width" to 5, "height" to 5, "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "star-e" to _pS(_uM("left" to 54, "top" to 520)), "star-f" to _pS(_uM("right" to 86, "top" to 420, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-g" to _pS(_uM("left" to 138, "top" to 610)), "star-h" to _pS(_uM("right" to 36, "top" to 720, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-i" to _pS(_uM("left" to 76, "top" to 214, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-j" to _pS(_uM("right" to 148, "top" to 276, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-k" to _pS(_uM("left" to 178, "top" to 392, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-l" to _pS(_uM("right" to 64, "top" to 560, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "back-button" to _pS(_uM("position" to "absolute", "left" to 18, "top" to 28, "width" to 38, "height" to 38, "borderTopLeftRadius" to 19, "borderTopRightRadius" to 19, "borderBottomRightRadius" to 19, "borderBottomLeftRadius" to 19, "backgroundColor" to "rgba(255,255,255,0.12)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.16)", "borderRightColor" to "rgba(219,200,237,0.16)", "borderBottomColor" to "rgba(219,200,237,0.16)", "borderLeftColor" to "rgba(219,200,237,0.16)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 2)), "back-icon" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 30, "lineHeight" to "34px", "fontWeight" to "bold")), "action-container" to _pS(_uM("width" to "88%", "backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "paddingTop" to 26, "paddingRight" to 22, "paddingBottom" to 22, "paddingLeft" to 22, "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "flexDirection" to "column", "alignItems" to "center", "zIndex" to 1)), "ritual-label" to _pS(_uM("fontSize" to 12, "color" to "#DBC8ED", "fontWeight" to "bold", "marginBottom" to 8)), "action-title" to _pS(_uM("fontSize" to 28, "fontWeight" to "bold", "color" to "#FFFFFF", "marginBottom" to 8, "textAlign" to "center")), "action-subtitle" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14, "textAlign" to "center", "lineHeight" to "20px", "marginBottom" to 18, "paddingTop" to 0, "paddingRight" to 10, "paddingBottom" to 0, "paddingLeft" to 10)), "tts-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14, "lineHeight" to "21px", "textAlign" to "center", "marginBottom" to 18, "paddingTop" to 0, "paddingRight" to 10, "paddingBottom" to 0, "paddingLeft" to 10)), "lottie-placeholder" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "textAlign" to "center")), "lottie-stage" to _pS(_uM("width" to "100%", "minHeight" to 196, "borderTopLeftRadius" to 30, "borderTopRightRadius" to 30, "borderBottomRightRadius" to 30, "borderBottomLeftRadius" to 30, "backgroundColor" to "rgba(255,255,255,0.1)", "backgroundImage" to "linear-gradient(135deg, rgba(79, 88, 125, 0.28) 0%, rgba(219, 200, 237, 0.12) 100%)", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to 18, "paddingRight" to 14, "paddingBottom" to 18, "paddingLeft" to 14, "marginBottom" to 18, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.16)", "borderRightColor" to "rgba(219,200,237,0.16)", "borderBottomColor" to "rgba(219,200,237,0.16)", "borderLeftColor" to "rgba(219,200,237,0.16)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "lottie-stage-playing" to _pS(_uM("boxShadow" to "0 14px 34px rgba(5, 6, 20, 0.24)")), "lottie-stage-paused" to _pS(_uM("opacity" to 0.86)), "lottie-glow" to _pS(_uM("width" to 128, "height" to 128, "borderTopLeftRadius" to 64, "borderTopRightRadius" to 64, "borderBottomRightRadius" to 64, "borderBottomLeftRadius" to 64, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(219,200,237,0.16)", "marginBottom" to 10)), "lottie-orbit" to _pS(_uM("position" to "absolute", "width" to 122, "height" to 48, "borderTopLeftRadius" to 61, "borderTopRightRadius" to 61, "borderBottomRightRadius" to 61, "borderBottomLeftRadius" to 61, "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "icon-display" to _pS(_uM("width" to 90, "height" to 90, "borderTopLeftRadius" to 45, "borderTopRightRadius" to 45, "borderBottomRightRadius" to 45, "borderBottomLeftRadius" to 45, "justifyContent" to "center", "alignItems" to "center", "backgroundColor" to "rgba(219,200,237,0.18)", "backgroundImage" to "linear-gradient(135deg, #DBC8ED 0%, #C68DC0 42%, #4F587D 100%)")), "action-icon" to _pS(_uM("fontSize" to 46)), "lottie-controls" to _pS(_uM("flexDirection" to "row", "marginTop" to 10)), "lottie-chip" to _pS(_uM("paddingTop" to 6, "paddingRight" to 12, "paddingBottom" to 6, "paddingLeft" to 12, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "rgba(255,255,255,0.1)", "marginTop" to 0, "marginRight" to 4, "marginBottom" to 0, "marginLeft" to 4)), "lottie-chip-active" to _pS(_uM("backgroundColor" to "#DBC8ED")), "lottie-chip-text" to _pS(_uM("fontSize" to 11, "color" to "#DBC8ED")), "lottie-chip-text-active" to _pS(_uM("color" to "#161A33")), "execute-guide" to _pS(_uM("color" to "rgba(255,255,255,0.86)")), "button-group" to _pS(_uM("width" to "100%", "flexDirection" to "column", "marginTop" to 14)), "action-btn" to _pS(_uM("width" to "100%", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "fontSize" to 15, "textAlign" to "center", "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "action-primary" to _pS(_uM("backgroundColor" to "#DBC8ED", "color" to "#161A33", "borderTopColor" to "rgba(255,255,255,0.16)", "borderRightColor" to "rgba(255,255,255,0.16)", "borderBottomColor" to "rgba(255,255,255,0.16)", "borderLeftColor" to "rgba(255,255,255,0.16)", "fontWeight" to "bold")), "action-secondary" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.16)", "color" to "rgba(255,255,255,0.78)", "borderTopColor" to "rgba(219,200,237,0.16)", "borderRightColor" to "rgba(219,200,237,0.16)", "borderBottomColor" to "rgba(219,200,237,0.16)", "borderLeftColor" to "rgba(219,200,237,0.16)")), "action-ghost" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.08)", "color" to "rgba(255,255,255,0.72)", "borderTopColor" to "rgba(219,200,237,0.16)", "borderRightColor" to "rgba(219,200,237,0.16)", "borderBottomColor" to "rgba(219,200,237,0.16)", "borderLeftColor" to "rgba(219,200,237,0.16)")), "dialog-overlay" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "justifyContent" to "center", "alignItems" to "center", "backgroundColor" to "rgba(0,0,0,0.4)", "zIndex" to 999)), "dialog-content" to _pS(_uM("width" to 280, "backgroundColor" to "rgba(29,25,62,0.94)", "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "flexDirection" to "column", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#FFFFFF", "textAlign" to "center", "marginBottom" to 12)), "dialog-body-text" to _pS(_uM("fontSize" to 14, "color" to "rgba(255,255,255,0.72)", "lineHeight" to "20px", "textAlign" to "center", "marginBottom" to 16)), "dialog-actions" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between")), "cancel-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 10, "paddingLeft" to 10, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "fontSize" to 14, "textAlign" to "center", "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0, "marginRight" to 8, "backgroundColor" to "rgba(255,255,255,0.1)", "color" to "rgba(255,255,255,0.72)")), "confirm-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 10, "paddingLeft" to 10, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "fontSize" to 14, "textAlign" to "center", "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0, "marginLeft" to 8, "backgroundColor" to "#DBC8ED", "color" to "#161A33")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
