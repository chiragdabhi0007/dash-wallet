package de.schildbach.wallet.model.dashBalanceModel

import com.google.gson.annotations.SerializedName

data class DashBalanceResponse(

	@field:SerializedName("environment")
	val environment: String? = null,

	@field:SerializedName("payload")
	val payload: Payload? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)