/**
 * c/gtk/webkit/first.c
 *
 * 下記サイトのコードを基に、「gtk2.0 から gtk3.0 への変更」と
 * 「cpp から c への変更」を行った。
 *
 * [パソコンの前で分からないと叫ぶ: WebkitでWebブラウザを作ってみた]
 * (http://mylibstacktrace.blogspot.jp/2009/05/webkitweb.html)
 *
 */

#include <gtk/gtk.h>
#include <webkit2/webkit2.h>
#include <stdio.h>

static WebKitWebView* web_view;

static GtkWidget* create_browser() {
    GtkWidget* scrolled_window = gtk_scrolled_window_new(NULL, NULL);
    gtk_scrolled_window_set_policy(
            GTK_SCROLLED_WINDOW(scrolled_window),
            GTK_POLICY_AUTOMATIC,
            GTK_POLICY_AUTOMATIC);
    web_view = WEBKIT_WEB_VIEW(webkit_web_view_new());
    gtk_container_add(
            GTK_CONTAINER(scrolled_window),
            GTK_WIDGET(web_view));

    return scrolled_window;
}

static void destroy_cb(GtkWidget* widget, gpointer data) {
    gtk_main_quit();
}

static GtkWidget* create_window() {
    GtkWidget* window = gtk_window_new (GTK_WINDOW_TOPLEVEL);
    gtk_window_set_default_size (GTK_WINDOW (window), 800, 600);
    gtk_widget_set_name (window, "GtkLauncher");
    g_signal_connect (G_OBJECT (window), "destroy", G_CALLBACK (destroy_cb), NULL);

    return window;
}

int main(int argc, char* argv[]) {
    printf("START PROCESS");
    gtk_init(&argc, &argv);

    GtkWidget* box = gtk_box_new(GTK_ORIENTATION_VERTICAL, 0);
    gtk_box_pack_start(GTK_BOX(box), create_browser(), TRUE, TRUE, 0);

    GtkWidget* _main_window;
    _main_window = create_window();
    gtk_container_add(GTK_CONTAINER(_main_window),box);

    webkit_web_view_load_uri(web_view, "http://www.google.com/");

    gtk_widget_grab_focus(GTK_WIDGET(web_view));
    gtk_widget_show_all(_main_window);
    gtk_main();

    return 0;
}
