def __main__(argv)
  if argv[1] == "version"
    puts "v#{TestFileOpen::VERSION}"
  else
    File.open('./test.txt', 'r') { |f|
      puts f.read
    }
  end
end
