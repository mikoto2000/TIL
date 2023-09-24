import React from "react";
import { useAuth } from "react-oidc-context";
import { useLocation } from 'react-router-dom';

function LogInOut() {
    const auth = useAuth();
    const location = useLocation();

    switch (auth.activeNavigator) {
        case "signinSilent":
            return <div>Signing you in...</div>;
        case "signoutRedirect":
            return <div>Signing you out...</div>;
    }

    if (auth.isLoading) {
        return <div>Loading...</div>;
    }

    if (auth.error) {
        return <div>Oops... {auth.error.message}</div>;
    }

    if (auth.isAuthenticated) {
        return (
          <React.Fragment>
            <div>
              User: {auth.user?.profile?.preferred_username}, <br></br>
              <pre>
                Roles: {JSON.stringify((auth.user?.profile?.realm_access as any)?.roles, null, 2)}
              </pre>
            </div>
            <button onClick={() => {
                // セッションストレージ・ローカルストレージからユーザー情報を削除
                auth.removeUser();

                // OIDC 認可サーバーにセッションを破棄するように依頼して、
                // 破棄したら post_logout_redirect_uri にリダイレクトする
                auth.signoutRedirect({
                    id_token_hint: auth.user?.id_token,
                    post_logout_redirect_uri: "http://localhost:3000"
                });
            }}>Log out</button>
          </React.Fragment>
        );
    }

    return <button onClick={() => {
        // ログイン後に元居たページに戻れるように、
        // 今いるパスをセッションストレージに記憶しておく
        sessionStorage.setItem('path', location.pathname);

        // state の削除
        auth.clearStaleState();

        // 認可サーバーにアクセスしてログイン、
        // ログインに成功したら redirect_uri にリダイレクト
        auth.signinRedirect();
    }}>Log in</button>;
}

export default LogInOut;

