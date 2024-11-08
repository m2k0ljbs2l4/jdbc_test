package testThemes.je.jdbc.entity;

import java.math.BigDecimal;

public class Ticket {
    private Long id;
    private String passangerNo;
    private String passangerName;
    private Flight flight;
    private String seatNo;
    private BigDecimal cost;

    public Ticket(Long id, String passangerNo, String passangerName, Flight flight, String seatNo, BigDecimal cost) {
        this.id = id;
        this.passangerNo = passangerNo;
        this.passangerName = passangerName;
        this.flight = flight;
        this.seatNo = seatNo;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassangerNo() {
        return passangerNo;
    }

    public void setPassangerNo(String passangerNo) {
        this.passangerNo = passangerNo;
    }

    public String getPassangerName() {
        return passangerName;
    }

    public void setPassangerName(String passangerName) {
        this.passangerName = passangerName;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
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
        return "Ticket{" +
               "id=" + id +
               ", passangerNo='" + passangerNo + '\'' +
               ", passangerName='" + passangerName + '\'' +
               ", flightId=" + flight +
               ", seatNo='" + seatNo + '\'' +
               ", cost=" + cost +
               '}';
    }
}
