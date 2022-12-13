package frontend;

import backend.User;
import backend.UsersDatabase;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionsInput;
import session.Session;

import java.util.Objects;

interface Visitor {
    void visit(ChangePage changePage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, ArrayNode output);
    void visit(LogoutPage logoutPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, ArrayNode output);
    void visit(OnPage onPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, ArrayNode output);

}

interface Visitable {
    void accept(PageVisitor v, ActionsInput action, Session session,
                UsersDatabase usersDatabase, ArrayNode output);
}

public class PageVisitor implements Visitor {
    /**
     * change page visitor
     */
    @Override
    public void visit(final ChangePage changePage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final ArrayNode output) {
        if (!session.isLogin()) {
            if (Objects.equals(session.getCurrentPage(),
                    "homepage neautentificat")) {
                if (Objects.equals(action.getPage(), "login")) {
                    System.out.println("changed to login");
                    session.setCurrentPage("login");
                } else if (Objects.equals(action.getPage(), "register")) {
                    System.out.println("changed to regsiter");
                    session.setCurrentPage("register");
                } else {
                    System.out.println("Eroare pe homepage neautentificat. Pagina inexistenta");
                }
            } else if (Objects.equals(session.getCurrentPage(), "login")) {
                System.out.println("Numai on page command login");
            } else if (Objects.equals(session.getCurrentPage(), "register")) {
                System.out.println("Numai on page command register");
            } else {
                System.out.println("blocat in home neauth");
            }
        } else {
            if (Objects.equals(session.getCurrentPage(),
                    "homepage autentificat")) {
                if (Objects.equals(action.getPage(), "logout")) {

                    PageVisitor visitor = new PageVisitor();
                    LogoutPage logoutPage = new LogoutPage();
                    logoutPage.accept(visitor, action, session, usersDatabase, output);
                } else {
                    System.out.println("Erroare autentificat changePage");
                    OutputGenerator outputGenerator = new OutputGenerator("General",
                            session.getCurrentUser());
                    output.add(outputGenerator.outputCreator());
                }
            }
        }
    }
    /**
     * logout page visitor
     */
    @Override
    public void visit(final LogoutPage logoutPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final ArrayNode output) {
        System.out.println("Logout");
        session.setCurrentPage("homepage neautentificat");
        session.setLogin(false);
        session.setCurrentUser(null);
    }
    /**
     * on page visitor
     */
    @Override
    public void visit(final OnPage onPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final ArrayNode output) {
        if (Objects.equals(action.getFeature(), "login")
                && Objects.equals(session.getCurrentPage(), "login")) {
            User checkUser = new User(action.getCredentials().getName(),
                    action.getCredentials().getPassword(),
                    null, null, 0);
            if (usersDatabase.checkUser(checkUser)) {
                session.setCurrentUser(usersDatabase.getUser(checkUser));
                session.setLogin(true);
                session.setCurrentPage("homepage autentificat");
                System.out.println("User " + checkUser.getName() + " added");
                User outUser = usersDatabase.getUser(checkUser);
                OutputGenerator outputGenerator = new OutputGenerator("User", outUser);
                output.add(outputGenerator.outputCreator());
            } else {
                OutputGenerator outputGenerator = new OutputGenerator("General", checkUser);
                output.add(outputGenerator.outputCreator());
                System.out.println("Eroare: nu e in baza de date " + checkUser.getName() + " "
                        + checkUser.getPassword());
                session.setCurrentPage("homepage neautentificat");
            }
        } else if (Objects.equals(action.getFeature(), "register")
                && Objects.equals(session.getCurrentPage(), "register")) {
            User checkUser = new User(action.getCredentials().getName(),
                    action.getCredentials().getPassword(),
                    action.getCredentials().getAccountType(), action.getCredentials().getCountry(),
                    action.getCredentials().getBalance());
            if (usersDatabase.checkUser(checkUser)) {
                System.out.println("error: User "
                        + action.getCredentials().getName() + " deja exista");
            } else {
                usersDatabase.addUser(checkUser);
                session.setCurrentPage("homepage autentificat");
                session.setLogin(true);
                session.setCurrentUser(checkUser);
                System.out.println("User " + checkUser.getName() + " added");
                OutputGenerator outputGenerator = new OutputGenerator("User", checkUser);
                output.add(outputGenerator.outputCreator());
            }
        } else {
            System.out.println("Error onPage");
            OutputGenerator outputGenerator = new OutputGenerator("General", null);
            output.add(outputGenerator.outputCreator());
        }
    }
}
