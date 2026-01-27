 package com.example.dailyquiztest.core

interface Const {
    abstract class Base(private val value: String) : Const {
        override fun toString(): String {
            return value
        }
    }

    object WelcomeContDesc : Base("welcomeScreen")
    object LoadingContDesc : Base("loadingScreen")
}