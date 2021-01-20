package de.schildbach.wallet.model.dashBalanceModel

import com.google.gson.annotations.SerializedName

data class FiatAccountsItem(
/*
	@field:SerializedName("lowest_offer_interest_rate")
	val lowestOfferInterestRate: String? = null,

	@field:SerializedName("send_to_btc_address")
	val sendToBtcAddress: String? = null,

	@field:SerializedName("balance")
	val balance: String? = null,

	@field:SerializedName("exchange_rate")
	val exchangeRate: String? = null,

	@field:SerializedName("pusher_channel")
	val pusherChannel: String? = null,

	@field:SerializedName("currency_symbol")
	val currencySymbol: String? = null,

	@field:SerializedName("currency_type")
	val currencyType: String? = null,

	@field:SerializedName("highest_offer_interest_rate")
	val highestOfferInterestRate: String? = null,*/

        @field:SerializedName("currency")
        val currency: String? = null,

        @field:SerializedName("balance")
        val balance: String? = null

        /*@field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("reserved_balance")
        val reservedBalance: String? = null*/
)