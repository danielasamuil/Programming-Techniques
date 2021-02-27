package main;

import java.time.LocalDateTime;

public class MonitoredData {
    public LocalDateTime start_time;
    public LocalDateTime end_time;
    public String activity_label;

    public MonitoredData(LocalDateTime startTime, LocalDateTime endTime, String activityLabel) {
        this.start_time = startTime;
        this.end_time = endTime;
        this.activity_label = activityLabel;
    }

    public String returnStartDate(){
        return this.start_time.getYear() + "-" + this.start_time.getMonth() + "-" + this.start_time.getDayOfMonth();
    }

    public String toString() {
        return "<" + "starting time=" + start_time + ", ending time=" + end_time + ", activity name=" + activity_label + "> ";
    }

}
