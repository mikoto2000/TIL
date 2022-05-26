import colors from 'colors';
import { Command } from 'commander';
import { Model } from '../language-server/generated/ast';
import { FirststepLanguageMetaData } from '../language-server/generated/module';
import { createFirststepServices } from '../language-server/firststep-module';
import { extractAstNode } from './cli-util';

export const testAction = async (fileName: string, opts: GenerateOptions): Promise<void> => {
    const services = createFirststepServices().Firststep;
    await extractAstNode<Model>(fileName, services);
    console.log(colors.green(`文法チェック OK`));
    // ※ extractAstNode から呼ばれる `extractDocument` 内で、 `process.exit(1)` されるので、 try-catch しない
};

export type GenerateOptions = {
    destination?: string;
}

export default function(): void {
    const program = new Command();

    program
        // eslint-disable-next-line @typescript-eslint/no-var-requires
        .version(require('../../package.json').version);

    const fileExtensions = FirststepLanguageMetaData.fileExtensions.join(', ');
    program
        .command('test')
        .argument('<file>', `source file (possible file extensions: ${fileExtensions})`)
        .description('文法チェック')
        .action(testAction);

    program.parse(process.argv);
}
