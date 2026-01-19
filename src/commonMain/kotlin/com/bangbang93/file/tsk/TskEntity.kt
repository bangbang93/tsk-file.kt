package com.bangbang93.file.tsk

/**
 * Map File Configuration
 */
class MapFileConfiguration(value: String) {
    private val value: String = value
    private val bitSet: MutableList<Int>

    init {
        // Convert value to binary representation and reverse
        this.bitSet = value.map { it.digitToInt() }.reversed().toMutableList()
    }

    fun getValue(): String = value

    fun getRawData(): Int {
        val reversed = bitSet.reversed()
        return reversed.joinToString("").toInt(2)
    }

    fun headerInformation(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[0] = bitValue
        }
        return bitSet[0]
    }

    fun testResultInformationPerDie(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[1] = bitValue
        }
        return bitSet[1]
    }

    fun lineCategoryInformation(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[2] = bitValue
        }
        return bitSet[2]
    }

    fun extensionHeaderInformation(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[3] = bitValue
        }
        return bitSet[3]
    }

    fun testResultInformationPerExtensionDie(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[4] = bitValue
        }
        return bitSet[4]
    }

    fun extensionLineCategoryInformation(bitValue: Int? = null): Int {
        if (bitValue != null) {
            bitSet[5] = bitValue
        }
        return bitSet[5]
    }
}

/**
 * Map File Header
 */
data class MapFileHeader(
    var operatorName: String = "",
    var deviceName: String = "",
    /** 40, 45, 50, 60, 80 (Unit: 0.1 inch) or 100, 115, 125, 150, 200 (Unit: mm) */
    var waferSize: Int = 0,
    var machineNo: Int = 0,
    /** Unit: 0.01um, Example: 50000 */
    var indexSizeX: Int = 0,
    /** Unit: 0.01um, Example: 50000 */
    var indexSizeY: Int = 0,
    /** 0 to 359 (Unit: degree°), Example: 180 */
    var standardOrientationFlatDirection: Int = 0,
    var finalEditingMachineType: Int = 0,
    /**
     * 0: Normal
     * 1: 250,000 Chips
     * 2: 256 Multi-sites (currently only this is supported)
     * 3: 256 Multi-sites (without extended header information)
     * 4: 1024 category
     */
    var mapVersion: Int = 0,
    /** Number of columns, Example: 400 */
    var mapDataAreaRowSize: Int = 0,
    /** Number of rows, Example: 400 */
    var mapDataAreaLineSize: Int = 0,
    var mapDataForm: Int = 0,
    var waferId: String = "",
    var numberOfProbing: Int = 0,
    var lotNo: String = "",
    var cassetteNo: Int = 0,
    var slotNo: Int = 0,
    /** 1: leftward 2: rightward */
    var xCoordinatesIncreaseDirection: Int = 0,
    /** 1: forward 2: backward */
    var yCoordinatesIncreaseDirection: Int = 0,
    /** 1: Wafer center die 3: Target sense die 2: Teaching die */
    var referenceDieSettingProcedures: Int = 0,
    var reserved: Int = 0,
    /**
     * Reference die position X from wafer center (Unit: 0.01um)
     * Only when target sense die is selected for "Standard die setting procedure"
     */
    var targetDiePositionX: UInt = 0u,
    /**
     * Reference die position Y from wafer center (Unit: 0.01um)
     * Only when target sense die is selected for "Standard die setting procedure"
     */
    var targetDiePositionY: UInt = 0u,
    var referenceDieCoordinatorX: Int = 0,
    var referenceDieCoordinatorY: Int = 0,
    var probingStartPosition: Int = 0,
    var probingDirection: Int = 0,
    var distanceXtoWaferCenterDieOrigin: UInt = 0u,
    var distanceYtoWaferCenterDieOrigin: UInt = 0u,
    var coordinatorXofWaferCenterDie: Int = 0,
    var coordinatorYofWaferCenterDie: Int = 0,
    var firstDieCoordinatorX: UInt = 0u,
    var firstDieCoordinatorY: UInt = 0u,
    var startYear: String = "",
    var startMonth: String = "",
    var startDay: String = "",
    var startHour: String = "",
    var startMinute: String = "",
    var reserved1: ByteArray = ByteArray(0),
    var endYear: String = "",
    var endMonth: String = "",
    var endDay: String = "",
    var endHour: String = "",
    var endMinute: String = "",
    var reserved2: ByteArray = ByteArray(0),
    var loadYear: String = "",
    var loadMonth: String = "",
    var loadDay: String = "",
    var loadHour: String = "",
    var loadMinute: String = "",
    var reserved3: ByteArray = ByteArray(0),
    var unloadYear: String = "",
    var unloadMonth: String = "",
    var unloadDay: String = "",
    var unloadHour: String = "",
    var unloadMinute: String = "",
    var reserved4: ByteArray = ByteArray(0),
    var vegaMachineNo: String = "",
    var vegaMachineNo2: String = "",
    var specialCharacters: String = "",
    var testingEndInformation: Int = 0,
    var reserved5: ByteArray = ByteArray(0),
    /** Total tested count = totalPassDice + totalFailDice */
    var totalTestedDice: Int = 0,
    /** Total pass count */
    var totalPassDice: Int = 0,
    /** Total fail count */
    var totalFailDice: Int = 0,
    var testDieInformationAddress: Int = 0,
    var numberOfLineCategoryData: Int = 0,
    var lineCategoryAddress: Int = 0,
    var mapFileConfiguration: MapFileConfiguration? = null,
    var maxMultiSite: Int = 0,
    var maxCategories: Int = 0,
    var reserved6: ByteArray = ByteArray(0)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as MapFileHeader

        if (!reserved1.contentEquals(other.reserved1)) return false
        if (!reserved2.contentEquals(other.reserved2)) return false
        if (!reserved3.contentEquals(other.reserved3)) return false
        if (!reserved4.contentEquals(other.reserved4)) return false
        if (!reserved5.contentEquals(other.reserved5)) return false
        if (!reserved6.contentEquals(other.reserved6)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = reserved1.contentHashCode()
        result = 31 * result + reserved2.contentHashCode()
        result = 31 * result + reserved3.contentHashCode()
        result = 31 * result + reserved4.contentHashCode()
        result = 31 * result + reserved5.contentHashCode()
        result = 31 * result + reserved6.contentHashCode()
        return result
    }
}

/**
 * Test Result Per Die
 */
data class TestResultPerDie(
    /**
     * 0: Not Tested
     * 1: Pass Die
     * 2: Fail 1 Die
     * 3: Fail 2 Die
     */
    var dieTestResult: Int = 0,
    var marking: Int = 0,
    var failMarkInspection: Int = 0,
    var reProbingResult: Int = 0,
    var needleMarkInspectionResult: Int = 0,
    var dieCoordinatorValueX: Int = 0,
    /**
     * 0: Skip Die
     * 1: Probing Die
     * 2: Compulsory Marking Die
     */
    var dieProperty: Int = 0,
    var needleMarkInspectionExecutionDieSelection: Int = 0,
    var samplingDie: Int = 0,
    /**
     * 0: DieCoordinatorValueX is negative
     * 1: DieCoordinatorValueX is positive
     */
    var codeBitOfCoordinatorValueX: Int = 0,
    /**
     * 0: DieCoordinatorValueY is negative
     * 1: DieCoordinatorValueY is positive
     */
    var codeBitOfCoordinatorValueY: Int = 0,
    /**
     * except wafer
     * 0: No 1: Ignore data
     */
    var dummyData: Int = 0,
    var dieCoordinatorValueY: Int = 0,
    var measurementFinish: Int = 0,
    var rejectChipFlag: Int = 0,
    var testExecutionSite: Int = 0,
    var blockAreaJudgmentFunction: Int = 0,
    var categoryData: Int = 0
)

/**
 * Extension Header Information
 */
data class ExtensionHeaderInformation(
    var testedDice: Int = 0,
    var testedPassDice: Int = 0,
    var testedFailDice: Int = 0
)

/**
 * TSK Map File
 */
data class TskMapFile(
    var header: MapFileHeader? = null,
    var dieResults: List<TestResultPerDie> = emptyList()
)
