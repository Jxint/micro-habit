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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSettingsIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSettingsIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSettingsIndex
            val _cache = __ins.renderCache
            val durationThreshold = ref(getInt("app_duration_threshold", 60))
            val dndStart = ref("22:00")
            val dndEnd = ref("07:00")
            val durationPref = ref(getInt("action_duration_pref", 15))
            var debugTapCount: Number = 0
            var debugTapTimer: Number? = null
            fun gen_handleDebugTap_fn(): Unit {
                debugTapCount++
                val timerId = debugTapTimer
                if (timerId != null) {
                    clearTimeout(timerId)
                }
                debugTapTimer = setTimeout(fun(): Unit {
                    debugTapCount = 0
                }
                , 3000)
                if (debugTapCount >= 5) {
                    debugTapCount = 0
                    putInt("app_duration_threshold", 1)
                    uni_showToast(ShowToastOptions(title = "Debug模式激活，阈值设为1分钟", position = "bottom"))
                }
            }
            val handleDebugTap = ::gen_handleDebugTap_fn
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "触发设置"),
                            _cE("view", _uM("class" to "setting-item"), _uA(
                                _cE("text", _uM("class" to "setting-label"), "连续使用提醒阈值"),
                                _cE("text", _uM("class" to "setting-value"), _tD(unref(durationThreshold)) + " 分钟", 1)
                            )),
                            _cE("view", _uM("class" to "setting-item"), _uA(
                                _cE("text", _uM("class" to "setting-label"), "免打扰时段"),
                                _cE("text", _uM("class" to "setting-value"), _tD(unref(dndStart)) + " - " + _tD(unref(dndEnd)), 1)
                            ))
                        )),
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "微动作设置"),
                            _cE("view", _uM("class" to "setting-item"), _uA(
                                _cE("text", _uM("class" to "setting-label"), "默认时长偏好"),
                                _cE("text", _uM("class" to "setting-value"), _tD(unref(durationPref)) + " 秒", 1)
                            ))
                        )),
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "关于"),
                            _cE("view", _uM("class" to "setting-item", "onClick" to handleDebugTap), _uA(
                                _cE("text", _uM("class" to "setting-label"), "版本号"),
                                _cE("text", _uM("class" to "setting-value"), "1.0.0")
                            ))
                        ))
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F5F6FA")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "settings-section" to _pS(_uM("marginTop" to 12, "backgroundColor" to "#FFFFFF")), "section-title" to _pS(_uM("fontSize" to 14, "color" to "#7F8C8D", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 4, "paddingLeft" to 16)), "setting-item" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "setting-label" to _pS(_uM("fontSize" to 15, "color" to "#2C3E50")), "setting-value" to _pS(_uM("fontSize" to 14, "color" to "#95A5A6")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
