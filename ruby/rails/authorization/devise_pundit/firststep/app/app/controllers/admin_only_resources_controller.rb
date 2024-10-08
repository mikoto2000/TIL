class AdminOnlyResourcesController < ApplicationController
  include Pundit                                                      # この 2 行を追加
  rescue_from Pundit::NotAuthorizedError, with: :user_not_authorized  # この 2 行を追加

  before_action :set_admin_only_resource, only: %i[ show edit update destroy ]

  # GET /admin_only_resources or /admin_only_resources.json
  def index
    authorize AdminOnlyResource # この行を追加
    @admin_only_resources = AdminOnlyResource.all
  end

  # GET /admin_only_resources/1 or /admin_only_resources/1.json
  def show
    authorize AdminOnlyResource # この行を追加
  end

  # GET /admin_only_resources/new
  def new
    authorize AdminOnlyResource # この行を追加
    @admin_only_resource = AdminOnlyResource.new
  end

  # GET /admin_only_resources/1/edit
  def edit
    authorize AdminOnlyResource # この行を追加
  end

  # POST /admin_only_resources or /admin_only_resources.json
  def create
    authorize AdminOnlyResource # この行を追加
    @admin_only_resource = AdminOnlyResource.new(admin_only_resource_params)

    respond_to do |format|
      if @admin_only_resource.save
        format.html { redirect_to @admin_only_resource, notice: "Admin only resource was successfully created." }
        format.json { render :show, status: :created, location: @admin_only_resource }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @admin_only_resource.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /admin_only_resources/1 or /admin_only_resources/1.json
  def update
    authorize AdminOnlyResource # この行を追加
    respond_to do |format|
      if @admin_only_resource.update(admin_only_resource_params)
        format.html { redirect_to @admin_only_resource, notice: "Admin only resource was successfully updated." }
        format.json { render :show, status: :ok, location: @admin_only_resource }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @admin_only_resource.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /admin_only_resources/1 or /admin_only_resources/1.json
  def destroy
    authorize AdminOnlyResource # この行を追加
    @admin_only_resource.destroy!

    respond_to do |format|
      format.html { redirect_to admin_only_resources_path, status: :see_other, notice: "Admin only resource was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_admin_only_resource
      @admin_only_resource = AdminOnlyResource.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def admin_only_resource_params
      params.require(:admin_only_resource).permit(:name)
    end

    def user_not_authorized
      flash[:alert] = "You are not authorized to perform this action."
      redirect_to(request.referer || root_path)
    end
end
