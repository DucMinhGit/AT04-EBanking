package utils;

import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.Message;
import com.mailosaur.models.MessageSearchParams;
import com.mailosaur.models.SearchCriteria;
import lombok.extern.log4j.Log4j2;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class MailosaurUtil {

    private static MailosaurClient mailosaurClient;
    private static String serverId;
    private static Properties config = new Properties();

    static {
        try {
            config.load(new FileReader("src/test/resources/config.properties"));
            String apiKey = config.getProperty("mailosaur.api.key");
            serverId = config.getProperty("mailosaur.server.id");

            if (apiKey == null || serverId == null) {
                throw new RuntimeException("Mailosaur API Key or Server ID can not config in config.properties");
            }
            mailosaurClient = new MailosaurClient(apiKey);
        } catch (IOException e) {
            throw new RuntimeException("Can not read file config.properties", e);
        }
    }

    public static String getOtp(String emailAddress, long searchSince) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(serverId);

        params.withReceivedAfter(searchSince);

        SearchCriteria criteria = new SearchCriteria();
        criteria.withSentTo(emailAddress);

        log.info("Waiting email OTP at address: " + emailAddress + " (start " + searchSince + ")");
        Message message = mailosaurClient.messages().get(params, criteria);

        log.info("Received email: " + message.subject());

        Pattern pattern = Pattern.compile("OTP\\s*:\\s*([A-Z0-9]+)");
        Matcher matcher = pattern.matcher(message.text().body());

        if (matcher.find()) {
            String otp = matcher.group(1);
            log.info("Get OTP: " + otp);
            return otp;
        } else {
            log.info("--- DEBUG: NOT FOUND REGEX ---");
            log.info("FIND TEXT BODY:");
            log.info(message.text().body());
            log.info("---------------------------------");
            throw new RuntimeException("Not found string 'OTP : [CODE]' in content TEXT of email.");
        }
    }
}
