package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BeanForProductManage;
import model.Employee;

/**
 * Servlet implementation class ProductManage
 */
@WebServlet("/G10/main/product-manage")
public class ProductManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	    HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("employee") != null) {
            BeanForProductManage bean = new BeanForProductManage((Employee)session.getAttribute("employee"));
            if (bean.getEmployee().isManagerFlag()) {
                bean.setProductMap();
                request.setAttribute("bean", bean);
                
                String disp ="/WEB-INF/jsp/product_manage.jsp";
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
	}

}
