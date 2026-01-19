package com.bangbang93.file.tsk

/**
 * Binary Writer for writing TSK file data
 */
class TskBinaryWriter {
    private val buffer = mutableListOf<Byte>()
    var openLog: Boolean = false

    fun writeAsBytes(bytes: ByteArray) {
        if (openLog) {
            println(byteToHexString(bytes))
        }
        buffer.addAll(bytes.toList())
    }

    /**
     * Write integer value as bytes
     * @param intValue The integer value to write
     * @param bytesNumber Number of bytes to write (1, 2, or 4)
     */
    fun writeAsInt(intValue: Int, bytesNumber: Int) {
        when (bytesNumber) {
            1 -> writeAsBytes(byteArrayOf(intValue.toByte()))
            2 -> {
                val bytes = byteArrayOf(
                    ((intValue shr 8) and 0xFF).toByte(),
                    (intValue and 0xFF).toByte()
                )
                writeAsBytes(bytes)
            }
            4 -> {
                val bytes = byteArrayOf(
                    ((intValue shr 24) and 0xFF).toByte(),
                    ((intValue shr 16) and 0xFF).toByte(),
                    ((intValue shr 8) and 0xFF).toByte(),
                    (intValue and 0xFF).toByte()
                )
                writeAsBytes(bytes)
            }
            else -> throw IllegalArgumentException("Unsupported byte number: $bytesNumber")
        }
    }

    /**
     * Write string as bytes with fixed length
     * @param stringValue The string to write
     * @param length Fixed length, will be padded with spaces if needed
     */
    fun writeAsString(stringValue: String, length: Int) {
        val truncated = if (stringValue.length > length) {
            stringValue.substring(0, length)
        } else {
            stringValue.padEnd(length, ' ')
        }
        val bytes = truncated.encodeToByteArray()
        writeAsBytes(bytes)
    }

    /**
     * Get the complete buffer as ByteArray
     */
    fun toByteArray(): ByteArray = buffer.toByteArray()

    /**
     * Clear the buffer
     */
    fun clear() {
        buffer.clear()
    }

    companion object {
        /**
         * Convert byte array to hex string
         */
        fun byteToHexString(data: ByteArray): String {
            return data.joinToString("") { byte ->
                val value = byte.toInt() and 0xFF
                value.toString(16).padStart(2, '0')
            }
        }

        /**
         * Convert binary string to bytes
         * Example: "11010001" => 0xd1
         */
        fun binaryStringToBytes(values: String): ByteArray {
            var value = values
            if (value.startsWith("0x")) {
                value = value.substring(2)
            }
            // Pad to multiple of 8
            if (value.length % 8 != 0) {
                value = value.padStart(value.length + (8 - value.length % 8), '0')
            }

            val bytes = ByteArray(value.length / 8)
            for (i in bytes.indices) {
                val substr = value.substring(i * 8, i * 8 + 8)
                bytes[i] = substr.toInt(2).toByte()
            }
            return bytes
        }

        /**
         * Convert hex string to bytes
         * Example: "aa00" => [0xaa, 0x00]
         */
        fun hexStringToBytes(hs: String): ByteArray {
            var hex = hs
            if (hex.startsWith("0x")) {
                hex = hex.substring(2)
            }

            val bytes = ByteArray(hex.length / 2)
            for (i in bytes.indices) {
                val substr = hex.substring(i * 2, i * 2 + 2)
                bytes[i] = substr.toInt(16).toByte()
            }
            return bytes
        }
    }
}
