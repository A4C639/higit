package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/2/18.
 */

//洗衣技巧item
public class SkillEntity {

    private String keywords;
    private String description;

    public SkillEntity(String keywords, String description) {
        this.keywords = keywords;
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SkillEntity{" +
                "keywords='" + keywords + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
