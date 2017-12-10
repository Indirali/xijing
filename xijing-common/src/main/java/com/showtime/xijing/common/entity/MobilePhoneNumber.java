package com.showtime.xijing.common.entity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MobilePhoneNumber extends PhoneNumber {

    public MobilePhoneNumber(String numberToParse)
            throws InvalidNumberException {
        super(numberToParse);
        if (!isMobile()) {
            log.info("not a mobile phone number!");
            throw new InvalidNumberException("not a mobile phone number");
        }
    }

    public MobilePhoneNumber(String numberToParse, String defaultRegion) throws InvalidNumberException {
        super(numberToParse, defaultRegion);
        if (!isMobile()) {
            log.info("not a mobile phone number!");
            throw new InvalidNumberException("not a mobile phone number");
        }
    }

}
