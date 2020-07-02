

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
import java.util.ArrayList;
import java.util.Date;

public class services3 {

	/**
	 * @param args
	 */
	//ͼƬ�ļ���
	public static String path="D://service//IMG";
	public static String path2="D://service//login.txt";
	public static void main(String[] args) {
		try {
			//�������������׽��֣��������Կͻ��˵���������
			ServerSocket server=new ServerSocket(10000);
		
			String message="";
			
			while(true) {
				
				System.out.println("�˿�10000�ȴ��ͻ��˷�������");
                Socket socket = server.accept();
                System.out.println("���ӳɹ�");
                
                InputStream is=socket.getInputStream();
				DataInputStream dis=new DataInputStream(is);
				
				String msg=dis.readUTF();//����Ϣ 
			    System.out.println("Client msg = " + msg); 
		
			    socket.close();
                
			    if(msg.length()>=2&&msg.charAt(1)=='I') {
			    	msg=msg+"\n";
			    	writeTofile(msg);
			    	
			    }
			    else if(msg.length()>=2&&msg.charAt(1)=='D') {
			    	socket=server.accept();
			    	OutputStream os=socket.getOutputStream();
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
        
					
					System.out.println("mes:" + message); 
					//message=br.readLine();
					dos.writeUTF(message);
			    	
					socket.close();
			    	os.close();
			    	dos.close();
			    	ts.close();
			    	isr.close();
			    	
			    }
				else {

					socket = server.accept();
					int picIndex = Integer.valueOf(msg).intValue();

					ArrayList Filelist = new ArrayList();
					Filelist = (ArrayList) getFiles(path).clone();

					for (int i = 0; i < Filelist.size(); i++) {
						System.out.println(String.valueOf(i) + ":	" + Filelist.get(i));
					}

					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					FileInputStream fis = new FileInputStream((String) Filelist.get(picIndex));
					int size = fis.available();
					byte[] data = new byte[size];
					fis.read(data);
					dos.writeInt(size);
					dos.write(data);
					dos.flush();
					fis.close();
				}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
		

	}
	
	
	public static ArrayList getFiles(String path) {
		ArrayList res = new ArrayList();
        File file = new File(path);
        // ������·�����ļ���
        if (file.isDirectory()) {
            // ��ȡ·���µ������ļ�
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // ��������ļ��� �ݹ��ȡ������ļ� �ļ���
                if (files[i].isDirectory()) {
                    //System.out.println("Ŀ¼��" + files[i].getPath());
                    getFiles(files[i].getPath());
                } else {
                	res.add(files[i].getPath().toString());
                    //System.out.println("�ļ���" + files[i].getPath());
                }
 
            }
        } else {
            //System.out.println("�ļ���" + file.getPath());
        }
        return res;
    }


	static public void writeTofile(String s) {
		try {
				FileWriter  writer = new FileWriter(path2,true);
      
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
	    
	    File file=new File(path2);

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

