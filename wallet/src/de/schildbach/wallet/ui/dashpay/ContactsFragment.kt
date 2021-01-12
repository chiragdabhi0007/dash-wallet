/*
 * Copyright 2020 Dash Core Group.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.schildbach.wallet.ui.dashpay

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import de.schildbach.wallet.data.PaymentIntent
import de.schildbach.wallet.data.UsernameSearchResult
import de.schildbach.wallet.data.UsernameSortOrderBy
import de.schildbach.wallet.livedata.Resource
import de.schildbach.wallet.livedata.Status
import de.schildbach.wallet.ui.*
import de.schildbach.wallet.ui.send.SendCoinsInternalActivity
import de.schildbach.wallet.util.KeyboardUtil
import de.schildbach.wallet_test.R
import kotlinx.android.synthetic.main.contacts_empty_state_layout.*
import kotlinx.android.synthetic.main.contacts_list_layout.*
import org.bitcoinj.core.PrefixedChecksummedBytes
import org.bitcoinj.core.Transaction
import org.bitcoinj.core.VerificationException
import org.dash.wallet.common.InteractionAwareActivity

class ContactsFragment : BottomNavFragment(R.layout.fragment_contacts_root), TextWatcher,
        ContactSearchResultsAdapter.Listener,
        ContactViewHolder.OnItemClickListener {

    companion object {
        private const val EXTRA_MODE = "extra_mode"

        const val MODE_SEARCH_CONTACTS = 0
        const val MODE_SELECT_CONTACT = 1
        const val MODE_VIEW_REQUESTS = 2

        @JvmStatic
        fun newInstance(mode: Int = MODE_SEARCH_CONTACTS): ContactsFragment {
            val args = Bundle()
            args.putInt(EXTRA_MODE, mode)

            val instance = ContactsFragment()
            instance.arguments = args

            return instance
        }
    }

    override val navigationItemId = R.id.contacts

    private lateinit var dashPayViewModel: DashPayViewModel
    private var searchHandler: Handler = Handler()
    private lateinit var searchContactsRunnable: Runnable
    private lateinit var contactsAdapter: ContactSearchResultsAdapter
    private var query = ""
    private var direction = UsernameSortOrderBy.USERNAME
    private val mode by lazy { requireArguments().getInt(EXTRA_MODE, MODE_SEARCH_CONTACTS) }
    private var initialSearch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireActivity() is ContactSearchResultsAdapter.OnViewAllRequestsListener) {
            val viewAllRequestsListener = requireActivity() as ContactSearchResultsAdapter.OnViewAllRequestsListener
            contactsAdapter = ContactSearchResultsAdapter(this, viewAllRequestsListener)
        } else {
            throw java.lang.IllegalStateException("The activity hosting this fragment should implement" +
                    "ContactSearchResultsAdapter.Listener")
        }

        contacts_rv.layoutManager = LinearLayoutManager(requireContext())
        contacts_rv.adapter = this.contactsAdapter
        this.contactsAdapter.itemClickListener = this

        initViewModel()

        if (mode == MODE_VIEW_REQUESTS) {
            search.visibility = View.GONE
            icon.visibility = View.GONE
            setupActionBarWithTitle(R.string.contact_requests_title)
        } else {
            // search should be available for all other modes
            search.addTextChangedListener(this)
            search.visibility = View.VISIBLE
            icon.visibility = View.VISIBLE
            setupActionBarWithTitle(R.string.contacts_title)
        }

        search_for_user.setOnClickListener {
            startActivity(Intent(context, SearchUserActivity::class.java))
        }
        searchContacts()
    }

    private fun showEmptyPane() {
        suggestions_search_no_result.visibility = View.VISIBLE
    }

    private fun initViewModel() {
        dashPayViewModel = ViewModelProvider(this).get(DashPayViewModel::class.java)
        dashPayViewModel.searchContactsLiveData.observe(viewLifecycleOwner, Observer {
            imitateUserInteraction()
            if (Status.SUCCESS == it.status) {
                if (initialSearch && query.isEmpty() && (mode != MODE_VIEW_REQUESTS)) {
                    if (it.data == null || it.data.isEmpty() || it.data.find { u -> u.requestReceived } == null) {
                        empty_state_pane.visibility = View.VISIBLE
                        search.visibility = View.GONE
                        icon.visibility = View.GONE
                    } else {
                        search.visibility = View.VISIBLE
                        icon.visibility = View.VISIBLE
                        empty_state_pane.visibility = View.GONE
                    }
                    initialSearch = false
                }
                if (it.data != null) {
                    processResults(it.data)
                }
            }
        })

        dashPayViewModel.sendContactRequestState.observe(viewLifecycleOwner, Observer {
            imitateUserInteraction()
            contactsAdapter.sendContactRequestWorkStateMap = it
        })
        dashPayViewModel.contactsUpdatedLiveData.observe(viewLifecycleOwner, Observer<Resource<Boolean>> {
            if (it?.data != null && it.data) {
                searchContacts()
            }
        })
        dashPayViewModel.searchUsernamesLiveData.observe(viewLifecycleOwner, Observer {
            if (it.data != null && it.data.isNotEmpty()) {
                showSuggestedUsers(it.data)
            } else {
                if (contactsAdapter.results.isEmpty()) {
                    showEmptyPane()
                }
            }
        })
    }

    private fun showSuggestedUsers(users: List<UsernameSearchResult>?) {
        val results = contactsAdapter.results

        if (results.isEmpty()) {
            results.add(ContactSearchResultsAdapter.ViewItem(null,
                    ContactSearchResultsAdapter.CONTACT_NO_RESULTS))
        }

        if (users != null) {
            results.add(ContactSearchResultsAdapter.ViewItem(null,
                    ContactSearchResultsAdapter.CONTACTS_SUGGESTIONS_HEADER))
            for (user in users) {
                results.add(ContactSearchResultsAdapter.ViewItem(user,
                        ContactSearchResultsAdapter.CONTACT_SUGGESTION_ROW))
            }
        }


        contactsAdapter.results = results
    }

    private fun showEmptySuggestions() {
        suggestions_search_no_result.visibility = View.VISIBLE
        KeyboardUtil.hideKeyboard(requireContext(), requireView())
        val searchUsersBtn = suggestions_search_no_result.findViewById<View>(R.id.search_for_user_suggestions)
        searchUsersBtn.setOnClickListener {
            startActivity(Intent(context, SearchUserActivity::class.java))
        }
        val text = getString(R.string.suggestions_empty_result_part_1) +
                " \"<b>$query</b>\" " + getString(R.string.suggestions_empty_result_part_2)
        val noResultsLabel = suggestions_search_no_result.findViewById<TextView>(R.id.no_results_label)
        noResultsLabel.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    override fun onResume() {
        super.onResume()
        dashPayViewModel.updateDashPayState()
    }

    private fun processResults(data: List<UsernameSearchResult>) {
        val results = ArrayList<ContactSearchResultsAdapter.ViewItem>()
        // process the requests
        val requests = if (mode != MODE_SELECT_CONTACT) {
            data.filter { r -> r.isPendingRequest }.toMutableList()
        } else {
            ArrayList()
        }

        val requestCount = requests.size
        if (mode != MODE_VIEW_REQUESTS) {
            while (requests.size > 3) {
                requests.remove(requests[requests.size - 1])
            }
        }

        if (requests.isNotEmpty() && mode == MODE_SEARCH_CONTACTS)
            results.add(ContactSearchResultsAdapter.ViewItem(null, ContactSearchResultsAdapter.CONTACT_REQUEST_HEADER, requestCount = requestCount))
        requests.forEach { r -> results.add(ContactSearchResultsAdapter.ViewItem(r, ContactSearchResultsAdapter.CONTACT)) }

        // process contacts
        val contacts = if (mode != MODE_VIEW_REQUESTS)
            data.filter { r -> r.requestSent && r.requestReceived }
        else ArrayList()

        if (contacts.isNotEmpty() && mode != MODE_VIEW_REQUESTS)
            results.add(ContactSearchResultsAdapter.ViewItem(null, ContactSearchResultsAdapter.CONTACT_HEADER))
        contacts.forEach { r -> results.add(ContactSearchResultsAdapter.ViewItem(r, ContactSearchResultsAdapter.CONTACT)) }

        contactsAdapter.results = results
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        if (mode == MODE_SEARCH_CONTACTS) {
            menuInflater.inflate(R.menu.contacts_menu, menu)
        }
        return super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onSortOrderChanged(direction: UsernameSortOrderBy) {
        this.direction = direction
        searchContacts()
    }

    override fun onSearchUser() {
        startActivity(Intent(context, SearchUserActivity::class.java))
    }

    private fun searchContacts() {
        suggestions_search_no_result.visibility = View.GONE
        if (this::searchContactsRunnable.isInitialized) {
            searchHandler.removeCallbacks(searchContactsRunnable)
        }

        searchContactsRunnable = Runnable {
            contactsAdapter.query = query
            dashPayViewModel.searchContacts(query, direction)
            if (!initialSearch && query.isNotEmpty()) {
                dashPayViewModel.searchUsernames(query, limit = 3, removeContacts = true)
            }
        }
        searchHandler.postDelayed(searchContactsRunnable, 500)
    }

    override fun afterTextChanged(s: Editable?) {
        s?.let {
            query = it.toString()
            searchContacts()
        }
    }

    private fun imitateUserInteraction() {
        (requireActivity() as InteractionAwareActivity).imitateUserInteraction()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun onItemClicked(view: View, usernameSearchResult: UsernameSearchResult) {
        when (mode) {
            MODE_SEARCH_CONTACTS, MODE_VIEW_REQUESTS -> {
                startActivity(DashPayUserActivity.createIntent(requireContext(), usernameSearchResult))
            }
            MODE_SELECT_CONTACT -> {
                handleString(usernameSearchResult.toContactRequest!!.toUserId, true, R.string.scan_to_pay_username_dialog_message)
            }
        }
    }

    override fun onAcceptRequest(usernameSearchResult: UsernameSearchResult, position: Int) {
        dashPayViewModel.sendContactRequest(usernameSearchResult.fromContactRequest!!.userId)
    }

    override fun onIgnoreRequest(usernameSearchResult: UsernameSearchResult, position: Int) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DashPayUserActivity.REQUEST_CODE_DEFAULT && resultCode == DashPayUserActivity.RESULT_CODE_CHANGED) {
            searchContacts()
        }
    }

    private fun handleString(input: String, fireAction: Boolean, errorDialogTitleResId: Int) {
        object : InputParser.StringInputParser(input, true) {

            override fun handlePaymentIntent(paymentIntent: PaymentIntent) {
                if (fireAction) {
                    SendCoinsInternalActivity.start(context, paymentIntent, true)
                } else {

                }
            }

            override fun error(ex: Exception?, messageResId: Int, vararg messageArgs: Any) {
                if (fireAction) {
                    dialog(context, null, errorDialogTitleResId, messageResId, *messageArgs)
                } else {

                }
            }

            override fun handlePrivateKey(key: PrefixedChecksummedBytes) {
                // ignore
            }

            @Throws(VerificationException::class)
            override fun handleDirectTransaction(tx: Transaction) {
                // ignore
            }
        }.parse()
    }

}
