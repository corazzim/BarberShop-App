package com.example.barbershop

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var isPasswordVisible = false
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val entrar: Button = findViewById(R.id.btn_enter)
        val txtSenha: TextView = findViewById(R.id.txt_senha)
        val cadastro: TextView = findViewById(R.id.txt_cadastro)
        val txtEmail: EditText = findViewById(R.id.txt_email)
        val txtPassword: EditText = findViewById(R.id.txt_password)
        val togglePasswordVisibility: ImageView = findViewById(R.id.toglle_password)

        // Alternar a visibilidade da senha
        togglePasswordVisibility.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                txtPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                togglePasswordVisibility.setImageResource(R.drawable.baseline_visibility_off_24) // Ícone de "olho fechado"
            } else {
                txtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                togglePasswordVisibility.setImageResource(R.drawable.baseline_visibility_24) // Ícone de "olho aberto"
            }

            // Move o cursor para o final do texto
            txtPassword.setSelection(txtPassword.text.length)
        }

        entrar.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        txtSenha.setOnClickListener {
            val intent = Intent(this, Main_senha::class.java)
            startActivity(intent)
        }

        cadastro.setOnClickListener {
            val intent = Intent(this, Main_cadastro::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login bem-sucedido
                    val intent = Intent(this, Main_Agenda::class.java)
                    startActivity(intent)
                    finish() // Opcional: fecha a atividade atual para que o usuário não possa voltar
                } else {
                    // Se o login falhar
                    Toast.makeText(this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
