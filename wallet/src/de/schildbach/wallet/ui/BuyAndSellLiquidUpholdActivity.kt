package de.schildbach.wallet.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.e.liquid_integration.`interface`.CurrencySelectListner
import com.e.liquid_integration.currency.CurrencyResponse
import com.e.liquid_integration.currency.PayloadItem
import com.e.liquid_integration.data.LiquidClient
import com.e.liquid_integration.ui.LiquidBuyAndSellDashActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.schildbach.wallet.WalletApplication
import de.schildbach.wallet.dialog.CurrencyDialog
import de.schildbach.wallet.rates.ExchangeRatesViewModel
import de.schildbach.wallet_test.R
import kotlinx.android.synthetic.main.activity_buy_and_sell_liquid_uphold.*
import org.bitcoinj.core.Coin
import org.bitcoinj.utils.ExchangeRate
import org.bitcoinj.utils.MonetaryFormat
import org.dash.wallet.common.Configuration
import org.dash.wallet.common.Constants
import org.dash.wallet.common.util.GenericUtils
import org.dash.wallet.integration.uphold.currencyModel.UpholdCurrencyResponse
import org.dash.wallet.integration.uphold.data.UpholdClient
import org.dash.wallet.integration.uphold.ui.UpholdAccountActivity
import org.json.JSONObject
import java.math.BigDecimal


class BuyAndSellLiquidUpholdActivity : AppCompatActivity() {


    private var liquidClient: LiquidClient? = null
    private var loadingDialog: ProgressDialog? = null
    private var application: WalletApplication? = null
    private var config: Configuration? = null
    private var amount: String? = null
    private var upholdCurrencyArrayList = ArrayList<UpholdCurrencyResponse>()
    private val liquidCurrencyArrayList = ArrayList<PayloadItem>()
    private var currentExchangeRate: de.schildbach.wallet.rates.ExchangeRate? = null

    private var selectedFilterCurrencyItems: PayloadItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_and_sell_liquid_uphold)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        application = WalletApplication.getInstance()
        config = application!!.configuration

        liquidClient = LiquidClient.getInstance()

        val actionBar = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setTitle(R.string.menu_buy_and_sell_title)

        loadingDialog = ProgressDialog(this)
        loadingDialog!!.isIndeterminate = true
        loadingDialog!!.setCancelable(false)
        loadingDialog!!.setMessage(getString(com.e.liquid_integration.R.string.loading))


        rlLiquid.setOnClickListener {
            startActivityForResult(LiquidBuyAndSellDashActivity.createIntent(this), Constants.USER_BUY_SELL_DASH)
        }

        rlUphold.setOnClickListener {
            val wallet = WalletApplication.getInstance().wallet
            startActivity(UpholdAccountActivity.createIntent(this, wallet))
        }

        setLoginStatus()

        if (LiquidClient.getInstance()!!.isAuthenticated) {
            getUserLiquidAccountBalance()
        }
        if (UpholdClient.getInstance().isAuthenticated()) {
            getUpholdUserBalance()
        }


        val exchangeRatesViewModel = ViewModelProviders.of(this)
                .get(ExchangeRatesViewModel::class.java)
        exchangeRatesViewModel.getRate(config?.getExchangeCurrencyCode()).observe(this,
                Observer { exchangeRate ->
                    if (exchangeRate != null) {
                        currentExchangeRate = exchangeRate
                    }
                })

    }

    override fun onResume() {
        super.onResume()
        setLoginStatus()
    }

    private fun setLoginStatus() {


        if (LiquidClient.getInstance()!!.isAuthenticated) {
            txtLiquidConnect.visibility = View.GONE
            txtLiquidConnected.visibility = View.VISIBLE
            llLiquidAmount.visibility = View.VISIBLE
        } else {
            txtLiquidConnect.visibility = View.VISIBLE
            txtLiquidConnected.visibility = View.GONE
            llLiquidAmount.visibility = View.GONE
        }


        if (UpholdClient.getInstance().isAuthenticated()) {
            txtUpholdConnect.visibility = View.GONE
            txtUpholdConnected.visibility = View.VISIBLE
        } else {
            txtUpholdConnect.visibility = View.VISIBLE
            txtUpholdConnected.visibility = View.GONE
            llUpholdAmount.visibility = View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.wallet_buy_and_sell, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.buy_and_sell_dash_filter -> {
                getLiquidCurrencyList()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUserLiquidAccountBalance() {
        if (GenericUtils.isInternetConnected(this)) {
            loadingDialog!!.show()
            liquidClient?.getUserAccountBalance(liquidClient?.storedSessionId!!, object : LiquidClient.Callback<String> {
                override fun onSuccess(data: String) {
                    if (isFinishing) {
                        return
                    }
                    loadingDialog!!.hide()
                    showDashLiquidBalance(data)
                }

                override fun onError(e: Exception?) {
                    if (isFinishing) {
                        return
                    }
                    loadingDialog!!.hide()
                }
            })
        } else {
            GenericUtils.showToast(this, getString(com.e.liquid_integration.R.string.internet_connected))
        }
    }

    private fun showDashLiquidBalance(data: String) {

        try {


            val jsonObject = JSONObject(data)
            val cryptoArray = jsonObject.getJSONObject("payload").getJSONArray("crypto_accounts")

            for (i in 0 until cryptoArray.length()) {
                val currency = cryptoArray.getJSONObject(i).getString("currency")
                if (currency == "DASH") {
                    llLiquidAmount.visibility = View.VISIBLE
                    txtLiquidAmount.text = cryptoArray.getJSONObject(i).getString("balance")//config?.exchangeCurrencyCode + " " +
                    amount = cryptoArray.getJSONObject(i).getString("balance")
                }
            }
        //    amount = "4.0"
            /*      amount = "4.0"
              txtLiquidAmount.text = amount*/

            val dashAmount = try {
                Coin.parseCoin(amount)
            } catch (x: Exception) {
                Coin.ZERO
            }


            if (currentExchangeRate != null) {

                val exchangeRate = ExchangeRate(Coin.COIN, currentExchangeRate?.fiat)
                val localValue = exchangeRate.coinToFiat(dashAmount)
                val fiatFormat = de.schildbach.wallet.Constants.LOCAL_FORMAT

                txtUSAmount.text = config?.exchangeCurrencyCode + " " + if (dashAmount.isZero) "0.00" else fiatFormat.format(localValue)
            } else {
                txtUSAmount.text = config?.exchangeCurrencyCode + " 0.00"
            }


            //   val exchangeRateData = buyAndSellLiquidUpholdViewModel?.exchangeRateData

            /*  val rate = ExchangeRate(Coin.COIN,
                      exchangeRateData.value!!.fiat)
  */


            /*    walletDataProvider.getExchangeRate()
                val fiatAmount = try {
                    Fiat.parseFiat(currencyCode, amount)

                } catch (x: Exception) {
                    Fiat.valueOf(currencyCode, 0)
                }
    */

            /* val base = Gson().fromJson(data, DashBalanceResponse::class.java)

             if (base.success!!) {

                 for (i in base.payload?.cryptoAccounts!!.indices) {

                     if (base.payload.cryptoAccounts[i]!!.currency == "DASH") {
                         llLiquidAmount.visibility = View.VISIBLE
                         txtUSAmount.text = base.payload.cryptoAccounts[i]!!.balance
                         amount = base.payload.cryptoAccounts[i]!!.balance
                         break
                     }
                 }
             }*/
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun getUpholdUserBalance() {
        if (GenericUtils.isInternetConnected(this)) {
            //    loadingDialog!!.show()
            UpholdClient.getInstance().getDashBalance(object : UpholdClient.Callback<BigDecimal> {
                override fun onSuccess(data: BigDecimal) {
                    if (isFinishing) {
                        return
                    }
                    val balance = data
                    val monetaryFormat = MonetaryFormat().noCode().minDecimals(8)
                    txtUpholdBalance.setFormat(monetaryFormat)
                    txtUpholdBalance.setApplyMarkup(false)
                    txtUpholdBalance.setAmount(Coin.parseCoin(balance.toString()))
                    loadingDialog!!.hide()

                    llUpholdAmount.visibility = View.VISIBLE
                }

                override fun onError(e: java.lang.Exception, otpRequired: Boolean) {
                    if (isFinishing) {
                        return
                    }
                    loadingDialog!!.hide()
                }
            })
        } else {
            GenericUtils.showToast(this, getString(R.string.internet_connected))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.USER_BUY_SELL_DASH && resultCode == Activity.RESULT_OK) {
            if (LiquidClient.getInstance()!!.isAuthenticated) {
                getUserLiquidAccountBalance()
            }
        }
    }

    private fun getLiquidCurrencyList() {

        if (GenericUtils.isInternetConnected(this)) {

            loadingDialog!!.show()
            liquidClient?.getAllCurrencies(object : LiquidClient.CallbackCurrency<String> {

                override fun onSuccess(data: CurrencyResponse) {
                    if (isFinishing) {
                        return
                    }
                    liquidCurrencyArrayList.clear()
                    //   loadingDialog!!.hide()
                    liquidCurrencyArrayList.addAll(data.payload)
                    getUpholdCurrencyList()

                }

                override fun onError(e: Exception?) {
                    if (isFinishing) {
                        return
                    }

                    //  loadingDialog!!.hide()
                    getUpholdCurrencyList()
                }
            })
        } else {
            GenericUtils.showToast(this, getString(R.string.internet_connected))
        }
    }


    private fun getUpholdCurrencyList() {

        if (GenericUtils.isInternetConnected(this)) {
            loadingDialog!!.show()
            UpholdClient.getInstance().getUpholdCurrency(object : UpholdClient.Callback<String> {
                override fun onSuccess(data: String?) {
                    loadingDialog!!.hide()
                    upholdCurrencyArrayList.clear()
                    val turnsType = object : TypeToken<List<UpholdCurrencyResponse>>() {}.type
                    val turns = Gson().fromJson<List<UpholdCurrencyResponse>>(data, turnsType)
                    upholdCurrencyArrayList.addAll(turns)
                    showCurrenciesDialog()
                }

                override fun onError(e: java.lang.Exception?, otpRequired: Boolean) {
                    loadingDialog!!.hide()
                    showCurrenciesDialog()
                }
            }
            )
        } else {
            GenericUtils.showToast(this, getString(R.string.internet_connected))
        }
    }

    private fun showCurrenciesDialog() {
        CurrencyDialog(this, liquidCurrencyArrayList, upholdCurrencyArrayList, selectedFilterCurrencyItems, object : CurrencySelectListner {
            override fun onCurrencySelected(isLiquidSelcted: Boolean, isUpholdSelected: Boolean, selectedFilterCurrencyItem: PayloadItem?) {
                rlLiquid.visibility = if (isLiquidSelcted) View.VISIBLE else View.GONE
                rlUphold.visibility = if (isUpholdSelected) View.VISIBLE else View.GONE
                selectedFilterCurrencyItems = selectedFilterCurrencyItem
                setSelectedCurrency()
            }
        })
    }

    private fun setSelectedCurrency() {

        if (selectedFilterCurrencyItems != null) {
            llFilterSelected.visibility = View.VISIBLE
            txtSelectedFilterCurrency.text = selectedFilterCurrencyItems?.label + " (" + selectedFilterCurrencyItems?.symbol + ")"
        } else {
            llFilterSelected.visibility = View.GONE
        }


        imgRemoveCurrency.setOnClickListener {
            selectedFilterCurrencyItems = null
            llFilterSelected.visibility = View.GONE
            rlLiquid.visibility = View.VISIBLE
            rlUphold.visibility = View.VISIBLE
        }
    }
}