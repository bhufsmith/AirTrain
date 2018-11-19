package me.bhufsmith.airtrain.messenger

interface Observable {

    fun subscribeForMessages( receiver: MessageReceiver )
    fun unsubscribe( receiver: MessageReceiver )
}