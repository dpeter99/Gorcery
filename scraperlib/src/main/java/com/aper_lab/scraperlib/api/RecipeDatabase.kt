package com.aper_lab.scraperlib.api

interface DatabaseConnection {

    fun storeData(path: String, data: IHasID);

    fun getData(path: String):IHasID?;

    fun <T>getDataQuerry(path:String, key: String, value: String):List<IHasID>?
}