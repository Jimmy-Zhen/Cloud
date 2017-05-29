package com.lee.aspectj;

import android.telecom.Call;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by J_Zhen on 5/28/2017.
 */

public class Client implements Callable {

    private static String Host="10.50.35.235";
    private static int PORT = 8888;
    private static Socket Server = null;
    private static ObjectInputStream input = null;
    private static ObjectOutputStream output = null;
    private String className;
    private String methodName;
    private int inputNum;
    private Object[] args;
    private Object result = new Object();

    public Client(String c,String m,int i,Object[] a){
        className = c;
        methodName = m;
        inputNum = i;
        args = a;
    }

    @Override
    public Object call(){
        try {
            Server = new Socket(Host, PORT);
            output = new ObjectOutputStream(Server.getOutputStream());
            input = new ObjectInputStream(Server.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
        if(Server.isConnected()){
            if(!Server.isOutputShutdown()){
                try{
                    output.writeObject(className);
                    output.flush();
                    output.writeObject(methodName);
                    output.flush();
                    output.writeObject(inputNum);
                    output.flush();
                    for(int i = 0;i < inputNum; i++){
                        output.writeObject(args[i]);
                        output.flush();
                    }
                    while(Server.isConnected()){
                        if(!Server.isInputShutdown()){
                            result = input.readObject();
                        }
                    }
                    input.close();
                    output.close();
                    Server.close();
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
