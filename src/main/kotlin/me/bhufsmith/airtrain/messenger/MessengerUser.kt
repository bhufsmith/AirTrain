package me.bhufsmith.airtrain.messenger

interface MessengerUser: MessageReceiver {

    val id:String
    val name:String
    /*
     * Send a message through the message service to the desired user
     */
    fun sendMessage(message: String)

    fun isDisturbable(): Boolean

    fun validateKey(key: String): Boolean
}