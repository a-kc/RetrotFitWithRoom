package com.example.retrofitwithroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapplication.base.BaseViewHolder
import com.example.retrofitwithroom.R
import com.example.retrofitwithroom.databinding.ItemButtonBinding
import com.example.retrofitwithroom.databinding.ItemEmployeeOfflineBinding
import com.example.retrofitwithroom.model.Employee

class EmployeeListAdapter(
    private var employeeData: List<Employee>,
    private val childClickListener: (view: View, employee: Employee?, position: Int) -> Unit,
    private val buttonClickListener: (view: View) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_EMPLOYEE = 0
    val VIEW_TYPE_BUTTON = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EMPLOYEE -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_employee_offline, parent, false)
                return EmployeeViewHolder(view)
            }

            VIEW_TYPE_BUTTON -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_button, parent, false)
                return ButtonViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EmployeeViewHolder -> {
                val item = employeeData[position]
                holder.itemBinding?.employeeDetails = item
            }

            is ButtonViewHolder -> {

            }

        }
    }

    override fun getItemCount(): Int {
        return employeeData.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < employeeData.size) VIEW_TYPE_EMPLOYEE else VIEW_TYPE_BUTTON
    }

    inner class EmployeeViewHolder(itemView: View) :
        BaseViewHolder<ItemEmployeeOfflineBinding>(itemView) {
        init {
            itemBinding?.deleteEmployee?.setOnClickListener {
                childClickListener.invoke(
                    it,
                    employeeData[adapterPosition],
                    adapterPosition
                )
            }
        }
    }

    inner class ButtonViewHolder(itemView: View) : BaseViewHolder<ItemButtonBinding>(itemView) {
        init {
            itemBinding?.addEmployee?.setOnClickListener {
                buttonClickListener.invoke(
                    it
                )
            }
        }
    }

    inner class MyDiffCallback(
        private val oldList: List<Employee>,
        private val newList: List<Employee>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].empId == newList[newItemPosition].empId

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].empId == newList[newItemPosition].empId
    }

    fun updateData(newDataList: List<Employee>) {
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