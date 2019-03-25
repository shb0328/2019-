import java.io.*;

public class OutputStreamDo {

	public static void main(String[] args) {
		/* OutputStream: byte stream, OutputStreamWriter char stream */
		
		//OutputStream out = (System.out); //read per byte
		OutputStreamWriter out = new OutputStreamWriter(System.out); //read per char
		
		char out1 = 'A';
		char out2 = '가';
		
		try {
			out.write(out1);
			out.write(out2);
			
			out.flush();
			out.close();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}

}
