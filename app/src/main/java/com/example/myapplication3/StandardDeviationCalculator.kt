package com.example.myapplication3

class StandardDeviationCalculator {

    companion object{

        fun calculate(list : ArrayList<Float>) : Double{


            try {
                var sumValue = 0.0

                list.map {
                    sumValue += it
                }

                val meanValue = sumValue / list.size
                var sumDiff = 0.0

                list.map {
                    var diff = it - meanValue
                    var diffSq = diff * diff
                    sumDiff += diffSq
                }

                val sd = Math.sqrt(sumDiff / (list.size - 1))
                return sd
            }catch (e : Exception){
                e.printStackTrace()
            }
            return 0.0
        }
    }
}