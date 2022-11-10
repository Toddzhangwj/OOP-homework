import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class ChessListener extends MouseAdapter implements ActionListener{
    private FiveChess fiveChess;
    public ChessListener(FiveChess fiveChess) {
        this.fiveChess = fiveChess;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
