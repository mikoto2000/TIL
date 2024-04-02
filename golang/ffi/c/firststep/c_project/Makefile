SRCS = \
	./src/libhello.c

OBJS = $(subst .c,.o,$(SRCS))

CFLAGS = 
LIBS = 
TARGET = ./lib/libhello.a
ifeq ($(OS),Windows_NT)
TARGET := $(TARGET).exe
endif

.SUFFIXES: .c .o

test : helloapp
helloapp : lib ./test/helloapp.c
	gcc -o ./helloapp ./test/helloapp.c -Iinclude -Llib -lhello


lib : $(TARGET)

$(TARGET) : $(OBJS)
	mkdir -p lib
	ar rcs $@ $(OBJS) $(LIBS)

.c.o :
	gcc -c $(CFLAGS) -I. $< -o $@

clean :
	rm -f src/*.o
	rm -f test/*.o
	rm -rf lib
	rm -f helloapp

