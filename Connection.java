import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection {

    public String Connect(URL url, int oID, Currencies currencies) throws IOException {
        switch(oID){
            case 1:{
                return operation1(url, currencies);
            }
            case 2:{
                return operation2(url, currencies);
            }
            case 3:{
                return operation3(url, currencies);
            }
            default:
                return "Incorrect input";
        }
    }
    private String operation1(URL url, Currencies currencies) throws IOException {
        String str = getData(url);
        char[] chArr = str.toCharArray();
        String avgPrice = "";
        String whereWeAt = "";
        Pattern pattern = Pattern.compile("mid", Pattern.CASE_INSENSITIVE);
        for(char c : chArr){
            Matcher matcher = pattern.matcher(whereWeAt);
            whereWeAt += c;
            if(matcher.find()&&c>='0'&&c<='9'||c=='.'){
                avgPrice += c;
            }
        }
       return "Average exchange rate for "+ currencies.getName() +": "+avgPrice;
    }
    private String operation2(URL url, Currencies currencies) throws IOException {
        String str = getData(url);
        char[] chArr = str.toCharArray();
        int qCounter = 0;
        String[] avgPArr = new String[255];
        String avgPrice = "";
        String whereWeAt = "";
        Pattern pattern = Pattern.compile("mid", Pattern.CASE_INSENSITIVE);
        for(char c : chArr){
            Matcher matcher = pattern.matcher(whereWeAt);
            whereWeAt += c;
            if(matcher.find()&&c>='0'&&c<='9'||c=='.'){
                avgPrice += c;
            } else if(!avgPrice.equals("")){
                avgPArr[qCounter] = avgPrice;
                whereWeAt = "";
                avgPrice = "";
                qCounter++;
            }
        }
        double max = 0;
        double min = Integer.MAX_VALUE;
        for(String s : avgPArr){
            try {
                double check = Double.parseDouble(s);
                if(check>max){
                    max = check;
                }
                if(check<min){
                    min = check;
                }
            }catch (NullPointerException e){
                break;
            }
        }
        return "Maximum exchange rate for "+ currencies.getName() +" in the last "+ qCounter +" days is: "+max+"\n"+
        "Minimum exchange rate for "+ currencies.getName() +" in the last "+ qCounter +" days is: "+min;
    }

    private String operation3(URL url, Currencies currencies) throws IOException {
        String str = getData(url);
        char[] chArr = str.toCharArray();
        int qCounter = 0;
        Map<String, String> map = new HashMap<>();
        String bidPrice = "";
        String bidPrice2 = "";
        String askPrice = "";
        String whereWeAt = "";
        Pattern patternAsk = Pattern.compile("ask", Pattern.CASE_INSENSITIVE);
        Pattern patternBid = Pattern.compile("bid", Pattern.CASE_INSENSITIVE);
        for(char c : chArr){
            Matcher matcherAsk = patternAsk.matcher(whereWeAt);
            Matcher matcherBid = patternBid.matcher(whereWeAt);
            whereWeAt += c;
            if(matcherBid.find()&&(c>='0'&&c<='9'||c=='.')){
                bidPrice += c;
            } else if(!bidPrice.equals("")){
                whereWeAt = "";
                bidPrice2 = bidPrice;
                bidPrice = "";
            }
            if(matcherAsk.find()&&(c>='0'&&c<='9'||c=='.')){
                askPrice += c;
            } else if(!askPrice.equals("")){
                map.put(bidPrice2, askPrice);
                whereWeAt = "";
                askPrice = "";
                qCounter++;
            }
        }
        ArrayList<Double> dif = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String bid = entry.getKey();
            String ask = entry.getValue();
            double bidD = Double.parseDouble(bid);
            double askD = Double.parseDouble(ask);
            dif.add(Math.abs(bidD - askD));
        }
        Collections.sort(dif);
        Collections.reverse(dif);
        return "The biggest difference between ask and buy price for "+ currencies.getName() +" in the last "+ qCounter +" days is: "+dif.get(0);
    }
    private String getData(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        StringBuilder str = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            str.append(scanner.nextLine());
        }
        return str.toString();
    }
}
