package de.ulfbiallas.imagemanager.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.ulfbiallas.imagemanager.body.AutoTagConfig;

@Component
public class AutoTagServiceImpl implements AutoTagService {

    final static Logger logger = LoggerFactory.getLogger(AutoTagServiceImpl.class);

    private Map<String, Set<String>> tokenTagMapping;

    public AutoTagServiceImpl() {
        tokenTagMapping = new HashMap<String, Set<String>>();
        try {
            String config = readFile(new File("autotag.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            List<AutoTagConfig> autoTagRules = objectMapper.readValue(config, new TypeReference<List<AutoTagConfig>>(){});
            for(AutoTagConfig rule : autoTagRules) {
                for(String token : rule.getTokens()) {
                    if(!tokenTagMapping.containsKey(token)) {
                        tokenTagMapping.put(token, new HashSet<String>());
                    }
                    tokenTagMapping.get(token).addAll(rule.getTags());
                }
            }
        } catch (FileNotFoundException e) {
            logger.warn("autotag.json not found: auto-tagging disabled.");
        } catch (JsonParseException e) {
            logger.error(e.getMessage());
        } catch (JsonMappingException e) {
            logger.warn("autotag.json malformed: auto-tagging disabled.");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        
    }

    @Override
    public Set<String> getTagNamesForTokens(Set<String> tokens) {
        Set<String> tags = new HashSet<String>();
        for(String token : tokens) {
            if(tokenTagMapping.containsKey(token)) {
                tags.addAll(tokenTagMapping.get(token));
            }
        }
        return tags;
    }

    // src: http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
    private String readFile(File file) throws FileNotFoundException {
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");
        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

}
