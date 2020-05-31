package com.aper_lab.scraperlib.util

import java.net.URL

object URLutils {

    fun HTTPToHTTPS(url: String):String{
        if(url.startsWith("http://")){
            val res = url.replace("http", "https");
            return res;
        }

        return url;
    }

    /* Returns true if url is valid */
    fun isValid(url: String?): Boolean {
        /* Try creating a valid URL */
        return try {
            URL(url)
                    .toURI()
            true
        } // If there was an Exception
        // while creating URL object
        catch (e: Exception) {
            false
        }
    }

}