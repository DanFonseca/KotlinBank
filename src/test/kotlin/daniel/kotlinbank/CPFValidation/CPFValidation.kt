package daniel.kotlinbank.CPFValidation

import daniel.kotlinbank.service.Util.CPFUtil
import daniel.kotlinbank.errors.CPFInvalidException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class CPFValidation {

    @Test
    fun `Must Validation with a correct CPF` (){
        CPFUtil.myValidateCPF("498.093.870-16")
    }


    @Test
    fun `Must Validation with a incorrect CPF` (){
        val exception = Assertions.assertThrows(CPFInvalidException::class.java) {
            CPFUtil.myValidateCPF("498.093.870-01")
        }

        Assertions.assertEquals("CPF is invalid", exception.message)
    }

    @Test
    fun `Must Validation with a lees 10 character` (){
        val exception = Assertions.assertThrows(CPFInvalidException::class.java) {
            CPFUtil.myValidateCPF("498.093.870-0")
        }

        Assertions.assertEquals("CPF Must Has 11 Characters", exception.message)
    }

    @Test
    fun `Must Validation with a character` (){
        val exception = Assertions.assertThrows(NumberFormatException::class.java) {
            CPFUtil.myValidateCPF("ABCD.EFG.HIJ-K")
        }

        Assertions.assertEquals("CPF Must Be a Number", exception.message)
    }
}