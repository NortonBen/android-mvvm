package com.norton.mvvmbase.factory;

import android.arch.lifecycle.MutableLiveData;
import android.support.v4.app.Fragment;

import java.util.Stack;

public class HistoryFactory {

    private final MutableLiveData<Fragment> activity = new MutableLiveData<>();
    private final Stack<Fragment> histories = new Stack<>();

    public HistoryFactory() {
        activity.observeForever(fragment -> {
            histories.push(fragment);
            if (histories.size() > 5) {
                histories.pop();
            }
        });
    }

    public void to(Fragment fragment){
        activity.setValue(fragment);
    }

    public boolean back() {
        if (histories.size() < 1) {
            return  false;
        }
        histories.pop();
        if (histories.size() < 1) {
            return  false;
        }
        Fragment fragment = histories.pop();
        activity.setValue(fragment);
        return  true;
    }

    public MutableLiveData<Fragment> getActivity() {
        return activity;
    }
}
