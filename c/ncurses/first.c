#include <ncurses.h>

int
main(int argc, char* argv[]) {
    initscr();

    move(-10, -20);
    addstr("Hello, World!");
    getch();

    endwin();
    return 0;
}
