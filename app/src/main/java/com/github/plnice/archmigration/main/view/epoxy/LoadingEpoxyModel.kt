package com.github.plnice.archmigration.main.view.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.github.plnice.archmigration.R

@EpoxyModelClass(layout = R.layout.epoxy_loading)
abstract class LoadingEpoxyModel : EpoxyModel<View>()
