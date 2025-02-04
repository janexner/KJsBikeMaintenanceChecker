package com.exner.tools.kjsbikemaintenancechecker.ui

import androidx.lifecycle.ViewModel
import com.exner.tools.kjsbikemaintenancechecker.database.KJsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageAccessoriesViewModel @Inject constructor(
    repository: KJsRepository
) : ViewModel() {

    val accessories = repository.observeAccessories

}
