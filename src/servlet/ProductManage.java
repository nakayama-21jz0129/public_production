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
import model.Product;

/**
 * Servlet implementation class ProductManage
 */
@WebServlet("/G10/main/product-manage")
public class ProductManage extends HttpServlet {
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
            BeanForProductManage bean = new BeanForProductManage(employee);
            
            // 権限を確認
            if (employee.isManagerFlag()) {
                
                // インスタンスを作成
                Product product = new Product();
                
                // beanにデータをセット
                bean.setProductMapExt(product.getMapExt(false));
                
                // リクエストにbeanをセット
                request.setAttribute("bean", bean);
                
                // 商品管理ページへ飛ぶ
                String disp ="/WEB-INF/jsp/product_manage.jsp";
                RequestDispatcher dispatch = request.getRequestDispatcher(disp);
                dispatch.forward(request, response); 
            }
            else {            
                // メインServletへ飛ぶ
                String url = "../main";
                response.sendRedirect(url); 
            }
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
