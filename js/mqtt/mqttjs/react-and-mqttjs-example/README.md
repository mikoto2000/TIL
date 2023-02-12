# React and MQTT.js example

## create project

```sh
npm init vite@latest # choose React and TypeScript

cd frontend

npm i mqtt @types/mqtt ws

npm i --save-dev @types/mqtt @types/node @types/ws vite-tsconfig-paths

npm run dev -- --host 0.0.0.0
```

## References:

- [Vite | 次世代フロントエンドツール](https://ja.vitejs.dev/)
- [環境変数とモード | Vite](https://ja.vitejs.dev/guide/env-and-mode.html)
- [サーバオプション | Vite](https://ja.vitejs.dev/config/server-options.html)
- [mqttjs/MQTT.js: The MQTT client for Node.js and the browser](https://github.com/mqttjs/MQTT.js)
- [vite support · Issue #1269 · mqttjs/MQTT.js](https://github.com/mqttjs/MQTT.js/issues/1269?utm_source=pocket_saves#issuecomment-984864265)
