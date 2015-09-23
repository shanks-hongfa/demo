package com.paimai.auctiondemo.demo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.paimai.auctiondemo.R;
import com.taobao.phenix.intf.Phenix;
import com.taobao.uikit.extend.feature.view.TUrlImageView;

public class PhenixActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phenix);
        ImageView img1 = (ImageView) findViewById(R.id.phenix_image);
        TUrlImageView img2 = (TUrlImageView) findViewById(R.id.uikit_image);

        img2.setImageUrl("http://gw1.alicdn.com/bao/uploaded/i1/T17Fh8FxxsXXXXXXXX_!!0-item_pic.jpg");
        img2.setFadeIn(false);

        Phenix.instance()
                .with(this)
                .load("http://img.alicdn.com/bao/uploaded/i3/2089538621/TB2ikxBeFXXXXX9XpXXXXXXXXXX_!!2089538621-0-paimai.jpg")
                .placeholder(R.drawable.default_loading_big_base)
                .error(R.drawable.default_loading_big_error_base)
                .into(img1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_phenix, menu);
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
