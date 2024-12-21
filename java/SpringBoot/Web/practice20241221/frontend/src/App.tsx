import './App.css'
import { Link, Route, Routes } from 'react-router'
import { BookMastersPage } from './pages/bookmaster/BookMastersPage'
import { AuthorsPage } from './pages/author/AuthorsPage'
import { BookMasterCreatePage } from './pages/bookmaster/BookMasterCreatePage'

function App() {
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
        <AuthorsPage />
      }
      />
      < Route path="/bookMasters" element={
        <BookMastersPage />
      }
      />
      < Route path="/bookMasters/create" element={
        <BookMasterCreatePage />
      }
      />
    </Routes>
  )
}

export default App
