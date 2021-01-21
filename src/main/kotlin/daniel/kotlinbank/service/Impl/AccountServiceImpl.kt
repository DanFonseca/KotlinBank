package daniel.kotlinbank.service.Impl

import daniel.kotlinbank.errors.AccountException
import daniel.kotlinbank.errors.ClientException
import daniel.kotlinbank.model.Account
import daniel.kotlinbank.repository.AccountRepository
import daniel.kotlinbank.service.Util.CPFUtil
import daniel.kotlinbank.service.interfaces.AccountService
import daniel.kotlinbank.service.interfaces.ClientService
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(
        val accountRepository: AccountRepository,
        val clientService: ClientService
) : AccountService {

    override fun createAccount(account: Account): Account {

        if (account.client != null) {

           // CPFUtil.myValidateCPF(account.client.cpf)
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

    override fun isAccountAlreadyExist(cpf: String) {
         if (clientService.findClientByCPF(cpf) != null)
             throw AccountException("This Account Already Exist")
    }
}