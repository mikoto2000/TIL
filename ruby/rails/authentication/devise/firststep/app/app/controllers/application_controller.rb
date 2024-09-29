class ApplicationController < ActionController::Base
  before_action :authenticate_account!
  # Only allow modern browsers supporting webp images, web push, badges, import maps, CSS nesting, and CSS :has.
  allow_browser versions: :modern
end
