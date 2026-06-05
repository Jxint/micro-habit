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
open class GenComponentsHeatmapCalendar : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var dates: UTSArray<String> by `$props`
    open var counts: UTSArray<Number> by `$props`
    open var height: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsHeatmapCalendar) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsHeatmapCalendar
            val _cache = __ins.renderCache
            val props = __props
            val wrapperH = props.height
            val weekdayLabels = _uA(
                "一",
                "",
                "三",
                "",
                "五",
                "",
                "日"
            ) as UTSArray<String>
            fun gen_getMonthLabel_fn(): String {
                if (props.dates.length < 1) {
                    return ""
                }
                val first = props.dates[0]
                if (first == null) {
                    return ""
                }
                val parts = first.split("-")
                return parts[0] + " 年 " + parseInt(parts[1] as String) + " 月"
            }
            val getMonthLabel = ::gen_getMonthLabel_fn
            fun gen_getCellGrid_fn(): UTSArray<UTSArray<CellInfo>> {
                val rows: UTSArray<UTSArray<CellInfo>> = _uA()
                var firstDayOfWeek: Number = 0
                if (props.dates.length > 0) {
                    val first = props.dates[0]
                    if (first != null) {
                        val parts = first.split("-")
                        val yy = parseInt(parts[0] as String)
                        val mm = parseInt(parts[1] as String) - 1
                        val dd = parseInt(parts[2] as String)
                        val fd = Date(yy, mm, dd)
                        var dow = fd.getDay()
                        if (dow < 1) {
                            dow = 7
                        }
                        firstDayOfWeek = dow - 1
                    }
                }
                run {
                    var i: Number = 0
                    while(i < props.dates.length){
                        val pos = i + firstDayOfWeek
                        val r = Math.floor(pos / 7)
                        val c = pos % 7
                        while(rows.length <= r){
                            val row: UTSArray<CellInfo> = _uA()
                            run {
                                var k: Number = 0
                                while(k < 7){
                                    row.push(CellInfo(day = null, count = 0))
                                    k++
                                }
                            }
                            rows.push(row)
                        }
                        val targetRow = rows[r]
                        if (targetRow == null) {
                            i++
                            continue
                        }
                        val dateStr = props.dates[i]
                        if (dateStr == null) {
                            i++
                            continue
                        }
                        val parts = dateStr.split("-")
                        val day = parseInt(parts[2] as String)
                        val cnt = if (i < props.counts.length) {
                            (props.counts[i] as Number)
                        } else {
                            0
                        }
                        targetRow[c] = CellInfo(day = day, count = cnt)
                        i++
                    }
                }
                return rows
            }
            val getCellGrid = ::gen_getCellGrid_fn
            val monthLabel = computed<String>(getMonthLabel)
            val cellGrid = computed<UTSArray<UTSArray<CellInfo>>>(getCellGrid)
            fun gen_cellStyle_fn(count: Number): UTSJSONObject {
                if (count >= 5) {
                    return _uO("backgroundColor" to "#8FF0C3")
                }
                if (count >= 3) {
                    return _uO("backgroundColor" to "#8DA2FF")
                }
                if (count >= 1) {
                    return _uO("backgroundColor" to "#9A86D6")
                }
                return _uO("backgroundColor" to "rgba(219, 200, 237, 0.16)")
            }
            val cellStyle = ::gen_cellStyle_fn
            return fun(): Any? {
                return _cE("view", _uM("class" to "chart-wrapper", "style" to _nS(_uM("height" to (unref(wrapperH) + "px")))), _uA(
                    _cE("view", _uM("class" to "header-row"), _uA(
                        _cE("text", _uM("class" to "header-month"), _tD(unref(monthLabel)), 1)
                    )),
                    _cE("view", _uM("class" to "grid-area"), _uA(
                        _cE("view", _uM("class" to "weekday-col"), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(weekdayLabels, fun(d, di, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("wd-" + di), "class" to "weekday-cell"), _uA(
                                    _cE("text", _uM("class" to "weekday-text"), _tD(d), 1)
                                ))
                            }
                            ), 64)
                        )),
                        _cE("view", _uM("class" to "cells-col"), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(unref(cellGrid), fun(row, ri, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("r-" + ri), "class" to "cell-row"), _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(row, fun(cell, ci, __index, _cached): Any {
                                        return _cE("view", _uM("key" to ("c-" + ri + "-" + ci), "class" to _nC(_uA(
                                            "cell",
                                            if (cell.day == null) {
                                                "cell-empty"
                                            } else {
                                                "cell-filled"
                                            }
                                        )), "style" to _nS(cellStyle(cell.count))), _uA(
                                            if (cell.day != null) {
                                                _cE("text", _uM("key" to 0, "class" to _nC(_uA(
                                                    "cell-text",
                                                    if (cell.count > 0) {
                                                        "cell-text-on"
                                                    } else {
                                                        "cell-text-off"
                                                    }
                                                ))), _tD(cell.day), 3)
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        ), 6)
                                    }
                                    ), 128)
                                ))
                            }
                            ), 128)
                        ))
                    )),
                    _cE("view", _uM("class" to "legend-row"), _uA(
                        _cE("text", _uM("class" to "legend-text"), "少"),
                        _cE("view", _uM("class" to "legend-cell", "style" to _nS(_uM("background-color" to "rgba(219, 200, 237, 0.16)"))), null, 4),
                        _cE("view", _uM("class" to "legend-cell", "style" to _nS(_uM("background-color" to "#9A86D6"))), null, 4),
                        _cE("view", _uM("class" to "legend-cell", "style" to _nS(_uM("background-color" to "#8DA2FF"))), null, 4),
                        _cE("view", _uM("class" to "legend-cell", "style" to _nS(_uM("background-color" to "#8FF0C3"))), null, 4),
                        _cE("text", _uM("class" to "legend-text"), "多")
                    ))
                ), 4)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("chart-wrapper" to _pS(_uM("flexDirection" to "column", "width" to "100%", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "paddingTop" to 8, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8)), "header-row" to _pS(_uM("paddingTop" to 0, "paddingRight" to 4, "paddingBottom" to 6, "paddingLeft" to 4)), "header-month" to _pS(_uM("fontSize" to 13, "fontWeight" to "bold", "color" to "#FFFFFF")), "grid-area" to _pS(_uM("flexDirection" to "row", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "weekday-col" to _pS(_uM("width" to 20, "flexDirection" to "column", "paddingTop" to 0)), "weekday-cell" to _pS(_uM("height" to 32, "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 4)), "weekday-text" to _pS(_uM("fontSize" to 9, "color" to "rgba(255,255,255,0.56)")), "cells-col" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "paddingLeft" to 4, "minHeight" to 200)), "cell-row" to _pS(_uM("flexDirection" to "row", "marginBottom" to 4, "height" to 32)), "cell" to _pS(_uM("width" to 32, "height" to 32, "marginRight" to 4, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(255,255,255,0.1)")), "cell-empty" to _pS(_uM("backgroundColor" to "rgba(0,0,0,0)")), "cell-filled" to _pS(_uM("borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "cell-text" to _pS(_uM("fontSize" to 10, "fontWeight" to "500")), "cell-text-on" to _pS(_uM("color" to "#111832")), "cell-text-off" to _pS(_uM("color" to "rgba(255,255,255,0.56)")), "legend-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to 8, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0)), "legend-text" to _pS(_uM("fontSize" to 10, "color" to "rgba(255,255,255,0.58)", "marginTop" to 0, "marginRight" to 6, "marginBottom" to 0, "marginLeft" to 6)), "legend-cell" to _pS(_uM("width" to 12, "height" to 12, "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3, "marginTop" to 0, "marginRight" to 2, "marginBottom" to 0, "marginLeft" to 2)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("dates" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "counts" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "height" to _uM("type" to "Number", "required" to true, "default" to 220)))
        var propsNeedCastKeys = _uA(
            "dates",
            "counts",
            "height"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
