package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BeanForOrderConfirm;
import model.Employee;
import model.Order;

/**
 * Servlet implementation class OrderConfirm
 */
@WebServlet("/G10/main/order-confirm")
public class OrderConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
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
            BeanForOrderConfirm bean = new BeanForOrderConfirm(employee);
            
            // インスタンスを作成
            Order order = new Order();
            
            // beanにデータをセット
            bean.setOrderArray(order.getArray());
            
            // リクエストにbeanをセット
            request.setAttribute("bean", bean);
            
            // 注文確認ページへ飛ぶ
            String disp ="/WEB-INF/jsp/order_confirm.jsp";
            RequestDispatcher dispatch = request.getRequestDispatcher(disp);
            dispatch.forward(request, response); 
        }
        else {
            // ログインServletへ飛ぶ
            String url = "../login";
            response.sendRedirect(url); 
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
