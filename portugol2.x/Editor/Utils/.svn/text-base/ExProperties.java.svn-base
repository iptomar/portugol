import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ExProperties {

	public static void main(String[] args) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File file = new File ("editor.def");
		Properties props = new Properties();
		try {
			fis = new FileInputStream(file);
			props.load(fis);
			fis.close();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
                /*
		props.setProperty("file0", "ficheiro00000");
                props.setProperty("file1", "ficheir011111111111");
                props.setProperty("file2", "ficheir2 2 2 2");
                */
		props.list(System.out);		//imprime o conteudo do objeto no console
		
		try {
			fos = new FileOutputStream(file);
			props.store(fos, "Configuração do editor");
			fos.close();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
}
