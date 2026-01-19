package com.bangbang93.file.tsk

/**
 * Node.js-specific file reading utility
 */
object TskFileReader {
    /**
     * Parse a TSK file from a file path
     */
    fun parseFile(filePath: String): TskMapFile {
        val data = readFileSync(filePath)
        val parser = TskParser(data)
        return parser.mapFile
    }

    /**
     * Parse a TSK file from byte array
     */
    fun parseBytes(data: ByteArray): TskMapFile {
        val parser = TskParser(data)
        return parser.mapFile
    }
}
