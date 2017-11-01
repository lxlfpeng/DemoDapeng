package com.uitlscollection.tools;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;

/**
 * Author: palmer
 * time: 2017/10/31
 * email:lxlfpeng@163.com
 * desc:
 */

public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * 判断某个文件是否存在
     *
     * @param
     * @return
     */
    public static boolean isFileExists(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型为B、为KB、为MB、为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, String sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 获取文件大小,按规则格式化好
     *
     * @param filePath
     * @return
     */
    public static String getFileOrFilesSizeAuto(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "获取失败!");
        }
        return getFormatSize(blockSize);
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, String sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case "B":
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case "KB":
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case "MB":
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case "GB":
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        } else {
            //  file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 格式化单位
     * 单位B
     *
     * @param size
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 获取文件的SHA1值
     *
     * @param file 目标文件
     * @return 文件的SHA1值
     */
    public static String getSHA1ByFile(File file) {
        if (file == null || !file.exists()) return "文件不存在";
        long time = System.currentTimeMillis();
        InputStream in = null;
        String value = null;
        try {
            in = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            int numRead = 0;
            while (numRead != -1) {
                numRead = in.read(buffer);
                if (numRead > 0) digest.update(buffer, 0, numRead);
            }
            byte[] sha1Bytes = digest.digest();
            String t = new String(buffer);
            value = convertHashToString(sha1Bytes);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                }
        }
        return value;
    }

    /**
     * @param hashBytes
     * @return
     */
    private static String convertHashToString(byte[] hashBytes) {
        String returnVal = "";
        for (int i = 0; i < hashBytes.length; i++) {
            returnVal += Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return returnVal.toLowerCase();
    }

    /**
     * 创建文件
     *
     * @param file 文件
     */
    public static boolean createFile(File file) {
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 创建文件夹
     *
     * @param files 文件夹
     */
    public static boolean createFiles(File files) {
        //通过file的mkdirs()方法创建
        return files.mkdirs();
    }

    /**
     * 删除文件和文件夹
     *
     * @param dir
     * @return
     */
    public static boolean deleteFile(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteFile(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 移动文件
     *
     * @param srcFilePath
     * @param destFilePath
     */
    public static void moveFile(String srcFilePath, String destFilePath) throws FileNotFoundException {
        if (TextUtils.isEmpty(srcFilePath) || TextUtils.isEmpty(destFilePath)) {
            throw new RuntimeException("Both srcFilePath and destFilePath cannot be null.");
        }
        moveFile(new File(srcFilePath), new File(destFilePath));
    }

    /**
     * 移动文件
     *
     * @param srcFile
     * @param destFile
     */
    public static void moveFile(File srcFile, File destFile) throws FileNotFoundException {
        boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
            deleteFile(srcFile);
        }
    }

    /**
     * 复制文件
     *
     * @param srcFilePath
     * @param destFilePath
     * @return
     * @throws FileNotFoundException
     */
    public static boolean copyFile(String srcFilePath, String destFilePath) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(srcFilePath);
        return writeFile(new File(destFilePath), inputStream, false);
    }

    /**
     * 重新命名文件
     *
     * @param file
     * @param newFileName
     * @return
     */
    public static boolean renameFile(File file, String newFileName) {
        File newFile = null;
        if (file.isDirectory()) {
            newFile = new File(file.getParentFile(), newFileName);
        } else {
            String temp = newFileName
                    + file.getName().substring(
                    file.getName().lastIndexOf('.'));
            newFile = new File(file.getParentFile(), temp);
        }
        if (file.renameTo(newFile)) {
            return true;
        }
        return false;
    }

    /**
     * 写入文件
     *
     * @param file
     * @param is
     * @param append
     * @return
     */
    private static boolean writeFile(File file, InputStream is, boolean append) {
        OutputStream o = null;
        try {
            createFile(file);
            o = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = is.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException", e);
        } finally {
            try {
                o.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
