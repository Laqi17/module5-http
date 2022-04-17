package com.model;

public class Order {
    private Integer id;
    private Integer petId;
    private Integer quantity;
    private String shipDate;
    private Status status;
    private boolean complete;

    private enum Status {
        PLACED("placed"),
        APPROVED("approved"),
        DELIVERED("delivered");

        private String status;

        public String getStatus() {
            return status;
        }

        Status(String status) {
            this.status = status;
        }
    }

    public Order() {
    }

    public Order(Integer id, Integer petId, Integer quantity, String shipDate, Status status, boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
