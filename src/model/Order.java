package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import conversion.Format;
import database.OrderDAO;
import database.OrderDTO;
import database.OrderDetailDAO;
import database.OrderDetailDTO;
import database.OrderJoinDAO;
import database.OrderJoinDTO;

public class Order {
    private int id;
    private Employee employee;
    private Customer customer;
    private String tempAddress;
    private LocalDateTime dateTime;
    private ArrayList<OrderDetail> orderDetailArray;
    private int totalPrice;
    private Tax tax;
    private TimePeriodDiscount timePeriodDiscount;
    private int billingPrice;
    private String status;

    /**
     * 引数無しコンストラクタ
     */
    public Order() {
    }
    
    private Order(int id, LocalDateTime dateTime, int billingPrice, String status, String customerName,
            String customerTel, String customerAddress, String tempAddress, String employeeName,
            boolean employeeManagerFlag) {
        this.id = id;
        this.employee = new Employee();
        this.employee.setName(employeeName);
        this.employee.setManagerFlag(employeeManagerFlag);
        this.customer = new Customer();
        this.customer.setName(customerName);
        this.customer.setTel(customerTel);
        this.customer.setAddress(customerAddress);
        this.tempAddress = tempAddress;
        this.dateTime = dateTime;
        this.billingPrice = billingPrice;
        this.status = status;
    }

    // getter・setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTempAddress() {
        return tempAddress;
    }

    public void setTempAddress(String tempAddress) {
        this.tempAddress = tempAddress;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ArrayList<OrderDetail> getOrderDetailArray() {
        return orderDetailArray;
    }

    public void setOrderDetailArray(ArrayList<OrderDetail> orderDetailArray) {
        this.orderDetailArray = orderDetailArray;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public TimePeriodDiscount getTimePeriodDiscount() {
        return timePeriodDiscount;
    }

    public void setTimePeriodDiscount(TimePeriodDiscount timePeriodDiscount) {
        this.timePeriodDiscount = timePeriodDiscount;
    }

    public int getBillingPrice() {
        return billingPrice;
    }

    public void setBillingPrice(int billingPrice) {
        this.billingPrice = billingPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @param employeeId
     * @param customerId
     * @param customerTempAddress
     * @param dateTime
     * @param orderDetailArray
     * @param totalPrice
     * @return
     */
    public int reg(int employeeId, int customerId, String customerTempAddress, LocalDateTime dateTime,
            ArrayList<OrderDetail> orderDetailArray, int totalPrice, int billingPrice) {
        // return用
        int result = 0;
        
        // DAO
        OrderDAO orderDAO = new OrderDAO();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        
        // 注文を登録
        int orderId = orderDAO.reg(
                Format.localDateTimeToTimestamp(dateTime),
                totalPrice,
                billingPrice,
                customerId,
                customerTempAddress,
                employeeId);
        
        // 注文の成功を確認
        if (orderId > 0) {
            
            // 注文明細を登録
            if (orderDetailDAO.reg(orderId, orderDetailArray) > 0) {
                result = orderId;
            }
            else {
                // 注文を削除
                orderDAO.del(orderId);
                result = -1;
            }
        }
        
        return result;
    }
    
    /**
     * 全注文をOrder型配列で返す。
     * @return
     */
    public ArrayList<Order> getArray() {
        // return用
        ArrayList<Order> array = new ArrayList<>();
        
        // DAO・DTO
        OrderJoinDAO orderJoinDAO = new OrderJoinDAO();
        ArrayList<OrderJoinDTO> orderJoinDTOArray = null;
        
        // OrderJoinDTO型配列をOrder型配列に変換
        orderJoinDTOArray = orderJoinDAO.getArray();
        for (OrderJoinDTO dto : orderJoinDTOArray) {
            array.add(
                    new Order(
                            dto.getId(),
                            Format.timestampToLocalDateTime(dto.getOrderTime()),
                            dto.getBillingPrice(),
                            dto.getStatus(),
                            dto.getCustomerName(),
                            dto.getCustomerTel(),
                            dto.getCustomerAddress(),
                            dto.getTempAddress(),
                            dto.getEmployeeName(),
                            Format.intToBoolean(dto.getEmployeeManagerFlag())));
        }
        
        return array;
    }
    
    /**
     * 識別子から注文を探す。
     *  見つかればフィールドにデータをセット
     * @param id
     * @return
     */
    public boolean search(int id) {
        // DAO
        OrderDAO orderDAO = new OrderDAO();
        OrderDTO orderDTO = null;
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        
        // 識別子を確認
        if (id != 0) {
            
            // 注文を探す
            orderDTO = orderDAO.search(id);
            if (orderDTO != null) {
                
                // インスタンスを作成
                Employee employee = new Employee();
                Customer customer = new Customer();
                ArrayList<OrderDetail> orderDetailArray = new ArrayList<>();
                Tax tax = new Tax();
                TimePeriodDiscount timePeriodDiscount = new TimePeriodDiscount();
                
                // 従業員を探す
                employee.search(orderDTO.getEmployeeId());
                
                // 顧客を探す
                customer.search(orderDTO.getCustomerId());
                
                // 注文明細を探す
                for (OrderDetailDTO dto : orderDetailDAO.search(orderDTO.getId())) {
                    orderDetailArray.add(new OrderDetail(dto.getProductId(), dto.getQuantity()));
                }
                
                // 消費税を探す
                tax.search(
                        Format.localDateTimeToLocalDate(
                                Format.timestampToLocalDateTime(orderDTO.getOrderTime())));
                
                // 時間帯割引を探す
                timePeriodDiscount.search(Format.timestampToLocalDateTime(orderDTO.getOrderTime()));
                
                // フィールドに値をセット
                setId(orderDTO.getId());
                setEmployee(employee);
                setCustomer(customer);
                setTempAddress(orderDTO.getTempAddress());
                setDateTime(Format.timestampToLocalDateTime(orderDTO.getOrderTime()));
                setOrderDetailArray(orderDetailArray);
                setTotalPrice(orderDTO.getTotalPrice());
                setTax(tax);
                setTimePeriodDiscount(timePeriodDiscount);
                setBillingPrice(orderDTO.getBillingPrice());
                setStatus(orderDTO.getStatus());
                
                return true;
            }
        }
        
        return false;
    }

    public Map<String, Object> toMapExt() {
        // return用
        Map<String, Object> map = new HashMap<>();
       
        // フィールドの値をセット
        // 識別子
        map.put("id", getId());
        // 従業員
        map.put("employee", getEmployee().toMap());
        // 顧客
        map.put("customer", getCustomer().toMap());
        // 住所
        if (getTempAddress() != null) {
            map.put("address", getTempAddress());
        }
        else {
            map.put("address", getCustomer().getAddress());
        }
        // 日時
        map.put("time", getDateTime().format(DateTimeFormatter.ofPattern("yyyy'年'MM'月'dd'日　'HH'時'mm'分'")));
        
        // 注文明細
        Map<Integer, Map> orderDetailMap = new HashMap<>();
        Product product = new Product();
        ProductClass productClass = new ProductClass();
        Map<Integer, Map> productMap = product.getMap(false);
        Map<Integer, String> productClassMap = productClass.getMap();
        for (OrderDetail orderDetail : getOrderDetailArray()) {
            Map<String, Object> productMapValue = productMap.get(orderDetail.getProductId());
            if (!orderDetailMap.containsKey(productMapValue.get("product_class_id"))) {
                Map<String, Object> innerMap = new HashMap<>();
                innerMap.put("name", productClassMap.get(productMapValue.get("product_class_id")));
                innerMap.put("product", new HashMap<Integer, Map>());
                orderDetailMap.put((Integer)productMapValue.get("product_class_id"), innerMap);
            }
            productMapValue.put("quantity", orderDetail.getQuantity());
            productMapValue.put("sub_price", (Integer)productMapValue.get("price") * orderDetail.getQuantity());
            ((Map<Integer, Map>)(orderDetailMap.get(productMapValue.get("product_class_id"))
                    .get("product"))).put(orderDetail.getProductId(), productMapValue);
        }
        map.put("order_detail", orderDetailMap);
        
        // 合計金額
        map.put("total_price", getTotalPrice());
        
        // 割引額
        map.put("discount_price", (int)(getTotalPrice() * getTimePeriodDiscount().getRate()));
        
        // 消費税金額
        map.put("tax_price", (int)((getTotalPrice() - (int)(getTotalPrice() * getTimePeriodDiscount().getRate())) * getTax().getRate()));
        
        // 請求金額
        map.put("billing_price", getBillingPrice());
        
        // ステータス
        map.put("status", getStatus());
        
        return map;
    }
}
