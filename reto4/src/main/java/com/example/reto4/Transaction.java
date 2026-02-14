package com.example.reto4;

import java.util.*;
import java.util.stream.Collectors;

public class Transaction {
    private final int id;
    private final double amount;
    private final String from;
    private final Map<String, Double> conversions;

    public Transaction(int id, double amount, String from, List<String> to, ExchangeStrategy strategy) {
        this.id = id;
        this.amount = amount;
        this.from = from;
        this.conversions = to.stream()
                .map(String::trim)
                .collect(Collectors.toMap(
                        c -> c,
                        c -> strategy.convert(amount, from, c)
                ));
    }

    public void display() {
        System.out.printf("Tx %d: %.2f %s%n", id, amount, from);
        conversions.forEach((c, a) ->
                System.out.printf("  -> %s: %.2f %s%n", c, a, c));
    }

    public Map<String, Double> getConversions() {
        return conversions;
    }
}
