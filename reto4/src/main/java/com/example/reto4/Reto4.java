package com.example.reto4;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Reto4 {
    private static final ExchangeStrategy STRATEGY = new RealExchangeStrategy();

    public static void ejecutar() {
        try (var sc = new Scanner(System.in)) {
            var txs = Stream.of(sc)
                    .peek(s -> System.out.println("CASA DE CAMBIO"))
                    .peek(s -> System.out.println("Monedas: USD, EUR, JPY, COP"))
                    .map(s -> pedirInt(s, "Transacciones: "))
                    .flatMap(n -> IntStream.rangeClosed(1, n)
                            .mapToObj(i -> {
                                System.out.printf("%n--- Tx %d ---%n", i);
                                return new Transaction(
                                        i,
                                        pedirDouble(sc, "Monto: "),
                                        pedirMoneda(sc, "origen"),
                                        pedirDestinos(sc),
                                        STRATEGY
                                );
                            }))
                    .collect(Collectors.toList());

            System.out.println("\nRESULTADOS");
            txs.forEach(Transaction::display);

            System.out.println("\n--- TOTALES ---");
            txs.stream()
                    .flatMap(t -> t.getConversions().entrySet().stream())
                    .collect(Collectors.groupingBy(
                            Map.Entry::getKey,
                            Collectors.summingDouble(Map.Entry::getValue)
                    ))
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> System.out.printf("%s: %.2f %s%n",
                            e.getKey(), e.getValue(), e.getKey()));
        }
    }

    private static int pedirInt(Scanner sc, String msg) {
        System.out.print(msg);
        return Integer.parseInt(sc.nextLine());
    }

    private static double pedirDouble(Scanner sc, String msg) {
        System.out.print(msg);
        return Double.parseDouble(sc.nextLine());
    }

    private static String pedirMoneda(Scanner sc, String tipo) {
        return Stream.generate(() -> {
                    System.out.print("Moneda " + tipo + ": ");
                    return sc.nextLine().toUpperCase();
                })
                .filter(RealExchangeStrategy::isValid)
                .findFirst()
                .orElseThrow();
    }

    private static List<String> pedirDestinos(Scanner sc) {
        return Stream.of(pedirString(sc, "Destinos (USD,EUR,JPY,COP): ").toUpperCase().split(","))
                .map(String::trim)
                .filter(RealExchangeStrategy::isValid)
                .collect(Collectors.toList());
    }

    private static String pedirString(Scanner sc, String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }
}