package dev.rcode.android.feature.chat

import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.rcode.android.core.ui.navigation.ChatNavigator
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.feature.chat.adapter.ChatListAdapter
import dev.rcode.android.feature.chat.databinding.FragmentChatListBinding
import javax.inject.Inject

@AndroidEntryPoint
class ChatListFragment : Fragment(), ChatListAdapter.ChatGroupListener {

    private var _binding: FragmentChatListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatListFragmentViewModel by viewModels()

    @Inject
    lateinit var navigator: ChatNavigator

    private lateinit var adapter: ChatListAdapter

    //private var selectedChatGroup: ChatGroup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Chat App"

        // Add options menu to the toolbar
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if(viewModel.selectedChatGroup.value == null)
                    menuInflater.inflate(R.menu.no_menu, menu)
                else {
                    if (!viewModel.selectedChatGroup.value!!.isMuted)
                        menuInflater.inflate(R.menu.chat_list_mute_menu, menu)
                    else
                        menuInflater.inflate(R.menu.chat_list_unmute_menu, menu)
                }
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.item_mute_chat_group -> {
                        viewModel.selectedChatGroup.value?.let {
                            viewModel.toggleMute(viewModel.selectedChatGroup.value!!)
                            Log.d(this::class.java.simpleName, "message deleted")
                        }
                        true
                    }
                    R.id.item_unmute_chat_group -> {
                        viewModel.selectedChatGroup.value?.let {
                            viewModel.toggleMute(viewModel.selectedChatGroup.value!!)
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        setUpUi()
        setUpObservers()
        setUpUiListeners()

        viewModel.getInitialData()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun setUpUi() {
        // Setup recycler view
        adapter = ChatListAdapter(chatGroups = mutableListOf(), this, viewModel.selectedChatGroup)
        //binding.progressBar.visibility = ProgressBar.INVISIBLE
        val manager = LinearLayoutManager(context)
        //manager.stackFromEnd = true
        binding.recyclerChatGroupList.layoutManager = manager
        binding.recyclerChatGroupList.adapter = adapter
    }

    private fun setUpObservers() {

        viewModel.chatGroupsLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
            activity?.invalidateOptionsMenu()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }

        viewModel.goToRegistrationScreen.observe(viewLifecycleOwner){
            if(it) navigator.goToRegistrationScreen()
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) {
            if(it == null) navigator.goToRegistrationScreen()
        }

        /*viewModel.chatListLiveData.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ChatListUIState.Loading -> {}
                is ChatListUIState.UserRetrieved -> {
                    // Logged in user found
                    view
                    // Get all chat groups available in the list
                    //
                }
                is ChatListUIState.UserNotAvailable -> {
                    // Navigate to Registration Fragment
                    //navController.navigate(R.id.action_chatListFragment_to_chatFragment)
                }
                is ChatListUIState.ChatGroupsRetrieved -> {
                    // Remove loading state
                    adapter.setData(uiState.chatGroups)
                }
                else -> {}
            }
        }*/
    }

    private fun setUpUiListeners() {
        _binding?.floatingActionButtonCreateGroup?.setOnClickListener { v ->
            // Show Group creation Dialog
            //viewModel.createNewChatGroup()
            showGroupCreationDialog()
        }
    }

    private fun showGroupCreationDialog() {
        // Show Material Dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Create Chat Group")
            //.setMessage(message)
            .setView(R.layout.dialog_create_chat_group)
            .setPositiveButton(getString(android.R.string.ok)) { dialog, which ->
                var chatGroupName = (dialog as AlertDialog).findViewById<EditText>(R.id.edittext_chat_group_name)?.text.toString()
                viewModel.createNewChatGroup(chatGroupName)
                dialog.dismiss()
            }
            .setNegativeButton(getString(android.R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onChatGroupClicked(groupName: String) {
        if(groupName == viewModel.selectedChatGroup.value?.chatGroupName) {
            viewModel.selectedChatGroup.value = null
            activity?.invalidateOptionsMenu()
        } else if(groupName.isNotEmpty()) {
            navigator.goToChatScreen(groupName)
        }
    }

    override fun onChatGroupSelected(groupName: String) {
        viewModel.selectedChatGroup.value = viewModel.chatGroupsLiveData.value?.find {
            it.chatGroupName == groupName
        }
        activity?.invalidateOptionsMenu()
    }

    override fun onChatUnSelected() {
        viewModel.selectedChatGroup.value = null
        activity?.invalidateOptionsMenu()
    }
}