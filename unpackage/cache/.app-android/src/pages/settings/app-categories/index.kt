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
                        _cE("image", _uM("class" to "page-bg-img", "src" to "/static/images/dream-gradient-bg.png", "mode" to "aspectFill"))
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
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "rgba(0,0,0,0)", "position" to "relative", "overflow" to "hidden")), "page-bg" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "width" to "100%", "height" to "100%", "zIndex" to 0)), "page-bg-img" to _pS(_uM("width" to "100%", "height" to "100%")), "scroll-area" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "zIndex" to 1)), "header-block" to _pS(_uM("paddingTop" to 20, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "backgroundColor" to "#2C3E50", "flexDirection" to "column")), "header-title" to _pS(_uM("fontSize" to 18, "fontWeight" to "bold", "color" to "#FFFFFF", "marginBottom" to 6)), "header-desc" to _pS(_uM("fontSize" to 12, "color" to "rgba(255,255,255,0.8)", "lineHeight" to 1.6)), "filter-row" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12, "backgroundColor" to "#FFFFFF")), "filter-chip" to _pS(_uM("paddingTop" to 6, "paddingRight" to 12, "paddingBottom" to 6, "paddingLeft" to 12, "backgroundColor" to "#ECF0F1", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "marginRight" to 6, "marginBottom" to 8)), "filter-chip-active" to _pS(_uM("backgroundColor" to "#3498DB")), "filter-chip-text" to _uM("" to _uM("fontSize" to 12, "color" to "#34495E"), ".filter-chip-active " to _uM("color" to "#FFFFFF", "fontWeight" to "bold")), "empty-block" to _pS(_uM("paddingTop" to 60, "paddingRight" to 20, "paddingBottom" to 60, "paddingLeft" to 20, "alignItems" to "center")), "empty-text" to _pS(_uM("fontSize" to 14, "color" to "#7F8C8D", "marginBottom" to 6)), "empty-hint" to _pS(_uM("fontSize" to 12, "color" to "#95A5A6", "textAlign" to "center")), "tag-list" to _pS(_uM("paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12)), "tag-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 8, "flexDirection" to "row")), "tag-card-left" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "column")), "tag-card-right" to _pS(_uM("flexDirection" to "column", "alignItems" to "flex-end", "marginLeft" to 10)), "tag-label" to _pS(_uM("fontSize" to 14, "fontWeight" to "bold", "color" to "#2C3E50", "marginBottom" to 2)), "tag-pkg" to _pS(_uM("fontSize" to 11, "color" to "#95A5A6", "marginBottom" to 6)), "tag-source-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "tag-source" to _pS(_uM("fontSize" to 10, "paddingTop" to 2, "paddingRight" to 6, "paddingBottom" to 2, "paddingLeft" to 6, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "marginRight" to 6)), "tag-source-llm" to _pS(_uM("backgroundColor" to "rgba(52,152,219,0.15)", "color" to "#3498DB")), "tag-source-user" to _pS(_uM("backgroundColor" to "rgba(46,204,113,0.15)", "color" to "#27AE60")), "tag-source-pending" to _pS(_uM("backgroundColor" to "rgba(243,156,18,0.15)", "color" to "#E67E22")), "tag-uses" to _pS(_uM("fontSize" to 10, "color" to "#95A5A6")), "cat-picker" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#ECF0F1", "paddingTop" to 4, "paddingRight" to 10, "paddingBottom" to 4, "paddingLeft" to 10, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "marginBottom" to 4)), "cat-picker-text" to _pS(_uM("fontSize" to 12, "color" to "#2C3E50", "marginRight" to 4)), "cat-picker-arrow" to _pS(_uM("fontSize" to 10, "color" to "#95A5A6")), "lock-row" to _pS(_uM("paddingTop" to 2, "paddingRight" to 0, "paddingBottom" to 2, "paddingLeft" to 0)), "lock-text" to _pS(_uM("fontSize" to 10, "color" to "#95A5A6")), "lock-text-on" to _pS(_uM("color" to "#E74C3C", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
