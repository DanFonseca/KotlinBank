package daniel.kotlinbank.service.interfaces

import daniel.kotlinbank.model.Client
import org.springframework.stereotype.Service

@Service
interface ClientService {
    fun findClientByCPF (cpf: String) : Client?
}