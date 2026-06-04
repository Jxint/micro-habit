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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSettingsIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSettingsIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSettingsIndex
            val _cache = __ins.renderCache
            fun gen_pad2_fn(n: Number): String {
                if (n < 10) {
                    return "0" + n
                }
                return "" + n
            }
            val pad2 = ::gen_pad2_fn
            fun gen_parseHourFromSetting_fn(v: String, def: Number): Number {
                if (v.length === 0) {
                    return def
                }
                val parts = v.split(":")
                val h = parseInt(parts[0] as String)
                if (isNaN(h) || h < 0 || h > 23) {
                    return def
                }
                return h
            }
            val parseHourFromSetting = ::gen_parseHourFromSetting_fn
            val durationThreshold = ref<Number>(getInt("app_duration_threshold", 60))
            val dndStartHour = ref<Number>(parseHourFromSetting(getSetting("dnd_start", "22:00"), 22))
            val dndEndHour = ref<Number>(parseHourFromSetting(getSetting("dnd_end", "07:00"), 7))
            val durationPref = ref<Number>(getInt("action_duration_pref", 15))
            val targetCount = ref<Number>(getInt("category_target_count", 3))
            val rules = ref(_uA<TriggerRule>())
            val llmEnabled = ref<Boolean>(getBool("llm_trigger_enabled", true))
            val askEachTime = ref<Boolean>(getBool("llm_trigger_ask_each_time", true))
            val dndStart = computed<String>(fun(): String {
                return pad2(dndStartHour.value) + ":00"
            }
            )
            val dndEnd = computed<String>(fun(): String {
                return pad2(dndEndHour.value) + ":00"
            }
            )
            fun gen_formatTime_fn(t: Number): String {
                if (t <= 0) {
                    return "-"
                }
                val d = Date(t * 1000)
                return d.getFullYear() + "-" + pad2(d.getMonth() + 1) + "-" + pad2(d.getDate()) + " " + pad2(d.getHours()) + ":" + pad2(d.getMinutes())
            }
            val formatTime = ::gen_formatTime_fn
            fun gen_formatRuleType_fn(t: String): String {
                if (t === "frequency") {
                    return "频率控制"
                }
                if (t === "duration") {
                    return "时长控制"
                }
                if (t === "time_window") {
                    return "时间窗口"
                }
                return t
            }
            val formatRuleType = ::gen_formatRuleType_fn
            fun gen_formatTriggerLabel_fn(t: String): String {
                if (t === "app_duration") {
                    return "APP使用时长"
                }
                if (t === "manual") {
                    return "手动触发"
                }
                if (t === "scheduled") {
                    return "定时触发"
                }
                return t
            }
            val formatTriggerLabel = ::gen_formatTriggerLabel_fn
            fun gen_formatSource_fn(s: String): String {
                if (s === "ai_suggest") {
                    return "🤖 AI建议"
                }
                if (s === "default") {
                    return "📦 默认"
                }
                if (s === "manual") {
                    return "👤 手动"
                }
                return s
            }
            val formatSource = ::gen_formatSource_fn
            fun gen_formatCondition_fn(json: String): String {
                if (json === "{}" || json.length === 0) {
                    return "无条件"
                }
                try {
                    val obj = JSON.parse(json) as UTSJSONObject
                    if (obj == null) {
                        return json.substring(0, 20)
                    }
                    val hourMin = obj.get("hour_min")
                    val hourMax = obj.get("hour_max")
                    if (hourMin != null && hourMax != null) {
                        return "时段 " + hourMin + ":00-" + hourMax + ":00"
                    }
                    return json.substring(0, 20)
                }
                 catch (_: Throwable) {
                    return json.substring(0, 20)
                }
            }
            val formatCondition = ::gen_formatCondition_fn
            val showThresholdPicker = ref<Boolean>(false)
            val showDndPicker = ref<Boolean>(false)
            val showDurationPrefPicker = ref<Boolean>(false)
            val showTargetCountPicker = ref<Boolean>(false)
            fun gen_openThresholdPicker_fn(): Unit {
                showThresholdPicker.value = true
            }
            val openThresholdPicker = ::gen_openThresholdPicker_fn
            fun gen_openDndPicker_fn(): Unit {
                showDndPicker.value = true
            }
            val openDndPicker = ::gen_openDndPicker_fn
            fun gen_openDurationPrefPicker_fn(): Unit {
                showDurationPrefPicker.value = true
            }
            val openDurationPrefPicker = ::gen_openDurationPrefPicker_fn
            fun gen_openTargetCountPicker_fn(): Unit {
                showTargetCountPicker.value = true
            }
            val openTargetCountPicker = ::gen_openTargetCountPicker_fn
            fun gen_onThresholdConfirm_fn(v: Number): Unit {
                durationThreshold.value = v
                putInt("app_duration_threshold", v)
                showThresholdPicker.value = false
                uni_showToast(ShowToastOptions(title = "已保存", icon = "success"))
            }
            val onThresholdConfirm = ::gen_onThresholdConfirm_fn
            fun gen_onDndConfirm_fn(s: Number, e: Number): Unit {
                dndStartHour.value = s
                dndEndHour.value = e
                putSetting("dnd_start", pad2(s) + ":00")
                putSetting("dnd_end", pad2(e) + ":00")
                showDndPicker.value = false
                uni_showToast(ShowToastOptions(title = "已保存", icon = "success"))
            }
            val onDndConfirm = ::gen_onDndConfirm_fn
            fun gen_onDurationPrefConfirm_fn(v: Number): Unit {
                durationPref.value = v
                putInt("action_duration_pref", v)
                showDurationPrefPicker.value = false
                uni_showToast(ShowToastOptions(title = "已保存", icon = "success"))
            }
            val onDurationPrefConfirm = ::gen_onDurationPrefConfirm_fn
            fun gen_onTargetCountConfirm_fn(v: Number): Unit {
                targetCount.value = v
                putInt("category_target_count", v)
                showTargetCountPicker.value = false
                uni_showToast(ShowToastOptions(title = "已保存", icon = "success"))
            }
            val onTargetCountConfirm = ::gen_onTargetCountConfirm_fn
            fun gen_goDebugLogs_fn(): Unit {
                uni_navigateTo(NavigateToOptions(url = "/pages/debug/logs"))
            }
            val goDebugLogs = ::gen_goDebugLogs_fn
            fun gen_goDebugDB_fn(): Unit {
                uni_navigateTo(NavigateToOptions(url = "/pages/debug/logs"))
            }
            val goDebugDB = ::gen_goDebugDB_fn
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
                    uni_showToast(ShowToastOptions(title = "Debug模式激活，阈值1分钟", position = "bottom"))
                }
            }
            val handleDebugTap = ::gen_handleDebugTap_fn
            fun gen_onLlmEnabledChange_fn(e: Any): Unit {
                try {
                    val obj = e as UTSJSONObject
                    if (obj == null) {
                        return
                    }
                    val v = obj["detail"]
                    if (v == null) {
                        return
                    }
                    val detail = v as UTSJSONObject
                    if (detail == null) {
                        return
                    }
                    val valRaw = detail["value"]
                    if (valRaw == null) {
                        return
                    }
                    val enabled = valRaw as Boolean
                    llmEnabled.value = enabled === true
                    putBool("llm_trigger_enabled", enabled === true)
                }
                 catch (err: Throwable) {
                    console.warn("[settings] onLlmEnabledChange 异常: " + JSON.stringify(err))
                }
            }
            val onLlmEnabledChange = ::gen_onLlmEnabledChange_fn
            fun gen_onAskEachTimeChange_fn(e: Any): Unit {
                try {
                    val obj = e as UTSJSONObject
                    if (obj == null) {
                        return
                    }
                    val v = obj["detail"]
                    if (v == null) {
                        return
                    }
                    val detail = v as UTSJSONObject
                    if (detail == null) {
                        return
                    }
                    val valRaw = detail["value"]
                    if (valRaw == null) {
                        return
                    }
                    val v2 = valRaw as Boolean
                    askEachTime.value = v2 === true
                    putBool("llm_trigger_ask_each_time", v2 === true)
                }
                 catch (err: Throwable) {
                    console.warn("[settings] onAskEachTimeChange 异常: " + JSON.stringify(err))
                }
            }
            val onAskEachTimeChange = ::gen_onAskEachTimeChange_fn
            onPageShow(fun(){
                try {
                    rules.value = getAllEnabledRules()
                }
                 catch (_: Throwable) {
                    rules.value = _uA()
                }
                durationThreshold.value = getInt("app_duration_threshold", 60)
                durationPref.value = getInt("action_duration_pref", 15)
                targetCount.value = getInt("category_target_count", 3)
                dndStartHour.value = parseHourFromSetting(getSetting("dnd_start", "22:00"), 22)
                dndEndHour.value = parseHourFromSetting(getSetting("dnd_end", "07:00"), 7)
                llmEnabled.value = getBool("llm_trigger_enabled", true)
                askEachTime.value = getBool("llm_trigger_ask_each_time", true)
            }
            )
            return fun(): Any? {
                val _component_switch = resolveComponent("switch")
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "触发设置"),
                            _cE("view", _uM("class" to "setting-item", "onClick" to openThresholdPicker), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "连续使用提醒阈值"),
                                    _cE("text", _uM("class" to "setting-hint"), "同一 App 连续使用多久后提醒")
                                )),
                                _cE("text", _uM("class" to "setting-value"), _tD(unref(durationThreshold)) + " 分钟 ›", 1)
                            )),
                            _cE("view", _uM("class" to "setting-item", "onClick" to openDndPicker), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "免打扰时段"),
                                    _cE("text", _uM("class" to "setting-hint"), "该时段不触发提醒")
                                )),
                                _cE("text", _uM("class" to "setting-value"), _tD(unref(dndStart)) + " - " + _tD(unref(dndEnd)) + " ›", 1)
                            )),
                            _cE("view", _uM("class" to "setting-item"), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "启用 LLM 实时建议"),
                                    _cE("text", _uM("class" to "setting-hint"), "触发时调用 LLM 生成即兴文案 + 复评触发规则")
                                )),
                                _cV(_component_switch, _uM("checked" to unref(llmEnabled), "onChange" to onLlmEnabledChange), null, 8, _uA(
                                    "checked"
                                ))
                            )),
                            _cE("view", _uM("class" to "setting-item"), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "每次微动作后询问 AI 触发建议"),
                                    _cE("text", _uM("class" to "setting-hint"), "关闭后静默接受 LLM 建议（不开每次问询）")
                                )),
                                _cV(_component_switch, _uM("checked" to unref(askEachTime), "disabled" to !unref(llmEnabled), "onChange" to onAskEachTimeChange), null, 8, _uA(
                                    "checked",
                                    "disabled"
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "微动作设置"),
                            _cE("view", _uM("class" to "setting-item", "onClick" to openDurationPrefPicker), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "默认时长偏好"),
                                    _cE("text", _uM("class" to "setting-hint"), "手动微动作的默认倒计时")
                                )),
                                _cE("text", _uM("class" to "setting-value"), _tD(unref(durationPref)) + " 秒 ›", 1)
                            )),
                            _cE("view", _uM("class" to "setting-item", "onClick" to openTargetCountPicker), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "每日目标次数"),
                                    _cE("text", _uM("class" to "setting-hint"), "每类动作每天完成几次算满分")
                                )),
                                _cE("text", _uM("class" to "setting-value"), _tD(unref(targetCount)) + " 次 ›", 1)
                            ))
                        )),
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "触发规则（" + _tD(unref(rules).length) + " 条）", 1),
                            if (unref(rules).length === 0) {
                                _cE("view", _uM("key" to 0, "class" to "empty-rules"), _uA(
                                    _cE("text", _uM("class" to "empty-text"), "暂无启用的触发规则")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE(Fragment, null, RenderHelpers.renderList(unref(rules), fun(rule, idx, __index, _cached): Any {
                                return _cE("view", _uM("key" to rule.id, "class" to "rule-card"), _uA(
                                    _cE("view", _uM("class" to "rule-row1"), _uA(
                                        _cE("text", _uM("class" to "rule-type"), _tD(formatRuleType(rule.rule_type)), 1),
                                        _cE("text", _uM("class" to "rule-trigger"), _tD(formatTriggerLabel(rule.trigger_type)), 1),
                                        _cE("text", _uM("class" to "rule-priority"), "优先级 " + _tD(rule.priority), 1)
                                    )),
                                    _cE("view", _uM("class" to "rule-row2"), _uA(
                                        _cE("text", _uM("class" to "rule-source"), _tD(formatSource(rule.source)), 1),
                                        _cE("text", _uM("class" to "rule-cond"), _tD(formatCondition(rule.condition_json)), 1)
                                    )),
                                    _cE("view", _uM("class" to "rule-row3"), _uA(
                                        _cE("text", _uM("class" to "rule-time"), "创建 " + _tD(formatTime(rule.created_at)), 1),
                                        _cE("text", _uM("class" to "rule-time"), "更新 " + _tD(formatTime(rule.updated_at)), 1)
                                    ))
                                ))
                            }
                            ), 128)
                        )),
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "调试"),
                            _cE("view", _uM("class" to "setting-item", "onClick" to goDebugLogs), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "查看触发/动作日志")
                                )),
                                _cE("text", _uM("class" to "setting-value"), "›")
                            )),
                            _cE("view", _uM("class" to "setting-item", "onClick" to goDebugDB), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "数据库巡检 & 配置")
                                )),
                                _cE("text", _uM("class" to "setting-value"), "›")
                            ))
                        )),
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "关于"),
                            _cE("view", _uM("class" to "setting-item", "onClick" to handleDebugTap), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "版本号")
                                )),
                                _cE("text", _uM("class" to "setting-value"), "1.0.0")
                            ))
                        )),
                        _cE("view", _uM("class" to "bottom-spacer"))
                    )),
                    _cV(unref(GenComponentsNumberPickerDialogClass), _uM("visible" to unref(showThresholdPicker), "title" to "连续使用提醒阈值", "hint" to "达到该连续时长（分钟）后弹窗提醒，建议 30-90 分钟", "initial-value" to unref(durationThreshold), "min-value" to 5, "max-value" to 180, "step" to 5, "unit" to "分钟", "quick-values" to _uA(
                        15,
                        30,
                        45,
                        60,
                        90,
                        120
                    ), "onClose" to fun(){
                        showThresholdPicker.value = false
                    }
                    , "onConfirm" to onThresholdConfirm), null, 8, _uA(
                        "visible",
                        "initial-value",
                        "onClose"
                    )),
                    _cV(unref(GenComponentsTimeRangePickerDialogClass), _uM("visible" to unref(showDndPicker), "title" to "免打扰时段", "hint" to "起止小时（24小时制，分钟固定为 00）", "initial-start-hour" to unref(dndStartHour), "initial-end-hour" to unref(dndEndHour), "onClose" to fun(){
                        showDndPicker.value = false
                    }
                    , "onConfirm" to onDndConfirm), null, 8, _uA(
                        "visible",
                        "initial-start-hour",
                        "initial-end-hour",
                        "onClose"
                    )),
                    _cV(unref(GenComponentsNumberPickerDialogClass), _uM("visible" to unref(showDurationPrefPicker), "title" to "默认时长偏好", "hint" to "手动微动作的默认倒计时秒数", "initial-value" to unref(durationPref), "min-value" to 5, "max-value" to 60, "step" to 1, "unit" to "秒", "quick-values" to _uA(
                        10,
                        15,
                        20,
                        30,
                        45,
                        60
                    ), "onClose" to fun(){
                        showDurationPrefPicker.value = false
                    }
                    , "onConfirm" to onDurationPrefConfirm), null, 8, _uA(
                        "visible",
                        "initial-value",
                        "onClose"
                    )),
                    _cV(unref(GenComponentsNumberPickerDialogClass), _uM("visible" to unref(showTargetCountPicker), "title" to "每日目标次数", "hint" to "每类动作每天完成几次算 100 分", "initial-value" to unref(targetCount), "min-value" to 1, "max-value" to 20, "step" to 1, "unit" to "次", "quick-values" to _uA(
                        1,
                        2,
                        3,
                        5,
                        8,
                        10
                    ), "onClose" to fun(){
                        showTargetCountPicker.value = false
                    }
                    , "onConfirm" to onTargetCountConfirm), null, 8, _uA(
                        "visible",
                        "initial-value",
                        "onClose"
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F5F6FA")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "settings-section" to _pS(_uM("marginTop" to 12, "backgroundColor" to "#FFFFFF")), "section-title" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D", "fontWeight" to "bold", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 6, "paddingLeft" to 16)), "setting-item" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "setting-left" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "setting-label" to _pS(_uM("fontSize" to 15, "color" to "#2C3E50")), "setting-hint" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginTop" to 2)), "setting-value" to _pS(_uM("fontSize" to 14, "color" to "#3498DB", "fontWeight" to 500)), "empty-rules" to _pS(_uM("alignItems" to "center", "paddingTop" to 20, "paddingRight" to 0, "paddingBottom" to 20, "paddingLeft" to 0)), "empty-text" to _pS(_uM("fontSize" to 13, "color" to "#BDC3C7")), "rule-card" to _pS(_uM("paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "rule-row1" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 4)), "rule-type" to _pS(_uM("fontSize" to 13, "fontWeight" to "bold", "color" to "#2C3E50", "backgroundColor" to "#EBF5FB", "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "paddingTop" to 2, "paddingRight" to 6, "paddingBottom" to 2, "paddingLeft" to 6, "marginRight" to 6)), "rule-trigger" to _pS(_uM("fontSize" to 11, "color" to "#7F8C8D", "backgroundColor" to "#F0F3F4", "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "paddingTop" to 2, "paddingRight" to 6, "paddingBottom" to 2, "paddingLeft" to 6)), "rule-priority" to _pS(_uM("fontSize" to 11, "color" to "#3498DB", "marginLeft" to "auto")), "rule-row2" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 4)), "rule-source" to _pS(_uM("fontSize" to 11, "color" to "#27AE60", "marginRight" to 8)), "rule-cond" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "rule-row3" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "marginTop" to 4)), "rule-time" to _pS(_uM("fontSize" to 10, "color" to "#BDC3C7", "fontFamily" to "monospace")), "bottom-spacer" to _pS(_uM("height" to 80)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
