package src.main.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/**
 * Created by Даша on 04.05.2016.
 */
@WebServlet(value = "/login")

public class loginServlet extends HttpServlet{

    boolean userExist(String log, String pass) throws IOException {
        String path = getServletContext().getRealPath("/");
        path += "users.txt";
        Scanner in = new Scanner(new File(path));
        ArrayList<User> list = new ArrayList<User>();
        while(in.hasNext()){
            list.add(new User(in.next(), in.next()));
        }
        for(User u: list){
            if(u.getUsername().equals(log) && u.getPassword().equals(pass)){
                return true;
            }
        }
        return false;
    }

    private static String encryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String sha1 = "";
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(password.getBytes("UTF-8"));
        Formatter formatter = new Formatter();
        for (byte b : crypt.digest()) {
            formatter.format("%02x", b);
        }
        sha1 = formatter.toString();
        formatter.close();
        return sha1;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String log = req.getParameter("username");
        String pass = req.getParameter("pass");
        try {
            if(userExist(log, encryptPassword(pass))){
                req.getSession();
                resp.sendRedirect("homepage.html");
             }
             else{
                req.setAttribute("error", "Unknown user, please try again");
                req.getRequestDispatcher("/logPass.jsp").forward(req, resp);
              }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
