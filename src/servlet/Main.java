package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BeanForMain;
import model.Employee;

/**
 * Servlet implementation class Main
 */
@WebServlet("/G10/main")
public class Main extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        // セッションを取得
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("employee") != null) {
            
            // セッションから従業員データを取得
            Employee employee = (Employee)session.getAttribute("employee");
            
            // beanの作成
            BeanForMain bean = new BeanForMain(employee);
            
            // リクエストにbeanをセット
            request.setAttribute("bean", bean);
            
            // メインページへ飛ぶ
            String disp ="/WEB-INF/jsp/main.jsp";
            RequestDispatcher dispatch = request.getRequestDispatcher(disp);
            dispatch.forward(request, response);
        }
        else {
            // ログインServletへ飛ぶ
            String url = "login";
            response.sendRedirect(url); 
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
