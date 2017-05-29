package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.myServer.Service;

public class myServer {

	
	private static final int PORT = 8888;
	private List<Socket> mList = new ArrayList<Socket>();
	private ServerSocket server = null;
	private ExecutorService mExecutorService = null;   //线程池
	
	public static void main(String[] args){
		new myServer();
	}
	
	public myServer(){
		try {
            server = new ServerSocket(PORT);
            mExecutorService = Executors.newCachedThreadPool();  //create a thread pool
            System.out.println("服务器已启动...");
            Socket client = null;
            while(true) {
                client = server.accept();
                //把客户端放入客户端集合中
                mList.add(client);
                mExecutorService.execute(new Service(client)); //start a new thread to handle the connection
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	class Service implements Runnable{
		private Socket socket;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		private String msg="";
		
		public Service(Socket socket){
			this.socket = socket;
			try{
				msg = "服务器地址：" +this.socket.getInetAddress() + " come toal:" + mList.size()+"（服务器发送）";
				this.sendmsg();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				input = new ObjectInputStream(socket.getInputStream());
				output = new ObjectOutputStream(socket.getOutputStream());
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	
		@Override
		public void run(){
			try{
				String className;
				String methodName;
				int inputNum;
				className = (String) input.readObject();
				methodName = (String) input.readObject();
				inputNum = (int) input.readObject();
				Object[] args = new Object[inputNum];
				for(int i=0; i<inputNum;i++){
					args[i]=input.readObject();
				}
				Object result = new Object();
				result = Offload(className,methodName,inputNum,args);
				output.writeObject(result);
				output.flush();
				mList.remove(socket);
				input.close();
				output.close();
				socket.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public Object Offload(String className, String methodName,int inputNum, Object[] args){
			Object result = new Object();
			try{
				Class c = Class.forName(className);
				Class[] cc = new Class[inputNum];
				for(int i=0;i<inputNum;i++){
					cc[i]=args[i].getClass();
				}
				Method method = c.getMethod(methodName, cc);
				result = method.invoke(c.newInstance(),args);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}
		
		public void sendmsg(){
			System.out.println(msg);
		}
		
	}
	
}
