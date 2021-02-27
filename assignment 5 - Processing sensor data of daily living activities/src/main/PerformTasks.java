package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PerformTasks {

    private final static DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void processFile(List<MonitoredData> list) throws IOException {
            Stream<String> stream = Files.lines(Paths.get("Activities.txt"));
            stream.forEach(line -> processLine(line, list));
    }

    public void processLine(String line, List<MonitoredData> list) {
        String splits[] = line.split("\t\t");

            LocalDateTime start_time = LocalDateTime.parse(splits[0], parser);
            LocalDateTime end_time = LocalDateTime.parse(splits[1], parser);
            String activity_label = splits[2].trim();

            MonitoredData activity = new MonitoredData(start_time, end_time, activity_label);
            list.add(activity);
    }

    public void createFile(String name_of_file){
        try {
            File newFile = new File(name_of_file);
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void writeToFile(String file,String toBeWritten){
        try {
                BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
                output.append(toBeWritten);
                output.append('\n');
                output.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void task1() throws IOException {
        List<MonitoredData> list = new ArrayList<MonitoredData>();

        processFile(list);
        createFile("Task_1.txt");

        list.stream().forEach(activity ->writeToFile("Task_1.txt",activity.toString()));
    }

    private int countDistinctDays (List<MonitoredData> list) {
        return list.stream().map(data -> data.start_time.getYear() + "-" + data.start_time.getMonth() + "-" + data.start_time.getDayOfMonth()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).keySet().size();
    }

    public void task2() throws IOException {
        List<MonitoredData> list = new ArrayList<MonitoredData>();

        processFile(list);
        createFile("Task_2.txt");

        int nr=countDistinctDays(list);

        String s="Total number of counted days: " + nr;

        //System.out.println("For task 2:  " + s);

        writeToFile("Task_2.txt",s);
    }

    public Map<String, Long> countActivityperPeriod (List<MonitoredData> list) {
        return list.stream().map(data -> data.activity_label).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
    }

    public void task3() throws IOException {
        List<MonitoredData> list = new ArrayList<MonitoredData>();

        processFile(list);
        createFile("Task_3.txt");

        for (String activity_label : countActivityperPeriod(list).keySet()) {
            writeToFile("Task_3.txt", activity_label + " has appeared a total of " + countActivityperPeriod(list).get(activity_label) + " times");
        }
    }

    public Map<Integer,Map<String, Long>> countActivityperDay (List<MonitoredData> list) {

        List<String> DifferentDays = new ArrayList<String>();

        for(MonitoredData m : list){
            if(!DifferentDays.contains(m.start_time.getYear() + "-" + m.start_time.getMonth() + "-" + m.start_time.getDayOfMonth()))
                DifferentDays.add(m.start_time.getYear() + "-" + m.start_time.getMonth() + "-" + m.start_time.getDayOfMonth());
        }

        Map<Integer,Map<String, Long>> returning = new HashMap<Integer,Map<String, Long>>();
        for(int i=1; i<=countDistinctDays(list); i++){
            int k=i-1;
            returning.put(i,list.stream().filter(m -> m.returnStartDate().equals(DifferentDays.get(k))).map(data -> data.start_time.getYear() + "-" + data.start_time.getMonth() + "-" + data.start_time.getDayOfMonth() + " - " + data.activity_label).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
        }

        return returning;
    }

    public void task4() throws IOException {
        List<MonitoredData> list = new ArrayList<MonitoredData>();

        processFile(list);
        createFile("Task_4.txt");

        for (Integer dayNumber : countActivityperDay(list).keySet()) {
            writeToFile("Task_4.txt", "For day " + dayNumber + " the activities and their count was: " + countActivityperDay(list).get(dayNumber));
        }
    }

    public Long durationInMinutes(LocalDateTime start, LocalDateTime end) {
        return start.until(end, ChronoUnit.MINUTES);
    }

    public List<ActivityDuration> activityDuration(List<MonitoredData> list) {
        return list.stream().map(data -> new ActivityDuration(data.activity_label, durationInMinutes(data.start_time, data.end_time))).collect(Collectors.toList());
    }

    public Map<String, Long> totalDuration(List<MonitoredData> list) {
        return activityDuration(list).stream().collect(Collectors.groupingBy(data -> data.activity_label, Collectors.summingLong(data -> data.duration)));
    }

    public void task5() throws IOException {
        List<MonitoredData> list = new ArrayList<MonitoredData>();

        processFile(list);
        createFile("Task_5.txt");

        for (String activity : totalDuration(list).keySet()) {
            writeToFile("Task_5.txt", "For the activity " + activity + " the total duration was " + totalDuration(list).get(activity) + " minutes");
        }
    }

    public List<String> ActivitiesLessThan5 (List<MonitoredData> list) {

        Map<String,Long> totalduration = totalDuration(list);
        List<String> shortActivities = new ArrayList<String>();

        for(String activity: totalduration.keySet()){
            if(0.9 * totalduration.get(activity) <= 5) {
                shortActivities.add(activity);
            }
        }
        return shortActivities;
    }

    public void task6() throws IOException {
        List<MonitoredData> list = new ArrayList<MonitoredData>();

        processFile(list);
        createFile("Task_6.txt");

        for (String activity : ActivitiesLessThan5(list)) {
            writeToFile("Task_6.txt", "For the activity " + activity + " the total duration was " + totalDuration(list).get(activity) + " minutes, which means 90% of the monitoring records with duration less than 5 minutes");
        }
    }
}
