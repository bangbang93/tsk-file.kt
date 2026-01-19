# tsk-file.kt

A Kotlin Multiplatform library for parsing and generating A-PM-90A/UF Series Map Data Files (TSK files).

This project is a Kotlin Multiplatform port of [tsk-parser](https://github.com/Ian-HL/tsk-parser) and the original [larvata/tsk-parser](https://github.com/larvata/tsk-parser).

## Features

- 🎯 **Multiplatform Support**: Works on JVM and Node.js
- 📦 **Easy to Use**: Simple API for parsing and generating TSK files
- 🔧 **Type Safe**: Fully typed Kotlin API with data classes
- ⚡ **Modern**: Built with Kotlin 2.3 and Gradle 9.2

## Installation

### Maven (JVM)

```xml
<dependency>
    <groupId>com.bangbang93.file.tsk</groupId>
    <artifactId>tsk-file-jvm</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle (JVM)

```kotlin
dependencies {
    implementation("com.bangbang93.file.tsk:tsk-file-jvm:1.0.0")
}
```

### npm (Node.js)

```bash
npm install tsk-file
```

## Usage

### Parsing TSK Files

#### JVM

```kotlin
import com.bangbang93.file.tsk.TskFileReader

// Parse from file
val mapFile = TskFileReader.parseFile("/path/to/file.tsk")

// Or parse from byte array
val data: ByteArray = ... // your byte array
val mapFile = TskFileReader.parseBytes(data)

// Access header information
println("Operator: ${mapFile.header?.operatorName}")
println("Device: ${mapFile.header?.deviceName}")
println("Wafer Size: ${mapFile.header?.waferSize}")
println("Total Tested: ${mapFile.header?.totalTestedDice}")
println("Total Pass: ${mapFile.header?.totalPassDice}")
println("Total Fail: ${mapFile.header?.totalFailDice}")

// Access die results
mapFile.dieResults.forEach { die ->
    println("Die Test Result: ${die.dieTestResult}")
}
```

#### Node.js

```javascript
const { TskFileReader } = require('tsk-file');

const mapFile = TskFileReader.parseFile('/path/to/file.tsk');

// Access header information
console.log(`Operator: ${mapFile.header.operatorName}`);
console.log(`Device: ${mapFile.header.deviceName}`);
console.log(`Total Tested: ${mapFile.header.totalTestedDice}`);

// Access die results
mapFile.dieResults.forEach(die => {
    console.log(`Die Test Result: ${die.dieTestResult}`);
});
```

### Generating TSK Files

#### JVM

```kotlin
import com.bangbang93.file.tsk.*

// Create header
val header = MapFileHeader(
    operatorName = "Operator",
    deviceName = "Device",
    waferSize = 200,
    mapVersion = 2,
    mapDataAreaRowSize = 10,
    mapDataAreaLineSize = 10,
    totalTestedDice = 100,
    totalPassDice = 90,
    totalFailDice = 10,
    reserved1 = ByteArray(2),
    reserved2 = ByteArray(2),
    reserved3 = ByteArray(2),
    reserved4 = ByteArray(2),
    reserved5 = ByteArray(1),
    reserved6 = ByteArray(2)
)

// Create die results
val dieResults = listOf(
    TestResultPerDie(dieTestResult = 1), // Pass
    TestResultPerDie(dieTestResult = 2), // Fail
    // ... more die results
)

// Create map file
val mapFile = TskMapFile(header = header, dieResults = dieResults)

// Write to file
TskFileWriter.writeFile(mapFile, "/path/to/output.tsk")

// Or generate as byte array
val data = TskFileWriter.writeBytes(mapFile)
```

#### Node.js

```javascript
const { TskMapFile, MapFileHeader, TestResultPerDie, TskFileWriter } = require('tsk-file');

// Create header
const header = new MapFileHeader();
header.operatorName = "Operator";
header.deviceName = "Device";
header.waferSize = 200;
// ... set other fields

// Create die results
const dieResults = [
    new TestResultPerDie({ dieTestResult: 1 }), // Pass
    new TestResultPerDie({ dieTestResult: 2 }), // Fail
    // ... more die results
];

// Create map file
const mapFile = new TskMapFile({ header, dieResults });

// Write to file
TskFileWriter.writeFile(mapFile, '/path/to/output.tsk');
```

## Building

```bash
# Build for all platforms
./gradlew build

# Build JVM jar
./gradlew jvmJar

# Build JS/Node.js package
./gradlew jsNodeProductionLibraryDistribution

# Run tests
./gradlew test
```

## License

MIT

## Credits

- Original CoffeeScript version: [larvata/tsk-parser](https://github.com/larvata/tsk-parser)
- C# version: [Ian-HL/tsk-parser](https://github.com/Ian-HL/tsk-parser)