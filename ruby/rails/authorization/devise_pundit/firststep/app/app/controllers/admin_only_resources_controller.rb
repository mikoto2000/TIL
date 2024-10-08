class AdminOnlyResourcesController < ApplicationController
  before_action :set_admin_only_resource, only: %i[ show edit update destroy ]

  # GET /admin_only_resources or /admin_only_resources.json
  def index
    @admin_only_resources = AdminOnlyResource.all
  end

  # GET /admin_only_resources/1 or /admin_only_resources/1.json
  def show
  end

  # GET /admin_only_resources/new
  def new
    @admin_only_resource = AdminOnlyResource.new
  end

  # GET /admin_only_resources/1/edit
  def edit
  end

  # POST /admin_only_resources or /admin_only_resources.json
  def create
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
end
