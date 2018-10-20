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
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

/**
 * GetMail
 */
public class GetMail {
    public static void main(String[] args) throws IOException, NoSuchProviderException, MessagingException {

        Properties properties = new Properties();
        properties.load(new InputStreamReader(
                    ClassLoader.getSystemResourceAsStream("account.properties"), "UTF-8"));

        final String HOST = properties.getProperty("HOST");
        final String PORT = properties.getProperty("PORT");
        final String USER = properties.getProperty("USER");
        final String PASSWORD = properties.getProperty("PASSWORD");
        final String SUBJECT_START_WITH = properties.getProperty("SUBJECT_START_WITH");

        System.out.println(properties);

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

        // 検索条件組み立て
        SearchTerm st = new SubjectTerm(SUBJECT_START_WITH);

        Message[] messages = folder.search(st);

        for (Message message : messages) {
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
