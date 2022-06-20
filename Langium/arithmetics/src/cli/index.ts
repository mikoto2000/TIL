import colors from 'colors';
import { Command } from 'commander';
import { Model } from '../language-server/generated/ast';
import { ArithmeticsLanguageMetaData } from '../language-server/generated/module';
import { createArithmeticsServices } from '../language-server/arithmetics-module';
import { extractAstNode } from './cli-util';
//import { generateJavaScript } from './generator';
import { evaluateExpression } from './evaluater';

//export const generateAction = async (fileName: string, opts: GenerateOptions): Promise<void> => {
//    const services = createArithmeticsServices().Arithmetics;
//    const model = await extractAstNode<Model>(fileName, services);
//    const generatedFilePath = generateJavaScript(model, fileName, opts.destination);
//    console.log(colors.green(`JavaScript code generated successfully: ${generatedFilePath}`));
//};

export const validateAction = async (fileName: string, opts: GenerateOptions): Promise<void> => {
    const services = createArithmeticsServices().Arithmetics;
    const model = await extractAstNode<Model>(fileName, services);
    console.log(model);
    console.log(colors.green(`arithmetics code validate successfully`));
};

export const evalAction = async (fileName: string, opts: GenerateOptions): Promise<void> => {
    const services = createArithmeticsServices().Arithmetics;
    const model = await extractAstNode<Model>(fileName, services);
    const results = evaluateExpression(model);
    console.log(colors.green(`${results}`));
};

export type GenerateOptions = {
    destination?: string;
}

export default function(): void {
    const program = new Command();

    program
        // eslint-disable-next-line @typescript-eslint/no-var-requires
        .version(require('../../package.json').version);

    const fileExtensions = ArithmeticsLanguageMetaData.fileExtensions.join(', ');
//    program
//        .command('generate')
//        .argument('<file>', `source file (possible file extensions: ${fileExtensions})`)
//        .option('-d, --destination <dir>', 'destination directory of generating')
//        .description('generates JavaScript code that prints "Hello, {name}!" for each greeting in a source file')
//        .action(generateAction);

    program
        .command('validate')
        .argument('<file>', `source file (possible file extensions: ${fileExtensions})`)
        .description('validate arithmetics file')
        .action(validateAction);

    program
        .command('eval')
        .argument('<file>', `source file (possible file extensions: ${fileExtensions})`)
        .description('eval arithmetics file')
        .action(evalAction);

    program.parse(process.argv);
}
