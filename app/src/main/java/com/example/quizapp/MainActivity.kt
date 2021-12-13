package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var btnStart: Button
    private lateinit var et_name: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        btnStart = findViewById<Button>(R.id.btn_start)
        et_name = findViewById(R.id.et_name)

        btnStart.setOnClickListener{
            if (et_name.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, QuizQuestionActivity::class.java)
                intent.putExtra(Constants.USER_NAME, et_name.text.toString())
                finish()
                startActivity(intent)
            }
        }
    }
}