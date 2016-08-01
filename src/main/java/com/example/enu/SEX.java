package com.example.enu;

public enum SEX {

    /**
     * 男
     */
    Male("1", "男"),

    /**
     * 女
     */
    Female("-1", "女"),

    /**
     * 保密
     */
    Unknown("0", "保密");

    private final String value;

    private final String description;

    private SEX(String v, String desc) {
        this.value = v;
        this.description = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
    	System.out.println("description");
        return description;
    }

    public static SEX get(String strValue) {
        for (SEX e : values()) {
            if (e.getValue().equals(strValue)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
    	System.out.println("toString");
        return this.value;
    }

}