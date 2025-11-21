package dev.csse.kubiak.btdemo.bt

data class BTMessage(
  val message: String,
  val senderName: String,
  val isFromLocalUser: Boolean
) {
  fun toByteArray(): ByteArray {
    return "$senderName#$message".encodeToByteArray()
  }
}

fun String.toBTMessage(isFromLocalUser: Boolean): BTMessage {
  val name = substringBeforeLast("#")
  val message = substringAfter("#")
  return BTMessage(
    message = message,
    senderName = name,
    isFromLocalUser = isFromLocalUser
  )
}
