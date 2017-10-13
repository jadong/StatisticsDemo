package com.jumei.analysis;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.io.File;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by kayo on 17/9/19.
 */
@SuppressWarnings("WeakerAccess")
public class Config {

    public static boolean debug = true;
    public static String MOBILE_TYPE = "";
    public static String MOBILE_BRAND = "";
    public static String MOBILE_IMEI = "";
    public static String MOBILE_IMSI = "";
    public static String MOBILE_NUMBER = "";
    public static String MOBILE_ISROOT = "false";
    public static String OS_VERSION = "";
    private Context context;

    public Config(Context context) {
        this.context = context;
        Config.MOBILE_ISROOT = isRoot();
        getInfo();
    }

    /**
     * 是否root
     * @return
     */
    private String isRoot(){
        String bool = "Root:false";
        try{
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())){
                bool = "Root:false";
            } else {
                bool = "Root:true";
            }
        } catch (Exception e) {
        }
        return bool;
    }

    private void getInfo(){
        TelephonyManager mTm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        String imei = mTm.getDeviceId();
        String imsi = mTm.getSubscriberId();
        String version = android.os.Build.VERSION.RELEASE;//系统版本号
        String mtype = android.os.Build.MODEL; // 手机型号
        String mtyb= android.os.Build.BRAND;//手机品牌
        String numer = mTm.getLine1Number(); // 手机号码，有的可得，有的不可得
        Config.OS_VERSION = version;
        Config.MOBILE_IMEI = imei;
        Config.MOBILE_IMSI = imsi;
        Config.MOBILE_TYPE = mtype;
        Config.MOBILE_BRAND = mtyb;
        Config.MOBILE_NUMBER = numer;

//        String result = "";
//        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        result = wifiInfo.getMacAddress();


        //获取手机cpu信息
//        String str1 = "/proc/cpuinfo";
//        String str2 = "";
//        String[] cpuInfo = {"", ""}; //1-cpu型号 //2-cpu频率
//        String[] arrayOfString;
//        try {
//            FileReader fr = new FileReader(str1);
//            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
//            str2 = localBufferedReader.readLine();
//            arrayOfString = str2.split("\\s+");
//            for (int i = 2; i < arrayOfString.length; i++) {
//                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
//            }
//            str2 = localBufferedReader.readLine();
//            arrayOfString = str2.split("\\s+");
//            cpuInfo[1] += arrayOfString[2];
//            localBufferedReader.close();
//        } catch (IOException e) {
//        }
    }

}
