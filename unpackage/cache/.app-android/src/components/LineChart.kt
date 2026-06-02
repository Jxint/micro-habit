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
import io.dcloud.uniapp.extapi.createCanvasContextAsync as uni_createCanvasContextAsync
import io.dcloud.uniapp.extapi.getDeviceInfo as uni_getDeviceInfo
open class GenComponentsLineChart : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var series: UTSArray<LineSeries__1> by `$props`
    open var title: String by `$props`
    open var height: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsLineChart) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsLineChart
            val _cache = __ins.renderCache
            val props = __props
            val canvasId = "line_" + Math.floor(Math.random() * 100000)
            val canvasH = props.height
            var ctx: CanvasRenderingContext2D? = null
            var canvasW: Number = 0
            var canvasHPhys: Number = 0
            fun gen_draw_fn(): Unit {
                if (ctx == null) {
                    return
                }
                val c = ctx as CanvasRenderingContext2D
                val w = canvasW
                val h = canvasHPhys
                val padLeft: Number = 36
                val padTop: Number = 30
                val padRight: Number = 16
                val padBottom: Number = 24
                val chartW = w - padLeft - padRight
                val chartH = h - padTop - padBottom
                c.clearRect(0, 0, w, h)
                if (props.title !== "") {
                    c.fillStyle = "#2C3E50"
                    c.font = "12px sans-serif"
                    c.textAlign = "center"
                    c.fillText(props.title, w / 2, 14)
                    c.textAlign = "start"
                }
                val seriesArr = props.series
                if (seriesArr.length === 0 || seriesArr[0].points.length === 0) {
                    return
                }
                val maxPoints = seriesArr[0].points.length
                val gridLines: Number = 4
                c.strokeStyle = "#E8E8E8"
                c.lineWidth = 0.5
                run {
                    var i: Number = 0
                    while(i <= gridLines){
                        val y = padTop + (chartH * i) / gridLines
                        c.beginPath()
                        c.moveTo(padLeft, y)
                        c.lineTo(w - padRight, y)
                        c.stroke()
                        val label = Math.round(100 * (1 - i / gridLines))
                        c.fillStyle = "#95A5A6"
                        c.font = "9px sans-serif"
                        c.textAlign = "right"
                        c.fillText("" + label, padLeft - 4, y + 3)
                        c.textAlign = "start"
                        i++
                    }
                }
                run {
                    var si: Number = 0
                    while(si < seriesArr.length){
                        val s = seriesArr[si]
                        if (s.points.length === 0) {
                            si++
                            continue
                        }
                        c.strokeStyle = s.color
                        c.lineWidth = 2
                        c.beginPath()
                        run {
                            var i: Number = 0
                            while(i < s.points.length){
                                val x = padLeft + (chartW * i) / (s.points.length - 1)
                                val kVal = Math.max(0, Math.min(100, s.points[i].value))
                                val y = padTop + chartH * (1 - kVal / 100)
                                if (i === 0) {
                                    c.moveTo(x, y)
                                } else {
                                    c.lineTo(x, y)
                                }
                                i++
                            }
                        }
                        c.stroke()
                        c.fillStyle = s.color
                        run {
                            var i: Number = 0
                            while(i < s.points.length){
                                val x = padLeft + (chartW * i) / (s.points.length - 1)
                                val kVal = Math.max(0, Math.min(100, s.points[i].value))
                                val y = padTop + chartH * (1 - kVal / 100)
                                c.beginPath()
                                c.arc(x, y, 3, 0, Math.PI * 2, false)
                                c.fill()
                                i++
                            }
                        }
                        si++
                    }
                }
                var legendX: Number = padLeft
                run {
                    var si: Number = 0
                    while(si < seriesArr.length){
                        val s = seriesArr[si]
                        c.fillStyle = s.color
                        c.fillRect(legendX, padTop - 14, 10, 10)
                        c.fillStyle = "#2C3E50"
                        c.font = "10px sans-serif"
                        c.textAlign = "start"
                        c.fillText(s.name, legendX + 14, padTop - 6)
                        legendX += 70
                        si++
                    }
                }
                c.textAlign = "start"
                val firstSeries = seriesArr[0]
                c.fillStyle = "#95A5A6"
                c.font = "9px sans-serif"
                c.textAlign = "center"
                run {
                    var i: Number = 0
                    while(i < maxPoints){
                        val x = padLeft + (chartW * i) / (maxPoints - 1)
                        c.fillText(firstSeries.points[i].label, x, padTop + chartH + 14)
                        i++
                    }
                }
                c.textAlign = "start"
            }
            val draw = ::gen_draw_fn
            onReady(fun(){
                val instance = getCurrentInstance()?.proxy
                if (instance == null) {
                    return
                }
                uni_createCanvasContextAsync(CreateCanvasContextAsyncOptions(id = canvasId, component = instance, success = fun(context: CanvasContext){
                    ctx = context.getContext("2d")!!
                    if (ctx == null) {
                        return
                    }
                    val canvas = ctx.canvas
                    val dpr = uni_getDeviceInfo(null).devicePixelRatio ?: 1
                    canvasW = canvas.offsetWidth
                    canvas.width = canvasW * dpr
                    canvas.height = canvasH * dpr
                    ctx.scale(dpr, dpr)
                    canvasHPhys = canvasH
                    draw()
                }
                ))
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "chart-wrapper"), _uA(
                    _cE("canvas", _uM("id" to canvasId, "class" to "chart-canvas", "style" to _nS(_uM("height" to (unref(canvasH) + "px")))), null, 4)
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
                return _uM("chart-wrapper" to _pS(_uM("flexDirection" to "column", "width" to "100%", "paddingTop" to 4, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0)), "chart-canvas" to _pS(_uM("width" to "100%")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("series" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "title" to _uM("type" to "String", "required" to true, "default" to ""), "height" to _uM("type" to "Number", "required" to true, "default" to 260)))
        var propsNeedCastKeys = _uA(
            "series",
            "title",
            "height"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
