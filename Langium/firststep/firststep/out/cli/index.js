"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.testAction = void 0;
const colors_1 = __importDefault(require("colors"));
const commander_1 = require("commander");
const module_1 = require("../language-server/generated/module");
const firststep_module_1 = require("../language-server/firststep-module");
const cli_util_1 = require("./cli-util");
const testAction = (fileName, opts) => __awaiter(void 0, void 0, void 0, function* () {
    const services = (0, firststep_module_1.createFirststepServices)().Firststep;
    yield (0, cli_util_1.extractAstNode)(fileName, services);
    console.log(colors_1.default.green(`文法チェック OK`));
    // ※ extractAstNode から呼ばれる `extractDocument` 内で、 `process.exit(1)` されるので、 try-catch しない
});
exports.testAction = testAction;
function default_1() {
    const program = new commander_1.Command();
    program
        // eslint-disable-next-line @typescript-eslint/no-var-requires
        .version(require('../../package.json').version);
    const fileExtensions = module_1.FirststepLanguageMetaData.fileExtensions.join(', ');
    program
        .command('test')
        .argument('<file>', `source file (possible file extensions: ${fileExtensions})`)
        .description('文法チェック')
        .action(exports.testAction);
    program.parse(process.argv);
}
exports.default = default_1;
//# sourceMappingURL=index.js.map