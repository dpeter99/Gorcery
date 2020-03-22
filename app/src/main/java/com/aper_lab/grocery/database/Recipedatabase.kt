package com.aper_lab.grocery.database

import com.aper_lab.scraperlib.api.DatabaseConnection
import com.aper_lab.scraperlib.api.IHasID
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

class Recipedatabase : DatabaseConnection {

    val db =Firebase.firestore;

    override fun storeData(path: String, data: IHasID) {
        db.collection(path).document(data.GetID()).set(data);
    }

    override fun getData(path: String): IHasID? {
        return db.document(path).get().result?.toObject<IHasID>()
    }

    override fun <T>getDataQuerry(path: String, key: String, value: String) :List<T>?{
        return db.collection(path).whereEqualTo(key,value).get().getResult()?.toObjects(T::class.java)
    }

}