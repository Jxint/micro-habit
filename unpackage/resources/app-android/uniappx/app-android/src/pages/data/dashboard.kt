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
            fun gen_getWkTitle_fn(): String {
                val r = store.weeklyReport.value
                return if (r != null) {
                    r.title
                } else {
                    ""
                }
            }
            val getWkTitle = ::gen_getWkTitle_fn
            fun gen_getWkHighlight_fn(): String {
                val r = store.weeklyReport.value
                return if (r != null) {
                    r.highlight
                } else {
                    ""
                }
            }
            val getWkHighlight = ::gen_getWkHighlight_fn
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
            fun gen_getWkImprovement_fn(): String {
                val r = store.weeklyReport.value
                return if (r != null) {
                    r.improvement
                } else {
                    ""
                }
            }
            val getWkImprovement = ::gen_getWkImprovement_fn
            fun gen_getWkSuggestions_fn(): UTSArray<String> {
                val r = store.weeklyReport.value
                return if (r != null) {
                    r.suggestions
                } else {
                    _uA()
                }
            }
            val getWkSuggestions = ::gen_getWkSuggestions_fn
            val wkTitle = computed<String>(getWkTitle)
            val wkHighlight = computed<String>(getWkHighlight)
            val wkTotalCompleted = computed<Number>(getWkTotalCompleted)
            val wkAvgGuard = computed<Number>(getWkAvgGuard)
            val wkBestDay = computed<Number>(getWkBestDay)
            val wkImprovement = computed<String>(getWkImprovement)
            val wkSuggestions = computed<UTSArray<String>>(getWkSuggestions)
            val wkSuggestCount = computed<Number>(fun(): Number {
                return wkSuggestions.value.length
            }
            )
            fun gen_getWkGoal_fn(): String {
                val r = store.weeklyReport.value
                return if (r != null) {
                    r.nextWeekGoal
                } else {
                    ""
                }
            }
            val getWkGoal = ::gen_getWkGoal_fn
            val wkGoal = computed<String>(getWkGoal)
            val todayDetails = ref(_uA<ActionDetail>())
            val detailCount = computed<Number>(fun(): Number {
                return todayDetails.value.length
            }
            )
            fun gen_loadData_fn(): Unit {
                val date = today()
                val hourly = getHourlyData(date)
                val hLabels: UTSArray<String> = _uA()
                val hValues: UTSArray<Number> = _uA()
                run {
                    var i: Number = 0
                    while(i < hourly.length){
                        hLabels.push(hourly[i].label)
                        hValues.push(hourly[i].value)
                        i++
                    }
                }
                hourlyLabels.value = hLabels
                hourlyValues.value = hValues
                val trend = getWeeklyTrend()
                if (trend.length > 0) {
                    val first = trend[0]
                    if (first != null) {
                        weekTrendNames.value = _uA(
                            first.name
                        )
                        weekTrendColors.value = _uA(
                            first.color
                        )
                        val v: UTSArray<Number> = _uA()
                        val labels: UTSArray<String> = _uA()
                        run {
                            var i: Number = 0
                            while(i < first.points.length){
                                labels.push(first.points[i].label)
                                v.push(first.points[i].value)
                                i++
                            }
                        }
                        weekTrendLabels.value = labels
                        weekTrendValues.value = _uA(
                            v
                        )
                    } else {
                        weekTrendNames.value = _uA()
                        weekTrendColors.value = _uA()
                        weekTrendLabels.value = _uA()
                        weekTrendValues.value = _uA()
                    }
                } else {
                    weekTrendNames.value = _uA()
                    weekTrendColors.value = _uA()
                    weekTrendLabels.value = _uA()
                    weekTrendValues.value = _uA()
                }
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
            onPageShow(fun(){
                store.refreshHomeData()
                loadData()
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("view", _uM("class" to "section-header"), _uA(
                                _cE("text", _uM("class" to "section-title"), "今日数据")
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
                                _cE("view", _uM("class" to "chart-container"), _uA(
                                    _cE("text", _uM("class" to "chart-label"), "各时段完成次数（相对高度）"),
                                    _cV(unref(GenComponentsBarChartClass), _uM("labels" to unref(hourlyLabels), "values" to unref(hourlyValues), "height" to 300, "barColor" to "#4CAF50"), null, 8, _uA(
                                        "labels",
                                        "values"
                                    ))
                                )),
                                if (unref(detailCount) > 0) {
                                    _cE("view", _uM("key" to 0, "class" to "list-section"), _uA(
                                        _cE("text", _uM("class" to "chart-label"), "今日完成记录"),
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(todayDetails), fun(item, idx, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "detail-item", "key" to idx), _uA(
                                                _cE("view", _uM("class" to "detail-left"), _uA(
                                                    _cE("text", _uM("class" to "detail-name"), _tD(item.name), 1),
                                                    _cE("text", _uM("class" to "detail-type"), _tD(item.type), 1)
                                                )),
                                                _cE("view", _uM("class" to "detail-right"), _uA(
                                                    _cE("text", _uM("class" to "detail-duration"), _tD(item.durationSec) + "s", 1),
                                                    _cE("text", _uM("class" to "detail-time"), _tD(item.time), 1)
                                                ))
                                            ))
                                        }), 128)
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                if (unref(detailCount) === 0) {
                                    _cE("view", _uM("key" to 1, "class" to "empty-hint"), _uA(
                                        _cE("text", _uM("class" to "empty-text"), "今日暂无完成记录")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        )),
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("view", _uM("class" to "section-header"), _uA(
                                _cE("text", _uM("class" to "section-title"), "本周趋势")
                            )),
                            _cE("view", _uM("class" to "section-body"), _uA(
                                _cE("view", _uM("class" to "chart-container"), _uA(
                                    _cV(unref(GenComponentsLineChartClass), _uM("series-values" to unref(weekTrendValues), "series-names" to unref(weekTrendNames), "series-colors" to unref(weekTrendColors), "point-labels" to unref(weekTrendLabels), "height" to 360, "title" to "本周完成次数趋势（左右可拖动）"), null, 8, _uA(
                                        "series-values",
                                        "series-names",
                                        "series-colors",
                                        "point-labels"
                                    ))
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("view", _uM("class" to "section-header"), _uA(
                                _cE("text", _uM("class" to "section-title"), "打卡热力图（近4周）")
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
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("view", _uM("class" to "section-header", "onClick" to toggleWeekly), _uA(
                                _cE("text", _uM("class" to "section-title"), "每周健康报告"),
                                _cE("text", _uM("class" to "section-toggle"), _tD(unref(weeklyToggleText)), 1)
                            )),
                            if (isTrue(unref(weeklyShow))) {
                                _cE("view", _uM("key" to 0, "class" to "section-body"), _uA(
                                    if (isTrue(unref(weeklyReportLoading))) {
                                        _cE("view", _uM("key" to 0, "class" to "loading-box"), _uA(
                                            _cE("text", _uM("class" to "loading-text"), "生成中...")
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    if (isTrue(unref(weeklyLoaded) && !unref(weeklyReportLoading))) {
                                        _cE("view", _uM("key" to 1, "class" to "report-box"), _uA(
                                            _cE("text", _uM("class" to "report-title"), _tD(unref(wkTitle)), 1),
                                            _cE("text", _uM("class" to "report-highlight"), _tD(unref(wkHighlight)), 1),
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
                                            if (unref(wkImprovement) !== "") {
                                                _cE("text", _uM("key" to 0, "class" to "report-improve"), _tD(unref(wkImprovement)), 1)
                                            } else {
                                                _cC("v-if", true)
                                            },
                                            if (unref(wkSuggestCount) > 0) {
                                                _cE("view", _uM("key" to 1, "class" to "suggestions"), _uA(
                                                    _cE("text", _uM("class" to "sug-label"), "AI 建议"),
                                                    _cE(Fragment, null, RenderHelpers.renderList(unref(wkSuggestions), fun(s, sIdx, __index, _cached): Any {
                                                        return _cE("view", _uM("key" to sIdx), _uA(
                                                            _cE("text", _uM("class" to "sug-text"), "· " + _tD(s), 1)
                                                        ))
                                                    }), 128)
                                                ))
                                            } else {
                                                _cC("v-if", true)
                                            },
                                            if (unref(wkGoal) !== "") {
                                                _cE("text", _uM("key" to 2, "class" to "report-goal"), _tD(unref(wkGoal)), 1)
                                            } else {
                                                _cC("v-if", true)
                                            }
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
                        _cE("view", _uM("class" to "bottom-spacer"))
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F5F6FA")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "section" to _pS(_uM("backgroundColor" to "#FFFFFF", "marginTop" to 8, "marginRight" to 8, "marginBottom" to 8, "marginLeft" to 8, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "overflow" to "hidden")), "section-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#F0F0F0")), "section-title" to _pS(_uM("fontSize" to 16, "fontWeight" to "bold", "color" to "#2C3E50")), "section-toggle" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6")), "section-body" to _pS(_uM("paddingTop" to 12, "paddingRight" to 0, "paddingBottom" to 12, "paddingLeft" to 0)), "guard-box" to _pS(_uM("alignItems" to "center", "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "marginTop" to 8, "marginRight" to 16, "marginBottom" to 8, "marginLeft" to 16, "backgroundColor" to "#EBF5FB", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12)), "guard-label" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D")), "guard-value" to _pS(_uM("fontSize" to 28, "fontWeight" to "bold", "color" to "#2980B9", "marginTop" to 4)), "guard-sub" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6", "marginTop" to 4)), "chart-container" to _pS(_uM("paddingTop" to 8, "paddingRight" to 4, "paddingBottom" to 8, "paddingLeft" to 4)), "chart-label" to _pS(_uM("fontSize" to 13, "fontWeight" to "bold", "color" to "#7F8C8D", "paddingLeft" to 16, "marginBottom" to 4)), "list-section" to _pS(_uM("paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16)), "detail-item" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 10, "paddingRight" to 0, "paddingBottom" to 10, "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#F0F0F0")), "detail-left" to _pS(_uM("flexDirection" to "column")), "detail-name" to _pS(_uM("fontSize" to 14, "color" to "#2C3E50")), "detail-type" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginTop" to 2)), "detail-right" to _pS(_uM("flexDirection" to "column", "alignItems" to "flex-end")), "detail-duration" to _pS(_uM("fontSize" to 14, "color" to "#27AE60", "fontWeight" to "bold")), "detail-time" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginTop" to 2)), "heatmap-legend" to _pS(_uM("alignItems" to "center", "paddingTop" to 4, "paddingRight" to 0, "paddingBottom" to 12, "paddingLeft" to 0)), "legend-text" to _pS(_uM("fontSize" to 12, "color" to "#BDC3C7")), "empty-hint" to _pS(_uM("alignItems" to "center", "paddingTop" to 24, "paddingRight" to 24, "paddingBottom" to 24, "paddingLeft" to 24)), "empty-text" to _pS(_uM("fontSize" to 13, "color" to "#BDC3C7")), "loading-box" to _pS(_uM("alignItems" to "center", "paddingTop" to 24, "paddingRight" to 24, "paddingBottom" to 24, "paddingLeft" to 24)), "loading-text" to _pS(_uM("fontSize" to 13, "color" to "#95A5A6")), "report-box" to _pS(_uM("paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16)), "report-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50", "marginBottom" to 8)), "report-highlight" to _pS(_uM("fontSize" to 14, "color" to "#34495E", "lineHeight" to 1.5)), "report-stats" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-around", "marginTop" to 16, "marginRight" to 0, "marginBottom" to 16, "marginLeft" to 0, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "backgroundColor" to "#F5F6FA", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8)), "stat-item" to _pS(_uM("alignItems" to "center")), "stat-val" to _pS(_uM("fontSize" to 20, "fontWeight" to "bold", "color" to "#2980B9")), "stat-label" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginTop" to 2)), "report-improve" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D", "lineHeight" to 1.5, "marginTop" to 8)), "suggestions" to _pS(_uM("marginTop" to 12)), "sug-label" to _pS(_uM("fontSize" to 13, "fontWeight" to "bold", "color" to "#2C3E50", "marginBottom" to 6)), "sug-text" to _pS(_uM("fontSize" to 13, "color" to "#34495E", "lineHeight" to 1.5)), "report-goal" to _pS(_uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#27AE60", "marginTop" to 12, "paddingTop" to 8, "paddingRight" to 12, "paddingBottom" to 8, "paddingLeft" to 12, "backgroundColor" to "#EAFAF1", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8)), "refresh-btn" to _pS(_uM("marginTop" to 12, "paddingTop" to 10, "paddingRight" to 24, "paddingBottom" to 10, "paddingLeft" to 24, "backgroundColor" to "#2980B9", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8)), "refresh-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14)), "bottom-spacer" to _pS(_uM("height" to 80)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
