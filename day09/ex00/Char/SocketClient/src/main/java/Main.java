import java.io.*;
import java.net.Socket;

public class Main {
    private static Socket socket = null;
    private static BufferedReader reader = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;
    private static Socket clientSocket = null;
    private static Integer port;
    private static String[] Args;

    public static void main(String[] args) {
        Args = args;
        if (checkArgs()) {
            return;
        }
        try {
            clientSocket = new Socket("localhost", port);
            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String serverMsg = in.readLine();
            System.out.println(serverMsg);
            String signupcmd = reader.readLine();
            out.write(signupcmd + "\n");
            out.flush();
            String serverOut = in.readLine();
            System.out.println(serverOut);
            String userName = reader.readLine();
            out.write(userName + "\n");
            out.flush();
            String serverOutout = in.readLine();
            System.out.println(serverOutout);
            String password = reader.readLine();
            out.write(password + "\n");
            out.flush();
            String serverOutoutout = in.readLine();
            System.out.println(serverOutoutout);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static boolean checkArgs() {
        if (Args.length != 1) {
            System.err.println("ERROR_BAD_ARGS");
            return true;
        }

        if (!Args[0].startsWith("--server-port=")) {
            System.err.println("ERROR_BAD_PARAMS");
            return true;
        }

        try {
            port = Integer.parseInt(Args[0].replaceFirst("--server-port=", ""));
        } catch (NumberFormatException e) {
            System.err.println("ERROR_PUT_NUMBER");
            return true;
        }

        return false;
    }
}