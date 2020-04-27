package com.aper_lab.grocery

interface IFABProvider {
    fun setFABProperties(props:FABParameters?);

    fun setFABListener(a:IHasFAB)
}