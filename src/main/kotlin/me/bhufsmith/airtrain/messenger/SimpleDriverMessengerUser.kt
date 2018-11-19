package me.bhufsmith.airtrain.messenger

import java.util.*

class SimpleDriverMessengerUser (name: String = "",
                                 trainMessageService: TrainMessageService): MessengerBaseUser(name, trainMessageService)
{
    var canDisturb:Boolean = true
        set( disturb ){
            if( disturb ){
                println("\t < - requesting pending messages - >")
                trainMessageService.sendPendingMessages(this.id, this.secretKey )
            }
            field = disturb
        }

    /**
     * A driver can always send themselves messages.
     */
    override fun sendMessage(message: String){
        this.trainMessageService.sendMessage(this.id, this.secretKey, this.id, message )
    }

    override fun validateKey(key: String): Boolean = this.secretKey == key

    override fun isDisturbable(): Boolean = canDisturb
}