package com.bangbang93.file.tsk

/**
 * TSK Map File
 */
data class TskMapFile(
    var header: MapFileHeader? = null,
    var dieResults: List<TestResultPerDie> = emptyList()
)
