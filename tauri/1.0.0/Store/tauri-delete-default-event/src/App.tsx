import { useEffect, useState } from "react";
import reactLogo from "./assets/react.svg";
import "./App.css";

import { Store } from "tauri-plugin-store-api";

function App() {
  const [greetMsg, setGreetMsg] = useState("");
  const [name, setName] = useState("");

  useEffect(() => {
    (async () => {
      // ストアから情報を抽出して存在するなら表示
      const store = new Store("greet.json");
      const name = await store.get("name");
      setName(name?.value);
    })();

  }, []);

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
        }}
      >
        <input
          id="greet-input"
          onChange={ async (e) => {
            setName(e.currentTarget.value)

            // ストアに保存
            const store = new Store("greet.json");
            await store.set("name", { value: e.currentTarget.value });
            store.save();
          }}
          placeholder="Enter a name..."
          value={name}
        />
        <button type="submit">Greet</button>
      </form>

      <p>{greetMsg}</p>
    </div>
  );
}

export default App;
