package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BeanForCustomerManage;
import model.Employee;

/**
 * Servlet implementation class CustomerManage
 */
@WebServlet("/G10/main/customer-manage")
public class CustomerManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	    HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("employee") != null) {
            BeanForCustomerManage bean = new BeanForCustomerManage((Employee)session.getAttribute("employee"));
            bean.setCustomerList();
            
            request.setAttribute("bean", bean);
            
            String disp ="/WEB-INF/jsp/customer_manage.jsp";
            RequestDispatcher dispatch = request.getRequestDispatcher(disp);
            dispatch.forward(request, response); 
        
        }
        else {
            String url = "../login";
    
            response.sendRedirect(url); 
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
