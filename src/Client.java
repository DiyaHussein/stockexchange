//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

class Client implements Runnable {
    private final StockExchangeServer server;
    private final String user;

    public Client(StockExchangeServer server, String user) {
        this.server = server;
        this.user = user;
    }

    public void run() {
        String stock = "AAPL";
        int quantity = 5;
        double price = this.server.getStockPrice(stock);
        System.out.printf("%s checked price of %s: $%.2f%n", this.user, stock, price);
        this.server.placeOrder(this.user, stock, quantity);
        double balance = this.server.getBalance(this.user);
        System.out.printf("%s's remaining balance: $%.2f%n", this.user, balance);
    }
}
