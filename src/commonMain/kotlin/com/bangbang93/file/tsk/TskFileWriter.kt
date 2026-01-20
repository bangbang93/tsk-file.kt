package com.bangbang93.file.tsk

/**
 * TSK file writer utility for generating TSK files as byte arrays
 */
object TskFileWriter {
    /**
     * Generate TSK file as byte array
     */
    fun writeBytes(mapFile: TskMapFile): ByteArray {
        val generator = TskGenerator()
        return generator.generate(mapFile)
    }
}
