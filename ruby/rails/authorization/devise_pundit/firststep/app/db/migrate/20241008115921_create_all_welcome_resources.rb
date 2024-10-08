class CreateAllWelcomeResources < ActiveRecord::Migration[7.2]
  def change
    create_table :all_welcome_resources do |t|
      t.string :name

      t.timestamps
    end
  end
end
