import { useState } from "react";
import reactLogo from "./assets/react.svg";
import "./App.css";

import { invoke } from '@tauri-apps/api/tauri'

function App() {
  const [greetMsg, setGreetMsg] = useState("");
  const [name, setName] = useState("");

  const [sumResult, setSumResult] = useState("");

  const [asyncCommandResult, setAsyncCommandResult] = useState([]);

  const [successOrFailedMessage, setSuccessOrFailedMessage] = useState("");

  function callImplementedCommandFunction() {
    invoke('implemented_command_function');
  }

  function callSum() {
    invoke('sum', {xForSum: 1, yForSum: 2})
      .then((result) => setSumResult(result));
  }

  function callAsyncCommand() {
    invoke('async_command')
      .then((result) => {
        setAsyncCommandResult((x) => [...x, result])
      })
  }

  function callSuccessOrFailed(success) {
    invoke('success_or_failed', {success: success})
      .then((result) => {
        setSuccessOrFailedMessage(result)
      })
      .catch((error) => {
        setSuccessOrFailedMessage(error)
      })
  }

  return (
    <div className="container">
      <h1>Welcome to Tauri!</h1>

      <div>
        <label>
          Call implemented_command_function command:
          <button
            onClick={() => {callImplementedCommandFunction()}}>
            call
          </button>
        </label>
      </div>

      <div>
        <label>
          Execute 1 + 2: {sumResult}
          <button
            onClick={() => {callSum()}}>
            call
          </button>
          <button
            onClick={() => setSumResult("")}>
            Clear
          </button>
        </label>
      </div>

      <div>
        <label>
          Execute asyncCommand:
          <button
            onClick={() => {callAsyncCommand()}}>
            call
          </button>
          <button
            onClick={() => setAsyncCommandResult([])}>
            Clear
          </button>
        </label>
        <ol>
          {
            asyncCommandResult.map((e) => <li>{e}</li>)
          }
        </ol>
      </div>

      <div>
        <label>
          Execute successOrFailed:
          <button
            onClick={() => {callSuccessOrFailed(true)}}>
            call success
          </button>
          <button
            onClick={() => {callSuccessOrFailed(false)}}>
            call failed
          </button>
          <button
            onClick={() => setSuccessOrFailedMessage("")}>
            Clear
          </button>
        </label>
        <p>{successOrFailedMessage}</p>
      </div>

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
          onChange={(e) => setName(e.currentTarget.value)}
          placeholder="Enter a name..."
        />
        <button type="submit">Greet</button>
      </form>

      <p>{greetMsg}</p>
    </div>
  );
}

export default App;
