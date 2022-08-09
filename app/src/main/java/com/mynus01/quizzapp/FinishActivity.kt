package com.mynus01.quizzapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mynus01.quizzapp.databinding.FinishActivityBinding

class FinishActivity : AppCompatActivity() {

   private lateinit var binding: FinishActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.finish_activity)


        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        binding.apply {
            textViewNome.text = intent.getStringExtra(Constants.USER_NAME)
            tvScore.text = "Sua pontuacao total foi $correctAnswers de $totalQuestions"

        }
        binding.btnFinish.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}