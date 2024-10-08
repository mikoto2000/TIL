require "application_system_test_case"

class AllWelcomeResourcesTest < ApplicationSystemTestCase
  setup do
    @all_welcome_resource = all_welcome_resources(:one)
  end

  test "visiting the index" do
    visit all_welcome_resources_url
    assert_selector "h1", text: "All welcome resources"
  end

  test "should create all welcome resource" do
    visit all_welcome_resources_url
    click_on "New all welcome resource"

    fill_in "Name", with: @all_welcome_resource.name
    click_on "Create All welcome resource"

    assert_text "All welcome resource was successfully created"
    click_on "Back"
  end

  test "should update All welcome resource" do
    visit all_welcome_resource_url(@all_welcome_resource)
    click_on "Edit this all welcome resource", match: :first

    fill_in "Name", with: @all_welcome_resource.name
    click_on "Update All welcome resource"

    assert_text "All welcome resource was successfully updated"
    click_on "Back"
  end

  test "should destroy All welcome resource" do
    visit all_welcome_resource_url(@all_welcome_resource)
    click_on "Destroy this all welcome resource", match: :first

    assert_text "All welcome resource was successfully destroyed"
  end
end
