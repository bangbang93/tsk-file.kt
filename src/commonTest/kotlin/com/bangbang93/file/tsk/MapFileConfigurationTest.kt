package com.bangbang93.file.tsk

import kotlin.test.Test
import kotlin.test.assertEquals

class MapFileConfigurationTest {

    @Test
    fun testMapFileConfiguration() {
        // Binary "10" = decimal 2
        val config = MapFileConfiguration("10")
        
        // getValue() should return the current state (padded to 6 bits)
        assertEquals("000010", config.getValue())
        assertEquals(2, config.getRawData())
    }

    @Test
    fun testBitAccessors() {
        // Create config with "111111" = 6 bits set
        val config = MapFileConfiguration("111111")
        
        // Test reading bits (reversed)
        assertEquals(1, config.headerInformation())
        assertEquals(1, config.testResultInformationPerDie())
        assertEquals(1, config.lineCategoryInformation())
        assertEquals(1, config.extensionHeaderInformation())
        assertEquals(1, config.testResultInformationPerExtensionDie())
        assertEquals(1, config.extensionLineCategoryInformation())
    }

    @Test
    fun testBitModification() {
        val config = MapFileConfiguration("000000")
        
        // Set a bit
        config.headerInformation(1)
        assertEquals(1, config.headerInformation())
        
        // Set another bit
        config.lineCategoryInformation(1)
        assertEquals(1, config.lineCategoryInformation())
        
        // Other bits should remain 0
        assertEquals(0, config.testResultInformationPerDie())
        
        // getValue() should reflect modifications
        // bitSet is [1, 0, 1, 0, 0, 0], reversed is [0, 0, 0, 1, 0, 1]
        assertEquals("000101", config.getValue())
    }
    
    @Test
    fun testShortBinaryStringPadding() {
        // Test that short binary strings are padded to at least 6 bits
        val config1 = MapFileConfiguration("1")
        assertEquals("000001", config1.getValue())
        assertEquals(1, config1.getRawData())
        
        val config2 = MapFileConfiguration("101")
        assertEquals("000101", config2.getValue())
        assertEquals(5, config2.getRawData())
        
        // All accessors should work without IndexOutOfBoundsException
        assertEquals(1, config1.headerInformation())
        assertEquals(0, config1.testResultInformationPerDie())
        assertEquals(0, config1.lineCategoryInformation())
        assertEquals(0, config1.extensionHeaderInformation())
        assertEquals(0, config1.testResultInformationPerExtensionDie())
        assertEquals(0, config1.extensionLineCategoryInformation())
    }
}
