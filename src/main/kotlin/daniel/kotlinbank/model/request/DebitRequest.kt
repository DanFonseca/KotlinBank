package daniel.kotlinbank.model.request

import java.math.BigDecimal

class DebitRequest  (
        override val accountId: Long,
        override val amount: BigDecimal
        ) : Request (accountId, amount) {
}