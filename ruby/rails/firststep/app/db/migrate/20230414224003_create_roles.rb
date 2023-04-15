class CreateRoles < ActiveRecord::Migration[7.0]
  def change
    create_table :roles do |t|
      # name に NOT NULL 制約を設定
      t.string :name, null: false

      t.timestamps
    end

    # name にユニーク制約を設定
    add_index :roles, [:name], unique: true
  end
end
