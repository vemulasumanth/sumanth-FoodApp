package com.sumanth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ContactInformation {
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String email;
	
	private String mobile;
	
	private String twitter;
	
	private String instagram;

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}
	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonProperty("mobile")
	public String getMobile() {
		return mobile;
	}
    
	@JsonProperty("mobile")
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonProperty("twitter")
	public String getTwitter() {
		return twitter;
	}

	@JsonProperty("twitter")
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	@JsonProperty("instagram")
	public String getInstagram() {
		return instagram;
	}

	@JsonProperty("instagram")
	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}
	
	
}
