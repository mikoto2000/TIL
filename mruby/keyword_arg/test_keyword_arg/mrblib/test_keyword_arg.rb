def __main__(argv)
  if argv[1] == "version"
    puts "v#{TestKeywordArg::VERSION}"
  else
    keyword_arg(key1: "key1", key2: "key2")
  end
end

def keyword_arg(key1: "key1", key2: "key2")
  puts "key1: #{key1}, key2: #{key2}"
end
