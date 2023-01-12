package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import conversion.Format;
import model.Employee;
import model.Order;
import model.OrderDetail;
import model.Product;
import model.Tax;
import model.TimePeriodDiscount;

/**
 * Servlet implementation class RegOrder
 */
@WebServlet("/G10/api/reg-order")
public class RegOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		// TODO Auto-generated method stub
	    
	    // 受信データの形式を指定
	    request.setCharacterEncoding("UTF-8");
	    // 送信データの形式を指定
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "nocache");
        response.setCharacterEncoding("UTF-8");
        
        // 送信用Mapを作成
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", false);
        responseMap.put("msg", "");
        
        // セッションを取得
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("employee") != null) {
            
            // セッションから従業員データを取得
            Employee employee = (Employee) session.getAttribute("employee");
            
            // 受信データを取得
            BufferedReader buffer = new BufferedReader(request.getReader());
            String requestJson = buffer.readLine();
            Map<String, Object> requestMap = Format.jsonToMap(requestJson);
            
            // 受信データの存在を確認
            if (requestMap != null) {
                
                // 受信データの要素を確認
                if (requestMap.containsKey("customer_id") &&
                        requestMap.containsKey("product_map")) {
                    
                    // インスタンスを作成
                    Product product = new Product();
                    Order order = new Order();
                    Tax tax = new Tax();
                    TimePeriodDiscount timePeriodDescount = new TimePeriodDiscount();
                    
                    // 商品一覧を取得
                    Map<Integer, Map> productMap = product.getMap(true);
                    
                    // 注文詳細を取得
                    Map<String, String> orderDetailMap = 
                            (Map<String, String>)(requestMap.get("product_map"));
                    
                    // 消費税を探す
                    tax.search(Format.localDateTimeToLocalDate(
                            (LocalDateTime)session.getAttribute("orderDateTime")));
                    
                    // 時間帯割引を探す
                    timePeriodDescount.search((LocalDateTime)session.getAttribute("orderDateTime"));
                    
                    // 処理用変数を作成
                    int sum = 0;
                    ArrayList<OrderDetail> array = new ArrayList<>();
                    
                    // 連想配列のキーでループ
                    for (String mapKey : orderDetailMap.keySet()) {
                        
                        // 商品の存在を確認
                        if (productMap.containsKey(Integer.parseInt(mapKey))) {
                            
                            // OrderDetail型配列にOrderDetailインスタンスをセット
                            array.add(new OrderDetail(Integer.parseInt(mapKey),
                                    Integer.parseInt(orderDetailMap.get(mapKey))));
                            
                            // 合計金額を加算
                            sum += (Integer)(productMap.get(Integer.parseInt(mapKey)).get("price")) * Integer.parseInt(orderDetailMap.get(mapKey)); 
                        }
                    }
                    
                    // 請求金額を計算
                    int billing = sum - (int)(sum * timePeriodDescount.getRate());
                    billing = billing + (int)(billing * tax.getRate());
                   
                    // 注文を登録
                    // 特殊な登録処理
                    int result = order.reg(
                            (Integer)employee.getId(),
                            (Integer)requestMap.get("customer_id"),
                            (String)requestMap.get("temp_address"),
                            (LocalDateTime)session.getAttribute("orderDateTime"),
                            array,
                            sum,
                            billing);
                    switch (result) {
                        case 0:
                            responseMap.replace("msg", "注文の登録に失敗しました");
                            break;
                        case -1:
                            responseMap.replace("msg", "注文明細の登録に失敗しました");
                            break;
                        default:
                            responseMap.replace("result", true);
                            responseMap.replace("msg", "注文の登録に成功しました");
                            order.search(result);
                            responseMap.put("order", order.toMapExt());
                            session.removeAttribute("orderDateTime");
                    }
                }
                else {
                    responseMap.replace("msg", "データが不足しています");
                }
            }
            else {
                responseMap.replace("msg", "適切なデータが存在しません");
            }
        }
        else {
            responseMap.replace("msg", "セッションが存在しません");
        }
        
        // データの送信
        PrintWriter out = response.getWriter();
        out.print(Format.mapToJson(responseMap));
	}

}
