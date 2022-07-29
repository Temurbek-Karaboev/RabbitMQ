package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Sender {

    private final static String HOST_NAME = "localhost";
    private final static String QUEUE_NAME = "my-second-queue";

    public static void main(String[] args) throws Exception {
        while (true){
            final ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(HOST_NAME);
            try (final Connection connection = connectionFactory.newConnection();
                 final Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                Scanner sc = new Scanner(System.in);
                System.out.println("Write Message: ");
                final String message = sc.nextLine();
                System.out.println("Sending message to RabbitMQ  ->  " + message);
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
