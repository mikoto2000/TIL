require "test_helper"

class AllWelcomeResourcesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @all_welcome_resource = all_welcome_resources(:one)
  end

  test "should get index" do
    get all_welcome_resources_url
    assert_response :success
  end

  test "should get new" do
    get new_all_welcome_resource_url
    assert_response :success
  end

  test "should create all_welcome_resource" do
    assert_difference("AllWelcomeResource.count") do
      post all_welcome_resources_url, params: { all_welcome_resource: { name: @all_welcome_resource.name } }
    end

    assert_redirected_to all_welcome_resource_url(AllWelcomeResource.last)
  end

  test "should show all_welcome_resource" do
    get all_welcome_resource_url(@all_welcome_resource)
    assert_response :success
  end

  test "should get edit" do
    get edit_all_welcome_resource_url(@all_welcome_resource)
    assert_response :success
  end

  test "should update all_welcome_resource" do
    patch all_welcome_resource_url(@all_welcome_resource), params: { all_welcome_resource: { name: @all_welcome_resource.name } }
    assert_redirected_to all_welcome_resource_url(@all_welcome_resource)
  end

  test "should destroy all_welcome_resource" do
    assert_difference("AllWelcomeResource.count", -1) do
      delete all_welcome_resource_url(@all_welcome_resource)
    end

    assert_redirected_to all_welcome_resources_url
  end
end
