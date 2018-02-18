require 'test/unit'

require_relative '../src/Target.rb'

class TestTarget < Test::Unit::TestCase
  def test_add
    target = Target.new
    assert_equal 3, target.add(1, 2)
  end

  def test_add_xがnil
    target = Target.new
    raised_error = assert_raise(AddError) {
      target.add nil, 1
    }

    assert_equal 'x が nil.', raised_error.message
  end

  def test_add_yがnil
    target = Target.new
    raised_error = assert_raise(AddError) {
      target.add 1, nil
    }

    assert_equal 'y が nil.', raised_error.message
  end

end

