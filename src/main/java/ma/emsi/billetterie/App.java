package ma.emsi.billetterie;

import ma.emsi.billetterie.services.BilletService;
import ma.emsi.billetterie.services.MatchService;
import ma.emsi.billetterie.services.StadeService;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args )
    {
        StadeService stadeService = new StadeService();
        MatchService matchService = new MatchService();
        BilletService billetService = new BilletService();
    }
}
