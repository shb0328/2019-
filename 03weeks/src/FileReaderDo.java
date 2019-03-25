import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderDo {

	public static void main(String[] args) {
		try {
			FileReader fr = new FileReader("./bin/data/software_read.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String s;
			while((s = br.readLine()) != null) {
				System.out.println(s);
			}
			fr.close();
		}
		catch(IOException ie)
		{
			System.out.println("예외? 이것때문 : " +ie);
		}
	}

}
