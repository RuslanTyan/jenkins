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
    for (i in list1) {
        for (y in list2) {
            println '' + i + y
            if (y+i == 'two2') {
                return false
            }
        }
    }
    return true
}