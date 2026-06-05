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
open class GenPagesSettingsAppCategoriesIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSettingsAppCategoriesIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSettingsAppCategoriesIndex
            val _cache = __ins.renderCache
            val categories = _uA(
                "social",
                "video",
                "game",
                "work",
                "study",
                "reading",
                "other"
            ) as UTSArray<String>
            val filterIdx = ref<Number>(-1)
            val allTags = ref(_uA<AppTag>())
            val visibleTags = computed<UTSArray<AppTag>>(fun(): UTSArray<AppTag> {
                val list = allTags.value
                if (filterIdx.value < 0 || filterIdx.value >= categories.length) {
                    return list
                }
                val targetCat = categories[filterIdx.value] as String
                val result: UTSArray<AppTag> = _uA()
                run {
                    var i: Number = 0
                    while(i < list.length){
                        val t = list[i]
                        if (t.category == targetCat) {
                            result.push(t)
                        }
                        i++
                    }
                }
                return result
            }
            )
            fun gen_setFilter_fn(idx: Number): Unit {
                filterIdx.value = idx
            }
            val setFilter = ::gen_setFilter_fn
            fun gen_getCategoryIdx_fn(cat: String): Number {
                run {
                    var i: Number = 0
                    while(i < categories.length){
                        if (categories[i] == cat) {
                            return i
                        }
                        i++
                    }
                }
                return categories.length - 1
            }
            val getCategoryIdx = ::gen_getCategoryIdx_fn
            fun gen_getSourceText_fn(s: String): String {
                if (s == "llm") {
                    return "LLM 分类"
                }
                if (s == "user") {
                    return "手动设置"
                }
                return "待分类"
            }
            val getSourceText = ::gen_getSourceText_fn
            fun gen_getSourceClass_fn(s: String): String {
                if (s == "llm") {
                    return "tag-source-llm"
                }
                if (s == "user") {
                    return "tag-source-user"
                }
                return "tag-source-pending"
            }
            val getSourceClass = ::gen_getSourceClass_fn
            fun gen_refresh_fn(): Unit {
                try {
                    allTags.value = listAll()
                }
                 catch (err: Throwable) {
                    console.warn("[app-categories] refresh 异常: " + JSON.stringify(err), " at pages/settings/app-categories/index.uvue:103")
                    allTags.value = _uA()
                }
            }
            val refresh = ::gen_refresh_fn
            fun gen_onCategoryChange_fn(pkg: String, e: UTSJSONObject): Unit {
                try {
                    if (e == null) {
                        return
                    }
                    val detail = e["detail"]
                    if (detail == null) {
                        return
                    }
                    val detailObj = detail as UTSJSONObject
                    if (detailObj == null) {
                        return
                    }
                    val kVal = detailObj["value"]
                    if (UTSAndroid.`typeof`(kVal) !== "number") {
                        return
                    }
                    val idx: Number = (kVal as Number).toInt()
                    if (idx < 0 || idx >= categories.length) {
                        return
                    }
                    val cat = categories[idx] as AppCategory
                    manualSetCategory(pkg, cat)
                    refresh()
                    uni_showToast(ShowToastOptions(title = "已更新", icon = "none"))
                }
                 catch (err: Throwable) {
                    console.warn("[app-categories] onCategoryChange 异常: " + JSON.stringify(err), " at pages/settings/app-categories/index.uvue:124")
                }
            }
            val onCategoryChange = ::gen_onCategoryChange_fn
            fun gen_toggleLock_fn(pkg: String, locked: Boolean): Unit {
                try {
                    setUserLocked(pkg, locked)
                    refresh()
                }
                 catch (err: Throwable) {
                    console.warn("[app-categories] toggleLock 异常: " + JSON.stringify(err), " at pages/settings/app-categories/index.uvue:133")
                }
            }
            val toggleLock = ::gen_toggleLock_fn
            onPageShow(fun(){
                refresh()
            }
            )
            fun gen_onBack_fn(): Unit {
                uni_navigateBack(null)
            }
            val onBack = ::gen_onBack_fn
            return fun(): Any? {
                val _component_picker = resolveComponent("picker")
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "page-bg"), _uA(
                        _cE("image", _uM("class" to "page-bg-img", "src" to "/static/images/micro-habit-page-bg.png", "mode" to "aspectFill"))
                    )),
                    _cE("scroll-view", _uM("class" to "scroll-area", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "header-block"), _uA(
                            _cE("text", _uM("class" to "header-title"), "App 分类管理"),
                            _cE("text", _uM("class" to "header-desc"), "首次见到的 App 会由系统自动归类，你可在此手动调整。已锁定的分类 LLM 不会覆盖。")
                        )),
                        _cE("view", _uM("class" to "filter-row"), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(categories, fun(cat, ci, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("cat-" + ci), "class" to _nC(_uA(
                                    "filter-chip",
                                    if (unref(filterIdx) === ci) {
                                        "filter-chip-active"
                                    } else {
                                        ""
                                    }
                                )), "onClick" to fun(){
                                    setFilter(ci)
                                }
                                ), _uA(
                                    _cE("text", _uM("class" to "filter-chip-text"), _tD(cat), 1)
                                ), 10, _uA(
                                    "onClick"
                                ))
                            }
                            ), 64)
                        )),
                        if (unref(visibleTags).length < 1) {
                            _cE("view", _uM("key" to 0, "class" to "empty-block"), _uA(
                                _cE("text", _uM("class" to "empty-text"), "暂无数据"),
                                _cE("text", _uM("class" to "empty-hint"), "开启使用情况权限后，系统会自动记录你常用的 App")
                            ))
                        } else {
                            _cE("view", _uM("key" to 1, "class" to "tag-list"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(unref(visibleTags), fun(tag, ti, __index, _cached): Any {
                                    return _cE("view", _uM("key" to ("t-" + ti), "class" to "tag-card"), _uA(
                                        _cE("view", _uM("class" to "tag-card-left"), _uA(
                                            _cE("text", _uM("class" to "tag-label"), _tD(tag.appLabel), 1),
                                            _cE("text", _uM("class" to "tag-pkg"), _tD(tag.packageName), 1),
                                            _cE("view", _uM("class" to "tag-source-row"), _uA(
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "tag-source",
                                                    getSourceClass(tag.tagSource)
                                                ))), _tD(getSourceText(tag.tagSource)), 3),
                                                if (tag.totalUses > 0) {
                                                    _cE("text", _uM("key" to 0, "class" to "tag-uses"), "使用 " + _tD(tag.totalUses) + " 次", 1)
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "tag-card-right"), _uA(
                                            _cV(_component_picker, _uM("mode" to "selector", "range" to categories, "value" to getCategoryIdx(tag.category), "onChange" to fun(e: UTSJSONObject){
                                                return onCategoryChange(tag.packageName, e)
                                            }
                                            ), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                return _uA(
                                                    _cE("view", _uM("class" to "cat-picker"), _uA(
                                                        _cE("text", _uM("class" to "cat-picker-text"), _tD(tag.category), 1),
                                                        _cE("text", _uM("class" to "cat-picker-arrow"), "▾")
                                                    ))
                                                )
                                            }
                                            ), "_" to 2), 1032, _uA(
                                                "value",
                                                "onChange"
                                            )),
                                            _cE("view", _uM("class" to "lock-row", "onClick" to fun(){
                                                toggleLock(tag.packageName, !tag.isUserLocked)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "lock-text",
                                                    if (tag.isUserLocked) {
                                                        "lock-text-on"
                                                    } else {
                                                        ""
                                                    }
                                                ))), _tD(if (tag.isUserLocked) {
                                                    "🔒 已锁定"
                                                } else {
                                                    "🔓 未锁"
                                                }
                                                ), 3)
                                            ), 8, _uA(
                                                "onClick"
                                            ))
                                        ))
                                    ))
                                }
                                ), 128)
                            ))
                        }
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#162038", "backgroundImage" to "linear-gradient(180deg, #131832 0%, #283252 45%, #4F587D 100%)", "position" to "relative", "overflow" to "hidden")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "zIndex" to 1)), "bg-glow" to _pS(_uM("position" to "absolute", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "zIndex" to 0)), "glow-purple" to _pS(_uM("width" to 210, "height" to 210, "left" to -82, "top" to -54, "backgroundColor" to "#776B97", "opacity" to 0.5)), "glow-blue" to _pS(_uM("width" to 168, "height" to 168, "right" to -60, "top" to 104, "backgroundColor" to "#C2E0EE", "opacity" to 0.24)), "glow-pink" to _pS(_uM("width" to 178, "height" to 178, "left" to 42, "bottom" to 88, "backgroundColor" to "#C68DC0", "opacity" to 0.18)), "sky-star" to _pS(_uM("position" to "absolute", "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "rgba(255,255,255,0.74)", "zIndex" to 0)), "star-a" to _pS(_uM("left" to 36, "top" to 88)), "star-b" to _pS(_uM("left" to 118, "top" to 144, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-c" to _pS(_uM("right" to 52, "top" to 116)), "star-d" to _pS(_uM("right" to 26, "top" to 310, "width" to 5, "height" to 5, "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "star-e" to _pS(_uM("left" to 54, "top" to 520)), "star-f" to _pS(_uM("right" to 86, "top" to 420, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-g" to _pS(_uM("left" to 138, "top" to 610)), "star-h" to _pS(_uM("right" to 36, "top" to 720, "width" to 4, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-i" to _pS(_uM("left" to 76, "top" to 214, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-j" to _pS(_uM("right" to 148, "top" to 276, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "star-k" to _pS(_uM("left" to 178, "top" to 392, "width" to 2, "height" to 2, "borderTopLeftRadius" to 1, "borderTopRightRadius" to 1, "borderBottomRightRadius" to 1, "borderBottomLeftRadius" to 1)), "star-l" to _pS(_uM("right" to 64, "top" to 560, "width" to 3, "height" to 3, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "dashboard-hero" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 22, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 22, "paddingRight" to 18, "paddingBottom" to 22, "paddingLeft" to 18, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "flexDirection" to "row", "alignItems" to "center")), "settings-hero" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 22, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 22, "paddingRight" to 18, "paddingBottom" to 22, "paddingLeft" to 18, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "flexDirection" to "row", "alignItems" to "center")), "planet-note" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 0, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 16, "paddingRight" to 18, "paddingBottom" to 16, "paddingLeft" to 18, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24)), "section" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 12, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "overflow" to "hidden")), "settings-section" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 12, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "overflow" to "hidden")), "guard-box" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 8, "marginRight" to 16, "marginBottom" to 12, "marginLeft" to 16, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "alignItems" to "center")), "chart-container" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 10, "marginRight" to 16, "marginBottom" to 0, "marginLeft" to 16, "paddingTop" to 14, "paddingRight" to 0, "paddingBottom" to 6, "paddingLeft" to 0, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22)), "report-stats" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "flexDirection" to "row", "justifyContent" to "space-around", "marginTop" to 10, "marginRight" to 16, "marginBottom" to 10, "marginLeft" to 16, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "tag-card" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "marginBottom" to 10, "flexDirection" to "row")), "header-block" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 22, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 20, "paddingRight" to 18, "paddingBottom" to 20, "paddingLeft" to 18, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "flexDirection" to "column")), "filter-row" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 0, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 4, "paddingLeft" to 12, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22)), "empty-block" to _pS(_uM("backgroundColor" to "rgba(29,25,62,0.82)", "backgroundImage" to "linear-gradient(135deg, rgba(48, 42, 94, 0.92) 0%, rgba(84, 65, 124, 0.72) 56%, rgba(198, 141, 192, 0.18) 100%)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "rgba(219,200,237,0.18)", "borderRightColor" to "rgba(219,200,237,0.18)", "borderBottomColor" to "rgba(219,200,237,0.18)", "borderLeftColor" to "rgba(219,200,237,0.18)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "boxShadow" to "0 18px 42px rgba(5, 6, 20, 0.28)", "marginTop" to 12, "marginRight" to 14, "marginBottom" to 12, "marginLeft" to 14, "paddingTop" to 50, "paddingRight" to 20, "paddingBottom" to 50, "paddingLeft" to 20, "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "alignItems" to "center")), "dashboard-hero-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "paddingRight" to 12)), "settings-hero-copy" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column", "paddingRight" to 12)), "dashboard-kicker" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 13, "fontWeight" to "bold", "marginBottom" to 8)), "settings-kicker" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 13, "fontWeight" to "bold", "marginBottom" to 8)), "section-toggle" to _pS(_uM("color" to "#DBC8ED")), "setting-value" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 14, "fontWeight" to "bold")), "rule-priority" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 11, "marginLeft" to "auto")), "rule-source" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 11, "marginRight" to 8)), "detail-duration" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 14, "fontWeight" to "bold")), "guard-value" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 28, "fontWeight" to "bold", "marginTop" to 4)), "stat-val" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 20, "fontWeight" to "bold")), "dashboard-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 28, "fontWeight" to "bold", "lineHeight" to "34px")), "settings-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 28, "fontWeight" to "bold", "lineHeight" to "34px")), "section-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 18, "fontWeight" to "bold", "paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 0, "paddingLeft" to 0)), "setting-label" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 15, "fontWeight" to "bold")), "planet-note-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 16, "fontWeight" to "bold", "marginBottom" to 6)), "detail-name" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14)), "report-body" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14, "lineHeight" to "22px", "whiteSpace" to "pre-wrap", "marginTop" to 8)), "report-title" to _pS(_uM("color" to "#FFFFFF")), "header-title" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 22, "fontWeight" to "bold", "marginBottom" to 8)), "tag-label" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14, "fontWeight" to "bold", "marginBottom" to 3)), "dashboard-subtitle" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 13, "lineHeight" to "20px", "marginTop" to 8)), "settings-subtitle" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 13, "lineHeight" to "20px", "marginTop" to 8)), "planet-note-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "19px")), "setting-hint" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "18px", "marginTop" to 4)), "guard-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "guard-sub" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "chart-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "paddingLeft" to 14, "marginBottom" to 0, "fontSize" to 13, "fontWeight" to "bold")), "legend-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "loading-text" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "detail-type" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "marginTop" to 2)), "detail-time" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "marginTop" to 2)), "stat-label" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "rule-cond" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "rule-time" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 10)), "header-desc" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 12, "lineHeight" to "19px")), "tag-pkg" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "fontSize" to 11, "marginBottom" to 7)), "tag-uses" to _pS(_uM("color" to "rgba(255,255,255,0.72)")), "empty-hint" to _pS(_uM("color" to "rgba(255,255,255,0.72)", "alignItems" to "center", "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20)), "dashboard-planet" to _pS(_uM("position" to "relative", "width" to 86, "height" to 86, "borderTopLeftRadius" to 43, "borderTopRightRadius" to 43, "borderBottomRightRadius" to 43, "borderBottomLeftRadius" to 43, "backgroundColor" to "rgba(219,200,237,0.18)", "alignItems" to "center", "justifyContent" to "center")), "settings-planet" to _pS(_uM("position" to "relative", "width" to 86, "height" to 86, "borderTopLeftRadius" to 43, "borderTopRightRadius" to 43, "borderBottomRightRadius" to 43, "borderBottomLeftRadius" to 43, "backgroundColor" to "rgba(219,200,237,0.18)", "alignItems" to "center", "justifyContent" to "center")), "dashboard-orbit" to _pS(_uM("position" to "absolute", "width" to 96, "height" to 34, "borderTopLeftRadius" to 48, "borderTopRightRadius" to 48, "borderBottomRightRadius" to 48, "borderBottomLeftRadius" to 48, "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "transform" to "rotate(-20deg)")), "settings-orbit" to _pS(_uM("position" to "absolute", "width" to 96, "height" to 34, "borderTopLeftRadius" to 48, "borderTopRightRadius" to 48, "borderBottomRightRadius" to 48, "borderBottomLeftRadius" to 48, "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopColor" to "rgba(219,200,237,0.38)", "borderRightColor" to "rgba(219,200,237,0.38)", "borderBottomColor" to "rgba(219,200,237,0.38)", "borderLeftColor" to "rgba(219,200,237,0.38)", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "transform" to "rotate(-20deg)")), "dashboard-planet-text" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 34)), "settings-planet-text" to _pS(_uM("color" to "#DBC8ED", "fontSize" to 34)), "section-header" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 16, "paddingRight" to 18, "paddingBottom" to 8, "paddingLeft" to 18)), "section-body" to _pS(_uM("paddingTop" to 8, "paddingRight" to 0, "paddingBottom" to 14, "paddingLeft" to 0)), "list-section" to _pS(_uM("paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16)), "detail-item" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 10, "paddingRight" to 0, "paddingBottom" to 10, "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomColor" to "rgba(219,200,237,0.16)", "borderBottomStyle" to "solid")), "detail-left" to _pS(_uM("flexDirection" to "column")), "detail-right" to _pS(_uM("flexDirection" to "column", "alignItems" to "flex-end")), "setting-left" to _pS(_uM("flexDirection" to "column", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "heatmap-legend" to _pS(_uM("alignItems" to "center", "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20)), "loading-box" to _pS(_uM("alignItems" to "center", "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20)), "report-box" to _pS(_uM("paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16)), "stat-item" to _pS(_uM("alignItems" to "center")), "refresh-btn" to _pS(_uM("marginTop" to 12, "paddingTop" to 10, "paddingRight" to 24, "paddingBottom" to 10, "paddingLeft" to 24, "backgroundColor" to "#DBC8ED", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "refresh-text" to _pS(_uM("color" to "#161A33", "fontSize" to 14, "fontWeight" to "bold")), "setting-item" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 15, "paddingRight" to 18, "paddingBottom" to 15, "paddingLeft" to 18, "borderBottomWidth" to 1, "borderBottomColor" to "rgba(219,200,237,0.16)", "borderBottomStyle" to "solid")), "empty-rules" to _pS(_uM("alignItems" to "center", "paddingTop" to 24, "paddingRight" to 16, "paddingBottom" to 28, "paddingLeft" to 16)), "rule-card" to _pS(_uM("paddingTop" to 14, "paddingRight" to 18, "paddingBottom" to 14, "paddingLeft" to 18, "borderBottomWidth" to 1, "borderBottomColor" to "rgba(219,200,237,0.16)", "borderBottomStyle" to "solid")), "rule-row1" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 4)), "rule-row2" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 4)), "rule-type" to _pS(_uM("fontSize" to 11, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 4, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8, "marginRight" to 6, "color" to "#FFFFFF", "backgroundColor" to "rgba(219,200,237,0.18)", "fontWeight" to "bold")), "rule-trigger" to _pS(_uM("fontSize" to 11, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 4, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8, "marginRight" to 6, "color" to "rgba(255,255,255,0.72)", "backgroundColor" to "rgba(194,224,238,0.12)")), "rule-row3" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "marginTop" to 4)), "filter-chip" to _pS(_uM("paddingTop" to 6, "paddingRight" to 12, "paddingBottom" to 6, "paddingLeft" to 12, "backgroundColor" to "rgba(255,255,255,0.1)", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "marginRight" to 6, "marginBottom" to 8)), "filter-chip-active" to _pS(_uM("backgroundColor" to "#DBC8ED")))
            }
        val styles1: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("filter-chip-text" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF")), "tag-list" to _pS(_uM("paddingTop" to 0, "paddingRight" to 14, "paddingBottom" to 18, "paddingLeft" to 14)), "tag-card-left" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "tag-card-right" to _pS(_uM("flexDirection" to "column", "alignItems" to "flex-end", "marginLeft" to 10)), "tag-source-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "tag-source" to _pS(_uM("fontSize" to 10, "paddingTop" to 3, "paddingRight" to 7, "paddingBottom" to 3, "paddingLeft" to 7, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "marginRight" to 6)), "tag-source-llm" to _pS(_uM("backgroundColor" to "rgba(194,224,238,0.14)", "color" to "#C2E0EE")), "tag-source-user" to _pS(_uM("backgroundColor" to "rgba(143,240,195,0.12)", "color" to "#8FF0C3")), "tag-source-pending" to _pS(_uM("backgroundColor" to "rgba(219,200,237,0.14)", "color" to "#DBC8ED")), "cat-picker" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "rgba(255,255,255,0.1)", "paddingTop" to 5, "paddingRight" to 10, "paddingBottom" to 5, "paddingLeft" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "marginBottom" to 6)), "cat-picker-text" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF", "marginRight" to 4)), "cat-picker-arrow" to _pS(_uM("color" to "rgba(255,255,255,0.68)")), "lock-text" to _pS(_uM("color" to "rgba(255,255,255,0.68)")), "lock-text-on" to _pS(_uM("color" to "#DBC8ED", "fontWeight" to "bold")), "bottom-spacer" to _pS(_uM("height" to 132)), "page-bg" to _pS(_uM("display" to "none")), "page-bg-img" to _pS(_uM("display" to "none")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
