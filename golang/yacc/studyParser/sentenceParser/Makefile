all: yacc build run

run: sentenceParser
	./sentenceParser "abc def."
	./sentenceParser "abc def hij?"
	./sentenceParser "abc def hij, wow!"

build: sentenceParser.go
	go build -o sentenceParser sentenceParser.go

yacc: sentenceParser.y
	go tool yacc -o sentenceParser.go sentenceParser.y

.PHONY:clean
clean:
	rm *.go
	rm *.output
	rm sentenceParser
