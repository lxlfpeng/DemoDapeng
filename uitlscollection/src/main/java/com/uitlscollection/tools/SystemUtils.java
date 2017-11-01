package com.uitlscollection.tools;

import android.os.Build;

/**
 * Author: palmer
 * time: 2017/11/1
 * email:lxlfpeng@163.com
 * desc:获取系统信息工具类
 */

public class SystemUtils {
    /**
     * 是否是ART
     *
     * @return
     */
    public static boolean isART() {
        final String vmVersion = System.getProperty("java.vm.version");
        return vmVersion != null && vmVersion.startsWith("2");
    }

    /**
     * 是否是DALVIK
     *
     * @return
     */
    public static boolean isDalvik() {
        final String vmVersion = System.getProperty("java.vm.version");
        return vmVersion != null && vmVersion.startsWith("1");
    }

    /**
     * 获取设备品牌
     *
     * @return
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备基板名称
     *
     * @return
     */
    public static String getBoard() {
        return Build.BOARD;
    }

    /**
     * 获取手机的型号 设备名称。
     *
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 设备版本号。
     *
     * @return
     */
    public static String getID() {
        return Build.ID;
    }

    /**
     * 获取系统版本字符串。如4.1.2 或2.2 或2.3等
     *
     * @return
     */
    public static String getVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 系统的API级别
     *
     * @return
     */
    public static int getVersionSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 设备的唯一标识。由设备的多个信息拼接合成。
     *
     * @return
     */
    public static String getFingerPrint() {
        return Build.FINGERPRINT;
    }

    /**
     * T整个产品的名称
     *
     * @return
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取设备制造商
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备驱动名称
     *
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * The name of the instruction set (CPU type + ABI convention) of native code, like "armeabi-v7a".
     *
     * @return
     */
    public static String getCpuAbi() {
        return Build.CPU_ABI;
    }

    /**
     * The name of the second instruction set (CPU type + ABI convention) of native code, like "armeabi".
     *
     * @return
     */
    public static String getCpuAbi2() {
        return Build.CPU_ABI2;
    }

    /**
     * 获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
     *
     * @return
     */
    public static String getDisplay() {
        return Build.DISPLAY;
    }
}
