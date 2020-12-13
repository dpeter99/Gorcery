package com.aper_lab.grocery.util

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.*

fun <T> Query.livedata(clazz: Class<T>): LiveData<List<T>> {
    return QueryLiveDataListNative(this, clazz)
}

fun <T> Query.singleLivedata(clazz: Class<T>): LiveData<T> {
    return QuerySingleLiveMutableDataNative(this, clazz)
}

public abstract class QueryLiveData<T>(
    private val query: Query
) : LiveData<T>() {

    private var listener: ListenerRegistration? = null

    override fun onActive() {
        super.onActive()

        listener = query.addSnapshotListener { querySnapshot, exception ->
            if (exception == null) {
                value = querySnapshot?.let { parseSnapshot(it) }
            } else {
                Log.e("FireStoreLiveData", "", exception)
            }
        }
    }

    override fun onInactive() {
        super.onInactive()

        listener?.remove()
        listener = null
    }

    abstract fun parseSnapshot(querySnapshot: QuerySnapshot): T

}

public abstract class QueryLiveDataList<T>(private val query: Query
) : QueryLiveData<List<T>>(query) {

    override fun parseSnapshot(querySnapshot: QuerySnapshot): List<T> {
        return querySnapshot.documents.map { parseSingle(it) }
    }

    abstract fun parseSingle(documentSnapshot: DocumentSnapshot): T
}


public open class QueryLiveDataListNative<T>(
    private val query: Query,
    private val clazz: Class<T>
) : QueryLiveDataList<T>(query) {

    override fun parseSingle(documentSnapshot: DocumentSnapshot): T {
        return documentSnapshot.toObject(clazz)!!;
    }
}


public open class QueryLiveDataListCustom<T>(private val query: Query,
                                     private val parser: (documentSnapshot: DocumentSnapshot) -> T
) : QueryLiveDataList<T>(query) {

    override fun parseSnapshot(querySnapshot: QuerySnapshot): List<T> {
        return querySnapshot.documents.map { parseSingle(it) }
    }

    override fun parseSingle(documentSnapshot: DocumentSnapshot): T {
        return parser.invoke(documentSnapshot);
    }

}

public open class QuerySingleLiveMutableDataNative<T>(
    private val query: Query,
    private val clazz: Class<T>
) : MutableLiveData<T>() {

    private var listener: ListenerRegistration? = null

    private var lastFirestoreData: DocumentSnapshot? = null;

    var firstUpdate: SingleLiveEvent<T> = SingleLiveEvent();
    var first: Boolean = true;
    init {
        this.observeForever {
            if(first) {
                firstUpdate.value= it;
                first = false;
            }
        }
    }

    override fun onActive() {
        super.onActive()

        listener = query.addSnapshotListener { querySnapshot, exception ->
            if (exception == null) {
                querySnapshot?.let {
                    if(!querySnapshot.isEmpty) {
                        value = querySnapshot.documents[0]
                                ?.toObject(clazz)
                        lastFirestoreData = querySnapshot.documents[0];
                    }
                    else{
                        onEmpty();
                    }
                }
            } else {
                onError(exception);
            }
        }
    }

    override fun onInactive() {
        super.onInactive()

        listener?.remove()
        listener = null
    }

    open fun onError(e: FirebaseFirestoreException?){
        Log.e("FireStoreLiveData", "", e)
    }

    open fun onEmpty(){
        //Log.e("FireStoreLiveData", "", e)
    }
}