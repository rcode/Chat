package dev.rcode.android.data.main.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.database.FirebaseDatabase
import dev.rcode.android.data.local.db.ChatDatabase
import dev.rcode.android.data.local.db.dao.ChatGroupDao
import dev.rcode.android.data.local.db.entities.ChatGroupEntity
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.repository.ChatGroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.kotlin.given
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ChatGroupRepositoryImplTest {

    private lateinit var chatGroupDao: ChatGroupDao
    private lateinit var db: ChatDatabase

    private lateinit var repository: ChatGroupRepositoryImpl

    @Mock
    private lateinit var firebaseDatabase: FirebaseDatabase
    @Mock
    private lateinit var chatGroupEntity: ChatGroupEntity

    @Before
    fun setUp() {
        runBlocking {
            val context = ApplicationProvider.getApplicationContext<Context>()
            db = Room.inMemoryDatabaseBuilder(
                context, ChatDatabase::class.java
            ).build()
            chatGroupDao = db.chatGroupDao()
            Dispatchers.setMain(Dispatchers.Unconfined)
            repository = ChatGroupRepositoryImpl(db, firebaseDatabase)
            given(repository.createChatGroup("Test Group")).willReturn(flow { Result.success(ChatGroup())})
        }
    }


    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun createChatGroup() = runBlocking {

        val chatGroupName = "Test Group"
        repository.createChatGroup(chatGroupName)

        chatGroupEntity = ChatGroupEntity(1L, chatGroupName, System.currentTimeMillis(), false)
        verify(db).chatGroupDao().createChatGroup(chatGroupEntity)
        verifyNoMoreInteractions(db)

        assertEquals("Chat Group created successfully", chatGroupName, db.chatGroupDao().getChatGroupDetails(chatGroupName).first().chatGroupName)
    }

    @Test
    fun deleteChatGroup() {
        //TODO
    }

    @Test
    fun getAllChatGroups() {
        // TODO
    }

    @Test
    fun toggleMuteForChatGroup() {
        //TODO
    }

    @Test
    fun getChatGroup() {
        //TODO
    }
}