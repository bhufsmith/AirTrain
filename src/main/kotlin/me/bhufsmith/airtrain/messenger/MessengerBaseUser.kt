package me.bhufsmith.airtrain.messenger

import java.util.*

abstract class MessengerBaseUser(override val name: String = "",
                                 protected val trainMessageService: TrainMessageService): MessengerUser, Observable {

    override val id = UUID.randomUUID().toString().substring(0, 7)
    protected val secretKey:String = UUID.randomUUID().toString()

    protected val subscribers = mutableListOf<MessageReceiver>()

    override fun subscribeForMessages( receiver: MessageReceiver ){
        subscribers.add(receiver)
    }

    override fun unsubscribe( receiver: MessageReceiver ){
        this.subscribers.remove( receiver )
    }

    override fun messageArrived(receiverId:String, message: Message) {
        subscribers.forEach{ it.messageArrived( receiverId, message )}
    }
}