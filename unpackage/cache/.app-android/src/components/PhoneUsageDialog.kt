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
open class GenComponentsPhoneUsageDialog : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var visible: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsPhoneUsageDialog) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsPhoneUsageDialog
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val summary = ref<PhoneUsageSummary>(PhoneUsageSummary(totalMinutes = 0, totalSeconds = 0, hourText = "0 小时", minuteText = "0 分钟", appCount = 0))
            val segments = ref(_uA<PieSegment>())
            val aiText = ref<String>("")
            val aiLoading = ref<Boolean>(false)
            fun gen_refresh_fn(): Unit {
                try {
                    summary.value = getTodayPhoneUsageSummary()
                }
                 catch (e: Throwable) {
                    console.error("PhoneUsageDialog refresh summary: " + JSON.stringify(e), " at components/PhoneUsageDialog.uvue:104")
                }
                try {
                    val segs = getTodayPieSegments()
                    segments.value = if (segs != null) {
                        segs
                    } else {
                        _uA()
                    }
                }
                 catch (e: Throwable) {
                    console.error("PhoneUsageDialog refresh segments: " + JSON.stringify(e), " at components/PhoneUsageDialog.uvue:110")
                    segments.value = _uA()
                }
                if (summary.value.totalSeconds < 1) {
                    try {
                        val fromSystem = getPhoneUsageFromSystem()
                        if (fromSystem != null && fromSystem.summary.totalSeconds > 0) {
                            summary.value = fromSystem.summary
                            segments.value = if (fromSystem.segments != null) {
                                fromSystem.segments
                            } else {
                                _uA()
                            }
                            console.log("[PhoneUsageDialog] fallback to system data, totalSec=" + fromSystem.summary.totalSeconds, " at components/PhoneUsageDialog.uvue:120")
                        }
                    }
                     catch (e: Throwable) {
                        console.error("PhoneUsageDialog system fallback: " + JSON.stringify(e), " at components/PhoneUsageDialog.uvue:123")
                    }
                }
            }
            val refresh = ::gen_refresh_fn
            fun gen_close_fn(): Unit {
                emit("close")
            }
            val close = ::gen_close_fn
            fun gen_onMaskTap_fn(): Unit {
                close()
            }
            val onMaskTap = ::gen_onMaskTap_fn
            fun gen_onAiTap_fn(): Unit {
                if (aiLoading.value) {
                    return
                }
                aiLoading.value = true
                aiText.value = ""
                try {
                    aiEvaluateTodayUsage(fun(text: String){
                        aiText.value = if (text != null) {
                            text
                        } else {
                            ""
                        }
                        aiLoading.value = false
                    }
                    )
                }
                 catch (e: Throwable) {
                    console.error("AI evaluate error: " + JSON.stringify(e), " at components/PhoneUsageDialog.uvue:146")
                    aiLoading.value = false
                }
            }
            val onAiTap = ::gen_onAiTap_fn
            watch(fun(): Boolean {
                return props.visible
            }
            , fun(v: Boolean): Unit {
                if (v) {
                    refresh()
                    aiText.value = ""
                }
            }
            )
            return fun(): Any? {
                return if (isTrue(_ctx.visible)) {
                    _cE("view", _uM("key" to 0, "class" to "dialog-mask", "onClick" to onMaskTap), _uA(
                        _cE("view", _uM("class" to "dialog-card", "onClick" to withModifiers(fun(){}, _uA(
                            "stop"
                        ))), _uA(
                            _cE("view", _uM("class" to "dialog-header"), _uA(
                                _cE("text", _uM("class" to "dialog-title"), "今日手机使用"),
                                _cE("view", _uM("class" to "dialog-close", "onClick" to close), _uA(
                                    _cE("text", _uM("class" to "dialog-close-text"), "×")
                                ))
                            )),
                            _cE("view", _uM("class" to "summary-row"), _uA(
                                _cE("view", _uM("class" to "summary-num-block"), _uA(
                                    _cE("text", _uM("class" to "summary-num"), _tD(unref(summary).hourText), 1),
                                    _cE("text", _uM("class" to "summary-num-sub"), _tD(unref(summary).minuteText), 1)
                                )),
                                _cE("view", _uM("class" to "summary-stat"), _uA(
                                    _cE("text", _uM("class" to "summary-stat-num"), _tD(unref(summary).appCount), 1),
                                    _cE("text", _uM("class" to "summary-stat-label"), "个应用")
                                ))
                            )),
                            if (unref(segments).length > 0) {
                                _cE("view", _uM("key" to 0, "class" to "chart-block"), _uA(
                                    _cE("view", _uM("class" to "stacked-bar-wrap"), _uA(
                                        _cE("view", _uM("class" to "stacked-bar"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(segments), fun(seg, si, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("sg-" + si), "class" to "stacked-seg", "style" to _nS(_uM("flexGrow" to if (seg.minutes > 0) {
                                                    seg.minutes
                                                } else {
                                                    0
                                                }, "backgroundColor" to seg.color))), null, 4)
                                            }), 128)
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "pie-legend"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(segments), fun(seg, si, __index, _cached): Any {
                                            return _cE("view", _uM("key" to ("lg-" + si), "class" to "legend-row"), _uA(
                                                _cE("view", _uM("class" to "legend-dot", "style" to _nS(_uM("backgroundColor" to seg.color))), null, 4),
                                                _cE("text", _uM("class" to "legend-label"), _tD(seg.label), 1),
                                                _cE("text", _uM("class" to "legend-percent"), _tD(seg.percent) + "%", 1),
                                                _cE("text", _uM("class" to "legend-minutes"), _tD(seg.minutes) + "分", 1)
                                            ))
                                        }), 128)
                                    ))
                                ))
                            } else {
                                _cE("view", _uM("key" to 1, "class" to "empty-block"), _uA(
                                    _cE("text", _uM("class" to "empty-text"), "今日暂无手机使用数据"),
                                    _cE("text", _uM("class" to "empty-hint"), "开启无障碍服务后会自动记录")
                                ))
                            },
                            _cE("view", _uM("class" to "ai-block"), _uA(
                                _cE("view", _uM("class" to "ai-block-header"), _uA(
                                    _cE("text", _uM("class" to "ai-block-title"), "🤖 AI 评价")
                                )),
                                if (unref(aiText).length > 0) {
                                    _cE("view", _uM("key" to 0, "class" to "ai-content"), _uA(
                                        _cE("text", _uM("class" to "ai-text"), _tD(unref(aiText)), 1)
                                    ))
                                } else {
                                    _cE("view", _uM("key" to 1, "class" to "ai-placeholder"), _uA(
                                        _cE("text", _uM("class" to "ai-placeholder-text"), "点击下方按钮，AI 将根据今日使用情况生成评价")
                                    ))
                                },
                                _cE("view", _uM("class" to _nC(_uA(
                                    "ai-btn",
                                    if (unref(aiLoading)) {
                                        "ai-btn-loading"
                                    } else {
                                        ""
                                    }
                                )), "onClick" to onAiTap), _uA(
                                    _cE("text", _uM("class" to "ai-btn-text"), _tD(if (unref(aiLoading)) {
                                        "生成中…"
                                    } else {
                                        "立即生成 AI 评价"
                                    }), 1)
                                ), 2)
                            )),
                            _cE("view", _uM("class" to "bottom-spacer"))
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
                return _uM("dialog-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.58)", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 999)), "dialog-card" to _pS(_uM("width" to "90%", "maxWidth" to 420, "maxHeight" to 560, "backgroundColor" to "rgba(29,25,62,0.96)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.94) 0%, rgba(84, 65, 124, 0.82) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "flexDirection" to "column", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.2)", "borderRightColor" to "rgba(219,200,237,0.2)", "borderBottomColor" to "rgba(219,200,237,0.2)", "borderLeftColor" to "rgba(219,200,237,0.2)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "dialog-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 12, "paddingLeft" to 4)), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#FFFFFF")), "dialog-close" to _pS(_uM("width" to 30, "height" to 30, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "rgba(255,255,255,0.1)", "alignItems" to "center", "justifyContent" to "center")), "dialog-close-text" to _pS(_uM("fontSize" to 18, "color" to "rgba(255,255,255,0.72)", "fontWeight" to "bold")), "hint-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "marginBottom" to 14)), "summary-row" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "marginBottom" to 12)), "ai-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginTop" to 4)), "preview-block" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "picker-value-box" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "time-value-box" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "history-rule" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "achv-progress-wrap" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "hint-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "summary-stat-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "marginLeft" to 4)), "legend-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 12)), "legend-percent" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "fontWeight" to "bold", "marginRight" to 4)), "legend-minutes" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "width" to 32, "textAlign" to "right")), "empty-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-hint" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "ai-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 13, "lineHeight" to "20px")), "ai-placeholder-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "18px")), "preview-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "range-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "picker-unit" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "achv-desc" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "achv-date" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "achv-history" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "action-row" to _pS(_uM("flexDirection" to "row", "marginTop" to 16)), "dialog-buttons" to _pS(_uM("flexDirection" to "row", "marginTop" to 16)), "unlock-buttons" to _pS(_uM("flexDirection" to "row", "marginTop" to 16)), "action-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "unlock-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "action-cancel" to _pS(_uM("marginRight" to 8, "backgroundColor" to "rgba(255,255,255,0.1)")), "btn-secondary" to _pS(_uM("marginRight" to 8, "backgroundColor" to "rgba(255,255,255,0.1)")), "action-confirm" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "btn-primary" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "unlock-btn-primary" to _pS(_uM("marginLeft" to 8, "backgroundColor" to "#DBC8ED")), "action-cancel-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "btn-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "unlock-btn-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 14)), "action-confirm-text" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")), "unlock-btn-text-primary" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")), "summary-num-block" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end")), "summary-stat" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end")), "summary-num" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 24)), "summary-num-sub" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 16, "marginLeft" to 4)), "summary-stat-num" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 18)), "ai-block-title" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 13)), "chart-block" to _pS(_uM("flexDirection" to "column", "paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4, "marginBottom" to 12)), "stacked-bar-wrap" to _pS(_uM("width" to "100%", "paddingTop" to 4, "paddingRight" to 0, "paddingBottom" to 12, "paddingLeft" to 0)), "stacked-bar" to _pS(_uM("width" to "100%", "height" to 16, "flexDirection" to "row", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "overflow" to "hidden", "backgroundColor" to "rgba(255,255,255,0.12)")), "stacked-seg" to _pS(_uM("height" to "100%")), "pie-legend" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "justifyContent" to "center")), "legend-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 6)), "legend-dot" to _pS(_uM("width" to 10, "height" to 10, "borderTopLeftRadius" to 5, "borderTopRightRadius" to 5, "borderBottomRightRadius" to 5, "borderBottomLeftRadius" to 5, "marginRight" to 6)), "empty-block" to _pS(_uM("paddingTop" to 30, "paddingRight" to 16, "paddingBottom" to 30, "paddingLeft" to 16, "alignItems" to "center", "marginBottom" to 12)), "ai-block-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 8)), "ai-content" to _pS(_uM("paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4)), "ai-placeholder" to _pS(_uM("paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4)), "ai-btn" to _pS(_uM("marginTop" to 10, "paddingTop" to 10, "paddingRight" to 14, "paddingBottom" to 10, "paddingLeft" to 14, "backgroundColor" to "#DBC8ED", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "ai-btn-loading" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.18)")), "ai-btn-text" to _pS(_uM("fontSize" to 13, "color" to "#161A33", "fontWeight" to "bold")), "bottom-spacer" to _pS(_uM("height" to 8)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("close" to null)
        var props = _nP(_uM("visible" to _uM("type" to "Boolean", "required" to true, "default" to false)))
        var propsNeedCastKeys = _uA(
            "visible"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
