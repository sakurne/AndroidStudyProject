package com.example.kotlintraining

import java.lang.Exception
import java.time.LocalDateTime

class TrainingPart2 {

    enum class Type {
        DEMO, FULL
    }

    data class User(val id: Long, val name: String, val age: Int, val type: Type) {
        val startTime: LocalDateTime by lazy { LocalDateTime.now() }
    }

    fun task4() {
        User(1, "Вася", 44, Type.DEMO).apply {
            println(startTime)
            Thread.sleep(1000)
            println(startTime)
        }
    }

    private fun task5(): List<User> {
        val users: List<User> = arrayListOf(
            User(1, "Вася", 44, Type.DEMO)
        ).apply {
            add(User(2, "Петя", 12, Type.FULL))
            add(User(3, "Инокентий", 30, Type.FULL))
        }
        return users
    }

    private fun task6(): List<User> {
        return task5().filter { it.type == Type.FULL }
    }

    private fun task7() {
        val userNames = task6().map { it.name }
        println(userNames.take(1))
        println(userNames.takeLast(1))
    }

    private fun User.isAdult() {
        if (age >= 18)
            println("user is adult")
        else
            throw Exception("user is not adult")
    }

    private fun task8(user: User) {
        user.isAdult()
    }

    interface AuthCallBack {
        fun authSuccess()
        fun authFailed()
    }

    private fun task9(): AuthCallBack {
        val authObject = object : AuthCallBack {
            override fun authFailed() {
                println("auth failed")
            }
            override fun authSuccess() {
                println("auth succeeded")
            }
        }
        return authObject
    }

    // task 10-11
    private inline fun auth(user: User, updateCache: () -> Unit) {
        try {
            user.isAdult()
            task9().authSuccess()
            updateCache()
        } catch (e: Exception) {
            task9().authFailed()
        }
    }

    fun task10() {
        auth(
            User(2, "Петя", 12, Type.FULL)
        ) { println("cache updated") }
    }

    // task 12
    sealed class Action

    open class Registration : Action()
    open class Login(val user: User) : Action()
    open class Logout : Action()

    // task 13
    fun doAction(action: Action) {
        when (action) {
            is Registration -> println("registration")
            is Login -> auth(
                action.user
            ) { println("cache updated") }
            is Logout -> println("logout")
        }
    }
}
