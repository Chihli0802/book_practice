package com.example.book_practice.service.impl;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.book_practice.entity.Book;
import com.example.book_practice.repository.BookDao;
import com.example.book_practice.service.ifs.BookService;
import com.example.book_practice.vo.BookRequest;
import com.example.book_practice.vo.BookResponse;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookDao bookDao;

	// 書名、ISBN、作者、價格、庫存數量、銷售量、分類(一種書籍可能會有多種分類)的新增、修改功能
	@Override
	public BookResponse addBook(BookRequest bookRequest) {
		List<Book> bookInfoList = bookRequest.getBookList();
		List<Book> updateBook = new ArrayList<>();
		List<Book> newBook = new ArrayList<>();
		if (CollectionUtils.isEmpty(bookInfoList)) {
			return new BookResponse("不得為空");
		}
		for (Book item : bookInfoList) {
			//防呆: 不得為空
			if (!StringUtils.hasText(item.getTitle()) || !StringUtils.hasText(item.getIsbn())
					|| !StringUtils.hasText(item.getWriter()) || !StringUtils.hasText(item.getCategory())) {
				return new BookResponse("資訊不得為空");
			}
			//防呆: 數字不得小於0
			if (item.getInventory() < 0 || item.getSalesVolume() < 0 || item.getPrice() < 0) {
				return new BookResponse("數量不得小於0");
			}
			
			//更新資訊，如果是已存在的id，表示是要更新商品資訊，加到updateBook list
			if (bookDao.existsById(item.getIsbn())) {
				updateBook.add(item);
				
			//如果是不存在的id，表示是要新增商品資訊，加到newBook list
			}else {
				newBook.add(item);
			}
			
		}
		
		//如果兩個list都有內容的話，都save，然後response
		if(!CollectionUtils.isEmpty(updateBook)&&!CollectionUtils.isEmpty(newBook)) {
			bookDao.saveAll(updateBook);
			bookDao.saveAll(newBook);
			return new BookResponse(newBook,updateBook);
		}
		//如果只有updateBook有內容(newBook List是空的)
		if (!CollectionUtils.isEmpty(updateBook)&& CollectionUtils.isEmpty(newBook)) {
			bookDao.saveAll(updateBook);
			return new BookResponse(updateBook, "商品已更新");
		}
		//如果只有newBook有內容
		bookDao.saveAll(newBook);
		return new BookResponse(newBook, "已新增商品");
		
	}
	// =====================================================================
	
	
	
	// 書籍分類搜尋(一或多)，只顯示書名、ISBN、作者、價格、庫存量，有符合其中一個搜尋條件即可
	@Override
	public BookResponse findByCategoryContaining(BookRequest bookRequest) {
		Set<String> cateList = bookRequest.getCateList();
		List<Book> list = new ArrayList<>();
		List<String> arrorList = new ArrayList<>();

		// 防呆:如果req沒有輸入任何資料
		if (CollectionUtils.isEmpty(cateList)) {
			return new BookResponse("不得為空");
		}

		// 對分類名稱list做遍歷(例如cate = "小說")
		for (String cate : cateList) {

			// 防呆:如果輸入的分類是" "，則回傳錯誤訊息
			if (cate.isBlank()) {
				return new BookResponse("不得為空白");
			}
			// 對資料庫搜尋
			List<Book> result = bookDao.findByCategoryContaining(cate);

			// 防呆:如果資料庫搜尋不到此筆資料，則將此筆分類名稱加入arrorList
			if (CollectionUtils.isEmpty(result)) {
				arrorList.add(cate);
			}
			// 遍歷取得的資料，並回傳書名、ISBN、作者、價格、庫存量
			for (Book item : result) {
				list.add(new Book(item.getTitle(), item.getIsbn(), item.getWriter(), item.getPrice(),
						item.getInventory()));
			}
		}
		// 如果arrorList中有資料(不是空白)，則回傳哪些分類名稱無法搜尋到資料，反之沒東西，則回傳搜集到資料的list
		return !(CollectionUtils.isEmpty(arrorList)) ? new BookResponse("查無資料", arrorList) : new BookResponse(list);
	}
	// =====================================================================

	// 書籍搜尋
	// 消費者: (透過書名或 ISBN或作者) ，只顯示書名、ISBN、作者、價格
	@Override
	public BookResponse guestSearchBook(BookRequest bookRequest) {
		Set<String> guestBooK = bookRequest.getGuestSearchList();
		List<Book> list = new ArrayList<>();
		List<String> arrorList = new ArrayList<>();
		if (CollectionUtils.isEmpty(guestBooK)) {
			return new BookResponse("不得為空");
		}
		// 對使用者輸入的list做遍歷("書本1","莊作者")
		for (String item : guestBooK) {

			// 防呆:如果輸入的分類是" "，則回傳錯誤訊息
			if (item.isBlank()) {
				return new BookResponse("不得為空白");
			}
			List<Book> result = bookDao.findByTitleContainingOrIsbnOrWriterContaining(item, item, item);
			// 防呆:如果資料庫搜尋不到此筆資料，則將此筆分類名稱加入arrorList
			if (CollectionUtils.isEmpty(result)) {
				arrorList.add(item);
			}
			// 遍歷取得的資料，並回傳書名、ISBN、作者、價格
			for (Book bookInfo : result) {
				list.add(new Book(bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getWriter(), bookInfo.getPrice()));
			}
		}
		// 如果arrorList中有資料(不是空白)，則回傳哪些req無法搜尋到資料，反之沒東西，則回傳搜集到資料的list
		return !(CollectionUtils.isEmpty(arrorList)) ? new BookResponse("查無資料", arrorList) : new BookResponse(list);
	}
	// =====================================================================

	// 書籍商: (透過書名或 ISBN或作者)，顯示書名、ISBN、作者、價格、銷售量、庫存量
	@Override
	public BookResponse sellerSearchBook(BookRequest bookRequest) {
		Set<String> sellerList = bookRequest.getSellerSearchList();
		List<Book> list = new ArrayList<>();
		List<String> arrorList = new ArrayList<>();
		if (CollectionUtils.isEmpty(sellerList)) {
			return new BookResponse("不得為空");
		}
		for (String item : sellerList) {

			// 防呆:如果輸入的分類是" "，則回傳錯誤訊息
			if (item.isBlank()) {
				return new BookResponse("不得為空白");
			}

			List<Book> result = bookDao.findByTitleContainingOrIsbnOrWriterContaining(item, item, item);
			// 防呆:如果資料庫搜尋不到此筆資料，則將此筆分類名稱加入arrorList
			if (CollectionUtils.isEmpty(result)) {
				arrorList.add(item);
			}
			// 遍歷取得的資料，並回傳書名、ISBN、作者、價格、銷售量、庫存量
			for (Book bookInfo : result) {
				list.add(new Book(bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getWriter(), bookInfo.getPrice(),
						bookInfo.getSalesVolume(), bookInfo.getInventory()));
			}
		}
		// 如果arrorList中有資料(不是空白)，則回傳哪些req無法搜尋到資料，反之沒東西，則回傳搜集到資料的list
		return !(CollectionUtils.isEmpty(arrorList)) ? new BookResponse("查無資料", arrorList) : new BookResponse(list);
	}
	// =====================================================================

	// 更新書籍資料(庫存量(進貨): 顯示書名、ISBN、作者、價格、庫存量 / 價格: 顯示書名、ISBN、作者、價格、庫存量)
	public BookResponse updateBook(BookRequest bookRequest) {
		String isbn = bookRequest.getIsbn();

		// 使用者輸入要更改的是庫存(i)，還是價格(p)
		String update = bookRequest.getUpdate();

		// 使用者輸入進貨量，或是新價格
		Integer updateVolume = bookRequest.getUpdateVolume();

		// 防呆: 確認輸入isbn
		if (!StringUtils.hasText(isbn)) {
			return new BookResponse("isbn不得為空");
		}
		// 防呆: 確認輸入的isbn在資料庫裡有資料
		if (!bookDao.existsById(isbn)) {
			return new BookResponse("isbn不存在");
		}
		// 防呆:數字不得小於0
		if (updateVolume < 0) {
			return new BookResponse("請確認upgate_volume輸入的內容");
		}

		List<Book> bookList = bookDao.findByIsbn(isbn);
		// if資料庫沒有這筆資料，回傳isbn及錯誤訊息
		if (CollectionUtils.isEmpty(bookList)) {
			return new BookResponse(isbn, "找不到此筆資料，請確認資訊");
		}
		Book bookInfo = bookList.get(0);

		// 判斷:如果update輸入i時，庫存更改
		if (update.equals("i")) {
			bookInfo.setInventory(bookInfo.getInventory() + updateVolume);
			bookDao.save(bookInfo);
			return new BookResponse(bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getWriter(), bookInfo.getPrice(),
					bookInfo.getInventory(), "庫存更改成功，請確認");

			// 判斷:如果update輸入p時，價格更改
		} else if (update.equals("p")) {
			bookInfo.setPrice(updateVolume);
			bookDao.save(bookInfo);
			return new BookResponse(bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getWriter(), bookInfo.getPrice(),
					bookInfo.getInventory(), "價格更改成功，請確認");
		}
		// update輸入i或是p以外的字時，回傳錯誤訊息
		return new BookResponse("請確認輸入i_for_inventory_p_for_price");
	}
	// =====================================================================

	// =====================================================================
	// 書籍銷售
	// 消費者購買(可買多本，但至多3本，取前3): 只顯示書名、ISBN、作者、價格、購買數量，購買總價格
	// 銷售量+1，庫存量-1
	public BookResponse buyBook(BookRequest bookRequest) {
		Map<String, Integer> bookMap = bookRequest.getBuyBook();
		// 放查無書籍的isbn
		List<String> errorList = new ArrayList<>();
		// 放成功購買的書籍
		List<BookResponse> resultBook = new ArrayList<>();
		int totalPrice = 0; // 總金額
		int price = 0; // 價格
		if (CollectionUtils.isEmpty(bookMap)) {
			return new BookResponse("不得為空");
		}
		//一筆一筆
		for (Entry<String, Integer> item : bookMap.entrySet()) {
			String bookIsbn = item.getKey(); // isbn
			Integer buyVolume = item.getValue();// 購買數量
			if (!StringUtils.hasText(bookIsbn) || buyVolume <= 0) {
				return new BookResponse("輸入錯誤");
			}
			Optional<Book> str = bookDao.findById(bookIsbn);
			// 查無資料時，將isbn加入清單
			if (!str.isPresent()) {
				errorList.add(bookIsbn);
				continue;
			}
			// 將書籍資料get出來
			Book bookInfo = str.get();
			// 庫存量<購買數量時，要回應庫存量和訊息
			if (bookInfo.getInventory() < buyVolume) {
				return new BookResponse(bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getInventory(), "庫存不足");
			}
			// 庫存足夠時，庫存=庫存減購買數，銷售量=銷售+購買數
			bookInfo.setInventory(bookInfo.getInventory() - buyVolume);
			bookInfo.setSalesVolume(bookInfo.getSalesVolume() + buyVolume);
			bookDao.save(bookInfo); // 更新新資訊
			// 價格=單本售價*購買數
			price = bookInfo.getPrice() * buyVolume;
			// 總金額 = 總金額+價格
			totalPrice += price;

			// 成功購買的書單
			resultBook.add(new BookResponse(buyVolume, bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getWriter(),
					bookInfo.getPrice()));
		}
		// 如果有查不到的書籍，回傳錯誤訊息
		if (!CollectionUtils.isEmpty(errorList)) {
			return new BookResponse("查無資料", errorList);
		}
		return new BookResponse(totalPrice, resultBook);
	}

	// =====================================================================

	// 暢銷書排行榜(依照銷售量前5，排序) ，只顯示書名、ISBN、作者、價格
	@Override
	public BookResponse getBestByTop5OrderBySalesVolumeDesc(BookRequest bookRequest) {
		List<Book> book = bookDao.findTop5ByOrderBySalesVolumeDesc();
		List<Book> list = new ArrayList<>();
		for (Book item : book) {
			list.add(new Book(item.getTitle(), item.getIsbn(), item.getWriter(), item.getPrice()));
		}
		return new BookResponse(list);
	}
	// =====================================================================

}
