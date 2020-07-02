import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;






public class services {

	/**
	 * @param args
	 */
	public static String path="D://service//1.txt";
	
	public static void main(String[] args) {
		try {
			//创建服务器端套接字，接受来自客户端的连接请求
			ServerSocket ss=new ServerSocket(9998);
		
			String message="";
			
			while(true){
				System.out.println("端口9998准备连接");
				Socket s=ss.accept();
				System.out.println("连接成功");
				
				//获取客户端输入
				InputStream is=s.getInputStream();
				DataInputStream dis=new DataInputStream(is);
				
				String msg=dis.readUTF();//收消息 
			    System.out.println("Client msg = " + msg); 
		
			    s.close();
				
			    if(msg.charAt(1)=='G') {
			    	
			    	s=ss.accept();
			    	OutputStream os=s.getOutputStream();
					DataOutputStream dos=new DataOutputStream(os);
					
					
			    	String dataToClient=readFromfile();
			    	System.out.println("读入数据" + dataToClient); 
	                InputStream ts = new ByteArrayInputStream(dataToClient.getBytes());
	                
					InputStreamReader isr=new InputStreamReader(ts);
					BufferedReader br=new BufferedReader(isr);
					
			
					String lineTxt="";
					message="";
					while((lineTxt = br.readLine()) != null){
						lineTxt+='\n';
						message+=lineTxt;
						lineTxt="";
					}
					//message.trim();
//					message=message.substring(0, message.length()-1);
//					Date date = new Date();
//			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			        String createdate = sdf.format(date);
//			        message=message+"	\""+createdate+"\"";
			        
					
					System.out.println("mes:" + message); 
					//message=br.readLine();
					dos.writeUTF(message);
			    	
			    	s.close();
			    	os.close();
			    	dos.close();
			    	ts.close();
			    	isr.close();
			    }
			    
			    
			    
			    else if(msg.charAt(1)=='S') {
			    
			    	
			    msg=msg.substring(0, msg.length()-1);
				Date date = new Date();
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    String createdate = sdf.format(date);
			    msg=msg+"\"	\""+createdate+"\"";
			    msg+="\n";
			    
			    
			    writeTofile(msg);
				
				//获取客户端套接字的输出流
				s=ss.accept();
				OutputStream os=s.getOutputStream();
				DataOutputStream dos=new DataOutputStream(os);
				System.out.println("准备输出到客户端");
				
                		
				String tss="success";
                InputStream ts = new ByteArrayInputStream(tss.getBytes());
               
				InputStreamReader isr=new InputStreamReader(ts);
				BufferedReader br=new BufferedReader(isr);
				message=br.readLine();
				dos.writeUTF(message);
				
				
				System.out.println("输出到客户端成功");
				dis.close();
				dos.close();
				os.close();
				ts.close();
				is.close();
				s.close();
			    }
			    else {
			    	System.out.println("状态码为其他");
			    }
			    

			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//System.out.println(readFromfile());
		

	}
	
	
	static public void writeTofile(String s) {
		try {
				FileWriter  writer = new FileWriter(path,true);
      
				writer.write(s);
				System.out.println("写入文件成功");
				writer.flush();//刷新内存，将内存中的数据立刻写出。
				writer.close();
				
        } catch (IOException e) {
            	e.printStackTrace();
        
        }
		
		
	}
	
	
	static public String readFromfile()
	{
		
	    String str="";
	    
	    File file=new File("D://service//1.txt");

	    try {

	        FileInputStream in=new FileInputStream(file);

	        // size  为字串的长度 ，这里一次性读完

	        int size=in.available();

	        byte[] buffer=new byte[size];

	        in.read(buffer);

	        in.close();

	        str=new String(buffer,"GB2312");

	    } catch (IOException e) {

	        // TODO Auto-generated catch block

	    	

	        e.printStackTrace();

	    }
	    System.out.println("du入文件成功");
	    return str;

	}

}

