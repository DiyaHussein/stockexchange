
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    private final StockExchangeServer stockExchangeServer;

    public SimpleServer(StockExchangeServer stockExchangeServer) {
        this.stockExchangeServer = stockExchangeServer;
    }

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            try {
                System.out.println("Server is listening on port " + port);

                while(true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected");
                    (new ClientHandler(socket, this.stockExchangeServer)).start();
                }
            } catch (Throwable var6) {
                try {
                    serverSocket.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }
        } catch (IOException var7) {
            IOException ex = var7;
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
