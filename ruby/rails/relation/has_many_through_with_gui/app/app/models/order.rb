class Order < ApplicationRecord
  has_many :order_items
  has_many :items, through: :order_items
  accepts_nested_attributes_for :order_items, allow_destroy: true
end
