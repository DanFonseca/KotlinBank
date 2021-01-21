package daniel.kotlinbank.repository

import daniel.kotlinbank.model.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository: CrudRepository<Account, Long> {
}