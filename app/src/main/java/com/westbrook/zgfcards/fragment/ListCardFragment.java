package com.westbrook.zgfcards.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.westbrook.zgfcards.CardInfo;
import com.westbrook.zgfcards.CardInfoManagerAgent;
import com.westbrook.zgfcards.CardListAdapter;
import com.westbrook.zgfcards.R;
import com.westbrook.zgfcards.impl.CardManager;

import java.util.List;

public class ListCardFragment extends BaseFragment {


    private TextView addText;

    private ListView cardListView;

    private List<CardInfo> dataList;

    private CardManager cardManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.list_card_fragment,container,false);

        addText = (TextView) contentView.findViewById(R.id.add_text);

        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addCard();
            }
        });


        cardListView = (ListView)contentView.findViewById(R.id.card_list);

        cardListView.setAdapter(new CardListAdapter(getContext(),dataList,listener));

        return contentView;
    }



    private void initData(){

        if(cardManager == null){
            cardManager = CardInfoManagerAgent.getCardInfoManagetAgent(getContext());
        }

        dataList = cardManager.getAllCardInfo();
    }

}
