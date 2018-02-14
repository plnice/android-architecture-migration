package com.github.plnice.archmigration.main.view.epoxy

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.github.plnice.archmigration.R
import kotlinx.android.synthetic.main.epoxy_message.view.*

@EpoxyModelClass(layout = R.layout.epoxy_message)
abstract class MessageEpoxyModel : EpoxyModelWithHolder<MessageEpoxyModel.Holder>() {

    @EpoxyAttribute var createdAt: String? = null
    @EpoxyAttribute var message: String? = null

    override fun bind(holder: Holder) {
        holder.createdAt.text = createdAt
        holder.message.text = message
    }

    class Holder : EpoxyHolder() {

        lateinit var createdAt: TextView
        lateinit var message: TextView

        override fun bindView(itemView: View) {
            createdAt = itemView.created_at
            message = itemView.message
        }

    }

}
