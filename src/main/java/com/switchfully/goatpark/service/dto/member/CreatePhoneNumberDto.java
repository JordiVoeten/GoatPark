package com.switchfully.goatpark.service.dto.member;

import javax.persistence.Column;

public class CreatePhoneNumberDto {

    private String prefix;
    private String number;

    public CreatePhoneNumberDto(String prefix, String number) {
        this.prefix = prefix;
        this.number = number;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNumber() {
        return number;
    }
}
