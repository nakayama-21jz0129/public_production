package servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BeanForOrderReg;
import conversion.Format;
import model.Employee;
import model.Product;
import model.Tax;
import model.TimePeriodDiscount;

/**
 * Servlet implementation class OrderReg
 */
@WebServlet("/G10/main/order-reg")
public class OrderReg extends HttpServlet {
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
            
            // 現在時刻を取得
            LocalDateTime dateTime = LocalDateTime.now();
            
            // beanを作成
            BeanForOrderReg bean = new BeanForOrderReg(employee);
            
            // インスタンスを作成
            Product product = new Product();
            Tax tax = new Tax();
            TimePeriodDiscount timePeriodDiscount = new TimePeriodDiscount();
            
            // 現在時刻から消費税と時間帯割引を取得
            tax.search(Format.localDateTimeToLocalDate(dateTime));
            timePeriodDiscount.search(dateTime);
            
            // bean用データを作成
            String hour = String.format("%02d", (int)timePeriodDiscount.getTimePeriod());
            String timePeriodText = hour + ":00 ～ " + hour + ":59";
            
            // beanにデータをセット
            bean.setProductMapExt(product.getMapExt(true));
            bean.setPercentRate((int)(timePeriodDiscount.getRate() * 100));
            bean.setTimePeriodText(timePeriodText);
            
            // セッションに注文開始時刻をセット
            session.setAttribute("orderDateTime", dateTime);
            
            // リクエストにbeanをセット
            request.setAttribute("bean", bean);
            
            // リクエストにJavaScript用データをセット
            request.setAttribute("productJson", Format.mapToJson(bean.getProductMapExt()));
            request.setAttribute("taxRate", tax.getRate());
            request.setAttribute("discountRate", timePeriodDiscount.getRate());
            
            // 注文登録ページへ飛ぶ
            String disp ="/WEB-INF/jsp/order_reg.jsp";
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
