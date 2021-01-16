import { program } from 'commander';

program.version('0.0.1');

program
  .command('command1')
  .description('引数無しのサブコマンド')
  .action(() => console.log('execute command1'));

program
  .command('command2 <requireArg>')
  .description('必須引数ひとつのサブコマンド')
  .action((requireArg) => console.log(`execute command2 with requireArg:${requireArg}`));

program
  .command('firststep')
  .description('firststep 相当のサブコマンド')
  .option("-s, --string <VALUE>", "文字列渡せますよー")
  .option("-i, --integer <VALUE>", "整数渡せますよー", myParseInt)
  .option("-f, --float <VALUE>", "小数(float)渡せますよー", myParseFloat)
  .option("-a, --array <VALUE>", "複数オプション渡すことで配列にできますよー", collect, [])
  .option("-b, --boolean", "真偽(boolean)渡せますよー")
  .action((options) => {
    console.log(`string : ${options.string}`);
    console.log(`integer: ${options.integer}`);
    console.log(`float  : ${options.float}`);
    console.log(`array  : ${options.array}`);
    console.log(`boolean: ${options.boolean}`);
   });

program.parse(process.argv);

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

