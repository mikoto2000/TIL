import { program } from 'commander';

function myParseInt(value: any) {
  if (!Number.isInteger(Number(value))) {
    throw "IntegerFormatError";
  }
  return parseInt(value);
}

function myParseFloat(value: any) {
  if (!Number.isFinite(Number(value))) {
    throw "FloatFormatError";
  }
  return parseFloat(value);
}

function collect(value: any, previous: any) {
  return previous.concat([value]);
}

program.version('0.0.1');

program
  .option("-s, --string <VALUE>", "文字列渡せますよー")
  .option("-i, --integer <VALUE>", "整数渡せますよー", myParseInt)
  .option("-f, --float <VALUE>", "小数(float)渡せますよー", myParseFloat)
  .option("-a, --array <VALUE>", "複数オプション渡すことで配列にできますよー", collect, [])
  .option("-b, --boolean", "真偽(boolean)渡せますよー")

try {
  program.parse(process.argv);
} catch (e) {
  console.error(e);
  process.exit(1);
}

console.log(`string : ${program.string}`);
console.log(`integer: ${program.integer}`);
console.log(`float  : ${program.float}`);
console.log(`array  : ${program.array}`);
console.log(`boolean: ${program.boolean}`);

