#!groovy

def call(list1, list2) {
    println list1.toString()
    println list2.toString()
    list1.each {
        println it
        list2.each {
            println it
        }
    }
    return true
}