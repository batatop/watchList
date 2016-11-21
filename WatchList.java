import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.PriorityQueue;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.PatternSyntaxException;
import java.util.ArrayList;
import java.util.List;


public class WatchList {
    public static void main(String[] args) throws IOException {
        PriorityQueue<Movie> pQueue= new PriorityQueue<Movie>();
        try {
			File file = new File("watchList.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String oneLine;
			while ((oneLine = bufferedReader.readLine()) != null) {
                try {
                    String[] movieLine= oneLine.split("\\s+");
                    pQueue.add(new Movie(movieLine[0], movieLine[1], movieLine[2], Integer.parseInt(movieLine[3])));
                } catch (PatternSyntaxException ex) {
                    System.out.println("Invalid input file.");
                }
			}
			fileReader.close();
		} catch (IOException e) {
            System.out.println("\"watchList.txt\" doesn't exist.");
		}
        //checks if there are any arguments
        if(args.length==2){
            //if the input is an IMDB URL, gets data from the link
            //and adds it to the Priority Queue
            if(args[0].indexOf("imdb.com/title/")!=-1){
                if(checkPriority(args[1])){
                    URL url = new URL(args[0]);
                    URLConnection con = url.openConnection();
                    InputStream is =con.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line = null;

                    String result= "";
                    String nameString= "", timeString= "", genreString= "";
                    int hours=0, minutes=0;
                    int priority= Integer.parseInt(args[1]);
                    String nameCheck1="<h1 itemprop=\"name\" class=\"\">", nameCheckLong1="<h1 itemprop=\"name\" class=\"long\">", nameCheck2="&nbsp;";
                    String timeCheck1= "<time itemprop=\"duration\" datetime=\"PT", timeCheck2= "M\">";
                    String genreCheck1= "<span class=\"itemprop\" itemprop=\"genre\">", genreCheck2= "</span>";

                    while ((line = br.readLine()) != null) {
                        if(line.indexOf(nameCheck1)!=-1){
                            nameString= line.substring(line.indexOf(nameCheck1)+nameCheck1.length(), line.indexOf(nameCheck2));
                        }
                        else if(line.indexOf(nameCheckLong1)!=-1){
                            nameString= line.substring(line.indexOf(nameCheckLong1)+nameCheckLong1.length(), line.indexOf(nameCheck2));
                        }
                        else if(line.indexOf(timeCheck1)!=-1){
                            timeString= line.substring(line.indexOf(timeCheck1)+timeCheck1.length(), line.indexOf(timeCheck2));
                        }
                        else if(line.indexOf(genreCheck1)!=-1){
                            genreString= genreString+ line.substring(line.indexOf(genreCheck1)+genreCheck1.length(), line.indexOf(genreCheck2))+",";
                        }
                    }
                    System.out.println(nameString);
//<h1 itemprop="name" class="long">Birdman or (The Unexpected Virtue of Ignorance)&nbsp;<span id="titleYear">(<a href="/year/2014/?ref_=tt_ov_inf">2014</a>)</span>            </h1>
                    Movie newMovie= new Movie(nameString, timeString, genreString, priority);
                    pQueue.add(newMovie);

                    try {
            			File file = new File("watchList.txt");
            			if (!file.exists()) {
            				file.createNewFile();
                            System.out.println("\"watchList.txt\" is created. Please don't delete it.");
            			}

            			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        while(!pQueue.isEmpty()){
                            String writeLine= pQueue.remove().getMovie()+"\n";
                            bufferedWriter.write(writeLine);
                        }
            			bufferedWriter.close();
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
                }
            }
            else{
                System.out.println("Your input is invalid.");
            }
        }
        else if(args.length==1){
            //genre
            if(checkGenre(args[0])){
                int counter= 0;
                System.out.println("watchList (" + args[0].toLowerCase() + ")");
                while(!pQueue.isEmpty()){
                    String movieLine= pQueue.peek().getMovie();
                    if(movieLine.toLowerCase().indexOf(args[0].toLowerCase())!= -1){
                        counter++;
                        System.out.println(counter + ". " + pQueue.remove());
                    }
                    else{
                        pQueue.remove();
                    }
                }
            }
            //sort based on time
        }
        else if(args.length==0){
            printList(pQueue);
        }
        else{
            System.out.println("Your input is invalid.");
        }
    }

    public static boolean checkPriority(String arg){
        for(int i=1; i<=10; i++){
            if(Integer.toString(i).equals(arg)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkGenre(String arg){
        String smallArg= arg.toLowerCase();

        if(smallArg.equals("action")){
            return true;
        }
        else if(smallArg.equals("adventure")){
            return true;
        }
        else if(smallArg.equals("animation")){
            return true;
        }
        else if(smallArg.equals("biography")){
            return true;
        }
        else if(smallArg.equals("comedy")){
            return true;
        }
        else if(smallArg.equals("crime")){
            return true;
        }
        else if(smallArg.equals("documentary")){
            return true;
        }
        else if(smallArg.equals("drama")){
            return true;
        }
        else if(smallArg.equals("family")){
            return true;
        }
        else if(smallArg.equals("fantasy")){
            return true;
        }
        else if(smallArg.equals("film-noir") || smallArg.equals("filmnoir")){
            return true;
        }
        else if(smallArg.equals("history")){
            return true;
        }
        else if(smallArg.equals("horror")){
            return true;
        }
        else if(smallArg.equals("music")){
            return true;
        }
        else if(smallArg.equals("musical")){
            return true;
        }
        else if(smallArg.equals("mystery")){
            return true;
        }
        else if(smallArg.equals("romance")){
            return true;
        }
        else if(smallArg.equals("sci-fi") || smallArg.equals("scifi")){
            return true;
        }
        else if(smallArg.equals("sport")){
            return true;
        }
        else if(smallArg.equals("thriller")){
            return true;
        }
        else if(smallArg.equals("war")){
            return true;
        }
        else if(smallArg.equals("western")){
            return true;
        }

        return false;
    }

    public static void printList(PriorityQueue pQueue){
        int counter= 0;
        System.out.println("watchList");
        while(!pQueue.isEmpty()){
            counter++;
            System.out.println(counter + ". " + pQueue.remove());
        }
    }
}
