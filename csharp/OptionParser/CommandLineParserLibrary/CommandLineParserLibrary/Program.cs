using CommandLine;
using CommandLine.Text;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CommandLineParserLibrary
{
    class Options
    {
        [Option("voiceroid", HelpText = "読み上げ VOICEROID(Yukari, YukariEx, Aoi)", DefaultValue = "Yukari2")]
        public string Voiceroid { get; set; }

        [Option('o', "output-file", HelpText = "出力ファイルパス")]
        public string OutputFile { get; set; }

        [Option('i', "input-file", HelpText = "入力ファイルパス")]
        public string InputFile { get; set; }

        [Option('u', "utf8", HelpText = "入力ファイル文字コードを UTF8 として処理")]
        public bool IsUtf8 { get; set; }

        [Option("split-size", HelpText = "読み上げ文字列を分割する目安のサイズ", DefaultValue = 2000)]
        public int SplitSize { get; set; }

        [HelpOption(HelpText= "ヘルプを表示")]
        public string GetUsage()
        {
            return HelpText.AutoBuild(this,
                (HelpText current) => HelpText.DefaultParsingErrorsHandler(this, current));
        }

        public override string ToString()
        {
            return $@"Options {{
    Voiceroid: {this.Voiceroid},
    OutputFile: {this.OutputFile},
    InputFile: {this.InputFile},
    IsUtf8: {this.IsUtf8},
    SplitSize: {this.SplitSize},
}}";
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            var options = new Options();

            if (!CommandLine.Parser.Default.ParseArguments(args, options))
            {
                Environment.Exit(CommandLine.Parser.DefaultExitCodeFail);
            }

            Console.WriteLine(options.ToString());
        }
    }
}
