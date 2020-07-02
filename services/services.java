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
			//�������������׽��֣��������Կͻ��˵���������
			ServerSocket ss=new ServerSocket(9998);
		
			String message="";
			
			while(true){
				System.out.println("�˿�9998׼������");
				Socket s=ss.accept();
				System.out.println("���ӳɹ�");
				
				//��ȡ�ͻ�������
				InputStream is=s.getInputStream();
				DataInputStream dis=new DataInputStream(is);
				
				String msg=dis.readUTF();//����Ϣ 
			    System.out.println("Client msg = " + msg); 
		
			    s.close();
				
			    if(msg.charAt(1)=='G') {
			    	
			    	s=ss.accept();
			    	OutputStream os=s.getOutputStream();
					DataOutputStream dos=new DataOutputStream(os);
					
					
			    	String dataToClient=readFromfile();
			    	System.out.println("��������" + dataToClient); 
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
				
				//��ȡ�ͻ����׽��ֵ������
				s=ss.accept();
				OutputStream os=s.getOutputStream();
				DataOutputStream dos=new DataOutputStream(os);
				System.out.println("׼��������ͻ���");
				
                		
				String tss="success";
                InputStream ts = new ByteArrayInputStream(tss.getBytes());
               
				InputStreamReader isr=new InputStreamReader(ts);
				BufferedReader br=new BufferedReader(isr);
				message=br.readLine();
				dos.writeUTF(message);
				
				
				System.out.println("������ͻ��˳ɹ�");
				dis.close();
				dos.close();
				os.close();
				ts.close();
				is.close();
				s.close();
			    }
			    else {
			    	System.out.println("״̬��Ϊ����");
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

