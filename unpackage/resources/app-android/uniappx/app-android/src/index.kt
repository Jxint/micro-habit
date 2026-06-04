@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNIB805CE2
import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteStatement
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
import io.dcloud.uniapp.extapi.`$emit` as uni__emit
import io.dcloud.uniapp.extapi.`$on` as uni__on
import android.view.WindowManager.LayoutParams as WindowManagerLayoutParams
import io.dcloud.uniapp.extapi.exit as uni_exit
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.vibrateShort as uni_vibrateShort
val runBlock1 = run {
    __uniConfig.getAppStyles = fun(): Map<String, Map<String, Map<String, Any>>> {
        return GenApp.styles
    }
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
) : UTSReactiveObject() {
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
) : UTSObject()
open class MicroHabitSqliteHelper : SQLiteOpenHelper {
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
open class SqliteStore {
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
        if (params.length.toInt() === 0) {
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
        if (n.toInt() === 0) {
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
        if (n.toInt() === 0) {
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
) : UTSObject()
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
    DefaultSetting(key = "body_limit", value = "none"),
    DefaultSetting(key = "llm_trigger_enabled", value = "1"),
    DefaultSetting(key = "llm_trigger_ask_each_time", value = "1")
) as UTSArray<DefaultSetting>
fun runSql(sql: String, params: UTSArray<Any> = _uA()): Unit {
    sqliteStore.execSql(sql, params)
}
fun onCreate(): Unit {
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
    runSql("CREATE TABLE IF NOT EXISTS daily_summaries (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    date TEXT NOT NULL UNIQUE,\n    total_completed INTEGER NOT NULL DEFAULT 0,\n    total_skipped INTEGER NOT NULL DEFAULT 0,\n    total_duration_sec INTEGER NOT NULL DEFAULT 0,\n    eye_score REAL NOT NULL DEFAULT 0,\n    posture_score REAL NOT NULL DEFAULT 0,\n    vitality_score REAL NOT NULL DEFAULT 0,\n    penetration REAL NOT NULL DEFAULT 0,\n    phone_usage_min INTEGER NOT NULL DEFAULT 0,\n    guard_minutes INTEGER NOT NULL DEFAULT 0,\n    guard_count INTEGER NOT NULL DEFAULT 0,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE UNIQUE INDEX IF NOT EXISTS idx_daily_summaries_date ON daily_summaries(date)")
    runSql("CREATE TABLE IF NOT EXISTS trigger_rules (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    rule_type TEXT NOT NULL,\n    trigger_type TEXT NOT NULL,\n    condition_json TEXT NOT NULL,\n    action_weights_json TEXT DEFAULT '{}',\n    enabled INTEGER NOT NULL DEFAULT 1,\n    priority INTEGER NOT NULL DEFAULT 0,\n    source TEXT DEFAULT 'local',\n    expires_at INTEGER DEFAULT NULL,\n    created_at INTEGER NOT NULL,\n    updated_at INTEGER NOT NULL\n  )")
    runSql("CREATE TABLE IF NOT EXISTS user_settings (\n    key TEXT PRIMARY KEY,\n    value TEXT NOT NULL,\n    updated_at INTEGER NOT NULL\n  )")
    runSql("CREATE TABLE IF NOT EXISTS app_usage_snapshots (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    date TEXT NOT NULL,\n    package_name TEXT NOT NULL,\n    app_label TEXT DEFAULT '',\n    total_foreground_sec INTEGER NOT NULL DEFAULT 0,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_app_usage_snapshots_date ON app_usage_snapshots(date)")
    runSql("CREATE TABLE IF NOT EXISTS tts_cache (\n    text_hash TEXT PRIMARY KEY,\n    text_content TEXT NOT NULL,\n    file_path TEXT NOT NULL,\n    file_size INTEGER NOT NULL DEFAULT 0,\n    source TEXT NOT NULL DEFAULT 'minimax',\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE TABLE IF NOT EXISTS heartbeat_logs (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    service_status TEXT NOT NULL,\n    timestamp INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_heartbeat_timestamp ON heartbeat_logs(timestamp)")
    runSql("CREATE TABLE IF NOT EXISTS llm_cache (\n    cache_key TEXT PRIMARY KEY,\n    cache_type TEXT NOT NULL,\n    response TEXT NOT NULL,\n    model TEXT NOT NULL DEFAULT '',\n    created_at INTEGER NOT NULL,\n    expires_at INTEGER NOT NULL\n  )")
    runSql("CREATE TABLE IF NOT EXISTS llm_history (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    stage TEXT NOT NULL,\n    context_json TEXT NOT NULL,\n    ai_raw_response TEXT NOT NULL,\n    parsed_result_json TEXT DEFAULT '{}',\n    adhoc_text TEXT DEFAULT NULL,\n    suggested_rule_json TEXT DEFAULT NULL,\n    reasoning TEXT DEFAULT NULL,\n    created_at INTEGER NOT NULL\n  )")
    runSql("CREATE INDEX IF NOT EXISTS idx_llm_history_date ON llm_history(created_at)")
    runSql("CREATE INDEX IF NOT EXISTS idx_llm_history_stage ON llm_history(stage)")
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
    open var guard_count: Number,
    @JsonNotNull
    open var created_at: Number,
) : UTSObject()
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
) : UTSObject()
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
) : UTSObject()
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
) : UTSReactiveObject() {
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
open class DbManagerShim {
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
    return v as Number
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
open class BarItem (
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var value: Number,
) : UTSObject()
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
        getTimestampFromDate(date),
        getTimestampFromDate(date) + 86400,
        "completed",
        "self_reported"
    ))
    for(row in resolveUTSValueIterator(rows)){
        val created = getNum(row, "created_at")
        val d = Date(created)
        val hour = d.getHours()
        result[hour].value = result[hour].value + 1
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
) : UTSReactiveObject() {
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
) : UTSObject()
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
fun insertOrUpdateSnapshot(packageName: String, appLabel: String, foregroundSec: Number): Unit {
    val date = today()
    val existing = dbManager.queryOne("SELECT id, total_foreground_sec FROM app_usage_snapshots WHERE date = ? AND package_name = ?", _uA(
        date,
        packageName
    ))
    if (existing != null) {
        val prevSec = getNum__1(existing, "total_foreground_sec")
        val nextSec = prevSec + foregroundSec
        val id = getNum__1(existing, "id")
        val row = SqlRow(columns = _uA(
            "total_foreground_sec",
            "app_label"
        ), values = _uA(
            nextSec,
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
            foregroundSec,
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
open class DailyCount (
    @JsonNotNull
    open var date: String,
    @JsonNotNull
    open var count: Number,
) : UTSReactiveObject() {
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
fun getSummaryByDate(date: String): DailySummary? {
    val row = dbManager.queryOne("SELECT * FROM daily_summaries WHERE date = ?", _uA(
        date
    ))
    return if (row != null) {
        mapRow__2(row)
    } else {
        null
    }
}
fun mapRow__2(row: Map<String, Any>): DailySummary {
    return DailySummary(id = getNum(row, "id"), date = getStr(row, "date"), total_completed = getNum(row, "total_completed"), total_skipped = getNum(row, "total_skipped"), total_duration_sec = getNum(row, "total_duration_sec"), eye_score = getNum(row, "eye_score"), posture_score = getNum(row, "posture_score"), vitality_score = getNum(row, "vitality_score"), penetration = getNum(row, "penetration"), phone_usage_min = getNum(row, "phone_usage_min"), guard_minutes = getNum(row, "guard_minutes"), guard_count = getNum(row, "guard_count"), created_at = getNum(row, "created_at"))
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
    return kVal === "1" || kVal === "true"
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
fun getTimestampFromDate__1(date: String): Number {
    val parts = date.split("-")
    return Math.floor(Date(parseInt(parts[0] as String), parseInt(parts[1] as String) - 1, parseInt(parts[2] as String)).getTime() / 1000)
}
fun countCompletedForDate(date: String): Number {
    val start = getTimestampFromDate__1(date)
    val end = start + 86400
    val row = dbManager.queryOne("SELECT COUNT(*) as cnt FROM action_logs WHERE created_at >= ? AND created_at < ? AND result IN (?,?)", _uA(
        start,
        end,
        "completed",
        "self_reported"
    ))
    val rawCnt = if (row != null) {
        row.get("cnt")
    } else {
        null
    }
    return if (rawCnt != null) {
        parseFloat("" + rawCnt)
    } else {
        0
    }
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
) : UTSObject()
fun getHomepageData(date: String): HomepageData {
    val threeStatus = calculateThreeStatus(date)
    val guardCount = countCompletedToday()
    val penetration = calcPenetration(date)
    val todayCompletedCount = countCompletedToday()
    val heatmapData = getRecent28Days()
    val targetCount = getTargetCount()
    return HomepageData(eyeScore = threeStatus.eyeScore, eyeLevel = threeStatus.eyeLevel, postureScore = threeStatus.postureScore, postureLevel = threeStatus.postureLevel, vitalityScore = threeStatus.vitalityScore, vitalityLevel = threeStatus.vitalityLevel, guardCount = guardCount, penetration = penetration, todayCompletedCount = todayCompletedCount, heatmapData = heatmapData, targetCount = targetCount)
}
fun getHourlyData(date: String): UTSArray<BarItem> {
    return getHourlyCompletionData(date)
}
open class WeekCountRow (
    @JsonNotNull
    open var date: String,
    @JsonNotNull
    open var count: Number,
) : UTSObject()
fun getWeeklyCounts(): UTSArray<WeekCountRow> {
    val result: UTSArray<WeekCountRow> = _uA()
    run {
        var i: Number = 6
        while(i >= 0){
            val d = daysAgo(i)
            val summary = getSummaryByDate(d)
            val cnt = if (summary != null) {
                summary.total_completed
            } else {
                countCompletedForDate(d)
            }
            result.push(WeekCountRow(date = d, count = cnt))
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
) : UTSObject()
open class LineSeries (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var color: String,
    @JsonNotNull
    open var points: UTSArray<LinePoint>,
) : UTSObject()
fun getWeeklyTrend(): UTSArray<LineSeries> {
    val rows = getWeeklyCounts()
    val points: UTSArray<LinePoint> = _uA()
    for(r in resolveUTSValueIterator(rows)){
        val parts = r.date.split("-")
        val label = parts[1] + "/" + parts[2]
        points.push(LinePoint(label = label, value = r.count))
    }
    return _uA(
        LineSeries(name = "完成次数", color = "#2ECC71", points = points)
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
) : UTSReactiveObject() {
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
val DISABLED_ACTIONS_KEY = "disabled_action_ids"
fun getEnabledActions(): UTSArray<MicroAction> {
    val disabledStr = getSetting(DISABLED_ACTIONS_KEY, "[]")
    var disabledIds: UTSArray<String> = _uA()
    try {
        disabledIds = JSON.parse(disabledStr) as UTSArray<String>
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
) : UTSObject()
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
    if (enabled.length.toInt() === 0) {
        return null
    }
    val candidates = enabled.filter(fun(a): Boolean {
        return isSuitableForContext(a, triggerType)
    }
    )
    if (candidates.length.toInt() === 0) {
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
) : UTSObject()
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
) : UTSObject()
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
        val list = JSON.parse(listStr) as UTSArray<String>
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
) : UTSReactiveObject() {
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
fun mapRow__3(row: Map<String, Any>): TriggerRule {
    return TriggerRule(id = getNum(row, "id"), rule_type = getStr(row, "rule_type"), trigger_type = getStr(row, "trigger_type"), condition_json = getStr(row, "condition_json"), action_weights_json = getStr(row, "action_weights_json"), enabled = getNum(row, "enabled"), priority = getNum(row, "priority"), source = getStr(row, "source"), expires_at = if (getStrOrNull(row, "expires_at") == null) {
        null
    } else {
        getNum(row, "expires_at")
    }
    , created_at = getNum(row, "created_at"), updated_at = getNum(row, "updated_at"))
}
fun getAllEnabledRules(): UTSArray<TriggerRule> {
    val rows = dbManager.query("SELECT * FROM trigger_rules WHERE enabled = 1 ORDER BY priority DESC")
    val result: UTSArray<TriggerRule> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            result.push(mapRow__3(rows[i]))
            i++
        }
    }
    return result
}
fun insertRule(rule: TriggerRule): Number {
    val row = SqlRow(columns = _uA(
        "rule_type",
        "trigger_type",
        "condition_json",
        "action_weights_json",
        "enabled",
        "priority",
        "source",
        "expires_at",
        "created_at",
        "updated_at"
    ), values = _uA(
        rule.rule_type,
        rule.trigger_type,
        rule.condition_json,
        rule.action_weights_json,
        rule.enabled,
        rule.priority,
        rule.source,
        rule.expires_at,
        rule.created_at,
        rule.updated_at
    ))
    return dbManager.insert("trigger_rules", row)
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
) : UTSObject()
open class MonitorCallbacks (
    open var onAppDurationTrigger: (info: AppForegroundInfo) -> Unit,
    open var onAppSwitch: (fromPackage: String, toPackage: String) -> Unit,
    open var onTimePeriodTrigger: (periodName: String) -> Unit,
) : UTSObject()
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
open class AppMonitorService : AccessibilityService {
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
        info.notificationTimeout = 100.toLong()
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
        , 1000.toLong())
    }
    private fun stopCheck(): Unit {
        if (this.handler != null) {
            this.handler!!!!.removeCallbacksAndMessages(null)
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
) : UTSObject()
open class OverlayCallbacks (
    open var onAgree: () -> Unit,
    open var onSelfReported: () -> Unit,
    open var onBusyRemindLater: () -> Unit,
    open var onSkipDuringExec: () -> Unit,
    open var onCountdownTick: (remainingMs: Number) -> Unit,
    open var onCountdownFinish: () -> Unit,
    open var onPartialCompletion: (completedMs: Number, totalMs: Number) -> Unit,
) : UTSObject()
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
open class FloatingOverlayManager {
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
) : UTSReactiveObject() {
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
) : UTSReactiveObject() {
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
open class TimeWindow (
    @JsonNotNull
    open var start: String,
    @JsonNotNull
    open var end: String,
) : UTSReactiveObject() {
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
) : UTSReactiveObject() {
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
open class EffectiveTriggerRule (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var actionId: String,
    open var timeWindow: TimeWindow? = null,
    open var screenConditions: ScreenConditions? = null,
    @JsonNotNull
    open var timeThresholdMinutes: Number,
    @JsonNotNull
    open var source: String,
    @JsonNotNull
    open var enabled: Boolean = false,
    @JsonNotNull
    open var createdAt: Number,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return EffectiveTriggerRuleReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class EffectiveTriggerRuleReactiveObject : EffectiveTriggerRule, IUTSReactive<EffectiveTriggerRule> {
    override var __v_raw: EffectiveTriggerRule
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: EffectiveTriggerRule, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, actionId = __v_raw.actionId, timeWindow = __v_raw.timeWindow, screenConditions = __v_raw.screenConditions, timeThresholdMinutes = __v_raw.timeThresholdMinutes, source = __v_raw.source, enabled = __v_raw.enabled, createdAt = __v_raw.createdAt) {
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
open class PreTriggerContext (
    @JsonNotNull
    open var appLabel: String,
    @JsonNotNull
    open var appPackage: String,
    @JsonNotNull
    open var continuousMinutes: Number,
    @JsonNotNull
    open var hour: Number,
    @JsonNotNull
    open var todayCompletedCount: Number,
    @JsonNotNull
    open var guardMinutes: Number,
    @JsonNotNull
    open var recentActions: UTSArray<String>,
) : UTSObject()
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
    open var recentActions: UTSArray<String>,
    @JsonNotNull
    open var currentRules: String,
) : UTSObject()
val SYSTEM_PROMPT_EVALUATE = "你是一个微习惯健康助手，正在帮用户做碎片化健康动作。\n你的回答必须严格按 JSON 格式输出，不要有额外文字（包括 ```json``` 标记）。\n基调用温柔+轻度幽默+不油腻的中文风格。避免医学恐吓和引发焦虑的表述。\n绝对禁止读取用户隐私内容，只能使用提供的脱敏聚合数据。"
fun buildPreTriggerPrompt(context: PreTriggerContext): String {
    val recentStr = if (context.recentActions.length > 0) {
        context.recentActions.join("、")
    } else {
        "无"
    }
    return "用户当前状态：\n- 正在使用：" + context.appLabel + "\n" + "- 已连续前台：" + context.continuousMinutes + " 分钟\n" + "- 当前时间：" + context.hour + ":00\n" + "- 今日已完成微动作：" + context.todayCompletedCount + " 次\n" + "- 今日已守护：" + context.guardMinutes + " 分钟\n" + "- 最近 5 次动作：" + recentStr + "\n\n" + "请基于以上状态生成：\n" + "1. adhocText：一句 10-30 字的 TTS 即兴文案，温暖+轻度幽默，要提到当前 app 和时长\n" + "2. stateDescription：用 5-15 字概括当前状态（用于\"为什么提醒我\"展示）\n\n" + "严格按 JSON 格式输出：{\"adhocText\": \"...\", \"stateDescription\": \"...\"}"
}
fun buildPostActionPrompt(context: PostActionContext): String {
    val recentStr = if (context.recentActions.length > 0) {
        context.recentActions.join("、")
    } else {
        "无"
    }
    return "用户刚完成微动作：" + context.actionName + "（" + context.actionCategory + "，" + Math.round(context.durationMs / 1000) + "秒）\n" + "原触发上下文：" + context.originalContext + "\n" + "今日累计：" + context.todayCompletedCount + " 次，" + context.guardMinutes + " 分钟\n" + "最近 5 次：" + recentStr + "\n" + "当前生效的触发规则：" + context.currentRules + "\n\n" + "请评估：\n" + "1. 用户的健康习惯是否稳定？是否需要调整触发频率或类型？\n" + "2. 是否需要新增一条更个性化的触发规则？\n\n" + "如果要新增规则，输出 JSON：\n" + "{\"suggestedRule\": {\"actionId\": \"eye_blink\", \"timeWindow\": {\"start\": \"22:00\", \"end\": \"07:00\"}, \"screenConditions\": {\"appPackages\": [], \"includeHome\": true}, \"timeThresholdMinutes\": 60}, \"reasoning\": \"...\"}\n\n" + "如果不需要新规则，输出：\n" + "{\"suggestedRule\": null, \"reasoning\": \"当前规则合理，继续观察\"}"
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
val SYSTEM_PROMPT_DAILY = "你是一个微习惯健康助手，负责生成每日健康小结。\n你的回答必须严格按 JSON 格式输出，不要有额外文字（包括 ```json``` 标记）。\n风格：温柔+轻度幽默+不油腻的中文。避免医学恐吓和引发焦虑的表述。\n绝对禁止读取用户隐私内容，只能使用提供的脱敏聚合数据。"
fun buildDailySummaryPrompt(data: DailyData): String {
    return "以下是用户今日（截止当前）的健康微动作数据：\n- 今日完成微动作：" + data.todayCompletedCount + " 次\n" + "- 今日跳过：" + data.totalSkipped + " 次\n" + "- 累计守护时长：" + data.totalDurationSec + " 秒\n" + "- 护眼评分：" + data.eyeScore + " / 100\n" + "- 体态评分：" + data.postureScore + " / 100\n" + "- 活力评分：" + data.vitalityScore + " / 100\n" + "- 穿透率：" + Math.round(data.penetration * 100) + "%\n\n" + "请生成今日健康小结，严格按 JSON 格式输出：\n" + "{\"one_liner\": \"一句话总结（15字以内）\", \"summary\": \"详细小结（50-100字）\", \"tomorrow_goal\": \"明日小目标（15字以内）\", \"encourage\": \"一句鼓励（15字以内）\"}"
}
val SYSTEM_PROMPT_WEEKLY = "你是一个微习惯健康助手，负责生成每周健康报告。\n你的回答必须严格按 JSON 格式输出，不要有额外文字（包括 ```json``` 标记）。\n风格：温暖+鼓励+不油腻的中文。避免医学恐吓和引发焦虑的表述。\n绝对禁止读取用户隐私内容，只能使用提供的脱敏聚合数据。"
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
    ) + "\n\n" + "请生成每周健康报告，严格按 JSON 格式输出：\n" + "{\"title\": \"报告标题（10字以内）\", \"highlight\": \"本周亮点（30字以内）\", \"improvement\": \"改进建议（30字以内）\", \"suggestions\": [\"建议1\", \"建议2\", \"建议3\"], \"nextWeekGoal\": \"下周目标（15字以内）\", \"tone\": \"great/okay/needs_care（三选一）\"}"
}
val MINIMAX_BASE_URL = "https://api.minimax.chat/v1"
val MINIMAX_CHAT_PATH = "text/chatcompletion_v2"
val MINIMAX_CHAT_MODEL = "MiniMax-M2.7"
val MINIMAX_API_KEY = "sk-cp-Dd2_yFv0hINLsc2fnuWrrde0agYZQCzaRzW0DaK64CAl7Y-plAqRd7R2jSxQTSgSEWbl6teJc2oFL90HPMgyqLXel67OJhufLeLeVauln93DyXmA1277iQw"
open class MinimaxBaseResp (
    open var status_code: Number? = null,
    open var status_msg: String? = null,
) : UTSObject()
open class MinimaxMessage (
    open var content: String? = null,
    open var reasoning_content: String? = null,
) : UTSObject()
open class MinimaxChoice (
    open var message: MinimaxMessage? = null,
) : UTSObject()
open class MinimaxChatResp (
    open var base_resp: MinimaxBaseResp? = null,
    open var choices: UTSArray<MinimaxChoice>? = null,
) : UTSObject()
fun callMinimaxChat(systemPrompt: String, userPrompt: String, maxTokens: Number, onOK: (raw: String) -> Unit, onFail: () -> Unit): Unit {
    if (MINIMAX_API_KEY === "PLACEHOLDER_KEY") {
        console.warn("[CloudService] MiniMax API key 未配置，走降级路径")
        onFail()
        return
    }
    val reqBody: UTSJSONObject = _uO("model" to MINIMAX_CHAT_MODEL, "messages" to _uA(
        _uO("role" to "system", "content" to systemPrompt, "name" to "MiniMax AI"),
        _uO("role" to "user", "content" to userPrompt, "name" to "用户")
    ), "temperature" to 0.7, "max_completion_tokens" to maxTokens, "reasoning_effort" to "low")
    try {
        uni_request<Any>(RequestOptions(url = MINIMAX_BASE_URL + "/" + MINIMAX_CHAT_PATH, method = "POST", header = _uO("Content-Type" to "application/json", "Authorization" to ("Bearer " + MINIMAX_API_KEY)), data = JSON.stringify(reqBody), timeout = 15000, success = fun(res) {
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
                console.log("[CloudService] callMinimaxChat response, finishReason=" + finishReason)
                val content = extractChatContent(if (data != null) {
                    data
                } else {
                    ""
                }
                )
                if (content.length > 0) {
                    onOK(content)
                } else {
                    console.warn("[CloudService] callMinimaxChat empty content, raw data=" + JSON.stringify(data))
                    onFail()
                }
            }
             catch (_: Throwable) {
                onFail()
            }
        }
        , fail = fun(_err) {
            console.warn("[CloudService] callMinimaxChat network fail: " + JSON.stringify(_err))
            onFail()
        }
        ))
    }
     catch (_: Throwable) {
        onFail()
    }
}
fun callDaily(date: String, data: DailyData, onOK: (result: Any) -> Unit, onFail: () -> Unit): Unit {
    console.log("[CloudService] callDaily start, date=" + date)
    val userPrompt = buildDailySummaryPrompt(data)
    callMinimaxChat(SYSTEM_PROMPT_DAILY, userPrompt, 500, fun(raw: String): Unit {
        try {
            val obj = parseJsonThreeLevels(raw)
            if (obj != null) {
                console.log("[CloudService] callDaily OK, raw.length=" + raw.length)
                onOK(obj)
                return
            }
            console.warn("[CloudService] callDaily parse failed, raw=" + raw)
        }
         catch (e: Throwable) {
            console.warn("[CloudService] callDaily parse exception: " + JSON.stringify(e))
        }
        onFail()
    }
    , fun(){
        console.warn("[CloudService] callDaily failed, fallback to local")
        onFail()
    }
    )
}
fun callWeekly(data: WeeklyReportStats, onOK: (result: Any) -> Unit, onFail: () -> Unit): Unit {
    console.log("[CloudService] callWeekly start, totalCompleted=" + data.totalCompleted)
    val userPrompt = buildWeeklyReportPrompt(data)
    callMinimaxChat(SYSTEM_PROMPT_WEEKLY, userPrompt, 600, fun(raw: String): Unit {
        try {
            val obj = parseJsonThreeLevels(raw)
            if (obj != null) {
                console.log("[CloudService] callWeekly OK, raw.length=" + raw.length)
                onOK(obj)
                return
            }
            console.warn("[CloudService] callWeekly parse failed, raw=" + raw)
        }
         catch (e: Throwable) {
            console.warn("[CloudService] callWeekly parse exception: " + JSON.stringify(e))
        }
        onFail()
    }
    , fun(){
        console.warn("[CloudService] callWeekly failed, fallback to local")
        onFail()
    }
    )
}
fun callLlmEvaluate(stage: String, contextJson: String, onOK: (raw: String) -> Unit, onFail: () -> Unit): Unit {
    var userPrompt = ""
    try {
        if (stage === "pre") {
            val ctx = JSON.parse(contextJson) as PreTriggerContext
            userPrompt = buildPreTriggerPrompt(ctx)
        } else {
            val ctx = JSON.parse(contextJson) as PostActionContext
            userPrompt = buildPostActionPrompt(ctx)
        }
    }
     catch (_: Throwable) {
        onFail()
        return
    }
    callMinimaxChat(SYSTEM_PROMPT_EVALUATE, userPrompt, 500, onOK, onFail)
}
fun extractChatContent(data: Any): String {
    try {
        val dataStr = JSON.stringify(data)
        val obj = JSON.parse(dataStr) as MinimaxChatResp
        if (obj.base_resp != null && obj.base_resp!!.status_code != null && obj.base_resp!!.status_code != 0) {
            console.warn("[CloudService] MiniMax 返回非 0 状态: " + (obj.base_resp!!.status_msg ?: ""))
            return ""
        }
        if (obj.choices != null && obj.choices!!.length > 0) {
            val first = obj.choices!![0]
            if (first != null && first.message != null) {
                val msg = first.message!!
                if (msg.content != null) {
                    val c = msg.content!!
                    if (UTSAndroid.`typeof`(c) === "string" && c.length > 0) {
                        return c
                    }
                }
                if (msg.reasoning_content != null) {
                    val rc = msg.reasoning_content!!
                    if (UTSAndroid.`typeof`(rc) === "string" && rc.length > 0) {
                        return rc
                    }
                }
            }
        }
    }
     catch (_: Throwable) {}
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
fun parseJsonThreeLevels(raw: String): UTSJSONObject? {
    try {
        return JSON.parse(raw) as UTSJSONObject
    }
     catch (_: Throwable) {}
    try {
        val match = raw.match(UTSRegExp("\\{[\\s\\S]*\\}", ""))
        if (match != null) {
            val m = match[0]
            if (m != null && m.length > 0) {
                return JSON.parse(m) as UTSJSONObject
            }
        }
    }
     catch (_: Throwable) {}
    return null
}
typealias LlmHistoryStage = String
open class ParsedLlmResult (
    open var adhocText: String? = null,
    open var stateDescription: String? = null,
    open var suggestedRule: EffectiveTriggerRule? = null,
    open var reasoning: String? = null,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ParsedLlmResultReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ParsedLlmResultReactiveObject : ParsedLlmResult, IUTSReactive<ParsedLlmResult> {
    override var __v_raw: ParsedLlmResult
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ParsedLlmResult, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(adhocText = __v_raw.adhocText, stateDescription = __v_raw.stateDescription, suggestedRule = __v_raw.suggestedRule, reasoning = __v_raw.reasoning) {
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
    @JsonNotNull
    open var createdAt: Number,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LlmHistoryEntryReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class LlmHistoryEntryReactiveObject : LlmHistoryEntry, IUTSReactive<LlmHistoryEntry> {
    override var __v_raw: LlmHistoryEntry
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LlmHistoryEntry, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, stage = __v_raw.stage, contextJson = __v_raw.contextJson, aiRawResponse = __v_raw.aiRawResponse, parsedResult = __v_raw.parsedResult, adhocText = __v_raw.adhocText, suggestedRule = __v_raw.suggestedRule, reasoning = __v_raw.reasoning, createdAt = __v_raw.createdAt) {
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
fun insertHistory(stage: LlmHistoryStage, contextJson: String, aiRawResponse: String, parsedResult: ParsedLlmResult, adhocText: String?, suggestedRule: EffectiveTriggerRule?, reasoning: String?): Number {
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
        "created_at"
    ), values = _uA(
        stage,
        contextJson,
        aiRawResponse,
        parsedJson,
        adhocText,
        ruleJson,
        reasoning,
        createdAt
    ))
    return dbManager.insert("llm_history", row)
}
fun getAllHistory(limit: Number): UTSArray<LlmHistoryEntry> {
    val rows = dbManager.query("SELECT * FROM llm_history ORDER BY created_at DESC LIMIT ?", _uA(
        limit
    ))
    return mapRows(rows)
}
fun mapRows(rows: UTSArray<Map<String, Any>>): UTSArray<LlmHistoryEntry> {
    val result: UTSArray<LlmHistoryEntry> = _uA()
    run {
        var i: Number = 0
        while(i < rows.length){
            val r = rows[i]
            if (r != null) {
                result.push(mapRow__4(r))
            }
            i++
        }
    }
    return result
}
fun mapRow__4(row: Map<String, Any>): LlmHistoryEntry {
    val parsedJson = getStr(row, "parsed_result_json")
    val ruleJson = getStrOrNull(row, "suggested_rule_json")
    var parsed = ParsedLlmResult()
    if (parsedJson.length > 0) {
        try {
            parsed = JSON.parse(parsedJson) as ParsedLlmResult
        }
         catch (_: Throwable) {
            parsed = ParsedLlmResult()
        }
    }
    var rule: EffectiveTriggerRule? = null
    if (ruleJson != null && ruleJson.length > 0) {
        try {
            rule = JSON.parse(ruleJson) as EffectiveTriggerRule
        }
         catch (_: Throwable) {
            rule = null
        }
    }
    return LlmHistoryEntry(id = getNum(row, "id"), stage = getStr(row, "stage") as LlmHistoryStage, contextJson = getStr(row, "context_json"), aiRawResponse = getStr(row, "ai_raw_response"), parsedResult = parsed, adhocText = getStrOrNull(row, "adhoc_text"), suggestedRule = rule, reasoning = getStrOrNull(row, "reasoning"), createdAt = getNum(row, "created_at"))
}
open class PreTriggerResult (
    @JsonNotNull
    open var adhocText: String,
    @JsonNotNull
    open var stateDescription: String,
    @JsonNotNull
    open var fallback: Boolean = false,
) : UTSObject()
open class PostActionResult (
    open var suggestedRule: EffectiveTriggerRule? = null,
    @JsonNotNull
    open var reasoning: String,
    @JsonNotNull
    open var fallback: Boolean = false,
) : UTSObject()
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
fun evaluatePre(onReady: (result: PreTriggerResult) -> Unit, onFail: () -> Unit): Unit {
    val ctx = preContextCache
    if (ctx == null) {
        onFail()
        return
    }
    val contextJson = JSON.stringify(ctx)
    try {
        callLlmEvaluate("pre", contextJson, fun(raw: String): Unit {
            try {
                val parsed = parsePreResponse(raw)
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
                    console.warn("[LlmTriggerFlow] 写 pre history 失败: " + JSON.stringify(e))
                }
                val result = PreTriggerResult(adhocText = adhoc, stateDescription = stateDesc, fallback = false)
                onReady(result)
            }
             catch (e: Throwable) {
                console.warn("[LlmTriggerFlow] pre parse 失败: " + JSON.stringify(e))
                val fallback: PreTriggerResult = buildFallbackPre()
                try {
                    insertHistory("pre", contextJson, raw, ParsedLlmResult(), fallback.adhocText, null, fallback.stateDescription)
                }
                 catch (_: Throwable) {}
                onReady(fallback)
            }
        }
        , fun(): Unit {
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
        console.warn("[LlmTriggerFlow] pre 异常: " + JSON.stringify(e))
        onFail()
    }
}
fun evaluatePost(actionId: String, actionName: String, actionCategory: String, durationMs: Number, onReady: (result: PostActionResult) -> Unit, onFail: () -> Unit): Unit {
    if (postContextCache == null) {
        onFail()
        return
    }
    val ctx = postContextCache!!!!
    val enriched = PostActionContext(actionId = actionId, actionName = actionName, actionCategory = actionCategory, durationMs = durationMs, originalContext = ctx.originalContext, todayCompletedCount = ctx.todayCompletedCount, guardMinutes = ctx.guardMinutes, recentActions = ctx.recentActions, currentRules = ctx.currentRules)
    val contextJson = JSON.stringify(enriched)
    try {
        callLlmEvaluate("post", contextJson, fun(raw: String): Unit {
            try {
                val parsed = parsePostResponse(raw)
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
                try {
                    insertHistory("post", contextJson, raw, parsed, null, rule, reasoning)
                }
                 catch (e: Throwable) {
                    console.warn("[LlmTriggerFlow] 写 post history 失败: " + JSON.stringify(e))
                }
                val result = PostActionResult(suggestedRule = rule, reasoning = reasoning, fallback = false)
                onReady(result)
            }
             catch (e: Throwable) {
                console.warn("[LlmTriggerFlow] post parse 失败: " + JSON.stringify(e))
                try {
                    insertHistory("post", contextJson, raw, ParsedLlmResult(), null, null, "")
                }
                 catch (_: Throwable) {}
                val result = PostActionResult(suggestedRule = null, reasoning = "", fallback = true)
                onReady(result)
            }
        }
        , fun(): Unit {
            try {
                insertHistory("post", contextJson, "", ParsedLlmResult(), null, null, "")
            }
             catch (_: Throwable) {}
            val result = PostActionResult(suggestedRule = null, reasoning = "", fallback = true)
            onReady(result)
        }
        )
    }
     catch (e: Throwable) {
        console.warn("[LlmTriggerFlow] post 异常: " + JSON.stringify(e))
        onFail()
    }
}
fun buildFallbackPre(): PreTriggerResult {
    return PreTriggerResult(adhocText = getFallbackAdhoc(), stateDescription = "检测到需要休息", fallback = true)
}
fun parsePreResponse(raw: String): ParsedLlmResult {
    val obj = parseJsonThreeLevels__1(raw)
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
    val obj = parseJsonThreeLevels__1(raw)
    val result = ParsedLlmResult()
    if (obj == null) {
        return result
    }
    val reasoning = obj["reasoning"]
    if (UTSAndroid.`typeof`(reasoning) === "string" && (reasoning as String).length > 0) {
        result.reasoning = reasoning as String
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
        val includeHome = scObj["includeHome"] === true
        val appPackagesRaw = scObj["appPackages"]
        val pkgs: UTSArray<String> = _uA()
        if (appPackagesRaw != null) {
            try {
                val arrStr = JSON.stringify(appPackagesRaw)
                val arr = JSON.parse(arrStr) as UTSArray<Any>
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
    var threshold: Number = 0
    val thresholdRaw = raw["timeThresholdMinutes"]
    if (UTSAndroid.`typeof`(thresholdRaw) === "number") {
        val t = thresholdRaw as Number
        threshold = t.toInt()
    }
    val rule = EffectiveTriggerRule(id = 0, actionId = actionIdStr, timeWindow = timeWindow, screenConditions = screenConditions, timeThresholdMinutes = threshold, source = "llm", enabled = true, createdAt = Math.floor(Date.now() / 1000))
    return rule
}
fun parseJsonThreeLevels__1(raw: String): UTSJSONObject? {
    try {
        return JSON.parse(raw) as UTSJSONObject
    }
     catch (_: Throwable) {}
    try {
        val match = raw.match(UTSRegExp("\\{[\\s\\S]*\\}", ""))
        if (match != null) {
            val m = match[0]
            if (m != null && m.length > 0) {
                return JSON.parse(m) as UTSJSONObject
            }
        }
    }
     catch (_: Throwable) {}
    return null
}
fun speakAdhoc(text: String): Unit {
    if (isSystemMuted()) {
        return
    }
    if (text.length < 1) {
        return
    }
    try {
        speakSystemTts(text)
    }
     catch (e: Throwable) {
        console.warn("[TtsService] speakAdhoc plugin 调用失败: " + JSON.stringify(e))
    }
}
fun isSystemMuted(): Boolean {
    return !getBool("tts_enabled", true)
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
            fun gen_safeNowHour_fn(): Number {
                try {
                    return currentHour()
                }
                 catch (_: Throwable) {
                    return 12
                }
            }
            val safeNowHour = ::gen_safeNowHour_fn
            fun gen_buildPreContext_fn(info: AppForegroundInfo, actionId: String): PreTriggerContext {
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
                val homeData = getHomepageData("")
                val guardMin = Math.floor(getTotalCompletedDurationSec("") / 60)
                return PreTriggerContext(appLabel = info.packageName, appPackage = info.packageName, continuousMinutes = Math.floor(info.continuousMs / 60000), hour = safeNowHour(), todayCompletedCount = homeData.todayCompletedCount, guardMinutes = guardMin, recentActions = names)
            }
            val buildPreContext = ::gen_buildPreContext_fn
            fun gen_buildPostContext_fn(actionName: String, actionCategory: String, durationMs: Number): PostActionContext {
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
                val homeData = getHomepageData("")
                val guardMin = Math.floor(getTotalCompletedDurationSec("") / 60)
                val rulesStr = "当前阈值 " + getInt("app_duration_threshold", 60) + " 分钟"
                return PostActionContext(actionId = pendingActionId, actionName = actionName, actionCategory = actionCategory, durationMs = durationMs, originalContext = pendingAdhocText, todayCompletedCount = homeData.todayCompletedCount, guardMinutes = guardMin, recentActions = names, currentRules = rulesStr)
            }
            val buildPostContext = ::gen_buildPostContext_fn
            fun gen_emitShowRuleDialog_fn(rule: Any, reasoning: String): Unit {
                try {
                    uni__emit("showTriggerRuleDialog", _uO("rule" to rule, "reasoning" to reasoning))
                }
                 catch (_: Throwable) {}
            }
            val emitShowRuleDialog = ::gen_emitShowRuleDialog_fn
            fun gen_convertEffectiveRuleToTriggerRule_fn(effective: EffectiveTriggerRule): TriggerRule {
                val nowSec = Math.floor(Date.now() / 1000)
                var conditionJson = "{}"
                try {
                    val parts: UTSArray<String> = _uA()
                    if (effective.timeWindow != null) {
                        parts.push("\"timeWindow\":{\"start\":\"" + effective.timeWindow!!!!.start + "\",\"end\":\"" + effective.timeWindow!!!!.end + "\"}")
                    }
                    if (effective.screenConditions != null) {
                        val sc = effective.screenConditions!!!!
                        val pkgs = JSON.stringify(sc.appPackages)
                        parts.push("\"screenConditions\":{\"appPackages\":" + pkgs + ",\"includeHome\":" + (if (sc.includeHome) {
                            "true"
                        } else {
                            "false"
                        }
                        ) + "}")
                    }
                    if (effective.timeThresholdMinutes > 0) {
                        parts.push("\"timeThresholdMinutes\":" + effective.timeThresholdMinutes)
                    }
                    if (parts.length > 0) {
                        conditionJson = "{" + parts.join(",") + "}"
                    }
                }
                 catch (_: Throwable) {}
                val rule = TriggerRule(id = 0, rule_type = "frequency", trigger_type = "app_duration", condition_json = conditionJson, action_weights_json = "{\"" + effective.actionId + "\":1}", enabled = 1, priority = 10, source = "llm", expires_at = null, created_at = nowSec, updated_at = nowSec)
                return rule
            }
            val convertEffectiveRuleToTriggerRule = ::gen_convertEffectiveRuleToTriggerRule_fn
            fun gen_onActionResolvedLLM_fn(actionId: String, actionName: String, actionCategory: String, durationMs: Number): Unit {
                val llmEnabled = getBool("llm_trigger_enabled", true)
                if (!llmEnabled) {
                    clearPreContext()
                    clearPostContext()
                    return
                }
                pendingActionId = actionId
                actionName
                actionCategory
                val postCtx = buildPostContext(actionName, actionCategory, durationMs)
                setPostContext(postCtx)
                try {
                    evaluatePost(actionId, actionName, actionCategory, durationMs, fun(postResult: PostActionResult): Unit {
                        try {
                            if (postResult.suggestedRule != null) {
                                val rule = postResult.suggestedRule!!!!
                                val askEach = getBool("llm_trigger_ask_each_time", true)
                                if (askEach) {
                                    emitShowRuleDialog(rule, postResult.reasoning)
                                } else {
                                    try {
                                        val triggerRule = convertEffectiveRuleToTriggerRule(rule)
                                        val insertId = insertRule(triggerRule)
                                        console.log("[App.uvue] 静默保存 suggestedRule, insertId=" + insertId)
                                    }
                                     catch (e2: Throwable) {
                                        console.warn("[App.uvue] 静默保存 suggestedRule 失败: " + JSON.stringify(e2))
                                    }
                                }
                            }
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] post-result 处理异常: " + JSON.stringify(e))
                        }
                        clearPreContext()
                        clearPostContext()
                    }
                    , fun(): Unit {
                        clearPreContext()
                        clearPostContext()
                    }
                    )
                }
                 catch (e: Throwable) {
                    console.warn("[App.uvue] evaluatePost 异常: " + JSON.stringify(e))
                    clearPreContext()
                    clearPostContext()
                }
            }
            val onActionResolvedLLM = ::gen_onActionResolvedLLM_fn
            fun gen_showOverlayWithAdhoc_fn(decision: TriggerDecision, adhocText: String, fallback: Boolean): Unit {
                triggeredActionId = decision.actionId
                pendingDecisionActionId = decision.actionId
                pendingDecisionLevel = decision.triggerLevel
                pendingDecisionTts = decision.ttsText
                pendingDecisionDuration = decision.durationMs
                pendingAdhocText = adhocText
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
                    )
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
                    , 0)
                }
                , onSkipDuringExec = fun(): Unit {
                    onActionResolved()
                    onActionResolvedLLM(pendingDecisionActionId, "", "", 0)
                }
                , onCountdownTick = fun(_remaining: Number): Unit {}, onCountdownFinish = fun(): Unit {
                    shortVibrate(2)
                    val encourageTextFinal = encourageText
                    speakSystemTts(encourageTextFinal)
                    val action = getActionById(pendingDecisionActionId)
                    if (action != null) {
                        insertActionLog(ActionLog(id = 0, action_id = action.id, action_type = action.category, result = "completed", skip_reason = null, trigger_type = "app_duration", trigger_level = pendingDecisionLevel, duration_ms = action.defaultDurationMs, target_ms = action.defaultDurationMs, triggered_at = Math.floor(Date.now() / 1000), completed_at = Math.floor(Date.now() / 1000), created_at = Math.floor(Date.now() / 1000)))
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
                    )
                }
                , onPartialCompletion = fun(_completed: Number, _total: Number): Unit {})
                try {
                    showOverlay(cfg, cbs)
                }
                 catch (e: Throwable) {
                    console.warn("[App.uvue] showOverlay 失败: " + JSON.stringify(e))
                }
                if (!fallback && adhocText.length > 0) {
                    try {
                        speakAdhoc(adhocText)
                    }
                     catch (_: Throwable) {}
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
                            if (actionId == null || actionId.length < 1) {
                                return
                            }
                            onActionResolvedLLM(actionId, actionName, actionCategory, durationMs)
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] llmActionCompleted 处理异常: " + JSON.stringify(e))
                        }
                    }
                    )
                }
                 catch (e: Throwable) {
                    console.warn("[App.uvue] \$on llmActionCompleted 异常: " + JSON.stringify(e))
                }
                try {
                    sqliteStore.openDatabase("micro_habit_v2.db", 1, fun(): Unit {
                        return onCreate()
                    }
                    , fun(oldV: Number, newV: Number): Unit {})
                    val callbacks = MonitorCallbacks(onAppDurationTrigger = fun(info: AppForegroundInfo): Unit {
                        try {
                            insertOrUpdateSnapshot(info.packageName, info.packageName, 1)
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] insertOrUpdateSnapshot 失败: " + JSON.stringify(e))
                        }
                        val threshold = getInt("app_duration_threshold", 60) * 1000
                        if (info.continuousMs < threshold) {
                            return
                        }
                        var decision: TriggerDecision? = null
                        try {
                            val ctx = TriggerContext(appPackage = info.packageName, appLabel = info.packageName, continuousMinutes = Math.floor(info.continuousMs / 60000), triggerType = "app_duration")
                            decision = shouldTrigger(ctx)
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] shouldTrigger 失败: " + JSON.stringify(e))
                        }
                        if (decision == null) {
                            return
                        }
                        val llmEnabled = getBool("llm_trigger_enabled", true)
                        if (!llmEnabled) {
                            showOverlayWithAdhoc(decision, decision.ttsText, true)
                            return
                        }
                        val preCtx = buildPreContext(info, decision.actionId)
                        setPreContext(preCtx)
                        try {
                            evaluatePre(fun(preResult: PreTriggerResult): Unit {
                                try {
                                    showOverlayWithAdhoc(decision, preResult.adhocText, preResult.fallback)
                                }
                                 catch (e: Throwable) {
                                    console.warn("[App.uvue] onReady 处理失败: " + JSON.stringify(e))
                                }
                            }
                            , fun(): Unit {
                                try {
                                    showOverlayWithAdhoc(decision, getFallbackAdhoc(), true)
                                }
                                 catch (e: Throwable) {
                                    console.warn("[App.uvue] onFail 降级失败: " + JSON.stringify(e))
                                }
                                clearPreContext()
                            }
                            )
                        }
                         catch (e: Throwable) {
                            console.warn("[App.uvue] evaluatePre 异常: " + JSON.stringify(e))
                            showOverlayWithAdhoc(decision, getFallbackAdhoc(), true)
                            clearPreContext()
                        }
                    }
                    , onAppSwitch = fun(_from: String, _to: String): Unit {}, onTimePeriodTrigger = fun(_name: String): Unit {})
                    startMonitorService(callbacks)
                }
                 catch (e: Throwable) {
                    console.error("App init error: " + JSON.stringify(e))
                }
                console.log("App Launch")
            }
            )
            onAppShow(fun(_options){
                try {
                    saveTodaySummary()
                }
                 catch (_: Throwable) {}
                console.log("App Show")
            }
            )
            onAppHide(fun(){
                console.log("App Hide")
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
                sqliteStore.close()
                console.log("App Exit")
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
) : UTSReactiveObject() {
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
) : UTSReactiveObject() {
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
val LLM_RESERVED_NOTE = "LLM 接口待配置"
fun aiEvaluateTodayUsage(onComplete: (text: String) -> Unit): Unit {
    onComplete(LLM_RESERVED_NOTE)
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
open class ScoredItem (
    @JsonNotNull
    open var action: MicroAction,
    @JsonNotNull
    open var score: Number,
) : UTSObject()
fun getRecommendedActions(): UTSArray<MicroAction> {
    val enabledActions = getEnabledActions()
    if (enabledActions.length.toInt() === 0) {
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
) : UTSObject()
fun getByKey(key: String): LlmCache? {
    val row = dbManager.queryOne("SELECT * FROM llm_cache WHERE cache_key = ?", _uA(
        key
    ))
    if (row == null) {
        return null
    }
    return mapRow__5(row)
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
fun mapRow__5(row: Map<String, Any>): LlmCache {
    return LlmCache(cache_key = getStr(row, "cache_key"), cache_type = getStr(row, "cache_type"), response = getStr(row, "response"), model = getStr(row, "model"), created_at = getNum(row, "created_at"), expires_at = getNum(row, "expires_at"))
}
fun isNetworkAvailable(): Boolean {
    return true
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
) : UTSObject()
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
            val obj = JSON.parse(jsonStr) as WeeklyLLMOutput
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
) : UTSReactiveObject() {
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
    open var refreshHomeData: () -> Unit,
    open var refreshWeeklyReport: () -> Unit,
    open var refreshLlmHistory: () -> Unit,
) : UTSObject()
val eyeScore = ref<Number>(0)
val eyeStatus = ref<String>("no_data")
val postureScore = ref<Number>(0)
val postureStatus = ref<String>("no_data")
val vitalityScore = ref<Number>(0)
val vitalityStatus = ref<String>("no_data")
val guardCount = ref<Number>(0)
val penetration = ref<Number>(0)
val phoneMinutes = ref<Number>(0)
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
fun refreshLlmHistory(): Unit {
    try {
        llmHistory.value = getAllHistory(200)
    }
     catch (e: Throwable) {
        console.warn("[appStore] refreshLlmHistory 失败: " + JSON.stringify(e))
        llmHistory.value = _uA()
    }
}
fun useAppStore(): AppStore {
    val store = AppStore(eyeScore = eyeScore, eyeStatus = eyeStatus, postureScore = postureScore, postureStatus = postureStatus, vitalityScore = vitalityScore, vitalityStatus = vitalityStatus, guardCount = guardCount, penetration = penetration, phoneMinutes = phoneMinutes, todayCompletedCount = todayCompletedCount, recommendedActions = recommendedActions, heatmapData = heatmapData, dailySummary = dailySummary, dailySummaryLoading = dailySummaryLoading, homeDataLoaded = homeDataLoaded, weeklyReport = weeklyReport, weeklyReportLoading = weeklyReportLoading, llmHistory = llmHistory, refreshLlmHistory = fun(): Unit {
        refreshLlmHistory()
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
                    val parsed = JSON.parse(todayCache.response)
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
                val fallback = DailyOutput(one_liner = "昨天完成" + ydata.todayCompletedCount + "次微动作", summary = "累计" + ydata.guardCount + "次", tomorrow_goal = "", encourage = "")
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
fun buildLocalSummary(completedCount: Number, count: Number): DailyOutput {
    return DailyOutput(one_liner = "今日完成" + completedCount + "次微动作", summary = "累计" + count + "次，继续加油", tomorrow_goal = "继续保持！", encourage = "你做得很好")
}
fun generateDailySummary(date: String, onComplete: (summary: DailyOutput) -> Unit): Unit {
    val data = getHomepageData(date)
    if (!isNetworkAvailable()) {
        onComplete(buildLocalSummary(data.todayCompletedCount, data.guardCount))
        return
    }
    val dto = DailyData(totalCompleted = data.todayCompletedCount, totalSkipped = 0, totalDurationSec = 0, guardMinutes = 0, guardCount = data.guardCount, penetration = data.penetration, eyeScore = Math.max(0, Math.round(data.eyeScore)), postureScore = Math.max(0, Math.round(data.postureScore)), vitalityScore = Math.max(0, Math.round(data.vitalityScore)), todayCompletedCount = data.todayCompletedCount)
    callDaily(date, dto, fun(result: Any){
        try {
            val jsonStr = JSON.stringify(result)
            val obj = JSON.parse(jsonStr) as DailyOutput
            if (obj.one_liner != null) {
                onComplete(obj)
                return
            }
        }
         catch (_: Throwable) {}
        onComplete(buildLocalSummary(data.todayCompletedCount, data.guardCount))
    }
    , fun(){
        onComplete(buildLocalSummary(data.todayCompletedCount, data.guardCount))
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
open class CellInfo (
    open var day: Number? = null,
    @JsonNotNull
    open var count: Number,
) : UTSObject()
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
) : UTSReactiveObject() {
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
) : UTSReactiveObject() {
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
    override var versionName: String = "1.0.2"
    override var versionCode: String = "102"
    override var uniCompilerVersion: String = "5.11"
    constructor() : super() {}
}
fun definePageRoutes() {
    __uniRoutes.push(UniPageRoute(path = "pages/guide/index", component = GenPagesGuideIndexClass, meta = UniPageMeta(isQuit = true), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/home/index", component = GenPagesHomeIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/action/execute", component = GenPagesActionExecuteClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/data/dashboard", component = GenPagesDataDashboardClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "数据看板")))
    __uniRoutes.push(UniPageRoute(path = "pages/settings/index", component = GenPagesSettingsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "设置")))
    __uniRoutes.push(UniPageRoute(path = "pages/debug/logs", component = GenPagesDebugLogsClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "调试日志")))
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
open class UniCloudConfig : io.dcloud.unicloud.InternalUniCloudConfig {
    override var isDev: Boolean = false
    override var spaceList: String = "[{\"provider\":\"aliyun\",\"spaceName\":\"trial-w7onvxzrxrp2h087561\",\"spaceId\":\"mp-76d188c8-df1a-4535-9cee-3bb94b3a694d\",\"clientSecret\":\"b94cO0f6Qv3WwtDVDIwHFw==\",\"endpoint\":\"https://api.next.bspapp.com\"}]"
    override var debuggerInfo: String? = null
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
