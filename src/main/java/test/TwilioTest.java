package test;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.cdimascio.dotenv.Dotenv;

public class TwilioTest {
    public static void main(String[] args) {
       Twilio.init(Dotenv.load().get("ACCOUNT_SID"), Dotenv.load().get("AUTH_TOKEN"));
        Message message = Message.creator(
                new PhoneNumber("+8801906023937"),
                new PhoneNumber(Dotenv.load().get("TWILIO_NUMBER")),
                "Test Message From MedEasy"
        ).create();
        System.out.println(message.getSid());
    }
}
