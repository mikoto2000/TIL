import './App.css'
import { Link, Route, Routes } from 'react-router'
import { BookMastersPage } from './pages/BookMastersPage'
import { AuthorsPage } from './pages/AuthorsPage'

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
    </Routes>
  )
}

export default App
