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
open class GenComponentsLlmHistoryCard : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var entries: UTSArray<LlmHistoryEntry> by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsLlmHistoryCard) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsLlmHistoryCard
            val _cache = __ins.renderCache
            val expanded = ref<Boolean>(false)
            fun gen_toggle_fn(): Unit {
                expanded.value = !expanded.value
            }
            val toggle = ::gen_toggle_fn
            fun gen_formatTime_fn(timestamp: Number): String {
                val d = Date(timestamp * 1000)
                val m = d.getMonth() + 1
                val day = d.getDate()
                val h = d.getHours().toString(10).padStart(2, "0")
                val min = d.getMinutes().toString(10).padStart(2, "0")
                return m + "/" + day + " " + h + ":" + min
            }
            val formatTime = ::gen_formatTime_fn
            fun gen_formatRule_fn(r: EffectiveTriggerRule): String {
                val parts: UTSArray<String> = _uA()
                val a = getActionById(r.actionId)
                parts.push(if (a != null) {
                    a.name
                } else {
                    r.actionId
                }
                )
                if (r.timeWindow != null) {
                    parts.push(r.timeWindow!!.start + "~" + r.timeWindow!!.end)
                }
                if (r.timeThresholdMinutes > 0) {
                    parts.push("≥" + r.timeThresholdMinutes + "min")
                }
                return parts.join(" · ")
            }
            val formatRule = ::gen_formatRule_fn
            return fun(): Any? {
                return _cE("view", _uM("class" to "history-card"), _uA(
                    _cE("view", _uM("class" to "history-header", "onClick" to toggle), _uA(
                        _cE("text", _uM("class" to "history-title"), "🤖 AI 历次判断"),
                        _cE("text", _uM("class" to "history-count"), _tD(_ctx.entries.length) + " 条", 1),
                        _cE("text", _uM("class" to "history-arrow"), _tD(if (unref(expanded)) {
                            "▼"
                        } else {
                            "▶"
                        }
                        ), 1)
                    )),
                    if (isTrue(unref(expanded))) {
                        _cE("view", _uM("key" to 0, "class" to "history-list"), _uA(
                            if (_ctx.entries.length < 1) {
                                _cE("view", _uM("key" to 0, "class" to "history-empty"), _uA(
                                    _cE("text", _uM("class" to "history-empty-text"), "暂无记录")
                                ))
                            } else {
                                _cC("v-if", true)
                            },
                            _cE(Fragment, null, RenderHelpers.renderList(_ctx.entries, fun(entry, idx, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("h-" + idx), "class" to "history-item"), _uA(
                                    _cE("view", _uM("class" to "history-item-header"), _uA(
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "history-stage",
                                            if (entry.stage === "pre") {
                                                "stage-pre"
                                            } else {
                                                "stage-post"
                                            }
                                        ))), _tD(if (entry.stage === "pre") {
                                            "触发时"
                                        } else {
                                            "复评时"
                                        }), 3),
                                        _cE("text", _uM("class" to "history-time"), _tD(formatTime(entry.createdAt)), 1)
                                    )),
                                    if (isTrue(entry.adhocText != null && entry.adhocText!!.length > 0)) {
                                        _cE("text", _uM("key" to 0, "class" to "history-adhoc"), " 💬 " + _tD(entry.adhocText), 1)
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    if (isTrue(entry.reasoning != null && entry.reasoning!!.length > 0)) {
                                        _cE("text", _uM("key" to 1, "class" to "history-reasoning"), " 🤔 " + _tD(entry.reasoning), 1)
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    if (entry.suggestedRule != null) {
                                        _cE("view", _uM("key" to 2, "class" to "history-rule"), _uA(
                                            _cE("text", _uM("class" to "history-rule-label"), "建议规则"),
                                            _cE("text", _uM("class" to "history-rule-content"), _tD(formatRule(entry.suggestedRule!!)), 1)
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ))
                            }), 128)
                        ))
                    } else {
                        _cC("v-if", true)
                    }
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
                return _uM("history-card" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "marginTop" to 12, "marginRight" to 16, "marginBottom" to 12, "marginLeft" to 16, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12)), "history-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 4, "paddingLeft" to 4)), "history-title" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "fontWeight" to "bold", "color" to "#FFFFFF")), "history-count" to _pS(_uM("color" to "rgba(255,255,255,0.68)", "fontSize" to 12, "marginRight" to 8)), "history-arrow" to _pS(_uM("color" to "rgba(255,255,255,0.68)", "fontSize" to 12)), "history-time" to _pS(_uM("color" to "rgba(255,255,255,0.68)", "fontSize" to 11)), "history-empty-text" to _pS(_uM("color" to "rgba(255,255,255,0.68)", "fontSize" to 12)), "history-adhoc" to _pS(_uM("color" to "rgba(255,255,255,0.68)", "fontSize" to 12, "lineHeight" to "18px", "marginBottom" to 4)), "history-reasoning" to _pS(_uM("color" to "rgba(255,255,255,0.68)", "fontSize" to 12, "lineHeight" to "18px", "marginBottom" to 4)), "history-rule-label" to _pS(_uM("color" to "rgba(255,255,255,0.68)", "fontSize" to 10, "marginBottom" to 2)), "history-list" to _pS(_uM("marginTop" to 8)), "history-empty" to _pS(_uM("paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "alignItems" to "center")), "history-item" to _pS(_uM("paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4, "borderBottomWidth" to 1, "borderBottomColor" to "rgba(219,200,237,0.14)", "borderBottomStyle" to "solid", "flexDirection" to "column")), "history-item-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 4)), "history-stage" to _pS(_uM("fontSize" to 10, "fontWeight" to "bold", "paddingTop" to 3, "paddingRight" to 7, "paddingBottom" to 3, "paddingLeft" to 7, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "marginRight" to 6)), "stage-pre" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.18)", "color" to "#DBC8ED")), "stage-post" to _pS(_uM("backgroundColor" to "rgba(194,224,238,0.14)", "color" to "#C2E0EE")), "history-rule" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.1)", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "paddingTop" to 8, "paddingRight" to 10, "paddingBottom" to 8, "paddingLeft" to 10, "marginTop" to 4)), "history-rule-content" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("entries" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        )))
        var propsNeedCastKeys = _uA(
            "entries"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
