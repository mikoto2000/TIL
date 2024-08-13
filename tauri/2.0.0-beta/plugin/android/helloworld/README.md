---
title: Tauri 2.0 の Android プラグインの Example を helloworld に変更するまで
author: mikoto2000
date: 2024/07/22
---

# やること

Android と正しくやり取りするために変更が必要な個所がたくさんあって苦労したので、備忘のために記録しておく。


# 前提

- OS: Windows 11 Pro 23H2 ビルド 22631.3880
- Docker Desktop: Version 4.32.0 (157355)
- tauri2 と  Android ビルドの環境が構築済みであること。
    - See: [docker-images/tauri2/Dockerfile at master · mikoto2000/docker-images](https://github.com/mikoto2000/docker-images/blob/master/tauri2/Dockerfile)
    - rustup: 1.27.1
    - cargo: 1.79.0
    - rustc: 1.79.0
- Windows 上に Android Studio がインストール済みであり、 `adb` コマンドが使えること
- firststep でやったことも含めて全部まとめる

# まずは Example が正しく動くことを確認する

## プラグインのひな形プロジェクト(Example) を作成

```sh
$ npx @tauri-apps/cli@next plugin new --android helloworld
✔ What should be the Android Package ID for your plugin? · com.plugin.helloworld
```

## api プロジェクトのビルド

これで、フロントエンドから `import { ping } from "tauri-plugin-example";` して `ping(...)` できるようになる。

```sh
cd tauri-plugin-helloworld/
npm i
npm run build
```

## Example アプリのビルド

「プラグインプロジェクト単体でビルド」という概念は無いので、 Example プロジェクトのビルドを行う。

```sh
cd examples/tauri-app
yarn
```

そして Android プロジェクトのビルド。

`identifier` が `com.tauri.dev`, `version` が `0.0.0`  のままだとビルドができないのでこれを書き換えてビルド。

```
sed -i -e 's/com\.tauri\.dev/dev.mikoto2000.study.android.plugin.helloworld/' ./src-tauri/tauri.conf.json
sed -i -e 's/0\.0\.0/0.0.1/' ./src-tauri/tauri.conf.json
npm run tauri android init
npm run tauri android build -- --target aarch64 -d
```

これで、 `src-tauri/gen/android/app/build/outputs/apk/universal/debug/app-universal-debug.apk` に apk ファイルができるので、 Android 端末かエミュレーターか WSA にインストールする。


## 動作確認

自分の環境では物理端末が一番手っ取り早かったのでそれにインストール。

```sh
adb.exe install \\wsl.localhost\Ubuntu-24.04\home\mikoto\project\TIL\tauri\2.0.0-beta\plugin\android\helloworld\tauri-plugin-helloworld\examples\tauri-app\src-tauri\gen\android\app\build\outputs\apk\universal\debug\app-universal-debug.apk
```

![ひな形の動作確認結果](https://github.com/user-attachments/assets/5148f1a7-f511-408d-9256-5d2f261d1a8b)

Ping したら Pong が返ってくる。OK.

自分のプロジェクトに組み込みたい場合には、この Example プロジェクトの真似をして `package.json` と `Cargo.toml` にプラグインプロジェクトを登録すれば OK.


# Hello World プラグインへ変更する

ゼロ引数で呼び出したら、 `Hello, World!` 文字列を返却する `hello` コマンドを定義する。

## ひな形で出力された Java のクラス名を変更

`ExamplePlugin` という名前で出力されているので、 `HelloWorldPlugin` という名前に変更する。

呼び出し側も同様。

### 名前の変更

```sh
mv android/src/main/java/ExamplePlugin.kt android/src/main/java/HelloWorldPlugin.kt
```

次にクラス名の変更。

`android/src/main/java/ExamplePlugin.kt`:

```diff
--- ExamplePlugin.kt    2024-08-10 07:45:56.445244184 +0000
+++ HelloWorldPlugin.kt 2024-08-10 07:47:59.953060605 +0000
@@ -14,7 +14,7 @@
 }
 
 @TauriPlugin
-class ExamplePlugin(private val activity: Activity): Plugin(activity) {
+class HelloWorldPlugin(private val activity: Activity): Plugin(activity) {
     private val implementation = Example()
 
     @Command
```

そして、このプラグインを呼び出している箇所も変更する。

`src/mobile.rs`:

```diff
--- src/mobile_old.rs   2024-08-10 07:49:34.954659013 +0000
+++ src/mobile.rs       2024-08-10 07:49:57.512796998 +0000
@@ -18,7 +18,7 @@
   api: PluginApi<R, C>,
 ) -> crate::Result<Helloworld<R>> {
   #[cfg(target_os = "android")]
-  let handle = api.register_android_plugin(PLUGIN_IDENTIFIER, "ExamplePlugin")?;
+  let handle = api.register_android_plugin(PLUGIN_IDENTIFIER, "HelloWorldPlugin")?;
   #[cfg(target_os = "ios")]
   let handle = api.register_ios_plugin(init_plugin_helloworld)?;
   Ok(Helloworld(handle))
```

### 動作確認

どこでつまづくかわからんので、何か修正するたびに動作確認するのを推奨。

```sh
cd {PLUGIN_ROOT}
cd examples/tauri-app
npm run tauri android build -- --target aarch64 -d
adb.exe install \\wsl.localhost\Ubuntu-24.04\home\mikoto\project\TIL\tauri\2.0.0-beta\plugin\android\helloworld\tauri-plugin-helloworld\examples\tauri-app\src-tauri\gen\android\app\build\outputs\apk\universal\debug\app-universal-debug.apk
```

## プラグインの実装を変更

`Hello, World!` を返却するように実装を修正する。

`android/src/main/java/HelloWorldPlugin.kt`:

```diff
--- android/src/main/java/HelloWorldPlugin_old.kt       2024-08-10 07:56:36.663657163 +0000
+++ android/src/main/java/HelloWorldPlugin.kt   2024-08-10 07:59:07.338791931 +0000
@@ -15,14 +15,11 @@
 
 @TauriPlugin
 class HelloWorldPlugin(private val activity: Activity): Plugin(activity) {
-    private val implementation = Example()
 
     @Command
     fun ping(invoke: Invoke) {
-        val args = invoke.parseArgs(PingArgs::class.java)
-
         val ret = JSObject()
-        ret.put("value", implementation.pong(args.value ?: "default value :("))
+        ret.put("message", "Hello, World!")
         invoke.resolve(ret)
     }
 }
```

`ret` は、単純な JSON オブジェクトなので、要件に合わせてキーもバリューも変更可能。
今回は `value` を `message` に変更した。

## 呼び出されなくなった `Example.kt` を削除

```sh
rm android/src/main/java/Example.kt
```

## `value` を `message` に変えたので、呼び出し元を修正

### `src/desktop.rs`:

```diff
--- src/desktop_old.rs  2024-08-10 08:08:19.963657124 +0000
+++ src/desktop.rs      2024-08-10 08:06:01.223738063 +0000
@@ -16,7 +16,7 @@
 impl<R: Runtime> Helloworld<R> {
   pub fn ping(&self, payload: PingRequest) -> crate::Result<PingResponse> {
     Ok(PingResponse {
-      message: "Hello, World!",
+      value: payload.value,
     })
   }
 }
```

デスクトップ版のコードが `Hello, World!` を返却するようになっていなかったのでついでに直した。

### `src/models.rs`:

```diff
--- src/models_old.rs   2024-08-10 08:07:30.517003748 +0000
+++ src/models.rs       2024-08-10 08:07:35.770305605 +0000
@@ -9,5 +9,5 @@
 #[derive(Debug, Clone, Default, Deserialize, Serialize)]
 #[serde(rename_all = "camelCase")]
 pub struct PingResponse {
-  pub value: Option<String>,
+  pub message: Option<String>,
 }
```

返却する JSON のキーバリューに合わせて構造体のキーバリューも変更。

### `guest-js/index.ts`:

ここでプラグインからの戻り値を解析しているので、 `value` を `message` に変更。

```diff
--- guest-js/index_old.ts       2024-08-10 08:31:43.500586948 +0000
+++ guest-js/index.ts   2024-08-10 08:31:46.271840251 +0000
@@ -1,9 +1,9 @@
 import { invoke } from '@tauri-apps/api/core'
 
 export async function ping(value: string): Promise<string | null> {
-  return await invoke<{value?: string}>('plugin:helloworld|ping', {
+  return await invoke<{message?: string}>('plugin:helloworld|ping', {
     payload: {
       value,
     },
-  }).then((r) => (r.value ? r.value : null));
+  }).then((r) => (r.message ? r.message : null));
 }
```

## 動作確認

どこでつまづくかわからんので、何か修正するたびに動作確認するのを推奨。

```sh
cd {PLUGIN_ROOT}
npm run build
cd examples/tauri-app
npm run tauri android build -- --target aarch64 -d
adb.exe install \\wsl.localhost\Ubuntu-24.04\home\mikoto\project\TIL\tauri\2.0.0-beta\plugin\android\helloworld\tauri-plugin-helloworld\examples\tauri-app\src-tauri\gen\android\app\build\outputs\apk\universal\debug\app-universal-debug.apk
```

![挙動変更後の動作確認結果](https://github.com/user-attachments/assets/f2a6aa7f-97df-4b3f-b26e-1731dc4a979f)

# コマンド名の変更

ひな形では `ping` になっているので `hello` に直していく。

数が多いので無言で変更点だけ列挙。

## バックエンド

### `android/src/main/java/HelloWorldPlugin.kt`:

```diff
--- HelloWorldPlugin_old.kt     2024-08-10 08:43:37.958074978 +0000
+++ HelloWorldPlugin.kt 2024-08-10 08:43:54.033949948 +0000
@@ -17,7 +17,7 @@
 class HelloWorldPlugin(private val activity: Activity): Plugin(activity) {
 
     @Command
-    fun ping(invoke: Invoke) {
+    fun hello(invoke: Invoke) {
         val ret = JSObject()
         ret.put("message", "Hello, World!")
         invoke.resolve(ret)
```

### `build.rs`:

```diff
--- build_old.rs        2024-08-10 08:45:29.460349190 +0000
+++ build.rs    2024-08-10 08:45:36.816924888 +0000
@@ -1,4 +1,4 @@
-const COMMANDS: &[&str] = &["ping"];
+const COMMANDS: &[&str] = &["hello"];
 
 fn main() {
   tauri_plugin::Builder::new(COMMANDS)
```

### `src/mobile.rs`:

```diff
--- mobile_old.rs       2024-08-10 08:42:34.527832052 +0000
+++ mobile.rs   2024-08-10 08:47:32.356542347 +0000
@@ -28,10 +28,10 @@
 pub struct Helloworld<R: Runtime>(PluginHandle<R>);
 
 impl<R: Runtime> Helloworld<R> {
-  pub fn ping(&self, payload: PingRequest) -> crate::Result<PingResponse> {
+  pub fn hello(&self, payload: PingRequest) -> crate::Result<PingResponse> {
     self
       .0
-      .run_mobile_plugin("ping", payload)
+      .run_mobile_plugin("hello", payload)
       .map_err(Into::into)
   }
 }
```

### `src/desktop.rs`:

```diff
--- desktop_old.rs      2024-08-10 08:42:25.142614925 +0000
+++ desktop.rs  2024-08-10 08:48:37.711540195 +0000
@@ -14,7 +14,7 @@
 pub struct Helloworld<R: Runtime>(AppHandle<R>);
 
 impl<R: Runtime> Helloworld<R> {
-  pub fn ping(&self, payload: PingRequest) -> crate::Result<PingResponse> {
+  pub fn hello(&self, payload: PingRequest) -> crate::Result<PingResponse> {
     Ok(PingResponse {
       value: payload.value,
     })
```

### `src/command.rs`:

```diff
--- commands_old.rs     2024-08-10 08:42:16.608263014 +0000
+++ commands.rs 2024-08-10 08:50:13.923587186 +0000
@@ -5,9 +5,9 @@
 use crate::HelloworldExt;
 
 #[command]
-pub(crate) async fn ping<R: Runtime>(
+pub(crate) async fn hello<R: Runtime>(
     app: AppHandle<R>,
     payload: PingRequest,
 ) -> Result<PingResponse> {
-    app.helloworld().ping(payload)
+    app.helloworld().hello(payload)
 }
```

`app.helloworld().hello()` は、 `build.rs` の `const COMMANDS: &[&str] = &["hello"];` が効いているみたい。

### `src/lib.rs`:

```diff
--- lib_old.rs  2024-08-10 08:42:43.143107028 +0000
+++ lib.rs      2024-08-10 08:49:37.915377589 +0000
@@ -35,7 +35,7 @@
 /// Initializes the plugin.
 pub fn init<R: Runtime>() -> TauriPlugin<R> {
   Builder::new("helloworld")
-    .invoke_handler(tauri::generate_handler![commands::ping])
+    .invoke_handler(tauri::generate_handler![commands::hello])
     .setup(|app, api| {
       #[cfg(mobile)]
       let helloworld = mobile::init(app, api)?;
```

### `permissions/default.toml`:

```diff
--- default_old.toml    2024-08-10 08:59:59.670456017 +0000
+++ default.toml        2024-08-10 09:00:05.236614391 +0000
@@ -1,3 +1,3 @@
 [default]
 description = "Default permissions for the plugin"
-permissions = ["allow-ping"]
+permissions = ["allow-hello"]
```

これで、 `capability` に `helloworld:default` と書くだけで `hello` が許可される。

## フロントエンド

### `guest-js/index.ts`:

```diff
--- index_old.ts        2024-08-10 08:54:09.632238557 +0000
+++ index.ts    2024-08-10 08:54:18.780645318 +0000
@@ -1,7 +1,7 @@
 import { invoke } from '@tauri-apps/api/core'
 
-export async function ping(value: string): Promise<string | null> {
-  return await invoke<{message?: string}>('plugin:helloworld|ping', {
+export async function hello(value: string): Promise<string | null> {
+  return await invoke<{message?: string}>('plugin:helloworld|hello', {
     payload: {
       value,
     },
```

### `examples/tauri-app/src/App.svelte`:

```diff
--- App_old.svelte      2024-08-10 08:56:23.722477132 +0000
+++ App.svelte  2024-08-10 08:56:42.557878920 +0000
@@ -1,6 +1,6 @@
 <script>
   import Greet from './lib/Greet.svelte'
-  import { ping } from 'tauri-plugin-helloworld-api'
+  import { hello } from 'tauri-plugin-helloworld-api'
 
        let response = ''
 
@@ -8,8 +8,8 @@
                response += `[${new Date().toLocaleTimeString()}] ` + (typeof returnValue === 'strin
g' ? returnValue : JSON.stringify(returnValue)) + '<br>'
        }
 
-       function _ping() {
-               ping("Pong!").then(updateResponse).catch(updateResponse)
+       function _hello() {
+               hello("Pong!").then(updateResponse).catch(updateResponse)
        }
 </script>
 
@@ -37,7 +37,7 @@
   </div>
 
   <div>
-    <button on:click="{_ping}">Ping</button>
+    <button on:click="{_hello}">Hello</button>
     <div>{@html response}</div>
   </div>
 
```


## 動作確認

どこでつまづくかわからんので、何か修正するたびに動作確認するのを推奨。

```sh
cd {PLUGIN_ROOT}
npm run build
cd examples/tauri-app
npm run tauri android build -- --target aarch64 -d
adb.exe install \\wsl.localhost\Ubuntu-24.04\home\mikoto\project\TIL\tauri\2.0.0-beta\plugin\android\helloworld\tauri-plugin-helloworld\examples\tauri-app\src-tauri\gen\android\app\build\outputs\apk\universal\debug\app-universal-debug.apk
```

![コマンド名変更後の動作確認結果](https://github.com/user-attachments/assets/4b2b52a1-738f-4569-b4d6-15816f2ba3ad)

# 入出力の型名の変更

`PingRequest`, `PingResponse` となっているのでそれを修正する。

## `src/command.rs`:

```diff
--- commands_old.rs     2024-08-10 09:08:01.554610301 +0000
+++ commands.rs 2024-08-10 09:07:51.677197634 +0000
@@ -7,7 +7,7 @@
 #[command]
 pub(crate) async fn hello<R: Runtime>(
     app: AppHandle<R>,
-    payload: PingRequest,
-) -> Result<PingResponse> {
+    payload: HelloRequest,
+) -> Result<HelloResponse> {
     app.helloworld().hello(payload)
 }
```

## `src/desktop.rs`:

```diff
--- desktop_old.rs      2024-08-10 09:08:25.065568537 +0000
+++ desktop.rs  2024-08-10 09:08:37.947672343 +0000
@@ -14,8 +14,8 @@
 pub struct Helloworld<R: Runtime>(AppHandle<R>);
 
 impl<R: Runtime> Helloworld<R> {
-  pub fn hello(&self, payload: PingRequest) -> crate::Result<PingResponse> {
-    Ok(PingResponse {
+  pub fn hello(&self, payload: HelloRequest) -> crate::Result<HelloResponse> {
+    Ok(HelloResponse {
       value: payload.value,
     })
   }
```

## `src/mobile.rs`:

```diff
--- mobile_old.rs       2024-08-10 09:09:26.290321301 +0000
+++ mobile.rs   2024-08-10 09:09:51.865233902 +0000
@@ -28,7 +28,7 @@
 pub struct Helloworld<R: Runtime>(PluginHandle<R>);
 
 impl<R: Runtime> Helloworld<R> {
-  pub fn hello(&self, payload: PingRequest) -> crate::Result<PingResponse> {
+  pub fn hello(&self, payload: HelloRequest) -> crate::Result<HelloResponse> {
     self
       .0
       .run_mobile_plugin("hello", payload)
```

## `src/models.rs`:

```diff
--- models_old.rs       2024-08-10 09:10:21.880734202 +0000
+++ models.rs   2024-08-10 09:10:15.473778847 +0000
@@ -2,12 +2,12 @@
 
 #[derive(Debug, Deserialize, Serialize)]
 #[serde(rename_all = "camelCase")]
-pub struct PingRequest {
+pub struct HelloRequest {
   pub value: Option<String>,
 }
 
 #[derive(Debug, Clone, Default, Deserialize, Serialize)]
 #[serde(rename_all = "camelCase")]
-pub struct PingResponse {
+pub struct HelloResponse {
   pub message: Option<String>,
 }
```

※ フロントエンドは `{message?: string}` という感じで型に名前を付けてないので変更なし。


## 動作確認

どこでつまづくかわからんので、何か修正するたびに動作確認するのを推奨。

```sh
cd {PLUGIN_ROOT}
npm run build
cd examples/tauri-app
npm run tauri android build -- --target aarch64 -d
adb.exe install \\wsl.localhost\Ubuntu-24.04\home\mikoto\project\TIL\tauri\2.0.0-beta\plugin\android\helloworld\tauri-plugin-helloworld\examples\tauri-app\src-tauri\gen\android\app\build\outputs\apk\universal\debug\app-universal-debug.apk
```


# 入出力変数のフィールド変更

入力に値はいらないので削除、出力は今のままでOK.

## バックエンド

### `src/desktop.rs`:

```diff
--- desktop_old.rs      2024-08-10 09:27:30.922875662 +0000
+++ desktop.rs  2024-08-10 09:28:06.748192666 +0000
@@ -14,9 +14,9 @@
 pub struct Helloworld<R: Runtime>(AppHandle<R>);
 
 impl<R: Runtime> Helloworld<R> {
-  pub fn hello(&self, payload: HelloRequest) -> crate::Result<HelloResponse> {
+  pub fn hello(&self) -> crate::Result<HelloResponse> {
     Ok(HelloResponse {
       value: "Hello, World!",
     })
```

### `src/mobile.rs`:

```diff
--- mobile_old.rs       2024-08-10 09:29:03.239267742 +0000
+++ mobile.rs   2024-08-10 09:29:13.963827352 +0000
@@ -28,10 +28,10 @@
 pub struct Helloworld<R: Runtime>(PluginHandle<R>);
 
 impl<R: Runtime> Helloworld<R> {
-  pub fn hello(&self, payload: HelloRequest) -> crate::Result<HelloResponse> {
+  pub fn hello(&self) -> crate::Result<HelloResponse> {
     self
       .0
-      .run_mobile_plugin("hello", payload)
+      .run_mobile_plugin("hello", {})
       .map_err(Into::into)
   }
 }
```


### `src/commands.rs`:

```diff
--- commands_old.rs     2024-08-10 11:36:36.714869837 +0000
+++ commands.rs 2024-08-10 11:36:39.536555095 +0000
@@ -7,7 +7,6 @@
 #[command]
 pub(crate) async fn hello<R: Runtime>(
     app: AppHandle<R>,
-    payload: HelloRequest,
 ) -> Result<HelloResponse> {
-    app.helloworld().hello(payload)
+    app.helloworld().hello()
 }
```

### `src/models.rs`:

```diff
--- models_old.rs       2024-08-10 09:15:16.789108196 +0000
+++ models.rs   2024-08-10 09:16:05.562759826 +0000
@@ -2,9 +2,7 @@
 
 #[derive(Debug, Deserialize, Serialize)]
 #[serde(rename_all = "camelCase")]
-pub struct HelloRequest {
-  pub value: Option<String>,
-}
+pub struct HelloRequest { }
 
 #[derive(Debug, Clone, Default, Deserialize, Serialize)]
 #[serde(rename_all = "camelCase")]
```

## フロントエンド

### `guest-js/index.ts`:

```diff
--- index_old.ts        2024-08-10 09:18:45.723552366 +0000
+++ index.ts    2024-08-10 09:18:49.203552425 +0000
@@ -1,9 +1,6 @@
 import { invoke } from '@tauri-apps/api/core'
 
 export async function hello(value: string): Promise<string | null> {
-  return await invoke<{message?: string}>('plugin:helloworld|hello', {
-    payload: {
-      value,
-    },
-  }).then((r) => (r.message ? r.message : null));
+  return await invoke<{message?: string}>('plugin:helloworld|hello', {})
+    .then((r) => (r.message ? r.message : null));
 }
```

### `examples/tauri-app/src/App.svelte`:

```diff
--- App_old.svelte      2024-08-10 09:20:24.190648621 +0000
+++ App.svelte  2024-08-10 09:20:27.197116066 +0000
@@ -9,7 +9,7 @@
        }
 
        function _hello() {
-               hello("Pong!").then(updateResponse).catch(updateResponse)
+               hello().then(updateResponse).catch(updateResponse)
        }
 </script>
```


## 動作確認

ここまでで一通り hello 向けに修正したので、最後の動作確認。

```sh
cd {PLUGIN_ROOT}
npm run build
cd examples/tauri-app
npm run tauri android build -- --target aarch64 -d
adb.exe install \\wsl.localhost\Ubuntu-24.04\home\mikoto\project\TIL\tauri\2.0.0-beta\plugin\android\helloworld\tauri-plugin-helloworld\examples\tauri-app\src-tauri\gen\android\app\build\outputs\apk\universal\debug\app-universal-debug.apk
```

# 参考資料

- [Improvement plugin by mikoto2000 · Pull Request #2 · mikoto2000/OASIZ_TimeLogger2](https://github.com/mikoto2000/OASIZ_TimeLogger2/pull/2)

