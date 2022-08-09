package com.mynus01.quizzapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.mynus01.quizzapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionsBinding
    private var mCurrentPosition: Int = 1
    private lateinit var mQuestionsList: ArrayList<Question>
    private var mSelectedOptionPosition: Int = 0
    private var mUserName : String? = null
    private var mCorrectAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_questions)
        mQuestionsList = Constants.getQuestions()

        binding.apply {
            tvOptionOne.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionTwo.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionThree.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionFour.setOnClickListener(this@QuizQuestionsActivity)
            btnSubmit.setOnClickListener(this@QuizQuestionsActivity)
        }

        setQuestion()
    }

    private fun setQuestion() {
        defaultOptionsView()
        //For each question in questionList
        mQuestionsList.forEach { question ->
            Log.e("Questions", question.question)
        }

        val question: Question = mQuestionsList[mCurrentPosition - 1]

        binding.apply {
            tvImage.setImageResource(question.image)
            progressBar.progress = mCurrentPosition
            tvProgressBar.text = "$mCurrentPosition / ${progressBar.max}"
            tvQuestion.text = question.question
            tvOptionOne.text = question.optionOne
            tvOptionTwo.text = question.optionTwo
            tvOptionThree.text = question.optionThree
            tvOptionFour.text = question.optionFour
            if (mCurrentPosition == mQuestionsList.size) {
                btnSubmit.text = "TERMINAR QUIZZ"
            } else {
                btnSubmit.text = "ENVIAR"
            }
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        binding.apply {
            tvOptionOne.let { tv ->
                options.add(0, tv)
            }
            tvOptionTwo.let { tv ->
                options.add(1, tv)
            }
            tvOptionThree.let { tv ->
                options.add(2, tv)
            }
            tvOptionFour.let { tv ->
                options.add(3, tv)
            }
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_border
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border
        )

    }

    override fun onClick(view: View) {

        binding.apply {
            when (view.id) {
                R.id.tvOptionOne -> {
                    selectedOptionView(tvOptionOne, 1)
                }
                R.id.tvOptionTwo -> {
                    selectedOptionView(tvOptionTwo, 2)
                }
                R.id.tvOptionThree -> {
                    selectedOptionView(tvOptionThree, 3)
                }
                R.id.tvOptionFour -> {
                    selectedOptionView(tvOptionFour, 4)
                }
                R.id.btnSubmit -> {
                    if(mSelectedOptionPosition == 0){
                        mCurrentPosition++

                        when{
                            mCurrentPosition <= mQuestionsList.size -> {
                                setQuestion()
                            }
                            else ->{
                                val intent = Intent(this@QuizQuestionsActivity, FinishActivity::class.java)
                                intent.putExtra(Constants.USER_NAME, mUserName)
                                intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                                intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList.size)
                                startActivity(intent)
                                finish()
                            }
                        }
                    } else {
                        val question = mQuestionsList[mCurrentPosition - 1]
                        if(question.correctAnswer != mSelectedOptionPosition){
                            answerView(mSelectedOptionPosition, R.drawable.wrong_border)
                        } else {
                            mCorrectAnswers++
                        }
                        answerView(question.correctAnswer, R.drawable.win_border)

                        if(mCurrentPosition == mQuestionsList.size){
                            btnSubmit.text = "TERMINAR QUIZZ"
                        } else {
                            btnSubmit.text = "PROXIMA PERGUNTA"
                        }
                        mSelectedOptionPosition = 0
                    }
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        binding.apply {
            when (answer) {
                1 -> {
                    tvOptionOne.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                    )
                }
                2 -> {
                    tvOptionTwo.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                    )
                }
                3 -> {
                    tvOptionThree.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                    )
                }
                4 -> {
                    tvOptionFour.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                    )
                }
            }
        }
    }
}