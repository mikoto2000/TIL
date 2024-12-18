import { useEffect, useState } from 'react'
import './App.css'
import { AuthorEntityControllerApiFactory, Configuration, EntityModelAuthor } from './api'

const BASE_URL = "http://localhost:8080"

function App() {
  const [authors, setAuthors] = useState<EntityModelAuthor[] | undefined>([])

  useEffect(() => {
    (async () => {
      const authorApiFactory = AuthorEntityControllerApiFactory(new Configuration(), BASE_URL);
      const authorsResult = await authorApiFactory.getCollectionResourceAuthorGet({})
      console.log(authorsResult);
      const authorsData = await authorsResult.data;
      console.log(authorsData);

      setAuthors(authorsData._embedded?.authors);
    })();
  }, []);

  return (
    <>
      <ul>
        {
          authors
            ?
            authors.map((e) => <li>{e.name}</li>)
            :
            "表示するものがありませんでした。"
        }
      </ul>
    </>
  )
}

export default App
