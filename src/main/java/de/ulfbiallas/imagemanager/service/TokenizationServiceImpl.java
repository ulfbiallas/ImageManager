package de.ulfbiallas.imagemanager.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class TokenizationServiceImpl implements TokenizationService {

    private static final int MINIMUM_TAG_LENGTH = 2;

    private static final int MAXIMUM_GRAM_LENGTH = 5;

    public Set<String> tokenize(String string) {
        string = prepareString(string);
        String[] tokens = string.split(" ");

        List<String> tokenList = new ArrayList<String>();
        tokenList.addAll(new ArrayList<String>(Arrays.asList(tokens)));
        for(int n=1; n<=MAXIMUM_GRAM_LENGTH; ++n) {
            tokenList.addAll(extractNGrams(tokens, n));
        }

        tokenList = tokenList.stream(). //
                map(x -> x.trim()). //
                filter(x -> x.length() >= MINIMUM_TAG_LENGTH). //
                map(x -> x.toLowerCase()). //
                map(x -> normalizeUmlauts(x)). //
                collect(Collectors.toList());
        return new HashSet<String>(tokenList);
    }

    private List<String> extractNGrams(String[] tokens, int n) {
        if(n<1) {
            throw new RuntimeException("n has to be larger than 0!");
        }
        List<String> twoGrams = new ArrayList<String>();
        for(int k=0; k<tokens.length-n; ++k) {
            StringBuilder nGram = new StringBuilder();
            for(int i=0; i<n; ++i) {
                nGram.append(tokens[i]);
            }
            twoGrams.add(nGram.toString());
        }
        return twoGrams;
    }

    private String prepareString(String string) {
        string = string.replaceAll("-", " ");
        string = string.replaceAll("_", " ");
        string = string.replaceAll("\\.", " "); // replace '.'
        string = string.replaceAll(",", " ");
        string = string.replaceAll(";", " ");
        string = string.replaceAll("/", " ");
        string = string.replaceAll("\\\\", " "); // replace '\'
        string = string.replaceAll("'", " ");
        string = string.replaceAll("&", " ");
        string = string.replaceAll("\\(", " ");
        string = string.replaceAll("\\)", " ");
        return replaceMultipleSpaces(string);
    }

    private String replaceMultipleSpaces(String string) {
        return string.trim().replaceAll(" +", " ");
    }

    private String normalizeUmlauts(String token) {
        token = token.replaceAll("ä", "ae");
        token = token.replaceAll("ö", "oe");
        token = token.replaceAll("ü", "ue");
        token = token.replaceAll("ß", "ss");
        return token;
    }

}
