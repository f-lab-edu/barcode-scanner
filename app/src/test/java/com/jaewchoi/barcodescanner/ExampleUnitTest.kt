package com.jaewchoi.barcodescanner

import com.jaewchoi.barcodescanner.domain.model.SheetsSettings
import com.jaewchoi.barcodescanner.utils.SettingValidator
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `빈 시트 세팅을 넣었을때`() {
        assertFalse(SettingValidator.checkSheetsSettingValid(
            SheetsSettings(
                "",
                "",
                "",
                "",
                ""
            )
        ))
    }

    @Test
    fun `fieldCount가 numeric이 아닐때`() {
        assertFalse(SettingValidator.checkSheetsSettingValid(
            SheetsSettings(
                "",
                "",
                "",
                "",
                ""
            )
        ))
    }

    @Test
    fun `정확한 SheetSettings값이 들어왔을때`() {
        assertFalse(SettingValidator.checkSheetsSettingValid(
            SheetsSettings(
                "",
                "",
                "",
                "",
                ""
            )
        ))
    }
}