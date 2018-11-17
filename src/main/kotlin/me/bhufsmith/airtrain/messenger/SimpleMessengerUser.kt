package me.bhufsmith.airtrain.messenger

import java.util.*

/**
 * The messenger user can be used
 */
class SimpleMessengerUser(val name: String = "",
                          private val messageService: MessageService): MessengerUser
{
    val id = UUID.randomUUID().toString().substring(0, 7)

    private val secretKey:String = UUID.randomUUID().toString()
    private val doNotDisturb:Boolean = false
    private val messages = mutableListOf<String>()
    private val subscribers = mutableListOf<MessageSubscriber>()


    override fun sendMessage(receiverId:String, message: String){
        this.messageService.sendMessage(this.id, this.secretKey, receiverId, message )
    }

    override fun receiveMessage(message: Message){

    }

    override fun subscribeForMessages( subscriber: MessageSubscriber ): Boolean {

        return true
    }

    override fun validateKey(key: String): Boolean = this.secretKey == key

    override fun isDisturbable(): Boolean = !this.doNotDisturb
}

