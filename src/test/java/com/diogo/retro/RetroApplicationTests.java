package com.diogo.retro;

import com.diogo.retro.model.Comment;
import com.diogo.retro.model.CommentRepository;
import com.diogo.retro.model.CommentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RetroApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	public void saveComments_HappyPath_ShouldReturnStatus302() throws Exception {
		// When
		ResultActions resultActions = mockMvc.perform(post("/comment").with(csrf()).with(user("shazin").roles("USER")).requestAttr("plusComment", "Test Plus"));

		// Then
		resultActions
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"))
				.andExpect(redirectedUrl("/"));
	}

	@Test
	public void getComments_HappyPath_ShouldReturnStatus200() throws Exception {
		// Given
		Comment comment = new Comment();
		comment.setComment("Test Comment");
		comment.setType(CommentType.PLUS);
		comment.setCreatedBy("shazin");
		commentRepository.save(comment);

		// When
		ResultActions resultActions = mockMvc.perform(get("/").with(user("shazin").roles("USER")));

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(view().name("comment"))
				.andExpect(model().attribute("plusComments", hasSize(1)))
				.andExpect(model().attribute("plusComments", hasItem(
						allOf(
								hasProperty("createdBy", is("shazin")),
								hasProperty("comment", is("Test Comment"))
						)
				)));

	}

}
