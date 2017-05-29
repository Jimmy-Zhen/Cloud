package com.lee.aspectj;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class iniTool {

    static Map<String, Map<String, Object>> iniFile = new HashMap<String, Map<String, Object>>();


    public  void write(String name) throws IOException {
        StringBuilder sb = new StringBuilder("");
        for (String section : iniFile.keySet()) {
            sb.append("[").append(section).append("]").append("\n");
            Map<String, Object> map = iniFile.get(section);
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                sb.append(key).append("=").append(map.get(key)).append("\n");
            }
        }
        String fname= Environment.getExternalStorageDirectory().getAbsolutePath();

        File file = new File(fname,"/"+name);
        if (!file.exists()) {
            try {
                file.createNewFile();
                Log.e("_____________________","create");
                /*OutputStream os = new FileOutputStream(file);
                os.write(sb.toString().getBytes());
                os.flush();
                os.close();*/
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("_____________________","notcreate");
            }
        }
        else{
            Log.e("__________________","find it");
        }
    }

    public  void setValue(String section, String key, Object value) {
        Map<String, Object> sectionMap = iniFile.get(section);
        if (sectionMap == null) {
            sectionMap = new HashMap<String, Object>();
            iniFile.put(section, sectionMap);
        }
        sectionMap.put(key, value);
    }

    public Object getValue(String section, String key) {
        Object obj = new Object();
        Map<String, Object> item = iniFile.get(section);
        if (item != null) {
            obj = item.get(key);
        }
        return obj;

    }
}