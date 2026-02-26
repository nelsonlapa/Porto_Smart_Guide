package com.example.portosmartguide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Dizemos qual é o XML (Design) que este ecrã vai usar
        setContentView(R.layout.activity_login)

        // 1. Encontrar o botão pelo ID que definimos no XML
        val btnContinue = findViewById<Button>(R.id.btnContinuePassword)

        // 2. Dizer à app o que fazer quando o utilizador clica no botão
        btnContinue.setOnClickListener {
            // Intent é a "Intenção" de viajar desta Activity (LoginActivity) para outra (MainActivity)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // finish() serve para "destruir" o ecrã de Login.
            // Assim, se no mapa o utilizador clicar na seta de voltar para trás do telemóvel, a app fecha em vez de voltar ao Login.
            finish()
        }
    }
}