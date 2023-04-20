package com.example.book_practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book_practice.entity.Book;



@Repository
public interface BookDao extends JpaRepository<Book, String> {

	// 書籍分類搜尋(一或多)，只顯示書名、ISBN、作者、價格、庫存量
		// 有符合其中一個搜尋條件即可
		//只要回傳的資料不只一筆就用List
		List<Book> findByCategoryContaining(String category);
		// =====================================================================

		
		// 書籍搜尋
		// 消費者: (透過書名或 ISBN或作者) ，只顯示書名、ISBN、作者、價格
		// 書籍商: (透過書名或 ISBN或作者)，顯示書名、ISBN、作者、價格、銷售量、庫存量
		List<Book> findByTitleContainingOrIsbnOrWriterContaining(String title,String isbn, String writer);
		// =====================================================================
		
		
		// 暢銷書排行榜(依照銷售量前5，排序) ，只顯示書名、ISBN、作者、價格
		List<Book> findTop5ByOrderBySalesVolumeDesc();
		// =====================================================================
		

		// 更新書籍資料
		// 庫存量(進貨)  顯示書名、ISBN、作者、價格、庫存量
		// 價格         顯示書名、ISBN、作者、價格、庫存
		List<Book> findByIsbn(String isbn);
		// =====================================================================
}
