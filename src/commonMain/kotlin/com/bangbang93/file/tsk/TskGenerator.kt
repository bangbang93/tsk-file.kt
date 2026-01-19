package com.bangbang93.file.tsk

/**
 * TSK File Generator for creating TSK files from TskMapFile data
 */
class TskGenerator {
    private val bw = TskBinaryWriter()

    /**
     * Generate TSK file data from TskMapFile
     * @param tskMapFile The TSK map file data to convert
     * @return ByteArray containing the complete TSK file data
     */
    fun generate(tskMapFile: TskMapFile): ByteArray {
        bw.clear()
        
        val header = tskMapFile.header
            ?: throw IllegalArgumentException("TskMapFile header cannot be null")
        
        writeMapFileHeader(header)
        convertTestResultsToBinary(tskMapFile.dieResults)

        // Add 8-byte padding after each die result (as per TSK file format specification)
        for (die in tskMapFile.dieResults) {
            bw.writeAsBytes(ByteArray(8) { 0 })
        }

        return bw.toByteArray()
    }

    private fun writeMapFileHeader(header: MapFileHeader) {
        bw.writeAsString(header.operatorName, 20)
        bw.writeAsString(header.deviceName, 16)
        bw.writeAsInt(header.waferSize, 2)
        bw.writeAsInt(header.machineNo, 2)
        bw.writeAsInt(header.indexSizeX, 4)
        bw.writeAsInt(header.indexSizeY, 4)
        bw.writeAsInt(header.standardOrientationFlatDirection, 2)
        bw.writeAsInt(header.finalEditingMachineType, 1)
        bw.writeAsInt(header.mapVersion, 1)
        bw.writeAsInt(header.mapDataAreaRowSize, 2)
        bw.writeAsInt(header.mapDataAreaLineSize, 2)
        bw.writeAsInt(header.mapDataForm, 4)
        bw.writeAsString(header.waferId, 21)
        bw.writeAsInt(header.numberOfProbing, 1)
        bw.writeAsString(header.lotNo, 18)
        bw.writeAsInt(header.cassetteNo, 2)
        bw.writeAsInt(header.slotNo, 2)
        bw.writeAsInt(header.xCoordinatesIncreaseDirection, 1)
        bw.writeAsInt(header.yCoordinatesIncreaseDirection, 1)
        bw.writeAsInt(header.referenceDieSettingProcedures, 1)
        bw.writeAsInt(header.reserved, 1)
        bw.writeAsInt(header.targetDiePositionX.toInt(), 4)
        bw.writeAsInt(header.targetDiePositionY.toInt(), 4)
        bw.writeAsInt(header.referenceDieCoordinatorX, 2)
        bw.writeAsInt(header.referenceDieCoordinatorY, 2)
        bw.writeAsInt(header.probingStartPosition, 1)
        bw.writeAsInt(header.probingDirection, 1)
        bw.writeAsBytes(ByteArray(2) { 0 }) // Reserved 2 bytes
        bw.writeAsInt(header.distanceXtoWaferCenterDieOrigin.toInt(), 4)
        bw.writeAsInt(header.distanceYtoWaferCenterDieOrigin.toInt(), 4)
        bw.writeAsInt(header.coordinatorXofWaferCenterDie, 4)
        bw.writeAsInt(header.coordinatorYofWaferCenterDie, 4)
        bw.writeAsInt(header.firstDieCoordinatorX.toInt(), 4)
        bw.writeAsInt(header.firstDieCoordinatorY.toInt(), 4)
        bw.writeAsString(header.startYear, 2)
        bw.writeAsString(header.startMonth, 2)
        bw.writeAsString(header.startDay, 2)
        bw.writeAsString(header.startHour, 2)
        bw.writeAsString(header.startMinute, 2)
        bw.writeAsBytes(header.reserved1)
        bw.writeAsString(header.endYear, 2)
        bw.writeAsString(header.endMonth, 2)
        bw.writeAsString(header.endDay, 2)
        bw.writeAsString(header.endHour, 2)
        bw.writeAsString(header.endMinute, 2)
        bw.writeAsBytes(header.reserved2)
        bw.writeAsString(header.loadYear, 2)
        bw.writeAsString(header.loadMonth, 2)
        bw.writeAsString(header.loadDay, 2)
        bw.writeAsString(header.loadHour, 2)
        bw.writeAsString(header.loadMinute, 2)
        bw.writeAsBytes(header.reserved3)
        bw.writeAsString(header.unloadYear, 2)
        bw.writeAsString(header.unloadMonth, 2)
        bw.writeAsString(header.unloadDay, 2)
        bw.writeAsString(header.unloadHour, 2)
        bw.writeAsString(header.unloadMinute, 2)
        bw.writeAsBytes(header.reserved4)
        bw.writeAsString(header.vegaMachineNo, 4)
        bw.writeAsString(header.vegaMachineNo2, 4)
        bw.writeAsString(header.specialCharacters, 4)
        bw.writeAsInt(header.testingEndInformation, 1)
        bw.writeAsBytes(header.reserved5)
        bw.writeAsInt(header.totalTestedDice, 2)
        bw.writeAsInt(header.totalPassDice, 2)
        bw.writeAsInt(header.totalFailDice, 2)
        bw.writeAsInt(header.testDieInformationAddress, 4)

        bw.openLog = true
        bw.writeAsInt(header.numberOfLineCategoryData, 4)
        bw.writeAsInt(header.lineCategoryAddress, 4)

        // Write MapFileConfiguration - always 2 bytes
        val config = header.mapFileConfiguration
        if (config != null) {
            val configBytes = TskBinaryWriter.binaryStringToBytes(config.getValue())
            // Ensure exactly 2 bytes
            if (configBytes.size >= 2) {
                bw.writeAsBytes(configBytes.copyOfRange(0, 2))
            } else {
                bw.writeAsBytes(configBytes + ByteArray(2 - configBytes.size) { 0 })
            }
        } else {
            bw.writeAsBytes(ByteArray(2) { 0 })
        }

        bw.writeAsInt(header.maxMultiSite, 2)
        bw.writeAsInt(header.maxCategories, 2)
        bw.writeAsBytes(header.reserved6)

        bw.openLog = false
        
        // Calculate and add padding to make header exactly 256 bytes
        // This ensures die data starts at the standard offset
        val currentSize = bw.toByteArray().size
        val targetHeaderSize = 256
        val paddingSize = targetHeaderSize - currentSize
        if (paddingSize > 0) {
            bw.writeAsBytes(ByteArray(paddingSize) { 0 })
        }
    }

    private fun convertTestResultsToBinary(results: List<TestResultPerDie>) {
        for (die in results) {
            var word = (die.dieTestResult shl 14) or
                       (die.marking shl 13) or
                       (die.failMarkInspection shl 12) or
                       (die.reProbingResult shl 10) or
                       (die.needleMarkInspectionResult shl 9) or
                       die.dieCoordinatorValueX
            bw.writeAsInt(word and 0xFFFF, 2)

            word = (die.dieProperty shl 14) or
                   (die.needleMarkInspectionExecutionDieSelection shl 13) or
                   (die.samplingDie shl 12) or
                   (die.codeBitOfCoordinatorValueX shl 11) or
                   (die.codeBitOfCoordinatorValueY shl 10) or
                   (die.dummyData shl 9) or
                   die.dieCoordinatorValueY
            bw.writeAsInt(word and 0xFFFF, 2)

            word = (die.measurementFinish shl 15) or
                   (die.rejectChipFlag shl 14) or
                   (die.testExecutionSite shl 8) or
                   (die.blockAreaJudgmentFunction shl 6) or
                   die.categoryData
            bw.writeAsInt(word and 0xFFFF, 2)
        }
    }
}
