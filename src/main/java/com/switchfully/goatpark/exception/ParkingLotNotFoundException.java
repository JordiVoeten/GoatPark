package com.switchfully.goatpark.exception;

public class ParkingLotNotFoundException extends RuntimeException {
    public ParkingLotNotFoundException(String message) {
        super(message);
    }
}
