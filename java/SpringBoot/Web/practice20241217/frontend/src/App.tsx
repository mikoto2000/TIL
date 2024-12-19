import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

const BASE_URL = "http://localhost:8080"

function App() {
  const [authors, setAuthors] = useState([])

  useEffect(() => {
    (async () => {
      const authorsResult = await fetch(BASE_URL + "/authors");
      const authorsJson = await authorsResult.json();
      console.log(authorsJson);
      setAuthors(authorsJson._embedded.authors);
    })();
  }, []);

  return (
    <>
      <ul>
        {
          authors.map((e) => <li>{e.name}</li>)
        }
      </ul>
    </>
  )
}

export default App
