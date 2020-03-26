package com.by5388.demo.slidingpanelayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

/**
 * TODO 20200326 SlidingPaneLayout 主要定义在界面上定义2个View ,设置android:layout_gravity = "start"
 * 和 android:layout_gravity = "end"，通过调用{@link SlidingPaneLayout#openPane()} 或者{@link SlidingPaneLayout#closePane()} }
 * 来切换 页面，并且自带动画效果
 */
public class SlidingPaneLayoutActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private SlidingPaneLayout mSlidingPaneLayout;
    private ListView mListView;
    private TextView mTextViewShow;
    private String mOriginTitle;
    private String mShowTitle;

    private List<String> mStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_sliding_pane_layout);
        mOriginTitle = getTitle().toString();
        mSlidingPaneLayout = findViewById(R.id.slidingPaneLayout);
        mListView = findViewById(R.id.listView);
        mTextViewShow = findViewById(R.id.textView_show);
        for (int i = 0; i < 30; i++) {
            mStrings.add("item" + i);
        }

        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mStrings));
        mListView.setOnItemClickListener(this);
        mSlidingPaneLayout.openPane();

        mSlidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.SimplePanelSlideListener() {
            @Override
            public void onPanelOpened(View panel) {
                super.onPanelOpened(panel);
                setTitle(mOriginTitle);
            }

            @Override
            public void onPanelClosed(View panel) {
                super.onPanelClosed(panel);
                setTitle(mShowTitle);
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String text = (String) mListView.getAdapter().getItem(position);
        mShowTitle = text;
        mTextViewShow.setText(text);
        mSlidingPaneLayout.closePane();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            if (mSlidingPaneLayout.isOpen()) {
                finish();
            } else {
                mSlidingPaneLayout.openPane();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (!mSlidingPaneLayout.isOpen()) {
            mSlidingPaneLayout.openPane();
        } else {
            super.onBackPressed();
        }
    }
}
