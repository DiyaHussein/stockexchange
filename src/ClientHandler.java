
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
    private final Socket socket;
    private final StockExchangeServer stockExchangeServer;

    public ClientHandler(Socket socket, StockExchangeServer stockExchangeServer) {
        this.socket = socket;
        this.stockExchangeServer = stockExchangeServer;
    }

    public void run() {
        try {
            InputStream input = this.socket.getInputStream();

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                try {
                    OutputStream output = this.socket.getOutputStream();

                    try {
                        PrintWriter writer = new PrintWriter(output, true);

                        try {
                            writer.println("Welcome to the Stock Exchange Server!");

                            String text;
                            while((text = reader.readLine()) != null) {
                                System.out.println("Received from client: " + text);
                                String[] parts = text.split(" ");
                                String command = parts[0];
                                String user;
                                double balance;
                                switch (command.toLowerCase()) {
                                    case "price":
                                        if (parts.length == 2) {
                                            user = parts[1];
                                            balance = this.stockExchangeServer.getStockPrice(user);
                                            writer.println("Price of " + user + ": $" + balance);
                                        } else {
                                            writer.println("Invalid command. Use: price <stock>");
                                        }
                                        break;
                                    case "balance":
                                        if (parts.length == 2) {
                                            user = parts[1];
                                            balance = this.stockExchangeServer.getBalance(user);
                                            writer.println(user + "'s balance: $" + balance);
                                        } else {
                                            writer.println("Invalid command. Use: balance <user>");
                                        }
                                        break;
                                    case "buy":
                                        writer.println("incase buy");
                                        if (parts.length == 3) {
                                            writer.println("in if");
                                            user = parts[1];
                                            String stock = parts[2];
                                            int quantity = Integer.parseInt(parts[3]);
                                            this.stockExchangeServer.placeOrder(user, stock, quantity);
                                            writer.println("Order placed for " + quantity + " shares of " + stock);
                                        } else {
                                            writer.println("Invalid command. Use: buy <user> <stock> <quantity>");
                                        }
                                        break;
                                    default:
                                        writer.println("Unknown command");
                                }
                            }
                        } catch (Throwable var33) {
                            try {
                                writer.close();
                            } catch (Throwable var32) {
                                var33.addSuppressed(var32);
                            }

                            throw var33;
                        }

                        writer.close();
                    } catch (Throwable var34) {
                        if (output != null) {
                            try {
                                output.close();
                            } catch (Throwable var31) {
                                var34.addSuppressed(var31);
                            }
                        }

                        throw var34;
                    }

                    if (output != null) {
                        output.close();
                    }
                } catch (Throwable var35) {
                    try {
                        reader.close();
                    } catch (Throwable var30) {
                        var35.addSuppressed(var30);
                    }

                    throw var35;
                }

                reader.close();
            } catch (Throwable var36) {
                if (input != null) {
                    try {
                        input.close();
                    } catch (Throwable var29) {
                        var36.addSuppressed(var29);
                    }
                }

                throw var36;
            }

            if (input != null) {
                input.close();
            }
        } catch (IOException var37) {
            IOException ex = var37;
            System.out.println("Client handler exception: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                this.socket.close();
            } catch (IOException var28) {
                IOException ex = var28;
                System.out.println("Could not close socket");
                ex.printStackTrace();
            }

        }

    }
}
