package com.aper_lab.grocery.util.FABUtils

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

open class FABFragment : Fragment(), IHasFAB {
    private lateinit var fab: FABProvider;

    var fabParameters: FABParameters? = null
    set(v){
        field = v;
        fab.setFABProperties(v);
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.i("APP","Binding FABFragment to the activity: " + this.javaClass.name)
        if(context is FABProvider){
            fab = context;
            //fab.setFABProperties(fabParameters);
            //fab.setFABListener(this);
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fab.let {
            //fab = context;
            fab.setFABProperties(fabParameters);
            fab.setFABListener(this);
        }
    }

    override fun onStart() {

        Log.i("APP","Binding FABFragment to the activity: " + this.javaClass.name)
        val a = activity;
        if(a is FABProvider){
            fab = a;
            fab.setFABProperties(fabParameters);
            fab.setFABListener(this);
        }

        super.onStart()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.i("APP","Binding FABFragment to the activity: " + this.javaClass.name)

        super.onViewStateRestored(savedInstanceState)
        fab.setFABProperties(fabParameters);
        fab.setFABListener(this);
    }

    override fun onFABClicked() {

    }


}