import { invoke } from "@tauri-apps/api";
import "./App.css";
import { emit } from "@tauri-apps/api/event";

function App() {

  return (
    <div className="container">
      <h1>Welcome to Tauri!</h1>

      <button onClick={() => {
        invoke('print_xdg_base_directories', {});
      }}>コマンド実行</button>
      <button onClick={() => {
        emit('event', {});
      }}>イベント送信</button>

    </div>
  );
}

export default App;
