package pti.sb_mvc_airport.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "flights")
public class Flight {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "city_from")
	private String cityFrom;
	@Column(name = "city_to")
	private String cityTo;
	@Column(name = "date_from")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFrom;
	@Column(name = "date_to")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTo;
	@Column(name = "flight_id")
	private String flightId;
	@Column(name = "captain")
	private String captain;

	public Flight() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityFrom() {
		return cityFrom;
	}

	public void setCityFrom(String cityFrom) {
		this.cityFrom = cityFrom;
	}

	public String getCityTo() {
		return cityTo;
	}

	public void setCityTo(String cityTo) {
		this.cityTo = cityTo;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public String getCaptain() {
		return captain;
	}

	public void setCaptain(String captain) {
		this.captain = captain;
	}

	public long getFlightTimeInSec() {
		return (dateTo.getTime() - dateFrom.getTime()) / 1000;
	}

	public long getFlightTimeInMin() {
		return (dateTo.getTime() - dateFrom.getTime()) / (1000 * 60);
	}

	public String getFlightTimeAsString() {
		long intervalInsec = getFlightTimeInSec();

		long hour = intervalInsec / 3600;
		long min = (intervalInsec % 3600) / 60;
		return String.format("%02d:%02d", hour, min);
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return "Flight [id=" + id + ", cityFrom=" + cityFrom + ", cityTo=" + cityTo + ", dateFrom="
				+ dateFormat.format(dateFrom) + ", dateTo=" + dateFormat.format(dateTo) + ", flightId=" + flightId
				+ ", captain=" + captain + ", getFlightTimeAsString()=" + getFlightTimeAsString() + "]";
	}

}
