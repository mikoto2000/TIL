module XmlElement
  def to_xml
    "<#{@name}>#{@value}</#{@name}>"
  end
end

class MyXmlElement
  include XmlElement

  def initialize(name, value)
    @name = name
    @value = value
  end
end

my_xml_element = MyXmlElement.new("user", "mikoto2000")
puts my_xml_element.to_xml

