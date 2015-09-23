package com.paimai.auctiondemo.demo.View.RefreshableLayout;

/**
 * Created by moxun on 15/9/9.
 */
public interface IRefreshTriggerListener {
    public void onUpdateDistance(int distance);
    public void onRefreshable();
    public void onRefreshing();
    public void onRefreshComplete();
}
