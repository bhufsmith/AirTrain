package me.bhufsmith.airtrain.messenger

data class Message( val senderId:String,
                    val senderName:String,
                    val message:String,
                    val urgent: Boolean = false)