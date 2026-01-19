// Example usage for Node.js
const { TskFileReader } = require('tsk-file');

// Parse TSK file
const mapFile = TskFileReader.parseFile('/path/to/file.tsk');

console.log('=== TSK File Information ===');
if (mapFile.header) {
    const header = mapFile.header;
    console.log(`Operator: ${header.operatorName}`);
    console.log(`Device: ${header.deviceName}`);
    console.log(`Wafer Size: ${header.waferSize}`);
    console.log(`Lot No: ${header.lotNo}`);
    console.log(`Wafer ID: ${header.waferId}`);
    console.log();
    
    console.log('=== Test Statistics ===');
    console.log(`Total Tested: ${header.totalTestedDice}`);
    console.log(`Total Pass: ${header.totalPassDice}`);
    console.log(`Total Fail: ${header.totalFailDice}`);
    const yield_ = (header.totalPassDice / header.totalTestedDice * 100).toFixed(2);
    console.log(`Yield: ${yield_}%`);
    console.log();
    
    console.log('=== Map Information ===');
    console.log(`Map Version: ${header.mapVersion}`);
    console.log(`Row Size: ${header.mapDataAreaRowSize}`);
    console.log(`Line Size: ${header.mapDataAreaLineSize}`);
    console.log(`Index Size X: ${header.indexSizeX}`);
    console.log(`Index Size Y: ${header.indexSizeY}`);
}

// Process die results
console.log('\n=== Die Results Summary ===');
const dieResults = mapFile.dieResults;
console.log(`Total Dies: ${dieResults.length}`);

const passCount = dieResults.filter(die => die.dieTestResult === 1).length;
const fail1Count = dieResults.filter(die => die.dieTestResult === 2).length;
const fail2Count = dieResults.filter(die => die.dieTestResult === 3).length;
const notTestedCount = dieResults.filter(die => die.dieTestResult === 0).length;

console.log(`Pass: ${passCount}`);
console.log(`Fail 1: ${fail1Count}`);
console.log(`Fail 2: ${fail2Count}`);
console.log(`Not Tested: ${notTestedCount}`);

// Parse from byte array
// const data = Buffer.from(...); // your byte array
// const mapFile2 = TskFileReader.parseBytes(data);
