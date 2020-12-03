package com.aper_lab.grocery.util

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.*

fun <T> Query.livedata(clazz: Class<T>): LiveData<List<T>> {
    return QueryLiveDataNative(this, clazz)
}

fun <T> Query.singleLivedata(clazz: Class<T>): LiveData<T> {
    return QuerySingleLiveMutableDataNative(this, clazz)
}

private class QueryLiveDataNative<T>(
    private val query: Query,
    private val clazz: Class<T>
) : LiveData<List<T>>() {

    private var listener: ListenerRegistration? = null

    override fun onActive() {
        super.onActive()

        listener = query.addSnapshotListener { querySnapshot, exception ->
            if (exception == null) {
                value = querySnapshot?.documents?.map { it.toObject(clazz)!! }
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
                        value = querySnapshot?.documents?.get(0)
                                ?.toObject(clazz)
                        lastFirestoreData = querySnapshot?.documents?.get(0);
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