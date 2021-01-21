package daniel.kotlinbank.model


import com.fasterxml.jackson.annotation.JsonIgnore
import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "client")
class Client (
        @Id
        var cpf: String,
        @NotNull
        var name: String,
        @OneToOne(mappedBy = "client")
        @JsonIgnore
        val account: Account? = null
        )  {
}