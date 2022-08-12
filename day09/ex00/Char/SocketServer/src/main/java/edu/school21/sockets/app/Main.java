package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketApplicationConfig;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static Integer port;
    private static String[] Args;

    public static void main(String[] args) {
        Args = args;
        if (checkArgs()) {
            return;
        }
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketApplicationConfig.class);
        Server server = new Server(port);
        server.ChatServer();
    }


    private static boolean checkArgs() {
        if (Args.length != 1) {
            System.err.println("ERROR_BAD_ARGS");
            return true;
        }

        if (!Args[0].startsWith("--port=")) {
            System.err.println("ERROR_BAD_PARAMS");
            return true;
        }

        try {
            port = Integer.parseInt(Args[0].replaceFirst("--port=", ""));
        } catch (NumberFormatException e) {
            System.err.println("ERROR_PUT_NUMBER");
            return true;
        }

        return false;
    }
}