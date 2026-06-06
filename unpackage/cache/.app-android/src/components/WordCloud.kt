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
open class GenComponentsWordCloud : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var words: UTSArray<MoodWord> by `$props`
    open var maxFontSize: Number by `$props`
    open var minFontSize: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsWordCloud) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsWordCloud
            val _cache = __ins.renderCache
            val props = __props
            var maxWeight: Number = 1
            var baseSeed: Number = 0
            fun gen_seedFor_fn(idx: Number): Number {
                var h = baseSeed + idx * 2654435761
                if (h < 0) {
                    h = -h
                }
                return h % 1000
            }
            val seedFor = ::gen_seedFor_fn
            fun gen_fontSizeFor_fn(weight: Number): Number {
                if (maxWeight < 1) {
                    return props.minFontSize
                }
                val ratio: Number = weight / maxWeight
                return Math.floor(props.minFontSize + ratio * (props.maxFontSize - props.minFontSize))
            }
            val fontSizeFor = ::gen_fontSizeFor_fn
            fun gen_colorFor_fn(word: String): String {
                var h: Number = 0
                run {
                    var i: Number = 0
                    while(i < word.length){
                        h = (h * 31 + (word.charCodeAt(i) as Number)) % 1000
                        i++
                    }
                }
                val palette = _uA(
                    "#E8D8F5",
                    "#8FF0C3",
                    "#FFCAD4",
                    "#D4ECF7",
                    "#FFE4B5",
                    "#B3D9F7",
                    "#FFE8BF",
                    "#C8E8D0"
                ) as UTSArray<String>
                return palette[h % palette.length] as String
            }
            val colorFor = ::gen_colorFor_fn
            fun gen_rotateFor_fn(idx: Number): String {
                val s: Number = seedFor(idx)
                val deg: Number = (s % 30) - 15
                return "rotate(" + deg + "deg)"
            }
            val rotateFor = ::gen_rotateFor_fn
            fun gen_yOffsetFor_fn(idx: Number): Number {
                val s: Number = seedFor(idx)
                return ((s % 7) - 3)
            }
            val yOffsetFor = ::gen_yOffsetFor_fn
            fun gen_opacityFor_fn(weight: Number): Number {
                if (maxWeight < 1) {
                    return 0.5
                }
                return 0.75 + (weight / maxWeight) * 0.25
            }
            val opacityFor = ::gen_opacityFor_fn
            fun gen_recompute_fn(): Unit {
                maxWeight = 1
                baseSeed = Math.floor(Date.now() / 1000) % 100
                run {
                    var i: Number = 0
                    while(i < props.words.length){
                        val w = props.words[i]
                        if (w != null && w.weight > maxWeight) {
                            maxWeight = w.weight
                        }
                        i++
                    }
                }
            }
            val recompute = ::gen_recompute_fn
            onMounted(fun(): Unit {
                recompute()
            }
            )
            watch(fun(): UTSArray<MoodWord> {
                return props.words
            }
            , fun(_v: UTSArray<MoodWord>): Unit {
                recompute()
            }
            , WatchOptions(deep = true))
            return fun(): Any? {
                return _cE("view", _uM("class" to "word-cloud"), _uA(
                    if (_ctx.words.length < 1) {
                        _cE("view", _uM("key" to 0, "class" to "wc-empty"), _uA(
                            _cE("text", _uM("class" to "wc-empty-text"), "暂无数据")
                        ))
                    } else {
                        _cE("view", _uM("key" to 1, "class" to "wc-wrap"), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(_ctx.words, fun(w, idx, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("w-" + idx), "class" to "wc-word", "style" to _nS(_uM("fontSize" to (fontSizeFor(w.weight) + "px"), "color" to colorFor(w.word), "transform" to (rotateFor(idx) + " translateY(" + yOffsetFor(idx) + "px)"), "opacity" to opacityFor(w.weight)))), _tD(w.word), 5)
                            }
                            ), 128)
                        ))
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
                return _uM("word-cloud" to _pS(_uM("paddingTop" to 8, "paddingRight" to 0, "paddingBottom" to 8, "paddingLeft" to 0, "minHeight" to 160)), "wc-empty" to _pS(_uM("paddingTop" to 40, "paddingRight" to 0, "paddingBottom" to 40, "paddingLeft" to 0, "alignItems" to "center")), "wc-empty-text" to _pS(_uM("fontSize" to 13, "color" to "rgba(255,255,255,0.6)")), "wc-wrap" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 4, "paddingLeft" to 4)), "wc-word" to _pS(_uM("fontWeight" to "bold", "marginTop" to 4, "marginRight" to 8, "marginBottom" to 4, "marginLeft" to 8, "paddingTop" to 2, "paddingRight" to 4, "paddingBottom" to 2, "paddingLeft" to 4)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("words" to _uM("type" to "Array", "required" to true, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "maxFontSize" to _uM("type" to "Number", "required" to false, "default" to 30), "minFontSize" to _uM("type" to "Number", "required" to false, "default" to 13)))
        var propsNeedCastKeys = _uA(
            "words",
            "maxFontSize",
            "minFontSize"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
