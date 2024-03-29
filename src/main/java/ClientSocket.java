import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ClientSocket {
    private static String URL = "ws://localhost:8083/gs-guide-websocket";
    static final String WEBSOCKET_URI = "ws://localhost:8080/websocket";
    static final String WEBSOCKET_TOPIC = "/topic/greetings/";

    public static void main(String[] args) {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new Handler();
        stompClient.connect(URL, sessionHandler);


        new Scanner(System.in).nextLine(); // Don't close immediately.

    }
}
