package auction;

import javax.swing.*;

/**
 * Created by kujit on 05.10.15.
 */
public class Third extends JFrame
{
    Second.Solut soll;

    public Third(Second.Solut sol)
    {
        super("Изменения");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400,300);

        this.soll = sol;

    }
}
