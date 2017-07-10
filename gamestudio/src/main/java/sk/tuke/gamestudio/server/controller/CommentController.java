package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import sk.tuke.gamestudio.server.service.CommentService;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class CommentController {

	@Autowired
	private CommentService commentService;
	private String game;

}
