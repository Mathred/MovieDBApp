package com.example.moviedbapp.ui.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapp.databinding.ItemContactBinding
import com.example.moviedbapp.model.entities.Contact

class ContactsAdapter(private val makeCall: (String) -> Unit, private val dialNumber: (String) -> Unit) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var contactsList: List<Contact>? = null

    inner class ViewHolder(binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvName
        val ivPhone = binding.ivPhone
        val tvPhone = binding.tvPhone
        val ivDialPad = binding.ivDial
        val tvDialPad = binding.tvDial
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
                    with (holder) {
                        ivPhone.isVisible = true
                        tvPhone. isVisible = true
                        ivPhone.setOnClickListener {
                            makeCall.invoke(phone)
                        }
                        ivDialPad.isVisible = true
                        tvDialPad. isVisible = true
                        ivDialPad.setOnClickListener {
                            dialNumber.invoke(phone)
                        }
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