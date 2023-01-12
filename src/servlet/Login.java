package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BeanForLogin;
import model.Employee;

/**
 * Servlet implementation class Login
 */
@WebServlet("/G10/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    
	    // beanを作成
        BeanForLogin bean = new BeanForLogin();
	    
	    // リクエストにbeanをセット
        request.setAttribute("bean", bean);
	    
	    // ログインページへ飛ぶ
	    String disp ="/WEB-INF/jsp/login.jsp";
        RequestDispatcher dispatch = request.getRequestDispatcher(disp);
        dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    
	    // 受信データの形式を指定
	    request.setCharacterEncoding("UTF-8");
	    
	    // 必要なパラメータの存在を確認
	    if (request.getParameter("name") != null && request.getParameter("password") != null) {
	        
	        // beanを作成
	        BeanForLogin bean = new BeanForLogin();
	        
	        // 従業員のログインを処理
	        Employee employee = new Employee();
	        if (employee.login(
                    request.getParameter("name"),
                    request.getParameter("password"))) {
	            
	            // セッションを作成
	            HttpSession session = request.getSession(true);
	            
	            // セッションに従業員データをセット
	            session.setAttribute("employee", employee);
	            
	            // メインServletへ飛ぶ
	            String url = "main";
	            response.sendRedirect(url);
	        }
	        else {
	            // beanにデータをセット
	            bean.setName(request.getParameter("name"));
	            bean.setMsg(employee.getId() > 0 ?
	                    "このアカウントは使用できません" :"従業員名・パスワードが正しくありません");
	            
	            // リクエストにbeanをセット
	            request.setAttribute("bean", bean);
	            
	            // ログインページへ飛ぶ
	            String disp ="/WEB-INF/jsp/login.jsp";
	            RequestDispatcher dispatch = request.getRequestDispatcher(disp);
	            dispatch.forward(request, response);
	        }
	    }
	    else {
	        // ログインページへ飛ぶ
	        String disp ="/WEB-INF/jsp/login.jsp";
	        RequestDispatcher dispatch = request.getRequestDispatcher(disp);
	        dispatch.forward(request, response);
	    }
	}

}
