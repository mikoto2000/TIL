all: yacc build run

run: sectionParser
	./sectionParser "./text.txt"

build: sectionParser.go
	go build -o sectionParser sectionParser.go

yacc: sectionParser.go.y
	go tool yacc -o sectionParser.go sectionParser.go.y

.PHONY:clean
clean:
	rm *.go
	rm *.output
	rm sectionParser
