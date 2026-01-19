package com.bangbang93.file.tsk.example

import com.bangbang93.file.tsk.TskFileReader

fun main() {
    // Example 1: Parse from file path
    val mapFile = TskFileReader.parseFile("/path/to/file.tsk")
    
    println("=== TSK File Information ===")
    mapFile.header?.let { header ->
        println("Operator: ${header.operatorName}")
        println("Device: ${header.deviceName}")
        println("Wafer Size: ${header.waferSize}")
        println("Lot No: ${header.lotNo}")
        println("Wafer ID: ${header.waferId}")
        println()
        
        println("=== Test Statistics ===")
        println("Total Tested: ${header.totalTestedDice}")
        println("Total Pass: ${header.totalPassDice}")
        println("Total Fail: ${header.totalFailDice}")
        println("Yield: ${String.format("%.2f", header.totalPassDice.toDouble() / header.totalTestedDice * 100)}%")
        println()
        
        println("=== Map Information ===")
        println("Map Version: ${header.mapVersion}")
        println("Row Size: ${header.mapDataAreaRowSize}")
        println("Line Size: ${header.mapDataAreaLineSize}")
        println("Index Size X: ${header.indexSizeX}")
        println("Index Size Y: ${header.indexSizeY}")
    }
    
    // Example 2: Process die results
    println("\n=== Die Results Summary ===")
    val dieResults = mapFile.dieResults
    println("Total Dies: ${dieResults.size}")
    
    val passCount = dieResults.count { it.dieTestResult == 1 }
    val fail1Count = dieResults.count { it.dieTestResult == 2 }
    val fail2Count = dieResults.count { it.dieTestResult == 3 }
    val notTestedCount = dieResults.count { it.dieTestResult == 0 }
    
    println("Pass: $passCount")
    println("Fail 1: $fail1Count")
    println("Fail 2: $fail2Count")
    println("Not Tested: $notTestedCount")
    
    // Example 3: Parse from byte array
    // val data: ByteArray = ... // your byte array
    // val mapFile2 = TskFileReader.parseBytes(data)
}
