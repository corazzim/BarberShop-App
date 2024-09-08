package com.example.barbershop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Main_cadastro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_cadastro)

        val auth = FirebaseAuth.getInstance()

        val nome: EditText = findViewById(R.id.txt_nome)
        val phone: EditText = findViewById(R.id.txt_phone)
        val email: EditText = findViewById(R.id.txt_email)
        val senha: EditText = findViewById(R.id.txt_senha)
        val cadastro: Button = findViewById(R.id.btn_enviar)
        val voltar: ImageView = findViewById(R.id.btn_voltar)

        cadastro.setOnClickListener {
            val nomeText = nome.text.toString().trim()
            val phoneText = phone.text.toString().trim()
            val emailText = email.text.toString().trim()
            val senhaText = senha.text.toString().trim()

            // Verifica se todos os campos estão preenchidos
            if (nomeText.isEmpty() || phoneText.isEmpty() || emailText.isEmpty() || senhaText.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(emailText, senhaText).addOnCompleteListener { cadastro ->
                    if (cadastro.isSuccessful) {
                        Toast.makeText(this, "Sucesso ao cadastrar usuário!", Toast.LENGTH_LONG).show()
                        // Cria uma Intent para iniciar a MainActivity (página de login)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        // Finaliza a atividade atual para que o usuário não possa voltar a ela
                        finish()
                    } else {
                        Toast.makeText(this, "Erro ao cadastrar usuário: ${cadastro.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        voltar.setOnClickListener {
            // Cria uma Intent para iniciar a MainActivity (página de login)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Finaliza a atividade atual
            finish()
        }
    }
}
