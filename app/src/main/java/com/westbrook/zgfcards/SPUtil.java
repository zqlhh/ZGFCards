package com.westbrook.zgfcards;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SPUtil {


    private static final int MODE_ADD = 0;

    private static final int MODE_CHANGE = 1;

    static ArrayList<CardInfo> list = new ArrayList<>();

    public static void addCard(Context context, CardInfo cardInfo) {

        String cardInfoString = cardInfoToString(cardInfo, MODE_ADD, 0);
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

        String cardInfoString = preferences.getString("start", "0");

        if ("0".equals(cardInfoString)) {
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

        String cardInfoString = cardInfoToString(cardInfo, MODE_CHANGE, 0);

        if (!"".equals(cardInfoString)) {
            SharedPreferences preferences = context.getSharedPreferences("start", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(String.valueOf(cardInfo.cardId), cardInfoString);
            editor.apply();
        }
    }


    public static void deleteCardInfo(Context context, CardInfo cardInfo) {

        SharedPreferences preferences = context.getSharedPreferences("start", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (list.size() == 1) {
            editor.remove("start");
        } else if (cardInfo.equals(list.get(0))) {

            editor.putString("start", String.valueOf(cardInfo.nextId));

        } else if (cardInfo.equals(list.get(list.size() - 1))) {

            String cardInfoString = cardInfoToString(list.get(list.size() - 2), MODE_CHANGE, 0);

            editor.putString(String.valueOf(list.get(list.size() - 2)), cardInfoString);


        } else {

            // 双向链表更佳

            int index;

            for (index = 0; index < list.size(); index++) {

                if (cardInfo.equals(list.get(index))) {
                    break;
                }
            }

            String cardInfoString = cardInfoToString(list.get(index - 1), MODE_CHANGE, list.get(index + 1).cardId);
            editor.putString(String.valueOf(list.get(index - 1).cardId), cardInfoString);

        }

        editor.remove(String.valueOf(cardInfo.cardId));
        editor.apply();

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


    private static String cardInfoToString(CardInfo cardInfo, int type, long nextId) {


        String cardInfoString = "";
        try {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("cardId", cardInfo.cardId);

            jsonObject.put("foodName", cardInfo.foodName);


            if (type == MODE_ADD) {
                // 危险操作，如果进入 App 时不读取全部信息，会丢失所有信息

                jsonObject.put("nextId", list.size() == 0 ? 0 : list.get(0).cardId);
                jsonObject.put("leftNum", cardInfo.leftNum);

            } else if (type == MODE_CHANGE) {

                if (nextId == 0) {
                    jsonObject.put("nextId", cardInfo.nextId);
                    jsonObject.put("leftNum", cardInfo.leftNum - 1);
                } else {

                    jsonObject.put("nextId", nextId);
                    jsonObject.put("leftNum", cardInfo.leftNum);

                }
            }
            cardInfoString = jsonObject.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cardInfoString;

    }


}
