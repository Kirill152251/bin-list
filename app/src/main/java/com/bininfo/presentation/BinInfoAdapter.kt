package com.bininfo.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bininfo.databinding.RecycleViewItemBinding
import com.bininfo.domain.BinInfoHistory

class BinInfoAdapter(private val onDelete: (binInfoHistory: BinInfoHistory) -> Unit) :
    ListAdapter<BinInfoHistory, BinInfoAdapter.ViewHolder>(ItemCallback) {

    class ViewHolder(val binding: RecycleViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecycleViewItemBinding.inflate(inflater, parent, false)
        binding.buttonDelete.setOnClickListener { onDelete }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            textBinValue.text = item.bin
            textBrandValue.text = item.brand
            textBankValue.text = item.bank
            textBankSiteValue.text = item.bankSite
            textBankPhoneValue.text = item.bankPhone
            textCountryValue.text = item.country
            textQueryDate.text = item.inputDate
        }
    }

    object ItemCallback : DiffUtil.ItemCallback<BinInfoHistory>() {
        override fun areItemsTheSame(oldItem: BinInfoHistory, newItem: BinInfoHistory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BinInfoHistory, newItem: BinInfoHistory): Boolean {
            return oldItem.inputDate == newItem.inputDate
        }
    }
}