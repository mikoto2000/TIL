import { useState } from "react";
import "./App.css";
import { invoke } from "@tauri-apps/api";

function App() {

  const [count, setCount] = useState(0);

  const [kenPaStack, setKenPaStack] = useState<Array<string>>([]);

  async function incCount() {
    let count = await invoke<number>('inc_count', {});
    setCount(count);
  }

  async function decCount() {
    let count = await invoke<number>('dec_count', {});
    setCount(count);
  }

  async function putKen() {
    invoke<number>('put_ken', {});
  }

  async function putPa() {
    invoke<number>('put_pa', {});
  }

  async function getKenPaStack() {
    let kenPaStack = await invoke<Array<string>>('get_ken_pa_stack', {});
    setKenPaStack(kenPaStack);
  }

  return (
    <div className="container">
      <h1>Welcome to Tauri!</h1>

      <h2>Counter example:</h2>
      <div>
        <button onClick={decCount}>-</button>
        <input type="text" value={count} readOnly></input>
        <button onClick={incCount}>+</button>
      </div>

      <h2>KEN-PA example:</h2>
        <button onClick={putKen}>けん</button>
        <button onClick={putPa}>ぱ</button>
        <button onClick={getKenPaStack}>けんけんぱできた？</button>
        <div>
          <ul>
          {
            kenPaStack.map((e) => <li>{e}</li>)
          }
          </ul>
        </div>
    </div>
  );
}

export default App;
