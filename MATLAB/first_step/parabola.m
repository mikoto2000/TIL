% ���Ԏ��쐬
times = 0:1:20;

% �������x�B������� 98 m/s
initial_velocity = 98;

% �d�͉����x�B�������� 9.8 m/s^2
gravitational_acceleration = -9.8;

% �����x�̕ω����v���b�g
accelerations = zeros(1, size(times, 2)) + gravitational_acceleration;
subplot(4,1,1);
plot(times, accelerations);
title('�����x�̕ω�');


% �ݐϐϕ����g���đ��x�̕ω����v�Z���ăv���b�g
velocity = cumtrapz(times, accelerations) + initial_velocity;
subplot(4,1,2);
plot(times,velocity);
title('���x�̕ω�');


% �ݐϐϕ����g���Ĉʒu�̕ω����v�Z���ăv���b�g
position = cumtrapz(times, velocity);
subplot(4,1,3);
plot(times,position);
title('�ʒu�̕ω�(�ݐϐϕ�)');


% �������̕��������g�p���ĕ��������v���b�g
% y = v0t + 1/2 * g * t^2
% v0: �����x
% g: �d�͉����x
% t: ����
x = 0:1:20;
y = (initial_velocity * x) + (gravitational_acceleration * x.^2)./2;
subplot(4,1,4);
plot(x,y);
title('�ʒu�̕ω�(�������̌���)');
