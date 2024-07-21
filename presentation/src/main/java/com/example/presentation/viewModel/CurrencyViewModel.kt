package com.example.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Rates
import com.example.domain.usecase.GetRatesUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getRates: GetRatesUseCase
) : ViewModel() {

    private val _convertResult: MutableLiveData<String?> = MutableLiveData()
    val convertResult: LiveData<String?>
        get() = _convertResult

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null) {
            _convertResult.value = "Invalid amount"
            return
        }

        viewModelScope.launch {
            _loading.value = true
            val ratesResponse = getRates(fromCurrency)
            _loading.value = false
            when (ratesResponse) {
                is Resource.Error -> {
                    _convertResult.value = "Error: ${ratesResponse.message}"
                }
                is Resource.Success -> {
                    val rates = ratesResponse.data?.rates
                    val rate = rates?.let { getRateForCurrency(toCurrency, it) }
                    if (rate != null) {
                        val convertedCurrency = round(fromAmount * rate * 100) / 100
                        _convertResult.value = "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                    } else {
                        _convertResult.value = "Rate not available"
                    }
                }
            }
        }
    }

    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "CAD" -> rates.cAD
        "HKD" -> rates.hKD
        "ISK" -> rates.iSK
        "EUR" -> rates.eUR
        "PHP" -> rates.pHP
        "DKK" -> rates.dKK
        "HUF" -> rates.hUF
        "CZK" -> rates.cZK
        "AUD" -> rates.aUD
        "RON" -> rates.rON
        "SEK" -> rates.sEK
        "IDR" -> rates.iDR
        "INR" -> rates.iNR
        "BRL" -> rates.bRL
        "RUB" -> rates.rUB
        "HRK" -> rates.hRK
        "JPY" -> rates.jPY
        "THB" -> rates.tHB
        "CHF" -> rates.cHF
        "SGD" -> rates.sGD
        "PLN" -> rates.pLN
        "BGN" -> rates.bGN
        "CNY" -> rates.cNY
        "NOK" -> rates.nOK
        "NZD" -> rates.nZD
        "ZAR" -> rates.zAR
        "USD" -> rates.uSD
        "MXN" -> rates.mXN
        "ILS" -> rates.iLS
        "GBP" -> rates.gBP
        "KRW" -> rates.kRW
        "MYR" -> rates.mYR
        else -> null
    }
}