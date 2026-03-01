package utils;

import jakarta.mail.*;
import jakarta.mail.search.SubjectTerm;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import org.jsoup.Jsoup;

public class EmailReader {

    public static Message waitForEmail(String host, String username, String appPassword, String subject, int timeoutSeconds) throws Exception {

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("imaps");
        store.connect(host, username, appPassword);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        for (int i = 0; i < timeoutSeconds; i++) {
            Message[] messages = inbox.search(new SubjectTerm(subject));
            if (messages.length > 0) {
                return messages[messages.length - 1];
            }
            Thread.sleep(1000);
        }

        throw new RuntimeException("Email not received within timeout");
    }

    public static String getFullEmailBody(Message message) throws Exception {
        Object content = message.getContent();

        if (content instanceof String) {
            // Single-part email
            return content.toString();
        }

        if (content instanceof MimeMultipart) {
            return extractAllText((MimeMultipart) content);
        }

        // Fallback: sometimes content is InputStream
        if (content instanceof InputStream) {
            InputStream is = (InputStream) content;
            return Jsoup.parse(new String(is.readAllBytes(), StandardCharsets.UTF_8)).text();
        }

        return "";
    }

    private static String extractAllText(MimeMultipart multipart) throws Exception {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart part = multipart.getBodyPart(i);

            // Ignore attachments
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                continue;
            }

            Object partContent = part.getContent();

            // text/plain → include

            if (part.isMimeType("text/html")) {
                if (partContent instanceof String) {
                    result.append(partContent.toString()).append("\n");
                } else if (partContent instanceof InputStream) {
                    InputStream is = (InputStream) partContent;
                    result.append(new String(is.readAllBytes(), StandardCharsets.UTF_8)).append("\n");
                }
            }

            // text/html → convert to text
            else if (part.isMimeType("text/plain")) {
                String html = null;
                if (partContent instanceof String) {
                    html = partContent.toString();
                } else if (partContent instanceof InputStream) {
                    InputStream is = (InputStream) partContent;
                    html = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                }
                if (html != null) {
                    result.append(Jsoup.parse(html).text()).append("\n");
                }
            }

            // Nested multipart
            else if (partContent instanceof MimeMultipart) {
                result.append(extractAllText((MimeMultipart) partContent));
            }
        }

        return result.toString().trim();
    }
}
