package mx.itson.cheemstour

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnTripMap = findViewById<View>(R.id.btnTripMap) as Button
        btnTripMap.setOnClickListener (this)
        val btnTripAdd = findViewById<Button>(R.id.btnTripAdd) as Button
        btnTripAdd.setOnClickListener (this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btnTripMap -> {
                val intentMap = Intent(this, TripMapActivity::class.java)
                startActivity(intentMap)
            }

            R.id.btnTripAdd -> {
                val intentTripForm = Intent(this, TripFormActivity :: class.java)
                startActivity(intentTripForm)
            }
        }
    }


}