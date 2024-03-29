
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
        session.subscribe("/topic/greetings", this);
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
        Greeting greeting = (Greeting) payload;
        logger.info("Received : " + greeting.getContent());

    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    private HelloMessage getSampleMessage(String name) {
        HelloMessage msg = new HelloMessage();
        msg.setName(name);

        return msg;
    }
    private HelloMessage getSampleMessage() {
        HelloMessage msg = new HelloMessage();
        msg.setName("Nicky");

        return msg;
    }
}