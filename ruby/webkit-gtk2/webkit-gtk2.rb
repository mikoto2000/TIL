require 'webkit-gtk2'

window = Gtk::Window.new

web_view = WebKitGtk2::WebView.new
window.signal_connect("destroy") do
    Gtk.widget_destroy
end

web_view.load_uri('http://google.co.jp/')
window.add(web_view)

window.show_all
Gtk.main
