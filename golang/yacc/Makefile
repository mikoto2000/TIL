all: yacc build

build: MyParser.go
	go build -o MyParser MyParser.go

yacc: MyParser.y
	go tool yacc -o MyParser.go MyParser.y

.PHONY:clean
clean:
	rm *.go
	rm *.output
	rm MyParser
