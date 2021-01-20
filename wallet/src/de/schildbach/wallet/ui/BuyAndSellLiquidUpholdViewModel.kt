/*
 * Copyright 2020 Dash Core Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.schildbach.wallet.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.schildbach.wallet.Constants
import de.schildbach.wallet.WalletApplication
import de.schildbach.wallet.data.PaymentIntent
import de.schildbach.wallet.livedata.Resource
import de.schildbach.wallet.offline.DirectPaymentTask
import de.schildbach.wallet.offline.DirectPaymentTask.HttpPaymentTask
import de.schildbach.wallet.rates.ExchangeRate
import de.schildbach.wallet.rates.ExchangeRatesRepository
import de.schildbach.wallet.ui.send.RequestPaymentRequestTask.HttpRequestTask
import de.schildbach.wallet_test.BuildConfig
import de.schildbach.wallet_test.R
import org.bitcoinj.core.Coin
import org.bitcoinj.core.Context
import org.bitcoinj.protocols.payments.PaymentProtocol
import org.bitcoinj.wallet.KeyChain.KeyPurpose
import org.bitcoinj.wallet.SendRequest
import org.slf4j.LoggerFactory

class BuyAndSellLiquidUpholdViewModel(application: Application) : AndroidViewModel(application) {


    val exchangeRateData: LiveData<ExchangeRate>
    val walletApplication = application as WalletApplication

    val exchangeRate: org.bitcoinj.utils.ExchangeRate?
        get() = exchangeRateData.value?.run {
            org.bitcoinj.utils.ExchangeRate(Coin.COIN, fiat)
        }

    init {
        val currencyCode = walletApplication.configuration.exchangeCurrencyCode
        exchangeRateData = ExchangeRatesRepository.getInstance().getRate(currencyCode)
    }


}