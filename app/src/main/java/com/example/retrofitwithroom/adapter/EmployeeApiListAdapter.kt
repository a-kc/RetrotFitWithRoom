package com.example.retrofitwithroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapplication.base.BaseViewHolder
import com.example.retrofitwithroom.R
import com.example.retrofitwithroom.databinding.ItemEmployeeOnlineBinding
import com.example.retrofitwithroom.model.EmployeeData


class EmployeeApiListAdapter(
    private var employeeData: List<EmployeeData>
) :
    RecyclerView.Adapter<EmployeeApiListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_employee_online, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = employeeData[position]
        holder.itemBinding?.employeeDetails = item

    }


    override fun getItemCount(): Int {
        return employeeData.size
    }


    inner class ViewHolder(itemView: View) :
        BaseViewHolder<ItemEmployeeOnlineBinding>(itemView) {
    }


    inner class MyDiffCallback(
        private val oldList: List<EmployeeData>,
        private val newList: List<EmployeeData>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    fun updateData(newDataList: List<EmployeeData>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(employeeData, newDataList))
        employeeData = newDataList
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeItem(position: Int) {
        // Check if the position is valid
        if (position in employeeData.indices) {
            // Remove the item from the list
            val removedItem = employeeData[position]
            employeeData = employeeData.toMutableList().apply { removeAt(position) }

            // Notify the adapter of the removal
            notifyItemRemoved(position)

            // Optionally, you can notify range changed to update other items if needed
            notifyItemRangeChanged(position, itemCount)
        }
    }

    fun isListEmpty(): Boolean {
        return employeeData.isEmpty()
    }
}