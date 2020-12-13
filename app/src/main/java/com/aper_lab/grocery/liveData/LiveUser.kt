package com.aper_lab.grocery.liveData

import com.aper_lab.grocery.model.User
import com.aper_lab.grocery.util.DocumentLiveData
import com.aper_lab.grocery.util.QuerySingleLiveMutableDataNative
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.model.Document

class LiveUser(doc: DocumentReference) :
    DocumentLiveData<User>(doc, User::class.java) {

    init {

    }


}