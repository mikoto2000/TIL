all: lex toc

toc: mylex.yy.c
	gcc -o mylex mylex.yy.c -ll

lex: mylex.l
	lex -o mylex.yy.c mylex.l

.PHONY:clean
clean:
	rm *.yy.c
	rm mylex
