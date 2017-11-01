package com.uitlscollection.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Author: palmer
 * time: 2017/10/31
 * email:lxlfpeng@163.com
 * desc:
 */

public class AssetsUtils {
    /**
     * 读取资源文件里面的json数据
     *
     * @param context
     * @param jsonFileName
     * @return
     */
    public static JSONObject getJsonFromAssets(Context context, String jsonFileName) {
        JSONObject testjson = new JSONObject();
        try {
            InputStreamReader isr = new InputStreamReader(context.getAssets().open(jsonFileName), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            isr.close();
            //  String testJson = builder.toString();
            testjson = new JSONObject(builder.toString());//builder读取了JSON中的数据。
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testjson;
    }

    /**
     * 从assets目录中复制单个文件到SD卡中
     *
     * @param context        Context 使用CopyFiles类的Activity
     * @param assetsFileName String  名称
     * @param sdFilePath     String  复制到的sd路径（目录）  如：Environment.getExternalStorageDirectory()
     */
    public static boolean copyFileFromAssetsToSD(Context context, String assetsFileName, String sdFilePath) {
        try {
            InputStream stream = context.getResources().getAssets().open(assetsFileName);

            File file = new File(sdFilePath);

            OutputStream o = null;
            try {
                //创建父目录
                String parentPath = file.getAbsolutePath();
                String getFolderName = "";
                if (parentPath == null || parentPath.length() == 0) {
                    getFolderName = parentPath;
                } else {
                    int filePosi = parentPath.lastIndexOf(File.separator);
                    getFolderName = (filePosi == -1) ? "" : parentPath.substring(0, filePosi);
                }

                Boolean makeDirs = false;
                if (getFolderName == null || getFolderName.length() == 0) {
                    makeDirs = false;
                } else {
                    File folder = new File(getFolderName);
                    makeDirs = (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
                }
                if (makeDirs) {
                    o = new FileOutputStream(file, false);
                    byte data[] = new byte[1024];
                    int length = -1;
                    while ((length = stream.read(data)) != -1) {
                        o.write(data, 0, length);
                    }
                    o.flush();
                    return true;
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException occurred. ", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                if (o != null) {
                    try {
                        o.close();
                        stream.close();
                    } catch (IOException e) {
                        throw new RuntimeException("IOException occurred. ", e);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred. ", e);
        }
        return false;
    }

    /**
     * 获取assets目录下的图片资源的bitmap对象
     *
     * @param assetsImgPath - 文件的相对路径，例如：image.png或者www/img/image.png
     *                      使用方式：id_imageview.setImageBitmap(bitmap);
     */
    public static Bitmap getImageBitmapFromAssetsFile(Context mContext, String assetsImgPath) {
        Bitmap bitmap = null;
        try {
            InputStream is = mContext.getResources().getAssets().open(assetsImgPath);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 从res中的raw文件夹中获取文件并读取数据（资源文件只能读不能写）
     *
     * @param rawFileId - 文件的ID值：R.raw.xxx；例如：R.raw.rawtext
     * @return 文件中的内容字符串
     */
    /*
     * 首先调用Context.getResource获得当前应用程序上下文的Resources引用.
	 * 然后调用openRawResource(int id)得到InputStream.
	 * 最后，操作InputStream得到数据。
	 * 注意：把文件放在res/raw目录下，则R类会自动提供该id.*/
    public static String getStringFromRaw(Context mContext, int rawFileId) {
        String res = "";
        try {
            InputStream in = mContext.getResources().openRawResource(rawFileId);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            //res = EncodingUtils.getString(buffer, "GBK");
            res = new String(buffer, "UTF-8");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 写入（./data/data/包名/file）文件里面内容
     *
     * @param file    - 私有文件夹下的文件名，例如：datatext.txt【在/data/data/{package}/目录下】
     * @param message - 想要写入的数据字符串
     */
    public static void writeFileToData(Context mContext, String file, String message) {
        try {
			/*
             * MODE_APPEND 追加模式 - 如果已经存在的文件，将数据写入到现有文件的末尾而不是抹去它。
             * MODE_PRIVATE 私有模式 - 只有本程序或包名相同的程序才能访问
             * MODE_WORLD_READABLE 读取模式 - 其他程序可以读取此文件
             * MODE_WORLD_WRITEABLE - 写入模式 其他程序可以修改此文件
             * 除了追加模式其他模式都会将内容全部覆盖
             */
            FileOutputStream fout = mContext.openFileOutput(file, Context.MODE_PRIVATE);
            byte[] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取./data/data/包名/file/下面的文件内容
     *
     * @param fileName - 私有文件夹下的文件名，例如：datatext.txt
     * @return
     */
    public static String getStringFileFromData(Context mContext, String fileName) {
        String res = "";
        try {
            FileInputStream fin = mContext.openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            //res = EncodingUtils.getString(buffer, "UTF-8");
            res = new String(buffer, "UTF-8");
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
