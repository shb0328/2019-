import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedByteStreamDo {

	public static void main(String[] args) {
		int i,len = 0;
		//byte[] buffer = new byte[256];
		
		String sourFile = "./bin/data/base.txt";
		String destFile = "./bin/data/output/output_byte.txt";
		
		System.out.println("Source Name:"+sourFile);
		System.out.println("Target Name:"+destFile);
		
		try
		{
			BufferedInputStream bi = null;
			BufferedOutputStream bo = null;
			
			bi = new BufferedInputStream(new FileInputStream(new File(sourFile)));
			bo = new BufferedOutputStream(new FileOutputStream(destFile),256);
			
			while((i=bi.read())!=-1)
			{
				bo.write(i);
				
				len+=1;
				System.out.println("Read data["+i+","+len+"]");
				System.out.println("Read data["+(char)i+","+len+"]");
			}
			bi.close();
			bo.close();
			System.out.println("finished");
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}

}
