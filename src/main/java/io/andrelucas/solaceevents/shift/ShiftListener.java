package io.andrelucas.solaceevents.shift;

import com.solacesystems.jcsmp.BytesMessage;
import com.solacesystems.jcsmp.BytesXMLMessage;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.TextMessage;
import com.solacesystems.jcsmp.XMLMessageListener;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class ShiftListener implements XMLMessageListener {
  private static final Logger logger = Logger.getLogger(ShiftListener.class.getName());

  @Override
  public void onReceive(BytesXMLMessage bytesXMLMessage) {
    logger.info("ShiftListener.onReceive");

    if (bytesXMLMessage instanceof TextMessage) {
      logger.info("TextMessage received: " + ((TextMessage) bytesXMLMessage).getText());
    } else if (bytesXMLMessage instanceof BytesMessage) {
      byte[] bytes = new byte[((BytesMessage) bytesXMLMessage).getAttachmentContentLength()];
      ((BytesMessage) bytesXMLMessage).readAttachmentBytes(bytes);
      logger.info("BytesMessage received: " + new String(bytes, StandardCharsets.UTF_8));
    }

  }

  @Override
  public void onException(JCSMPException e) {
    System.out.println("ShiftListener.onException");
    System.out.println(e.getMessage());
  }
}
