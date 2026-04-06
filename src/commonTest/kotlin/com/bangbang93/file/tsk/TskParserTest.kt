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
        
        // MapVersion (1 byte) at offset 51 - set to 0 for simplest case
        testData[51] = 0
        
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
        testData[51] = 0 // MapVersion = 0 at offset 51
        
        val parser = TskParser(testData)
        
        assertNotNull(parser.mapFile)
        assertEquals(0, parser.mapFile.dieResults.size)
    }
    
    @Test
    fun testMultiSites256ParsingWithConfiguration() {
        // Create a complete test TSK file with MapVersion=2 (MULTI_SITES_256)
        // Header size is 256 bytes
        val headerData = ByteArray(256) { 0 }
        
        // Set OperatorName (20 bytes at offset 0)
        "TestOperator".encodeToByteArray().copyInto(headerData, 0)
        
        // Set DeviceName (16 bytes at offset 20)
        "TestDevice".encodeToByteArray().copyInto(headerData, 20)
        
        // Set MapVersion to 2 (MULTI_SITES_256) at offset 51
        headerData[51] = 2
        
        // Set mapDataAreaRowSize (2 bytes at offset 52) = 2
        headerData[52] = 0
        headerData[53] = 2
        
        // Set mapDataAreaLineSize (2 bytes at offset 54) = 2
        headerData[54] = 0
        headerData[55] = 2
        
        // Set totalTestedDice (2 bytes at offset 210) = 4
        headerData[210] = 0
        headerData[211] = 4
        
        // Set totalPassDice (2 bytes at offset 212) = 3
        headerData[212] = 0
        headerData[213] = 3
        
        // Set totalFailDice (2 bytes at offset 214) = 1
        headerData[214] = 0
        headerData[215] = 1
        
        // Set testDieInformationAddress (4 bytes at offset 216) = 256 (right after header)
        // In big-endian: 0x00000100
        headerData[216] = 0
        headerData[217] = 0
        headerData[218] = 0x01.toByte()
        headerData[219] = 0x00.toByte()
        
        // Set mapFileConfiguration (2 bytes at offset 228)
        // Binary "1000" (with leading zeros padded to 16 bits: "0000000000001000") = 8 in decimal
        // This sets bit 3 (extensionHeaderInformation) to 1
        headerData[228] = 0
        headerData[229] = 8
        
        // Create die results data (4 dies * 6 bytes = 24 bytes)
        val dieResultsData = ByteArray(24) { 0 }
        
        // Die 1: PASS (dieTestResult = 1, shifted to bits 14-15)
        // word1: 0100000000000000 = 0x4000
        dieResultsData[0] = 0x40.toByte()
        dieResultsData[1] = 0x00.toByte()
        // word2 and word3
        dieResultsData[2] = 0
        dieResultsData[3] = 0
        dieResultsData[4] = 0
        dieResultsData[5] = 0
        
        // Die 2, 3, 4 similar (PASS)
        for (i in 1..3) {
            val offset = i * 6
            dieResultsData[offset] = 0x40.toByte()
            dieResultsData[offset + 1] = 0x00.toByte()
        }
        
        // Combine header and die results
        val testData = headerData + dieResultsData
        
        // Parse the data
        val parser = TskParser(testData)
        
        // Verify header was parsed
        assertNotNull(parser.mapFile.header)
        assertEquals("TestOperator", parser.mapFile.header?.operatorName?.trim())
        assertEquals(MapVersion.MULTI_SITES_256, parser.mapFile.header?.mapVersion)
        assertEquals(2, parser.mapFile.header?.mapDataAreaRowSize)
        assertEquals(2, parser.mapFile.header?.mapDataAreaLineSize)
        assertEquals(4, parser.mapFile.header?.totalTestedDice)
        
        // Verify mapFileConfiguration was parsed correctly
        assertNotNull(parser.mapFile.header?.mapFileConfiguration)
        assertEquals(1, parser.mapFile.header?.mapFileConfiguration?.extensionHeaderInformation())
        
        // Verify die results were parsed
        assertEquals(4, parser.mapFile.dieResults.size)
        assertEquals(DieTestResult.PASS, parser.mapFile.dieResults[0].dieTestResult)
    }
    
    @Test
    fun testMapFileConfigurationWithShortBinaryString() {
        // Test that MapFileConfiguration handles short binary strings properly
        // readAsBit can produce strings without leading zeros like "10" instead of "0000000000000010"
        val headerData = ByteArray(256) { 0 }
        
        // Set MapVersion to 2 at offset 51
        headerData[51] = 2
        
        // Set mapFileConfiguration at offset 228 to "10" in binary = 2 in decimal (only 2 bits)
        // This should be padded to at least 6 bits internally
        headerData[228] = 0
        headerData[229] = 2
        
        val parser = TskParser(headerData)
        
        // Should not throw IndexOutOfBoundsException
        assertNotNull(parser.mapFile.header?.mapFileConfiguration)
        // Bit 1 (testResultInformationPerDie) should be 1
        assertEquals(1, parser.mapFile.header?.mapFileConfiguration?.testResultInformationPerDie())
        // Bit 0 should be 0
        assertEquals(0, parser.mapFile.header?.mapFileConfiguration?.headerInformation())
        // Bits 2-5 should be 0 (padded)
        assertEquals(0, parser.mapFile.header?.mapFileConfiguration?.lineCategoryInformation())
    }
}
