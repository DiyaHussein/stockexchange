
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class StockExchangeServer {
    private final Map<String, Double> stockPrices = new HashMap();
    private final Map<String, Double> accountBalances = new HashMap();
    private final Lock lock = new ReentrantLock();

    public StockExchangeServer() {
        this.stockPrices.put("AAPL", 150.0);
        this.stockPrices.put("GOOG", 2800.0);
        this.stockPrices.put("AMZN", 3300.0);
        this.accountBalances.put("user1", 10000.0);
        this.accountBalances.put("user2", 5000.0);
    }

    public void placeOrder(String user, String stock, int quantity) {
        this.lock.lock();

        try {
            double stockPrice = (Double)this.stockPrices.getOrDefault(stock, 0.0);
            double orderCost = stockPrice * (double)quantity;
            double userBalance = (Double)this.accountBalances.getOrDefault(user, 0.0);
            if (userBalance >= orderCost) {
                this.accountBalances.put(user, userBalance - orderCost);
                System.out.printf("Order successful! %s bought %d shares of %s at $%.2f per share.%n", user, quantity, stock, stockPrice);
            } else {
                System.out.printf("Order failed! %s has insufficient balance to buy %d shares of %s.%n", user, quantity, stock);
            }
        } finally {
            this.lock.unlock();
        }

    }

    public double getStockPrice(String stock) {
        return (Double)this.stockPrices.getOrDefault(stock, 0.0);
    }

    public double getBalance(String user) {
        return (Double)this.accountBalances.getOrDefault(user, 0.0);
    }
}
