all: yacc build run

run: wordParserAddTokenInfo
	./wordParserAddTokenInfo "abc"

build: wordParserAddTokenInfo.go
	go build -o wordParserAddTokenInfo wordParserAddTokenInfo.go

yacc: wordParserAddTokenInfo.y
	go tool yacc -o wordParserAddTokenInfo.go wordParserAddTokenInfo.y

.PHONY:clean
clean:
	rm *.go
	rm *.output
	rm wordParserAddTokenInfo
