class ApplicationController < ActionController::Base
  include SessionHelper

  before_action :store_url, only: [:index, :new, :edit, :show]

  def index
  end

end
