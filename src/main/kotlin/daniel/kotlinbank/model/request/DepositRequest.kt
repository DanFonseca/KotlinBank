package daniel.kotlinbank.model.request

import java.math.BigDecimal

class DepositRequest (
        override val accountId: Long,
        override val amount: BigDecimal
        ) : Request(accountId, amount) {
}