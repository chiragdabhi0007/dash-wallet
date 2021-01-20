package de.schildbach.wallet.model.dashBalanceModel

import com.google.gson.annotations.SerializedName

data class Payload(

	@field:SerializedName("crypto_accounts")
	val cryptoAccounts: List<CryptoAccountsItem?>? = null

/*	@field:SerializedName("fiat_accounts")
	val fiatAccounts: List<FiatAccountsItem?>? = null*/
)