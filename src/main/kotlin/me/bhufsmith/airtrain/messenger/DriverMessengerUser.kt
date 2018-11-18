package me.bhufsmith.airtrain.messenger

import java.util.*

class DriverMessengerUser (val name: String = "",
                           private val trainMessageService: TrainMessageService): MessengerUser
{
    val id = UUID.randomUUID().toString().substring(0, 7)

    private val secretKey:String = UUID.randomUUID().toString()
    private val messages = mutableListOf<Message>()

    var canDisturb:Boolean = true

    /**
     * A driver can always send themselves messages.
     */
    override fun sendMessage(message: String){
        val msg = Message(this.id, this.name, message, false)
        this.receiveMessage( msg )
    }

    override fun receiveMessage(message: Message){
        synchronized( this.messages ) {
            this.messages.add(message)
        }
    }

    override fun validateKey(key: String): Boolean = this.secretKey == key

    override fun isDisturbable(): Boolean = canDisturb
}