package com.arit.adserve.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="item")
@ToString
public class Item {	
	
	@Id	
	@NonNull
	private String itemId;
	@NonNull
	private String title;
	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> subTitles;
	private String description, source, productId, galeryURL, viewItemURL,
				   location, country, condition, priceForamtted, currency;
	@Lob
	private String image64BaseStr;
	@Lob
	private String modifiedImage64BaseStr;
	private int price;
	private boolean process;	
	private Date createdOn = new Date(), updatedOn = new Date();
	
}
