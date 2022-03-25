package com.beerstock.excepitons;

public class StockMaxCapacityException extends RuntimeException {
    public StockMaxCapacityException(int maxAllowedQuantity, int exceededQuantity) {
        super(String.format("Quantidade máxima do estoque excedida em %s. O valor máximo a ser inserido é de %s.", exceededQuantity ,maxAllowedQuantity));
    }
}
