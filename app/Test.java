import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String regex = "(\b(https?|ftp|file)://|www)[-a-z-A-Z0-9+&@#/%?=~_|!:,.;]*[-a-z-A-Z0-9+&@#/%=~_|]";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher("Entra a el https://www.dropbox.com/s/ptcwmp3kbmguf6l/TrabajoSCRUM_V7.xls es una buena página");
		
		if(matcher.find()){
			System.out.println(":o");
			System.out.println(matcher.group());
		}
	}

}
