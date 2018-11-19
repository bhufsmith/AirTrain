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

## Build

The program is built using kotlin against java 1.8.
The build script is gradle. 
The gradle wrapper has been included so that you will not need to have gradle installed to compile this project. 

In the root directory of the project. 

Linux / MacOS
``` ./gradlew clean build ```

Windows 
``` gradlew.sh clean build ```

The build produces a "Fat Jar" containing everything that you need to run the simulator. 
This can be found in ``` build/libs/AirTrain-1.0-SNAPSHOT.jar ```    

## Run   

You will need java 1.8 or later installed to run ths. 
To run the project, you can execute the following in the root directory, after build. 
``` java -jar build/libs/AirTrain-1.0-SNAPSHOT.jar ```

## Modify 

In ``` AirTrain/src/main/kotlin/me/bhufsmith/airtrain/AirTrainSim.kt ``` You will find the main method. 

There are two areas of interest here.   
Line 19: ``` val timeHelper = TimeHelper( 0.001f) ```  
Feel free to modify the argument to TimeHelper to adjust the time scale of the program. 

With the current value, the sim should complete in about 1 min 45 seconds. 

There will be the following, which starts on line 25   
```  
    val route = Route()
    route.addStation("A", 0.0f, 0.0f)
    route.addStation("B", 9.0f, 11.0f)


    //UNCOMMENT THE FOLLOWING LINE TO ADD ANOTHER STATION
    //route.addStation("C", 22.5f, 17.5f)
```

You may uncomment the line, and or add new stations as desired.    
Then re-build as shown above, and run the program. 
