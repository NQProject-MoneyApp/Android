import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.network.models.ExpenseType
import com.nqproject.MoneyApp.repository.ExpenseRepository
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.repository.GroupRepository
import com.nqproject.MoneyApp.repository.SuggestedPayment
import kotlinx.coroutines.launch


class SuggestedPaymentViewModel(app: Application) : AndroidViewModel(app) {

    private val _loading = MutableLiveData(false)
    private val _suggestedPaymentsList = MutableLiveData(emptyList<SuggestedPayment>())

    val loading: LiveData<Boolean> = _loading
    val suggestedPaymentsList: LiveData<List<SuggestedPayment>> = _suggestedPaymentsList

    private var initialized = false
    private lateinit var _group: Group


    fun init(group: Group) {
        if (initialized) return
        initialized = true

        _group = group

        viewModelScope.launch {
            _loading.value = true
            fetchSuggestedPayment(group.id)
            _loading.value = false
        }
    }

    private suspend fun fetchSuggestedPayment(id: Int) {

        val result = GroupRepository.suggestedPayment(id)

        when(result) {
            is SimpleResult.Success -> {
                _suggestedPaymentsList.value = result.data
            }
            is SimpleResult.Error -> {
                Toast.makeText(getApplication(), result.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun savePayments(payment: SuggestedPayment) {

        viewModelScope.launch {
            val result = ExpenseRepository.addExpense(
                _group.id,
                name = "${payment.paidBy.name} -> ${payment.paidTo.name}",
                paidById = payment.paidBy.pk,
                paidToId = payment.paidTo.pk,
                amount = payment.amount.toFloat(),
                type= ExpenseType.Payment
            )
            _loading.value = true
            fetchSuggestedPayment(_group.id)
            _loading.value = false
        }
    }
}