package ma.emsi.billetterie;

import ma.emsi.billetterie.dao.IBilletImpl;
import ma.emsi.billetterie.dao.IMatchImpl;
import ma.emsi.billetterie.dao.IStadeImpl;
import ma.emsi.billetterie.entities.Billet;
import ma.emsi.billetterie.entities.Competition;
import ma.emsi.billetterie.entities.Match;
import ma.emsi.billetterie.entities.Stade;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args )
    {
        IStadeImpl iStade = new IStadeImpl();
        IMatchImpl iMatch = new IMatchImpl();
        IBilletImpl iBillet = new IBilletImpl();
    }
}
