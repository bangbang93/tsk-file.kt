package com.bangbang93.file.tsk

/**
 * TSK Parser for parsing A-PM-90A/UF Series Map Data Files
 */
class TskParser(data: ByteArray) {
    val mapFile: TskMapFile

    init {
        val br = BinaryReader(data)
        mapFile = TskMapFile()
        mapFile.header = loadMapFileHeader(br)

        when (mapFile.header?.mapVersion) {
            MapVersion.MULTI_SITES_256 -> {
                br.goto(mapFile.header?.testDieInformationAddress ?: 0)
                val dieCount = (mapFile.header?.mapDataAreaRowSize ?: 0) * 
                               (mapFile.header?.mapDataAreaLineSize ?: 0)
                mapFile.dieResults = loadTestResults(br, dieCount)
                if (mapFile.header?.mapFileConfiguration?.extensionHeaderInformation(0) != 0) {
                    // Handle extension header
                }
            }
            MapVersion.NORMAL, MapVersion.CHIPS_250K, MapVersion.MULTI_SITES_256_NO_EXT, MapVersion.CATEGORY_1024 -> {
                // These map versions are not yet fully implemented
                // For now, we parse the header but skip die results
            }
            null -> {
                // Header is null, cannot proceed with parsing
            }
        }
    }

    private fun loadMapFileHeader(br: BinaryReader): MapFileHeader {
        val header = MapFileHeader()
        header.operatorName = br.readAsString(20)
        header.deviceName = br.readAsString(16)
        header.waferSize = br.readAsInt(2)
        header.machineNo = br.readAsInt(2)
        header.indexSizeX = br.readAsInt(4)
        header.indexSizeY = br.readAsInt(4)
        header.standardOrientationFlatDirection = br.readAsInt(2)
        header.finalEditingMachineType = br.readAsInt(1)
        header.mapVersion = MapVersion.fromValueOrDefault(br.readAsInt(1))
        header.mapDataAreaRowSize = br.readAsInt(2)
        header.mapDataAreaLineSize = br.readAsInt(2)
        header.mapDataForm = br.readAsInt(4)
        header.waferId = br.readAsString(21)
        header.numberOfProbing = br.readAsInt(1)
        header.lotNo = br.readAsString(18)
        header.cassetteNo = br.readAsInt(2)
        header.slotNo = br.readAsInt(2)
        header.xCoordinatesIncreaseDirection = CoordinateDirection.fromValueOrNull(br.readAsInt(1))
        header.yCoordinatesIncreaseDirection = CoordinateDirection.fromValueOrNull(br.readAsInt(1))
        header.referenceDieSettingProcedures = ReferenceDieSettingProcedure.fromValueOrNull(br.readAsInt(1))
        header.reserved = br.readAsInt(1)
        header.targetDiePositionX = br.readAsInt(4).toUInt()
        header.targetDiePositionY = br.readAsInt(4).toUInt()
        header.referenceDieCoordinatorX = br.readAsInt(2)
        header.referenceDieCoordinatorY = br.readAsInt(2)
        header.probingStartPosition = br.readAsInt(1)
        header.probingDirection = br.readAsInt(1)
        br.skip(2) // Skip 2 reserved bytes
        header.distanceXtoWaferCenterDieOrigin = br.readAsInt(4).toUInt()
        header.distanceYtoWaferCenterDieOrigin = br.readAsInt(4).toUInt()
        header.coordinatorXofWaferCenterDie = br.readAsInt(4)
        header.coordinatorYofWaferCenterDie = br.readAsInt(4)
        header.firstDieCoordinatorX = br.readAsInt(4).toUInt()
        header.firstDieCoordinatorY = br.readAsInt(4).toUInt()
        header.startYear = br.readAsString(2)
        header.startMonth = br.readAsString(2)
        header.startDay = br.readAsString(2)
        header.startHour = br.readAsString(2)
        header.startMinute = br.readAsString(2)
        header.reserved1 = br.readAsBytes(2)
        header.endYear = br.readAsString(2)
        header.endMonth = br.readAsString(2)
        header.endDay = br.readAsString(2)
        header.endHour = br.readAsString(2)
        header.endMinute = br.readAsString(2)
        header.reserved2 = br.readAsBytes(2)
        header.loadYear = br.readAsString(2)
        header.loadMonth = br.readAsString(2)
        header.loadDay = br.readAsString(2)
        header.loadHour = br.readAsString(2)
        header.loadMinute = br.readAsString(2)
        header.reserved3 = br.readAsBytes(2)
        header.unloadYear = br.readAsString(2)
        header.unloadMonth = br.readAsString(2)
        header.unloadDay = br.readAsString(2)
        header.unloadHour = br.readAsString(2)
        header.unloadMinute = br.readAsString(2)
        header.reserved4 = br.readAsBytes(2)
        header.vegaMachineNo = br.readAsString(4)
        header.vegaMachineNo2 = br.readAsString(4)
        header.specialCharacters = br.readAsString(4)
        header.testingEndInformation = br.readAsInt(1)
        header.reserved5 = br.readAsBytes(1)
        header.totalTestedDice = br.readAsInt(2)
        header.totalPassDice = br.readAsInt(2)
        header.totalFailDice = br.readAsInt(2)
        header.testDieInformationAddress = br.readAsInt(4)
        header.numberOfLineCategoryData = br.readAsInt(4)
        header.lineCategoryAddress = br.readAsInt(4)
        header.mapFileConfiguration = MapFileConfiguration(br.readAsBit(2))
        header.maxMultiSite = br.readAsInt(2)
        header.maxCategories = br.readAsInt(2)
        header.reserved6 = br.readAsBytes(2)
        return header
    }

    private fun loadTestResults(br: BinaryReader, dieCount: Int): List<TestResultPerDie> {
        val results = mutableListOf<TestResultPerDie>()
        for (i in 0 until dieCount) {
            val die = TestResultPerDie()
            var word = br.readAsInt(2)
            die.dieTestResult = DieTestResult.fromValueOrDefault(word shr 14)
            die.marking = (word shr 13) and 1
            die.failMarkInspection = (word shr 12) and 1
            die.reProbingResult = (word shr 10) and 3
            die.needleMarkInspectionResult = (word shr 9) and 1
            die.dieCoordinatorValueX = word and 511
            
            word = br.readAsInt(2)
            die.dieProperty = DieProperty.fromValueOrDefault(word shr 14)
            die.needleMarkInspectionExecutionDieSelection = (word shr 13) and 1
            die.samplingDie = (word shr 12) and 1
            die.codeBitOfCoordinatorValueX = (word shr 11) and 1
            die.codeBitOfCoordinatorValueY = (word shr 10) and 1
            die.dummyData = (word shr 9) and 1
            die.dieCoordinatorValueY = word and 511
            
            word = br.readAsInt(2)
            die.measurementFinish = (word shr 15) and 1
            die.rejectChipFlag = (word shr 14) and 1
            die.testExecutionSite = (word shr 8) and 63
            die.blockAreaJudgmentFunction = (word shr 6) and 3
            die.categoryData = word and 63
            
            results.add(die)
        }
        return results
    }
}
