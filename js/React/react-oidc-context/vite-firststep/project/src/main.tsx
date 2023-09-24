import './main.css'
import App from './App.tsx'
import React from 'react'
import ReactDOM from 'react-dom/client'
import { AuthProvider } from "react-oidc-context";
import { BrowserRouter } from "react-router-dom";

const oidcConfig = {
    authority:"http://localhost:8080/realms/myoidc",
    client_id: "myoidc-client",
    client_secret: "myoidc-secret",
    redirect_uri: "http://localhost:3000",
    onSigninCallback: () => {
        // セッションストレージから元居たパスを取得セッションストレージ内のデータは削除
        const redirectLocation = sessionStorage.getItem('path');
        sessionStorage.removeItem('path');

        if (redirectLocation) {
            // ローカルストレージにパスがあったらそこにリダイレクト
            window.location.replace(redirectLocation);
        } else {
            // クエリパラメータに色々な情報が載っているので、それを削除する
            window.history.replaceState(
                {},
                document.title,
                window.location.pathname
            );
        }
    }
}

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <AuthProvider {...oidcConfig}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </AuthProvider>
  </React.StrictMode>
)
