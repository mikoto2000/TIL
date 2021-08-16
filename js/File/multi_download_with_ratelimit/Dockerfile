FROM httpd:2.4.48

# Apache で帯域制限をすることで、テキストファイルのダウンロードに時間がかかるように設定
RUN sed -i -e "s/#LoadModule ratelimit_module modules\/mod_ratelimit.so/LoadModule ratelimit_module modules\/mod_ratelimit.so/" /usr/local/apache2/conf/httpd.conf \
    && echo "Include conf/extra/ratelimit.conf" >> /usr/local/apache2/conf/httpd.conf \
    && echo "<Location \"/\">" >> /usr/local/apache2/conf/extra/ratelimit.conf \
    && echo "SetOutputFilter RATE_LIMIT" >> /usr/local/apache2/conf/extra/ratelimit.conf \
    && echo "SetEnv rate-limit 1" >> /usr/local/apache2/conf/extra/ratelimit.conf \
    && echo "</Location>" >> /usr/local/apache2/conf/extra/ratelimit.conf


