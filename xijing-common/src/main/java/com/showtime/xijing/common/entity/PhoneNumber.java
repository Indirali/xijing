package com.showtime.xijing.common.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PhoneNumber implements JsonSerializable {
    private static final int HASH_ITERATION = 1000;
    private static final byte[] SALT = DatatypeConverter
            .parseHexBinary("f263575e7b00a977a8e9a37e08b9c215feb9bfb2f992b2b8f11e");
    private static final int HASH_KEY_LEN = 160;

    public static final String DEFAULT_REGION = "CN";

    // see https://code.google.com/p/libphonenumber/
    private com.google.i18n.phonenumbers.Phonenumber.PhoneNumber phoneNumber;
    private byte[] hashed;

    protected PhoneNumber() {
    }

    public PhoneNumber(String numberToParse
    ) throws InvalidNumberException {
        this(numberToParse, DEFAULT_REGION);
    }

    public PhoneNumber(String numberToParse, String defaultRegion
    ) throws InvalidNumberException {
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        try {
            phoneNumber = util.parse(numberToParse, defaultRegion);
        } catch (NumberParseException e) {
            throw new InvalidNumberException("invalid format", e);
        }
        if (!util.isValidNumber(phoneNumber)) {
            throw new InvalidNumberException("invalid number");
        }
    }

    public int getCountryCode() {
        return phoneNumber.getCountryCode();
    }

    public long getNationalNumber() {
        return phoneNumber.getNationalNumber();
    }

    public boolean isMobile() {
        PhoneNumberType type = PhoneNumberUtil.getInstance().getNumberType(phoneNumber);
        return type == PhoneNumberType.MOBILE
                || type == PhoneNumberType.FIXED_LINE_OR_MOBILE;
    }

    public byte[] getHashed() {
        if (hashed == null) {
            hashed = hash();
        }
        return hashed;
    }

    private byte[] hash() {
        char[] chars = (getCountryCode() + "-" + getNationalNumber()).toCharArray();
        PBEKeySpec spec = new PBEKeySpec(chars, SALT, HASH_ITERATION, HASH_KEY_LEN);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    // NOTE: in our app we only care about the country code and national number, extension should be ignored
    // phoneNumber's equals function use exactSame equals
    // which return false for 18800000001#834, 18800000001
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (toString().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return toString().equals(obj.toString());
    }

    public long toLong() {
        return Long.valueOf("" + getCountryCode() + getNationalNumber());
    }

    // some country nationalNumber is begin of zero 
    // for example +225-08336613, it will throw InvalidNumberException when parse +225-8336613 as PhoneNumber
    // so can't return "+" + getCountryCode() + "-" + getNationalNumber() as String value
    @Override
    public String toString() {
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        return "+" + getCountryCode() + "-" + util.getNationalSignificantNumber(phoneNumber);
    }

    @Override
    public void serialize(JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        // NOTE: pnoStr will be used for constructor PhoneNumber(String )
        // in deserialization.
        String pnoStr = "+" + getCountryCode() + "-" + getNationalNumber();
        jgen.writeString(pnoStr);
    }

    @Override
    public void serializeWithType(JsonGenerator jgen,
                                  SerializerProvider provider, TypeSerializer typeSer)
            throws IOException, JsonProcessingException {
        serialize(jgen, provider);
    }

    public static class InvalidNumberException extends Exception {
        private static final long serialVersionUID = 1797606728395658616L;

        public InvalidNumberException() {
        }

        public InvalidNumberException(String msg) {
            super(msg);
        }

        public InvalidNumberException(String msg, Exception e) {
            super(msg, e);
        }
    }

    public String getRegion() {
        return PhoneNumberUtil.getInstance().getRegionCodeForNumber(phoneNumber);
    }
}
