package com.antartica.koltin_playground.ground.dp

fun main() {
    val list = (0..10).toList()

    val groupedList = list.groupingBy { it % 2 == 0 }.eachCount()
    println(groupedList)
}