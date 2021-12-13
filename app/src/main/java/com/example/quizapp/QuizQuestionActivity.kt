package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgress: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var tvImage: ImageView
    private lateinit var tvOptionOne: TextView
    private lateinit var tvOptionTwo: TextView
    private lateinit var tvOptionThree: TextView
    private lateinit var tvOptionFour: TextView
    private lateinit var btn_submit: Button



//    private val progressBar = findViewById<ProgressBar>(R.id.progressBar)
//    private val tvProgress = findViewById<TextView>(R.id.tv_progress)
//    private val tvQuestion = findViewById<TextView>(R.id.tv_question)
//    private val tvImage = findViewById<ImageView>(R.id.tv_image)
//    private val tvOptionOne = findViewById<TextView>(R.id.tvOptionOne)
//    private val tvOptionTwo = findViewById<TextView>(R.id.tvOptionTwo)
//    private val tvOptionThree = findViewById<TextView>(R.id.tvOptionThree)
//    private val tvOptionFour = findViewById<TextView>(R.id.tvOptionFour)


    private var mCurrentPosition:Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        tvOptionOne = findViewById<TextView>(R.id.tvOptionOne)
        tvOptionTwo = findViewById<TextView>(R.id.tvOptionTwo)
        tvOptionThree = findViewById<TextView>(R.id.tvOptionThree)
        tvOptionFour = findViewById<TextView>(R.id.tvOptionFour)
        btn_submit = findViewById(R.id.btn_submit)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mQuestionsList = Constants.getQuestions()

        setQuestion()


        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btn_submit.setOnClickListener(this)


    }

    private fun setQuestion(){

        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        tvProgress = findViewById<TextView>(R.id.tv_progress)
        tvQuestion = findViewById<TextView>(R.id.tv_question)
        tvImage = findViewById<ImageView>(R.id.tv_image)


        val question = mQuestionsList!![mCurrentPosition - 1]

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size){
            btn_submit.text = "FINISH"
        }else{
            btn_submit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        tvProgress.text = "$mCurrentPosition" + "/" + progressBar.max

        tvQuestion.text = question!!.question
        tvImage.setImageResource(question.image)
        tvOptionOne.text = question.optionOne
        tvOptionTwo.text = question.optionTwo
        tvOptionThree.text = question.optionThree
        tvOptionFour.text = question.optionFour
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)
        options.add(2, tvOptionThree)
        options.add(3, tvOptionFour)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?){
        when(v?.id){
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
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, mUserName)
                        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                        startActivity(intent)
                        finish()
                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)


                    if(mCurrentPosition == mQuestionsList!!.size){
                        btn_submit.text = "FINISH"
                    }else{
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0

                }
            }
        }
    }

    private fun answerView(answer: Int, drawableview: Int){
        when(answer){
            1 -> {
                tvOptionOne.background = ContextCompat.getDrawable(this, drawableview)
            }
            2 -> {
                tvOptionTwo.background = ContextCompat.getDrawable(this, drawableview)
            }
            3 -> {
                tvOptionThree.background = ContextCompat.getDrawable(this, drawableview)
            }
            4 -> {
                tvOptionFour.background = ContextCompat.getDrawable(this, drawableview)
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border)
    }



}



