package me.bhufsmith.airtrain.train

import me.bhufsmith.airtrain.messenger.*
import me.bhufsmith.airtrain.time.TimeHelper
import java.util.*

class AnoyingPassengers(private val messageService: TrainMessageService,
                        private val timeHelper: TimeHelper,
                        private val messagesPerHour:Int = 5,
                        private val urgenPerHour:Int = 2): Runnable, MessageReceiver {


    private val trainPassengers = mutableListOf<SimpleMessengerUser>()
    private var hourlyUrgent = urgenPerHour

    //Register some users.
    private fun registerUsers(){

        trainPassengers.forEach{ it.unsubscribe( this) }
        trainPassengers.clear()

        //Select 5 names to use and register the users.
        var messengerUser: SimpleMessengerUser
        for (i in 0 until 5) {
            messengerUser = messageService.registerUser( RandomNess.randomName() )
            trainPassengers.add(messengerUser)
            messengerUser.subscribeForMessages( this )
        }
    }

    private fun sendMessagesForHour(){
        hourlyUrgent = urgenPerHour

        val messageMaxInterval: Int = timeHelper.minPerHour / messagesPerHour
        var messageCountDown = this.messagesPerHour

        var waitTime:Float
        var messageIndex: Int
        var minTimeSpent: Float = 0.0f

        while( messageCountDown > 0 ){
            waitTime = (Math.random() * messageMaxInterval).toFloat()

            timeHelper.waitFor( waitTime )
            minTimeSpent += waitTime

            //Since both, passenger list, and messageOrder list are in random order, we can use the same index for both
            trainPassengers[ messageCountDown - 1 ].sendMessage( RandomNess.randomMessage() )

            messageCountDown--
        }

        //Wait out the remainder of the hour
        timeHelper.waitFor(timeHelper.minPerHour - minTimeSpent )
    }


    override fun messageArrived(receiverId: String, message: Message) {
        val sender = trainPassengers.find { it.id == receiverId }

        println("\t - [${timeHelper.currentTime()}: To:${sender?.name}  From:${message.senderName}]: ${message.message}")

        if( message.senderName == "AUTO_REPLY" && hourlyUrgent > 0 ){
            sender?.sendMessage("urgent")
            hourlyUrgent--
        }
    }

    override fun run() {

        while( !timeHelper.stopTime() ) {
            registerUsers()
            sendMessagesForHour()
        }

    }

}