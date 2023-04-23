public enum Currencies {
    PLN("Polish Zloty"), USD("US Dollar"), EUR("Euro"), SEK("Swedish Krona"), CHF("Swiss Franc"),
    GBP("Pound Sterling"), CZK("Czech Koruna"), AUD("Australian Dollar"), THB("Baht"), BRL("Brazilian Real"),
    BGN("Bulgarian Lev"), CAD("Canadian Dollar"), CLP("Chilean Peso"),  DKK("Danish Krone"),
    HUF("Forint"), HKD("Hong Kong Dollar"), UAH("Hryvnia"), ISK("Iceland Krona"), INR("Indian Rupee"),
    MYR("Malaysian Ringgit"), MXN("Mexican Peso"), ILS("New Israeli Shekel"), NZD("New Zealand Dollar"), NOK("Norwegian Krone"),
    PHP("Philippine Peso"), ZAR("Rand"), RON("Romanian Leu"), IDR("Rupiah"),
    SGD("Singapore Dollar"), TRY("Turkish Lira"), KRW("Won"),
    JPY("Yen"), CNY("Yuan Renminbi"), XDR("SDR Int'l Monetary Fund (I.M.F.)");
    private String name;
    private Currencies(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
