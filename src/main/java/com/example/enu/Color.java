package com.example.enu;

public enum Color {
	GREEN("1","绿色"),RED("2","红色"),YELLOW("3","黄色");
	private String name;
	private String value;
	private Color(String value,String name){
		this.name=name;
		this.value=value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public static Color get(String strValue) {
        for (Color e : values()) {
            if (e.getValue().equals(strValue)) {
                return e;
            }
        }
        System.out.println("null");
        return null;
    }
	@Override
    public String toString() {
        return this.name;
    }
}
