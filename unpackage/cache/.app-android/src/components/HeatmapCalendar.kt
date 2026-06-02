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
open class GenComponentsHeatmapCalendar : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var data: UTSArray<DailyCount__1> by `$props`
    open var height: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsHeatmapCalendar) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsHeatmapCalendar
            val _cache = __ins.renderCache
            val props = __props
            val canvasId: String = "heat_" + Math.floor(Math.random() * 100000)
            val canvasH: Number = props.height
            val cellSize: Number = 20
            val cellGap: Number = 4
            val cols: Number = 7
            val maxRows: Number = 4
            var ctx: CanvasRenderingContext2D? = null
            var canvasW: Number = 0
            var canvasHPhys: Number = 0
            var cellRects: UTSArray<CellRect> = _uA()
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
                cellRects = _uA()
                c.clearRect(0, 0, w, h)
                val totalW = cols * cellSize + (cols - 1) * cellGap
                val startX = (w - totalW) / 2
                val startY: Number = 22
                val weekLabels = _uA(
                    "一",
                    "二",
                    "三",
                    "四",
                    "五",
                    "六",
                    "日"
                )
                c.fillStyle = "#95A5A6"
                c.font = "9px sans-serif"
                c.textAlign = "center"
                run {
                    var ci: Number = 0
                    while(ci < cols){
                        c.fillText(weekLabels[ci], startX + ci * (cellSize + cellGap) + cellSize / 2, startY - 6)
                        ci++
                    }
                }
                c.textAlign = "start"
                val days = props.data
                var firstDayOfWeek: Number = 0
                if (days.length > 0) {
                    val parts = days[0].date.split("-")
                    val yy = parseInt(parts[0] as String)
                    val mm = parseInt(parts[1] as String) - 1
                    val dd = parseInt(parts[2] as String)
                    val fd = Date(yy, mm, dd)
                    var dow = fd.getDay()
                    if (dow === 0) {
                        dow = 7
                    }
                    firstDayOfWeek = dow - 1
                }
                run {
                    var i: Number = 0
                    while(i < days.length){
                        val pos = i + firstDayOfWeek
                        val row = Math.floor(pos / cols)
                        val col = pos % cols
                        if (row >= maxRows) {
                            break
                        }
                        val x = startX + col * (cellSize + cellGap)
                        val y = startY + row * (cellSize + cellGap)
                        val count = days[i].count
                        var color = "#D5D8DC"
                        if (count >= 5) {
                            color = "#2ECC71"
                        } else if (count >= 3) {
                            color = "#82E0AA"
                        } else if (count >= 1) {
                            color = "#F9E79F"
                        }
                        c.fillStyle = color
                        drawRound(c, x, y, cellSize, cellSize, 3)
                        c.fill()
                        cellRects.push(CellRect(x = x, y = y, w = cellSize + cellGap, h = cellSize + cellGap, day = days[i]))
                        i++
                    }
                }
                val legendY = startY + maxRows * (cellSize + cellGap) + 12
                val legendColors = _uA(
                    "#D5D8DC",
                    "#F9E79F",
                    "#82E0AA",
                    "#2ECC71"
                )
                val legSize: Number = 14
                val legGap: Number = 4
                val legendW = 4 * legSize + 3 * (legGap + 24)
                var lx = (w - legendW) / 2
                c.font = "9px sans-serif"
                c.fillStyle = "#95A5A6"
                c.textAlign = "start"
                c.fillText("少", lx - 14, legendY + legSize / 2 + 3)
                run {
                    var i: Number = 0
                    while(i < 4){
                        c.fillStyle = legendColors[i]
                        c.fillRect(lx, legendY, legSize, legSize)
                        lx += legSize + legGap + (if (i < 3) {
                            24
                        } else {
                            0
                        }
                        )
                        i++
                    }
                }
                c.fillText("多", lx + 2, legendY + legSize / 2 + 3)
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
        var props = _nP(_uM("data" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "height" to _uM("type" to "Number", "required" to true, "default" to 220)))
        var propsNeedCastKeys = _uA(
            "data",
            "height"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
