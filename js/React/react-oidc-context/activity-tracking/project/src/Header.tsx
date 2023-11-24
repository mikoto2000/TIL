import React from 'react';
import { Link } from "react-router-dom";

import AuthWrapper from './AuthWrapper';
import LogInOut from './LogInOut';
import { useTokenManager } from './TokenManager';

function Header() {
  useTokenManager();

  return (
    <header>
      <LogInOut />
      <nav>
        <Link to="public" >一般公開ページ</Link>
        { /* ログイン済みユーザーにだけ見せたい要素は AuthWrapper の auth に渡す */ }
        <AuthWrapper auth={
          <React.Fragment>
            &nbsp; - &nbsp;
            <Link to="auth" >認証必須ページ</Link>
          </React.Fragment>
        } />
        { /* ロールのあるユーザーにだけ見せたい要素は AuthWrapper の auth に渡す */ }
        <AuthWrapper role="admin" auth={
          <React.Fragment>
            &nbsp; - &nbsp;
            <Link to="admin" >admin ページ</Link>
          </React.Fragment>
        } />
      </nav>
    </header>
  );
}

export default Header;

