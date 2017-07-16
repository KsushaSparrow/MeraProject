package com.example.ksusha.tetris;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RulesActivity extends AppCompatActivity { @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    LayoutInflater inflater = LayoutInflater.from(this);
    List<View> pages = new ArrayList<>();

    View page = inflater.inflate(R.layout.screenpager, null);
    TextView textView = (TextView) page.findViewById(R.id.text_view);
    textView.setText("There are such figures:");
    pages.add(page);

    page = inflater.inflate(R.layout.screenpager, null);
    textView = (TextView) page.findViewById(R.id.text_view);
    textView.setText("Page 2");
    ImageView figure1 = (ImageView) page.findViewById(R.id.icon1);
    figure1.setVisibility(View.INVISIBLE);
    ImageView figure2 = (ImageView) page.findViewById(R.id.icon2);
    figure2.setVisibility(View.INVISIBLE);
    ImageView figure3 = (ImageView) page.findViewById(R.id.icon3);
    figure3.setVisibility(View.INVISIBLE);
    pages.add(page);

    page = inflater.inflate(R.layout.screenpager, null);
    textView = (TextView) page.findViewById(R.id.text_view);
    textView.setText("Страница 3");
    pages.add(page);

    MyPagerAdapter pagerAdapter = new MyPagerAdapter(pages);
    ViewPager viewPager = new ViewPager(this);
    viewPager.setAdapter(pagerAdapter);
    viewPager.setCurrentItem(0);

    setContentView(viewPager);
}
}
