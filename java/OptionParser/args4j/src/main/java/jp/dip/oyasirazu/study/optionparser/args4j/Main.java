package jp.dip.oyasirazu.study.optionparser.args4j;

import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {

        // オプションオブジェクト準備
        Options options = new Options();

        // パーサー準備
        CmdLineParser optionParser = new CmdLineParser(options);

        try {
            // パース
            optionParser.parseArgument(args);

            // パース結果表示
            System.out.println(options);
        } catch (CmdLineException E) {
            // Useage を表示
            System.out.println("Useage:\n"
                    + "  Main [options] [ARGUMENTS...]\n"
                    + "\n"
                    + "Options:");
            optionParser.printUsage(System.out);
        }


    }

    static class Options {
        @Option(name="-s", aliases="--string", metaVar="VALUE", usage="文字列渡せますよー")
        private String optionString;

        @Option(name="-i", aliases="--integer", metaVar="VALUE", usage="整数(integer)渡せますよー")
        private int optionInteger;

        @Option(name="-l", aliases="--long", metaVar="VALUE", usage="整数(long)渡せますよー")
        private long optionLong;

        @Option(name="-f", aliases="--float", metaVar="VALUE", usage="小数(float)渡せますよー")
        private float optionFloat;

        @Option(name="-d", aliases="--double", metaVar="VALUE", usage="小数(double)渡せますよー")
        private double optionDouble;

        @Option(name="-b", aliases="--boolean", metaVar="VALUE", usage="真偽(boolean)渡せますよー")
        private boolean optionBoolean;

        @Argument
        private List<String> arguments;

        public String toString() {
            return "optionString: " + this.optionString + "\n"
                    + "optionString: " + this.optionInteger + "\n"
                    + "optionLong: " + this.optionLong + "\n"
                    + "optionFloat: " + this.optionFloat + "\n"
                    + "optionDouble: " + this.optionDouble + "\n"
                    + "optionBoolean: " + this.optionBoolean + "\n"
                    + "arguments: " + this.arguments + "\n";
        }
    }
}

