import { useActivityWatcher } from './ActivityWatcher';
import { useAuth } from "react-oidc-context";
import { useEffect } from 'react';

/**
 * 「未操作 x 時間でログアウト」を実現するために、トークンの更新タイミングを管理する。
 *
 * 何かしら画面に触った場合、次回のトークン自動更新を有効にし、
 * 画面に全く触らなかった場合、次回のトークン自動更新を無効にする。
 */
export const useTokenManager = () =>{
  const auth = useAuth();

  // アクティビティがあったら、次回の自動リフレッシュを行うように設定
  useActivityWatcher(() => {
    console.log("handleActivity");
    auth.startSilentRenew()
  });

  useEffect(() => {
    // アクセストークンの期限切れが発生したら、アラートを表示してサインアウト
    const accessTokenExpiredEvent = auth.events.addAccessTokenExpired(() => {
      alert("トークン期限切れ");
      auth.signoutRedirect({
        id_token_hint: auth.user?.id_token,
        post_logout_redirect_uri: globalThis.location.href
      });
    });

    // 自動リフレッシュが完了するたびに、次回自動リフレッシュを無効にする
    const addUserLoadedEvent = auth.events.addUserLoaded(() => {
      console.log("addUserLoaded - stopSilentRenew");
      auth.stopSilentRenew();
    });

    // セットしたリスナーの後片付け
    return () => {
      auth.events.removeUserLoaded(addUserLoadedEvent);
      auth.events.removeAccessTokenExpired(accessTokenExpiredEvent);
    };
  }, []);

}

