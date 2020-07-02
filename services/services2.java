
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
import java.util.Date;
public class services2 {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		try {
			//创建服务器端套接字，接受来自客户端的连接请求
			ServerSocket ss=new ServerSocket(9999);
		
			String message="";
			
			while(true){
                System.out.println("端口9999等待客户端发送图片");
				final Socket s = ss.accept();
                System.out.println("收到图片请求");
                byte[] inputByte = null;
                int length = 0;
                DataInputStream dis = new DataInputStream(s.getInputStream());
                String createtime = (new Date()).getTime() +"";
                String url = "D://service//IMG/"+createtime+".jpg";
                FileOutputStream fos = new FileOutputStream(new File(url));
                inputByte = new byte[1024];
                System.out.println("开始接收数据...");
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    //System.out.println(length);
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
                fos.close();
                System.out.println("完成接收");

			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//System.out.println(readFromfile());
		

	}
	
	
	static public void writeTofile(String s) {
		try {
				FileWriter  writer = new FileWriter("D://service//1.txt",true);
      
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

