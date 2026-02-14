# RETO #4: La Estafa de la Casa de Cambio

## Patrón de Diseño
- **Categoría:** Comportamiento
- **Patrón Utilizado:** Strategy

## Justificación
El patrón Strategy es ideal para este caso porque:
- Permite cambiar la estrategia de conversión sin modificar el código cliente
- Encapsula diferentes algoritmos de cambio (real, con comisión, etc.)
- Facilita agregar nuevas monedas o tasas de cambio
- Separa la lógica de conversión de la lógica de negocio

## Cómo lo aplico
1. **Interfaz Strategy:** `ExchangeStrategy` - define el contrato para las conversiones
2. **Estrategia concreta:** `RealExchangeStrategy` - implementa la conversión con tasas reales
3. **Contexto:** `Transaction` - utiliza la estrategia para convertir montos
4. **Cliente:** `Reto4` - inyecta la estrategia y procesa las transacciones

## Principios SOLID aplicados
- **S:** Cada clase tiene una única responsabilidad (Transaction maneja transacciones, RealExchangeStrategy maneja conversiones)
- **O:** Podemos agregar nuevas estrategias (ej: con comisión, tasas preferenciales) sin modificar código existente
- **L:** Todas las estrategias implementan ExchangeStrategy y son intercambiables
- **I:** La interfaz ExchangeStrategy es funcional y específica
- **D:** Transaction depende de la abstracción ExchangeStrategy, no de implementaciones concretas

## Uso de Streams
```java
// Crear transacciones
Stream.of(sc)
    .flatMap(n -> IntStream.rangeClosed(1, n)
    .mapToObj(i -> new Transaction(...)))
    .collect(Collectors.toList());

// Agrupar y sumar por moneda destino
txs.stream()
    .flatMap(t -> t.getConversions().entrySet().stream())
    .collect(Collectors.groupingBy(
        Map.Entry::getKey,
        Collectors.summingDouble(Map.Entry::getValue)
    ));

// Filtrar monedas válidas
Stream.of(destinos.split(","))
    .map(String::trim)
    .filter(RealExchangeStrategy::isValid)
    .collect(Collectors.toList());
