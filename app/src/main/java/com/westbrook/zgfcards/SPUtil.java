package com.westbrook.zgfcards;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SPUtil {


    private static final int MODE_ADD = 0;

    private static final int MODE_CHANGE = 1;


    static ArrayList<CardInfo> list = new ArrayList<>();

    public static void addCard(Context context, CardInfo cardInfo) {

        String cardInfoString = cardInfoToString(cardInfo, MODE_ADD);
        if (!"".equals(cardInfoString)) {
            SharedPreferences preferences = context.getSharedPreferences("start", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("start", String.valueOf(cardInfo.cardId));
            editor.putString(String.valueOf(cardInfo.cardId), cardInfoString);
            editor.apply();
        }

    }

    public static List<CardInfo> getCards(Context context) {

        list.clear();

        SharedPreferences preferences = context.getSharedPreferences("start", Context.MODE_PRIVATE);

        String cardInfoString = preferences.getString("start", "");

        if ("".equals(cardInfoString)) {
            return list;
        } else {

            do {
                CardInfo cardInfo = getCard(context, cardInfoString);
                if (cardInfo != null) {
                    list.add(cardInfo);
                    cardInfoString = String.valueOf(cardInfo.nextId);
                } else {
                    break;
                }
            } while (!"0".equals(cardInfoString));

        }

        return list;

    }


    public static void changeCardInfo(Context context, CardInfo cardInfo) {

        String cardInfoString = cardInfoToString(cardInfo, MODE_CHANGE);

        if (!"".equals(cardInfoString)) {
            SharedPreferences preferences = context.getSharedPreferences("start", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(String.valueOf(cardInfo.cardId), cardInfoString);
            editor.apply();
        }
    }


    private static CardInfo getCard(Context context, String cardId) {

        SharedPreferences preferences = context.getSharedPreferences("start", Context.MODE_PRIVATE);

        String cardInfoString = preferences.getString(cardId, "");


        try {
            JSONObject jsonObject = new JSONObject(cardInfoString);

            CardInfo cardInfo = new CardInfo();

            cardInfo.cardId = jsonObject.getLong("cardId");

            cardInfo.foodName = jsonObject.getString("foodName");

            cardInfo.leftNum = jsonObject.getInt("leftNum");

            cardInfo.nextId = jsonObject.getLong("nextId");

            return cardInfo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    private static String cardInfoToString(CardInfo cardInfo, int type) {


        String cardInfoString = "";
        try {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("cardId", cardInfo.cardId);

            jsonObject.put("foodName", cardInfo.foodName);

            jsonObject.put("leftNum", cardInfo.leftNum);


            if (type == MODE_ADD) {
                // 危险操作，如果进入 App 时不读取全部信息，会丢失所有信息

                jsonObject.put("nextId", list.size() == 0 ? 0 : list.get(0).cardId);
                jsonObject.put("leftNum", cardInfo.leftNum);

            } else {
                jsonObject.put("nextId", cardInfo.nextId);
                jsonObject.put("leftNum", cardInfo.leftNum - 1);

            }
            cardInfoString = jsonObject.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cardInfoString;

    }


}
