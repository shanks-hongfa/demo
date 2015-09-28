package com.shanks.auctiondemo.demo.View.RefreshableLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.paimai.auctiondemo.R;

/**
 * Created by moxun on 15/9/9.
 */
public class HeaderFactory implements IRefreshTriggerListener{

    private View self;
    private TextView tip;
    private Context context;

    public View createHeaderView(Context context, ViewGroup rootView) {
        this.context = context;
        self = LayoutInflater.from(context).inflate(R.layout.refresh_header, rootView, false);
        tip = (TextView) self.findViewById(R.id.header_text);
        return self;
    }

    @Override
    public void onUpdateDistance(int distance) {
        tip.setText("下拉距离 " + distance + " px");
    }

    @Override
    public void onRefreshable() {
        tip.setText("释放进行刷新");
    }

    @Override
    public void onRefreshing() {
        tip.setText("刷新中……");
    }

    @Override
    public void onRefreshComplete() {
        tip.setText("刷新完成");
        Toast.makeText(context,"刷新完成",Toast.LENGTH_SHORT).show();
    }
}
