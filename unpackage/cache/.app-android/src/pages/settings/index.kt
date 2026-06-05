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
import io.dcloud.uniapp.extapi.`$emit` as uni__emit
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showModal as uni_showModal
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
                if (v.length < 1) {
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
            val rules = ref(_uA<PersistedEffectiveRule>())
            val triggerEnabled = ref<Boolean>(getBool("trigger_enabled", true))
            val llmEnabled = ref<Boolean>(getBool("llm_trigger_enabled", true))
            val askEachTime = ref<Boolean>(getBool("llm_trigger_ask_each_time", true))
            val blacklistCount = ref<Number>(0)
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
            fun gen_getActionDisplayName_fn(actionId: String): String {
                val a = getActionById(actionId)
                return if (a != null) {
                    a.name
                } else {
                    actionId
                }
            }
            val getActionDisplayName = ::gen_getActionDisplayName_fn
            fun gen_formatAppPackages_fn(pkgs: UTSArray<String>): String {
                if (pkgs.length < 1) {
                    return "任意 App"
                }
                if (pkgs.length == 1) {
                    return pkgs[0] as String
                }
                return pkgs.length + " 个应用"
            }
            val formatAppPackages = ::gen_formatAppPackages_fn
            fun gen_formatRuleType_fn(t: String): String {
                if (t.length < 1) {
                    return "时长控制"
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
                if (s === "llm") {
                    return "🤖 LLM建议"
                }
                if (s === "user") {
                    return "👤 用户"
                }
                if (s === "system") {
                    return "📦 系统"
                }
                return s
            }
            val formatSource = ::gen_formatSource_fn
            fun gen_formatTriggerLevel_fn(level: String): String {
                if (level === "gentle") {
                    return "轻提醒"
                }
                if (level === "strong") {
                    return "强提醒"
                }
                if (level === "night") {
                    return "夜间轻提醒"
                }
                return level
            }
            val formatTriggerLevel = ::gen_formatTriggerLevel_fn
            fun gen_formatCondition_fn(json: String): String {
                if (json.length < 1) {
                    return "无条件"
                }
                try {
<<<<<<< HEAD
                    val obj = UTSAndroid.consoleDebugError(JSON.parse(json), " at pages/settings/index.uvue:283") as UTSJSONObject
=======
                    val obj = UTSAndroid.consoleDebugError(JSON.parse(json), " at pages/settings/index.uvue:293") as UTSJSONObject
>>>>>>> b47ceb2b6ee776642391bf71543f588897cdde9d
                    if (obj == null) {
                        return json.substring(0, 20)
                    }
                    val apps = obj.get("appPackages")
                    val tw = obj.get("timeWindow")
                    val parts: UTSArray<String> = _uA()
                    if (apps != null) {
                        val arr = apps as UTSArray<Any>
                        if (arr.length > 0) {
                            parts.push("包名 " + (if (arr.length == 1) {
                                (arr[0] as String)
                            } else {
                                (arr.length + " 个")
                            }
                            ))
                        }
                    }
                    if (tw != null) {
                        val twObj = tw as UTSJSONObject
                        val s = twObj.get("start") as String
                        val e = twObj.get("end") as String
                        if (s != null && e != null) {
                            parts.push("时段 " + s + "-" + e)
                        }
                    }
                    if (parts.length < 1) {
                        return json.substring(0, 20)
                    }
                    return parts.join(" / ")
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
            fun gen_resetAchievements_fn(): Unit {
                try {
                    uni_showModal(ShowModalOptions(title = "确认重置", content = "清空所有成就解锁记录，重新开始统计。", success = fun(res){
                        try {
                            if (res == null) {
                                return
                            }
                            val obj = res as UTSJSONObject
                            if (obj == null) {
                                return
                            }
                            val confirm = obj["confirm"]
                            if (confirm !== true) {
                                return
                            }
                            clearAchievements()
                            putSetting("seen_achievements", "[]")
                            try {
                                useAppStore().refreshAchievements()
                            }
                             catch (_: Throwable) {}
                            uni_showToast(ShowToastOptions(title = "已重置成就", icon = "none"))
                        }
                         catch (e: Throwable) {
<<<<<<< HEAD
                            console.warn("[settings] resetAchievements 回调异常: " + JSON.stringify(e), " at pages/settings/index.uvue:374")
=======
                            console.warn("[settings] resetAchievements 回调异常: " + JSON.stringify(e), " at pages/settings/index.uvue:384")
>>>>>>> b47ceb2b6ee776642391bf71543f588897cdde9d
                        }
                    }
                    ))
                }
                 catch (e: Throwable) {
<<<<<<< HEAD
                    console.warn("[settings] resetAchievements 异常: " + JSON.stringify(e), " at pages/settings/index.uvue:379")
=======
                    console.warn("[settings] resetAchievements 异常: " + JSON.stringify(e), " at pages/settings/index.uvue:389")
>>>>>>> b47ceb2b6ee776642391bf71543f588897cdde9d
                }
            }
            val resetAchievements = ::gen_resetAchievements_fn
            fun gen_goAppCategories_fn(): Unit {
                uni_navigateTo(NavigateToOptions(url = "/pages/settings/app-categories/index"))
            }
            val goAppCategories = ::gen_goAppCategories_fn
            fun gen_readBlacklistCount_fn(): Number {
                try {
                    val raw = getSetting("app_blacklist", "[]")
                    val arr = UTSAndroid.consoleDebugError(JSON.parse(raw), " at pages/settings/index.uvue:390") as UTSArray<Any>
                    if (arr == null) {
                        return 0
                    }
                    var n: Number = 0
                    run {
                        var i: Number = 0
                        while(i < arr.length){
                            val v = arr[i]
                            if (UTSAndroid.`typeof`(v) === "string" && (v as String).length > 0) {
                                n++
                            }
                            i++
                        }
                    }
                    return n
                }
                 catch (_: Throwable) {
                    return 0
                }
            }
            val readBlacklistCount = ::gen_readBlacklistCount_fn
            fun gen_goBlacklist_fn(): Unit {
                uni_navigateTo(NavigateToOptions(url = "/pages/settings/blacklist"))
            }
            val goBlacklist = ::gen_goBlacklist_fn
            val showRuleEditor = ref<Boolean>(false)
            val editingRuleJson = ref<String>("")
            fun gen_onEditRule_fn(r: PersistedEffectiveRule): Unit {
                val ruleJson: String = JSON.stringify(r) as String
                editingRuleJson.value = ruleJson
                showRuleEditor.value = true
            }
            val onEditRule = ::gen_onEditRule_fn
            fun gen_onRuleEditorClose_fn(): Unit {
                showRuleEditor.value = false
                editingRuleJson.value = ""
            }
            val onRuleEditorClose = ::gen_onRuleEditorClose_fn
            fun gen_onRuleSave_fn(updated: PersistedEffectiveRule): Unit {
                try {
                    if (updated.id > 0) {
                        updateRule(updated)
                    } else {
                        val newId = insertRule(updated)
                        updated.id = newId
                    }
                    showRuleEditor.value = false
                    editingRuleJson.value = ""
                    try {
                        rules.value = getActiveRules()
                    }
                     catch (_: Throwable) {
                        rules.value = _uA()
                    }
                    uni_showToast(ShowToastOptions(title = "已保存", icon = "success"))
                }
                 catch (e: Throwable) {
                    console.warn("[settings] onRuleSave 异常: " + JSON.stringify(e), " at pages/settings/index.uvue:435")
                    uni_showToast(ShowToastOptions(title = "保存失败", icon = "none"))
                }
            }
            val onRuleSave = ::gen_onRuleSave_fn
            fun gen_onRuleDelete_fn(id: Number): Unit {
                try {
                    if (id > 0) {
                        deleteRule(id)
                    }
                    showRuleEditor.value = false
                    editingRuleJson.value = ""
                    try {
                        rules.value = getActiveRules()
                    }
                     catch (_: Throwable) {
                        rules.value = _uA()
                    }
                    uni_showToast(ShowToastOptions(title = "已删除", icon = "success"))
                }
                 catch (e: Throwable) {
                    console.warn("[settings] onRuleDelete 异常: " + JSON.stringify(e), " at pages/settings/index.uvue:448")
                    uni_showToast(ShowToastOptions(title = "删除失败", icon = "none"))
                }
            }
            val onRuleDelete = ::gen_onRuleDelete_fn
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
            fun gen_onManualTrigger_fn(): Unit {
                try {
                    uni__emit("manualTriggerCheck", _uO())
                    uni_showToast(ShowToastOptions(title = "正在检查中", icon = "none", position = "bottom"))
                }
                 catch (e: Throwable) {
<<<<<<< HEAD
                    console.warn("[settings] manualTrigger 异常: " + JSON.stringify(e), " at pages/settings/index.uvue:473")
=======
                    console.warn("[settings] manualTrigger 异常: " + JSON.stringify(e), " at pages/settings/index.uvue:417")
>>>>>>> b47ceb2b6ee776642391bf71543f588897cdde9d
                }
            }
            val onManualTrigger = ::gen_onManualTrigger_fn
            fun gen_extractBoolFromSwitch_fn(e: Any): Boolean? {
                try {
                    val obj = e as UTSJSONObject
                    if (obj == null) {
                        return null
                    }
                    val v = obj["detail"]
                    if (v == null) {
                        return null
                    }
                    val detail = v as UTSJSONObject
                    if (detail == null) {
                        return null
                    }
                    val valRaw = detail["value"]
                    if (valRaw == null) {
                        return null
                    }
                    if (UTSAndroid.`typeof`(valRaw) === "boolean") {
                        return valRaw as Boolean
                    }
                    if (UTSAndroid.`typeof`(valRaw) === "number") {
                        return (valRaw as Number) != 0
                    }
                    return null
                }
                 catch (_: Throwable) {
                    return null
                }
            }
            val extractBoolFromSwitch = ::gen_extractBoolFromSwitch_fn
            fun gen_onTriggerEnabledChange_fn(e: Any): Unit {
                try {
                    val enabled = extractBoolFromSwitch(e)
                    if (enabled == null) {
                        return
                    }
                    triggerEnabled.value = enabled
                    putBool("trigger_enabled", enabled)
                    setTriggerEnabled(enabled)
                }
                 catch (err: Throwable) {
<<<<<<< HEAD
                    console.warn("[settings] onTriggerEnabledChange 异常: " + JSON.stringify(err), " at pages/settings/index.uvue:503")
=======
                    console.warn("[settings] onTriggerEnabledChange 异常: " + JSON.stringify(err), " at pages/settings/index.uvue:447")
>>>>>>> b47ceb2b6ee776642391bf71543f588897cdde9d
                }
            }
            val onTriggerEnabledChange = ::gen_onTriggerEnabledChange_fn
            fun gen_onLlmEnabledChange_fn(e: Any): Unit {
                try {
                    val enabled = extractBoolFromSwitch(e)
                    if (enabled == null) {
                        return
                    }
                    llmEnabled.value = enabled
                    putBool("llm_trigger_enabled", enabled)
                }
                 catch (err: Throwable) {
<<<<<<< HEAD
                    console.warn("[settings] onLlmEnabledChange 异常: " + JSON.stringify(err), " at pages/settings/index.uvue:514")
=======
                    console.warn("[settings] onLlmEnabledChange 异常: " + JSON.stringify(err), " at pages/settings/index.uvue:458")
>>>>>>> b47ceb2b6ee776642391bf71543f588897cdde9d
                }
            }
            val onLlmEnabledChange = ::gen_onLlmEnabledChange_fn
            fun gen_onAskEachTimeChange_fn(e: Any): Unit {
                try {
                    val enabled = extractBoolFromSwitch(e)
                    if (enabled == null) {
                        return
                    }
                    askEachTime.value = enabled
                    putBool("llm_trigger_ask_each_time", enabled)
                }
                 catch (err: Throwable) {
<<<<<<< HEAD
                    console.warn("[settings] onAskEachTimeChange 异常: " + JSON.stringify(err), " at pages/settings/index.uvue:525")
=======
                    console.warn("[settings] onAskEachTimeChange 异常: " + JSON.stringify(err), " at pages/settings/index.uvue:469")
>>>>>>> b47ceb2b6ee776642391bf71543f588897cdde9d
                }
            }
            val onAskEachTimeChange = ::gen_onAskEachTimeChange_fn
            onPageShow(fun(){
                try {
                    rules.value = getActiveRules()
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
                triggerEnabled.value = getBool("trigger_enabled", true)
                blacklistCount.value = readBlacklistCount()
            }
            )
            return fun(): Any? {
                val _component_switch = resolveComponent("switch")
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "page-bg"), _uA(
                        _cE("image", _uM("class" to "page-bg-img", "src" to "/static/images/micro-habit-page-bg.png", "mode" to "aspectFill"))
                    )),
                    _cE("view", _uM("class" to "bg-glow glow-purple")),
                    _cE("view", _uM("class" to "bg-glow glow-blue")),
                    _cE("view", _uM("class" to "bg-glow glow-pink")),
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "settings-hero"), _uA(
                            _cE("view", _uM("class" to "settings-hero-copy"), _uA(
                                _cE("text", _uM("class" to "settings-kicker"), "微憩星球"),
                                _cE("text", _uM("class" to "settings-title"), "守护中心"),
                                _cE("text", _uM("class" to "settings-subtitle"), "你的小星球会记录节奏，提醒也会越来越懂你。")
                            )),
                            _cE("view", _uM("class" to "settings-planet"), _uA(
                                _cE("view", _uM("class" to "settings-orbit")),
                                _cE("text", _uM("class" to "settings-planet-text"), "◐")
                            ))
                        )),
                        _cE("view", _uM("class" to "planet-note"), _uA(
                            _cE("text", _uM("class" to "planet-note-title"), "今日也可以只做一点点"),
                            _cE("text", _uM("class" to "planet-note-text"), "微动作不追求强度，只是在合适的时候，把你从屏幕里轻轻拉回来。")
                        )),
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "守护模式"),
                            _cE("view", _uM("class" to "setting-item"), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "自动触发"),
                                    _cE("text", _uM("class" to "setting-hint"), "开启后才会根据应用使用时长自动弹出微动作提醒")
                                )),
                                _cV(_component_switch, _uM("checked" to unref(triggerEnabled), "onChange" to onTriggerEnabledChange), null, 8, _uA(
                                    "checked"
                                ))
                            )),
                            _cE("view", _uM("class" to "setting-item", "onClick" to onManualTrigger), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "立即检查一次"),
                                    _cE("text", _uM("class" to "setting-hint"), "主动查询当前 App 使用时长并判断是否触发微动作提醒")
                                )),
                                _cE("text", _uM("class" to "setting-value"), "检查 ›")
                            ))
                        )),
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "AI 分析与提醒"),
                            _cE("view", _uM("class" to "setting-item", "onClick" to openDndPicker), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "免打扰时段"),
                                    _cE("text", _uM("class" to "setting-hint"), "该时段不触发提醒")
                                )),
                                _cE("text", _uM("class" to "setting-value"), _tD(unref(dndStart)) + " - " + _tD(unref(dndEnd)) + " ›", 1)
                            )),
                            _cE("view", _uM("class" to "setting-item"), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "启用 AI 实时建议"),
                                    _cE("text", _uM("class" to "setting-hint"), "触发时生成轻量开场白，并复盘提醒规则")
                                )),
                                _cV(_component_switch, _uM("checked" to unref(llmEnabled), "onChange" to onLlmEnabledChange), null, 8, _uA(
                                    "checked"
                                ))
                            )),
                            _cE("view", _uM("class" to "setting-item"), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "每次微动作后询问 AI 建议"),
                                    _cE("text", _uM("class" to "setting-hint"), "关闭后默认接受 AI 建议，不再每次询问")
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
                            if (unref(rules).length < 1) {
                                _cE("view", _uM("key" to 0, "class" to "empty-rules"), _uA(
                                    _cE("text", _uM("class" to "empty-text"), "暂无启用的触发规则"),
                                    _cE("text", _uM("class" to "empty-hint"), "完成微动作后 AI 会自动建议规则")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE(Fragment, null, RenderHelpers.renderList(unref(rules), fun(rule, idx, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("r-" + idx), "class" to "rule-card", "onClick" to fun(){
                                    onEditRule(rule)
                                }
                                ), _uA(
                                    _cE("view", _uM("class" to "rule-row1"), _uA(
                                        _cE("text", _uM("class" to "rule-type"), _tD(getActionDisplayName(rule.actionId)), 1),
                                        _cE("text", _uM("class" to "rule-trigger"), "≥ " + _tD(rule.timeThresholdMinutes) + " 分钟", 1),
                                        _cE("text", _uM("class" to "rule-priority"), _tD(formatTriggerLevel(rule.triggerLevel)), 1)
                                    )),
                                    _cE("view", _uM("class" to "rule-row2"), _uA(
                                        _cE("text", _uM("class" to "rule-source"), _tD(formatSource(rule.source)), 1),
                                        _cE("text", _uM("class" to "rule-cond"), _tD(formatAppPackages(rule.appPackages)) + _tD(if (rule.timeWindow != null) {
                                            " · " + rule.timeWindow!!.start + "-" + rule.timeWindow!!.end
                                        } else {
                                            ""
                                        }
                                        ), 1)
                                    )),
                                    _cE("view", _uM("class" to "rule-row3"), _uA(
                                        _cE("text", _uM("class" to "rule-time"), "创建 " + _tD(formatTime(rule.createdAt)), 1)
                                    ))
                                ), 8, _uA(
                                    "onClick"
                                ))
                            }
                            ), 128)
                        )),
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "App 分类"),
                            _cE("view", _uM("class" to "setting-item", "onClick" to goAppCategories), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "App 分类管理"),
                                    _cE("text", _uM("class" to "setting-hint"), "手动调整每个 App 的分类，锁定后 LLM 不会覆盖")
                                )),
                                _cE("text", _uM("class" to "setting-value"), "›")
                            )),
                            _cE("view", _uM("class" to "setting-item", "onClick" to goBlacklist), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "不提醒这些 App"),
                                    _cE("text", _uM("class" to "setting-hint"), "系统应用已默认排除，此处可补充不希望被提醒的第三方 App")
                                )),
                                _cE("text", _uM("class" to "setting-value"), _tD(unref(blacklistCount)) + " 个 ›", 1)
                            ))
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
                                    _cE("text", _uM("class" to "setting-label"), "查看调试日志")
                                )),
                                _cE("text", _uM("class" to "setting-value"), "›")
                            )),
                            _cE("view", _uM("class" to "setting-item", "onClick" to resetAchievements), _uA(
                                _cE("view", _uM("class" to "setting-left"), _uA(
                                    _cE("text", _uM("class" to "setting-label"), "重置成就进度"),
                                    _cE("text", _uM("class" to "setting-hint"), "清空所有已解锁成就，可重新触发")
                                )),
                                _cE("text", _uM("class" to "setting-value"), "›")
                            ))
                        )),
                        _cE("view", _uM("class" to "settings-section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "关于微憩星球"),
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
                    )),
                    _cV(unref(GenComponentsRuleEditDialogClass), _uM("visible" to unref(showRuleEditor), "rule-json" to unref(editingRuleJson), "onClose" to onRuleEditorClose, "onSave" to onRuleSave, "onDelete" to onRuleDelete), null, 8, _uA(
                        "visible",
                        "rule-json"
                    ))
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0,
                styles1
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
<<<<<<< HEAD
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "rgba(0,0,0,0)", "position" to "relative", "overflow" to "hidden")), "page-bg" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "width" to "100%", "height" to "100%", "zIndex" to 0)), "page-bg-img" to _pS(_uM("width" to "100%", "height" to "100%")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "zIndex" to 1)), "settings-section" to _pS(_uM("marginTop" to 12, "backgroundColor" to "#FFFFFF")), "section-title" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D", "fontWeight" to "bold", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 6, "paddingLeft" to 16)), "setting-item" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "setting-left" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "setting-label" to _pS(_uM("fontSize" to 15, "color" to "#2C3E50")), "setting-hint" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginTop" to 2)), "setting-value" to _pS(_uM("fontSize" to 14, "color" to "#3498DB", "fontWeight" to 500)), "empty-rules" to _pS(_uM("alignItems" to "center", "paddingTop" to 20, "paddingRight" to 0, "paddingBottom" to 20, "paddingLeft" to 0)), "empty-text" to _pS(_uM("fontSize" to 13, "color" to "#BDC3C7")), "rule-card" to _pS(_uM("paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "rule-row1" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 4)), "rule-type" to _pS(_uM("fontSize" to 13, "fontWeight" to "bold", "color" to "#2C3E50", "backgroundColor" to "#EBF5FB", "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "paddingTop" to 2, "paddingRight" to 6, "paddingBottom" to 2, "paddingLeft" to 6, "marginRight" to 6)), "rule-trigger" to _pS(_uM("fontSize" to 11, "color" to "#7F8C8D", "backgroundColor" to "#F0F3F4", "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "paddingTop" to 2, "paddingRight" to 6, "paddingBottom" to 2, "paddingLeft" to 6)), "rule-priority" to _pS(_uM("fontSize" to 11, "color" to "#3498DB", "marginLeft" to "auto")), "rule-row2" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 4)), "rule-source" to _pS(_uM("fontSize" to 11, "color" to "#27AE60", "marginRight" to 8)), "rule-cond" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "rule-row3" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "marginTop" to 4)), "rule-time" to _pS(_uM("fontSize" to 10, "color" to "#BDC3C7", "fontFamily" to "monospace")), "bottom-spacer" to _pS(_uM("height" to 80)))
=======
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#162038", "position" to "relative", "overflow" to "hidden")), "page-bg" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "width" to "100%", "height" to "100%", "zIndex" to 0)), "page-bg-img" to _pS(_uM("width" to "100%", "height" to "100%")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "zIndex" to 1)), "bg-glow" to _pS(_uM("position" to "absolute", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "zIndex" to 0)), "glow-purple" to _pS(_uM("width" to 210, "height" to 210, "left" to -82, "top" to -54, "backgroundColor" to "#776B97", "opacity" to 0.5)), "glow-blue" to _pS(_uM("width" to 168, "height" to 168, "right" to -60, "top" to 104, "backgroundColor" to "#C2E0EE", "opacity" to 0.24)), "glow-pink" to _pS(_uM("width" to 178, "height" to 178, "left" to 42, "bottom" to 88, "backgroundColor" to "#C68DC0", "opacity" to 0.18)), "sky-star" to _pS(_uM("position" to "absolute", "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "rgba(255,255,255,0.74)", "zIndex" to 0)), "star-a" to _pS(_uM("left" to 36, "top" to 88)), "star-b" to _pS(_uM("left" to 118, "top" to 144, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-c" to _pS(_uM("right" to 52, "top" to 116)), "star-d" to _pS(_uM("right" to 26, "top" to 310, "width" to 5, "height" to 5, "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "star-e" to _pS(_uM("left" to 54, "top" to 520)), "star-f" to _pS(_uM("right" to 86, "top" to 420, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-g" to _pS(_uM("left" to 138, "top" to 610)), "star-h" to _pS(_uM("right" to 36, "top" to 720, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-i" to _pS(_uM("left" to 76, "top" to 214, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-j" to _pS(_uM("right" to 148, "top" to 276, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-k" to _pS(_uM("left" to 178, "top" to 392, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-l" to _pS(_uM("right" to 64, "top" to 560, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "dashboard-hero" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 22, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 22, "paddingRight" to 18, "paddingBottom" to 22, "paddingLeft" to 18, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "flexDirection" to "row", "alignItems" to "center")), "settings-hero" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 22, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 22, "paddingRight" to 18, "paddingBottom" to 22, "paddingLeft" to 18, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "flexDirection" to "row", "alignItems" to "center")), "planet-note" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 0, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 16, "paddingRight" to 18, "paddingBottom" to 16, "paddingLeft" to 18, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24)), "section" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 12, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "overflow" to "hidden")), "settings-section" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 12, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "overflow" to "hidden")), "guard-box" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 8, "marginRight" to 16, "marginBottom" to 12, "marginLeft" to 16, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "alignItems" to "center")), "chart-container" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 10, "marginRight" to 16, "marginBottom" to 0, "marginLeft" to 16, "paddingTop" to 14, "paddingRight" to 0, "paddingBottom" to 6, "paddingLeft" to 0, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22)), "report-stats" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "flexDirection" to "row", "justifyContent" to "space-around", "marginTop" to 10, "marginRight" to 16, "marginBottom" to 10, "marginLeft" to 16, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "tag-card" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "marginBottom" to 10, "flexDirection" to "row")), "header-block" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 22, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 20, "paddingRight" to 18, "paddingBottom" to 20, "paddingLeft" to 18, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "flexDirection" to "column")), "filter-row" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 0, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 4, "paddingLeft" to 12, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22)), "empty-block" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 12, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 50, "paddingRight" to 20, "paddingBottom" to 50, "paddingLeft" to 20, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "alignItems" to "center")), "dashboard-hero-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "paddingRight" to 12)), "settings-hero-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "paddingRight" to 12)), "dashboard-kicker" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 13, "fontWeight" to "bold", "marginBottom" to 8)), "settings-kicker" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 13, "fontWeight" to "bold", "marginBottom" to 8)), "section-toggle" to _pS(_uM("color" to "#DBC8ED")), "setting-value" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 14, "fontWeight" to "bold")), "rule-priority" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 11, "marginLeft" to "auto")), "rule-source" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 11, "marginRight" to 8)), "detail-duration" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 14, "fontWeight" to "bold")), "guard-value" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 28, "fontWeight" to "bold", "marginTop" to 4)), "stat-val" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 20, "fontWeight" to "bold")), "dashboard-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 28, "fontWeight" to "bold", "lineHeight" to "34px")), "settings-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 28, "fontWeight" to "bold", "lineHeight" to "34px")), "section-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 18, "fontWeight" to "bold", "paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 0, "paddingLeft" to 0)), "setting-label" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 15, "fontWeight" to "bold")), "planet-note-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 16, "fontWeight" to "bold", "marginBottom" to 6)), "detail-name" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14)), "report-body" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14, "lineHeight" to "22px", "whiteSpace" to "pre-wrap", "marginTop" to 8)), "report-title" to _pS(_uM("color" to "#FFFFFF")), "header-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 22, "fontWeight" to "bold", "marginBottom" to 8)), "tag-label" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14, "fontWeight" to "bold", "marginBottom" to 3)), "dashboard-subtitle" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 13, "lineHeight" to "20px", "marginTop" to 8)), "settings-subtitle" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 13, "lineHeight" to "20px", "marginTop" to 8)), "planet-note-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "19px")), "setting-hint" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "18px", "marginTop" to 4)), "guard-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "guard-sub" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "chart-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "paddingLeft" to 14, "marginBottom" to 0, "fontSize" to 13, "fontWeight" to "bold")), "legend-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "loading-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "detail-type" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "marginTop" to 2)), "detail-time" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "marginTop" to 2)), "stat-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "rule-cond" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "rule-time" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 10)), "header-desc" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "19px")), "tag-pkg" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "marginBottom" to 7)), "tag-uses" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-hint" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "alignItems" to "center", "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20)), "dashboard-planet" to _pS(_uM("position" to "relative", "width" to 86, "height" to 86, "borderTopLeftRadius" to 43, "borderTopRightRadius" to 43, "borderBottomRightRadius" to 43, "borderBottomLeftRadius" to 43, "backgroundColor" to "rgba(219,200,237,0.18)", "alignItems" to "center", "justifyContent" to "center")), "settings-planet" to _pS(_uM("position" to "relative", "width" to 86, "height" to 86, "borderTopLeftRadius" to 43, "borderTopRightRadius" to 43, "borderBottomRightRadius" to 43, "borderBottomLeftRadius" to 43, "backgroundColor" to "rgba(219,200,237,0.18)", "alignItems" to "center", "justifyContent" to "center")), "dashboard-orbit" to _pS(_uM("position" to "absolute", "width" to 96, "height" to 34, "borderTopLeftRadius" to 48, "borderTopRightRadius" to 48, "borderBottomRightRadius" to 48, "borderBottomLeftRadius" to 48, "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "transform" to "rotate(-20deg)")), "settings-orbit" to _pS(_uM("position" to "absolute", "width" to 96, "height" to 34, "borderTopLeftRadius" to 48, "borderTopRightRadius" to 48, "borderBottomRightRadius" to 48, "borderBottomLeftRadius" to 48, "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "transform" to "rotate(-20deg)")), "dashboard-planet-text" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 34)), "settings-planet-text" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 34)), "section-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 16, "paddingRight" to 18, "paddingBottom" to 8, "paddingLeft" to 18)), "section-body" to _pS(_uM("paddingTop" to 8, "paddingRight" to 0, "paddingBottom" to 14, "paddingLeft" to 0)), "list-section" to _pS(_uM("paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16)), "detail-item" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 10, "paddingRight" to 0, "paddingBottom" to 10, "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomColor" to "rgba(219,200,237,0.16)", "borderBottomStyle" to "solid")), "detail-left" to _pS(_uM("flexDirection" to "column")), "detail-right" to _pS(_uM("flexDirection" to "column", "alignItems" to "flex-end")), "setting-left" to _pS(_uM("flexDirection" to "column", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "heatmap-legend" to _pS(_uM("alignItems" to "center", "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20)), "loading-box" to _pS(_uM("alignItems" to "center", "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20)), "report-box" to _pS(_uM("paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16)), "stat-item" to _pS(_uM("alignItems" to "center")), "refresh-btn" to _pS(_uM("marginTop" to 12, "paddingTop" to 10, "paddingRight" to 24, "paddingBottom" to 10, "paddingLeft" to 24, "backgroundColor" to "#DBC8ED", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "refresh-text" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")), "setting-item" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 15, "paddingRight" to 18, "paddingBottom" to 15, "paddingLeft" to 18, "borderBottomWidth" to 1, "borderBottomColor" to "rgba(219,200,237,0.16)", "borderBottomStyle" to "solid")), "empty-rules" to _pS(_uM("alignItems" to "center", "paddingTop" to 24, "paddingRight" to 16, "paddingBottom" to 28, "paddingLeft" to 16)), "rule-card" to _pS(_uM("paddingTop" to 14, "paddingRight" to 18, "paddingBottom" to 14, "paddingLeft" to 18, "borderBottomWidth" to 1, "borderBottomColor" to "rgba(219,200,237,0.16)", "borderBottomStyle" to "solid")), "rule-row1" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 4)), "rule-row2" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 4)), "rule-type" to _pS(_uM("fontSize" to 11, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 4, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8, "marginRight" to 6, "color" to "#FFFFFF", "backgroundColor" to "rgba(219,200,237,0.18)", "fontWeight" to "bold")), "rule-trigger" to _pS(_uM("fontSize" to 11, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 4, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8, "marginRight" to 6, "color" to "rgba(255,255,255,0.72)", "backgroundColor" to "rgba(194,224,238,0.12)")), "rule-row3" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "marginTop" to 4)))
            }
        val styles1: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("filter-chip" to _pS(_uM("paddingTop" to 6, "paddingRight" to 12, "paddingBottom" to 6, "paddingLeft" to 12, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "marginRight" to 6, "marginBottom" to 8)), "filter-chip-active" to _pS(_uM("backgroundColor" to "#DBC8ED")), "filter-chip-text" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF")), "tag-list" to _pS(_uM("paddingTop" to 0, "paddingRight" to 14, "paddingBottom" to 18, "paddingLeft" to 14)), "tag-card-left" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "tag-card-right" to _pS(_uM("flexDirection" to "column", "alignItems" to "flex-end", "marginLeft" to 10)), "tag-source-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "tag-source" to _pS(_uM("fontSize" to 10, "paddingTop" to 3, "paddingRight" to 7, "paddingBottom" to 3, "paddingLeft" to 7, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "marginRight" to 6)), "tag-source-llm" to _pS(_uM("backgroundColor" to "rgba(194,224,238,0.14)", "color" to "#C2E0EE")), "tag-source-user" to _pS(_uM("backgroundColor" to "rgba(143,240,195,0.12)", "color" to "#8FF0C3")), "tag-source-pending" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.14)", "color" to "#DBC8ED")), "cat-picker" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "rgba(255,255,255,0.1)", "paddingTop" to 5, "paddingRight" to 10, "paddingBottom" to 5, "paddingLeft" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "marginBottom" to 6)), "cat-picker-text" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "marginRight" to 4)), "cat-picker-arrow" to _pS(_uM("color" to "rgba(255,255,255,0.68)")), "lock-text" to _pS(_uM("color" to "rgba(255,255,255,0.68)")), "lock-text-on" to _pS(_uM("color" to "#DBC8ED", "fontWeight" to "bold")), "bottom-spacer" to _pS(_uM("height" to 132)))
>>>>>>> b47ceb2b6ee776642391bf71543f588897cdde9d
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
