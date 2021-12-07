package com.example.workoutapp

class constants {

    companion object {
        fun defaultExcercise(): ArrayList<excercise> {
            val excerciseList = ArrayList<excercise>()


            //limited exercise list have been added. To add list of Exercise follow the sequence (id,image,boolean(false),boolean(false))
            //id starts from 1-> in order to show output
            //id is integer, not array index
            //add the exercise variable to arraylist-> exceriseList

            val jumpingJacks = excercise(
                1,
                "Jumping Jacks",
                R.drawable.jacks,
                false,
                false
            )
            excerciseList.add(jumpingJacks)

            val Inchworm = excercise(
                2,
                "Inchworm walk out to shoulder tap",
                R.drawable.inchup,
                false,
                false
            )
            excerciseList.add(Inchworm)

            val squats = excercise(
                3,
                "squats",
                R.drawable.sq,
                false,
                false
            )
            excerciseList.add(squats)

            val burps = excercise(
                4,
                "burps",
                R.drawable.burp,
                false,
                false
            )
            excerciseList.add(burps)
            return excerciseList
        }
    }
}