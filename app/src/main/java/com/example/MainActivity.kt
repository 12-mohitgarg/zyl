package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ui.BazaarApp
import com.example.ui.theme.MyApplicationTheme
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking

/** Razorpay payment result – collected by Compose UI */
sealed class RazorpayResult {
    data class Success(val paymentId: String) : RazorpayResult()
    data class Error(val code: Int, val description: String) : RazorpayResult()
}

class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    private val _razorpayResult = MutableSharedFlow<RazorpayResult>(extraBufferCapacity = 1)
    val razorpayResult: SharedFlow<RazorpayResult> = _razorpayResult.asSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                BazaarApp(activity = this)
            }
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?, paymentData: PaymentData?) {
        runBlocking {
            _razorpayResult.emit(RazorpayResult.Success(razorpayPaymentId ?: ""))
        }
    }

    override fun onPaymentError(code: Int, description: String?, paymentData: PaymentData?) {
        runBlocking {
            _razorpayResult.emit(RazorpayResult.Error(code, description ?: "Payment failed"))
        }
    }
}
