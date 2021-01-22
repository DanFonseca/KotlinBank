package daniel.kotlinbank.controller

import daniel.kotlinbank.model.Account
import daniel.kotlinbank.model.Client
import daniel.kotlinbank.model.request.DepositRequest
import daniel.kotlinbank.model.request.TransferRequest
import daniel.kotlinbank.service.interfaces.AccountService
import daniel.kotlinbank.service.interfaces.ClientService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.security.auth.login.AccountException

@RestController
@RequestMapping("/v1/account")
class AccountController(
        val clientService: ClientService,
        val accountService: AccountService
) {

    @GetMapping("/")
    fun getAccounts (): ResponseEntity<List<Account>> =
            ResponseEntity.ok(accountService.getAccounts())

    @GetMapping("user/{cpf}")
    fun findByCpf(@PathVariable(required = true) cpf: String): ResponseEntity<Client?> {
        val client = clientService.findClientByCPF(cpf)

        if (client != null) {
            return ResponseEntity.ok(client)
        }

        throw AccountException("Account not Found")
    }

    @PostMapping
    fun createAccount(@RequestBody account: Account): ResponseEntity<Account> =
             ResponseEntity.ok(accountService.createAccount(account))


    @GetMapping("/{id}")
    fun getAccountById(@PathVariable id: Long): ResponseEntity<Account> =
            ResponseEntity.ok(accountService.getAccountById(id))

    @PostMapping("/transfer")
    fun makeTransfer(@RequestBody transferRequest: TransferRequest): ResponseEntity<Map<String, Account>> =
            ResponseEntity.ok(accountService.makeTransfer(transferRequest))


    @PostMapping("/deposit")
    fun makeDeposit(@RequestBody @Validated depositRequest: DepositRequest): ResponseEntity<Account> {

        val account = accountService.getAccountById(depositRequest.accountId)
        accountService.makeDepositVerify(depositRequest)
        accountService.doDeposit(account, depositRequest)

        return ResponseEntity.ok(account)
    }

}