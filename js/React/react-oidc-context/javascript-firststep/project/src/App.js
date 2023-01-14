// src/App.jsx
import React from "react";
import { useAuth } from "react-oidc-context";

function App() {
    const auth = useAuth();

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
        <div>
            <p>
                User: {auth.user?.profile?.preferred_username}
            </p>
            <p>
                AccessToken: {auth.user?.access_token}
            </p>
            <p>
                {JSON.stringify(auth)}
            </p>
            <button onClick={() => void auth.removeUser()}>Log out</button>
        </div>
        );
    }

    return <button onClick={() => void auth.signinRedirect()}>Log in</button>;
}

export default App;
