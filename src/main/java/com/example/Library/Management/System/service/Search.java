package com.example.Library.Management.System.service;

import com.example.Library.Management.System.models.Book;

import java.util.Date;
import java.util.List;

public interface Search {
    public List<Book> searchByTitle(String title);
    public List<Book> searchByAuthor(String author);
    public List<Book> searchBySubject(String subject);
    public List<Book> searchByPubDate(Date publishDate);
}
