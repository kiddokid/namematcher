package com.example.filterproject.namematcher.formatter;

public class TextFormatter {
    //TODO: class that filter @RiskCustomer fields. Susbstituting wrong symbols to unicode, clearing email, addresses
    //TODO: State filter for US from Florida -> FL

    private String clearAddress(String address) {
        String[] insideKeyWords = {" str\\.", " ave ", " ave\\.", " suite "};
        String[] endsKeyWords = {" st", " st.", " street.", " street", " str", " road"};
        String result = address.toLowerCase();
        for (String replacement : insideKeyWords) {
            result = result.replaceAll(replacement, "");
        }
        for (String replacement : endsKeyWords) {
            if (result.endsWith(replacement)) {
                result = result.substring(0, result.lastIndexOf(replacement));
            }
        }
        return result;
    }

    private String clearEmail(String email) {
        String result;
        result = email.toLowerCase();
        if (result.contains("@")) {
            result = result.substring(0, result.indexOf("@"));
        }
        return result;
    }
}
