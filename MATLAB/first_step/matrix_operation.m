% �s��̉����Z
% See: https://oguemon.com/study/linear-algebra/matrix-op/#toc0

matrix_1 = [1 2; 3 4];
matrix_2 = [5 6; 7 8];

added_matrix = matrix_1 + matrix_2; % [6 8; 10 12]
subed_matrix = matrix_2 - matrix_1; % [4 4; 4 4]


% �s��̃X�J���[�{
% See: https://oguemon.com/study/linear-algebra/matrix-op/#toc1
double_matrix = matrix_1 * 2; % [2 4; 6 8]
half_matrix = matrix_1 / 2; % [0.5 1; 1.5 2]


% �s�x�N�g���Ɨ�x�N�g���̓���
% See: https://atarimae.biz/archives/23930#i
row_vector = [1 2 3 4];
col_vector = [5; 6; 7; 8];

% 1*5 + 2*6 + 3*7 + 4*8 = 70
inner_product = row_vector * col_vector;


% �s�񓯎m�̊|���Z
% See: https://mathwords.net/gyouretsuseki

% [matrix_1(1,:)*matrix_2(:,1) matrix_1(1,:)*matrix_2(:,2); ...
%  matrix_1(2,:)*matrix_2(:,1) matrix_1(2,:)*matrix_2(:,2)]
multied_matrix_1 = matrix_1 * matrix_2;

% [matrix_3(1,:)*matrix_4(:,1); ...
%  matrix_3(2,:)*matrix_4(:,1); ...
%  matrix_3(3,:)*matrix_4(:,1)]
matrix_3 = [1 2; 3 4; 5 6];
matrix_4 = [7; 8];
multied_matrix_2 = matrix_3 * matrix_4;

% [matrix_3(1,:)*matrix_5(:,1) matrix_3(1,:)*matrix_5(:,2); ...
%  matrix_3(2,:)*matrix_5(:,1) matrix_3(2,:)*matrix_5(:,2); ...
%  matrix_3(3,:)*matrix_5(:,1) matrix_3(3,:)*matrix_5(:,2)]
matrix_5 = [7 8; 9 10];
multied_matrix_3 = matrix_3 * matrix_5;


% �s��̓����ʒu�̗v�f���ƂɌv�Z����
% �����Z�����Z�͂��Ƃ��Ƃ������������B
% �|���Z�ł����������Ƃ��������ꍇ�� `.*` ���Z�q���g���B
matrix_6 = [1 2; 3 4];
matrix_7 = [5 6; 7 8];
multied_matrix_4 = matrix_6 .* matrix_7; % [5 12; 21 32]


