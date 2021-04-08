package ru.grandi.bambino.changeablerecycler

class Repository(private val provider: LocalProvider) {

    fun getAllChangeableItems() = provider.changeableItems

}