package pti.sb_mvc_airport.db;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import pti.sb_mvc_airport.model.Flight;

public class Database {
	private SessionFactory factory;

	public Database() {
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}

	public void close() {
		factory.close();
	}

	public List<Flight> getAllFlights() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("SELECT f FROM Flight f ORDER BY f.dateFrom", Flight.class);
		List<Flight> flights = query.getResultList();
		transaction.commit();
		session.close();
		return flights;
	}
}
