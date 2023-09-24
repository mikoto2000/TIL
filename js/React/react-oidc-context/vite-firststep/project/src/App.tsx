import React from 'react';
import { Routes, Route } from "react-router-dom";

import AuthPage from './AuthPage';
import AuthWrapper from './AuthWrapper';
import Header from './Header';
import PublicPage from './PublicPage';

function App() {
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
    </div>
  );
}

export default App;
