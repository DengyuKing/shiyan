package coop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class RandWfile {
	
	public static void readfile(String fileName,ArrayList<Node> list){
		System.out.println("开始读文件...");
		File file = new File(fileName);
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
			String tmpString =null;
			
			int line =1;
			while ((tmpString = reader.readLine())!= null){
				System.out.println("node"+line +":"+tmpString);
				String [] str =tmpString.split(" ");
				float [] data =new float[str.length];
				for (int i=0;i<str.length;++i)
					data[i]=Float.parseFloat(str[i]);
				Node node = new Node(data);
				list.add(node);
				++line;
			}
		System.out.println("节点初始化完成...");
			
			
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			if (reader != null){
				try{
					reader.close();
				}catch(IOException e1){
					
				}
			}
		}
		
	}
	public static void writefile(String filename, String data,boolean flag){
		System.out.println("开始写文件...");
		
		
		try{
			File file = new File(filename);
			if (!file.exists()){
				 file.createNewFile();
			}
			BufferedWriter writer = null;
			if (flag)
				writer = new BufferedWriter(new FileWriter(file,flag));
			else
				writer = new BufferedWriter(new FileWriter(file));
			writer.write(data);
			writer.close();
			
			
		System.out.println("写文件完成...");
			
			
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			
		}
		
	}

}
