package com.westbrook.zgfcards.fragment;

import android.support.v4.app.Fragment;

import com.westbrook.zgfcards.impl.FragmentRouterListener;

public class BaseFragment extends Fragment {

    public FragmentRouterListener listener;

    public void setListener(FragmentRouterListener listener){
        this.listener = listener;
    }





}
