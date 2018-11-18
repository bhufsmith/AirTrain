package me.bhufsmith.airtrain.messenger

import java.lang.IllegalArgumentException

class TrainDriverMessageService(private val driverKey:String) : TrainMessageService {

    //Here we will store the users that are registered with the service
    //The users will be looked up by their Id.
    private val users = mutableMapOf<String, SimpleMessengerUser >()

    private var currentDriver: SimpleMessengerUser? = null

    override fun registerDriver(name: String, driverKey: String):SimpleMessengerUser {
        if( name.isBlank() ){
            throw IllegalArgumentException("Can not have an empty name")
        }
        val newUser = SimpleMessengerUser(name,this)
        currentDriver = newUser


        this.users.put( newUser.id, newUser )
        return newUser
    }

    override fun registerUser(name: String): SimpleMessengerUser {

        if( name.isBlank() ){
            throw IllegalArgumentException("Can not have an empty name")
        }
        val newUser = SimpleMessengerUser(name,this)

        this.users.put( newUser.id, newUser )
        return newUser
    }

    override fun retrieveDriverId(): String = if(this.currentDriver != null) this.currentDriver!!.id else ""

    override fun sendMessage(senderId: String, senderKey:String, receiverId: String, message: String) {
        val sender = this.users.get( senderId )

        if( sender == null || !sender.validateKey( senderKey ) ){
            throw InvalidUserException("Sender could not be validated! Make sure this is a registered user.")
        }

        val receiver = this.users.get( receiverId )
        if ( receiver != null ){

            val msg = Message( sender!!.id, sender.name, message )
            receiver.receiveMessage( msg )
            println("\t - [${sender.name} to ${receiver.name}] - ${msg.message}")
        }

    }
}