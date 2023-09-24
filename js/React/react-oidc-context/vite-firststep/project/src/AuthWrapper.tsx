import { Fragment, ReactNode } from "react";
import { useAuth } from "react-oidc-context";

type AuthWrapperProps = {
  role?: string;
  auth?: ReactNode;
  noAuth?: ReactNode;
};

function AuthWrapper(props: AuthWrapperProps) {
  const auth = useAuth();

  if (auth.isLoading || auth.error) {
      return <></>;
  }

  if (auth.isAuthenticated) {
    if (!props.role) {
      return (<Fragment>{ props.auth }</Fragment>);
    } else {
      if (((auth.user?.profile?.realm_access as any)?.roles as string[])
              && ((auth.user?.profile?.realm_access as any)?.roles as string[]).includes(props.role)) {
        return (
          <Fragment>{ props.auth }</Fragment>
        );
      } else {
        return (
            <Fragment>{ props.noAuth }</Fragment>
        );
      }
    }
  } else {
    return (
        <Fragment>{ props.noAuth }</Fragment>
    );
  }
}

export default AuthWrapper;

