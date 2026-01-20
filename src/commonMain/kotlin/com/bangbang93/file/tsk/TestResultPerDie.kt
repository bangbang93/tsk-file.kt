package com.bangbang93.file.tsk

/**
 * Test Result Per Die
 */
data class TestResultPerDie(
    var dieTestResult: DieTestResult = DieTestResult.NOT_TESTED,
    var marking: Int = 0,
    var failMarkInspection: Int = 0,
    var reProbingResult: Int = 0,
    var needleMarkInspectionResult: Int = 0,
    var dieCoordinatorValueX: Int = 0,
    var dieProperty: DieProperty = DieProperty.SKIP,
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
