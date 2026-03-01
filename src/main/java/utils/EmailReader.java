package utils;

import jakarta.mail.*;
import jakarta.mail.search.SubjectTerm;
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

            // text/plain (optional, you can include or skip)
            if (part.isMimeType("text/plain")) {
                result.append(part.getContent().toString()).append("\n");
            }

            // text/html → convert to text
            else if (part.isMimeType("text/html")) {
                String html = part.getContent().toString();
                result.append(Jsoup.parse(html).text()).append("\n");
            }

            // Nested multipart
            else if (part.getContent() instanceof MimeMultipart) {
                result.append(extractAllText((MimeMultipart) part.getContent()));
            }
        }

        return result.toString().trim();
    }
}
