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
	
	// �ѦW�BISBN�B�@�̡B����B�w�s�ƶq�B�P��q�B����(�@�خ��y�i��|���h�ؤ���)���s�W�B�ק�\��
		@PostMapping("/add_book")
		public BookResponse addBook(@RequestBody BookRequest bookRequest) {
			return bookService.addBook(bookRequest);
		}
		// =====================================================================

		

		// ���y�����j�M(�@�Φh)�A�u��ܮѦW�BISBN�B�@�̡B����B�w�s�q
		// ���ŦX�䤤�@�ӷj�M����Y�i
		@PostMapping("/cate_search_book")
		public BookResponse findByCategoryContaining(@RequestBody BookRequest bookRequest) {
			return bookService.findByCategoryContaining(bookRequest);
		}
		// =====================================================================

		
		

		// ���y�j�M
		// ���O��: (�z�L�ѦW�� ISBN�Χ@��) �A�u��ܮѦW�BISBN�B�@�̡B����
		@PostMapping("/guest_search_book")
		public BookResponse guestSearchBook(@RequestBody BookRequest bookRequest) {
			return bookService.guestSearchBook(bookRequest);
		}
		// =====================================================================
		
		
		
		// ���y�j�M
		// ���y��: (�z�L�ѦW�� ISBN�Χ@��)�A��ܮѦW�BISBN�B�@�̡B����B�P��q�B�w�s�q
		@PostMapping("/seller_search_book")
		public BookResponse sellerSearchBook(@RequestBody BookRequest bookRequest) {
			return bookService.sellerSearchBook(bookRequest);
			}
		// =====================================================================
		
		// ��s���y���(�w�s�q(�i�f): ��ܮѦW�BISBN�B�@�̡B����B�w�s�q / ����: ��ܮѦW�BISBN�B�@�̡B����B�w�s�q)
		@PostMapping("/update_book")
		public BookResponse updateBook (@RequestBody BookRequest bookRequest) {
			return bookService.updateBook(bookRequest);
		}
		
		// ���y�P��
		// ���O���ʶR(�i�R�h���A���ܦh3���A���e3): �u��ܮѦW�BISBN�B�@�̡B����B�ʶR�ƶq�A�ʶR�`����
		// �P��q+1�A�w�s�q-1
		@PostMapping("/buy_book")
		public BookResponse buyBook(@RequestBody BookRequest bookRequest) {
			return bookService.buyBook(bookRequest);
		}
		// =====================================================================
		
		// �Z�P�ѱƦ�](�̷ӾP��q�e5�A�Ƨ�) �A�u��ܮѦW�BISBN�B�@�̡B����
		@PostMapping("/best_top_5")
		public BookResponse getBestByTop5OrderBySalesVolumeDesc(BookRequest bookRequest) {
			return bookService.getBestByTop5OrderBySalesVolumeDesc(bookRequest);
			}
		// =====================================================================
		
}
