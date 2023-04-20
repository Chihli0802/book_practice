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

	// �ѦW�BISBN�B�@�̡B����B�w�s�ƶq�B�P��q�B����(�@�خ��y�i��|���h�ؤ���)���s�W�B�ק�\��
	@Override
	public BookResponse addBook(BookRequest bookRequest) {
		List<Book> bookInfoList = bookRequest.getBookList();
		List<Book> updateBook = new ArrayList<>();
		List<Book> newBook = new ArrayList<>();
		if (CollectionUtils.isEmpty(bookInfoList)) {
			return new BookResponse("���o����");
		}
		for (Book item : bookInfoList) {
			//���b: ���o����
			if (!StringUtils.hasText(item.getTitle()) || !StringUtils.hasText(item.getIsbn())
					|| !StringUtils.hasText(item.getWriter()) || !StringUtils.hasText(item.getCategory())) {
				return new BookResponse("��T���o����");
			}
			//���b: �Ʀr���o�p��0
			if (item.getInventory() < 0 || item.getSalesVolume() < 0 || item.getPrice() < 0) {
				return new BookResponse("�ƶq���o�p��0");
			}
			
			//��s��T�A�p�G�O�w�s�b��id�A��ܬO�n��s�ӫ~��T�A�[��updateBook list
			if (bookDao.existsById(item.getIsbn())) {
				updateBook.add(item);
				
			//�p�G�O���s�b��id�A��ܬO�n�s�W�ӫ~��T�A�[��newBook list
			}else {
				newBook.add(item);
			}
			
		}
		
		//�p�G���list�������e���ܡA��save�A�M��response
		if(!CollectionUtils.isEmpty(updateBook)&&!CollectionUtils.isEmpty(newBook)) {
			bookDao.saveAll(updateBook);
			bookDao.saveAll(newBook);
			return new BookResponse(newBook,updateBook);
		}
		//�p�G�u��updateBook�����e(newBook List�O�Ū�)
		if (!CollectionUtils.isEmpty(updateBook)&& CollectionUtils.isEmpty(newBook)) {
			bookDao.saveAll(updateBook);
			return new BookResponse(updateBook, "�ӫ~�w��s");
		}
		//�p�G�u��newBook�����e
		bookDao.saveAll(newBook);
		return new BookResponse(newBook, "�w�s�W�ӫ~");
		
	}
	// =====================================================================
	
	
	
	// ���y�����j�M(�@�Φh)�A�u��ܮѦW�BISBN�B�@�̡B����B�w�s�q�A���ŦX�䤤�@�ӷj�M����Y�i
	@Override
	public BookResponse findByCategoryContaining(BookRequest bookRequest) {
		Set<String> cateList = bookRequest.getCateList();
		List<Book> list = new ArrayList<>();
		List<String> arrorList = new ArrayList<>();

		// ���b:�p�Greq�S����J������
		if (CollectionUtils.isEmpty(cateList)) {
			return new BookResponse("���o����");
		}

		// ������W��list���M��(�Ҧpcate = "�p��")
		for (String cate : cateList) {

			// ���b:�p�G��J�������O" "�A�h�^�ǿ��~�T��
			if (cate.isBlank()) {
				return new BookResponse("���o���ť�");
			}
			// ���Ʈw�j�M
			List<Book> result = bookDao.findByCategoryContaining(cate);

			// ���b:�p�G��Ʈw�j�M���즹����ơA�h�N���������W�٥[�JarrorList
			if (CollectionUtils.isEmpty(result)) {
				arrorList.add(cate);
			}
			// �M�����o����ơA�æ^�ǮѦW�BISBN�B�@�̡B����B�w�s�q
			for (Book item : result) {
				list.add(new Book(item.getTitle(), item.getIsbn(), item.getWriter(), item.getPrice(),
						item.getInventory()));
			}
		}
		// �p�GarrorList�������(���O�ť�)�A�h�^�ǭ��Ǥ����W�ٵL�k�j�M���ơA�Ϥ��S�F��A�h�^�Ƿj�����ƪ�list
		return !(CollectionUtils.isEmpty(arrorList)) ? new BookResponse("�d�L���", arrorList) : new BookResponse(list);
	}
	// =====================================================================

	// ���y�j�M
	// ���O��: (�z�L�ѦW�� ISBN�Χ@��) �A�u��ܮѦW�BISBN�B�@�̡B����
	@Override
	public BookResponse guestSearchBook(BookRequest bookRequest) {
		Set<String> guestBooK = bookRequest.getGuestSearchList();
		List<Book> list = new ArrayList<>();
		List<String> arrorList = new ArrayList<>();
		if (CollectionUtils.isEmpty(guestBooK)) {
			return new BookResponse("���o����");
		}
		// ��ϥΪ̿�J��list���M��("�ѥ�1","���@��")
		for (String item : guestBooK) {

			// ���b:�p�G��J�������O" "�A�h�^�ǿ��~�T��
			if (item.isBlank()) {
				return new BookResponse("���o���ť�");
			}
			List<Book> result = bookDao.findByTitleContainingOrIsbnOrWriterContaining(item, item, item);
			// ���b:�p�G��Ʈw�j�M���즹����ơA�h�N���������W�٥[�JarrorList
			if (CollectionUtils.isEmpty(result)) {
				arrorList.add(item);
			}
			// �M�����o����ơA�æ^�ǮѦW�BISBN�B�@�̡B����
			for (Book bookInfo : result) {
				list.add(new Book(bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getWriter(), bookInfo.getPrice()));
			}
		}
		// �p�GarrorList�������(���O�ť�)�A�h�^�ǭ���req�L�k�j�M���ơA�Ϥ��S�F��A�h�^�Ƿj�����ƪ�list
		return !(CollectionUtils.isEmpty(arrorList)) ? new BookResponse("�d�L���", arrorList) : new BookResponse(list);
	}
	// =====================================================================

	// ���y��: (�z�L�ѦW�� ISBN�Χ@��)�A��ܮѦW�BISBN�B�@�̡B����B�P��q�B�w�s�q
	@Override
	public BookResponse sellerSearchBook(BookRequest bookRequest) {
		Set<String> sellerList = bookRequest.getSellerSearchList();
		List<Book> list = new ArrayList<>();
		List<String> arrorList = new ArrayList<>();
		if (CollectionUtils.isEmpty(sellerList)) {
			return new BookResponse("���o����");
		}
		for (String item : sellerList) {

			// ���b:�p�G��J�������O" "�A�h�^�ǿ��~�T��
			if (item.isBlank()) {
				return new BookResponse("���o���ť�");
			}

			List<Book> result = bookDao.findByTitleContainingOrIsbnOrWriterContaining(item, item, item);
			// ���b:�p�G��Ʈw�j�M���즹����ơA�h�N���������W�٥[�JarrorList
			if (CollectionUtils.isEmpty(result)) {
				arrorList.add(item);
			}
			// �M�����o����ơA�æ^�ǮѦW�BISBN�B�@�̡B����B�P��q�B�w�s�q
			for (Book bookInfo : result) {
				list.add(new Book(bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getWriter(), bookInfo.getPrice(),
						bookInfo.getSalesVolume(), bookInfo.getInventory()));
			}
		}
		// �p�GarrorList�������(���O�ť�)�A�h�^�ǭ���req�L�k�j�M���ơA�Ϥ��S�F��A�h�^�Ƿj�����ƪ�list
		return !(CollectionUtils.isEmpty(arrorList)) ? new BookResponse("�d�L���", arrorList) : new BookResponse(list);
	}
	// =====================================================================

	// ��s���y���(�w�s�q(�i�f): ��ܮѦW�BISBN�B�@�̡B����B�w�s�q / ����: ��ܮѦW�BISBN�B�@�̡B����B�w�s�q)
	public BookResponse updateBook(BookRequest bookRequest) {
		String isbn = bookRequest.getIsbn();

		// �ϥΪ̿�J�n��諸�O�w�s(i)�A�٬O����(p)
		String update = bookRequest.getUpdate();

		// �ϥΪ̿�J�i�f�q�A�άO�s����
		Integer updateVolume = bookRequest.getUpdateVolume();

		// ���b: �T�{��Jisbn
		if (!StringUtils.hasText(isbn)) {
			return new BookResponse("isbn���o����");
		}
		// ���b: �T�{��J��isbn�b��Ʈw�̦����
		if (!bookDao.existsById(isbn)) {
			return new BookResponse("isbn���s�b");
		}
		// ���b:�Ʀr���o�p��0
		if (updateVolume < 0) {
			return new BookResponse("�нT�{upgate_volume��J�����e");
		}

		List<Book> bookList = bookDao.findByIsbn(isbn);
		// if��Ʈw�S���o����ơA�^��isbn�ο��~�T��
		if (CollectionUtils.isEmpty(bookList)) {
			return new BookResponse(isbn, "�䤣�즹����ơA�нT�{��T");
		}
		Book bookInfo = bookList.get(0);

		// �P�_:�p�Gupdate��Ji�ɡA�w�s���
		if (update.equals("i")) {
			bookInfo.setInventory(bookInfo.getInventory() + updateVolume);
			bookDao.save(bookInfo);
			return new BookResponse(bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getWriter(), bookInfo.getPrice(),
					bookInfo.getInventory(), "�w�s��令�\�A�нT�{");

			// �P�_:�p�Gupdate��Jp�ɡA������
		} else if (update.equals("p")) {
			bookInfo.setPrice(updateVolume);
			bookDao.save(bookInfo);
			return new BookResponse(bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getWriter(), bookInfo.getPrice(),
					bookInfo.getInventory(), "�����令�\�A�нT�{");
		}
		// update��Ji�άOp�H�~���r�ɡA�^�ǿ��~�T��
		return new BookResponse("�нT�{��Ji_for_inventory_p_for_price");
	}
	// =====================================================================

	// =====================================================================
	// ���y�P��
	// ���O���ʶR(�i�R�h���A���ܦh3���A���e3): �u��ܮѦW�BISBN�B�@�̡B����B�ʶR�ƶq�A�ʶR�`����
	// �P��q+1�A�w�s�q-1
	public BookResponse buyBook(BookRequest bookRequest) {
		Map<String, Integer> bookMap = bookRequest.getBuyBook();
		// ��d�L���y��isbn
		List<String> errorList = new ArrayList<>();
		// �񦨥\�ʶR�����y
		List<BookResponse> resultBook = new ArrayList<>();
		int totalPrice = 0; // �`���B
		int price = 0; // ����
		if (CollectionUtils.isEmpty(bookMap)) {
			return new BookResponse("���o����");
		}
		//�@���@��
		for (Entry<String, Integer> item : bookMap.entrySet()) {
			String bookIsbn = item.getKey(); // isbn
			Integer buyVolume = item.getValue();// �ʶR�ƶq
			if (!StringUtils.hasText(bookIsbn) || buyVolume <= 0) {
				return new BookResponse("��J���~");
			}
			Optional<Book> str = bookDao.findById(bookIsbn);
			// �d�L��ƮɡA�Nisbn�[�J�M��
			if (!str.isPresent()) {
				errorList.add(bookIsbn);
				continue;
			}
			// �N���y���get�X��
			Book bookInfo = str.get();
			// �w�s�q<�ʶR�ƶq�ɡA�n�^���w�s�q�M�T��
			if (bookInfo.getInventory() < buyVolume) {
				return new BookResponse(bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getInventory(), "�w�s����");
			}
			// �w�s�����ɡA�w�s=�w�s���ʶR�ơA�P��q=�P��+�ʶR��
			bookInfo.setInventory(bookInfo.getInventory() - buyVolume);
			bookInfo.setSalesVolume(bookInfo.getSalesVolume() + buyVolume);
			bookDao.save(bookInfo); // ��s�s��T
			// ����=�楻���*�ʶR��
			price = bookInfo.getPrice() * buyVolume;
			// �`���B = �`���B+����
			totalPrice += price;

			// ���\�ʶR���ѳ�
			resultBook.add(new BookResponse(buyVolume, bookInfo.getTitle(), bookInfo.getIsbn(), bookInfo.getWriter(),
					bookInfo.getPrice()));
		}
		// �p�G���d���쪺���y�A�^�ǿ��~�T��
		if (!CollectionUtils.isEmpty(errorList)) {
			return new BookResponse("�d�L���", errorList);
		}
		return new BookResponse(totalPrice, resultBook);
	}

	// =====================================================================

	// �Z�P�ѱƦ�](�̷ӾP��q�e5�A�Ƨ�) �A�u��ܮѦW�BISBN�B�@�̡B����
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
