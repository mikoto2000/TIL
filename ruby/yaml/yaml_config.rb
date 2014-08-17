require 'yaml'

class YamlConfig
    @file_path = nil
    @config = nil

    # 設定ファイルを読み込む
    # 設定ファイルが存在しなければ作る
    def initialize(file_path)
        @file_path = File.expand_path file_path
        unless File.exist? @file_path then
            File.open(@file_path, 'w').close()
        end

        begin
            @config = YAML.load_file(@file_path)
        rescue
            @config = {}
        end
    end

    def save()
        file = open(@file_path, 'w')
        YAML.dump(@config, file)
    end

    def get(path)
        path_array = nil
        if path.kind_of? String then
            path_array = path.split '/'
        elsif path.kind_of? Array then
            path_array = path
        else
            raise StandardError
        end

        current_node = @config
        for key in path_array do
            current_node = current_node[key]
        end
        return current_node
    end

    def set(path, value)
        path_array = nil
        if path.kind_of? String then
            path_array = path.split '/'
        elsif path.kind_of? Array then
            path_array = path
        else
            raise StandardError
        end

        current_node = @config
        depth = path_array.size - 1

        i = 0
        while i < depth do
            key = path_array[i]
            unless current_node[key] then
                current_node[key] = {}
            end

            current_node = current_node[key]
            i = i + 1
        end

        current_node[path_array[i]] = value
    end
end

