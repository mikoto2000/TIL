require "application_system_test_case"

class AdminOnlyResourcesTest < ApplicationSystemTestCase
  setup do
    @admin_only_resource = admin_only_resources(:one)
  end

  test "visiting the index" do
    visit admin_only_resources_url
    assert_selector "h1", text: "Admin only resources"
  end

  test "should create admin only resource" do
    visit admin_only_resources_url
    click_on "New admin only resource"

    fill_in "Name", with: @admin_only_resource.name
    click_on "Create Admin only resource"

    assert_text "Admin only resource was successfully created"
    click_on "Back"
  end

  test "should update Admin only resource" do
    visit admin_only_resource_url(@admin_only_resource)
    click_on "Edit this admin only resource", match: :first

    fill_in "Name", with: @admin_only_resource.name
    click_on "Update Admin only resource"

    assert_text "Admin only resource was successfully updated"
    click_on "Back"
  end

  test "should destroy Admin only resource" do
    visit admin_only_resource_url(@admin_only_resource)
    click_on "Destroy this admin only resource", match: :first

    assert_text "Admin only resource was successfully destroyed"
  end
end
