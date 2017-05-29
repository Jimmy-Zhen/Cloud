package com.j_zhen.dynamicblur;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by J_Zhen on 5/28/2017.
 */

public class BlurUtils {
    /**
     * ͼƬ���ű���
     */
    private static final float SCALE_DEGREE = 0.4f;
    /**
     * ���ģ���ȣ���0.0��25.0֮�䣩
     */
    private static final float BLUR_RADIUS = 25f;

    /**
     * ģ��ͼƬ
     * @param context   ������
     * @param bitmap    ��Ҫģ����ͼƬ
     * @return          ģ��������ͼƬ
     */
    public static Bitmap blur(Context context, Bitmap bitmap) {
        //����ͼƬ��С�ĳ���
        int width = Math.round(bitmap.getWidth() * SCALE_DEGREE);
        int height = Math.round(bitmap.getHeight() * SCALE_DEGREE);

        //����С���ͼƬ��ΪԤ��Ⱦ��ͼƬ
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        //����һ����Ⱦ�������ͼƬ
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        //����RenderScript�ں˶���
        RenderScript renderScript = RenderScript.create(context);
        //����һ��ģ��Ч����RenderScript�Ĺ��߶���
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));

        /**
         * ����RenderScript��û��ʹ��VM�������ڴ�,������Ҫʹ��Allocation���������ͷ����ڴ�ռ䡣
         * ����Allocation�����ʱ����ʵ�ڴ��ǿյ�,��Ҫʹ��copyTo()����������ȥ��
         */
        Allocation inputAllocation = Allocation.createFromBitmap(renderScript, inputBitmap);
        Allocation outputAllocation = Allocation.createFromBitmap(renderScript, outputBitmap);

        //������Ⱦ��ģ���̶ȣ�25f�����ģ����
        scriptIntrinsicBlur.setRadius(BLUR_RADIUS);
        //����ScriptIntrinsicBlur����������ڴ�
        scriptIntrinsicBlur.setInput(inputAllocation);
        //��ScriptIntrinsicBlur������ݱ��浽����ڴ���
        scriptIntrinsicBlur.forEach(outputAllocation);

        //��������䵽Allocation��
        outputAllocation.copyTo(outputBitmap);

        return outputBitmap;
    }
}