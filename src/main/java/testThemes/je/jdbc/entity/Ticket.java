package testThemes.je.jdbc.entity;

import java.math.BigDecimal;

public class Ticket {
	private Long id;
	private String passportNo;
	private String passangerName;
	private Long flightId;
	private String seatNo;
	private BigDecimal cost;
	
	
	public Ticket(Long id, String passportNo, String passangerName, Long flightId, String seatNo, BigDecimal cost) {
		this.id = id;
		this.passportNo = passportNo;
		this.passangerName = passangerName;
		this.flightId = flightId;
		this.seatNo = seatNo;
		this.cost = cost;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPassportNo() {
		return passportNo;
	}


	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}


	public String getPassangerName() {
		return passangerName;
	}


	public void setPassangerName(String passangerName) {
		this.passangerName = passangerName;
	}


	public Long getFlightId() {
		return flightId;
	}


	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}


	public String getSeatNo() {
		return seatNo;
	}


	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}


	public BigDecimal getCost() {
		return cost;
	}


	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}


	@Override
	public String toString() {
		return "Ticket [id=" + id + ", passportNo=" + passportNo + ", passangerName=" + passangerName + ", flightId="
				+ flightId + ", seatNo=" + seatNo + ", cost=" + cost + "]";
	}

	
	
}
