# encoding: UTF-8
module MyModule
  def self.class_hello
    puts "MyModule class method hello!"
  end

  def instance_hello
    puts "MyModule instance method hello!"
  end
end

class MyClass
  include MyModule
end

# Enumerable をインクルードして each を実装すると、 map とかが使用可能になるらしい
# See: https://qiita.com/00inoue/items/4d3b392bcdde4d4498b6
# See: https://qiita.com/QUANON/items/d84fcf417c285721837d
class MyEnum
  include Enumerable

  def initialize(list)
    @list = list
  end

  def each
    for v in @list
      yield v
    end
  end
end

def __main__(argv)
  if argv[1] == "version"
    puts "v#{ModuleTest::VERSION}"
  else
    # モジュールのクラスメソッド呼び出し
    # Module のも「クラスメソッド」と呼んでいいのか？
    MyModule.class_hello

    # モジュールをインクルードしたので、モジュールで定義したインスタンスメソッドを呼び出せる
    MyClass.new.instance_hello

    # MyClass 的にはクラスメソッドを持っていないことになっている
    begin
      MyClass.class_hello
    rescue(NoMethodError) => e
      puts "`MyClass.class_hello` raise NoMethodError."
    end

    # Enumerable をインクルードして each を実装したので、 map が使える
    # map した結果の配列を puts
    puts MyEnum.new([1,2,3]).map { |e|
      e * 2
    }.to_s
  end
end

