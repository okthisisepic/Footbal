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
    public void gen() {
        try {
            String apiKey = "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";

            URL url = new URL("https://api.groq.com/openai/v1/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);


            String question = "Random Footballername OUTPUT JUST THE NAME NOTHING ELSE";

            String json =
                    "{"
                            + "\"model\":\"llama-3.3-70b-versatile\","
                            + "\"messages\":["
                            + "{\"role\":\"user\",\"content\":\"" + question + "\"}"
                            + "]"
                            + "}";

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String output;

            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
