package mx.itson.cheemstour

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mx.itson.cheemstour.R.id
import mx.itson.cheemstour.entities.Trip

class TripFormActivity : AppCompatActivity(), View.OnClickListener, OnMapReadyCallback {

    var map: GoogleMap? = null
    lateinit var name: EditText
    lateinit var city: EditText
    lateinit var btnSave: Button
    lateinit var txtCoords: TextView
    var selectedCoords: LatLng? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trip_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        name = findViewById(R.id.txt_name)
        city = findViewById(R.id.txt_city)
        txtCoords = findViewById(R.id.txt_coords)
        btnSave = findViewById(R.id.btn_save)
        btnSave.setOnClickListener(this)


        //val btnSave = findViewById<View>(id.btn_save) as Button
        //btnSave.setOnClickListener(this)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_form) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_save -> {
                val nameText = name.text.toString()
                val cityText = city.text.toString()

                if (nameText.isEmpty() || cityText.isEmpty() || selectedCoords == null) {
                    Toast.makeText(this, getString(R.string.textEmptyFields), Toast.LENGTH_LONG)
                        .show()
                    return
                }

                val trip = Trip()
                trip.name = nameText
                trip.city = cityText
                trip.latitude = selectedCoords!!.latitude
                trip.longitude = selectedCoords!!.longitude

                trip.save { result ->
                    // runOnUiThread es necesario porque Retrofit ejecuta el callback en un hilo secundario y solo en el hilo principal se puede modificar la UI
                    runOnUiThread {
                        if (result) {
                            Toast.makeText(
                                this,
                                getString(R.string.textSavedTrip),
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                getString(R.string.textNoSavedTrip),
                                Toast.LENGTH_LONG
                            ).show()
                        }


                    }
                }
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        try {
            map = googleMap
            map?.mapType = GoogleMap.MAP_TYPE_NORMAL
            map?.clear()

            map?.setOnMapClickListener { latLng ->
                map?.clear()
                map?.addMarker(
                    MarkerOptions().position(latLng).title(getString(R.string.textSelectedLocation)).draggable(true)
                )

                map?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                map?.animateCamera(CameraUpdateFactory.zoomTo(8f))

                selectedCoords = latLng

                txtCoords.text = "Lat: %.4f, Lng: %.4f".format(latLng.latitude, latLng.longitude)
                Log.d("Latitude", latLng.latitude.toString())
                Log.d("Longitude", latLng.longitude.toString())


                map?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                    override fun onMarkerDragStart(marker: com.google.android.gms.maps.model.Marker) {}

                    override fun onMarkerDrag(marker: com.google.android.gms.maps.model.Marker) {}

                    override fun onMarkerDragEnd(marker: com.google.android.gms.maps.model.Marker) {
                        selectedCoords = marker.position
                        txtCoords.text = "Lat: %.4f, Lng: %.4f".format(
                            marker.position.latitude,
                            marker.position.longitude
                        )
                    }
                })
            }
        } catch (ex: Exception) {
            Log.e("Error saving", ex.message.toString())
        }
    }
}


























//override fun onClick(view: View) {
//   when(view.id){
//      R.id.btn_save -> {
//          val name = findViewById<EditText>(R.id.txt_name).text.toString()
//          val city = findViewById<EditText>(R.id.txt_city).text.toString()
//
//          if(name.isEmpty() || city.isEmpty() || selectedCoords == null){
//              Toast.makeText(this, getString(R.string.textEmptyFields), Toast.LENGTH_LONG).show()
//              return
//          }
//
//          val trip = Trip()
//          trip.name = name
//          trip.city = city
//          trip.latitude = selectedCoords!!.latitude
//          trip.longitude = selectedCoords!!.longitude
//
//          trip.save { result ->
//              // runOnUiThread es necesario porque Retrofit ejecuta el callback en un hilo secundario y solo en el hilo principal se puede modificar la UI
//              runOnUiThread {
//                  if (result) {
//                      Toast.makeText(this, getString(R.string.textSavedTrip), Toast.LENGTH_LONG).show()
//                      finish()
//                  } else {
//                      Toast.makeText(this, getString(R.string.textNoSavedTrip), Toast.LENGTH_LONG).show()
//                  }
//
//
//              }
//          }
//      }
//  }
//}



//override fun onMapReady(googleMap: GoogleMap){
  //  map = googleMap
   // map?.mapType = GoogleMap.MAP_TYPE_NORMAL
    //map?.setOnMapClickListener { latLng ->
    //    map?.clear()
     //   map?.addMarker(MarkerOptions().position(latLng).title(getString(R.string.textSelectedLocation)))
     //   selectedCoords = latLng

     //   val txtCoords = findViewById<TextView>(R.id.txt_coords)
      //  txtCoords.text = "Lat: %.4f, Lng: %.4f".format(latLng.latitude, latLng.longitude)
   // }

//}


//override fun onMapReady(googleMap: GoogleMap){
//try {
//      map = googleMap
//      map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
//      map?.clear()
//
//      map?.setOnMapClickListener { latLng ->
//          map?.clear()
//          var latLng = LatLng(0.0, 0.0)
//          map?.addMarker(MarkerOptions().position(latLng).draggable(true))
//          map?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//          map?.animateCamera(CameraUpdateFactory.zoomTo(8f))
//
//      }
//  }catch (ex: Exception){
//      Log.e("Error saving",ex.message.toString())
//  }
//}


//}



// fun onMapReady(googleMap: GoogleMap){
//  try {
//      map = googleMap
//      map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
//      map?.clear()
//
//      map?.setOnMapClickListener { latLng ->
//          map?.clear()
//          var latLng = LatLng(0.0, 0.0)
//          map?.addMarker(MarkerOptions().position(latLng).draggable(true))
//          map?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//          map?.animateCamera(CameraUpdateFactory.zoomTo(8f))
//          map?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener){
//
//          }
//
//      }
//  }catch (ex: Exception){
//      Log.e("Error saving",ex.message.toString())
//  }
//}



