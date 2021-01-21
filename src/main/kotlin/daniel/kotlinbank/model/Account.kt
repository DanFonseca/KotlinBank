package daniel.kotlinbank.model

import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import javax.persistence.*

@Entity
class Account (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        @NotNull ()
        var amount: BigDecimal,

        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "Client_cpf")
        val client: Client? = null

        ) {
}