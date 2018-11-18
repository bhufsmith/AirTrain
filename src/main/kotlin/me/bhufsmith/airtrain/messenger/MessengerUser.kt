package me.bhufsmith.airtrain.messenger

interface MessengerUser {

    /*
     * Send a message through the message service to the desired user
     */
    fun sendMessage(message: String)

    /*
     * This function will be called when the message service sends a message to this user.
     */
    fun receiveMessage(message: Message)

    fun isDisturbable(): Boolean

    fun validateKey(key: String): Boolean
}