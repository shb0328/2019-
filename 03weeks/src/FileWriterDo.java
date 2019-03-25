import java.io.FileWriter;
import java.io.IOException;

public class FileWriterDo {

	public static void main(String[] args) {
		String what1 = new String("안녕? ㅡ,ㅡ\r\n");
		String what2 = new String("자바 네프 듣고 있다!!!");
		
		try
		{
			FileWriter fw = new FileWriter("./bin/data/output/software_write.txt",true);
			fw.write(what1);
			fw.write(what2);
			fw.close();
			System.out.println("Done: check software.txt");
		}
		catch(IOException ie)
		{
			System.out.println(ie);
		}
	}

}
