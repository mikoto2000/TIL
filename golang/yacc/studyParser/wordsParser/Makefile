all: yacc build run

run: wordsParser
	./wordsParser "abc def."

build: wordsParser.go
	go build -o wordsParser wordsParser.go

yacc: wordsParser.y
	go tool yacc -o wordsParser.go wordsParser.y

.PHONY:clean
clean:
	rm *.go
	rm *.output
	rm wordsParser
