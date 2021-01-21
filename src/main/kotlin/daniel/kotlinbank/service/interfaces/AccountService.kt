package daniel.kotlinbank.service.interfaces

import daniel.kotlinbank.errors.AccountException
import daniel.kotlinbank.model.Account
import daniel.kotlinbank.model.request.DebitRequest
import daniel.kotlinbank.model.request.DepositRequest
import daniel.kotlinbank.model.request.Request
import daniel.kotlinbank.model.request.TransferRequest
import java.io.Serializable
import java.math.BigDecimal

interface AccountService {
    fun createAccount (account: Account) : Account
    fun getAccountById (id: Long): Account
    fun isAccountAlreadyExist (cpf: String)

    fun makeTransfer (transferRequest: TransferRequest) : Map<String, Account>
    fun makeDebitVerify (debitRequest: Request): Account
    fun makeDepositVerify (depositRequest: Request): Account

    fun doDebit (accountFrom: Account, debitRequest: Request)
    fun doDeposit (accountTo: Account, depositRequest: Request)

    fun verifyAmountLessThenZero (account: Account){
        if(account.amount <= BigDecimal.ZERO)
            throw AccountException("Account has 0 amount")
    }

    fun verifyDepositBiggerThen (depositRequest: Request){
        if(depositRequest.amount > BigDecimal.valueOf(2000))
            throw AccountException("Amount of Deposit must be less then 2.000")
    }
}


