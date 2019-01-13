package com.example.filterproject.namematcher.checker

import groovy.transform.Canonical

@Canonical
class CheckerTestResult {
    String first
    String second
    Long usedTime
    Double similarity
    String algorithmName
}
