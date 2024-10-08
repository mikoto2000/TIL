class AllWelcomeResourcesController < ApplicationController
  before_action :set_all_welcome_resource, only: %i[ show edit update destroy ]

  # GET /all_welcome_resources or /all_welcome_resources.json
  def index
    @all_welcome_resources = AllWelcomeResource.all
  end

  # GET /all_welcome_resources/1 or /all_welcome_resources/1.json
  def show
  end

  # GET /all_welcome_resources/new
  def new
    @all_welcome_resource = AllWelcomeResource.new
  end

  # GET /all_welcome_resources/1/edit
  def edit
  end

  # POST /all_welcome_resources or /all_welcome_resources.json
  def create
    @all_welcome_resource = AllWelcomeResource.new(all_welcome_resource_params)

    respond_to do |format|
      if @all_welcome_resource.save
        format.html { redirect_to @all_welcome_resource, notice: "All welcome resource was successfully created." }
        format.json { render :show, status: :created, location: @all_welcome_resource }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @all_welcome_resource.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /all_welcome_resources/1 or /all_welcome_resources/1.json
  def update
    respond_to do |format|
      if @all_welcome_resource.update(all_welcome_resource_params)
        format.html { redirect_to @all_welcome_resource, notice: "All welcome resource was successfully updated." }
        format.json { render :show, status: :ok, location: @all_welcome_resource }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @all_welcome_resource.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /all_welcome_resources/1 or /all_welcome_resources/1.json
  def destroy
    @all_welcome_resource.destroy!

    respond_to do |format|
      format.html { redirect_to all_welcome_resources_path, status: :see_other, notice: "All welcome resource was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_all_welcome_resource
      @all_welcome_resource = AllWelcomeResource.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def all_welcome_resource_params
      params.require(:all_welcome_resource).permit(:name)
    end
end
