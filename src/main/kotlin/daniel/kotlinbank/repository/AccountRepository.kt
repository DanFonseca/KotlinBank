package daniel.kotlinbank.repository

import daniel.kotlinbank.model.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: CrudRepository<Account, Long> {
}