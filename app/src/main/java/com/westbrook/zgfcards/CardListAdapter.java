package com.westbrook.zgfcards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.westbrook.zgfcards.impl.FragmentRouterListener;

import java.util.List;

public class CardListAdapter extends BaseAdapter {


    private Context context;
    private List<CardInfo> cardInfos;
    private FragmentRouterListener listener;

    public CardListAdapter(Context context, List<CardInfo> cardInfos, FragmentRouterListener listener){
        this.context = context;
        this.cardInfos = cardInfos;
        this.listener = listener;
    }

    public void setDatas(List<CardInfo> cardInfos){
        this.cardInfos = cardInfos;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cardInfos.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return cardInfos.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.list_item_layout,parent,false);

        TextView textView = (TextView)contentView.findViewById(R.id.item_info_text);

        final CardInfo cardInfo = cardInfos.get(position);

        textView.setText(cardInfo.foodName +"  "+cardInfo.leftNum+"次");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showCardInfo(cardInfo);
            }
        });

        return contentView;

    }
}