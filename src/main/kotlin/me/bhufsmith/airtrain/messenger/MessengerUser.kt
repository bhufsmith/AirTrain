package me.bhufsmith.airtrain.messenger

interface MessengerUser {

    /*
     * Send a message through the message service to the desired user
     */
    fun sendMessage(receiverId:String, message: String)

    /*
     * This function will be called when the message service sends a message to this user.
     */
    fun receiveMessage(message: Message)

    /*
     * This function is to subscribe for messages that arrive on this user account.
     */
    fun subscribeForMessages( subscriber: MessageSubscriber ): Boolean

    fun isDisturbable(): Boolean

    fun validateKey(key: String): Boolean
}