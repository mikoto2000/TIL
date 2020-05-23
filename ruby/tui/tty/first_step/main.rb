require 'tty-prompt'

LIST_ITEMS = [
  'One',
  'Two',
  'Three',
]

prompt = TTY::Prompt.new

puts '基本的な使い方'
answer = prompt.ask('自由に記入してもらう:', default: '未回答')
choosed_item = prompt.select('選択肢の中から一つ選ぶ:', LIST_ITEMS)
selected_item = prompt.multi_select('選択肢の中から複数選ぶ:', LIST_ITEMS)


puts '== 結果 =='
puts "ask result: #{answer}."
puts "select result: #{choosed_item}."
puts "multi_select result: #{selected_item}."
puts

puts 'グループ化できる'

results = prompt.collect {
  key(:first).ask('自由入力', default: '未回答')
  key(:second).select('選択肢の中から一つ選ぶ:', LIST_ITEMS)
  key(:third).multi_select('選択肢の中から複数選ぶ:', LIST_ITEMS)
}
puts "グループ化した結果: #{results}"
puts

puts 'ラベルと値を別定義'

choices = [
  {name: 'small', value: 1},
  {name: 'medium', value: 2},
  {name: 'large', value: 3}
]
choosed_item = prompt.select('選択肢の中から一つ選ぶ:', choices)
puts "ラベルに対する値: #{choosed_item}."

