import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Spieler {
    public String name;
    public int price;
    public enum PrefferredPosition {
        Torwart,Innerverteidiger,Außenverteidiger,DefensiverMittelspieler,ZentralMittelfeldSpieler,RechterFlügelSpieler,LinkerFlügelSpieler,OffensiverMittelFeldSpieler;
    }

    public PrefferredPosition getPosition() {
        return Position;
    }

    public void setPosition(PrefferredPosition position) {
        Position = position;
    }


    PrefferredPosition Position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public static void gen() {
        try {
            String apiKey = "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";


            URL url = new URL("https://api.groq.com/openai/v1/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String question = "Random Footballer not Cristiano Ronaldo output just the name thank you";

            String body = """
            {
              "model": "llama-3.3-70b-versatile",
              "messages": [
                {
                  "role": "user",
                  "content": "%s"
                }
              ]
            }
            """.formatted(question);

            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String line;

            while ((line = br.readLine()) != null) {

                if (line.contains("\"content\":\"")) {

                    String result = line.split("\"content\":\"")[1]
                            .split("\"")[0];

                    System.out.println(result);
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        gen();
    }
}
