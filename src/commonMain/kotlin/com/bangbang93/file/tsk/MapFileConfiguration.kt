package com.bangbang93.file.tsk

/**
 * Map File Configuration
 */
class MapFileConfiguration(value: String) {
    private val value: String = value
    private val bitSet: MutableList<Int>

    init {
        require(value.all { it == '0' || it == '1' }) {
            "MapFileConfiguration value must contain only binary digits ('0' or '1'): \"$value\""
        }
        // Convert value to binary representation and reverse, padding to at least 6 bits
        this.bitSet = value.map { it.digitToInt() }.reversed().toMutableList().apply {
            while (size < 6) {
                add(0)
            }
        }
    }

    fun getValue(): String {
        // Return current bitSet state, reversed and as string
        return bitSet.reversed().joinToString("") { it.toString() }
    }

    fun getRawData(): Int {
        val reversed = bitSet.reversed()
        return reversed.joinToString("").toInt(2)
    }

    fun headerInformation(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[0] = bitValue
        }
        return bitSet[0]
    }

    fun testResultInformationPerDie(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[1] = bitValue
        }
        return bitSet[1]
    }

    fun lineCategoryInformation(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[2] = bitValue
        }
        return bitSet[2]
    }

    fun extensionHeaderInformation(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[3] = bitValue
        }
        return bitSet[3]
    }

    fun testResultInformationPerExtensionDie(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[4] = bitValue
        }
        return bitSet[4]
    }

    fun extensionLineCategoryInformation(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[5] = bitValue
        }
        return bitSet[5]
    }
}
