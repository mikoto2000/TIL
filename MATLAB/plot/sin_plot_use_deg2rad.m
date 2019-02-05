% 1 から 360 (degree) を 1 度ずつインクリメントしたベクトルを作る
range = [1:1:360];

% degree を radian に変換したものをプロット
% x 軸がベクトルのインデックスと等しいため、値のベクトルだけ渡している
% https://ch.mathworks.com/help/matlab/ref/deg2rad.html
plot(sin(deg2rad(range)));

