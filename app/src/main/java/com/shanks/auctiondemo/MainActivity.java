package com.shanks.auctiondemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paimai.auctiondemo.R;
import com.shanks.auctiondemo.demo.Activity.FlowLayoutActivity;
import com.shanks.auctiondemo.demo.Activity.PhenixActivity;
import com.shanks.auctiondemo.demo.Activity.ProgressBarActivity;
import com.shanks.auctiondemo.demo.Activity.RefreshableLayoutActivity;
import com.shanks.auctiondemo.demo.Activity.TouchSystemTestActivity;
import com.shanks.auctiondemo.demo.Activity.UIKitTestActivity;
import com.shanks.auctiondemo.demo.Utils.dp2px;
import com.shanks.auctiondemo.demo.View.RefreshableLayout.RefreshableLayout;


public class MainActivity extends Activity {

    private ViewGroup rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = (ViewGroup) findViewById(R.id.content);
        addItem();
        dp2px.setContext(getApplicationContext());
        //initDir();
        //copyRNAssets();
    }

    private void addItem() {
        add("TouchSystemTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TouchSystemTestActivity.class));
            }
        });

        add("ProgressBar", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , ProgressBarActivity.class));
            }
        });

        add("FlowLayout", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FlowLayoutActivity.class));
            }
        });

        add(RefreshableLayout.class, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RefreshableLayoutActivity.class));
            }
        });

        add(UIKitTestActivity.class, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UIKitTestActivity.class));
            }
        });

        add(PhenixActivity.class, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhenixActivity.class));
            }
        });
        add("test", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriManager.open(MainActivity.this, "shanks://shanks.uri/mylibrary/test");
            }
        });
        add("main", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriManager.open(MainActivity.this, "shanks://shanks.uri/mylibrary/main");
            }
        });
      /*  add("ReactNative HelloWorld", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RNMainActivity.class));
            }
        });
        add("ReactNative Controller Panel", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RNControllerActivity.class));
            }
        });*/
        add("main-fakes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriManager.open(MainActivity.this, "shanks://shanks.uri/fakes/main");
            }
        });
    }

    private void add(String name,View.OnClickListener listener) {
        TextView textView = generateItem(name);
        textView.setOnClickListener(listener);
        addView(textView);
    }

    private void add(Class clazz,View.OnClickListener listener) {
        TextView textView = generateItem(clazz.getSimpleName());
        textView.setOnClickListener(listener);
        addView(textView);
    }

    private TextView generateItem(String text) {
        TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.demo_item, rootView,false);
        textView.setText(text);
        return textView;
    }

    private void addView(View view) {
        rootView.addView(view);
    }
}
