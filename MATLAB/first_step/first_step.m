% �X�J��
s = 1;

% �s�x�N�g��
row_vector = [ 1 2 3 4 5 ];

% ��x�N�g��
col_vector = [ 1; 2; 3; 4; 5 ];

% ���l�s�x�N�g�����R�������Z�q�Ŏ�y�ɍ��
% `�����l:���ݒl:�ŏI�l` �Ƃ����`���ŋL�q
% `for (int i = �����l; i = i + ���ݒl; i <= �ŏI�l) {}` �Ƃ����ꍇ�� i �̐��ڂƓ����B
colon_generated_row_vec_1 = 1:1:10;
colon_generated_row_vec_2 = 0:0.1:1;
colon_generated_row_vec_3 = 0:0.3:1;

% ��x�N�g���ɂ������ꍇ�� `'` �œ]�u����
colon_generated_col_vec_1 = colon_generated_row_vec_1';
colon_generated_col_vec_2 = colon_generated_row_vec_2';
colon_generated_col_vec_3 = colon_generated_row_vec_3';

% �s��
matrix = [ 1 2 3; 4 5 6; 7 8 9];

% �s��̊e�s���R�������Z�q�ō���
colon_generated_matrix = [ 1:3; 0.3:0.3:1; 0:5:10 ];

% �s��ւ̃A�N�Z�X�� `(row_index, col_index)` �ōs��
matrix_1_1 = matrix(1,1); % 1
matrix_2_2 = matrix(2,2); % 5
matrix_3_3 = matrix(3,3); % 9

% �ꉞ�C���f�b�N�X�ЂƂł��A�N�Z�X�ł���
matrix_1 = matrix(1); % 1 = matrix(1,1)
matrix_2 = matrix(2); % 4 = matrix(2,1)
matrix_3 = matrix(3); % 7 = matrix(3,1)
matrix_4 = matrix(4); % 2 = matrix(1,2)
matrix_5 = matrix(5); % 5 = matrix(2,2)
matrix_6 = matrix(6); % 8 = matrix(3,2)
matrix_7 = matrix(7); % 3 = matrix(1,3)
matrix_8 = matrix(8); % 6 = matrix(2,3)
matrix_9 = matrix(9); % 9 = matrix(3,3)

% �s��ɁA�u�C���f�b�N�X�̃x�N�g���v��^���邱�ƂŁA
% �w�肵���C���f�b�N�X���܂Ƃ߂Ď擾�ł���B
matrix_2_1_and_2_3 = matrix(2, [1 3]); % [4 6]
matrix_2_1_and_2_3_and_3_1_and_3_3 = matrix([2 3], [1 3]); % [4 6;7 9]

% �R�������Z�q�ŃC���f�b�N�X���Ă��ł���
matrix_1_1_and_1_3 = matrix(1, 1:2:3); % [1 3]

% �S�v�f���w�肵�����ꍇ�� `:` ���w��ł�����
matrix_2_row_all = matrix(2, :); % [4 5 6]

% �������z��

% �S�v�f�� 1 �́A3x3x3 �̑������z��
% Step1: 2 �����z������
page_1 = [1 1 1; 1 1 1; 1 1 1];
page_2 = [1 1 1; 1 1 1; 1 1 1];
page_3 = [1 1 1; 1 1 1; 1 1 1];

% Step2-1: MATLAB ���A�z��T�C�Y�������g�����Ă����̂𗘗p���āA
%          3 �����ڂ̃C���f�b�N�X���w�肵�uStep1�v�ō���� 2 �����z���������B
three_dimensional_matrix_1(:,:,1) = page_1;
three_dimensional_matrix_1(:,:,2) = page_2;
three_dimensional_matrix_1(:,:,3) = page_3;

% Step2-2: cat �֐����g���č�邱�Ƃ��ł���
% �� �������ɓn���Ă��鐔���́u�������v�B
%    (����� 3 ���������Ɍ�������̂� 3 ���w��)
% See: https://jp.mathworks.com/help/matlab/ref/cat.html
three_dimensional_matrix_2 = cat(3, page_1, page_2, page_3);

% �\����
% �L�[�� String �̘A�z�z��I�Ȋ����B
% �u�^���`���āA���̒ʂ�̃v���p�e�B�������Ƃ���������v�݂����Ȏd�g�݂͂Ȃ������B
struct_1 = struct('key_1', 'value_1', 'key_2', 3);
struct_2.key_1 = 'value_3';
struct_2.key_2 = 'value_4';

% �Z���z��
cell_1 = {1 'name_1' struct('value1', 1, 'value2', 2, 'value3', 3)};
cell_2 = {2 'name_2' struct('value1', 1, 'value2', 2, 'value3', 3)};
cell_3 = {3 'name_3' struct('value1', 1, 'value2', 2, 'value3', 3)};
cell_array = {cell_1 cell_2 cell_3};

% �Z���z��ւ̃A�N�Z�X�́A`()` �� `{}` �ɂȂ��������Ŕz��Ɠ����B
cell_1_number = cell_array{1}{1}; % 1
cell_2_number = cell_array{2}{1}; % 2
cell_3_number = cell_array{3}{1}; % 3
