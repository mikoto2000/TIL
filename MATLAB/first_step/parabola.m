% 時間軸作成
times = 0:1:20;

% 初期速度。上向きに 98 m/s
initial_velocity = 98;

% 重力加速度。下向きに 9.8 m/s^2
gravitational_acceleration = -9.8;

% 加速度の変化をプロット
accelerations = zeros(1, size(times, 2)) + gravitational_acceleration;
subplot(4,1,1);
plot(times, accelerations);
title('加速度の変化');


% 累積積分を使って速度の変化を計算してプロット
velocity = cumtrapz(times, accelerations) + initial_velocity;
subplot(4,1,2);
plot(times,velocity);
title('速度の変化');


% 累積積分を使って位置の変化を計算してプロット
position = cumtrapz(times, velocity);
subplot(4,1,3);
plot(times,position);
title('位置の変化(累積積分)');


% 放物線の方程式を使用して放物線をプロット
% y = v0t + 1/2 * g * t^2
% v0: 初速度
% g: 重力加速度
% t: 時間
x = 0:1:20;
y = (initial_velocity * x) + (gravitational_acceleration * x.^2)./2;
subplot(4,1,4);
plot(x,y);
title('位置の変化(放物線の公式)');
