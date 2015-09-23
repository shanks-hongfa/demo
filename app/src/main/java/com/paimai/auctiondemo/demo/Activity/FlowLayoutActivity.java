package com.paimai.auctiondemo.demo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paimai.auctiondemo.R;
import com.paimai.auctiondemo.demo.Utils.dp2px;

import java.util.Random;

public class FlowLayoutActivity extends AppCompatActivity {

    private Random random = new Random();
    private String src = "1a2b3c4d5e6f7g8h9i10j11k";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ViewGroup) findViewById(R.id.flow)).getChildCount() > 2) {
                    ((ViewGroup) findViewById(R.id.flow)).removeViewAt(random.nextInt(((ViewGroup) findViewById(R.id.flow)).getChildCount()));
                }
            }
        });

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = new TextView(FlowLayoutActivity.this);
                ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.topMargin = dip2px(4);
                t.setLayoutParams(layoutParams);
                t.setPadding(dip2px(4), dip2px(4), dip2px(4), dip2px(4));
                t.setBackgroundResource(R.drawable.select_item_background);
                t.setText("Item " + src.substring(0, random.nextInt(15) + 1));
                ((ViewGroup) findViewById(R.id.flow)).addView(t, random.nextInt(((ViewGroup) findViewById(R.id.flow)).getChildCount()));
            }
        });

        findViewById(R.id.add_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = new TextView(FlowLayoutActivity.this);
                ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.topMargin = dip2px(4);
                t.setLayoutParams(layoutParams);
                t.setPadding(dip2px(4), dip2px(4), dip2px(4), dip2px(4));
                t.setBackgroundResource(R.drawable.select_item_background);
                t.setText("Item " + src.substring(0, random.nextInt(15) + 1));
                ((ViewGroup) findViewById(R.id.flow)).addView(t);
            }
        });
    }

    private int dip2px(int src) {
        return dp2px.dip2px(src);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flow_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
