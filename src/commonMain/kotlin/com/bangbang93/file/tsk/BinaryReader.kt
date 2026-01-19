package com.bangbang93.file.tsk

/**
 * Binary Reader for reading TSK file data
 */
class BinaryReader(private val buffer: ByteArray) {
    private var position: Int = 0

    fun readAsBytes(bytes: Int, preventAutoForward: Boolean = false): ByteArray {
        require(position + bytes <= buffer.size) {
            "Cannot read $bytes bytes from position $position (buffer size: ${buffer.size})"
        }
        val ret = buffer.copyOfRange(position, position + bytes)
        if (!preventAutoForward) {
            position += bytes
        }
        return ret
    }

    fun readAsString(bytes: Int, preventAutoForward: Boolean = false): String {
        return readAsBytes(bytes, preventAutoForward).decodeToString().trim('\u0000')
    }

    fun readAsHex(bytes: Int, preventAutoForward: Boolean = false): String {
        return readAsBytes(bytes, preventAutoForward).joinToString("") { byte ->
            val value = byte.toInt() and 0xFF
            value.toString(16).padStart(2, '0')
        }
    }

    fun readAsInt(bytes: Int, preventAutoForward: Boolean = false): Int {
        val hex = readAsHex(bytes, preventAutoForward)
        return hex.toInt(16)
    }

    fun readAsBit(bytesCount: Int, preventAutoForward: Boolean = false): String {
        // Convert to binary string format
        // 2 => "10"
        // 4 => "100"
        return readAsInt(bytesCount, preventAutoForward).toString(2)
    }

    fun skip(bytesToSkip: Int) {
        position += bytesToSkip
    }

    fun goto(position: Int) {
        require(position >= 0 && position <= buffer.size) {
            "Position $position is out of bounds for buffer of size ${buffer.size}"
        }
        this.position = position
    }

    fun getPosition(): Int = position
}
