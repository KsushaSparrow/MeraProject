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
    TextView textView = (TextView) page.findViewById(R.id.text_topic1);
    textView.setText("There are such figures:");
    ImageView arrowDown = (ImageView) page.findViewById(R.id.arrowDown);
    arrowDown.setVisibility(View.INVISIBLE);
    ImageView arrowLeft = (ImageView) page.findViewById(R.id.arrowLeft);
    arrowLeft.setVisibility(View.INVISIBLE);
    ImageView beforeReduce = (ImageView) page.findViewById(R.id.beforeReduce);
    beforeReduce.setVisibility(View.INVISIBLE);
    ImageView afterReduce = (ImageView) page.findViewById(R.id.afterReduce);
    afterReduce.setVisibility(View.INVISIBLE);
    ImageView figure8 = (ImageView) page.findViewById(R.id.bonus);
    figure8.setVisibility(View.INVISIBLE);
    TextView textView2 = (TextView) page.findViewById(R.id.description2);
    textView2.setVisibility(View.INVISIBLE);
    pages.add(page);

    page = inflater.inflate(R.layout.screenpager, null);
    textView = (TextView) page.findViewById(R.id.text_topic1);
    textView.setText("Scores:");
    textView = (TextView) page.findViewById(R.id.description1);
    textView.setText("You get 10 points for reducing 1 line");
    ImageView figure1 = (ImageView) page.findViewById(R.id.icon1);
    figure1.setVisibility(View.INVISIBLE);
    ImageView figure2 = (ImageView) page.findViewById(R.id.icon2);
    figure2.setVisibility(View.INVISIBLE);
    ImageView figure3 = (ImageView) page.findViewById(R.id.icon3);
    figure3.setVisibility(View.INVISIBLE);
    ImageView figure4 = (ImageView) page.findViewById(R.id.icon4);
    figure4.setVisibility(View.INVISIBLE);
    ImageView figure5 = (ImageView) page.findViewById(R.id.icon5);
    figure5.setVisibility(View.INVISIBLE);
    ImageView figure6 = (ImageView) page.findViewById(R.id.icon6);
    figure6.setVisibility(View.INVISIBLE);
    ImageView figure7 = (ImageView) page.findViewById(R.id.icon7);
    figure7.setVisibility(View.INVISIBLE);
    figure8 = (ImageView) page.findViewById(R.id.bonus);
    figure8.setVisibility(View.INVISIBLE);
    textView2 = (TextView) page.findViewById(R.id.description2);
    textView2.setVisibility(View.INVISIBLE);
    pages.add(page);

    page = inflater.inflate(R.layout.screenpager, null);
    figure1 = (ImageView) page.findViewById(R.id.icon1);
    figure1.setVisibility(View.INVISIBLE);
    figure2 = (ImageView) page.findViewById(R.id.icon2);
    figure2.setVisibility(View.INVISIBLE);
    figure3 = (ImageView) page.findViewById(R.id.icon3);
    figure3.setVisibility(View.INVISIBLE);
    figure4 = (ImageView) page.findViewById(R.id.icon4);
    figure4.setVisibility(View.INVISIBLE);
    figure5 = (ImageView) page.findViewById(R.id.icon5);
    figure5.setVisibility(View.INVISIBLE);
    figure6 = (ImageView) page.findViewById(R.id.icon6);
    figure6.setVisibility(View.INVISIBLE);
    figure7 = (ImageView) page.findViewById(R.id.icon7);
    figure7.setVisibility(View.INVISIBLE);
    arrowDown = (ImageView) page.findViewById(R.id.arrowDown);
    arrowDown.setVisibility(View.INVISIBLE);
    beforeReduce = (ImageView) page.findViewById(R.id.beforeReduce);
    beforeReduce.setVisibility(View.INVISIBLE);
    afterReduce = (ImageView) page.findViewById(R.id.afterReduce);
    afterReduce.setVisibility(View.INVISIBLE);
    textView = (TextView) page.findViewById(R.id.text_topic1);
    textView.setText("Bonus figure");
    textView2 = (TextView) page.findViewById(R.id.description2);
    textView2.setText("Your scores will be doubled during 15 seconds after reducing a cell of this figure");
    pages.add(page);

    page = inflater.inflate(R.layout.screenpager, null);
    figure1 = (ImageView) page.findViewById(R.id.icon1);
    figure1.setVisibility(View.INVISIBLE);
    figure2 = (ImageView) page.findViewById(R.id.icon2);
    figure2.setVisibility(View.INVISIBLE);
    figure3 = (ImageView) page.findViewById(R.id.icon3);
    figure3.setVisibility(View.INVISIBLE);
    figure4 = (ImageView) page.findViewById(R.id.icon4);
    figure4.setVisibility(View.INVISIBLE);
    figure5 = (ImageView) page.findViewById(R.id.icon5);
    figure5.setVisibility(View.INVISIBLE);
    figure6 = (ImageView) page.findViewById(R.id.icon6);
    figure6.setVisibility(View.INVISIBLE);
    figure7 = (ImageView) page.findViewById(R.id.icon7);
    figure7.setVisibility(View.INVISIBLE);
    arrowDown = (ImageView) page.findViewById(R.id.arrowDown);
    arrowDown.setVisibility(View.INVISIBLE);
    beforeReduce = (ImageView) page.findViewById(R.id.beforeReduce);
    beforeReduce.setVisibility(View.INVISIBLE);
    afterReduce = (ImageView) page.findViewById(R.id.afterReduce);
    afterReduce.setVisibility(View.INVISIBLE);
    textView = (TextView) page.findViewById(R.id.text_topic1);
    textView.setText("Gestures");
    TextView textView1 = (TextView) page.findViewById(R.id.text_view);
    textView1.setText("\n\n\n" + "►Tap on a figure to turn it"+"\n"+"►Swipe right to move a figure to the right"+"\n"+"►Swipe left to move a figure to the left"
            +"\n"+"►Swipe down to speed a figure up");
    ImageView arrowRight = (ImageView) page.findViewById(R.id.arrowRight);
    arrowRight.setVisibility(View.INVISIBLE);
    textView2 = (TextView) page.findViewById(R.id.description2);
    textView2.setVisibility(View.INVISIBLE);
    figure8 = (ImageView) page.findViewById(R.id.bonus);
    figure8.setVisibility(View.INVISIBLE);
    TextView textView3 = (TextView) page.findViewById(R.id.description1);
    textView3.setVisibility(View.INVISIBLE);
    pages.add(page);

    MyPagerAdapter pagerAdapter = new MyPagerAdapter(pages);
    ViewPager viewPager = new ViewPager(this);
    viewPager.setAdapter(pagerAdapter);
    viewPager.setCurrentItem(0);

    setContentView(viewPager);
}
}
