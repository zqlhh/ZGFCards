package com.westbrook.zgfcards.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.westbrook.zgfcards.CardInfo;
import com.westbrook.zgfcards.R;
import com.westbrook.zgfcards.fragment.AddCardFragment;
import com.westbrook.zgfcards.fragment.ListCardFragment;
import com.westbrook.zgfcards.fragment.ShowCardFragment;
import com.westbrook.zgfcards.impl.FragmentRouterListener;


public class MainActivity extends FragmentActivity {



    private AddCardFragment addCardFragment;

    private ListCardFragment listCardFragment;

    private ShowCardFragment showCardFragment;

    private boolean inMainPage;

    @Override
    public void onBackPressed() {

        if(inMainPage){
            super.onBackPressed();
        }else{
            listener.showCradList();
        }

    }

    private FragmentRouterListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initRouterListener();
        showListCardFragment();
    }


    private void initRouterListener(){

        listener = new FragmentRouterListener() {
            @Override
            public void showCardInfo(CardInfo cardInfo) {
                showShowCardFragment(cardInfo);
            }

            @Override
            public void addCard() {
                showAddFragment();
            }

            @Override
            public void showCradList() {
                showListCardFragment();
            }
        };
    }





    private void showAddFragment(){

        if (addCardFragment == null) {
            addCardFragment = new AddCardFragment();
            addCardFragment.setListener(listener);
        }

        getFragmentTransaction().replace(R.id.main_layout, addCardFragment).commitAllowingStateLoss();

        inMainPage = false;

    }


    private void showListCardFragment(){
        if (listCardFragment == null) {
            listCardFragment = new ListCardFragment();
            listCardFragment.setListener(listener);
        }

        getFragmentTransaction().replace(R.id.main_layout, listCardFragment).commitAllowingStateLoss();
        inMainPage = true;

    }


    private void showShowCardFragment(CardInfo cardInfo){
        if (showCardFragment == null) {
            showCardFragment = new ShowCardFragment();
            showCardFragment.setListener(listener);
        }

        showCardFragment.setCard(cardInfo);

        getFragmentTransaction().replace(R.id.main_layout, showCardFragment).commitAllowingStateLoss();
        inMainPage = false;

    }

    private FragmentTransaction getFragmentTransaction(){

        return getSupportFragmentManager().beginTransaction();
    }

}
