import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.extensions.java6.auth.oauth2.GooglePromptReceiver;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class GmailSearchMessages {
    private static final String APPLICATION_NAME = "Gmail API Java SearchMessages";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GmailSearchMessages.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        GooglePromptReceiver receier = new GooglePromptReceiver();
        return new AuthorizationCodeInstalledApp(flow, receier).authorize("user");
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Search message on the conditions of `query`.
        Properties properties = new Properties();
        properties.load(new InputStreamReader(
                    ClassLoader.getSystemResourceAsStream("query.properties"), "UTF-8"));
        String query = properties.getProperty("QUERY");

        String user = "me";
        ListMessagesResponse listResponse = service.users().messages().list(user).setQ(query).execute();
        List<Message> messages = listResponse.getMessages();

        // if found message, display it.
        if (messages.isEmpty()) {
            System.out.println("No message found.");
        } else {
            for (Message message : messages) {
                // get full message.
                Message fullMessage = service.users().messages().get(user, message.getId()).execute();

                // get Payload.
                MessagePart payload = fullMessage.getPayload();

                // print Subject.
                Optional<MessagePartHeader> subject = payload.getHeaders().stream().filter(h -> h.getName().equals("Subject")).findFirst();
                subject.ifPresent(s -> System.out.printf("Subject: %s\n", s.getValue()));

                // get Body of `text/plain`.
                // supported structure:
                //     1. text/plain
                //     2. multipart/alternative
                //         - text/plain
                //     3. multipart/mixed
                //         - text/plain
                MessagePart part;
                if (payload.getParts() == null) {
                    part = payload;
                    if (!(part.getMimeType().equals("text/plain"))) {
                        System.err.println("no text/plain part.");
                        continue;
                    }
                } else {
                    Optional<MessagePart> op = payload.getParts().stream().filter(p -> p.getMimeType().equals("text/plain")).findFirst();
                    if (op.isPresent()) {
                        part = op.get();
                    } else {
                        System.err.println("no text/plain part.");
                        continue;
                    }
                }

                // print body
                byte[] rawBody = part.getBody().decodeData();
                String body = new String(rawBody, "UTF-8");
                System.out.println(body);

                // print new line.
                System.out.println();
            }
        }
    }
}

