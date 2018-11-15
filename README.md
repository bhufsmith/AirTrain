# AirTrain

The EWR AirTrain shuttles between terminals A & B.  
The trip takes 9 minutes from Terminal A to Terminal B, 
the return journey takes 11 minutes. 

The train waits 7.5 minutes between each trip except when there is a shift change for the driver, 
and an additional 5.5 minutes are added to allow for the drivers to swap places. 

Each driver is limited to an 8 hour shift

While the train is in transit, 
* if a message is sent to the driver, the sender receives ”Driver In Transit. Please try again later” as an automatic response.  
* For a fee, a sender can reply to the auto response with the message “Urgent” and have the driver receive the message immediately. 

Once a driver reaches a station, all pending messages are delivered to them.

Write a program in Java or Kotlin, that simulates a shuttle running for 24 hours with each driver receiving 5 random messages per hour, 2 of which are urgent. Design your solution so it can be easily extended to add additional stations to the route, and the simulation can be completed in a reasonably observable timeframe.
