all: dynamic static

static: tiocgwinsz.c
	gcc -static tiocgwinsz.c -o tiocgwinsz_static

dynamic: tiocgwinsz.c
	gcc tiocgwinsz.c -o tiocgwinsz

.PHONY: clean

clean:
	rm tiocgwinsz tiocgwinsz_static
