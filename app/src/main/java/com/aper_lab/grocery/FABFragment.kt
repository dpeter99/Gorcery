package com.aper_lab.grocery

import android.content.Context
import android.os.Bundle
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

        if(context is IFABProvider){
            fab = context;
            fab.setFABProperties(fabParameters);
            fab.setFABListener(this);
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        fab.setFABProperties(fabParameters);
        fab.setFABListener(this);
    }

    override fun onFABClicked() {

    }


}