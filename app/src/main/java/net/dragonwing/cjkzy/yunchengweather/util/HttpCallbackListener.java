package net.dragonwing.cjkzy.yunchengweather.util;

/**
 * Created by xuncl on 2015/12/4.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
