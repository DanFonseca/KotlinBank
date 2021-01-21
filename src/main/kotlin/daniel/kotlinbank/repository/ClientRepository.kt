package daniel.kotlinbank.repository

import daniel.kotlinbank.model.Client
import org.springframework.data.repository.CrudRepository

interface ClientRepository : CrudRepository<Client, String> {
    fun findByCpf (cpf: String): Client?
}