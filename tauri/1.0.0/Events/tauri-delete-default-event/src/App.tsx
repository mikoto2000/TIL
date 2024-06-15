import { useState } from "react";
import reactLogo from "./assets/react.svg";
import "./App.css";

import { emit, listen } from '@tauri-apps/api/event'

function App() {
  const [greetMsg, setGreetMsg] = useState("");
  const [name, setName] = useState("");

  const [greetEndedMsg, setGreetEndedMsg] = useState("");

  {/* `greet_ended` イベントをリッスン */}
  listen('greet_ended', (event) => {
    setGreetEndedMsg(JSON.stringify(event));
  })

  return (
    <div className="container">
      <h1>Welcome to Tauri!</h1>

      <div className="row">
        <a href="https://vitejs.dev" target="_blank">
          <img src="/vite.svg" className="logo vite" alt="Vite logo" />
        </a>
        <a href="https://tauri.app" target="_blank">
          <img src="/tauri.svg" className="logo tauri" alt="Tauri logo" />
        </a>
        <a href="https://reactjs.org" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>

      <p>Click on the Tauri, Vite, and React logos to learn more.</p>

      <form
        className="row"
        onSubmit={(e) => {
          e.preventDefault();
          setGreetMsg(`Hello, ${name}! You've been greeted from Rust!`);

          {/*ボタン押下で `greet` イベントを emit */}
          emit('greet', { greet_message: name })
        }}
      >
        <input
          id="greet-input"
          onChange={(e) => setName(e.currentTarget.value)}
          placeholder="Enter a name..."
        />
        <button type="submit">Greet</button>
      </form>

      <p>{greetMsg}</p>

      {/* 受信した `greet_ended` イベントのメッセージを表示 */}
      <p>{greetEndedMsg}</p>
    </div>
  );
}

export default App;
