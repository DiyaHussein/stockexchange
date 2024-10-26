
public class StockExchangeApp {
    public StockExchangeApp() {
    }

    public static void main(String[] args) {
        StockExchangeServer stockExchangeServer = new StockExchangeServer();
        SimpleServer simpleServer = new SimpleServer(stockExchangeServer);
        simpleServer.start(8080);
    }
}
