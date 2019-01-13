package com.example.filterproject.namematcher.checker.match

import com.example.filterproject.namematcher.checker.CheckerTestResult
import com.example.filterproject.namematcher.checker.configuration.ApacheNGramDistance
import groovy.util.logging.Slf4j
import org.apache.commons.math3.util.Precision
import org.apache.commons.text.similarity.JaroWinklerDistance
import spock.lang.Specification

@Slf4j
class CheckersProductivityTest extends Specification { //TODO: NEEDS REFACTORING

    private ApacheNGramDistance apacheNGramDistance2 = new ApacheNGramDistance(2)
    private ApacheNGramDistance apacheNGramDistance3 = new ApacheNGramDistance(3)
    private JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance()

    def "Speed and result for equal data"() {
        given:
        List<String> words =
                ["a",
                 "me",
                 "bazz",
                 "pebble",
                 "beautiful",
                 "schnozzles",
                 "hydroxyzines",
                 "accompaniments",
                 "Undiscriminating",
                 "congregationalisms",
                 "Disproportionateness"
                ]
        when:
        List<CheckerTestResult> jwResults = new ArrayList<>()
        for (int i = 0; i < words.size(); i++) {
            jwResults.add(processAndCountTimeForWinkler(words.get(i)))
        }
        jwResults.forEach {
            result -> System.out.println("J-W. Time: " + result.getUsedTime() + " | Similarity: " + result.getSimilarity() + " | Original: " + result.getFirst())
        }
        printSpace()
        List<CheckerTestResult> ngramResults2 = new ArrayList<>()
        for (int i = 0; i < words.size(); i++) {
            ngramResults2.add(processAndCountTimeForNgram2(words.get(i)))
        }
        ngramResults2.forEach {
            result -> System.out.println("NGR2. Time: " + result.getUsedTime() + " | Similarity: " + result.getSimilarity() + " | Original: " + result.getFirst())
        }
        printSpace()
        List<CheckerTestResult> ngramResults3 = new ArrayList<>()
        for (int i = 0; i < words.size(); i++) {
            ngramResults3.add(processAndCountTimeForNgram3(words.get(i)))
        }
        ngramResults3.forEach {
            result -> System.out.println("NGR3. Time: " + result.getUsedTime() + " | Similarity: " + result.getSimilarity() + " | Original: " + result.getFirst())
        }
        printSpace()
        compareTimeForTestResult(ngramResults2, ngramResults3, jwResults)

        then:
        assert true
    }

    def "Speed and result for lightly changed data"() {
        given:
        List<String> originalWords =
                ["a",
                 "me",
                 "bazz",
                 "pebble",
                 "beautiful",
                 "schnozzles",
                 "hydroxyzines",
                 "accompaniments",
                 "Undiscriminating",
                 "congregationalisms",
                 "Disproportionateness"
                ]
        List<String> changedWords =
                ["a",
                 "me",
                 "bass",
                 "pebbll",
                 "beauty",
                 "schnossles",
                 "hydroxyssin",
                 "accompanimemnt",
                 "Undiscriminat",
                 "congregational",
                 "Disproportionatistic"
                ]

        when:
        List<CheckerTestResult> jwResults = new ArrayList<>()
        for (int i = 0; i < originalWords.size(); i++) {
            jwResults.add(processAndCountTimeForWinkler(originalWords.get(i), changedWords.get(i)))
        }
        jwResults.forEach {
            result -> System.out.println("J-W. Time: " + result.getUsedTime() + " | Similarity: " + result.getSimilarity() + " | Original: " + result.getFirst() + " | Changed: " + result.getSecond())
        }
        printSpace()
        List<CheckerTestResult> ngramResults2 = new ArrayList<>()
        for (int i = 0; i < originalWords.size(); i++) {
            ngramResults2.add(processAndCountTimeForNgram2(originalWords.get(i), changedWords.get(i)))
        }
        ngramResults2.forEach {
            result -> System.out.println("NGR2. Time: " + result.getUsedTime() + " | Similarity: " + result.getSimilarity() + " | Original: " + result.getFirst() + " | Changed: " + result.getSecond())
        }
        printSpace()
        List<CheckerTestResult> ngramResults3 = new ArrayList<>()
        for (int i = 0; i < originalWords.size(); i++) {
            ngramResults3.add(processAndCountTimeForNgram3(originalWords.get(i), changedWords.get(i)))
        }
        ngramResults3.forEach {
            result -> System.out.println("NGR3. Time: " + result.getUsedTime() + " | Similarity: " + result.getSimilarity() + " | Original: " + result.getFirst() + " | Changed: " + result.getSecond())
        }
        printSpace()
        compareTimeForTestResult(ngramResults2, ngramResults3, jwResults)

        then:
        assert true
    }

    private CheckerTestResult processAndCountTimeForWinkler(String str) {
        return processAndCountTimeForWinkler(str, str)
    }

    private CheckerTestResult processAndCountTimeForWinkler(String str, String str2) {
        Long timeInMs = 0L
        Double result = null
        timeInMs = System.nanoTime()
        result = Precision.round(jaroWinklerDistance.apply(str, str2), 2)
        timeInMs = System.nanoTime() - timeInMs
        return new CheckerTestResult(str, str2, timeInMs, result, "Jaro-Winkler")
    }

    private CheckerTestResult processAndCountTimeForNgram2(String str) {
        return processAndCountTimeForNgram2(str, str)
    }

    private CheckerTestResult processAndCountTimeForNgram3(String str) {
        return processAndCountTimeForNgram3(str, str)
    }

    private CheckerTestResult processAndCountTimeForNgram2(String str, String str2) {
        Long timeInMs = 0L
        Double result = null
        timeInMs = System.nanoTime()
        result = Precision.round(apacheNGramDistance2.getDistance(str, str2), 2)
        timeInMs = System.nanoTime() - timeInMs
        return new CheckerTestResult(str, str2, timeInMs, result, "N-Gram(2)")
    }

    private CheckerTestResult processAndCountTimeForNgram3(String str, String str2) {
        Long timeInMs = 0L
        Double result = null
        timeInMs = System.nanoTime()
        result = Precision.round(apacheNGramDistance3.getDistance(str, str2), 2)
        timeInMs = System.nanoTime() - timeInMs
        return new CheckerTestResult(str, str2, timeInMs, result, "N-Gram(3)")
    }

    private void compareTimeForTestResult(List<CheckerTestResult>... args) {
        List<CheckerTestResult> comparingList = new ArrayList<>()
        for (int testResultIndex = 0; testResultIndex < args[0].size(); testResultIndex++) {
            List<CheckerTestResult> tempComparingList = new ArrayList<>()
            for (int listIndex = 0; listIndex < args.size(); listIndex++) {
                tempComparingList.add(args[listIndex].get(testResultIndex))
            }
            CheckerTestResult result = tempComparingList.stream().min(Comparator.comparingLong({ it.usedTime })).get()
            System.out.println(result.getAlgorithmName() + " is the best for '" + result.getFirst() + "'")
        }
    }

    private void printSpace() {
        System.out.println("__________________________________________________________________________")
    }
}
