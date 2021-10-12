package com.formation.m2i.hotels.model.entities;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table (name = "reservation")
public class ReservationsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client", referencedColumnName = "id", foreignKey = @ForeignKey(name = "CLIENT_ID_FK"), nullable = false)
    private ClientsEntity client;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="hotel", referencedColumnName = "id", foreignKey = @ForeignKey(name = "HOTEL_ID_FK"), nullable = false)
    private HotelsEntity hotel;

    @NotNull
    @Column(nullable = false)
    private Date startDate;

    @NotNull
    @Column(nullable = false)
    private Date endDate;

    @NotNull
    @Column(nullable = false)
    private int roomNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientsEntity getClient() {
        return client;
    }

    public void setClient(ClientsEntity client) {
        this.client = client;
    }

    public HotelsEntity getHotel() {
        return hotel;
    }

    public void setHotel(HotelsEntity hotel) {
        this.hotel = hotel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationsEntity that = (ReservationsEntity) o;
        return roomNumber == that.roomNumber && id.equals(that.id) && client.equals(that.client) && hotel.equals(that.hotel) && startDate.equals(that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, hotel, startDate, endDate, roomNumber);
    }
}
