class ApplicationController < ActionController::Base
  include Pundit
  def pundit_user
    current_account
  end

  before_action :authenticate_account!
  # Only allow modern browsers supporting webp images, web push, badges, import maps, CSS nesting, and CSS :has.
  allow_browser versions: :modern
end
