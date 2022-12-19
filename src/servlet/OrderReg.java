package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.BeanForOrderReg;
import model.Employee;

/**
 * Servlet implementation class OrderReg
 */
@WebServlet("/G10/main/order-reg")
public class OrderReg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	    HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("employee") != null) {
            BeanForOrderReg bean = new BeanForOrderReg((Employee)session.getAttribute("employee"));
            
            bean.setProductMap();
            bean.setTaxRate();
            request.setAttribute("bean", bean);
            ObjectMapper mapper = new ObjectMapper();
            request.setAttribute("productJson", mapper.writeValueAsString(bean.getProductMap()));
            request.setAttribute("taxRate", bean.getTaxRate());
            
            String disp ="/WEB-INF/jsp/order_reg.jsp";
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
