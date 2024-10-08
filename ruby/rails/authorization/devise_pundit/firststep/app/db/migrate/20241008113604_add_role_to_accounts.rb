class AddRoleToAccounts < ActiveRecord::Migration[7.2]
  def change
    add_column :accounts, :role, :string, default: "user"
  end
end
