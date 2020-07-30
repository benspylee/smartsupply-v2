package com.bluebee.smartsupply.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;

@Component
public class PushNotificationClient {

    @Value("${app.imfpush.url}")
    private String url;
    @Value("${app.imfpush.apikey}")
    private String apikey;

    @Autowired
    private RestTemplate restTemplate;

    public String pushNotification(String msg) throws Exception{
        try {
            PushNotificationClient.MessageReq message=new PushNotificationClient.MessageReq();
            message.setMessageText(msg);
            String json = new ObjectMapper().writeValueAsString(message);
            HttpEntity<String> httpEntity = new HttpEntity<>(json, getHeader());
          //  HttpEntity<PushNotificationClient.MessageReq> httpEntity=new HttpEntity<>(message,getHeader());
            ResponseEntity<Object> response =   restTemplate.postForEntity(new URI(url),httpEntity,Object.class);
            if(response.getStatusCode()== HttpStatus.OK || response.getStatusCode()== HttpStatus.CREATED){
                return "success";
            }
        } catch (RestClientException e) {
            return "fail";
        }
        return "fail";
    }

  private HttpHeaders getHeader(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Content-Type","application/json");
        httpHeaders.add("Authorization","Basic "+apikey);
        return httpHeaders;
    }

    public static class MessageReq{
        private String messageText;

        public String getMessageText() {
            return messageText;
        }

        public void setMessageText(String messageText) {
            this.messageText = messageText;
        }
    }


}
