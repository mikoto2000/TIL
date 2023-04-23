class SessionController < ApplicationController
  # セッション開始時の処理
  # ユーザー名とグループを持つ Hash としてユーザー情報をセッションに格納
  def create
    store_user
    redirect_back
  end

  # セッション終了時の処理
  # 全セッション情報削除
  def delete
    redirect_back
    reset_session
  end
end