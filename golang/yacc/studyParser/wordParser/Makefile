all: yacc build run

run: wordParser
	./wordParser "abc"

build: wordParser.go
	go build -o wordParser wordParser.go

yacc: wordParser.y
	go tool yacc -o wordParser.go wordParser.y

.PHONY:clean
clean:
	rm *.go
	rm *.output
	rm wordParser
