package app.aaps.pump.medtronic.keys

import app.aaps.core.keys.PreferenceType
import app.aaps.core.keys.interfaces.IntPreferenceKey
import app.aaps.core.keys.interfaces.TextRef
import app.aaps.pump.medtronic.R

enum class MedtronicIntPreferenceKey(
    override val key: String,
    override val defaultValue: Int,
    private val titleResId: Int,
    private val summaryResId: Int? = null,
    override val preferenceType: PreferenceType = PreferenceType.TEXT_FIELD,
    private val entriesResIds: Map<Int, Int> = emptyMap(),
    override val min: Int = Int.MIN_VALUE,
    override val max: Int = Int.MAX_VALUE,
) : IntPreferenceKey {

    MaxBasal(
        key = "pref_medtronic_max_basal",
        defaultValue = 35,
        titleResId = R.string.medtronic_pump_max_basal,
        min = 1,
        max = 35
    ),
    MaxBolus(
        key = "pref_medtronic_max_bolus",
        defaultValue = 25,
        titleResId = R.string.medtronic_pump_max_bolus,
        min = 1,
        max = 25
    ),
    ;

    override val title: TextRef = TextRef.AndroidRes(titleResId)
    override val entries: Map<Int, TextRef> = entriesResIds.mapValues { TextRef.AndroidRes(it.value) }
    override val summary: TextRef? = summaryResId?.let { TextRef.AndroidRes(it) }
}
