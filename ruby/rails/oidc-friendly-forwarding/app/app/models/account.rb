class Account < ApplicationRecord
  belongs_to :role
  # インスタンス初期化後に呼び出すメソッドを指定し、
  # その中でデフォルト値を設定する
  after_initialize :set_default_values

  # name に一意制約とブランク禁止のバリデーションを追加
  validates :name, uniqueness: true, length: { minimum: 1 }, allow_blank: false

  private
  def set_default_values
    # 有効期限を 1 ヶ月に設定
    self.expiration_date ||= Time.current.since(1.month)
  end
end
