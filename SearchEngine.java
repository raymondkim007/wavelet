import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class MyHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    
    ArrayList<String> myList = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String res = "{";
            for (int i = 0; i < myList.size() - 1; i++)
                res += myList.get(i) + ", ";
            if (myList.size() > 0) res += myList.get(myList.size() - 1);
            res += "}";
            return String.format("Raymond's List: s", res);
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                myList.add(parameters[1]);
                return String.format("String \"%s\" added to ArrayList", parameters[1]);
            }
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                ArrayList<String> resArray = new ArrayList<String>();
                String substr = parameters[1];
                for (int i = 0; i < myList.size(); i++)
                    if (myList.get(i).contains(substr))
                        resArray.add(myList.get(i));
                String res = "{";
                for (int i = 0; i < resArray.size() - 1; i++)
                    res += resArray.get(i) + ", ";
                if (resArray.size() > 0) res += resArray.get(resArray.size() - 1);
                res += "}";
                return String.format("Results: s", res);
            }
        } else {
            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
