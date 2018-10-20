package jp.dip.oyasirazu.study.gmail;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * GetMail
 */
public class GetMail {
    public static void main(String[] args) throws IOException, NoSuchProviderException, MessagingException {

        Properties properties = new Properties();
        properties.load(new InputStreamReader(
                    ClassLoader.class.getResourceAsStream("/account.properties"), "UTF-8"));

        final String HOST = properties.getProperty("HOST");
        final String PORT = properties.getProperty("PORT");
        final String USER = properties.getProperty("USER");
        final String PASSWORD = properties.getProperty("PASSWORD");

        System.out.println(properties);

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        Properties gmailProperties = new Properties();
        gmailProperties.put("mail.imap.starttls.enable","true");
        gmailProperties.put("mail.imap.auth", "true");
        gmailProperties.put("mail.imap.socketFactory.port", PORT);
        gmailProperties.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        gmailProperties.put("mail.imap.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(gmailProperties, null);
        Store store = session.getStore("imap");
        store.connect(HOST, USER, PASSWORD);

        Folder folder = store.getFolder(properties.getProperty("LABEL"));
        folder.open(Folder.READ_ONLY);

        for (Message message : folder.getMessages()) {
            System.out.printf("Subject: %s\n", message.getSubject());
            System.out.printf("Received Date: %s\n", message.getReceivedDate());

            System.out.printf("Content:\n%s\n", getText(message.getContent()));
        }

        folder.close(false);
    }

    private static String getText(Object content) throws IOException, MessagingException {
        if (content instanceof Multipart) {
            // text だけ取得する
            Multipart mp = (Multipart) content;
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    return getText(bp.getContent());
                }
            }
        }

        // マッチしなかったらとりあえず toString.
        return content.toString();
    }
}
