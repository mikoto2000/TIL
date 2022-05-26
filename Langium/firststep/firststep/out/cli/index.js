"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
//import colors from 'colors';
const commander_1 = require("commander");
function default_1() {
    const program = new commander_1.Command();
    program
        // eslint-disable-next-line @typescript-eslint/no-var-requires
        .version(require('../../package.json').version);
    // const fileExtensions = FirststepLanguageMetaData.fileExtensions.join(', ');
    program.parse(process.argv);
}
exports.default = default_1;
//# sourceMappingURL=index.js.map