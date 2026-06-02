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
open class GenComponentsLineChart : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var seriesValues: UTSArray<UTSArray<Number>> by `$props`
    open var seriesNames: UTSArray<String> by `$props`
    open var seriesColors: UTSArray<String> by `$props`
    open var pointLabels: UTSArray<String> by `$props`
    open var title: String by `$props`
    open var height: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsLineChart) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsLineChart
            val _cache = __ins.renderCache
            val props = __props
            val titleAreaH: Number = 24
            val legendAreaH: Number = 24
            val xAxisAreaH: Number = 22
            val wrapperH = props.height
            val chartAreaH = wrapperH - titleAreaH - legendAreaH - xAxisAreaH
            fun gen_pointLeft_fn(pi: Number, total: Number): Number {
                if (total <= 1) {
                    return 0
                }
                return (pi / (total - 1)) * 100
            }
            val pointLeft = ::gen_pointLeft_fn
            fun gen_pointBottom_fn(value: Number): Number {
                val v = if (value < 0) {
                    0
                } else {
                    if (value > 100) {
                        100
                    } else {
                        value
                    }
                }
                return v
            }
            val pointBottom = ::gen_pointBottom_fn
            fun gen_xLabelLeft_fn(pi: Number): Number {
                val total = props.pointLabels.length
                if (total <= 1) {
                    return 0
                }
                return (pi / (total - 1)) * 100
            }
            val xLabelLeft = ::gen_xLabelLeft_fn
            return fun(): Any? {
                return _cE("view", _uM("class" to "chart-wrapper", "style" to _nS(_uM("height" to (unref(wrapperH) + "px")))), _uA(
                    if (_ctx.title.length > 0) {
                        _cE("view", _uM("key" to 0, "class" to "chart-title"), _uA(
                            _cE("text", _uM("class" to "chart-title-text"), _tD(_ctx.title), 1)
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    _cE("view", _uM("class" to "chart-legend"), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(_ctx.seriesNames, fun(name, si, __index, _cached): Any {
                            return _cE("view", _uM("key" to ("leg-" + si), "class" to "legend-item"), _uA(
                                _cE("view", _uM("class" to "legend-dot", "style" to _nS(_uM("backgroundColor" to _ctx.seriesColors[si]))), null, 4),
                                _cE("text", _uM("class" to "legend-text"), _tD(name), 1)
                            ))
                        }
                        ), 128)
                    )),
                    _cE("view", _uM("class" to "chart-area", "style" to _nS(_uM("height" to (chartAreaH + "px")))), _uA(
                        _cE("view", _uM("class" to "y-axis"), _uA(
                            _cE("text", _uM("class" to "y-tick"), "100"),
                            _cE("text", _uM("class" to "y-tick"), "75"),
                            _cE("text", _uM("class" to "y-tick"), "50"),
                            _cE("text", _uM("class" to "y-tick"), "25"),
                            _cE("text", _uM("class" to "y-tick"), "0")
                        )),
                        _cE("view", _uM("class" to "plot-area"), _uA(
                            _cE("view", _uM("class" to "grid-area"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(4, fun(g, __key, __index, _cached): Any {
                                    return _cE("view", _uM("key" to ("g-" + g), "class" to "grid-line"))
                                }
                                ), 64)
                            )),
                            _cE(Fragment, null, RenderHelpers.renderList(_ctx.seriesValues, fun(values, si, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("series-" + si), "class" to "series-layer"), _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(values, fun(v, pi, __index, _cached): Any {
                                        return _cE("view", _uM("key" to ("pt-" + si + "-" + pi), "class" to "point", "style" to _nS(_uM("left" to (pointLeft(pi, values.length) + "%"), "bottom" to (pointBottom(v) + "%"), "backgroundColor" to _ctx.seriesColors[si]))), null, 4)
                                    }
                                    ), 128)
                                ))
                            }
                            ), 128)
                        ))
                    ), 4),
                    _cE("view", _uM("class" to "x-axis"), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(_ctx.pointLabels, fun(p, pi, __index, _cached): Any {
                            return _cE("text", _uM("key" to ("xl-" + pi), "class" to "x-tick", "style" to _nS(_uM("left" to (xLabelLeft(pi) + "%")))), _tD(p), 5)
                        }
                        ), 128)
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
                return _uM("chart-wrapper" to _pS(_uM("flexDirection" to "column", "width" to "100%", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "paddingTop" to 8, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8)), "chart-title" to _pS(_uM("paddingTop" to 0, "paddingRight" to 4, "paddingBottom" to 4, "paddingLeft" to 4)), "chart-title-text" to _pS(_uM("fontSize" to 13, "fontWeight" to "bold", "color" to "#2C3E50")), "chart-legend" to _pS(_uM("flexDirection" to "row", "paddingTop" to 0, "paddingRight" to 4, "paddingBottom" to 6, "paddingLeft" to 4)), "legend-item" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginRight" to 16)), "legend-dot" to _pS(_uM("width" to 10, "height" to 10, "borderTopLeftRadius" to 5, "borderTopRightRadius" to 5, "borderBottomRightRadius" to 5, "borderBottomLeftRadius" to 5, "marginRight" to 4)), "legend-text" to _pS(_uM("fontSize" to 11, "color" to "#7F8C8D")), "chart-area" to _pS(_uM("flexDirection" to "row", "width" to "100%")), "y-axis" to _pS(_uM("width" to 30, "flexDirection" to "column", "justifyContent" to "space-between", "alignItems" to "flex-end", "paddingRight" to 4)), "y-tick" to _pS(_uM("fontSize" to 9, "color" to "#95A5A6")), "plot-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "position" to "relative")), "grid-area" to _pS(_uM("position" to "absolute", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "column", "justifyContent" to "space-between")), "grid-line" to _pS(_uM("width" to "100%", "height" to 1, "backgroundColor" to "#ECF0F1")), "series-layer" to _pS(_uM("position" to "absolute", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0)), "point" to _pS(_uM("position" to "absolute", "width" to 8, "height" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "marginLeft" to -4, "marginBottom" to -4)), "x-axis" to _pS(_uM("height" to 22, "width" to "100%", "flexDirection" to "row", "position" to "relative", "marginLeft" to 30)), "x-tick" to _pS(_uM("position" to "absolute", "fontSize" to 9, "color" to "#95A5A6", "width" to 30, "textAlign" to "center", "marginLeft" to -15)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("seriesValues" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "seriesNames" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "seriesColors" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "pointLabels" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "title" to _uM("type" to "String", "required" to true, "default" to ""), "height" to _uM("type" to "Number", "required" to true, "default" to 260)))
        var propsNeedCastKeys = _uA(
            "seriesValues",
            "seriesNames",
            "seriesColors",
            "pointLabels",
            "title",
            "height"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
