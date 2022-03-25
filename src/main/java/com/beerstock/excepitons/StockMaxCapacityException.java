package com.beerstock.excepitons;

public class StockMaxCapacityException extends RuntimeException {
    public StockMaxCapacityException(int maxAllowedQuantity) {
        super(String.format("Quantidade máxima do estoque excedida. O valor máximo a ser inserido é de %s.", maxAllowedQuantity));
    }
}
