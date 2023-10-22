package dev.rcode.android.data.main.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import dev.rcode.android.core.base.error.DataBaseException
import dev.rcode.android.data.local.db.ChatDatabase
import dev.rcode.android.domain.model.ChatGroup
import javax.inject.Inject
import dev.rcode.android.domain.repository.ChatGroupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import java.lang.Exception

class ChatGroupRepositoryImpl @Inject constructor(
    private val db: ChatDatabase,
    private val fireBaseRealTimeDatabase: FirebaseDatabase
) : ChatGroupRepository {

    private val chatGroupsReference = fireBaseRealTimeDatabase.getReference("chat_groups")
    override suspend fun createChatGroup(chatGroupName: String): Flow<Result<ChatGroup>> {

        val currentTime = System.currentTimeMillis()
        val insertedId = db.chatGroupDao().createChatGroup(
            dev.rcode.android.data.local.db.entities.ChatGroupEntity(
                0,
                chatGroupName = chatGroupName,
                createdOn = currentTime,
                false
            )
        )

        return flow {
            if (insertedId != -1L) {
                emit(Result.success(ChatGroup(insertedId, chatGroupName, currentTime, false)))
            } else {
                emit(Result.failure(DataBaseException()))
            }
        }
        //dbReference.child("chat_groups")

        //var newChatGroupRef = chatGroupsReference.push({})
        // newChatGroupRef.key
        // Insert the object into local database

        /*val dbReference = fireBaseRealTimeDatabase.reference
        val newChatGroup = ChatGroup(0, chatGroupName, System.currentTimeMillis())
        val newChatGroupRef = dbReference.child("chat_groups").child(chatGroupName).setValue(newChatGroup.getNewObjectJson())

        return callbackFlow {

            dbReference.child("chat_groups").child(chatGroupName).setValue(newChatGroup.getNewObjectJson())
                .addOnSuccessListener { result ->
                    Log.d("RealTimeDataBase", "chat group created")
                    trySend(Result.success(
                            ChatGroup(
                                0,
                                chatGroupName,
                                System.currentTimeMillis()
                            )
                        )
                    )
                }
                .addOnFailureListener {
                    Log.d("RealTimeDataBase", "chat group creation failed")
                    trySend(Result.failure(NoDataException()))
                }

        }*/

    }

    override suspend fun deleteChatGroup(chatGroup: ChatGroup): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllChatGroups(): Flow<Result<List<ChatGroup>>> {

        /*return chatGroupsReference.snapshots.map { snapshot ->

            var chatGroups = snapshot.children.mapNotNull {
                var chatGroupObj = (it.value as HashMap<*, *>)
                ChatGroup(chatGroupObj["chat_group_id"] as Long, chatGroupObj["name"] as String, chatGroupObj["created_at"] as Long)
            }
            Result.success(chatGroups)
        }*/

        return db.chatGroupDao().getAllChatGroups().mapNotNull { chatGroupEntityList ->
            Result.success(chatGroupEntityList.mapNotNull {
                it.toModel()
            })
        }.catch {
            emit(Result.failure(DataBaseException()))
        }

        /*return callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        var chatGroups = snapshot.children.mapNotNull {
                            var chatGroupObj = (it.value as HashMap<*, *>)
                            ChatGroup(chatGroupObj["chat_group_id"] as Long, chatGroupObj["name"] as String, chatGroupObj["created_at"] as Long)
                            //var chatGroupObj = Gson().fromJson(it.value.toString(), ChatGroupDto::class.java)
                            //ChatGroup(chatGroupObj.chat_group_id, chatGroupObj.name, chatGroupObj.created_at)

                        }
                        trySend(Result.success(chatGroups))
                    } catch (e: Exception){
                        trySend(Result.failure(e))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Result.failure(error.toException()))
                }
            }

            chatGroupsReference.addValueEventListener(listener)

            awaitClose {
                chatGroupsReference.removeEventListener(listener)
            }
        }*/
    }

    override suspend fun toggleMuteForChatGroup(chatGroup: ChatGroup): Flow<Result<ChatGroup>> {
        chatGroup.isMuted = !chatGroup.isMuted
        var updateStatus = 0
        try {
            updateStatus = db.chatGroupDao()
                .updateChatGroup(
                    dev.rcode.android.data.local.db.entities.ChatGroupEntity.fromModel(
                        chatGroup
                    )
                )
        } catch (e: Exception) {
            Log.e(this::class.java.simpleName, e.message?: "Some error occurred")
        }
        return flow {

            if(updateStatus != -1)
                emit(Result.success(chatGroup))
            else
                emit(Result.failure(DataBaseException()))

        }
    }

    override suspend fun getChatGroup(chatGroupName: String): Flow<Result<ChatGroup>> {
        return db.chatGroupDao().getChatGroupDetails(chatGroupName).map {
            Result.success(it.toModel())
        }
    }

    suspend fun <T> makeIOCall(
        dispatcher: CoroutineDispatcher,
        call: suspend () -> T
    ): Result<T> = runCatching {
        withContext(dispatcher) {
            call.invoke()
        }
    }

}