package me.bhufsmith.airtrain.messenger

/**
 *This interface is the template for a message service
 *The senderKey should be generated inside the registerUser function so that it can be kept private
 */

interface MessageService {

    /**
     * Registers a user with the message service
     * That process includes generating a secret for sender validation
     */
    fun registerUser(): MessengerUser

    /**
     * This function will send a message to a receiver from a sender.
     * The sender must be validated by the sender key.
     */
    fun sendMessage(senderId:String, senderKey:String, receiverId: String, message: String)

}