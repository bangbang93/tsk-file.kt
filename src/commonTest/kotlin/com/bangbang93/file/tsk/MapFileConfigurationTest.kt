package com.bangbang93.file.tsk

import kotlin.test.Test
import kotlin.test.assertEquals

class MapFileConfigurationTest {

    @Test
    fun testMapFileConfiguration() {
        // Binary "10" = decimal 2
        val config = MapFileConfiguration("10")
        
        assertEquals("10", config.getValue())
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
    }
}
