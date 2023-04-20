package com.example.book_practice.service.ifs;

import com.example.book_practice.vo.BookRequest;
import com.example.book_practice.vo.BookResponse;

public interface BookService {

	//書名、ISBN、作者、價格、庫存數量、銷售量、分類(一種書籍可能會有多種分類)的新增、修改功能
		public BookResponse addBook(BookRequest bookRequest);
		//=====================================================================
		
		

		//書籍分類搜尋(一或多)，只顯示書名、ISBN、作者、價格、庫存量
		//有符合其中一個搜尋條件即可
		public BookResponse findByCategoryContaining(BookRequest bookRequest);
		//=====================================================================
		
		

		//書籍搜尋
		//消費者: (透過書名或 ISBN或作者) ，只顯示書名、ISBN、作者、價格
		public BookResponse guestSearchBook(BookRequest bookRequest);
		//書籍商: (透過書名或 ISBN或作者)，顯示書名、ISBN、作者、價格、銷售量、庫存量
		public BookResponse sellerSearchBook(BookRequest bookRequest);
		//=====================================================================
		
		

		//更新書籍資料
		//庫存量(進貨)，顯示書名、ISBN、作者、價格、庫存量
		//價格，顯示書名、ISBN、作者、價格、庫存量
		public BookResponse updateBook(BookRequest bookRequest);
		//=====================================================================
		
		

		//書籍銷售
		//消費者購買(可買多本，但至多3本，取前3): 只顯示書名、ISBN、作者、價格、購買數量，購買總價格
		//銷售量+1，庫存量-1
		public BookResponse buyBook(BookRequest bookRequest);
		//=====================================================================
		

		//暢銷書排行榜(依照銷售量前5，排序) ，只顯示書名、ISBN、作者、價格
		public BookResponse getBestByTop5OrderBySalesVolumeDesc(BookRequest bookRequest);
		//=====================================================================
}
