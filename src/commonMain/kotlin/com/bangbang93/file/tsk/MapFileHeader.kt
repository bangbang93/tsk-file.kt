package com.bangbang93.file.tsk

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
)
