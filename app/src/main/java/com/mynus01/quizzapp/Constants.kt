package com.mynus01.quizzapp

object Constants {

    const val USER_NAME : String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"


    fun getQuestions():ArrayList<Question>{
        val questionsList = ArrayList<Question>()

        val question1 = Question(
            1, "Essa bandeira pertence a qual pais?", R.drawable.germany, "Belgica", "Austria", "Alemanha", "Espanha", 3
        )
        questionsList.add(question1)


        val question2 = Question(
            2, "Essa bandeira pertence a qual pais?", R.drawable.egypt, "Austria", "Egito", "Paraguai", "Dinamarca", 2
        )
        questionsList.add(question2)

        return questionsList
    }

}