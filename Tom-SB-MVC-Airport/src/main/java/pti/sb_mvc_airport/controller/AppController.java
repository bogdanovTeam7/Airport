package pti.sb_mvc_airport.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pti.sb_mvc_airport.db.Database;
import pti.sb_mvc_airport.model.Flight;

@Controller
public class AppController {
	@GetMapping("/")
	public String home() {
		return "home.html";
	}

	@GetMapping("/allFlights")
	public String allFlights(Model model) {
		Database db = new Database();
		List<Flight> flights = db.getAllFlights();
		db.close();
		model.addAttribute("flights", flights);
		
		return "allFlights.html";
	}
}