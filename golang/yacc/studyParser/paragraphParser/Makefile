all: yacc build run

run: paragraphParser
	./paragraphParser "abc def."
	./paragraphParser "abc def hij?"
	./paragraphParser "abc def hij, wow!"

build: paragraphParser.go
	go build -o paragraphParser paragraphParser.go

yacc: paragraphParser.go.y
	go tool yacc -o paragraphParser.go paragraphParser.go.y

.PHONY:clean
clean:
	rm *.go
	rm *.output
	rm paragraphParser
