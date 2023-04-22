class SessionController < ApplicationController
  # セッション開始時の処理
  # ユーザー名とグループを持つ Hash としてユーザー情報をセッションに格納
  def create
    session[:user] = {
      username: request.env['omniauth.auth']['extra']['raw_info']['cognito:username'],
      groups: request.env['omniauth.auth']['extra']['raw_info']['cognito:groups']
    }
    redirect_to root_url
  end

  # セッション終了時の処理
  # 全セッション情報削除
  def delete
    reset_session
    redirect_to root_url
  end
end