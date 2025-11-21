package dev.csse.kubiak.btdemo.bt

import android.bluetooth.BluetoothSocket
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.IOException

class TransferFailedException:
  IOException("Reading incoming data failed")


class BTDataTransferService(
  private val socket: BluetoothSocket
) {
  fun listenForIncomingMessages(): Flow<BTMessage> {
    return flow {
      if(!socket.isConnected) {
        Log.e("BTDataTransferService", "Socket not connected")
        return@flow
      }
      val buffer = ByteArray(1024)
      while(true) {
        val byteCount = try {
          socket.inputStream.read(buffer)
        } catch(e: IOException) {
          throw TransferFailedException()
        }

        val message = buffer.decodeToString(
          endIndex = byteCount
        ).toBTMessage(
          isFromLocalUser = false
        )
        Log.d("BTDataTransferService", "Received: $message")
        emit(message)
      }
    }.flowOn(Dispatchers.IO)
  }

  suspend fun sendMessage(bytes: ByteArray): Boolean {
    return withContext(Dispatchers.IO) {
      try {
        socket.outputStream.write(bytes)
      } catch(e: IOException) {
        e.printStackTrace()
        return@withContext false
      }

      true
    }
  }
}