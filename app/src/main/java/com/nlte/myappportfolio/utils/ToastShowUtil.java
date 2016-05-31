package com.nlte.myappportfolio.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Function：Toast工具类
 *
 * @author NLTE
 * @date 2016/5/31 0031 20:05
 */
public class ToastShowUtil {
    public static void show(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
