all: yacc build run

run: wordParserWithResult
	./wordParserWithResult "abc"

build: wordParserWithResult.go
	go build -o wordParserWithResult wordParserWithResult.go

yacc: wordParserWithResult.y
	go tool yacc -o wordParserWithResult.go wordParserWithResult.y

.PHONY:clean
clean:
	rm *.go
	rm *.output
	rm wordParserWithResult
