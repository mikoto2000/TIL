FROM ubuntu:latest
LABEL maintainer "mikoto2000 <mikoto2000@gmail.com>"

RUN apt update \
    && apt install -y --no-install-recommends openssh-server

# 送る企業ユーザーの追加
RUN useradd -m sendcompany
COPY ./sendcompany_id_rsa.pub /home/sendcompany/.ssh/authorized_keys
RUN chown -R sendcompany:sendcompany /home/sendcompany/.ssh
RUN chmod 700 /home/sendcompany/.ssh

# 受け取る企業ユーザーの追加
RUN useradd -m receivecompany
COPY ./receivecompany_id_rsa.pub /home/receivecompany/.ssh/authorized_keys
RUN chown -R receivecompany:receivecompany /home/receivecompany/.ssh
RUN chmod 700 /home/receivecompany/.ssh

# sshd の実行ディレクトリ作成
RUN mkdir /run/sshd


CMD ["sshd", "-D"]

