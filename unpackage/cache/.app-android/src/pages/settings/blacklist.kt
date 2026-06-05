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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSettingsBlacklist : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSettingsBlacklist) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSettingsBlacklist
            val _cache = __ins.renderCache
            val manualInput = ref<String>("")
            val candidateApps = ref(_uA<CandidateApp>())
            val blacklistPkgs = ref(_uA<String>())
            val blacklistAppLabels = ref(_uA<BlacklistEntry>())
            fun gen_loadBlacklist_fn(): UTSArray<String> {
                val raw = getSetting("app_blacklist", "[]")
                val list: UTSArray<String> = _uA()
                try {
                    val arr = UTSAndroid.consoleDebugError(JSON.parse(raw), " at pages/settings/blacklist.uvue:90") as UTSArray<Any>
                    if (arr != null) {
                        run {
                            var i: Number = 0
                            while(i < arr.length){
                                val v = arr[i]
                                if (UTSAndroid.`typeof`(v) === "string" && (v as String).length > 0) {
                                    list.push(v as String)
                                }
                                i++
                            }
                        }
                    }
                }
                 catch (_: Throwable) {}
                return list
            }
            val loadBlacklist = ::gen_loadBlacklist_fn
            fun gen_rebuildBlacklistLabels_fn(): Unit {
                val pkgs = blacklistPkgs.value
                val cands = candidateApps.value
                val out: UTSArray<BlacklistEntry> = _uA()
                run {
                    var i: Number = 0
                    while(i < pkgs.length){
                        val p = pkgs[i] as String
                        var label = p
                        run {
                            var j: Number = 0
                            while(j < cands.length){
                                if (cands[j].packageName == p) {
                                    label = if (cands[j].appLabel.length > 0) {
                                        cands[j].appLabel
                                    } else {
                                        p
                                    }
                                    break
                                }
                                j++
                            }
                        }
                        out.push(BlacklistEntry(pkg = p, label = label))
                        i++
                    }
                }
                blacklistAppLabels.value = out
            }
            val rebuildBlacklistLabels = ::gen_rebuildBlacklistLabels_fn
            fun gen_saveBlacklist_fn(list: UTSArray<String>): Unit {
                putSetting("app_blacklist", JSON.stringify(list))
                blacklistPkgs.value = list
                rebuildBlacklistLabels()
            }
            val saveBlacklist = ::gen_saveBlacklist_fn
            fun gen_isInBlacklist_fn(pkg: String): Boolean {
                return blacklistPkgs.value.indexOf(pkg) >= 0
            }
            val isInBlacklist = ::gen_isInBlacklist_fn
            fun gen_onToggle_fn(pkg: String, e: Any): Unit {
                try {
                    val obj = e as UTSJSONObject
                    if (obj == null) {
                        return
                    }
                    val detail = obj["detail"]
                    if (detail == null) {
                        return
                    }
                    val d = detail as UTSJSONObject
                    if (d == null) {
                        return
                    }
                    val v = d["value"]
                    var enabled = false
                    if (UTSAndroid.`typeof`(v) === "boolean") {
                        enabled = v as Boolean
                    } else if (UTSAndroid.`typeof`(v) === "number") {
                        enabled = (v as Number) != 0
                    }
                    val list: UTSArray<String> = blacklistPkgs.value.concat(_uA<String>())
                    val idx = list.indexOf(pkg)
                    if (enabled && idx < 0) {
                        list.push(pkg)
                    } else if (!enabled && idx >= 0) {
                        list.splice(idx, 1)
                    }
                    saveBlacklist(list)
                }
                 catch (err: Throwable) {
                    console.warn("[blacklist] onToggle 异常: " + JSON.stringify(err), " at pages/settings/blacklist.uvue:147")
                }
            }
            val onToggle = ::gen_onToggle_fn
            fun gen_onManualAdd_fn(): Unit {
                try {
                    val text = manualInput.value.trim()
                    if (text.length < 1) {
                        uni_showToast(ShowToastOptions(title = "请输入包名", icon = "none"))
                        return
                    }
                    val parts = text.split(",")
                    val additions: UTSArray<String> = _uA()
                    run {
                        var i: Number = 0
                        while(i < parts.length){
                            val p = parts[i].trim()
                            if (p.length < 1) {
                                i++
                                continue
                            }
                            if (isSystemPackage(p)) {
                                i++
                                continue
                            }
                            if (blacklistPkgs.value.indexOf(p) >= 0) {
                                i++
                                continue
                            }
                            if (additions.indexOf(p) >= 0) {
                                i++
                                continue
                            }
                            additions.push(p)
                            i++
                        }
                    }
                    if (additions.length < 1) {
                        uni_showToast(ShowToastOptions(title = "无可添加的包名", icon = "none"))
                        return
                    }
                    val next = blacklistPkgs.value.concat(additions)
                    saveBlacklist(next)
                    manualInput.value = ""
                    uni_showToast(ShowToastOptions(title = "已添加 " + additions.length + " 个", icon = "success"))
                }
                 catch (err: Throwable) {
                    console.warn("[blacklist] onManualAdd 异常: " + JSON.stringify(err), " at pages/settings/blacklist.uvue:177")
                }
            }
            val onManualAdd = ::gen_onManualAdd_fn
            fun gen_onRemove_fn(pkg: String): Unit {
                try {
                    val list: UTSArray<String> = blacklistPkgs.value.concat(_uA<String>())
                    val idx = list.indexOf(pkg)
                    if (idx < 0) {
                        return
                    }
                    list.splice(idx, 1)
                    saveBlacklist(list)
                }
                 catch (err: Throwable) {
                    console.warn("[blacklist] onRemove 异常: " + JSON.stringify(err), " at pages/settings/blacklist.uvue:189")
                }
            }
            val onRemove = ::gen_onRemove_fn
            fun gen_refresh_fn(): Unit {
                try {
                    val breakdown = getTodayAppBreakdown()
                    val seen: Map<String, Boolean> = Map<String, Boolean>()
                    val cands: UTSArray<CandidateApp> = _uA()
                    run {
                        var i: Number = 0
                        while(i < breakdown.length){
                            val item = breakdown[i]
                            if (item == null) {
                                i++
                                continue
                            }
                            val pkg = item.packageName
                            if (pkg.length < 1) {
                                i++
                                continue
                            }
                            if (isSystemPackage(pkg)) {
                                i++
                                continue
                            }
                            if (seen.get(pkg) == true) {
                                i++
                                continue
                            }
                            seen.set(pkg, true)
                            cands.push(CandidateApp(packageName = pkg, appLabel = item.appLabel))
                            i++
                        }
                    }
                    candidateApps.value = cands
                }
                 catch (err: Throwable) {
                    console.warn("[blacklist] refresh candidates 异常: " + JSON.stringify(err), " at pages/settings/blacklist.uvue:210")
                    candidateApps.value = _uA()
                }
                blacklistPkgs.value = loadBlacklist()
                rebuildBlacklistLabels()
            }
            val refresh = ::gen_refresh_fn
            onPageShow(fun(){
                refresh()
            }
            )
            fun gen_onBack_fn(): Unit {
                uni_navigateBack(null)
            }
            val onBack = ::gen_onBack_fn
            return fun(): Any? {
                val _component_switch = resolveComponent("switch")
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "page-bg"), _uA(
                        _cE("image", _uM("class" to "page-bg-img", "src" to "/static/images/micro-habit-page-bg.png", "mode" to "aspectFill"))
                    )),
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "header-block"), _uA(
                            _cE("text", _uM("class" to "header-title"), "不提醒这些 App"),
                            _cE("text", _uM("class" to "header-desc"), " 勾选的 App 不会再触发微动作提醒。系统自带 App（launcher、settings 等）已默认排除，无需手动添加。 ")
                        )),
                        _cE("view", _uM("class" to "manual-block"), _uA(
                            _cE("text", _uM("class" to "manual-title"), "手动添加包名"),
                            _cE("textarea", _uM("class" to "manual-input", "modelValue" to unref(manualInput), "onInput" to fun(`$event`: UniInputEvent){
                                trySetRefValue(manualInput, `$event`.detail.value)
                            }
                            , "placeholder" to "多个包名用逗号分隔，如 com.tencent.mm, com.sina.weibo", "auto-height" to true), null, 40, _uA(
                                "modelValue"
                            )),
                            _cE("view", _uM("class" to "manual-btn-row"), _uA(
                                _cE("view", _uM("class" to "manual-btn", "onClick" to onManualAdd), _uA(
                                    _cE("text", _uM("class" to "manual-btn-text"), "添加到黑名单")
                                ))
                            ))
                        )),
                        if (unref(candidateApps).length > 0) {
                            _cE("view", _uM("key" to 0, "class" to "candidates-block"), _uA(
                                _cE("text", _uM("class" to "candidates-title"), "从近期使用过的 App 中选择（" + _tD(unref(candidateApps).length) + "）", 1),
                                _cE(Fragment, null, RenderHelpers.renderList(unref(candidateApps), fun(c, ci, __index, _cached): Any {
                                    return _cE("view", _uM("key" to ("c-" + ci), "class" to "candidate-card"), _uA(
                                        _cE("view", _uM("class" to "candidate-left"), _uA(
                                            _cE("text", _uM("class" to "candidate-label"), _tD(if (c.appLabel.length > 0) {
                                                c.appLabel
                                            } else {
                                                c.packageName
                                            }), 1),
                                            _cE("text", _uM("class" to "candidate-pkg"), _tD(c.packageName), 1)
                                        )),
                                        _cV(_component_switch, _uM("checked" to isInBlacklist(c.packageName), "onChange" to fun(e: Any){
                                            return onToggle(c.packageName, e)
                                        }), null, 8, _uA(
                                            "checked",
                                            "onChange"
                                        ))
                                    ))
                                }), 128)
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (unref(candidateApps).length < 1) {
                            _cE("view", _uM("key" to 1, "class" to "empty-block"), _uA(
                                _cE("text", _uM("class" to "empty-text"), "暂无候选 App"),
                                _cE("text", _uM("class" to "empty-hint"), "开启使用情况权限并使用几天 App 后，这里会出现近期使用过的应用")
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (unref(blacklistAppLabels).length > 0) {
                            _cE("view", _uM("key" to 2, "class" to "current-block"), _uA(
                                _cE("text", _uM("class" to "current-title"), "当前黑名单（" + _tD(unref(blacklistAppLabels).length) + "）", 1),
                                _cE(Fragment, null, RenderHelpers.renderList(unref(blacklistAppLabels), fun(item, idx, __index, _cached): Any {
                                    return _cE("view", _uM("key" to ("bl-" + idx), "class" to "current-card"), _uA(
                                        _cE("view", _uM("class" to "current-left"), _uA(
                                            _cE("text", _uM("class" to "current-label"), _tD(item.label), 1),
                                            _cE("text", _uM("class" to "current-pkg"), _tD(item.pkg), 1)
                                        )),
                                        _cE("view", _uM("class" to "current-remove", "onClick" to fun(){
                                            onRemove(item.pkg)
                                        }), _uA(
                                            _cE("text", _uM("class" to "current-remove-text"), "移除")
                                        ), 8, _uA(
                                            "onClick"
                                        ))
                                    ))
                                }), 128)
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "rgba(0,0,0,0)", "position" to "relative", "overflow" to "hidden")), "page-bg" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "width" to "100%", "height" to "100%", "zIndex" to 0)), "page-bg-img" to _pS(_uM("width" to "100%", "height" to "100%")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "zIndex" to 1)), "header-block" to _pS(_uM("paddingTop" to 20, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "backgroundColor" to "rgba(255,255,255,0.52)", "flexDirection" to "column")), "header-title" to _pS(_uM("fontSize" to 18, "fontWeight" to "bold", "color" to "#1E2A38", "marginBottom" to 6)), "header-desc" to _pS(_uM("fontSize" to 12, "color" to "#526273", "lineHeight" to 1.6)), "manual-block" to _pS(_uM("backgroundColor" to "#FFFFFF", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid", "flexDirection" to "column")), "manual-title" to _pS(_uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#2C3E50", "marginBottom" to 8)), "manual-input" to _pS(_uM("fontSize" to 13, "color" to "#2C3E50", "backgroundColor" to "#F4F6F8", "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "paddingTop" to 8, "paddingRight" to 10, "paddingBottom" to 8, "paddingLeft" to 10, "minHeight" to 60, "width" to "100%", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#E0E6EB", "borderRightColor" to "#E0E6EB", "borderBottomColor" to "#E0E6EB", "borderLeftColor" to "#E0E6EB", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "manual-btn-row" to _pS(_uM("flexDirection" to "row", "justifyContent" to "flex-end", "marginTop" to 8)), "manual-btn" to _pS(_uM("backgroundColor" to "#3498DB", "paddingTop" to 8, "paddingRight" to 16, "paddingBottom" to 8, "paddingLeft" to 16, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6)), "manual-btn-text" to _pS(_uM("fontSize" to 13, "color" to "#FFFFFF", "fontWeight" to "bold")), "candidates-block" to _pS(_uM("backgroundColor" to "#FFFFFF", "paddingTop" to 12, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "candidates-title" to _pS(_uM("fontSize" to 13, "color" to "#7F8C8D", "fontWeight" to "bold", "paddingTop" to 6, "paddingRight" to 16, "paddingBottom" to 6, "paddingLeft" to 16)), "candidate-card" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 10, "paddingRight" to 16, "paddingBottom" to 10, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "candidate-left" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "candidate-label" to _pS(_uM("fontSize" to 14, "color" to "#2C3E50", "fontWeight" to 500)), "candidate-pkg" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginTop" to 2)), "current-block" to _pS(_uM("backgroundColor" to "#FFFFFF", "paddingTop" to 12, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0)), "current-title" to _pS(_uM("fontSize" to 13, "color" to "#E74C3C", "fontWeight" to "bold", "paddingTop" to 6, "paddingRight" to 16, "paddingBottom" to 6, "paddingLeft" to 16)), "current-card" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 10, "paddingRight" to 16, "paddingBottom" to 10, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "current-left" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "current-label" to _pS(_uM("fontSize" to 14, "color" to "#2C3E50")), "current-pkg" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginTop" to 2)), "current-remove" to _pS(_uM("backgroundColor" to "#FAEAEA", "paddingTop" to 4, "paddingRight" to 10, "paddingBottom" to 4, "paddingLeft" to 10, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4)), "current-remove-text" to _pS(_uM("fontSize" to 12, "color" to "#E74C3C")), "empty-block" to _pS(_uM("paddingTop" to 40, "paddingRight" to 20, "paddingBottom" to 40, "paddingLeft" to 20, "alignItems" to "center", "backgroundColor" to "#FFFFFF")), "empty-text" to _pS(_uM("fontSize" to 14, "color" to "#7F8C8D", "marginBottom" to 6)), "empty-hint" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6", "textAlign" to "center")), "bottom-spacer" to _pS(_uM("height" to 80)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
