package com.example.myapplication2;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.support.v4.app.Fragment;;


public class MainActivity extends AppCompatActivity implements headlines.OnHeadlineSelectedListener {

    @Override
    public void onAttachFragment(Fragment fragment){
        if(fragment instanceof headlines){
            headlines headlinesFragment = (headlines) fragment;
            headlinesFragment.setOnHeadlineSelectedListener(this);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onArticleSelected(int position){
        newsFragment newsFragment = (newsFragment) getSupportFragmentManager().findFragmentById(R.id.news_fragment);
        if (newsFragment !=null){
            newsFragment.updateArticleView(position);
        }else{
            newsFragment newFragment = new newsFragment();
            Bundle args = new Bundle();
            args.putInt(newFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}