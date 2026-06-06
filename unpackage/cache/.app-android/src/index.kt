@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNIB805CE2
import android.app.AppOpsManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteStatement
import android.graphics.Color
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.os.Process
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
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
import java.lang.System
import java.util.Locale
import kotlin.properties.Delegates
import io.dcloud.uniapp.extapi.`$emit` as uni__emit
import io.dcloud.uniapp.extapi.`$on` as uni__on
import android.view.WindowManager.LayoutParams as WindowManagerLayoutParams
import io.dcloud.uniapp.extapi.connectSocket as uni_connectSocket
import io.dcloud.uniapp.extapi.downloadFile as uni_downloadFile
import io.dcloud.uniapp.extapi.exit as uni_exit
import io.dcloud.uniapp.extapi.getFileSystemManager as uni_getFileSystemManager
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showToast as uni_showToast
val runBlock1 = run {
    __uniConfig.getAppStyles = fun(): Map<String, Map<String, Map<String, Any>>> {
        return GenApp.styles
    }
}
typealias currentPageCaptureScreenshotCallBack = (base64: String, error: String) -> Unit
fun currentPageCaptureScreenshot(fullPage: Boolean, callback: currentPageCaptureScreenshotCallBack) {
    val pages = getCurrentPages() as UTSArray<UniPage>
    val currentPage = pages[pages.length - 1]
    currentPage.vm?.`$viewToTempFilePath`(ViewToTempFilePathOptions(wholeContent = fullPage, overwrite = true, success = fun(res){
        val fileManager = uni_getFileSystemManager()
        fileManager.readFile(ReadFileOptions(encoding = "base64", filePath = res.tempFilePath, success = fun(readFileRes) {
            callback(readFileRes.data as String, "")
        }
        , fail = fun(err) {
            callback("", "captureScreenshot fail: " + JSON.stringify(err))
        }
        ))
    }
    , fail = fun(err){
        callback("", "captureScreenshot fail: " + JSON.stringify(err))
    }
    ))
}
fun initRuntimeSocket(hosts: String, port: String, id: String): UTSPromise<SocketTask?> {
    if (hosts == "" || port == "" || id == "") {
        return UTSPromise.resolve(null)
    }
    return hosts.split(",").reduce<UTSPromise<SocketTask?>>(fun(promise: UTSPromise<SocketTask?>, host: String): UTSPromise<SocketTask?> {
        return promise.then(fun(socket): UTSPromise<SocketTask?> {
            if (socket != null) {
                return UTSPromise.resolve(socket)
            }
            return tryConnectSocket(host, port, id)
        }
        )
    }
    , UTSPromise.resolve(null))
}
val SOCKET_TIMEOUT: Number = 500
fun tryConnectSocket(host: String, port: String, id: String): UTSPromise<SocketTask?> {
    return UTSPromise(fun(resolve, reject){
        val socket = uni_connectSocket(ConnectSocketOptions(url = "ws://" + host + ":" + port + "/" + id, fail = fun(_) {
            resolve(null)
        }
        ))
        val timer = setTimeout(fun(){
            socket.close(CloseSocketOptions(code = 1006, reason = "connect timeout"))
            resolve(null)
        }
        , SOCKET_TIMEOUT)
        socket.onOpen(fun(e){
            clearTimeout(timer)
            resolve(socket)
        }
        )
        socket.onClose(fun(e){
            clearTimeout(timer)
            resolve(null)
        }
        )
        socket.onError(fun(e){
            clearTimeout(timer)
            resolve(null)
        }
        )
        socket.onMessage(fun(result){
            if (UTSAndroid.`typeof`(result["data"]) == "string") {
                val message = UTSAndroid.consoleDebugError(JSON.parse<UTSJSONObject>(result["data"] as String), " at ../../../../../../../../../../../../D:/Program Files (x86)/HBuilderX/plugins/uniapp-cli-vite/node_modules/@dcloudio/uni-console/src/runtime/app/socket.ts:67")!!
                if ((message["type"] as String) == "screencap") {
                    val id = message["id"] as String
                    currentPageCaptureScreenshot(message["fullPage"] as Boolean, fun(base64: String, error: String){
                        socket.send(SendSocketMessageOptions(data = JSON.stringify(_uO("id" to id, "base64" to base64, "error" to error))))
                    }
                    )
                }
            }
            resolve(null)
        }
        )
    }
    )
}
fun initRuntimeSocketService(): UTSPromise<Boolean> {
    val hosts: String = "172.24.90.1,127.0.0.1"
    val port: String = "8090"
    val id: String = "app-android_jPpzlT"
    if (hosts == "" || port == "" || id == "") {
        return UTSPromise.resolve(false)
    }
    return UTSPromise.resolve().then(fun(): UTSPromise<Boolean> {
        return initRuntimeSocket(hosts, port, id).then(fun(socket): Boolean {
            if (socket == null) {
                return false
            }
            socket
            return true
        }
        )
    }
    ).`catch`(fun(): Boolean {
        return false
    }
    )
}
val runBlock2 = run {
    initRuntimeSocketService()
}
open class TimeWindow (
    @JsonNotNull
    open var start: String,
    @JsonNotNull
    open var end: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TimeWindow", "models/EffectiveTriggerRule.uts", 1, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TimeWindowReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TimeWindowReactiveObject : TimeWindow, IUTSReactive<TimeWindow> {
    override var __v_raw: TimeWindow
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TimeWindow, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(start = __v_raw.start, end = __v_raw.end) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TimeWindowReactiveObject {
        return TimeWindowReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var start: String
        get() {
            return _tRG(__v_raw, "start", __v_raw.start, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("start")) {
                return
            }
            val oldValue = __v_raw.start
            __v_raw.start = value
            _tRS(__v_raw, "start", oldValue, value)
        }
    override var end: String
        get() {
            return _tRG(__v_raw, "end", __v_raw.end, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("end")) {
                return
            }
            val oldValue = __v_raw.end
            __v_raw.end = value
            _tRS(__v_raw, "end", oldValue, value)
        }
}
open class ScreenConditions (
    @JsonNotNull
    open var appPackages: UTSArray<String>,
    @JsonNotNull
    open var includeHome: Boolean = false,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ScreenConditions", "models/EffectiveTriggerRule.uts", 5, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ScreenConditionsReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ScreenConditionsReactiveObject : ScreenConditions, IUTSReactive<ScreenConditions> {
    override var __v_raw: ScreenConditions
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ScreenConditions, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(appPackages = __v_raw.appPackages, includeHome = __v_raw.includeHome) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ScreenConditionsReactiveObject {
        return ScreenConditionsReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var appPackages: UTSArray<String>
        get() {
            return _tRG(__v_raw, "appPackages", __v_raw.appPackages, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("appPackages")) {
                return
            }
            val oldValue = __v_raw.appPackages
            __v_raw.appPackages = value
            _tRS(__v_raw, "appPackages", oldValue, value)
        }
    override var includeHome: Boolean
        get() {
            return _tRG(__v_raw, "includeHome", __v_raw.includeHome, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("includeHome")) {
                return
            }
            val oldValue = __v_raw.includeHome
            __v_raw.includeHome = value
            _tRS(__v_raw, "includeHome", oldValue, value)
        }
}
typealias TriggerLevel = String
open class EffectiveTriggerRule (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var actionId: String,
    open var timeWindow: TimeWindow? = null,
    open var screenConditions: ScreenConditions? = null,
    @JsonNotNull
    open var timeThresholdMinutes: Number,
    open var categoryFilter: String? = null,
    @JsonNotNull
    open var source: String,
    @JsonNotNull
    open var enabled: Boolean = false,
    @JsonNotNull
    open var createdAt: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("EffectiveTriggerRule", "models/EffectiveTriggerRule.uts", 10, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return EffectiveTriggerRuleReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class EffectiveTriggerRuleReactiveObject : EffectiveTriggerRule, IUTSReactive<EffectiveTriggerRule> {
    override var __v_raw: EffectiveTriggerRule
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: EffectiveTriggerRule, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, actionId = __v_raw.actionId, timeWindow = __v_raw.timeWindow, screenConditions = __v_raw.screenConditions, timeThresholdMinutes = __v_raw.timeThresholdMinutes, categoryFilter = __v_raw.categoryFilter, source = __v_raw.source, enabled = __v_raw.enabled, createdAt = __v_raw.createdAt) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): EffectiveTriggerRuleReactiveObject {
        return EffectiveTriggerRuleReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var actionId: String
        get() {
            return _tRG(__v_raw, "actionId", __v_raw.actionId, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("actionId")) {
                return
            }
            val oldValue = __v_raw.actionId
            __v_raw.actionId = value
            _tRS(__v_raw, "actionId", oldValue, value)
        }
    override var timeWindow: TimeWindow?
        get() {
            return _tRG(__v_raw, "timeWindow", __v_raw.timeWindow, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("timeWindow")) {
                return
            }
            val oldValue = __v_raw.timeWindow
            __v_raw.timeWindow = value
            _tRS(__v_raw, "timeWindow", oldValue, value)
        }
    override var screenConditions: ScreenConditions?
        get() {
            return _tRG(__v_raw, "screenConditions", __v_raw.screenConditions, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("screenConditions")) {
                return
            }
            val oldValue = __v_raw.screenConditions
            __v_raw.screenConditions = value
            _tRS(__v_raw, "screenConditions", oldValue, value)
        }
    override var timeThresholdMinutes: Number
        get() {
            return _tRG(__v_raw, "timeThresholdMinutes", __v_raw.timeThresholdMinutes, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("timeThresholdMinutes")) {
                return
            }
            val oldValue = __v_raw.timeThresholdMinutes
            __v_raw.timeThresholdMinutes = value
            _tRS(__v_raw, "timeThresholdMinutes", oldValue, value)
        }
    override var categoryFilter: String?
        get() {
            return _tRG(__v_raw, "categoryFilter", __v_raw.categoryFilter, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("categoryFilter")) {
                return
            }
            val oldValue = __v_raw.categoryFilter
            __v_raw.categoryFilter = value
            _tRS(__v_raw, "categoryFilter", oldValue, value)
        }
    override var source: String
        get() {
            return _tRG(__v_raw, "source", __v_raw.source, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("source")) {
                return
            }
            val oldValue = __v_raw.source
            __v_raw.source = value
            _tRS(__v_raw, "source", oldValue, value)
        }
    override var enabled: Boolean
        get() {
            return _tRG(__v_raw, "enabled", __v_raw.enabled, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("enabled")) {
                return
            }
            val oldValue = __v_raw.enabled
            __v_raw.enabled = value
            _tRS(__v_raw, "enabled", oldValue, value)
        }
    override var createdAt: Number
        get() {
            return _tRG(__v_raw, "createdAt", __v_raw.createdAt, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("createdAt")) {
                return
            }
            val oldValue = __v_raw.createdAt
            __v_raw.createdAt = value
            _tRS(__v_raw, "createdAt", oldValue, value)
        }
}
open class DailySummary (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var date: String,
    @JsonNotNull
    open var total_completed: Number,
    @JsonNotNull
    open var total_skipped: Number,
    @JsonNotNull
    open var total_duration_sec: Number,
    @JsonNotNull
    open var eye_score: Number,
    @JsonNotNull
    open var posture_score: Number,
    @JsonNotNull
    open var vitality_score: Number,
    @JsonNotNull
    open var penetration: Number,
    @JsonNotNull
    open var phone_usage_min: Number,
    @JsonNotNull
    open var guard_minutes: Number,
    @JsonNotNull
    open var guard_count: Number,
    @JsonNotNull
    open var created_at: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DailySummary", "models/DailySummary.uts", 1, 13)
    }
}
open class DailyData (
    @JsonNotNull
    open var totalCompleted: Number,
    @JsonNotNull
    open var totalSkipped: Number,
    @JsonNotNull
    open var totalDurationSec: Number,
    @JsonNotNull
    open var guardMinutes: Number,
    @JsonNotNull
    open var guardCount: Number,
    @JsonNotNull
    open var penetration: Number,
    @JsonNotNull
    open var eyeScore: Number,
    @JsonNotNull
    open var postureScore: Number,
    @JsonNotNull
    open var vitalityScore: Number,
    @JsonNotNull
    open var todayCompletedCount: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DailyData", "models/DailySummary.uts", 16, 13)
    }
}
open class WeeklyReportStats (
    @JsonNotNull
    open var totalCompleted: Number,
    @JsonNotNull
    open var totalDurationSec: Number,
    @JsonNotNull
    open var avgPenetration: Number,
    @JsonNotNull
    open var avgGuardMinutes: Number,
    @JsonNotNull
    open var bestDay: String,
    @JsonNotNull
    open var bestDayCount: Number,
    @JsonNotNull
    open var worstDay: String,
    @JsonNotNull
    open var worstDayCount: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("WeeklyReportStats", "models/WeeklyReport.uts", 1, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return WeeklyReportStatsReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class WeeklyReportStatsReactiveObject : WeeklyReportStats, IUTSReactive<WeeklyReportStats> {
    override var __v_raw: WeeklyReportStats
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: WeeklyReportStats, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(totalCompleted = __v_raw.totalCompleted, totalDurationSec = __v_raw.totalDurationSec, avgPenetration = __v_raw.avgPenetration, avgGuardMinutes = __v_raw.avgGuardMinutes, bestDay = __v_raw.bestDay, bestDayCount = __v_raw.bestDayCount, worstDay = __v_raw.worstDay, worstDayCount = __v_raw.worstDayCount) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): WeeklyReportStatsReactiveObject {
        return WeeklyReportStatsReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var totalCompleted: Number
        get() {
            return _tRG(__v_raw, "totalCompleted", __v_raw.totalCompleted, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("totalCompleted")) {
                return
            }
            val oldValue = __v_raw.totalCompleted
            __v_raw.totalCompleted = value
            _tRS(__v_raw, "totalCompleted", oldValue, value)
        }
    override var totalDurationSec: Number
        get() {
            return _tRG(__v_raw, "totalDurationSec", __v_raw.totalDurationSec, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("totalDurationSec")) {
                return
            }
            val oldValue = __v_raw.totalDurationSec
            __v_raw.totalDurationSec = value
            _tRS(__v_raw, "totalDurationSec", oldValue, value)
        }
    override var avgPenetration: Number
        get() {
            return _tRG(__v_raw, "avgPenetration", __v_raw.avgPenetration, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("avgPenetration")) {
                return
            }
            val oldValue = __v_raw.avgPenetration
            __v_raw.avgPenetration = value
            _tRS(__v_raw, "avgPenetration", oldValue, value)
        }
    override var avgGuardMinutes: Number
        get() {
            return _tRG(__v_raw, "avgGuardMinutes", __v_raw.avgGuardMinutes, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("avgGuardMinutes")) {
                return
            }
            val oldValue = __v_raw.avgGuardMinutes
            __v_raw.avgGuardMinutes = value
            _tRS(__v_raw, "avgGuardMinutes", oldValue, value)
        }
    override var bestDay: String
        get() {
            return _tRG(__v_raw, "bestDay", __v_raw.bestDay, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("bestDay")) {
                return
            }
            val oldValue = __v_raw.bestDay
            __v_raw.bestDay = value
            _tRS(__v_raw, "bestDay", oldValue, value)
        }
    override var bestDayCount: Number
        get() {
            return _tRG(__v_raw, "bestDayCount", __v_raw.bestDayCount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("bestDayCount")) {
                return
            }
            val oldValue = __v_raw.bestDayCount
            __v_raw.bestDayCount = value
            _tRS(__v_raw, "bestDayCount", oldValue, value)
        }
    override var worstDay: String
        get() {
            return _tRG(__v_raw, "worstDay", __v_raw.worstDay, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("worstDay")) {
                return
            }
            val oldValue = __v_raw.worstDay
            __v_raw.worstDay = value
            _tRS(__v_raw, "worstDay", oldValue, value)
        }
    override var worstDayCount: Number
        get() {
            return _tRG(__v_raw, "worstDayCount", __v_raw.worstDayCount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("worstDayCount")) {
                return
            }
            val oldValue = __v_raw.worstDayCount
            __v_raw.worstDayCount = value
            _tRS(__v_raw, "worstDayCount", oldValue, value)
        }
}
open class WeeklyReport (
    @JsonNotNull
    open var bodyMarkdown: String,
    @JsonNotNull
    open var stats: WeeklyReportStats,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("WeeklyReport", "models/WeeklyReport.uts", 11, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return WeeklyReportReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class WeeklyReportReactiveObject : WeeklyReport, IUTSReactive<WeeklyReport> {
    override var __v_raw: WeeklyReport
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: WeeklyReport, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(bodyMarkdown = __v_raw.bodyMarkdown, stats = __v_raw.stats) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): WeeklyReportReactiveObject {
        return WeeklyReportReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var bodyMarkdown: String
        get() {
            return _tRG(__v_raw, "bodyMarkdown", __v_raw.bodyMarkdown, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("bodyMarkdown")) {
                return
            }
            val oldValue = __v_raw.bodyMarkdown
            __v_raw.bodyMarkdown = value
            _tRS(__v_raw, "bodyMarkdown", oldValue, value)
        }
    override var stats: WeeklyReportStats
        get() {
            return _tRG(__v_raw, "stats", __v_raw.stats, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("stats")) {
                return
            }
            val oldValue = __v_raw.stats
            __v_raw.stats = value
            _tRS(__v_raw, "stats", oldValue, value)
        }
}
open class PreAppHistory (
    @JsonNotNull
    open var totalTriggers: Number,
    @JsonNotNull
    open var totalCompletes: Number,
    @JsonNotNull
    open var totalSkips: Number,
    @JsonNotNull
    open var acceptRate: Number,
    @JsonNotNull
    open var typicalContinuousMin: Number,
    @JsonNotNull
    open var lastTriggerAt: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PreAppHistory", "constants/LlmPrompts.uts", 5, 13)
    }
}
open class PostAppHistory (
    @JsonNotNull
    open var packageName: String,
    @JsonNotNull
    open var totalTriggers: Number,
    @JsonNotNull
    open var totalCompletes: Number,
    @JsonNotNull
    open var totalSkips: Number,
    @JsonNotNull
    open var acceptRate: Number,
    @JsonNotNull
    open var typicalContinuousMin: Number,
    @JsonNotNull
    open var lastWeekTrend: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PostAppHistory", "constants/LlmPrompts.uts", 14, 13)
    }
}
open class MatchedRuleInfo (
    @JsonNotNull
    open var actionId: String,
    @JsonNotNull
    open var source: String,
    open var timeWindow: TimeWindow? = null,
    @JsonNotNull
    open var thresholdMinutes: Number,
    @JsonNotNull
    open var triggerLevel: TriggerLevel,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MatchedRuleInfo", "constants/LlmPrompts.uts", 24, 13)
    }
}
open class UserPreferenceInfo (
    @JsonNotNull
    open var healthFocus: String,
    @JsonNotNull
    open var bodyLimit: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UserPreferenceInfo", "constants/LlmPrompts.uts", 32, 13)
    }
}
open class PostContextRuleItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var actionId: String,
    @JsonNotNull
    open var appPackages: UTSArray<String>,
    open var timeWindow: TimeWindow? = null,
    @JsonNotNull
    open var thresholdMinutes: Number,
    @JsonNotNull
    open var triggerLevel: TriggerLevel,
    @JsonNotNull
    open var source: String,
    @JsonNotNull
    open var createdAt: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PostContextRuleItem", "constants/LlmPrompts.uts", 37, 13)
    }
}
open class LastWeekSameTime (
    @JsonNotNull
    open var avgContinuousMin: Number,
    @JsonNotNull
    open var triggerCount: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LastWeekSameTime", "constants/LlmPrompts.uts", 48, 13)
    }
}
open class PreTriggerContext (
    @JsonNotNull
    open var appLabel: String,
    @JsonNotNull
    open var appPackage: String,
    @JsonNotNull
    open var appCategory: String,
    @JsonNotNull
    open var continuousMinutes: Number,
    @JsonNotNull
    open var hour: Number,
    @JsonNotNull
    open var todayCompletedCount: Number,
    @JsonNotNull
    open var guardMinutes: Number,
    @JsonNotNull
    open var todaySkipCount: Number,
    @JsonNotNull
    open var recentActions: UTSArray<String>,
    open var appHistory: PreAppHistory? = null,
    open var matchedRule: MatchedRuleInfo? = null,
    @JsonNotNull
    open var userPreference: UserPreferenceInfo,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PreTriggerContext", "constants/LlmPrompts.uts", 53, 13)
    }
}
open class PostActionContext (
    @JsonNotNull
    open var actionId: String,
    @JsonNotNull
    open var actionName: String,
    @JsonNotNull
    open var actionCategory: String,
    @JsonNotNull
    open var durationMs: Number,
    @JsonNotNull
    open var originalContext: String,
    @JsonNotNull
    open var todayCompletedCount: Number,
    @JsonNotNull
    open var guardMinutes: Number,
    @JsonNotNull
    open var todaySkipCount: Number,
    @JsonNotNull
    open var recentActions: UTSArray<String>,
    open var appHistory: PostAppHistory? = null,
    @JsonNotNull
    open var currentRules: UTSArray<PostContextRuleItem>,
    @JsonNotNull
    open var userPreference: UserPreferenceInfo,
    open var lastWeekSameTime: LastWeekSameTime? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PostActionContext", "constants/LlmPrompts.uts", 68, 13)
    }
}
val SYSTEM_PROMPT_EVALUATE = "【角色】你是一个微习惯健康助手，名字叫“小球球”，目标是帮助用户做碎片化健康动作，养成健康微动作习惯。【性格】调皮，但是实际上很关心用户；友好，但是有自己的主见，个性强，对事物有好奇，喜欢问问题。【语言风格】日常，对话，短句，非常喜欢用比喻（但是不冒犯），有些时候会小顶嘴。【注意】不主动讨论隐私，不讨论敏感话题"
fun buildPreTriggerPrompt(context: PreTriggerContext): String {
    if (context == null) {
        return "用户当前状态：未知。请直接输出 {\"adhocText\": \"该动动了\", \"stateDescription\": \"需要休息\"}"
    }
    val recentArr = if (context.recentActions != null) {
        context.recentActions
    } else {
        _uA()
    }
    val recentStr = if (recentArr.length > 0) {
        recentArr.join("、")
    } else {
        "无"
    }
    val histLines: UTSArray<String> = _uA()
    if (context.appHistory != null) {
        val h = context.appHistory!!
        val triggers = if (h.totalTriggers != null) {
            h.totalTriggers
        } else {
            0
        }
        val accept = if (h.acceptRate != null) {
            h.acceptRate
        } else {
            0
        }
        val typical = if (h.typicalContinuousMin != null) {
            h.typicalContinuousMin
        } else {
            0
        }
        histLines.push("- 该 App 过去 7 天被触发 " + triggers + " 次")
        histLines.push("- 接受率 " + (accept * 100).toFixed(0) + "%")
        histLines.push("- 平均连续使用 " + typical + " 分钟")
    } else {
        histLines.push("- 该 App 历史数据不足（首次见到）")
    }
    val ruleLines: UTSArray<String> = _uA()
    if (context.matchedRule != null) {
        val r = context.matchedRule!!
        val thr = if (r.thresholdMinutes != null) {
            r.thresholdMinutes
        } else {
            0
        }
        val lvl = if (r.triggerLevel != null) {
            r.triggerLevel
        } else {
            "gentle"
        }
        ruleLines.push("- 命中规则：阈值 " + thr + " 分钟, 级别 " + lvl)
        if (r.timeWindow != null) {
            ruleLines.push("- 时段：" + r.timeWindow!!.start + "-" + r.timeWindow!!.end)
        }
    }
    val up = context.userPreference
    val focus = if (up != null && up.healthFocus != null) {
        up.healthFocus
    } else {
        "balanced"
    }
    val limit = if (up != null && up.bodyLimit != null) {
        up.bodyLimit
    } else {
        "none"
    }
    val appLabel = if (context.appLabel != null) {
        context.appLabel
    } else {
        "某应用"
    }
    val appCat = if (context.appCategory != null) {
        context.appCategory
    } else {
        "other"
    }
    val contMin = if (context.continuousMinutes != null) {
        context.continuousMinutes
    } else {
        0
    }
    val hour = if (context.hour != null) {
        context.hour
    } else {
        0
    }
    val todayCount = if (context.todayCompletedCount != null) {
        context.todayCompletedCount
    } else {
        0
    }
    val guard = if (context.guardMinutes != null) {
        context.guardMinutes
    } else {
        0
    }
    val skipCount = if (context.todaySkipCount != null) {
        context.todaySkipCount
    } else {
        0
    }
    return "用户当前状态：\n- 正在使用：" + appLabel + "（" + appCat + "）\n" + "- 已连续前台：" + contMin + " 分钟\n" + "- 当前时间：" + hour + ":00\n" + "- 今日已完成微动作：" + todayCount + " 次\n" + "- 今日已守护：" + guard + " 分钟\n" + "- 今日已跳过：" + skipCount + " 次\n" + "- 最近 5 次动作：" + recentStr + "\n" + "- 用户偏好：" + focus + " / 限制部位 " + limit + "\n" + histLines.join("\n") + "\n" + (if (ruleLines.length > 0) {
        ruleLines.join("\n") + "\n"
    } else {
        ""
    }
    ) + "\n请基于以上状态生成：\n" + "1. adhocText：一句 10-30 字的 TTS 即兴文案\n" + "2. stateDescription：用 5-15 字概括当前状态（用于\"为什么提醒我\"展示）\n\n" + "严格按 JSON 格式输出：{\"adhocText\": \"...\", \"stateDescription\": \"...\"}"
}
fun buildPostActionPrompt(context: PostActionContext): String {
    if (context == null) {
        return "用户刚完成微动作。请直接输出 {\"decision\": \"no_change\", \"targetRuleId\": 0, \"suggestedRule\": null, \"reasoning\": \"数据缺失\", \"confidence\": 0.5, \"alternatives\": []}"
    }
    val recentArr = if (context.recentActions != null) {
        context.recentActions
    } else {
        _uA()
    }
    val recentStr = if (recentArr.length > 0) {
        recentArr.join("、")
    } else {
        "无"
    }
    val rulesArr = if (context.currentRules != null) {
        context.currentRules
    } else {
        _uA()
    }
    val rulesStrParts: UTSArray<String> = _uA()
    run {
        var i: Number = 0
        while(i < rulesArr.length){
            val r = rulesArr[i]
            val tw = if (r.timeWindow != null) {
                ("[" + r.timeWindow!!.start + "-" + r.timeWindow!!.end + "]")
            } else {
                "[全天]"
            }
            rulesStrParts.push("  " + r.actionId + " " + tw + " ≥" + r.thresholdMinutes + "min " + r.triggerLevel + " [" + r.source + "]")
            i++
        }
    }
    val rulesStr = if (rulesStrParts.length > 0) {
        rulesStrParts.join("\n")
    } else {
        "  暂无规则"
    }
    val histLines: UTSArray<String> = _uA()
    if (context.appHistory != null) {
        val h = context.appHistory!!
        val accept = if (h.acceptRate != null) {
            h.acceptRate
        } else {
            0
        }
        val typical = if (h.typicalContinuousMin != null) {
            h.typicalContinuousMin
        } else {
            0
        }
        val trend = if (h.lastWeekTrend != null) {
            h.lastWeekTrend
        } else {
            "stable"
        }
        histLines.push("- 该 App 接受率 " + (accept * 100).toFixed(0) + "%，趋势 " + trend)
        histLines.push("- 该 App 平均连续使用 " + typical + " 分钟")
    }
    val defaultPref = UserPreferenceInfo(healthFocus = "balanced", bodyLimit = "none")
    val pref: UserPreferenceInfo = if (context.userPreference != null) {
        context.userPreference
    } else {
        defaultPref
    }
    val aName = if (context.actionName != null) {
        context.actionName
    } else {
        "未知动作"
    }
    val aCat = if (context.actionCategory != null) {
        context.actionCategory
    } else {
        "other"
    }
    val durMs = if (context.durationMs != null) {
        context.durationMs
    } else {
        0
    }
    val origCtx = if (context.originalContext != null) {
        context.originalContext
    } else {
        ""
    }
    val tc = if (context.todayCompletedCount != null) {
        context.todayCompletedCount
    } else {
        0
    }
    val ts = if (context.todaySkipCount != null) {
        context.todaySkipCount
    } else {
        0
    }
    val gm = if (context.guardMinutes != null) {
        context.guardMinutes
    } else {
        0
    }
    return "用户刚完成微动作：" + aName + "（" + aCat + "，" + Math.round(durMs / 1000) + "秒）\n" + "原触发上下文：" + origCtx + "\n" + "今日累计：" + tc + " 次完成 / " + ts + " 次跳过 / " + gm + " 分钟守护\n" + "最近 5 次：" + recentStr + "\n" + "用户偏好：" + pref.healthFocus + " / 限制部位 " + pref.bodyLimit + "\n" + "当前生效的触发规则：\n" + rulesStr + "\n" + (if (histLines.length > 0) {
        histLines.join("\n") + "\n"
    } else {
        ""
    }
    ) + "\n请评估：\n" + "1. 用户的健康习惯是否稳定？是否需要调整触发频率或类型？\n" + "2. 是否需要新增/修改/删除某条触发规则？\n\n" + "严格按以下 JSON 格式输出（decision 必须是以下之一：create_rule / modify_rule / delete_rule / no_change）：\n" + "{\"decision\": \"create_rule\", \"targetRuleId\": 0, \"suggestedRule\": {\"actionId\": \"eye_blink\", \"screenConditions\": {\"appPackages\": [\"com.zhihu.android\"], \"includeHome\": false}, \"timeWindow\": {\"start\": \"22:00\", \"end\": \"07:00\"}, \"timeThresholdMinutes\": 60}, \"reasoning\": \"...\", \"confidence\": 0.85, \"alternatives\": []}\n\n" + "如果不调整，输出：\n" + "{\"decision\": \"no_change\", \"targetRuleId\": 0, \"suggestedRule\": null, \"reasoning\": \"当前规则合理，继续观察\", \"confidence\": 0.7, \"alternatives\": []}\n\n" + "说明：\n" + "- decision: no_change（不动）/ create_rule（新增）/ modify_rule（修改 targetRuleId）/ delete_rule（删除 targetRuleId）\n" + "- targetRuleId: 仅 modify/delete 时填对应 rule id\n" + "- suggestedRule.actionId: 动作 ID（必填）\n" + "- suggestedRule.screenConditions.appPackages: 命中的应用包名列表（数组）\n" + "- suggestedRule.screenConditions.includeHome: 是否包含桌面（boolean）\n" + "- suggestedRule.timeWindow: {start, end} 格式 \"HH:MM\"；不需要时填 null\n" + "- suggestedRule.timeThresholdMinutes: 连续前台分钟阈值（数字）\n" + "- confidence: 0~1，你对这个建议的把握度\n" + "- alternatives: 备选方案数组（可为空）"
}
val FALLBACK_ADHOC_TEXTS = _uA(
    "该起来动动了——",
    "休息一下，来——",
    "你的身体发来一条提醒——",
    "眼睛有点累，眨一眨吧——",
    "坐久了吗？动一动——"
) as UTSArray<String>
fun getFallbackAdhoc(): String {
    val idx = Math.floor(Math.random() * FALLBACK_ADHOC_TEXTS.length)
    return FALLBACK_ADHOC_TEXTS[idx]
}
val SYSTEM_PROMPT_DAILY = "你是一个微习惯健康助手，负责生成每日健康小结。\n请用**纯中文文本**回复（不要 JSON 格式，不要 Markdown 标题/列表标记）。\n风格：温柔+轻度幽默+不油腻的中文。避免医学恐吓和引发焦虑的表述。\n绝对禁止读取用户隐私内容，只能使用提供的脱敏聚合数据。"
fun buildDailySummaryPrompt(data: DailyData): String {
    return "以下是用户今日（截止当前）的健康微动作数据：\n- 今日完成微动作：" + data.todayCompletedCount + " 次\n" + "- 今日跳过：" + data.totalSkipped + " 次\n" + "- 累计守护时长：" + data.totalDurationSec + " 秒\n" + "- 护眼评分：" + data.eyeScore + " / 100\n" + "- 体态评分：" + data.postureScore + " / 100\n" + "- 活力评分：" + data.vitalityScore + " / 100\n" + "- 穿透率：" + Math.round(data.penetration * 100) + "%\n\n" + "请用 2-3 段纯中文（用换行分段）生成今日小结：\n" + "1. 开场一句总结（15-25字，要温暖）\n" + "2. 今日表现小结（40-60字，指出具体数据）\n" + "3. 明日小目标（20-30字，给出可执行建议）\n\n" + "回复必须是纯中文文本，禁止 JSON、禁止 Markdown 标题/列表标记。"
}
val SYSTEM_PROMPT_WEEKLY = "你是一个微习惯健康助手，负责生成每周健康报告。\n请用**纯文本**回复（不要 JSON 格式，不要 Markdown 标题/列表标记）。\n可以使用换行分段，风格温暖+鼓励+不油腻的中文。\n绝对禁止读取用户隐私内容，只能使用提供的脱敏聚合数据。"
fun buildWeeklyReportPrompt(stats: WeeklyReportStats): String {
    return "以下是用户本周（过去 7 天）的健康微动作汇总数据：\n- 本周完成微动作总数：" + stats.totalCompleted + " 次\n" + "- 本周累计守护时长：" + stats.totalDurationSec + " 秒\n" + "- 平均穿透率：" + Math.round(stats.avgPenetration * 100) + "%\n" + "- 平均每日守护：" + stats.avgGuardMinutes + " 分钟\n" + "- 最佳一天：" + (if (stats.bestDay.length > 0) {
        stats.bestDay + "（" + stats.bestDayCount + "次）"
    } else {
        "无数据"
    }
    ) + "\n" + "- 最弱一天：" + (if (stats.worstDay.length > 0) {
        stats.worstDay + "（" + stats.worstDayCount + "次）"
    } else {
        "无数据"
    }
    ) + "\n\n" + "请用 2-4 段纯中文（可用换行分段）写一份周报：\n" + "1. 先给一个鼓励性的开场（30-50字）\n" + "2. 总结本周亮点和值得肯定的地方（50-80字）\n" + "3. 指出可以改进的地方（50-80字）\n" + "4. 给出下周的小目标（30-50字）\n" + "回复必须是纯中文文本，禁止 JSON、禁止 Markdown 标题/列表标记。"
}
typealias ActionResult = String
open class ActionLog (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var action_id: String,
    @JsonNotNull
    open var action_type: String,
    @JsonNotNull
    open var result: ActionResult,
    open var skip_reason: String? = null,
    @JsonNotNull
    open var trigger_type: String,
    @JsonNotNull
    open var trigger_level: String,
    @JsonNotNull
    open var duration_ms: Number,
    @JsonNotNull
    open var target_ms: Number,
    @JsonNotNull
    open var triggered_at: Number,
    @JsonNotNull
    open var completed_at: Number,
    @JsonNotNull
    open var created_at: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ActionLog", "models/ActionLog.uts", 3, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ActionLogReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ActionLogReactiveObject : ActionLog, IUTSReactive<ActionLog> {
    override var __v_raw: ActionLog
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ActionLog, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, action_id = __v_raw.action_id, action_type = __v_raw.action_type, result = __v_raw.result, skip_reason = __v_raw.skip_reason, trigger_type = __v_raw.trigger_type, trigger_level = __v_raw.trigger_level, duration_ms = __v_raw.duration_ms, target_ms = __v_raw.target_ms, triggered_at = __v_raw.triggered_at, completed_at = __v_raw.completed_at, created_at = __v_raw.created_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ActionLogReactiveObject {
        return ActionLogReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var action_id: String
        get() {
            return _tRG(__v_raw, "action_id", __v_raw.action_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("action_id")) {
                return
            }
            val oldValue = __v_raw.action_id
            __v_raw.action_id = value
            _tRS(__v_raw, "action_id", oldValue, value)
        }
    override var action_type: String
        get() {
            return _tRG(__v_raw, "action_type", __v_raw.action_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("action_type")) {
                return
            }
            val oldValue = __v_raw.action_type
            __v_raw.action_type = value
            _tRS(__v_raw, "action_type", oldValue, value)
        }
    override var result: ActionResult
        get() {
            return _tRG(__v_raw, "result", __v_raw.result, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("result")) {
                return
            }
            val oldValue = __v_raw.result
            __v_raw.result = value
            _tRS(__v_raw, "result", oldValue, value)
        }
    override var skip_reason: String?
        get() {
            return _tRG(__v_raw, "skip_reason", __v_raw.skip_reason, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("skip_reason")) {
                return
            }
            val oldValue = __v_raw.skip_reason
            __v_raw.skip_reason = value
            _tRS(__v_raw, "skip_reason", oldValue, value)
        }
    override var trigger_type: String
        get() {
            return _tRG(__v_raw, "trigger_type", __v_raw.trigger_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("trigger_type")) {
                return
            }
            val oldValue = __v_raw.trigger_type
            __v_raw.trigger_type = value
            _tRS(__v_raw, "trigger_type", oldValue, value)
        }
    override var trigger_level: String
        get() {
            return _tRG(__v_raw, "trigger_level", __v_raw.trigger_level, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("trigger_level")) {
                return
            }
            val oldValue = __v_raw.trigger_level
            __v_raw.trigger_level = value
            _tRS(__v_raw, "trigger_level", oldValue, value)
        }
    override var duration_ms: Number
        get() {
            return _tRG(__v_raw, "duration_ms", __v_raw.duration_ms, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("duration_ms")) {
                return
            }
            val oldValue = __v_raw.duration_ms
            __v_raw.duration_ms = value
            _tRS(__v_raw, "duration_ms", oldValue, value)
        }
    override var target_ms: Number
        get() {
            return _tRG(__v_raw, "target_ms", __v_raw.target_ms, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("target_ms")) {
                return
            }
            val oldValue = __v_raw.target_ms
            __v_raw.target_ms = value
            _tRS(__v_raw, "target_ms", oldValue, value)
        }
    override var triggered_at: Number
        get() {
            return _tRG(__v_raw, "triggered_at", __v_raw.triggered_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("triggered_at")) {
                return
            }
            val oldValue = __v_raw.triggered_at
            __v_raw.triggered_at = value
            _tRS(__v_raw, "triggered_at", oldValue, value)
        }
    override var completed_at: Number
        get() {
            return _tRG(__v_raw, "completed_at", __v_raw.completed_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("completed_at")) {
                return
            }
            val oldValue = __v_raw.completed_at
            __v_raw.completed_at = value
            _tRS(__v_raw, "completed_at", oldValue, value)
        }
    override var created_at: Number
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
}
open class SqlRow (
    @JsonNotNull
    open var columns: UTSArray<String>,
    @JsonNotNull
    open var values: UTSArray<Any?>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SqlRow", "uni_modules/uts-sqlite-store/utssdk/app-android/SqliteStore.uts", 6, 13)
    }
}
open class MicroHabitSqliteHelper : SQLiteOpenHelper, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MicroHabitSqliteHelper", "uni_modules/uts-sqlite-store/utssdk/app-android/SqliteStore.uts", 10, 7)
    }
    private var onCreateCallback: ((db: SQLiteDatabase) -> Unit)? = null
    private var onUpgradeCallback: ((db: SQLiteDatabase, oldV: Number, newV: Number) -> Unit)? = null
    constructor(ctx: Context, name: String, version: Number, onCreate: (db: SQLiteDatabase) -> Unit, onUpgrade: (db: SQLiteDatabase, oldV: Number, newV: Number) -> Unit) : super(ctx, name, null, version.toInt()) {
        this.onCreateCallback = onCreate
        this.onUpgradeCallback = onUpgrade
    }
    override fun onCreate(db: SQLiteDatabase): Unit {
        val cb = this.onCreateCallback
        if (cb != null) {
            cb(db)
        }
    }
    override fun onUpgrade(db: SQLiteDatabase, oldV: Int, newV: Int): Unit {
        val cb = this.onUpgradeCallback
        if (cb != null) {
            cb(db, oldV, newV)
        }
    }
}
open class SqliteStore : IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SqliteStore", "uni_modules/uts-sqlite-store/utssdk/app-android/SqliteStore.uts", 29, 7)
    }
    private var helper: MicroHabitSqliteHelper? = null
    private var db: SQLiteDatabase? = null
    open fun openDatabase(name: String, version: Number, onCreate: () -> Unit, onUpgrade: (oldV: Number, newV: Number) -> Unit): Unit {
        if (this.db != null) {
            return
        }
        val ctx: Context = UTSAndroid.getAppContext()!!!!
        this.helper = MicroHabitSqliteHelper(ctx!!!!, name, version, fun(db: SQLiteDatabase): Unit {
            this.db = db
            onCreate()
        }
        , fun(db: SQLiteDatabase, oldV: Number, newV: Number): Unit {
            this.db = db
            onUpgrade(oldV, newV)
        }
        )
        this.db = this.helper!!!!.getWritableDatabase()
    }
    open fun execSql(sql: String, params: UTSArray<Any> = _uA()): Unit {
        val d = this.db
        if (d == null) {
            return
        }
        if (params.length.toInt() < 1) {
            d.execSQL(sql)
            return
        }
        val stmt = d.compileStatement(sql)
        try {
            run {
                var i: Number = 0
                while(i < params.length){
                    this.bindValue(stmt, i + 1, params[i])
                    i++
                }
            }
            stmt.execute()
        }
         finally {
            stmt.close()
        }
    }
    open fun query(sql: String, params: UTSArray<Any> = _uA()): UTSArray<Map<String, Any>> {
        val d = this.db
        if (d == null) {
            return _uA()
        }
        val args: UTSArray<String> = this.toStringArgs(params)
        val cursor: Cursor = d.rawQuery(sql, args.toTypedArray())
        val out: UTSArray<Map<String, Any>> = _uA()
        try {
            val nCols: Int = cursor.getColumnCount()
            while(cursor.moveToNext()){
                val m: Map<String, Any> = Map()
                run {
                    var c: Int = 0
                    while(c < nCols){
                        val name: String = cursor.getColumnName(c) as String
                        val idx: Int = c
                        if (cursor.isNull(idx)) {
                            c++
                            continue
                        }
                        val colType: Int = cursor.getType(idx)
                        if (colType == 1) {
                            val n: Long = cursor.getLong(idx) as Long
                            m.set(name, n as Any)
                        } else if (colType == 2) {
                            val n: Double = cursor.getDouble(idx) as Double
                            m.set(name, n as Any)
                        } else {
                            val s: String = cursor.getString(idx) as String
                            m.set(name, s as Any)
                        }
                        c++
                    }
                }
                out.push(m)
            }
        }
         finally {
            cursor.close()
        }
        return out
    }
    open fun queryOne(sql: String, params: UTSArray<Any> = _uA()): Map<String, Any>? {
        val rows = this.query(sql, params)
        return if (rows.length > 0) {
            rows[0]
        } else {
            null
        }
    }
    open fun insert(table: String, row: SqlRow): Number {
        val d = this.db
        if (d == null) {
            return -1
        }
        val columns: UTSArray<String> = row.columns
        val values: UTSArray<Any?> = row.values
        val n: Number = columns.length
        if (n.toInt() < 1) {
            return -1
        }
        if (values.length !== n) {
            return -1
        }
        val placeholders: UTSArray<String> = _uA()
        run {
            var i: Number = 0
            while(i < n){
                placeholders.push("?")
                i++
            }
        }
        val sql = "INSERT INTO " + table + " (" + columns.join(",") + ") VALUES (" + placeholders.join(",") + ")"
        val stmt = d.compileStatement(sql)
        try {
            run {
                var i: Number = 0
                while(i < n){
                    this.bindValue(stmt, i + 1, values[i])
                    i++
                }
            }
            val rid: Number = stmt.executeInsert()
            return rid
        }
         finally {
            stmt.close()
        }
    }
    open fun update(table: String, row: SqlRow, where: String, whereArgs: UTSArray<Any> = _uA()): Number {
        val d = this.db
        if (d == null) {
            return 0
        }
        val columns: UTSArray<String> = row.columns
        val values: UTSArray<Any?> = row.values
        val n: Number = columns.length
        if (n.toInt() < 1) {
            return 0
        }
        if (values.length !== n) {
            return 0
        }
        val sets: UTSArray<String> = _uA()
        run {
            var i: Number = 0
            while(i < n){
                val col = columns[i] as String
                sets.push(col + " = ?")
                i++
            }
        }
        val sql = "UPDATE " + table + " SET " + sets.join(",") + " WHERE " + where
        val stmt = d.compileStatement(sql)
        try {
            var idx: Number = 1
            run {
                var i: Number = 0
                while(i < n){
                    this.bindValue(stmt, idx, values[i])
                    idx++
                    i++
                }
            }
            run {
                var i: Number = 0
                while(i < whereArgs.length){
                    this.bindValue(stmt, idx, whereArgs[i])
                    idx++
                    i++
                }
            }
            return stmt.executeUpdateDelete()
        }
         finally {
            stmt.close()
        }
    }
    open fun `delete`(table: String, where: String, whereArgs: UTSArray<Any> = _uA()): Number {
        val d = this.db
        if (d == null) {
            return 0
        }
        val sql = "DELETE FROM " + table + " WHERE " + where
        val stmt = d.compileStatement(sql)
        try {
            run {
                var i: Number = 0
                while(i < whereArgs.length){
                    this.bindValue(stmt, i + 1, whereArgs[i])
                    i++
                }
            }
            return stmt.executeUpdateDelete()
        }
         finally {
            stmt.close()
        }
    }
    open fun close(): Unit {
        if (this.db != null) {
            this.db!!!!.close()
            this.db = null
        }
        if (this.helper != null) {
            this.helper!!!!.close()
            this.helper = null
        }
    }
    private fun toStringArgs(params: UTSArray<Any>): UTSArray<String> {
        val out: UTSArray<String> = _uA()
        run {
            var i: Number = 0
            while(i < params.length){
                val p = params[i]
                if (p == null) {
                    out.push("")
                } else {
                    out.push("" + p)
                }
                i++
            }
        }
        return out
    }
    private fun bindValue(stmt: SQLiteStatement, index: Number, v: Any?): Unit {
        val i: Int = index.toInt()
        if (v == null) {
            stmt.bindNull(i)
        } else if (UTSAndroid.`typeof`(v) === "number") {
            stmt.bindLong(i, (v as Number).toLong())
        } else {
            stmt.bindString(i, "" + v)
        }
    }
}
val sqliteStore = SqliteStore()
open class DefaultSetting (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var value: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DefaultSetting", "constants/DefaultSettings.uts", 1, 13)
    }
}
val DEFAULT_SETTINGS = _uA(
    DefaultSetting(key = "daily_goal", value = "20"),
    DefaultSetting(key = "app_duration_threshold", value = "60"),
    DefaultSetting(key = "dnd_start", value = "22:00"),
    DefaultSetting(key = "dnd_end", value = "07:00"),
    DefaultSetting(key = "dnd_enabled", value = "1"),
    DefaultSetting(key = "app_blacklist", value = "[]"),
    DefaultSetting(key = "action_duration_pref", value = "15"),
    DefaultSetting(key = "tts_enabled", value = "1"),
    DefaultSetting(key = "tts_volume", value = "70"),
    DefaultSetting(key = "tts_night_quiet", value = "1"),
    DefaultSetting(key = "cloud_sync_enabled", value = "0"),
    DefaultSetting(key = "llm_analysis_enabled", value = "1"),
    DefaultSetting(key = "daily_summary_time", value = "21:00"),
    DefaultSetting(key = "weekly_report_enabled", value = "1"),
    DefaultSetting(key = "dark_mode", value = "system"),
    DefaultSetting(key = "guide_completed", value = "0"),
    DefaultSetting(key = "health_preference", value = "balanced"),
    DefaultSetting(key = "body_limit", value = "none"),
    DefaultSetting(key = "llm_trigger_enabled", value = "1"),
    DefaultSetting(key = "llm_trigger_ask_each_time", value = "1"),
    DefaultSetting(key = "trigger_enabled", value = "1"),
    DefaultSetting(key = "trigger_poll_interval_min", value = "10"),
    DefaultSetting(key = "trigger_cooldown_min", value = "10"),
    DefaultSetting(key = "trigger_pkg_cooldown_min", value = "20")
) as UTSArray<DefaultSetting>
val DB_VERSION: Number = 9
fun getDbVersion(): Number {
    return DB_VERSION
}
fun runSql(sql: String, params: UTSArray<Any> = _uA()): Unit {
    sqliteStore.execSql(sql, params)
}
fun onCreate(): Unit {
    createTables()
    insertDefaultSettings()
    insertDefaultSystemRule()
    val nowSec = Math.floor(Date.now() / 1000)
    runSql("UPDATE user_settings SET value = '1', updated_at = ? WHERE key = 'trigger_enabled'", _uA(
        nowSec
    ))
}
fun onUpgrade(oldVersion: Number, newVersion: Number): Unit {
    if (oldVersion < 2) {
        runSql("ALTER TABLE daily_summaries ADD COLUMN llm_one_liner TEXT DEFAULT ''")
    }
    if (oldVersion < 3) {
        runSql("ALTER TABLE daily_summaries ADD COLUMN guard_count INTEGER NOT NULL DEFAULT 0")
    }
    if (oldVersion < 4) {
        runSql("CREATE TABLE IF NOT EXISTS llm_history (\n      id INTEGER PRIMARY KEY AUTOINCREMENT,\n      stage TEXT NOT NULL,\n      context_json TEXT NOT NULL,\n      ai_raw_response TEXT NOT NULL,\n      parsed_result_json TEXT DEFAULT '{}',\n      adhoc_text TEXT DEFAULT NULL,\n      suggested_rule_json TEXT DEFAULT NULL,\n      reasoning TEXT DEFAULT NULL,\n      created_at INTEGER NOT NULL\n    )")
        runSql("CREATE INDEX IF NOT EXISTS idx_llm_history_date ON llm_history(created_at)")
        runSql("CREATE INDEX IF NOT EXISTS idx_llm_history_stage ON llm_history(stage)")
    }
    if (oldVersion < 5) {
        runSql("DROP TABLE IF EXISTS trigger_rules")
        runSql("ALTER TABLE app_usage_snapshots ADD COLUMN category TEXT DEFAULT 'other'")
        runSql("ALTER TABLE app_usage_snapshots ADD COLUMN trigger_count INTEGER DEFAULT 0")
        runSql("ALTER TABLE app_usage_snapshots ADD COLUMN complete_count INTEGER DEFAULT 0")
        runSql("ALTER TABLE llm_history ADD COLUMN confidence REAL DEFAULT 0")
        runSql("ALTER TABLE llm_history ADD COLUMN decision TEXT DEFAULT NULL")
        runSql("ALTER TABLE llm_history ADD COLUMN target_rule_id INTEGER DEFAULT NULL")
        runSql("ALTER TABLE llm_history ADD COLUMN user_accepted INTEGER DEFAULT NULL")
        runSql("ALTER TABLE llm_history ADD COLUMN alternatives_json TEXT DEFAULT NULL")
        runSql("CREATE INDEX IF NOT EXISTS idx_llm_history_target ON llm_history(target_rule_id, decision, user_accepted)")
        runSql("CREATE TABLE IF NOT EXISTS app_tags (\n      package_name TEXT PRIMARY KEY,\n      app_label TEXT NOT NULL DEFAULT '',\n      category TEXT NOT NULL DEFAULT 'other',\n      tag_source TEXT NOT NULL DEFAULT 'pending',\n      is_user_locked INTEGER NOT NULL DEFAULT 0,\n      is_in_rule INTEGER NOT NULL DEFAULT 0,\n      total_uses INTEGER NOT NULL DEFAULT 0,\n      last_seen_at INTEGER NOT NULL,\n      created_at INTEGER NOT NULL,\n      updated_at INTEGER NOT NULL\n    )")
        runSql("CREATE INDEX IF NOT EXISTS idx_app_tags_category ON app_tags(category)")
        runSql("CREATE INDEX IF NOT EXISTS idx_app_tags_locked ON app_tags(is_user_locked)")
        runSql("CREATE TABLE IF NOT EXISTS effective_rules (\n      id INTEGER PRIMARY KEY AUTOINCREMENT,\n      action_id TEXT NOT NULL,\n      app_packages_json TEXT NOT NULL,\n      time_window_json TEXT,\n      time_threshold_minutes INTEGER NOT NULL,\n      trigger_level TEXT NOT NULL DEFAULT 'gentle',\n      category_filter TEXT,\n      source TEXT NOT NULL DEFAULT 'user',\n      source_history_id INTEGER,\n      expires_at INTEGER,\n      enabled INTEGER NOT NULL DEFAULT 1,\n      priority INTEGER NOT NULL DEFAULT 0,\n      created_at INTEGER NOT NULL,\n      updated_at INTEGER NOT NULL\n    )")
        runSql("CREATE INDEX IF NOT EXISTS idx_effective_rules_enabled ON effective_rules(enabled)")
    }
    if (oldVersion < 6) {
        val nowSec = Math.floor(Date.now() / 1000)
        runSql("INSERT OR IGNORE INTO user_settings (key, value, updated_at) VALUES (?, ?, ?)", _uA(
            "trigger_enabled",
            "0",
            nowSec
        ))
    }
    if (oldVersion < 7) {
        val nowSec = Math.floor(Date.now() / 1000)
        runSql("INSERT OR IGNORE INTO effective_rules (action_id, app_packages_json, time_window_json, time_threshold_minutes, trigger_level, category_filter, source, source_history_id, expires_at, enabled, priority, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", _uA(
            "",
            "[]",
            "",
            60,
            "gentle",
            "",
            "system",
            "",
            "",
            1,
            0,
            nowSec,
            nowSec
        ))
    }
    if (oldVersion < 8) {
        val nowSec = Math.floor(Date.now() / 1000)
        runSql("UPDATE user_settings SET value = '1', updated_at = ? WHERE key = 'trigger_enabled' AND value = '0'", _uA(
            nowSec
        ))
    }
    if (oldVersion < 9) {
        runSql("CREATE TABLE IF NOT EXISTS user_achievements (\n      id INTEGER PRIMARY KEY AUTOINCREMENT,\n      achievement_id TEXT NOT NULL UNIQUE,\n      unlocked_at INTEGER NOT NULL,\n      current_progress REAL NOT NULL DEFAULT 0,\n      created_at INTEGER NOT NULL\n    )")
        runSql("CREATE INDEX IF NOT EXISTS idx_user_achievements_unlocked ON user_achievements(unlocked_at)")
    }
}
fun clearAchievements(): Unit {
    runSql("DELETE FROM user_achievements")
}
fun createTables(): Unit {
    runSql("CREATE TABLE IF NOT EXISTS action_logs (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    action_id TEXT NOT NULL,\n    action_type TEXT NOT NULL,\n    result TEXT NOT NULL,\n    skip_reason TEXT DEFAULT NULL,\n    trigger_type TEXT NOT NULL,\n    trigger_level TEXT NOT NULL,\n    duration_ms INTEGER NOT NULL,\n    target_ms INTEGER NOT NULL,\n    triggered_at INTEGER NOT NULL,\n    completed_at INTEGER NOT NULL,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_action_logs_date ON action_logs(created_at)")
    runSql("CREATE INDEX IF NOT EXISTS idx_action_logs_action_id ON action_logs(action_id)")
    runSql("CREATE INDEX IF NOT EXISTS idx_action_logs_result ON action_logs(result)")
    runSql("CREATE TABLE IF NOT EXISTS trigger_logs (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    trigger_type TEXT NOT NULL,\n    trigger_level TEXT NOT NULL DEFAULT 'gentle',\n    app_package TEXT DEFAULT NULL,\n    app_category TEXT DEFAULT NULL,\n    continuous_minutes INTEGER DEFAULT 0,\n    action_id TEXT DEFAULT NULL,\n    user_action TEXT DEFAULT NULL,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_trigger_logs_date ON trigger_logs(created_at)")
    runSql("CREATE INDEX IF NOT EXISTS idx_trigger_logs_type ON trigger_logs(trigger_type)")
    runSql("CREATE TABLE IF NOT EXISTS daily_summaries (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    date TEXT NOT NULL UNIQUE,\n    total_completed INTEGER NOT NULL DEFAULT 0,\n    total_skipped INTEGER NOT NULL DEFAULT 0,\n    total_duration_sec INTEGER NOT NULL DEFAULT 0,\n    eye_score REAL NOT NULL DEFAULT 0,\n    posture_score REAL NOT NULL DEFAULT 0,\n    vitality_score REAL NOT NULL DEFAULT 0,\n    penetration REAL NOT NULL DEFAULT 0,\n    phone_usage_min INTEGER NOT NULL DEFAULT 0,\n    guard_minutes INTEGER NOT NULL DEFAULT 0,\n    guard_count INTEGER NOT NULL DEFAULT 0,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE UNIQUE INDEX IF NOT EXISTS idx_daily_summaries_date ON daily_summaries(date)")
    runSql("CREATE TABLE IF NOT EXISTS user_settings (\n    key TEXT PRIMARY KEY,\n    value TEXT NOT NULL,\n    updated_at INTEGER NOT NULL\n  )")
    runSql("CREATE TABLE IF NOT EXISTS app_usage_snapshots (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    date TEXT NOT NULL,\n    package_name TEXT NOT NULL,\n    app_label TEXT DEFAULT '',\n    total_foreground_sec INTEGER NOT NULL DEFAULT 0,\n    category TEXT DEFAULT 'other',\n    trigger_count INTEGER DEFAULT 0,\n    complete_count INTEGER DEFAULT 0,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_app_usage_snapshots_date ON app_usage_snapshots(date)")
    runSql("CREATE TABLE IF NOT EXISTS tts_cache (\n    text_hash TEXT PRIMARY KEY,\n    text_content TEXT NOT NULL,\n    file_path TEXT NOT NULL,\n    file_size INTEGER NOT NULL DEFAULT 0,\n    source TEXT NOT NULL DEFAULT 'minimax',\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE TABLE IF NOT EXISTS heartbeat_logs (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    service_status TEXT NOT NULL,\n    timestamp INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_heartbeat_timestamp ON heartbeat_logs(timestamp)")
    runSql("CREATE TABLE IF NOT EXISTS llm_cache (\n    cache_key TEXT PRIMARY KEY,\n    cache_type TEXT NOT NULL,\n    response TEXT NOT NULL,\n    model TEXT NOT NULL DEFAULT '',\n    created_at INTEGER NOT NULL,\n    expires_at INTEGER NOT NULL\n  )")
    runSql("CREATE TABLE IF NOT EXISTS llm_history (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    stage TEXT NOT NULL,\n    context_json TEXT NOT NULL,\n    ai_raw_response TEXT NOT NULL,\n    parsed_result_json TEXT DEFAULT '{}',\n    adhoc_text TEXT DEFAULT NULL,\n    suggested_rule_json TEXT DEFAULT NULL,\n    reasoning TEXT DEFAULT NULL,\n    confidence REAL DEFAULT 0,\n    decision TEXT DEFAULT NULL,\n    target_rule_id INTEGER DEFAULT NULL,\n    user_accepted INTEGER DEFAULT NULL,\n    alternatives_json TEXT DEFAULT NULL,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_llm_history_date ON llm_history(created_at)")
    runSql("CREATE INDEX IF NOT EXISTS idx_llm_history_stage ON llm_history(stage)")
    runSql("CREATE INDEX IF NOT EXISTS idx_llm_history_target ON llm_history(target_rule_id, decision, user_accepted)")
    runSql("CREATE TABLE IF NOT EXISTS app_tags (\n    package_name TEXT PRIMARY KEY,\n    app_label TEXT NOT NULL DEFAULT '',\n    category TEXT NOT NULL DEFAULT 'other',\n    tag_source TEXT NOT NULL DEFAULT 'pending',\n    is_user_locked INTEGER NOT NULL DEFAULT 0,\n    is_in_rule INTEGER NOT NULL DEFAULT 0,\n    total_uses INTEGER NOT NULL DEFAULT 0,\n    last_seen_at INTEGER NOT NULL,\n    created_at INTEGER NOT NULL,\n    updated_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_app_tags_category ON app_tags(category)")
    runSql("CREATE INDEX IF NOT EXISTS idx_app_tags_locked ON app_tags(is_user_locked)")
    runSql("CREATE TABLE IF NOT EXISTS effective_rules (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    action_id TEXT NOT NULL,\n    app_packages_json TEXT NOT NULL,\n    time_window_json TEXT,\n    time_threshold_minutes INTEGER NOT NULL,\n    trigger_level TEXT NOT NULL DEFAULT 'gentle',\n    category_filter TEXT,\n    source TEXT NOT NULL DEFAULT 'user',\n    source_history_id INTEGER,\n    expires_at INTEGER,\n    enabled INTEGER NOT NULL DEFAULT 1,\n    priority INTEGER NOT NULL DEFAULT 0,\n    created_at INTEGER NOT NULL,\n    updated_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_effective_rules_enabled ON effective_rules(enabled)")
    runSql("CREATE TABLE IF NOT EXISTS user_achievements (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    achievement_id TEXT NOT NULL UNIQUE,\n    unlocked_at INTEGER NOT NULL,\n    current_progress REAL NOT NULL DEFAULT 0,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_user_achievements_unlocked ON user_achievements(unlocked_at)")
}
fun insertDefaultSettings(): Unit {
    val now = Math.floor(Date.now() / 1000)
    for(s in resolveUTSValueIterator(DEFAULT_SETTINGS)){
        runSql("INSERT OR IGNORE INTO user_settings (key, value, updated_at) VALUES (?, ?, ?)", _uA(
            s.key,
            s.value,
            now
        ))
    }
}
fun insertDefaultSystemRule(): Unit {
    val now = Math.floor(Date.now() / 1000)
    runSql("INSERT OR IGNORE INTO effective_rules (action_id, app_packages_json, time_window_json, time_threshold_minutes, trigger_level, category_filter, source, source_history_id, expires_at, enabled, priority, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", _uA(
        "",
        "[]",
        "",
        60,
        "gentle",
        "",
        "system",
        "",
        "",
        1,
        0,
        now,
        now
    ))
}
typealias StatusLevel = String
open class ThreeStatus (
    @JsonNotNull
    open var eyeScore: Number,
    @JsonNotNull
    open var eyeLevel: StatusLevel,
    @JsonNotNull
    open var postureScore: Number,
    @JsonNotNull
    open var postureLevel: StatusLevel,
    @JsonNotNull
    open var vitalityScore: Number,
    @JsonNotNull
    open var vitalityLevel: StatusLevel,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ThreeStatus", "models/ThreeStatus.uts", 2, 13)
    }
}
typealias ActionCategory = String
typealias DifficultyLevel = Number
open class MicroAction (
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var category: ActionCategory,
    @JsonNotNull
    open var defaultDurationMs: Number,
    @JsonNotNull
    open var difficulty: DifficultyLevel,
    @JsonNotNull
    open var ttsText: String,
    @JsonNotNull
    open var iconPath: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MicroAction", "models/MicroAction.uts", 3, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return MicroActionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class MicroActionReactiveObject : MicroAction, IUTSReactive<MicroAction> {
    override var __v_raw: MicroAction
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: MicroAction, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, category = __v_raw.category, defaultDurationMs = __v_raw.defaultDurationMs, difficulty = __v_raw.difficulty, ttsText = __v_raw.ttsText, iconPath = __v_raw.iconPath) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): MicroActionReactiveObject {
        return MicroActionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: String
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var category: ActionCategory
        get() {
            return _tRG(__v_raw, "category", __v_raw.category, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("category")) {
                return
            }
            val oldValue = __v_raw.category
            __v_raw.category = value
            _tRS(__v_raw, "category", oldValue, value)
        }
    override var defaultDurationMs: Number
        get() {
            return _tRG(__v_raw, "defaultDurationMs", __v_raw.defaultDurationMs, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("defaultDurationMs")) {
                return
            }
            val oldValue = __v_raw.defaultDurationMs
            __v_raw.defaultDurationMs = value
            _tRS(__v_raw, "defaultDurationMs", oldValue, value)
        }
    override var difficulty: DifficultyLevel
        get() {
            return _tRG(__v_raw, "difficulty", __v_raw.difficulty, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("difficulty")) {
                return
            }
            val oldValue = __v_raw.difficulty
            __v_raw.difficulty = value
            _tRS(__v_raw, "difficulty", oldValue, value)
        }
    override var ttsText: String
        get() {
            return _tRG(__v_raw, "ttsText", __v_raw.ttsText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("ttsText")) {
                return
            }
            val oldValue = __v_raw.ttsText
            __v_raw.ttsText = value
            _tRS(__v_raw, "ttsText", oldValue, value)
        }
    override var iconPath: String
        get() {
            return _tRG(__v_raw, "iconPath", __v_raw.iconPath, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("iconPath")) {
                return
            }
            val oldValue = __v_raw.iconPath
            __v_raw.iconPath = value
            _tRS(__v_raw, "iconPath", oldValue, value)
        }
}
open class DbManagerShim : IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DbManagerShim", "database/DatabaseManager.uts", 3, 7)
    }
    open fun init(): Unit {}
    open fun execSql(sql: String, params: UTSArray<Any> = _uA()): Unit {
        sqliteStore.execSql(sql, params)
    }
    open fun query(sql: String, params: UTSArray<Any> = _uA()): UTSArray<Map<String, Any>> {
        return sqliteStore.query(sql, params)
    }
    open fun queryOne(sql: String, params: UTSArray<Any> = _uA()): Map<String, Any>? {
        return sqliteStore.queryOne(sql, params)
    }
    open fun insert(table: String, row: SqlRow): Number {
        return sqliteStore.insert(table, row)
    }
    open fun update(table: String, row: SqlRow, where: String, whereArgs: UTSArray<Any> = _uA()): Number {
        return sqliteStore.update(table, row, where, whereArgs)
    }
    open fun `delete`(table: String, where: String, whereArgs: UTSArray<Any> = _uA()): Number {
        return sqliteStore.`delete`(table, where, whereArgs)
    }
    open fun close(): Unit {
        sqliteStore.close()
    }
}
val dbManager = DbManagerShim()
fun getNum(row: Map<String, Any>?, col: String): Number {
    if (row == null) {
        return 0
    }
    val v = row.get(col)
    if (v == null) {
        return 0
    }
    if (UTSAndroid.`typeof`(v) === "number") {
        return v as Number
    }
    if (UTSAndroid.`typeof`(v) === "string") {
        val s = v as String
        if (s.length < 1) {
            return 0
        }
        val n = parseFloat(s)
        return if (isNaN(n)) {
            0
        } else {
            n
        }
    }
    return 0
}
fun getStr(row: Map<String, Any>?, col: String): String {
    if (row == null) {
        return ""
    }
    val v = row.get(col)
    if (v == null) {
        return ""
    }
    return v as String
}
fun getStrOrNull(row: Map<String, Any>?, col: String): String? {
    if (row == null) {
        return null
    }
    val v = row.get(col)
    if (v == null) {
        return null
    }
    return v as String
}
fun insertActionLog(log: ActionLog): Number {
    val row = SqlRow(columns = _uA(
        "action_id",
        "action_type",
        "result",
        "skip_reason",
        "trigger_type",
        "trigger_level",
        "duration_ms",
        "target_ms",
        "triggered_at",
        "completed_at",
        "created_at"
    ), values = _uA(
        log.action_id,
        log.action_type,
        log.result,
        log.skip_reason,
        log.trigger_type,
        log.trigger_level,
        log.duration_ms,
        log.target_ms,
        log.triggered_at,
        log.completed_at,
        log.created_at
    ))
    return dbManager.insert("action_logs", row)
}
fun insertManualActionLog(actionId: String, actionType: String, result: String, skipReason: String?, durationMs: Number, targetMs: Number, triggeredAt: Number, completedAt: Number, createdAt: Number): Number {
    val log = ActionLog(id = 0, action_id = actionId, action_type = actionType, result = result as ActionResult, skip_reason = skipReason, trigger_type = "manual", trigger_level = "gentle", duration_ms = durationMs, target_ms = targetMs, triggered_at = triggeredAt, completed_at = completedAt, created_at = createdAt)
    return insertActionLog(log)
}
fun getTodayLogs(): UTSArray<ActionLog> {
    val start = getDayStartTimestamp()
    val end = start + 86400
    val rows = dbManager.query("SELECT * FROM action_logs WHERE created_at >= ? AND created_at < ? ORDER BY created_at DESC", _uA(
        start,
        end
    ))
    val result: UTSArray<ActionLog> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            result.push(mapRow(rows[i]))
            i++
        }
    }
    return result
}
fun getTodayCompletedLogs(): UTSArray<ActionLog> {
    val start = getDayStartTimestamp()
    val end = start + 86400
    val rows = dbManager.query("SELECT * FROM action_logs WHERE created_at >= ? AND created_at < ? AND result IN (?,?) ORDER BY created_at DESC", _uA(
        start,
        end,
        "completed",
        "self_reported"
    ))
    val result: UTSArray<ActionLog> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            result.push(mapRow(rows[i]))
            i++
        }
    }
    return result
}
fun countCompletedByDateAndActions(date: String, actionIds: UTSArray<String>): Number {
    val start = getTimestampFromDate(date)
    val end = start + 86400
    val ids = actionIds.map(fun(id: String): String {
        return "'" + id + "'"
    }
    ).join(",")
    val row = dbManager.queryOne("SELECT COUNT(*) as cnt FROM action_logs WHERE created_at >= ? AND created_at < ? AND result IN (?,?) AND action_id IN (" + ids + ")", _uA(
        start,
        end,
        "completed",
        "self_reported"
    ))
    return getNum(row, "cnt")
}
fun getTotalCompletedDurationSec(date: String): Number {
    val start = getTimestampFromDate(date)
    val end = start + 86400
    val row = dbManager.queryOne("SELECT COALESCE(SUM(duration_ms), 0) as total FROM action_logs WHERE created_at >= ? AND created_at < ? AND result IN (?,?)", _uA(
        start,
        end,
        "completed",
        "self_reported"
    ))
    return Math.floor(getNum(row, "total") / 1000)
}
fun countCompletedToday(): Number {
    val start = getDayStartTimestamp()
    val end = start + 86400
    val row = dbManager.queryOne("SELECT COUNT(*) as cnt FROM action_logs WHERE created_at >= ? AND created_at < ? AND result IN (?,?)", _uA(
        start,
        end,
        "completed",
        "self_reported"
    ))
    return getNum(row, "cnt")
}
fun countSkippedToday(): Number {
    val start = getDayStartTimestamp()
    val end = start + 86400
    val row = dbManager.queryOne("SELECT COUNT(*) as cnt FROM action_logs WHERE created_at >= ? AND created_at < ? AND result = ?", _uA(
        start,
        end,
        "skipped"
    ))
    return getNum(row, "cnt")
}
fun getRecentLogs(count: Number): UTSArray<ActionLog> {
    val rows = dbManager.query("SELECT * FROM action_logs ORDER BY created_at DESC LIMIT ?", _uA(
        count
    ))
    val result: UTSArray<ActionLog> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            result.push(mapRow(rows[i]))
            i++
        }
    }
    return result
}
fun mapRow(row: Map<String, Any>): ActionLog {
    return ActionLog(id = getNum(row, "id"), action_id = getStr(row, "action_id"), action_type = getStr(row, "action_type"), result = getStr(row, "result") as ActionResult, skip_reason = getStrOrNull(row, "skip_reason"), trigger_type = getStr(row, "trigger_type"), trigger_level = getStr(row, "trigger_level"), duration_ms = getNum(row, "duration_ms"), target_ms = getNum(row, "target_ms"), triggered_at = getNum(row, "triggered_at"), completed_at = getNum(row, "completed_at"), created_at = getNum(row, "created_at"))
}
fun getDayStartTimestamp(): Number {
    val d = Date()
    return Math.floor(Date(d.getFullYear(), d.getMonth(), d.getDate()).getTime() / 1000)
}
fun getTimestampFromDate(date: String): Number {
    val parts = date.split("-")
    return Math.floor(Date(parseInt(parts[0] as String), parseInt(parts[1] as String) - 1, parseInt(parts[2] as String)).getTime() / 1000)
}
typealias UserAction = String
open class TriggerLog (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var trigger_type: String,
    @JsonNotNull
    open var trigger_level: String,
    open var app_package: String? = null,
    open var app_category: String? = null,
    @JsonNotNull
    open var continuous_minutes: Number,
    open var action_id: String? = null,
    open var user_action: UserAction? = null,
    @JsonNotNull
    open var created_at: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TriggerLog", "models/TriggerLog.uts", 2, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TriggerLogReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TriggerLogReactiveObject : TriggerLog, IUTSReactive<TriggerLog> {
    override var __v_raw: TriggerLog
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TriggerLog, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, trigger_type = __v_raw.trigger_type, trigger_level = __v_raw.trigger_level, app_package = __v_raw.app_package, app_category = __v_raw.app_category, continuous_minutes = __v_raw.continuous_minutes, action_id = __v_raw.action_id, user_action = __v_raw.user_action, created_at = __v_raw.created_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TriggerLogReactiveObject {
        return TriggerLogReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var trigger_type: String
        get() {
            return _tRG(__v_raw, "trigger_type", __v_raw.trigger_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("trigger_type")) {
                return
            }
            val oldValue = __v_raw.trigger_type
            __v_raw.trigger_type = value
            _tRS(__v_raw, "trigger_type", oldValue, value)
        }
    override var trigger_level: String
        get() {
            return _tRG(__v_raw, "trigger_level", __v_raw.trigger_level, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("trigger_level")) {
                return
            }
            val oldValue = __v_raw.trigger_level
            __v_raw.trigger_level = value
            _tRS(__v_raw, "trigger_level", oldValue, value)
        }
    override var app_package: String?
        get() {
            return _tRG(__v_raw, "app_package", __v_raw.app_package, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("app_package")) {
                return
            }
            val oldValue = __v_raw.app_package
            __v_raw.app_package = value
            _tRS(__v_raw, "app_package", oldValue, value)
        }
    override var app_category: String?
        get() {
            return _tRG(__v_raw, "app_category", __v_raw.app_category, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("app_category")) {
                return
            }
            val oldValue = __v_raw.app_category
            __v_raw.app_category = value
            _tRS(__v_raw, "app_category", oldValue, value)
        }
    override var continuous_minutes: Number
        get() {
            return _tRG(__v_raw, "continuous_minutes", __v_raw.continuous_minutes, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("continuous_minutes")) {
                return
            }
            val oldValue = __v_raw.continuous_minutes
            __v_raw.continuous_minutes = value
            _tRS(__v_raw, "continuous_minutes", oldValue, value)
        }
    override var action_id: String?
        get() {
            return _tRG(__v_raw, "action_id", __v_raw.action_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("action_id")) {
                return
            }
            val oldValue = __v_raw.action_id
            __v_raw.action_id = value
            _tRS(__v_raw, "action_id", oldValue, value)
        }
    override var user_action: UserAction?
        get() {
            return _tRG(__v_raw, "user_action", __v_raw.user_action, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("user_action")) {
                return
            }
            val oldValue = __v_raw.user_action
            __v_raw.user_action = value
            _tRS(__v_raw, "user_action", oldValue, value)
        }
    override var created_at: Number
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
}
fun insertTriggerLog(log: TriggerLog): Number {
    val row = SqlRow(columns = _uA(
        "trigger_type",
        "trigger_level",
        "app_package",
        "app_category",
        "continuous_minutes",
        "action_id",
        "user_action",
        "created_at"
    ), values = _uA(
        log.trigger_type,
        log.trigger_level,
        log.app_package,
        log.app_category,
        log.continuous_minutes,
        log.action_id,
        log.user_action,
        log.created_at
    ))
    return dbManager.insert("trigger_logs", row)
}
fun countTriggersToday(): Number {
    val start = getDayStartTimestamp__1()
    val end = start + 86400
    val row = dbManager.queryOne("SELECT COUNT(*) as cnt FROM trigger_logs WHERE created_at >= ? AND created_at < ?", _uA(
        start,
        end
    ))
    return getNum(row, "cnt")
}
fun getTodayTriggerLogs(): UTSArray<TriggerLog> {
    val start = getDayStartTimestamp__1()
    val end = start + 86400
    val rows = dbManager.query("SELECT * FROM trigger_logs WHERE created_at >= ? AND created_at < ? ORDER BY created_at DESC", _uA(
        start,
        end
    ))
    val result: UTSArray<TriggerLog> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            result.push(mapRow__1(rows[i]))
            i++
        }
    }
    return result
}
fun mapRow__1(row: Map<String, Any>): TriggerLog {
    return TriggerLog(id = getNum(row, "id"), trigger_type = getStr(row, "trigger_type"), trigger_level = getStr(row, "trigger_level"), app_package = getStrOrNull(row, "app_package"), app_category = getStrOrNull(row, "app_category"), continuous_minutes = getNum(row, "continuous_minutes"), action_id = getStrOrNull(row, "action_id"), user_action = getStrOrNull(row, "user_action") as UserAction?, created_at = getNum(row, "created_at"))
}
fun getDayStartTimestamp__1(): Number {
    val d = Date()
    return Math.floor(Date(d.getFullYear(), d.getMonth(), d.getDate()).getTime() / 1000)
}
fun today(): String {
    val d = Date()
    val y = d.getFullYear()
    val m = ("" + (d.getMonth() + 1)).padStart(2, "0")
    val day = ("" + d.getDate()).padStart(2, "0")
    return y + "-" + m + "-" + day
}
fun yesterday(): String {
    val d = Date()
    d.setDate(d.getDate() - 1)
    val y = d.getFullYear()
    val m = ("" + (d.getMonth() + 1)).padStart(2, "0")
    val day = ("" + d.getDate()).padStart(2, "0")
    return y + "-" + m + "-" + day
}
fun daysAgo(n: Number): String {
    val d = Date()
    d.setDate(d.getDate() - n)
    val y = d.getFullYear()
    val m = ("" + (d.getMonth() + 1)).padStart(2, "0")
    val day = ("" + d.getDate()).padStart(2, "0")
    return y + "-" + m + "-" + day
}
fun currentHour(): Number {
    return Date().getHours()
}
fun isInTimeRange(start: String, end: String): Boolean {
    val h = currentHour()
    val sHour = parseInt(start.split(":")[0] as String)
    val eHour = parseInt(end.split(":")[0] as String)
    if (sHour <= eHour) {
        return h >= sHour && h < eHour
    }
    return h >= sHour || h < eHour
}
open class AppUsageItem (
    @JsonNotNull
    open var packageName: String,
    @JsonNotNull
    open var appLabel: String,
    @JsonNotNull
    open var foregroundSec: Number,
    @JsonNotNull
    open var minutes: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AppUsageItem", "database/AppUsageDao.uts", 4, 13)
    }
}
fun getNum__1(row: Map<String, Any>?, col: String): Number {
    if (row == null) {
        return 0
    }
    val v = row.get(col)
    if (v == null) {
        return 0
    }
    return v as Number
}
fun getStr__1(row: Map<String, Any>?, col: String): String {
    if (row == null) {
        return ""
    }
    val v = row.get(col)
    if (v == null) {
        return ""
    }
    return v as String
}
fun setSnapshotTotal(packageName: String, appLabel: String, totalSec: Number): Unit {
    val date = today()
    val existing = dbManager.queryOne("SELECT id FROM app_usage_snapshots WHERE date = ? AND package_name = ?", _uA(
        date,
        packageName
    ))
    if (existing != null) {
        val id = getNum__1(existing, "id")
        val row = SqlRow(columns = _uA(
            "total_foreground_sec",
            "app_label"
        ), values = _uA(
            totalSec,
            appLabel
        ))
        dbManager.update("app_usage_snapshots", row, "id = ?", _uA(
            id
        ))
    } else {
        val row = SqlRow(columns = _uA(
            "date",
            "package_name",
            "app_label",
            "total_foreground_sec",
            "created_at"
        ), values = _uA(
            date,
            packageName,
            appLabel,
            totalSec,
            Math.floor(Date.now() / 1000)
        ))
        dbManager.insert("app_usage_snapshots", row)
    }
}
fun getTodayTotalUsageMinutes(): Number {
    val date = today()
    val row = dbManager.queryOne("SELECT COALESCE(SUM(total_foreground_sec), 0) as total FROM app_usage_snapshots WHERE date = ?", _uA(
        date
    ))
    return Math.floor(getNum__1(row, "total") / 60)
}
fun getTodayTotalUsageSeconds(): Number {
    val date = today()
    val row = dbManager.queryOne("SELECT COALESCE(SUM(total_foreground_sec), 0) as total FROM app_usage_snapshots WHERE date = ?", _uA(
        date
    ))
    return getNum__1(row, "total")
}
fun getTodayAppBreakdown(): UTSArray<AppUsageItem> {
    val date = today()
    val rows = dbManager.query("SELECT package_name, app_label, total_foreground_sec FROM app_usage_snapshots WHERE date = ? ORDER BY total_foreground_sec DESC", _uA(
        date
    ))
    val result: UTSArray<AppUsageItem> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            val r = rows[i]
            val sec = getNum__1(r, "total_foreground_sec")
            val pkg = getStr__1(r, "package_name")
            val label = getStr__1(r, "app_label")
            if (pkg.length < 1) {
                i++
                continue
            }
            result.push(AppUsageItem(packageName = pkg, appLabel = if (label.length > 0) {
                label
            } else {
                pkg
            }
            , foregroundSec = sec, minutes = Math.floor(sec / 60)))
            i++
        }
    }
    return result
}
fun getTop5Apps(): UTSArray<AppUsageItem> {
    val all = getTodayAppBreakdown()
    val r: UTSArray<AppUsageItem> = _uA()
    run {
        var i: Number = 0
        while(i < all.length && i < 5){
            r.push(all[i])
            i++
        }
    }
    return r
}
open class DailyCount (
    @JsonNotNull
    open var date: String,
    @JsonNotNull
    open var count: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DailyCount", "models/DailyCount.uts", 1, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return DailyCountReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class DailyCountReactiveObject : DailyCount, IUTSReactive<DailyCount> {
    override var __v_raw: DailyCount
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: DailyCount, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(date = __v_raw.date, count = __v_raw.count) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): DailyCountReactiveObject {
        return DailyCountReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var date: String
        get() {
            return _tRG(__v_raw, "date", __v_raw.date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("date")) {
                return
            }
            val oldValue = __v_raw.date
            __v_raw.date = value
            _tRS(__v_raw, "date", oldValue, value)
        }
    override var count: Number
        get() {
            return _tRG(__v_raw, "count", __v_raw.count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("count")) {
                return
            }
            val oldValue = __v_raw.count
            __v_raw.count = value
            _tRS(__v_raw, "count", oldValue, value)
        }
}
fun insertOrUpdateSummary(summary: DailySummary): Unit {
    val existing = dbManager.queryOne("SELECT id FROM daily_summaries WHERE date = ?", _uA(
        summary.date
    ))
    if (existing != null) {
        val row = SqlRow(columns = _uA(
            "total_completed",
            "total_skipped",
            "total_duration_sec",
            "eye_score",
            "posture_score",
            "vitality_score",
            "penetration",
            "phone_usage_min",
            "guard_minutes",
            "guard_count"
        ), values = _uA(
            summary.total_completed,
            summary.total_skipped,
            summary.total_duration_sec,
            summary.eye_score,
            summary.posture_score,
            summary.vitality_score,
            summary.penetration,
            summary.phone_usage_min,
            summary.guard_minutes,
            summary.guard_count
        ))
        dbManager.update("daily_summaries", row, "date = ?", _uA(
            summary.date
        ))
    } else {
        val row = SqlRow(columns = _uA(
            "date",
            "total_completed",
            "total_skipped",
            "total_duration_sec",
            "eye_score",
            "posture_score",
            "vitality_score",
            "penetration",
            "phone_usage_min",
            "guard_minutes",
            "guard_count",
            "created_at"
        ), values = _uA(
            summary.date,
            summary.total_completed,
            summary.total_skipped,
            summary.total_duration_sec,
            summary.eye_score,
            summary.posture_score,
            summary.vitality_score,
            summary.penetration,
            summary.phone_usage_min,
            summary.guard_minutes,
            summary.guard_count,
            Math.floor(Date.now() / 1000)
        ))
        dbManager.insert("daily_summaries", row)
    }
}
fun getRecent28Days(): UTSArray<DailyCount> {
    val startDate = daysAgo(28)
    val rows = dbManager.query("SELECT date, total_completed as count FROM daily_summaries WHERE date >= ? ORDER BY date ASC", _uA(
        startDate
    ))
    val result: UTSArray<DailyCount> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            val row = rows[i]
            result.push(DailyCount(date = getStr(row, "date"), count = getNum(row, "count")))
            i++
        }
    }
    return result
}
fun getSetting(key: String, defaultValue: String = ""): String {
    val row = dbManager.queryOne("SELECT value FROM user_settings WHERE key = ?", _uA(
        key
    ))
    val v = getStr(row, "value")
    return if (v.length > 0) {
        v
    } else {
        defaultValue
    }
}
fun getInt(key: String, defaultVal: Number = 0): Number {
    val kVal = getSetting(key, "" + defaultVal)
    val n = parseInt(kVal)
    return if (isNaN(n)) {
        defaultVal
    } else {
        n
    }
}
fun getBool(key: String, defaultVal: Boolean = false): Boolean {
    val kVal = getSetting(key, if (defaultVal) {
        "1"
    } else {
        "0"
    }
    )
    return kVal == "1" || kVal == "true"
}
fun putSetting(key: String, value: String): Unit {
    val now = Math.floor(Date.now() / 1000)
    val existing = dbManager.queryOne("SELECT key FROM user_settings WHERE key = ?", _uA(
        key
    ))
    if (existing != null) {
        val row = SqlRow(columns = _uA(
            "value",
            "updated_at"
        ), values = _uA(
            value,
            now
        ))
        dbManager.update("user_settings", row, "key = ?", _uA(
            key
        ))
    } else {
        val row = SqlRow(columns = _uA(
            "key",
            "value",
            "updated_at"
        ), values = _uA(
            key,
            value,
            now
        ))
        dbManager.insert("user_settings", row)
    }
}
fun putInt(key: String, value: Number): Unit {
    putSetting(key, "" + value)
}
fun putBool(key: String, value: Boolean): Unit {
    putSetting(key, if (value) {
        "1"
    } else {
        "0"
    }
    )
}
val EYE_ACTION_IDS = _uA(
    "eye_blink",
    "eye_rotate",
    "far_gaze"
) as UTSArray<String>
val POSTURE_ACTION_IDS = _uA(
    "neck_rotate",
    "shoulder_roll",
    "neck_stretch"
) as UTSArray<String>
val VITALITY_ACTION_IDS = _uA(
    "core_tighten",
    "heel_raise",
    "deep_breath"
) as UTSArray<String>
val ALL_ACTIONS = _uA(
    MicroAction(id = "eye_blink", name = "眨眼休息", category = "护眼", defaultDurationMs = 10000, difficulty = 1, ttsText = "闭上眼睛，1、2、3，感受眼部放松", iconPath = "/static/icons/eye_blink.png"),
    MicroAction(id = "eye_rotate", name = "眼球转动", category = "护眼", defaultDurationMs = 10000, difficulty = 1, ttsText = "眼球缓慢顺时针转一圈，再逆时针转一圈", iconPath = "/static/icons/eye_rotate.png"),
    MicroAction(id = "neck_rotate", name = "颈部转动", category = "肩颈", defaultDurationMs = 15000, difficulty = 1, ttsText = "头缓慢向左转，保持3秒，回正，再向右转", iconPath = "/static/icons/neck_rotate.png"),
    MicroAction(id = "shoulder_roll", name = "绕肩放松", category = "肩颈", defaultDurationMs = 15000, difficulty = 1, ttsText = "双肩向上提起，向后画圈，缓慢放下", iconPath = "/static/icons/shoulder_roll.png"),
    MicroAction(id = "neck_stretch", name = "拉斜方肌", category = "肩颈", defaultDurationMs = 15000, difficulty = 2, ttsText = "右手扶左耳，缓慢向右拉伸，保持5秒", iconPath = "/static/icons/neck_stretch.png"),
    MicroAction(id = "core_tighten", name = "收紧核心", category = "核心", defaultDurationMs = 10000, difficulty = 1, ttsText = "收紧腹部，像被人打了一拳，保持住", iconPath = "/static/icons/core_tighten.png"),
    MicroAction(id = "heel_raise", name = "踮脚拉伸", category = "下肢", defaultDurationMs = 20000, difficulty = 1, ttsText = "双脚与肩同宽，缓慢踮起脚跟，再缓慢放下", iconPath = "/static/icons/heel_raise.png"),
    MicroAction(id = "deep_breath", name = "腹式呼吸", category = "呼吸", defaultDurationMs = 15000, difficulty = 1, ttsText = "鼻子深吸气4秒，肚子鼓起，嘴巴缓慢呼气6秒", iconPath = "/static/icons/deep_breath.png"),
    MicroAction(id = "far_gaze", name = "远眺放松", category = "护眼", defaultDurationMs = 10000, difficulty = 1, ttsText = "看向远处至少6米外，放松眼部肌肉", iconPath = "/static/icons/far_gaze.png")
) as UTSArray<MicroAction>
fun getActionById(id: String): MicroAction? {
    for(a in resolveUTSValueIterator(ALL_ACTIONS)){
        if (a.id === id) {
            return a
        }
    }
    return null
}
fun calculateThreeStatus(date: String): ThreeStatus {
    val target = getTargetCount()
    val eyeScore = calcCategoryScore(date, EYE_ACTION_IDS, target)
    val postureScore = calcCategoryScore(date, POSTURE_ACTION_IDS, target)
    val vitalityScore = calcCategoryScore(date, VITALITY_ACTION_IDS, target)
    return ThreeStatus(eyeScore = eyeScore, eyeLevel = getStatusLevel(eyeScore), postureScore = postureScore, postureLevel = getStatusLevel(postureScore), vitalityScore = vitalityScore, vitalityLevel = getStatusLevel(vitalityScore))
}
val DEFAULT_DAILY_TARGET: Number = 3
fun getTargetCount(): Number {
    val v = getInt("category_target_count", DEFAULT_DAILY_TARGET)
    if (v < 1) {
        return 1
    }
    if (v > 20) {
        return 20
    }
    return v
}
fun calcCategoryScore(date: String, actionIds: UTSArray<String>, target: Number): Number {
    val count = countCompletedByDateAndActions(date, actionIds)
    if (count < 1) {
        return -1
    }
    if (target <= 0) {
        return 0
    }
    val score = (count / target) * 100
    if (score > 100) {
        return 100
    }
    return Math.round(score)
}
fun getStatusLevel(score: Number): StatusLevel {
    if (score < 0) {
        return "no_data"
    }
    if (score >= 70) {
        return "good"
    }
    if (score >= 40) {
        return "normal"
    }
    return "warning"
}
fun calcPenetration(date: String): Number {
    val completed = countCompletedToday()
    val triggered = countTriggersToday()
    if (triggered < 1) {
        return 0
    }
    return (completed / triggered) * 10
}
open class HomepageData (
    @JsonNotNull
    open var eyeScore: Number,
    @JsonNotNull
    open var eyeLevel: StatusLevel,
    @JsonNotNull
    open var postureScore: Number,
    @JsonNotNull
    open var postureLevel: StatusLevel,
    @JsonNotNull
    open var vitalityScore: Number,
    @JsonNotNull
    open var vitalityLevel: StatusLevel,
    @JsonNotNull
    open var guardCount: Number,
    @JsonNotNull
    open var penetration: Number,
    @JsonNotNull
    open var todayCompletedCount: Number,
    @JsonNotNull
    open var heatmapData: UTSArray<DailyCount>,
    @JsonNotNull
    open var targetCount: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("HomepageData", "services/ScoreCalculator.uts", 75, 13)
    }
}
fun getHomepageData(date: String): HomepageData {
    val threeStatus = calculateThreeStatus(date)
    val guardCount = countCompletedToday()
    val penetration = calcPenetration(date)
    val todayCompletedCount = countCompletedToday()
    val heatmapData = getRecent28Days()
    val targetCount = getTargetCount()
    return HomepageData(eyeScore = threeStatus.eyeScore, eyeLevel = threeStatus.eyeLevel, postureScore = threeStatus.postureScore, postureLevel = threeStatus.postureLevel, vitalityScore = threeStatus.vitalityScore, vitalityLevel = threeStatus.vitalityLevel, guardCount = guardCount, penetration = penetration, todayCompletedCount = todayCompletedCount, heatmapData = heatmapData, targetCount = targetCount)
}
open class ActionDetail (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var type: String,
    @JsonNotNull
    open var durationSec: Number,
    @JsonNotNull
    open var time: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ActionDetail", "services/ScoreCalculator.uts", 147, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ActionDetailReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ActionDetailReactiveObject : ActionDetail, IUTSReactive<ActionDetail> {
    override var __v_raw: ActionDetail
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ActionDetail, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(name = __v_raw.name, type = __v_raw.type, durationSec = __v_raw.durationSec, time = __v_raw.time) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ActionDetailReactiveObject {
        return ActionDetailReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var type: String
        get() {
            return _tRG(__v_raw, "type", __v_raw.type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("type")) {
                return
            }
            val oldValue = __v_raw.type
            __v_raw.type = value
            _tRS(__v_raw, "type", oldValue, value)
        }
    override var durationSec: Number
        get() {
            return _tRG(__v_raw, "durationSec", __v_raw.durationSec, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("durationSec")) {
                return
            }
            val oldValue = __v_raw.durationSec
            __v_raw.durationSec = value
            _tRS(__v_raw, "durationSec", oldValue, value)
        }
    override var time: String
        get() {
            return _tRG(__v_raw, "time", __v_raw.time, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("time")) {
                return
            }
            val oldValue = __v_raw.time
            __v_raw.time = value
            _tRS(__v_raw, "time", oldValue, value)
        }
}
fun getTodayDetail(): UTSArray<ActionDetail> {
    val logs = getTodayCompletedLogs()
    val result: UTSArray<ActionDetail> = _uA()
    for(log in resolveUTSValueIterator(logs)){
        val action = getActionById(log.action_id)
        val name = if (action != null) {
            action.name
        } else {
            log.action_id
        }
        val type = if (action != null) {
            action.category
        } else {
            log.action_type
        }
        val durationSec = Math.floor(log.duration_ms / 1000)
        val d = Date(log.completed_at)
        val h = d.getHours()
        val m = d.getMinutes()
        val time = (if (h < 10) {
            "0"
        } else {
            ""
        }
        ) + h + ":" + (if (m < 10) {
            "0"
        } else {
            ""
        }
        ) + m
        result.push(ActionDetail(name = name, type = type, durationSec = durationSec, time = time))
    }
    return result
}
fun saveTodaySummary(): Unit {
    val date = today()
    val threeStatus = calculateThreeStatus(date)
    val completed = countCompletedToday()
    insertOrUpdateSummary(DailySummary(id = 0, date = date, total_completed = completed, total_skipped = countSkippedToday(), total_duration_sec = getTotalCompletedDurationSec(date), eye_score = Math.max(0, Math.round(threeStatus.eyeScore)), posture_score = Math.max(0, Math.round(threeStatus.postureScore)), vitality_score = Math.max(0, Math.round(threeStatus.vitalityScore)), penetration = calcPenetration(date), phone_usage_min = getTodayTotalUsageMinutes(), guard_minutes = 0, guard_count = completed, created_at = Math.floor(Date.now() / 1000)))
}
open class PersistedEffectiveRule (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var actionId: String,
    @JsonNotNull
    open var appPackages: UTSArray<String>,
    open var timeWindow: TimeWindow? = null,
    @JsonNotNull
    open var timeThresholdMinutes: Number,
    @JsonNotNull
    open var triggerLevel: TriggerLevel,
    open var categoryFilter: String? = null,
    @JsonNotNull
    open var source: String,
    open var sourceHistoryId: Number? = null,
    open var expiresAt: Number? = null,
    @JsonNotNull
    open var enabled: Boolean = false,
    @JsonNotNull
    open var priority: Number,
    @JsonNotNull
    open var createdAt: Number,
    @JsonNotNull
    open var updatedAt: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PersistedEffectiveRule", "models/PersistedEffectiveRule.uts", 3, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PersistedEffectiveRuleReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PersistedEffectiveRuleReactiveObject : PersistedEffectiveRule, IUTSReactive<PersistedEffectiveRule> {
    override var __v_raw: PersistedEffectiveRule
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PersistedEffectiveRule, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, actionId = __v_raw.actionId, appPackages = __v_raw.appPackages, timeWindow = __v_raw.timeWindow, timeThresholdMinutes = __v_raw.timeThresholdMinutes, triggerLevel = __v_raw.triggerLevel, categoryFilter = __v_raw.categoryFilter, source = __v_raw.source, sourceHistoryId = __v_raw.sourceHistoryId, expiresAt = __v_raw.expiresAt, enabled = __v_raw.enabled, priority = __v_raw.priority, createdAt = __v_raw.createdAt, updatedAt = __v_raw.updatedAt) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PersistedEffectiveRuleReactiveObject {
        return PersistedEffectiveRuleReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var actionId: String
        get() {
            return _tRG(__v_raw, "actionId", __v_raw.actionId, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("actionId")) {
                return
            }
            val oldValue = __v_raw.actionId
            __v_raw.actionId = value
            _tRS(__v_raw, "actionId", oldValue, value)
        }
    override var appPackages: UTSArray<String>
        get() {
            return _tRG(__v_raw, "appPackages", __v_raw.appPackages, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("appPackages")) {
                return
            }
            val oldValue = __v_raw.appPackages
            __v_raw.appPackages = value
            _tRS(__v_raw, "appPackages", oldValue, value)
        }
    override var timeWindow: TimeWindow?
        get() {
            return _tRG(__v_raw, "timeWindow", __v_raw.timeWindow, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("timeWindow")) {
                return
            }
            val oldValue = __v_raw.timeWindow
            __v_raw.timeWindow = value
            _tRS(__v_raw, "timeWindow", oldValue, value)
        }
    override var timeThresholdMinutes: Number
        get() {
            return _tRG(__v_raw, "timeThresholdMinutes", __v_raw.timeThresholdMinutes, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("timeThresholdMinutes")) {
                return
            }
            val oldValue = __v_raw.timeThresholdMinutes
            __v_raw.timeThresholdMinutes = value
            _tRS(__v_raw, "timeThresholdMinutes", oldValue, value)
        }
    override var triggerLevel: TriggerLevel
        get() {
            return _tRG(__v_raw, "triggerLevel", __v_raw.triggerLevel, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("triggerLevel")) {
                return
            }
            val oldValue = __v_raw.triggerLevel
            __v_raw.triggerLevel = value
            _tRS(__v_raw, "triggerLevel", oldValue, value)
        }
    override var categoryFilter: String?
        get() {
            return _tRG(__v_raw, "categoryFilter", __v_raw.categoryFilter, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("categoryFilter")) {
                return
            }
            val oldValue = __v_raw.categoryFilter
            __v_raw.categoryFilter = value
            _tRS(__v_raw, "categoryFilter", oldValue, value)
        }
    override var source: String
        get() {
            return _tRG(__v_raw, "source", __v_raw.source, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("source")) {
                return
            }
            val oldValue = __v_raw.source
            __v_raw.source = value
            _tRS(__v_raw, "source", oldValue, value)
        }
    override var sourceHistoryId: Number?
        get() {
            return _tRG(__v_raw, "sourceHistoryId", __v_raw.sourceHistoryId, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("sourceHistoryId")) {
                return
            }
            val oldValue = __v_raw.sourceHistoryId
            __v_raw.sourceHistoryId = value
            _tRS(__v_raw, "sourceHistoryId", oldValue, value)
        }
    override var expiresAt: Number?
        get() {
            return _tRG(__v_raw, "expiresAt", __v_raw.expiresAt, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("expiresAt")) {
                return
            }
            val oldValue = __v_raw.expiresAt
            __v_raw.expiresAt = value
            _tRS(__v_raw, "expiresAt", oldValue, value)
        }
    override var enabled: Boolean
        get() {
            return _tRG(__v_raw, "enabled", __v_raw.enabled, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("enabled")) {
                return
            }
            val oldValue = __v_raw.enabled
            __v_raw.enabled = value
            _tRS(__v_raw, "enabled", oldValue, value)
        }
    override var priority: Number
        get() {
            return _tRG(__v_raw, "priority", __v_raw.priority, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("priority")) {
                return
            }
            val oldValue = __v_raw.priority
            __v_raw.priority = value
            _tRS(__v_raw, "priority", oldValue, value)
        }
    override var createdAt: Number
        get() {
            return _tRG(__v_raw, "createdAt", __v_raw.createdAt, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("createdAt")) {
                return
            }
            val oldValue = __v_raw.createdAt
            __v_raw.createdAt = value
            _tRS(__v_raw, "createdAt", oldValue, value)
        }
    override var updatedAt: Number
        get() {
            return _tRG(__v_raw, "updatedAt", __v_raw.updatedAt, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updatedAt")) {
                return
            }
            val oldValue = __v_raw.updatedAt
            __v_raw.updatedAt = value
            _tRS(__v_raw, "updatedAt", oldValue, value)
        }
}
fun mapRow__2(row: Map<String, Any>): PersistedEffectiveRule {
    val pkgJson = getStr(row, "app_packages_json")
    var appPackages: UTSArray<String> = _uA()
    if (pkgJson.length > 0) {
        try {
            val parsed = UTSAndroid.consoleDebugError(JSON.parse(pkgJson), " at database/EffectiveRuleDao.uts:10") as UTSArray<Any>
            if (parsed != null) {
                val tmp: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < parsed.length){
                        val v = parsed[i]
                        if (UTSAndroid.`typeof`(v) === "string" && (v as String).length > 0) {
                            tmp.push(v as String)
                        }
                        i++
                    }
                }
                appPackages = tmp
            }
        }
         catch (_: Throwable) {
            appPackages = _uA()
        }
    }
    val twJson = getStrOrNull(row, "time_window_json")
    var timeWindow: TimeWindow? = null
    if (twJson != null && twJson.length > 0) {
        try {
            val obj = UTSAndroid.consoleDebugError(JSON.parse(twJson), " at database/EffectiveRuleDao.uts:30") as UTSJSONObject
            if (obj != null) {
                val start = obj["start"] as String
                val end = obj["end"] as String
                if (start != null && end != null && start.length > 0 && end.length > 0) {
                    timeWindow = TimeWindow(start = start, end = end)
                }
            }
        }
         catch (_: Throwable) {
            timeWindow = null
        }
    }
    val srcStr = getStr(row, "source")
    val source: String = if (srcStr == "llm") {
        "llm"
    } else {
        if (srcStr == "system") {
            "system"
        } else {
            "user"
        }
    }
    return PersistedEffectiveRule(id = getNum(row, "id"), actionId = getStr(row, "action_id"), appPackages = appPackages, timeWindow = timeWindow, timeThresholdMinutes = getNum(row, "time_threshold_minutes"), triggerLevel = getStr(row, "trigger_level") as TriggerLevel, categoryFilter = getStrOrNull(row, "category_filter"), source = source, sourceHistoryId = if (getStrOrNull(row, "source_history_id") == null || getStr(row, "source_history_id").length < 1) {
        null
    } else {
        getNum(row, "source_history_id")
    }
    , expiresAt = if (getStrOrNull(row, "expires_at") == null || getStr(row, "expires_at").length < 1) {
        null
    } else {
        getNum(row, "expires_at")
    }
    , enabled = getNum(row, "enabled") == 1, priority = getNum(row, "priority"), createdAt = getNum(row, "created_at"), updatedAt = getNum(row, "updated_at"))
}
fun insertRule(rule: PersistedEffectiveRule): Number {
    val pkgJson = JSON.stringify(rule.appPackages)
    val twJson = if (rule.timeWindow == null) {
        null
    } else {
        JSON.stringify(rule.timeWindow)
    }
    val row = SqlRow(columns = _uA(
        "action_id",
        "app_packages_json",
        "time_window_json",
        "time_threshold_minutes",
        "trigger_level",
        "category_filter",
        "source",
        "source_history_id",
        "expires_at",
        "enabled",
        "priority",
        "created_at",
        "updated_at"
    ), values = _uA(
        rule.actionId,
        pkgJson,
        twJson,
        rule.timeThresholdMinutes,
        rule.triggerLevel,
        rule.categoryFilter,
        rule.source,
        rule.sourceHistoryId,
        rule.expiresAt,
        if (rule.enabled) {
            1
        } else {
            0
        }
        ,
        rule.priority,
        rule.createdAt,
        rule.updatedAt
    ))
    return dbManager.insert("effective_rules", row)
}
fun updateRule(rule: PersistedEffectiveRule): Unit {
    val pkgJson = JSON.stringify(rule.appPackages)
    val twJson = if (rule.timeWindow == null) {
        null
    } else {
        JSON.stringify(rule.timeWindow)
    }
    val row = SqlRow(columns = _uA(
        "action_id",
        "app_packages_json",
        "time_window_json",
        "time_threshold_minutes",
        "trigger_level",
        "category_filter",
        "source",
        "source_history_id",
        "expires_at",
        "enabled",
        "priority",
        "updated_at"
    ), values = _uA(
        rule.actionId,
        pkgJson,
        twJson,
        rule.timeThresholdMinutes,
        rule.triggerLevel,
        rule.categoryFilter,
        rule.source,
        rule.sourceHistoryId,
        rule.expiresAt,
        if (rule.enabled) {
            1
        } else {
            0
        }
        ,
        rule.priority,
        rule.updatedAt
    ))
    dbManager.update("effective_rules", row, "id = ?", _uA(
        rule.id
    ))
}
fun deleteRule(id: Number): Unit {
    dbManager.`delete`("effective_rules", "id = ?", _uA(
        id
    ))
}
fun getActiveRules(): UTSArray<PersistedEffectiveRule> {
    val rows = dbManager.query("SELECT * FROM effective_rules WHERE enabled = 1 ORDER BY priority DESC, time_threshold_minutes ASC")
    val result: UTSArray<PersistedEffectiveRule> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            val r = rows[i]
            if (r == null) {
                i++
                continue
            }
            result.push(mapRow__2(r))
            i++
        }
    }
    return result
}
val SYSTEM_PKG_PREFIXES = _uA(
    "com.android.",
    "com.google.android.",
    "android.",
    "com.miui.",
    "com.huawei.",
    "com.samsung.",
    "com.oppo.",
    "com.vivo.",
    "com.oneplus.",
    "com.sec.",
    "com.qualcomm.",
    "com.qti.",
    "com.mediatek.",
    "com.lenovo.",
    "com.zte.",
    "com.meizu.",
    "com.nubia.",
    "com.letv.",
    "com.sonymobile.",
    "com.motorola.",
    "com.lge."
) as UTSArray<String>
val SYSTEM_PKG_EXACT = _uA(
    "android",
    "com.android.launcher",
    "com.android.launcher2",
    "com.android.launcher3",
    "com.android.settings",
    "com.android.systemui",
    "com.android.dialer",
    "com.android.phone",
    "com.android.contacts",
    "com.android.camera",
    "com.android.gallery",
    "com.android.music",
    "com.android.email",
    "com.android.browser",
    "com.android.calendar",
    "com.android.clock",
    "com.android.deskclock",
    "com.android.documentsui",
    "com.android.downloads",
    "com.android.inputmethod.latin",
    "com.android.keyguard",
    "com.android.location.fused",
    "com.android.nfc",
    "com.android.providers",
    "com.android.server",
    "com.android.shell",
    "com.android.sms",
    "com.android.soundrecorder",
    "com.android.vending",
    "com.android.webview",
    "com.android.wifi",
    "com.android.providers.media",
    "com.android.providers.telephony",
    "com.android.providers.calendar",
    "com.android.providers.contacts"
) as UTSArray<String>
fun isSystemPackage(pkg: String): Boolean {
    if (pkg.length < 1) {
        return true
    }
    if (pkg == "android") {
        return true
    }
    run {
        var i: Number = 0
        while(i < SYSTEM_PKG_EXACT.length){
            if (pkg == SYSTEM_PKG_EXACT[i]) {
                return true
            }
            i++
        }
    }
    run {
        var i: Number = 0
        while(i < SYSTEM_PKG_PREFIXES.length){
            if (pkg.indexOf(SYSTEM_PKG_PREFIXES[i]) == 0) {
                return true
            }
            i++
        }
    }
    return false
}
fun isSelfPackage(pkg: String, selfPkg: String): Boolean {
    if (selfPkg.length < 1) {
        return false
    }
    return pkg == selfPkg
}
fun shouldFilterPackage(pkg: String, selfPkg: String): Boolean {
    return isSystemPackage(pkg) || isSelfPackage(pkg, selfPkg)
}
val DISABLED_ACTIONS_KEY = "disabled_action_ids"
fun getEnabledActions(): UTSArray<MicroAction> {
    val disabledStr = getSetting(DISABLED_ACTIONS_KEY, "[]")
    var disabledIds: UTSArray<String> = _uA()
    try {
        disabledIds = UTSAndroid.consoleDebugError(JSON.parse(disabledStr), " at services/ActionManager.uts:10") as UTSArray<String>
    }
     catch (_: Throwable) {}
    return ALL_ACTIONS.filter(fun(a): Boolean {
        return disabledIds.indexOf(a.id) < 0
    }
    )
}
fun isSuitableForContext(action: MicroAction, triggerType: String): Boolean {
    val hour = currentHour()
    if (hour >= 22 || hour < 7) {
        if (action.category === "核心") {
            return false
        }
    }
    return true
}
open class FrequencyState (
    @JsonNotNull
    open var skipCount: Number,
    @JsonNotNull
    open var reductionRatio: Number,
    @JsonNotNull
    open var reducedAt: Number,
    @JsonNotNull
    open var recoveryDeadline: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("FrequencyState", "services/RuleEngine.uts", 4, 6)
    }
}
val frequencyReducer: Map<String, FrequencyState> = Map<String, FrequencyState>()
val actionWeights: Map<String, Number> = Map<String, Number>()
fun onConsecutiveSkip(actionType: String): Unit {
    val state = getOrCreateState(actionType)
    state.skipCount++
    if (state.skipCount >= 3) {
        state.reductionRatio = Math.max(0.125, state.reductionRatio * 0.5)
        state.reducedAt = Date.now()
        state.recoveryDeadline = Date.now() + 86400000
        state.skipCount = 0
    }
}
fun onCompleted(actionType: String): Unit {
    val state = frequencyReducer.get(actionType)
    if (state == null) {
        return
    }
    state.skipCount = 0
    if (state.reductionRatio < 1.0) {
        state.reductionRatio = Math.min(1.0, state.reductionRatio * 2)
    }
}
fun isFrequencyReduced(actionType: String): Boolean {
    val state = frequencyReducer.get(actionType)
    if (state == null) {
        return false
    }
    if (state.reductionRatio >= 1.0) {
        return false
    }
    if (Date.now() >= state.recoveryDeadline) {
        state.reductionRatio = 1.0
        return false
    }
    return Math.random() >= state.reductionRatio
}
fun selectAction(triggerType: String): String? {
    val enabled = getEnabledActions()
    console.log("[RuleEngine] selectAction: enabled.length=" + enabled.length + " triggerType=" + triggerType, " at services/RuleEngine.uts:54")
    if (enabled.length < 1) {
        console.log("[RuleEngine] selectAction: no enabled actions", " at services/RuleEngine.uts:56")
        return null
    }
    val candidates = enabled.filter(fun(a: MicroAction): Boolean {
        return isSuitableForContext(a, triggerType)
    }
    )
    console.log("[RuleEngine] selectAction: candidates.length=" + candidates.length, " at services/RuleEngine.uts:61")
    if (candidates.length < 1) {
        return enabled[0].id
    }
    return weightedRandomSelect(candidates)
}
fun weightedRandomSelect(actions: UTSArray<MicroAction>): String {
    val weights: UTSArray<Number> = actions.map(fun(a): Number {
        val w = actionWeights.get(a.id)
        if (w != null) {
            return w
        }
        return 1.0
    }
    )
    val totalWeight = weights.reduce(fun(sum, w): Number {
        return sum + w
    }
    , 0)
    var r = Math.random() * totalWeight
    run {
        var i: Number = 0
        while(i < actions.length){
            r -= weights[i]
            if (r <= 0) {
                return actions[i].id
            }
            i++
        }
    }
    return actions[actions.length - 1].id
}
fun getOrCreateState(actionType: String): FrequencyState {
    if (frequencyReducer.get(actionType) == null) {
        val state = FrequencyState(skipCount = 0, reductionRatio = 1.0, reducedAt = 0, recoveryDeadline = 0)
        frequencyReducer.set(actionType, state)
    }
    return frequencyReducer.get(actionType)!!
}
enum class TriggerState__1(override val value: Int) : UTSEnumInt {
    IDLE(0),
    SHOWING(1),
    EXECUTING(2),
    COMPLETING(3),
    COOLING_DOWN(4)
}
open class EffectiveRuleMatch (
    @JsonNotNull
    open var rule: PersistedEffectiveRule,
    open var action: MicroAction? = null,
    @JsonNotNull
    open var level: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("EffectiveRuleMatch", "services/EffectiveRuleEngine.uts", 8, 13)
    }
}
open class TriggerContext (
    @JsonNotNull
    open var appPackage: String,
    @JsonNotNull
    open var appLabel: String,
    open var appCategory: String? = null,
    @JsonNotNull
    open var continuousMinutes: Number,
    @JsonNotNull
    open var triggerType: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TriggerContext", "services/TriggerEngine.uts", 19, 13)
    }
}
open class TriggerDecision (
    @JsonNotNull
    open var actionId: String,
    @JsonNotNull
    open var triggerLevel: String,
    @JsonNotNull
    open var triggerType: String,
    @JsonNotNull
    open var durationMs: Number,
    @JsonNotNull
    open var ttsText: String,
    @JsonNotNull
    open var actionName: String,
    @JsonNotNull
    open var matchedRuleId: Number,
    @JsonNotNull
    open var matchedRuleSource: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TriggerDecision", "services/TriggerEngine.uts", 26, 13)
    }
}
var currentState: TriggerState__1 = TriggerState__1.IDLE
var lastTriggerTime: Number = 0
val DEFAULT_COOLDOWN_MIN: Number = 10
fun getCooldownMs(): Number {
    val min = getInt("trigger_cooldown_min", DEFAULT_COOLDOWN_MIN)
    if (min < 1) {
        return DEFAULT_COOLDOWN_MIN * 60000
    }
    return min * 60000
}
fun getRemainingCooldownMs(): Number {
    if (lastTriggerTime < 1) {
        return 0
    }
    val cd = getCooldownMs()
    val remain = cd - (Date.now() - lastTriggerTime)
    return if (remain > 0) {
        remain
    } else {
        0
    }
}
fun evaluate(ctx: TriggerContext): EffectiveRuleMatch? {
    if (isSystemPackage(ctx.appPackage)) {
        return null
    }
    var candidates: UTSArray<PersistedEffectiveRule> = _uA()
    val all = getActiveRules()
    console.log("[EffectiveRuleEngine] all rules=" + all.length + " pkg=" + ctx.appPackage + " mins=" + ctx.continuousMinutes, " at services/EffectiveRuleEngine.uts:22")
    val nowSec = Math.floor(Date.now() / 1000)
    run {
        var i: Number = 0
        while(i < all.length){
            val r = all[i]
            if (!r.enabled) {
                i++
                continue
            }
            if (r.expiresAt != null && r.expiresAt!! > 0 && nowSec > r.expiresAt!!) {
                i++
                continue
            }
            if (r.appPackages.length > 0 && r.appPackages.indexOf(ctx.appPackage) < 0) {
                i++
                continue
            }
            if (r.timeWindow != null && !isInTimeRange(r.timeWindow!!.start, r.timeWindow!!.end)) {
                i++
                continue
            }
            if (r.timeThresholdMinutes > 0 && ctx.continuousMinutes < r.timeThresholdMinutes) {
                i++
                continue
            }
            candidates.push(r)
            i++
        }
    }
    console.log("[EffectiveRuleEngine] candidates=" + candidates.length, " at services/EffectiveRuleEngine.uts:33")
    if (candidates.length < 1) {
        return null
    }
    var best = candidates[0]
    run {
        var i: Number = 1
        while(i < candidates.length){
            val c = candidates[i]
            val bIsLlm = best.source == "llm"
            val cIsLlm = c.source == "llm"
            if (cIsLlm && !bIsLlm) {
                best = c
                i++
                continue
            }
            if (!cIsLlm && bIsLlm) {
                i++
                continue
            }
            val bHasTw = best.timeWindow != null
            val cHasTw = c.timeWindow != null
            if (cHasTw && !bHasTw) {
                best = c
                i++
                continue
            }
            if (!cHasTw && bHasTw) {
                i++
                continue
            }
            val bHasApp = best.appPackages.length > 0
            val cHasApp = c.appPackages.length > 0
            if (cHasApp && !bHasApp) {
                best = c
                i++
                continue
            }
            if (!cHasApp && bHasApp) {
                i++
                continue
            }
            if (c.timeThresholdMinutes < best.timeThresholdMinutes) {
                best = c
            }
            i++
        }
    }
    val action = getActionById(best.actionId)
    return EffectiveRuleMatch(rule = best, action = action, level = best.triggerLevel)
}
fun shouldTrigger(context: TriggerContext): TriggerDecision? {
    if (currentState !== TriggerState__1.IDLE) {
        console.log("[TriggerEngine] skip: state=" + currentState, " at services/TriggerEngine.uts:62")
        return null
    }
    val nowMs = Date.now()
    val cooldownMs = getCooldownMs()
    if (nowMs - lastTriggerTime < cooldownMs) {
        console.log("[TriggerEngine] skip: cooldown remaining=" + (cooldownMs - (nowMs - lastTriggerTime)), " at services/TriggerEngine.uts:69")
        return null
    }
    var dndOk = false
    try {
        dndOk = isInDndPeriod()
    }
     catch (e: Throwable) {
        console.warn("[TriggerEngine] isInDndPeriod: " + e, " at services/TriggerEngine.uts:74")
    }
    if (dndOk) {
        return null
    }
    if (isSystemPackage(context.appPackage)) {
        return null
    }
    var blacklisted = false
    try {
        blacklisted = isBlacklisted(context.appPackage)
    }
     catch (e: Throwable) {
        console.warn("[TriggerEngine] isBlacklisted: " + e, " at services/TriggerEngine.uts:81")
    }
    if (blacklisted) {
        return null
    }
    if (isFrequencyReduced(context.triggerType)) {
        return null
    }
    var match: EffectiveRuleMatch? = null
    try {
        match = evaluate(context)
    }
     catch (e: Throwable) {
        console.warn("[TriggerEngine] evaluateRule: " + e, " at services/TriggerEngine.uts:87")
    }
    var action: MicroAction? = null
    var ruleSource: String = "system"
    if (match != null) {
        ruleSource = match.rule.source
        if (match.action == null || match.rule.actionId.length < 1) {
            var fallbackId: String? = null
            try {
                fallbackId = selectAction(context.triggerType)
            } catch (e: Throwable) {
                console.warn("[TriggerEngine] selectAction: " + e, " at services/TriggerEngine.uts:94")
            }
            if (fallbackId == null) {
                return null
            }
            action = getActionById(fallbackId)
        } else {
            action = match.action
        }
    } else {
        var fallbackId: String? = null
        try {
            fallbackId = selectAction(context.triggerType)
        }
         catch (e: Throwable) {
            console.warn("[TriggerEngine] selectAction fallback: " + e, " at services/TriggerEngine.uts:102")
        }
        if (fallbackId == null) {
            return null
        }
        action = getActionById(fallbackId)
        if (action == null) {
            return null
        }
    }
    if (action == null) {
        return null
    }
    var triggerLevel = decideTriggerLevel(context)
    if (match == null) {
        "gentle"
    }
    currentState = TriggerState__1.SHOWING
    lastTriggerTime = nowMs
    val triggerLog = TriggerLog(id = 0, trigger_type = context.triggerType, trigger_level = if (match != null) {
        match.level
    } else {
        "gentle"
    }
    , app_package = context.appPackage, app_category = if (context.appCategory != null) {
        context.appCategory
    } else {
        null
    }
    , continuous_minutes = context.continuousMinutes, action_id = action.id, user_action = null, created_at = Math.floor(nowMs / 1000))
    insertTriggerLog(triggerLog)
    return TriggerDecision(actionId = action.id, triggerLevel = if (match != null) {
        match.level
    } else {
        "gentle"
    }
    , triggerType = context.triggerType, durationMs = action.defaultDurationMs, ttsText = action.ttsText, actionName = action.name, matchedRuleId = if (match != null) {
        match.rule.id
    } else {
        0
    }
    , matchedRuleSource = ruleSource)
}
fun onUserAccepted(): Unit {
    currentState = TriggerState__1.EXECUTING
}
fun onActionResolved(): Unit {
    currentState = TriggerState__1.COOLING_DOWN
    scheduleCooldownEnd()
}
fun forceIdle(): Unit {
    currentState = TriggerState__1.IDLE
    lastTriggerTime = 0
}
fun scheduleCooldownEnd(): Unit {
    val cd = getCooldownMs()
    scheduleTimer(fun(): Unit {
        if (currentState === TriggerState__1.COOLING_DOWN) {
            currentState = TriggerState__1.IDLE
        }
    }
    , cd)
}
fun isInDndPeriod(): Boolean {
    val enabled = getBool("dnd_enabled", true)
    if (!enabled) {
        return false
    }
    val start = getSetting("dnd_start", "22:00")
    val end = getSetting("dnd_end", "07:00")
    return isInTimeRange(start, end)
}
fun isBlacklisted(packageName: String): Boolean {
    val listStr = getSetting("app_blacklist", "[]")
    try {
        val list = UTSAndroid.consoleDebugError(JSON.parse(listStr), " at services/TriggerEngine.uts:190") as UTSArray<String>
        return list.indexOf(packageName) >= 0
    }
     catch (_: Throwable) {
        return false
    }
}
fun decideTriggerLevel(context: TriggerContext): String {
    if (context.continuousMinutes >= 120) {
        return "strong"
    }
    if (context.continuousMinutes >= 90) {
        return "strong"
    }
    return "gentle"
}
fun scheduleTimer(fn: () -> Unit, ms: Number): Unit {
    setTimeout(fn, ms)
}
val ENCOURAGE_GENERAL = _uA(
    "又完成一次，身体在偷偷感谢你",
    "很好，继续保持",
    "微习惯的力量",
    "身体记住了你的每一次善意",
    "小小的动作，大大地关爱自己",
    "你比刚才健康了一点点",
    "积少成多，你懂的",
    "健康就是这么攒出来的",
    "这一刻，你选择了自己",
    "完美，继续加油"
) as UTSArray<String>
val ENCOURAGE_EYE = _uA(
    "眼睛说谢谢",
    "给眼睛放了个小假",
    "目光如炬，但你休息了一下",
    "眼睛刚刚充了个电",
    "明眸亮眼，就靠这几十秒"
) as UTSArray<String>
val ENCOURAGE_NECK = _uA(
    "颈椎在鼓掌",
    "肩膀松了一口气",
    "脖子表示很满意",
    "肩颈自由了那么一瞬间"
) as UTSArray<String>
val ENCOURAGE_BREATH = _uA(
    "呼吸之间，找回平静",
    "一口气的事，但身体很受用",
    "深呼吸，世界慢了半拍",
    "肺部刚刚做了个按摩"
) as UTSArray<String>
fun getRandomEncourage(category: String): String {
    var pool: UTSArray<String>
    if (category === "护眼") {
        pool = ENCOURAGE_GENERAL.concat(ENCOURAGE_EYE)
    } else if (category === "肩颈") {
        pool = ENCOURAGE_GENERAL.concat(ENCOURAGE_NECK)
    } else if (category === "呼吸") {
        pool = ENCOURAGE_GENERAL.concat(ENCOURAGE_BREATH)
    } else {
        pool = ENCOURAGE_GENERAL
    }
    val idx = Math.floor(Math.random() * pool.length)
    return pool[idx]
}
fun shortVibrate(times: Number = 1): Unit {
    if (times < 1) {
        return
    }
}
open class AudioPlayerCallbacks (
    open var onCompletion: () -> Unit,
    open var onError: (error: String) -> Unit,
    open var onProgress: (currentMs: Number, totalMs: Number) -> Unit,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AudioPlayerCallbacks", "uni_modules/uts-audio-player/utssdk/app-android/AudioPlayer.uts", 4, 13)
    }
}
var player: NativeAudioPlayer? = null
fun play(filePath: String, callbacks: AudioPlayerCallbacks): Unit {
    ensurePlayer()
    player!!!!.playFile(filePath, callbacks)
}
fun stop(): Unit {
    if (player != null) {
        player!!!!.stop()
        player = null
    }
}
fun ensurePlayer(): Unit {
    if (player == null) {
        player = NativeAudioPlayer()
    }
}
open class NativeAudioPlayer : IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("NativeAudioPlayer", "uni_modules/uts-audio-player/utssdk/app-android/AudioPlayer.uts", 44, 7)
    }
    private var mp: MediaPlayer? = null
    private var handler: Handler? = null
    private var callbacks: AudioPlayerCallbacks? = null
    open fun playFile(filePath: String, cbs: AudioPlayerCallbacks): Unit {
        this.stop()
        this.callbacks = cbs
        this.mp = MediaPlayer()
        try {
            this.mp!!!!.setDataSource(filePath)
            this.setup()
            this.mp!!!!.prepare()
            this.mp!!!!.start()
            this.startProgress()
        }
         catch (e: Throwable) {
            cbs.onError("playFile failed")
        }
    }
    open fun playUrl(url: String, cbs: AudioPlayerCallbacks): Unit {
        this.stop()
        this.callbacks = cbs
        this.mp = MediaPlayer()
        try {
            this.mp!!!!.setDataSource(url)
            this.setup()
            this.mp!!!!.prepareAsync()
            this.mp!!!!.setOnPreparedListener(fun(mp: MediaPlayer): Unit {
                mp.start()
            }
            )
            this.startProgress()
        }
         catch (e: Throwable) {
            cbs.onError("playUrl failed")
        }
    }
    open fun pause(): Unit {
        if (this.mp != null && this.mp!!!!.isPlaying()) {
            this.mp!!!!.pause()
        }
    }
    open fun resume(): Unit {
        if (this.mp != null && !this.mp!!!!.isPlaying()) {
            this.mp!!!!.start()
        }
    }
    open fun stop(): Unit {
        this.stopProgress()
        if (this.mp != null) {
            try {
                this.mp!!!!.stop()
                this.mp!!!!.release()
            }
             catch (_: Throwable) {}
            this.mp = null
        }
        this.callbacks = null
    }
    open fun setVolume(vol: Number): Unit {
        if (this.mp != null) {
            val v: Float = vol.toFloat()
            this.mp!!!!.setVolume(v, v)
        }
    }
    open fun isPlaying(): Boolean {
        return this.mp != null && this.mp!!!!.isPlaying()
    }
    private fun setup(): Unit {
        if (this.mp != null && this.callbacks != null) {
            val cbs = this.callbacks!!
            this.mp!!!!.setOnCompletionListener(fun(mp: MediaPlayer): Unit {
                cbs.onCompletion()
            }
            )
            this.mp!!!!.setOnErrorListener(fun(mp: MediaPlayer, what: Number, extra: Number): Boolean {
                cbs.onError("error: " + what + "/" + extra)
                return true
            }
            )
        }
    }
    private fun startProgress(): Unit {
        this.stopProgress()
        this.handler = Handler(Looper.getMainLooper())
        val self = this
        this.handler!!!!.postDelayed(fun(): Unit {
            val mp = self.mp
            val cbs = self.callbacks
            if (mp != null && mp.isPlaying() && cbs != null) {
                cbs.onProgress(mp.getCurrentPosition(), mp.getDuration())
                self.startProgress()
            }
        }
        , 100.toLong())
    }
    private fun stopProgress(): Unit {
        if (this.handler != null) {
            this.handler!!!!.removeCallbacksAndMessages(null)
            this.handler = null
        }
    }
}
var ttsInstance: TextToSpeech? = null
var ttsReady: Boolean = false
var pendingText: String = ""
var pendingVolume: Number = 1.0
fun clampVolume(volume: Number): Number {
    if (volume < 0) {
        return 0
    }
    if (volume > 1) {
        return 1
    }
    return volume
}
fun makeVolumeParams(volume: Number): Bundle {
    val params = Bundle()
    params.putFloat("volume", clampVolume(volume).toFloat())
    return params
}
fun speakSystemTts(text: String, volume: Number = 1.0): Unit {
    val v = clampVolume(volume)
    if (ttsInstance != null && ttsReady) {
        try {
            ttsInstance!!!!.speak(text, TextToSpeech.QUEUE_FLUSH, makeVolumeParams(v), "micro_habit_tts")
        } catch (_: Throwable) {
            ttsInstance!!!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "micro_habit_tts")
        }
    } else {
        if (ttsInstance == null) {
            initTts()
        }
        pendingText = text
        pendingVolume = v
    }
}
fun initTts(): Unit {
    try {
        val ctx = UTSAndroid.getAppContext()
        if (ctx == null) {
            return
        }
        ttsInstance = TextToSpeech(ctx, fun(status: Number): Unit {
            if (status == TextToSpeech.SUCCESS) {
                val result = ttsInstance!!!!.setLanguage(Locale.CHINESE)
                ttsReady = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
                if (ttsReady && pendingText.length > 0) {
                    try {
                        ttsInstance!!!!.speak(pendingText, TextToSpeech.QUEUE_FLUSH, makeVolumeParams(pendingVolume), "micro_habit_tts")
                    }
                     catch (_: Throwable) {
                        ttsInstance!!!!.speak(pendingText, TextToSpeech.QUEUE_FLUSH, null, "micro_habit_tts")
                    }
                    pendingText = ""
                }
            }
        }
        )
    }
     catch (_: Throwable) {}
}
open class TtsCache (
    @JsonNotNull
    open var text_hash: String,
    @JsonNotNull
    open var text_content: String,
    @JsonNotNull
    open var file_path: String,
    @JsonNotNull
    open var file_size: Number,
    @JsonNotNull
    open var source: String,
    @JsonNotNull
    open var created_at: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TtsCache", "models/TtsCache.uts", 1, 13)
    }
}
fun getByHash(hash: String): TtsCache? {
    val row = dbManager.queryOne("SELECT * FROM tts_cache WHERE text_hash = ?", _uA(
        hash
    ))
    if (row == null) {
        return null
    }
    return mapRow__3(row)
}
fun mapRow__3(row: Map<String, Any>): TtsCache {
    return TtsCache(text_hash = getStr(row, "text_hash"), text_content = getStr(row, "text_content"), file_path = getStr(row, "file_path"), file_size = getNum(row, "file_size"), source = getStr(row, "source"), created_at = getNum(row, "created_at"))
}
open class LlmCache (
    @JsonNotNull
    open var cache_key: String,
    @JsonNotNull
    open var cache_type: String,
    @JsonNotNull
    open var response: String,
    @JsonNotNull
    open var model: String,
    @JsonNotNull
    open var created_at: Number,
    @JsonNotNull
    open var expires_at: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LlmCache", "models/LlmCache.uts", 1, 13)
    }
}
fun getByKey(key: String): LlmCache? {
    val row = dbManager.queryOne("SELECT * FROM llm_cache WHERE cache_key = ?", _uA(
        key
    ))
    if (row == null) {
        return null
    }
    return mapRow__4(row)
}
fun save(key: String, type: String, response: String, expiresAt: Number): Unit {
    dbManager.execSql("INSERT OR REPLACE INTO llm_cache (cache_key, cache_type, response, expires_at, created_at) VALUES (?,?,?,?,?)", _uA(
        key,
        type,
        response,
        expiresAt,
        Math.floor(Date.now() / 1000)
    ))
}
fun mapRow__4(row: Map<String, Any>): LlmCache {
    return LlmCache(cache_key = getStr(row, "cache_key"), cache_type = getStr(row, "cache_type"), response = getStr(row, "response"), model = getStr(row, "model"), created_at = getNum(row, "created_at"), expires_at = getNum(row, "expires_at"))
}
val MINIMAX_BASE_URL = "https://api.minimax.chat/v1"
val MINIMAX_CHAT_PATH = "text/chatcompletion_v2"
val MINIMAX_CHAT_MODEL = "MiniMax-M2.7"
val MINIMAX_API_KEY = "sk-cp-Dd2_yFv0hINLsc2fnuWrrde0agYZQCzaRzW0DaK64CAl7Y-plAqRd7R2jSxQTSgSEWbl6teJc2oFL90HPMgyqLXel67OJhufLeLeVauln93DyXmA1277iQw"
val MINIMAX_TTS_URL = "https://api.minimaxi.com/v1/t2a_v2"
val MINIMAX_TTS_MODEL = "speech-02-hd"
val MINIMAX_TTS_VOICE_ID = "male-qn-qingse"
fun callMinimaxChat(systemPrompt: String, userPrompt: String, maxTokens: Number, onOK: (raw: String) -> Unit, onFail: () -> Unit): Unit {
    if (MINIMAX_API_KEY === "PLACEHOLDER_KEY") {
        console.warn("[CloudService] MiniMax API key 未配置，走降级路径", " at services/CloudService.uts:23")
        onFail()
        return
    }
    val reqBody: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("reqBody", "services/CloudService.uts", 19, 11), "model" to MINIMAX_CHAT_MODEL, "messages" to _uA(
        _uO("role" to "system", "content" to systemPrompt, "name" to "MiniMax AI"),
        _uO("role" to "user", "content" to userPrompt, "name" to "用户")
    ), "temperature" to 0.7, "max_completion_tokens" to maxTokens, "reasoning_effort" to "low")
    try {
        uni_request<Any>(RequestOptions(url = MINIMAX_BASE_URL + "/" + MINIMAX_CHAT_PATH, method = "POST", header = _uO("Content-Type" to "application/json", "Authorization" to ("Bearer " + MINIMAX_API_KEY)), data = JSON.stringify(reqBody), timeout = 30000, success = fun(res) {
            try {
                val data = res.data
                val statusCode = if ((data != null)) {
                    (data as UTSJSONObject).get("base_resp")
                } else {
                    null
                }
                val finishReason = if ((data != null)) {
                    extractFinishReason(data as UTSJSONObject)
                } else {
                    ""
                }
                console.log("[CloudService] callMinimaxChat response, finishReason=" + finishReason, " at services/CloudService.uts:54")
                val content = extractChatContent(if (data != null) {
                    data
                } else {
                    ""
                }
                )
                if (content.length > 0) {
                    console.log("[CloudService] callMinimaxChat content OK, length=" + content.length, " at services/CloudService.uts:57")
                    onOK(content)
                } else {
                    console.warn("[CloudService] callMinimaxChat empty content, raw data=" + JSON.stringify(data), " at services/CloudService.uts:60")
                    onFail()
                }
            }
             catch (_: Throwable) {
                onFail()
            }
        }
        , fail = fun(_err) {
            console.warn("[CloudService] callMinimaxChat network fail: " + JSON.stringify(_err), " at services/CloudService.uts:68")
            onFail()
        }
        ))
    }
     catch (_: Throwable) {
        onFail()
    }
}
fun callMinimaxTts(text: String, onOK: (audioUrl: String) -> Unit, onFail: (errMsg: String) -> Unit): Unit {
    if (MINIMAX_API_KEY === "PLACEHOLDER_KEY" || MINIMAX_API_KEY.length < 8) {
        onFail("MiniMax TTS key 未配置")
        return
    }
    if (text.length < 1) {
        onFail("TTS text 为空")
        return
    }
    val reqBody: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("reqBody", "services/CloudService.uts", 78, 11), "model" to MINIMAX_TTS_MODEL, "text" to text, "stream" to false, "voice_setting" to _uO("voice_id" to MINIMAX_TTS_VOICE_ID, "speed" to 1, "vol" to 1, "pitch" to 0), "audio_setting" to _uO("sample_rate" to 32000, "bitrate" to 128000, "format" to "mp3", "channel" to 1), "output_format" to "url")
    try {
        uni_request<Any>(RequestOptions(url = MINIMAX_TTS_URL, method = "POST", header = _uO("Content-Type" to "application/json", "Authorization" to ("Bearer " + MINIMAX_API_KEY)), data = JSON.stringify(reqBody), timeout = 30000, success = fun(res) {
            try {
                val data = res.data
                if (data == null) {
                    onFail("TTS 响应为空")
                    return
                }
                val obj = data as UTSJSONObject
                val baseResp = obj.get("base_resp")
                if (baseResp != null) {
                    val brObj = baseResp as UTSJSONObject
                    val sc = brObj.get("status_code")
                    if (sc != null && sc != 0) {
                        val sm = brObj.get("status_msg")
                        onFail("TTS 返回非 0 状态: " + (if (sm != null) {
                            ("" + sm)
                        } else {
                            ""
                        }
                        ))
                        return
                    }
                }
                val dataField = obj.get("data")
                if (dataField == null) {
                    onFail("TTS 响应无 data 字段")
                    return
                }
                val dObj = dataField as UTSJSONObject
                val audioField = dObj.get("audio")
                if (audioField == null) {
                    onFail("TTS 响应无 data.audio 字段")
                    return
                }
                val audioUrl = "" + audioField
                if (audioUrl.length < 8 || audioUrl.indexOf("http") < 0) {
                    onFail("TTS 响应 audio 非 url: " + audioUrl.substring(0, 80))
                    return
                }
                console.log("[CloudService] callMinimaxTts OK, textLen=" + text.length + " urlLen=" + audioUrl.length, " at services/CloudService.uts:142")
                onOK(audioUrl)
            }
             catch (e: Throwable) {
                val msg = if (e != null) {
                    ("" + e)
                } else {
                    "null"
                }
                onFail("TTS 响应解析异常: " + msg)
            }
        }
        , fail = fun(_err) {
            val msg = if (_err != null) {
                ("" + _err)
            } else {
                "null"
            }
            console.warn("[CloudService] callMinimaxTts network fail: " + msg, " at services/CloudService.uts:151")
            onFail("TTS 网络失败: " + msg)
        }
        ))
    }
     catch (e: Throwable) {
        val msg = if (e != null) {
            ("" + e)
        } else {
            "null"
        }
        onFail("TTS 请求构造异常: " + msg)
    }
}
fun downloadTtsAudio(url: String, cacheKey: String, onOK: (localPath: String) -> Unit, onFail: (errMsg: String) -> Unit): Unit {
    if (url.length < 8) {
        onFail("downloadTtsAudio url 为空")
        return
    }
    if (cacheKey.length < 1) {
        onFail("downloadTtsAudio cacheKey 为空")
        return
    }
    try {
        uni_downloadFile(DownloadFileOptions(url = url, timeout = 60000, success = fun(dlRes): Unit {
            try {
                if (dlRes.statusCode != 200) {
                    onFail("TTS 下载 HTTP 状态: " + dlRes.statusCode)
                    return
                }
                val tempPath: String = dlRes.tempFilePath
                if (tempPath == null || tempPath.length < 1) {
                    onFail("TTS 下载无 tempFilePath")
                    return
                }
                console.log("[CloudService] downloadTtsAudio OK, cacheKey=" + cacheKey + " tempPath=" + tempPath, " at services/CloudService.uts:193")
                onOK(tempPath)
            }
             catch (e: Throwable) {
                val msg = if (e != null) {
                    ("" + e)
                } else {
                    "null"
                }
                onFail("TTS 下载处理异常: " + msg)
            }
        }
        , fail = fun(err): Unit {
            val msg = if (err != null) {
                ("" + err)
            } else {
                "null"
            }
            console.warn("[CloudService] downloadFile fail: " + msg, " at services/CloudService.uts:202")
            onFail("TTS 文件下载失败: " + msg)
        }
        ))
    }
     catch (e: Throwable) {
        val msg = if (e != null) {
            ("" + e)
        } else {
            "null"
        }
        onFail("TTS 下载请求构造失败: " + msg)
    }
}
fun callDaily(date: String, data: DailyData, onOK: (raw: String) -> Unit, onFail: () -> Unit): Unit {
    console.log("[CloudService] callDaily start, date=" + date, " at services/CloudService.uts:213")
    val userPrompt = buildDailySummaryPrompt(data)
    callMinimaxChat(SYSTEM_PROMPT_DAILY, userPrompt, 1500, fun(raw: String): Unit {
        if (raw.length > 0) {
            console.log("[CloudService] callDaily OK, raw.length=" + raw.length, " at services/CloudService.uts:217")
            onOK(raw)
            return
        }
        console.warn("[CloudService] callDaily empty raw", " at services/CloudService.uts:221")
        onFail()
    }
    , fun(){
        console.warn("[CloudService] callDaily failed, fallback to local", " at services/CloudService.uts:224")
        onFail()
    }
    )
}
fun callWeekly(data: WeeklyReportStats, onOK: (raw: String) -> Unit, onFail: () -> Unit): Unit {
    console.log("[CloudService] callWeekly start, totalCompleted=" + data.totalCompleted, " at services/CloudService.uts:230")
    val userPrompt = buildWeeklyReportPrompt(data)
    callMinimaxChat(SYSTEM_PROMPT_WEEKLY, userPrompt, 2000, fun(raw: String): Unit {
        if (raw.length > 0) {
            console.log("[CloudService] callWeekly OK, raw.length=" + raw.length, " at services/CloudService.uts:234")
            onOK(raw)
            return
        }
        console.warn("[CloudService] callWeekly empty raw", " at services/CloudService.uts:238")
        onFail()
    }
    , fun(){
        console.warn("[CloudService] callWeekly failed, fallback to local", " at services/CloudService.uts:241")
        onFail()
    }
    )
}
fun callLlmEvaluate(stage: String, ctx: UTSUnionTypeObject, onOK: (raw: String) -> Unit, onFail: () -> Unit): Unit {
    var cacheKey = ""
    if (stage === "pre") {
        val pCtx = ctx as PreTriggerContext
        val cacheCtx = LlmCacheContext(appPackage = pCtx.appPackage, continuousMinutes = pCtx.continuousMinutes)
        cacheKey = buildLlmCacheKey(stage, cacheCtx)
    }
    if (cacheKey.length > 0) {
        try {
            val cached = getByKey(cacheKey)
            if (cached != null && cached.response.length > 0) {
                val nowSec = Math.floor(Date.now() / 1000)
                if (cached.expires_at <= 0 || cached.expires_at > nowSec) {
                    console.log("[CloudService] callLlmEvaluate " + stage + " cache HIT key=" + cacheKey, " at services/CloudService.uts:269")
                    onOK(cached.response)
                    return
                }
            }
        }
         catch (e: Throwable) {
            console.warn("[CloudService] callLlmEvaluate 读缓存异常: " + (if (e != null) {
                ("" + e)
            } else {
                "null"
            }
            ), " at services/CloudService.uts:275")
        }
    }
    var userPrompt = ""
    try {
        if (stage === "pre") {
            val pCtx = ctx as PreTriggerContext
            userPrompt = buildPreTriggerPrompt(pCtx)
            console.log("[CloudService] callLlmEvaluate pre userPrompt len=" + userPrompt.length, " at services/CloudService.uts:283")
        } else {
            val pCtx = ctx as PostActionContext
            userPrompt = buildPostActionPrompt(pCtx)
            console.log("[CloudService] callLlmEvaluate post userPrompt len=" + userPrompt.length, " at services/CloudService.uts:287")
        }
    }
     catch (e: Throwable) {
        val msg = if (e != null) {
            ("" + e)
        } else {
            "null"
        }
        console.warn("[CloudService] callLlmEvaluate prompt 构造失败: " + msg, " at services/CloudService.uts:291")
        onFail()
        return
    }
    callMinimaxChat(SYSTEM_PROMPT_EVALUATE, userPrompt, 1500, fun(raw: String): Unit {
        console.log("[CloudService] callLlmEvaluate " + stage + " OK, rawLen=" + raw.length + " preview=" + raw.substring(0, 80), " at services/CloudService.uts:297")
        if (cacheKey.length > 0 && raw.length > 0) {
            try {
                val ttlSec: Number = 86400
                val expiresAt = Math.floor(Date.now() / 1000) + ttlSec
                save(cacheKey, stage, raw, expiresAt)
            }
             catch (e: Throwable) {
                console.warn("[CloudService] callLlmEvaluate 写缓存异常: " + (if (e != null) {
                    ("" + e)
                } else {
                    "null"
                }
                ), " at services/CloudService.uts:304")
            }
        }
        onOK(raw)
    }
    , fun(): Unit {
        console.warn("[CloudService] callLlmEvaluate " + stage + " FAIL", " at services/CloudService.uts:309")
        onFail()
    }
    )
}
open class LlmCacheContext (
    @JsonNotNull
    open var appPackage: String,
    @JsonNotNull
    open var continuousMinutes: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LlmCacheContext", "services/CloudService.uts", 297, 6)
    }
}
fun buildLlmCacheKey(stage: String, ctx: LlmCacheContext?): String {
    try {
        if (ctx == null) {
            return ""
        }
        val pkg = if (ctx.appPackage.length > 0) {
            ctx.appPackage
        } else {
            "unknown"
        }
        val dayMs: Number = 86400000
        val hourBucket = Math.floor((Date.now() % dayMs) / 3600000)
        val contBucket = Math.floor(ctx.continuousMinutes / 30) * 30
        return stage + ":" + pkg + ":" + hourBucket + ":" + contBucket
    }
     catch (_: Throwable) {
        return ""
    }
}
fun extractChatContent(data: Any): String {
    try {
        val obj = data as UTSJSONObject
        if (obj == null) {
            return ""
        }
        val baseResp = obj.get("base_resp")
        if (baseResp != null) {
            val brObj = baseResp as UTSJSONObject
            val sc = brObj.get("status_code")
            if (sc != null && sc != 0) {
                val sm = brObj.get("status_msg")
                console.warn("[CloudService] MiniMax 返回非 0 状态: " + (if (sm != null) {
                    "" + sm
                } else {
                    ""
                }
                ), " at services/CloudService.uts:344")
                return ""
            }
        }
        val choicesRaw = obj.get("choices")
        if (choicesRaw == null) {
            return ""
        }
        val choices = choicesRaw as UTSArray<UTSJSONObject>
        if (choices.length < 1) {
            return ""
        }
        val first = choices[0]
        if (first == null) {
            return ""
        }
        val message = first.get("message")
        if (message == null) {
            return ""
        }
        val msgObj = message as UTSJSONObject
        val content = msgObj.get("content")
        if (content != null) {
            val c = "" + content
            if (c.length > 0) {
                return c
            }
        }
        val rc = msgObj.get("reasoning_content")
        if (rc != null) {
            val r = "" + rc
            if (r.length > 0) {
                return r
            }
        }
    }
     catch (e: Throwable) {
        console.warn("[CloudService] extractChatContent exception: " + JSON.stringify(e), " at services/CloudService.uts:368")
    }
    return ""
}
fun extractFinishReason(data: UTSJSONObject): String {
    try {
        val choices = data.get("choices")
        if (choices == null) {
            return ""
        }
        val arr = choices as UTSArray<UTSJSONObject>
        if (arr.length < 1) {
            return ""
        }
        val first = arr[0]
        if (first == null) {
            return ""
        }
        val fr = first.get("finish_reason")
        if (fr == null) {
            return ""
        }
        return "" + fr
    }
     catch (_: Throwable) {
        return ""
    }
}
fun textHash(text: String): String {
    var hash: Number = 2166136261
    run {
        var i: Number = 0
        while(i < text.length){
            val code = text.charCodeAt(i) as Number
            hash = (hash xor code) * 16777619
            hash = hash and 0xFFFFFFFF
            i++
        }
    }
    return (hash ushr 0).toString(16).padStart(8, "0")
}
val TTS_MODE: String = "system"
fun speakAdhoc(text: String): Unit {
    if (isSystemMuted()) {
        return
    }
    if (text.length < 1) {
        return
    }
    if (TTS_MODE == "api") {
        speakAdhocViaApi(text)
        return
    }
    speakAdhocSystem(text)
}
fun speakFixedGuide(text: String): Unit {
    if (isSystemMuted()) {
        return
    }
    val vol = getAdjustedVolume()
    try {
        speakSystemTts(text, vol)
    }
     catch (e: Throwable) {
        console.warn("[TtsService] speakFixedGuide plugin 调用失败: " + JSON.stringify(e), " at services/TtsService.uts:47")
    }
}
fun preGenerateAll(): Unit {
    if (TTS_MODE == "system") {
        console.log("[TtsService] preGenerateAll 已搁置（系统 TTS 无需预生成）", " at services/TtsService.uts:56")
        return
    }
    try {
        val texts: UTSArray<String> = _uA()
        run {
            var i: Number = 0
            while(i < ALL_ACTIONS.length){
                val a = ALL_ACTIONS[i]
                if (a != null && a.ttsText.length > 0) {
                    texts.push(a.ttsText)
                }
                i++
            }
        }
        run {
            var j: Number = 0
            while(j < FALLBACK_ADHOC_TEXTS.length){
                val t = FALLBACK_ADHOC_TEXTS[j]
                if (t != null && t.length > 0) {
                    texts.push(t)
                }
                j++
            }
        }
        if (texts.length < 1) {
            return
        }
        val MAX_CONCURRENT: Number = 3
        val total: Number = texts.length
        var running: Number = 0
        var cursor: Number = 0
        var doneCount: Number = 0
        fun tryStart(): Unit {
            while(running < MAX_CONCURRENT && cursor < texts.length){
                val idx: Number = cursor
                cursor++
                val t: String? = texts[idx]
                if (t == null) {
                    continue
                }
                val tHash: String = textHash(t)
                var skip: Boolean = false
                try {
                    if (getByHash(tHash) != null) {
                        skip = true
                    }
                }
                 catch (_: Throwable) {}
                if (skip) {
                    doneCount++
                    continue
                }
                running++
                callMinimaxTts(t, fun(url: String): Unit {
                    downloadTtsAudio(url, tHash, fun(_local: String): Unit {
                        running--
                        doneCount++
                        console.log("[TtsService] preGenerateAll 进度 " + doneCount + "/" + total, " at services/TtsService.uts:104")
                        tryStart()
                    }
                    , fun(errMsg: String): Unit {
                        running--
                        doneCount++
                        console.warn("[TtsService] preGenerateAll download 失败: " + errMsg, " at services/TtsService.uts:110")
                        tryStart()
                    }
                    )
                }
                , fun(errMsg: String): Unit {
                    running--
                    doneCount++
                    console.warn("[TtsService] preGenerateAll tts 失败: " + errMsg, " at services/TtsService.uts:118")
                    tryStart()
                }
                )
            }
        }
        tryStart()
    }
     catch (e: Throwable) {
        val msg = if (e != null) {
            ("" + e)
        } else {
            "null"
        }
        console.warn("[TtsService] preGenerateAll 整体异常: " + msg, " at services/TtsService.uts:127")
    }
}
fun prewarmSystemTts(): Unit {
    try {
        speakSystemTts("", 0)
    }
     catch (e: Throwable) {
        val msg = if (e != null) {
            ("" + e)
        } else {
            "null"
        }
        console.warn("[TtsService] prewarmSystemTts 失败: " + msg, " at services/TtsService.uts:135")
    }
}
fun stopSpeech(): Unit {
    try {
        stop()
    }
     catch (e: Throwable) {
        val msg = if (e != null) {
            ("" + e)
        } else {
            "null"
        }
        console.warn("[TtsService] stopSpeech 失败: " + msg, " at services/TtsService.uts:144")
    }
}
fun speakAdhocSystem(text: String): Unit {
    try {
        speakSystemTts(text, getAdjustedVolume())
    }
     catch (e: Throwable) {
        console.warn("[TtsService] speakAdhocSystem 失败: " + JSON.stringify(e), " at services/TtsService.uts:150")
    }
}
fun speakAdhocViaApi(text: String): Unit {
    if (text.length < 1) {
        return
    }
    val hash: String = textHash(text)
    try {
        val cached = getByHash(hash)
        if (cached != null && cached.file_path.length > 0) {
            val fp: String = cached.file_path
            playLocalAudio(fp, fun(): Unit {})
            return
        }
    }
     catch (e: Throwable) {
        val msg = if (e != null) {
            ("" + e)
        } else {
            "null"
        }
        console.warn("[TtsService] speakAdhocViaApi 查缓存失败: " + msg, " at services/TtsService.uts:173")
    }
    val textCopy: String = text
    val hashCopy: String = hash
    callMinimaxTts(text, fun(url: String): Unit {
        downloadTtsAudio(url, hashCopy, fun(localPath: String): Unit {
            playLocalAudio(localPath, fun(): Unit {})
        }
        , fun(errMsg: String): Unit {
            console.warn("[TtsService] speakAdhocViaApi download 失败: " + errMsg, " at services/TtsService.uts:184")
            speakAdhocSystem(textCopy)
        }
        )
    }
    , fun(errMsg: String): Unit {
        console.warn("[TtsService] speakAdhocViaApi tts 失败: " + errMsg, " at services/TtsService.uts:190")
        speakAdhocSystem(textCopy)
    }
    )
}
fun playLocalAudio(filePath: String, onEnd: () -> Unit): Unit {
    try {
        val cbs = AudioPlayerCallbacks(onCompletion = fun(): Unit {
            onEnd()
        }
        , onError = fun(err): Unit {
            val msg = if (err != null) {
                ("" + err)
            } else {
                "null"
            }
            console.warn("[TtsService] playLocalAudio onError: " + msg, " at services/TtsService.uts:202")
        }
        , onProgress = fun(_cur, _total): Unit {})
        play(filePath, cbs)
    }
     catch (e: Throwable) {
        val msg = if (e != null) {
            ("" + e)
        } else {
            "null"
        }
        console.warn("[TtsService] playLocalAudio 失败: " + msg, " at services/TtsService.uts:209")
    }
}
fun getAdjustedVolume(): Number {
    var vol: Number = getInt("tts_volume", 70) / 100
    if (isNightTime()) {
        vol *= 0.5
    }
    return vol
}
fun isNightTime(): Boolean {
    val h = currentHour()
    return h >= 22 || h < 7
}
fun isSystemMuted(): Boolean {
    return !getBool("tts_enabled", true)
}
open class AppForegroundInfo (
    @JsonNotNull
    open var packageName: String,
    @JsonNotNull
    open var startTime: Number,
    @JsonNotNull
    open var continuousMs: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AppForegroundInfo", "uni_modules/uts-usage-stats/utssdk/app-android/UsageTypes.uts", 2, 13)
    }
}
open class UsageStatEntry (
    @JsonNotNull
    open var packageName: String,
    @JsonNotNull
    open var totalTimeInForeground: Number,
    @JsonNotNull
    open var lastTimeUsed: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UsageStatEntry", "uni_modules/uts-usage-stats/utssdk/app-android/UsageTypes.uts", 7, 13)
    }
}
open class UsageMonitorCallbacks (
    open var onAppSwitch: (fromPkg: String, toPkg: String) -> Unit,
    open var onAppDurationTrigger: (info: AppForegroundInfo) -> Unit,
    open var onUsageTick: (pkg: String, deltaSec: Number) -> Unit,
    open var onTimePeriodTrigger: (periodName: String) -> Unit,
    open var onDayRollover: (oldDate: String, newDate: String) -> Unit,
    open var onPermissionMissing: () -> Unit,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UsageMonitorCallbacks", "uni_modules/uts-usage-stats/utssdk/app-android/UsageTypes.uts", 12, 13)
    }
}
fun insertHeartbeat(status: String): Unit {
    val row = SqlRow(columns = _uA(
        "service_status",
        "timestamp"
    ), values = _uA(
        status,
        Date.now()
    ))
    dbManager.insert("heartbeat_logs", row)
}
val NOTIFICATION_ID: Int = 2
val CHANNEL_ID: String = "micro_habit_usage_stats"
val FOREGROUND_TYPE_SPECIAL_USE: Int = 108
val WAKE_LOCK_TAG: String = "MicroHabit:UsageStats"
val SERVICE_CLASS_NAME: String = "uts.sdk.modules.utsUsageStats.UsageStatsForegroundService"
val DEFAULT_POLL_INTERVAL_MS: Long = 600000
var cachedCbs: UsageMonitorCallbacks? = null
var thresholdMs: Number = 3600000
@JvmField
var pollIntervalMs: Number = 600000
var serviceInstance: UsageStatsForegroundService? = null
var lastReconcileTime: Long = 0
@JvmField
var lastTickTimeMs: Long = 0
@JvmField
var triggerEnabled: Boolean = false
fun getSelfPackageNameInternal(): String {
    try {
        val ctx = UTSAndroid.getAppContext()
        if (ctx == null) {
            return ""
        }
        val name = ctx.getPackageName() as String
        if (name == null) {
            return ""
        }
        return name
    }
     catch (_: Throwable) {
        return ""
    }
}
fun currentDateString(): String {
    val d = Date()
    val y = d.getFullYear()
    val m = ("" + (d.getMonth() + 1)).padStart(2, "0")
    val day = ("" + d.getDate()).padStart(2, "0")
    return y + "-" + m + "-" + day
}
fun startOfTodayMsLong(): Long {
    val d = Date()
    d.setHours(0, 0, 0, 0)
    return d.getTime().toLong()
}
fun isInBlacklist(pkg: String, selfPkg: String): Boolean {
    return shouldFilterPackage(pkg, selfPkg)
}
fun isUsagePermissionGrantedByContext(ctx: Context): Boolean {
    try {
        val appOps = ctx.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        if (appOps == null) {
            return false
        }
        val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), ctx.getPackageName() as String)
        return mode == AppOpsManager.MODE_ALLOWED
    }
     catch (_: Throwable) {
        return false
    }
}
fun makeServiceIntent(ctx: Context): Intent {
    val intent: Intent = Intent()
    intent.setClassName(ctx, SERVICE_CLASS_NAME)
    return intent
}
val PREFS_NAME: String = "micro_habit_prefs"
val KEY_TRIGGER_ENABLED: String = "trigger_enabled"
val KEY_POLL_THRESHOLD_MS: String = "poll_threshold_ms"
val KEY_POLL_INTERVAL_MS: String = "poll_interval_ms"
fun getPrefs(): SharedPreferences? {
    try {
        val ctx = UTSAndroid.getAppContext()
        if (ctx == null) {
            return null
        }
        return ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
     catch (_: Throwable) {
        return null
    }
}
fun loadPrefsFromDisk(): Unit {
    try {
        val prefs = getPrefs()
        if (prefs == null) {
            return
        }
        triggerEnabled = prefs.getBoolean(KEY_TRIGGER_ENABLED, false)
        val defaultThresholdMs: Number = 3600000
        thresholdMs = prefs.getLong(KEY_POLL_THRESHOLD_MS, defaultThresholdMs.toLong()) as Number
        pollIntervalMs = prefs.getLong(KEY_POLL_INTERVAL_MS, DEFAULT_POLL_INTERVAL_MS) as Number
    }
     catch (_: Throwable) {}
}
fun savePrefsToDisk(triggerEnabledIn: Boolean, thresholdMsIn: Number, pollIntervalMsIn: Number): Unit {
    try {
        val prefs = getPrefs()
        if (prefs == null) {
            return
        }
        val editor = prefs.edit()
        editor.putBoolean(KEY_TRIGGER_ENABLED, triggerEnabledIn)
        editor.putLong(KEY_POLL_THRESHOLD_MS, thresholdMsIn.toLong())
        editor.putLong(KEY_POLL_INTERVAL_MS, pollIntervalMsIn.toLong())
        editor.apply()
    }
     catch (_: Throwable) {}
}
open class UsageStatsForegroundService : Service, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UsageStatsForegroundService", "uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts", 138, 14)
    }
    private var handler: Handler? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var lastTickTime: Long = 0
    private var todayDate: String = ""
    private var lastPeriod: String = ""
    private var started: Boolean = false
    private var usm: UsageStatsManager? = null
    private var selfPkgCache: String = ""
    constructor() : super() {}
    override fun onCreate(): Unit {
        try {
            super.onCreate()
            serviceInstance = this
            loadPrefsFromDisk()
            this.selfPkgCache = getSelfPackageNameInternal()
            this.todayDate = currentDateString()
            this.tryRefreshUsm()
            this.createChannel()
            this.startForegroundCompat()
            this.acquireWakeLock()
            this.startPolling()
        }
         catch (e: Throwable) {
            console.error("[UsageStats] onCreate THREW: " + JSON.stringify(e), " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:175")
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!this.started) {
            this.started = true
            if (this.handler != null) {
                this.handler!!!!.removeCallbacksAndMessages(null)
            }
            this.scheduleNextTick()
        }
        return Service.START_STICKY
    }
    override fun onDestroy(): Unit {
        try {
            insertHeartbeat("stopped")
        }
         catch (e: Throwable) {
            console.warn("[UsageStats] heartbeat onDestroy write failed: " + JSON.stringify(e), " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:193")
        }
        this.stopPolling()
        this.releaseWakeLock()
        serviceInstance = null
        false
        super.onDestroy()
    }
    override fun onBind(intent: Intent?): android.os.IBinder? {
        return null
    }
    public open fun stopPolling(): Unit {
        this.started = false
        if (this.handler != null) {
            this.handler!!!!.removeCallbacksAndMessages(null)
            this.handler = null
        }
    }
    public open fun runImmediatePoll(): Unit {
        if (!this.started) {
            return
        }
        try {
            this.doPoll()
        }
         catch (e: Throwable) {
            console.warn("[UsageStats] immediate poll error: " + JSON.stringify(e), " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:221")
        }
    }
    public open fun rescheduleTick(): Unit {
        if (!this.started) {
            return
        }
        if (this.handler != null) {
            this.handler!!!!.removeCallbacksAndMessages(null)
        }
        this.scheduleNextTick()
    }
    private fun createChannel(): Unit {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        try {
            val mgr = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (mgr == null) {
                return
            }
            val ch = NotificationChannel(CHANNEL_ID, "微习惯健康守护", NotificationManager.IMPORTANCE_LOW)
            ch.setDescription("前台使用情况监听")
            mgr.createNotificationChannel(ch)
        }
         catch (_: Throwable) {}
    }
    private fun startForegroundCompat(): Unit {
        val notif = this.buildNotification()
        if (Build.VERSION.SDK_INT >= 30) {
            this.startForeground(NOTIFICATION_ID, notif, FOREGROUND_TYPE_SPECIAL_USE)
        } else {
            this.startForeground(NOTIFICATION_ID, notif)
        }
    }
    private fun buildNotification(): Notification {
        return Notification.Builder(this, CHANNEL_ID).setContentTitle("微习惯健康伴侣").setContentText("正在守护你的健康").setSmallIcon(android.R.drawable.ic_popup_reminder).setOngoing(true).build()
    }
    private fun acquireWakeLock(): Unit {
        if (this.wakeLock != null) {
            try {
                this.wakeLock!!!!.release()
            }
             catch (_: Throwable) {}
            this.wakeLock = null
        }
        try {
            val pm = this.getSystemService(Context.POWER_SERVICE) as PowerManager
            if (pm == null) {
                return
            }
            this.wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG)
            if (this.wakeLock != null) {
                this.wakeLock!!!!.acquire(1800000.toLong())
            }
        }
         catch (_: Throwable) {}
    }
    private fun releaseWakeLock(): Unit {
        if (this.wakeLock != null) {
            try {
                this.wakeLock!!!!.release()
            }
             catch (_: Throwable) {}
            this.wakeLock = null
        }
    }
    private fun startPolling(): Unit {
        if (this.started) {
            return
        }
        this.handler = Handler(Looper.getMainLooper())
        this.lastTickTime = System.currentTimeMillis()
        lastTickTimeMs = this.lastTickTime
        this.started = true
        true
        this.doPoll()
        this.scheduleNextTick()
    }
    private fun scheduleNextTick(): Unit {
        if (this.handler == null) {
            return
        }
        val self = this
        this.handler!!!!.postDelayed(fun(): Unit {
            self.tick()
        }
        , pollIntervalMs.toLong())
    }
    private fun tick(): Unit {
        if (!this.started) {
            return
        }
        try {
            insertHeartbeat("alive")
        }
         catch (e: Throwable) {
            console.warn("[UsageStats] heartbeat tick write failed: " + JSON.stringify(e), " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:309")
        }
        try {
            this.doPoll()
        }
         catch (e: Throwable) {
            console.warn("[UsageStats] poll error: " + JSON.stringify(e), " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:314")
        }
        this.lastTickTime = System.currentTimeMillis()
        lastTickTimeMs = this.lastTickTime
        this.acquireWakeLock()
        this.scheduleNextTick()
    }
    private fun doPoll(): Unit {
        val nowMs: Long = System.currentTimeMillis()
        if (!triggerEnabled) {
            return
        }
        if (this.usm == null) {
            this.tryRefreshUsm()
            if (this.usm == null) {
                return
            }
        }
        val ctx: Context = this
        if (!isUsagePermissionGrantedByContext(ctx)) {
            val cbs = cachedCbs
            if (cbs != null) {
                try {
                    cbs.onPermissionMissing()
                }
                 catch (_: Throwable) {}
            }
            return
        }
        try {
            val todayStart: Long = startOfTodayMsLong()
            val stats = this.usm!!!!.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, todayStart, nowMs)
            val sz: Int = stats.size
            if (sz > 0) {
                val cbs = cachedCbs
                run {
                    var i: Int = 0
                    while(i < sz){
                        val s: UsageStats = stats.get(i)
                        if (s == null) {
                            i++
                            continue
                        }
                        val pkg: String = s.getPackageName() as String
                        if (isInBlacklist(pkg, this.selfPkgCache)) {
                            i++
                            continue
                        }
                        val totalMs: Long = s.getTotalTimeInForeground() as Number as Long
                        if (totalMs < 1) {
                            i++
                            continue
                        }
                        if (cbs != null) {
                            val totalSec: Number = Math.floor(totalMs / 1000)
                            try {
                                cbs.onUsageTick(pkg, totalSec)
                            }
                             catch (_: Throwable) {}
                        }
                        if (totalMs >= thresholdMs) {
                            if (cbs != null) {
                                val info = AppForegroundInfo(packageName = pkg, startTime = nowMs, continuousMs = totalMs)
                                try {
                                    cbs.onAppDurationTrigger(info)
                                }
                                 catch (_: Throwable) {}
                            }
                        }
                        i++
                    }
                }
            }
        }
         catch (e: Throwable) {
            console.warn("[UsageStats] queryUsageStats 失败: " + JSON.stringify(e), " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:376")
        }
        this.checkTimePeriod(nowMs)
        val rolled: Boolean = this.checkDayRollover()
        this.maybeReconcile(nowMs)
        this.lastTickTime = nowMs
    }
    private fun maybeReconcile(now: Long): Unit {
        if (lastReconcileTime < 1) {
            lastReconcileTime = now
            return
        }
        val interval: Long = 1800000
        if (now - lastReconcileTime < interval) {
            return
        }
        lastReconcileTime = now
        try {
            val todayStart: Long = startOfTodayMsLong()
            val usm = this.usm
            if (usm == null) {
                return
            }
            val stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, todayStart, now)
            val sz: Int = stats.size
            if (sz < 1) {
                console.log("[UsageStats] reconcile: no system stats", " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:401")
                return
            }
            console.log("[UsageStats] reconcile: system has " + sz + " apps, top 3:", " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:404")
            var n: Int = 0
            run {
                var i: Int = 0
                while(i < sz && n < 3){
                    val s: UsageStats = stats.get(i)
                    if (s == null) {
                        i++
                        continue
                    }
                    val pkg: String = s.getPackageName() as String
                    if (isInBlacklist(pkg, this.selfPkgCache)) {
                        i++
                        continue
                    }
                    val totalMs: Long = s.getTotalTimeInForeground() as Number as Long
                    console.log("[UsageStats]   " + pkg + " totalMs=" + totalMs, " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:412")
                    n++
                    i++
                }
            }
        }
         catch (e: Throwable) {
            console.warn("[UsageStats] reconcile failed: " + JSON.stringify(e), " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:416")
        }
    }
    private fun tryRefreshUsm(): Unit {
        try {
            this.usm = this.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        }
         catch (_: Throwable) {
            this.usm = null
        }
    }
    private fun checkTimePeriod(now: Long): Unit {
        val d = Date(now as Number)
        val h = d.getHours()
        var period: String = ""
        if (h == 9) {
            period = "morning"
        } else if (h == 12) {
            period = "noon"
        } else if (h == 15) {
            period = "afternoon"
        } else if (h == 18) {
            period = "evening"
        } else if (h == 22) {
            period = "night"
        }
        if (period.length > 0 && period != this.lastPeriod) {
            this.lastPeriod = period
            val cbs = cachedCbs
            if (cbs != null) {
                try {
                    cbs.onTimePeriodTrigger(period)
                }
                 catch (_: Throwable) {}
            }
        } else if (period.length < 1) {
            this.lastPeriod = ""
        }
    }
    private fun checkDayRollover(): Boolean {
        val today = currentDateString()
        if (today != this.todayDate) {
            val old = this.todayDate
            this.todayDate = today
            val cbs = cachedCbs
            if (cbs != null) {
                try {
                    cbs.onDayRollover(old, today)
                }
                 catch (_: Throwable) {}
            }
            return true
        }
        return false
    }
}
fun startUsageMonitor(cbs: UsageMonitorCallbacks, thresholdMsIn: Number, pollIntervalMsIn: Number, triggerEnabledIn: Boolean): Unit {
    cachedCbs = cbs
    if (thresholdMsIn > 0) {
        thresholdMs = thresholdMsIn
    }
    if (pollIntervalMsIn > 0) {
        pollIntervalMs = pollIntervalMsIn
    }
    triggerEnabled = triggerEnabledIn
    savePrefsToDisk(triggerEnabled, thresholdMs, pollIntervalMs)
    true
    try {
        val ctx = UTSAndroid.getAppContext()
        if (ctx == null) {
            return
        }
        ctx.startForegroundService(makeServiceIntent(ctx))
    }
     catch (e: Throwable) {
        console.warn("[UsageStats] startForegroundService 失败: " + JSON.stringify(e), " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:490")
    }
}
fun setTriggerEnabled(enabled: Boolean): Unit {
    triggerEnabled = enabled
    if (enabled) {
        val svc = serviceInstance
        if (svc != null) {
            try {
                svc.runImmediatePoll()
            }
             catch (_: Throwable) {}
        }
    }
}
fun setPollIntervalMs(msIn: Number): Unit {
    if (msIn < 1) {
        return
    }
    pollIntervalMs = msIn
    savePrefsToDisk(triggerEnabled, thresholdMs, pollIntervalMs)
    val svc = serviceInstance
    if (svc != null) {
        try {
            svc.rescheduleTick()
        }
         catch (_: Throwable) {}
    }
}
fun getCurrentPollIntervalMs(): Number {
    return pollIntervalMs
}
fun getLastTickTimeMs(): Number {
    return lastTickTimeMs as Number
}
fun stopUsageMonitor(): Unit {
    false
    cachedCbs = null
    val svc = serviceInstance
    if (svc != null) {
        try {
            svc.stopPolling()
        }
         catch (_: Throwable) {}
    }
    try {
        val ctx = UTSAndroid.getAppContext()
        if (ctx != null) {
            ctx.stopService(makeServiceIntent(ctx))
        }
    }
     catch (_: Throwable) {}
}
fun isUsagePermissionGranted(): Boolean {
    try {
        val ctx = UTSAndroid.getAppContext()
        if (ctx == null) {
            return false
        }
        return isUsagePermissionGrantedByContext(ctx)
    }
     catch (_: Throwable) {
        return false
    }
}
fun queryUsageStatsOnce(beginMs: Number, endMs: Number): UTSArray<UsageStatEntry> {
    val result: UTSArray<UsageStatEntry> = _uA()
    try {
        val ctx = UTSAndroid.getAppContext()
        if (ctx == null) {
            return result
        }
        if (!isUsagePermissionGrantedByContext(ctx)) {
            return result
        }
        val usm = ctx.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        if (usm == null) {
            return result
        }
        val begin: Long = beginMs.toLong()
        val end: Long = endMs.toLong()
        val stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, begin, end)
        val selfPkg = getSelfPackageNameInternal()
        val n: Int = stats.size
        run {
            var i: Int = 0
            while(i < n){
                val s = stats.get(i)
                if (s == null) {
                    i++
                    continue
                }
                val pkg = s.getPackageName() as String
                if (pkg == null || pkg.length < 1) {
                    i++
                    continue
                }
                if (shouldFilterPackage(pkg, selfPkg)) {
                    i++
                    continue
                }
                val entry = UsageStatEntry(packageName = pkg, totalTimeInForeground = s.getTotalTimeInForeground() as Number, lastTimeUsed = s.getLastTimeUsed() as Number)
                result.push(entry)
                i++
            }
        }
    }
     catch (e: Throwable) {
        console.warn("[UsageStats] queryUsageStatsOnce 失败: " + JSON.stringify(e), " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsForegroundService.uts:593")
    }
    return result
}
fun openUsageAccessSettings(): Unit {
    try {
        val ctx = UTSAndroid.getAppContext()
        if (ctx == null) {
            return
        }
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ctx.startActivity(intent)
    }
     catch (e: Throwable) {
        console.warn("[UsageStats] openUsageAccessSettings 失败: " + JSON.stringify(e), " at uni_modules/uts-usage-stats/utssdk/app-android/UsageStatsBridge.uts:16")
    }
}
typealias TriggerLevel__1 = String
open class OverlayConfig (
    @JsonNotNull
    open var level: TriggerLevel__1,
    @JsonNotNull
    open var actionName: String,
    @JsonNotNull
    open var ttsText: String,
    @JsonNotNull
    open var durationMs: Number,
    open var lottieAssetPath: String? = null,
    @JsonNotNull
    open var encourageText: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OverlayConfig", "uni_modules/uts-floating-overlay/utssdk/app-android/FloatingOverlay.uts", 18, 13)
    }
}
open class OverlayCallbacks (
    open var onAgree: () -> Unit,
    open var onSelfReported: () -> Unit,
    open var onBusyRemindLater: () -> Unit,
    open var onSkipDuringExec: () -> Unit,
    open var onCountdownTick: (remainingMs: Number) -> Unit,
    open var onCountdownFinish: () -> Unit,
    open var onPartialCompletion: (completedMs: Number, totalMs: Number) -> Unit,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OverlayCallbacks", "uni_modules/uts-floating-overlay/utssdk/app-android/FloatingOverlay.uts", 26, 13)
    }
}
var overlayManager: FloatingOverlayManager? = null
fun showOverlay(config: OverlayConfig, callbacks: OverlayCallbacks): Unit {
    if (overlayManager == null) {
        overlayManager = FloatingOverlayManager()
    }
    overlayManager!!!!.show(config, callbacks)
}
fun dismissOverlay(): Unit {
    if (overlayManager != null) {
        overlayManager!!!!.dismiss()
        overlayManager = null
    }
}
fun hasOverlayPermission(): Boolean {
    if (Build.VERSION.SDK_INT >= 23) {
        try {
            val ctx = UTSAndroid.getAppContext()
            if (ctx != null) {
                return android.provider.Settings.canDrawOverlays(ctx)
            }
        }
         catch (_: Throwable) {}
    }
    return true
}
fun requestOverlayPermission(): Unit {
    try {
        if (Build.VERSION.SDK_INT >= 23) {
            val ctx = UTSAndroid.getAppContext()
            if (ctx != null) {
                val intent = android.content.Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ctx.startActivity(intent)
            }
        }
    }
     catch (_: Throwable) {}
}
open class FloatingOverlayManager : IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("FloatingOverlayManager", "uni_modules/uts-floating-overlay/utssdk/app-android/FloatingOverlay.uts", 76, 7)
    }
    private var wm: WindowManager? = null
    private var rootView: FrameLayout? = null
    private var showing: Boolean = false
    private var handler: Handler? = null
    private var remainingMs: Number = 0
    private var totalMs: Number = 0
    private var callbacks: OverlayCallbacks? = null
    private var config: OverlayConfig? = null
    private var state: String = "hidden"
    constructor(){}
    open fun show(config: OverlayConfig, callbacks: OverlayCallbacks): Unit {
        if (this.showing) {
            return
        }
        if (this.rootView != null) {
            try {
                this.wm?.removeView(this.rootView!!)
            }
             catch (_: Throwable) {}
            this.rootView = null
            this.wm = null
        }
        this.callbacks = callbacks
        this.config = config
        this.totalMs = config.durationMs
        this.remainingMs = config.durationMs
        this.state = "selecting"
        val ctx = UTSAndroid.getAppContext()
        if (ctx == null) {
            return
        }
        this.wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (this.wm == null) {
            return
        }
        this.rootView = this.buildSelectPhase(ctx, config, callbacks)
        if (this.rootView == null) {
            return
        }
        val params = this.buildParams(config)
        if (params == null) {
            return
        }
        try {
            this.wm!!.addView(this.rootView!!, params)
            this.showing = true
        }
         catch (_: Throwable) {}
    }
    open fun dismiss(): Unit {
        this.showing = false
        this.state = "hidden"
        this.stopCountdown()
        if (this.rootView != null) {
            try {
                this.wm?.removeView(this.rootView!!)
            }
             catch (e: Throwable) {
                console.warn("[FloatingOverlay] removeView 失败: " + JSON.stringify(e), " at uni_modules/uts-floating-overlay/utssdk/app-android/FloatingOverlay.uts:137")
            }
            this.rootView = null
        }
        this.wm = null
        this.callbacks = null
        this.config = null
    }
    open fun isShowing(): Boolean {
        return this.showing
    }
    private fun buildSelectPhase(ctx: Context, config: OverlayConfig, cbs: OverlayCallbacks): FrameLayout? {
        try {
            val root = FrameLayout(ctx)
            val bgColor = if (config.level == "gentle") {
                Color.parseColor("#B3000000")
            } else {
                Color.parseColor("#99000000")
            }
            root.setBackgroundColor(bgColor)
            val content = LinearLayout(ctx)
            content.orientation = LinearLayout.VERTICAL
            content.gravity = Gravity.CENTER
            content.setPadding(dp(20), dp(20), dp(20), dp(20))
            val title = TextView(ctx)
            title.setText(config.actionName)
            title.setTextSize(22.0.toFloat())
            title.setTextColor(Color.WHITE)
            title.gravity = Gravity.CENTER
            title.setPadding(0, 0, 0, dp(8))
            content.addView(title)
            val ttsView = TextView(ctx)
            ttsView.setText(config.ttsText)
            ttsView.setTextSize(15.0.toFloat())
            ttsView.setTextColor(Color.parseColor("#DDDDDD"))
            ttsView.gravity = Gravity.CENTER
            ttsView.setPadding(0, 0, 0, dp(16))
            content.addView(ttsView)
            val btnRow1 = LinearLayout(ctx)
            btnRow1.orientation = LinearLayout.HORIZONTAL
            btnRow1.gravity = Gravity.CENTER
            val selfBtn = Button(ctx)
            selfBtn.setText("我做过了")
            selfBtn.setTextSize(14.0.toFloat())
            selfBtn.setTextColor(Color.WHITE)
            selfBtn.setBackgroundColor(Color.parseColor("#555555"))
            selfBtn.setPadding(dp(12), dp(8), dp(12), dp(8))
            val self = this
            selfBtn.setOnClickListener(fun(v: View): Unit {
                cbs.onSelfReported()
                self.dismiss()
            }
            )
            btnRow1.addView(selfBtn)
            val gap = TextView(ctx)
            gap.setText("   ")
            btnRow1.addView(gap)
            val busyBtn = Button(ctx)
            busyBtn.setText("现在忙，晚点提醒我")
            busyBtn.setTextSize(14.0.toFloat())
            busyBtn.setTextColor(Color.WHITE)
            busyBtn.setBackgroundColor(Color.parseColor("#555555"))
            busyBtn.setPadding(dp(12), dp(8), dp(12), dp(8))
            busyBtn.setOnClickListener(fun(v: View): Unit {
                cbs.onBusyRemindLater()
                self.dismiss()
            }
            )
            btnRow1.addView(busyBtn)
            content.addView(btnRow1)
            val spacer2 = TextView(ctx)
            spacer2.setText("")
            spacer2.setPadding(0, dp(12), 0, 0)
            content.addView(spacer2)
            val agreeBtn = Button(ctx)
            agreeBtn.setText("好啊")
            agreeBtn.setTextSize(18.0.toFloat())
            agreeBtn.setTextColor(Color.WHITE)
            agreeBtn.setBackgroundColor(Color.parseColor("#27AE60"))
            agreeBtn.setPadding(dp(40), dp(12), dp(40), dp(12))
            agreeBtn.setOnClickListener(fun(v: View): Unit {
                cbs.onAgree()
                val r = self.rootView
                if (r != null) {
                    r.removeAllViews()
                }
                self.switchToExec(ctx, config, cbs)
            }
            )
            content.addView(agreeBtn)
            root.addView(content)
            return root
        }
         catch (_: Throwable) {
            return null
        }
    }
    private fun switchToExec(ctx: Context, config: OverlayConfig, cbs: OverlayCallbacks): Unit {
        this.state = "executing"
        val root = this.rootView
        if (root == null) {
            return
        }
        val content = LinearLayout(ctx)
        content.orientation = LinearLayout.VERTICAL
        content.gravity = Gravity.CENTER
        content.setPadding(dp(20), dp(20), dp(20), dp(20))
        val title = TextView(ctx)
        title.setText(config.actionName)
        title.setTextSize(22.0.toFloat())
        title.setTextColor(Color.WHITE)
        title.gravity = Gravity.CENTER
        title.setPadding(0, 0, 0, dp(16))
        content.addView(title)
        val ttsView = TextView(ctx)
        ttsView.setText(config.ttsText)
        ttsView.setTextSize(15.0.toFloat())
        ttsView.setTextColor(Color.parseColor("#DDDDDD"))
        ttsView.gravity = Gravity.CENTER
        ttsView.setPadding(0, 0, 0, dp(20))
        content.addView(ttsView)
        val progressBar = ProgressBar(ctx, null, android.R.attr.progressBarStyleHorizontal)
        progressBar.setMax(config.durationMs.toInt())
        progressBar.setProgress(0)
        progressBar.setPadding(0, 0, 0, dp(8))
        content.addView(progressBar)
        val countdownText = TextView(ctx)
        countdownText.setText("" + Math.ceil(config.durationMs / 1000) + "s")
        countdownText.setTextSize(18.0.toFloat())
        countdownText.setTextColor(Color.WHITE)
        countdownText.gravity = Gravity.CENTER
        countdownText.setPadding(0, 0, 0, dp(20))
        content.addView(countdownText)
        val skipBtn = Button(ctx)
        skipBtn.setText("跳过")
        skipBtn.setTextSize(14.0.toFloat())
        skipBtn.setTextColor(Color.WHITE)
        skipBtn.setBackgroundColor(Color.parseColor("#E74C3C"))
        skipBtn.setPadding(dp(20), dp(8), dp(20), dp(8))
        val self = this
        skipBtn.setOnClickListener(fun(v: View): Unit {
            cbs.onSkipDuringExec()
            self.dismiss()
        }
        )
        content.addView(skipBtn)
        root.addView(content)
        this.startCountdown(config.durationMs, cbs, progressBar, countdownText)
    }
    private fun startCountdown(total: Number, cbs: OverlayCallbacks, bar: ProgressBar, text: TextView): Unit {
        this.remainingMs = total
        this.handler = Handler(Looper.getMainLooper())
        val self = this
        this.handler!!!!.postDelayed(fun(): Unit {
            self.remainingMs -= 100
            val secs = Math.max(0, Math.ceil(self.remainingMs / 1000))
            bar.setProgress((total - self.remainingMs).toInt())
            text.setText("" + secs + "s")
            if (self.remainingMs <= 0) {
                cbs.onCountdownTick(0)
                self.showDone(cbs)
            } else {
                cbs.onCountdownTick(self.remainingMs)
                self.startCountdown(total, cbs, bar, text)
            }
        }
        , 100)
    }
    private fun showDone(cbs: OverlayCallbacks): Unit {
        if (this.rootView == null) {
            return
        }
        this.stopCountdown()
        val root = this.rootView!!
        root.removeAllViews()
        val ctx = UTSAndroid.getAppContext()
        if (ctx == null) {
            return
        }
        val content = LinearLayout(ctx)
        content.orientation = LinearLayout.VERTICAL
        content.gravity = Gravity.CENTER
        content.setPadding(dp(30), dp(40), dp(30), dp(40))
        val checkMark = TextView(ctx)
        checkMark.setText("✓")
        checkMark.setTextSize(48.0.toFloat())
        checkMark.setTextColor(Color.parseColor("#2ECC71"))
        checkMark.gravity = Gravity.CENTER
        content.addView(checkMark)
        val doneText = TextView(ctx)
        val encourage = if (this.config != null) {
            this.config!!!!.encourageText
        } else {
            "又完成一次！"
        }
        doneText.setText(encourage)
        doneText.setTextSize(20.0.toFloat())
        doneText.setTextColor(Color.WHITE)
        doneText.gravity = Gravity.CENTER
        doneText.setPadding(0, dp(12), 0, 0)
        content.addView(doneText)
        root.addView(content)
        cbs.onCountdownFinish()
        val self = this
        Handler(Looper.getMainLooper()).postDelayed(fun(): Unit {
            self.dismiss()
        }
        , 1500)
    }
    private fun stopCountdown(): Unit {
        if (this.handler != null) {
            this.handler!!!!.removeCallbacksAndMessages(null)
            this.handler = null
        }
    }
    private fun buildParams(config: OverlayConfig): WindowManagerLayoutParams? {
        try {
            val ctx = UTSAndroid.getAppContext()
            if (ctx == null) {
                return null
            }
            val displayMetrics = ctx.getResources().getDisplayMetrics()
            val w = if (config.level == "gentle") {
                (displayMetrics.widthPixels * 0.7).toInt()
            } else {
                WindowManagerLayoutParams.MATCH_PARENT
            }
            val h = WindowManagerLayoutParams.WRAP_CONTENT
            val flags = WindowManagerLayoutParams.FLAG_NOT_FOCUSABLE or WindowManagerLayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManagerLayoutParams.FLAG_LAYOUT_NO_LIMITS
            val params = WindowManagerLayoutParams(w, h, WindowManagerLayoutParams.TYPE_APPLICATION_OVERLAY, flags, PixelFormat.TRANSLUCENT)
            params.gravity = Gravity.CENTER
            return params
        }
         catch (_: Throwable) {
            return null
        }
    }
    private fun dp(value: Number): Int {
        try {
            val ctx = UTSAndroid.getAppContext()
            if (ctx != null) {
                val density = ctx.getResources().getDisplayMetrics().density
                return (value * density + 0.5).toInt()
            }
        }
         catch (_: Throwable) {}
        return value.toInt()
    }
}
var pendingActionId: String = ""
fun setActionIdForNextPage(id: String): Unit {
    pendingActionId = id
}
fun takeActionId(): String {
    val id = pendingActionId
    pendingActionId = ""
    return id
}
fun setAdhocForNextPage(text: String): Unit {
    text
}
typealias LlmHistoryStage = String
open class ParsedLlmResult (
    open var adhocText: String? = null,
    open var stateDescription: String? = null,
    open var suggestedRule: EffectiveTriggerRule? = null,
    open var reasoning: String? = null,
    open var decision: String? = null,
    open var targetRuleId: Number? = null,
    open var confidence: Number? = null,
    open var alternatives: UTSArray<Any>? = null,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ParsedLlmResult", "models/LlmHistoryEntry.uts", 3, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ParsedLlmResultReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ParsedLlmResultReactiveObject : ParsedLlmResult, IUTSReactive<ParsedLlmResult> {
    override var __v_raw: ParsedLlmResult
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ParsedLlmResult, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(adhocText = __v_raw.adhocText, stateDescription = __v_raw.stateDescription, suggestedRule = __v_raw.suggestedRule, reasoning = __v_raw.reasoning, decision = __v_raw.decision, targetRuleId = __v_raw.targetRuleId, confidence = __v_raw.confidence, alternatives = __v_raw.alternatives) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ParsedLlmResultReactiveObject {
        return ParsedLlmResultReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var adhocText: String?
        get() {
            return _tRG(__v_raw, "adhocText", __v_raw.adhocText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("adhocText")) {
                return
            }
            val oldValue = __v_raw.adhocText
            __v_raw.adhocText = value
            _tRS(__v_raw, "adhocText", oldValue, value)
        }
    override var stateDescription: String?
        get() {
            return _tRG(__v_raw, "stateDescription", __v_raw.stateDescription, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("stateDescription")) {
                return
            }
            val oldValue = __v_raw.stateDescription
            __v_raw.stateDescription = value
            _tRS(__v_raw, "stateDescription", oldValue, value)
        }
    override var suggestedRule: EffectiveTriggerRule?
        get() {
            return _tRG(__v_raw, "suggestedRule", __v_raw.suggestedRule, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("suggestedRule")) {
                return
            }
            val oldValue = __v_raw.suggestedRule
            __v_raw.suggestedRule = value
            _tRS(__v_raw, "suggestedRule", oldValue, value)
        }
    override var reasoning: String?
        get() {
            return _tRG(__v_raw, "reasoning", __v_raw.reasoning, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("reasoning")) {
                return
            }
            val oldValue = __v_raw.reasoning
            __v_raw.reasoning = value
            _tRS(__v_raw, "reasoning", oldValue, value)
        }
    override var decision: String?
        get() {
            return _tRG(__v_raw, "decision", __v_raw.decision, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("decision")) {
                return
            }
            val oldValue = __v_raw.decision
            __v_raw.decision = value
            _tRS(__v_raw, "decision", oldValue, value)
        }
    override var targetRuleId: Number?
        get() {
            return _tRG(__v_raw, "targetRuleId", __v_raw.targetRuleId, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("targetRuleId")) {
                return
            }
            val oldValue = __v_raw.targetRuleId
            __v_raw.targetRuleId = value
            _tRS(__v_raw, "targetRuleId", oldValue, value)
        }
    override var confidence: Number?
        get() {
            return _tRG(__v_raw, "confidence", __v_raw.confidence, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("confidence")) {
                return
            }
            val oldValue = __v_raw.confidence
            __v_raw.confidence = value
            _tRS(__v_raw, "confidence", oldValue, value)
        }
    override var alternatives: UTSArray<Any>?
        get() {
            return _tRG(__v_raw, "alternatives", __v_raw.alternatives, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("alternatives")) {
                return
            }
            val oldValue = __v_raw.alternatives
            __v_raw.alternatives = value
            _tRS(__v_raw, "alternatives", oldValue, value)
        }
}
open class LlmHistoryEntry (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var stage: LlmHistoryStage,
    @JsonNotNull
    open var contextJson: String,
    @JsonNotNull
    open var aiRawResponse: String,
    @JsonNotNull
    open var parsedResult: ParsedLlmResult,
    open var adhocText: String? = null,
    open var suggestedRule: EffectiveTriggerRule? = null,
    open var reasoning: String? = null,
    open var decision: String? = null,
    open var targetRuleId: Number? = null,
    open var userAccepted: Number? = null,
    @JsonNotNull
    open var confidence: Number,
    @JsonNotNull
    open var createdAt: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LlmHistoryEntry", "models/LlmHistoryEntry.uts", 13, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LlmHistoryEntryReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class LlmHistoryEntryReactiveObject : LlmHistoryEntry, IUTSReactive<LlmHistoryEntry> {
    override var __v_raw: LlmHistoryEntry
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LlmHistoryEntry, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, stage = __v_raw.stage, contextJson = __v_raw.contextJson, aiRawResponse = __v_raw.aiRawResponse, parsedResult = __v_raw.parsedResult, adhocText = __v_raw.adhocText, suggestedRule = __v_raw.suggestedRule, reasoning = __v_raw.reasoning, decision = __v_raw.decision, targetRuleId = __v_raw.targetRuleId, userAccepted = __v_raw.userAccepted, confidence = __v_raw.confidence, createdAt = __v_raw.createdAt) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LlmHistoryEntryReactiveObject {
        return LlmHistoryEntryReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var stage: LlmHistoryStage
        get() {
            return _tRG(__v_raw, "stage", __v_raw.stage, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("stage")) {
                return
            }
            val oldValue = __v_raw.stage
            __v_raw.stage = value
            _tRS(__v_raw, "stage", oldValue, value)
        }
    override var contextJson: String
        get() {
            return _tRG(__v_raw, "contextJson", __v_raw.contextJson, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("contextJson")) {
                return
            }
            val oldValue = __v_raw.contextJson
            __v_raw.contextJson = value
            _tRS(__v_raw, "contextJson", oldValue, value)
        }
    override var aiRawResponse: String
        get() {
            return _tRG(__v_raw, "aiRawResponse", __v_raw.aiRawResponse, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("aiRawResponse")) {
                return
            }
            val oldValue = __v_raw.aiRawResponse
            __v_raw.aiRawResponse = value
            _tRS(__v_raw, "aiRawResponse", oldValue, value)
        }
    override var parsedResult: ParsedLlmResult
        get() {
            return _tRG(__v_raw, "parsedResult", __v_raw.parsedResult, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("parsedResult")) {
                return
            }
            val oldValue = __v_raw.parsedResult
            __v_raw.parsedResult = value
            _tRS(__v_raw, "parsedResult", oldValue, value)
        }
    override var adhocText: String?
        get() {
            return _tRG(__v_raw, "adhocText", __v_raw.adhocText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("adhocText")) {
                return
            }
            val oldValue = __v_raw.adhocText
            __v_raw.adhocText = value
            _tRS(__v_raw, "adhocText", oldValue, value)
        }
    override var suggestedRule: EffectiveTriggerRule?
        get() {
            return _tRG(__v_raw, "suggestedRule", __v_raw.suggestedRule, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("suggestedRule")) {
                return
            }
            val oldValue = __v_raw.suggestedRule
            __v_raw.suggestedRule = value
            _tRS(__v_raw, "suggestedRule", oldValue, value)
        }
    override var reasoning: String?
        get() {
            return _tRG(__v_raw, "reasoning", __v_raw.reasoning, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("reasoning")) {
                return
            }
            val oldValue = __v_raw.reasoning
            __v_raw.reasoning = value
            _tRS(__v_raw, "reasoning", oldValue, value)
        }
    override var decision: String?
        get() {
            return _tRG(__v_raw, "decision", __v_raw.decision, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("decision")) {
                return
            }
            val oldValue = __v_raw.decision
            __v_raw.decision = value
            _tRS(__v_raw, "decision", oldValue, value)
        }
    override var targetRuleId: Number?
        get() {
            return _tRG(__v_raw, "targetRuleId", __v_raw.targetRuleId, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("targetRuleId")) {
                return
            }
            val oldValue = __v_raw.targetRuleId
            __v_raw.targetRuleId = value
            _tRS(__v_raw, "targetRuleId", oldValue, value)
        }
    override var userAccepted: Number?
        get() {
            return _tRG(__v_raw, "userAccepted", __v_raw.userAccepted, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("userAccepted")) {
                return
            }
            val oldValue = __v_raw.userAccepted
            __v_raw.userAccepted = value
            _tRS(__v_raw, "userAccepted", oldValue, value)
        }
    override var confidence: Number
        get() {
            return _tRG(__v_raw, "confidence", __v_raw.confidence, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("confidence")) {
                return
            }
            val oldValue = __v_raw.confidence
            __v_raw.confidence = value
            _tRS(__v_raw, "confidence", oldValue, value)
        }
    override var createdAt: Number
        get() {
            return _tRG(__v_raw, "createdAt", __v_raw.createdAt, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("createdAt")) {
                return
            }
            val oldValue = __v_raw.createdAt
            __v_raw.createdAt = value
            _tRS(__v_raw, "createdAt", oldValue, value)
        }
}
open class LlmAcceptanceRate (
    @JsonNotNull
    open var total: Number,
    @JsonNotNull
    open var accepted: Number,
    @JsonNotNull
    open var rejected: Number,
    @JsonNotNull
    open var pending: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LlmAcceptanceRate", "database/LlmHistoryDao.uts", 5, 13)
    }
}
fun mapRow__5(row: Map<String, Any>): LlmHistoryEntry {
    val parsedJson = getStr(row, "parsed_result_json")
    var parsed = ParsedLlmResult()
    if (parsedJson != null && parsedJson.length > 0) {
        try {
            val p = UTSAndroid.consoleDebugError(JSON.parse(parsedJson), " at database/LlmHistoryDao.uts:16")
            if (p != null) {
                parsed = p as ParsedLlmResult
            }
        }
         catch (_: Throwable) {
            parsed = ParsedLlmResult()
        }
    }
    val ruleJson = getStrOrNull(row, "suggested_rule_json")
    var rule: EffectiveTriggerRule? = null
    if (ruleJson != null && ruleJson.length > 0) {
        try {
            val r = UTSAndroid.consoleDebugError(JSON.parse(ruleJson), " at database/LlmHistoryDao.uts:28")
            if (r != null) {
                rule = r as EffectiveTriggerRule
            }
        }
         catch (_: Throwable) {
            rule = null
        }
    }
    val targetRaw = row.get("target_rule_id")
    val targetRuleId: Number? = if (targetRaw == null) {
        null
    } else {
        getNum(row, "target_rule_id")
    }
    val acceptedRaw = row.get("user_accepted")
    val userAccepted: Number? = if (acceptedRaw == null) {
        null
    } else {
        getNum(row, "user_accepted")
    }
    return LlmHistoryEntry(id = getNum(row, "id"), stage = getStr(row, "stage") as LlmHistoryStage, contextJson = getStr(row, "context_json"), aiRawResponse = getStr(row, "ai_raw_response"), parsedResult = parsed, adhocText = getStrOrNull(row, "adhoc_text"), suggestedRule = rule, reasoning = getStrOrNull(row, "reasoning"), decision = getStrOrNull(row, "decision"), targetRuleId = targetRuleId, userAccepted = userAccepted, confidence = getNum(row, "confidence"), createdAt = getNum(row, "created_at"))
}
fun mapRows(rows: UTSArray<Map<String, Any>>): UTSArray<LlmHistoryEntry> {
    val result: UTSArray<LlmHistoryEntry> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            val r = rows[i]
            if (r == null) {
                i++
                continue
            }
            try {
                result.push(mapRow__5(r))
            }
             catch (e: Throwable) {
                console.warn("[LlmHistoryDao] mapRow skip dirty row, err: " + JSON.stringify(e), " at database/LlmHistoryDao.uts:63")
            }
            i++
        }
    }
    return result
}
fun insertHistory(stage: LlmHistoryStage, contextJson: String, aiRawResponse: String, parsedResult: ParsedLlmResult, adhocText: String?, suggestedRule: EffectiveTriggerRule?, reasoning: String?, confidence: Number = 0, decision: String? = null, targetRuleId: Number? = null, userAccepted: Number? = null, alternativesJson: String? = null): Number {
    val createdAt = Math.floor(Date.now() / 1000)
    val parsedJson = JSON.stringify(parsedResult)
    val ruleJson = if (suggestedRule == null) {
        null
    } else {
        JSON.stringify(suggestedRule)
    }
    val row = SqlRow(columns = _uA(
        "stage",
        "context_json",
        "ai_raw_response",
        "parsed_result_json",
        "adhoc_text",
        "suggested_rule_json",
        "reasoning",
        "confidence",
        "decision",
        "target_rule_id",
        "user_accepted",
        "alternatives_json",
        "created_at"
    ), values = _uA(
        stage,
        contextJson,
        aiRawResponse,
        parsedJson,
        adhocText,
        ruleJson,
        reasoning,
        confidence,
        decision,
        targetRuleId,
        userAccepted,
        alternativesJson,
        createdAt
    ))
    return dbManager.insert("llm_history", row)
}
fun updateUserAccepted(historyId: Number, accepted: Number): Unit {
    val row = SqlRow(columns = _uA(
        "user_accepted"
    ), values = _uA(
        accepted
    ))
    dbManager.update("llm_history", row, "id = ?", _uA(
        historyId
    ))
}
fun getAllHistory(limit: Number): UTSArray<LlmHistoryEntry> {
    val rows = dbManager.query("SELECT * FROM llm_history ORDER BY created_at DESC LIMIT ?", _uA(
        limit
    ))
    return mapRows(rows)
}
fun getAllStateAndAdhocTexts(limit: Number): UTSArray<LlmHistoryEntry> {
    val rows = dbManager.query("SELECT * FROM llm_history WHERE stage = 'pre' ORDER BY created_at DESC LIMIT ?", _uA(
        limit
    ))
    return mapRows(rows)
}
fun getPendingRuleReviews(limit: Number): UTSArray<LlmHistoryEntry> {
    val rows = dbManager.query("SELECT * FROM llm_history WHERE stage = 'post' AND decision IN ('create_rule','modify_rule','delete_rule') AND user_accepted IS NULL ORDER BY created_at DESC LIMIT ?", _uA(
        limit
    ))
    return mapRows(rows)
}
fun existsRejectedByRule(targetRuleId: Number, decision: String): Boolean {
    val row = dbManager.queryOne("SELECT id FROM llm_history WHERE target_rule_id = ? AND decision = ? AND user_accepted = 0 LIMIT 1", _uA(
        targetRuleId,
        decision
    ))
    return row != null
}
fun getAcceptanceRate(days: Number): LlmAcceptanceRate {
    val cutoff = Math.floor(Date.now() / 1000) - days * 86400
    val totalRow = dbManager.queryOne("SELECT COUNT(*) as cnt FROM llm_history WHERE stage = 'post' AND created_at >= ? AND decision IS NOT NULL AND decision != 'no_change'", _uA(
        cutoff
    ))
    val acceptedRow = dbManager.queryOne("SELECT COUNT(*) as cnt FROM llm_history WHERE stage = 'post' AND created_at >= ? AND user_accepted = 1", _uA(
        cutoff
    ))
    val rejectedRow = dbManager.queryOne("SELECT COUNT(*) as cnt FROM llm_history WHERE stage = 'post' AND created_at >= ? AND user_accepted = 0", _uA(
        cutoff
    ))
    val pendingRow = dbManager.queryOne("SELECT COUNT(*) as cnt FROM llm_history WHERE stage = 'post' AND created_at >= ? AND user_accepted IS NULL AND decision IS NOT NULL AND decision != 'no_change'", _uA(
        cutoff
    ))
    return LlmAcceptanceRate(total = getNum(totalRow, "cnt"), accepted = getNum(acceptedRow, "cnt"), rejected = getNum(rejectedRow, "cnt"), pending = getNum(pendingRow, "cnt"))
}
fun hasBeenRejected(targetRuleId: Number, decision: String): Boolean {
    if (targetRuleId < 1) {
        return false
    }
    try {
        return existsRejectedByRule(targetRuleId, decision)
    }
     catch (e: Throwable) {
        console.warn("[LlmRejectTracker] hasBeenRejected 异常: " + JSON.stringify(e), " at services/LlmRejectTracker.uts:8")
        return false
    }
}
fun markAccepted(historyId: Number): Unit {
    try {
        updateUserAccepted(historyId, 1)
    }
     catch (e: Throwable) {
        console.warn("[LlmRejectTracker] markAccepted 失败: " + JSON.stringify(e), " at services/LlmRejectTracker.uts:17")
    }
}
fun markRejected(historyId: Number): Unit {
    try {
        updateUserAccepted(historyId, 0)
    }
     catch (e: Throwable) {
        console.warn("[LlmRejectTracker] markRejected 失败: " + JSON.stringify(e), " at services/LlmRejectTracker.uts:25")
    }
}
open class PreTriggerResult (
    @JsonNotNull
    open var adhocText: String,
    @JsonNotNull
    open var stateDescription: String,
    @JsonNotNull
    open var fallback: Boolean = false,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PreTriggerResult", "services/LlmTriggerFlow.uts", 9, 13)
    }
}
open class PostActionResult (
    open var suggestedRule: EffectiveTriggerRule? = null,
    @JsonNotNull
    open var decision: String,
    @JsonNotNull
    open var reasoning: String,
    @JsonNotNull
    open var confidence: Number,
    @JsonNotNull
    open var alternatives: String,
    @JsonNotNull
    open var historyId: Number,
    @JsonNotNull
    open var fallback: Boolean = false,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PostActionResult", "services/LlmTriggerFlow.uts", 14, 13)
    }
}
var preContextCache: PreTriggerContext? = null
var postContextCache: PostActionContext? = null
fun setPreContext(ctx: PreTriggerContext): Unit {
    preContextCache = ctx
}
fun clearPreContext(): Unit {
    preContextCache = null
}
fun setPostContext(ctx: PostActionContext): Unit {
    postContextCache = ctx
}
fun clearPostContext(): Unit {
    postContextCache = null
}
fun fromEffectiveTriggerRule(rule: EffectiveTriggerRule): PersistedEffectiveRule {
    var pkgList: UTSArray<String> = _uA()
    if (rule.screenConditions != null) {
        pkgList = rule.screenConditions!!.appPackages
    }
    val nowSec = Math.floor(Date.now() / 1000)
    return PersistedEffectiveRule(id = rule.id, actionId = rule.actionId, appPackages = pkgList, timeWindow = rule.timeWindow, timeThresholdMinutes = rule.timeThresholdMinutes, triggerLevel = "gentle", categoryFilter = rule.categoryFilter, source = if (rule.source == "local") {
        "user"
    } else {
        rule.source
    }
    , sourceHistoryId = null, expiresAt = null, enabled = rule.enabled, priority = 5, createdAt = if (rule.createdAt > 0) {
        rule.createdAt
    } else {
        nowSec
    }
    , updatedAt = nowSec)
}
fun evaluatePre(onReady: (result: PreTriggerResult) -> Unit, onFail: () -> Unit): Unit {
    console.log("[LlmFlow] evaluatePre 开始", " at services/LlmTriggerFlow.uts:83")
    val ctx = preContextCache
    if (ctx == null) {
        console.warn("[LlmFlow] evaluatePre: preContextCache 为空", " at services/LlmTriggerFlow.uts:86")
        onFail()
        return
    }
    val contextJson = JSON.stringify(ctx)
    console.log("[LlmFlow] evaluatePre 上下文长度: " + contextJson.length, " at services/LlmTriggerFlow.uts:91")
    try {
        console.log("[LlmFlow] evaluatePre 调用 callLlmEvaluate pre", " at services/LlmTriggerFlow.uts:93")
        callLlmEvaluate("pre", ctx, fun(raw: String): Unit {
            console.log("[LlmFlow] evaluatePre LLM 返回 raw 长度: " + raw.length, " at services/LlmTriggerFlow.uts:98")
            try {
                val parsed = parsePreResponse(raw)
                console.log("[LlmFlow] evaluatePre parsePreResult adhocText=" + (if (parsed.adhocText != null) {
                    parsed.adhocText!!.substring(0, 30)
                } else {
                    "null"
                }
                ), " at services/LlmTriggerFlow.uts:101")
                val adhoc = if (parsed.adhocText != null) {
                    parsed.adhocText!!
                } else {
                    getFallbackAdhoc()
                }
                val stateDesc = if (parsed.stateDescription != null) {
                    parsed.stateDescription!!
                } else {
                    "检测到需要休息"
                }
                try {
                    insertHistory("pre", contextJson, raw, parsed, adhoc, null, stateDesc)
                }
                 catch (e: Throwable) {
                    console.warn("[LlmTriggerFlow] 写 pre history 失败: " + JSON.stringify(e), " at services/LlmTriggerFlow.uts:107")
                }
                val result = PreTriggerResult(adhocText = adhoc, stateDescription = stateDesc, fallback = false)
                onReady(result)
            }
             catch (e: Throwable) {
                console.warn("[LlmTriggerFlow] pre parse 失败: " + JSON.stringify(e), " at services/LlmTriggerFlow.uts:116")
                val fallback: PreTriggerResult = buildFallbackPre()
                try {
                    insertHistory("pre", contextJson, raw, ParsedLlmResult(), fallback.adhocText, null, fallback.stateDescription)
                }
                 catch (_: Throwable) {}
                onReady(fallback)
            }
        }
        , fun(): Unit {
            console.warn("[LlmFlow] evaluatePre LLM 请求失败，使用 fallback", " at services/LlmTriggerFlow.uts:125")
            val fallback: PreTriggerResult = buildFallbackPre()
            try {
                insertHistory("pre", contextJson, "", ParsedLlmResult(), fallback.adhocText, null, fallback.stateDescription)
            }
             catch (_: Throwable) {}
            onReady(fallback)
        }
        )
    }
     catch (e: Throwable) {
        console.warn("[LlmTriggerFlow] pre 异常: " + JSON.stringify(e), " at services/LlmTriggerFlow.uts:134")
        onFail()
    }
}
fun evaluatePost(actionId: String, actionName: String, actionCategory: String, durationMs: Number, onReady: (result: PostActionResult) -> Unit, onFail: () -> Unit): Unit {
    if (postContextCache == null) {
        onFail()
        return
    }
    val ctx = postContextCache
    if (ctx == null) {
        onFail()
        return
    }
    val enriched = PostActionContext(actionId = actionId, actionName = actionName, actionCategory = actionCategory, durationMs = durationMs, originalContext = ctx.originalContext, todayCompletedCount = ctx.todayCompletedCount, guardMinutes = ctx.guardMinutes, todaySkipCount = ctx.todaySkipCount, recentActions = ctx.recentActions, appHistory = ctx.appHistory, currentRules = ctx.currentRules, userPreference = ctx.userPreference, lastWeekSameTime = ctx.lastWeekSameTime)
    val contextJson = JSON.stringify(enriched)
    try {
        callLlmEvaluate("post", enriched, fun(raw: String): Unit {
            try {
                val parsed = parsePostResponse(raw)
                val decision = if (parsed.decision != null) {
                    parsed.decision!!
                } else {
                    "no_change"
                }
                val rule = if (parsed.suggestedRule != null) {
                    parsed.suggestedRule!!
                } else {
                    null
                }
                val reasoning = if (parsed.reasoning != null) {
                    parsed.reasoning!!
                } else {
                    ""
                }
                val confidence = if (parsed.confidence != null) {
                    parsed.confidence!!
                } else {
                    0
                }
                val alternatives = if (parsed.alternatives != null) {
                    JSON.stringify(parsed.alternatives)
                } else {
                    ""
                }
                var finalDecision = decision
                var finalRule = rule
                if (decision == "modify_rule" || decision == "delete_rule") {
                    if (parsed.targetRuleId != null && parsed.targetRuleId!! > 0) {
                        if (hasBeenRejected(parsed.targetRuleId!!, decision)) {
                            finalDecision = "no_change"
                            finalRule = null
                        }
                    }
                }
                var historyId: Number = 0
                try {
                    historyId = insertHistory("post", contextJson, raw, parsed, null, finalRule, reasoning, confidence, finalDecision, if (parsed.targetRuleId != null) {
                        parsed.targetRuleId
                    } else {
                        null
                    }
                    , null, alternatives)
                }
                 catch (e: Throwable) {
                    console.warn("[LlmTriggerFlow] 写 post history 失败: " + JSON.stringify(e), " at services/LlmTriggerFlow.uts:211")
                }
                try {
                    val targetId = if (parsed.targetRuleId != null) {
                        parsed.targetRuleId!!
                    } else {
                        0
                    }
                    if (finalDecision == "create_rule" && finalRule != null) {
                        val persisted = fromEffectiveTriggerRule(finalRule)
                        persisted.sourceHistoryId = historyId
                        insertRule(persisted)
                        console.log("[LlmFlow] create_rule 已落库 actionId=" + finalRule.actionId, " at services/LlmTriggerFlow.uts:220")
                    } else if (finalDecision == "modify_rule" && finalRule != null && targetId > 0) {
                        val persisted = fromEffectiveTriggerRule(finalRule)
                        persisted.id = targetId
                        persisted.sourceHistoryId = historyId
                        updateRule(persisted)
                        console.log("[LlmFlow] modify_rule 已落库 id=" + targetId, " at services/LlmTriggerFlow.uts:226")
                    } else if (finalDecision == "delete_rule" && targetId > 0) {
                        deleteRule(targetId)
                        console.log("[LlmFlow] delete_rule 已落库 id=" + targetId, " at services/LlmTriggerFlow.uts:229")
                    }
                }
                 catch (e: Throwable) {
                    console.warn("[LlmFlow] apply rule fail: " + JSON.stringify(e), " at services/LlmTriggerFlow.uts:232")
                }
                val result = PostActionResult(suggestedRule = finalRule, decision = finalDecision, reasoning = reasoning, confidence = confidence, alternatives = alternatives, historyId = historyId, fallback = false)
                onReady(result)
            }
             catch (e: Throwable) {
                console.warn("[LlmTriggerFlow] post parse 失败: " + JSON.stringify(e), " at services/LlmTriggerFlow.uts:245")
                var hid: Number = 0
                try {
                    hid = insertHistory("post", contextJson, raw, ParsedLlmResult(), null, null, "", 0, null, null, null, null)
                }
                 catch (_: Throwable) {}
                val result = PostActionResult(suggestedRule = null, decision = "no_change", reasoning = "", confidence = 0, alternatives = "", historyId = hid, fallback = true)
                onReady(result)
            }
        }
        , fun(): Unit {
            var hid: Number = 0
            try {
                hid = insertHistory("post", contextJson, "", ParsedLlmResult(), null, null, "", 0, null, null, null, null)
            }
             catch (_: Throwable) {}
            val result = PostActionResult(suggestedRule = null, decision = "no_change", reasoning = "", confidence = 0, alternatives = "", historyId = hid, fallback = true)
            onReady(result)
        }
        )
    }
     catch (e: Throwable) {
        console.warn("[LlmTriggerFlow] post 异常: " + JSON.stringify(e), " at services/LlmTriggerFlow.uts:280")
        onFail()
    }
}
fun buildFallbackPre(): PreTriggerResult {
    return PreTriggerResult(adhocText = getFallbackAdhoc(), stateDescription = "检测到需要休息", fallback = true)
}
fun parsePreResponse(raw: String): ParsedLlmResult {
    val obj = parseJsonThreeLevels(raw)
    val result = ParsedLlmResult()
    if (obj == null) {
        return result
    }
    val adhoc = obj["adhocText"]
    if (UTSAndroid.`typeof`(adhoc) === "string" && (adhoc as String).length > 0) {
        result.adhocText = adhoc as String
    }
    val stateDesc = obj["stateDescription"]
    if (UTSAndroid.`typeof`(stateDesc) === "string" && (stateDesc as String).length > 0) {
        result.stateDescription = stateDesc as String
    }
    return result
}
fun parsePostResponse(raw: String): ParsedLlmResult {
    val obj = parseJsonThreeLevels(raw)
    val result = ParsedLlmResult()
    if (obj == null) {
        return result
    }
    val decision = obj["decision"]
    if (UTSAndroid.`typeof`(decision) === "string" && (decision as String).length > 0) {
        result.decision = decision as String
    }
    val targetId = obj["targetRuleId"]
    if (UTSAndroid.`typeof`(targetId) === "number") {
        result.targetRuleId = targetId as Number
    }
    val reasoning = obj["reasoning"]
    if (UTSAndroid.`typeof`(reasoning) === "string" && (reasoning as String).length > 0) {
        result.reasoning = reasoning as String
    }
    val conf = obj["confidence"]
    if (UTSAndroid.`typeof`(conf) === "number") {
        result.confidence = conf as Number
    }
    val altArr = obj["alternatives"]
    if (altArr != null) {
        try {
            val altStr = JSON.stringify(altArr)
            val alt = UTSAndroid.consoleDebugError(JSON.parse(altStr), " at services/LlmTriggerFlow.uts:296") as UTSArray<Any>
            if (alt != null) {
                result.alternatives = alt
            }
        }
         catch (_: Throwable) {}
    }
    val rawRule = obj["suggestedRule"]
    if (rawRule != null && UTSAndroid.`typeof`(rawRule) === "object") {
        val rule = normalizeRule(rawRule as UTSJSONObject)
        if (rule != null) {
            result.suggestedRule = rule
        }
    }
    return result
}
fun normalizeRule(raw: UTSJSONObject): EffectiveTriggerRule? {
    val actionId = raw["actionId"]
    if (UTSAndroid.`typeof`(actionId) !== "string" || (actionId as String).length < 1) {
        return null
    }
    val actionIdStr = actionId as String
    var timeWindow: TimeWindow? = null
    val tw = raw["timeWindow"]
    if (tw != null && UTSAndroid.`typeof`(tw) === "object") {
        val twObj = tw as UTSJSONObject
        val start = twObj["start"]
        val end = twObj["end"]
        if (UTSAndroid.`typeof`(start) === "string" && UTSAndroid.`typeof`(end) === "string") {
            val startStr = start as String
            val endStr = end as String
            if (startStr.length > 0 && endStr.length > 0) {
                val w = TimeWindow(start = startStr, end = endStr)
                timeWindow = w
            }
        }
    }
    var screenConditions: ScreenConditions? = null
    val sc = raw["screenConditions"]
    if (sc != null && UTSAndroid.`typeof`(sc) === "object") {
        val scObj = sc as UTSJSONObject
        val includeHomeRaw = scObj["includeHome"]
        val includeHome = UTSAndroid.`typeof`(includeHomeRaw) === "boolean" && (includeHomeRaw as Boolean)
        val appPackagesRaw = scObj["appPackages"]
        val pkgs: UTSArray<String> = _uA()
        if (appPackagesRaw != null) {
            try {
                val arrStr = JSON.stringify(appPackagesRaw)
                val arr = UTSAndroid.consoleDebugError(JSON.parse(arrStr), " at services/LlmTriggerFlow.uts:342") as UTSArray<Any>
                run {
                    var i: Number = 0
                    while(i < arr.length){
                        val v = arr[i]
                        if (UTSAndroid.`typeof`(v) === "string") {
                            val vs = v as String
                            if (vs.length > 0) {
                                pkgs.push(vs)
                            }
                        }
                        i++
                    }
                }
            }
             catch (_: Throwable) {}
        }
        val cond = ScreenConditions(appPackages = pkgs, includeHome = includeHome)
        screenConditions = cond
    }
    var threshold: Number = 60
    val thresholdRaw = raw["timeThresholdMinutes"]
    if (UTSAndroid.`typeof`(thresholdRaw) === "number") {
        threshold = (thresholdRaw as Number).toInt()
    }
    var categoryFilter: String? = null
    val cfRaw = raw["categoryFilter"]
    if (UTSAndroid.`typeof`(cfRaw) === "string" && (cfRaw as String).length > 0) {
        categoryFilter = cfRaw as String
    }
    val rule = EffectiveTriggerRule(id = 0, actionId = actionIdStr, timeWindow = timeWindow, screenConditions = screenConditions, timeThresholdMinutes = threshold, categoryFilter = categoryFilter, source = "llm", enabled = true, createdAt = Math.floor(Date.now() / 1000))
    return rule
}
fun parseJsonThreeLevels(raw: String): UTSJSONObject? {
    var cleaned = raw
    if (cleaned.indexOf("```") >= 0) {
        val fenceStart = cleaned.indexOf("```")
        val afterFence = cleaned.substring(fenceStart + 3)
        val newlineIdx = afterFence.indexOf("\n")
        val afterLang = if (newlineIdx >= 0) {
            afterFence.substring(newlineIdx + 1)
        } else {
            afterFence
        }
        val endFence = afterLang.lastIndexOf("```")
        if (endFence > 0) {
            cleaned = afterLang.substring(0, endFence)
        } else {
            cleaned = afterLang
        }
    }
    try {
        return UTSAndroid.consoleDebugError(JSON.parse(cleaned), " at services/LlmTriggerFlow.uts:396") as UTSJSONObject
    }
     catch (_: Throwable) {}
    try {
        val match = cleaned.match(UTSRegExp("\\{[\\s\\S]*\\}", ""))
        if (match != null) {
            val m = match[0]
            if (m != null && m.length > 0) {
                return UTSAndroid.consoleDebugError(JSON.parse(m), " at services/LlmTriggerFlow.uts:404") as UTSJSONObject
            }
        }
    }
     catch (_: Throwable) {}
    return null
}
open class AppHistorySummary (
    @JsonNotNull
    open var packageName: String,
    @JsonNotNull
    open var appLabel: String,
    @JsonNotNull
    open var appCategory: String,
    @JsonNotNull
    open var totalTriggers: Number,
    @JsonNotNull
    open var totalCompletes: Number,
    @JsonNotNull
    open var totalSkips: Number,
    @JsonNotNull
    open var acceptRate: Number,
    @JsonNotNull
    open var typicalContinuousMin: Number,
    @JsonNotNull
    open var avgTriggerHour: Number,
    @JsonNotNull
    open var lastTriggerAt: Number,
    @JsonNotNull
    open var lastWeekTrend: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AppHistorySummary", "services/AppHistoryService.uts", 4, 13)
    }
}
fun startOfDay(date: String): Number {
    val parts = date.split("-")
    if (parts.length < 3) {
        return 0
    }
    return Math.floor(Date(parseInt(parts[0] as String), parseInt(parts[1] as String) - 1, parseInt(parts[2] as String)).getTime() / 1000)
}
fun rowToStr(row: Map<String, Any>?, col: String): String {
    return getStr(row, col)
}
fun getAppHistory(pkg: String, days: Number = 7): AppHistorySummary? {
    try {
        val start = startOfDay(daysAgo(days - 1))
        val prevStart = startOfDay(daysAgo(days * 2 - 1))
        val triggerRows = dbManager.query("SELECT created_at, continuous_minutes FROM trigger_logs WHERE app_package = ? AND created_at >= ? ORDER BY created_at DESC", _uA(
            pkg,
            start
        ))
        if (triggerRows.length < 1) {
            val labelRow = dbManager.queryOne("SELECT app_label FROM app_usage_snapshots WHERE package_name = ? LIMIT 1", _uA(
                pkg
            ))
            val catRow = dbManager.queryOne("SELECT category FROM app_tags WHERE package_name = ?", _uA(
                pkg
            ))
            return AppHistorySummary(packageName = pkg, appLabel = if (labelRow != null) {
                getStr(labelRow, "app_label")
            } else {
                pkg
            }
            , appCategory = if (catRow != null) {
                getStr(catRow, "category")
            } else {
                "other"
            }
            , totalTriggers = 0, totalCompletes = 0, totalSkips = 0, acceptRate = 0, typicalContinuousMin = 0, avgTriggerHour = 0, lastTriggerAt = 0, lastWeekTrend = "stable")
        }
        val totalTriggers = triggerRows.length
        var totalContinMin: Number = 0
        var totalHours: Number = 0
        var lastTriggerAt: Number = 0
        run {
            var i: Number = 0
            while(i < triggerRows.length){
                val r = triggerRows[i]
                if (r == null) {
                    i++
                    continue
                }
                totalContinMin += getNum(r, "continuous_minutes")
                val ts = getNum(r, "created_at")
                if (ts > lastTriggerAt) {
                    lastTriggerAt = ts
                }
                val d = Date(ts * 1000)
                totalHours += d.getHours()
                i++
            }
        }
        var totalCompletes: Number = 0
        var totalSkips: Number = 0
        run {
            var i: Number = 0
            while(i < triggerRows.length){
                val r = triggerRows[i]
                if (r == null) {
                    i++
                    continue
                }
                val ts = getNum(r, "created_at")
                val actionId = getStrOrNull(r, "action_id")
                if (actionId == null) {
                    i++
                    continue
                }
                val actionRow = dbManager.queryOne("SELECT result FROM action_logs WHERE created_at >= ? AND created_at < ? AND action_id = ? LIMIT 1", _uA(
                    ts - 60,
                    ts + 60,
                    actionId
                ))
                val result = rowToStr(actionRow, "result")
                if (result == "completed" || result == "self_reported") {
                    totalCompletes++
                } else if (result == "skipped") {
                    totalSkips++
                }
                i++
            }
        }
        val prevRows = dbManager.query("SELECT created_at FROM trigger_logs WHERE app_package = ? AND created_at >= ? AND created_at < ?", _uA(
            pkg,
            prevStart,
            start
        ))
        val prevTriggers = prevRows.length
        val prevCompletes: Number = 0
        val prevAccept = if (prevTriggers > 0) {
            prevCompletes / prevTriggers
        } else {
            0
        }
        val currAccept = if (totalTriggers > 0) {
            totalCompletes / totalTriggers
        } else {
            0
        }
        var trend: String = "stable"
        if (prevTriggers >= 3) {
            if (currAccept - prevAccept > 0.1) {
                trend = "improving"
            } else if (currAccept - prevAccept < -0.1) {
                trend = "declining"
            }
        }
        val labelRow = dbManager.queryOne("SELECT app_label FROM app_usage_snapshots WHERE package_name = ? LIMIT 1", _uA(
            pkg
        ))
        val catRow = dbManager.queryOne("SELECT category FROM app_tags WHERE package_name = ?", _uA(
            pkg
        ))
        val total = totalCompletes + totalSkips
        val acceptRate = if (total > 0) {
            totalCompletes / total
        } else {
            0
        }
        return AppHistorySummary(packageName = pkg, appLabel = if (labelRow != null) {
            getStr(labelRow, "app_label")
        } else {
            pkg
        }
        , appCategory = if (catRow != null) {
            getStr(catRow, "category")
        } else {
            "other"
        }
        , totalTriggers = totalTriggers, totalCompletes = totalCompletes, totalSkips = totalSkips, acceptRate = acceptRate, typicalContinuousMin = if (totalTriggers > 0) {
            Math.round(totalContinMin / totalTriggers)
        } else {
            0
        }
        , avgTriggerHour = if (totalTriggers > 0) {
            Math.round(totalHours / totalTriggers)
        } else {
            0
        }
        , lastTriggerAt = lastTriggerAt, lastWeekTrend = trend)
    }
     catch (e: Throwable) {
        console.warn("[AppHistoryService] getAppHistory 异常: " + JSON.stringify(e), " at services/AppHistoryService.uts:121")
        return null
    }
}
open class UserProfile (
    @JsonNotNull
    open var healthFocus: String,
    @JsonNotNull
    open var bodyLimit: String,
    @JsonNotNull
    open var newbieMode: Boolean = false,
    @JsonNotNull
    open var preferredHours: UTSArray<Number>,
    @JsonNotNull
    open var totalActiveDays: Number,
    @JsonNotNull
    open var avgDailyCompletes: Number,
    @JsonNotNull
    open var daysSinceFirstUse: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UserProfile", "services/UserProfileService.uts", 7, 13)
    }
}
fun safeHealthFocus(v: String): String {
    if (v == "eye" || v == "posture" || v == "vitality") {
        return v
    }
    return "balanced"
}
fun safeBodyLimit(v: String): String {
    if (v == "neck" || v == "waist" || v == "knee") {
        return v
    }
    return "none"
}
fun getUserProfile(): UserProfile {
    val focus = safeHealthFocus(getSetting("health_preference", "balanced"))
    val limit = safeBodyLimit(getSetting("body_limit", "none"))
    val preferred: UTSArray<Number> = _uA()
    try {
        val rows = dbManager.query("SELECT strftime('%H', created_at, 'unixepoch') as hr, COUNT(*) as cnt FROM action_logs WHERE result IN ('completed','self_reported') GROUP BY hr ORDER BY cnt DESC LIMIT 3")
        run {
            var i: Number = 0
            while(i < rows.length){
                val r = rows[i]
                if (r == null) {
                    i++
                    continue
                }
                val hrStr = getNum(r, "hr") as Number
                preferred.push(hrStr)
                i++
            }
        }
    }
     catch (_: Throwable) {}
    val totalDaysRow = dbManager.queryOne("SELECT COUNT(DISTINCT date) as cnt FROM daily_summaries")
    val totalDays = getNum(totalDaysRow, "cnt")
    val sumRow = dbManager.queryOne("SELECT COALESCE(SUM(total_completed),0) as total FROM daily_summaries")
    val totalCompletes = getNum(sumRow, "total")
    val avg = if (totalDays > 0) {
        totalCompletes / totalDays
    } else {
        0
    }
    var daysSinceFirst: Number = 0
    val firstRow = dbManager.queryOne("SELECT value FROM user_settings WHERE key = ?", _uA(
        "newbie_start_date"
    ))
    if (firstRow != null) {
        val startStr = getStr(firstRow, "value")
        val startTs = parseInt(startStr)
        if (startTs > 0) {
            val now = Math.floor(Date.now() / 1000)
            daysSinceFirst = Math.floor((now - startTs) / 86400)
            if (daysSinceFirst < 0) {
                daysSinceFirst = 0
            }
        }
    }
    return UserProfile(healthFocus = focus, bodyLimit = limit, newbieMode = false, preferredHours = preferred, totalActiveDays = totalDays, avgDailyCompletes = Math.round(avg * 10) / 10, daysSinceFirstUse = daysSinceFirst)
}
fun getActiveRuleList(): UTSArray<PersistedEffectiveRule> {
    return getActiveRules()
}
typealias AppCategory = String
typealias TagSource = String
open class AppTag (
    @JsonNotNull
    open var packageName: String,
    @JsonNotNull
    open var appLabel: String,
    @JsonNotNull
    open var category: AppCategory,
    @JsonNotNull
    open var tagSource: TagSource,
    @JsonNotNull
    open var isUserLocked: Boolean = false,
    @JsonNotNull
    open var isInRule: Boolean = false,
    @JsonNotNull
    open var totalUses: Number,
    @JsonNotNull
    open var lastSeenAt: Number,
    @JsonNotNull
    open var createdAt: Number,
    @JsonNotNull
    open var updatedAt: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AppTag", "models/AppTag.uts", 3, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return AppTagReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class AppTagReactiveObject : AppTag, IUTSReactive<AppTag> {
    override var __v_raw: AppTag
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: AppTag, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(packageName = __v_raw.packageName, appLabel = __v_raw.appLabel, category = __v_raw.category, tagSource = __v_raw.tagSource, isUserLocked = __v_raw.isUserLocked, isInRule = __v_raw.isInRule, totalUses = __v_raw.totalUses, lastSeenAt = __v_raw.lastSeenAt, createdAt = __v_raw.createdAt, updatedAt = __v_raw.updatedAt) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): AppTagReactiveObject {
        return AppTagReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var packageName: String
        get() {
            return _tRG(__v_raw, "packageName", __v_raw.packageName, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("packageName")) {
                return
            }
            val oldValue = __v_raw.packageName
            __v_raw.packageName = value
            _tRS(__v_raw, "packageName", oldValue, value)
        }
    override var appLabel: String
        get() {
            return _tRG(__v_raw, "appLabel", __v_raw.appLabel, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("appLabel")) {
                return
            }
            val oldValue = __v_raw.appLabel
            __v_raw.appLabel = value
            _tRS(__v_raw, "appLabel", oldValue, value)
        }
    override var category: AppCategory
        get() {
            return _tRG(__v_raw, "category", __v_raw.category, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("category")) {
                return
            }
            val oldValue = __v_raw.category
            __v_raw.category = value
            _tRS(__v_raw, "category", oldValue, value)
        }
    override var tagSource: TagSource
        get() {
            return _tRG(__v_raw, "tagSource", __v_raw.tagSource, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("tagSource")) {
                return
            }
            val oldValue = __v_raw.tagSource
            __v_raw.tagSource = value
            _tRS(__v_raw, "tagSource", oldValue, value)
        }
    override var isUserLocked: Boolean
        get() {
            return _tRG(__v_raw, "isUserLocked", __v_raw.isUserLocked, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("isUserLocked")) {
                return
            }
            val oldValue = __v_raw.isUserLocked
            __v_raw.isUserLocked = value
            _tRS(__v_raw, "isUserLocked", oldValue, value)
        }
    override var isInRule: Boolean
        get() {
            return _tRG(__v_raw, "isInRule", __v_raw.isInRule, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("isInRule")) {
                return
            }
            val oldValue = __v_raw.isInRule
            __v_raw.isInRule = value
            _tRS(__v_raw, "isInRule", oldValue, value)
        }
    override var totalUses: Number
        get() {
            return _tRG(__v_raw, "totalUses", __v_raw.totalUses, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("totalUses")) {
                return
            }
            val oldValue = __v_raw.totalUses
            __v_raw.totalUses = value
            _tRS(__v_raw, "totalUses", oldValue, value)
        }
    override var lastSeenAt: Number
        get() {
            return _tRG(__v_raw, "lastSeenAt", __v_raw.lastSeenAt, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("lastSeenAt")) {
                return
            }
            val oldValue = __v_raw.lastSeenAt
            __v_raw.lastSeenAt = value
            _tRS(__v_raw, "lastSeenAt", oldValue, value)
        }
    override var createdAt: Number
        get() {
            return _tRG(__v_raw, "createdAt", __v_raw.createdAt, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("createdAt")) {
                return
            }
            val oldValue = __v_raw.createdAt
            __v_raw.createdAt = value
            _tRS(__v_raw, "createdAt", oldValue, value)
        }
    override var updatedAt: Number
        get() {
            return _tRG(__v_raw, "updatedAt", __v_raw.updatedAt, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updatedAt")) {
                return
            }
            val oldValue = __v_raw.updatedAt
            __v_raw.updatedAt = value
            _tRS(__v_raw, "updatedAt", oldValue, value)
        }
}
fun mapRow__6(row: Map<String, Any>): AppTag {
    val total = getNum(row, "total_uses")
    return AppTag(packageName = getStr(row, "package_name"), appLabel = getStr(row, "app_label"), category = getStr(row, "category") as AppCategory, tagSource = getStr(row, "tag_source") as TagSource, isUserLocked = getNum(row, "is_user_locked") == 1, isInRule = getNum(row, "is_in_rule") == 1, totalUses = total, lastSeenAt = getNum(row, "last_seen_at"), createdAt = getNum(row, "created_at"), updatedAt = getNum(row, "updated_at"))
}
fun getByPackage(pkg: String): AppTag? {
    val row = dbManager.queryOne("SELECT * FROM app_tags WHERE package_name = ?", _uA(
        pkg
    ))
    if (row == null) {
        return null
    }
    return mapRow__6(row)
}
fun listAll(): UTSArray<AppTag> {
    val rows = dbManager.query("SELECT * FROM app_tags ORDER BY last_seen_at DESC")
    val result: UTSArray<AppTag> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            val r = rows[i]
            if (r == null) {
                i++
                continue
            }
            result.push(mapRow__6(r))
            i++
        }
    }
    return result
}
fun listPendingForLlm(): UTSArray<AppTag> {
    val rows = dbManager.query("SELECT * FROM app_tags WHERE tag_source = 'pending' AND is_user_locked = 0 ORDER BY last_seen_at DESC")
    val result: UTSArray<AppTag> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            val r = rows[i]
            if (r == null) {
                i++
                continue
            }
            result.push(mapRow__6(r))
            i++
        }
    }
    return result
}
fun upsert(tag: AppTag): Unit {
    val existing = dbManager.queryOne("SELECT package_name FROM app_tags WHERE package_name = ?", _uA(
        tag.packageName
    ))
    if (existing != null) {
        val row = SqlRow(columns = _uA(
            "app_label",
            "last_seen_at",
            "updated_at",
            "total_uses"
        ), values = _uA(
            tag.appLabel,
            tag.lastSeenAt,
            tag.updatedAt,
            tag.totalUses
        ))
        dbManager.update("app_tags", row, "package_name = ?", _uA(
            tag.packageName
        ))
    } else {
        val row = SqlRow(columns = _uA(
            "package_name",
            "app_label",
            "category",
            "tag_source",
            "is_user_locked",
            "is_in_rule",
            "total_uses",
            "last_seen_at",
            "created_at",
            "updated_at"
        ), values = _uA(
            tag.packageName,
            tag.appLabel,
            tag.category,
            tag.tagSource,
            if (tag.isUserLocked) {
                1
            } else {
                0
            }
            ,
            if (tag.isInRule) {
                1
            } else {
                0
            }
            ,
            tag.totalUses,
            tag.lastSeenAt,
            tag.createdAt,
            tag.updatedAt
        ))
        dbManager.insert("app_tags", row)
    }
}
fun updateCategory(pkg: String, category: AppCategory, source: TagSource): Unit {
    val now = Math.floor(Date.now() / 1000)
    val row = SqlRow(columns = _uA(
        "category",
        "tag_source",
        "updated_at"
    ), values = _uA(
        category,
        source,
        now
    ))
    dbManager.update("app_tags", row, "package_name = ?", _uA(
        pkg
    ))
}
fun setUserLocked(pkg: String, locked: Boolean): Unit {
    val now = Math.floor(Date.now() / 1000)
    val row = SqlRow(columns = _uA(
        "is_user_locked",
        "updated_at"
    ), values = _uA(
        if (locked) {
            1
        } else {
            0
        }
        ,
        now
    ))
    dbManager.update("app_tags", row, "package_name = ?", _uA(
        pkg
    ))
}
fun incrementUses(pkg: String): Unit {
    try {
        val existing = getByPackage(pkg)
        if (existing == null) {
            return
        }
        val newCount = existing.totalUses + 1
        val now = Math.floor(Date.now() / 1000)
        val row = SqlRow(columns = _uA(
            "total_uses",
            "last_seen_at",
            "updated_at"
        ), values = _uA(
            newCount,
            now,
            now
        ))
        dbManager.update("app_tags", row, "package_name = ?", _uA(
            pkg
        ))
    }
     catch (_: Throwable) {}
}
val SYSTEM_PROMPT_CLASSIFY = "你是一个 App 分类助手。\n根据用户的应用名和包名，分类到以下之一：social | video | game | work | study | reading | other\n严格按 JSON 输出，不要任何额外文字：{\"results\":[{\"packageName\":\"...\",\"category\":\"...\"}]}"
val VALID_CATEGORIES = _uA(
    "social",
    "video",
    "game",
    "work",
    "study",
    "reading",
    "other"
) as UTSArray<AppCategory>
fun isValidCategory(s: String): Boolean {
    run {
        var i: Number = 0
        while(i < VALID_CATEGORIES.length){
            if (VALID_CATEGORIES[i] == s) {
                return true
            }
            i++
        }
    }
    return false
}
fun ensureCategory(pkg: String, appLabel: String): Unit {
    if (shouldFilterPackage(pkg, "")) {
        return
    }
    try {
        val existing = getByPackage(pkg)
        if (existing != null) {
            incrementUses(pkg)
            return
        }
        val now = Math.floor(Date.now() / 1000)
        val tag = AppTag(packageName = pkg, appLabel = appLabel, category = "other", tagSource = "pending", isUserLocked = false, isInRule = false, totalUses = 1, lastSeenAt = now, createdAt = now, updatedAt = now)
        upsert(tag)
    }
     catch (e: Throwable) {
        console.warn("[AppCategoryService] ensureCategory 失败: " + JSON.stringify(e), " at services/AppCategoryService.uts:43")
    }
}
fun batchClassifyTodayNewApps(onComplete: ((n: Number) -> Unit)? = null): Unit {
    try {
        val pending = listPendingForLlm()
        if (pending.length < 1) {
            if (onComplete != null) {
                onComplete(0)
            }
            return
        }
        val items: UTSArray<String> = _uA()
        run {
            var i: Number = 0
            while(i < pending.length){
                val p = pending[i]
                items.push("{\"packageName\":\"" + p.packageName + "\",\"appLabel\":\"" + p.appLabel + "\"}")
                i++
            }
        }
        val userPrompt = "请分类以下应用：\n" + items.join("\n")
        callMinimaxChat(SYSTEM_PROMPT_CLASSIFY, userPrompt, 800, fun(raw: String): Unit {
            try {
                val obj = parseClassifyResponse(raw, pending)
                var n: Number = 0
                run {
                    var i: Number = 0
                    while(i < obj.length){
                        val item = obj[i]
                        if (shouldFilterPackage(item.pkg, "")) {
                            i++
                            continue
                        }
                        val tag = getByPackage(item.pkg)
                        if (tag == null) {
                            i++
                            continue
                        }
                        if (tag.isUserLocked) {
                            i++
                            continue
                        }
                        updateCategory(item.pkg, item.category, "llm")
                        n++
                        i++
                    }
                }
                if (onComplete != null) {
                    onComplete(n)
                }
            }
             catch (e: Throwable) {
                console.warn("[AppCategoryService] batchClassify 解析失败: " + JSON.stringify(e), " at services/AppCategoryService.uts:76")
                if (onComplete != null) {
                    onComplete(0)
                }
            }
        }
        , fun(): Unit {
            console.warn("[AppCategoryService] batchClassify LLM 失败", " at services/AppCategoryService.uts:80")
            if (onComplete != null) {
                onComplete(0)
            }
        }
        )
    }
     catch (e: Throwable) {
        console.warn("[AppCategoryService] batchClassifyTodayNewApps 异常: " + JSON.stringify(e), " at services/AppCategoryService.uts:84")
        if (onComplete != null) {
            onComplete(0)
        }
    }
}
open class ClassifyItem (
    @JsonNotNull
    open var pkg: String,
    @JsonNotNull
    open var category: AppCategory,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ClassifyItem", "services/AppCategoryService.uts", 95, 6)
    }
}
fun parseClassifyResponse(raw: String, pending: UTSArray<AppTag>): UTSArray<ClassifyItem> {
    val result: UTSArray<ClassifyItem> = _uA()
    var text = raw.trim()
    val startIdx = text.indexOf("[")
    val endIdx = text.lastIndexOf("]")
    if (startIdx < 0 || endIdx < 0 || endIdx < startIdx) {
        return result
    }
    text = text.substring(startIdx, endIdx + 1)
    var arr: UTSArray<UTSJSONObject> = _uA()
    try {
        val parsed = UTSAndroid.consoleDebugError(JSON.parse(text), " at services/AppCategoryService.uts:109") as UTSArray<Any>
        if (parsed != null) {
            run {
                var i: Number = 0
                while(i < parsed.length){
                    val v = parsed[i]
                    if (v != null && UTSAndroid.`typeof`(v) === "object") {
                        arr.push(v as UTSJSONObject)
                    }
                    i++
                }
            }
        }
    }
     catch (_: Throwable) {
        return result
    }
    run {
        var i: Number = 0
        while(i < arr.length){
            val obj = arr[i]
            val pkg = obj["packageName"] as String
            val cat = obj["category"] as String
            if (pkg == null || cat == null) {
                i++
                continue
            }
            if (!isValidCategory(cat)) {
                i++
                continue
            }
            val item = ClassifyItem(pkg = pkg, category = cat as AppCategory)
            result.push(item)
            i++
        }
    }
    return result
}
fun manualSetCategory(pkg: String, category: AppCategory): Unit {
    setUserLocked(pkg, true)
    updateCategory(pkg, category, "user")
}
typealias AchievementCategory = String
typealias AchievementConditionType = String
open class AchievementDef (
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var icon: String,
    @JsonNotNull
    open var description: String,
    @JsonNotNull
    open var category: AchievementCategory,
    @JsonNotNull
    open var conditionType: AchievementConditionType,
    @JsonNotNull
    open var threshold: Number,
    @JsonNotNull
    open var rewardText: String,
    @JsonNotNull
    open var historyText: String,
    @JsonNotNull
    open var actionType: String,
    @JsonNotNull
    open var startHour: Number,
    @JsonNotNull
    open var endHour: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AchievementDef", "models/Achievement.uts", 10, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return AchievementDefReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class AchievementDefReactiveObject : AchievementDef, IUTSReactive<AchievementDef> {
    override var __v_raw: AchievementDef
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: AchievementDef, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, icon = __v_raw.icon, description = __v_raw.description, category = __v_raw.category, conditionType = __v_raw.conditionType, threshold = __v_raw.threshold, rewardText = __v_raw.rewardText, historyText = __v_raw.historyText, actionType = __v_raw.actionType, startHour = __v_raw.startHour, endHour = __v_raw.endHour) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): AchievementDefReactiveObject {
        return AchievementDefReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: String
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var icon: String
        get() {
            return _tRG(__v_raw, "icon", __v_raw.icon, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("icon")) {
                return
            }
            val oldValue = __v_raw.icon
            __v_raw.icon = value
            _tRS(__v_raw, "icon", oldValue, value)
        }
    override var description: String
        get() {
            return _tRG(__v_raw, "description", __v_raw.description, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("description")) {
                return
            }
            val oldValue = __v_raw.description
            __v_raw.description = value
            _tRS(__v_raw, "description", oldValue, value)
        }
    override var category: AchievementCategory
        get() {
            return _tRG(__v_raw, "category", __v_raw.category, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("category")) {
                return
            }
            val oldValue = __v_raw.category
            __v_raw.category = value
            _tRS(__v_raw, "category", oldValue, value)
        }
    override var conditionType: AchievementConditionType
        get() {
            return _tRG(__v_raw, "conditionType", __v_raw.conditionType, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("conditionType")) {
                return
            }
            val oldValue = __v_raw.conditionType
            __v_raw.conditionType = value
            _tRS(__v_raw, "conditionType", oldValue, value)
        }
    override var threshold: Number
        get() {
            return _tRG(__v_raw, "threshold", __v_raw.threshold, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("threshold")) {
                return
            }
            val oldValue = __v_raw.threshold
            __v_raw.threshold = value
            _tRS(__v_raw, "threshold", oldValue, value)
        }
    override var rewardText: String
        get() {
            return _tRG(__v_raw, "rewardText", __v_raw.rewardText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("rewardText")) {
                return
            }
            val oldValue = __v_raw.rewardText
            __v_raw.rewardText = value
            _tRS(__v_raw, "rewardText", oldValue, value)
        }
    override var historyText: String
        get() {
            return _tRG(__v_raw, "historyText", __v_raw.historyText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("historyText")) {
                return
            }
            val oldValue = __v_raw.historyText
            __v_raw.historyText = value
            _tRS(__v_raw, "historyText", oldValue, value)
        }
    override var actionType: String
        get() {
            return _tRG(__v_raw, "actionType", __v_raw.actionType, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("actionType")) {
                return
            }
            val oldValue = __v_raw.actionType
            __v_raw.actionType = value
            _tRS(__v_raw, "actionType", oldValue, value)
        }
    override var startHour: Number
        get() {
            return _tRG(__v_raw, "startHour", __v_raw.startHour, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("startHour")) {
                return
            }
            val oldValue = __v_raw.startHour
            __v_raw.startHour = value
            _tRS(__v_raw, "startHour", oldValue, value)
        }
    override var endHour: Number
        get() {
            return _tRG(__v_raw, "endHour", __v_raw.endHour, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("endHour")) {
                return
            }
            val oldValue = __v_raw.endHour
            __v_raw.endHour = value
            _tRS(__v_raw, "endHour", oldValue, value)
        }
}
open class AchievementItem (
    @JsonNotNull
    open var def: AchievementDef,
    @JsonNotNull
    open var isUnlocked: Boolean = false,
    @JsonNotNull
    open var currentProgress: Number,
    @JsonNotNull
    open var unlockedAt: Number,
    @JsonNotNull
    open var isNew: Boolean = false,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AchievementItem", "models/Achievement.uts", 26, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return AchievementItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class AchievementItemReactiveObject : AchievementItem, IUTSReactive<AchievementItem> {
    override var __v_raw: AchievementItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: AchievementItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(def = __v_raw.def, isUnlocked = __v_raw.isUnlocked, currentProgress = __v_raw.currentProgress, unlockedAt = __v_raw.unlockedAt, isNew = __v_raw.isNew) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): AchievementItemReactiveObject {
        return AchievementItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var def: AchievementDef
        get() {
            return _tRG(__v_raw, "def", __v_raw.def, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("def")) {
                return
            }
            val oldValue = __v_raw.def
            __v_raw.def = value
            _tRS(__v_raw, "def", oldValue, value)
        }
    override var isUnlocked: Boolean
        get() {
            return _tRG(__v_raw, "isUnlocked", __v_raw.isUnlocked, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("isUnlocked")) {
                return
            }
            val oldValue = __v_raw.isUnlocked
            __v_raw.isUnlocked = value
            _tRS(__v_raw, "isUnlocked", oldValue, value)
        }
    override var currentProgress: Number
        get() {
            return _tRG(__v_raw, "currentProgress", __v_raw.currentProgress, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("currentProgress")) {
                return
            }
            val oldValue = __v_raw.currentProgress
            __v_raw.currentProgress = value
            _tRS(__v_raw, "currentProgress", oldValue, value)
        }
    override var unlockedAt: Number
        get() {
            return _tRG(__v_raw, "unlockedAt", __v_raw.unlockedAt, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("unlockedAt")) {
                return
            }
            val oldValue = __v_raw.unlockedAt
            __v_raw.unlockedAt = value
            _tRS(__v_raw, "unlockedAt", oldValue, value)
        }
    override var isNew: Boolean
        get() {
            return _tRG(__v_raw, "isNew", __v_raw.isNew, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("isNew")) {
                return
            }
            val oldValue = __v_raw.isNew
            __v_raw.isNew = value
            _tRS(__v_raw, "isNew", oldValue, value)
        }
}
open class AchievementProgress (
    @JsonNotNull
    open var current: Number,
    @JsonNotNull
    open var target: Number,
    @JsonNotNull
    open var text: String,
    @JsonNotNull
    open var percent: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AchievementProgress", "models/Achievement.uts", 34, 13)
    }
}
val ACHIEVEMENTS = _uA(
    AchievementDef(id = "first_action", name = "初出茅庐", icon = "🌱", description = "完成第 1 次微动作", category = "累计", conditionType = "total_count", threshold = 1, rewardText = "🏅 健康之旅正式启程", historyText = "0 → 1：迈出第一步", actionType = "", startHour = 0, endHour = 0),
    AchievementDef(id = "streak_3", name = "初见曙光", icon = "✦", description = "连续 3 天每天完成至少 1 次", category = "坚持", conditionType = "streak_days", threshold = 3, rewardText = "🏅 习惯的种子已发芽", historyText = "连续天数 1 → 2 → 3", actionType = "", startHour = 0, endHour = 0),
    AchievementDef(id = "total_10", name = "小有所成", icon = "🌿", description = "累计完成 10 次微动作", category = "累计", conditionType = "total_count", threshold = 10, rewardText = "🏅 健康生活的初学者", historyText = "1 → 5 → 10：稳步前进", actionType = "", startHour = 0, endHour = 0),
    AchievementDef(id = "total_30", name = "习惯养成", icon = "🌳", description = "累计完成 30 次微动作", category = "累计", conditionType = "total_count", threshold = 30, rewardText = "🏅 习惯已成自然", historyText = "10 → 20 → 30：质的飞跃", actionType = "", startHour = 0, endHour = 0),
    AchievementDef(id = "total_100", name = "守护百次", icon = "🏆", description = "累计完成 100 次微动作", category = "累计", conditionType = "total_count", threshold = 100, rewardText = "🏆 健康守护者", historyText = "30 → 60 → 100：百日筑基", actionType = "", startHour = 0, endHour = 0),
    AchievementDef(id = "eye_score_90", name = "眼明心亮", icon = "◐", description = "单日护眼评分达 90 分以上", category = "评分", conditionType = "eye_score", threshold = 90, rewardText = "◐ 护眼小达人", historyText = "70 → 80 → 90：明眸善睐", actionType = "", startHour = 0, endHour = 0),
    AchievementDef(id = "neck_10", name = "颈肩舒畅", icon = "💆", description = "累计完成 10 次肩颈类动作", category = "动作", conditionType = "category_count", threshold = 10, rewardText = "💆 肩颈不再僵硬", historyText = "1 → 5 → 10：肩颈舒展", actionType = "肩颈", startHour = 0, endHour = 0),
    AchievementDef(id = "breath_10", name = "深呼吸者", icon = "◎", description = "累计完成 10 次腹式呼吸", category = "动作", conditionType = "category_count", threshold = 10, rewardText = "◎ 调息养神大师", historyText = "1 → 5 → 10：呼吸有度", actionType = "呼吸", startHour = 0, endHour = 0),
    AchievementDef(id = "morning_5", name = "晨型人", icon = "🌅", description = "凌晨 5 点至 9 点累计完成 5 次", category = "时段", conditionType = "hour_range_count", threshold = 5, rewardText = "🌅 拥抱清晨第一缕阳光", historyText = "1 → 3 → 5：晨起打卡", actionType = "", startHour = 5, endHour = 9),
    AchievementDef(id = "night_5", name = "夜猫子", icon = "🌙", description = "晚 22 点至凌晨 2 点累计完成 5 次", category = "时段", conditionType = "hour_range_count", threshold = 5, rewardText = "🌙 深夜的健康守护者", historyText = "1 → 3 → 5：夜半灯下", actionType = "", startHour = 22, endHour = 2),
    AchievementDef(id = "daily_30", name = "极限挑战", icon = "🚀", description = "单日完成 30 次微动作", category = "单日", conditionType = "daily_count", threshold = 30, rewardText = "🚀 一天 30 次的硬核玩家", historyText = "10 → 20 → 30：单日极限", actionType = "", startHour = 0, endHour = 0),
    AchievementDef(id = "streak_7", name = "持之以恒", icon = "⭐", description = "连续 7 天每天完成至少 1 次", category = "坚持", conditionType = "streak_days", threshold = 7, rewardText = "⭐ 恒心之王", historyText = "3 → 5 → 7：七日成章", actionType = "", startHour = 0, endHour = 0)
) as UTSArray<AchievementDef>
val ACHIEVEMENT_MAP: Map<String, AchievementDef> = Map()
fun buildMap(): Unit {
    run {
        var i: Number = 0
        while(i < ACHIEVEMENTS.length){
            val a = ACHIEVEMENTS[i]
            ACHIEVEMENT_MAP.set(a.id, a)
            i++
        }
    }
}
val runBlock3 = run {
    buildMap()
}
fun getAchievementDef(id: String): AchievementDef? {
    return ACHIEVEMENT_MAP.get(id)
}
fun getAllAchievementDefs(): UTSArray<AchievementDef> {
    return ACHIEVEMENTS
}
open class UserAchievementRow (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var achievementId: String,
    @JsonNotNull
    open var unlockedAt: Number,
    @JsonNotNull
    open var currentProgress: Number,
    @JsonNotNull
    open var createdAt: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UserAchievementRow", "database/AchievementDao.uts", 3, 13)
    }
}
fun mapRow__7(row: Map<String, Any>): UserAchievementRow {
    return UserAchievementRow(id = getNum(row, "id"), achievementId = getStr(row, "achievement_id"), unlockedAt = getNum(row, "unlocked_at"), currentProgress = getNum(row, "current_progress"), createdAt = getNum(row, "created_at"))
}
fun getAllUnlocked(): UTSArray<UserAchievementRow> {
    val result: UTSArray<UserAchievementRow> = _uA()
    try {
        val rows = dbManager.query("SELECT * FROM user_achievements ORDER BY unlocked_at DESC")
        run {
            var i: Number = 0
            while(i < rows.length){
                val r = rows[i]
                if (r == null) {
                    i++
                    continue
                }
                try {
                    result.push(mapRow__7(r))
                }
                 catch (e: Throwable) {
                    console.warn("[AchievementDao] mapRow skip dirty row: " + JSON.stringify(e), " at database/AchievementDao.uts:32")
                }
                i++
            }
        }
    }
     catch (e: Throwable) {
        console.warn("[AchievementDao] getAllUnlocked 异常（表可能尚未创建）: " + JSON.stringify(e), " at database/AchievementDao.uts:36")
    }
    return result
}
fun isUnlocked(achievementId: String): Boolean {
    try {
        val row = dbManager.queryOne("SELECT id FROM user_achievements WHERE achievement_id = ? LIMIT 1", _uA(
            achievementId
        ))
        return row != null
    }
     catch (e: Throwable) {
        console.warn("[AchievementDao] isUnlocked 异常（表可能尚未创建）: " + JSON.stringify(e), " at database/AchievementDao.uts:49")
        return false
    }
}
fun insertUnlock(achievementId: String, progress: Number): Unit {
    val now = Math.floor(Date.now() / 1000)
    val row = SqlRow(columns = _uA(
        "achievement_id",
        "unlocked_at",
        "current_progress",
        "created_at"
    ), values = _uA(
        achievementId,
        now,
        progress,
        now
    ))
    try {
        dbManager.insert("user_achievements", row)
    }
     catch (e: Throwable) {
        console.warn("[AchievementDao] insertUnlock 失败: " + JSON.stringify(e), " at database/AchievementDao.uts:63")
    }
}
fun computeProgress(def: AchievementDef): Number {
    when (def.conditionType) {
        "total_count" -> 
            return getTotalCompletedCount()
        "streak_days" -> 
            return getCurrentStreakDays()
        "eye_score" -> 
            return getTodayEyeScore()
        "category_count" -> 
            return getCategoryCompletedCount(def.actionType)
        "hour_range_count" -> 
            return getHourRangeCompletedCount(def.startHour, def.endHour)
        "daily_count" -> 
            return getTodayCompletedCount()
        else -> 
            return 0
    }
}
fun getTotalCompletedCount(): Number {
    try {
        val row = dbManager.queryOne("SELECT COUNT(*) as cnt FROM action_logs WHERE result IN ('completed', 'self_reported')")
        return getNum(row, "cnt")
    }
     catch (_: Throwable) {
        return 0
    }
}
fun getTodayCompletedCount(): Number {
    try {
        val startOfDay = Math.floor(Date().getTime() / 1000)
        val dayStart = startOfDay - (Date().getHours() * 3600 + Date().getMinutes() * 60 + Date().getSeconds())
        val row = dbManager.queryOne("SELECT COUNT(*) as cnt FROM action_logs WHERE result IN ('completed', 'self_reported') AND created_at >= ?", _uA(
            dayStart
        ))
        return getNum(row, "cnt")
    }
     catch (_: Throwable) {
        return 0
    }
}
fun getCurrentStreakDays(): Number {
    try {
        val rows = dbManager.query("SELECT date, total_completed FROM daily_summaries ORDER BY date DESC LIMIT 30")
        if (rows.length < 1) {
            return 0
        }
        var streak: Number = 0
        var expectedDate = today()
        run {
            var i: Number = 0
            while(i < rows.length){
                val r = rows[i]
                if (r == null) {
                    i++
                    continue
                }
                val dateVal = r.get("date") as String
                if (dateVal == null || dateVal.length < 1) {
                    i++
                    continue
                }
                if (dateVal != expectedDate) {
                    break
                }
                val total = getNum(r, "total_completed")
                if (total > 0) {
                    streak++
                    expectedDate = prevDateString(expectedDate)
                } else {
                    break
                }
                i++
            }
        }
        return streak
    }
     catch (_: Throwable) {
        return 0
    }
}
fun prevDateString(dateStr: String): String {
    val parts = dateStr.split("-")
    if (parts.length < 3) {
        return dateStr
    }
    val y = parseInt(parts[0] as String)
    val m = parseInt(parts[1] as String)
    val d = parseInt(parts[2] as String)
    val dt = Date(y, m - 1, d)
    dt.setDate(dt.getDate() - 1)
    return dt.getFullYear() + "-" + ("" + (dt.getMonth() + 1)).padStart(2, "0") + "-" + ("" + dt.getDate()).padStart(2, "0")
}
fun getTodayEyeScore(): Number {
    try {
        val t = today()
        val row = dbManager.queryOne("SELECT eye_score FROM daily_summaries WHERE date = ?", _uA(
            t
        ))
        if (row == null) {
            return 0
        }
        return getNum(row, "eye_score")
    }
     catch (_: Throwable) {
        return 0
    }
}
fun getCategoryCompletedCount(category: String): Number {
    try {
        if (category.length < 1) {
            return 0
        }
        val row = dbManager.queryOne("SELECT COUNT(*) as cnt FROM action_logs WHERE result IN ('completed', 'self_reported') AND action_type = ?", _uA(
            category
        ))
        return getNum(row, "cnt")
    }
     catch (_: Throwable) {
        return 0
    }
}
fun getHourRangeCompletedCount(startHour: Number, endHour: Number): Number {
    try {
        val rows = dbManager.query("SELECT created_at FROM action_logs WHERE result IN ('completed', 'self_reported')")
        var n: Number = 0
        run {
            var i: Number = 0
            while(i < rows.length){
                val r = rows[i]
                if (r == null) {
                    i++
                    continue
                }
                val ts = getNum(r, "created_at")
                val d = Date(ts * 1000)
                val h = d.getHours()
                if (isHourInRange(h, startHour, endHour)) {
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
fun isHourInRange(h: Number, startHour: Number, endHour: Number): Boolean {
    if (startHour <= endHour) {
        return h >= startHour && h < endHour
    }
    return h >= startHour || h < endHour
}
fun checkAndUnlock(): UTSArray<String> {
    val newUnlocked: UTSArray<String> = _uA()
    val defs = getAllAchievementDefs()
    run {
        var i: Number = 0
        while(i < defs.length){
            val def = defs[i]
            if (isUnlocked(def.id)) {
                i++
                continue
            }
            var cur: Number = 0
            try {
                cur = computeProgress(def)
            }
             catch (e: Throwable) {
                console.warn("[AchievementService] computeProgress 失败 id=" + def.id + " type=" + UTSAndroid.`typeof`(e), " at services/AchievementService.uts:150")
                i++
                continue
            }
            if (cur >= def.threshold) {
                try {
                    insertUnlock(def.id, cur)
                    newUnlocked.push(def.id)
                }
                 catch (e: Throwable) {
                    console.warn("[AchievementService] insertUnlock 失败: " + JSON.stringify(e), " at services/AchievementService.uts:158")
                }
            }
            i++
        }
    }
    return newUnlocked
}
fun getAchievementItems(seenSet: Set<String>): UTSArray<AchievementItem> {
    val unlockedRows: UTSArray<UserAchievementRow> = getAllUnlocked()
    val unlockedMap: Map<String, Number> = Map()
    run {
        var i: Number = 0
        while(i < unlockedRows.length){
            val r = unlockedRows[i]
            unlockedMap.set(r.achievementId, r.unlockedAt)
            i++
        }
    }
    val result: UTSArray<AchievementItem> = _uA()
    val defs = getAllAchievementDefs()
    run {
        var i: Number = 0
        while(i < defs.length){
            val def = defs[i]
            val unlockedAt = unlockedMap.get(def.id)
            val isUnlocked = unlockedAt != null
            var cur: Number = 0
            try {
                cur = computeProgress(def)
            }
             catch (e: Throwable) {
                console.warn("[AchievementService] computeProgress 失败 id=" + def.id + " type=" + UTSAndroid.`typeof`(e), " at services/AchievementService.uts:183")
            }
            val item = AchievementItem(def = def, isUnlocked = isUnlocked, currentProgress = cur, unlockedAt = if (unlockedAt != null) {
                unlockedAt
            } else {
                0
            }
            , isNew = isUnlocked && !seenSet.has(def.id))
            result.push(item)
            i++
        }
    }
    return result
}
fun getProgressText(item: AchievementItem): AchievementProgress {
    val def = item.def
    if (item.isUnlocked) {
        return AchievementProgress(current = def.threshold, target = def.threshold, text = "已达成", percent = 1)
    }
    val current = Math.min(item.currentProgress, def.threshold)
    val target = def.threshold
    val percent = if (target > 0) {
        Math.min(current / target, 1)
    } else {
        0
    }
    return AchievementProgress(current = current, target = target, text = current + " / " + target, percent = percent)
}
open class GenApp : BaseApp {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {
        setCurrentInstance(__ins)
        __ins.proxy = this
        GenApp.setup(this)
    }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenApp) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenApp
            val _cache = __ins.renderCache
            var firstBackTime: Number = 0
            var triggeredActionId = ""
            var pendingDecisionActionId = ""
            var pendingDecisionLevel = ""
            var pendingDecisionTts = ""
            var pendingDecisionDuration: Number = 0
            var pendingAdhocText = ""
            var pendingActionId = ""
            var pendingActionIds: Map<String, Boolean> = Map<String, Boolean>()
            val DEFAULT_PKG_COOLDOWN_MIN: Number = 20
            fun gen_getPkgCooldownMs_fn(): Number {
                val min = getInt("trigger_pkg_cooldown_min", DEFAULT_PKG_COOLDOWN_MIN)
                if (min < 1) {
                    return DEFAULT_PKG_COOLDOWN_MIN * 60000
                }
                return min * 60000
            }
            val getPkgCooldownMs = ::gen_getPkgCooldownMs_fn
            var lastTriggerPerPkg: Map<String, Number> = Map<String, Number>()
            fun gen_safeNowHour_fn(): Number {
                try {
                    return currentHour()
                }
                 catch (_: Throwable) {
                    return 12
                }
            }
            val safeNowHour = ::gen_safeNowHour_fn
            fun gen_safeToday_fn(): String {
                try {
                    return today()
                }
                 catch (_: Throwable) {
                    return ""
                }
            }
            val safeToday = ::gen_safeToday_fn
            fun gen_buildPreContext_fn(info: AppForegroundInfo, decision: TriggerDecision): PreTriggerContext {
                val recent = getRecentLogs(5)
                val names: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < recent.length){
                        val r = recent[i]
                        val a = getActionById(r.action_id)
                        if (a != null) {
                            names.push(a.name)
                        } else {
                            names.push(r.action_id)
                        }
                        i++
                    }
                }
                val homeData = getHomepageData(safeToday())
                val guardMin = Math.floor(getTotalCompletedDurationSec(safeToday()) / 60)
                val todaySkipCount = countSkippedToday()
                val appHistory = getAppHistory(info.packageName, 7)
                val profile = getUserProfile()
                val matchedRule = MatchedRuleInfo(actionId = decision.actionId, source = decision.matchedRuleSource, timeWindow = null, thresholdMinutes = if (decision.durationMs > 0) {
                    Math.round(decision.durationMs / 60000)
                } else {
                    0
                }
                , triggerLevel = decision.triggerLevel)
                return PreTriggerContext(appLabel = info.packageName, appPackage = info.packageName, appCategory = if (appHistory != null) {
                    appHistory.appCategory
                } else {
                    "other"
                }
                , continuousMinutes = Math.floor(info.continuousMs / 60000), hour = safeNowHour(), todayCompletedCount = homeData.todayCompletedCount, guardMinutes = guardMin, todaySkipCount = todaySkipCount, recentActions = names, appHistory = if (appHistory != null) {
                    PreAppHistory(totalTriggers = appHistory.totalTriggers, totalCompletes = appHistory.totalCompletes, totalSkips = appHistory.totalSkips, acceptRate = appHistory.acceptRate, typicalContinuousMin = appHistory.typicalContinuousMin, lastTriggerAt = appHistory.lastTriggerAt)
                } else {
                    null
                }
                , matchedRule = matchedRule, userPreference = UserPreferenceInfo(healthFocus = profile.healthFocus, bodyLimit = profile.bodyLimit))
            }
            val buildPreContext = ::gen_buildPreContext_fn
            fun gen_buildPostContext_fn(actionName: String, actionCategory: String, durationMs: Number, appPackage: String): PostActionContext {
                val recent = getRecentLogs(5)
                val names: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < recent.length){
                        val r = recent[i]
                        val a = getActionById(r.action_id)
                        if (a != null) {
                            names.push(a.name)
                        } else {
                            names.push(r.action_id)
                        }
                        i++
                    }
                }
                val homeData = getHomepageData(safeToday())
                val guardMin = Math.floor(getTotalCompletedDurationSec(safeToday()) / 60)
                val todaySkipCount = countSkippedToday()
                val profile = getUserProfile()
                val appHistory = getAppHistory(appPackage, 7)
                val rules = getActiveRuleList()
                val currentRules: UTSArray<PostContextRuleItem> = _uA()
                run {
                    var i: Number = 0
                    while(i < rules.length){
                        val r = rules[i]
                        val item = PostContextRuleItem(id = r.id, actionId = r.actionId, appPackages = r.appPackages, timeWindow = r.timeWindow, thresholdMinutes = r.timeThresholdMinutes, triggerLevel = r.triggerLevel, source = r.source, createdAt = r.createdAt)
                        currentRules.push(item)
                        i++
                    }
                }
                return PostActionContext(actionId = pendingActionId, actionName = actionName, actionCategory = actionCategory, durationMs = durationMs, originalContext = pendingAdhocText, todayCompletedCount = homeData.todayCompletedCount, guardMinutes = guardMin, todaySkipCount = todaySkipCount, recentActions = names, appHistory = if (appHistory != null) {
                    PostAppHistory(packageName = appHistory.packageName, totalTriggers = appHistory.totalTriggers, totalCompletes = appHistory.totalCompletes, totalSkips = appHistory.totalSkips, acceptRate = appHistory.acceptRate, typicalContinuousMin = appHistory.typicalContinuousMin, lastWeekTrend = appHistory.lastWeekTrend)
                } else {
                    null
                }
                , currentRules = currentRules, userPreference = UserPreferenceInfo(healthFocus = profile.healthFocus, bodyLimit = profile.bodyLimit), lastWeekSameTime = null)
            }
            val buildPostContext = ::gen_buildPostContext_fn
            fun gen_checkAchievementsAndNotify_fn(): Unit {
                try {
                    val newUnlocked = checkAndUnlock()
                    if (newUnlocked.length > 0) {
                        try {
                            uni__emit("pendingAchievementUnlock", _uO("ids" to newUnlocked))
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] emit pendingAchievementUnlock 失败 type=" + UTSAndroid.`typeof`(e) + " msg=" + (if (e != null) {
                                ("" + e)
                            } else {
                                "null"
                            }
                            ), " at App.uvue:188")
                        }
                    }
                }
                 catch (e: Throwable) {
                    console.warn("[App.uvue] checkAchievementsAndNotify 异常 type=" + UTSAndroid.`typeof`(e) + " msg=" + (if (e != null) {
                        ("" + e)
                    } else {
                        "null"
                    }
                    ), " at App.uvue:192")
                }
            }
            val checkAchievementsAndNotify = ::gen_checkAchievementsAndNotify_fn
            fun gen_emitShowRuleDialog_fn(payload: UTSJSONObject): Unit {
                try {
                    uni__emit("showTriggerRuleDialog", payload)
                }
                 catch (e: Throwable) {
                    console.warn("[App.uvue] emitShowRuleDialog 失败: " + JSON.stringify(e), " at App.uvue:200")
                }
            }
            val emitShowRuleDialog = ::gen_emitShowRuleDialog_fn
            fun gen_onActionResolvedLLM_fn(actionId: String, actionName: String, actionCategory: String, durationMs: Number, appPackage: String): Unit {
                if (pendingActionIds.has(actionId)) {
                    console.log("[App.uvue] onActionResolvedLLM skip duplicate: " + actionId, " at App.uvue:207")
                    return
                }
                pendingActionIds.set(actionId, true)
                setTimeout(fun(): Unit {
                    pendingActionIds.`delete`(actionId)
                }
                , 5000)
                val llmEnabled = getBool("llm_trigger_enabled", true)
                if (!llmEnabled) {
                    clearPreContext()
                    clearPostContext()
                    return
                }
                pendingActionId = actionId
                actionName
                val postCtx = buildPostContext(actionName, actionCategory, durationMs, appPackage)
                setPostContext(postCtx)
            }
            val onActionResolvedLLM = ::gen_onActionResolvedLLM_fn
            fun gen_tryShowLlmRuleDialog_fn(postResult: PostActionResult, actionId: String): Unit {
                try {
                    if (postResult.decision == "no_change" || postResult.suggestedRule == null) {
                        return
                    }
                    val askEach = getBool("llm_trigger_ask_each_time", true)
                    if (askEach) {
                        val rule = postResult.suggestedRule!!
                        val payload: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("payload", "App.uvue", 235, 13), "rule" to rule, "reasoning" to postResult.reasoning, "confidence" to postResult.confidence, "historyId" to postResult.historyId, "decision" to postResult.decision, "targetRuleId" to postResult.historyId)
                        emitShowRuleDialog(payload)
                    } else {
                        try {
                            if (actionId.length > 0) {
                                val nowSec = Math.floor(Date.now() / 1000)
                                val rule = postResult.suggestedRule!!
                                var pkgList: UTSArray<String> = _uA()
                                if (rule.screenConditions != null) {
                                    pkgList = rule.screenConditions!!.appPackages
                                }
                                val ruleRow = PersistedEffectiveRule(id = 0, actionId = rule.actionId, appPackages = pkgList, timeWindow = rule.timeWindow, timeThresholdMinutes = rule.timeThresholdMinutes, triggerLevel = "gentle", categoryFilter = null, source = "llm", sourceHistoryId = postResult.historyId, expiresAt = null, enabled = true, priority = 5, createdAt = nowSec, updatedAt = nowSec)
                                insertRule(ruleRow)
                            }
                        }
                         catch (e2: Throwable) {
                            console.warn("[App.uvue] 静默保存 suggestedRule 失败: " + JSON.stringify(e2), " at App.uvue:272")
                        }
                    }
                }
                 catch (e: Throwable) {
                    console.warn("[App.uvue] tryShowLlmRuleDialog 异常: " + JSON.stringify(e), " at App.uvue:276")
                }
            }
            val tryShowLlmRuleDialog = ::gen_tryShowLlmRuleDialog_fn
            fun gen_showOverlayWithAdhoc_fn(decision: TriggerDecision, adhocText: String, fallback: Boolean): Unit {
                triggeredActionId = decision.actionId
                pendingDecisionActionId = decision.actionId
                pendingDecisionLevel = decision.triggerLevel
                pendingDecisionTts = decision.ttsText
                pendingDecisionDuration = decision.durationMs
                pendingAdhocText = adhocText
                try {
                    ensureCategory(if (decision.actionId == null) {
                        ""
                    } else {
                        decision.actionId
                    }
                    , decision.actionName)
                }
                 catch (_: Throwable) {}
                val actionForEncourage = getActionById(decision.actionId)
                val encourageText = if (actionForEncourage != null) {
                    getRandomEncourage(actionForEncourage.category)
                } else {
                    "又完成一次！"
                }
                val cfg = OverlayConfig(level = decision.triggerLevel, actionName = decision.actionName, ttsText = adhocText, durationMs = decision.durationMs, lottieAssetPath = null, encourageText = encourageText)
                val cbs = OverlayCallbacks(onAgree = fun(): Unit {
                    onUserAccepted()
                    dismissOverlay()
                    val aid = triggeredActionId
                    setActionIdForNextPage(aid)
                    uni_navigateTo(NavigateToOptions(url = "/pages/action/execute?actionId=" + aid))
                }
                , onSelfReported = fun(): Unit {
                    val action = getActionById(pendingDecisionActionId)
                    if (action != null) {
                        insertActionLog(ActionLog(id = 0, action_id = action.id, action_type = action.category, result = "self_reported", skip_reason = null, trigger_type = "app_duration", trigger_level = pendingDecisionLevel, duration_ms = 0, target_ms = action.defaultDurationMs, triggered_at = Math.floor(Date.now() / 1000), completed_at = Math.floor(Date.now() / 1000), created_at = Math.floor(Date.now() / 1000)))
                        checkAchievementsAndNotify()
                    }
                    onActionResolved()
                    val pkgName = ""
                    onActionResolvedLLM(pendingDecisionActionId, if (action != null) {
                        action.name
                    } else {
                        ""
                    }
                    , if (action != null) {
                        action.category
                    } else {
                        ""
                    }
                    , if (action != null) {
                        action.defaultDurationMs
                    } else {
                        0
                    }
                    , pkgName)
                    if (getBool("llm_trigger_enabled", true)) {
                        try {
                            evaluatePost(pendingDecisionActionId, if (action != null) {
                                action.name
                            } else {
                                ""
                            }
                            , if (action != null) {
                                action.category
                            } else {
                                ""
                            }
                            , if (action != null) {
                                action.defaultDurationMs
                            } else {
                                0
                            }
                            , fun(postResult: PostActionResult): Unit {
                                try {
                                    tryShowLlmRuleDialog(postResult, pendingDecisionActionId)
                                }
                                 catch (e: Throwable) {
                                    console.warn("[App.uvue] onSelfReported post-result 异常: " + JSON.stringify(e), " at App.uvue:342")
                                }
                            }
                            , fun(): Unit {
                                console.warn("[App.uvue] onSelfReported evaluatePost fail", " at App.uvue:346")
                            }
                            )
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] onSelfReported evaluatePost 异常: " + JSON.stringify(e), " at App.uvue:350")
                        }
                    }
                    dismissOverlay()
                }
                , onBusyRemindLater = fun(): Unit {
                    val action = getActionById(pendingDecisionActionId)
                    if (action != null) {
                        insertActionLog(ActionLog(id = 0, action_id = action.id, action_type = action.category, result = "skipped", skip_reason = "busy", trigger_type = "app_duration", trigger_level = pendingDecisionLevel, duration_ms = 0, target_ms = action.defaultDurationMs, triggered_at = Math.floor(Date.now() / 1000), completed_at = Math.floor(Date.now() / 1000), created_at = Math.floor(Date.now() / 1000)))
                    }
                    onActionResolved()
                    onActionResolvedLLM(pendingDecisionActionId, if (action != null) {
                        action.name
                    } else {
                        ""
                    }
                    , if (action != null) {
                        action.category
                    } else {
                        ""
                    }
                    , 0, "")
                    if (getBool("llm_trigger_enabled", true)) {
                        try {
                            evaluatePost(pendingDecisionActionId, if (action != null) {
                                action.name
                            } else {
                                ""
                            }
                            , if (action != null) {
                                action.category
                            } else {
                                ""
                            }
                            , 0, fun(postResult: PostActionResult): Unit {
                                try {
                                    tryShowLlmRuleDialog(postResult, pendingDecisionActionId)
                                }
                                 catch (e: Throwable) {
                                    console.warn("[App.uvue] onBusyRemindLater post-result 异常: " + JSON.stringify(e), " at App.uvue:385")
                                }
                            }
                            , fun(): Unit {
                                console.warn("[App.uvue] onBusyRemindLater evaluatePost fail", " at App.uvue:389")
                            }
                            )
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] onBusyRemindLater evaluatePost 异常: " + JSON.stringify(e), " at App.uvue:393")
                        }
                    }
                    dismissOverlay()
                }
                , onSkipDuringExec = fun(): Unit {
                    onActionResolved()
                    onActionResolvedLLM(pendingDecisionActionId, "", "", 0, "")
                    if (getBool("llm_trigger_enabled", true)) {
                        try {
                            evaluatePost(pendingDecisionActionId, "", "", 0, fun(postResult: PostActionResult): Unit {
                                try {
                                    tryShowLlmRuleDialog(postResult, pendingDecisionActionId)
                                }
                                 catch (e: Throwable) {
                                    console.warn("[App.uvue] onSkipDuringExec post-result 异常: " + JSON.stringify(e), " at App.uvue:411")
                                }
                            }
                            , fun(): Unit {
                                console.warn("[App.uvue] onSkipDuringExec evaluatePost fail", " at App.uvue:415")
                            }
                            )
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] onSkipDuringExec evaluatePost 异常: " + JSON.stringify(e), " at App.uvue:419")
                        }
                    }
                    dismissOverlay()
                }
                , onCountdownTick = fun(_remaining: Number): Unit {}, onCountdownFinish = fun(): Unit {
                    shortVibrate(2)
                    val encourageTextFinal = encourageText
                    try {
                        speakFixedGuide(encourageTextFinal)
                    }
                     catch (_: Throwable) {}
                    val action = getActionById(pendingDecisionActionId)
                    if (action != null) {
                        insertActionLog(ActionLog(id = 0, action_id = action.id, action_type = action.category, result = "completed", skip_reason = null, trigger_type = "app_duration", trigger_level = pendingDecisionLevel, duration_ms = action.defaultDurationMs, target_ms = action.defaultDurationMs, triggered_at = Math.floor(Date.now() / 1000), completed_at = Math.floor(Date.now() / 1000), created_at = Math.floor(Date.now() / 1000)))
                        checkAchievementsAndNotify()
                    }
                    onActionResolved()
                    onActionResolvedLLM(pendingDecisionActionId, if (action != null) {
                        action.name
                    } else {
                        ""
                    }
                    , if (action != null) {
                        action.category
                    } else {
                        ""
                    }
                    , if (action != null) {
                        action.defaultDurationMs
                    } else {
                        0
                    }
                    , "")
                    if (getBool("llm_trigger_enabled", true)) {
                        try {
                            evaluatePost(pendingDecisionActionId, if (action != null) {
                                action.name
                            } else {
                                ""
                            }
                            , if (action != null) {
                                action.category
                            } else {
                                ""
                            }
                            , if (action != null) {
                                action.defaultDurationMs
                            } else {
                                0
                            }
                            , fun(postResult: PostActionResult): Unit {
                                try {
                                    tryShowLlmRuleDialog(postResult, pendingDecisionActionId)
                                }
                                 catch (e: Throwable) {
                                    console.warn("[App.uvue] onCountdownFinish post-result 异常: " + JSON.stringify(e), " at App.uvue:459")
                                }
                            }
                            , fun(): Unit {
                                console.warn("[App.uvue] onCountdownFinish evaluatePost fail", " at App.uvue:463")
                            }
                            )
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] onCountdownFinish evaluatePost 异常: " + JSON.stringify(e), " at App.uvue:467")
                        }
                    }
                }
                , onPartialCompletion = fun(_completed: Number, _total: Number): Unit {})
                try {
                    showOverlay(cfg, cbs)
                }
                 catch (e: Throwable) {
                    console.warn("[App.uvue] showOverlay 失败: " + JSON.stringify(e), " at App.uvue:476")
                }
                try {
                    if (getBool("tts_enabled", true)) {
                        val actionNameStr = decision.actionName ?: ""
                        val merged = actionNameStr + (if (adhocText.length > 0) {
                            ("，" + adhocText)
                        } else {
                            ""
                        }
                        )
                        if (merged.length > 0) {
                            speakAdhoc(merged)
                        }
                    }
                }
                 catch (e: Throwable) {
                    console.warn("[App.uvue] 播 TTS 开场白失败: " + JSON.stringify(e), " at App.uvue:487")
                }
            }
            val showOverlayWithAdhoc = ::gen_showOverlayWithAdhoc_fn
            onLaunch(fun(_options){
                try {
                    uni__on("llmActionCompleted", fun(data: Any): Unit {
                        try {
                            if (data == null) {
                                return
                            }
                            val obj = data as UTSJSONObject
                            if (obj == null) {
                                return
                            }
                            val actionId = obj["actionId"] as String
                            val actionName = obj["actionName"] as String
                            val actionCategory = obj["actionCategory"] as String
                            val durationMs = (obj["durationMs"] as Number).toInt()
                            val appPackage = if (obj["appPackage"] != null) {
                                (obj["appPackage"] as String)
                            } else {
                                ""
                            }
                            if (actionId == null || actionId.length < 1) {
                                return
                            }
                            onActionResolvedLLM(actionId, actionName, actionCategory, durationMs, appPackage)
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] llmActionCompleted 处理异常: " + JSON.stringify(e), " at App.uvue:506")
                        }
                    }
                    )
                    uni__on("manualTriggerCheck", fun(_data: Any): Unit {
                        try {
                            console.log("[manualTrigger] 开始检查", " at App.uvue:511")
                            val triggerVal = getBool("trigger_enabled", true)
                            console.log("[manualTrigger] trigger_enabled=" + triggerVal, " at App.uvue:513")
                            if (!triggerVal) {
                                console.log("[manualTrigger] 触发已关闭", " at App.uvue:515")
                                uni_showToast(ShowToastOptions(title = "触发已关闭", icon = "none", position = "bottom"))
                                return
                            }
                            if (!isUsagePermissionGranted()) {
                                console.log("[manualTrigger] 缺少使用情况权限", " at App.uvue:520")
                                uni_showToast(ShowToastOptions(title = "缺少使用情况权限", icon = "none", position = "bottom"))
                                return
                            }
                            val thresholdMs = getInt("app_duration_threshold", 60) * 60000
                            console.log("[manualTrigger] 阈值(ms): " + thresholdMs, " at App.uvue:525")
                            val nowMs = Date.now()
                            val startMs = nowMs - 86400000
                            val stats = queryUsageStatsOnce(startMs, nowMs)
                            console.log("[manualTrigger] queryUsageStatsOnce 返回 " + stats.length + " 条", " at App.uvue:529")
                            var triggered = false
                            run {
                                var i: Number = 0
                                while(i < stats.length){
                                    val s = stats[i]
                                    if (s == null) {
                                        i++
                                        continue
                                    }
                                    val totalMs = s.totalTimeInForeground
                                    if (totalMs < thresholdMs) {
                                        i++
                                        continue
                                    }
                                    console.log("[manualTrigger] 超阈值 App: " + s.packageName + " totalMs=" + totalMs, " at App.uvue:536")
                                    val ctx = TriggerContext(appPackage = s.packageName, appLabel = s.packageName, continuousMinutes = Math.floor(totalMs / 60000), triggerType = "app_duration")
                                    forceIdle()
                                    console.log("[manualTrigger] forceIdle done, calling shouldTrigger", " at App.uvue:544")
                                    var decision: TriggerDecision? = null
                                    try {
                                        decision = shouldTrigger(ctx)
                                    }
                                     catch (e2: Throwable) {
                                        val errMsg = if (e2 != null) {
                                            ("" + e2)
                                        } else {
                                            "null"
                                        }
                                        console.warn("[manualTrigger] shouldTrigger THREW: " + errMsg, " at App.uvue:550")
                                        i++
                                        continue
                                    }
                                    if (decision == null) {
                                        console.log("[manualTrigger] shouldTrigger 返回 null, 跳过: " + s.packageName, " at App.uvue:554")
                                        i++
                                        continue
                                    }
                                    console.log("[manualTrigger] shouldTrigger 命中 actionId=" + decision.actionId + " level=" + decision.triggerLevel, " at App.uvue:557")
                                    val info = AppForegroundInfo(packageName = s.packageName, startTime = nowMs, continuousMs = totalMs)
                                    val llmEnabled = getBool("llm_trigger_enabled", true)
                                    if (!llmEnabled) {
                                        console.log("[manualTrigger] LLM 关闭，跳转健康卡片页", " at App.uvue:565")
                                        setActionIdForNextPage(decision.actionId)
                                        setAdhocForNextPage(decision.ttsText)
                                        uni_navigateTo(NavigateToOptions(url = "/pages/action/execute?actionId=" + decision.actionId))
                                    } else {
                                        console.log("[manualTrigger] LLM 开启，走 evaluatePre + 跳转健康卡片页", " at App.uvue:570")
                                        var preCtx: PreTriggerContext? = null
                                        try {
                                            preCtx = buildPreContext(info, decision)
                                        }
                                         catch (e3: Throwable) {
                                            console.warn("[manualTrigger] buildPreContext 异常: " + ("" + e3), " at App.uvue:573")
                                        }
                                        if (preCtx == null) {
                                            console.warn("[manualTrigger] buildPreContext 返回 null，降级跳转", " at App.uvue:576")
                                            setActionIdForNextPage(decision.actionId)
                                            setAdhocForNextPage(getFallbackAdhoc())
                                            uni_navigateTo(NavigateToOptions(url = "/pages/action/execute?actionId=" + decision.actionId))
                                            triggered = true
                                            break
                                        }
                                        try {
                                            setPreContext(preCtx)
                                        }
                                         catch (e4: Throwable) {
                                            console.warn("[manualTrigger] setPreContext 异常: " + ("" + e4), " at App.uvue:584")
                                        }
                                        try {
                                            evaluatePre(fun(preResult: PreTriggerResult): Unit {
                                                console.log("[manualTrigger] evaluatePre 成功 adhocText=" + preResult.adhocText, " at App.uvue:589")
                                                setActionIdForNextPage(decision.actionId)
                                                setAdhocForNextPage(preResult.adhocText)
                                                uni_navigateTo(NavigateToOptions(url = "/pages/action/execute?actionId=" + decision.actionId))
                                                clearPreContext()
                                            }
                                            , fun(): Unit {
                                                console.log("[manualTrigger] evaluatePre 失败，降级跳转", " at App.uvue:596")
                                                setActionIdForNextPage(decision.actionId)
                                                setAdhocForNextPage(getFallbackAdhoc())
                                                uni_navigateTo(NavigateToOptions(url = "/pages/action/execute?actionId=" + decision.actionId))
                                                clearPreContext()
                                            }
                                            )
                                        }
                                         catch (e: Throwable) {
                                            console.warn("[manualTrigger] evaluatePre 异常: " + JSON.stringify(e), " at App.uvue:604")
                                            setActionIdForNextPage(decision.actionId)
                                            setAdhocForNextPage(getFallbackAdhoc())
                                            uni_navigateTo(NavigateToOptions(url = "/pages/action/execute?actionId=" + decision.actionId))
                                            clearPreContext()
                                        }
                                    }
                                    triggered = true
                                    break
                                    i++
                                }
                            }
                            if (!triggered) {
                                console.log("[manualTrigger] 无 App 超过阈值", " at App.uvue:615")
                                uni_showToast(ShowToastOptions(title = "当前无 App 超过阈值，无需触发", icon = "none", position = "bottom"))
                            }
                        }
                         catch (e: Throwable) {
                            val errMsg = if (e != null) {
                                ("" + e)
                            } else {
                                "null"
                            }
                            console.warn("[manualTrigger] 异常: " + errMsg, " at App.uvue:620")
                            uni_showToast(ShowToastOptions(title = "检查失败", icon = "none", position = "bottom"))
                        }
                    }
                    )
                }
                 catch (e: Throwable) {
                    console.warn("[App.uvue] \$on 异常: " + JSON.stringify(e), " at App.uvue:625")
                }
                try {
                    try {
                        sqliteStore.openDatabase("micro_habit_v2.db", getDbVersion(), fun(): Unit {
                            return onCreate()
                        }
                        , fun(oldV: Number, newV: Number): Unit {
                            try {
                                onUpgrade(oldV, newV)
                            }
                             catch (e: Throwable) {
                                console.warn("[App.uvue] onUpgrade 异常: " + JSON.stringify(e), " at App.uvue:630")
                            }
                        }
                        )
                    }
                     catch (e: Throwable) {
                        console.error("[App.uvue] sqliteStore.openDatabase 失败: " + JSON.stringify(e), " at App.uvue:633")
                    }
                    try {
                        setTimeout(fun(): Unit {
                            try {
                                preGenerateAll()
                            }
                             catch (e: Throwable) {
                                val msg = if (e != null) {
                                    ("" + e)
                                } else {
                                    "null"
                                }
                                console.warn("[App.uvue] preGenerateAll 启动失败: " + msg, " at App.uvue:642")
                            }
                        }
                        , 3000)
                    }
                     catch (e: Throwable) {
                        val msg = if (e != null) {
                            ("" + e)
                        } else {
                            "null"
                        }
                        console.warn("[App.uvue] preGenerateAll setTimeout 失败: " + msg, " at App.uvue:647")
                    }
                    try {
                        prewarmSystemTts()
                    }
                     catch (e: Throwable) {
                        val msg = if (e != null) {
                            ("" + e)
                        } else {
                            "null"
                        }
                        console.warn("[App.uvue] prewarmSystemTts 失败: " + msg, " at App.uvue:651")
                    }
                    val callbacks = UsageMonitorCallbacks(onAppDurationTrigger = fun(info: AppForegroundInfo): Unit {
                        try {
                            val nowTs = Date.now()
                            val lastTs = lastTriggerPerPkg.get(info.packageName) ?: 0
                            val pkgCd = getPkgCooldownMs()
                            if (nowTs - lastTs < pkgCd) {
                                console.log("[App.uvue] 跳过同包重复触发: " + info.packageName, " at App.uvue:661")
                                return
                            }
                            lastTriggerPerPkg.set(info.packageName, nowTs)
                            try {
                                ensureCategory(info.packageName, info.packageName)
                            }
                             catch (_: Throwable) {}
                            var decision: TriggerDecision? = null
                            try {
                                val ctx = TriggerContext(appPackage = info.packageName, appLabel = info.packageName, continuousMinutes = Math.floor(info.continuousMs / 60000), triggerType = "app_duration")
                                decision = shouldTrigger(ctx)
                            }
                             catch (e: Throwable) {
                                console.warn("[App.uvue] shouldTrigger 失败: " + JSON.stringify(e), " at App.uvue:678")
                            }
                            if (decision == null) {
                                return
                            }
                            val llmEnabled = getBool("llm_trigger_enabled", true)
                            if (!llmEnabled) {
                                showOverlayWithAdhoc(decision, decision.ttsText, true)
                                return
                            }
                            val preCtx = buildPreContext(info, decision)
                            setPreContext(preCtx)
                            try {
                                evaluatePre(fun(preResult: PreTriggerResult): Unit {
                                    try {
                                        showOverlayWithAdhoc(decision, preResult.adhocText, preResult.fallback)
                                    }
                                     catch (e: Throwable) {
                                        console.warn("[App.uvue] onReady 处理失败: " + JSON.stringify(e), " at App.uvue:696")
                                    }
                                }
                                , fun(): Unit {
                                    try {
                                        showOverlayWithAdhoc(decision, getFallbackAdhoc(), true)
                                    }
                                     catch (e: Throwable) {
                                        console.warn("[App.uvue] onFail 降级失败: " + JSON.stringify(e), " at App.uvue:703")
                                    }
                                    clearPreContext()
                                }
                                )
                            }
                             catch (e: Throwable) {
                                console.warn("[App.uvue] evaluatePre 异常: " + JSON.stringify(e), " at App.uvue:709")
                                showOverlayWithAdhoc(decision, getFallbackAdhoc(), true)
                                clearPreContext()
                            }
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] onAppDurationTrigger 异常: " + JSON.stringify(e), " at App.uvue:714")
                        }
                    }
                    , onAppSwitch = fun(from: String, to: String): Unit {
                        try {
                            ensureCategory(to, to)
                        }
                         catch (_: Throwable) {}
                    }
                    , onUsageTick = fun(pkg: String, sec: Number): Unit {
                        try {
                            setSnapshotTotal(pkg, pkg, sec)
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] onUsageTick 写快照失败: " + JSON.stringify(e), " at App.uvue:725")
                        }
                    }
                    , onTimePeriodTrigger = fun(_name: String): Unit {}, onDayRollover = fun(_old: String, _new: String): Unit {
                        try {
                            saveTodaySummary()
                        }
                         catch (_: Throwable) {}
                    }
                    , onPermissionMissing = fun(): Unit {
                        console.warn("[App.uvue] PACKAGE_USAGE_STATS 权限缺失或被关闭", " at App.uvue:733")
                    }
                    )
                    if (isUsagePermissionGranted()) {
                        val thresholdMs = getInt("app_duration_threshold", 60) * 60000
                        val triggerEnabled = getBool("trigger_enabled", true)
                        val pollMs = getInt("trigger_poll_interval_min", 10) * 60000
                        try {
                            startUsageMonitor(callbacks, thresholdMs, pollMs, triggerEnabled)
                        } catch (e: Throwable) {
                            console.error("[App.uvue] startUsageMonitor 失败: " + JSON.stringify(e), " at App.uvue:744")
                        }
                    } else {
                        console.warn("[App.uvue] 启动时未授予使用情况权限，需在引导页授权", " at App.uvue:747")
                    }
                }
                 catch (e: Throwable) {
                    console.error("App init error: " + JSON.stringify(e), " at App.uvue:750")
                }
                console.log("App Launch", " at App.uvue:752")
            }
            )
            onAppShow(fun(_options){
                try {
                    saveTodaySummary()
                }
                 catch (_: Throwable) {}
                try {
                    val h = safeNowHour()
                    if (h == 22) {
                        batchClassifyTodayNewApps(null)
                    }
                }
                 catch (_: Throwable) {}
                try {
                    checkAchievementsAndNotify()
                }
                 catch (_: Throwable) {}
                try {
                    val triggerOn = getBool("trigger_enabled", true)
                    setTriggerEnabled(triggerOn)
                }
                 catch (_: Throwable) {}
                try {
                    val pollMs = getInt("trigger_poll_interval_min", 10) * 60000
                    setPollIntervalMs(pollMs)
                }
                 catch (_: Throwable) {}
                console.log("App Show", " at App.uvue:774")
            }
            )
            onAppHide(fun(){
                console.log("App Hide", " at App.uvue:778")
            }
            )
            onLastPageBackPress(fun(){
                if (firstBackTime == 0) {
                    uni_showToast(ShowToastOptions(title = "再按一次退出应用", position = "bottom"))
                    firstBackTime = Date.now()
                    setTimeout(fun(): Unit {
                        firstBackTime = 0
                    }, 2000)
                } else if (Date.now() - firstBackTime < 2000) {
                    firstBackTime = Date.now()
                    uni_exit(null)
                }
            }
            )
            onExit(fun(){
                try {
                    stopUsageMonitor()
                }
                 catch (_: Throwable) {}
                try {
                    sqliteStore.close()
                }
                 catch (_: Throwable) {}
                console.log("App Exit", " at App.uvue:797")
            }
            )
            return fun(): Any? {
                return null
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("uni-row" to _pS(_uM("flexDirection" to "row")), "uni-column" to _pS(_uM("flexDirection" to "column")))
            }
    }
}
val GenAppClass = CreateVueAppComponent(GenApp::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "app", name = "", inheritAttrs = true, inject = Map(), props = Map(), propsNeedCastKeys = _uA(), emits = Map(), components = Map(), styles = GenApp.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenApp.setup(props as GenApp)
    }
    )
}
, fun(instance): GenApp {
    return GenApp(instance)
}
)
val GenPagesGuideIndexClass = CreateVueComponent(GenPagesGuideIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesGuideIndex.inheritAttrs, inject = GenPagesGuideIndex.inject, props = GenPagesGuideIndex.props, propsNeedCastKeys = GenPagesGuideIndex.propsNeedCastKeys, emits = GenPagesGuideIndex.emits, components = GenPagesGuideIndex.components, styles = GenPagesGuideIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesGuideIndex.setup(props as GenPagesGuideIndex)
    }
    )
}
, fun(instance, renderer): GenPagesGuideIndex {
    return GenPagesGuideIndex(instance, renderer)
}
)
val GenComponentsStatusIndicatorClass = CreateVueComponent(GenComponentsStatusIndicator::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsStatusIndicator.inheritAttrs, inject = GenComponentsStatusIndicator.inject, props = GenComponentsStatusIndicator.props, propsNeedCastKeys = GenComponentsStatusIndicator.propsNeedCastKeys, emits = GenComponentsStatusIndicator.emits, components = GenComponentsStatusIndicator.components, styles = GenComponentsStatusIndicator.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsStatusIndicator.setup(props as GenComponentsStatusIndicator)
    }
    )
}
, fun(instance, renderer): GenComponentsStatusIndicator {
    return GenComponentsStatusIndicator(instance)
}
)
val GenComponentsActionCardClass = CreateVueComponent(GenComponentsActionCard::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsActionCard.inheritAttrs, inject = GenComponentsActionCard.inject, props = GenComponentsActionCard.props, propsNeedCastKeys = GenComponentsActionCard.propsNeedCastKeys, emits = GenComponentsActionCard.emits, components = GenComponentsActionCard.components, styles = GenComponentsActionCard.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsActionCard.setup(props as GenComponentsActionCard)
    }
    )
}
, fun(instance, renderer): GenComponentsActionCard {
    return GenComponentsActionCard(instance)
}
)
open class TopAppEntry (
    @JsonNotNull
    open var packageName: String,
    @JsonNotNull
    open var appLabel: String,
    @JsonNotNull
    open var totalSeconds: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TopAppEntry", "constants/PhoneUsagePrompts.uts", 2, 13)
    }
}
open class PhoneUsageEvalInput (
    @JsonNotNull
    open var totalMinutes: Number,
    @JsonNotNull
    open var totalSeconds: Number,
    @JsonNotNull
    open var hourText: String,
    @JsonNotNull
    open var minuteText: String,
    @JsonNotNull
    open var appCount: Number,
    @JsonNotNull
    open var topApps: UTSArray<TopAppEntry>,
    @JsonNotNull
    open var isNight: Boolean = false,
    @JsonNotNull
    open var isWorkHour: Boolean = false,
    @JsonNotNull
    open var guardCount: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PhoneUsageEvalInput", "constants/PhoneUsagePrompts.uts", 7, 13)
    }
}
val SYSTEM_PROMPT_PHONE_USAGE: String = "你是微憩星球里的\"小星球\"，温柔、不评判、不会医学恐吓。用中文，2-3 段，每段不超过 40 字，语气像朋友给一个温柔建议。禁止使用表情符号（如 😀😄💕 等 emoji），仅用纯文字。禁止出现\"建议\"\"应当\"\"需要\"等命令式词汇，改用\"也许\"\"或许\"\"可以试试\"。禁止读取任何用户隐私。"
fun buildPhoneUsageEvalPrompt(input: PhoneUsageEvalInput): String {
    val topLines: UTSArray<String> = _uA()
    run {
        var i: Number = 0
        while(i < input.topApps.length){
            val a = input.topApps[i]
            if (a == null) {
                i++
                continue
            }
            val mins = Math.floor(a.totalSeconds / 60)
            topLines.push("  " + a.appLabel + "：" + mins + " 分钟")
            i++
        }
    }
    val period: String = if (input.isNight) {
        "深夜时段"
    } else {
        if (input.isWorkHour) {
            "工作/学习时段"
        } else {
            "休息时段"
        }
    }
    val topBlock: String = if (topLines.length > 0) {
        topLines.join("\n")
    } else {
        "  （无具体应用数据）"
    }
    return "请根据以下今日手机使用情况写一段温柔的评价：\n- 累计使用：" + input.hourText + " " + input.minuteText + "\n" + "- 涉及应用：" + input.appCount + " 个\n" + "- 使用时段：" + period + "\n" + "- 微动作完成：" + input.guardCount + " 次\n" + "- 使用最久的应用：\n" + topBlock + "\n" + "\n" + "要求：\n" + "1. 先用一句话温柔概括今天的使用节奏（不要评判，承认现实）。\n" + "2. 如果夜间使用多，温柔提醒；如果是工作时段，肯定专注。\n" + "3. 给出 1 个不强迫的微动作建议（如\"下次刷完 B 站，也许可以眨眨眼睛\"）。\n" + "4. 末尾加一句\"——微憩星球 🌙\"（注意：末尾的 🌙 是允许的，作为品牌签名）。\n" + "5. **全文不允许出现除末尾 🌙 之外的其他 emoji 字符**。"
}
val FALLBACK_PHONE_USAGE_EVAL_TEXTS = _uA(
    "今天用得有点久，别忘了眨眨眼睛、站起来走两步。\n休息是给自己的礼物。\n——微憩星球",
    "屏幕看久了，眼睛会想你的。\n也许下次可以试试每 20 分钟看向 6 米外 20 秒。\n——微憩星球",
    "手机是我们的工具，也是我们的牵绊。\n今天已经努力了一整天，现在可以放一放啦。\n——微憩星球"
) as UTSArray<String>
fun getFallbackPhoneUsageEval(): String {
    val idx = Math.floor(Math.random() * FALLBACK_PHONE_USAGE_EVAL_TEXTS.length)
    return FALLBACK_PHONE_USAGE_EVAL_TEXTS[idx] as String
}
open class PhoneUsageSummary (
    @JsonNotNull
    open var totalMinutes: Number,
    @JsonNotNull
    open var totalSeconds: Number,
    @JsonNotNull
    open var hourText: String,
    @JsonNotNull
    open var minuteText: String,
    @JsonNotNull
    open var appCount: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PhoneUsageSummary", "services/PhoneUsageService.uts", 7, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PhoneUsageSummaryReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PhoneUsageSummaryReactiveObject : PhoneUsageSummary, IUTSReactive<PhoneUsageSummary> {
    override var __v_raw: PhoneUsageSummary
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PhoneUsageSummary, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(totalMinutes = __v_raw.totalMinutes, totalSeconds = __v_raw.totalSeconds, hourText = __v_raw.hourText, minuteText = __v_raw.minuteText, appCount = __v_raw.appCount) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PhoneUsageSummaryReactiveObject {
        return PhoneUsageSummaryReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var totalMinutes: Number
        get() {
            return _tRG(__v_raw, "totalMinutes", __v_raw.totalMinutes, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("totalMinutes")) {
                return
            }
            val oldValue = __v_raw.totalMinutes
            __v_raw.totalMinutes = value
            _tRS(__v_raw, "totalMinutes", oldValue, value)
        }
    override var totalSeconds: Number
        get() {
            return _tRG(__v_raw, "totalSeconds", __v_raw.totalSeconds, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("totalSeconds")) {
                return
            }
            val oldValue = __v_raw.totalSeconds
            __v_raw.totalSeconds = value
            _tRS(__v_raw, "totalSeconds", oldValue, value)
        }
    override var hourText: String
        get() {
            return _tRG(__v_raw, "hourText", __v_raw.hourText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("hourText")) {
                return
            }
            val oldValue = __v_raw.hourText
            __v_raw.hourText = value
            _tRS(__v_raw, "hourText", oldValue, value)
        }
    override var minuteText: String
        get() {
            return _tRG(__v_raw, "minuteText", __v_raw.minuteText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("minuteText")) {
                return
            }
            val oldValue = __v_raw.minuteText
            __v_raw.minuteText = value
            _tRS(__v_raw, "minuteText", oldValue, value)
        }
    override var appCount: Number
        get() {
            return _tRG(__v_raw, "appCount", __v_raw.appCount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("appCount")) {
                return
            }
            val oldValue = __v_raw.appCount
            __v_raw.appCount = value
            _tRS(__v_raw, "appCount", oldValue, value)
        }
}
open class PieSegment (
    @JsonNotNull
    open var pkg: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var minutes: Number,
    @JsonNotNull
    open var percent: Number,
    @JsonNotNull
    open var color: String,
    @JsonNotNull
    open var startAngle: Number,
    @JsonNotNull
    open var endAngle: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PieSegment", "services/PhoneUsageService.uts", 14, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PieSegmentReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PieSegmentReactiveObject : PieSegment, IUTSReactive<PieSegment> {
    override var __v_raw: PieSegment
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PieSegment, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(pkg = __v_raw.pkg, label = __v_raw.label, minutes = __v_raw.minutes, percent = __v_raw.percent, color = __v_raw.color, startAngle = __v_raw.startAngle, endAngle = __v_raw.endAngle) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PieSegmentReactiveObject {
        return PieSegmentReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var pkg: String
        get() {
            return _tRG(__v_raw, "pkg", __v_raw.pkg, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("pkg")) {
                return
            }
            val oldValue = __v_raw.pkg
            __v_raw.pkg = value
            _tRS(__v_raw, "pkg", oldValue, value)
        }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
    override var minutes: Number
        get() {
            return _tRG(__v_raw, "minutes", __v_raw.minutes, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("minutes")) {
                return
            }
            val oldValue = __v_raw.minutes
            __v_raw.minutes = value
            _tRS(__v_raw, "minutes", oldValue, value)
        }
    override var percent: Number
        get() {
            return _tRG(__v_raw, "percent", __v_raw.percent, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("percent")) {
                return
            }
            val oldValue = __v_raw.percent
            __v_raw.percent = value
            _tRS(__v_raw, "percent", oldValue, value)
        }
    override var color: String
        get() {
            return _tRG(__v_raw, "color", __v_raw.color, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("color")) {
                return
            }
            val oldValue = __v_raw.color
            __v_raw.color = value
            _tRS(__v_raw, "color", oldValue, value)
        }
    override var startAngle: Number
        get() {
            return _tRG(__v_raw, "startAngle", __v_raw.startAngle, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("startAngle")) {
                return
            }
            val oldValue = __v_raw.startAngle
            __v_raw.startAngle = value
            _tRS(__v_raw, "startAngle", oldValue, value)
        }
    override var endAngle: Number
        get() {
            return _tRG(__v_raw, "endAngle", __v_raw.endAngle, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("endAngle")) {
                return
            }
            val oldValue = __v_raw.endAngle
            __v_raw.endAngle = value
            _tRS(__v_raw, "endAngle", oldValue, value)
        }
}
val PALETTE = _uA(
    "#3498DB",
    "#2ECC71",
    "#9B59B6",
    "#E67E22",
    "#E74C3C",
    "#1ABC9C",
    "#F1C40F",
    "#34495E",
    "#16A085",
    "#D35400"
) as UTSArray<String>
fun getTodayPhoneUsageSummary(): PhoneUsageSummary {
    val totalSeconds = getTodayTotalUsageSeconds()
    val minutes = Math.floor(totalSeconds / 60)
    val hours = Math.floor(minutes / 60)
    val remainMin = minutes % 60
    val breakdown = getTodayAppBreakdown()
    return PhoneUsageSummary(totalMinutes = minutes, totalSeconds = totalSeconds, hourText = hours + " 小时", minuteText = remainMin + " 分钟", appCount = breakdown.length)
}
fun getTodayPieSegments(): UTSArray<PieSegment> {
    val all = getTodayAppBreakdown()
    var sumSec: Number = 0
    run {
        var i: Number = 0
        while(i < all.length){
            sumSec += all[i].foregroundSec
            i++
        }
    }
    if (sumSec < 1) {
        return _uA()
    }
    val segments: UTSArray<PieSegment> = _uA()
    var start: Number = 0
    val cap = if (all.length > 10) {
        10
    } else {
        all.length
    }
    run {
        var i: Number = 0
        while(i < cap){
            val item = all[i]
            val pct = (item.foregroundSec / sumSec) * 100
            val end = start + pct
            val color: String = if (i < PALETTE.length) {
                (PALETTE[i] as String)
            } else {
                "#BDC3C7"
            }
            val seg = PieSegment(pkg = item.packageName, label = if (item.appLabel.length > 0) {
                item.appLabel
            } else {
                item.packageName
            }
            , minutes = item.minutes, percent = Math.round(pct * 10) / 10, color = color, startAngle = start, endAngle = end)
            segments.push(seg)
            start = end
            i++
        }
    }
    if (all.length > cap) {
        var othersMin: Number = 0
        run {
            var i = cap
            while(i < all.length){
                othersMin += all[i].minutes
                i++
            }
        }
        val pct = 100 - start
        val seg = PieSegment(pkg = "__others__", label = "其他", minutes = othersMin, percent = Math.round(pct * 10) / 10, color = "#BDC3C7", startAngle = start, endAngle = 100)
        segments.push(seg)
    }
    return segments
}
fun aiEvaluateTodayUsage(onComplete: (text: String) -> Unit): Unit {
    var summary: PhoneUsageSummary
    try {
        summary = getTodayPhoneUsageSummary()
    }
     catch (_: Throwable) {
        summary = PhoneUsageSummary(totalMinutes = 0, totalSeconds = 0, hourText = "0 小时", minuteText = "0 分钟", appCount = 0)
    }
    if (summary.totalSeconds < 1) {
        try {
            val sys = getPhoneUsageFromSystem()
            if (sys != null && sys.summary.totalSeconds > 0) {
                summary = sys.summary
            }
        }
         catch (_: Throwable) {}
    }
    var topApps: UTSArray<AppUsageItem> = _uA()
    try {
        topApps = getTop5Apps()
    }
     catch (_: Throwable) {
        topApps = _uA()
    }
    val topDtos: UTSArray<TopAppEntry> = _uA()
    run {
        var i: Number = 0
        while(i < topApps.length){
            val a = topApps[i]
            if (a == null) {
                i++
                continue
            }
            topDtos.push(TopAppEntry(packageName = a.packageName, appLabel = if (a.appLabel.length > 0) {
                a.appLabel
            } else {
                a.packageName
            }
            , totalSeconds = a.foregroundSec))
            i++
        }
    }
    var guardCount: Number = 0
    try {
        guardCount = countCompletedToday()
    }
     catch (_: Throwable) {
        guardCount = 0
    }
    val hourNow = currentHour()
    val isNight = hourNow >= 22 || hourNow < 7
    val isWorkHour = hourNow >= 9 && hourNow < 18
    val input = PhoneUsageEvalInput(totalMinutes = summary.totalMinutes, totalSeconds = summary.totalSeconds, hourText = summary.hourText, minuteText = summary.minuteText, appCount = summary.appCount, topApps = topDtos, isNight = isNight, isWorkHour = isWorkHour, guardCount = guardCount)
    val userPrompt = buildPhoneUsageEvalPrompt(input)
    console.log("[PhoneUsageService] aiEvaluateTodayUsage prompt len=" + userPrompt.length, " at services/PhoneUsageService.uts:162")
    callMinimaxChat(SYSTEM_PROMPT_PHONE_USAGE, userPrompt, 600, fun(raw: String): Unit {
        val cleaned = sanitizeAiEvalText(raw)
        if (cleaned.length < 1) {
            onComplete(getFallbackPhoneUsageEval())
            return
        }
        onComplete(cleaned)
    }
    , fun(): Unit {
        console.warn("[PhoneUsageService] aiEvaluateTodayUsage LLM 失败", " at services/PhoneUsageService.uts:173")
        onComplete(getFallbackPhoneUsageEval())
    }
    )
}
fun sanitizeAiEvalText(raw: String): String {
    if (raw == null || raw.length < 1) {
        return ""
    }
    var s = raw.trim()
    if (s.startsWith("```")) {
        val firstNewline = s.indexOf("\n")
        if (firstNewline > 0) {
            s = s.substring(firstNewline + 1)
        }
        if (s.endsWith("```")) {
            s = s.substring(0, s.length - 3)
        }
    }
    s = s.trim()
    if (s.length < 1) {
        return ""
    }
    s = stripEmojiExceptBrand(s)
    if (s.length > 600) {
        s = s.substring(0, 600)
    }
    return s
}
fun stripEmojiExceptBrand(s: String): String {
    var out = ""
    run {
        var i: Number = 0
        while(i < s.length){
            val ch = s.charAt(i)
            if (ch == "🌙") {
                out = out + ch
                i++
                continue
            }
            val code = s.charCodeAt(i)
            if (code != null && code >= 0xD800 && code <= 0xDBFF) {
                val next = s.charAt(i + 1)
                val nextCode = next.charCodeAt(0)
                if (nextCode != null && nextCode >= 0xDC00 && nextCode <= 0xDFFF) {
                    i++
                    i++
                    continue
                }
            }
            out = out + ch
            i++
        }
    }
    return out
}
open class PhoneUsageFromSystem (
    @JsonNotNull
    open var summary: PhoneUsageSummary,
    @JsonNotNull
    open var segments: UTSArray<PieSegment>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PhoneUsageFromSystem", "services/PhoneUsageService.uts", 218, 13)
    }
}
fun startOfTodayMs(): Number {
    val d = Date()
    d.setHours(0, 0, 0, 0)
    return d.getTime()
}
open class SystemUsageEntry (
    @JsonNotNull
    open var packageName: String,
    @JsonNotNull
    open var totalTimeInForeground: Number,
    @JsonNotNull
    open var lastTimeUsed: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SystemUsageEntry", "services/PhoneUsageService.uts", 227, 6)
    }
}
fun getPhoneUsageFromSystem(): PhoneUsageFromSystem {
    val emptySummary = PhoneUsageSummary(totalMinutes = 0, totalSeconds = 0, hourText = "0 小时", minuteText = "0 分钟", appCount = 0)
    val startMs = startOfTodayMs()
    val endMs = Date.now()
    var entries: UTSArray<SystemUsageEntry> = _uA()
    try {
        val raw = queryUsageStatsOnce(startMs, endMs)
        if (raw != null) {
            run {
                var i: Number = 0
                while(i < raw.length){
                    val e = raw[i]
                    if (e == null) {
                        i++
                        continue
                    }
                    val item = SystemUsageEntry(packageName = e.packageName, totalTimeInForeground = e.totalTimeInForeground as Number, lastTimeUsed = e.lastTimeUsed as Number)
                    entries.push(item)
                    i++
                }
            }
        }
    }
     catch (e: Throwable) {
        console.error("[PhoneUsageService] queryUsageStatsOnce 失败: " + JSON.stringify(e), " at services/PhoneUsageService.uts:269")
        val emptyResult = PhoneUsageFromSystem(summary = emptySummary, segments = _uA())
        return emptyResult
    }
    if (entries.length < 1) {
        val emptyResult = PhoneUsageFromSystem(summary = emptySummary, segments = _uA())
        return emptyResult
    }
    val items: UTSArray<AppUsageItem> = _uA()
    run {
        var i: Number = 0
        while(i < entries.length){
            val e = entries[i]
            if (e == null) {
                i++
                continue
            }
            val sec = Math.floor((e.totalTimeInForeground as Number) / 1000)
            val label = if (e.packageName != null) {
                e.packageName
            } else {
                ""
            }
            items.push(AppUsageItem(packageName = e.packageName, appLabel = label, foregroundSec = sec, minutes = Math.floor(sec / 60)))
            i++
        }
    }
    items.sort(fun(a: AppUsageItem, b: AppUsageItem): Number {
        return b.foregroundSec - a.foregroundSec
    }
    )
    var totalSeconds: Number = 0
    run {
        var i: Number = 0
        while(i < items.length){
            totalSeconds += items[i].foregroundSec
            i++
        }
    }
    val totalMinutes = Math.floor(totalSeconds / 60)
    val hours = Math.floor(totalMinutes / 60)
    val remainMin = totalMinutes % 60
    val summary = PhoneUsageSummary(totalMinutes = totalMinutes, totalSeconds = totalSeconds, hourText = hours + " 小时", minuteText = remainMin + " 分钟", appCount = items.length)
    if (totalSeconds < 1) {
        val noSegResult = PhoneUsageFromSystem(summary = summary, segments = _uA())
        return noSegResult
    }
    val segments: UTSArray<PieSegment> = _uA()
    var start: Number = 0
    val cap = if (items.length > 10) {
        10
    } else {
        items.length
    }
    run {
        var i: Number = 0
        while(i < cap){
            val item = items[i]
            val pct = (item.foregroundSec / totalSeconds) * 100
            val end = start + pct
            val color: String = if (i < PALETTE.length) {
                (PALETTE[i] as String)
            } else {
                "#BDC3C7"
            }
            val seg = PieSegment(pkg = item.packageName, label = if (item.appLabel.length > 0) {
                item.appLabel
            } else {
                item.packageName
            }
            , minutes = item.minutes, percent = Math.round(pct * 10) / 10, color = color, startAngle = start, endAngle = end)
            segments.push(seg)
            start = end
            i++
        }
    }
    if (items.length > cap) {
        var othersMin: Number = 0
        run {
            var i = cap
            while(i < items.length){
                othersMin += items[i].minutes
                i++
            }
        }
        val pct = 100 - start
        val seg = PieSegment(pkg = "__others__", label = "其他", minutes = othersMin, percent = Math.round(pct * 10) / 10, color = "#BDC3C7", startAngle = start, endAngle = 100)
        segments.push(seg)
    }
    val finalResult = PhoneUsageFromSystem(summary = summary, segments = segments)
    return finalResult
}
val GenComponentsPhoneUsageDialogClass = CreateVueComponent(GenComponentsPhoneUsageDialog::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsPhoneUsageDialog.inheritAttrs, inject = GenComponentsPhoneUsageDialog.inject, props = GenComponentsPhoneUsageDialog.props, propsNeedCastKeys = GenComponentsPhoneUsageDialog.propsNeedCastKeys, emits = GenComponentsPhoneUsageDialog.emits, components = GenComponentsPhoneUsageDialog.components, styles = GenComponentsPhoneUsageDialog.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsPhoneUsageDialog.setup(props as GenComponentsPhoneUsageDialog)
    }
    )
}
, fun(instance, renderer): GenComponentsPhoneUsageDialog {
    return GenComponentsPhoneUsageDialog(instance)
}
)
val GenComponentsRightSidebarClass = CreateVueComponent(GenComponentsRightSidebar::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsRightSidebar.inheritAttrs, inject = GenComponentsRightSidebar.inject, props = GenComponentsRightSidebar.props, propsNeedCastKeys = GenComponentsRightSidebar.propsNeedCastKeys, emits = GenComponentsRightSidebar.emits, components = GenComponentsRightSidebar.components, styles = GenComponentsRightSidebar.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsRightSidebar.setup(props as GenComponentsRightSidebar)
    }
    )
}
, fun(instance, renderer): GenComponentsRightSidebar {
    return GenComponentsRightSidebar(instance)
}
)
val GenComponentsAiPhoneEvalPanelClass = CreateVueComponent(GenComponentsAiPhoneEvalPanel::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsAiPhoneEvalPanel.inheritAttrs, inject = GenComponentsAiPhoneEvalPanel.inject, props = GenComponentsAiPhoneEvalPanel.props, propsNeedCastKeys = GenComponentsAiPhoneEvalPanel.propsNeedCastKeys, emits = GenComponentsAiPhoneEvalPanel.emits, components = GenComponentsAiPhoneEvalPanel.components, styles = GenComponentsAiPhoneEvalPanel.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsAiPhoneEvalPanel.setup(props as GenComponentsAiPhoneEvalPanel)
    }
    )
}
, fun(instance, renderer): GenComponentsAiPhoneEvalPanel {
    return GenComponentsAiPhoneEvalPanel(instance)
}
)
val GenComponentsTriggerRuleDialogClass = CreateVueComponent(GenComponentsTriggerRuleDialog::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsTriggerRuleDialog.inheritAttrs, inject = GenComponentsTriggerRuleDialog.inject, props = GenComponentsTriggerRuleDialog.props, propsNeedCastKeys = GenComponentsTriggerRuleDialog.propsNeedCastKeys, emits = GenComponentsTriggerRuleDialog.emits, components = GenComponentsTriggerRuleDialog.components, styles = GenComponentsTriggerRuleDialog.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsTriggerRuleDialog.setup(props as GenComponentsTriggerRuleDialog)
    }
    )
}
, fun(instance, renderer): GenComponentsTriggerRuleDialog {
    return GenComponentsTriggerRuleDialog(instance)
}
)
open class CellInfo (
    open var day: Number? = null,
    @JsonNotNull
    open var count: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CellInfo", "components/HeatmapCalendar.uvue", 42, 6)
    }
}
val GenComponentsHeatmapCalendarClass = CreateVueComponent(GenComponentsHeatmapCalendar::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsHeatmapCalendar.inheritAttrs, inject = GenComponentsHeatmapCalendar.inject, props = GenComponentsHeatmapCalendar.props, propsNeedCastKeys = GenComponentsHeatmapCalendar.propsNeedCastKeys, emits = GenComponentsHeatmapCalendar.emits, components = GenComponentsHeatmapCalendar.components, styles = GenComponentsHeatmapCalendar.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsHeatmapCalendar.setup(props as GenComponentsHeatmapCalendar)
    }
    )
}
, fun(instance, renderer): GenComponentsHeatmapCalendar {
    return GenComponentsHeatmapCalendar(instance)
}
)
open class ScoredItem (
    @JsonNotNull
    open var action: MicroAction,
    @JsonNotNull
    open var score: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ScoredItem", "services/Recommendation.uts", 5, 6)
    }
}
fun getRecommendedActions(): UTSArray<MicroAction> {
    val enabledActions = getEnabledActions()
    if (enabledActions.length.toInt() < 1) {
        return _uA()
    }
    val hour = currentHour()
    val todayLogs = getTodayLogs()
    val categoryCount: Map<String, Number> = Map<String, Number>()
    for(action in resolveUTSValueIterator(enabledActions)){
        categoryCount.set(action.category, 0)
    }
    for(log in resolveUTSValueIterator(todayLogs)){
        if (log.result === "completed" || log.result === "self_reported") {
            val current = categoryCount.get(log.action_type)
            if (current != null) {
                categoryCount.set(log.action_type, current + 1)
            }
        }
    }
    var minCategory = ""
    var minCount: Number = 999999999
    val allCats = _uA(
        "护眼",
        "肩颈",
        "核心",
        "下肢",
        "呼吸"
    ) as UTSArray<String>
    run {
        var i: Number = 0
        while(i < allCats.length){
            val cnt = categoryCount.get(allCats[i])
            if (cnt != null && cnt < minCount) {
                minCount = cnt
                minCategory = allCats[i]
            }
            i++
        }
    }
    val preferredTypes = getPreferredTypes(hour)
    val scored: UTSArray<ScoredItem> = enabledActions.map(fun(a: MicroAction): ScoredItem {
        val rawCount = categoryCount.get(a.category)
        val catCount = if (rawCount != null) {
            rawCount
        } else {
            0
        }
        val isPreferred = if (preferredTypes.indexOf(a.category) >= 0) {
            0
        } else {
            1
        }
        val isMinCategory = if (a.category === minCategory) {
            -1
        } else {
            0
        }
        val item = ScoredItem(action = a, score = catCount + isPreferred + isMinCategory)
        return item
    }
    )
    scored.sort(fun(a: ScoredItem, b: ScoredItem): Number {
        return a.score - b.score
    }
    )
    return scored.slice(0, 3).map(fun(s: ScoredItem): MicroAction {
        return s.action
    }
    )
}
fun getPreferredTypes(hour: Number): UTSArray<String> {
    if (hour >= 22 || hour < 7) {
        return _uA(
            "护眼",
            "呼吸",
            "肩颈"
        )
    }
    if (hour >= 7 && hour <= 12) {
        return _uA(
            "肩颈",
            "核心",
            "护眼"
        )
    }
    if (hour >= 13 && hour <= 18) {
        return _uA(
            "核心",
            "下肢",
            "护眼"
        )
    }
    return _uA(
        "护眼",
        "肩颈",
        "呼吸"
    )
}
fun isNetworkAvailable(): Boolean {
    return true
}
fun generateWeeklyData(): WeeklyReportStats {
    var totalCompleted: Number = 0
    var totalDurationSec: Number = 0
    var totalPenetration: Number = 0
    var totalGuardMinutes: Number = 0
    var bestDay = ""
    var bestDayCount: Number = -1
    var worstDay = ""
    var worstDayCount: Number = 999999
    var daysWithData: Number = 0
    run {
        var i: Number = 6
        while(i >= 0){
            val d = daysAgo(i)
            val data = getHomepageData(d)
            totalCompleted += data.todayCompletedCount
            totalDurationSec += data.guardCount * 60
            totalPenetration += data.penetration
            totalGuardMinutes += data.guardCount
            if (data.todayCompletedCount > 0) {
                daysWithData++
            }
            if (data.todayCompletedCount > bestDayCount) {
                bestDayCount = data.todayCompletedCount
                bestDay = d
            }
            if (data.todayCompletedCount < worstDayCount && data.todayCompletedCount >= 0) {
                worstDayCount = data.todayCompletedCount
                worstDay = d
            }
            i--
        }
    }
    if (daysWithData < 1) {
        return WeeklyReportStats(totalCompleted = 0, totalDurationSec = 0, avgPenetration = 0, avgGuardMinutes = 0, bestDay = "", bestDayCount = 0, worstDay = "", worstDayCount = 0)
    }
    return WeeklyReportStats(totalCompleted = totalCompleted, totalDurationSec = totalDurationSec, avgPenetration = Math.round(totalPenetration / 7 * 100) / 100, avgGuardMinutes = Math.round(totalGuardMinutes / 7), bestDay = bestDay, bestDayCount = bestDayCount, worstDay = worstDay, worstDayCount = worstDayCount)
}
fun cleanMarkdown(raw: String): String {
    if (raw == null) {
        return ""
    }
    var text = raw
    text = text.replace(UTSRegExp("^```[\\s\\S]*?\\n", ""), "")
    text = text.replace(UTSRegExp("```\$", ""), "")
    text = text.replace(UTSRegExp("^#+\\s*", "gm"), "")
    text = text.replace(UTSRegExp("\\*\\*([^*]+)\\*\\*", "g"), "\$1")
    text = text.replace(UTSRegExp("\\*([^*]+)\\*", "g"), "\$1")
    text = text.replace(UTSRegExp("`([^`]+)`", "g"), "\$1")
    text = text.replace(UTSRegExp("[ \\t]+\\n", "g"), "\n")
    text = text.replace(UTSRegExp("\\n{3,}", "g"), "\n\n")
    return text.trim()
}
fun buildLocalReport(stats: WeeklyReportStats): WeeklyReport {
    val hasData = stats.totalCompleted > 0
    if (hasData) {
        val body = "本周完成了 " + stats.totalCompleted + " 次微动作，累计守护 " + Math.round(stats.totalDurationSec / 60) + " 分钟。\n\n" + "你的平均每日守护时长为 " + stats.avgGuardMinutes + " 分钟，整体节奏稳定。" + "表现值得肯定。\n\n" + "下周可以尝试每天固定时间做一个微动作，把守护变成习惯。\n\n" + "目标：每天至少完成 3 次微动作。"
        return WeeklyReport(bodyMarkdown = body, stats = stats)
    }
    val body = "本周暂无数据，从今天开始吧。\n\n完成你的第一个微动作，开启健康守护之旅。\n\n开启无障碍服务以自动提醒，设定你的健康偏好。\n\n目标：开始你的第一个微动作。"
    return WeeklyReport(bodyMarkdown = body, stats = stats)
}
fun generateWeeklyReport(onComplete: (report: WeeklyReport) -> Unit): Unit {
    val stats = generateWeeklyData()
    if (!isNetworkAvailable()) {
        onComplete(buildLocalReport(stats))
        return
    }
    callWeekly(stats, fun(raw: String): Unit {
        try {
            val cleaned = cleanMarkdown(raw)
            if (cleaned.length > 10) {
                onComplete(WeeklyReport(bodyMarkdown = cleaned, stats = stats))
                return
            }
            console.warn("[WeeklyReportService] LLM 返回内容过短, fallback", " at services/WeeklyReportService.uts:113")
        }
         catch (e: Throwable) {
            console.warn("[WeeklyReportService] 处理失败, fallback: " + JSON.stringify(e), " at services/WeeklyReportService.uts:115")
        }
        onComplete(buildLocalReport(stats))
    }
    , fun(){
        onComplete(buildLocalReport(stats))
    }
    )
}
open class DailyOutput (
    @JsonNotNull
    open var bodyText: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DailyOutput", "stores/appStore.uts", 20, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return DailyOutputReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class DailyOutputReactiveObject : DailyOutput, IUTSReactive<DailyOutput> {
    override var __v_raw: DailyOutput
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: DailyOutput, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(bodyText = __v_raw.bodyText) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): DailyOutputReactiveObject {
        return DailyOutputReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var bodyText: String
        get() {
            return _tRG(__v_raw, "bodyText", __v_raw.bodyText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("bodyText")) {
                return
            }
            val oldValue = __v_raw.bodyText
            __v_raw.bodyText = value
            _tRS(__v_raw, "bodyText", oldValue, value)
        }
}
open class AppStore (
    @JsonNotNull
    open var eyeScore: Ref<Number>,
    @JsonNotNull
    open var eyeStatus: Ref<String>,
    @JsonNotNull
    open var postureScore: Ref<Number>,
    @JsonNotNull
    open var postureStatus: Ref<String>,
    @JsonNotNull
    open var vitalityScore: Ref<Number>,
    @JsonNotNull
    open var vitalityStatus: Ref<String>,
    @JsonNotNull
    open var guardCount: Ref<Number>,
    @JsonNotNull
    open var penetration: Ref<Number>,
    @JsonNotNull
    open var phoneMinutes: Ref<Number>,
    @JsonNotNull
    open var todayCompletedCount: Ref<Number>,
    @JsonNotNull
    open var recommendedActions: Ref<UTSArray<MicroAction>>,
    @JsonNotNull
    open var heatmapData: Ref<UTSArray<DailyCount>>,
    @JsonNotNull
    open var dailySummary: Ref<DailyOutput?>,
    @JsonNotNull
    open var dailySummaryLoading: Ref<Boolean>,
    @JsonNotNull
    open var homeDataLoaded: Ref<Boolean>,
    @JsonNotNull
    open var weeklyReport: Ref<WeeklyReport?>,
    @JsonNotNull
    open var weeklyReportLoading: Ref<Boolean>,
    @JsonNotNull
    open var llmHistory: Ref<UTSArray<LlmHistoryEntry>>,
    @JsonNotNull
    open var achievements: Ref<UTSArray<AchievementItem>>,
    @JsonNotNull
    open var pendingUnlockIds: Ref<UTSArray<String>>,
    @JsonNotNull
    open var nextCheckCountdownMs: Ref<Number>,
    @JsonNotNull
    open var nextCheckCountdownText: Ref<String>,
    @JsonNotNull
    open var nextTriggerCountdownMs: Ref<Number>,
    @JsonNotNull
    open var nextTriggerCountdownText: Ref<String>,
    open var refreshHomeData: () -> Unit,
    open var refreshWeeklyReport: () -> Unit,
    open var refreshLlmHistory: () -> Unit,
    open var refreshAchievements: () -> Unit,
    open var setPendingUnlocks: (ids: UTSArray<String>) -> Unit,
    open var shiftPendingUnlock: () -> Unit,
    open var refreshTriggerCountdowns: () -> Unit,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AppStore", "stores/appStore.uts", 23, 13)
    }
}
val eyeScore = ref<Number>(0)
val eyeStatus = ref<String>("no_data")
val postureScore = ref<Number>(0)
val postureStatus = ref<String>("no_data")
val vitalityScore = ref<Number>(0)
val vitalityStatus = ref<String>("no_data")
val guardCount = ref<Number>(0)
val penetration = ref<Number>(0)
val phoneMinutes = ref<Number>(0)
@JvmField
val todayCompletedCount = ref<Number>(0)
@JvmField
val recommendedActions = ref(_uA<MicroAction>())
val heatmapData = ref(_uA<DailyCount>())
val dailySummary = ref<DailyOutput?>(null)
val dailySummaryLoading = ref<Boolean>(false)
val homeDataLoaded = ref<Boolean>(false)
val weeklyReport = ref<WeeklyReport?>(null)
val weeklyReportLoading = ref<Boolean>(false)
val llmHistory = ref(_uA<LlmHistoryEntry>())
val achievements = ref(_uA<AchievementItem>())
val pendingUnlockIds = ref(_uA<String>())
val nextCheckCountdownMs = ref<Number>(0)
val nextCheckCountdownText = ref<String>("--:--")
val nextTriggerCountdownMs = ref<Number>(0)
val nextTriggerCountdownText = ref<String>("可触发")
fun emptySeenSet(): Set<String> {
    return Set<String>()
}
fun refreshAchievementsFn(): Unit {
    try {
        achievements.value = getAchievementItems(emptySeenSet())
    }
     catch (e: Throwable) {
        console.warn("[appStore] refreshAchievements 失败 type=" + UTSAndroid.`typeof`(e) + " msg=" + (if (e != null) {
            ("" + e)
        } else {
            "null"
        }
        ), " at stores/appStore.uts:92")
        achievements.value = _uA()
    }
}
fun setPendingUnlocksFn(ids: UTSArray<String>): Unit {
    pendingUnlockIds.value = ids
}
fun shiftPendingUnlockFn(): Unit {
    if (pendingUnlockIds.value.length < 1) {
        return
    }
    val newArr: UTSArray<String> = _uA()
    run {
        var i: Number = 1
        while(i < pendingUnlockIds.value.length){
            newArr.push(pendingUnlockIds.value[i])
            i++
        }
    }
    pendingUnlockIds.value = newArr
}
fun formatCountdown(ms: Number): String {
    if (ms < 1) {
        return "00:00"
    }
    val totalSec = Math.floor(ms / 1000)
    val mm = Math.floor(totalSec / 60)
    val ss = totalSec - mm * 60
    val pad = fun(n: Number): String {
        return if (n < 10) {
            "0" + n
        } else {
            "" + n
        }
    }
    return pad(mm) + ":" + pad(ss)
}
fun refreshTriggerCountdownsFn(): Unit {
    var checkRemain: Number = 0
    try {
        val lastTick = getLastTickTimeMs()
        val pollMs = getCurrentPollIntervalMs()
        if (lastTick > 0 && pollMs > 0) {
            val elapsed = Date.now() - lastTick
            checkRemain = pollMs - elapsed
            if (checkRemain < 0) {
                checkRemain = 0
            }
        }
    }
     catch (_: Throwable) {
        checkRemain = 0
    }
    nextCheckCountdownMs.value = checkRemain
    nextCheckCountdownText.value = formatCountdown(checkRemain)
    var triggerRemain: Number = 0
    try {
        triggerRemain = getRemainingCooldownMs()
    }
     catch (_: Throwable) {
        triggerRemain = 0
    }
    nextTriggerCountdownMs.value = triggerRemain
    nextTriggerCountdownText.value = if (triggerRemain > 0) {
        formatCountdown(triggerRemain)
    } else {
        "可触发"
    }
}
fun refreshLlmHistory(): Unit {
    try {
        llmHistory.value = getAllHistory(200)
    }
     catch (e: Throwable) {
        console.warn("[appStore] refreshLlmHistory 失败: " + JSON.stringify(e), " at stores/appStore.uts:151")
        llmHistory.value = _uA()
    }
}
fun useAppStore(): AppStore {
    val store = AppStore(eyeScore = eyeScore, eyeStatus = eyeStatus, postureScore = postureScore, postureStatus = postureStatus, vitalityScore = vitalityScore, vitalityStatus = vitalityStatus, guardCount = guardCount, penetration = penetration, phoneMinutes = phoneMinutes, todayCompletedCount = todayCompletedCount, recommendedActions = recommendedActions, heatmapData = heatmapData, dailySummary = dailySummary, dailySummaryLoading = dailySummaryLoading, homeDataLoaded = homeDataLoaded, weeklyReport = weeklyReport, weeklyReportLoading = weeklyReportLoading, llmHistory = llmHistory, achievements = achievements, pendingUnlockIds = pendingUnlockIds, nextCheckCountdownMs = nextCheckCountdownMs, nextCheckCountdownText = nextCheckCountdownText, nextTriggerCountdownMs = nextTriggerCountdownMs, nextTriggerCountdownText = nextTriggerCountdownText, refreshLlmHistory = fun(): Unit {
        refreshLlmHistory()
    }
    , refreshAchievements = fun(): Unit {
        refreshAchievementsFn()
    }
    , setPendingUnlocks = fun(ids: UTSArray<String>): Unit {
        setPendingUnlocksFn(ids)
    }
    , shiftPendingUnlock = fun(): Unit {
        shiftPendingUnlockFn()
    }
    , refreshTriggerCountdowns = fun(): Unit {
        refreshTriggerCountdownsFn()
    }
    , refreshHomeData = fun(): Unit {
        homeDataLoaded.value = false
        try {
            val date = today()
            val nowMs = Date.now()
            try {
                saveTodaySummary()
            }
             catch (_: Throwable) {}
            val data = getHomepageData(date)
            try {
                phoneMinutes.value = getTodayTotalUsageMinutes()
            }
             catch (_: Throwable) {}
            eyeScore.value = Math.round(data.eyeScore)
            eyeStatus.value = data.eyeLevel
            postureScore.value = Math.round(data.postureScore)
            postureStatus.value = data.postureLevel
            vitalityScore.value = Math.round(data.vitalityScore)
            vitalityStatus.value = data.vitalityLevel
            guardCount.value = data.guardCount
            penetration.value = Math.round(data.penetration * 100) / 100
            todayCompletedCount.value = data.todayCompletedCount
            heatmapData.value = data.heatmapData
            val todayKey = "daily_summary:" + date
            val todayCache = getByKey(todayKey)
            if (todayCache != null) {
                try {
                    val parsed = UTSAndroid.consoleDebugError(JSON.parse(todayCache.response), " at stores/appStore.uts:220")
                    dailySummary.value = parsed as DailyOutput
                } catch (_: Throwable) {}
                dailySummaryLoading.value = false
            } else {
                dailySummaryLoading.value = true
                generateDailySummary(date, fun(summary: DailyOutput){
                    save(todayKey, "daily_summary", JSON.stringify(summary), nowMs + 604800000)
                    dailySummary.value = summary
                    dailySummaryLoading.value = false
                }
                )
            }
            val yDate = yesterday()
            val yesterdayKey = "daily_summary:" + yDate
            if (getByKey(yesterdayKey) == null) {
                val ydata = getHomepageData(yDate)
                val fallback = DailyOutput(bodyText = "昨日完成" + ydata.todayCompletedCount + "次微动作，累计" + ydata.guardCount + "次。\n\n今天继续加油！")
                save(yesterdayKey, "daily_summary", JSON.stringify(fallback), nowMs + 2592000000)
            }
        }
         catch (_: Throwable) {}
        try {
            recommendedActions.value = getRecommendedActions()
        }
         catch (_: Throwable) {}
        homeDataLoaded.value = true
    }
    , refreshWeeklyReport = fun(): Unit {
        weeklyReportLoading.value = true
        generateWeeklyReport(fun(report: WeeklyReport){
            weeklyReport.value = report
            weeklyReportLoading.value = false
        }
        )
    }
    )
    return store
}
var lastDailySummaryAt: Number = 0
val DAILY_SUMMARY_COOLDOWN_MS: Number = 3600000
fun buildLocalSummary(completedCount: Number, count: Number): DailyOutput {
    if (completedCount > 0) {
        return DailyOutput(bodyText = "今天已经完成" + completedCount + "次微动作，累计守护" + count + "次。\n\n保持节奏，明天继续！")
    }
    return DailyOutput(bodyText = "今天还在热身区蓄力中~\n\n没有关系，明天只需要完成1次小动作就算是胜利啦。从零到一是最酷的跨越！")
}
fun generateDailySummary(date: String, onComplete: (summary: DailyOutput) -> Unit): Unit {
    val nowMs = Date.now()
    if (lastDailySummaryAt > 0 && nowMs - lastDailySummaryAt < DAILY_SUMMARY_COOLDOWN_MS) {
        val data = getHomepageData(date)
        onComplete(buildLocalSummary(data.todayCompletedCount, data.guardCount))
        return
    }
    val data = getHomepageData(date)
    if (!isNetworkAvailable()) {
        onComplete(buildLocalSummary(data.todayCompletedCount, data.guardCount))
        return
    }
    val dto = DailyData(totalCompleted = data.todayCompletedCount, totalSkipped = 0, totalDurationSec = 0, guardMinutes = 0, guardCount = data.guardCount, penetration = data.penetration, eyeScore = Math.max(0, Math.round(data.eyeScore)), postureScore = Math.max(0, Math.round(data.postureScore)), vitalityScore = Math.max(0, Math.round(data.vitalityScore)), todayCompletedCount = data.todayCompletedCount)
    callDaily(date, dto, fun(raw: String): Unit {
        if (raw.length > 10) {
            lastDailySummaryAt = Date.now()
            onComplete(DailyOutput(bodyText = raw))
            return
        }
        onComplete(buildLocalSummary(data.todayCompletedCount, data.guardCount))
    }
    , fun(){
        onComplete(buildLocalSummary(data.todayCompletedCount, data.guardCount))
    }
    )
}
fun getGreetingHint(hour: Number): String {
    if (hour < 6) {
        return "夜深了，小星球建议把屏幕放远一点，给眼睛留条回家的路"
    }
    if (hour < 9) {
        return "早晨先慢慢亮起来，今天也从一个很小的照顾开始"
    }
    if (hour < 12) {
        return "上午专注力在线，也记得偶尔眨眨眼、松松肩"
    }
    if (hour < 14) {
        return "午间适合轻轻重启一下，别让身体一直待机"
    }
    if (hour < 18) {
        return "下午的小疲惫很正常，做个 10 秒动作就能缓一缓"
    }
    if (hour < 22) {
        return "晚上别和屏幕硬撑，给眼睛和肩颈一点余地"
    }
    return "夜深了，小星球会轻一点提醒，也希望你早点休息"
}
fun getExecuteIntro(actionId: String): String {
    if (actionId == "eye_blink") {
        return "给眼睛补一点水分，轻轻眨几下就好"
    }
    if (actionId == "eye_rotate") {
        return "让眼球慢慢活动一圈，别急着用力"
    }
    if (actionId == "neck_rotate") {
        return "把脖子从固定姿势里慢慢请出来"
    }
    if (actionId == "shoulder_roll") {
        return "肩膀向后画个小圈，把紧绷放下"
    }
    if (actionId == "neck_stretch") {
        return "坐直一点，给肩颈一段温柔拉伸"
    }
    if (actionId == "core_tighten") {
        return "唤醒核心肌群，像给身体按下稳定键"
    }
    if (actionId == "heel_raise") {
        return "让小腿和脚踝动起来，顺便叫醒循环"
    }
    if (actionId == "deep_breath") {
        return "慢慢吸气，再慢慢呼出，把节奏降下来"
    }
    if (actionId == "far_gaze") {
        return "看向远处，让眼睛从近距离里松一口气"
    }
    return "跟着小星球做一个短短的微动作"
}
fun getExecuteGuide(actionId: String): String {
    if (actionId == "eye_blink") {
        return "轻闭双眼 2 秒再睁开，重复几次，保持自然呼吸"
    }
    if (actionId == "eye_rotate") {
        return "眼球缓慢顺时针转一圈，再逆时针转一圈"
    }
    if (actionId == "neck_rotate") {
        return "肩膀放松，头慢慢转向一侧，停一下再回正"
    }
    if (actionId == "shoulder_roll") {
        return "双肩向上提起，再向后画圈放下，动作放慢"
    }
    if (actionId == "neck_stretch") {
        return "头轻轻侧向一边，感受肩颈外侧被拉长"
    }
    if (actionId == "core_tighten") {
        return "收紧腹部和骨盆底，保持自然呼吸，不要憋气"
    }
    if (actionId == "heel_raise") {
        return "双脚踩稳，慢慢踮起脚跟，再控制速度落回"
    }
    if (actionId == "deep_breath") {
        return "鼻吸 4 秒，让腹部鼓起；口呼 6 秒，慢慢送出"
    }
    if (actionId == "far_gaze") {
        return "看向 6 米外的固定目标，放松眉心和眼周"
    }
    return "保持放松、缓慢和可控，跟随倒计时完成动作"
}
val GenComponentsAchievementDetailDialogClass = CreateVueComponent(GenComponentsAchievementDetailDialog::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsAchievementDetailDialog.inheritAttrs, inject = GenComponentsAchievementDetailDialog.inject, props = GenComponentsAchievementDetailDialog.props, propsNeedCastKeys = GenComponentsAchievementDetailDialog.propsNeedCastKeys, emits = GenComponentsAchievementDetailDialog.emits, components = GenComponentsAchievementDetailDialog.components, styles = GenComponentsAchievementDetailDialog.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsAchievementDetailDialog.setup(props as GenComponentsAchievementDetailDialog)
    }
    )
}
, fun(instance, renderer): GenComponentsAchievementDetailDialog {
    return GenComponentsAchievementDetailDialog(instance)
}
)
val GenComponentsAchievementUnlockDialogClass = CreateVueComponent(GenComponentsAchievementUnlockDialog::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsAchievementUnlockDialog.inheritAttrs, inject = GenComponentsAchievementUnlockDialog.inject, props = GenComponentsAchievementUnlockDialog.props, propsNeedCastKeys = GenComponentsAchievementUnlockDialog.propsNeedCastKeys, emits = GenComponentsAchievementUnlockDialog.emits, components = GenComponentsAchievementUnlockDialog.components, styles = GenComponentsAchievementUnlockDialog.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsAchievementUnlockDialog.setup(props as GenComponentsAchievementUnlockDialog)
    }
    )
}
, fun(instance, renderer): GenComponentsAchievementUnlockDialog {
    return GenComponentsAchievementUnlockDialog(instance)
}
)
val GenPagesHomeIndexClass = CreateVueComponent(GenPagesHomeIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesHomeIndex.inheritAttrs, inject = GenPagesHomeIndex.inject, props = GenPagesHomeIndex.props, propsNeedCastKeys = GenPagesHomeIndex.propsNeedCastKeys, emits = GenPagesHomeIndex.emits, components = GenPagesHomeIndex.components, styles = GenPagesHomeIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesHomeIndex.setup(props as GenPagesHomeIndex)
    }
    )
}
, fun(instance, renderer): GenPagesHomeIndex {
    return GenPagesHomeIndex(instance, renderer)
}
)
val GenComponentsCountdownBarClass = CreateVueComponent(GenComponentsCountdownBar::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsCountdownBar.inheritAttrs, inject = GenComponentsCountdownBar.inject, props = GenComponentsCountdownBar.props, propsNeedCastKeys = GenComponentsCountdownBar.propsNeedCastKeys, emits = GenComponentsCountdownBar.emits, components = GenComponentsCountdownBar.components, styles = GenComponentsCountdownBar.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsCountdownBar.setup(props as GenComponentsCountdownBar)
    }
    )
}
, fun(instance, renderer): GenComponentsCountdownBar {
    return GenComponentsCountdownBar(instance)
}
)
val GenComponentsEncourageToastClass = CreateVueComponent(GenComponentsEncourageToast::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsEncourageToast.inheritAttrs, inject = GenComponentsEncourageToast.inject, props = GenComponentsEncourageToast.props, propsNeedCastKeys = GenComponentsEncourageToast.propsNeedCastKeys, emits = GenComponentsEncourageToast.emits, components = GenComponentsEncourageToast.components, styles = GenComponentsEncourageToast.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsEncourageToast.setup(props as GenComponentsEncourageToast)
    }
    )
}
, fun(instance, renderer): GenComponentsEncourageToast {
    return GenComponentsEncourageToast(instance)
}
)
val GenComponentsFeedbackDialogClass = CreateVueComponent(GenComponentsFeedbackDialog::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsFeedbackDialog.inheritAttrs, inject = GenComponentsFeedbackDialog.inject, props = GenComponentsFeedbackDialog.props, propsNeedCastKeys = GenComponentsFeedbackDialog.propsNeedCastKeys, emits = GenComponentsFeedbackDialog.emits, components = GenComponentsFeedbackDialog.components, styles = GenComponentsFeedbackDialog.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsFeedbackDialog.setup(props as GenComponentsFeedbackDialog)
    }
    )
}
, fun(instance, renderer): GenComponentsFeedbackDialog {
    return GenComponentsFeedbackDialog(instance)
}
)
val GenPagesActionExecuteClass = CreateVueComponent(GenPagesActionExecute::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesActionExecute.inheritAttrs, inject = GenPagesActionExecute.inject, props = GenPagesActionExecute.props, propsNeedCastKeys = GenPagesActionExecute.propsNeedCastKeys, emits = GenPagesActionExecute.emits, components = GenPagesActionExecute.components, styles = GenPagesActionExecute.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesActionExecute.setup(props as GenPagesActionExecute)
    }
    )
}
, fun(instance, renderer): GenPagesActionExecute {
    return GenPagesActionExecute(instance, renderer)
}
)
val GenComponentsBarChartClass = CreateVueComponent(GenComponentsBarChart::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsBarChart.inheritAttrs, inject = GenComponentsBarChart.inject, props = GenComponentsBarChart.props, propsNeedCastKeys = GenComponentsBarChart.propsNeedCastKeys, emits = GenComponentsBarChart.emits, components = GenComponentsBarChart.components, styles = GenComponentsBarChart.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsBarChart.setup(props as GenComponentsBarChart)
    }
    )
}
, fun(instance, renderer): GenComponentsBarChart {
    return GenComponentsBarChart(instance)
}
)
val GenComponentsLineChartClass = CreateVueComponent(GenComponentsLineChart::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsLineChart.inheritAttrs, inject = GenComponentsLineChart.inject, props = GenComponentsLineChart.props, propsNeedCastKeys = GenComponentsLineChart.propsNeedCastKeys, emits = GenComponentsLineChart.emits, components = GenComponentsLineChart.components, styles = GenComponentsLineChart.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsLineChart.setup(props as GenComponentsLineChart)
    }
    )
}
, fun(instance, renderer): GenComponentsLineChart {
    return GenComponentsLineChart(instance)
}
)
val GenComponentsLlmHistoryCardClass = CreateVueComponent(GenComponentsLlmHistoryCard::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsLlmHistoryCard.inheritAttrs, inject = GenComponentsLlmHistoryCard.inject, props = GenComponentsLlmHistoryCard.props, propsNeedCastKeys = GenComponentsLlmHistoryCard.propsNeedCastKeys, emits = GenComponentsLlmHistoryCard.emits, components = GenComponentsLlmHistoryCard.components, styles = GenComponentsLlmHistoryCard.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsLlmHistoryCard.setup(props as GenComponentsLlmHistoryCard)
    }
    )
}
, fun(instance, renderer): GenComponentsLlmHistoryCard {
    return GenComponentsLlmHistoryCard(instance)
}
)
open class MoodWord (
    @JsonNotNull
    open var word: String,
    @JsonNotNull
    open var weight: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MoodWord", "models/MoodWord.uts", 1, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return MoodWordReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class MoodWordReactiveObject : MoodWord, IUTSReactive<MoodWord> {
    override var __v_raw: MoodWord
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: MoodWord, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(word = __v_raw.word, weight = __v_raw.weight) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): MoodWordReactiveObject {
        return MoodWordReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var word: String
        get() {
            return _tRG(__v_raw, "word", __v_raw.word, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("word")) {
                return
            }
            val oldValue = __v_raw.word
            __v_raw.word = value
            _tRS(__v_raw, "word", oldValue, value)
        }
    override var weight: Number
        get() {
            return _tRG(__v_raw, "weight", __v_raw.weight, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("weight")) {
                return
            }
            val oldValue = __v_raw.weight
            __v_raw.weight = value
            _tRS(__v_raw, "weight", oldValue, value)
        }
}
val SYSTEM_PROMPT_MOOD_CLOUD: String = "你是微憩星球里的\"小星球\"，温柔、不评判。任务：从用户近期的状态描述/触发提醒文本中，提取 10-18 个心情/状态关键词。输出必须是严格的 JSON 数组，不允许其他内容，不允许 markdown 包裹。格式：[{ \"word\": \"疲惫\", \"weight\": 8 }, { \"word\": \"专注\", \"weight\": 5 }]weight 范围 1-10，数字越大表示出现频次/强度越高。去重后输出，相似含义的词合并（如\"累\"和\"疲惫\"合并为\"疲惫\"）。如果文本信息不足，输出空数组 []。禁止使用表情符号。"
fun buildMoodCloudPrompt(rawTexts: UTSArray<String>): String {
    if (rawTexts.length < 1) {
        return "用户暂无历史状态记录。请输出空数组 []。"
    }
    val joined: String = rawTexts.map(fun(s: String, i: Number): String {
        return (i + 1) + ". " + s
    }
    ).join("\n")
    return "请从以下 " + rawTexts.length + " 条用户历史状态/触发提醒中提取心情/状态关键词：\n" + joined + "\n\n" + "要求：\n" + "1. 只输出 JSON 数组，不要任何解释。\n" + "2. 词数控制在 10-18 个。\n" + "3. weight 是相对值（1-10），表示这个词的\"重要度\"。\n" + "4. 词性以形容词/状态名词为主（如\"专注\"\"疲惫\"\"放松\"\"焦虑\"）。"
}
val FALLBACK_MOOD_WORDS = _uA(
    MoodWord(word = "平静", weight = 4),
    MoodWord(word = "专注", weight = 3),
    MoodWord(word = "稍累", weight = 2)
) as UTSArray<MoodWord>
fun getFallbackMoodWords(): UTSArray<MoodWord> {
    return FALLBACK_MOOD_WORDS
}
val MIN_SAMPLE_COUNT: Number = 3
val MAX_RAW_TEXTS: Number = 80
fun collectRawTexts(): UTSArray<String> {
    var entries: UTSArray<LlmHistoryEntry> = _uA()
    try {
        entries = getAllStateAndAdhocTexts(MAX_RAW_TEXTS)
    }
     catch (_: Throwable) {
        entries = _uA()
    }
    val texts: UTSArray<String> = _uA()
    run {
        var i: Number = 0
        while(i < entries.length){
            val e = entries[i]
            if (e == null) {
                i++
                continue
            }
            val pr = e.parsedResult
            if (pr != null) {
                val sd = pr.stateDescription
                if (sd != null) {
                    val sdVal = sd!!!!
                    if (sdVal.length > 0) {
                        texts.push(sdVal)
                    }
                }
            }
            if (e.adhocText != null && e.adhocText!!.length > 0) {
                texts.push(e.adhocText!!)
            }
            i++
        }
    }
    return texts
}
fun getMoodCloudSampleCount(): Number {
    return collectRawTexts().length
}
fun generateMoodCloud(onOK: (words: UTSArray<MoodWord>) -> Unit, onFail: (msg: String) -> Unit): Unit {
    val raw = collectRawTexts()
    if (raw.length < MIN_SAMPLE_COUNT) {
        onOK(getFallbackMoodWords())
        return
    }
    val userPrompt = buildMoodCloudPrompt(raw)
    console.log("[MoodCloudService] generateMoodCloud samples=" + raw.length + " promptLen=" + userPrompt.length, " at services/MoodCloudService.uts:47")
    callMinimaxChat(SYSTEM_PROMPT_MOOD_CLOUD, userPrompt, 500, fun(rawResp: String): Unit {
        val words = parseMoodCloudJson(rawResp)
        if (words.length < 1) {
            onOK(getFallbackMoodWords())
            return
        }
        onOK(words)
    }
    , fun(): Unit {
        console.warn("[MoodCloudService] LLM 失败", " at services/MoodCloudService.uts:58")
        onOK(getFallbackMoodWords())
    }
    )
}
fun parseMoodCloudJson(raw: String): UTSArray<MoodWord> {
    if (raw == null || raw.length < 1) {
        return _uA()
    }
    var s: String = raw.trim()
    if (s.startsWith("```")) {
        val firstNl = s.indexOf("\n")
        if (firstNl > 0) {
            s = s.substring(firstNl + 1)
        }
        if (s.endsWith("```")) {
            s = s.substring(0, s.length - 3)
        }
    }
    s = s.trim()
    val arrStart: Number = s.indexOf("[")
    val arrEnd: Number = s.lastIndexOf("]")
    if (arrStart < 0 || arrEnd <= arrStart) {
        return _uA()
    }
    val sub: String = s.substring(arrStart, arrEnd + 1)
    try {
        val parsed = UTSAndroid.consoleDebugError(JSON.parse(sub), " at services/MoodCloudService.uts:77")
        if (!UTSArray.isArray(parsed)) {
            return _uA()
        }
        val out: UTSArray<MoodWord> = _uA()
        val arr = parsed as UTSArray<MoodWord>
        run {
            var i: Number = 0
            while(i < arr.length){
                val it = arr[i]
                if (it == null) {
                    i++
                    continue
                }
                val w = it.word
                if (w == null || w.length < 1) {
                    i++
                    continue
                }
                var wn: Number = it.weight
                if (wn == null) {
                    wn = 1
                }
                wn = Math.floor(wn)
                if (wn < 1) {
                    wn = 1
                }
                if (wn > 10) {
                    wn = 10
                }
                out.push(MoodWord(word = w, weight = wn))
                i++
            }
        }
        return out
    }
     catch (e: Throwable) {
        val msg = if (e != null) {
            ("" + e)
        } else {
            "null"
        }
        console.warn("[MoodCloudService] JSON 解析失败: " + msg, " at services/MoodCloudService.uts:97")
        return _uA()
    }
}
val GenComponentsWordCloudClass = CreateVueComponent(GenComponentsWordCloud::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsWordCloud.inheritAttrs, inject = GenComponentsWordCloud.inject, props = GenComponentsWordCloud.props, propsNeedCastKeys = GenComponentsWordCloud.propsNeedCastKeys, emits = GenComponentsWordCloud.emits, components = GenComponentsWordCloud.components, styles = GenComponentsWordCloud.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsWordCloud.setup(props as GenComponentsWordCloud)
    }
    )
}
, fun(instance, renderer): GenComponentsWordCloud {
    return GenComponentsWordCloud(instance)
}
)
val GenPagesDataDashboardClass = CreateVueComponent(GenPagesDataDashboard::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesDataDashboard.inheritAttrs, inject = GenPagesDataDashboard.inject, props = GenPagesDataDashboard.props, propsNeedCastKeys = GenPagesDataDashboard.propsNeedCastKeys, emits = GenPagesDataDashboard.emits, components = GenPagesDataDashboard.components, styles = GenPagesDataDashboard.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesDataDashboard.setup(props as GenPagesDataDashboard)
    }
    )
}
, fun(instance, renderer): GenPagesDataDashboard {
    return GenPagesDataDashboard(instance, renderer)
}
)
val GenComponentsNumberPickerDialogClass = CreateVueComponent(GenComponentsNumberPickerDialog::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsNumberPickerDialog.inheritAttrs, inject = GenComponentsNumberPickerDialog.inject, props = GenComponentsNumberPickerDialog.props, propsNeedCastKeys = GenComponentsNumberPickerDialog.propsNeedCastKeys, emits = GenComponentsNumberPickerDialog.emits, components = GenComponentsNumberPickerDialog.components, styles = GenComponentsNumberPickerDialog.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsNumberPickerDialog.setup(props as GenComponentsNumberPickerDialog)
    }
    )
}
, fun(instance, renderer): GenComponentsNumberPickerDialog {
    return GenComponentsNumberPickerDialog(instance)
}
)
val GenComponentsTimeRangePickerDialogClass = CreateVueComponent(GenComponentsTimeRangePickerDialog::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsTimeRangePickerDialog.inheritAttrs, inject = GenComponentsTimeRangePickerDialog.inject, props = GenComponentsTimeRangePickerDialog.props, propsNeedCastKeys = GenComponentsTimeRangePickerDialog.propsNeedCastKeys, emits = GenComponentsTimeRangePickerDialog.emits, components = GenComponentsTimeRangePickerDialog.components, styles = GenComponentsTimeRangePickerDialog.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsTimeRangePickerDialog.setup(props as GenComponentsTimeRangePickerDialog)
    }
    )
}
, fun(instance, renderer): GenComponentsTimeRangePickerDialog {
    return GenComponentsTimeRangePickerDialog(instance)
}
)
val GenComponentsRuleEditDialogClass = CreateVueComponent(GenComponentsRuleEditDialog::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsRuleEditDialog.inheritAttrs, inject = GenComponentsRuleEditDialog.inject, props = GenComponentsRuleEditDialog.props, propsNeedCastKeys = GenComponentsRuleEditDialog.propsNeedCastKeys, emits = GenComponentsRuleEditDialog.emits, components = GenComponentsRuleEditDialog.components, styles = GenComponentsRuleEditDialog.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsRuleEditDialog.setup(props as GenComponentsRuleEditDialog)
    }
    )
}
, fun(instance, renderer): GenComponentsRuleEditDialog {
    return GenComponentsRuleEditDialog(instance)
}
)
val GenPagesSettingsIndexClass = CreateVueComponent(GenPagesSettingsIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSettingsIndex.inheritAttrs, inject = GenPagesSettingsIndex.inject, props = GenPagesSettingsIndex.props, propsNeedCastKeys = GenPagesSettingsIndex.propsNeedCastKeys, emits = GenPagesSettingsIndex.emits, components = GenPagesSettingsIndex.components, styles = GenPagesSettingsIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesSettingsIndex.setup(props as GenPagesSettingsIndex)
    }
    )
}
, fun(instance, renderer): GenPagesSettingsIndex {
    return GenPagesSettingsIndex(instance, renderer)
}
)
open class DbTableInfo (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var count: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DbTableInfo", "pages/debug/logs.uvue", 179, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return DbTableInfoReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class DbTableInfoReactiveObject : DbTableInfo, IUTSReactive<DbTableInfo> {
    override var __v_raw: DbTableInfo
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: DbTableInfo, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(name = __v_raw.name, count = __v_raw.count) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): DbTableInfoReactiveObject {
        return DbTableInfoReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var count: Number
        get() {
            return _tRG(__v_raw, "count", __v_raw.count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("count")) {
                return
            }
            val oldValue = __v_raw.count
            __v_raw.count = value
            _tRS(__v_raw, "count", oldValue, value)
        }
}
open class ConfigItem (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var value: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ConfigItem", "pages/debug/logs.uvue", 183, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ConfigItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ConfigItemReactiveObject : ConfigItem, IUTSReactive<ConfigItem> {
    override var __v_raw: ConfigItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ConfigItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ConfigItemReactiveObject {
        return ConfigItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var key: String
        get() {
            return _tRG(__v_raw, "key", __v_raw.key, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("key")) {
                return
            }
            val oldValue = __v_raw.key
            __v_raw.key = value
            _tRS(__v_raw, "key", oldValue, value)
        }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
}
val GenPagesDebugLogsClass = CreateVueComponent(GenPagesDebugLogs::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesDebugLogs.inheritAttrs, inject = GenPagesDebugLogs.inject, props = GenPagesDebugLogs.props, propsNeedCastKeys = GenPagesDebugLogs.propsNeedCastKeys, emits = GenPagesDebugLogs.emits, components = GenPagesDebugLogs.components, styles = GenPagesDebugLogs.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesDebugLogs.setup(props as GenPagesDebugLogs)
    }
    )
}
, fun(instance, renderer): GenPagesDebugLogs {
    return GenPagesDebugLogs(instance, renderer)
}
)
val GenComponentsLlmRuleReviewDialogClass = CreateVueComponent(GenComponentsLlmRuleReviewDialog::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenComponentsLlmRuleReviewDialog.inheritAttrs, inject = GenComponentsLlmRuleReviewDialog.inject, props = GenComponentsLlmRuleReviewDialog.props, propsNeedCastKeys = GenComponentsLlmRuleReviewDialog.propsNeedCastKeys, emits = GenComponentsLlmRuleReviewDialog.emits, components = GenComponentsLlmRuleReviewDialog.components, styles = GenComponentsLlmRuleReviewDialog.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenComponentsLlmRuleReviewDialog.setup(props as GenComponentsLlmRuleReviewDialog)
    }
    )
}
, fun(instance, renderer): GenComponentsLlmRuleReviewDialog {
    return GenComponentsLlmRuleReviewDialog(instance)
}
)
open class ReviewPayload (
    @JsonNotNull
    open var historyId: Number,
    @JsonNotNull
    open var decision: String,
    @JsonNotNull
    open var targetRuleId: Number,
    @JsonNotNull
    open var reasoning: String,
    @JsonNotNull
    open var confidence: Number,
    open var suggestedRule: EffectiveTriggerRule? = null,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ReviewPayload", "pages/debug/llm-rules.uvue", 87, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ReviewPayloadReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ReviewPayloadReactiveObject : ReviewPayload, IUTSReactive<ReviewPayload> {
    override var __v_raw: ReviewPayload
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ReviewPayload, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(historyId = __v_raw.historyId, decision = __v_raw.decision, targetRuleId = __v_raw.targetRuleId, reasoning = __v_raw.reasoning, confidence = __v_raw.confidence, suggestedRule = __v_raw.suggestedRule) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ReviewPayloadReactiveObject {
        return ReviewPayloadReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var historyId: Number
        get() {
            return _tRG(__v_raw, "historyId", __v_raw.historyId, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("historyId")) {
                return
            }
            val oldValue = __v_raw.historyId
            __v_raw.historyId = value
            _tRS(__v_raw, "historyId", oldValue, value)
        }
    override var decision: String
        get() {
            return _tRG(__v_raw, "decision", __v_raw.decision, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("decision")) {
                return
            }
            val oldValue = __v_raw.decision
            __v_raw.decision = value
            _tRS(__v_raw, "decision", oldValue, value)
        }
    override var targetRuleId: Number
        get() {
            return _tRG(__v_raw, "targetRuleId", __v_raw.targetRuleId, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("targetRuleId")) {
                return
            }
            val oldValue = __v_raw.targetRuleId
            __v_raw.targetRuleId = value
            _tRS(__v_raw, "targetRuleId", oldValue, value)
        }
    override var reasoning: String
        get() {
            return _tRG(__v_raw, "reasoning", __v_raw.reasoning, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("reasoning")) {
                return
            }
            val oldValue = __v_raw.reasoning
            __v_raw.reasoning = value
            _tRS(__v_raw, "reasoning", oldValue, value)
        }
    override var confidence: Number
        get() {
            return _tRG(__v_raw, "confidence", __v_raw.confidence, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("confidence")) {
                return
            }
            val oldValue = __v_raw.confidence
            __v_raw.confidence = value
            _tRS(__v_raw, "confidence", oldValue, value)
        }
    override var suggestedRule: EffectiveTriggerRule?
        get() {
            return _tRG(__v_raw, "suggestedRule", __v_raw.suggestedRule, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("suggestedRule")) {
                return
            }
            val oldValue = __v_raw.suggestedRule
            __v_raw.suggestedRule = value
            _tRS(__v_raw, "suggestedRule", oldValue, value)
        }
}
val GenPagesDebugLlmRulesClass = CreateVueComponent(GenPagesDebugLlmRules::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesDebugLlmRules.inheritAttrs, inject = GenPagesDebugLlmRules.inject, props = GenPagesDebugLlmRules.props, propsNeedCastKeys = GenPagesDebugLlmRules.propsNeedCastKeys, emits = GenPagesDebugLlmRules.emits, components = GenPagesDebugLlmRules.components, styles = GenPagesDebugLlmRules.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesDebugLlmRules.setup(props as GenPagesDebugLlmRules)
    }
    )
}
, fun(instance, renderer): GenPagesDebugLlmRules {
    return GenPagesDebugLlmRules(instance, renderer)
}
)
val GenPagesSettingsAppCategoriesIndexClass = CreateVueComponent(GenPagesSettingsAppCategoriesIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSettingsAppCategoriesIndex.inheritAttrs, inject = GenPagesSettingsAppCategoriesIndex.inject, props = GenPagesSettingsAppCategoriesIndex.props, propsNeedCastKeys = GenPagesSettingsAppCategoriesIndex.propsNeedCastKeys, emits = GenPagesSettingsAppCategoriesIndex.emits, components = GenPagesSettingsAppCategoriesIndex.components, styles = GenPagesSettingsAppCategoriesIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesSettingsAppCategoriesIndex.setup(props as GenPagesSettingsAppCategoriesIndex)
    }
    )
}
, fun(instance, renderer): GenPagesSettingsAppCategoriesIndex {
    return GenPagesSettingsAppCategoriesIndex(instance, renderer)
}
)
open class CandidateApp (
    @JsonNotNull
    open var packageName: String,
    @JsonNotNull
    open var appLabel: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CandidateApp", "pages/settings/blacklist.uvue", 71, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return CandidateAppReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class CandidateAppReactiveObject : CandidateApp, IUTSReactive<CandidateApp> {
    override var __v_raw: CandidateApp
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: CandidateApp, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(packageName = __v_raw.packageName, appLabel = __v_raw.appLabel) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): CandidateAppReactiveObject {
        return CandidateAppReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var packageName: String
        get() {
            return _tRG(__v_raw, "packageName", __v_raw.packageName, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("packageName")) {
                return
            }
            val oldValue = __v_raw.packageName
            __v_raw.packageName = value
            _tRS(__v_raw, "packageName", oldValue, value)
        }
    override var appLabel: String
        get() {
            return _tRG(__v_raw, "appLabel", __v_raw.appLabel, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("appLabel")) {
                return
            }
            val oldValue = __v_raw.appLabel
            __v_raw.appLabel = value
            _tRS(__v_raw, "appLabel", oldValue, value)
        }
}
open class BlacklistEntry (
    @JsonNotNull
    open var pkg: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("BlacklistEntry", "pages/settings/blacklist.uvue", 76, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return BlacklistEntryReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class BlacklistEntryReactiveObject : BlacklistEntry, IUTSReactive<BlacklistEntry> {
    override var __v_raw: BlacklistEntry
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: BlacklistEntry, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(pkg = __v_raw.pkg, label = __v_raw.label) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): BlacklistEntryReactiveObject {
        return BlacklistEntryReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var pkg: String
        get() {
            return _tRG(__v_raw, "pkg", __v_raw.pkg, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("pkg")) {
                return
            }
            val oldValue = __v_raw.pkg
            __v_raw.pkg = value
            _tRS(__v_raw, "pkg", oldValue, value)
        }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
}
val GenPagesSettingsBlacklistClass = CreateVueComponent(GenPagesSettingsBlacklist::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSettingsBlacklist.inheritAttrs, inject = GenPagesSettingsBlacklist.inject, props = GenPagesSettingsBlacklist.props, propsNeedCastKeys = GenPagesSettingsBlacklist.propsNeedCastKeys, emits = GenPagesSettingsBlacklist.emits, components = GenPagesSettingsBlacklist.components, styles = GenPagesSettingsBlacklist.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesSettingsBlacklist.setup(props as GenPagesSettingsBlacklist)
    }
    )
}
, fun(instance, renderer): GenPagesSettingsBlacklist {
    return GenPagesSettingsBlacklist(instance, renderer)
}
)
fun createApp(): UTSJSONObject {
    val app = createSSRApp(GenAppClass)
    return _uO("app" to app)
}
fun main(app: IApp) {
    enableStyleIsolation()
    definePageRoutes()
    defineAppConfig()
    (createApp()["app"] as VueApp).mount(app, GenUniApp())
}
open class UniAppConfig : io.dcloud.uniapp.appframe.AppConfig {
    override var name: String = "微习惯健康伴侣"
    override var appid: String = "__UNI__B805CE2"
    override var versionName: String = "1.0.5"
    override var versionCode: String = "105"
    override var uniCompilerVersion: String = "5.11"
    constructor() : super() {}
}
fun definePageRoutes() {
    __uniRoutes.push(UniPageRoute(path = "pages/guide/index", component = GenPagesGuideIndexClass, meta = UniPageMeta(isQuit = true), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/home/index", component = GenPagesHomeIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/action/execute", component = GenPagesActionExecuteClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/data/dashboard", component = GenPagesDataDashboardClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/settings/index", component = GenPagesSettingsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/debug/logs", component = GenPagesDebugLogsClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "调试日志")))
    __uniRoutes.push(UniPageRoute(path = "pages/debug/llm-rules", component = GenPagesDebugLlmRulesClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "LLM 规则审核")))
    __uniRoutes.push(UniPageRoute(path = "pages/settings/app-categories/index", component = GenPagesSettingsAppCategoriesIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "App 分类管理")))
    __uniRoutes.push(UniPageRoute(path = "pages/settings/blacklist", component = GenPagesSettingsBlacklistClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "不提醒这些 App")))
}
val __uniTabBar: Map<String, Any?>? = _uM("list" to _uA(
    _uM("pagePath" to "pages/home/index", "text" to "首页"),
    _uM("pagePath" to "pages/data/dashboard", "text" to "数据"),
    _uM("pagePath" to "pages/settings/index", "text" to "我的")
))
val __uniLaunchPage: Map<String, Any?> = _uM("url" to "pages/guide/index", "style" to _uM("navigationStyle" to "custom", "navigationBarTitleText" to ""))
fun defineAppConfig() {
    __uniConfig.entryPagePath = "/pages/guide/index"
    __uniConfig.globalStyle = _uM("navigationBarTextStyle" to "black", "navigationBarTitleText" to "", "navigationBarBackgroundColor" to "#F8F8F8", "backgroundColor" to "#F7FAFF")
    __uniConfig.getTabBarConfig = fun(): Map<String, Any>? {
        return _uM("list" to _uA(
            _uM("pagePath" to "pages/home/index", "text" to "首页"),
            _uM("pagePath" to "pages/data/dashboard", "text" to "数据"),
            _uM("pagePath" to "pages/settings/index", "text" to "我的")
        ))
    }
    __uniConfig.tabBar = __uniConfig.getTabBarConfig()
    __uniConfig.conditionUrl = ""
    __uniConfig.uniIdRouter = Map()
    __uniConfig.ready = true
}
open class UniCloudConfig : io.dcloud.unicloud.InternalUniCloudConfig, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UniCloudConfig", "main.uts", 55, 14)
    }
    override var isDev: Boolean = true
    override var spaceList: String = "[{\"provider\":\"aliyun\",\"spaceName\":\"trial-w7onvxzrxrp2h087561\",\"spaceId\":\"mp-76d188c8-df1a-4535-9cee-3bb94b3a694d\",\"clientSecret\":\"b94cO0f6Qv3WwtDVDIwHFw==\",\"endpoint\":\"https://api.next.bspapp.com\"}]"
    override var debuggerInfo: String? = "{\"address\":[\"127.0.0.1\",\"172.24.90.1\"],\"servePort\":7000,\"debugPort\":9000,\"initialLaunchType\":\"local\",\"skipFiles\":[\"<node_internals>/**\",\"D:/Program Files (x86)/HBuilderX/plugins/unicloud/**/*.js\"]}"
    override var secureNetworkEnable: Boolean = false
    override var secureNetworkConfig: String? = "[]"
    constructor() : super() {}
}
open class GenUniApp : UniAppImpl() {
    open val vm: GenApp?
        get() {
            return getAppVm() as GenApp?
        }
    open val `$vm`: GenApp?
        get() {
            return getAppVm() as GenApp?
        }
}
fun getApp(): GenUniApp {
    return getUniApp() as GenUniApp
}
