package me.bhufsmith.airtrain.messenger

import java.util.*

/**
 * The messenger user can be used
 */
class MessengerUser( val id: String = UUID.randomUUID().toString().substring(0, 7),
                     val name: String = "",

                     private val key:String,
                     private val messageService: MessageService)
{
    private val doNotDisturb:Boolean = false
    private val messages = mutableListOf<String>()
    private val subscribers = mutableListOf<MessageSubscriber>()


    fun sendMessage(receiverId:String, message: String){
        this.messageService.sendMessage(this.id, this.key, receiverId, message )
    }

    fun receiveMessage(message: Message){

    }

    fun subscribeForMessages( subscriber: MessageSubscriber ): Boolean {

        return true
    }
}

