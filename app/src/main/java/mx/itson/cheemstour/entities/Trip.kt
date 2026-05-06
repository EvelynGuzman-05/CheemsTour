package mx.itson.cheemstour.entities


import android.content.Context


class Trip {
    constructor()
    constructor(id: Int, name: String, city: String, latitude: Double, longitude: Double){
        this.id = id
        this.name = name
        this.city = city
        this.latitude = latitude
        this.longitude = longitude


    }

    var id: Int? = null
    var name: String? = null
    var city: String? = null
    var latitude: Double = 0.0
    var longitude: Double = 0.0





    // unit --> void
    fun save(callback: (Boolean) -> Unit) {
        val call = mx.itson.cheemstour.utils.RetrofitUtil.getApi().saveTrip(this)
        call.enqueue(object : retrofit2.Callback<Boolean> {
            override fun onResponse(call: retrofit2.Call<Boolean>, response: retrofit2.Response<Boolean>) {
                callback(response.body() == true)
            }
            override fun onFailure(call: retrofit2.Call<Boolean>, t: Throwable) {
                callback(false)
            }
        })
    }

}
