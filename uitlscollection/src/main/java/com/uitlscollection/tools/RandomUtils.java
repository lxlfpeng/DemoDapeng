package com.uitlscollection.tools;

import android.text.TextUtils;

import java.util.Random;

/**
 * Author: palmer
 * time: 2017/11/1
 * email:lxlfpeng@163.com
 * desc:
 */

public class RandomUtils {
    /**
     * 返回伪随机均匀分布int范围内
     *
     * @return
     */
    public static int randomInt() {
        Random random = new Random();
        return random.nextInt();
    }

    /**
     * 返回伪随机均匀分布0-n
     *
     * @param n
     * @return
     */
    public static int randomInt(int n) {
        Random random = new Random();
        return random.nextInt(n);
    }

    /**
     * 返回伪随机均匀分布min-max
     *
     * @param min
     * @param max
     * @return
     */
    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 返回伪随机均匀分布Float范围内
     *
     * @return
     */
    public static float randomFloat() {
        Random random = new Random();
        return random.nextFloat();
    }

    /**
     * 返回伪随机均匀分布Double范围内
     *
     * @return
     */
    public static double randomDouble() {
        Random random = new Random();
        return random.nextDouble();
    }

    /**
     * 返回伪随机均匀分布Long范围内
     *
     * @return
     */
    public static long randomLong() {
        Random random = new Random();
        return random.nextLong();
    }

    /**
     * 回伪随机均匀分布Boolean范围内
     *
     * @return
     */
    public static boolean randomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    /**
     * @return
     */
    public static double randomGaussian() {
        Random random = new Random();
        return random.nextGaussian();
    }

    /**
     * @param buf
     */
    public static void randomBytes(byte[] buf) {
        Random random = new Random();
        random.nextBytes(buf);
    }

    /**
     * 得到一个随机字符串
     *
     * @param source
     * @param length
     * @return
     */
    public static String randomString(String source, int length) {
        return TextUtils.isEmpty(source) ? null : randomString(source.toCharArray(), length);
    }

    /**
     * Get a random string
     * 得到一个随机字符串
     *
     * @param sourceChar
     * @param length
     * @return
     */
    public static String randomString(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(sourceChar[randomInt(sourceChar.length)]);
        }
        return builder.toString();
    }

    /**
     * Shuffling a int array
     * 重新排序数组
     *
     * @param intArray
     * @return
     */
    public static int[] shuffle(int[] intArray) {
        if (intArray == null) {
            return null;
        }
        return shuffle(intArray, intArray.length);
    }

    /**
     * Shuffling a int array
     * 重新排序数组
     *
     * @param intArray
     * @param shuffleCount
     * @return
     */
    public static int[] shuffle(int[] intArray, int shuffleCount) {
        int length;
        if (intArray == null || shuffleCount < 0 || (length = intArray.length) < shuffleCount) {
            return null;
        }

        int[] out = new int[shuffleCount];
        for (int i = 1; i <= shuffleCount; i++) {
            int random = randomInt(length - i);
            out[i - 1] = intArray[random];
            int temp = intArray[length - i];
            intArray[length - i] = intArray[random];
            intArray[random] = temp;
        }
        return out;
    }
}
