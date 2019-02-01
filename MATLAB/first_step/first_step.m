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



