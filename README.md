# tsk-file.kt

A Kotlin Multiplatform library for parsing A-PM-90A/UF Series Map Data Files (TSK files).

This project is a Kotlin Multiplatform port of [tsk-parser](https://github.com/Ian-HL/tsk-parser) and the original [larvata/tsk-parser](https://github.com/larvata/tsk-parser).

## Features

- 🎯 **Multiplatform Support**: Works on JVM and Node.js
- 📦 **Easy to Use**: Simple API for parsing TSK files
- 🔧 **Type Safe**: Fully typed Kotlin API with data classes

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

### JVM

```kotlin
import com.bangbang93.file.tsk.TskFileReader

val mapFile = TskFileReader.parseFile("/path/to/file.tsk")

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

### Node.js

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