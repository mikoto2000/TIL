import { useEffect, useState } from "react";
import "./App.css";
import { appWindow } from "@tauri-apps/api/window";

function App() {

  const [dropFiles, setDropFiles] = useState<string[]>([]);

  useEffect(() => {
    let unlisten: any = null;
    if (!unlisten) {
      (async () => {
        // ファイルのドロップを購読
        unlisten = await appWindow.onFileDropEvent((event) => {
          if (event.payload.type === 'hover') {
            console.log('User hovering', event);
          } else if (event.payload.type === 'drop') {
            console.log('User dropped', event);
            setDropFiles(event.payload.paths);
          } else {
            console.log('File drop cancelled');
          }
        });
      })();
    }

    return () => {
      if (unlisten) {
        unlisten();
      }
    }
  })

  return (
    <div className="container">
      <h1>Welcome to Tauri!</h1>

      <h2>Drop files:</h2>
      <ul>
        { /* ドラッグアンドドロップで受け取ったファイルのファイルパスを表示 */
          dropFiles.map((filePath) => <li key={filePath}>{filePath}</li>)
        }
      </ul>

    </div>
  );
}

export default App;
