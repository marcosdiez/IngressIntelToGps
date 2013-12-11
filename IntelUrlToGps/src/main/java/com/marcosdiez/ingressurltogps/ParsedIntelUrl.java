package com.marcosdiez.ingressurltogps;

/**
 * Created by Marcos on 11/24/13.
 */
public class ParsedIntelUrl {
    final static String TAG = "Intel2GPS";
    public String getLongitude(){
        return longitude;
    }

    public String getLatitude(){
        return latitude;
    }

    private String longitude;
    private String latitude;

    public ParsedIntelUrl(String intelUrl){
        // http://www.ingress.com/intel?ll=-11.111111,-22.222222&z=12
        // https://www.ingress.com/intel?ll=-11.111111,-22.222222&z=12&pll=-33.11111,-44.222222

        // http://ingress.com/intel?ll=-11.111111,-22.222222&z=12
        // https://ingress.com/intel?ll=-11.111111,-22.222222&z=12&pll=-33.11111,-44.222222

        // verifyUrlSanity(intelUrl);

        int begin = getUrlBegin(intelUrl);
        int end = getUrlEnd(intelUrl, begin);

        String parsedUrl = intelUrl.substring(begin, end);
        int coma = getComa(intelUrl, parsedUrl);

        longitude = parsedUrl.substring(0,coma);
        latitude = parsedUrl.substring(coma+1);
    }

    private int getComa(String intelUrl, String parsedUrl) {
        int coma = parsedUrl.indexOf(",");
        if(coma == -1){
            throw new IllegalArgumentException("Intel URL must have a coma. You sent [" + intelUrl +"]");
        }
        return coma;
    }

//    private void verifyUrlSanity(String intelUrl) {
          // that is useless and limits my app, probably making it future incompatible
//        if( ! intelUrl.startsWith("http://www.ingress.com/intel?") && ! intelUrl.startsWith("https://www.ingress.com/intel?")){
//            throw new IllegalArgumentException("Intel URL must start with [http://www.ingress.com/intel]. You sent [" + intelUrl +"]");
//        }
//    }

    private int getUrlEnd(String intelUrl, int begin) {
        int end=intelUrl.indexOf("&", begin);
        if(end == -1 ){
            end = intelUrl.length();
        }
        return end;
    }

    private int getUrlBegin(String intelUrl) {
        int begin=-1;

        String[] locationTokenArray = {"&pll=","?pll=","?ll=", "&ll="};
        String theLocationToken=null;
        for( String locationToken : locationTokenArray ){
            begin = intelUrl.indexOf(locationToken);
            if( begin != -1 ){
                theLocationToken=locationToken;
                break;
            }
        }
        if(begin==-1){
            throw new IllegalArgumentException("Intel URL must contain either [?ll=] or [&ll=] or [?pll=] or [&pll=]");
        }
        return begin + theLocationToken.length();
    }
}
