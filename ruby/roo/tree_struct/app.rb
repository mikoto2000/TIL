# encoding: utf-8
require "roo"

class Context
  attr_reader :current_depth

  def initialize
    @name_stack = []
    @current_depth = 0
  end

  def push(name)
    @name_stack.push(name)
    @current_depth += 1
  end

  def pop
    @name_stack.pop
    @current_depth -= 1
  end

  def drop(depth)
    @name_stack = @name_stack.take(depth)
    @current_depth = depth
  end

  def get_path
    path = @name_stack.join("/")
    path = "/" + path if path[0] != "/"
    path
  end

  def to_s
    @name_stack.to_s
  end
end

class TreeParameterParser

  def initialize(
      excel_file_path,
      excel_sheet_name,
      header_row,
      parameter_start_column,
      parameter_end_column,
      parameter_name_column,
      parameter_value_column)

    # ファイルを開く
    excel_file = Roo::Excelx.new(excel_file_path, {:expand_merged_ranges => true})
    # シートを開く
    @target_sheet = excel_file.sheet(excel_sheet_name)

    # パーサー初期値設定
    @parameter_start_column = parameter_start_column
    @parameter_end_column = parameter_end_column
    @parameter_name_column = parameter_name_column
    @parameter_value_column = parameter_value_column

    # 先頭行計算
    @first_row = header_row + 1
    @last_row = @target_sheet.last_row

    # パース位置記録
    @current_row = @first_row

    @context = Context.new
  end

  def next
    if @current_row >= @last_row then
      return nil
    end

    # 行データ取得
    row_elements = (@parameter_start_column..@parameter_end_column).map { |index|
      @target_sheet.cell(@current_row, index)
    }

    # 最初の要素を取得
    depth = row_elements.find_index { |e| not e.nil? }
    unless depth.nil? then
      if depth < @context.current_depth then
        @context.drop(depth)
      end
      @context.push(row_elements[depth])
    else
      @context.pop if @prev_type == 'parameter'
      @context.push(@target_sheet.cell(@current_row, @parameter_name_column))
    end

    if depth.nil? then
      # パラメーター
      return_value = { :config_name => @context.get_path, :type => 'parameter', :value => @target_sheet.cell(@current_row, @parameter_value_column) }
    else
      # コンテナ
      name = @target_sheet.cell(@current_row, @parameter_value_column) || @context.get_path.split("/").last
      return_value = { :config_name => @context.get_path, :type => 'container', :name => name }
    end

    @prev_type = return_value[:type]
    @current_row += 1

    return_value
  end
end

if $0 == __FILE__

  # TODO: コンフィグ化
  HEADER_ROWS = 5
  PARAMETER_START_COLUMN = 2 # 'B'
  PARAMETER_END_COLUMN = 8 # 'D'
  PARAMETER_COLUMN = 'I'
  VALUE_COLUMN = 'J'

  # ファイルを開く
  parser = TreeParameterParser.new(
    'parameter_list.xlsx',
    'タスク設定',
    HEADER_ROWS,
    PARAMETER_START_COLUMN,
    PARAMETER_END_COLUMN,
    PARAMETER_COLUMN,
    VALUE_COLUMN
  )

  while param = parser.next do
    puts param.to_s
  end
end

