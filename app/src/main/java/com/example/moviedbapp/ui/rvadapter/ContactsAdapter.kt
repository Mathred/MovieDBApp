package com.example.moviedbapp.ui.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapp.databinding.ItemContactBinding
import com.example.moviedbapp.model.entities.Contact

class ContactsAdapter(private val makeCall: (String) -> Unit) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var contactsList: List<Contact>? = null

    inner class ViewHolder(binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvName
        val ivPhone = binding.ivPhone
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactsList?.getOrNull(position)
        contact?.let {
            holder.name.text = it.name
            it.phone?.let { phone ->
                if (phone.isNotBlank()) {
                    holder.ivPhone.isVisible = true
                    holder.ivPhone.setOnClickListener {
                        makeCall.invoke(phone)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return contactsList?.size ?: 0
    }

    fun setData(data: List<Contact>?) {
        contactsList = data
        notifyItemRangeInserted(0, data?.size ?: 1 - 1)
    }
}