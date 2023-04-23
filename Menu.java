import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Menu{

    private Scanner scanner = new Scanner(System.in);

    public String get(String ref) throws IOException, ParseException {
        Pattern patternList = Pattern.compile("list", Pattern.CASE_INSENSITIVE);
        Pattern patternExchange = Pattern.compile("exchange", Pattern.CASE_INSENSITIVE);
        Pattern patternMinAndMax = Pattern.compile("minmax", Pattern.CASE_INSENSITIVE);
        Pattern patternDifference = Pattern.compile("diff", Pattern.CASE_INSENSITIVE);
        Matcher matcherList = patternList.matcher(ref);
        Matcher matcherExchange = patternExchange.matcher(ref);
        Matcher matcherMinAndMax = patternMinAndMax.matcher(ref);
        Matcher matcherDifference = patternDifference.matcher(ref);
        if(matcherList.find()){
            return getList(ref);
        }
        if(matcherExchange.find()){
            return operation1(ref);
        }
        if(matcherMinAndMax.find()){
            return operation23('a', ref);
        }
        if(matcherDifference.find()){
            return operation23('c', ref);
        }
        return "Incorrect input";
    }
    private String operation1(String ref) throws ParseException {
        String code = getCode(ref);
        if(code.equals("Incorrect currency code"))
            return "Incorrect currency code";
        code = code.toUpperCase();
        String date = getDate(ref);
        if(!date.equals("Incorrect date")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            Date pDate = sdf.parse(date);
            if(pDate.compareTo(today)<=0) {
                try {
                    if(sdf.format(today).equals(sdf.format(pDate))){
                        date = "today";
                    }
                    Connection con = new Connection();
                    URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + date + "/");
                    return con.Connect(url, 1, Currencies.valueOf(code));
                } catch (MalformedURLException e) {
                    return "Incorrect input";
                } catch (IOException e) {

                }
                return "Input date is weekend or holiday";
            }
        }
        return "Incorrect date";
    }
    private String operation23(char table, String ref) throws IOException {
        String code = getCode(ref);
        if(code.equals("Incorrect currency code"))
            return "Incorrect currency code";
        String number = getNumberOfQ(ref, code);
        code = code.toUpperCase();
        int numberQ = -1;
        try {
            numberQ = Integer.parseInt(number);
        } catch (NumberFormatException e){
            return "Incorrect number of quotations";
        }
        if(numberQ<=0||numberQ>255){
            return "Incorrect number of quotations";
        }
        else {
            URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/"+table+"/"+ code +"/last/"+ numberQ +"/");
            Connection con = new Connection();
            if(table=='a') {
                return con.Connect(url, 2, Currencies.valueOf(code));
            }else{
                return con.Connect(url, 3, Currencies.valueOf(code));
            }
        }
    }
    private String getNumberOfQ(String ref, String code){
        Pattern pattern = Pattern.compile(code+"/\\d");
        char[] chArr = ref.toCharArray();
        String str = "";
        int index = 0;
        String numberQ = "";
        for(char c : chArr){
            str+=c;
            Matcher matcher = pattern.matcher(str);
            if(matcher.find()){
                numberQ += c;
            }
            if(index==chArr.length-1){
                return numberQ;
            }
            index++;
        }
        return "Incorrect number of quotations";
    }
    private String getDate(String ref){
        Pattern pattern = Pattern.compile(
                "((20(0[48]|1[26]|20)-02-29)|(20([0-1]\\d|2[0-3])-(((0[13578]|1[02])-([0-2]\\d|3[0-1]))|((0[469]|11)-(([0-2]\\d|30)|02-[0-2][0-8])))))"
        );
        char[] chArr = ref.toCharArray();
        String str = "";
        int index = 0;
        for(char c : chArr) {
            str += c;
            Matcher matcher = pattern.matcher(str);
            if(matcher.find()){
                String date = "";
                for(int i = index-9; i<=index;i++) {
                    date+=chArr[i];
                }
                return date;
            }
            index++;
        }
        return "Incorrect date";
    }
    private String getCode(String ref){
        for(Currencies c : Currencies.values()){
            Pattern pattern = Pattern.compile(c.toString(), Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(ref);
            if(matcher.find()){
                char[] chArr = ref.toCharArray();
                String str = "";
                int index = 0;
                for(char ch : chArr){
                    str += ch;
                    Matcher matcher2 = pattern.matcher(str);
                    if(matcher2.find()){
                        return chArr[index-2]+""+chArr[index-1]+""+chArr[index];
                    }
                    index++;
                }
            }
        }
        return "Incorrect currency code";
    }
    private String getList(String ref){
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        for(Currencies c : Currencies.values()){
            if(counter!=0&&counter%4==0||counter==Currencies.values().length){
                sb.append(c.getName() + " - " + c.toString()+'\n');
            }
            else {
                sb.append(c.getName() + " - " + c.toString() + " | ");
            }
            counter++;
        }
        String str = sb.toString();
        return str;
    }
}
