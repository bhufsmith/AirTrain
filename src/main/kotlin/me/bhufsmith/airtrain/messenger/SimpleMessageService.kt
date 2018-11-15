package me.bhufsmith.airtrain.messenger

class SimpleMessageService: MessageService {


    //Here we will store the users that are registered with the service
    //The users will be looked up by their Id.
    private val users = mutableMapOf<String, MessengerUser >()


    override fun registerUser(): MessengerUser {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendMessage(senderId: String, senderKey:String, receiverId: String, message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}