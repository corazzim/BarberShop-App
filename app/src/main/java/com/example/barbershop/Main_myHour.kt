package com.example.barbershop

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class Main_myHour : AppCompatActivity() {

    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var serviceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_my_hour)

        // Inicializa os TextViews
        dateTextView = findViewById(R.id.date) // Substitua pelos IDs reais
        timeTextView = findViewById(R.id.hour)
        serviceTextView = findViewById(R.id.service)

        // Recupera os dados passados
        val date = intent.getStringExtra("EXTRA_DATE")
        val time = intent.getStringExtra("EXTRA_TIME")
        val service = intent.getStringExtra("EXTRA_SERVICE")

        // Exibe os dados
        dateTextView.text = "Data: $date"
        timeTextView.text = "Horário: $time"
        serviceTextView.text = "Serviço: $service"
    }
}
