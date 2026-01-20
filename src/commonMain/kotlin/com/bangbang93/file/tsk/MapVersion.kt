package com.bangbang93.file.tsk

/**
 * Map version enumeration
 */
enum class MapVersion(val value: Int) {
    /** Normal */
    NORMAL(0),
    /** 250,000 Chips */
    CHIPS_250K(1),
    /** 256 Multi-sites (currently only this is supported) */
    MULTI_SITES_256(2),
    /** 256 Multi-sites (without extended header information) */
    MULTI_SITES_256_NO_EXT(3),
    /** 1024 category */
    CATEGORY_1024(4);

    companion object {
        fun fromValue(value: Int): MapVersion {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Invalid MapVersion value: $value")
        }
    }
}
