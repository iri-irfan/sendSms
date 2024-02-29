package com.twilioSmsService.controller;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilioSmsService.payload.SMSRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @PostMapping("/send-sms")
    public ResponseEntity<?> sendSMS(@RequestBody SMSRequest smsRequest) {
        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                            new PhoneNumber("whatsapp:"+smsRequest.getTo()),
                            new PhoneNumber("whatsapp:"+twilioPhoneNumber),
                            smsRequest.getMessage())
                    .create();
            return ResponseEntity.ok("Sms Sent Successfully:" + message.getSid());
        } catch (
                Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("errorMessage" + e.getMessage());

        }
    }


}
