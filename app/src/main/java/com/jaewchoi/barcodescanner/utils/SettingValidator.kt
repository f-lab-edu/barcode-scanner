package com.jaewchoi.barcodescanner.utils

import com.jaewchoi.barcodescanner.domain.model.SheetsSettings

object SettingValidator {
    private val CELL_REF_REGEX = Regex("^[A-Za-z][0-9]+$")
    private val ALPHA_REGEX = Regex("^[A-Za-z]$")

    private fun isCellReference(value: String): Boolean =
        CELL_REF_REGEX.matches(value)

    private fun isNumeric(value: String): Boolean =
        value.isNotEmpty() && value.all { it.isDigit() }

    private fun isSingleAlphabet(value: String): Boolean =
        ALPHA_REGEX.matches(value)

    fun checkSheetsSettingValid(settings: SheetsSettings): Boolean {
        val hasNotEmptyValue = listOf(
            settings.fileID,
            settings.sheetsName,
            settings.tableCell,
            settings.fieldCount,
            settings.barcodeColumn
        ).all { it.isNotEmpty() }
        return hasNotEmptyValue &&
                isCellReference(settings.tableCell) &&
                isNumeric(settings.fieldCount) &&
                isSingleAlphabet(settings.barcodeColumn)
    }
}