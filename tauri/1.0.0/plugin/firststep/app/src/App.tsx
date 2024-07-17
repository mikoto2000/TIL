import { hello } from "tauri-plugin-helloworld-api";
import "./App.css";

function App() {

  return (
    <div className="container">
      <button onClick={() => hello()}>hello</button>
    </div>
  );
}

export default App;
