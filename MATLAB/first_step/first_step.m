% スカラ
s = 1;

% 行ベクトル
row_vector = [ 1 2 3 4 5 ];

% 列ベクトル
col_vector = [ 1; 2; 3; 4; 5 ];

% 数値行ベクトルをコロン演算子で手軽に作る
% `初期値:刻み値:最終値` という形式で記述
% `for (int i = 初期値; i = i + 刻み値; i <= 最終値) {}` とした場合の i の推移と同じ。
colon_generated_row_vec_1 = 1:1:10;
colon_generated_row_vec_2 = 0:0.1:1;
colon_generated_row_vec_3 = 0:0.3:1;

% 列ベクトルにしたい場合は `'` で転置する
colon_generated_col_vec_1 = colon_generated_row_vec_1';
colon_generated_col_vec_2 = colon_generated_row_vec_2';
colon_generated_col_vec_3 = colon_generated_row_vec_3';

% 行列
matrix = [ 1 2 3; 4 5 6; 7 8 9];

% 行列の各行をコロン演算子で作れる
colon_generated_matrix = [ 1:3; 0.3:0.3:1; 0:5:10 ];

% 行列へのアクセスは `(row_index, col_index)` で行う
matrix_1_1 = matrix(1,1); % 1
matrix_2_2 = matrix(2,2); % 5
matrix_3_3 = matrix(3,3); % 9

% 一応インデックスひとつでもアクセスできる
matrix_1 = matrix(1); % 1 = matrix(1,1)
matrix_2 = matrix(2); % 4 = matrix(2,1)
matrix_3 = matrix(3); % 7 = matrix(3,1)
matrix_4 = matrix(4); % 2 = matrix(1,2)
matrix_5 = matrix(5); % 5 = matrix(2,2)
matrix_6 = matrix(6); % 8 = matrix(3,2)
matrix_7 = matrix(7); % 3 = matrix(1,3)
matrix_8 = matrix(8); % 6 = matrix(2,3)
matrix_9 = matrix(9); % 9 = matrix(3,3)

% 行列に、「インデックスのベクトル」を与えることで、
% 指定したインデックスをまとめて取得できる。
matrix_2_1_and_2_3 = matrix(2, [1 3]); % [4 6]
matrix_2_1_and_2_3_and_3_1_and_3_3 = matrix([2 3], [1 3]); % [4 6;7 9]

% コロン演算子でインデックスしてもできる
matrix_1_1_and_1_3 = matrix(1, 1:2:3); % [1 3]

% 全要素を指定したい場合は `:` を指定でいける
matrix_2_row_all = matrix(2, :); % [4 5 6]

% 多次元配列

% 全要素が 1 の、3x3x3 の多次元配列
% Step1: 2 次元配列を作る
page_1 = [1 1 1; 1 1 1; 1 1 1];
page_2 = [1 1 1; 1 1 1; 1 1 1];
page_3 = [1 1 1; 1 1 1; 1 1 1];

% Step2-1: MATLAB が、配列サイズを自動拡張してくれるのを利用して、
%          3 次元目のインデックスを指定しつつ「Step1」で作った 2 次元配列を代入する。
three_dimensional_matrix_1(:,:,1) = page_1;
three_dimensional_matrix_1(:,:,2) = page_2;
three_dimensional_matrix_1(:,:,3) = page_3;

% Step2-2: cat 関数を使って作ることもできる
% ※ 第一引数に渡している数字は「次元数」。
%    (今回は 3 次元方向に結合するので 3 を指定)
% See: https://jp.mathworks.com/help/matlab/ref/cat.html
three_dimensional_matrix_2 = cat(3, page_1, page_2, page_3);

% 構造体
% キーが String の連想配列的な感じ。
% 「型を定義して、その通りのプロパティを持つことを強制する」みたいな仕組みはなさそう。
struct_1 = struct('key_1', 'value_1', 'key_2', 3);
struct_2.key_1 = 'value_3';
struct_2.key_2 = 'value_4';

% セル配列
cell_1 = {1 'name_1' struct('value1', 1, 'value2', 2, 'value3', 3)};
cell_2 = {2 'name_2' struct('value1', 1, 'value2', 2, 'value3', 3)};
cell_3 = {3 'name_3' struct('value1', 1, 'value2', 2, 'value3', 3)};
cell_array = {cell_1 cell_2 cell_3};

% セル配列へのアクセスは、`()` が `{}` になっただけで配列と同じ。
cell_1_number = cell_array{1}{1}; % 1
cell_2_number = cell_array{2}{1}; % 2
cell_3_number = cell_array{3}{1}; % 3
