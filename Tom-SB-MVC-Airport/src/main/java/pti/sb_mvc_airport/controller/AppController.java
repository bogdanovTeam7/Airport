package pti.sb_mvc_airport.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

	@GetMapping("/captains")
	public String captains(Model model) {
		HashMap<String, Long> captains = new HashMap<>();
		Database db = new Database();
		List<Flight> flights = db.getAllFlights();
		for (Flight flight : flights) {
			String captain = flight.getCaptain();
			long flightDuration = flight.getFlightTimeInMin();
			if (captains.containsKey(captain)) {
				captains.put(captain, captains.get(captain) + flightDuration);
			} else {
				captains.put(captain, flightDuration);
			}
		}

		db.close();
		model.addAttribute("captains", captains);
		return "captains.html";
	}

	@GetMapping("/captainsRoutes")
	public String captainsRoutes(Model model) {
		HashMap<String, ArrayList<String>> captainsRoutes = new HashMap<>();
		Database db = new Database();
		List<Flight> flights = db.getAllFlights();
		db.close();

		for (Flight flight : flights) {
			String captain = flight.getCaptain();
			String cityFrom = flight.getCityFrom();
			String cityTo = flight.getCityTo();
			ArrayList<String> cities = null;
			if (!captainsRoutes.containsKey(captain)) {
				cities = new ArrayList<>();
				cities.add(cityFrom);
				cities.add(cityTo);
			} else {
				cities = captainsRoutes.get(captain);
				if (!cities.get(0).equals(cities.get(cities.size() - 1))) {
					cities.add(cityTo);
				}
			}
			captainsRoutes.put(captain, cities);
		}

		String captain = null;

		Set<Entry<String, ArrayList<String>>> entrySet = captainsRoutes.entrySet();
		for (Entry<String, ArrayList<String>> entry : entrySet) {
			ArrayList<String> cities = entry.getValue();
			if (cities.size() > 3) {
				String firstCity = cities.get(0);
				String lastCity = cities.get(cities.size() - 1);
				if (firstCity.equals(lastCity)) {
					captain = entry.getKey();
					break;
				}
			}
		}

		model.addAttribute("captain", captain);
		model.addAttribute("captainsRoutes", captainsRoutes);
		return "captainsRoutes.html";
	}

	@GetMapping("/route/{from}/{to}")
	public String routeFromTo(Model model, @PathVariable String from, @PathVariable String to) {
		Database db = new Database();
		List<Flight> flights = db.getAllFlights();
		db.close();

		String cityFrom = from;
		ArrayList<Flight> route = new ArrayList<>();
		for (Flight flight : flights) {
			if (flight.getCityFrom().equals(cityFrom)) {
				route.add(flight);
				cityFrom = flight.getCityTo();
				if (cityFrom.equals(to)) {
					break;
				}
			}
		}

		LinkedHashMap<Flight, String> routeWithWaitingTime = new LinkedHashMap<>();
		for (int i = 0; i < route.size(); i++) {
			Flight flight = route.get(i);
			String waitingTimeAsString = null;
			if (i < route.size() - 1) {
				waitingTimeAsString = flight.getWaitingTimeAsString(route.get(i + 1));
			}

			routeWithWaitingTime.put(flight, waitingTimeAsString);
		}

		model.addAttribute("route", route);
		model.addAttribute("routeWithWaitingTime", routeWithWaitingTime);
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		return "route.html";
	}
}