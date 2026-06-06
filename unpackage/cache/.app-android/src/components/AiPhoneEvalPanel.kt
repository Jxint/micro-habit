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
import io.dcloud.uniapp.extapi.setClipboardData as uni_setClipboardData
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenComponentsAiPhoneEvalPanel : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var summaryHourText: String by `$props`
    open var summaryMinuteText: String by `$props`
    open var summaryAppCount: Number by `$props`
    open var segments: UTSArray<PieSegment> by `$props`
    open var aiText: String by `$props`
    open var aiLoading: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsAiPhoneEvalPanel) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsAiPhoneEvalPanel
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            var listening: Boolean = false
            val hourTextStr = computed<String>(fun(): String {
                return props.summaryHourText
            }
            )
            val minuteTextStr = computed<String>(fun(): String {
                return props.summaryMinuteText
            }
            )
            val appCountStr = computed<String>(fun(): String {
                return "" + props.summaryAppCount
            }
            )
            fun gen_onAiTap_fn(): Unit {
                if (props.aiLoading) {
                    return
                }
                emit("regenerate")
            }
            val onAiTap = ::gen_onAiTap_fn
            fun gen_onCopy_fn(): Unit {
                try {
                    uni_setClipboardData(SetClipboardDataOptions(data = props.aiText))
                    uni_showToast(ShowToastOptions(title = "已复制", icon = "success"))
                }
                 catch (e: Throwable) {
                    console.warn("[AiPhoneEvalPanel] onCopy 异常: " + JSON.stringify(e), " at components/AiPhoneEvalPanel.uvue:111")
                }
            }
            val onCopy = ::gen_onCopy_fn
            fun gen_onListen_fn(): Unit {
                if (listening) {
                    listening = false
                    emit("stopSpeak")
                    return
                }
                if (props.aiText.length < 1) {
                    return
                }
                listening = true
                emit("speak", props.aiText)
            }
            val onListen = ::gen_onListen_fn
            return fun(): Any? {
                return _cE("view", _uM("class" to "ai-panel"), _uA(
                    if (_ctx.summaryAppCount > 0) {
                        _cE("view", _uM("key" to 0, "class" to "summary-row"), _uA(
                            _cE("view", _uM("class" to "summary-num-block"), _uA(
                                _cE("text", _uM("class" to "summary-num"), _tD(unref(hourTextStr)), 1),
                                _cE("text", _uM("class" to "summary-num-sub"), _tD(unref(minuteTextStr)), 1)
                            )),
                            _cE("view", _uM("class" to "summary-stat"), _uA(
                                _cE("text", _uM("class" to "summary-stat-num"), _tD(unref(appCountStr)), 1),
                                _cE("text", _uM("class" to "summary-stat-label"), "个应用")
                            ))
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (_ctx.segments.length > 0) {
                        _cE("view", _uM("key" to 1, "class" to "chart-block"), _uA(
                            _cE("view", _uM("class" to "stacked-bar"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(_ctx.segments, fun(seg, si, __index, _cached): Any {
                                    return _cE("view", _uM("key" to ("sg-" + si), "class" to "stacked-seg", "style" to _nS(_uM("flexGrow" to if (seg.minutes > 0) {
                                        seg.minutes
                                    } else {
                                        0
                                    }, "backgroundColor" to seg.color))), null, 4)
                                }), 128)
                            )),
                            _cE(Fragment, null, RenderHelpers.renderList(_ctx.segments, fun(seg, si, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("lg-" + si), "class" to "legend-row"), _uA(
                                    _cE("view", _uM("class" to "legend-dot", "style" to _nS(_uM("backgroundColor" to seg.color))), null, 4),
                                    _cE("text", _uM("class" to "legend-label"), _tD(seg.label), 1),
                                    _cE("text", _uM("class" to "legend-percent"), _tD(seg.percent) + "%", 1),
                                    _cE("text", _uM("class" to "legend-minutes"), _tD(seg.minutes) + "分", 1)
                                ))
                            }), 128)
                        ))
                    } else {
                        _cE("view", _uM("key" to 2, "class" to "empty-block"), _uA(
                            _cE("text", _uM("class" to "empty-text"), "今日暂无手机使用数据")
                        ))
                    }
                    ,
                    _cE("view", _uM("class" to "ai-block"), _uA(
                        _cE("view", _uM("class" to "ai-block-header"), _uA(
                            _cE("text", _uM("class" to "ai-block-title"), "AI 评价"),
                            if (_ctx.aiText.length > 0) {
                                _cE("view", _uM("key" to 0, "class" to "ai-action-row"), _uA(
                                    _cE("view", _uM("class" to "ai-action-btn", "onClick" to onCopy), _uA(
                                        _cE("text", _uM("class" to "ai-action-text"), "复制")
                                    )),
                                    _cE("view", _uM("class" to "ai-action-btn", "onClick" to onListen), _uA(
                                        _cE("text", _uM("class" to "ai-action-text"), _tD(if (unref(listening)) {
                                            "停止"
                                        } else {
                                            "朗读"
                                        }), 1)
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                        )),
                        if (isTrue(_ctx.aiLoading)) {
                            _cE("view", _uM("key" to 0, "class" to "ai-loading"), _uA(
                                _cE("text", _uM("class" to "ai-loading-text"), "AI 正在生成评价…")
                            ))
                        } else {
                            if (_ctx.aiText.length > 0) {
                                _cE("view", _uM("key" to 1, "class" to "ai-content"), _uA(
                                    _cE("text", _uM("class" to "ai-text", "selectable" to true, "user-select" to true), _tD(_ctx.aiText), 1)
                                ))
                            } else {
                                _cE("view", _uM("key" to 2, "class" to "ai-placeholder"), _uA(
                                    _cE("text", _uM("class" to "ai-placeholder-text"), "点击下方按钮，AI 将根据今日使用情况生成评价")
                                ))
                            }
                        }
                        ,
                        _cE("view", _uM("class" to _nC(_uA(
                            "ai-btn",
                            if (_ctx.aiLoading) {
                                "ai-btn-loading"
                            } else {
                                ""
                            }
                        )), "onClick" to onAiTap), _uA(
                            _cE("text", _uM("class" to "ai-btn-text"), _tD(if (_ctx.aiLoading) {
                                "生成中…"
                            } else {
                                if (_ctx.aiText.length > 0) {
                                    "重新生成"
                                } else {
                                    "立即生成 AI 评价"
                                }
                            }
                            ), 1)
                        ), 2)
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
                return _uM("ai-panel" to _pS(_uM("flexDirection" to "column")), "summary-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "marginBottom" to 12, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "summary-num-block" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end")), "summary-stat" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end")), "summary-num" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 24)), "summary-num-sub" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 16, "marginLeft" to 4)), "summary-stat-num" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 18)), "ai-block-title" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold", "fontSize" to 14)), "summary-stat-label" to _pS(_uM("fontSize" to 12, "marginLeft" to 4, "color" to "rgba(255,255,255,0.7)")), "chart-block" to _pS(_uM("flexDirection" to "column", "paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4, "marginBottom" to 12)), "stacked-bar" to _pS(_uM("width" to "100%", "height" to 16, "flexDirection" to "row", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "overflow" to "hidden", "backgroundColor" to "rgba(255,255,255,0.12)", "marginBottom" to 8)), "stacked-seg" to _pS(_uM("height" to "100%")), "legend-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 6)), "legend-dot" to _pS(_uM("width" to 10, "height" to 10, "borderTopLeftRadius" to 5, "borderTopRightRadius" to 5, "borderBottomRightRadius" to 5, "borderBottomLeftRadius" to 5, "marginRight" to 6)), "legend-label" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 12, "color" to "rgba(255,255,255,0.78)")), "legend-percent" to _pS(_uM("fontSize" to 12, "fontWeight" to "bold", "color" to "#FFFFFF", "marginRight" to 4)), "legend-minutes" to _pS(_uM("fontSize" to 11, "width" to 32, "textAlign" to "right", "color" to "rgba(255,255,255,0.62)")), "empty-block" to _pS(_uM("paddingTop" to 30, "paddingRight" to 16, "paddingBottom" to 30, "paddingLeft" to 16, "alignItems" to "center", "marginBottom" to 12, "backgroundColor" to "rgba(255,255,255,0.08)", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "empty-text" to _pS(_uM("fontSize" to 13, "color" to "rgba(255,255,255,0.78)")), "ai-block" to _pS(_uM("paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.14)", "borderRightColor" to "rgba(219,200,237,0.14)", "borderBottomColor" to "rgba(219,200,237,0.14)", "borderLeftColor" to "rgba(219,200,237,0.14)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "flexDirection" to "column")), "ai-block-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 8)), "ai-action-row" to _pS(_uM("flexDirection" to "row")), "ai-action-btn" to _pS(_uM("paddingTop" to 5, "paddingRight" to 10, "paddingBottom" to 5, "paddingLeft" to 10, "backgroundColor" to "rgba(255,255,255,0.14)", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "marginLeft" to 6)), "ai-action-text" to _pS(_uM("fontSize" to 11, "color" to "#DBC8ED")), "ai-content" to _pS(_uM("paddingTop" to 4, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0)), "ai-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "22px", "color" to "#FFFFFF")), "ai-placeholder" to _pS(_uM("paddingTop" to 4, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0)), "ai-placeholder-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "rgba(255,255,255,0.62)")), "ai-loading" to _pS(_uM("paddingTop" to 16, "paddingRight" to 0, "paddingBottom" to 16, "paddingLeft" to 0, "alignItems" to "center")), "ai-loading-text" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.78)")), "ai-btn" to _pS(_uM("marginTop" to 12, "paddingTop" to 11, "paddingRight" to 14, "paddingBottom" to 11, "paddingLeft" to 14, "backgroundColor" to "#DBC8ED", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center")), "ai-btn-loading" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.18)")), "ai-btn-text" to _pS(_uM("fontSize" to 13, "color" to "#161A33", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("regenerate" to null, "speak" to null, "stopSpeak" to null)
        var props = _nP(_uM("summaryHourText" to _uM("type" to "String", "required" to true, "default" to "0 小时"), "summaryMinuteText" to _uM("type" to "String", "required" to true, "default" to "0 分钟"), "summaryAppCount" to _uM("type" to "Number", "required" to true, "default" to 0), "segments" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "aiText" to _uM("type" to "String", "required" to true, "default" to ""), "aiLoading" to _uM("type" to "Boolean", "required" to true, "default" to false)))
        var propsNeedCastKeys = _uA(
            "summaryHourText",
            "summaryMinuteText",
            "summaryAppCount",
            "segments",
            "aiText",
            "aiLoading"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
