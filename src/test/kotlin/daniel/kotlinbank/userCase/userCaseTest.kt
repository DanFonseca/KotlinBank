package daniel.kotlinbank.userCase

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import daniel.kotlinbank.controller.AccountController
import daniel.kotlinbank.errors.AccountException
import daniel.kotlinbank.errors.CPFInvalidException
import daniel.kotlinbank.model.Account
import daniel.kotlinbank.model.Client
import daniel.kotlinbank.model.request.DepositRequest
import daniel.kotlinbank.model.request.TransferRequest
import daniel.kotlinbank.repository.AccountRepository
import daniel.kotlinbank.repository.ClientRepository
import daniel.kotlinbank.service.Impl.AccountServiceImpl
import daniel.kotlinbank.service.Impl.ClientServiceImpl
import daniel.kotlinbank.service.interfaces.AccountService
import daniel.kotlinbank.service.interfaces.ClientService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.util.*


@WebMvcTest(AccountController::class)
class userCaseTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var clientService: ClientService

    @MockBean
    lateinit var accountService: AccountService

    @Mock
    lateinit var accountRepository: AccountRepository

    @MockBean
    lateinit var clientRepository: ClientRepository

    @Test
    fun `Must Return Error When Put Incorrect CPF`() {
        val account = Account(1, BigDecimal(400),
                Client("33226902021", "João")
        )

        val exception = Assertions.assertThrows(CPFInvalidException::class.java) {
            Mockito.`when`(AccountServiceImpl(accountRepository, ClientServiceImpl(clientRepository)).createAccount(account))
                    .thenThrow(CPFInvalidException::class.java)
        }

        Assertions.assertEquals("CPF is invalid", exception.message)
    }

    @Test
    fun `Must Return Success When Put Correct CPF`() {
        val account = Account(1, BigDecimal(400),
                Client("33226902024", "João")
        )

        Mockito.`when`(accountRepository.save(account))
                .thenReturn(account)

        Mockito.`when`(AccountServiceImpl(accountRepository, ClientServiceImpl(clientRepository)).createAccount(account))
                .thenReturn(account)

        mockMvc.perform(post("/v1/account/")
                .content(jacksonObjectMapper().writeValueAsString(account))
                .contentType("application/json"))
                .andExpect(status().`is`(200));
    }


    @Test
    fun `Must Make A Transfer`() {

        val account1 = Account(1, BigDecimal(400),
                Client("33226902024", "João")
        )

        val account2 = Account(2, BigDecimal(400),
                Client("78244831006", "Pedro")
        )

        val transferRequest = TransferRequest(1, 2, BigDecimal(100))

        Mockito.`when`(accountRepository.save(account1))
                .thenReturn(account1)
        Mockito.`when`(AccountServiceImpl(accountRepository, ClientServiceImpl(clientRepository)).createAccount(account1))
                .thenReturn(account1)

        Mockito.`when`(accountRepository.save(account2))
                .thenReturn(account2)
        Mockito.`when`(AccountServiceImpl(accountRepository, ClientServiceImpl(clientRepository)).createAccount(account2))
                .thenReturn(account2)


        Mockito.`when`(accountRepository.findById(1))
                .thenReturn(Optional.of(account1))

        Mockito.`when`(accountRepository.findById(2))
                .thenReturn(Optional.of(account2))


        Mockito.`when`(AccountServiceImpl(accountRepository, ClientServiceImpl(clientRepository)).makeTransfer(transferRequest))
                .thenReturn(mapOf())

        mockMvc.perform(post("/v1/account/transfer")
                .content(jacksonObjectMapper().writeValueAsString(transferRequest))
                .contentType("application/json"))
                .andExpect(status().`is`(200));
    }

    @Test
    fun `Must NOT MAKE A Transfer If The AccountFrom Has Less Amount Than The transferRequest`() {

        val account1 = Account(1, BigDecimal(400),
                Client("33226902024", "João")
        )

        val account2 = Account(2, BigDecimal(400),
                Client("78244831006", "Pedro")
        )

        val transferRequest = TransferRequest(1, 2, BigDecimal(500))

        Mockito.`when`(accountRepository.save(account1))
                .thenReturn(account1)
        Mockito.`when`(AccountServiceImpl(accountRepository, ClientServiceImpl(clientRepository)).createAccount(account1))
                .thenReturn(account1)

        Mockito.`when`(accountRepository.save(account2))
                .thenReturn(account2)
        Mockito.`when`(AccountServiceImpl(accountRepository, ClientServiceImpl(clientRepository)).createAccount(account2))
                .thenReturn(account2)


        Mockito.`when`(accountRepository.findById(1))
                .thenReturn(Optional.of(account1))

        Mockito.`when`(accountRepository.findById(2))
                .thenReturn(Optional.of(account2))

        val exception = Assertions.assertThrows(AccountException::class.java) {
            Mockito.`when`(AccountServiceImpl(accountRepository, ClientServiceImpl(clientRepository)).makeTransfer(transferRequest))
                    .thenThrow(AccountException::class.java)
        }

        Assertions.assertEquals("You can not transfer more than you have in your account amount", exception.message)
    }

    @Test
    fun `Must NOT Do a Deposit Bigger Than 2000`() {
        val account = Account(1, BigDecimal(5000),
                Client("33226902021", "João")
        )

        val depositRequest = DepositRequest(1, BigDecimal(2001))

        val exception = Assertions.assertThrows(AccountException::class.java) {

            Mockito.`when`(accountRepository.findById(1))
                    .thenReturn(Optional.of(account))

            Mockito.`when`(AccountServiceImpl(accountRepository, ClientServiceImpl(clientRepository)).makeDepositVerify(depositRequest))
                    .thenThrow(AccountException::class.java)
        }

        Assertions.assertEquals("Amount of Deposit must be less then 2.000", exception.message)
    }
}