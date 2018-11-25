package com.example.filterproject.namematcher.formatter;

import com.example.filterproject.namematcher.model.RiskCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static java.util.Objects.nonNull;

@Component
@Slf4j
public class TextFormatter {
    //TODO: class that filter @RiskCustomer fields. Susbstituting wrong symbols to unicode, clearing email, addresses

    public RiskCustomer process(RiskCustomer riskCustomer) {
        RiskCustomer result = riskCustomer;
        result = processAddresses(result);
        result = processCountryAndState(result);
        return result;
    }

    private RiskCustomer processCountryAndState(RiskCustomer riskCustomer) {
        riskCustomer.setCountry(countryToCode(riskCustomer.getCountry()));
        if (isUS(riskCustomer)) {
            riskCustomer.setRegion_state(stateToCodeUs(riskCustomer.getRegion_state()));
        }
        return riskCustomer;
    }

    private RiskCustomer processAddresses(RiskCustomer riskCustomer) {
        riskCustomer.setAddress1(clearAddressSafely(riskCustomer.getAddress1()));
        riskCustomer.setAddress2(clearAddressSafely(riskCustomer.getAddress2()));
        return riskCustomer;
    }

    public String stateToCodeUs(String state) {
        if (nonNull(state)) {
            if (state.length() > 2) {
                Map<String, String> states = new HashMap<>();
                states.put("Alabama", "AL");
                states.put("Alaska", "AK");
                states.put("Alberta", "AB");
                states.put("American Samoa", "AS");
                states.put("Arizona", "AZ");
                states.put("Arkansas", "AR");
                states.put("Armed Forces (AE)", "AE");
                states.put("Armed Forces Americas", "AA");
                states.put("Armed Forces Pacific", "AP");
                states.put("British Columbia", "BC");
                states.put("California", "CA");
                states.put("Colorado", "CO");
                states.put("Connecticut", "CT");
                states.put("Delaware", "DE");
                states.put("District Of Columbia", "DC");
                states.put("Florida", "FL");
                states.put("Georgia", "GA");
                states.put("Guam", "GU");
                states.put("Hawaii", "HI");
                states.put("Idaho", "ID");
                states.put("Illinois", "IL");
                states.put("Indiana", "IN");
                states.put("Iowa", "IA");
                states.put("Kansas", "KS");
                states.put("Kentucky", "KY");
                states.put("Louisiana", "LA");
                states.put("Maine", "ME");
                states.put("Manitoba", "MB");
                states.put("Maryland", "MD");
                states.put("Massachusetts", "MA");
                states.put("Michigan", "MI");
                states.put("Minnesota", "MN");
                states.put("Mississippi", "MS");
                states.put("Missouri", "MO");
                states.put("Montana", "MT");
                states.put("Nebraska", "NE");
                states.put("Nevada", "NV");
                states.put("New Brunswick", "NB");
                states.put("New Hampshire", "NH");
                states.put("New Jersey", "NJ");
                states.put("New Mexico", "NM");
                states.put("New York", "NY");
                states.put("Newfoundland", "NF");
                states.put("North Carolina", "NC");
                states.put("North Dakota", "ND");
                states.put("Northwest Territories", "NT");
                states.put("Nova Scotia", "NS");
                states.put("Nunavut", "NU");
                states.put("Ohio", "OH");
                states.put("Oklahoma", "OK");
                states.put("Ontario", "ON");
                states.put("Oregon", "OR");
                states.put("Pennsylvania", "PA");
                states.put("Prince Edward Island", "PE");
                states.put("Puerto Rico", "PR");
                states.put("Quebec", "QC");
                states.put("Rhode Island", "RI");
                states.put("Saskatchewan", "SK");
                states.put("South Carolina", "SC");
                states.put("South Dakota", "SD");
                states.put("Tennessee", "TN");
                states.put("Texas", "TX");
                states.put("Utah", "UT");
                states.put("Vermont", "VT");
                states.put("Virgin Islands", "VI");
                states.put("Virginia", "VA");
                states.put("Washington", "WA");
                states.put("West Virginia", "WV");
                states.put("Wisconsin", "WI");
                states.put("Wyoming", "WY");
                states.put("Yukon Territory", "YT");

                return states.get(state);
            }
        }
        return null;
    }

    public String countryToCode(String country) {
        String countryCode;
        if (country.length() > 2) {
            final Map<String, String> map = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
            map.put("Andorra, Principality Of", "AD");
            map.put("United Arab Emirates", "AE");
            map.put("Afghanistan, Islamic State Of", "AF");
            map.put("Antigua And Barbuda", "AG");
            map.put("Anguilla", "AI");
            map.put("Albania", "AL");
            map.put("Armenia", "AM");
            map.put("Netherlands Antilles", "AN");
            map.put("Angola", "AO");
            map.put("Antarctica", "AQ");
            map.put("Argentina", "AR");
            map.put("American Samoa", "AS");
            map.put("Austria", "AT");
            map.put("Australia", "AU");
            map.put("Aruba", "AW");
            map.put("Azerbaidjan", "AZ");
            map.put("Bosnia-Herzegovina", "BA");
            map.put("Barbados", "BB");
            map.put("Bangladesh", "BD");
            map.put("Belgium", "BE");
            map.put("Burkina Faso", "BF");
            map.put("Bulgaria", "BG");
            map.put("Bahrain", "BH");
            map.put("Burundi", "BI");
            map.put("Benin", "BJ");
            map.put("Bermuda", "BM");
            map.put("Brunei Darussalam", "BN");
            map.put("Bolivia", "BO");
            map.put("Brazil", "BR");
            map.put("Bahamas", "BS");
            map.put("Bhutan", "BT");
            map.put("Bouvet Island", "BV");
            map.put("Botswana", "BW");
            map.put("Belarus", "BY");
            map.put("Belize", "BZ");
            map.put("Canada", "CA");
            map.put("Cocos (Keeling) Islands", "CC");
            map.put("Central African Republic", "CF");
            map.put("Congo, The Democratic Republic Of The", "CD");
            map.put("Congo", "CG");
            map.put("Switzerland", "CH");
            map.put("Ivory Coast (Cote D'Ivoire)", "CI");
            map.put("Cook Islands", "CK");
            map.put("Chile", "CL");
            map.put("Cameroon", "CM");
            map.put("China", "CN");
            map.put("Colombia", "CO");
            map.put("Costa Rica", "CR");
            map.put("Former Czechoslovakia", "CS");
            map.put("Cuba", "CU");
            map.put("Cape Verde", "CV");
            map.put("Christmas Island", "CX");
            map.put("Cyprus", "CY");
            map.put("Czech Republic", "CZ");
            map.put("Germany", "DE");
            map.put("Djibouti", "DJ");
            map.put("Denmark", "DK");
            map.put("Dominica", "DM");
            map.put("Dominican Republic", "DO");
            map.put("Algeria", "DZ");
            map.put("Ecuador", "EC");
            map.put("Estonia", "EE");
            map.put("Egypt", "EG");
            map.put("Western Sahara", "EH");
            map.put("Eritrea", "ER");
            map.put("Spain", "ES");
            map.put("Ethiopia", "ET");
            map.put("Finland", "FI");
            map.put("Fiji", "FJ");
            map.put("Falkland Islands", "FK");
            map.put("Micronesia", "FM");
            map.put("Faroe Islands", "FO");
            map.put("France", "FR");
            map.put("France (European Territory)", "FX");
            map.put("Gabon", "GA");
            map.put("Great Britain", "UK");
            map.put("Grenada", "GD");
            map.put("Georgia", "GE");
            map.put("French Guyana", "GF");
            map.put("Ghana", "GH");
            map.put("Gibraltar", "GI");
            map.put("Greenland", "GL");
            map.put("Gambia", "GM");
            map.put("Guinea", "GN");
            map.put("Guadeloupe (French)", "GP");
            map.put("Equatorial Guinea", "GQ");
            map.put("Greece", "GR");
            map.put("S. Georgia & S. Sandwich Isls.", "GS");
            map.put("Guatemala", "GT");
            map.put("Guam (USA)", "GU");
            map.put("Guinea Bissau", "GW");
            map.put("Guyana", "GY");
            map.put("Hong Kong", "HK");
            map.put("Heard And McDonald Islands", "HM");
            map.put("Honduras", "HN");
            map.put("Croatia", "HR");
            map.put("Haiti", "HT");
            map.put("Hungary", "HU");
            map.put("Indonesia", "ID");
            map.put("Ireland", "IE");
            map.put("Israel", "IL");
            map.put("India", "IN");
            map.put("British Indian Ocean Territory", "IO");
            map.put("Iraq", "IQ");
            map.put("Iran", "IR");
            map.put("Iceland", "IS");
            map.put("Italy", "IT");
            map.put("Jamaica", "JM");
            map.put("Jordan", "JO");
            map.put("Japan", "JP");
            map.put("Kenya", "KE");
            map.put("Kyrgyz Republic (Kyrgyzstan)", "KG");
            map.put("Cambodia, Kingdom Of", "KH");
            map.put("Kiribati", "KI");
            map.put("Comoros", "KM");
            map.put("Saint Kitts & Nevis Anguilla", "KN");
            map.put("North Korea", "KP");
            map.put("South Korea", "KR");
            map.put("Kuwait", "KW");
            map.put("Cayman Islands", "KY");
            map.put("Kazakhstan", "KZ");
            map.put("Laos", "LA");
            map.put("Lebanon", "LB");
            map.put("Saint Lucia", "LC");
            map.put("Liechtenstein", "LI");
            map.put("Sri Lanka", "LK");
            map.put("Liberia", "LR");
            map.put("Lesotho", "LS");
            map.put("Lithuania", "LT");
            map.put("Luxembourg", "LU");
            map.put("Latvia", "LV");
            map.put("Libya", "LY");
            map.put("Morocco", "MA");
            map.put("Monaco", "MC");
            map.put("Moldavia", "MD");
            map.put("Moldova", "MD");
            map.put("Madagascar", "MG");
            map.put("Marshall Islands", "MH");
            map.put("Macedonia", "MK");
            map.put("Mali", "ML");
            map.put("Myanmar", "MM");
            map.put("Mongolia", "MN");
            map.put("Macau", "MO");
            map.put("Northern Mariana Islands", "MP");
            map.put("Martinique (French)", "MQ");
            map.put("Mauritania", "MR");
            map.put("Montserrat", "MS");
            map.put("Malta", "MT");
            map.put("Mauritius", "MU");
            map.put("Maldives", "MV");
            map.put("Malawi", "MW");
            map.put("Mexico", "MX");
            map.put("Malaysia", "MY");
            map.put("Mozambique", "MZ");
            map.put("Namibia", "NA");
            map.put("New Caledonia (French)", "NC");
            map.put("Niger", "NE");
            map.put("Norfolk Island", "NF");
            map.put("Nigeria", "NG");
            map.put("Nicaragua", "NI");
            map.put("Netherlands", "NL");
            map.put("Norway", "NO");
            map.put("Nepal", "NP");
            map.put("Nauru", "NR");
            map.put("Neutral Zone", "NT");
            map.put("Niue", "NU");
            map.put("New Zealand", "NZ");
            map.put("Oman", "OM");
            map.put("Panama", "PA");
            map.put("Peru", "PE");
            map.put("Polynesia (French)", "PF");
            map.put("Papua New Guinea", "PG");
            map.put("Philippines", "PH");
            map.put("Pakistan", "PK");
            map.put("Poland", "PL");
            map.put("Saint Pierre And Miquelon", "PM");
            map.put("Pitcairn Island", "PN");
            map.put("Puerto Rico", "PR");
            map.put("Portugal", "PT");
            map.put("Palau", "PW");
            map.put("Paraguay", "PY");
            map.put("Qatar", "QA");
            map.put("Reunion (French)", "RE");
            map.put("Romania", "RO");
            map.put("Russia", "RU");
            map.put("Rwanda", "RW");
            map.put("Saudi Arabia", "SA");
            map.put("Solomon Islands", "SB");
            map.put("Seychelles", "SC");
            map.put("Sudan", "SD");
            map.put("Sweden", "SE");
            map.put("Singapore", "SG");
            map.put("Saint Helena", "SH");
            map.put("Slovenia", "SI");
            map.put("Svalbard And Jan Mayen Islands", "SJ");
            map.put("Slovak Republic", "SK");
            map.put("Sierra Leone", "SL");
            map.put("San Marino", "SM");
            map.put("Senegal", "SN");
            map.put("Somalia", "SO");
            map.put("Suriname", "SR");
            map.put("Saint Tome (Sao Tome) And Principe", "ST");
            map.put("Former USSR", "SU");
            map.put("El Salvador", "SV");
            map.put("Syria", "SY");
            map.put("Swaziland", "SZ");
            map.put("Turks And Caicos Islands", "TC");
            map.put("Chad", "TD");
            map.put("French Southern Territories", "TF");
            map.put("Togo", "TG");
            map.put("Thailand", "TH");
            map.put("Tadjikistan", "TJ");
            map.put("Tokelau", "TK");
            map.put("Turkmenistan", "TM");
            map.put("Tunisia", "TN");
            map.put("Tonga", "TO");
            map.put("East Timor", "TP");
            map.put("Turkey", "TR");
            map.put("Trinidad And Tobago", "TT");
            map.put("Tuvalu", "TV");
            map.put("Taiwan", "TW");
            map.put("Tanzania", "TZ");
            map.put("Ukraine", "UA");
            map.put("Uganda", "UG");
            map.put("United Kingdom", "UK");
            map.put("USA Minor Outlying Islands", "UM");
            map.put("United States", "US");
            map.put("Uruguay", "UY");
            map.put("Uzbekistan", "UZ");
            map.put("Holy See (Vatican City State)", "VA");
            map.put("Saint Vincent & Grenadines", "VC");
            map.put("Venezuela", "VE");
            map.put("Virgin Islands (British)", "VG");
            map.put("Virgin Islands (USA)", "VI");
            map.put("Vietnam", "VN");
            map.put("Vanuatu", "VU");
            map.put("Wallis And Futuna Islands", "WF");
            map.put("Samoa", "WS");
            map.put("Yemen", "YE");
            map.put("Mayotte", "YT");
            map.put("Yugoslavia", "YU");
            map.put("South Africa", "ZA");
            map.put("Zambia", "ZM");
            map.put("Zaire", "ZR");
            map.put("Zimbabwe", "ZW");
            countryCode = map.get(country);
            if (Objects.isNull(countryCode)) {
                log.error("[TEXT-FORMATTER] - Country {} not found", country);
            }
            return countryCode;
        }
        return country;
    }

    private String clearAddressSafely(String address) {
        if (!address.isEmpty()) {
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
        } else {
            log.info("[TEXT-FORMATTER] - address is empty. Skipping");
            return null;
        }
    }

    private String clearEmail(String email) {
        String result;
        result = email.toLowerCase();
        if (result.contains("@")) {
            result = result.substring(0, result.indexOf("@"));
        }
        return result;
    }

    private boolean isUS(RiskCustomer riskCustomer) {
        return riskCustomer.getCountry().equals("US");
    }
}
