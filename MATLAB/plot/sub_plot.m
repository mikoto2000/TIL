% 0 ���� 2pi (radian) �� 100 ���������x�N�g�������
range = linspace(0, 2*pi, 100);

% sin, cos, tan, asin, acos, atab ���쐬
sin_values = sin(range);
cos_values = cos(range);
tan_values = tan(range);
asin_values = asin(range);
acos_values = acos(range);
atan_values = atan(range);

% figure �E�B���h�E�̃^�C�g����ݒ�
figure('Name', 'sin, cos, tan, asin, acos and atan');

% 3 �s 2 ��̃T�u�v���b�g�z��� 1 �Ԗڂ̃T�u�v���b�g�̕`��(sin)
subplot(3,2,1);
plot(range, sin_values);
title('sin');

% 3 �s 2 ��̃T�u�v���b�g�z��� 2 �Ԗڂ̃T�u�v���b�g�̕`��(cos)
subplot(3,2,2);
plot(range, cos_values);
title('cos');

% 3 �s 2 ��̃T�u�v���b�g�z��� 3 �Ԗڂ̃T�u�v���b�g�̕`��(tan)
subplot(3,2,3);
plot(range, tan_values);
title('tan');

% 3 �s 2 ��̃T�u�v���b�g�z��� 4 �Ԗڂ̃T�u�v���b�g�̕`��(asin)
subplot(3,2,4);
plot(range, asin_values);
title('asin');

% 3 �s 2 ��̃T�u�v���b�g�z��� 5 �Ԗڂ̃T�u�v���b�g�̕`��(acos)
subplot(3,2,5);
plot(range, acos_values);
title('acos');

% 3 �s 2 ��̃T�u�v���b�g�z��� 6 �Ԗڂ̃T�u�v���b�g�̕`��(atan)
subplot(3,2,6);
plot(range, atan_values);
title('atan');

