
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

/**
 * This class is an implementation for <code>StompSessionHandlerAdapter</code>.
 * Once a connection is established, We subscribe to /topic/messages and
 * send a sample message to server.
 *
 * @author Kalyan
 *
 */
public class Handler extends StompSessionHandlerAdapter {

    private Logger logger = LogManager.getLogger(Handler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("topic/greetings", this);
        logger.info("Subscribed to topic/greetings");
        session.send("/app/hello", getSampleMessage());

        logger.info("Message sent to websocket server");

    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Greeting.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        String string = (String) payload;
        logger.info("Received : " + string);
    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    private String getSampleMessage() {
        HelloMessage msg = new HelloMessage();
        msg.setName("Nicky");

        return String.format("{\"name\":\"%s\"}",msg.getName());
    }
}