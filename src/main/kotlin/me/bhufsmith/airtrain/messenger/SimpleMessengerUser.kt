package me.bhufsmith.airtrain.messenger

import java.util.*

/**
 * The messenger user can be used
 */
class SimpleMessengerUser(val name: String = "",
                          private val trainMessageService: TrainMessageService): MessengerUser
{
    val id = UUID.randomUUID().toString().substring(0, 7)

    private val secretKey:String = UUID.randomUUID().toString()
    private val messages = mutableListOf<String>()

    override fun sendMessage(message: String){
        this.trainMessageService.sendMessage(this.id, this.secretKey, trainMessageService.retrieveDriverId(), message )
    }

    override fun receiveMessage(message: Message){

    }

    override fun validateKey(key: String): Boolean = this.secretKey == key

    override fun isDisturbable(): Boolean = true
}

