package com.example.book_practice.service.ifs;

import com.example.book_practice.vo.BookRequest;
import com.example.book_practice.vo.BookResponse;

public interface BookService {

	//�ѦW�BISBN�B�@�̡B����B�w�s�ƶq�B�P��q�B����(�@�خ��y�i��|���h�ؤ���)���s�W�B�ק�\��
		public BookResponse addBook(BookRequest bookRequest);
		//=====================================================================
		
		

		//���y�����j�M(�@�Φh)�A�u��ܮѦW�BISBN�B�@�̡B����B�w�s�q
		//���ŦX�䤤�@�ӷj�M����Y�i
		public BookResponse findByCategoryContaining(BookRequest bookRequest);
		//=====================================================================
		
		

		//���y�j�M
		//���O��: (�z�L�ѦW�� ISBN�Χ@��) �A�u��ܮѦW�BISBN�B�@�̡B����
		public BookResponse guestSearchBook(BookRequest bookRequest);
		//���y��: (�z�L�ѦW�� ISBN�Χ@��)�A��ܮѦW�BISBN�B�@�̡B����B�P��q�B�w�s�q
		public BookResponse sellerSearchBook(BookRequest bookRequest);
		//=====================================================================
		
		

		//��s���y���
		//�w�s�q(�i�f)�A��ܮѦW�BISBN�B�@�̡B����B�w�s�q
		//����A��ܮѦW�BISBN�B�@�̡B����B�w�s�q
		public BookResponse updateBook(BookRequest bookRequest);
		//=====================================================================
		
		

		//���y�P��
		//���O���ʶR(�i�R�h���A���ܦh3���A���e3): �u��ܮѦW�BISBN�B�@�̡B����B�ʶR�ƶq�A�ʶR�`����
		//�P��q+1�A�w�s�q-1
		public BookResponse buyBook(BookRequest bookRequest);
		//=====================================================================
		

		//�Z�P�ѱƦ�](�̷ӾP��q�e5�A�Ƨ�) �A�u��ܮѦW�BISBN�B�@�̡B����
		public BookResponse getBestByTop5OrderBySalesVolumeDesc(BookRequest bookRequest);
		//=====================================================================
}
