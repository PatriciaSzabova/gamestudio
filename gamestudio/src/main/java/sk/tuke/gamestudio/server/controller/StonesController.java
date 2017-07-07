package sk.tuke.gamestudio.server.controller;

import java.util.Formatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import sk.tuke.gamestudio.game.kamene.core.Field;
import sk.tuke.gamestudio.game.kamene.core.InvalidMoveException;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class StonesController {

	private Field field = new Field(4, 4);

	@RequestMapping("/stones")
	public String Stones(@RequestParam(name = "row", required = false) String row,
			@RequestParam(name = "column", required = false) String column, Model model) {
		try {
			int rowInt = Integer.parseInt(row);
			int colInt = Integer.parseInt(column);
			field.moveDefaultTile(rowInt, colInt);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		} catch (InvalidMoveException e) {
			// e.printStackTrace();
		}
		model.addAttribute("stonesController", this);
		return "stones";
	}

	public String renderField() {
		Formatter formatter = new Formatter();
		formatter.format("<table>");
		for (int row = 0; row < field.getRowCount(); row++) {
			formatter.format("<tr>");
			for (int col = 0; col < field.getColumnCount(); col++) {
				formatter.format("<td>");
				formatter.format("<a href='?row=%d&column=%d'>", row, col);
				formatter.format("%3s", field.getTile(row, col).toString());
				formatter.format("</a>");
				formatter.format("</td>");
			}
			formatter.format("\n", null);
			formatter.format("<tr>");
		}
		formatter.format("</table>");
		return formatter.toString();
	}

}
