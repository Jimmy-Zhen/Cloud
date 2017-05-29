package com.lee.aspectj;
import android.util.Log;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;

@Aspect
public class maspect {
    private static final String POINT_CALLMETHOD = "execution(* com.lee.myapp..BubbleSort.*(..))";
    private List<Socket> mList = new ArrayList<Socket>();
    private ExecutorService pool = Executors.newCachedThreadPool();  //线程池
    @Pointcut(POINT_CALLMETHOD)
    public void methodCallAnnotated(){}
    @Around("methodCallAnnotated()")
    public Object replacecall(JoinPoint joinPoint) {
//        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
//        Log.e("--------方法信息-----------",joinPoint.getSignature().toString());
//        Object[] args = joinPoint.getArgs();
//        iniTool tools=new iniTool();
//        for(int i=0;i<args.length;i++)
//        {
//            System.out.println(i);
//            Log.e("--------参数名-----------",paramNames[i]);
//            Log.e("--------参数值-----------",args[i].toString());
//            Object o = joinPoint.getTarget();
//            Log.e("--------------------- ",o.toString());
//            try{
//                tools.write("aop.ini");
//            }catch(IOException e){
//                Log.e("-------------------","bigbug");
//                e.printStackTrace();
//            }
//        }
//        tools.setValue(joinPoint.getSignature().toString(),"flag","1");
        Object result = new Object();
        Client client = new Client(joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName(),joinPoint.getArgs().length,joinPoint.getArgs());
        Future future = pool.submit(client);
        try {
            result = future.get();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
