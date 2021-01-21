package daniel.kotlinbank.service.interfaces

import daniel.kotlinbank.model.Account

interface AccountService {
    fun createAccount (account: Account) : Account
    fun getAccountById (id: Long): Account
    fun isAccountAlreadyExist (cpf: String)
}