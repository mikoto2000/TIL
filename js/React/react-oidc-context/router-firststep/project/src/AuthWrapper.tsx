import { Fragment, ReactNode } from "react";
import { useAuth } from "react-oidc-context";

type AuthWrapperProps = {
  auth?: ReactNode;
  noAuth?: ReactNode;
};

function AuthWrapper(props: AuthWrapperProps) {
  const auth = useAuth();

  if (auth.isLoading || auth.error) {
      return <></>;
  }

  if (auth.isAuthenticated) {
    return (
      <Fragment>{ props.auth }</Fragment>
    );
  } else {
    return (
        <Fragment>{ props.noAuth }</Fragment>
    );
  }
}

export default AuthWrapper;

