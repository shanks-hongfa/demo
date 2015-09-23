package com.shanks.urirouter.base;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shanksYao on 9/1/15.
 */
public class Router {


     Response handle(Context context, Uri uri) {
        Response response = new Response();
        String path = uri.getPath();
        path = path == null ? "" : path;
        while (path.contains("//")) {
            path = path.replaceAll("//", "/");
        }
        if (filter(context, uri, path))
            response.code = Response.OK;
        else
            response.code = Response.RENDER_NOT_FOUND;
        return response;
    }

    ///////
    private Map<String, List<Render>> map = new HashMap<>();
    private List<Pair<String, Render>> dirPath = new ArrayList<>();
    private Render defaultRender;

    private boolean filter(Context context, Uri uri, String path) {
        List<Render> renders = map.get(path);
        if (renders != null) {
            for (Render render : renders) {
                if (render.render(context, uri))
                    return true;
            }
        }

        for (Pair<String, Render> pair : dirPath) {
            if (path.startsWith(pair.first)) {
                return pair.second.render(context, uri);
            }
        }

        return defaultRender != null&&defaultRender.render(context, uri);

    }

    /**
     * 全路径匹配
     *
     * @param path
     * @param render
     */
    public void addRouter(String path, Render render) {
        if (map.get(path) == null) {
            map.put(path, new ArrayList<Render>());
        }
        map.get(path).add(render);
    }

    /**
     * 前缀匹配
     * 优先匹配全路径/
     *
     * @param dir
     * @param render
     */
    public void addRouterDir(String dir, Render render) {
        dirPath.add(Pair.create(dir, render));
    }


    public Router(Render defaultRender) {
        this.defaultRender = defaultRender;
    }

    public Router() {
    }

    public interface Render {
        /**
         * @param context
         * @param uri
         * @return false表示不处理/true表示接受处理
         */
        boolean render(Context context, Uri uri);
    }
    public interface Register{
        void init();
    }
}


