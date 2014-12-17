package main

import (
	"fmt"
	"net/http"
	"net/url"
)

/**
 * クエリパラメータ "value" の値が、 "aaaa" だった時のみ false を返すサービス
*/
func main() {
	http.HandleFunc("/validate", func(w http.ResponseWriter, r *http.Request) {
		u, _ := url.Parse(r.URL.String())
		qp := u.Query()

		w.Header().Set("Access-Control-Allow-Origin", "http://localhost:8080")
		w.Header().Set("Cache-Control", "no-cache")
		w.Header().Set("Content-Type", "application/json")

		if qp.Get("value") == "aaaa" {
			fmt.Println(qp.Get("value"))
			fmt.Fprint(w, "false")
		} else {
			fmt.Fprint(w, "true")
		}
	})

	http.ListenAndServe("localhost:8088", nil)
}
