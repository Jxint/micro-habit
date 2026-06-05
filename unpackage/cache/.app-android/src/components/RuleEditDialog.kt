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
open class GenComponentsRuleEditDialog : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var visible: Boolean by `$props`
    open var ruleJson: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsRuleEditDialog) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsRuleEditDialog
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val actions = ALL_ACTIONS
            fun gen_pad2_fn(n: Number): String {
                if (n < 10) {
                    return "0" + n
                }
                return "" + n
            }
            val pad2 = ::gen_pad2_fn
            var draftActionId: String = ""
            var draftThreshold: Number = 60
            var draftTimeEnabled: Boolean = false
            var draftStartHour: Number = 9
            var draftEndHour: Number = 22
            var draftAppsText: String = ""
            var draftLevel: TriggerLevel = "gentle"
            var draftEnabled: Boolean = true
            var isDefault: Boolean = false
            var ruleId: Number = 0
            var ruleSource: String = "user"
            var ruleCreatedAt: Number = 0
            var ruleSourceHistoryId: Number? = null
            var ruleExpiresAt: Number? = null
            fun gen_parseToRule_fn(o: UTSJSONObject?): PersistedEffectiveRule {
                if (o == null) {
                    return PersistedEffectiveRule(id = 0, actionId = "", appPackages = _uA<String>(), timeWindow = null, timeThresholdMinutes = 60, triggerLevel = "gentle" as TriggerLevel, categoryFilter = null, source = "user" as String, sourceHistoryId = null, expiresAt = null, enabled = true, priority = 0, createdAt = Math.floor(Date.now() / 1000), updatedAt = Math.floor(Date.now() / 1000))
                }
                val pkgArr: UTSArray<String> = _uA()
                val rawPkgs = o["appPackages"] as UTSArray<Any>?
                if (rawPkgs != null) {
                    run {
                        var i: Number = 0
                        while(i < rawPkgs.length){
                            val v = rawPkgs[i] as String?
                            if (v != null && v.length > 0) {
                                pkgArr.push(v)
                            }
                            i++
                        }
                    }
                }
                val twRaw = o["timeWindow"]
                var tw: TimeWindow? = null
                if (twRaw != null) {
                    val twObj = twRaw as UTSJSONObject
                    val s = twObj["start"] as String?
                    val e = twObj["end"] as String?
                    if (s != null && e != null && s.length > 0 && e.length > 0) {
                        tw = TimeWindow(start = s!!!!, end = e!!!!)
                    }
                }
                val srcRaw = (o["source"] as String?) ?: "user"
                val src: String = if (srcRaw == "llm") {
                    "llm"
                } else {
                    if (srcRaw == "system") {
                        "system"
                    } else {
                        "user"
                    }
                }
                val lvlRaw = (o["triggerLevel"] as String?) ?: "gentle"
                val lvl: TriggerLevel = if (lvlRaw == "strong") {
                    "strong"
                } else {
                    if (lvlRaw == "forced") {
                        "forced"
                    } else {
                        "gentle"
                    }
                }
                val histRaw = o["sourceHistoryId"]
                val histId: Number? = if (histRaw == null) {
                    null
                } else {
                    (histRaw as Number)
                }
                val expRaw = o["expiresAt"]
                val expAt: Number? = if (expRaw == null) {
                    null
                } else {
                    (expRaw as Number)
                }
                return PersistedEffectiveRule(id = (o["id"] as Number) ?: 0, actionId = (o["actionId"] as String) ?: "", appPackages = pkgArr, timeWindow = tw, timeThresholdMinutes = (o["timeThresholdMinutes"] as Number) ?: 60, triggerLevel = lvl, categoryFilter = (o["categoryFilter"] as String?) ?: null, source = src, sourceHistoryId = histId, expiresAt = expAt, enabled = (o["enabled"] as Boolean) == true, priority = (o["priority"] as Number) ?: 0, createdAt = (o["createdAt"] as Number) ?: 0, updatedAt = (o["updatedAt"] as Number) ?: 0)
            }
            val parseToRule = ::gen_parseToRule_fn
            fun gen_resetDraft_fn(): Unit {
                if (props.ruleJson.length < 1) {
                    draftActionId = ""
                    draftThreshold = 60
                    draftTimeEnabled = false
                    draftStartHour = 9
                    draftEndHour = 22
                    draftAppsText = ""
                    draftLevel = "gentle"
                    draftEnabled = true
                    isDefault = false
                    ruleId = 0
                    ruleSource = "user"
                    ruleCreatedAt = Math.floor(Date.now() / 1000)
                    ruleSourceHistoryId = null
                    ruleExpiresAt = null
                    return
                }
                val obj: UTSJSONObject = UTSAndroid.consoleDebugError(JSON.parse(props.ruleJson), " at components/RuleEditDialog.uvue:240") as UTSJSONObject
                val r: PersistedEffectiveRule = parseToRule(obj)
                ruleId = r.id
                isDefault = r.source == "system"
                draftActionId = r.actionId
                draftThreshold = r.timeThresholdMinutes
                draftTimeEnabled = r.timeWindow != null
                if (r.timeWindow != null) {
                    val sh = parseInt(r.timeWindow!!.start.split(":")[0] as String)
                    val eh = parseInt(r.timeWindow!!.end.split(":")[0] as String)
                    draftStartHour = if (isNaN(sh)) {
                        9
                    } else {
                        sh
                    }
                    draftEndHour = if (isNaN(eh)) {
                        22
                    } else {
                        eh
                    }
                } else {
                    draftStartHour = 9
                    draftEndHour = 22
                }
                draftAppsText = r.appPackages.join("\n")
                draftLevel = r.triggerLevel
                draftEnabled = r.enabled
                ruleSource = r.source
                ruleCreatedAt = r.createdAt
                ruleSourceHistoryId = r.sourceHistoryId
                ruleExpiresAt = r.expiresAt
            }
            val resetDraft = ::gen_resetDraft_fn
            fun gen_onMaskTap_fn(): Unit {
                emit("close")
            }
            val onMaskTap = ::gen_onMaskTap_fn
            fun gen_onCancel_fn(): Unit {
                emit("close")
            }
            val onCancel = ::gen_onCancel_fn
            fun gen_onPickAction_fn(id: String): Unit {
                if (isDefault) {
                    return
                }
                draftActionId = id
            }
            val onPickAction = ::gen_onPickAction_fn
            fun gen_onDecStep_fn(): Unit {
                if (draftThreshold > 5) {
                    draftThreshold = draftThreshold - 5
                }
            }
            val onDecStep = ::gen_onDecStep_fn
            fun gen_onIncStep_fn(): Unit {
                if (draftThreshold < 240) {
                    draftThreshold = draftThreshold + 5
                }
            }
            val onIncStep = ::gen_onIncStep_fn
            fun gen_onToggleTimeWindow_fn(enabled: Boolean): Unit {
                draftTimeEnabled = enabled
            }
            val onToggleTimeWindow = ::gen_onToggleTimeWindow_fn
            fun gen_onDecStart_fn(): Unit {
                if (draftStartHour > 0) {
                    draftStartHour = draftStartHour - 1
                }
            }
            val onDecStart = ::gen_onDecStart_fn
            fun gen_onIncStart_fn(): Unit {
                if (draftStartHour < 23) {
                    draftStartHour = draftStartHour + 1
                }
            }
            val onIncStart = ::gen_onIncStart_fn
            fun gen_onDecEnd_fn(): Unit {
                if (draftEndHour > 0) {
                    draftEndHour = draftEndHour - 1
                }
            }
            val onDecEnd = ::gen_onDecEnd_fn
            fun gen_onIncEnd_fn(): Unit {
                if (draftEndHour < 23) {
                    draftEndHour = draftEndHour + 1
                }
            }
            val onIncEnd = ::gen_onIncEnd_fn
            fun gen_onPickLevel_fn(lv: String): Unit {
                if (isDefault) {
                    return
                }
                draftLevel = lv
            }
            val onPickLevel = ::gen_onPickLevel_fn
            fun gen_onEnabledChange_fn(e: Any): Unit {
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
                    if (UTSAndroid.`typeof`(valRaw) === "boolean") {
                        draftEnabled = valRaw as Boolean
                    } else if (UTSAndroid.`typeof`(valRaw) === "number") {
                        draftEnabled = (valRaw as Number) != 0
                    }
                }
                 catch (_: Throwable) {}
            }
            val onEnabledChange = ::gen_onEnabledChange_fn
            fun gen_onSave_fn(): Unit {
                val pkgs: UTSArray<String> = _uA()
                if (!isDefault && draftAppsText.length > 0) {
                    val lines = draftAppsText.split("\n")
                    run {
                        var i: Number = 0
                        while(i < lines.length){
                            val t = (lines[i] as String).trim()
                            if (t.length > 0) {
                                pkgs.push(t)
                            }
                            i++
                        }
                    }
                }
                val tw: TimeWindow? = if (draftTimeEnabled) {
                    TimeWindow(start = pad2(draftStartHour) + ":00", end = pad2(draftEndHour) + ":00")
                } else {
                    null
                }
                val out = PersistedEffectiveRule(id = ruleId, actionId = if (isDefault) {
                    ""
                } else {
                    draftActionId
                }
                , appPackages = pkgs, timeWindow = tw, timeThresholdMinutes = draftThreshold, triggerLevel = if (isDefault) {
                    "gentle"
                } else {
                    draftLevel
                }
                , categoryFilter = null, source = ruleSource, sourceHistoryId = ruleSourceHistoryId, expiresAt = ruleExpiresAt, enabled = draftEnabled, priority = if (isDefault) {
                    0
                } else {
                    5
                }
                , createdAt = ruleCreatedAt, updatedAt = Math.floor(Date.now() / 1000))
                emit("save", out)
            }
            val onSave = ::gen_onSave_fn
            fun gen_onDelete_fn(): Unit {
                if (isDefault) {
                    return
                }
                emit("delete", ruleId)
            }
            val onDelete = ::gen_onDelete_fn
            watch(fun(): Boolean {
                return props.visible
            }
            , fun(v: Boolean): Unit {
                if (v) {
                    resetDraft()
                }
            }
            )
            return fun(): Any? {
                val _component_switch = resolveComponent("switch")
                return if (isTrue(_ctx.visible)) {
                    _cE("view", _uM("key" to 0, "class" to "dialog-mask", "onClick" to onMaskTap), _uA(
                        _cE("view", _uM("class" to "dialog-card", "onClick" to withModifiers(fun(){}, _uA(
                            "stop"
                        ))), _uA(
                            _cE("view", _uM("class" to "dialog-header"), _uA(
                                _cE("text", _uM("class" to "dialog-title"), "编辑触发规则"),
                                if (isTrue(unref(isDefault))) {
                                    _cE("text", _uM("key" to 0, "class" to "dialog-subtitle"), "系统默认规则")
                                } else {
                                    _cC("v-if", true)
                                }
                            )),
                            _cE("scroll-view", _uM("class" to "dialog-body", "scroll-y" to "true"), _uA(
                                _cE("view", _uM("class" to "field-group"), _uA(
                                    _cE("text", _uM("class" to "field-label"), "微动作"),
                                    if (isTrue(unref(isDefault))) {
                                        _cE("view", _uM("key" to 0, "class" to "field-static"), _uA(
                                            _cE("text", _uM("class" to "field-static-text"), "任意动作（由算法推荐）")
                                        ))
                                    } else {
                                        _cE("view", _uM("key" to 1, "class" to "action-picker"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(actions), fun(a, i, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("act-" + i), "class" to _nC(_uA(
                                                    "action-item",
                                                    if (a.id === unref(draftActionId)) {
                                                        "action-item-active"
                                                    } else {
                                                        ""
                                                    }
                                                )), "onClick" to fun(){
                                                    onPickAction(a.id)
                                                }), _uA(
                                                    _cE("text", _uM("class" to "action-name"), _tD(a.name), 1),
                                                    _cE("text", _uM("class" to "action-cat"), _tD(a.category), 1)
                                                ), 10, _uA(
                                                    "onClick"
                                                ))
                                            }), 128)
                                        ))
                                    }
                                )),
                                _cE("view", _uM("class" to "field-group"), _uA(
                                    _cE("text", _uM("class" to "field-label"), "触发阈值"),
                                    _cE("view", _uM("class" to "threshold-row"), _uA(
                                        _cE("text", _uM("class" to "field-value"), _tD(unref(draftThreshold)) + " 分钟", 1),
                                        _cE("view", _uM("class" to "stepper"), _uA(
                                            _cE("view", _uM("class" to "stepper-btn", "onClick" to onDecStep), _uA(
                                                _cE("text", _uM("class" to "stepper-text"), "-")
                                            )),
                                            _cE("view", _uM("class" to "stepper-btn", "onClick" to onIncStep), _uA(
                                                _cE("text", _uM("class" to "stepper-text"), "+")
                                            ))
                                        ))
                                    )),
                                    _cE("text", _uM("class" to "field-hint"), "使用同一应用达到该时长后触发")
                                )),
                                _cE("view", _uM("class" to "field-group"), _uA(
                                    _cE("text", _uM("class" to "field-label"), "生效时段"),
                                    _cE("view", _uM("class" to "time-row"), _uA(
                                        _cE("view", _uM("class" to _nC(_uA(
                                            "time-pill",
                                            if (!unref(draftTimeEnabled)) {
                                                "time-pill-active"
                                            } else {
                                                ""
                                            }
                                        )), "onClick" to fun(){
                                            onToggleTimeWindow(false)
                                        }), _uA(
                                            _cE("text", _uM("class" to "time-pill-text"), "全天")
                                        ), 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to _nC(_uA(
                                            "time-pill",
                                            if (unref(draftTimeEnabled)) {
                                                "time-pill-active"
                                            } else {
                                                ""
                                            }
                                        )), "onClick" to fun(){
                                            onToggleTimeWindow(true)
                                        }), _uA(
                                            _cE("text", _uM("class" to "time-pill-text"), "自定义")
                                        ), 10, _uA(
                                            "onClick"
                                        ))
                                    )),
                                    if (isTrue(unref(draftTimeEnabled))) {
                                        _cE("view", _uM("key" to 0, "class" to "time-inputs"), _uA(
                                            _cE("view", _uM("class" to "time-input"), _uA(
                                                _cE("text", _uM("class" to "time-input-label"), "起"),
                                                _cE("view", _uM("class" to "stepper stepper-sm"), _uA(
                                                    _cE("view", _uM("class" to "stepper-btn", "onClick" to onDecStart), _uA(
                                                        _cE("text", _uM("class" to "stepper-text"), "-")
                                                    )),
                                                    _cE("text", _uM("class" to "time-input-val"), _tD(pad2(unref(draftStartHour))) + ":00", 1),
                                                    _cE("view", _uM("class" to "stepper-btn", "onClick" to onIncStart), _uA(
                                                        _cE("text", _uM("class" to "stepper-text"), "+")
                                                    ))
                                                ))
                                            )),
                                            _cE("view", _uM("class" to "time-input"), _uA(
                                                _cE("text", _uM("class" to "time-input-label"), "止"),
                                                _cE("view", _uM("class" to "stepper stepper-sm"), _uA(
                                                    _cE("view", _uM("class" to "stepper-btn", "onClick" to onDecEnd), _uA(
                                                        _cE("text", _uM("class" to "stepper-text"), "-")
                                                    )),
                                                    _cE("text", _uM("class" to "time-input-val"), _tD(pad2(unref(draftEndHour))) + ":00", 1),
                                                    _cE("view", _uM("class" to "stepper-btn", "onClick" to onIncEnd), _uA(
                                                        _cE("text", _uM("class" to "stepper-text"), "+")
                                                    ))
                                                ))
                                            ))
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                )),
                                if (isTrue(!unref(isDefault))) {
                                    _cE("view", _uM("key" to 0, "class" to "field-group"), _uA(
                                        _cE("text", _uM("class" to "field-label"), "指定 App"),
                                        _cE("text", _uM("class" to "field-hint"), "留空表示所有 App 触发，每行一个包名"),
                                        _cE("textarea", _uM("class" to "apps-area", "modelValue" to unref(draftAppsText), "onInput" to fun(`$event`: UniInputEvent){
                                            draftAppsText = trySetRefValue(draftAppsText, `$event`.detail.value)
                                        }, "placeholder" to "com.tencent.mm\ncom.android.chrome", "auto-height" to "true"), null, 40, _uA(
                                            "modelValue"
                                        ))
                                    ))
                                } else {
                                    _cC("v-if", true)
                                },
                                if (isTrue(!unref(isDefault))) {
                                    _cE("view", _uM("key" to 1, "class" to "field-group"), _uA(
                                        _cE("text", _uM("class" to "field-label"), "触发强度"),
                                        _cE("view", _uM("class" to "level-row"), _uA(
                                            _cE("view", _uM("class" to _nC(_uA(
                                                "level-pill",
                                                if (unref(draftLevel) == "gentle") {
                                                    "level-pill-active"
                                                } else {
                                                    ""
                                                }
                                            )), "onClick" to fun(){
                                                onPickLevel("gentle")
                                            }), _uA(
                                                _cE("text", _uM("class" to "level-pill-text"), "温和")
                                            ), 10, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to _nC(_uA(
                                                "level-pill",
                                                if (unref(draftLevel) == "strong") {
                                                    "level-pill-active"
                                                } else {
                                                    ""
                                                }
                                            )), "onClick" to fun(){
                                                onPickLevel("strong")
                                            }), _uA(
                                                _cE("text", _uM("class" to "level-pill-text"), "强烈")
                                            ), 10, _uA(
                                                "onClick"
                                            ))
                                        ))
                                    ))
                                } else {
                                    _cC("v-if", true)
                                },
                                _cE("view", _uM("class" to "field-group"), _uA(
                                    _cE("view", _uM("class" to "enable-row"), _uA(
                                        _cE("text", _uM("class" to "field-label"), "启用此规则"),
                                        _cV(_component_switch, _uM("checked" to unref(draftEnabled), "onChange" to onEnabledChange), null, 8, _uA(
                                            "checked"
                                        ))
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "dialog-footer"), _uA(
                                if (isTrue(!unref(isDefault))) {
                                    _cE("view", _uM("key" to 0, "class" to "btn btn-danger", "onClick" to onDelete), _uA(
                                        _cE("text", _uM("class" to "btn-text btn-text-danger"), "删除")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                },
                                _cE("view", _uM("class" to "btn btn-ghost", "onClick" to onCancel), _uA(
                                    _cE("text", _uM("class" to "btn-text"), "取消")
                                )),
                                _cE("view", _uM("class" to "btn btn-primary", "onClick" to onSave), _uA(
                                    _cE("text", _uM("class" to "btn-text btn-text-primary"), "保存")
                                ))
                            ))
                        ), 8, _uA(
                            "onClick"
                        ))
                    ))
                } else {
                    _cC("v-if", true)
                }
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("dialog-mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "zIndex" to 999, "justifyContent" to "center", "alignItems" to "center")), "dialog-card" to _pS(_uM("width" to "88%", "maxHeight" to 560, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "flexDirection" to "column", "overflow" to "hidden")), "dialog-header" to _pS(_uM("paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid", "alignItems" to "center")), "dialog-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#2C3E50")), "dialog-subtitle" to _pS(_uM("fontSize" to 12, "color" to "#F39C12", "marginTop" to 4)), "dialog-body" to _pS(_uM("maxHeight" to 480, "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16)), "field-group" to _pS(_uM("marginBottom" to 16)), "field-label" to _pS(_uM("fontSize" to 13, "color" to "#2C3E50", "fontWeight" to "bold", "marginBottom" to 8)), "field-hint" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginTop" to 4)), "field-static" to _pS(_uM("backgroundColor" to "#F8F9FA", "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6)), "field-static-text" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D")), "field-value" to _pS(_uM("fontSize" to 15, "color" to "#3498DB", "fontWeight" to 500)), "action-picker" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap")), "action-item" to _pS(_uM("paddingTop" to 8, "paddingRight" to 12, "paddingBottom" to 8, "paddingLeft" to 12, "marginRight" to 8, "marginBottom" to 8, "backgroundColor" to "#F0F3F4", "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "flexDirection" to "column", "alignItems" to "center")), "action-item-active" to _pS(_uM("backgroundColor" to "#3498DB")), "action-name" to _uM("" to _uM("fontSize" to 13, "color" to "#2C3E50"), ".action-item-active " to _uM("color" to "#FFFFFF")), "action-cat" to _uM("" to _uM("fontSize" to 10, "color" to "#95A5A6", "marginTop" to 2), ".action-item-active " to _uM("color" to "#ECF0F1")), "threshold-row" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "stepper" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "stepper-btn" to _uM("" to _uM("width" to 36, "height" to 36, "backgroundColor" to "#F0F3F4", "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "justifyContent" to "center", "alignItems" to "center", "marginLeft" to 8), ".stepper-sm " to _uM("width" to 28, "height" to 28, "marginLeft" to 4)), "stepper-text" to _pS(_uM("fontSize" to 18, "color" to "#3498DB", "fontWeight" to "bold")), "time-row" to _pS(_uM("flexDirection" to "row", "marginBottom" to 8)), "time-pill" to _pS(_uM("paddingTop" to 8, "paddingRight" to 16, "paddingBottom" to 8, "paddingLeft" to 16, "backgroundColor" to "#F0F3F4", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "marginRight" to 8)), "time-pill-active" to _pS(_uM("backgroundColor" to "#3498DB")), "time-pill-text" to _uM("" to _uM("fontSize" to 13, "color" to "#2C3E50"), ".time-pill-active " to _uM("color" to "#FFFFFF")), "time-inputs" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "marginTop" to 8)), "time-input" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "time-input-label" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D", "marginRight" to 8)), "time-input-val" to _pS(_uM("fontSize" to 14, "color" to "#2C3E50", "fontWeight" to 500, "marginLeft" to 8, "marginRight" to 8)), "apps-area" to _pS(_uM("width" to "100%", "minHeight" to 80, "paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 10, "paddingLeft" to 10, "backgroundColor" to "#F8F9FA", "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "fontSize" to 13, "color" to "#2C3E50", "marginTop" to 4)), "level-row" to _pS(_uM("flexDirection" to "row")), "level-pill" to _pS(_uM("paddingTop" to 8, "paddingRight" to 20, "paddingBottom" to 8, "paddingLeft" to 20, "backgroundColor" to "#F0F3F4", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "marginRight" to 8)), "level-pill-active" to _pS(_uM("backgroundColor" to "#E74C3C")), "level-pill-text" to _uM("" to _uM("fontSize" to 13, "color" to "#2C3E50"), ".level-pill-active " to _uM("color" to "#FFFFFF")), "enable-row" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "dialog-footer" to _pS(_uM("flexDirection" to "row", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "borderTopWidth" to 1, "borderTopColor" to "#F0F0F0", "borderTopStyle" to "solid")), "btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 40, "justifyContent" to "center", "alignItems" to "center", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "marginLeft" to 8, "marginLeft:first-child" to 0)), "btn-ghost" to _pS(_uM("backgroundColor" to "#ECF0F1")), "btn-primary" to _pS(_uM("backgroundColor" to "#3498DB")), "btn-danger" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#E74C3C", "borderRightColor" to "#E74C3C", "borderBottomColor" to "#E74C3C", "borderLeftColor" to "#E74C3C", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "btn-text" to _pS(_uM("fontSize" to 14, "color" to "#2C3E50")), "btn-text-primary" to _pS(_uM("color" to "#FFFFFF")), "btn-text-danger" to _pS(_uM("color" to "#E74C3C")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("close" to null, "save" to null, "delete" to null)
        var props = _nP(_uM("visible" to _uM("type" to "Boolean", "required" to true, "default" to false), "ruleJson" to _uM("type" to "String", "required" to true, "default" to "")))
        var propsNeedCastKeys = _uA(
            "visible",
            "ruleJson"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
