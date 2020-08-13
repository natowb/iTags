package me.inato.tagredeem.data;

public class Tag {


    private String display;
    private String lore;
    private String prefix;
    private String suffix;
    private boolean enchanted;


    public Tag(String display, String lore, String prefix, String suffix, boolean enchanted) {
        this.display = display;
        this.lore = lore;
        this.prefix = prefix;
        this.suffix = suffix;
        this.enchanted = enchanted;
    }

    public String getDisplay() {
        return display;
    }

    public String getLore() {
        return lore;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public boolean getEnchanted() {
        return enchanted;
    }
}
