import React, { useState } from 'react';
import { Routes, Route } from "react-router-dom";
import { useAuth } from "react-oidc-context";

import AuthPage from './AuthPage';
import AuthWrapper from './AuthWrapper';
import Header from './Header';
import PublicPage from './PublicPage';

type User = {
    name: string;
};

function App() {
  const auth = useAuth();
  const [users, setUsers] = useState<User[]>([]);
  const [tests, setTests] = useState<User[]>([]);

  function getUsers(): void {
    const access_token = auth.user?.access_token;

    fetch("http://host.docker.internal:8081/users", {
      headers: {
        "authorization": "Bearer " + access_token
      },
      body: null,
      method: "GET",
      mode: "cors"
    })
    .then((response) => { return response.json() })
    .then((json) => { setUsers(json); console.log(json) });
  }

  function getTest(): void {
    const access_token = auth.user?.access_token;

    fetch("http://host.docker.internal:8081/test", {
      method: 'GET',
      mode: 'cors',
      cache: 'no-cache',
      headers: {
        'Authorization': 'Bearer ' + access_token
      }
    })
    .then((response) => { return response.json() })
    .then((json) => { setTests(json); console.log(json) });
  }

  return (
    <div className="App">
      <Header />
      <h1>react-oidc-context と react-router を組み合わせて RBAC する</h1>
      <Routes>
        <Route path="/public" element={
          <PublicPage />
        } />
        { /* ログイン済みユーザーにだけ見せたい要素は AuthWrapper の auth に渡す */ }
        { /* 未ログインの時に見せる要素は AuthWrapper の noAuth に渡す */ }
        <Route path="/auth" element={
          <AuthWrapper
            auth={<AuthPage />}
            noAuth={
            <React.Fragment>
                <p>You need loged in.</p>
            </React.Fragment>
          } />
        } />
        { /* ログイン済み、かつ、指定のロールを持つユーザーにだけ見せたい要素は AuthWrapper の role と auth を渡す */ }
        <Route path="/admin" element={
          <AuthWrapper
            role="admin"
            auth={<React.Fragment><div>admin ページですよー</div></React.Fragment>}
            noAuth={
            <React.Fragment>
                <p>Not Found.</p>
            </React.Fragment>
          } />
        } />
        <Route path="/" element={
          <React.Fragment>
          </React.Fragment>
        } />
        <Route path="*" element={
          <React.Fragment>
            <div>
              Not Found.
            </div>
          </React.Fragment>
        } />
      </Routes>
      <button onClick={getUsers}>fetch users</button>
      <button onClick={getTest}>fetch test</button>
      <div>
        users:
        <ul>
          {
            users.map(user => <li>{user.name}</li>)
          }
        </ul>
      </div>
      <div>
        tests:
        <ul>
          {
            tests.map(test => <li>{test.name}</li>)
          }
        </ul>
      </div>
    </div>
  );
}

export default App;
