package com.bangbang93.file.tsk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TskBinaryWriterTest {

    @Test
    fun testWriteAsBytes() {
        val writer = TskBinaryWriter()
        writer.writeAsBytes(byteArrayOf(0x01, 0x02, 0x03))
        
        val result = writer.toByteArray()
        assertEquals(3, result.size)
        assertEquals(0x01, result[0])
        assertEquals(0x02, result[1])
        assertEquals(0x03, result[2])
    }

    @Test
    fun testWriteAsInt1Byte() {
        val writer = TskBinaryWriter()
        writer.writeAsInt(0x42, 1)
        
        val result = writer.toByteArray()
        assertEquals(1, result.size)
        assertEquals(0x42, result[0].toInt() and 0xFF)
    }

    @Test
    fun testWriteAsInt2Bytes() {
        val writer = TskBinaryWriter()
        writer.writeAsInt(0x0102, 2)
        
        val result = writer.toByteArray()
        assertEquals(2, result.size)
        assertEquals(0x01, result[0].toInt() and 0xFF)
        assertEquals(0x02, result[1].toInt() and 0xFF)
    }

    @Test
    fun testWriteAsInt4Bytes() {
        val writer = TskBinaryWriter()
        writer.writeAsInt(0x01020304, 4)
        
        val result = writer.toByteArray()
        assertEquals(4, result.size)
        assertEquals(0x01, result[0].toInt() and 0xFF)
        assertEquals(0x02, result[1].toInt() and 0xFF)
        assertEquals(0x03, result[2].toInt() and 0xFF)
        assertEquals(0x04, result[3].toInt() and 0xFF)
    }

    @Test
    fun testWriteAsString() {
        val writer = TskBinaryWriter()
        writer.writeAsString("Hello", 10)
        
        val result = writer.toByteArray()
        assertEquals(10, result.size)
        assertEquals("Hello     ", result.decodeToString())
    }

    @Test
    fun testWriteAsStringTruncate() {
        val writer = TskBinaryWriter()
        writer.writeAsString("HelloWorld", 5)
        
        val result = writer.toByteArray()
        assertEquals(5, result.size)
        assertEquals("Hello", result.decodeToString())
    }

    @Test
    fun testByteToHexString() {
        val bytes = byteArrayOf(0x01, 0x0a, 0xff.toByte())
        val hex = TskBinaryWriter.byteToHexString(bytes)
        assertEquals("010aff", hex)
    }

    @Test
    fun testBinaryStringToBytes() {
        val binary = "11010001"
        val bytes = TskBinaryWriter.binaryStringToBytes(binary)
        assertEquals(1, bytes.size)
        assertEquals(0xd1.toByte(), bytes[0])
    }

    @Test
    fun testHexStringToBytes() {
        val hex = "aa00"
        val bytes = TskBinaryWriter.hexStringToBytes(hex)
        assertEquals(2, bytes.size)
        assertEquals(0xaa.toByte(), bytes[0])
        assertEquals(0x00, bytes[1])
    }

    @Test
    fun testClear() {
        val writer = TskBinaryWriter()
        writer.writeAsBytes(byteArrayOf(0x01, 0x02))
        assertEquals(2, writer.toByteArray().size)
        
        writer.clear()
        assertEquals(0, writer.toByteArray().size)
    }
}
