@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNIB805CE2
import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
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
import android.view.WindowManager.LayoutParams as WindowManagerLayoutParams
import io.dcloud.uniapp.extapi.connectSocket as uni_connectSocket
import io.dcloud.uniapp.extapi.exit as uni_exit
import io.dcloud.uniapp.extapi.getFileSystemManager as uni_getFileSystemManager
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.vibrateShort as uni_vibrateShort
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
    val hosts: String = "192.168.3.87,127.0.0.1"
    val port: String = "8090"
    val id: String = "app-android_6lVZbf"
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TriggerLog", "models/TriggerLog.uts", 2, 13)
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
open class RowEntry (
    @JsonNotNull
    open var cols: UTSArray<String>,
    @JsonNotNull
    open var vals: UTSArray<Any>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("RowEntry", "database/DatabaseManager.uts", 1, 6)
    }
}
open class TableEntry (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var rows: UTSArray<RowEntry>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TableEntry", "database/DatabaseManager.uts", 5, 6)
    }
}
open class AutoIncrementEntry (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var value: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AutoIncrementEntry", "database/DatabaseManager.uts", 9, 6)
    }
}
open class MicroHabitDB : IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MicroHabitDB", "database/DatabaseManager.uts", 13, 7)
    }
    private var tables: UTSArray<TableEntry>
    private var autoIncrement: UTSArray<AutoIncrementEntry>
    private constructor(){
        this.tables = _uA()
        this.autoIncrement = _uA()
    }
    open fun init(): Unit {
        try {
            this.loadFromStorage()
        }
         catch (e: Throwable) {
            console.error("DatabaseManager.init error: " + e, " at database/DatabaseManager.uts:37")
        }
    }
    open fun execSql(sql: String, params: UTSArray<Any> = _uA()): Unit {
        val upper = sql.toUpperCase().trim()
        if (upper.startsWith("PRAGMA")) {
            return
        }
        if (upper.startsWith("CREATE TABLE")) {
            val nameMatch = sql.match(UTSRegExp("CREATE\\s+TABLE\\s+(?:IF\\s+NOT\\s+EXISTS\\s+)?(\\w+)", "i"))
            if (nameMatch != null) {
                val tableName = nameMatch[1] as String
                if (this.findTable(tableName) == null) {
                    val tableEntry = TableEntry(name = tableName, rows = _uA())
                    this.tables.push(tableEntry)
                    val autoIncEntry = AutoIncrementEntry(name = tableName, value = 1)
                    this.autoIncrement.push(autoIncEntry)
                }
            }
            return
        }
        if (upper.startsWith("CREATE INDEX") || upper.startsWith("CREATE UNIQUE INDEX")) {
            return
        }
        if (upper.startsWith("INSERT INTO")) {
            val nameMatch = sql.match(UTSRegExp("INSERT\\s+INTO\\s+(\\w+)", "i"))
            if (nameMatch != null) {
                val tableName = nameMatch[1] as String
                val entry = this.findTable(tableName)
                if (entry == null) {
                    return
                }
                val colsMatch = sql.match(UTSRegExp("\\(([^)]+)\\)\\s*VALUES", "i"))
                if (colsMatch == null) {
                    return
                }
                val colsParts = (colsMatch[1] as String).split(",")
                val row = RowEntry(cols = _uA(
                    "id"
                ), vals = _uA(
                    this.getNextId(tableName)
                ))
                run {
                    var c: Number = 0
                    while(c < colsParts.length && c < params.length){
                        val col = colsParts[c].trim()
                        row.cols.push(col)
                        row.vals.push(params[c])
                        c++
                    }
                }
                entry.rows.push(row)
                this.saveToStorage()
            }
            return
        }
        if (upper.startsWith("UPDATE")) {
            val nameMatch = sql.match(UTSRegExp("UPDATE\\s+(\\w+)", "i"))
            if (nameMatch != null) {
                val tableName = nameMatch[1] as String
                val entry = this.findTable(tableName)
                if (entry == null) {
                    return
                }
                val setMatch = sql.match(UTSRegExp("SET\\s+(.+?)(?:\\s+WHERE\\s|\$)", "i"))
                val whereMatch = sql.match(UTSRegExp("WHERE\\s+(.+)\$", "i"))
                if (setMatch == null) {
                    return
                }
                val setParts = (setMatch[1] as String).split(",")
                val setCols: UTSArray<String> = _uA()
                run {
                    var c: Number = 0
                    while(c < setParts.length){
                        val eqParts = setParts[c].trim().split("=")
                        if (eqParts.length >= 1) {
                            setCols.push(eqParts[0].trim())
                        }
                        c++
                    }
                }
                var paramIdx: Number = 0
                run {
                    var r: Number = 0
                    while(r < entry.rows.length){
                        if (whereMatch != null) {
                            val whereParams: UTSArray<Any> = _uA()
                            run {
                                var w = setCols.length
                                while(w < params.length){
                                    whereParams.push(params[w])
                                    w++
                                }
                            }
                            if (!this.matchWhere(entry.rows[r], whereMatch[1] as String, whereParams)) {
                                r++
                                continue
                            }
                        }
                        run {
                            var c: Number = 0
                            while(c < setCols.length){
                                this.setColVal(entry.rows[r], setCols[c], params[paramIdx + c])
                                c++
                            }
                        }
                        paramIdx += setCols.length
                        r++
                    }
                }
                this.saveToStorage()
            }
            return
        }
        if (upper.startsWith("DELETE FROM") || upper.startsWith("DELETE")) {
            val nameMatch = sql.match(UTSRegExp("DELETE\\s+FROM\\s+(\\w+)", "i"))
            if (nameMatch != null) {
                val tableName = nameMatch[1] as String
                val entry = this.findTable(tableName)
                if (entry == null) {
                    return
                }
                val whereMatch = sql.match(UTSRegExp("WHERE\\s+(.+)\$", "i"))
                if (whereMatch != null) {
                    val toRemove: UTSArray<Number> = _uA()
                    run {
                        var i: Number = 0
                        while(i < entry.rows.length){
                            if (this.matchWhere(entry.rows[i], whereMatch[1] as String, params)) {
                                toRemove.push(i)
                            }
                            i++
                        }
                    }
                    run {
                        var i = toRemove.length - 1
                        while(i >= 0){
                            entry.rows.splice(toRemove[i], 1)
                            i--
                        }
                    }
                } else {
                    entry.rows = _uA()
                }
                this.saveToStorage()
            }
            return
        }
    }
    open fun query(sql: String, params: UTSArray<Any> = _uA()): UTSArray<Map<String, Any>> {
        val upper = sql.toUpperCase().trim()
        if (upper.startsWith("SELECT")) {
            val nameMatch = sql.match(UTSRegExp("FROM\\s+(\\w+)", "i"))
            if (nameMatch == null) {
                return _uA()
            }
            val tableName = nameMatch[1] as String
            val entry = this.findTable(tableName)
            if (entry == null) {
                return _uA()
            }
            val whereMatch = sql.match(UTSRegExp("WHERE\\s+(.+?)(?:\\s+ORDER\\s+BY|\$)", "i"))
            val orderMatch = sql.match(UTSRegExp("ORDER\\s+BY\\s+(.+)\$", "i"))
            var results: UTSArray<RowEntry> = _uA()
            if (whereMatch != null) {
                run {
                    var i: Number = 0
                    while(i < entry.rows.length){
                        if (this.matchWhere(entry.rows[i], whereMatch[1] as String, params)) {
                            results.push(this.cloneRow(entry.rows[i]))
                        }
                        i++
                    }
                }
            } else {
                run {
                    var i: Number = 0
                    while(i < entry.rows.length){
                        results.push(this.cloneRow(entry.rows[i]))
                        i++
                    }
                }
            }
            if (orderMatch != null) {
                val orderParts = (orderMatch[1] as String).trim().split(UTSRegExp("\\s+", ""))
                val orderCol = orderParts[0]
                val orderDir = if (orderParts.length > 1) {
                    orderParts[1].toUpperCase()
                } else {
                    "ASC"
                }
                this.sortResults(results, orderCol, orderDir)
            }
            val selectMatch = sql.match(UTSRegExp("SELECT\\s+(.+?)\\s+FROM", "i"))
            if (selectMatch != null) {
                val selectPart = (selectMatch[1] as String).trim()
                if (selectPart === "last_insert_rowid() as id") {
                    val out: UTSArray<Map<String, Any>> = _uA()
                    run {
                        var i: Number = 0
                        while(i < results.length){
                            out.push(this.rowToMap(results[i]))
                            i++
                        }
                    }
                    return out
                }
                if (selectPart !== "*") {
                    val aggMatch = selectPart.match(UTSRegExp("(\\w+)\\((\\w+)\\)", "i"))
                    if (aggMatch != null) {
                        val aggFn = (aggMatch[1] as String).toUpperCase()
                        val aggCol = aggMatch[2] as String
                        if (aggFn === "COUNT") {
                            val row = RowEntry(cols = _uA(), vals = _uA())
                            row.cols.push("COUNT(*)")
                            row.vals.push(results.length)
                            return _uA(
                                this.rowToMap(row)
                            )
                        }
                        if (aggFn === "SUM") {
                            var sum: Number = 0
                            run {
                                var i: Number = 0
                                while(i < results.length){
                                    val v = this.getColVal(results[i], aggCol)
                                    val n = parseFloat(this.toStr(v))
                                    if (!isNaN(n)) {
                                        sum += n
                                    }
                                    i++
                                }
                            }
                            val row = RowEntry(cols = _uA(), vals = _uA())
                            row.cols.push("SUM(" + aggCol + ")")
                            row.vals.push(sum)
                            return _uA(
                                this.rowToMap(row)
                            )
                        }
                    }
                }
            }
            val out: UTSArray<Map<String, Any>> = _uA()
            run {
                var i: Number = 0
                while(i < results.length){
                    out.push(this.rowToMap(results[i]))
                    i++
                }
            }
            return out
        }
        return _uA()
    }
    open fun queryOne(sql: String, params: UTSArray<Any> = _uA()): Map<String, Any>? {
        val results = this.query(sql, params)
        return if (results.length > 0) {
            results[0]
        } else {
            null
        }
    }
    open fun insert(table: String, data: Any): Number {
        val entry = this.findTable(table)
        if (entry == null) {
            return -1
        }
        val id = this.getNextId(table)
        val row = RowEntry(cols = _uA(), vals = _uA())
        row.cols.push("id")
        row.vals.push(id)
        val dataRow = this.objToRow(data)
        run {
            var d: Number = 0
            while(d < dataRow.cols.length){
                row.cols.push(dataRow.cols[d])
                row.vals.push(dataRow.vals[d])
                d++
            }
        }
        entry.rows.push(row)
        this.saveToStorage()
        return id
    }
    open fun update(table: String, data: Any, where: String, whereArgs: UTSArray<Any> = _uA()): Unit {
        val entry = this.findTable(table)
        if (entry == null) {
            return
        }
        val dataRow = this.objToRow(data)
        run {
            var i: Number = 0
            while(i < entry.rows.length){
                if (this.matchWhere(entry.rows[i], where, whereArgs)) {
                    run {
                        var d: Number = 0
                        while(d < dataRow.cols.length){
                            this.setColVal(entry.rows[i], dataRow.cols[d], dataRow.vals[d])
                            d++
                        }
                    }
                }
                i++
            }
        }
        this.saveToStorage()
    }
    open fun `delete`(table: String, where: String, whereArgs: UTSArray<Any> = _uA()): Unit {
        val entry = this.findTable(table)
        if (entry == null) {
            return
        }
        val toRemove: UTSArray<Number> = _uA()
        run {
            var i: Number = 0
            while(i < entry.rows.length){
                if (this.matchWhere(entry.rows[i], where, whereArgs)) {
                    toRemove.push(i)
                }
                i++
            }
        }
        run {
            var i = toRemove.length - 1
            while(i >= 0){
                entry.rows.splice(toRemove[i], 1)
                i--
            }
        }
        this.saveToStorage()
    }
    open fun close(): Unit {
        this.saveToStorage()
    }
    private fun findTable(name: String): TableEntry? {
        run {
            var i: Number = 0
            while(i < this.tables.length){
                if (this.tables[i].name === name) {
                    return this.tables[i]
                }
                i++
            }
        }
        return null
    }
    private fun getNextId(tableName: String): Number {
        run {
            var i: Number = 0
            while(i < this.autoIncrement.length){
                if (this.autoIncrement[i].name === tableName) {
                    val id = this.autoIncrement[i].value
                    this.autoIncrement[i].value = this.autoIncrement[i].value + 1
                    return id
                }
                i++
            }
        }
        return 1
    }
    private fun toStr(v: Any?): String {
        if (v == null) {
            return "null"
        }
        return "" + v
    }
    private fun getColVal(row: RowEntry, col: String): Any? {
        run {
            var i: Number = 0
            while(i < row.cols.length){
                if (row.cols[i] === col) {
                    return row.vals[i]
                }
                i++
            }
        }
        return null
    }
    private fun setColVal(row: RowEntry, col: String, kVal: Any): Unit {
        run {
            var i: Number = 0
            while(i < row.cols.length){
                if (row.cols[i] === col) {
                    row.vals[i] = kVal
                    return
                }
                i++
            }
        }
        row.cols.push(col)
        row.vals.push(kVal)
    }
    private fun cloneRow(row: RowEntry): RowEntry {
        val clone = RowEntry(cols = _uA(), vals = _uA())
        run {
            var i: Number = 0
            while(i < row.cols.length){
                clone.cols.push(row.cols[i])
                clone.vals.push(row.vals[i])
                i++
            }
        }
        return clone
    }
    private fun rowToMap(row: RowEntry): Map<String, Any> {
        val map: Map<String, Any> = Map()
        run {
            var i: Number = 0
            while(i < row.cols.length){
                map.set(row.cols[i], row.vals[i])
                i++
            }
        }
        return map
    }
    private fun rowToObj(row: RowEntry): Any {
        val pairs: UTSArray<String> = _uA()
        run {
            var i: Number = 0
            while(i < row.cols.length){
                pairs.push("\"" + row.cols[i] + "\":" + JSON.stringify(row.vals[i]))
                i++
            }
        }
        val jsonStr = "{" + pairs.join(",") + "}"
        return UTSAndroid.consoleDebugError(JSON.parse(jsonStr), " at database/DatabaseManager.uts:330") as Any
    }
    private fun objToRow(obj: Any): RowEntry {
        val row = RowEntry(cols = _uA(), vals = _uA())
        val jsonStr = JSON.stringify(obj)
        if (jsonStr != null && jsonStr.length > 0 && jsonStr.substring(0, 1) === "{") {
            val inner = this.extractJsonPairs(jsonStr)
            run {
                var p: Number = 0
                while(p < inner.length){
                    val kv = this.splitPair(inner[p])
                    if (kv != null) {
                        row.cols.push(kv[0] as String)
                        row.vals.push(kv[1])
                    }
                    p++
                }
            }
        }
        return row
    }
    private fun extractJsonPairs(jsonStr: String): UTSArray<String> {
        val result: UTSArray<String> = _uA()
        val content = jsonStr.substring(1, jsonStr.length - 1)
        var depth: Number = 0
        var start: Number = 0
        var inStr = false
        run {
            var i: Number = 0
            while(i < content.length){
                val ch = content.substring(i, i + 1)
                if (ch === "\"" && (i === 0 || content.substring(i - 1, i) !== "\\")) {
                    inStr = !inStr
                } else if (!inStr) {
                    if (ch === "{" || ch === "[") {
                        depth++
                    } else if (ch === "}" || ch === "]") {
                        depth--
                    } else if (ch === "," && depth === 0) {
                        result.push(content.substring(start, i).trim())
                        start = i + 1
                    }
                }
                i++
            }
        }
        if (start < content.length) {
            result.push(content.substring(start).trim())
        }
        return result
    }
    private fun splitPair(pair: String): UTSArray<Any>? {
        var colonIdx: Number = -1
        var inStr = false
        run {
            var i: Number = 0
            while(i < pair.length){
                val ch = pair.substring(i, i + 1)
                if (ch === "\"" && (i === 0 || pair.substring(i - 1, i) !== "\\")) {
                    inStr = !inStr
                } else if (!inStr && ch === ":") {
                    colonIdx = i
                }
                i++
            }
        }
        if (colonIdx < 0) {
            return null
        }
        val keyPart = pair.substring(0, colonIdx).trim()
        var key = keyPart
        if (key.length >= 2 && key.substring(0, 1) === "\"" && key.substring(key.length - 1) === "\"") {
            key = key.substring(1, key.length - 1)
        }
        val valPart = pair.substring(colonIdx + 1).trim()
        var kVal: Any = valPart
        if (valPart === "true") {
            kVal = true
        } else if (valPart === "false") {
            kVal = false
        } else if (valPart.length >= 2 && valPart.substring(0, 1) === "\"" && valPart.substring(valPart.length - 1) === "\"") {
            kVal = valPart.substring(1, valPart.length - 1)
        } else {
            val parsedNum = parseFloat(valPart)
            if (!isNaN(parsedNum) && this.isNumericStr(valPart)) {
                kVal = parsedNum
            }
        }
        return _uA(
            key,
            kVal
        )
    }
    private fun isNumericStr(s: String): Boolean {
        if (s.length === 0) {
            return false
        }
        var dots: Number = 0
        run {
            var i: Number = 0
            while(i < s.length){
                val ch = s.substring(i, i + 1)
                if (ch === "-" && i === 0) {
                    i++
                    continue
                }
                if (ch === ".") {
                    dots++
                    if (dots > 1) {
                        return false
                    }
                    i++
                    continue
                }
                val digitMatch = ch.match(UTSRegExp("[0-9]", ""))
                if (digitMatch == null) {
                    return false
                }
                i++
            }
        }
        return true
    }
    private fun sortResults(results: UTSArray<RowEntry>, col: String, dir: String): Unit {
        val n = results.length
        run {
            var i: Number = 0
            while(i < n - 1){
                run {
                    var j: Number = 0
                    while(j < n - i - 1){
                        val va = this.getColVal(results[j], col)
                        val vb = this.getColVal(results[j + 1], col)
                        var shouldSwap = false
                        if (va == null && vb != null) {
                            shouldSwap = true
                        } else if (va != null && vb == null) {
                            shouldSwap = false
                        } else {
                            val na = parseFloat(this.toStr(va))
                            val nb = parseFloat(this.toStr(vb))
                            if (!isNaN(na) && !isNaN(nb)) {
                                shouldSwap = if (dir === "ASC") {
                                    na > nb
                                } else {
                                    na < nb
                                }
                            } else {
                                val sa = this.toStr(va)
                                val sb = this.toStr(vb)
                                shouldSwap = if (dir === "ASC") {
                                    sa > sb
                                } else {
                                    sa < sb
                                }
                            }
                        }
                        if (shouldSwap) {
                            val tmp = results[j]
                            results[j] = results[j + 1]
                            results[j + 1] = tmp
                        }
                        j++
                    }
                }
                i++
            }
        }
    }
    private fun matchWhere(row: RowEntry, whereClause: String, params: UTSArray<Any>): Boolean {
        val trimmed = whereClause.trim()
        if (trimmed === "1" || trimmed === "1=1") {
            return true
        }
        val parts = trimmed.split(UTSRegExp("\\s+(?:AND|and)\\s+", ""))
        var paramIdx: Number = 0
        run {
            var p: Number = 0
            while(p < parts.length){
                val part = parts[p]
                val eqMatch = part.match(UTSRegExp("(\\w+)\\s*=\\s*\\?", ""))
                if (eqMatch != null) {
                    val col = eqMatch[1] as String
                    val kVal = params[paramIdx]
                    paramIdx++
                    if (this.getColVal(row, col) != kVal) {
                        return false
                    }
                    p++
                    continue
                }
                val neqMatch = part.match(UTSRegExp("(\\w+)\\s*(!=|<>)\\s*\\?", ""))
                if (neqMatch != null) {
                    val col = neqMatch[1] as String
                    val kVal = params[paramIdx]
                    paramIdx++
                    if (this.getColVal(row, col) == kVal) {
                        return false
                    }
                    p++
                    continue
                }
                val gteMatch = part.match(UTSRegExp("(\\w+)\\s*>=\\s*\\?", ""))
                if (gteMatch != null) {
                    val col = gteMatch[1] as String
                    val kVal = params[paramIdx] as Number
                    paramIdx++
                    val rowVal = parseFloat(this.toStr(this.getColVal(row, col)))
                    if (isNaN(rowVal) || rowVal < kVal) {
                        return false
                    }
                    p++
                    continue
                }
                val gtMatch = part.match(UTSRegExp("(\\w+)\\s*>\\s*\\?", ""))
                if (gtMatch != null) {
                    val col = gtMatch[1] as String
                    val kVal = params[paramIdx] as Number
                    paramIdx++
                    val rowVal = parseFloat(this.toStr(this.getColVal(row, col)))
                    if (isNaN(rowVal) || rowVal <= kVal) {
                        return false
                    }
                    p++
                    continue
                }
                val lteMatch = part.match(UTSRegExp("(\\w+)\\s*<=\\s*\\?", ""))
                if (lteMatch != null) {
                    val col = lteMatch[1] as String
                    val kVal = params[paramIdx] as Number
                    paramIdx++
                    val rowVal = parseFloat(this.toStr(this.getColVal(row, col)))
                    if (isNaN(rowVal) || rowVal > kVal) {
                        return false
                    }
                    p++
                    continue
                }
                val ltMatch = part.match(UTSRegExp("(\\w+)\\s*<\\s*\\?", ""))
                if (ltMatch != null) {
                    val col = ltMatch[1] as String
                    val kVal = params[paramIdx] as Number
                    paramIdx++
                    val rowVal = parseFloat(this.toStr(this.getColVal(row, col)))
                    if (isNaN(rowVal) || rowVal >= kVal) {
                        return false
                    }
                    p++
                    continue
                }
                paramIdx++
                p++
            }
        }
        return true
    }
    private fun loadFromStorage(): Unit {
        try {
            val raw = uni_getStorageSync("_db_tables")
            if (raw != null && raw !== "") {
                this.tables = this.parseTableEntries(raw as String)
            }
            val idRaw = uni_getStorageSync("_db_auto_inc")
            if (idRaw != null && idRaw !== "") {
                this.autoIncrement = this.parseAutoIncEntries(idRaw as String)
            }
        }
         catch (_: Throwable) {}
    }
    private fun parseTableEntries(json: String): UTSArray<TableEntry> {
        val result: UTSArray<TableEntry> = _uA()
        val items = this.splitJsonArray(json)
        run {
            var i: Number = 0
            while(i < items.length){
                val name = this.extractJsonStrValue(items[i], "name")
                if (name == null || name.length === 0) {
                    i++
                    continue
                }
                val rowsJson = this.extractJsonRawValue(items[i], "rows")
                val rows = if (rowsJson != null) {
                    this.parseRowEntries(rowsJson)
                } else {
                    _uA()
                }
                result.push(TableEntry(name = name, rows = rows))
                i++
            }
        }
        return result
    }
    private fun parseAutoIncEntries(json: String): UTSArray<AutoIncrementEntry> {
        val result: UTSArray<AutoIncrementEntry> = _uA()
        val items = this.splitJsonArray(json)
        run {
            var i: Number = 0
            while(i < items.length){
                val name = this.extractJsonStrValue(items[i], "name")
                val value = this.extractJsonNumValue(items[i], "value")
                if (name != null && name.length > 0) {
                    result.push(AutoIncrementEntry(name = name, value = value))
                }
                i++
            }
        }
        return result
    }
    private fun parseRowEntries(json: String): UTSArray<RowEntry> {
        val result: UTSArray<RowEntry> = _uA()
        val items = this.splitJsonArray(json)
        run {
            var i: Number = 0
            while(i < items.length){
                val colsJson = this.extractJsonRawValue(items[i], "cols")
                val valsJson = this.extractJsonRawValue(items[i], "vals")
                if (colsJson == null || valsJson == null) {
                    i++
                    continue
                }
                val cols = UTSAndroid.consoleDebugError(JSON.parse(colsJson), " at database/DatabaseManager.uts:579") as UTSArray<String>
                val vals = UTSAndroid.consoleDebugError(JSON.parse(valsJson), " at database/DatabaseManager.uts:580") as UTSArray<Any>
                result.push(RowEntry(cols = cols, vals = vals))
                i++
            }
        }
        return result
    }
    private fun splitJsonArray(json: String): UTSArray<String> {
        val trimmed = json.trim()
        if (trimmed.length < 2 || trimmed.substring(0, 1) !== "[") {
            return _uA()
        }
        val content = trimmed.substring(1, trimmed.length - 1)
        val items: UTSArray<String> = _uA()
        var depth: Number = 0
        var start: Number = 0
        var inStr = false
        run {
            var i: Number = 0
            while(i < content.length){
                val ch = content.substring(i, i + 1)
                if (ch === "\"" && (i === 0 || content.substring(i - 1, i) !== "\\")) {
                    inStr = !inStr
                } else if (!inStr) {
                    if (ch === "{" || ch === "[") {
                        depth++
                    } else if (ch === "}" || ch === "]") {
                        depth--
                    } else if (ch === "," && depth === 0) {
                        items.push(content.substring(start, i))
                        start = i + 1
                    }
                }
                i++
            }
        }
        if (start < content.length) {
            val last = content.substring(start).trim()
            if (last.length > 0) {
                items.push(last)
            }
        }
        return items
    }
    private fun extractJsonStrValue(objJson: String, key: String): String? {
        val pair = this.findJsonPair(objJson, key)
        if (pair == null) {
            return null
        }
        val raw = pair.substring(pair.indexOf(":") + 1).trim()
        if (raw.length >= 2 && raw.substring(0, 1) === "\"" && raw.substring(raw.length - 1) === "\"") {
            return raw.substring(1, raw.length - 1)
        }
        return null
    }
    private fun extractJsonNumValue(objJson: String, key: String): Number {
        val pair = this.findJsonPair(objJson, key)
        if (pair == null) {
            return 0
        }
        val raw = pair.substring(pair.indexOf(":") + 1).trim()
        val n = parseFloat(raw)
        return if (!isNaN(n)) {
            n
        } else {
            0
        }
    }
    private fun extractJsonRawValue(objJson: String, key: String): String? {
        val pair = this.findJsonPair(objJson, key)
        if (pair == null) {
            return null
        }
        return pair.substring(pair.indexOf(":") + 1).trim()
    }
    private fun findJsonPair(objJson: String, key: String): String? {
        val pairs = this.extractJsonPairs(objJson)
        run {
            var i: Number = 0
            while(i < pairs.length){
                val splitted = this.splitPair(pairs[i])
                if (splitted != null && splitted.length > 0 && splitted[0] === key) {
                    return pairs[i]
                }
                i++
            }
        }
        return null
    }
    private fun saveToStorage(): Unit {
        try {
            uni_setStorageSync("_db_tables", JSON.stringify(this.tables))
            uni_setStorageSync("_db_auto_inc", JSON.stringify(this.autoIncrement))
        }
         catch (e: Throwable) {
            console.error("DB save error: " + e, " at database/DatabaseManager.uts:656")
        }
    }
    companion object {
        private var instance: MicroHabitDB? = null
        fun getInstance(): MicroHabitDB {
            if (MicroHabitDB.instance == null) {
                MicroHabitDB.instance = MicroHabitDB()
            }
            return MicroHabitDB.instance!!
        }
    }
}
val dbManager = MicroHabitDB.getInstance()
fun getSetting(key: String, defaultValue: String = ""): String {
    val row = dbManager.queryOne("SELECT value FROM user_settings WHERE key = ?", _uA(
        key
    ))
    return if (row != null) {
        (row.get("value") as String)
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
    return kVal === "1" || kVal === "true"
}
fun putSetting(key: String, value: String): Unit {
    val now = Math.floor(Date.now() / 1000)
    val existing = dbManager.queryOne("SELECT key FROM user_settings WHERE key = ?", _uA(
        key
    ))
    if (existing != null) {
        dbManager.update("user_settings", _uO("value" to value, "updated_at" to now), "key = ?", _uA(
            key
        ))
    } else {
        dbManager.insert("user_settings", _uO("key" to key, "value" to value, "updated_at" to now))
    }
}
fun putInt(key: String, value: Number): Unit {
    putSetting(key, "" + value)
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
fun getHour(timestamp: Number): Number {
    return Date(timestamp).getHours()
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
    if (enabled.length === 0) {
        return null
    }
    val candidates = enabled.filter(fun(a): Boolean {
        return isSuitableForContext(a, triggerType)
    }
    )
    if (candidates.length === 0) {
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
val NORMAL_INTERVAL_MS: Number = 3600000
val NEWBIE_INTERVAL_MS: Number = 7200000
fun isNewbie(): Boolean {
    val newbieMode = getInt("newbie_mode", 1)
    if (newbieMode === 0) {
        return false
    }
    val startDate = getInt("newbie_start_date", 0)
    if (startDate === 0) {
        return false
    }
    val daysSince = Math.floor((Date.now() - startDate) / 86400000)
    return daysSince < 3
}
fun getMinTriggerIntervalMs(): Number {
    return if (isNewbie()) {
        NEWBIE_INTERVAL_MS
    } else {
        NORMAL_INTERVAL_MS
    }
}
fun getNewbieTriggerLevel(originalLevel: String): String {
    return "gentle"
}
fun insertTriggerLog(log: TriggerLog): Number {
    return dbManager.insert("trigger_logs", _uO("trigger_type" to log.trigger_type, "trigger_level" to log.trigger_level, "app_package" to log.app_package, "app_category" to log.app_category, "continuous_minutes" to log.continuous_minutes, "action_id" to log.action_id, "user_action" to log.user_action, "created_at" to log.created_at))
}
fun countTriggersByDateAndActionIds(date: String, actionIds: UTSArray<String>): Number {
    val start = getTimestampFromDate(date)
    val end = start + 86400000
    val ids = actionIds.map(fun(id: String): String {
        return "'" + id + "'"
    }
    ).join(",")
    val row = dbManager.queryOne("SELECT COUNT(*) as cnt FROM trigger_logs WHERE created_at >= ? AND created_at < ? AND action_id IN (" + ids + ")", _uA(
        start,
        end
    ))
    return if (row != null) {
        (row.get("cnt") as Number)
    } else {
        0
    }
}
fun getTimestampFromDate(date: String): Number {
    val parts = date.split("-")
    return Date(parseInt(parts[0] as String), parseInt(parts[1] as String) - 1, parseInt(parts[2] as String)).getTime()
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ActionLog", "models/ActionLog.uts", 3, 13)
    }
}
fun insertActionLog(log: ActionLog): Number {
    return dbManager.insert("action_logs", _uO("action_id" to log.action_id, "action_type" to log.action_type, "result" to log.result, "skip_reason" to log.skip_reason, "trigger_type" to log.trigger_type, "trigger_level" to log.trigger_level, "duration_ms" to log.duration_ms, "target_ms" to log.target_ms, "triggered_at" to log.triggered_at, "completed_at" to log.completed_at, "created_at" to log.created_at))
}
fun getTodayLogs(): UTSArray<ActionLog> {
    val start = getDayStartTimestamp()
    val end = start + 86400000
    val rows = dbManager.query("SELECT * FROM action_logs WHERE created_at >= ? AND created_at < ? ORDER BY created_at DESC", _uA(
        start,
        end
    ))
    return rows.map(fun(r: Map<String, Any>): ActionLog {
        return mapRow(r)
    }
    )
}
fun getTodayCompletedLogs(): UTSArray<ActionLog> {
    val start = getDayStartTimestamp()
    val end = start + 86400000
    val rows = dbManager.query("SELECT * FROM action_logs WHERE created_at >= ? AND created_at < ? AND result IN (?,?) ORDER BY created_at DESC", _uA(
        start,
        end,
        "completed",
        "self_reported"
    ))
    return rows.map(fun(r: Map<String, Any>): ActionLog {
        return mapRow(r)
    }
    )
}
fun getCompletedLogsByDateAndActions(date: String, actionIds: UTSArray<String>): UTSArray<ActionLog> {
    val start = getTimestampFromDate__1(date)
    val end = start + 86400000
    val ids = actionIds.map(fun(id: String): String {
        return "'" + id + "'"
    }
    ).join(",")
    val rows = dbManager.query("SELECT * FROM action_logs WHERE created_at >= ? AND created_at < ? AND result IN (?,?) AND action_id IN (" + ids + ") ORDER BY created_at DESC", _uA(
        start,
        end,
        "completed",
        "self_reported"
    ))
    return rows.map(fun(r: Map<String, Any>): ActionLog {
        return mapRow(r)
    }
    )
}
fun getTotalCompletedDurationSec(date: String): Number {
    val start = getTimestampFromDate__1(date)
    val end = start + 86400000
    val row = dbManager.queryOne("SELECT COALESCE(SUM(duration_ms), 0) as total FROM action_logs WHERE created_at >= ? AND created_at < ? AND result IN (?,?)", _uA(
        start,
        end,
        "completed",
        "self_reported"
    ))
    return if (row != null) {
        Math.floor((row.get("total") as Number) / 1000)
    } else {
        0
    }
}
fun countCompletedToday(): Number {
    val start = getDayStartTimestamp()
    val end = start + 86400000
    val row = dbManager.queryOne("SELECT COUNT(*) as cnt FROM action_logs WHERE created_at >= ? AND created_at < ? AND result IN (?,?)", _uA(
        start,
        end,
        "completed",
        "self_reported"
    ))
    return if (row != null) {
        (row.get("cnt") as Number)
    } else {
        0
    }
}
fun countSkippedToday(): Number {
    val start = getDayStartTimestamp()
    val end = start + 86400000
    val row = dbManager.queryOne("SELECT COUNT(*) as cnt FROM action_logs WHERE created_at >= ? AND created_at < ? AND result = ?", _uA(
        start,
        end,
        "skipped"
    ))
    return if (row != null) {
        (row.get("cnt") as Number)
    } else {
        0
    }
}
open class BarItem (
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var value: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("BarItem", "database/ActionLogDao.uts", 59, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return BarItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class BarItemReactiveObject : BarItem, IUTSReactive<BarItem> {
    override var __v_raw: BarItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: BarItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(label = __v_raw.label, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): BarItemReactiveObject {
        return BarItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var value: Number
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
fun getHourlyCompletionData(date: String): UTSArray<BarItem> {
    val result: UTSArray<BarItem> = _uA()
    run {
        var h: Number = 0
        while(h < 24){
            val label = (if (h < 10) {
                "0"
            } else {
                ""
            }
            ) + h + ":00"
            result.push(BarItem(label = label, value = 0))
            h++
        }
    }
    val rows = dbManager.query("SELECT * FROM action_logs WHERE created_at >= ? AND created_at < ? AND result IN (?,?)", _uA(
        getTimestampFromDate__1(date),
        getTimestampFromDate__1(date) + 86400000,
        "completed",
        "self_reported"
    ))
    for(row in resolveUTSValueIterator(rows)){
        val created = row.get("created_at") as Number
        val d = Date(created)
        val hour = d.getHours()
        result[hour].value = result[hour].value + 1
    }
    return result
}
fun mapRow(row: Map<String, Any>): ActionLog {
    return ActionLog(id = row.get("id") as Number, action_id = row.get("action_id") as String, action_type = row.get("action_type") as String, result = row.get("result") as ActionResult, skip_reason = row.get("skip_reason") as String?, trigger_type = row.get("trigger_type") as String, trigger_level = row.get("trigger_level") as String, duration_ms = row.get("duration_ms") as Number, target_ms = row.get("target_ms") as Number, triggered_at = row.get("triggered_at") as Number, completed_at = row.get("completed_at") as Number, created_at = row.get("created_at") as Number)
}
fun getDayStartTimestamp(): Number {
    val d = Date()
    return Date(d.getFullYear(), d.getMonth(), d.getDate()).getTime()
}
fun getTimestampFromDate__1(date: String): Number {
    val parts = date.split("-")
    return Date(parseInt(parts[0] as String), parseInt(parts[1] as String) - 1, parseInt(parts[2] as String)).getTime()
}
enum class TriggerState__1(override val value: Int) : UTSEnumInt {
    IDLE(0),
    SHOWING(1),
    EXECUTING(2),
    COMPLETING(3),
    COOLING_DOWN(4)
}
open class TriggerContext (
    @JsonNotNull
    open var appPackage: String,
    @JsonNotNull
    open var appLabel: String,
    @JsonNotNull
    open var continuousMinutes: Number,
    @JsonNotNull
    open var triggerType: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TriggerContext", "services/TriggerEngine.uts", 16, 13)
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TriggerDecision", "services/TriggerEngine.uts", 22, 13)
    }
}
var currentState: TriggerState__1 = TriggerState__1.IDLE
var lastTriggerTime: Number = 0
val COOL_DOWN_MS: Number = 300000
fun shouldTrigger(context: TriggerContext): TriggerDecision? {
    if (currentState !== TriggerState__1.IDLE) {
        return null
    }
    val nowMs = Date.now()
    if (nowMs - lastTriggerTime < COOL_DOWN_MS) {
        return null
    }
    if (isInDndPeriod()) {
        return null
    }
    if (isBlacklisted(context.appPackage)) {
        return null
    }
    if (isNewbie()) {
        val minInterval = getMinTriggerIntervalMs()
        if (nowMs - lastTriggerTime < minInterval) {
            return null
        }
    }
    if (isFrequencyReduced(context.triggerType)) {
        return null
    }
    val actionId = selectAction(context.triggerType)
    if (actionId == null) {
        return null
    }
    val action = getActionById(actionId)
    if (action == null) {
        return null
    }
    var triggerLevel = decideTriggerLevel(context)
    if (isNewbie()) {
        triggerLevel = getNewbieTriggerLevel(triggerLevel)
    }
    currentState = TriggerState__1.SHOWING
    lastTriggerTime = nowMs
    val triggerLog = TriggerLog(id = 0, trigger_type = context.triggerType, trigger_level = triggerLevel, app_package = context.appPackage, app_category = null, continuous_minutes = context.continuousMinutes, action_id = actionId, user_action = null, created_at = Math.floor(nowMs / 1000))
    insertTriggerLog(triggerLog)
    return TriggerDecision(actionId = actionId, triggerLevel = triggerLevel, triggerType = context.triggerType, durationMs = action.defaultDurationMs, ttsText = action.ttsText, actionName = action.name)
}
fun onUserAccepted(): Unit {
    currentState = TriggerState__1.EXECUTING
}
fun onActionResolved(): Unit {
    currentState = TriggerState__1.COOLING_DOWN
    scheduleCooldownEnd()
}
fun scheduleCooldownEnd(): Unit {
    scheduleTimer(fun(): Unit {
        if (currentState === TriggerState__1.COOLING_DOWN) {
            currentState = TriggerState__1.IDLE
        }
    }
    , COOL_DOWN_MS)
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
        val list = UTSAndroid.consoleDebugError(JSON.parse(listStr), " at services/TriggerEngine.uts:121") as UTSArray<String>
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
    DefaultSetting(key = "newbie_mode", value = "1"),
    DefaultSetting(key = "guide_completed", value = "0"),
    DefaultSetting(key = "health_preference", value = "balanced"),
    DefaultSetting(key = "body_limit", value = "none")
) as UTSArray<DefaultSetting>
fun runSql(sql: String, params: UTSArray<Any> = _uA()): Unit {
    dbManager.execSql(sql, params)
}
fun onCreate(db: Any): Unit {
    createTables()
    insertDefaultSettings()
}
fun createTables(): Unit {
    runSql("CREATE TABLE IF NOT EXISTS action_logs (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    action_id TEXT NOT NULL,\n    action_type TEXT NOT NULL,\n    result TEXT NOT NULL,\n    skip_reason TEXT DEFAULT NULL,\n    trigger_type TEXT NOT NULL,\n    trigger_level TEXT NOT NULL,\n    duration_ms INTEGER NOT NULL,\n    target_ms INTEGER NOT NULL,\n    triggered_at INTEGER NOT NULL,\n    completed_at INTEGER NOT NULL,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_action_logs_date ON action_logs(created_at)")
    runSql("CREATE INDEX IF NOT EXISTS idx_action_logs_action_id ON action_logs(action_id)")
    runSql("CREATE INDEX IF NOT EXISTS idx_action_logs_result ON action_logs(result)")
    runSql("CREATE TABLE IF NOT EXISTS trigger_logs (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    trigger_type TEXT NOT NULL,\n    trigger_level TEXT NOT NULL DEFAULT 'gentle',\n    app_package TEXT DEFAULT NULL,\n    app_category TEXT DEFAULT NULL,\n    continuous_minutes INTEGER DEFAULT 0,\n    action_id TEXT DEFAULT NULL,\n    user_action TEXT DEFAULT NULL,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_trigger_logs_date ON trigger_logs(created_at)")
    runSql("CREATE INDEX IF NOT EXISTS idx_trigger_logs_type ON trigger_logs(trigger_type)")
    runSql("CREATE TABLE IF NOT EXISTS daily_summaries (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    date TEXT NOT NULL UNIQUE,\n    total_completed INTEGER NOT NULL DEFAULT 0,\n    total_skipped INTEGER NOT NULL DEFAULT 0,\n    total_duration_sec INTEGER NOT NULL DEFAULT 0,\n    eye_score REAL NOT NULL DEFAULT 0,\n    posture_score REAL NOT NULL DEFAULT 0,\n    vitality_score REAL NOT NULL DEFAULT 0,\n    penetration REAL NOT NULL DEFAULT 0,\n    phone_usage_min INTEGER NOT NULL DEFAULT 0,\n    guard_minutes INTEGER NOT NULL DEFAULT 0,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE UNIQUE INDEX IF NOT EXISTS idx_daily_summaries_date ON daily_summaries(date)")
    runSql("CREATE TABLE IF NOT EXISTS trigger_rules (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    rule_type TEXT NOT NULL,\n    trigger_type TEXT NOT NULL,\n    condition_json TEXT NOT NULL,\n    action_weights_json TEXT DEFAULT '{}',\n    enabled INTEGER NOT NULL DEFAULT 1,\n    priority INTEGER NOT NULL DEFAULT 0,\n    source TEXT DEFAULT 'local',\n    expires_at INTEGER DEFAULT NULL,\n    created_at INTEGER NOT NULL,\n    updated_at INTEGER NOT NULL\n  )")
    runSql("CREATE TABLE IF NOT EXISTS user_settings (\n    key TEXT PRIMARY KEY,\n    value TEXT NOT NULL,\n    updated_at INTEGER NOT NULL\n  )")
    runSql("CREATE TABLE IF NOT EXISTS app_usage_snapshots (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    date TEXT NOT NULL,\n    package_name TEXT NOT NULL,\n    app_label TEXT DEFAULT '',\n    total_foreground_sec INTEGER NOT NULL DEFAULT 0,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_app_usage_snapshots_date ON app_usage_snapshots(date)")
    runSql("CREATE TABLE IF NOT EXISTS tts_cache (\n    text_hash TEXT PRIMARY KEY,\n    text_content TEXT NOT NULL,\n    file_path TEXT NOT NULL,\n    file_size INTEGER NOT NULL DEFAULT 0,\n    source TEXT NOT NULL DEFAULT 'minimax',\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE TABLE IF NOT EXISTS heartbeat_logs (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    service_status TEXT NOT NULL,\n    timestamp INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_heartbeat_timestamp ON heartbeat_logs(timestamp)")
    runSql("CREATE TABLE IF NOT EXISTS llm_cache (\n    cache_key TEXT PRIMARY KEY,\n    cache_type TEXT NOT NULL,\n    response TEXT NOT NULL,\n    model TEXT NOT NULL DEFAULT '',\n    created_at INTEGER NOT NULL,\n    expires_at INTEGER NOT NULL\n  )")
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
    open var created_at: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DailySummary", "models/DailySummary.uts", 1, 13)
    }
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
fun getTodayTotalUsageMinutes(): Number {
    val date = today()
    val row = dbManager.queryOne("SELECT COALESCE(SUM(total_foreground_sec), 0) as total FROM app_usage_snapshots WHERE date = ?", _uA(
        date
    ))
    return if (row != null) {
        Math.floor((row.get("total") as Number) / 60)
    } else {
        0
    }
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
        dbManager.update("daily_summaries", _uO("total_completed" to summary.total_completed, "total_skipped" to summary.total_skipped, "total_duration_sec" to summary.total_duration_sec, "eye_score" to summary.eye_score, "posture_score" to summary.posture_score, "vitality_score" to summary.vitality_score, "penetration" to summary.penetration, "phone_usage_min" to summary.phone_usage_min, "guard_minutes" to summary.guard_minutes), "date = ?", _uA(
            summary.date
        ))
    } else {
        dbManager.insert("daily_summaries", _uO("date" to summary.date, "total_completed" to summary.total_completed, "total_skipped" to summary.total_skipped, "total_duration_sec" to summary.total_duration_sec, "eye_score" to summary.eye_score, "posture_score" to summary.posture_score, "vitality_score" to summary.vitality_score, "penetration" to summary.penetration, "phone_usage_min" to summary.phone_usage_min, "guard_minutes" to summary.guard_minutes, "created_at" to Math.floor(Date.now() / 1000)))
    }
}
fun getRecent28Days(): UTSArray<DailyCount> {
    val startDate = daysAgo(28)
    val rows = dbManager.query("SELECT date, total_completed as count FROM daily_summaries WHERE date >= ? ORDER BY date ASC", _uA(
        startDate
    ))
    return rows.map(fun(row: Map<String, Any>): DailyCount {
        return (DailyCount(date = row.get("date") as String, count = row.get("count") as Number))
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
fun calculateThreeStatus(date: String): ThreeStatus {
    val eyeScore = calcCategoryScore(date, EYE_ACTION_IDS)
    val postureScore = calcCategoryScore(date, POSTURE_ACTION_IDS)
    val vitalityScore = calcCategoryScore(date, VITALITY_ACTION_IDS)
    return ThreeStatus(eyeScore = eyeScore, eyeLevel = getStatusLevel(eyeScore), postureScore = postureScore, postureLevel = getStatusLevel(postureScore), vitalityScore = vitalityScore, vitalityLevel = getStatusLevel(vitalityScore))
}
fun calcCategoryScore(date: String, actionIds: UTSArray<String>): Number {
    val logs = getCompletedLogsByDateAndActions(date, actionIds)
    if (logs.length === 0) {
        return -1
    }
    var totalDurationSec: Number = 0
    for(log in resolveUTSValueIterator(logs)){
        totalDurationSec += Math.floor(log.duration_ms / 1000)
    }
    val triggerCount = countTriggersByDateAndActionIds(date, actionIds)
    val avgDurationSec = calcAvgDuration(actionIds)
    if (triggerCount === 0 || avgDurationSec === 0) {
        return -1
    }
    val idealTotalSec = triggerCount * avgDurationSec
    val score = (totalDurationSec / idealTotalSec) * 100
    return Math.min(score, 100)
}
fun calcAvgDuration(actionIds: UTSArray<String>): Number {
    var totalDefaultMs: Number = 0
    for(id in resolveUTSValueIterator(actionIds)){
        val action = getActionById(id)
        if (action != null) {
            totalDefaultMs += action.defaultDurationMs
        }
    }
    if (actionIds.length === 0) {
        return 0
    }
    return Math.floor(totalDefaultMs / actionIds.length / 1000)
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
    val totalActionSec = getTotalCompletedDurationSec(date)
    val phoneUsageMin = getTodayTotalUsageMinutes()
    if (phoneUsageMin === 0) {
        return 0
    }
    return (totalActionSec / 60 / phoneUsageMin) * 1000
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
    open var guardMinutes: Number,
    @JsonNotNull
    open var penetration: Number,
    @JsonNotNull
    open var todayCompletedCount: Number,
    @JsonNotNull
    open var heatmapData: UTSArray<DailyCount>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("HomepageData", "services/ScoreCalculator.uts", 68, 13)
    }
}
fun getHomepageData(date: String): HomepageData {
    val threeStatus = calculateThreeStatus(date)
    val guardMinutes = Math.floor(getTotalCompletedDurationSec(date) / 60)
    val penetration = calcPenetration(date)
    val todayCompletedCount = countCompletedToday()
    val heatmapData = getRecent28Days()
    return HomepageData(eyeScore = threeStatus.eyeScore, eyeLevel = threeStatus.eyeLevel, postureScore = threeStatus.postureScore, postureLevel = threeStatus.postureLevel, vitalityScore = threeStatus.vitalityScore, vitalityLevel = threeStatus.vitalityLevel, guardMinutes = guardMinutes, penetration = penetration, todayCompletedCount = todayCompletedCount, heatmapData = heatmapData)
}
fun getHourlyData(date: String): UTSArray<BarItem> {
    return getHourlyCompletionData(date)
}
open class WeekScoreRow (
    @JsonNotNull
    open var date: String,
    @JsonNotNull
    open var eye: Number,
    @JsonNotNull
    open var posture: Number,
    @JsonNotNull
    open var vitality: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("WeekScoreRow", "services/ScoreCalculator.uts", 102, 6)
    }
}
fun getWeeklyScores(): UTSArray<WeekScoreRow> {
    val result: UTSArray<WeekScoreRow> = _uA()
    run {
        var i: Number = 6
        while(i >= 0){
            val d = daysAgo(i)
            val ts = calculateThreeStatus(d)
            result.push(WeekScoreRow(date = d, eye = Math.max(0, Math.round(ts.eyeScore)), posture = Math.max(0, Math.round(ts.postureScore)), vitality = Math.max(0, Math.round(ts.vitalityScore))))
            i--
        }
    }
    return result
}
open class LinePoint (
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var value: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LinePoint", "services/ScoreCalculator.uts", 122, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LinePointReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class LinePointReactiveObject : LinePoint, IUTSReactive<LinePoint> {
    override var __v_raw: LinePoint
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LinePoint, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(label = __v_raw.label, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LinePointReactiveObject {
        return LinePointReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var value: Number
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
open class LineSeries (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var color: String,
    @JsonNotNull
    open var points: UTSArray<LinePoint>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LineSeries", "services/ScoreCalculator.uts", 126, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LineSeriesReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class LineSeriesReactiveObject : LineSeries, IUTSReactive<LineSeries> {
    override var __v_raw: LineSeries
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LineSeries, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(name = __v_raw.name, color = __v_raw.color, points = __v_raw.points) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LineSeriesReactiveObject {
        return LineSeriesReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var points: UTSArray<LinePoint>
        get() {
            return _tRG(__v_raw, "points", __v_raw.points, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("points")) {
                return
            }
            val oldValue = __v_raw.points
            __v_raw.points = value
            _tRS(__v_raw, "points", oldValue, value)
        }
}
fun getWeeklyTrend(): UTSArray<LineSeries> {
    val scores = getWeeklyScores()
    val eyePoints: UTSArray<LinePoint> = _uA()
    val posturePoints: UTSArray<LinePoint> = _uA()
    val vitalityPoints: UTSArray<LinePoint> = _uA()
    for(s in resolveUTSValueIterator(scores)){
        val parts = s.date.split("-")
        val label = parts[1] + "/" + parts[2]
        eyePoints.push(LinePoint(label = label, value = s.eye))
        posturePoints.push(LinePoint(label = label, value = s.posture))
        vitalityPoints.push(LinePoint(label = label, value = s.vitality))
    }
    return _uA(
        LineSeries(name = "护眼", color = "#2ECC71", points = eyePoints),
        LineSeries(name = "体态", color = "#3498DB", points = posturePoints),
        LineSeries(name = "活力", color = "#F39C12", points = vitalityPoints)
    )
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
        return UTSSourceMapPosition("ActionDetail", "services/ScoreCalculator.uts", 149, 13)
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
    insertOrUpdateSummary(DailySummary(id = 0, date = date, total_completed = countCompletedToday(), total_skipped = countSkippedToday(), total_duration_sec = getTotalCompletedDurationSec(date), eye_score = Math.max(0, Math.round(threeStatus.eyeScore)), posture_score = Math.max(0, Math.round(threeStatus.postureScore)), vitality_score = Math.max(0, Math.round(threeStatus.vitalityScore)), penetration = calcPenetration(date), phone_usage_min = getTodayTotalUsageMinutes(), guard_minutes = Math.floor(getTotalCompletedDurationSec(date) / 60), created_at = Math.floor(Date.now() / 1000)))
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
    try {
        run {
            var i: Number = 0
            while(i < times){
                uni_vibrateShort(VibrateShortOptions(type = "medium"))
                i++
            }
        }
    }
     catch (_: Throwable) {}
}
var ttsInstance: TextToSpeech? = null
var ttsReady: Boolean = false
var pendingText: String = ""
fun speakSystemTts(text: String): Unit {
    if (ttsInstance != null && ttsReady) {
        ttsInstance!!!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "micro_habit_tts")
    } else {
        if (ttsInstance == null) {
            initTts()
        }
        pendingText = text
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
                if (ttsReady && !pendingText.isEmpty()) {
                    ttsInstance!!!!.speak(pendingText, TextToSpeech.QUEUE_FLUSH, null, "micro_habit_tts")
                    pendingText = ""
                }
            }
        }
        )
    }
     catch (_: Throwable) {}
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
        return UTSSourceMapPosition("AppForegroundInfo", "uni_modules/uts-accessibility-service/utssdk/app-android/AppMonitorService.uts", 9, 13)
    }
}
open class MonitorCallbacks (
    open var onAppDurationTrigger: (info: AppForegroundInfo) -> Unit,
    open var onAppSwitch: (fromPackage: String, toPackage: String) -> Unit,
    open var onTimePeriodTrigger: (periodName: String) -> Unit,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MonitorCallbacks", "uni_modules/uts-accessibility-service/utssdk/app-android/AppMonitorService.uts", 14, 13)
    }
}
var service: AppMonitorService? = null
var cachedCallbacks: MonitorCallbacks? = null
fun startMonitorService(callbacks: MonitorCallbacks): Unit {
    cachedCallbacks = callbacks
    true
    if (service != null) {
        service!!!!.setCallbacks(callbacks)
    }
}
fun isAccessibilityServiceEnabled(): Boolean {
    return service != null && service!!!!.isConnected()
}
fun openAccessibilitySettings(): Unit {
    try {
        val ctx = UTSAndroid.getAppContext()
        if (ctx != null) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ctx.startActivity(intent)
        }
    }
     catch (_: Throwable) {}
}
open class AppMonitorService : AccessibilityService, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AppMonitorService", "uni_modules/uts-accessibility-service/utssdk/app-android/AppMonitorService.uts", 58, 7)
    }
    private var pkg: String = ""
    private var startTime: Number = 0
    private var callbacks: MonitorCallbacks? = null
    private var handler: Handler? = null
    private var connected: Boolean = false
    private var elapsed: Number = 0
    constructor() : super() {}
    open fun setCallbacks(cbs: MonitorCallbacks?): Unit {
        this.callbacks = cbs
    }
    open fun getInfo(): AppForegroundInfo? {
        if (this.pkg.isEmpty()) {
            return null
        }
        val info = AppForegroundInfo(packageName = this.pkg, startTime = this.startTime, continuousMs = this.elapsed)
        return info
    }
    open fun getMs(): Number {
        return this.elapsed
    }
    open fun isConnected(): Boolean {
        return this.connected
    }
    override fun onAccessibilityEvent(event: AccessibilityEvent): Unit {
        if (event.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            return
        }
        val pkgName = event.packageName
        if (pkgName == null) {
            return
        }
        val pkgStr = pkgName.toString()
        if (pkgStr.isEmpty() || pkgStr == "android") {
            return
        }
        if (pkgStr == this.pkg) {
            return
        }
        val now = System.currentTimeMillis()
        if (!this.pkg.isEmpty()) {
            val cbs = this.callbacks
            if (cbs != null) {
                cbs.onAppSwitch(this.pkg, pkgStr)
            }
        }
        this.pkg = pkgStr
        this.startTime = now
        this.startCheck()
    }
    override fun onServiceConnected(): Unit {
        super.onServiceConnected()
        this.connected = true
        val info = AccessibilityServiceInfo()
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS or AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
        info.notificationTimeout = 100
        this.setServiceInfo(info)
        service = this
        if (cachedCallbacks != null) {
            this.callbacks = cachedCallbacks
        }
    }
    override fun onInterrupt(): Unit {
        this.connected = false
        this.stopCheck()
    }
    override fun onDestroy(): Unit {
        this.connected = false
        this.stopCheck()
        super.onDestroy()
    }
    private fun startCheck(): Unit {
        this.stopCheck()
        this.handler = Handler(Looper.getMainLooper())
        val self = this
        this.handler!!!!.postDelayed(fun(): Unit {
            if (self.pkg.isEmpty()) {
                return
            }
            self.elapsed = System.currentTimeMillis() - self.startTime
            val cbs = self.callbacks
            if (cbs != null) {
                val info = AppForegroundInfo(packageName = self.pkg, startTime = self.startTime, continuousMs = self.elapsed)
                cbs.onAppDurationTrigger(info)
            }
            self.startCheck()
        }
        , 1000)
    }
    private fun stopCheck(): Unit {
        if (this.handler != null) {
            this.handler!!.removeCallbacksAndMessages(null)
            this.handler = null
        }
    }
}
typealias TriggerLevel = String
open class OverlayConfig (
    @JsonNotNull
    open var level: TriggerLevel,
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
        if (this.rootView != null && this.wm != null) {
            try {
                this.wm!!.removeView(this.rootView!!)
            }
             catch (_: Throwable) {}
        }
        this.rootView = null
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
            this.handler!!.removeCallbacksAndMessages(null)
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
            var triggeredActionId: String = ""
            onLaunch(fun(_options){
                try {
                    dbManager.init()
                    onCreate(dbManager)
                    val callbacks = MonitorCallbacks(onAppDurationTrigger = fun(info: AppForegroundInfo): Unit {
                        val threshold = getInt("app_duration_threshold", 60) * 1000
                        if (info.continuousMs < threshold) {
                            return
                        }
                        try {
                            val decision = shouldTrigger(TriggerContext(appPackage = info.packageName, appLabel = info.packageName, continuousMinutes = Math.floor(info.continuousMs / 60000), triggerType = "app_duration"))
                            if (decision != null) {
                                triggeredActionId = decision.actionId
                                val actionForEncourage = getActionById(triggeredActionId)
                                val encourageText = if (actionForEncourage != null) {
                                    getRandomEncourage(actionForEncourage.category)
                                } else {
                                    "又完成一次！"
                                }
                                val cfg = OverlayConfig(level = decision.triggerLevel as String, actionName = decision.actionName, ttsText = decision.ttsText, durationMs = decision.durationMs, lottieAssetPath = null, encourageText = encourageText)
                                val cbs = OverlayCallbacks(onAgree = fun(): Unit {
                                    onUserAccepted()
                                    dismissOverlay()
                                    val aid = triggeredActionId
                                    uni_navigateTo(NavigateToOptions(url = "/pages/action/execute?actionId=" + aid))
                                }
                                , onSelfReported = fun(): Unit {
                                    val action = getActionById(triggeredActionId)
                                    if (action != null) {
                                        insertActionLog(ActionLog(id = 0, action_id = action.id, action_type = action.category, result = "self_reported", skip_reason = null, trigger_type = "app_duration", trigger_level = decision.triggerLevel, duration_ms = 0, target_ms = action.defaultDurationMs, triggered_at = Math.floor(Date.now() / 1000), completed_at = Math.floor(Date.now() / 1000), created_at = Math.floor(Date.now() / 1000)))
                                    }
                                    onActionResolved()
                                }
                                , onBusyRemindLater = fun(): Unit {
                                    val action = getActionById(triggeredActionId)
                                    if (action != null) {
                                        insertActionLog(ActionLog(id = 0, action_id = action.id, action_type = action.category, result = "skipped", skip_reason = "busy", trigger_type = "app_duration", trigger_level = decision.triggerLevel, duration_ms = 0, target_ms = action.defaultDurationMs, triggered_at = Math.floor(Date.now() / 1000), completed_at = Math.floor(Date.now() / 1000), created_at = Math.floor(Date.now() / 1000)))
                                    }
                                    onActionResolved()
                                }
                                , onSkipDuringExec = fun(): Unit {
                                    onActionResolved()
                                }
                                , onCountdownTick = fun(remaining: Number): Unit {}, onCountdownFinish = fun(): Unit {
                                    shortVibrate(2)
                                    speakSystemTts(encourageText)
                                    val action = getActionById(triggeredActionId)
                                    if (action != null) {
                                        insertActionLog(ActionLog(id = 0, action_id = action.id, action_type = action.category, result = "completed", skip_reason = null, trigger_type = "app_duration", trigger_level = decision.triggerLevel, duration_ms = action.defaultDurationMs, target_ms = action.defaultDurationMs, triggered_at = Math.floor(Date.now() / 1000), completed_at = Math.floor(Date.now() / 1000), created_at = Math.floor(Date.now() / 1000)))
                                    }
                                    onActionResolved()
                                }
                                , onPartialCompletion = fun(completed: Number, total: Number): Unit {})
                                showOverlay(cfg, cbs)
                            }
                        }
                         catch (_: Throwable) {}
                    }
                    , onAppSwitch = fun(from: String, to: String): Unit {}, onTimePeriodTrigger = fun(name: String): Unit {})
                    startMonitorService(callbacks)
                }
                 catch (e: Throwable) {
                    console.error("App init error: " + JSON.stringify(e), " at App.uvue:131")
                }
                console.log("App Launch", " at App.uvue:133")
            }
            )
            onAppShow(fun(_options){
                try {
                    saveTodaySummary()
                }
                 catch (_: Throwable) {}
                console.log("App Show", " at App.uvue:138")
            }
            )
            onAppHide(fun(){
                console.log("App Hide", " at App.uvue:142")
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
                dbManager.close()
                console.log("App Exit", " at App.uvue:160")
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
    if (enabledActions.length === 0) {
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
    return mapRow__1(row)
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
fun mapRow__1(row: Map<String, Any>): LlmCache {
    return LlmCache(cache_key = row.get("cache_key") as String, cache_type = row.get("cache_type") as String, response = row.get("response") as String, model = row.get("model") as String, created_at = row.get("created_at") as Number, expires_at = row.get("expires_at") as Number)
}
fun isNetworkAvailable(): Boolean {
    return true
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
    open var title: String,
    @JsonNotNull
    open var highlight: String,
    @JsonNotNull
    open var improvement: String,
    @JsonNotNull
    open var suggestions: UTSArray<String>,
    @JsonNotNull
    open var nextWeekGoal: String,
    @JsonNotNull
    open var tone: String,
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
    constructor(__v_raw: WeeklyReport, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(title = __v_raw.title, highlight = __v_raw.highlight, improvement = __v_raw.improvement, suggestions = __v_raw.suggestions, nextWeekGoal = __v_raw.nextWeekGoal, tone = __v_raw.tone, stats = __v_raw.stats) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): WeeklyReportReactiveObject {
        return WeeklyReportReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var title: String
        get() {
            return _tRG(__v_raw, "title", __v_raw.title, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("title")) {
                return
            }
            val oldValue = __v_raw.title
            __v_raw.title = value
            _tRS(__v_raw, "title", oldValue, value)
        }
    override var highlight: String
        get() {
            return _tRG(__v_raw, "highlight", __v_raw.highlight, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("highlight")) {
                return
            }
            val oldValue = __v_raw.highlight
            __v_raw.highlight = value
            _tRS(__v_raw, "highlight", oldValue, value)
        }
    override var improvement: String
        get() {
            return _tRG(__v_raw, "improvement", __v_raw.improvement, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("improvement")) {
                return
            }
            val oldValue = __v_raw.improvement
            __v_raw.improvement = value
            _tRS(__v_raw, "improvement", oldValue, value)
        }
    override var suggestions: UTSArray<String>
        get() {
            return _tRG(__v_raw, "suggestions", __v_raw.suggestions, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("suggestions")) {
                return
            }
            val oldValue = __v_raw.suggestions
            __v_raw.suggestions = value
            _tRS(__v_raw, "suggestions", oldValue, value)
        }
    override var nextWeekGoal: String
        get() {
            return _tRG(__v_raw, "nextWeekGoal", __v_raw.nextWeekGoal, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("nextWeekGoal")) {
                return
            }
            val oldValue = __v_raw.nextWeekGoal
            __v_raw.nextWeekGoal = value
            _tRS(__v_raw, "nextWeekGoal", oldValue, value)
        }
    override var tone: String
        get() {
            return _tRG(__v_raw, "tone", __v_raw.tone, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("tone")) {
                return
            }
            val oldValue = __v_raw.tone
            __v_raw.tone = value
            _tRS(__v_raw, "tone", oldValue, value)
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
open class TriggerRule (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var rule_type: String,
    @JsonNotNull
    open var trigger_type: String,
    @JsonNotNull
    open var condition_json: String,
    @JsonNotNull
    open var action_weights_json: String,
    @JsonNotNull
    open var enabled: Number,
    @JsonNotNull
    open var priority: Number,
    @JsonNotNull
    open var source: String,
    open var expires_at: Number? = null,
    @JsonNotNull
    open var created_at: Number,
    @JsonNotNull
    open var updated_at: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TriggerRule", "models/TriggerRule.uts", 1, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TriggerRuleReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TriggerRuleReactiveObject : TriggerRule, IUTSReactive<TriggerRule> {
    override var __v_raw: TriggerRule
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TriggerRule, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, rule_type = __v_raw.rule_type, trigger_type = __v_raw.trigger_type, condition_json = __v_raw.condition_json, action_weights_json = __v_raw.action_weights_json, enabled = __v_raw.enabled, priority = __v_raw.priority, source = __v_raw.source, expires_at = __v_raw.expires_at, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TriggerRuleReactiveObject {
        return TriggerRuleReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var rule_type: String
        get() {
            return _tRG(__v_raw, "rule_type", __v_raw.rule_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("rule_type")) {
                return
            }
            val oldValue = __v_raw.rule_type
            __v_raw.rule_type = value
            _tRS(__v_raw, "rule_type", oldValue, value)
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
    override var condition_json: String
        get() {
            return _tRG(__v_raw, "condition_json", __v_raw.condition_json, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("condition_json")) {
                return
            }
            val oldValue = __v_raw.condition_json
            __v_raw.condition_json = value
            _tRS(__v_raw, "condition_json", oldValue, value)
        }
    override var action_weights_json: String
        get() {
            return _tRG(__v_raw, "action_weights_json", __v_raw.action_weights_json, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("action_weights_json")) {
                return
            }
            val oldValue = __v_raw.action_weights_json
            __v_raw.action_weights_json = value
            _tRS(__v_raw, "action_weights_json", oldValue, value)
        }
    override var enabled: Number
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
    override var expires_at: Number?
        get() {
            return _tRG(__v_raw, "expires_at", __v_raw.expires_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("expires_at")) {
                return
            }
            val oldValue = __v_raw.expires_at
            __v_raw.expires_at = value
            _tRS(__v_raw, "expires_at", oldValue, value)
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
    override var updated_at: Number
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
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
        return UTSSourceMapPosition("DailyData", "services/CloudService.uts", 1, 13)
    }
}
open class CloudResponse (
    @JsonNotNull
    open var code: Number,
    @JsonNotNull
    open var data: Any,
    open var error: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CloudResponse", "services/CloudService.uts", 14, 6)
    }
}
fun callDaily(date: String, data: DailyData, onOK: (result: Any) -> Unit, onFail: () -> Unit): Unit {
    callCloudFn("llm-daily", _uO("date" to date, "data" to _uO("totalCompleted" to data.totalCompleted, "totalSkipped" to data.totalSkipped, "totalDurationSec" to data.totalDurationSec, "guardMinutes" to data.guardMinutes, "penetration" to data.penetration, "eyeScore" to data.eyeScore, "postureScore" to data.postureScore, "vitalityScore" to data.vitalityScore)), onOK, onFail)
}
fun callWeekly(data: WeeklyReportStats, onOK: (result: Any) -> Unit, onFail: () -> Unit): Unit {
    callCloudFn("llm-weekly", _uO("data" to _uO("totalCompleted" to data.totalCompleted, "totalDurationSec" to data.totalDurationSec, "avgPenetration" to data.avgPenetration, "avgGuardMinutes" to data.avgGuardMinutes, "bestDay" to data.bestDay, "bestDayCount" to data.bestDayCount, "worstDay" to data.worstDay, "worstDayCount" to data.worstDayCount)), onOK, onFail)
}
fun callRuleSuggest(data: Any, onOK: (result: Any) -> Unit): Unit {
    val r = Math.random()
    if (r < 0.15) {
        val rule = TriggerRule(id = 0, rule_type = "frequency", trigger_type = "app_duration", condition_json = "{}", action_weights_json = "{}", enabled = 1, priority = 1, source = "ai_suggest", expires_at = null, created_at = Math.floor(Date.now() / 1000), updated_at = Math.floor(Date.now() / 1000))
        onOK(_uO("shouldSuggest" to true, "rule" to rule))
    } else {
        onOK(_uO("shouldSuggest" to false, "rule" to null))
    }
}
fun callCloudFn(name: String, payload: Any, onOK: (result: Any) -> Unit, onFail: () -> Unit): Unit {
    try {
        val payloadStr = JSON.stringify(payload)
        uni_request<Any>(RequestOptions(url = "https://fc-mp-xxx.bspapp.com/" + name, method = "POST", header = _uO("Content-Type" to "application/json"), data = payloadStr, timeout = 15000, success = fun(res) {
            try {
                val dataJson = JSON.stringify(res.data)
                val obj = UTSAndroid.consoleDebugError(JSON.parse(dataJson), " at services/CloudService.uts:94") as CloudResponse
                if (obj.code === 0 && obj.data != null) {
                    onOK(obj.data)
                } else {
                    onFail()
                }
            }
             catch (_: Throwable) {
                onFail()
            }
        }
        , fail = fun(err) {
            onFail()
        }
        ))
    }
     catch (_: Throwable) {
        onFail()
    }
}
open class WeeklyLLMOutput (
    @JsonNotNull
    open var title: String,
    @JsonNotNull
    open var highlight: String,
    @JsonNotNull
    open var improvement: String,
    @JsonNotNull
    open var suggestions: UTSArray<String>,
    @JsonNotNull
    open var nextWeekGoal: String,
    @JsonNotNull
    open var tone: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("WeeklyLLMOutput", "services/WeeklyReportService.uts", 6, 6)
    }
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
            totalDurationSec += data.guardMinutes * 60
            totalPenetration += data.penetration
            totalGuardMinutes += data.guardMinutes
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
    if (daysWithData === 0) {
        return WeeklyReportStats(totalCompleted = 0, totalDurationSec = 0, avgPenetration = 0, avgGuardMinutes = 0, bestDay = "", bestDayCount = 0, worstDay = "", worstDayCount = 0)
    }
    return WeeklyReportStats(totalCompleted = totalCompleted, totalDurationSec = totalDurationSec, avgPenetration = Math.round(totalPenetration / 7 * 100) / 100, avgGuardMinutes = Math.round(totalGuardMinutes / 7), bestDay = bestDay, bestDayCount = bestDayCount, worstDay = worstDay, worstDayCount = worstDayCount)
}
fun generateWeeklyReport(onComplete: (report: WeeklyReport) -> Unit): Unit {
    val stats = generateWeeklyData()
    fun buildLocal(): WeeklyReport {
        val hasData = stats.totalCompleted > 0
        if (hasData) {
            return WeeklyReport(title = "本周健康小结", highlight = "本周完成了" + stats.totalCompleted + "次微动作，累计守护" + stats.totalDurationSec + "秒", improvement = "下周可以尝试更多样化的动作组合", suggestions = _uA(
                "尝试每天固定时间做一个微动作",
                "关注完成率最低的动作类型",
                "给自己设定一个小奖励"
            ), nextWeekGoal = "每天至少完成3次微动作", tone = if (stats.avgGuardMinutes >= 10) {
                "great"
            } else {
                "okay"
            }
            , stats = stats)
        }
        return WeeklyReport(title = "本周健康报告", highlight = "本周暂无数据，从今天开始吧", improvement = "", suggestions = _uA(
            "完成第一次微动作",
            "开启无障碍服务以自动提醒",
            "设置你的健康偏好"
        ), nextWeekGoal = "开始你的第一个微动作", tone = "needs_care", stats = stats)
    }
    if (!isNetworkAvailable()) {
        onComplete(buildLocal())
        return
    }
    callWeekly(stats, fun(result: Any){
        try {
            val jsonStr = JSON.stringify(result)
            val obj = UTSAndroid.consoleDebugError(JSON.parse(jsonStr), " at services/WeeklyReportService.uts:102") as WeeklyLLMOutput
            if (obj.title != null && obj.title.length > 0) {
                onComplete(WeeklyReport(title = obj.title, highlight = obj.highlight, improvement = obj.improvement, suggestions = obj.suggestions, nextWeekGoal = obj.nextWeekGoal, tone = obj.tone, stats = stats))
                return
            }
        }
         catch (_: Throwable) {}
        onComplete(buildLocal())
    }
    , fun(){
        onComplete(buildLocal())
    }
    )
}
open class DailyOutput (
    @JsonNotNull
    open var one_liner: String,
    @JsonNotNull
    open var summary: String,
    @JsonNotNull
    open var tomorrow_goal: String,
    @JsonNotNull
    open var encourage: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DailyOutput", "stores/appStore.uts", 12, 13)
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
    constructor(__v_raw: DailyOutput, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(one_liner = __v_raw.one_liner, summary = __v_raw.summary, tomorrow_goal = __v_raw.tomorrow_goal, encourage = __v_raw.encourage) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): DailyOutputReactiveObject {
        return DailyOutputReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var one_liner: String
        get() {
            return _tRG(__v_raw, "one_liner", __v_raw.one_liner, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("one_liner")) {
                return
            }
            val oldValue = __v_raw.one_liner
            __v_raw.one_liner = value
            _tRS(__v_raw, "one_liner", oldValue, value)
        }
    override var summary: String
        get() {
            return _tRG(__v_raw, "summary", __v_raw.summary, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("summary")) {
                return
            }
            val oldValue = __v_raw.summary
            __v_raw.summary = value
            _tRS(__v_raw, "summary", oldValue, value)
        }
    override var tomorrow_goal: String
        get() {
            return _tRG(__v_raw, "tomorrow_goal", __v_raw.tomorrow_goal, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("tomorrow_goal")) {
                return
            }
            val oldValue = __v_raw.tomorrow_goal
            __v_raw.tomorrow_goal = value
            _tRS(__v_raw, "tomorrow_goal", oldValue, value)
        }
    override var encourage: String
        get() {
            return _tRG(__v_raw, "encourage", __v_raw.encourage, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("encourage")) {
                return
            }
            val oldValue = __v_raw.encourage
            __v_raw.encourage = value
            _tRS(__v_raw, "encourage", oldValue, value)
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
    open var guardMinutes: Ref<Number>,
    @JsonNotNull
    open var penetration: Ref<Number>,
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
    open var refreshHomeData: () -> Unit,
    open var refreshWeeklyReport: () -> Unit,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AppStore", "stores/appStore.uts", 18, 13)
    }
}
val eyeScore = ref<Number>(0)
val eyeStatus = ref<String>("no_data")
val postureScore = ref<Number>(0)
val postureStatus = ref<String>("no_data")
val vitalityScore = ref<Number>(0)
val vitalityStatus = ref<String>("no_data")
val guardMinutes = ref<Number>(0)
val penetration = ref<Number>(0)
val todayCompletedCount = ref<Number>(0)
@JvmField
val recommendedActions = ref(_uA<MicroAction>())
val heatmapData = ref(_uA<DailyCount>())
val dailySummary = ref<DailyOutput?>(null)
val dailySummaryLoading = ref<Boolean>(false)
val homeDataLoaded = ref<Boolean>(false)
val weeklyReport = ref<WeeklyReport?>(null)
val weeklyReportLoading = ref<Boolean>(false)
fun useAppStore(): AppStore {
    val store = AppStore(eyeScore = eyeScore, eyeStatus = eyeStatus, postureScore = postureScore, postureStatus = postureStatus, vitalityScore = vitalityScore, vitalityStatus = vitalityStatus, guardMinutes = guardMinutes, penetration = penetration, todayCompletedCount = todayCompletedCount, recommendedActions = recommendedActions, heatmapData = heatmapData, dailySummary = dailySummary, dailySummaryLoading = dailySummaryLoading, homeDataLoaded = homeDataLoaded, weeklyReport = weeklyReport, weeklyReportLoading = weeklyReportLoading, refreshHomeData = fun(): Unit {
        homeDataLoaded.value = false
        try {
            val date = today()
            val nowMs = Date.now()
            val summaryHour = parseSummaryHour(getSetting("daily_summary_time", "21:00"))
            val data = getHomepageData(date)
            eyeScore.value = Math.round(data.eyeScore)
            eyeStatus.value = data.eyeLevel
            postureScore.value = Math.round(data.postureScore)
            postureStatus.value = data.postureLevel
            vitalityScore.value = Math.round(data.vitalityScore)
            vitalityStatus.value = data.vitalityLevel
            guardMinutes.value = data.guardMinutes
            penetration.value = Math.round(data.penetration * 100) / 100
            todayCompletedCount.value = data.todayCompletedCount
            heatmapData.value = data.heatmapData
            val todayKey = "daily_summary:" + date
            val todayCache = getByKey(todayKey)
            if (todayCache != null) {
                try {
                    val parsed = UTSAndroid.consoleDebugError(JSON.parse(todayCache.response), " at stores/appStore.uts:93")
                    dailySummary.value = parsed as DailyOutput
                } catch (_: Throwable) {}
                dailySummaryLoading.value = false
            } else if (getHour(nowMs) >= summaryHour) {
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
                val fallback = DailyOutput(one_liner = "昨天完成" + ydata.todayCompletedCount + "次微动作", summary = "累计" + ydata.guardMinutes + "分钟", tomorrow_goal = "", encourage = "")
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
fun parseSummaryHour(setting: String): Number {
    if (setting == null || setting.length === 0) {
        return 21
    }
    val sep = setting.indexOf(":")
    val hourStr = if (sep > 0) {
        setting.substring(0, sep)
    } else {
        setting
    }
    val h = parseInt(hourStr)
    if (isNaN(h) || h < 0 || h > 23) {
        return 21
    }
    return h
}
fun buildLocalSummary(completedCount: Number, minutes: Number): DailyOutput {
    return DailyOutput(one_liner = "今日完成" + completedCount + "次微动作", summary = "守护" + minutes + "分钟，继续加油", tomorrow_goal = "继续保持！", encourage = "你做得很好")
}
fun generateDailySummary(date: String, onComplete: (summary: DailyOutput) -> Unit): Unit {
    val data = getHomepageData(date)
    if (!isNetworkAvailable()) {
        onComplete(buildLocalSummary(data.todayCompletedCount, data.guardMinutes))
        return
    }
    val dto = DailyData(totalCompleted = data.todayCompletedCount, totalSkipped = 0, totalDurationSec = data.guardMinutes * 60, guardMinutes = data.guardMinutes, penetration = data.penetration, eyeScore = Math.max(0, Math.round(data.eyeScore)), postureScore = Math.max(0, Math.round(data.postureScore)), vitalityScore = Math.max(0, Math.round(data.vitalityScore)), todayCompletedCount = data.todayCompletedCount)
    callDaily(date, dto, fun(result: Any){
        try {
            val jsonStr = JSON.stringify(result)
            val obj = UTSAndroid.consoleDebugError(JSON.parse(jsonStr), " at stores/appStore.uts:175") as DailyOutput
            if (obj.one_liner != null) {
                onComplete(obj)
                return
            }
        }
         catch (_: Throwable) {}
        onComplete(buildLocalSummary(data.todayCompletedCount, data.guardMinutes))
    }
    , fun(){
        onComplete(buildLocalSummary(data.todayCompletedCount, data.guardMinutes))
    }
    )
}
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
fun insertRule(rule: TriggerRule): Number {
    return dbManager.insert("trigger_rules", _uO("rule_type" to rule.rule_type, "trigger_type" to rule.trigger_type, "condition_json" to rule.condition_json, "action_weights_json" to rule.action_weights_json, "enabled" to rule.enabled, "priority" to rule.priority, "source" to rule.source, "expires_at" to rule.expires_at, "created_at" to rule.created_at, "updated_at" to rule.updated_at))
}
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
open class BarItem__1 (
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var value: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("BarItem", "components/BarChart.uvue", 8, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return BarItem__1ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class BarItem__1ReactiveObject : BarItem__1, IUTSReactive<BarItem__1> {
    override var __v_raw: BarItem__1
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: BarItem__1, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(label = __v_raw.label, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): BarItem__1ReactiveObject {
        return BarItem__1ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var value: Number
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
open class LinePoint__1 (
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var value: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LinePoint", "components/LineChart.uvue", 8, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LinePoint__1ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class LinePoint__1ReactiveObject : LinePoint__1, IUTSReactive<LinePoint__1> {
    override var __v_raw: LinePoint__1
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LinePoint__1, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(label = __v_raw.label, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LinePoint__1ReactiveObject {
        return LinePoint__1ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var value: Number
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
open class LineSeries__1 (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var color: String,
    @JsonNotNull
    open var points: UTSArray<LinePoint__1>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LineSeries", "components/LineChart.uvue", 13, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LineSeries__1ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class LineSeries__1ReactiveObject : LineSeries__1, IUTSReactive<LineSeries__1> {
    override var __v_raw: LineSeries__1
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LineSeries__1, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(name = __v_raw.name, color = __v_raw.color, points = __v_raw.points) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LineSeries__1ReactiveObject {
        return LineSeries__1ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var points: UTSArray<LinePoint__1>
        get() {
            return _tRG(__v_raw, "points", __v_raw.points, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("points")) {
                return
            }
            val oldValue = __v_raw.points
            __v_raw.points = value
            _tRS(__v_raw, "points", oldValue, value)
        }
}
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
open class DailyCount__1 (
    @JsonNotNull
    open var date: String,
    @JsonNotNull
    open var count: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DailyCount", "components/HeatmapCalendar.uvue", 8, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return DailyCount__1ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class DailyCount__1ReactiveObject : DailyCount__1, IUTSReactive<DailyCount__1> {
    override var __v_raw: DailyCount__1
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: DailyCount__1, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(date = __v_raw.date, count = __v_raw.count) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): DailyCount__1ReactiveObject {
        return DailyCount__1ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
open class CellRect (
    @JsonNotNull
    open var x: Number,
    @JsonNotNull
    open var y: Number,
    @JsonNotNull
    open var w: Number,
    @JsonNotNull
    open var h: Number,
    @JsonNotNull
    open var day: DailyCount__1,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CellRect", "components/HeatmapCalendar.uvue", 13, 6)
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
    override var versionName: String = "1.0.0"
    override var versionCode: String = "100"
    override var uniCompilerVersion: String = "5.11"
    constructor() : super() {}
}
fun definePageRoutes() {
    __uniRoutes.push(UniPageRoute(path = "pages/guide/index", component = GenPagesGuideIndexClass, meta = UniPageMeta(isQuit = true), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/home/index", component = GenPagesHomeIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/action/execute", component = GenPagesActionExecuteClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/data/dashboard", component = GenPagesDataDashboardClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "数据看板")))
    __uniRoutes.push(UniPageRoute(path = "pages/settings/index", component = GenPagesSettingsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "设置")))
}
val __uniTabBar: Map<String, Any?>? = _uM("list" to _uA(
    _uM("pagePath" to "pages/home/index", "text" to "首页"),
    _uM("pagePath" to "pages/data/dashboard", "text" to "数据"),
    _uM("pagePath" to "pages/settings/index", "text" to "我的")
))
val __uniLaunchPage: Map<String, Any?> = _uM("url" to "pages/guide/index", "style" to _uM("navigationStyle" to "custom", "navigationBarTitleText" to ""))
fun defineAppConfig() {
    __uniConfig.entryPagePath = "/pages/guide/index"
    __uniConfig.globalStyle = _uM("navigationBarTextStyle" to "black", "navigationBarTitleText" to "", "navigationBarBackgroundColor" to "#F8F8F8", "backgroundColor" to "#F8F8F8")
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
        return UTSSourceMapPosition("UniCloudConfig", "main.uts", 47, 14)
    }
    override var isDev: Boolean = true
    override var spaceList: String = "[{\"provider\":\"aliyun\",\"spaceName\":\"trial-w7onvxzrxrp2h087561\",\"spaceId\":\"mp-76d188c8-df1a-4535-9cee-3bb94b3a694d\",\"clientSecret\":\"b94cO0f6Qv3WwtDVDIwHFw==\",\"endpoint\":\"https://api.next.bspapp.com\"}]"
    override var debuggerInfo: String? = "{\"address\":[\"127.0.0.1\",\"192.168.3.87\"],\"servePort\":7000,\"debugPort\":9000,\"initialLaunchType\":\"local\",\"skipFiles\":[\"<node_internals>/**\",\"D:/Program Files (x86)/HBuilderX/plugins/unicloud/**/*.js\"]}"
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
