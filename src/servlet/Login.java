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
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	    String disp ="/WEB-INF/jsp/login.jsp";
        RequestDispatcher dispatch = request.getRequestDispatcher(disp);
        dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
	    request.setCharacterEncoding("UTF-8");
	    if (request.getParameter("name") != null && request.getParameter("password") != null) {
	        BeanForLogin bean = new BeanForLogin();
	        bean.setName(request.getParameter("name"));
	        bean.setPassword(request.getParameter("password"));
	        
	        HttpSession session = null;
	        if (bean.loginEmployee()) {
	            session = request.getSession(true);
	            session.setAttribute("employee", bean.getEmployee());
	            
	            String url = "main";
	            response.sendRedirect(url);
	        }
	        else {
	            request.setAttribute("bean", bean);
	            
	            String disp ="/WEB-INF/jsp/login.jsp";
	            RequestDispatcher dispatch = request.getRequestDispatcher(disp);
	            dispatch.forward(request, response);
	        }
	    }
	    else {
	        String disp ="/WEB-INF/jsp/login.jsp";
	        RequestDispatcher dispatch = request.getRequestDispatcher(disp);
	        dispatch.forward(request, response);
	    }
	}

}
