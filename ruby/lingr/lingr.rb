#!/usr/bin/env ruby
# -*- coding: utf-8 -*-
=begin rdoc
Lingr から room のメッセージを取得するサンプル。
=end

require 'net/http'
require 'json'

# Lingr クラス
class Lingr
    ENDPOINT_URL = 'http://lingr.com/api/'

    # コンストラクタ
    # ユーザー名、パスワードからセッションを作成する
    def initialize(username, password)
        @username = username
        @password = password
        @session = create_session(username, password)
        puts "create session: #{@session}"
    end

    # セッションを破棄する
    def close
        destroy_session
        puts "destroy session: #{@session}"
    end

    # メッセージを取得する
    # TODO: 文字列じゃなくて日付型もらいたいよね
    def get_messages(rooms, start_time = '0000-00-00T00:00:00Z')
        verify_session

        puts "get message after #{start_time}. session: #{@session}, room: #{rooms} "

        response = Net::HTTP.post_form(
            URI.parse(ENDPOINT_URL + 'room/show'), {'session'=>@session, 'rooms'=>rooms})
        json = JSON.parse(response.body)
        return json['rooms'][0]['messages'].select{ |item|
            item['timestamp'] > start_time
        }.sort_by{ |message|
            message['timestamp']
        }
    end

    private

    # セッションを作成する
    def create_session(username, password)
        response = Net::HTTP.post_form(
            URI.parse(ENDPOINT_URL + 'session/create'), {'user'=>username, 'password'=>password})

        json = JSON.parse(response.body)

        return json['session']
    end

    # セッションを破棄する
    def destroy_session
        Net::HTTP.post_form(
            URI.parse(ENDPOINT_URL + 'session/destroy'), {'session'=>@session})
    end

    # セッションの有効性を確認し、必要であればアップデートする
    def verify_session
        response = Net::HTTP.post_form(
            URI.parse(ENDPOINT_URL + 'session/verify'), {'session'=>@session})

        json = JSON.parse(response.body)

        status = json['status']

        @session = create_session(@username, @password) unless status == "ok"

    end
end

STDOUT.sync = true

lingr = Lingr.new('USERNAME', 'PASSWORD')

begin
    last_time = '0000-00-00T00:00:00Z'
    100.times {
        messages = lingr.get_messages('vim', last_time)
        messages.each { |message|
            puts "[#{message['timestamp']}] #{message['nickname']} : #{message['text']}"
        }
        last_time = messages.last['timestamp'] if messages.last
        sleep(60)
    }
ensure
    lingr.close
end
