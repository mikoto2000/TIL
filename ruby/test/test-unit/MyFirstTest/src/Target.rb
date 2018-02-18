class AddError < RuntimeError
end

class Target
  def add (x, y)
    begin
      x + y
    rescue NoMethodError
      raise AddError.new('x が nil.')
    rescue TypeError
      raise AddError.new('y が nil.')
    end
  end
end

