package de.schildbach.wallet.model.dashBalanceModel

import com.google.gson.annotations.SerializedName

data class CryptoAccountsItem(

	@field:SerializedName("lowest_offer_interest_rate")
	val lowestOfferInterestRate: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("balance")
	val balance: String? = null,

	@field:SerializedName("pusher_channel")
	val pusherChannel: String? = null,

	@field:SerializedName("currency_symbol")
	val currencySymbol: String? = null,

	@field:SerializedName("currency_type")
	val currencyType: String? = null,

	@field:SerializedName("minimum_withdraw")
	val minimumWithdraw: String? = null,

	@field:SerializedName("highest_offer_interest_rate")
	val highestOfferInterestRate: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("reserved_balance")
	val reservedBalance: String? = null
)