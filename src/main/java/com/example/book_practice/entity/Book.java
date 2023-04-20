package com.example.book_practice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "book")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
	
	@Column(name = "title")
	private String title;
	
	@Id
	@Column(name = "isbn")
	private String isbn;
	
	@Column(name = "writer")
	private String writer;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "inventory")
	private Integer inventory;
	
	@Column(name = "salesVolume")
	private Integer salesVolume;
	
	@Column(name = "category")
	private String category;



	public Book(String title, String isbn, String writer, Integer price, Integer inventory, String category) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.writer = writer;
		this.price = price;
		this.inventory = inventory;
		this.category = category;
	}

	public Book(String title, String isbn, String writer, Integer price, Integer inventory, Integer salesVolume) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.writer = writer;
		this.price = price;
		this.inventory = inventory;
		this.salesVolume = salesVolume;
	}

	public Book(String title, String isbn, String writer, Integer price) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.writer = writer;
		this.price = price;
	}

	public Book(String title, String isbn, String writer, Integer price, Integer inventory) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.writer = writer;
		this.price = price;
		this.inventory = inventory;
	}

	public Book(String title, String isbn, String writer, Integer price, Integer inventory, Integer salesVolume,
			String category) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.writer = writer;
		this.price = price;
		this.inventory = inventory;
		this.salesVolume = salesVolume;
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Book() {
		
	}

	

}
