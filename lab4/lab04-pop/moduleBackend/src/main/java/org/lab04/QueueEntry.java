package org.lab04;

public class QueueEntry {
    private String provider;
    private String address;
    private String locality;
    private String phone;
    private String date;

    public QueueEntry(String provider, String address, String locality, String phone, String date) {
        this.provider = provider;
        this.address = address;
        this.locality = locality;
        this.phone = phone;
        this.date = date;
    }

    public String getProvider() {
        return provider;
    }
    public String getAddress() {
        return address;
    }

        public String getLocality() {
        return locality;
    }
    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }


    @Override
    public String toString() {
        return provider + " " + address + " " + locality + " " + phone + " " + date;
    }
}
