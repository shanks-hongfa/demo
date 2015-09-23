package com.paimai.auctiondemo.demo.Activity;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paimai.auctiondemo.R;
import com.paimai.auctiondemo.demo.Utils.LogUtil;
import com.paimai.auctiondemo.demo.View.RefreshableLayout.HeaderFactory;
import com.paimai.auctiondemo.demo.View.RefreshableLayout.IRefreshListener;
import com.paimai.auctiondemo.demo.View.RefreshableLayout.RefreshableLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RefreshableLayoutActivity extends AppCompatActivity implements IRefreshListener{

    RefreshableLayout layout;
    AdapterImpl adapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshable_layout);
        layout = (RefreshableLayout) findViewById(R.id.refresh_container);
        HeaderFactory factory = new HeaderFactory();
        View headerView = factory.createHeaderView(this, layout);
        layout.setHeaderView(headerView);
        layout.setRefreshTriggerListener(factory);
        layout.setRefreshListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.refresh_content);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AdapterImpl();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_refreshable_layout, menu);
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

    @Override
    public void onRefresh() {
        adapter.reload();
    }

    @Override
    public void onFinish() {
        adapter.inflate();
        linearLayoutManager.scrollToPosition(0);
    }

    private class AdapterImpl extends RecyclerView.Adapter {

        private List<Integer> dataSet = new ArrayList<>();

        public void reload() {
            SystemClock.sleep(1500);
        }

        public void inflate() {
            dataSet.add(0, 100);
            notifyItemInserted(0);

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.refresh_content_item,viewGroup,false);
            return new ItemHolder(item);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ((ItemHolder)viewHolder).textView.setText("item " + new Random().nextInt(10000));
        }

        @Override
        public int getItemCount() {
            return dataSet.size() + 3;
        }

        class ItemHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public ItemHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.refresh_content_text);
                itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Log.e("consume event","item in recyclerView receive a " + LogUtil.Event.event.get(event.getAction()));
                        return true;
                    }
                });
            }
        }
    }

}
