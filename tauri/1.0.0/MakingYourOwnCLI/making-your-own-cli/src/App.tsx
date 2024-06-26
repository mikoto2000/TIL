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

  const args = matches?.args;
  const lastArgsArray = args?.lastArgs?.value as string[]
  const subcommand = matches?.subcommand;
  const subcommandArgs = subcommand?.matches?.args;
  const subcommandLastArgsArray = subcommandArgs?.lastArgs?.value as string[]

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

      {!subcommand
        ?
        <div>
          <h1>Option parse result:</h1>
          <ul>
            <li>option1: {args?.option1?.value}</li>
            <li>option2: {JSON.stringify(args?.option2?.value)}</li>
            <li>option3: {args?.option3?.value}</li>
            <li>flagOption: {args?.flagOption?.value}
              and
              occurences: {args?.flagOption?.occurrences}</li>
            <li>flagOptionWithOccurrence: {args?.flagOptionWithOccurrence?.value}
              and
              occurences: {args?.flagOptionWithOccurrence?.occurrences}</li>
            <li>firstArg: {args?.firstArg?.value}</li>
            <li>secondArg: {args?.secondArg?.value}</li>
            <li>lastArgs: {lastArgsArray ?
              JSON.stringify(lastArgsArray)
              :
              <></>
            }
              </li>
          </ul>
        </div>
        :
        <div>
          <h1>Subcommand parse result:</h1>
          <ul>
            <li>option1: {subcommandArgs?.option1?.value}</li>
            <li>option2: {JSON.stringify(subcommandArgs?.option2?.value)}</li>
            <li>option3: {subcommandArgs?.option3?.value}</li>
            <li>flagOption: {subcommandArgs?.flagOption?.value}
              and
              occurences: {subcommandArgs?.flagOption?.occurrences}</li>
            <li>flagOptionWithOccurrence: {subcommandArgs?.flagOptionWithOccurrence?.value}
              and
              occurences: {subcommandArgs?.flagOptionWithOccurrence?.occurrences}</li>
            <li>firstArg: {subcommandArgs?.firstArg?.value}</li>
            <li>secondArg: {subcommandArgs?.secondArg?.value}</li>
            <li>lastArgs: {subcommandLastArgsArray ?
              JSON.stringify(subcommandLastArgsArray)
              :
              <></>
            }
              </li>
          </ul>
        </div>
      }

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
