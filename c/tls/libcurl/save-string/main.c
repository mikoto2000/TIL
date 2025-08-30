#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <curl/curl.h>

// レスポンス取得用構造体
struct Memory {
  char *response;
  size_t size;
};

static size_t write_callback(void *contents, size_t size, size_t nmemb, void *userp) {
  // レスポンス取得用の構造体にキャストしてレスポンス取得の準備
  struct Memory *mem = (struct Memory *)userp;

  // 今回取得したレスポンスのサイズを計算
  size_t realsize = size * nmemb;

  // メモリ領域を拡張
  char *ptr = realloc(mem->response, mem->size + realsize + 1);
  if(ptr == NULL) {
    return 0;
  }

  // 新しい領域を Memory 構造体の response に設定
  mem->response = ptr;
  // 今回受信した内容を Memory 構造体にコピー
  memcpy(&(mem->response[mem->size]), contents, realsize);
  // サイズ更新
  mem->size += realsize;
  // 文字列として扱うので末尾をゼロにする
  mem->response[mem->size] = 0;

  return realsize;
}

int main(void)
{
  CURL *curl;
  CURLcode res;
  struct Memory chunk = {0};

  curl = curl_easy_init();
  if(curl) {
    curl_easy_setopt(curl, CURLOPT_URL, "https://example.com");
    curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);
    // レスポンス受信時に実行されるコールバックを設定
    curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, write_callback);
    // コールバックの第四引数に渡す構造体を指定
    curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *)&chunk);

    // HTTPSリクエスト送信
    res = curl_easy_perform(curl);
    if(res != CURLE_OK) {
      fprintf(stderr, "curl_easy_perform() failed: %s\n",
          curl_easy_strerror(res));
      free(chunk.response);
    }

    // curl クリーンアップ
    curl_easy_cleanup(curl);

    // 受信データの表示
    printf("%s", chunk.response);

    // 後片付け
    free(chunk.response);
  }
  return 0;
}
