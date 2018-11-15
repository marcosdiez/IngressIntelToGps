package com.marcosdiez.ingressurltogps;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ParsedIntelUrlTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void doTests(){
        String expectedLongitude="-11.11111";
        String expectedLatitude="-22.222222";

        String expectedLongitudeX="-33.11111";
        String expectedLatitudeX="-44.222222";


        testUrl("http://www.ingress.com/intel?ll=-11.11111,-22.222222&z=12", expectedLongitude, expectedLatitude);
        testUrl("https://www.ingress.com/intel?ll=-11.11111,-22.222222&z=12", expectedLongitude, expectedLatitude);
        testUrl("http://ingress.com/intel?ll=-11.11111,-22.222222&z=12&pll=-33.11111,-44.222222", expectedLongitudeX, expectedLatitudeX);
        testUrl("https://ingress.com/intel?ll=-11.11111,-22.222222&z=12&pll=-33.11111,-44.222222", expectedLongitudeX, expectedLatitudeX);
        testUrl("http://intel.ingress.com/intel?ll=-11.11111,-22.222222&z=12&pll=-33.11111,-44.222222", expectedLongitudeX, expectedLatitudeX);
        testUrl("https://intel.ingress.com/intel?ll=-11.11111,-22.222222&z=12&pll=-33.11111,-44.222222", expectedLongitudeX, expectedLatitudeX);

    }

    public void testUrl(String theUrl, String expectedLongitude, String expectedLatitude){
        ParsedIntelUrl x = new ParsedIntelUrl(theUrl);
        if( !expectedLatitude.equals(x.getLatitude()) ){
            throw new AssertionError(
                    "Wrong Latitude. Expected ["+expectedLatitude+"], got [" + x.getLatitude() + "] from ["+theUrl+"]");
        }
        if( !expectedLongitude.equals(x.getLongitude()) ){
            throw new AssertionError(
                    "Wrong Latitude. Expected ["+expectedLongitude+"], got [" + x.getLongitude()+ "] from ["+theUrl+"]");
        }
    }
}

