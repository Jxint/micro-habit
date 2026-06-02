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
open class GenCloudObjLlmRuleSuggest : InternalUniCloudCloudObjectCaller {
    constructor(obj: InternalUniCloudCloudObject) : super(obj)
    open fun main(vararg do_not_transform_spread: Any?): UTSPromise<UTSJSONObject> {
        return _obj.callMethod("main", _getArgs(*do_not_transform_spread))
    }
    @JvmName("main1")
    inline fun <reified T> main(vararg do_not_transform_spread: Any?): UTSPromise<T> {
        return _obj.callMethod<T>("main", _getArgs(*do_not_transform_spread))
    }
}
