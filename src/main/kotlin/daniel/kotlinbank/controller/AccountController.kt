package daniel.kotlinbank.controller

import daniel.kotlinbank.model.Account
import daniel.kotlinbank.model.Client
import daniel.kotlinbank.service.interfaces.AccountService
import daniel.kotlinbank.service.interfaces.ClientService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.security.auth.login.AccountException

@RestController
@RequestMapping("/v1/account")
class AccountController (
        val clientService: ClientService,
        val accountService: AccountService
        ) {

    @GetMapping("/{cpf}")
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


}