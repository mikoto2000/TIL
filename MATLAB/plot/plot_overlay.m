% 0 ���� 2pi (radian) �� 100 ���������x�N�g�������
range = linspace(0, 2*pi, 100);

% sin, cos, tan, asin, acos, atab ���쐬
sin_values = sin(range);
cos_values = cos(range);
tan_values = tan(range)./30; % ���ꂾ���l���傫���̂ŁA 30 �Ŋ����Ă���
asin_values = asin(range);
acos_values = acos(range);
atan_values = atan(range);

% figure �E�B���h�E�̃^�C�g����ݒ�
figure('Name', 'combined sin, cos, tan, asin, acos and atan');

hold on;
plot(range, sin_values);
plot(range, cos_values);
plot(range, tan_values);
plot(range, asin_values);
plot(range, acos_values);
plot(range, atan_values);

legend('sin', 'cos', 'tan/30', 'asin', 'acos', 'atan', 'Location', 'southeast');

