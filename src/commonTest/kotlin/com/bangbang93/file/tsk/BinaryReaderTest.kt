package com.bangbang93.file.tsk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class BinaryReaderTest {

    @Test
    fun testReadAsBytes() {
        val data = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val reader = BinaryReader(data)
        
        val result = reader.readAsBytes(2)
        assertEquals(2, result.size)
        assertEquals(0x01, result[0])
        assertEquals(0x02, result[1])
        assertEquals(2, reader.getPosition())
    }

    @Test
    fun testReadAsString() {
        val data = "Hello".encodeToByteArray()
        val reader = BinaryReader(data)
        
        val result = reader.readAsString(5)
        assertEquals("Hello", result)
    }

    @Test
    fun testReadAsInt() {
        // 0x0102 = 258 in decimal
        val data = byteArrayOf(0x01, 0x02)
        val reader = BinaryReader(data)
        
        val result = reader.readAsInt(2)
        assertEquals(258, result)
    }

    @Test
    fun testReadAsHex() {
        val data = byteArrayOf(0x01.toByte(), 0x0a.toByte(), 0xff.toByte())
        val reader = BinaryReader(data)
        
        val result = reader.readAsHex(3)
        assertEquals("010aff", result)
    }

    @Test
    fun testSkipAndGoto() {
        val data = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05)
        val reader = BinaryReader(data)
        
        reader.skip(2)
        assertEquals(2, reader.getPosition())
        
        reader.goto(4)
        assertEquals(4, reader.getPosition())
        
        val result = reader.readAsBytes(1)
        assertEquals(0x05, result[0])
    }

    @Test
    fun testPreventAutoForward() {
        val data = byteArrayOf(0x01, 0x02, 0x03)
        val reader = BinaryReader(data)
        
        val result1 = reader.readAsBytes(2, preventAutoForward = true)
        assertEquals(0, reader.getPosition()) // Position should not change
        
        val result2 = reader.readAsBytes(2, preventAutoForward = false)
        assertEquals(2, reader.getPosition()) // Position should advance
    }
}
