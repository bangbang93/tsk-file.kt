package com.bangbang93.file.tsk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TskParserTest {

    @Test
    fun testParserInitialization() {
        // Create a minimal TSK file header (simplified test data)
        // This creates enough data for the header reading
        val headerSize = 256 // Approximate size based on the header fields
        val testData = ByteArray(headerSize) { 0 }
        
        // Set some recognizable values in the header
        // OperatorName (20 bytes) at offset 0
        "TestOperator".encodeToByteArray().copyInto(testData, 0)
        
        // DeviceName (16 bytes) at offset 20
        "TestDevice".encodeToByteArray().copyInto(testData, 20)
        
        // MapVersion (1 byte) at offset 47 - set to 0 for simplest case
        testData[47] = 0
        
        val parser = TskParser(testData)
        
        assertNotNull(parser.mapFile)
        assertNotNull(parser.mapFile.header)
        assertEquals("TestOperator", parser.mapFile.header?.operatorName?.trim())
        assertEquals("TestDevice", parser.mapFile.header?.deviceName?.trim())
    }

    @Test
    fun testEmptyDieResults() {
        // Create minimal test data with MapVersion 0 (no die results to parse)
        val headerSize = 256
        val testData = ByteArray(headerSize) { 0 }
        testData[47] = 0 // MapVersion = 0
        
        val parser = TskParser(testData)
        
        assertNotNull(parser.mapFile)
        assertEquals(0, parser.mapFile.dieResults.size)
    }
}
