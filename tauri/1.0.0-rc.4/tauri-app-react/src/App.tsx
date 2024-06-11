import React, { useState, useEffect } from 'react';

import { invoke } from '@tauri-apps/api/tauri'

type AppProps = {
  initialMessage: string
};

function App(props: AppProps) {
  const {initialMessage} = props;
  const [message, setMessage] = useState(initialMessage);

  useEffect(() => {
    console.log(`start useEffect, message is ${message}.`);
    (async () => {
      const echoMessage = await invoke('echo', {message: "Hello, World!!!!!"});
      setMessage((echoMessage as string));
    })()
  }, []);

  return (
    <div className="App">
      {message}
    </div>
  );
}

export default App;
