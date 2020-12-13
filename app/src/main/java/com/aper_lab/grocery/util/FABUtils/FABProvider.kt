package com.aper_lab.grocery.util.FABUtils

import androidx.appcompat.app.AppCompatActivity

abstract class FABProvider: AppCompatActivity() {
    abstract fun setFABProperties(props: FABParameters?);

    abstract fun setFABListener(a: IHasFAB?);

    /*
    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)

        supportFragmentManager.primaryNavigationFragment?.let {
            if(it !is FABFragment){
                setFABProperties(null);
                setFABListener(null);
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        supportFragmentManager.primaryNavigationFragment?.let {
            if(it !is FABFragment){
                setFABProperties(null);
                setFABListener(null);
            }
        }


    }
    */

}