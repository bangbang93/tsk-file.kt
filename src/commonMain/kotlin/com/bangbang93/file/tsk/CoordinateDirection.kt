package com.bangbang93.file.tsk

/**
 * Coordinate direction enumeration.
 *
 * The semantic meaning depends on the axis:
 * - NEGATIVE: leftward (X) or forward (Y)
 * - POSITIVE: rightward (X) or backward (Y)
 */
enum class CoordinateDirection(val value: Int) {
    /** Negative axis direction: leftward (X) or forward (Y) */
    NEGATIVE(1),
    /** Positive axis direction: rightward (X) or backward (Y) */
    POSITIVE(2);

    companion object {
        fun fromValue(value: Int): CoordinateDirection {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Invalid CoordinateDirection value: $value")
        }
        
        /**
         * Safely convert an integer value to CoordinateDirection, returning null for invalid values (0 or unknown)
         */
        fun fromValueOrNull(value: Int): CoordinateDirection? {
            return entries.find { it.value == value }
        }
    }
}
