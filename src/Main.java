import convertimg.convert.Converter;
import convertimg.convertInter.TextGraphicsConverter;
import server.Xserver;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new Converter();

        Xserver server = new Xserver(converter);
        server.start();

    }
}