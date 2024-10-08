require "test_helper"

class AdminOnlyResourcesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin_only_resource = admin_only_resources(:one)
  end

  test "should get index" do
    get admin_only_resources_url
    assert_response :success
  end

  test "should get new" do
    get new_admin_only_resource_url
    assert_response :success
  end

  test "should create admin_only_resource" do
    assert_difference("AdminOnlyResource.count") do
      post admin_only_resources_url, params: { admin_only_resource: { name: @admin_only_resource.name } }
    end

    assert_redirected_to admin_only_resource_url(AdminOnlyResource.last)
  end

  test "should show admin_only_resource" do
    get admin_only_resource_url(@admin_only_resource)
    assert_response :success
  end

  test "should get edit" do
    get edit_admin_only_resource_url(@admin_only_resource)
    assert_response :success
  end

  test "should update admin_only_resource" do
    patch admin_only_resource_url(@admin_only_resource), params: { admin_only_resource: { name: @admin_only_resource.name } }
    assert_redirected_to admin_only_resource_url(@admin_only_resource)
  end

  test "should destroy admin_only_resource" do
    assert_difference("AdminOnlyResource.count", -1) do
      delete admin_only_resource_url(@admin_only_resource)
    end

    assert_redirected_to admin_only_resources_url
  end
end
