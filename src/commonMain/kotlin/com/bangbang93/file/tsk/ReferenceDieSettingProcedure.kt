package com.bangbang93.file.tsk

/**
 * Reference die setting procedure enumeration
 */
enum class ReferenceDieSettingProcedure(val value: Int) {
    /** Wafer center die */
    WAFER_CENTER_DIE(1),
    /** Teaching die */
    TEACHING_DIE(2),
    /** Target sense die */
    TARGET_SENSE_DIE(3);

    companion object {
        fun fromValue(value: Int): ReferenceDieSettingProcedure {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Invalid ReferenceDieSettingProcedure value: $value")
        }
    }
}
