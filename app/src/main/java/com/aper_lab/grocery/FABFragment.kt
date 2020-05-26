package com.aper_lab.grocery

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar

open class FABFragment : Fragment(), IHasFAB {
    private lateinit var fab: IFABProvider;

    var fabParameters: FABParameters? = null
    set(v){
        field = v;
        fab.setFABProperties(v);
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.e("APP","Binding: " + this.javaClass.name)
        if(context is IFABProvider){
            fab = context;
            fab.setFABProperties(fabParameters);
            fab.setFABListener(this);
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {

        Log.e("APP","Binding: " + this.javaClass.name)
        val a = activity;
        if(a is IFABProvider){
            fab = a;
            fab.setFABProperties(fabParameters);
            fab.setFABListener(this);
        }

        super.onStart()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.e("APP","Binding: " + this.javaClass.name)

        super.onViewStateRestored(savedInstanceState)
        fab.setFABProperties(fabParameters);
        fab.setFABListener(this);
    }

    override fun onFABClicked() {

    }


}