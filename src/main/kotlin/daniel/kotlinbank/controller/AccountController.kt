package daniel.kotlinbank.controller

import daniel.kotlinbank.model.Account
import daniel.kotlinbank.model.Client
import daniel.kotlinbank.model.request.DepositRequest
import daniel.kotlinbank.model.request.TransferRequest
import daniel.kotlinbank.service.interfaces.AccountService
import daniel.kotlinbank.service.interfaces.ClientService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import javax.security.auth.login.AccountException

@RestController
@RequestMapping("/v1/account")
class AccountController (
        val clientService: ClientService,
        val accountService: AccountService
        ) {

    @GetMapping("user/{cpf}")
    fun findByCpf (@PathVariable (required = true) cpf: String): ResponseEntity<Client?> {
        val client = clientService.findClientByCPF(cpf)

        if (client != null){
            return ResponseEntity.ok(client)
        }

        throw AccountException("Account not Found")
    }

    @PostMapping
    fun createAccount (@RequestBody account: Account) : Account {
        return  accountService.createAccount(account)
    }

    @GetMapping("/{id}")
    fun getAccountById (@PathVariable id: Long) : Account {
        return  accountService.getAccountById(id)
    }

    @PostMapping ("/transfer")
    fun makeTransfer (@RequestBody transferRequest: TransferRequest): Map<String, Account> {
        return accountService.makeTransfer(transferRequest)
    }

    @PostMapping ("/deposit")
    fun makeDeposit (@RequestBody depositRequest: DepositRequest): Account {
        val account = accountService.getAccountById(depositRequest.accountId)
        accountService.makeDepositVerify(depositRequest)
        accountService.doDeposit(account, depositRequest)

        return account
    }

}