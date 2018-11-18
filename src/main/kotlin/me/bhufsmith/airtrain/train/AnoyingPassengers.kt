package me.bhufsmith.airtrain.train

import me.bhufsmith.airtrain.messenger.MessengerUser
import me.bhufsmith.airtrain.messenger.SimpleMessengerUser
import me.bhufsmith.airtrain.messenger.TrainMessageService
import me.bhufsmith.airtrain.time.TimeHelper
import java.util.*

class AnoyingPassengers(private val messageService: TrainMessageService,
                        private val timeHelper: TimeHelper,
                        private val messagesPerHour:Int = 5,
                        private val urgenPerHour:Int = 2): Runnable {


    val anoyingMessages = listOf<String>("Are we there yet!!!", "How much longer till my stop?",
                                        "Do you like driving trains?", "Why is there a squeaking noise in the back?",
                                        "There is a drunk guy throwing up in the back!!!!!",
                                        "Why did we stop for so long?", "You really need to fix these lights....",
                                        "Slow down.... I don't want to get to work yet!", "Did the train break down?",
                                        "Can you let me off between stops?", "Some guy is breathing on me... can you do something?",
                                        "WTF, why are there so many people on the train???", "Why is there no one else on the train?",
                                        "Do you have bottle service?", "Come on... is it really that hard to drive a train?",
                                        "Can you do something about all these bumps?")

    val passengerNames = listOf("Jake, State-farm", "Your, Mom", "Tom, Clancy", "Judy, Judge", "James, Bond",
                                "James", "Bill", "Ted", "Brian", "Lauren", "Christen", "Audrey", "Meagan", "Lisa")

    private val trainPassengers = mutableListOf<SimpleMessengerUser>()


    //Register some users.
    private fun registerUsers(){

        trainPassengers.clear()
        val userOrder = (0 until passengerNames.size).shuffled()

        //Select 5 names to use and register the users.
        var messengerUser: MessengerUser
        for (i in 0 until 5) {
            messengerUser = messageService.registerUser(passengerNames[userOrder[i]])
            trainPassengers.add(messengerUser)
        }
    }

    private fun sendMessagesForHour(){

        val messageOrder = (0 until anoyingMessages.size).shuffled()

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
            messageIndex = messageOrder[messageCountDown - 1]
            trainPassengers[ messageCountDown - 1 ].sendMessage( anoyingMessages[ messageIndex])

            messageCountDown--
        }

        //Wait out the remainder of the hour
        timeHelper.waitFor(timeHelper.minPerHour - minTimeSpent )
    }

    override fun run() {

        while( !timeHelper.stopTime() ) {
            registerUsers()
            sendMessagesForHour()
        }

    }

}