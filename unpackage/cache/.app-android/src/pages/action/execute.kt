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
import io.dcloud.uniapp.extapi.vibrateShort as uni_vibrateShort
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
                    console.warn("[execute] emitLlmActionCompleted 失败: " + JSON.stringify(e), " at pages/action/execute.uvue:141")
                }
            }
            val emitLlmActionCompleted = ::gen_emitLlmActionCompleted_fn
            fun recordAction(result: String, skipReason: String = ""): Unit {
                console.log("[execute] recordAction enter, result=" + result, " at pages/action/execute.uvue:146")
                val a = action.value
                if (a == null) {
                    console.log("[execute] recordAction SKIP: action.value is null", " at pages/action/execute.uvue:149")
                    return
                }
                val nowMs = Date.now()
                val dur = if (result === "completed") {
                    a.defaultDurationMs
                } else {
                    Math.floor(a.defaultDurationMs - remainingMs.value)
                }
                val log = ActionLog(id = 0, action_id = a.id, action_type = a.category, result = result, skip_reason = if (skipReason.length > 0) {
                    skipReason
                } else {
                    null
                }
                , trigger_type = "manual", trigger_level = "gentle", duration_ms = dur, target_ms = a.defaultDurationMs, triggered_at = nowMs - 30000, completed_at = nowMs, created_at = Math.floor(nowMs / 1000))
                val insertId = insertActionLog(log)
                console.log("[execute] insertActionLog 返回 id=" + insertId, " at pages/action/execute.uvue:169")
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
                console.log("[execute] startCountdown total=" + remainingMs.value + "ms", " at pages/action/execute.uvue:187")
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
                console.log("[execute] handleAgree", " at pages/action/execute.uvue:202")
                val a = action.value
                if (a == null) {
                    uni_showToast(ShowToastOptions(title = "未找到动作", icon = "none"))
                    return
                }
                isExecuting.value = true
                remainingMs.value = a.defaultDurationMs
                console.log("[execute] countdown start, ms=" + a.defaultDurationMs, " at pages/action/execute.uvue:210")
                startCountdown()
                try {
                    uni_vibrateShort(VibrateShortOptions(type = "light"))
                }
                 catch (_: Throwable) {}
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
                console.log("[execute] onLoad begin", " at pages/action/execute.uvue:247")
                var aid = takeActionId()
                console.log("[execute] from helper aid=" + aid, " at pages/action/execute.uvue:249")
                if (aid.length === 0) {
                    aid = getActionIdFromOption(option)
                    console.log("[execute] from option aid=" + aid, " at pages/action/execute.uvue:252")
                }
                actionId.value = aid
                if (aid.length > 0) {
                    val found = getActionById(aid)
                    action.value = found
                    console.log("[execute] found action=" + (if (found != null) {
                        found.name
                    } else {
                        "null"
                    }), " at pages/action/execute.uvue:258")
                } else {
                    console.log("[execute] no actionId", " at pages/action/execute.uvue:260")
                }
            }
            )
            onUnload(fun(): Unit {
                clearCountdown()
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "debug-bar"), _uA(
                        _cE("text", _uM("class" to "debug-text"), "aid=" + _tD(unref(actionId)) + " | act=" + _tD(unref(actionName)) + " | exec=" + _tD(unref(isExecuting)) + " | done=" + _tD(unref(isCompleted)) + " | ms=" + _tD(unref(remainingMs)), 1)
                    )),
                    _cE("view", _uM("class" to "action-container"), _uA(
                        _cE("text", _uM("class" to "action-title"), _tD(unref(actionName)), 1),
                        _cE("view", _uM("class" to "icon-display"), _uA(
                            _cE("text", _uM("class" to "action-icon"), _tD(unref(iconEmoji)), 1)
                        )),
                        _cE("text", _uM("class" to "tts-text"), _tD(unref(actionTtsText)), 1),
                        if (isTrue(unref(showCountdown))) {
                            _cE("view", _uM("key" to 0), _uA(
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
                            _cE("view", _uM("key" to 1, "class" to "button-group"), _uA(
                                _cE("button", _uM("class" to "action-btn secondary", "onClick" to handleSelfReported), "我做过了"),
                                _cE("button", _uM("class" to "action-btn secondary", "onClick" to handleBusy), "现在忙，晚点提醒我"),
                                _cE("button", _uM("class" to "action-btn primary", "onClick" to handleAgree), "好啊")
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(showSkip))) {
                            _cE("view", _uM("key" to 2, "class" to "button-group"), _uA(
                                _cE("button", _uM("class" to "action-btn secondary", "onClick" to handleSkipDuringExec), "跳过")
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F5F6FA", "justifyContent" to "center", "alignItems" to "center")), "action-container" to _pS(_uM("width" to "85%", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 24, "paddingRight" to 24, "paddingBottom" to 24, "paddingLeft" to 24, "flexDirection" to "column", "alignItems" to "center")), "action-title" to _pS(_uM("fontSize" to 22, "fontWeight" to "bold", "color" to "#2C3E50", "marginBottom" to 16)), "icon-display" to _pS(_uM("width" to 100, "height" to 100, "justifyContent" to "center", "alignItems" to "center", "backgroundColor" to "#F0F3F4", "borderTopLeftRadius" to 50, "borderTopRightRadius" to 50, "borderBottomRightRadius" to 50, "borderBottomLeftRadius" to 50, "marginBottom" to 16)), "action-icon" to _pS(_uM("fontSize" to 48)), "tts-text" to _pS(_uM("fontSize" to 15, "color" to "#7F8C8D", "textAlign" to "center", "marginBottom" to 20, "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16)), "button-group" to _pS(_uM("width" to "100%", "flexDirection" to "column", "marginTop" to 16)), "action-btn" to _uM("" to _uM("width" to "100%", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "fontSize" to 16, "textAlign" to "center", "marginBottom" to 8, "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0), ".primary" to _uM("backgroundColor" to "#3498DB", "color" to "#FFFFFF", "fontWeight" to "bold"), ".secondary" to _uM("backgroundColor" to "#F0F3F4", "color" to "#7F8C8D")), "debug-bar" to _pS(_uM("width" to "100%", "paddingTop" to 4, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8, "backgroundColor" to "#FFE9A8")), "debug-text" to _pS(_uM("fontSize" to 10, "color" to "#8B6914", "fontFamily" to "monospace")), "dialog-overlay" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "justifyContent" to "center", "alignItems" to "center", "backgroundColor" to "rgba(0,0,0,0.4)", "zIndex" to 999)), "dialog-content" to _pS(_uM("width" to 280, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "flexDirection" to "column")), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50", "textAlign" to "center", "marginBottom" to 12)), "dialog-body-text" to _pS(_uM("fontSize" to 14, "color" to "#34495E", "lineHeight" to "20px", "textAlign" to "center", "marginBottom" to 16)), "dialog-actions" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between")), "cancel-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "marginRight" to 8, "paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 10, "paddingLeft" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#ECF0F1", "color" to "#7F8C8D", "fontSize" to 14, "textAlign" to "center", "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0)), "confirm-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "marginLeft" to 8, "paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 10, "paddingLeft" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#3498DB", "color" to "#FFFFFF", "fontSize" to 14, "textAlign" to "center", "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
