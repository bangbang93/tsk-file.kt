package com.bangbang93.file.tsk

/**
 * TSK file reader utility for parsing TSK files from byte arrays
 */
object TskFileReader {
    /**
     * Parse a TSK file from byte array
     */
    fun parseBytes(data: ByteArray): TskMapFile {
        val parser = TskParser(data)
        return parser.mapFile
    }
}
