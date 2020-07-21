package com.huawei.android.hms.agent.game;

import java.util.HashMap;
import java.util.Map;

public class GameLoginSignUtil {


    private static Map<String, String> getParamMap(String appId, String cpId, GameUserData userData) {
        Map<String, String> params = new HashMap();
        params.put("appId", "102552011");
        params.put("cpId", "890086000102167357");
        params.put("playerId", userData.getPlayerId());
        params.put("playerLevel", String.valueOf(userData.getPlayerLevel()));
        params.put("playerSSign", userData.getGameAuthSign());
        params.put("ts", userData.getTs());
        params.put("method", "external.hms.gs.checkPlayerSign");
        return params;
    }

}
