package jp.dip.oyasirazu.study.yaml.yaml2objectmapping;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * Config
 */
public class MapListMapConfig {
    private Map<String, List<Map<String, Snippet>>> snippets;

    public void setSnippets(Map<String, List<Map<String, Snippet>>> snippets) {
        this.snippets = snippets;
    }

    public Map<String, List<Map<String, Snippet>>> getSnippets() {
        return snippets;
    }

    public String toString() {
        return snippets.toString();
    }

    public static class Snippet {
        private String label;
        private String description;
        private String newText;

        public void setLabel(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setNewText(String newText) {
            this.newText = newText;
        }

        public String getNewText() {
            return newText;
        }

        public String toString() {
            return String.format("{label: %s, description: %s, newText: %s}", label, description, newText);
        }
    }
}

