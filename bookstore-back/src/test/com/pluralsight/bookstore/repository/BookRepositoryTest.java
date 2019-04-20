package com.pluralsight.bookstore.repository;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.model.Language;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {

    @Inject
    private BookRepository bookRepository;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml");
    }

    @Test
    public void create() {

        assertEquals(Long.valueOf(0),bookRepository.countAll());
        assertEquals(0,bookRepository.findAll().size());

        // Create a Book
//        String title, String description, Float unitCost, String isbn, Date publicationDate, Integer nbOfPages, String imageUrl, Language language
         Book book = new Book("isbn", "description", 12F,"isbn", new Date(),123,"http://test", Language.ENGLISH);
         book = bookRepository.create(book);
         Long bookId = book.getId();

         //Check create book
         assertNotNull(bookId);

        //Find a created book
        Book bookFound = bookRepository.find(bookId);

        // Check the found book
        assertEquals("isbn",bookFound.getTitle());

    }
}
