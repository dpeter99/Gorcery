package com.aper_lab.grocery.viewModel.discover

import com.google.firebase.auth.FirebaseAuth

class DiscoverViewModel {

    var user = FirebaseAuth.getInstance().currentUser;


}