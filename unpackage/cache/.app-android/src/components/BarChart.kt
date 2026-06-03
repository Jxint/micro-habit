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
open class GenComponentsBarChart : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var labels: UTSArray<String> by `$props`
    open var values: UTSArray<Number> by `$props`
    open var barColor: String by `$props`
    open var title: String by `$props`
    open var height: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsBarChart) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsBarChart
            val _cache = __ins.renderCache
            val props = __props
            val wrapperH = props.height
            val rowW: Number = 600
            val barAreaH: Number = 180
            fun gen_getMaxVal_fn(): Number {
                var m: Number = 0
                run {
                    var i: Number = 0
                    while(i < props.values.length){
                        val v = props.values[i] as Number
                        if (v > m) {
                            m = v
                        }
                        i++
                    }
                }
                if (m < 1) {
                    return 1
                }
                return m
            }
            val getMaxVal = ::gen_getMaxVal_fn
            val maxVal = computed<Number>(getMaxVal)
            fun gen_barHpx_fn(v: Number): Number {
                if (v <= 0) {
                    return 0
                }
                val m = maxVal.value
                if (m <= 0) {
                    return 0
                }
                val px = Math.round((v / m) * barAreaH)
                if (px < 4) {
                    return 4
                }
                return px
            }
            val barHpx = ::gen_barHpx_fn
            fun gen_shouldShowLabel_fn(idx: Number): Boolean {
                val len = props.labels.length
                if (len <= 24) {
                    return idx % 2 == 0
                }
                return idx % 7 == 0
            }
            val shouldShowLabel = ::gen_shouldShowLabel_fn
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
                    _cE("scroll-view", _uM("class" to "chart-scroll", "scroll-x" to "true", "show-scrollbar" to false), _uA(
                        _cE("view", _uM("class" to "chart-row", "style" to _nS(_uM("width" to (rowW + "px")))), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(_ctx.values, fun(v, idx, __index, _cached): Any {
                                return _cE("view", _uM("key" to idx, "class" to "bar-col"), _uA(
                                    _cE("view", _uM("class" to "bar-value-row"), _uA(
                                        if (v > 0) {
                                            _cE("text", _uM("key" to 0, "class" to "bar-value-text"), _tD(v), 1)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    )),
                                    _cE("view", _uM("class" to "bar-track"), _uA(
                                        _cE("view", _uM("class" to "bar-fill", "style" to _nS(_uM("height" to (barHpx(v) + "px"), "backgroundColor" to if (v < 1) {
                                            "#E8E8E8"
                                        } else {
                                            _ctx.barColor
                                        }
                                        ))), null, 4)
                                    )),
                                    if (isTrue(shouldShowLabel(idx))) {
                                        _cE("view", _uM("key" to 0, "class" to "bar-label-row"), _uA(
                                            _cE("text", _uM("class" to "bar-label-text"), _tD(_ctx.labels[idx]), 1)
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ))
                            }
                            ), 128)
                        ), 4)
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
                return _uM("chart-wrapper" to _pS(_uM("flexDirection" to "column", "width" to "100%", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 4, "paddingLeft" to 4)), "chart-title" to _pS(_uM("paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 4, "paddingLeft" to 12)), "chart-title-text" to _pS(_uM("fontSize" to 13, "fontWeight" to "bold", "color" to "#2C3E50")), "chart-scroll" to _pS(_uM("width" to "100%", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "chart-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end", "height" to "100%")), "bar-col" to _pS(_uM("width" to 24, "flexShrink" to 0, "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "flex-end", "marginTop" to 0, "marginRight" to 1, "marginBottom" to 0, "marginLeft" to 1)), "bar-value-row" to _pS(_uM("height" to 14, "alignItems" to "center", "justifyContent" to "center")), "bar-value-text" to _pS(_uM("fontSize" to 8, "color" to "#7F8C8D")), "bar-track" to _pS(_uM("width" to "100%", "height" to 180, "flexDirection" to "column", "justifyContent" to "flex-end", "alignItems" to "stretch")), "bar-fill" to _pS(_uM("width" to "70%", "alignSelf" to "center", "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "minHeight" to 1)), "bar-label-row" to _pS(_uM("height" to 20, "alignItems" to "center", "justifyContent" to "center")), "bar-label-text" to _pS(_uM("fontSize" to 8, "color" to "#95A5A6")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("labels" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "values" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "barColor" to _uM("type" to "String", "required" to true, "default" to "#4CAF50"), "title" to _uM("type" to "String", "required" to true, "default" to ""), "height" to _uM("type" to "Number", "required" to true, "default" to 240)))
        var propsNeedCastKeys = _uA(
            "labels",
            "values",
            "barColor",
            "title",
            "height"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
