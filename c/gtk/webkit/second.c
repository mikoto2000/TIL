/**
 * c/gtk/webkit/second.c
 *
 * WebView でロードしたフレームを png 出力する。
 *
 * TODO: 各種オブジェクトの開放を行う
 */

#include <gtk/gtk.h>
#include <webkit2/webkit2.h>
#include <stdio.h>

static WebKitWebView* web_view;

// スナップショットを PNG 形式で出力
static void snapshot_finish_cb (
        GObject *source_object,
        GAsyncResult *res,
        gpointer user_data) {
    printf("snapshot finish\n");

    cairo_surface_t *surface = webkit_web_view_get_snapshot_finish (
            web_view,
            res,
            NULL);

    cairo_status_t cs = cairo_surface_write_to_png(surface, "./snapshot.png");

    gtk_main_quit();
}

static void load_changed_cb (
        WebKitWebView *web_view,
        WebKitLoadEvent load_event,
        gpointer user_data) {
    switch (load_event) {
    case WEBKIT_LOAD_FINISHED:
        printf("load finish\n");

        // スナップショット作成開始
        webkit_web_view_get_snapshot(
                web_view,
                WEBKIT_SNAPSHOT_REGION_FULL_DOCUMENT,
                WEBKIT_SNAPSHOT_OPTIONS_NONE,
                NULL,
                (GAsyncReadyCallback)snapshot_finish_cb,
                NULL);
        break;
    }
}

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

static GtkWidget* create_window() {
    GtkWidget* window = gtk_window_new (GTK_WINDOW_TOPLEVEL);
    gtk_window_set_default_size (GTK_WINDOW (window), 800, 600);
    gtk_widget_set_name (window, "GtkLauncher");

    return window;
}

int main(int argc, char* argv[]) {
    printf("START PROCESS\n");
    gtk_init(&argc, &argv);

    GtkWidget* box = gtk_box_new(GTK_ORIENTATION_VERTICAL, 0);
    gtk_box_pack_start(GTK_BOX(box), create_browser(), TRUE, TRUE, 0);

    GtkWidget* _main_window;
    _main_window = create_window();
    gtk_container_add(GTK_CONTAINER(_main_window),box);

    g_object_connect(
            G_OBJECT(web_view),
            "signal::load-changed", G_CALLBACK(load_changed_cb), _main_window, NULL);

    webkit_web_view_load_uri(web_view, "http://www.google.com/");

    gtk_main();

    return 0;
}
