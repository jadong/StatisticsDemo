package com.jumei.analysis;

import java.util.Map;

/**
 * Created by kayo on 17/9/18.
 * 数据发射  用于向服务器发送数据
 */
public class Sender {

    /**
     * 请求服务器 发送数据
     * @param params
     * @return
     */
    public int send(Map<String,String> params){
        TrackerLogger.getLogger().i("Sender",QUtils.mapMoveNull(params));
        return 1;
    }
}
