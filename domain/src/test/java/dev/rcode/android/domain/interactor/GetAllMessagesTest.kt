package dev.rcode.android.domain.interactor

import dev.rcode.android.core.base.functional.Either
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.model.Message
import dev.rcode.android.domain.repository.MessagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

class GetAllMessagesTest: UnitTest() {

    private lateinit var useCase: GetAllMessages

    private lateinit var mockMessagesRepository: MessagesRepository

    private val chatGroup: ChatGroup = ChatGroup(1L,"Test Group", System.currentTimeMillis(),false)

    @Before
    @ExperimentalCoroutinesApi
    fun setUp() {
        runBlocking {
            mockMessagesRepository = Mockito.mock(MessagesRepository::class.java)
            useCase = GetAllMessages(mockMessagesRepository)
            Dispatchers.setMain(Dispatchers.Unconfined)
            given(useCase.run(chatGroup)).willReturn(flow { Result.success(mutableListOf<Message>()) })
        }
    }

    @Test
    fun `should get all messages of a chat group`() {
        runBlocking {
            useCase.run(chatGroup)
            verify(mockMessagesRepository).getAllMessages(chatGroup)
            verifyNoMoreInteractions(mockMessagesRepository)
        }

    }
}