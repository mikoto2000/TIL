import { useEffect, useState } from "react";
import "./App.css";
import { WebviewWindow, appWindow, getCurrent } from "@tauri-apps/api/window";
import { emit, listen } from "@tauri-apps/api/event";
import { invoke } from "@tauri-apps/api";

function App() {
  const currentWindow = getCurrent();
  const [receivedEvent, setReceivedEvent] = useState<Array<any>>([]);

  let initialized = false;

  {/* useEffect を使って `event` という名前のイベントを購読する */}
  useEffect(() => {
    if (!initialized) {
      let unlisten: any = undefined;
      (async () => {
        unlisten = await listen('event', (e: any) => {
          console.log(e);
          setReceivedEvent((prev) => [...prev, e])
        });
      })();

      initialized = true;

      return () => {
        if (unlisten) {
          unlisten();
        }
      };
    }
  }, []);

  return (
    <div className="container">
      <h1>Welcome to Tauri!</h1>

      {/* getCurrent で、現在の WebviewWindow オブジェクトが取得できる */}
      <p>This window is: `{currentWindow.label}`</p>

      <h2>フロントエンドからのイベント送信</h2>

      <button onClick={() => {
        emit('event', { message: `${currentWindow.label}からのイベント` })
      }}>表示中の全ウィンドウへイベント送信</button>
      <button onClick={() => {
        appWindow.emit('event', { message: `${currentWindow.label}からのイベント` })
      }}>自分自身へイベント送信</button>
      <button onClick={() => {
        const mainWindow = new WebviewWindow('main');
        mainWindow.emit('event', { message: `${currentWindow.label}からのイベント` })
      }}>mainへのイベント送信</button>
      <button onClick={() => {
        const subWindow = new WebviewWindow('sub');
        subWindow.emit('event', { message: `${currentWindow.label}からのイベント` })
      }}>subへのイベント送信</button>

      <h2>バックエンドからのイベント送信</h2>

      <button onClick={() => {
        invoke('to_all', {});
      }}>表示中の全ウィンドウへイベント送信</button>
      <button onClick={() => {
        invoke('to_main', {});
      }}>mainへのイベント送信</button>
      <button onClick={() => {
        invoke('to_sub', {});
      }}>subへのイベント送信</button>

      <ul>
        {receivedEvent.map((e) => <li key={e.id}>{JSON.stringify(e)}</li>)}
      </ul>
    </div>
  );
}

export default App;
