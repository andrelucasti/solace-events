package io.andrelucas.solaceevents.tutorial;

import com.solacesystems.jcsmp.BytesMessage;
import com.solacesystems.jcsmp.BytesXMLMessage;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.TextMessage;
import com.solacesystems.jcsmp.XMLMessageListener;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;

@Component
public class TutorialListener implements XMLMessageListener {

  @Override
  public void onReceive(BytesXMLMessage bytesXMLMessage) {

    if (bytesXMLMessage instanceof TextMessage) {
      System.out.println("TextMessage received: " + ((TextMessage) bytesXMLMessage).getText());
    } else if (bytesXMLMessage instanceof BytesMessage) {
      byte[] bytes = new byte[((BytesMessage) bytesXMLMessage).getAttachmentContentLength()];
      ((BytesMessage) bytesXMLMessage).readAttachmentBytes(bytes);
      System.out.println("BytesMessage received: " + new String(bytes, StandardCharsets.UTF_8));
    }
  }

  @Override
  public void onException(JCSMPException e) {

  }
}
