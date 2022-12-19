package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BeanForEmployeeManage;
import model.Employee;

/**
 * Servlet implementation class EmployeeManage
 */
@WebServlet("/G10/main/employee-manage")
public class EmployeeManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	    HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("employee") != null) {
            BeanForEmployeeManage bean = new BeanForEmployeeManage((Employee)session.getAttribute("employee"));
            if (bean.getEmployee().isManagerFlag()) {
                bean.setEmployeeList();
                request.setAttribute("bean", bean);
                
                String disp ="/WEB-INF/jsp/employee_manage.jsp";
                RequestDispatcher dispatch = request.getRequestDispatcher(disp);
                dispatch.forward(request, response); 
            }
            else {            
                String url = "../main";
                
                response.sendRedirect(url); 
            }
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
//	    request.setCharacterEncoding("UTF-8");
//	    HttpSession session = request.getSession(false);
//        if (session != null && session.getAttribute("employee") != null) {
//            BeanForEmployeeManage bean = new BeanForEmployeeManage((Employee)session.getAttribute("employee"));
//    	    if (request.getParameter("name") != null && request.getParameter("password") != null) {
//    	        bean.setName(request.getParameter("name"));
//                bean.setPassword(request.getParameter("password"));
//                bean.setManagerFlag(request.getParameter("manager") != null ? true : false);
//                bean.setUseFlag(request.getParameter("use") != null ? true : false);
//                bean.addEmployee();
//    	    }
//    	    bean.setEmployeeList();
//    	    request.setAttribute("bean", bean);
//	        String disp ="/WEB-INF/jsp/employee_manage.jsp";
//            RequestDispatcher dispatch = request.getRequestDispatcher(disp);
//            dispatch.forward(request, response); 
//        }
//        else {
//            String url = "../login";
//    
//            response.sendRedirect(url); 
//        }
	}

}
