class CreateAccounts < ActiveRecord::Migration[7.0]
  def change
    create_table :accounts do |t|
      # name に NOT NULL 制約を設定
      t.string :name, :null => false

      # role に NOT NULL 制約と外部キーを設定
      t.belongs_to :role, null: false, foreign_key: true

      t.date :expiration_date

      t.timestamps
    end
  end
end
