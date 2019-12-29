# encoding: utf-8
require "roo"

# シート内の全データを出力
def put_all sheet
  first_row = sheet.first_row
  last_row = sheet.last_row
  first_column = sheet.first_column
  last_column = sheet.last_column
  for row in first_row .. last_row
    puts (first_column .. last_column).map { |e|
      sheet.cell(row, e).to_s
    }.join(', ')
  end
end

# ファイルを開く
パラメーター一覧表 = Roo::Excelx.new('パラメーター一覧表.xlsx', {:expand_merged_ranges => true})

# シートを開く
puts "====== データ型一覧"
データ型一覧 = パラメーター一覧表.sheet("データ型一覧")

# データが格納されている範囲を取得

# 全データ出力
puts "データ:"
put_all データ型一覧

# シートを開く
ユーザー一覧 = パラメーター一覧表.sheet("ユーザー一覧")

# データが格納されている範囲を取得
puts "\n====== ユーザー一覧"

# 全データ出力
puts "データ:"
put_all ユーザー一覧

