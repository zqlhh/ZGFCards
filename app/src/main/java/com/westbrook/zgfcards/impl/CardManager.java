package com.westbrook.zgfcards.impl;

import com.westbrook.zgfcards.CardInfo;

import java.util.List;

public interface CardManager {


    void addCard(CardInfo cardInfo);

    void changeCardInfo(CardInfo cardInfo);

    void deleteCardInfo(CardInfo cardInfo);

    List<CardInfo> getAllCardInfo();

}
