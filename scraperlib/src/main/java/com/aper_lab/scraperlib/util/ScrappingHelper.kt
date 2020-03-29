package com.aper_lab.scraperlib.util

import com.aper_lab.scraperlib.util.gsonAdapters.AlwaysListTypeAdapterFactory
import com.aper_lab.scraperlib.util.gsonAdapters.RuntimeTypeAdapterFactory
import com.aper_lab.scraperlib.util.schemaOrg.JsonLDData
import com.aper_lab.scraperlib.util.schemaOrg.Recipe
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URL
import java.nio.charset.Charset
import java.util.ArrayList

object ScrappingHelper {

    val recipeTypeRegex = Regex("\"@context\": *\"http:\\/\\/schema\\.org\", *\\n? *\"@type\": *\"Recipe\",")


    fun getDocFromURL(link: URL): Document {
        val doc = Jsoup.connect(link.toString()).followRedirects(true).get()
        doc.charset(Charset.forName("UTF-8"))
        return doc
    }

    ///Finds the script tag containing the recipe type (" "@context":"http://schema.org","@type":"Recipe" ") tag
    ///returns the text inside the script tag
    fun findJsonLD(doc: Document):String?{
        val jsonLDTags = doc.select("script[type=\"application/ld+json\"]");
        for (tag in jsonLDTags){
            val text = tag.data();
            if(text.contains(recipeTypeRegex)){
                //This does contain a recipe description
                return text;
            }
        }
        return null;
    }

    fun recipeFromJsonLD(jasonLD: String): com.aper_lab.scraperlib.util.schemaOrg.Recipe? {

        val runtimeTypeAdapterFactory: RuntimeTypeAdapterFactory<JsonLDData> = RuntimeTypeAdapterFactory
            .of(JsonLDData::class.java, "@type")
            .registerSubtype(Recipe::class.java, "Recipe")
            .registerSubtype(JsonLDData::class.java)
            //.registerSubtype(Other::class.java, "cat")

        val test = AlwaysListTypeAdapterFactory<ArrayList<JsonLDData>>();

        val gson = GsonBuilder()
            //.registerTypeAdapterFactory(test)
            .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
            .create()


        var list = arrayListOf<JsonLDData>();
        var a = gson.fromJson(jasonLD,list::class.java);
        //return Recipe.fromSchemaOrg(jsonobject);
        return null
    }

    fun checkWebsiteForJsonLD(doc: Document){
        val jsonLDText = findJsonLD(doc);
        if(jsonLDText != null)
        recipeFromJsonLD(jsonLDText);

    }
}