package valuesmap.streamTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.*;

/**
 * Created by I311352 on 7/28/2016.
 */
class Trade {
    private String status;
    private Long quantity;
    private String issuer;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
@FunctionalInterface
interface ITrade {
    public boolean check(Trade t);
}

@FunctionalInterface
interface IComponent {

    // Functional method - note we must have one of these functional methods only
    public void init();

    // default method - note the keyword default
    default String getComponentName(){
        return "DEFAULT NAME";
    }
    // default method - note the keyword default
    default Date getCreationDate(){
        return new Date();
    }
}

public class functionTest {

    private List<Trade> filterTrades(ITrade tradeLambda, List<Trade> trades) {
        List<Trade> newTrades = new ArrayList<>();

        for (Trade trade : trades) {
            if (tradeLambda.check(trade)) {
                newTrades.add(trade);
            }
        }
        return newTrades;
    }

    public static void main(String[] args) {
        // Predicate expecting two trades to compare and returning the condition's output
        BiPredicate<Trade,Trade> isBig = (t1, t2) -> t1.getQuantity() > t2.getQuantity();

        BiFunction<Trade,Trade,Long> sumQuantities = (t1, t2) -> {
            return t1.getQuantity()+t2.getQuantity();
        };


        Supplier<String> supplierString = ()->{return "From Supplier";};

        Consumer<String> printLower = (s)->{System.out.println(s.toLowerCase());};
        printLower.accept(supplierString.get());

        // Function to calculate the aggregated quantity of all the trades - taking in a collection and returning an integer!
        Function<List<Trade>,Integer> aggegatedQuantity = t -> {
            int aggregatedQuantity = 0;
            for (Trade tt: t){
                aggregatedQuantity+=tt.getQuantity();
            }
            return aggregatedQuantity;
        };
        // Using Stream and map and reduce functionality
        List<Trade> trades = new ArrayList<>();

        Long aggregatedQuantity =
                trades.stream()
                      .map((t) -> t.getQuantity())
                      .reduce(0L, Long::sum);
        UnaryOperator<String> toLowerUsingUnary = (s) -> s.toLowerCase();
//        // Or, even better
//        aggregatedQuantity =
//                trades.stream()
//                      .map((t) -> t.getQuantity()).sum();

        // convert centigrade to fahrenheit
        Function<Integer,Double> centigradeToFahrenheitInt = x -> new Double((x*9/5)+32);

        // String to an integer
        Function<String, Integer> stringToInt = x -> Integer.valueOf(x);

        // tests
        System.out.println("Centigrade to Fahrenheit: "+centigradeToFahrenheitInt.apply(200));
        System.out.println(" String to Int: " + stringToInt.apply("4"));
        // Lambda to check an empty string
        Predicate<String> emptyStringChecker = s -> s.isEmpty();


        ITrade newTradeChecker = (Trade t) -> t.getStatus().equals("NEW");
        ITrade newTradeChecker2 = (t) -> t.getStatus().equals("NEW");
        // Lambda for big trade
        ITrade bigTradeLambda = (Trade t) -> t.getQuantity() > 10000000;

        // Lambda that checks if the trade is a new large google trade
        ITrade issuerBigNewTradeLambda = (t) -> {
            return t.getIssuer().equals("GOOG") &&
                    t.getQuantity() > 10000000 &&
                    t.getStatus().equals("NEW");
        };

    }

}
