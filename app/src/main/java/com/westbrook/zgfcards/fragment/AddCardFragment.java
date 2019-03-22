package com.westbrook.zgfcards.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.westbrook.zgfcards.CardInfo;
import com.westbrook.zgfcards.CardInfoManagerAgent;
import com.westbrook.zgfcards.R;
import com.westbrook.zgfcards.impl.CardManager;

public class AddCardFragment extends BaseFragment {



    public AddCardFragment(){}

    EditText numEdit;

    EditText foodNameEdit;

    EditText leftNumEdit;

    TextView confirmText;

    CardInfo cardInfo;

    private CardManager cardManager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.add_card_fragment,container,false);

        numEdit = (EditText)contentView.findViewById(R.id.num_edit);

        foodNameEdit = (EditText) contentView.findViewById(R.id.food_edit);

        leftNumEdit = (EditText)contentView.findViewById(R.id.left_num_edit);

        confirmText = (TextView) contentView.findViewById(R.id.confirm_add_text);

        confirmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCard();
            }
        });

        return contentView;
    }



    private void addCard(){


        cardInfo = new CardInfo();

        cardInfo.cardId = Long.valueOf(numEdit.getText().toString());

        cardInfo.foodName = foodNameEdit.getText().toString();

        cardInfo.leftNum = Integer.valueOf(leftNumEdit.getText().toString());

        cardInfo.nextId = 0;

        CardInfoManagerAgent.getCardInfoManagetAgent(getContext()).addCard(cardInfo);

        reSetEdit();

        listener.showCradList();

    }

    private void reSetEdit(){

        numEdit.setText("");
        foodNameEdit.setText("");
        leftNumEdit.setText("");

    }



}
