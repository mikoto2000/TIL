#!/usr/bin/env python
# -*- coding: utf-8 -*-

import imaplib
import getpass
import sys
import base64

import email
from email.header import decode_header

host = 'imap.gmail.com'

def getUnseenMessages(host, mail_address, password):
    mail = imaplib.IMAP4_SSL(host)

    mail.login(mail_address, password)

    result, data = mail.select("inbox")

    data_array = []

    for num in data[0].split():
        _, sub = mail.fetch(num, '(RFC822)')
        # 現在テスト中のため未読に戻す
        mail.store(num, '-FLAGS', '\\SEEN')
        s = sub[0][1].strip()

        message = email.message_from_string(s)

        data_array.append(message)

    mail.close()
    mail.logout()

    return data_array

if __name__ == '__main__':
    if len(sys.argv) < 2 :
        print("Useage: {0} MAIL_ADDRESS".format(sys.argv[0]))
        sys.exit()

    mail_address = sys.argv[1]
    password = getpass.getpass()

    messages = getUnseenMessages(host, mail_address, password)

    for d in messages:
        subject = decode_header(d.get('Subject'))
        print subject[0][0]
        for item in d.walk():
            if item.get_content_type() == 'text/plain':
                body = item.get_payload(decode=True)
                print body

