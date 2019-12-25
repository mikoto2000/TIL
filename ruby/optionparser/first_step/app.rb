require "optparse"

def parse_option(argv)
  options = {
    :array => []
  }
  op = OptionParser.new

  op.on("-s VALUE", "--string VALUE", "文字列渡せますよー"){|v| options[:string] = v }
  op.on("-i VALUE", "--integer VALUE", Integer, "整数渡せますよー"){|v| options[:integer] = v }
  op.on("-f VALUE", "--float VALUE", Float, "小数(float)渡せますよー"){|v| options[:float] = v }
  op.on("-a VALUE", "--array VALUE", String, "複数オプション渡すことで配列にできますよー"){|v| options[:array] << v }
  op.on("-b", "--boolean", "真偽(boolean)渡せますよー"){|v| options[:boolean] = v }

  op.parse!(argv)

  return options, argv
end

opts, argv = parse_option(ARGV)
puts opts, argv.to_s

