package com.smssender;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ConversationList extends AppCompatActivity implements SwipeMenuListView.OnSwipeListener {

    private SwipeMenuListView mListView;
    private ConversationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        mListView = (SwipeMenuListView) findViewById(R.id.list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Build the query looking at all users:
        RealmQuery<SmsDataModel> query = SmsSenderApplication.getRealmDb().where(SmsDataModel.class);
        // Execute the query:
        RealmResults<SmsDataModel> result = query.findAll();
        adapter = new ConversationAdapter(this, result);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(getResources().getColor(R.color.unknown_bg_color)));
                openItem.setWidth(getDeviceWidth());
                menu.addMenuItem(openItem);
            }
        };

        // set creator
        mListView.setMenuCreator(creator);
        mListView.setAdapter(adapter);
        mListView.setOnSwipeListener(this);
    }

    private int getDeviceWidth() {
        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        return (int) (displaymetrics.widthPixels * 0.6f);
    }

    @Override
    public void onSwipeStart(int i) {
    }

    @Override
    public void onSwipeEnd(int position) {
        if(position >= 0) {
            SmsDataModel data = adapter.getItem(position);
            if (!FileUtils.contactExists(ConversationList.this, data.getNumber()))
                adapter.deleteItem(position);
            else
                setIsRead(data);
        }
    }

    private void setIsRead(SmsDataModel data) {
        SmsSenderApplication.getRealmDb().beginTransaction();
        RealmResults<SmsDataModel> results = SmsSenderApplication.getRealmDb().
                                            where(SmsDataModel.class).equalTo("id", data.getId()).findAll();
        for(int i=0; i<results.size(); i++) {
            SmsDataModel smsData = results.get(i);
            smsData.setIsRead(true);
        }
        SmsSenderApplication.getRealmDb().commitTransaction();
        adapter.notifyDataSetChanged();
    }
}
