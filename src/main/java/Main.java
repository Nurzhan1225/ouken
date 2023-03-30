import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    //private static HttpURLConnection connection;

    public static void main(String[] args) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        HttpURLConnection connection = null;
        try{
            URL url = new URL("http://dataservice.accuweather.com/currentconditions/v1/22234?apikey=sTSI0qLj7dX2nAzzsXQI9B1Wap3oweM5");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status);

            if(status == 200){
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                    responseContent.append("\n");
                }
                reader.close();
            }
            System.out.println(responseContent.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        connection.disconnect();
        HttpClient client =  HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/albums")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(Main::parse)
                .join();
    }

    public static String parse (String responseBody){
        JSONArray albums = new JSONArray(responseBody);
        for(int i = 0; i<albums.length();i++){
            JSONObject album = albums.getJSONObject(i);
            int id = album.getInt("id");
            int userid = album.getInt("userId");
            String title = album.getString("title");
            System.out.println(id + ", "+ userid +", "+title);
        }

        return null;



    }
}
