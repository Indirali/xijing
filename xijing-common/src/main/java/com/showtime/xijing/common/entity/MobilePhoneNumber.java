package com.showtime.xijing.common.entity;

public class MobilePhoneNumber extends PhoneNumber {

    public MobilePhoneNumber(String numberToParse) throws InvalidNumberException {
        super(numberToParse);
        if (!isMobile()) {
            throw new InvalidNumberException("not a mobile phone number");
        }
    }

    public MobilePhoneNumber(String numberToParse, String defaultRegion) throws InvalidNumberException {
        super(numberToParse, defaultRegion);
        if (!isMobile()) {
            throw new InvalidNumberException("not a mobile phone number");
        }
    }

}
