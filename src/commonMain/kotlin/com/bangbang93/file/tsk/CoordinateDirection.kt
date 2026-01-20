package com.bangbang93.file.tsk

/**
 * Coordinate direction enumeration
 */
enum class CoordinateDirection(val value: Int) {
    /** Leftward or Forward (depending on axis) */
    DIRECTION_1(1),
    /** Rightward or Backward (depending on axis) */
    DIRECTION_2(2);

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
