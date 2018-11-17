package me.bhufsmith.airtrain.messenger

import java.lang.IllegalArgumentException

class SimpleMessageService: MessageService {

    //Here we will store the users that are registered with the service
    //The users will be looked up by their Id.
    private val users = mutableMapOf<String, SimpleMessengerUser >()

    override fun registerUser(name: String): SimpleMessengerUser {

        if( name.isBlank() ){
            throw IllegalArgumentException("Can not have an empty name")
        }
        val newUser = SimpleMessengerUser(name,this)

        this.users.put( newUser.id, newUser )
        return newUser
    }

    override fun sendMessage(senderId: String, senderKey:String, receiverId: String, message: String) {
        val sender = this.users.get( senderId )

        if( sender == null || !sender.validateKey( senderKey ) ){
            throw InvalidUserException("Sender could not be validated! Make sure this is a registered user.")
        }

        val receiver = this.users.get( receiverId )
        if ( receiver != null ){

            val message = Message( sender!!.id, sender.name, message )
            receiver.receiveMessage( message )
        }

    }
}