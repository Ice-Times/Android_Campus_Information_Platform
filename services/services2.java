
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
			//�������������׽��֣��������Կͻ��˵���������
			ServerSocket ss=new ServerSocket(9999);
		
			String message="";
			
			while(true){
                System.out.println("�˿�9999�ȴ��ͻ��˷���ͼƬ");
				final Socket s = ss.accept();
                System.out.println("�յ�ͼƬ����");
                byte[] inputByte = null;
                int length = 0;
                DataInputStream dis = new DataInputStream(s.getInputStream());
                String createtime = (new Date()).getTime() +"";
                String url = "D://service//IMG/"+createtime+".jpg";
                FileOutputStream fos = new FileOutputStream(new File(url));
                inputByte = new byte[1024];
                System.out.println("��ʼ��������...");
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    //System.out.println(length);
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
                fos.close();
                System.out.println("��ɽ���");

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
				System.out.println("д���ļ��ɹ�");
				writer.flush();//ˢ���ڴ棬���ڴ��е���������д����
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

	        // size  Ϊ�ִ��ĳ��� ������һ���Զ���

	        int size=in.available();

	        byte[] buffer=new byte[size];

	        in.read(buffer);

	        in.close();

	        str=new String(buffer,"GB2312");

	    } catch (IOException e) {

	        // TODO Auto-generated catch block

	    	

	        e.printStackTrace();

	    }
	    System.out.println("du���ļ��ɹ�");
	    return str;

	}

}

