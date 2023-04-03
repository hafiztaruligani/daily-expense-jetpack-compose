package com.dailyexpenses


fun String.formatCurrencies(): String {
    var result = ""
    var data = this
    while (data.isNotBlank()){
        result = "." + data.takeLast(3) + result
        data = data.dropLast(3)
    }
    if (result.length>1 && result.first()=='.')
        result = result.drop(1)
    return result
}