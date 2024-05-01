package com.kodakro.mapshop.entity.enums;

public enum ImageUrlType {

	THUMBNAIL("thumbnail"),
	IMAGE("image");
	
	private String value;
	
	private ImageUrlType( String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
