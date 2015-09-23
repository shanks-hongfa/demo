package com.shanks.urirouter.base;

/**
 * Created by shanksYao on 9/1/15.
 */
public class Response {

    public int code;
    public final static int OK = 0;
    public final static int URL_INVALID = 1;
    public final static int ROUTER_NOT_FOUND = 3;
    public final static int RENDER_NOT_FOUND = 4;

}
