package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketApplicationConfig;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Integer port;
    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;
    private BufferedReader in = null;
    private BufferedWriter out = null;

    public Server(Integer port) {
        this.port = port;
    }

    public void ChatServer(){
        System.out.println("Server running");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            out.write("Hello from my server!\n");
            out.flush();
            String startmsg = in.readLine();
            System.out.println(startmsg);
            out.write("Enter username:\n");
            out.flush();
            String username = in.readLine();
            System.out.println(username);
            out.write("Enter password:\n");
            out.flush();
            String password = in.readLine();
            System.out.println(password);
            ApplicationContext context = new AnnotationConfigApplicationContext(SocketApplicationConfig.class);
            UsersService usersService = context.getBean("usersService", UsersService.class);
            usersService.signUp(username, password);
            System.out.println("qweqweqweqwe");
            out.write("successes\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
