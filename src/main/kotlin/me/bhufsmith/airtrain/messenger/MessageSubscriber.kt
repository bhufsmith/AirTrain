package me.bhufsmith.airtrain.messenger

interface MessageSubscriber {
    fun receiveMessage( message:Message )
}