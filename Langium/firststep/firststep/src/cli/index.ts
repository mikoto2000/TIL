//import colors from 'colors';
import { Command } from 'commander';
//import { Model } from '../language-server/generated/ast';
//import { FirststepLanguageMetaData } from '../language-server/generated/module';
//import { createFirststepServices } from '../language-server/firststep-module';
//import { extractAstNode } from './cli-util';

export type GenerateOptions = {
    destination?: string;
}

export default function(): void {
    const program = new Command();

    program
        // eslint-disable-next-line @typescript-eslint/no-var-requires
        .version(require('../../package.json').version);

    // const fileExtensions = FirststepLanguageMetaData.fileExtensions.join(', ');

    program.parse(process.argv);
}
