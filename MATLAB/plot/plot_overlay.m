% 0 から 2pi (radian) を 100 等分したベクトルを作る
range = linspace(0, 2*pi, 100);

% sin, cos, tan, asin, acos, atab を作成
sin_values = sin(range);
cos_values = cos(range);
tan_values = tan(range)./30; % これだけ値が大きいので、 30 で割っておく
asin_values = asin(range);
acos_values = acos(range);
atan_values = atan(range);

% figure ウィンドウのタイトルを設定
figure('Name', 'combined sin, cos, tan, asin, acos and atan');

hold on;
plot(range, sin_values);
plot(range, cos_values);
plot(range, tan_values);
plot(range, asin_values);
plot(range, acos_values);
plot(range, atan_values);

legend('sin', 'cos', 'tan/30', 'asin', 'acos', 'atan', 'Location', 'southeast');

