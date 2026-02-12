package challenge4;

import java.util.Map;

public class RealExchangeStrategy implements ExchangeStrategy {
    private static final Map<String, Double> RATES = Map.of(
            "USD", 1.0,
            "EUR", 0.92,
            "JPY", 148.5,
            "COP", 4000.0
    );

    @Override
    public double convert(double amount, String from, String to) {
        return amount / RATES.get(from) * RATES.get(to);
    }

    public static boolean isValid(String currency) {
        return RATES.containsKey(currency);
    }
}