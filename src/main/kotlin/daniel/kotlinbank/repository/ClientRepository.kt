package daniel.kotlinbank.repository

import daniel.kotlinbank.model.Client
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : CrudRepository<Client, String> {
    fun findByCpf (cpf: String): Client?
}