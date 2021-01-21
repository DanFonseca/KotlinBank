package daniel.kotlinbank.service.Impl

import daniel.kotlinbank.service.Util.CPFUtil
import daniel.kotlinbank.errors.ClientException
import daniel.kotlinbank.model.Client
import daniel.kotlinbank.repository.ClientRepository
import daniel.kotlinbank.service.interfaces.ClientService
import org.springframework.stereotype.Service

@Service
class ClientServiceImpl (
        val clientRepository: ClientRepository
        ) : ClientService {

    override fun findClientByCPF(cpf: String): Client? {
        CPFUtil.myValidateCPF(cpf)
        return clientRepository.findByCpf(cpf)
    }
}