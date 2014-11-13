#!/usr/bin/ruby
# -*- encoding: utf-8 -*-

require 'savon'

SERVICE_URL = 'http://voice:8080/Yakari/YukariService?wsdl'
TEST_MESSAGE = 'TESTING.'
TEST_FILE = 'test.mp3'

client = Savon.client(wsdl: SERVICE_URL)

response = client.call(:echo, message: {message: TEST_MESSAGE})

File.open(TEST_FILE,'wb') do |f|
    f.write(response.body[:echo_response][:return][:voice].unpack('m')[0])
end
