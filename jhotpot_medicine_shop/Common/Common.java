package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Common;

import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.Category;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.User;

public class Common {
    public static User currentUser;
    public static String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else if (status.equals("1"))
            return "On my way";
        else
            return "Order Cancelled, Please call our hotline for information";
    }
}
