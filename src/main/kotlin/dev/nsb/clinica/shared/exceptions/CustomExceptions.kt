package dev.nsb.clinica.shared.exceptions

class UniqueValueException(message: String = "Already There is a record with this value", val field:String) : RuntimeException(message) {
}

class ExistsIdException(message: String = "There is a record with this value", val field:String) : RuntimeException(message) {
}