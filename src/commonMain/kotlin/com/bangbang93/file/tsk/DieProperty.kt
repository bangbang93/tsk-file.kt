package com.bangbang93.file.tsk

/**
 * Die property enumeration
 */
enum class DieProperty(val value: Int) {
    /** Skip Die */
    SKIP(0),
    /** Probing Die */
    PROBING(1),
    /** Compulsory Marking Die */
    COMPULSORY_MARKING(2);

    companion object {
        fun fromValue(value: Int): DieProperty {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Invalid DieProperty value: $value")
        }
        
        /**
         * Safely convert an integer value to DieProperty, returning SKIP for invalid values
         */
        fun fromValueOrDefault(value: Int): DieProperty {
            return entries.find { it.value == value } ?: SKIP
        }
    }
}
