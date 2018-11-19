package me.bhufsmith.airtrain.messenger

interface MessageReceiver {

    fun messageArrived( receiverId:String, message:Message )
}