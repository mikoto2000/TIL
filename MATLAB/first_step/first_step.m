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



