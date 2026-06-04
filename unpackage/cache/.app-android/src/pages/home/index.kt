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
import io.dcloud.uniapp.extapi.`$on` as uni__on
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesHomeIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesHomeIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesHomeIndex
            val _cache = __ins.renderCache
            val store = useAppStore()
            val nowMs = ref<Number>(Date.now())
            val nowHour = ref<Number>(currentHour())
            var clockTimerId: Number? = null
            val eyeScore = computed<Number>(fun(): Number {
                return store.eyeScore.value
            }
            )
            val eyeStatus = computed<String>(fun(): String {
                return store.eyeStatus.value
            }
            )
            val postureScore = computed<Number>(fun(): Number {
                return store.postureScore.value
            }
            )
            val postureStatus = computed<String>(fun(): String {
                return store.postureStatus.value
            }
            )
            val vitalityScore = computed<Number>(fun(): Number {
                return store.vitalityScore.value
            }
            )
            val vitalityStatus = computed<String>(fun(): String {
                return store.vitalityStatus.value
            }
            )
            val guardCount = computed<Number>(fun(): Number {
                return store.guardCount.value
            }
            )
            val penetration = computed<Number>(fun(): Number {
                return store.penetration.value
            }
            )
            val penetrationStr = computed<String>(fun(): String {
                return penetration.value.toFixed(1)
            }
            )
            val recommendedActions = computed<UTSArray<MicroAction>>(fun(): UTSArray<MicroAction> {
                return store.recommendedActions.value
            }
            )
            val allActions = computed<UTSArray<MicroAction>>(fun(): UTSArray<MicroAction> {
                return getEnabledActions()
            }
            )
            val achievements = computed<UTSArray<AchievementItem>>(fun(): UTSArray<AchievementItem> {
                return store.achievements.value
            }
            )
            val achievementsCollapsed = ref<Boolean>(getInt("achievements_collapsed", 1) == 1)
            val seenSet = ref<Set<String>>(Set<String>())
            val unlockedCount = computed<Number>(fun(): Number {
                var n: Number = 0
                run {
                    var i: Number = 0
                    while(i < achievements.value.length){
                        val item = achievements.value[i]
                        if (item != null && item.isUnlocked) {
                            n++
                        }
                        i++
                    }
                }
                return n
            }
            )
            val newUnlockedCount = computed<Number>(fun(): Number {
                var n: Number = 0
                run {
                    var i: Number = 0
                    while(i < achievements.value.length){
                        val item = achievements.value[i]
                        if (item != null && item.isUnlocked && !seenSet.value.has(item.def.id)) {
                            n++
                        }
                        i++
                    }
                }
                return n
            }
            )
            fun gen_refreshSeenSet_fn(): Unit {
                try {
                    val raw = getSetting("seen_achievements", "[]")
                    val arr = UTSAndroid.consoleDebugError(JSON.parse(raw), " at pages/home/index.uvue:322") as UTSArray<Any>
                    val s = Set<String>()
                    if (arr != null) {
                        run {
                            var i: Number = 0
                            while(i < arr.length){
                                val v = arr[i]
                                if (UTSAndroid.`typeof`(v) === "string" && (v as String).length > 0) {
                                    s.add(v as String)
                                }
                                i++
                            }
                        }
                    }
                    seenSet.value = s
                }
                 catch (_: Throwable) {
                    seenSet.value = Set<String>()
                }
            }
            val refreshSeenSet = ::gen_refreshSeenSet_fn
            fun gen_markAchievementSeen_fn(id: String): Unit {
                val s = seenSet.value
                s.add(id)
                val arr: UTSArray<String> = _uA()
                s.forEach(fun(v: String): Unit {
                    arr.push(v)
                }
                )
                val json = "[" + arr.map(fun(v: String): String {
                    return "\"" + v + "\""
                }
                ).join(",") + "]"
                try {
                    putSetting("seen_achievements", json)
                }
                 catch (_: Throwable) {}
            }
            val markAchievementSeen = ::gen_markAchievementSeen_fn
            val showAchvDetail = ref<Boolean>(false)
            val detailItem = ref<AchievementItem?>(null)
            val detailIcon = ref<String>("")
            val detailName = ref<String>("")
            val detailCat = ref<String>("")
            val detailDesc = ref<String>("")
            val detailUnlocked = ref<Boolean>(false)
            val detailUnlockedDate = ref<String>("")
            val detailReward = ref<String>("")
            val detailProgressText = ref<String>("")
            val detailProgressPercent = ref<Number>(0)
            val detailHistoryText = ref<String>("")
            val showAchvUnlock = ref<Boolean>(false)
            val unlockIcon = ref<String>("")
            val unlockName = ref<String>("")
            val unlockDesc = ref<String>("")
            val unlockReward = ref<String>("")
            val unlockCurrentId = ref<String>("")
            fun gen_toggleAchievementsCollapsed_fn(): Unit {
                achievementsCollapsed.value = !achievementsCollapsed.value
                try {
                    putInt("achievements_collapsed", if (achievementsCollapsed.value) {
                        1
                    } else {
                        0
                    }
                    )
                }
                 catch (_: Throwable) {}
            }
            val toggleAchievementsCollapsed = ::gen_toggleAchievementsCollapsed_fn
            fun gen_openAchvDetail_fn(item: AchievementItem): Unit {
                detailItem.value = item
                detailIcon.value = item.def.icon
                detailName.value = item.def.name
                detailCat.value = item.def.category
                detailDesc.value = item.def.description
                detailUnlocked.value = item.isUnlocked
                if (item.isUnlocked && item.unlockedAt > 0) {
                    val d = Date(item.unlockedAt * 1000)
                    detailUnlockedDate.value = d.getFullYear() + "-" + ("" + (d.getMonth() + 1)).padStart(2, "0") + "-" + ("" + d.getDate()).padStart(2, "0")
                } else {
                    detailUnlockedDate.value = ""
                }
                detailReward.value = item.def.rewardText
                val prog = getProgressText(item)
                detailProgressText.value = prog.text
                detailProgressPercent.value = prog.percent
                detailHistoryText.value = item.def.historyText
                showAchvDetail.value = true
            }
            val openAchvDetail = ::gen_openAchvDetail_fn
            fun gen_closeAchvDetail_fn(): Unit {
                showAchvDetail.value = false
                detailItem.value = null
            }
            val closeAchvDetail = ::gen_closeAchvDetail_fn
            fun gen_tryShowNextUnlock_fn(): Unit {
                val pending = store.pendingUnlockIds.value
                if (pending.length < 1) {
                    return
                }
                val firstId = pending[0] as String
                val def = getAchievementDef(firstId)
                if (def == null) {
                    store.shiftPendingUnlock()
                    gen_tryShowNextUnlock_fn()
                    return
                }
                unlockIcon.value = def.icon
                unlockName.value = def.name
                unlockDesc.value = def.description
                unlockReward.value = def.rewardText
                unlockCurrentId.value = firstId
                showAchvUnlock.value = true
            }
            val tryShowNextUnlock = ::gen_tryShowNextUnlock_fn
            fun gen_onUnlockDismiss_fn(): Unit {
                if (unlockCurrentId.value.length > 0) {
                    markAchievementSeen(unlockCurrentId.value)
                }
                showAchvUnlock.value = false
                unlockCurrentId.value = ""
                store.shiftPendingUnlock()
                setTimeout(fun(): Unit {
                    tryShowNextUnlock()
                }
                , 200)
            }
            val onUnlockDismiss = ::gen_onUnlockDismiss_fn
            fun gen_onUnlockViewDetail_fn(): Unit {
                val firstId = unlockCurrentId.value
                if (firstId.length > 0) {
                    markAchievementSeen(firstId)
                }
                showAchvUnlock.value = false
                unlockCurrentId.value = ""
                store.shiftPendingUnlock()
                setTimeout(fun(): Unit {
                    run {
                        var i: Number = 0
                        while(i < achievements.value.length){
                            val item = achievements.value[i]
                            if (item != null && item.def.id == firstId) {
                                openAchvDetail(item)
                                break
                            }
                            i++
                        }
                    }
                    tryShowNextUnlock()
                }
                , 200)
            }
            val onUnlockViewDetail = ::gen_onUnlockViewDetail_fn
            fun gen_onAchievementChipTap_fn(item: AchievementItem): Unit {
                if (item.isNew) {
                    markAchievementSeen(item.def.id)
                }
                openAchvDetail(item)
            }
            val onAchievementChipTap = ::gen_onAchievementChipTap_fn
            fun gen_onAchievementGridTap_fn(item: AchievementItem): Unit {
                if (item.isNew) {
                    markAchievementSeen(item.def.id)
                }
                openAchvDetail(item)
            }
            val onAchievementGridTap = ::gen_onAchievementGridTap_fn
            fun gen_getHomeHeatmapDates_fn(): UTSArray<String> {
                val rows = store.heatmapData.value
                val out: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < rows.length){
                        val item = rows[i]
                        if (item != null) {
                            out.push(item.date)
                        }
                        i++
                    }
                }
                return out
            }
            val getHomeHeatmapDates = ::gen_getHomeHeatmapDates_fn
            fun gen_getHomeHeatmapCounts_fn(): UTSArray<Number> {
                val rows = store.heatmapData.value
                val out: UTSArray<Number> = _uA()
                run {
                    var i: Number = 0
                    while(i < rows.length){
                        val item = rows[i]
                        if (item != null) {
                            out.push(item.count)
                        }
                        i++
                    }
                }
                return out
            }
            val getHomeHeatmapCounts = ::gen_getHomeHeatmapCounts_fn
            val homeHeatmapDates = computed<UTSArray<String>>(getHomeHeatmapDates)
            val homeHeatmapCounts = computed<UTSArray<Number>>(getHomeHeatmapCounts)
            val dailySummary = computed<DailyOutput?>(fun(): DailyOutput? {
                return store.dailySummary.value
            }
            )
            val dailySummaryLoading = computed<Boolean>(fun(): Boolean {
                return store.dailySummaryLoading.value
            }
            )
            fun gen_getSummaryOneLiner_fn(): String {
                val s = dailySummary.value
                return if (s != null && s.bodyText.length > 0) {
                    "今日小结已生成"
                } else {
                    "今日小结待生成"
                }
            }
            val getSummaryOneLiner = ::gen_getSummaryOneLiner_fn
            fun gen_getSummaryBody_fn(): String {
                val s = dailySummary.value
                return if (s != null) {
                    s.bodyText
                } else {
                    ""
                }
            }
            val getSummaryBody = ::gen_getSummaryBody_fn
            fun gen_getSummaryGoal_fn(): String {
                return ""
            }
            val getSummaryGoal = ::gen_getSummaryGoal_fn
            fun gen_getSummaryEncourage_fn(): String {
                return ""
            }
            val getSummaryEncourage = ::gen_getSummaryEncourage_fn
            fun gen_getSummaryHour_fn(): Number {
                val raw = getSetting("daily_summary_time", "21:00")
                val sep = raw.indexOf(":")
                val h = parseInt(if (sep > 0) {
                    raw.substring(0, sep)
                } else {
                    raw
                }
                )
                if (h < 0 || h > 23) {
                    return 21
                }
                return h
            }
            val getSummaryHour = ::gen_getSummaryHour_fn
            fun gen_getSummaryPlaceholder_fn(): String {
                if (dailySummaryLoading.value) {
                    return "正在生成今日小结…"
                }
                val cnt = store.todayCompletedCount.value
                if (cnt < 1) {
                    return "完成一个微动作，开启今日小结"
                }
                return "已完成" + cnt + "次，" + getSummaryHour() + ":00 后自动小结"
            }
            val getSummaryPlaceholder = ::gen_getSummaryPlaceholder_fn
            val summaryOneLiner = computed<String>(getSummaryOneLiner)
            val summaryBody = computed<String>(getSummaryBody)
            val summaryGoal = computed<String>(getSummaryGoal)
            val summaryEncourage = computed<String>(getSummaryEncourage)
            val summaryHour = computed<Number>(getSummaryHour)
            val summaryPlaceholder = computed<String>(getSummaryPlaceholder)
            fun gen_getGreeting_fn(): String {
                val h = nowHour.value
                if (h < 6) {
                    return "夜深了"
                }
                if (h < 9) {
                    return "早上好"
                }
                if (h < 12) {
                    return "上午好"
                }
                if (h < 14) {
                    return "中午好"
                }
                if (h < 18) {
                    return "下午好"
                }
                if (h < 22) {
                    return "晚上好"
                }
                return "夜深了"
            }
            val getGreeting = ::gen_getGreeting_fn
            fun gen_getGreetingEmoji_fn(): String {
                val h = nowHour.value
                if (h < 6) {
                    return "🌙"
                }
                if (h < 9) {
                    return "☀️"
                }
                if (h < 12) {
                    return "☀️"
                }
                if (h < 14) {
                    return "🌤"
                }
                if (h < 18) {
                    return "⛅"
                }
                if (h < 22) {
                    return "🌆"
                }
                return "🌙"
            }
            val getGreetingEmoji = ::gen_getGreetingEmoji_fn
            val greeting = computed<String>(getGreeting)
            val greetingEmoji = computed<String>(getGreetingEmoji)
            val greetingHint = computed<String>(fun(): String {
                return getGreetingHint(nowHour.value)
            }
            )
            val phoneMinutes = computed<Number>(fun(): Number {
                return store.phoneMinutes.value
            }
            )
            fun gen_getPhoneUsageHint_fn(): String {
                if (phoneMinutes.value < 1) {
                    return "点击查看今日手机使用信号"
                }
                return "今日已使用 " + phoneMinutes.value + " 分钟，看看星球信号"
            }
            val getPhoneUsageHint = ::gen_getPhoneUsageHint_fn
            val phoneUsageHint = computed<String>(getPhoneUsageHint)
            fun gen_getGuardMinuteText_fn(): String {
                if (guardCount.value < 1) {
                    return "今天的小星球还在等第一次点亮"
                }
                return "已为你守护约 " + guardCount.value + " 分钟"
            }
            val getGuardMinuteText = ::gen_getGuardMinuteText_fn
            fun gen_getPlanetVisualClass_fn(): String {
                val count = guardCount.value
                if (count < 1) {
                    return "planet-dim"
                }
                if (count < 4) {
                    return "planet-soft"
                }
                if (count < 9) {
                    return "planet-ring-on"
                }
                return "planet-bright"
            }
            val getPlanetVisualClass = ::gen_getPlanetVisualClass_fn
            fun gen_getPlanetCoreClass_fn(): String {
                if (guardCount.value < 1) {
                    return "planet-core-dim"
                }
                return "planet-core-on"
            }
            val getPlanetCoreClass = ::gen_getPlanetCoreClass_fn
            fun gen_getShowPlanetRing_fn(): Boolean {
                return guardCount.value >= 4
            }
            val getShowPlanetRing = ::gen_getShowPlanetRing_fn
            fun gen_getShowPlanetParticles_fn(): Boolean {
                return guardCount.value >= 9
            }
            val getShowPlanetParticles = ::gen_getShowPlanetParticles_fn
            val guardMinuteText = computed<String>(getGuardMinuteText)
            val planetVisualClass = computed<String>(getPlanetVisualClass)
            val planetCoreClass = computed<String>(getPlanetCoreClass)
            val showPlanetRing = computed<Boolean>(getShowPlanetRing)
            val showPlanetParticles = computed<Boolean>(getShowPlanetParticles)
            val showPhoneDialog = ref<Boolean>(false)
            val showRuleDialog = ref<Boolean>(false)
            val pendingRule = ref<EffectiveTriggerRule?>(null)
            val pendingReasoning = ref<String>("")
            val ruleActionName = computed<String>(fun(): String {
                val r = pendingRule.value
                if (r == null) {
                    return ""
                }
                val a = getActionById(r.actionId)
                return if (a != null) {
                    a.name
                } else {
                    "微动作"
                }
            }
            )
            val ruleTimeWindowStart = computed<String>(fun(): String {
                val r = pendingRule.value
                if (r == null || r.timeWindow == null) {
                    return ""
                }
                return r.timeWindow!!.start
            }
            )
            val ruleTimeWindowEnd = computed<String>(fun(): String {
                val r = pendingRule.value
                if (r == null || r.timeWindow == null) {
                    return ""
                }
                return r.timeWindow!!.end
            }
            )
            val ruleTimeThresholdMinutes = computed<Number>(fun(): Number {
                val r = pendingRule.value
                if (r == null) {
                    return 0
                }
                return r.timeThresholdMinutes
            }
            )
            val ruleScreenCondText = computed<String>(fun(): String {
                val r = pendingRule.value
                if (r == null || r.screenConditions == null) {
                    return ""
                }
                val sc = r.screenConditions!!
                val parts: UTSArray<String> = _uA()
                if (sc.includeHome) {
                    parts.push("桌面")
                }
                if (sc.appPackages.length > 0) {
                    parts.push(sc.appPackages.join(", "))
                }
                return if (parts.length > 0) {
                    parts.join(" / ")
                } else {
                    "任意"
                }
            }
            )
            val llmHistoryEntries = computed<UTSArray<LlmHistoryEntry>>(fun(): UTSArray<LlmHistoryEntry> {
                return store.llmHistory.value
            }
            )
            fun gen_openPhoneDialog_fn(): Unit {
                showPhoneDialog.value = true
            }
            val openPhoneDialog = ::gen_openPhoneDialog_fn
            fun gen_closePhoneDialog_fn(): Unit {
                showPhoneDialog.value = false
            }
            val closePhoneDialog = ::gen_closePhoneDialog_fn
            fun gen_closeRuleDialog_fn(): Unit {
                showRuleDialog.value = false
                pendingRule.value = null
                pendingReasoning.value = ""
            }
            val closeRuleDialog = ::gen_closeRuleDialog_fn
            fun gen_acceptRule_fn(): Unit {
                showRuleDialog.value = false
                pendingRule.value = null
                pendingReasoning.value = ""
                uni_showToast(ShowToastOptions(title = "规则已接受（持久化待 Day 5）", icon = "none"))
            }
            val acceptRule = ::gen_acceptRule_fn
            fun gen_neverAskRule_fn(): Unit {
                putBool("llm_trigger_ask_each_time", false)
                closeRuleDialog()
                uni_showToast(ShowToastOptions(title = "已关闭\"每次问询\"", icon = "none"))
            }
            val neverAskRule = ::gen_neverAskRule_fn
            fun gen_getGreetingClass_fn(): String {
                val h = nowHour.value
                if (h < 6) {
                    return "bg-night"
                }
                if (h < 9) {
                    return "bg-morning"
                }
                if (h < 14) {
                    return "bg-noon"
                }
                if (h < 18) {
                    return "bg-afternoon"
                }
                if (h < 22) {
                    return "bg-evening"
                }
                return "bg-night"
            }
            val getGreetingClass = ::gen_getGreetingClass_fn
            fun gen_getDateStr_fn(): String {
                val tick = nowMs.value
                if (tick < 1) {
                    return ""
                }
                val d = Date()
                val m = d.getMonth() + 1
                val day = d.getDate()
                val weekDays = _uA(
                    "日",
                    "一",
                    "二",
                    "三",
                    "四",
                    "五",
                    "六"
                ) as UTSArray<String>
                return m + "月" + day + "日 · 星期" + weekDays[d.getDay()]
            }
            val getDateStr = ::gen_getDateStr_fn
            val greetingClass = computed<String>(getGreetingClass)
            val dateStr = computed<String>(getDateStr)
            fun gen_refreshClock_fn(): Unit {
                nowMs.value = Date.now()
                nowHour.value = currentHour()
            }
            val refreshClock = ::gen_refreshClock_fn
            fun gen_clearClockTimer_fn(): Unit {
                val tid = clockTimerId
                if (tid != null) {
                    clearInterval(tid)
                    clockTimerId = null
                }
            }
            val clearClockTimer = ::gen_clearClockTimer_fn
            fun gen_startClockTimer_fn(): Unit {
                clearClockTimer()
                clockTimerId = setInterval(fun(): Unit {
                    refreshClock()
                }
                , 60000)
            }
            val startClockTimer = ::gen_startClockTimer_fn
            onPageShow(fun(){
                refreshClock()
                store.refreshHomeData()
                store.refreshLlmHistory()
                refreshSeenSet()
                store.refreshAchievements()
                setTimeout(fun(): Unit {
                    tryShowNextUnlock()
                }
                , 500)
            }
            )
            fun gen_startAction_fn(actionId: String): Unit {
                setActionIdForNextPage(actionId)
                uni_navigateTo(NavigateToOptions(url = "/pages/action/execute?actionId=" + actionId))
            }
            val startAction = ::gen_startAction_fn
            fun gen_startHeroAction_fn(): Unit {
                val list = recommendedActions.value
                if (list.length > 0) {
                    val first = list[0]
                    if (first != null) {
                        startAction(first.id)
                        return
                    }
                }
                val all = allActions.value
                if (all.length > 0) {
                    val firstAll = all[0]
                    if (firstAll != null) {
                        startAction(firstAll.id)
                    }
                }
            }
            val startHeroAction = ::gen_startHeroAction_fn
            fun gen_goSettings_fn(): Unit {
                uni_switchTab(SwitchTabOptions(url = "/pages/settings/index"))
            }
            val goSettings = ::gen_goSettings_fn
            onLoad(fun(_options): Unit {
                refreshClock()
                startClockTimer()
                try {
                    uni__on("showTriggerRuleDialog", fun(data: Any): Unit {
                        try {
                            if (data == null) {
                                return
                            }
                            val obj = data as UTSJSONObject
                            if (obj == null) {
                                return
                            }
                            val ruleRaw = obj["rule"]
                            val reasoningRaw = obj["reasoning"]
                            val ruleStr = JSON.stringify(ruleRaw)
                            val rule = UTSAndroid.consoleDebugError(JSON.parse(ruleStr), " at pages/home/index.uvue:743") as EffectiveTriggerRule
                            if (rule == null) {
                                return
                            }
                            pendingRule.value = rule
                            val reasoning = if (UTSAndroid.`typeof`(reasoningRaw) === "string") {
                                (reasoningRaw as String)
                            } else {
                                ""
                            }
                            pendingReasoning.value = reasoning
                            showRuleDialog.value = true
                        }
                         catch (e: Throwable) {
                            console.warn("[home] showTriggerRuleDialog 处理异常: " + JSON.stringify(e), " at pages/home/index.uvue:750")
                        }
                    }
                    )
                }
                 catch (e: Throwable) {
                    console.warn("[home] \$on showTriggerRuleDialog 异常: " + JSON.stringify(e), " at pages/home/index.uvue:754")
                }
                try {
                    uni__on("pendingAchievementUnlock", fun(data: Any): Unit {
                        try {
                            if (data == null) {
                                return
                            }
                            val obj = data as UTSJSONObject
                            if (obj == null) {
                                return
                            }
                            val idsRaw = obj["ids"]
                            if (idsRaw == null) {
                                return
                            }
                            val idsStr = JSON.stringify(idsRaw)
                            val ids = UTSAndroid.consoleDebugError(JSON.parse(idsStr), " at pages/home/index.uvue:765") as UTSArray<Any>
                            if (ids == null || ids.length < 1) {
                                return
                            }
                            val idArr: UTSArray<String> = _uA()
                            run {
                                var i: Number = 0
                                while(i < ids.length){
                                    val v = ids[i]
                                    if (UTSAndroid.`typeof`(v) === "string" && (v as String).length > 0) {
                                        idArr.push(v as String)
                                    }
                                    i++
                                }
                            }
                            if (idArr.length > 0) {
                                store.setPendingUnlocks(idArr)
                                tryShowNextUnlock()
                            }
                        }
                         catch (e: Throwable) {
                            console.warn("[home] pendingAchievementUnlock 异常: " + JSON.stringify(e), " at pages/home/index.uvue:779")
                        }
                    }
                    )
                }
                 catch (e: Throwable) {
                    console.warn("[home] \$on pendingAchievementUnlock 异常: " + JSON.stringify(e), " at pages/home/index.uvue:783")
                }
            }
            )
            onUnload(fun(): Unit {
                clearClockTimer()
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "bg-glow glow-purple")),
                    _cE("view", _uM("class" to "bg-glow glow-blue")),
                    _cE("view", _uM("class" to "bg-glow glow-pink")),
                    _cE("view", _uM("class" to "sky-moon")),
                    _cE("view", _uM("class" to "sky-cloud cloud-a")),
                    _cE("view", _uM("class" to "sky-cloud cloud-b")),
                    _cE("view", _uM("class" to "sky-star star-a")),
                    _cE("view", _uM("class" to "sky-star star-b")),
                    _cE("view", _uM("class" to "sky-star star-c")),
                    _cE("view", _uM("class" to "sky-star star-d")),
                    _cE("view", _uM("class" to "sky-star star-e")),
                    _cE("view", _uM("class" to "sky-star star-f")),
                    _cE("view", _uM("class" to "sky-star star-g")),
                    _cE("view", _uM("class" to "sky-star star-h")),
                    _cE("view", _uM("class" to "sky-star star-i")),
                    _cE("view", _uM("class" to "sky-star star-j")),
                    _cE("view", _uM("class" to "sky-star star-k")),
                    _cE("view", _uM("class" to "sky-star star-l")),
                    _cE("view", _uM("class" to "sky-star star-m")),
                    _cE("view", _uM("class" to "sky-star star-n")),
                    _cE("view", _uM("class" to "sky-star star-o")),
                    _cE("view", _uM("class" to "sky-star star-p")),
                    _cE("view", _uM("class" to "sky-star star-q")),
                    _cE("view", _uM("class" to "sky-star star-r")),
                    _cE("view", _uM("class" to "sky-star star-s")),
                    _cE("view", _uM("class" to "sky-star star-t")),
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "greeting-bar"), _uA(
                            _cE("view", _uM("class" to "greeting-header"), _uA(
                                _cE("view", _uM("class" to "greeting-left"), _uA(
                                    _cE("text", _uM("class" to "brand-kicker"), "微憩星球"),
                                    _cE("text", _uM("class" to "greeting-text"), _tD(unref(greeting)), 1),
                                    _cE("text", _uM("class" to "greeting-date"), _tD(unref(dateStr)), 1),
                                    _cE("text", _uM("class" to "greeting-hint"), _tD(unref(greetingHint)), 1)
                                )),
                                _cE("view", _uM("class" to "greeting-right"), _uA(
                                    _cE("view", _uM("class" to "mini-planet", "onClick" to openPhoneDialog), _uA(
                                        _cE("view", _uM("class" to "mini-orbit")),
                                        _cE("view", _uM("class" to "mini-orbit mini-orbit-back")),
                                        _cE("view", _uM("class" to "mini-star mini-star-a")),
                                        _cE("view", _uM("class" to "mini-star mini-star-b")),
                                        _cE("view", _uM("class" to "mini-satellite")),
                                        _cE("view", _uM("class" to "mini-planet-core"), _uA(
                                            _cE("view", _uM("class" to "mini-planet-shine")),
                                            _cE("view", _uM("class" to "mini-planet-halo"))
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "settings-icon", "onClick" to goSettings), _uA(
                                        _cE("text", _uM("class" to "settings-icon-text"), "⚙")
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "phone-usage-hint", "onClick" to openPhoneDialog), _uA(
                                _cE("text", _uM("class" to "phone-usage-hint-text"), _tD(unref(phoneUsageHint)), 1)
                            )),
                            _cE("view", _uM("class" to "hero-action-row"), _uA(
                                _cE("view", _uM("class" to "hero-stat-pill"), _uA(
                                    _cE("text", _uM("class" to "hero-stat-num"), _tD(unref(guardCount)), 1),
                                    _cE("text", _uM("class" to "hero-stat-label"), "次点亮")
                                )),
                                _cE("view", _uM("class" to "hero-main-cta", "onClick" to startHeroAction), _uA(
                                    _cE("text", _uM("class" to "hero-main-cta-text"), "开始一个微动作")
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "achievement-section"), _uA(
                            _cE("view", _uM("class" to "achievement-header", "onClick" to toggleAchievementsCollapsed), _uA(
                                _cE("view", _uM("class" to "achievement-title-wrap"), _uA(
                                    _cE("text", _uM("class" to "achievement-title"), "成就栏"),
                                    _cE("text", _uM("class" to "achievement-count"), _tD(unref(unlockedCount)) + " / 12", 1),
                                    if (unref(newUnlockedCount) > 0) {
                                        _cE("view", _uM("key" to 0, "class" to "achievement-red-dot"))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                )),
                                _cE("text", _uM("class" to "achievement-toggle"), _tD(if (unref(achievementsCollapsed)) {
                                    "展开"
                                } else {
                                    "收起"
                                }
                                ), 1)
                            )),
                            if (isTrue(unref(achievementsCollapsed))) {
                                _cE("view", _uM("key" to 0, "class" to "achievement-collapsed-scroll"), _uA(
                                    _cE("scroll-view", _uM("class" to "achievement-scroll-x", "scroll-x" to "true", "show-scrollbar" to "false"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(achievements), fun(a, i, __index, _cached): Any {
                                            return _cE("view", _uM("key" to ("ac-" + i), "class" to _nC(_uA(
                                                "achv-chip",
                                                if (a.isUnlocked) {
                                                    "achv-chip-on"
                                                } else {
                                                    "achv-chip-off"
                                                }
                                            )), "onClick" to fun(){
                                                onAchievementChipTap(a)
                                            }), _uA(
                                                _cE("text", _uM("class" to "achv-chip-icon"), _tD(a.def.icon), 1),
                                                if (isTrue(a.isUnlocked)) {
                                                    _cE("text", _uM("key" to 0, "class" to "achv-chip-mark"), "✓")
                                                } else {
                                                    _cE("text", _uM("key" to 1, "class" to "achv-chip-mark achv-chip-mark-lock"), "锁")
                                                },
                                                if (isTrue(a.isNew)) {
                                                    _cE("view", _uM("key" to 2, "class" to "achv-chip-newdot"))
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                            ), 10, _uA(
                                                "onClick"
                                            ))
                                        }), 128)
                                    ))
                                ))
                            } else {
                                _cE("view", _uM("key" to 1, "class" to "achievement-grid"), _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(unref(achievements), fun(a, i, __index, _cached): Any {
                                        return _cE("view", _uM("key" to ("ag-" + i), "class" to _nC(_uA(
                                            "achv-grid-card",
                                            if (a.isUnlocked) {
                                                "achv-grid-on"
                                            } else {
                                                "achv-grid-off"
                                            }
                                        )), "onClick" to fun(){
                                            onAchievementGridTap(a)
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to "achv-grid-icon"), _tD(a.def.icon), 1),
                                            _cE("text", _uM("class" to "achv-grid-name"), _tD(a.def.name), 1),
                                            _cE("text", _uM("class" to "achv-grid-cat"), _tD(a.def.category), 1),
                                            if (isTrue(a.isUnlocked)) {
                                                _cE("text", _uM("key" to 0, "class" to "achv-grid-prog"), "已达成")
                                            } else {
                                                _cE("text", _uM("key" to 1, "class" to "achv-grid-prog achv-grid-prog-off"), _tD(a.currentProgress) + " / " + _tD(a.def.threshold), 1)
                                            }
                                            ,
                                            if (isTrue(a.isNew)) {
                                                _cE("view", _uM("key" to 2, "class" to "achv-grid-newdot"))
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        ), 10, _uA(
                                            "onClick"
                                        ))
                                    }
                                    ), 128)
                                ))
                            }
                        )),
                        _cE("view", _uM("class" to "planet-hero-card"), _uA(
                            _cE("view", _uM("class" to "planet-hero-copy"), _uA(
                                _cE("text", _uM("class" to "planet-eyebrow"), "今日已点亮"),
                                _cE("view", _uM("class" to "planet-count-row"), _uA(
                                    _cE("text", _uM("class" to "planet-count"), _tD(unref(guardCount)), 1),
                                    _cE("text", _uM("class" to "planet-count-unit"), "次")
                                )),
                                _cE("text", _uM("class" to "planet-guard-text"), _tD(unref(guardMinuteText)), 1),
                                _cE("text", _uM("class" to "planet-kind-text"), "每一次短暂停顿，都是身体收到的善意")
                            )),
                            _cE("view", _uM("class" to _nC(_uA(
                                "planet-visual",
                                unref(planetVisualClass)
                            ))), _uA(
                                if (isTrue(unref(showPlanetRing))) {
                                    _cE("view", _uM("key" to 0, "class" to "planet-ring"))
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                _cE("view", _uM("class" to _nC(_uA(
                                    "planet-core",
                                    unref(planetCoreClass)
                                ))), _uA(
                                    _cE("text", _uM("class" to "planet-core-text"), "✦")
                                ), 2),
                                if (isTrue(unref(showPlanetParticles))) {
                                    _cE("view", _uM("key" to 1, "class" to "planet-particle particle-a"))
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                if (isTrue(unref(showPlanetParticles))) {
                                    _cE("view", _uM("key" to 2, "class" to "planet-particle particle-b"))
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                if (isTrue(unref(showPlanetParticles))) {
                                    _cE("view", _uM("key" to 3, "class" to "planet-particle particle-c"))
                                } else {
                                    _cC("v-if", true)
                                }
                            ), 2)
                        )),
                        _cE("view", _uM("class" to "cockpit-grid"), _uA(
                            _cE("view", _uM("class" to "health-cluster"), _uA(
                                _cE("view", _uM("class" to "section-header-row compact-header"), _uA(
                                    _cE("text", _uM("class" to "section-title"), "三颗健康星球"),
                                    _cE("text", _uM("class" to "section-caption"), "护眼 · 体态 · 活力")
                                )),
                                _cV(unref(GenComponentsStatusIndicatorClass), _uM("title" to "护眼", "percent" to unref(eyeScore), "status" to unref(eyeStatus)), null, 8, _uA(
                                    "percent",
                                    "status"
                                )),
                                _cV(unref(GenComponentsStatusIndicatorClass), _uM("title" to "体态", "percent" to unref(postureScore), "status" to unref(postureStatus)), null, 8, _uA(
                                    "percent",
                                    "status"
                                )),
                                _cV(unref(GenComponentsStatusIndicatorClass), _uM("title" to "活力", "percent" to unref(vitalityScore), "status" to unref(vitalityStatus)), null, 8, _uA(
                                    "percent",
                                    "status"
                                ))
                            )),
                            _cE("view", _uM("class" to "cockpit-side"), _uA(
                                _cE("view", _uM("class" to "penetration-section"), _uA(
                                    _cE("view", _uM("class" to "penetration-copy"), _uA(
                                        _cE("text", _uM("class" to "section-label"), "习惯渗透度"),
                                        _cE("text", _uM("class" to "penetration-sub"), "星环正在慢慢变亮")
                                    )),
                                    _cE("view", _uM("class" to "penetration-track"), _uA(
                                        _cE("view", _uM("class" to "penetration-fill", "style" to _nS(_uM("width" to ((if (unref(penetration) * 10 > 100) {
                                            100
                                        } else {
                                            unref(penetration) * 10
                                        }
                                        ) + "%")))), null, 4)
                                    )),
                                    _cE("text", _uM("class" to "penetration-value"), _tD(unref(penetrationStr)), 1)
                                )),
                                if (unref(dailySummary) != null) {
                                    _cE("view", _uM("key" to 0, "class" to "summary-card"), _uA(
                                        _cE("text", _uM("class" to "summary-eyebrow"), "AI 今日判断"),
                                        _cE("text", _uM("class" to "summary-oneliner"), _tD(unref(summaryOneLiner)), 1),
                                        if (unref(summaryBody).length > 0) {
                                            _cE("text", _uM("key" to 0, "class" to "summary-body"), _tD(unref(summaryBody)), 1)
                                        } else {
                                            _cC("v-if", true)
                                        },
                                        if (unref(summaryGoal).length > 0) {
                                            _cE("view", _uM("key" to 1, "class" to "summary-goal-row"), _uA(
                                                _cE("text", _uM("class" to "summary-goal-label"), "明日轨道"),
                                                _cE("text", _uM("class" to "summary-goal-text"), _tD(unref(summaryGoal)), 1)
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        },
                                        if (unref(summaryEncourage).length > 0) {
                                            _cE("text", _uM("key" to 2, "class" to "summary-encourage"), _tD(unref(summaryEncourage)), 1)
                                        } else {
                                            _cC("v-if", true)
                                        },
                                        _cE("view", _uM("class" to "ai-feedback-row"), _uA(
                                            _cE("view", _uM("class" to "ai-feedback-chip"), _uA(
                                                _cE("text", _uM("class" to "ai-feedback-text"), "有帮助")
                                            )),
                                            _cE("view", _uM("class" to "ai-feedback-chip"), _uA(
                                                _cE("text", _uM("class" to "ai-feedback-text"), "不太准")
                                            ))
                                        ))
                                    ))
                                } else {
                                    _cE("view", _uM("key" to 1, "class" to "summary-card summary-empty"), _uA(
                                        _cE("text", _uM("class" to "summary-eyebrow"), "AI 今日判断"),
                                        _cE("text", _uM("class" to "summary-empty-text"), _tD(unref(summaryPlaceholder)), 1),
                                        _cE("text", _uM("class" to "summary-empty-hint"), "完成更多微动作后，我会更懂你的节奏")
                                    ))
                                }
                            ))
                        )),
                        _cE("view", _uM("class" to "recommend-section"), _uA(
                            _cE("view", _uM("class" to "section-header-row"), _uA(
                                _cE("text", _uM("class" to "section-title"), "今日推荐动作"),
                                _cE("text", _uM("class" to "section-caption"), "挑一个，让小星球亮一下")
                            )),
                            if (unref(recommendedActions).length < 1) {
                                _cE("view", _uM("key" to 0, "class" to "empty-recommend"), _uA(
                                    _cE("text", _uM("class" to "empty-text"), "暂无推荐")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE(Fragment, null, RenderHelpers.renderList(unref(recommendedActions), fun(action, idx, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("r-" + idx), "class" to _nC(_uA(
                                    "recommend-item",
                                    if (idx == 0) {
                                        "recommend-main"
                                    } else {
                                        "recommend-secondary"
                                    }
                                ))), _uA(
                                    _cV(unref(GenComponentsActionCardClass), _uM("action-id" to action.id, "action-name" to action.name, "action-duration" to Math.ceil(action.defaultDurationMs / 1000), "variant" to "recommend", "onActionTap" to startAction), null, 8, _uA(
                                        "action-id",
                                        "action-name",
                                        "action-duration"
                                    ))
                                ), 2)
                            }
                            ), 128)
                        )),
                        _cE("view", _uM("class" to "library-section"), _uA(
                            _cE("view", _uM("class" to "library-header"), _uA(
                                _cE("text", _uM("class" to "section-title"), "小动作仓库（" + _tD(unref(allActions).length) + "）", 1),
                                _cE("text", _uM("class" to "library-hint"), "点选任意动作，给身体一个几秒钟的小空隙")
                            )),
                            _cE(Fragment, null, RenderHelpers.renderList(unref(allActions), fun(action, idx, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("a-" + idx), "class" to "library-item"), _uA(
                                    _cV(unref(GenComponentsActionCardClass), _uM("action-id" to action.id, "action-name" to action.name, "action-duration" to Math.ceil(action.defaultDurationMs / 1000), "variant" to "library", "onActionTap" to startAction), null, 8, _uA(
                                        "action-id",
                                        "action-name",
                                        "action-duration"
                                    ))
                                ))
                            }
                            ), 128)
                        )),
                        _cE("view", _uM("class" to "light-record-card"), _uA(
                            _cE("view", _uM("class" to "light-record-header"), _uA(
                                _cE("view", _uM("class" to "light-record-icon"), _uA(
                                    _cE("text", _uM("class" to "light-record-icon-text"), "✧")
                                )),
                                _cE("view", _uM("class" to "light-record-copy"), _uA(
                                    _cE("text", _uM("class" to "light-record-title"), "星球点亮记录"),
                                    _cE("text", _uM("class" to "light-record-text"), "每个小格子，都是你照顾自己的一个瞬间。")
                                ))
                            )),
                            _cV(unref(GenComponentsHeatmapCalendarClass), _uM("dates" to unref(homeHeatmapDates), "counts" to unref(homeHeatmapCounts), "height" to 260), null, 8, _uA(
                                "dates",
                                "counts"
                            ))
                        )),
                        _cV(unref(GenComponentsLlmHistoryCardClass), _uM("entries" to unref(llmHistoryEntries)), null, 8, _uA(
                            "entries"
                        )),
                        _cE("view", _uM("class" to "bottom-spacer"))
                    )),
                    _cV(unref(GenComponentsPhoneUsageDialogClass), _uM("visible" to unref(showPhoneDialog), "onClose" to closePhoneDialog), null, 8, _uA(
                        "visible"
                    )),
                    _cV(unref(GenComponentsTriggerRuleDialogClass), _uM("visible" to unref(showRuleDialog), "action-name" to unref(ruleActionName), "time-window-start" to unref(ruleTimeWindowStart), "time-window-end" to unref(ruleTimeWindowEnd), "time-threshold-minutes" to unref(ruleTimeThresholdMinutes), "screen-cond-text" to unref(ruleScreenCondText), "reasoning" to unref(pendingReasoning), "onClose" to closeRuleDialog, "onAccept" to acceptRule, "onDecline" to closeRuleDialog, "onNeverAsk" to neverAskRule), null, 8, _uA(
                        "visible",
                        "action-name",
                        "time-window-start",
                        "time-window-end",
                        "time-threshold-minutes",
                        "screen-cond-text",
                        "reasoning"
                    )),
                    _cV(unref(GenComponentsAchievementDetailDialogClass), _uM("visible" to unref(showAchvDetail), "achievement-icon" to unref(detailIcon), "achievement-name" to unref(detailName), "achievement-category" to unref(detailCat), "achievement-description" to unref(detailDesc), "is-unlocked" to unref(detailUnlocked), "unlocked-date" to unref(detailUnlockedDate), "reward-text" to unref(detailReward), "progress-text" to unref(detailProgressText), "progress-percent" to unref(detailProgressPercent), "history-text" to unref(detailHistoryText), "onClose" to closeAchvDetail), null, 8, _uA(
                        "visible",
                        "achievement-icon",
                        "achievement-name",
                        "achievement-category",
                        "achievement-description",
                        "is-unlocked",
                        "unlocked-date",
                        "reward-text",
                        "progress-text",
                        "progress-percent",
                        "history-text"
                    )),
                    _cV(unref(GenComponentsAchievementUnlockDialogClass), _uM("visible" to unref(showAchvUnlock), "achievement-icon" to unref(unlockIcon), "achievement-name" to unref(unlockName), "achievement-description" to unref(unlockDesc), "reward-text" to unref(unlockReward), "onClose" to onUnlockDismiss, "onViewDetail" to onUnlockViewDetail), null, 8, _uA(
                        "visible",
                        "achievement-icon",
                        "achievement-name",
                        "achievement-description",
                        "reward-text"
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F7FAFF", "position" to "relative", "overflow" to "hidden", "backgroundImage" to "linear-gradient(180deg, #131832 0%, #283252 45%, #4F587D 100%)")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "zIndex" to 1)), "greeting-bar" to _pS(_uM("flexDirection" to "column", "paddingTop" to 38, "paddingRight" to 26, "paddingBottom" to 18, "paddingLeft" to 26, "backgroundColor" to "rgba(18,22,50,0.88)", "borderBottomLeftRadius" to 28, "borderBottomRightRadius" to 28, "marginTop" to 22, "marginRight" to 14, "marginBottom" to 0, "marginLeft" to 14, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "backgroundImage" to "linear-gradient(135deg, rgba(25, 30, 62, 0.96) 0%, rgba(42, 39, 83, 0.88) 58%, rgba(147, 139, 178, 0.26) 100%)", "boxShadow" to "0 18px 42px rgba(5, 8, 24, 0.32)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(230,220,255,0.22)", "borderRightColor" to "rgba(230,220,255,0.22)", "borderBottomColor" to "rgba(230,220,255,0.22)", "borderLeftColor" to "rgba(230,220,255,0.22)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "greeting-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingBottom" to 14)), "bg-morning" to _pS(_uM("backgroundColor" to "#A93226")), "bg-noon" to _pS(_uM("backgroundColor" to "#1A5490")), "bg-afternoon" to _pS(_uM("backgroundColor" to "#8B4513")), "bg-evening" to _pS(_uM("backgroundColor" to "#1A237E")), "bg-night" to _pS(_uM("backgroundColor" to "#1B2631")), "greeting-left" to _pS(_uM("flexDirection" to "column")), "greeting-text" to _pS(_uM("fontSize" to 34, "fontWeight" to "bold", "color" to "#FFFFFF", "lineHeight" to "40px")), "greeting-date" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.74)", "marginTop" to 4)), "greeting-right" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "emoji-button" to _pS(_uM("width" to 44, "height" to 44, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.25)", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 10)), "emoji-button-text" to _pS(_uM("fontSize" to 24)), "settings-icon" to _pS(_uM("width" to 40, "height" to 40, "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "backgroundColor" to "rgba(255,255,255,0.13)", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(230,220,255,0.18)", "borderRightColor" to "rgba(230,220,255,0.18)", "borderBottomColor" to "rgba(230,220,255,0.18)", "borderLeftColor" to "rgba(230,220,255,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "settings-icon-text" to _pS(_uM("fontSize" to 18, "color" to "#FFFFFF")), "phone-usage-hint" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to 11, "paddingRight" to 16, "paddingBottom" to 11, "paddingLeft" to 16, "marginTop" to 8, "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 0, "backgroundColor" to "rgba(255,255,255,0.13)", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "borderTopColor" to "rgba(230,220,255,0.18)", "borderRightColor" to "rgba(230,220,255,0.18)", "borderBottomColor" to "rgba(230,220,255,0.18)", "borderLeftColor" to "rgba(230,220,255,0.18)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "phone-usage-hint-text" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "fontWeight" to "500")), "guard-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "marginTop" to 8, "marginRight" to 16, "marginBottom" to 8, "marginLeft" to 16, "paddingTop" to 18, "paddingRight" to 20, "paddingBottom" to 18, "paddingLeft" to 20, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "flexDirection" to "column", "boxShadow" to "0 4px 12px rgba(0, 0, 0, 0.06)")), "guard-eyebrow" to _pS(_uM("fontSize" to 12, "color" to "#7F8C8D", "marginBottom" to 4)), "guard-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end", "marginBottom" to 6)), "guard-number" to _pS(_uM("fontSize" to 36, "fontWeight" to "bold", "color" to "#2ECC71")), "guard-unit" to _pS(_uM("fontSize" to 16, "color" to "#2ECC71", "fontWeight" to "bold", "marginLeft" to 4)), "guard-sub" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6")), "penetration-section" to _pS(_uM("flexDirection" to "column", "alignItems" to "flex-start", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "marginTop" to 0, "marginRight" to 0, "marginBottom" to 8, "marginLeft" to 0, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "boxShadow" to "0 8px 22px rgba(88, 122, 139, 0.07)", "minHeight" to 118, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "section-label" to _pS(_uM("fontSize" to 13, "color" to "#FFFFFF", "width" to "auto", "fontWeight" to "bold")), "penetration-track" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 6, "backgroundColor" to "rgba(119,107,151,0.18)", "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3, "overflow" to "hidden", "width" to "100%", "marginTop" to 13)), "penetration-fill" to _pS(_uM("height" to 6, "backgroundColor" to "#776BB9", "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "penetration-value" to _pS(_uM("fontSize" to 13, "color" to "#DBC8ED", "fontWeight" to "bold", "width" to "auto", "textAlign" to "right", "alignSelf" to "flex-end", "marginTop" to 6)), "summary-card" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.78)", "marginTop" to 0, "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 0, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "borderLeftWidth" to 1, "borderLeftColor" to "rgba(219,200,237,0.18)", "borderLeftStyle" to "solid", "flexDirection" to "column", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "minHeight" to 128, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.9) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)")), "summary-empty" to _pS(_uM("borderLeftColor" to "#BDC3C7", "alignItems" to "flex-start")), "summary-eyebrow" to _pS(_uM("fontSize" to 12, "color" to "#DBC8ED", "fontWeight" to "bold", "marginBottom" to 5)), "summary-oneliner" to _pS(_uM("fontSize" to 15, "fontWeight" to "bold", "color" to "#FFFFFF", "marginBottom" to 6, "lineHeight" to "20px")), "summary-body" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.72)", "lineHeight" to "17px", "marginBottom" to 6)), "summary-goal-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 7, "paddingRight" to 8, "paddingBottom" to 7, "paddingLeft" to 8, "marginBottom" to 6)), "summary-goal-label" to _pS(_uM("fontSize" to 11, "color" to "#7F8C8D", "marginRight" to 6)), "summary-goal-text" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "lineHeight" to "16px")), "summary-encourage" to _pS(_uM("fontSize" to 12, "color" to "#B88A2B", "fontStyle" to "italic", "marginTop" to 4, "lineHeight" to "16px")), "summary-empty-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF", "marginBottom" to 4, "fontWeight" to "bold")), "summary-empty-hint" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.72)", "marginTop" to 4)), "recommend-section" to _pS(_uM("marginTop" to 14, "paddingBottom" to 12, "marginRight" to 12, "marginBottom" to 0, "marginLeft" to 12, "paddingTop" to 8, "paddingRight" to 0, "paddingLeft" to 0, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "backgroundColor" to "rgba(29,25,62,0.78)", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.9) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)")), "section-header-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 4, "paddingLeft" to 16, "justifyContent" to "space-between")), "library-section" to _pS(_uM("marginTop" to 14, "paddingBottom" to 12, "marginRight" to 12, "marginBottom" to 0, "marginLeft" to 12, "paddingTop" to 12, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "backgroundColor" to "rgba(29,25,62,0.78)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.9) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)")), "library-header" to _pS(_uM("flexDirection" to "column", "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 4, "paddingLeft" to 16)), "library-hint" to _pS(_uM("fontSize" to 11, "color" to "rgba(255,255,255,0.72)", "marginTop" to 2, "lineHeight" to "17px")), "library-item" to _pS(_uM("marginTop" to 0, "marginRight" to 0, "marginBottom" to 8, "marginLeft" to 0)), "section-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#FFFFFF")), "empty-recommend" to _pS(_uM("paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "alignItems" to "center", "marginTop" to 6, "marginRight" to 16, "marginBottom" to 6, "marginLeft" to 16, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "backgroundColor" to "#FFFFFF")), "empty-text" to _pS(_uM("fontSize" to 14, "color" to "#9AAEB8")), "bottom-spacer" to _pS(_uM("height" to 110)), "greeting-hint" to _pS(_uM("fontSize" to 13, "color" to "rgba(255,255,255,0.74)", "marginTop" to 8, "lineHeight" to "19px")), "mini-planet" to _pS(_uM("width" to 112, "height" to 112, "borderTopLeftRadius" to 56, "borderTopRightRadius" to 56, "borderBottomRightRadius" to 56, "borderBottomLeftRadius" to 56, "backgroundColor" to "rgba(255,255,255,0.18)", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 10, "boxShadow" to "0 18px 34px rgba(141, 107, 255, 0.16)", "overflow" to "hidden")), "mini-planet-text" to _pS(_uM("fontSize" to 24)), "planet-hero-card" to _pS(_uM("marginTop" to 12, "marginRight" to 14, "marginBottom" to 0, "marginLeft" to 14, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "backgroundColor" to "rgba(29,25,62,0.78)", "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "flexDirection" to "row", "alignItems" to "center", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.9) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)")), "planet-hero-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "planet-eyebrow" to _pS(_uM("fontSize" to 12, "color" to "#DBC8ED", "fontWeight" to "bold")), "planet-count-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end", "marginTop" to 4)), "planet-count" to _pS(_uM("fontSize" to 42, "color" to "#DBC8ED", "fontWeight" to "bold")), "planet-count-unit" to _pS(_uM("fontSize" to 16, "color" to "#DBC8ED", "fontWeight" to "bold", "marginLeft" to 4, "marginBottom" to 7)), "planet-guard-text" to _pS(_uM("fontSize" to 13, "color" to "#FFFFFF", "marginTop" to 4)), "planet-kind-text" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.72)", "lineHeight" to "18px", "marginTop" to 7)), "planet-visual" to _pS(_uM("width" to 108, "height" to 108, "borderTopLeftRadius" to 54, "borderTopRightRadius" to 54, "borderBottomRightRadius" to 54, "borderBottomLeftRadius" to 54, "alignItems" to "center", "justifyContent" to "center", "marginLeft" to 12, "backgroundImage" to "linear-gradient(135deg, rgba(219, 200, 237, 0.22) 0%, rgba(119, 107, 151, 0.32) 100%)")), "planet-core" to _pS(_uM("width" to 72, "height" to 72, "borderTopLeftRadius" to 36, "borderTopRightRadius" to 36, "borderBottomRightRadius" to 36, "borderBottomLeftRadius" to 36, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#776B97", "backgroundImage" to "linear-gradient(135deg, #DBC8ED 0%, #C68DC0 42%, #4F587D 100%)")), "planet-core-on" to _pS(_uM("backgroundColor" to "#776B97", "backgroundImage" to "linear-gradient(135deg, #DBC8ED 0%, #C68DC0 42%, #4F587D 100%)")), "planet-core-dim" to _pS(_uM("backgroundColor" to "#DDE8EC")), "planet-core-text" to _pS(_uM("fontSize" to 30, "color" to "#FFFFFF")), "planet-dim" to _pS(_uM("backgroundColor" to "#EEF4F6")), "planet-soft" to _pS(_uM("backgroundColor" to "rgba(138,217,182,0.2)")), "planet-ring-on" to _pS(_uM("backgroundColor" to "rgba(141,187,239,0.16)")), "planet-bright" to _pS(_uM("backgroundColor" to "rgba(246,217,139,0.22)")), "planet-ring" to _pS(_uM("position" to "absolute", "width" to 94, "height" to 40, "borderTopLeftRadius" to 47, "borderTopRightRadius" to 47, "borderBottomRightRadius" to 47, "borderBottomLeftRadius" to 47, "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(198,141,192,0.74)", "borderRightColor" to "rgba(198,141,192,0.74)", "borderBottomColor" to "rgba(198,141,192,0.74)", "borderLeftColor" to "rgba(198,141,192,0.74)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "planet-particle" to _pS(_uM("position" to "absolute", "width" to 7, "height" to 7, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "backgroundColor" to "#FFFFFF")), "particle-a" to _pS(_uM("top" to 18, "right" to 18)), "particle-b" to _pS(_uM("bottom" to 20, "left" to 20)), "particle-c" to _pS(_uM("bottom" to 28, "right" to 12)), "section-caption" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.72)", "marginTop" to 2)), "penetration-copy" to _pS(_uM("width" to "auto", "flexDirection" to "column")), "penetration-sub" to _pS(_uM("fontSize" to 10, "color" to "rgba(255,255,255,0.72)", "marginTop" to 3)), "ai-feedback-row" to _pS(_uM("flexDirection" to "row", "marginTop" to 8)), "ai-feedback-chip" to _pS(_uM("paddingTop" to 5, "paddingRight" to 8, "paddingBottom" to 5, "paddingLeft" to 8, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "rgba(255,255,255,0.1)", "marginRight" to 5)), "ai-feedback-text" to _pS(_uM("fontSize" to 10, "color" to "rgba(255,255,255,0.72)")), "light-record-card" to _pS(_uM("marginTop" to 12, "marginRight" to 16, "marginBottom" to 12, "marginLeft" to 16, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26, "backgroundColor" to "rgba(29,25,62,0.78)", "flexDirection" to "column", "alignItems" to "stretch", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.9) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "light-record-icon" to _pS(_uM("width" to 44, "height" to 44, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "backgroundColor" to "rgba(255,255,255,0.1)", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 12)), "light-record-icon-text" to _pS(_uM("fontSize" to 22, "color" to "#DBC8ED")), "light-record-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "light-record-title" to _pS(_uM("fontSize" to 15, "color" to "#FFFFFF", "fontWeight" to "bold")), "light-record-text" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.72)", "lineHeight" to "18px", "marginTop" to 4)), "hero-action-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 14, "marginBottom" to 0)), "hero-stat-pill" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 48, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "backgroundColor" to "rgba(255,255,255,0.13)", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 10, "borderTopColor" to "rgba(230,220,255,0.18)", "borderRightColor" to "rgba(230,220,255,0.18)", "borderBottomColor" to "rgba(230,220,255,0.18)", "borderLeftColor" to "rgba(230,220,255,0.18)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "hero-stat-num" to _pS(_uM("fontSize" to 24, "color" to "#FFFFFF", "fontWeight" to "bold", "marginRight" to 5)), "hero-stat-label" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.74)")), "hero-main-cta" to _pS(_uM("height" to 48, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "backgroundColor" to "rgba(12,14,38,0.76)", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(230,220,255,0.18)", "borderRightColor" to "rgba(230,220,255,0.18)", "borderBottomColor" to "rgba(230,220,255,0.18)", "borderLeftColor" to "rgba(230,220,255,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "hero-main-cta-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 13, "fontWeight" to "bold")), "cockpit-grid" to _pS(_uM("flexDirection" to "row", "alignItems" to "stretch", "paddingTop" to 12, "paddingRight" to 8, "paddingBottom" to 12, "paddingLeft" to 8, "marginTop" to 14, "marginRight" to 10, "marginBottom" to 0, "marginLeft" to 10, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "backgroundColor" to "rgba(7,9,30,0.68)", "backgroundImage" to "linear-gradient(160deg, rgba(12, 12, 42, 0.92) 0%, rgba(48, 42, 94, 0.68) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.13)", "borderRightColor" to "rgba(219,200,237,0.13)", "borderBottomColor" to "rgba(219,200,237,0.13)", "borderLeftColor" to "rgba(219,200,237,0.13)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "health-cluster" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "borderTopLeftRadius" to 26, "borderTopRightRadius" to 26, "borderBottomRightRadius" to 26, "borderBottomLeftRadius" to 26, "backgroundColor" to "rgba(29,25,62,0.78)", "paddingTop" to 6, "paddingRight" to 0, "paddingBottom" to 8, "paddingLeft" to 0, "marginRight" to 7, "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.34)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.9) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)")), "cockpit-side" to _pS(_uM("width" to "38%", "flexDirection" to "column")), "compact-header" to _pS(_uM("paddingTop" to 8, "paddingRight" to 12, "paddingBottom" to 1, "paddingLeft" to 12, "flexDirection" to "column", "alignItems" to "flex-start")), "recommend-item" to _pS(_uM("marginTop" to 0, "marginRight" to 4, "marginBottom" to 0, "marginLeft" to 4)), "recommend-main" to _pS(_uM("transform" to "scale(1)")))
            }
        val styles1: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("recommend-secondary" to _pS(_uM("opacity" to 0.96)), "light-record-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to 10)), "bg-glow" to _pS(_uM("position" to "absolute", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "opacity" to 0.72, "zIndex" to 0)), "glow-purple" to _pS(_uM("width" to 190, "height" to 190, "left" to -70, "top" to -54, "backgroundColor" to "#776B97", "opacity" to 0.5)), "glow-blue" to _pS(_uM("width" to 136, "height" to 136, "right" to -46, "top" to 72, "backgroundColor" to "#C2E0EE", "opacity" to 0.42)), "glow-pink" to _pS(_uM("width" to 150, "height" to 150, "left" to 38, "bottom" to 72, "backgroundColor" to "#C68DC0", "opacity" to 0.18)), "brand-kicker" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "fontWeight" to "bold", "marginBottom" to 8)), "mini-orbit" to _pS(_uM("position" to "absolute", "width" to 122, "height" to 48, "borderTopLeftRadius" to 61, "borderTopRightRadius" to 61, "borderBottomRightRadius" to 61, "borderBottomLeftRadius" to 61, "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(128,171,255,0.58)", "borderRightColor" to "rgba(128,171,255,0.58)", "borderBottomColor" to "rgba(128,171,255,0.58)", "borderLeftColor" to "rgba(128,171,255,0.58)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "transform" to "rotate(-18deg)")), "mini-orbit-back" to _pS(_uM("width" to 100, "height" to 38, "borderTopColor" to "rgba(128,171,255,0.58)", "borderRightColor" to "rgba(128,171,255,0.58)", "borderBottomColor" to "rgba(128,171,255,0.58)", "borderLeftColor" to "rgba(128,171,255,0.58)", "transform" to "rotate(18deg)")), "mini-star" to _pS(_uM("position" to "absolute", "width" to 6, "height" to 6, "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3, "backgroundColor" to "#8D6BFF")), "mini-star-a" to _pS(_uM("right" to 14, "top" to 18)), "mini-star-b" to _pS(_uM("left" to 16, "bottom" to 18, "backgroundColor" to "#6DAEFF")), "mini-satellite" to _pS(_uM("position" to "absolute", "width" to 12, "height" to 12, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "right" to 10, "bottom" to 30, "backgroundColor" to "#7C8BFF", "boxShadow" to "0 6px 12px rgba(124, 139, 255, 0.28)")), "mini-planet-core" to _pS(_uM("width" to 58, "height" to 58, "borderTopLeftRadius" to 29, "borderTopRightRadius" to 29, "borderBottomRightRadius" to 29, "borderBottomLeftRadius" to 29, "backgroundColor" to "#DCC6FF", "backgroundImage" to "linear-gradient(135deg, #FFFFFF 0%, #CBB8FF 38%, #7C8BFF 100%)", "alignItems" to "center", "justifyContent" to "center", "boxShadow" to "0 14px 28px rgba(141, 107, 255, 0.26)")), "mini-planet-shine" to _pS(_uM("width" to 24, "height" to 24, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "rgba(255,255,255,0.5)", "alignSelf" to "flex-start", "marginTop" to 12, "marginLeft" to 12)), "mini-planet-halo" to _pS(_uM("position" to "absolute", "width" to 92, "height" to 92, "borderTopLeftRadius" to 46, "borderTopRightRadius" to 46, "borderBottomRightRadius" to 46, "borderBottomLeftRadius" to 46, "backgroundColor" to "rgba(203,184,255,0.18)")), "sky-star" to _pS(_uM("position" to "absolute", "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "rgba(255,255,255,0.86)", "zIndex" to 0)), "star-a" to _pS(_uM("left" to 36, "top" to 88)), "star-b" to _pS(_uM("left" to 112, "top" to 132, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-c" to _pS(_uM("right" to 52, "top" to 106)), "star-d" to _pS(_uM("right" to 26, "top" to 230, "width" to 5, "height" to 5, "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "star-e" to _pS(_uM("left" to 54, "top" to 330)), "star-f" to _pS(_uM("right" to 86, "top" to 420, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-g" to _pS(_uM("left" to 138, "top" to 530)), "star-h" to _pS(_uM("right" to 36, "top" to 650, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-i" to _pS(_uM("left" to 28, "top" to 760)), "star-j" to _pS(_uM("right" to 112, "top" to 850)), "star-k" to _pS(_uM("left" to 76, "top" to 214, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-l" to _pS(_uM("right" to 148, "top" to 276, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-m" to _pS(_uM("left" to 178, "top" to 392, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-n" to _pS(_uM("right" to 64, "top" to 488, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-o" to _pS(_uM("left" to 118, "top" to 612, "width" to 5, "height" to 5, "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "star-p" to _pS(_uM("right" to 156, "top" to 720, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-q" to _pS(_uM("left" to 214, "top" to 820, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-r" to _pS(_uM("right" to 22, "top" to 930, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-s" to _pS(_uM("left" to 48, "top" to 1030, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-t" to _pS(_uM("right" to 92, "top" to 1140, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "sky-moon" to _pS(_uM("position" to "absolute", "right" to 26, "top" to 34, "width" to 42, "height" to 42, "borderTopLeftRadius" to 21, "borderTopRightRadius" to 21, "borderBottomRightRadius" to 21, "borderBottomLeftRadius" to 21, "backgroundColor" to "rgba(241,229,246,0.9)", "boxShadow" to "0 0 24px rgba(219, 200, 237, 0.42)", "zIndex" to 0)), "sky-cloud" to _pS(_uM("position" to "absolute", "height" to 28, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "backgroundColor" to "rgba(194,224,238,0.18)", "zIndex" to 0)), "cloud-a" to _pS(_uM("width" to 120, "left" to 20, "top" to 190)), "cloud-b" to _pS(_uM("width" to 94, "right" to 12, "top" to 520, "backgroundColor" to "rgba(241,207,237,0.14)")), "achievement-section" to _pS(_uM("marginTop" to 12, "marginRight" to 14, "marginBottom" to 0, "marginLeft" to 14, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 16, "paddingLeft" to 14, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "backgroundColor" to "rgba(29,25,62,0.78)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.9) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 16px 34px rgba(5, 6, 20, 0.26)")), "achievement-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "achievement-title-wrap" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "flex-start")), "achievement-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#FFFFFF", "marginRight" to 8)), "achievement-count" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.66)")), "achievement-toggle" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.66)")), "achievement-red-dot" to _pS(_uM("width" to 8, "height" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "backgroundColor" to "#FF7C9E", "marginLeft" to 6)), "achv-chip-newdot" to _pS(_uM("width" to 8, "height" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "backgroundColor" to "#FF7C9E", "position" to "absolute", "right" to 7, "top" to 7)), "achv-grid-newdot" to _pS(_uM("width" to 8, "height" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "backgroundColor" to "#FF7C9E", "position" to "absolute", "right" to 7, "top" to 7)), "achievement-collapsed-scroll" to _pS(_uM("flexDirection" to "row", "marginTop" to 12)), "achievement-scroll-x" to _pS(_uM("flexDirection" to "row")), "achv-chip" to _pS(_uM("width" to 64, "height" to 72, "marginRight" to 10, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "alignItems" to "center", "justifyContent" to "center", "position" to "relative", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "achv-chip-on" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.22)", "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)")), "achv-grid-on" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.22)", "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)")), "achv-chip-off" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.08)", "borderTopColor" to "rgba(255,255,255,0.12)", "borderRightColor" to "rgba(255,255,255,0.12)", "borderBottomColor" to "rgba(255,255,255,0.12)", "borderLeftColor" to "rgba(255,255,255,0.12)")), "achv-grid-off" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.08)", "borderTopColor" to "rgba(255,255,255,0.12)", "borderRightColor" to "rgba(255,255,255,0.12)", "borderBottomColor" to "rgba(255,255,255,0.12)", "borderLeftColor" to "rgba(255,255,255,0.12)")), "achv-chip-icon" to _pS(_uM("fontSize" to 28)), "achv-grid-icon" to _pS(_uM("fontSize" to 28)), "achv-chip-mark" to _pS(_uM("fontSize" to 10, "color" to "#8FE7C1", "marginTop" to 3)), "achv-chip-mark-lock" to _pS(_uM("color" to "rgba(255,255,255,0.52)")), "achv-grid-prog-off" to _pS(_uM("color" to "rgba(255,255,255,0.52)")), "achievement-grid" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 12)), "achv-grid-card" to _pS(_uM("width" to "31%", "minHeight" to 102, "marginRight" to "2%", "marginBottom" to 10, "paddingTop" to 12, "paddingRight" to 8, "paddingBottom" to 12, "paddingLeft" to 8, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "alignItems" to "center", "position" to "relative", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "achv-grid-name" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "fontWeight" to "bold", "marginTop" to 6)), "achv-grid-cat" to _pS(_uM("fontSize" to 10, "color" to "rgba(255,255,255,0.66)", "marginTop" to 3)), "achv-grid-prog" to _pS(_uM("fontSize" to 10, "color" to "rgba(255,255,255,0.66)", "marginTop" to 3)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
