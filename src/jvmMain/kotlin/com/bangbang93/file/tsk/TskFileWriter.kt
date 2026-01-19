package com.bangbang93.file.tsk

import java.io.File

/**
 * JVM-specific file writing utility
 */
object TskFileWriter {
    /**
     * Generate and write a TSK file to the specified path
     */
    fun writeFile(mapFile: TskMapFile, filePath: String) {
        val generator = TskGenerator()
        val data = generator.generate(mapFile)
        File(filePath).writeBytes(data)
    }

    /**
     * Generate TSK file as byte array
     */
    fun writeBytes(mapFile: TskMapFile): ByteArray {
        val generator = TskGenerator()
        return generator.generate(mapFile)
    }
}
