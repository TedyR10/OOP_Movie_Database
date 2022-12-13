package frontend;

import backend.UsersDatabase;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionsInput;
import session.Session;

public class OnPage implements Visitable {

    public OnPage() {
    }
    /**
     * for coding style
     */
    public void accept(final PageVisitor v, final ActionsInput action,
                       final Session session, final UsersDatabase usersDatabase,
                       final ArrayNode output) {
        v.visit(this, action, session, usersDatabase, output);
    }
}
