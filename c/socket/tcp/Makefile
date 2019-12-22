all: client server

%.o: %.c
	$(CC) $(CFLAGS) -o $@

.PHONY: clean
clean:
	-rm client server

