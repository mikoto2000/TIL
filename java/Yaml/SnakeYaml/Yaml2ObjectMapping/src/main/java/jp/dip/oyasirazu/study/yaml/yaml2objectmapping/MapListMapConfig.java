package jp.dip.oyasirazu.study.yaml.yaml2objectmapping;

import java.util.List;
import java.util.Map;

/**
 * Config
 */
public class MapListMapConfig {
    private Map<String, List<Snippet>> snippets;

    public void setSnippets(Map<String, List<Snippet>> snippets) {
        this.snippets = snippets;
    }

    public Map<String, List<Snippet>> getSnippets() {
        return snippets;
    }

    public String toString() {
        return snippets.toString();
    }

    public static class Snippet {
        private String label;
        private String description;
        private String newText;

        public Snippet() {}

        public Snippet(String label, String description, String newText) {
            this.label = label;
            this.description = description;
            this.newText = newText;
        }

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

