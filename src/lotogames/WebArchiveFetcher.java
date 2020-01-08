package lotogames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;


public class WebArchiveFetcher {
				
	public final String web_page = "http://www.loto49.ro/arhiva-loto49.php";
	
	public ArrayList<String[]> fetchNewLoto649(int last_ck) throws IOException{
		//reads the archive web page and evaluates new rows above last_ck
		//returns new rows, if any, in an ArrayList
		
		ArrayList<String[]> target_data = new ArrayList<String[]>();
		
		String web_page 	= "http://www.loto49.ro/arhiva-loto49.php";					
		Document html_doc 	= Jsoup.connect(this.web_page).get();
		Element tbl 		= html_doc.getElementsByTag("table").get(2);
		Elements rws 		= tbl.select("tr");
				
		for (int i = rws.size() -1; i >last_ck; i--) {			
			Element rw = rws.get(i);
			Elements cols = rw.select("td");						
			
			String rw_data[] = new String[7];//hardcoded size should remain untouched
			
			for (int c = 0; c< cols.size(); c++) {				
				rw_data[c] = cols.get(c).text();				
			}
			target_data.add(rw_data);
			
		}
		return target_data;		
	}
	
	
	public static void main (String...args) throws IOException {	
		//small local tests
		WebArchiveFetcher af = new WebArchiveFetcher();
		ArrayList<String[]> new_games = af.fetchNewLoto649(1900);
		/*
		 * for(int i = 0; i< af.target_data.size();i++)
			System.out.println(i + "=>" + Arrays.toString(af.target_data.get(i)));

		 */		
		for(int i = 0; i< new_games.size();i++)
			System.out.println(i + "=>" + Arrays.toString(new_games.get(i)));
	}
	
}

