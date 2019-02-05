% 0 から 2pi (radian) を 100 等分したベクトルを作る
range = linspace(0, 2*pi, 100);

% sin, cos, tan, asin, acos, atab を作成
sin_values = sin(range);
cos_values = cos(range);
tan_values = tan(range);
asin_values = asin(range);
acos_values = acos(range);
atan_values = atan(range);

% figure ウィンドウのタイトルを設定
figure('Name', 'sin, cos, tan, asin, acos and atan');

% 3 行 2 列のサブプロット配列の 1 番目のサブプロットの描画(sin)
subplot(3,2,1);
plot(range, sin_values);
title('sin');

% 3 行 2 列のサブプロット配列の 2 番目のサブプロットの描画(cos)
subplot(3,2,2);
plot(range, cos_values);
title('cos');

% 3 行 2 列のサブプロット配列の 3 番目のサブプロットの描画(tan)
subplot(3,2,3);
plot(range, tan_values);
title('tan');

% 3 行 2 列のサブプロット配列の 4 番目のサブプロットの描画(asin)
subplot(3,2,4);
plot(range, asin_values);
title('asin');

% 3 行 2 列のサブプロット配列の 5 番目のサブプロットの描画(acos)
subplot(3,2,5);
plot(range, acos_values);
title('acos');

% 3 行 2 列のサブプロット配列の 6 番目のサブプロットの描画(atan)
subplot(3,2,6);
plot(range, atan_values);
title('atan');

