import { useState } from "react";
import reactLogo from "./assets/react.svg";
import "./App.css";

import { CliMatches, getMatches } from '@tauri-apps/api/cli'

function App() {
  const [greetMsg, setGreetMsg] = useState("");
  const [name, setName] = useState("");

  const [matches, setMatches] = useState<CliMatches|null>(null);

  getMatches().then((matches) => {
    setMatches(matches);
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

      <div>
        <ul>
          <li>option1: {matches?.args?.option1?.value?.toString()}</li>
          <li>option2: {matches?.args?.option2?.value?.toString()}</li>
          <li>option3: {matches?.args?.option3?.value?.toString()}</li>
          <li>firstArg: {matches?.args?.firstArg?.value?.toString()}</li>
          <li>secondArg: {matches?.args?.secondArg?.value?.toString()}</li>
          <li>flagOption: {matches?.args?.flagOption?.value?.toString()}
            and
            occurences: {matches?.args?.flagOption?.occurrences}</li>
          <li>flagOptionWithOccurrence: {matches?.args?.flagOptionWithOccurrence?.value?.toString()}
            and
            occurences: {matches?.args?.flagOptionWithOccurrence?.occurrences}</li>
        </ul>
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
