package com.westbrook.zgfcards;

public class CardInfo{

    public long cardId;

    public int leftNum;

    public String foodName;

    public long nextId;


    @Override
    public boolean equals(Object obj) {
        CardInfo cardInfo = (CardInfo) obj;

        if(this.cardId == cardInfo.cardId){
            return true;
        }

        return false;
    }
}
