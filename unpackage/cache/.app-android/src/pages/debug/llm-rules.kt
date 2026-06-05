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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesDebugLlmRules : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesDebugLlmRules) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesDebugLlmRules
            val _cache = __ins.renderCache
            val pendingList = ref(_uA<LlmHistoryEntry>())
            val acceptedCount = ref<Number>(0)
            val rejectedCount = ref<Number>(0)
            val dialogVisible = ref<Boolean>(false)
            val activeItem = ref<LlmHistoryEntry?>(null)
            val emptyPayload = ReviewPayload(historyId = 0, decision = "", targetRuleId = 0, reasoning = "", confidence = 0, suggestedRule = null)
            val dialogPayload = ref<ReviewPayload>(emptyPayload)
            fun gen_pad2_fn(n: Number): String {
                return if (n < 10) {
                    "0" + n
                } else {
                    "" + n
                }
            }
            val pad2 = ::gen_pad2_fn
            fun gen_formatTime_fn(t: Number): String {
                if (t == null || t <= 0) {
                    return "-"
                }
                val d = Date(t * 1000)
                val m = d.getMonth() + 1
                val day = d.getDate()
                val h = d.getHours()
                val mi = d.getMinutes()
                return m + "/" + day + " " + pad2(h) + ":" + pad2(mi)
            }
            val formatTime = ::gen_formatTime_fn
            fun gen_formatConf_fn(c: Number): String {
                if (c == null || c <= 0) {
                    return "0"
                }
                return "" + Math.floor(c * 100)
            }
            val formatConf = ::gen_formatConf_fn
            fun gen_formatAction_fn(id: String): String {
                if (id == null || id.length < 1) {
                    return "-"
                }
                val a = getActionById(id)
                return if (a != null) {
                    a.name
                } else {
                    id
                }
            }
            val formatAction = ::gen_formatAction_fn
            fun gen_decisionText_fn(d: String?): String {
                if (d == "create_rule") {
                    return "新增"
                }
                if (d == "modify_rule") {
                    return "修改"
                }
                if (d == "delete_rule") {
                    return "删除"
                }
                if (d == null || d.length < 1) {
                    return "建议"
                }
                return d
            }
            val decisionText = ::gen_decisionText_fn
            fun gen_openReview_fn(idx: Number): Unit {
                if (idx < 0 || idx >= pendingList.value.length) {
                    return
                }
                val item = pendingList.value[idx]
                if (item == null) {
                    return
                }
                activeItem.value = item
                val p = ReviewPayload(historyId = item.id, decision = if (item.decision != null) {
                    item.decision!!
                } else {
                    ""
                }
                , targetRuleId = if (item.targetRuleId != null) {
                    item.targetRuleId!!
                } else {
                    0
                }
                , reasoning = if (item.reasoning != null) {
                    item.reasoning!!
                } else {
                    ""
                }
                , confidence = item.confidence, suggestedRule = item.suggestedRule)
                dialogPayload.value = p
                dialogVisible.value = true
            }
            val openReview = ::gen_openReview_fn
            fun gen_onDialogClose_fn(): Unit {
                dialogVisible.value = false
                activeItem.value = null
                dialogPayload.value = emptyPayload
            }
            val onDialogClose = ::gen_onDialogClose_fn
            fun gen_refresh_fn(): Unit {
                try {
                    pendingList.value = getPendingRuleReviews(50)
                }
                 catch (e: Throwable) {
                    console.warn("[llm-rules] refresh 异常: " + JSON.stringify(e), " at pages/debug/llm-rules.uvue:167")
                    pendingList.value = _uA()
                }
                try {
                    val rate = getAcceptanceRate(30)
                    acceptedCount.value = rate.accepted
                    rejectedCount.value = rate.rejected
                }
                 catch (e: Throwable) {
                    acceptedCount.value = 0
                    rejectedCount.value = 0
                }
            }
            val refresh = ::gen_refresh_fn
            fun gen_onAccept_fn(_payload: Any): Unit {
                dialogVisible.value = false
                activeItem.value = null
                refresh()
                uni_showToast(ShowToastOptions(title = "已接受", icon = "none"))
            }
            val onAccept = ::gen_onAccept_fn
            fun gen_onReject_fn(_payload: Any): Unit {
                dialogVisible.value = false
                activeItem.value = null
                refresh()
                uni_showToast(ShowToastOptions(title = "已拒绝", icon = "none"))
            }
            val onReject = ::gen_onReject_fn
            fun gen_goBack_fn(): Unit {
                uni_navigateBack(NavigateBackOptions())
            }
            val goBack = ::gen_goBack_fn
            onPageShow(fun(){
                refresh()
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "page-bg"), _uA(
                        _cE("image", _uM("class" to "page-bg-img", "src" to "/static/images/micro-habit-page-bg.png", "mode" to "aspectFill"))
                    )),
                    _cE("view", _uM("class" to "header"), _uA(
                        _cE("view", _uM("class" to "header-left", "onClick" to goBack), _uA(
                            _cE("text", _uM("class" to "header-back"), "‹")
                        )),
                        _cE("text", _uM("class" to "header-title"), "LLM 规则审核"),
                        _cE("view", _uM("class" to "header-right", "onClick" to refresh), _uA(
                            _cE("text", _uM("class" to "header-refresh"), "↻")
                        ))
                    )),
                    _cE("view", _uM("class" to "stats-row"), _uA(
                        _cE("view", _uM("class" to "stat-card"), _uA(
                            _cE("text", _uM("class" to "stat-num"), _tD(unref(pendingList).length), 1),
                            _cE("text", _uM("class" to "stat-label"), "待审核")
                        )),
                        _cE("view", _uM("class" to "stat-card"), _uA(
                            _cE("text", _uM("class" to "stat-num"), _tD(unref(acceptedCount)), 1),
                            _cE("text", _uM("class" to "stat-label"), "已接受")
                        )),
                        _cE("view", _uM("class" to "stat-card"), _uA(
                            _cE("text", _uM("class" to "stat-num"), _tD(unref(rejectedCount)), 1),
                            _cE("text", _uM("class" to "stat-label"), "已拒绝")
                        ))
                    )),
                    _cE("scroll-view", _uM("class" to "log-scroll", "scroll-y" to "true"), _uA(
                        if (unref(pendingList).length < 1) {
                            _cE("view", _uM("key" to 0, "class" to "empty"), _uA(
                                _cE("text", _uM("class" to "empty-text"), "暂无待审核的 AI 规则"),
                                _cE("text", _uM("class" to "empty-hint"), "完成微动作后，AI 会评估是否需要新增/修改/删除触发规则")
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        _cE(Fragment, null, RenderHelpers.renderList(unref(pendingList), fun(item, idx, __index, _cached): Any {
                            return _cE("view", _uM("key" to ("p-" + item.id), "class" to "review-card", "onClick" to fun(){
                                openReview(idx)
                            }
                            ), _uA(
                                _cE("view", _uM("class" to "card-row1"), _uA(
                                    _cE("text", _uM("class" to _nC(_uA(
                                        "card-decision",
                                        "dec-" + (item.decision ?: "")
                                    ))), _tD(decisionText(item.decision)), 3),
                                    _cE("text", _uM("class" to "card-time"), _tD(formatTime(item.createdAt)), 1)
                                )),
                                if (item.suggestedRule != null) {
                                    _cE("text", _uM("key" to 0, "class" to "card-action"), _tD(formatAction(item.suggestedRule!!.actionId)), 1)
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                if (isTrue(item.reasoning != null && item.reasoning!!.length > 0)) {
                                    _cE("text", _uM("key" to 1, "class" to "card-reason"), _tD(item.reasoning), 1)
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                _cE("view", _uM("class" to "card-row2"), _uA(
                                    _cE("text", _uM("class" to "card-conf"), "把握度 " + _tD(formatConf(item.confidence)) + "%", 1),
                                    _cE("text", _uM("class" to "card-go"), "点击审核 →")
                                ))
                            ), 8, _uA(
                                "onClick"
                            ))
                        }
                        ), 128)
                    )),
                    _cV(unref(GenComponentsLlmRuleReviewDialogClass), _uM("visible" to unref(dialogVisible), "historyId" to unref(dialogPayload).historyId, "decision" to unref(dialogPayload).decision, "targetRuleId" to unref(dialogPayload).targetRuleId, "reasoning" to unref(dialogPayload).reasoning, "confidence" to unref(dialogPayload).confidence, "suggestedRule" to unref(dialogPayload).suggestedRule, "onClose" to onDialogClose, "onAccept" to onAccept, "onReject" to onReject), null, 8, _uA(
                        "visible",
                        "historyId",
                        "decision",
                        "targetRuleId",
                        "reasoning",
                        "confidence",
                        "suggestedRule"
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "rgba(0,0,0,0)", "position" to "relative", "overflow" to "hidden")), "page-bg" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "width" to "100%", "height" to "100%", "zIndex" to 0)), "page-bg-img" to _pS(_uM("width" to "100%", "height" to "100%")), "header" to _pS(_uM("position" to "relative", "zIndex" to 1, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 50, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "backgroundColor" to "#FFFFFF", "borderBottomWidth" to 1, "borderBottomColor" to "#ECF0F1", "borderBottomStyle" to "solid")), "stats-row" to _pS(_uM("position" to "relative", "zIndex" to 1, "flexDirection" to "row", "paddingTop" to 12, "paddingRight" to 8, "paddingBottom" to 12, "paddingLeft" to 8, "backgroundColor" to "#FFFFFF", "marginBottom" to 8)), "log-scroll" to _pS(_uM("position" to "relative", "zIndex" to 1, "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 8, "paddingRight" to 12, "paddingBottom" to 8, "paddingLeft" to 12)), "header-left" to _pS(_uM("width" to 32, "height" to 32, "alignItems" to "center", "justifyContent" to "center")), "header-right" to _pS(_uM("width" to 32, "height" to 32, "alignItems" to "center", "justifyContent" to "center")), "header-back" to _pS(_uM("fontSize" to 28, "color" to "#2C3E50")), "header-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50")), "header-refresh" to _pS(_uM("fontSize" to 22, "color" to "#3498DB")), "stat-card" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "alignItems" to "center", "paddingTop" to 4, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0)), "stat-num" to _pS(_uM("fontSize" to 20, "fontWeight" to "bold", "color" to "#3498DB", "marginBottom" to 2)), "stat-label" to _pS(_uM("fontSize" to 11, "color" to "#7F8C8D")), "empty" to _pS(_uM("alignItems" to "center", "paddingTop" to 60, "paddingRight" to 20, "paddingBottom" to 60, "paddingLeft" to 20)), "empty-text" to _pS(_uM("fontSize" to 14, "color" to "#7F8C8D", "marginBottom" to 6)), "empty-hint" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6", "textAlign" to "center")), "review-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 8, "flexDirection" to "column")), "card-row1" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 6)), "card-decision" to _pS(_uM("fontSize" to 11, "fontWeight" to "bold", "paddingTop" to 2, "paddingRight" to 8, "paddingBottom" to 2, "paddingLeft" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "marginRight" to 8)), "dec-create_rule" to _pS(_uM("backgroundColor" to "#D5F5E3", "color" to "#239B56")), "dec-modify_rule" to _pS(_uM("backgroundColor" to "#FCF3CF", "color" to "#B7950B")), "dec-delete_rule" to _pS(_uM("backgroundColor" to "#FADBD8", "color" to "#C0392B")), "card-time" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "fontFamily" to "monospace")), "card-action" to _pS(_uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#2C3E50", "marginBottom" to 4)), "card-reason" to _pS(_uM("fontSize" to 12, "color" to "#34495E", "lineHeight" to 1.5, "marginBottom" to 6)), "card-row2" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "card-conf" to _pS(_uM("fontSize" to 11, "color" to "#7F8C8D")), "card-go" to _pS(_uM("fontSize" to 11, "color" to "#3498DB", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
