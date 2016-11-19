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
                    String nameCheck1="<h1 itemprop=\"name\" class=\"\">", nameCheck2="&nbsp;";
                    String timeCheck1= "<time itemprop=\"duration\" datetime=\"PT", timeCheck2= "M\">";
                    String genreCheck1= "<span class=\"itemprop\" itemprop=\"genre\">", genreCheck2= "</span>";

                    while ((line = br.readLine()) != null) {
                        if(line.indexOf(nameCheck1)!=-1){
                            nameString= line.substring(line.indexOf(nameCheck1)+nameCheck1.length(), line.indexOf(nameCheck2));
                        }
                        else if(line.indexOf(timeCheck1)!=-1){
                            timeString= line.substring(line.indexOf(timeCheck1)+timeCheck1.length(), line.indexOf(timeCheck2));
                        }
                        else if(line.indexOf(genreCheck1)!=-1){
                            genreString= genreString+ line.substring(line.indexOf(genreCheck1)+genreCheck1.length(), line.indexOf(genreCheck2))+",";
                        }
                    }

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
            //1 argument conditions
            //sort based on time
            //filter genre
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

    public static void printList(PriorityQueue pQueue){
        int counter= 0;
        System.out.println("Watch List");
        while(!pQueue.isEmpty()){
            counter++;
            System.out.println(counter + ". " + pQueue.remove());
        }
    }
}
