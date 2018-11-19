package me.bhufsmith.airtrain.train

import java.util.concurrent.ThreadLocalRandom

class RandomNess {

    companion object {

        val anoyingMessages = listOf<String>("Are we there yet!!!", "How much longer till my stop?",
                "Do you like driving trains?", "Why is there a squeaking noise in the back?",
                "There is a drunk guy throwing up in the back!!!!!",
                "Why did we stop for so long?", "You really need to fix these lights....",
                "Slow down.... I don't want to get to work yet!", "Did the train break down?",
                "Can you let me off between stops?", "Some guy is breathing on me... can you do something?",
                "WTF, why are there so many people on the train???", "Why is there no one else on the train?",
                "Do you have bottle service?", "Come on... is it really that hard to drive a train?",
                "Can you do something about all these bumps?")

        val names = listOf("Jake, State-farm", "Your, Mom", "Tom, Clancy", "Judy, Judge", "James, Bond",
                                 "James", "Bill", "Ted", "Brian", "Lauren", "Christen", "Audrey", "Meagan", "Lisa")


        fun randomName():String = names[  ThreadLocalRandom.current().nextInt(0, names.size ) ]
        fun randomMessage():String = anoyingMessages[ ThreadLocalRandom.current().nextInt(0, anoyingMessages.size)]
    }
}