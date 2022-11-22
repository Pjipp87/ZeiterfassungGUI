package com.example.zeiterfassunggui.classes;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Worker {
    private final String firstName;
    private String lastName;
    private int dbid;
    private long workHour;
    private String Anfangszeit;
    private String EndeZeit;
    public static ArrayList<Worker> workerList = new ArrayList<>();
    private long beginDay;
    private long endDay;
    public static ArrayList<Worker> acticeWorker = new ArrayList<>();

    public static ArrayList<Integer> workerListFromDB = new ArrayList<>();
    public static ArrayList<Worker> getWorkerListFromDB = new ArrayList<>();

    private int arbeitet;



    public Worker(String firstName, String lastName, int id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.arbeitet = id;
        this.workHour = 0;
        workerList.add(this);

    }

    public int getArbeitet() {
        return arbeitet;
    }

    public void setArbeitet(int arbeitet) {
        this.arbeitet = arbeitet;
    }

    public Worker(String firstName, String lastName, int id, int dbid){
        this.firstName = firstName;
        this.lastName = lastName;
        this.arbeitet = id;
        this.workHour = 0;
        this.dbid = dbid;
        workerList.add(this);

    }

    public String setBeginDay() {
        long time = new Date().getTime();
        acticeWorker.add(this);
        this.beginDay = time;
        this.Anfangszeit = "Arbeit beginnt: "+ new Date(time).getHours()+ ":"+ new Date(time).getMinutes();
        return "Arbeit beginnt: "+ new Date(time).getHours()+ ":"+ new Date(time).getMinutes();
    }

    public long getBeginDay() {
        return beginDay;
    }

    public String setEndDay() {
        long time = new Date().getTime();
        acticeWorker.remove(this);
        this.endDay = time;
        this.workHour = getEndDay()-getBeginDay();

        return "Arbeit endet: "+ new Date(time).getHours()+ ":"+ new Date(time).getMinutes();
    }

    public long getEndDay() {
        return endDay;
    }

    public String getAnfangszeit() {
        return this.Anfangszeit;
    }


    public String getHours(){
        Date time = new Date(workHour);
        return time.getHours()-1+":"+time.getMinutes()+":"+time.getSeconds();
    }



    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getWorkHour() {
        return workHour;
    }

    public int getDbid() {
        return dbid;
    }
}
