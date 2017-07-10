package sk.tuke.gamestudio.server.controller;

import java.util.Formatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tuke.gamestudio.game.pexeso.core.Field;
import sk.tuke.gamestudio.game.pexeso.core.Tile;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PexesoController {

	private Field field = new Field(3, 4);

	@RequestMapping("/pexeso")
	public String pexeso(Model model) {
		model.addAttribute("pexesoController", this);
		return "pexeso";
	}

	public String renderField() {
		Formatter formatter = new Formatter();
		formatter.format("<table>");
		for (int row = 0; row < field.getRowCount(); row++) {
			formatter.format("<tr>");
			for (int col = 0; col < field.getColumnCount(); col++) {
				String image = getImageName(field.getTile(row, col));
				formatter.format("<td>");
				formatter.format("<a href='?row=%d&column=%d'>", row, col);
				formatter.format("<img src='/images/pexeso/%s.png'>", image);
				formatter.format("</a>");
				formatter.format("</td>");
			}
			formatter.format("\n", null);
			formatter.format("<tr>");
		}
		formatter.format("</table>");
		return formatter.toString();
	}

	private String getImageName(Tile tile) {
		switch (tile.getState()) {
		case CLOSED:
			return "default";
		case OPEN:
			return Integer.toString(tile.getValue());
		case SOLVED:
			return Integer.toString(tile.getValue());
		default:
			throw new IllegalArgumentException("Unexpected tile state");
		}

	}

}
