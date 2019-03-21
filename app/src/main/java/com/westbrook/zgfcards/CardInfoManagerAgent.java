package com.westbrook.zgfcards;

import android.content.Context;

import com.westbrook.zgfcards.impl.CardManager;

import java.util.List;

public class CardInfoManagerAgent implements CardManager {


    private static CardInfoManagerAgent agent;

    private Context context;

    @Override
    public List<CardInfo> getAllCardInfo() {
        return SPUtil.getCards(context);
    }

    private CardInfoManagerAgent(Context context) {

        this.context = context;

    }


    public static CardInfoManagerAgent getCardInfoManagetAgent(Context context) {

        if (agent == null) {
            agent = new CardInfoManagerAgent(context);
        }
        return agent;
    }


    @Override
    public void addCard(CardInfo cardInfo) {

        SPUtil.addCard(context,cardInfo);



    }

    @Override
    public void changeCardInfo(CardInfo cardInfo) {

        SPUtil.changeCardInfo(context,cardInfo);

    }

    @Override
    public void deleteCardInfo(CardInfo cardInfo) {

    }


}
