import { useEffect, useState } from 'react'
import './App.css'
import { AuthorEntityControllerApiFactory, BookMasterEntityControllerApiFactory, Configuration, EntityModelAuthor, EntityModelBookMaster } from './api'
import { Link, Route, Routes } from 'react-router'

const BASE_URL = "http://localhost:8080"

function App() {
  const [authors, setAuthors] = useState<EntityModelAuthor[] | undefined>([])
  const [bookMasters, setBookMasters] = useState<EntityModelBookMaster[] | undefined>([])

  useEffect(() => {
    (async () => {
      const authorApiFactory = AuthorEntityControllerApiFactory(new Configuration(), BASE_URL);
      const authorsResult = await authorApiFactory.getCollectionResourceAuthorGet({})
      console.log(authorsResult);
      const authorsData = await authorsResult.data;
      console.log(authorsData);

      setAuthors(authorsData._embedded?.authors);

      const bookMasterApiFactory = BookMasterEntityControllerApiFactory(new Configuration(), BASE_URL);
      const bookMastersResult = await bookMasterApiFactory.getCollectionResourceBookmasterGet({});
      console.log(bookMastersResult);
      const bookMastersData = await bookMastersResult.data;
      console.log(bookMastersData);

      setBookMasters(bookMastersData._embedded?.bookMasters);
    })();
  }, []);

  return (
    <Routes>
      <Route path="/" element={
        <ul>
          <li><Link to="/authors">Authors</Link></li>
          <li><Link to="/bookMasters">BookMasters</Link></li>
        </ul>
      }
      />
      <Route path="/authors" element={
        <ul>
          {
            authors
              ?
              authors.map((e) => <li>Name: {e.name}</li>)
              :
              "表示するものがありませんでした。"
          }
        </ul>
      }
      />
      <Route path="/bookMasters" element={
        <ul>
          {
            bookMasters
              ?
              bookMasters.map((e) => <li>Name: {e.name}</li>)
              :
              "表示するものがありませんでした。"
          }
        </ul>
      }
      />
    </Routes>
  )
}

export default App
