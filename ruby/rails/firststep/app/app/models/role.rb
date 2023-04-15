class Role < ApplicationRecord
  # name に一意制約とブランク禁止のバリデーションを追加
  validates :name, uniqueness: true, length: { minimum: 1 }, allow_blank: false
end
