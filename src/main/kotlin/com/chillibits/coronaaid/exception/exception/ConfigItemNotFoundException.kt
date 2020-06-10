package com.chillibits.coronaaid.exception.exception

class ConfigItemNotFoundException(configKey: String):
        RuntimeException("Config item with the key $configKey not found.")