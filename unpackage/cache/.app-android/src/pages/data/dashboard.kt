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
open class GenPagesDataDashboard : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesDataDashboard) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesDataDashboard
            val _cache = __ins.renderCache
            val store = useAppStore()
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
            fun gen_getPenetrationText_fn(): String {
                val p = store.penetration.value
                return "" + Math.round(p * 10) / 10
            }
            val getPenetrationText = ::gen_getPenetrationText_fn
            val penetrationText = computed<String>(getPenetrationText)
            val heatmapData = computed<UTSArray<DailyCount>>(fun(): UTSArray<DailyCount> {
                return store.heatmapData.value
            }
            )
            val hourlyLabels = ref(_uA<String>())
            val hourlyValues = ref(_uA<Number>())
            val weekTrendValues = ref(_uA<UTSArray<Number>>())
            val weekTrendNames = ref(_uA<String>())
            val weekTrendColors = ref(_uA<String>())
            val weekTrendLabels = ref(_uA<String>())
            val heatmapDates = ref(_uA<String>())
            val heatmapCounts = ref(_uA<Number>())
            val llmHistoryEntries = ref(_uA<LlmHistoryEntry>())
            val moodCloudWords = ref(_uA<MoodWord>())
            val moodCloudLoading = ref<Boolean>(false)
            val moodCloudSampleCount = ref<Number>(0)
            val moodCloudHintText = computed<String>(fun(): String {
                if (moodCloudSampleCount.value < 1) {
                    return "尚未记录任何状态，多用一段时间后再来"
                }
                if (moodCloudLoading.value) {
                    return "正在从历次记录中提取关键词"
                }
                return "点击\"提取状态\"按钮让 AI 汇总近期心情/状态"
            }
            )
            val weeklyShow = ref<Boolean>(false)
            val weeklyToggleText = computed<String>(fun(): String {
                return if (weeklyShow.value) {
                    "▼"
                } else {
                    "▶"
                }
            }
            )
            val weeklyLoaded = computed<Boolean>(fun(): Boolean {
                return store.weeklyReport.value != null
            }
            )
            val weeklyReportLoading = computed<Boolean>(fun(): Boolean {
                return store.weeklyReportLoading.value
            }
            )
            fun gen_getWkBody_fn(): String {
                val r = store.weeklyReport.value
                return if (r != null) {
                    r.bodyMarkdown
                } else {
                    ""
                }
            }
            val getWkBody = ::gen_getWkBody_fn
            fun gen_getWkTotalCompleted_fn(): Number {
                val r = store.weeklyReport.value
                return if (r != null) {
                    r.stats.totalCompleted
                } else {
                    0
                }
            }
            val getWkTotalCompleted = ::gen_getWkTotalCompleted_fn
            fun gen_getWkAvgGuard_fn(): Number {
                val r = store.weeklyReport.value
                return if (r != null) {
                    r.stats.avgGuardMinutes
                } else {
                    0
                }
            }
            val getWkAvgGuard = ::gen_getWkAvgGuard_fn
            fun gen_getWkBestDay_fn(): Number {
                val r = store.weeklyReport.value
                return if (r != null) {
                    r.stats.bestDayCount
                } else {
                    0
                }
            }
            val getWkBestDay = ::gen_getWkBestDay_fn
            val wkBody = computed<String>(getWkBody)
            val wkTotalCompleted = computed<Number>(getWkTotalCompleted)
            val wkAvgGuard = computed<Number>(getWkAvgGuard)
            val wkBestDay = computed<Number>(getWkBestDay)
            val todayDetails = ref(_uA<ActionDetail>())
            val detailCount = computed<Number>(fun(): Number {
                return todayDetails.value.length
            }
            )
            fun gen_formatDetailType_fn(t: String): String {
                if (t === "eye") {
                    return "护眼"
                }
                if (t === "posture") {
                    return "体态"
                }
                if (t === "vitality") {
                    return "活力"
                }
                if (t === "breath") {
                    return "呼吸"
                }
                if (t === "neck_shoulder") {
                    return "肩颈"
                }
                if (t === "core") {
                    return "核心"
                }
                if (t === "lower_body") {
                    return "下肢"
                }
                return t
            }
            val formatDetailType = ::gen_formatDetailType_fn
            fun gen_loadData_fn(): Unit {
                val date = today()
                val heatmap = store.heatmapData.value
                val dArr: UTSArray<String> = _uA()
                val cArr: UTSArray<Number> = _uA()
                run {
                    var i: Number = 0
                    while(i < heatmap.length){
                        dArr.push(heatmap[i].date)
                        cArr.push(heatmap[i].count)
                        i++
                    }
                }
                heatmapDates.value = dArr
                heatmapCounts.value = cArr
                todayDetails.value = getTodayDetail()
                try {
                    llmHistoryEntries.value = store.llmHistory.value
                }
                 catch (_: Throwable) {
                    llmHistoryEntries.value = _uA()
                }
                try {
                    moodCloudSampleCount.value = getMoodCloudSampleCount()
                }
                 catch (_: Throwable) {
                    moodCloudSampleCount.value = 0
                }
            }
            val loadData = ::gen_loadData_fn
            fun gen_toggleWeekly_fn(): Unit {
                weeklyShow.value = !weeklyShow.value
            }
            val toggleWeekly = ::gen_toggleWeekly_fn
            fun gen_refreshWeek_fn(): Unit {
                weeklyShow.value = true
                store.refreshWeeklyReport()
            }
            val refreshWeek = ::gen_refreshWeek_fn
            fun gen_onRefreshMoodCloud_fn(): Unit {
                if (moodCloudLoading.value) {
                    return
                }
                moodCloudLoading.value = true
                try {
                    generateMoodCloud(fun(words: UTSArray<MoodWord>): Unit {
                        moodCloudWords.value = words
                        moodCloudLoading.value = false
                    }
                    , fun(err: String): Unit {
                        console.warn("[dashboard] onRefreshMoodCloud 失败: " + err, " at pages/data/dashboard.uvue:272")
                        moodCloudLoading.value = false
                    }
                    )
                }
                 catch (e: Throwable) {
                    console.warn("[dashboard] onRefreshMoodCloud 异常: " + JSON.stringify(e), " at pages/data/dashboard.uvue:277")
                    moodCloudLoading.value = false
                }
            }
            val onRefreshMoodCloud = ::gen_onRefreshMoodCloud_fn
            onPageShow(fun(){
                store.refreshHomeData()
                loadData()
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "page-bg"), _uA(
                        _cE("image", _uM("class" to "page-bg-img", "src" to "/static/images/micro-habit-page-bg.png", "mode" to "aspectFill"))
                    )),
                    _cE("view", _uM("class" to "bg-glow glow-purple")),
                    _cE("view", _uM("class" to "bg-glow glow-blue")),
                    _cE("view", _uM("class" to "bg-glow glow-pink")),
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "dashboard-hero"), _uA(
                            _cE("view", _uM("class" to "dashboard-hero-copy"), _uA(
                                _cE("text", _uM("class" to "dashboard-kicker"), "微憩星球"),
                                _cE("text", _uM("class" to "dashboard-title"), "健康轨迹"),
                                _cE("text", _uM("class" to "dashboard-subtitle"), "每一次短暂停顿，都会在小星球上留下一点光。")
                            )),
                            _cE("view", _uM("class" to "dashboard-planet"), _uA(
                                _cE("view", _uM("class" to "dashboard-orbit")),
                                _cE("text", _uM("class" to "dashboard-planet-text"), "✦")
                            ))
                        )),
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("view", _uM("class" to "section-header"), _uA(
                                _cE("text", _uM("class" to "section-title"), "今日概览")
                            )),
                            _cE("view", _uM("class" to "section-body"), _uA(
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
                                )),
                                _cE("view", _uM("class" to "guard-box"), _uA(
                                    _cE("text", _uM("class" to "guard-label"), "今日完成"),
                                    _cE("text", _uM("class" to "guard-value"), _tD(unref(guardCount)) + " 次", 1),
                                    _cE("text", _uM("class" to "guard-sub"), "习惯渗透度 " + _tD(unref(penetrationText)), 1)
                                )),
                                if (unref(detailCount) > 0) {
                                    _cE("view", _uM("key" to 0, "class" to "list-section"), _uA(
                                        _cE("text", _uM("class" to "chart-label"), "今日完成记录"),
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(todayDetails), fun(item, idx, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "detail-item", "key" to idx), _uA(
                                                _cE("view", _uM("class" to "detail-left"), _uA(
                                                    _cE("text", _uM("class" to "detail-name"), _tD(item.name), 1),
                                                    _cE("text", _uM("class" to "detail-type"), _tD(formatDetailType(item.type)), 1)
                                                )),
                                                _cE("view", _uM("class" to "detail-right"), _uA(
                                                    _cE("text", _uM("class" to "detail-duration"), _tD(item.durationSec) + "秒", 1),
                                                    _cE("text", _uM("class" to "detail-time"), _tD(item.time), 1)
                                                ))
                                            ))
                                        }), 128)
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                if (unref(detailCount) < 1) {
                                    _cE("view", _uM("key" to 1, "class" to "empty-hint"), _uA(
                                        _cE("text", _uM("class" to "empty-text"), "完成更多微动作后，这里会出现你的健康轨迹")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        )),
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("view", _uM("class" to "section-header", "onClick" to toggleWeekly), _uA(
                                _cE("text", _uM("class" to "section-title"), "本周星球报告"),
                                _cE("text", _uM("class" to "section-toggle"), _tD(unref(weeklyToggleText)), 1)
                            )),
                            if (isTrue(unref(weeklyShow))) {
                                _cE("view", _uM("key" to 0, "class" to "section-body"), _uA(
                                    if (isTrue(unref(weeklyReportLoading))) {
                                        _cE("view", _uM("key" to 0, "class" to "loading-box"), _uA(
                                            _cE("text", _uM("class" to "loading-text"), "生成中")
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    if (isTrue(unref(weeklyLoaded) && !unref(weeklyReportLoading))) {
                                        _cE("view", _uM("key" to 1, "class" to "report-box"), _uA(
                                            _cE("view", _uM("class" to "report-stats"), _uA(
                                                _cE("view", _uM("class" to "stat-item"), _uA(
                                                    _cE("text", _uM("class" to "stat-val"), _tD(unref(wkTotalCompleted)), 1),
                                                    _cE("text", _uM("class" to "stat-label"), "次完成")
                                                )),
                                                _cE("view", _uM("class" to "stat-item"), _uA(
                                                    _cE("text", _uM("class" to "stat-val"), _tD(unref(wkAvgGuard)), 1),
                                                    _cE("text", _uM("class" to "stat-label"), "日均守护")
                                                )),
                                                _cE("view", _uM("class" to "stat-item"), _uA(
                                                    _cE("text", _uM("class" to "stat-val"), _tD(unref(wkBestDay)), 1),
                                                    _cE("text", _uM("class" to "stat-label"), "最佳日次")
                                                ))
                                            )),
                                            _cE("text", _uM("class" to "report-body"), _tD(unref(wkBody)), 1)
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    if (isTrue(!unref(weeklyLoaded) && !unref(weeklyReportLoading))) {
                                        _cE("view", _uM("key" to 2, "class" to "empty-hint"), _uA(
                                            _cE("text", _uM("class" to "empty-text"), "点击下方按钮生成周报"),
                                            _cE("view", _uM("class" to "refresh-btn", "onClick" to refreshWeek), _uA(
                                                _cE("text", _uM("class" to "refresh-text"), "生成周报")
                                            ))
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                        )),
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("view", _uM("class" to "section-header"), _uA(
                                _cE("text", _uM("class" to "section-title"), "心情状态词云"),
                                _cE("text", _uM("class" to "section-toggle", "onClick" to onRefreshMoodCloud), _tD(if (unref(moodCloudLoading)) {
                                    "生成中…"
                                } else {
                                    "提取状态"
                                }
                                ), 1)
                            )),
                            _cE("view", _uM("class" to "section-body"), _uA(
                                _cE("text", _uM("class" to "mood-cloud-hint"), "基于历次 AI 提醒与状态记录汇总，已去除情绪化用词与表情符号"),
                                if (unref(moodCloudWords).length > 0) {
                                    _cV(unref(GenComponentsWordCloudClass), _uM("key" to 0, "words" to unref(moodCloudWords)), null, 8, _uA(
                                        "words"
                                    ))
                                } else {
                                    _cE("view", _uM("key" to 1, "class" to "mood-cloud-empty"), _uA(
                                        _cE("text", _uM("class" to "empty-text"), _tD(unref(moodCloudHintText)), 1)
                                    ))
                                }
                            ))
                        )),
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("view", _uM("class" to "section-header"), _uA(
                                _cE("text", _uM("class" to "section-title"), "AI 历次判断"),
                                _cE("text", _uM("class" to "section-toggle"), _tD(unref(llmHistoryEntries).length) + " 条", 1)
                            )),
                            _cE("view", _uM("class" to "section-body"), _uA(
                                _cV(unref(GenComponentsLlmHistoryCardClass), _uM("entries" to unref(llmHistoryEntries)), null, 8, _uA(
                                    "entries"
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("view", _uM("class" to "section-header"), _uA(
                                _cE("text", _uM("class" to "section-title"), "星球点亮记录")
                            )),
                            _cE("view", _uM("class" to "section-body"), _uA(
                                _cE("view", _uM("class" to "chart-container"), _uA(
                                    _cV(unref(GenComponentsHeatmapCalendarClass), _uM("dates" to unref(heatmapDates), "counts" to unref(heatmapCounts), "height" to 320), null, 8, _uA(
                                        "dates",
                                        "counts"
                                    ))
                                )),
                                _cE("view", _uM("class" to "heatmap-legend"), _uA(
                                    _cE("text", _uM("class" to "legend-text"), "点击格子查看当天详情")
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "bottom-spacer"))
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#162038", "position" to "relative", "overflow" to "hidden")), "page-bg" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "width" to "100%", "height" to "100%", "zIndex" to 0)), "page-bg-img" to _pS(_uM("width" to "100%", "height" to "100%")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "zIndex" to 1)), "bg-glow" to _pS(_uM("position" to "absolute", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "zIndex" to 0)), "glow-purple" to _pS(_uM("width" to 210, "height" to 210, "left" to -82, "top" to -54, "backgroundColor" to "#776B97", "opacity" to 0.5)), "glow-blue" to _pS(_uM("width" to 168, "height" to 168, "right" to -60, "top" to 104, "backgroundColor" to "#C2E0EE", "opacity" to 0.24)), "glow-pink" to _pS(_uM("width" to 178, "height" to 178, "left" to 42, "bottom" to 88, "backgroundColor" to "#C68DC0", "opacity" to 0.18)), "sky-star" to _pS(_uM("position" to "absolute", "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "rgba(255,255,255,0.74)", "zIndex" to 0)), "star-a" to _pS(_uM("left" to 36, "top" to 88)), "star-b" to _pS(_uM("left" to 118, "top" to 144, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-c" to _pS(_uM("right" to 52, "top" to 116)), "star-d" to _pS(_uM("right" to 26, "top" to 310, "width" to 5, "height" to 5, "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "star-e" to _pS(_uM("left" to 54, "top" to 520)), "star-f" to _pS(_uM("right" to 86, "top" to 420, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-g" to _pS(_uM("left" to 138, "top" to 610)), "star-h" to _pS(_uM("right" to 36, "top" to 720, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-i" to _pS(_uM("left" to 76, "top" to 214, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-j" to _pS(_uM("right" to 148, "top" to 276, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-k" to _pS(_uM("left" to 178, "top" to 392, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-l" to _pS(_uM("right" to 64, "top" to 560, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "dashboard-hero" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 22, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 22, "paddingRight" to 18, "paddingBottom" to 22, "paddingLeft" to 18, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "flexDirection" to "row", "alignItems" to "center")), "settings-hero" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 22, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 22, "paddingRight" to 18, "paddingBottom" to 22, "paddingLeft" to 18, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "flexDirection" to "row", "alignItems" to "center")), "planet-note" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 0, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 16, "paddingRight" to 18, "paddingBottom" to 16, "paddingLeft" to 18, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24)), "section" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 12, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "overflow" to "hidden")), "settings-section" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 12, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "overflow" to "hidden")), "guard-box" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 8, "marginRight" to 16, "marginBottom" to 12, "marginLeft" to 16, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "alignItems" to "center")), "chart-container" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 10, "marginRight" to 16, "marginBottom" to 0, "marginLeft" to 16, "paddingTop" to 14, "paddingRight" to 0, "paddingBottom" to 6, "paddingLeft" to 0, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22)), "report-stats" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "flexDirection" to "row", "justifyContent" to "space-around", "marginTop" to 10, "marginRight" to 16, "marginBottom" to 10, "marginLeft" to 16, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "tag-card" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "marginBottom" to 10, "flexDirection" to "row")), "header-block" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 22, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 20, "paddingRight" to 18, "paddingBottom" to 20, "paddingLeft" to 18, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "flexDirection" to "column")), "filter-row" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 0, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 4, "paddingLeft" to 12, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22)), "empty-block" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 12, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 50, "paddingRight" to 20, "paddingBottom" to 50, "paddingLeft" to 20, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "alignItems" to "center")), "dashboard-hero-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "paddingRight" to 12)), "settings-hero-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "paddingRight" to 12)), "dashboard-kicker" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 13, "fontWeight" to "bold", "marginBottom" to 8)), "settings-kicker" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 13, "fontWeight" to "bold", "marginBottom" to 8)), "section-toggle" to _pS(_uM("color" to "#DBC8ED")), "setting-value" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 14, "fontWeight" to "bold")), "rule-priority" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 11, "marginLeft" to "auto")), "rule-source" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 11, "marginRight" to 8)), "detail-duration" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 14, "fontWeight" to "bold")), "guard-value" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 28, "fontWeight" to "bold", "marginTop" to 4)), "stat-val" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 20, "fontWeight" to "bold")), "dashboard-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 28, "fontWeight" to "bold", "lineHeight" to "34px")), "settings-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 28, "fontWeight" to "bold", "lineHeight" to "34px")), "section-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 18, "fontWeight" to "bold", "paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 0, "paddingLeft" to 0)), "setting-label" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 15, "fontWeight" to "bold")), "planet-note-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 16, "fontWeight" to "bold", "marginBottom" to 6)), "detail-name" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14)), "report-body" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14, "lineHeight" to "22px", "whiteSpace" to "pre-wrap", "marginTop" to 8)), "report-title" to _pS(_uM("color" to "#FFFFFF")), "header-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 22, "fontWeight" to "bold", "marginBottom" to 8)), "tag-label" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14, "fontWeight" to "bold", "marginBottom" to 3)), "dashboard-subtitle" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 13, "lineHeight" to "20px", "marginTop" to 8)), "settings-subtitle" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 13, "lineHeight" to "20px", "marginTop" to 8)), "planet-note-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "19px")), "setting-hint" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "18px", "marginTop" to 4)), "guard-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "guard-sub" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "chart-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "paddingLeft" to 14, "marginBottom" to 0, "fontSize" to 13, "fontWeight" to "bold")), "legend-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "loading-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "detail-type" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "marginTop" to 2)), "detail-time" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "marginTop" to 2)), "stat-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "rule-cond" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "rule-time" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 10)), "header-desc" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "19px")), "tag-pkg" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "marginBottom" to 7)), "tag-uses" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "dashboard-planet" to _pS(_uM("position" to "relative", "width" to 86, "height" to 86, "borderTopLeftRadius" to 43, "borderTopRightRadius" to 43, "borderBottomRightRadius" to 43, "borderBottomLeftRadius" to 43, "backgroundColor" to "rgba(219,200,237,0.18)", "alignItems" to "center", "justifyContent" to "center")), "settings-planet" to _pS(_uM("position" to "relative", "width" to 86, "height" to 86, "borderTopLeftRadius" to 43, "borderTopRightRadius" to 43, "borderBottomRightRadius" to 43, "borderBottomLeftRadius" to 43, "backgroundColor" to "rgba(219,200,237,0.18)", "alignItems" to "center", "justifyContent" to "center")), "dashboard-orbit" to _pS(_uM("position" to "absolute", "width" to 96, "height" to 34, "borderTopLeftRadius" to 48, "borderTopRightRadius" to 48, "borderBottomRightRadius" to 48, "borderBottomLeftRadius" to 48, "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "transform" to "rotate(-20deg)")), "settings-orbit" to _pS(_uM("position" to "absolute", "width" to 96, "height" to 34, "borderTopLeftRadius" to 48, "borderTopRightRadius" to 48, "borderBottomRightRadius" to 48, "borderBottomLeftRadius" to 48, "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "transform" to "rotate(-20deg)")), "dashboard-planet-text" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 34)), "settings-planet-text" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 34)), "section-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 16, "paddingRight" to 18, "paddingBottom" to 8, "paddingLeft" to 18)), "section-body" to _pS(_uM("paddingTop" to 8, "paddingRight" to 0, "paddingBottom" to 14, "paddingLeft" to 0)), "list-section" to _pS(_uM("paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16)), "mood-cloud-hint" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.62)", "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 8, "paddingLeft" to 16)), "mood-cloud-empty" to _pS(_uM("paddingTop" to 24, "paddingRight" to 16, "paddingBottom" to 24, "paddingLeft" to 16, "alignItems" to "center")), "detail-item" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 10, "paddingRight" to 0, "paddingBottom" to 10, "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomColor" to "rgba(219,200,237,0.16)", "borderBottomStyle" to "solid")), "detail-left" to _pS(_uM("flexDirection" to "column")), "detail-right" to _pS(_uM("flexDirection" to "column", "alignItems" to "flex-end")), "setting-left" to _pS(_uM("flexDirection" to "column", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "heatmap-legend" to _pS(_uM("alignItems" to "center", "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20)), "empty-hint" to _pS(_uM("alignItems" to "center", "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20)), "loading-box" to _pS(_uM("alignItems" to "center", "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20)), "report-box" to _pS(_uM("paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16)), "stat-item" to _pS(_uM("alignItems" to "center")), "refresh-btn" to _pS(_uM("marginTop" to 12, "paddingTop" to 10, "paddingRight" to 24, "paddingBottom" to 10, "paddingLeft" to 24, "backgroundColor" to "#DBC8ED", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "refresh-text" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")), "setting-item" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 15, "paddingRight" to 18, "paddingBottom" to 15, "paddingLeft" to 18, "borderBottomWidth" to 1, "borderBottomColor" to "rgba(219,200,237,0.16)", "borderBottomStyle" to "solid")), "empty-rules" to _pS(_uM("alignItems" to "center", "paddingTop" to 24, "paddingRight" to 16, "paddingBottom" to 28, "paddingLeft" to 16)), "rule-card" to _pS(_uM("paddingTop" to 14, "paddingRight" to 18, "paddingBottom" to 14, "paddingLeft" to 18, "borderBottomWidth" to 1, "borderBottomColor" to "rgba(219,200,237,0.16)", "borderBottomStyle" to "solid")), "rule-row1" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 4)), "rule-row2" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 4)), "rule-type" to _pS(_uM("fontSize" to 11, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 4, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8, "marginRight" to 6, "color" to "#FFFFFF", "backgroundColor" to "rgba(219,200,237,0.18)", "fontWeight" to "bold")))
            }
        val styles1: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("rule-trigger" to _pS(_uM("fontSize" to 11, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 4, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8, "marginRight" to 6, "color" to "rgba(255,255,255,0.72)", "backgroundColor" to "rgba(194,224,238,0.12)")), "rule-row3" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "marginTop" to 4)), "filter-chip" to _pS(_uM("paddingTop" to 6, "paddingRight" to 12, "paddingBottom" to 6, "paddingLeft" to 12, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "marginRight" to 6, "marginBottom" to 8)), "filter-chip-active" to _pS(_uM("backgroundColor" to "#DBC8ED")), "filter-chip-text" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF")), "tag-list" to _pS(_uM("paddingTop" to 0, "paddingRight" to 14, "paddingBottom" to 18, "paddingLeft" to 14)), "tag-card-left" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "tag-card-right" to _pS(_uM("flexDirection" to "column", "alignItems" to "flex-end", "marginLeft" to 10)), "tag-source-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "tag-source" to _pS(_uM("fontSize" to 10, "paddingTop" to 3, "paddingRight" to 7, "paddingBottom" to 3, "paddingLeft" to 7, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "marginRight" to 6)), "tag-source-llm" to _pS(_uM("backgroundColor" to "rgba(194,224,238,0.14)", "color" to "#C2E0EE")), "tag-source-user" to _pS(_uM("backgroundColor" to "rgba(143,240,195,0.12)", "color" to "#8FF0C3")), "tag-source-pending" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.14)", "color" to "#DBC8ED")), "cat-picker" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "rgba(255,255,255,0.1)", "paddingTop" to 5, "paddingRight" to 10, "paddingBottom" to 5, "paddingLeft" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "marginBottom" to 6)), "cat-picker-text" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "marginRight" to 4)), "cat-picker-arrow" to _pS(_uM("color" to "rgba(255,255,255,0.68)")), "lock-text" to _pS(_uM("color" to "rgba(255,255,255,0.68)")), "lock-text-on" to _pS(_uM("color" to "#DBC8ED", "fontWeight" to "bold")), "bottom-spacer" to _pS(_uM("height" to 132)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
