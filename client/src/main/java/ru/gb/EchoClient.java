package ru.gb;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class EchoClient {
    private static SocketChannel client;
    private static ByteBuffer buffer;

    public static void main(String[] args) {
        new EchoClient();
    }

    public EchoClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 4567));
            buffer = ByteBuffer.allocate(256);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.nextLine();
                sendMessage(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() throws IOException {
        client.close();
        buffer = null;
    }

    public void sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes());
        try {
            client.write(buffer);

            if ("q".equals(msg)) {
                stop();
                System.exit(0);
            }

            buffer.clear();
            client.read(buffer);
            String response = new String(buffer.array()).trim();
            System.out.println(response);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}