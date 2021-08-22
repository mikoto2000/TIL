package dev.mikoto2000.study.vertex.firststep;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    // ルーター作成
    Router router = Router.router(vertx);

    // 全パスにマッチするルートを作成し、そこにハンドラを割り当てる
    // 全パスにマッチするルートなので、HTTP リクエストが来るたびにハンドラメソッドが実行される
    router.route().handler(context -> {
      // リクエスト送信元アドレスを取得
      String address = context.request().connection().remoteAddress().toString();

      // クエリーパラメーター `name` を取得
      MultiMap queryParams = context.queryParams();
      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";

      // JSON レスポンスを生成して返却するよう設定
      context.json(
        new JsonObject()
          .put("name", name)
          .put("address", address)
          .put("message", "Hello " + name + " connected from " + address)
      );
    });

    // HTTP サーバーを生成
    vertx.createHttpServer()
      // 上で作成したルーターを設定
      .requestHandler(router)
      // 待ち受けポート設定
      .listen(8888)
      // 待ち受けポートを標準出力へ
      .onSuccess(server ->
        System.out.println(
          "HTTP server started on port " + server.actualPort()
        )
      );
  }
}
