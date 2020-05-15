package com.hali.spring.trackingapp.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationData implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 580754096006639303L;
	
	private String id;
	private double lat;
	private double lng;
}
