package com.example.retrofitwithroom

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.retrofitwithroom.adapter.EmployeeApiListAdapter
import com.example.retrofitwithroom.adapter.EmployeeListAdapter
import com.example.retrofitwithroom.base.BaseActivity
import com.example.retrofitwithroom.databinding.ActivityMainBinding
import com.example.retrofitwithroom.databinding.DailogEmployeeDetailBinding
import com.example.retrofitwithroom.model.Employee
import com.example.retrofitwithroom.model.EmployeeData
import com.example.retrofitwithroom.model.ResponseData
import com.example.retrofitwithroom.retorifit.ApiObserver
import com.example.retrofitwithroom.viewModel.EmployeeVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

//    Demo app will have only 1 screen
//
//    In 1st screen fetch and display name and salary from API( https://dummy.restapiexample.com/api/v1/employees )
//
//    Add button in end of the row, on click of add insert name and salary in database.
//
//    When internet is there is there data will come from API and when internet is not there data will come from database.
//
//    When data is displayed from database, there should be delete option at end. On delete click data should be deleted from list

    private val employeeListAdapter by lazy {
        EmployeeListAdapter(
            emptyList(), this::adapterChildClick, this::buttonChildClick
        )
    }

    private val employeeApiListAdapter by lazy {
        EmployeeApiListAdapter(emptyList())
    }

    private fun buttonChildClick(view: View) {
        when (view.id) {
            R.id.addEmployee -> {
                showEmployeeDetailDialog()
            }
        }
    }

    private fun adapterChildClick(view: View, employee: Employee?, adapterPosition: Int) {
        when (view.id) {
            R.id.deleteEmployee -> {
                employeeListAdapter.removeItem(adapterPosition)
                lifecycleScope.launch {
                    employeeVM.deleteEmployeeData(employee?.empId ?: 0)
                }
            }
        }
    }

    private val employeeVM: EmployeeVM by viewModels<EmployeeVM>()

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initVariable() {
        if (checkInternetConnection()) {
            binding.rvOnlineData.visibility = View.VISIBLE
            binding.rvLocalData.visibility = View.GONE
            Toast.makeText(this, "Data is fetch from APIs.", Toast.LENGTH_SHORT).show()
        } else {
            binding.rvOnlineData.visibility = View.GONE
            binding.rvLocalData.visibility = View.VISIBLE
            Toast.makeText(this, "Data is fetch from Local Database.", Toast.LENGTH_SHORT).show()
        }


        binding.refreshData.setOnClickListener {
            if (checkInternetConnection()) {
                binding.rvOnlineData.visibility = View.VISIBLE
                binding.rvLocalData.visibility = View.GONE
                Toast.makeText(this, "Data is fetch from APIs.", Toast.LENGTH_SHORT).show()
                employeeVM.callCategoryApi()
            } else {
                binding.rvOnlineData.visibility = View.GONE
                binding.rvLocalData.visibility = View.VISIBLE
                Toast.makeText(this, "Data is fetch from Local Database.", Toast.LENGTH_SHORT)
                    .show()
                lifecycleScope.launch {
                    employeeVM.allEmployeeList()
                    employeeListAdapter.updateData(employeeVM.allEmployeeList())
                }
            }
        }

        employeeVM.callCategoryApi()
        binding.rvLocalData.adapter = employeeListAdapter
        binding.rvOnlineData.adapter = employeeApiListAdapter
        lifecycleScope.launch {
            employeeVM.allEmployeeList()
            employeeListAdapter.updateData(employeeVM.allEmployeeList())
        }
    }

    override fun loadData() {
        employeeVM.employeeListFromApi.observe(this, object : ApiObserver<EmployeeData>(this) {
            override fun onSuccess(responseData: ResponseData<EmployeeData>) {
                Log.e("### Success", responseData.data.toString())
                binding.rvOnlineData.visibility = View.VISIBLE
                binding.rvLocalData.visibility = View.GONE
                employeeApiListAdapter.updateData(responseData.data)
            }

            override fun onError(data: ResponseData<EmployeeData>) {
                super.onError(data)
                binding.rvOnlineData.visibility = View.GONE
                binding.rvLocalData.visibility = View.VISIBLE
                Toast.makeText(
                    this@MainActivity,
                    "Server Error, Data is fetch from Local Database.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("### Error", data.message.toString())
            }

        })
    }

    fun showEmployeeDetailDialog() {
        val binding: DailogEmployeeDetailBinding = DailogEmployeeDetailBinding.inflate(
            LayoutInflater.from(this)
        )
        val dialog = AlertDialog.Builder(this)
            .setView(binding.root)
            .setTitle(getString(R.string.enter_employee_details))
            .create()

        dialog.show()

        binding.btnCancel.setOnClickListener {
            dialog.hide()
        }

        binding.btnSave.setOnClickListener {
            employeeVM.insert(
                employee = Employee(
                    empId = System.currentTimeMillis(),
                    employeeName = binding.etEmpName.text.toString(),
                    employeeSalary = binding.etSalary.text.toString().toInt()
                )
            )
            lifecycleScope.launch {
                employeeListAdapter.updateData(employeeVM.allEmployeeList())
            }
            dialog.dismiss()

        }
    }

    private fun checkInternetConnection(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }
}