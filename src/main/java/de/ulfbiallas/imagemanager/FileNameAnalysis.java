package de.ulfbiallas.imagemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.ulfbiallas.imagemanager.service.AutoTagService;
import de.ulfbiallas.imagemanager.service.AutoTagServiceImpl;

public class FileNameAnalysis {

    public static void main(String[] args) {
        String foldername = args[0]; // "C:\\Users\\UB\\Desktop\\pics";
        File folder = new File(foldername);

        AutoTagService autoTagService = new AutoTagServiceImpl();

        Map<String, Integer> tags = new HashMap<String, Integer>();
        for(File file : folder.listFiles()) {
            //System.out.println(file.getName());
            Set<String> tokens = autoTagService.extractTokens(file.getName());
            //System.out.println(autoTagService.getTagNamesForTokens(tokens));
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
