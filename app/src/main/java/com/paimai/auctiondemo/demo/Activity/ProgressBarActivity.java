package com.paimai.auctiondemo.demo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.paimai.auctiondemo.R;
import com.paimai.auctiondemo.demo.View.AuctionProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProgressBarActivity extends AppCompatActivity {

    private List<Integer> keys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        final AuctionProgressBar progressBar = (AuctionProgressBar) findViewById(R.id.progress_bar);
        findViewById(R.id.add_focus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edit_add);
                int key = Integer.valueOf(editText.getText().toString());
                if (key >=0 && key <= 99) {
                    progressBar.addFocusByNum(key);
                    keys.add(key);
                    editText.setText(new Random().nextInt(100)+"");
                }
            }
        });

        findViewById(R.id.remove_focus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keys.size() != 0) {
                    progressBar.removeFocusByNum(keys.get(keys.size() - 1));
                    keys.remove(keys.size() - 1);
                }
            }
        });

        findViewById(R.id.progress_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edit_progress);
                int progress = Integer.valueOf(editText.getText().toString());
                if (progress >= 0 && progress <= 100) {
                    progressBar.setCurrentLots(progress);
                    editText.setText(new Random().nextInt(100)+"");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_progress_bar, menu);
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
