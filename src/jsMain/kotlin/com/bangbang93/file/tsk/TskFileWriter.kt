package com.bangbang93.file.tsk

/**
 * Node.js-specific file writing utility
 */
object TskFileWriter {
    /**
     * Generate and write a TSK file to the specified path
     */
    fun writeFile(mapFile: TskMapFile, filePath: String) {
        val generator = TskGenerator()
        val data = generator.generate(mapFile)
        writeFileSync(filePath, data)
    }

    /**
     * Generate TSK file as byte array
     */
    fun writeBytes(mapFile: TskMapFile): ByteArray {
        val generator = TskGenerator()
        return generator.generate(mapFile)
    }
}
