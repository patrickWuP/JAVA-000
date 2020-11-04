package src.com.wp.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer1 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        try {
            ServerSocket serverSocket = new ServerSocket(8188);
            while (true) {
                Socket accept = serverSocket.accept();
                executorService.execute(() -> {
                    service(accept);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void service(Socket socket) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.write("I am wp");
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
