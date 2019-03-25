import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BufferdCharStreamDo {

	public static void main(String[] args) {
		int i, len =0;
		//String crSet = "_ansi";
		String crSet = "_UTF-8";
		
		String srcFile = "./bin/data/base"+crSet+".txt";
		String destFile = "./bin/data/output/output_char"+crSet+".txt";

		System.out.println("Source Name:"+srcFile);
		System.out.println("Target Name:"+destFile);
		
		try
		{
			BufferedReader br = null;
			BufferedWriter bw = null;
			
			br = new BufferedReader(new FileReader(srcFile));
			bw = new BufferedWriter(new FileWriter(destFile));
			
			while((i=br.read())!= -1)
			{
				bw.write(i);
				
				len+=1;
				System.out.println("Read data["+i+","+len+"]");
			}
			br.close();
			bw.close();
			System.out.println("finished");
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		
		
	}

}
