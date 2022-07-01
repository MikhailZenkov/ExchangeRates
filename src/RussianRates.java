import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class RussianRates {

    private HashMap<String, String> Rates; //Data will be stored in format: Currency Price

    RussianRates(String website) throws IOException {
        Document doc = Jsoup.connect(website).get();
        Elements el = doc.getElementsByClass("data"); //Reading the table of the website
        Rates = creationListRates(el);
    }

    public HashMap<String, String> creationListRates(Elements el){

        String htmlCode = String.valueOf(el.get(0));
        String[] Strings = htmlCode.split("\n");

        /*
        The index of the first required element is 13
        Required elements are found every 7 steps
         */
        HashMap<String, String> resultList = new HashMap<>();
        for(int i = 13; i < Strings.length; i+=7){
            resultList.put(Strings[i].replaceAll("[<td>/]", "").trim(), Strings[i+1].replaceAll("[<td>/ ]", ""));
        }
        return resultList;
    }

    public HashMap<String, String> getRates() {
        return Rates;
    }
}

class Test{
    public static void main(String[] args) throws IOException {
        RussianRates rates = new RussianRates("https://cbr.ru/currency_base/daily/");
        HashMap<String, String> ratesList = rates.getRates();
        for(Map.Entry<String, String> entry: ratesList.entrySet())
            System.out.println(entry);
    }
}
