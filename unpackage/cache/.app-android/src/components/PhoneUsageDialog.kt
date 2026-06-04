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
                return _uM("dialog-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 999)), "dialog-card" to _pS(_uM("width" to "90%", "maxWidth" to 420, "maxHeight" to 560, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "flexDirection" to "column")), "dialog-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 12, "paddingLeft" to 4)), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50")), "dialog-close" to _pS(_uM("width" to 28, "height" to 28, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#F0F3F4", "alignItems" to "center", "justifyContent" to "center")), "dialog-close-text" to _pS(_uM("fontSize" to 18, "color" to "#7F8C8D", "fontWeight" to "bold")), "summary-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "backgroundColor" to "#F8F9FA", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "marginBottom" to 12)), "summary-num-block" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end")), "summary-num" to _pS(_uM("fontSize" to 24, "fontWeight" to "bold", "color" to "#2ECC71")), "summary-num-sub" to _pS(_uM("fontSize" to 16, "fontWeight" to "bold", "color" to "#2ECC71", "marginLeft" to 4)), "summary-stat" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end")), "summary-stat-num" to _pS(_uM("fontSize" to 18, "fontWeight" to "bold", "color" to "#2C3E50")), "summary-stat-label" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6", "marginLeft" to 4)), "chart-block" to _pS(_uM("flexDirection" to "column", "paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4, "marginBottom" to 12)), "stacked-bar-wrap" to _pS(_uM("width" to "100%", "paddingTop" to 4, "paddingRight" to 0, "paddingBottom" to 12, "paddingLeft" to 0)), "stacked-bar" to _pS(_uM("width" to "100%", "height" to 16, "flexDirection" to "row", "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "overflow" to "hidden", "backgroundColor" to "#F0F3F4")), "stacked-seg" to _pS(_uM("height" to "100%")), "pie-legend" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "justifyContent" to "center")), "legend-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 6)), "legend-dot" to _pS(_uM("width" to 10, "height" to 10, "borderTopLeftRadius" to 5, "borderTopRightRadius" to 5, "borderBottomRightRadius" to 5, "borderBottomLeftRadius" to 5, "marginRight" to 6)), "legend-label" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 12, "color" to "#2C3E50")), "legend-percent" to _pS(_uM("fontSize" to 12, "fontWeight" to "bold", "color" to "#7F8C8D", "marginRight" to 4)), "legend-minutes" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "width" to 32, "textAlign" to "right")), "empty-block" to _pS(_uM("paddingTop" to 30, "paddingRight" to 16, "paddingBottom" to 30, "paddingLeft" to 16, "alignItems" to "center", "marginBottom" to 12)), "empty-text" to _pS(_uM("fontSize" to 14, "color" to "#95A5A6")), "empty-hint" to _pS(_uM("fontSize" to 12, "color" to "#BDC3C7", "marginTop" to 4)), "ai-block" to _pS(_uM("backgroundColor" to "#F8F9FA", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginTop" to 4)), "ai-block-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 8)), "ai-block-title" to _pS(_uM("fontSize" to 13, "fontWeight" to "bold", "color" to "#2C3E50")), "ai-content" to _pS(_uM("paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4)), "ai-text" to _pS(_uM("fontSize" to 13, "color" to "#34495E", "lineHeight" to 1.6)), "ai-placeholder" to _pS(_uM("paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4)), "ai-placeholder-text" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6", "lineHeight" to 1.5)), "ai-btn" to _pS(_uM("marginTop" to 10, "paddingTop" to 10, "paddingRight" to 14, "paddingBottom" to 10, "paddingLeft" to 14, "backgroundColor" to "#3498DB", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center")), "ai-btn-loading" to _pS(_uM("backgroundColor" to "#95A5A6")), "ai-btn-text" to _pS(_uM("fontSize" to 13, "color" to "#FFFFFF", "fontWeight" to "bold")), "bottom-spacer" to _pS(_uM("height" to 8)))
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
