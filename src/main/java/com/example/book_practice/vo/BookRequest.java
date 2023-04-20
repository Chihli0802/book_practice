package com.example.book_practice.vo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.book_practice.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookRequest {

	private List<Book> bookList;

	// 以分類收尋書籍
	private Set<String> cateList;

	@JsonProperty("buy_book")
	private Map<String, Integer> buyBook;

	// 消費者搜尋書籍
	@JsonProperty("guest_search_list")
	private Set<String> guestSearchList;

	// 書籍商搜尋書籍
	@JsonProperty("seller_search_list")
	private Set<String> sellerSearchList;
	
	
	//更改庫存或是價格
	@JsonProperty("i_for_inventory_p_for_price")
	private String update;
	//更改書籍資料用
	@JsonProperty("update_volume")
	private Integer updateVolume;

	private String title;

	private String isbn;

	private String writer;

	private Integer price;

	private Integer inventory;

	private Integer salesVolume;

	private String category;

	
	public Integer getUpdateVolume() {
		return updateVolume;
	}

	public void setUpdateVolume(Integer updateVolume) {
		this.updateVolume = updateVolume;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public Set<String> getSellerSearchList() {
		return sellerSearchList;
	}

	public void setSellerSearchList(Set<String> sellerSearchList) {
		this.sellerSearchList = sellerSearchList;
	}

	public Set<String> getGuestSearchList() {
		return guestSearchList;
	}

	public void setGuestSearchList(Set<String> guestSearchList) {
		this.guestSearchList = guestSearchList;
	}

	public BookRequest(Set<String> guestSearchList) {
		super();
		this.guestSearchList = guestSearchList;
	}

	public Set<String> getCateList() {
		return cateList;
	}

	public void setCateList(Set<String> cateList) {
		this.cateList = cateList;
	}

	public Map<String, Integer> getBuyBook() {
		return buyBook;
	}

	public void setBuyBook(Map<String, Integer> buyBook) {
		this.buyBook = buyBook;
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

	public BookRequest() {

	}
}
