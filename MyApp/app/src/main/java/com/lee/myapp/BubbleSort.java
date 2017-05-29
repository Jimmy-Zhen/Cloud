package com.lee.myapp;
import com.lee.aspectj.iniTool;

/**
 * Created by 李光旭 on 2017/5/24.
 */
public class BubbleSort {

    public static int[] bubbleSort(int [] numbers){
//         iniTool tools=new iniTool();
//
//        Object flag = tools.getValue("void com.lee.myapp.BubbleSort.bubbleSort(int[], int)","flag");
//        if(flag=="1") {
//            return null;
//        }
        int temp = 0;
        int size = numbers.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++){
                if(numbers[j] < numbers[j + 1]){
                    temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        return numbers;
    }
}
