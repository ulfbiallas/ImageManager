package de.ulfbiallas.imagemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.ulfbiallas.imagemanager.service.TokenizationService;
import de.ulfbiallas.imagemanager.service.TokenizationServiceImpl;

public class FileNameAnalysis {

    public static void main(String[] args) {
        String foldername = args[0];
        File folder = new File(foldername);

        TokenizationService tokenizationService = new TokenizationServiceImpl();

        Map<String, Integer> tags = new HashMap<String, Integer>();
        for(File file : folder.listFiles()) {
            Set<String> tokens = tokenizationService.tokenize(file.getName());
            for(String token : tokens) {
                if(tags.containsKey(token)) {
                    tags.put(token, tags.get(token)+1);
                } else {
                    tags.put(token, 1);
                }
            }
        }

        List<String> keys = new ArrayList<String>(tags.keySet());
        keys.sort((x,y) -> tags.get(y)-tags.get(x));
        for(String key : keys) {
            System.out.println(key + ": " + tags.get(key));
        }
    }

}
