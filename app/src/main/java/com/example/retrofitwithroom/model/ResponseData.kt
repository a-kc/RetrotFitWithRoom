package com.example.retrofitwithroom.model


data class ResponseData<T>(
    var message: String? = "",
    val data: List<EmployeeData>,
    var status: String? = "",
    var item_id: Long? = 0,
    var resource: Status,
    var key: String? = ""

) {
    companion object {
        fun <T> success(data: ResponseData<T>?): ResponseData<T> {
            return if (data is ResponseData<T>) {
                data.resource = Status.SUCCESS
                data
            } else {
                error("Data not Available", emptyList())
            }
        }

        fun <T> error(msg: String, data: List<EmployeeData>): ResponseData<T> {
            return ResponseData(message = msg, data = data, resource = Status.ERROR)
        }

        fun <T> loading(data: List<EmployeeData>): ResponseData<T> {
            return ResponseData(data = data, resource = Status.LOADING)
        }
    }
}