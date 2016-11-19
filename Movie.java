//Movie.java

public class Movie implements Comparable<Movie>{
    private String name;
    private String time;
    private String genre;
    private int priority;

    public Movie(String movieName, String movieTime, String movieGenre, int moviePriority){
        name= movieName;
        genre= movieGenre;
        time= movieTime;
        priority= moviePriority;
    }

    public String getName(){
        return name;
    }

    public String getGenre(){
        return genre;
    }

    public int getTime(){
        return Integer.parseInt(time);
    }

    public int getPriority(){
        return priority;
    }

    public String getMovie(){
        String tempName= name.replaceAll("\\s+","._.");
        String result= tempName + " "+ time + " " + genre;
        result= result + " " + getPriority();
        return result;
    }

    @Override
    public String toString(){
        //clean up the name
        name= name.replaceAll("\\b._.\\b"," ");

        //to convert time to a String
        String timeString="";
        int hours=0, minutes=0;
        minutes= Integer.parseInt(time);
        while(minutes>60){
            minutes-= 60;
            hours++;
        }
        timeString= hours+"h "+minutes+"min";

        String result= name + " (" + timeString + ") " + genre;
        return result.substring(0, result.length()-1);
    }

    public int compareTo(Movie otherMovie){
        if(priority==otherMovie.priority){
            return 0;
        }
        else if(priority>otherMovie.priority){
            return 1;
        }
        else{
            return -1;
        }
    }
}
