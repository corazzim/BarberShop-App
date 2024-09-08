package com.example.barbershop

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import java.util.*

class Main_Agenda : AppCompatActivity() {

    private lateinit var dateButton: AppCompatButton
    private lateinit var timeSpinner: Spinner
    private lateinit var confirmButton: AppCompatButton
    private lateinit var buttonCabelo: AppCompatButton
    private lateinit var buttonBarba: AppCompatButton
    private lateinit var buttonCabeloBarba: AppCompatButton
    private lateinit var buttonCrianca: AppCompatButton

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_agenda)

        dateButton = findViewById(R.id.data_picker)
        timeSpinner = findViewById(R.id.hora_spinner)
        confirmButton = findViewById(R.id.button_confirmar)

        buttonCabelo = findViewById(R.id.button_cabelo)
        buttonBarba = findViewById(R.id.button_barba)
        buttonCabeloBarba = findViewById(R.id.button_barba_cabelo)
        buttonCrianca = findViewById(R.id.button_kids)

        dateButton.setOnClickListener {
            showDatePickerDialog()
        }

        confirmButton.setOnClickListener {
            // Obtém a data e remove o prefixo "Data: ", se necessário
            val dateText = dateButton.text.toString()
            val date = if (dateText.startsWith("Data: ")) dateText.removePrefix("Data: ").trim() else ""

            // Obtém o horário selecionado
            val selectedTime = timeSpinner.selectedItem?.toString()?.trim() ?: ""

            // Verifica quais serviços foram selecionados
            val selectedOptions = mutableListOf<String>()
            if (buttonCabelo.isSelected) selectedOptions.add("Cabelo")
            if (buttonBarba.isSelected) selectedOptions.add("Barba")
            if (buttonCabeloBarba.isSelected) selectedOptions.add("Cabelo + Barba")
            if (buttonCrianca.isSelected) selectedOptions.add("Criança")
            val selectedOptionText = selectedOptions.joinToString(", ")

            // Adiciona logs para depuração
            println("Texto da data: '$dateText'")
            println("Data selecionada: '$date'")
            println("Hora selecionada: '$selectedTime'")
            println("Serviços selecionados: '$selectedOptionText'")

            // Verifica se todos os campos estão preenchidos
            if (date.isEmpty() || selectedTime.isEmpty() || selectedOptionText.isEmpty()) {
                showToast("Por favor, preencha todos os campos!")
            } else {
                showConfirmationDialog(date, selectedTime, selectedOptionText)
            }
        }



        setupServiceButtons()
        setupTimeSpinner()
    }

    private fun setupServiceButtons() {
        buttonCabelo.setOnClickListener { handleSelection(buttonCabelo) }
        buttonBarba.setOnClickListener { handleSelection(buttonBarba) }
        buttonCabeloBarba.setOnClickListener { handleSelection(buttonCabeloBarba) }
        buttonCrianca.setOnClickListener { handleChildSelection() }
    }

    private fun handleSelection(button: AppCompatButton) {
        buttonCabelo.isSelected = button == buttonCabelo
        buttonBarba.isSelected = button == buttonBarba
        buttonCabeloBarba.isSelected = button == buttonCabeloBarba
    }

    private fun handleChildSelection() {
        buttonCrianca.isSelected = !buttonCrianca.isSelected
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val formattedDate = "${dayOfMonth}/${(month + 1).toString().padStart(2, '0')}/$year"
                dateButton.text = "Data: $formattedDate"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun setupTimeSpinner() {
        val timeOptions = resources.getStringArray(R.array.horarios)
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            timeOptions
        )
        adapter.setDropDownViewResource(R.layout.dropdown_item)
        timeSpinner.adapter = adapter
    }

    private fun showConfirmationDialog(date: String, time: String, selectedOption: String) {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialogTheme)
        builder.setTitle("Confirmação")
        builder.setMessage("Data: $date\nHorário: $time\nServiço: $selectedOption")

        builder.setPositiveButton("Finalizar") { dialog, _ ->
            dialog.dismiss()
            showToast("Agendamento confirmado!")

            dateButton.text = "Data: "
            timeSpinner.setSelection(0)
            buttonCabelo.isSelected = false
            buttonBarba.isSelected = false
            buttonCabeloBarba.isSelected = false
            buttonCrianca.isSelected = false

            val intent = Intent(this, Main_myHour::class.java)
            intent.putExtra("EXTRA_DATE", date)
            intent.putExtra("EXTRA_TIME", time)
            intent.putExtra("EXTRA_SERVICE", selectedOption)
            startActivity(intent)
        }

        builder.setCancelable(true)
        val dialog = builder.create()
        dialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
