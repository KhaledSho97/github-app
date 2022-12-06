package com.khaledsh.core.util


fun String.setPathParameter(key: String, value: String): String {
    return replace("{$key}", value)
}

