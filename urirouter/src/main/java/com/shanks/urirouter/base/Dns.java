package com.shanks.urirouter.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shanksYao on 9/1/15.
 */
public class Dns {

    //////dns 要处理的是taobao.com domain///  再处理host的事情
    ///// 首先白名单应该要处理的是非拍卖的地址////我们要有所区隔   那些就是非拍卖的东西
    /////但是还有拍卖的地址
    ////所有的地址需要通过host 和 path
    ////需要的是前缀匹配
    ////一级path 二级path
    private static Map<String, Router> map = new HashMap<>();

    /**
     * 注册router
     * @param host
     * @param router
     */
    public static void register(String host, Router router) {
        map.put(host, router);
    }
    public static void register(List<String> hosts, Router router) {
        if(hosts==null||hosts.size()==0)
            return;
        for(String host:hosts){
            map.put(host, router);
        }
    }

    /**
     * 查找router
     * @param host
     * @return
     */
    public static Router lookup(String host) {
        return map.get(host);
    }

    ////////////////////////


}
