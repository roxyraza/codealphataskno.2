import java.util.*; 
public class StockTradingPlatform {

    static class Stock {
        String ticker;
        double price;

        Stock(String ticker, double price) {
            this.ticker = ticker;
            this.price = price;
        }
        
        @Override
        public String toString() {
            return ticker + ": $" + price;
        }
    }

   
    static class Portfolio {
        private final Map<String, Integer> holdings = new HashMap<>();
        private double cash;

        Portfolio(double initialCash) {
            this.cash = initialCash;
        }

        public void buyStock(Stock stock, int quantity) {
            double totalCost = stock.price * quantity;
            if (cash >= totalCost) {
                cash -= totalCost;
                holdings.put(stock.ticker, holdings.getOrDefault(stock.ticker, 0) + quantity);
                System.out.println("Bought " + quantity + " shares of " + stock.ticker);
            } else {
                System.out.println("Not enough cash to buy " + quantity + " shares of " + stock.ticker);
            }
        }

        public void sellStock(Stock stock, int quantity) {
            int currentQuantity = holdings.getOrDefault(stock.ticker, 0);
            if (currentQuantity >= quantity) {
                double totalRevenue = stock.price * quantity;
                cash += totalRevenue;
                holdings.put(stock.ticker, currentQuantity - quantity);
                System.out.println("Sold " + quantity + " shares of " + stock.ticker);
            } else {
                System.out.println("Not enough shares to sell " + quantity + " shares of " + stock.ticker);
            }
        }

        public void printPortfolio() {
            System.out.println("Cash available: $" + cash);
            System.out.println("Holdings:");
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " shares");
            }
        }
    }

    public static void main(String[] args) {
    
        List<Stock> marketStocks = Arrays.asList(
            new Stock("AAPL", 175.30),
            new Stock("GOOGL", 2800.50),
            new Stock("AMZN", 3400.80)
        );

        Portfolio userPortfolio = new Portfolio(10000.00);

        Scanner scanner = new Scanner(System.in);
        String command;

      
        while (true) {
            System.out.println("\nCommands: list, buy [ticker] [quantity], sell [ticker] [quantity], portfolio, exit");
            System.out.print("Enter command: ");
            command = scanner.nextLine();

            String[] parts = command.split(" ");
            if (parts[0].equalsIgnoreCase("list")) {
                System.out.println("Available stocks:");
                for (Stock stock : marketStocks) {
                    System.out.println(stock);
                }
            } else if (parts[0].equalsIgnoreCase("buy")) {
                if (parts.length != 3) {
                    System.out.println("Invalid buy command.");
                    continue;
                }
                String ticker = parts[1];
                int quantity;
                try {
                    quantity = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity.");
                    continue;
                }
                Stock stock = findStock(marketStocks, ticker);
                if (stock != null) {
                    userPortfolio.buyStock(stock, quantity);
                } else {
                    System.out.println("Stock not found.");
                }
            } else if (parts[0].equalsIgnoreCase("sell")) {
                if (parts.length != 3) {
                    System.out.println("Invalid sell command.");
                    continue;
                }
                String ticker = parts[1];
                int quantity;
                try {
                    quantity = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity.");
                    continue;
                }
                Stock stock = findStock(marketStocks, ticker);
                if (stock != null) {
                    userPortfolio.sellStock(stock, quantity);
                } else {
                    System.out.println("Stock not found.");
                }
            } else if (parts[0].equalsIgnoreCase("portfolio")) {
                userPortfolio.printPortfolio();
            } else if (parts[0].equalsIgnoreCase("exit")) {
                break;
            } else {
                System.out.println("Unknown command.");
            }
        }
     
        scanner.close();
    }

    private static Stock findStock(List<Stock> marketStocks, String ticker) {
        for (Stock stock : marketStocks) {
            if (stock.ticker.equalsIgnoreCase(ticker)) {
                return stock;
            }
        }
        return null;
    }
}
