package com.example.book_practice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_practice.service.ifs.BookService;
import com.example.book_practice.vo.BookRequest;
import com.example.book_practice.vo.BookResponse;


@RestController
public class BookController {

	@Autowired
	BookService bookService;
	
	// 書名、ISBN、作者、價格、庫存數量、銷售量、分類(一種書籍可能會有多種分類)的新增、修改功能
		@PostMapping("/add_book")
		public BookResponse addBook(@RequestBody BookRequest bookRequest) {
			return bookService.addBook(bookRequest);
		}
		// =====================================================================

		

		// 書籍分類搜尋(一或多)，只顯示書名、ISBN、作者、價格、庫存量
		// 有符合其中一個搜尋條件即可
		@PostMapping("/cate_search_book")
		public BookResponse findByCategoryContaining(@RequestBody BookRequest bookRequest) {
			return bookService.findByCategoryContaining(bookRequest);
		}
		// =====================================================================

		
		

		// 書籍搜尋
		// 消費者: (透過書名或 ISBN或作者) ，只顯示書名、ISBN、作者、價格
		@PostMapping("/guest_search_book")
		public BookResponse guestSearchBook(@RequestBody BookRequest bookRequest) {
			return bookService.guestSearchBook(bookRequest);
		}
		// =====================================================================
		
		
		
		// 書籍搜尋
		// 書籍商: (透過書名或 ISBN或作者)，顯示書名、ISBN、作者、價格、銷售量、庫存量
		@PostMapping("/seller_search_book")
		public BookResponse sellerSearchBook(@RequestBody BookRequest bookRequest) {
			return bookService.sellerSearchBook(bookRequest);
			}
		// =====================================================================
		
		// 更新書籍資料(庫存量(進貨): 顯示書名、ISBN、作者、價格、庫存量 / 價格: 顯示書名、ISBN、作者、價格、庫存量)
		@PostMapping("/update_book")
		public BookResponse updateBook (@RequestBody BookRequest bookRequest) {
			return bookService.updateBook(bookRequest);
		}
		
		// 書籍銷售
		// 消費者購買(可買多本，但至多3本，取前3): 只顯示書名、ISBN、作者、價格、購買數量，購買總價格
		// 銷售量+1，庫存量-1
		@PostMapping("/buy_book")
		public BookResponse buyBook(@RequestBody BookRequest bookRequest) {
			return bookService.buyBook(bookRequest);
		}
		// =====================================================================
		
		// 暢銷書排行榜(依照銷售量前5，排序) ，只顯示書名、ISBN、作者、價格
		@PostMapping("/best_top_5")
		public BookResponse getBestByTop5OrderBySalesVolumeDesc(BookRequest bookRequest) {
			return bookService.getBestByTop5OrderBySalesVolumeDesc(bookRequest);
			}
		// =====================================================================
		
}
