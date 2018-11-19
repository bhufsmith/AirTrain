package me.bhufsmith.airtrain.messenger

import java.util.*

/**
 * The messenger user can be used
 */
class SimpleMessengerUser(name:String, messageService: TrainMessageService): MessengerBaseUser(name, messageService)
{

    override fun sendMessage(message: String){
        this.trainMessageService.sendMessage(this.id, this.secretKey, trainMessageService.retrieveDriverId(), message )
    }

    override fun validateKey(key: String): Boolean = this.secretKey == key

    override fun isDisturbable(): Boolean = true
}

