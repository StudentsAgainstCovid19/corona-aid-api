package com.chillibits.coronaaid.exception.exception

class HistoryItemNotFoundException(historyItemId: Int):
        RuntimeException("History item with the id $historyItemId not found.")