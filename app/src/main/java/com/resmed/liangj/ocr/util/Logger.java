package com.resmed.liangj.ocr.util;

import java.util.List;

/**
 * Created by LiangJ on 12/03/2018.
 */

public class Logger {
    public static final String LOGTAG = "app_log";

    public static String getPermissionString(List<String> perms) {
        String data = "";
        if (perms != null && perms.size() > 0) {
            StringBuffer permsString = new StringBuffer();
            for (String permission : perms) {
                permsString.append(permission + "\n");
            }
            data = permsString.toString();
        }
        return data;
    }
}
