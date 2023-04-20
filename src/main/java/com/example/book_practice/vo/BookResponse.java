package com.example.book_practice.vo;

import java.util.List;
import java.util.Set;

import com.example.book_practice.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse {

	private Set<Book> bookSet;

	private List<Book> bookList;

	private List<String> cantFind;
	
	@JsonProperty("update_book")
	private List<Book> updateBook;
	
	@JsonProperty("new_book")
	private List<Book> addBook;
	
	private Book book;
	private String title;

	private String isbn;

	private String writer;

	private Integer price;

	private Integer inventory;

	private Integer salesVolume;

	private String category;

	private String message;
	
	// 買書總金額
	private Integer totalPrice;
	//買書數量
	private Integer buyVolume;
	@JsonProperty("buy_list")
	private List<BookResponse> res;
	
	
	
	public BookResponse( List<Book> addBook,List<Book> updateBook) {
		super();
		this.updateBook = updateBook;
		this.addBook = addBook;
	}

	public BookResponse(Integer buyVolume, String title, String isbn, String writer, Integer price) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.writer = writer;
		this.price = price;
		this.buyVolume = buyVolume;
	}

	public Integer getBuyVolume() {
		return buyVolume;
	}

	public void setBuyVolume(Integer buyVolume) {
		this.buyVolume = buyVolume;
	}

	// 書籍購買
	public BookResponse(Integer totalPrice, List<BookResponse> res) {
		super();
		this.totalPrice = totalPrice;
		this.res = res;
	}

	// 書籍購買
	public BookResponse(String title, String isbn, Integer inventory, String message) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.inventory = inventory;
		this.message = message;
	}


	public BookResponse(String title, String isbn, String writer, Integer price, Integer inventory, String message) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.writer = writer;
		this.price = price;
		this.inventory = inventory;
		this.message = message;
	}


	public BookResponse(Book book, String message) {
		super();
		this.book = book;
		this.message = message;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BookResponse(String isbn, String message) {
		super();
		this.isbn = isbn;
		this.message = message;
	}

	public BookResponse(String message, List<String> cantFind) {
		super();
		this.cantFind = cantFind;
		this.message = message;
	}

	public List<String> getCantFind() {
		return cantFind;
	}

	public void setCantFind(List<String> cantFind) {
		this.cantFind = cantFind;
	}

	public BookResponse(Set<Book> bookSet) {
		super();
		this.bookSet = bookSet;
	}

	public Set<Book> getBookSet() {
		return bookSet;
	}

	public void setBookSet(Set<Book> bookSet) {
		this.bookSet = bookSet;
	}


	public BookResponse(List<Book> bookList, String message) {
		super();
		this.bookList = bookList;
		this.message = message;
	}

	public BookResponse(List<Book> bookList) {
		super();
		this.bookList = bookList;
	}

	public BookResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
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

	public BookResponse() {

	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<BookResponse> getRes() {
		return res;
	}

	public void setRes(List<BookResponse> res) {
		this.res = res;
	}

	public List<Book> getUpdateBook() {
		return updateBook;
	}

	public void setUpdateBook(List<Book> updateBook) {
		this.updateBook = updateBook;
	}

	
}
