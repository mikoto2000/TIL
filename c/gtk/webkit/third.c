/**
 * c/gtk/webkit/second.c
 *
 * WebView でロードしたフレームを png 出力する。
 *
 * TODO: 各種オブジェクトの開放を行う
 * TODO: 日本語フォント対応
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

static void run_javascript_cb (
        GObject *source_object,
        GAsyncResult *res,
        gpointer user_data) {
    printf("done javascript.\n");
    // スナップショット作成開始
    webkit_web_view_get_snapshot(
            web_view,
            WEBKIT_SNAPSHOT_REGION_FULL_DOCUMENT,
            WEBKIT_SNAPSHOT_OPTIONS_NONE,
            NULL,
            (GAsyncReadyCallback)snapshot_finish_cb,
            NULL);
}

static void load_changed_cb (
        WebKitWebView *web_view,
        WebKitLoadEvent load_event,
        gpointer user_data) {

    gchar *script;
    script = g_strdup_printf("document.body.style.width=\"100px\";document.body.style.wordBreak=\"break-all\";");

    switch (load_event) {
    case WEBKIT_LOAD_FINISHED:
        printf("load finish\n");
        webkit_web_view_run_javascript(
                web_view,
                script,
                NULL,
                (GAsyncReadyCallback)run_javascript_cb,
                NULL);
        break;
    }
}

static GtkWidget* create_browser() {
    GtkWidget* webview = webkit_web_view_new();
    web_view = WEBKIT_WEB_VIEW(webview);
    return webview;
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

    webkit_web_view_load_uri(web_view, "file:///home/mikoto/project/MiscellaneousStudy/c/gtk/webkit/test.html");

    gtk_main();

    return 0;
}
