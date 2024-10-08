class CreateAdminOnlyResources < ActiveRecord::Migration[7.2]
  def change
    create_table :admin_only_resources do |t|
      t.string :name

      t.timestamps
    end
  end
end
