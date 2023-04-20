package com.example.book_practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book_practice.entity.Book;



@Repository
public interface BookDao extends JpaRepository<Book, String> {

	// ���y�����j�M(�@�Φh)�A�u��ܮѦW�BISBN�B�@�̡B����B�w�s�q
		// ���ŦX�䤤�@�ӷj�M����Y�i
		//�u�n�^�Ǫ���Ƥ��u�@���N��List
		List<Book> findByCategoryContaining(String category);
		// =====================================================================

		
		// ���y�j�M
		// ���O��: (�z�L�ѦW�� ISBN�Χ@��) �A�u��ܮѦW�BISBN�B�@�̡B����
		// ���y��: (�z�L�ѦW�� ISBN�Χ@��)�A��ܮѦW�BISBN�B�@�̡B����B�P��q�B�w�s�q
		List<Book> findByTitleContainingOrIsbnOrWriterContaining(String title,String isbn, String writer);
		// =====================================================================
		
		
		// �Z�P�ѱƦ�](�̷ӾP��q�e5�A�Ƨ�) �A�u��ܮѦW�BISBN�B�@�̡B����
		List<Book> findTop5ByOrderBySalesVolumeDesc();
		// =====================================================================
		

		// ��s���y���
		// �w�s�q(�i�f)  ��ܮѦW�BISBN�B�@�̡B����B�w�s�q
		// ����         ��ܮѦW�BISBN�B�@�̡B����B�w�s
		List<Book> findByIsbn(String isbn);
		// =====================================================================
}
