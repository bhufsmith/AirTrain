package me.bhufsmith.airtrain.messenger

import me.bhufsmith.airtrain.driver.InvalidDriverException
import me.bhufsmith.airtrain.time.TimeHelper
import java.lang.IllegalArgumentException

class TrainDriverMessageService(private val driverKey:String,
                                private val timeHelper: TimeHelper) : TrainMessageService {

    //Here we will store the users that are registered with the service
    //The users will be looked up by their Id.
    private val users = mutableMapOf<String, MessengerUser >()

    private val pendingMessages = mutableMapOf<String, MutableList<Message>>()
    private val userCharges = mutableMapOf<String, Float>()

    private var currentDriver: MessengerUser? = null

    private val AUTO_REPLY = "Driver In Transit. Please try again later. Reply with \"urgent\" to deliver immediately. " +
                            "A $1 fee will be charged to your account."

    val AUTO_REPLY_NAME = "AUTO_REPLY"
    val URGENT_TEXT = "urgent"

    override fun registerDriver(name: String, driverKey: String):SimpleDriverMessengerUser {
        synchronized( this.users ) {

            if(driverKey != this.driverKey){
                throw InvalidDriverException("Can not register driver")
            }

            if (name.isNullOrBlank()) {
                throw IllegalArgumentException("Can not have an empty name")
            }
            val newUser = SimpleDriverMessengerUser(name, this)
            this.users[newUser.id] = newUser

            return newUser
        }
    }

    override fun registerUser(name: String): SimpleMessengerUser {
        synchronized( this.users ){
            if( name.isNullOrBlank() ){
                throw IllegalArgumentException("Can not have an empty name")
            }
            val newUser = SimpleMessengerUser(name,this)


            this.users.put( newUser.id, newUser )
            return newUser
        }

    }

    override fun setCurrentDriver(driver: SimpleDriverMessengerUser) {
        val driverUser = this.users[ driver.id ]

        if( driver ==  driverUser ){
            currentDriver = driverUser
        }
    }

    override fun retrieveDriverId(): String = if(this.currentDriver != null) this.currentDriver!!.id else ""

    override fun sendPendingMessages(userId: String, userKey: String) {

        var user:MessengerUser? = null

        synchronized( this.users ) {
            user = this.users.get(userId)
        }

        if (user == null || ! user!!.validateKey(userKey)) {
            throw InvalidUserException("Sender could not be validated! Make sure this is a registered user.")
        }
        else {
            synchronized(pendingMessages) {
                val pending = this.pendingMessages[user!!.id]

                pending?.forEach { message ->
                    user!!.messageArrived(userId, message)
                }
                pending?.clear()
            }
        }

    }

    private fun sendLastMessageAsUrgen(sender: MessengerUser, receiver:MessengerUser){
        synchronized( this.pendingMessages ){

            val messages = this.pendingMessages[receiver.id]
            val message = messages?.findLast { it.senderId == sender.id }
            val urgentMessage = message?.copy(urgent = true)

            if(urgentMessage != null){
                messages.remove( message )
                receiver.messageArrived( receiver.id, urgentMessage )
            }
        }

    }

    override fun sendMessage(senderId: String, senderKey:String, receiverId: String, message: String) {
        val sender = this.users.get( senderId )

        if( sender == null || !sender.validateKey( senderKey ) ){
            throw InvalidUserException("Sender could not be validated! Make sure this is a registered user.")
        }

        val receiver = this.users[ receiverId ]
        if ( receiver != null ){

            val msg = Message( sender.id, sender.name, message, timeHelper.currentTime() )

            when {
                //Just send the message
                receiver.isDisturbable() -> {
                    receiver.messageArrived(receiverId, msg)
                }

                //If the message contains the word "urgent", then find the last message pending and send it
                msg.message.equals( URGENT_TEXT, true ) -> {
                    sendLastMessageAsUrgen( sender, receiver )
                }

                else -> {
                    synchronized( this.pendingMessages ) {
                        //If there is already a list in the pending message map, then get it, otherwise initialize one.
                        (this.pendingMessages.getOrPut(receiverId) { mutableListOf<Message>() })
                                .add(msg)
                    }

                    val autoreply = Message(receiverId, AUTO_REPLY_NAME, AUTO_REPLY, timeHelper.currentTime())
                    sender.messageArrived( senderId, autoreply )
                }
            }
        }

    }
}