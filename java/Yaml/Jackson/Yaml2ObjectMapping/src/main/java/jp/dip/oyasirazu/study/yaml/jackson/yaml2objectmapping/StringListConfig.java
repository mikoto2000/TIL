package jp.dip.oyasirazu.study.yaml.jackson.yaml2objectmapping;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * Config
 */
public class StringListConfig {
    private List<String> snippets;

    public void setSnippets(List<String> snippets) {
        this.snippets = snippets;
    }

    public List<String> getSnippets() {
        return snippets;
    }

    public String toString() {
        return String.join(", ", snippets);
    }
}

