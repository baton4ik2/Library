package com.WebDemo;

import com.WebDemo.Controller.BooksController;
import com.WebDemo.Repository.BookCoverRepository;
import com.WebDemo.Repository.BookRepository;
import com.WebDemo.model.Book;
import com.WebDemo.services.BookCoverService;
import com.WebDemo.services.BookService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class WebDemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private BookCoverRepository bookCoverRepository;

	@SpyBean
	private BookService bookService;

	@SpyBean
	private BookCoverService bookCoverService;

	@InjectMocks
	private BooksController booksController;

	@Test
	public void saveBookTest() throws Exception {
		final String name = "grtrw";
		final String author = "grgg3";
		final long id = 1;

		JSONObject bookObject = new JSONObject();
		bookObject.put("name", name);
		bookObject.put("author", author);

		Book book = new Book();
		book.setId(id);
		book.setName(name);
		book.setAuthor(author);

		when(bookRepository.save(any(Book.class))).thenReturn(book);
		when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
		//when(bookRepository.findById(eq(1L))).thenReturn(Optional.of(book)); один и тот же функционал

		mockMvc.perform(MockMvcRequestBuilders
						.post("/books")
						.content(bookObject.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value(name))
				.andExpect(jsonPath("$.author").value(author));

		mockMvc.perform(MockMvcRequestBuilders
						.get("/books/" +id)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value(name))
				.andExpect(jsonPath("$.author").value(author));
	}


}
