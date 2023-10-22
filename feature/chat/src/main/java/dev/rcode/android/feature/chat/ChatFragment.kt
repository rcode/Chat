package dev.rcode.android.feature.chat

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.rcode.android.domain.model.Message
import dev.rcode.android.feature.chat.adapter.ChatListAdapter
import dev.rcode.android.feature.chat.adapter.MessagesAdapter
import dev.rcode.android.feature.chat.databinding.FragmentChatBinding
import dev.rcode.android.feature.chat.databinding.FragmentChatListBinding

@AndroidEntryPoint
class ChatFragment : Fragment(), MessagesAdapter.MessageListener {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()

    //private val navController = findNavController(this)
    private lateinit var adapter: MessagesAdapter

    private var selectedMessage: Message? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        activity?.actionBar?.setDisplayHomeAsUpEnabled(true);
        activity?.actionBar?.setDisplayShowHomeEnabled(true);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add options menu to the toolbar
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if(selectedMessage == null) menuInflater.inflate(R.menu.no_menu, menu) else menuInflater.inflate(R.menu.chat_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.item_delete_message -> {
                        viewModel.deleteSelectedMessage(selectedMessage!!)
                        Log.d(this::class.java.simpleName, "message deleted")
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        setUpUi()
        setUpObservers()
        setUpUiListeners()

        arguments?.getString("chatGroup")?.let {
            viewModel.getChatGroupInfo(it)
            activity?.setTitle(it)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)


    }

    private fun setUpUi() {

        // Setup recycler view
        adapter = MessagesAdapter(mutableListOf<Message>(), this)
        //binding.progressBar.visibility = ProgressBar.INVISIBLE
        val manager: LinearLayoutManager = LinearLayoutManager(context)
        manager.stackFromEnd = true
        binding.recyclerChatMessages.layoutManager = manager
        binding.recyclerChatMessages.adapter = adapter
    }

    private fun setUpObservers() {

        viewModel.messagesLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        viewModel.chatGroup.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.getMessages()
            }
        }



        /*viewModel.chatLiveData.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ChatUIState.Loading -> {}
                is ChatUIState.ChatMessagesRetrieved -> {}
                is ChatUIState.Error -> {}
                else -> {}
            }
        }*/
    }

    private fun setUpUiListeners() {
        binding.sendButton.setOnClickListener {
            var messageText = binding.messageEditText.text.toString()
            if(messageText.isNotEmpty()) {
                viewModel.sendMessage(messageText)
                binding.messageEditText.text.clear()
            }
        }

    }

    override fun onMessageSelected(position: Int) {
        viewModel.messagesLiveData.value?.let {
            selectedMessage = it[position]
            activity?.invalidateOptionsMenu();
            Log.d(this::class.java.simpleName, "Message selected")
        }


    }

    override fun onMessageUnSelected() {
        selectedMessage = null
        activity?.invalidateOptionsMenu();
        Log.d(this::class.java.simpleName, "Message unselected")
    }

}