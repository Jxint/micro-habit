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
open class GenComponentsBarChart : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var data: UTSArray<BarItem__1> by `$props`
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
            val canvasId = "bar_" + Math.floor(Math.random() * 100000)
            val canvasH = props.height
            var ctx: CanvasRenderingContext2D? = null
            var canvasW: Number = 0
            var canvasHPhys: Number = 0
            fun gen_buildEmpty_fn(): UTSArray<BarItem__1> {
                val r: UTSArray<BarItem__1> = _uA()
                run {
                    var i: Number = 0
                    while(i < 24){
                        val label = (if (i < 10) {
                            "0"
                        } else {
                            ""
                        }
                        ) + i + ":00"
                        r.push(BarItem__1(label = label, value = 0))
                        i++
                    }
                }
                return r
            }
            val buildEmpty = ::gen_buildEmpty_fn
            fun gen_drawRound_fn(c: CanvasRenderingContext2D, x: Number, y: Number, w: Number, h: Number, r: Number): Unit {
                c.beginPath()
                c.moveTo(x + r, y)
                c.lineTo(x + w - r, y)
                c.arcTo(x + w, y, x + w, y + r, r)
                c.lineTo(x + w, y + h - r)
                c.arcTo(x + w, y + h, x + w - r, y + h, r)
                c.lineTo(x + r, y + h)
                c.arcTo(x, y + h, x, y + h - r, r)
                c.lineTo(x, y + r)
                c.arcTo(x, y, x + r, y, r)
                c.closePath()
            }
            val drawRound = ::gen_drawRound_fn
            fun gen_draw_fn(): Unit {
                if (ctx == null) {
                    return
                }
                val c = ctx as CanvasRenderingContext2D
                val w = canvasW
                val h = canvasHPhys
                val padLeft: Number = 30
                val padTop: Number = 12
                val padRight: Number = 12
                val padBottom: Number = 22
                val chartW = w - padLeft - padRight
                val chartH = h - padTop - padBottom
                val items = if (props.data.length > 0) {
                    props.data
                } else {
                    buildEmpty()
                }
                var maxVal: Number = 0
                for(item in resolveUTSValueIterator(items)){
                    if (item.value > maxVal) {
                        maxVal = item.value
                    }
                }
                if (maxVal === 0) {
                    maxVal = 1
                }
                c.clearRect(0, 0, w, h)
                if (props.title !== "") {
                    c.fillStyle = "#2C3E50"
                    c.font = "12px sans-serif"
                    c.textAlign = "center"
                    c.fillText(props.title, w / 2, 12)
                    c.textAlign = "start"
                }
                val gridLines = if (maxVal <= 3) {
                    2
                } else {
                    4
                }
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
                        i++
                    }
                }
                val gap: Number = 2
                val barW = (chartW - gap * (items.length - 1)) / items.length
                run {
                    var i: Number = 0
                    while(i < items.length){
                        val kVal = items[i].value
                        val barH = Math.max((kVal / maxVal) * chartH, if (kVal > 0) {
                            3
                        } else {
                            0
                        }
                        )
                        val x = padLeft + i * (barW + gap)
                        val y = padTop + chartH - barH
                        c.fillStyle = if (kVal === 0) {
                            "#E8E8E8"
                        } else {
                            props.barColor
                        }
                        drawRound(c, x, y, barW, barH, 2)
                        c.fill()
                        val labelEvery = if (items.length <= 24) {
                            2
                        } else {
                            7
                        }
                        if (i % labelEvery === 0) {
                            c.fillStyle = "#95A5A6"
                            c.font = "8px sans-serif"
                            c.textAlign = "center"
                            c.fillText(items[i].label, x + barW / 2, padTop + chartH + 12)
                            c.textAlign = "start"
                        }
                        i++
                    }
                }
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
        var props = _nP(_uM("data" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "barColor" to _uM("type" to "String", "required" to true, "default" to "#4CAF50"), "title" to _uM("type" to "String", "required" to true, "default" to ""), "height" to _uM("type" to "Number", "required" to true, "default" to 240)))
        var propsNeedCastKeys = _uA(
            "data",
            "barColor",
            "title",
            "height"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
