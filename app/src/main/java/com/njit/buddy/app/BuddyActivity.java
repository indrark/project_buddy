package com.njit.buddy.app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import com.njit.buddy.app.fragment.AttentionFragment;
import com.njit.buddy.app.fragment.MoodFragment;
import com.njit.buddy.app.fragment.MoreFragment;
import com.njit.buddy.app.fragment.NewsFragment;


public class BuddyActivity extends AppCompatActivity implements View.OnClickListener {

    private final int TAB_NEWS = 0x001;
    private final int TAB_ATTENTION = 0x002;
    private final int TAB_MOOD = 0x003;
    private final int TAB_MORE = 0x004;

    private Menu menu;

    private NewsFragment news_fragment;
    private AttentionFragment attention_fragment;
    private MoodFragment mood_fragment;
    private MoreFragment more_fragment;

    private View tab_news_layout;
    private View tab_attention_layout;
    private View tab_mood_layout;
    private View tab_more_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy);

        Connector.initialize();
        initComponents();

        if (savedInstanceState == null) {
            gotoLoginScreen();
        } else {
            String username = savedInstanceState.getString(getResources().getString(R.string.key_username));
            String password = savedInstanceState.getString(getResources().getString(R.string.key_password));
            if (username == null || password == null) {
                gotoLoginScreen();
            } else {
                //store login information
                SharedPreferences preferences =
                        getApplicationContext().getSharedPreferences(getResources().getString(R.string.key_preference), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getResources().getString(R.string.key_username), username);
                editor.putString(getResources().getString(R.string.key_password), password);
                editor.commit();
            }
        }

        setTabSelection(TAB_NEWS);
    }

    @SuppressWarnings("ResourceType")
    private void initComponents() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_buddy_activity);

        //initialize bottom tabs
        tab_news_layout = findViewById(R.id.tab_news_layout);
        tab_attention_layout = findViewById(R.id.tab_attention_layout);
        tab_mood_layout = findViewById(R.id.tab_mood_layout);
        tab_more_layout = findViewById(R.id.tab_more_layout);
        tab_news_layout.setOnClickListener(this);
        tab_attention_layout.setOnClickListener(this);
        tab_mood_layout.setOnClickListener(this);
        tab_more_layout.setOnClickListener(this);
    }

    private void gotoLoginScreen() {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_news_layout:
                setTabSelection(TAB_NEWS);
                break;
            case R.id.tab_attention_layout:
                setTabSelection(TAB_ATTENTION);
                break;
            case R.id.tab_mood_layout:
                setTabSelection(TAB_MOOD);
                break;
            case R.id.tab_more_layout:
                setTabSelection(TAB_MORE);
                break;
        }
    }

    private void setTabSelection(int index) {
        clearTabSelection();
        //begin a new fragment transaction
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //hide all the fragments in case multiple fragments are shown at the same time
        hideFragments(transaction);
        switch (index) {
            case TAB_NEWS:
                //change the news tab background to checked
                tab_news_layout.setBackgroundResource(R.drawable.background_tab_checked);
                //show the news fragment
                if (news_fragment == null) {
                    news_fragment = new NewsFragment();
                    transaction.add(R.id.content, news_fragment);
                } else {
                    transaction.show(news_fragment);
                }
                updateActionBar(getResources().getString(R.string.tab_news), true);
                break;
            case TAB_ATTENTION:
                //change the attention tab background to checked
                tab_attention_layout.setBackgroundResource(R.drawable.background_tab_checked);
                //show the attention fragment
                if (attention_fragment == null) {
                    attention_fragment = new AttentionFragment();
                    transaction.add(R.id.content, attention_fragment);
                } else {
                    transaction.show(attention_fragment);
                }
                updateActionBar(getResources().getString(R.string.tab_attention), false);
                break;
            case TAB_MOOD:
                //change the mood tab background to checked
                tab_mood_layout.setBackgroundResource(R.drawable.background_tab_checked);
                //show the mood fragment
                if (mood_fragment == null) {
                    mood_fragment = new MoodFragment();
                    transaction.add(R.id.content, mood_fragment);
                } else {
                    transaction.show(mood_fragment);
                }
                updateActionBar(getResources().getString(R.string.tab_mood), false);
                break;
            case TAB_MORE:
                //change the more tab background to checked
                tab_more_layout.setBackgroundResource(R.drawable.background_tab_checked);
                //show the more fragment
                if (more_fragment == null) {
                    more_fragment = new MoreFragment();
                    transaction.add(R.id.content, more_fragment);
                } else {
                    transaction.show(more_fragment);
                }
                updateActionBar(getResources().getString(R.string.tab_more), false);
                break;
        }
        transaction.commit();
    }

    private void clearTabSelection() {
        tab_news_layout.setBackgroundResource(R.drawable.background_tab_unchecked);
        tab_attention_layout.setBackgroundResource(R.drawable.background_tab_unchecked);
        tab_mood_layout.setBackgroundResource(R.drawable.background_tab_unchecked);
        tab_more_layout.setBackgroundResource(R.drawable.background_tab_unchecked);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (news_fragment != null) {
            transaction.hide(news_fragment);
        }
        if (attention_fragment != null) {
            transaction.hide(attention_fragment);
        }
        if(mood_fragment != null) {
            transaction.hide(mood_fragment);
        }
        if (more_fragment != null) {
            transaction.hide(more_fragment);
        }
    }

    private void updateActionBar(String title, boolean show_add_btn) {
        ((TextView) findViewById(R.id.abs_title)).setText(title);
        if(show_add_btn) {
            findViewById(R.id.btn_add_post).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.btn_add_post).setVisibility(View.INVISIBLE);
        }
    }

}
