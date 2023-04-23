module SessionHelper

  def store_user
    session[:user] = {
      username: request.env['omniauth.auth']['extra']['raw_info']['cognito:username'],
      groups: request.env['omniauth.auth']['extra']['raw_info']['cognito:groups']
    }
  end

  def store_url
    session[:original_url] = request.original_url if request.get?
  end

  def redirect_back
    redirect_to(session[:original_url] || root_url)
    session.delete(:original_url)
  end

end
