# encoding: UTF-8

# yield がある = ユーザーがブロックを渡すことで、
# 自分好みの処理を差し込める。
def function_with_block(arg)
  yield arg
end

# 引数を 2 倍にする
puts function_with_block(1) { |e|
  e * 2
}

# 引数を 4 倍にする
puts function_with_block(1) { |e|
  e * 4
}

