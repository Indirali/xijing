package com.showtime.xijing.web.utils;


/*******************************************************************************
 * Copyright (c) 2005-2017 longDai, Inc.
 * 效验身份证
 * Contributors:
 *******************************************************************************/
public class IDCardUtils {
    private static final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
    private static final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
    private static int[] ai = new int[18];

    public static boolean Verify(String idCard) {
        if (idCard.length() == 15) {
            idCard = upToEightEen(idCard);
        }
        if (idCard.length() != 18) {
            return false;
        }
        String verify = idCard.substring(17, 18);
        if (verify.equals(getVerify(idCard))) {
            return true;
        }
        return false;
    }

    private static String getVerify(String eightCardId) {
        int remaining = 0;

        if (eightCardId.length() == 18) {
            eightCardId = eightCardId.substring(0, 17);
        }

        if (eightCardId.length() == 17) {
            int sum = 0;
            for (int i = 0; i < 17; i++) {
                String k = eightCardId.substring(i, i + 1);
                ai[i] = Integer.parseInt(k);
            }

            for (int i = 0; i < 17; i++) {
                sum = sum + wi[i] * ai[i];
            }
            remaining = sum % 11;
        }

        return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
    }

    //十五位
    private static String upToEightEen(String fifteenCardId) {
        String eightCardId = fifteenCardId.substring(0, 6);
        eightCardId = eightCardId + "19";
        eightCardId = eightCardId + fifteenCardId.substring(6, 15);
        eightCardId = eightCardId + getVerify(eightCardId);
        return eightCardId;
    }
}
