package daniel.kotlinbank.service.Impl

import daniel.kotlinbank.errors.AccountException
import daniel.kotlinbank.errors.ClientException
import daniel.kotlinbank.model.Account
import daniel.kotlinbank.model.request.Request
import daniel.kotlinbank.model.request.TransferRequest
import daniel.kotlinbank.repository.AccountRepository
import daniel.kotlinbank.service.interfaces.AccountService
import daniel.kotlinbank.service.interfaces.ClientService
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AccountServiceImpl(
        val accountRepository: AccountRepository,
        val clientService: ClientService
) : AccountService {

    override  fun createAccount(account: Account): Account {

        if (account.client != null) {
            isAccountAlreadyExist(account.client.cpf)
            if (account.client.name == "")
                throw ClientException("Name can not be empty ")
        }
        return accountRepository.save(account)
    }

    override fun getAccountById(id: Long): Account {
        return accountRepository.findById(id)
                .orElseGet { throw AccountException("Account not Found") }
    }

    override fun getAccounts(): List<Account> {
        return accountRepository.findAll().map { x-> x }
    }

    override fun isAccountAlreadyExist(cpf: String) {
        if (clientService.findClientByCPF(cpf) != null)
            throw AccountException("This Account Already Exist")
    }

    override fun makeTransfer(transferRequest: TransferRequest): Map<String, Account> {
        val debitRequest = Request(transferRequest.accountIdFrom, transferRequest.amount)
        val debit = makeDebitVerify(debitRequest)

        val depositRequest = Request(transferRequest.accountIdTo, transferRequest.amount)
        val deposit = makeDepositVerify(depositRequest)

        doDebit(debit, debitRequest)
        doDeposit(deposit, depositRequest)

        return mutableMapOf(
                "Account From" to debit,
                "Account To" to deposit)

    }

    override fun makeDebitVerify(debitRequest: Request): Account {
        val account = getAccountById(debitRequest.accountId)

        if ((account.amount - debitRequest.amount) < BigDecimal.ZERO)
            throw AccountException("You can not transfer more than you have in your account amount")

        return account
    }

    override fun makeDepositVerify(depositRequest: Request): Account {
        val account = getAccountById(depositRequest.accountId)

        if (depositRequest.amount <= BigDecimal.ZERO)
            throw AccountException("Amount must be bigger then zero")

        if (depositRequest.amount > BigDecimal.valueOf(2000))
            throw AccountException("Amount of Deposit must be less then 2.000")


        return account
    }

    override fun doDebit(accountFrom: Account, debitRequest: Request) {
        accountFrom.amount -= debitRequest.amount
        accountRepository.save(accountFrom)
    }

    override fun doDeposit(accountTo: Account, depositRequest: Request) {
        accountTo.amount += depositRequest.amount
        accountRepository.save(accountTo)
    }

}