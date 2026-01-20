package com.bangbang93.file.tsk

/**
 * Die test result enumeration
 */
enum class DieTestResult(val value: Int) {
    /** Not Tested */
    NOT_TESTED(0),
    /** Pass Die */
    PASS(1),
    /** Fail 1 Die */
    FAIL_1(2),
    /** Fail 2 Die */
    FAIL_2(3);

    companion object {
        fun fromValue(value: Int): DieTestResult {
            return entries.find { it.value == value } 
                ?: throw IllegalArgumentException("Invalid DieTestResult value: $value")
        }
        
        /**
         * Safely convert an integer value to DieTestResult, returning NOT_TESTED for invalid values
         */
        fun fromValueOrDefault(value: Int): DieTestResult {
            return entries.find { it.value == value } ?: NOT_TESTED
        }
    }
}
