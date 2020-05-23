package com.arit.adserve.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.NumericField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Item entity from a provider e.g. eBay
 * @author Alex Ryjoukhine
 * @since May 11, 2020
 * 
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Indexed
@Table(name="item")
@IdClass(ItemId.class)
public class Item {	
	@Id	
	@NotBlank(message = "providerItemId is mandatory")
	private String providerItemId;
	@Id	
	@NotBlank(message = "providerName is mandatory")
	private String providerName;
	@Version
	private Integer version;
	private Integer relevance;
	@DocumentId(name = "item_doc_id")
	private String docId;
	@Field
	@NotBlank(message = "title is mandatory")
	private String title;
	@NotBlank(message = "viewItemURL is mandatory")
	@Size(min = 15)
	private String viewItemURL;
	@Column
	@ElementCollection(targetClass=String.class)
	private Set<String> subTitles = new HashSet<>();
	@Field
	private String description;
	@Field
	private String location;
	@Field
	private String country;
	@Field
	private String condition;
	@Field
	private String currency;
	@Field @NumericField
	private int price;  //the price in cents, pence, kopecks etc.
	private String source, productId, galleryURL, priceFormatted;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String image64BaseStr;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String modifiedImage64BaseStr;
	private Date modifiedImageDate;	
	private boolean process, deleted;
	private int rank;
	private Date createdOn = new Date(), updatedOn = new Date();
	
}
