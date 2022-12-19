<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>メイン</title>
  
  <c:choose>
    <c:when test="${bean.employee.managerFlag}">
      <link rel="stylesheet" href="../css/color_pattern2.css">
  	</c:when>
  	<c:otherwise>
      <link rel="stylesheet" href="../css/color_pattern1.css">
  	</c:otherwise>
  </c:choose>
  
  <link rel="stylesheet" href="../css/default.css">
  <link rel="stylesheet" href="../css/main.css">
</head>
<body>

  <header>
    <h1>
      <img src="../image/logo.png" alt="炭火焼鳥山澤">
    </h1>
    <nav>
      <ol>
        <a href="main"><li value="true">メイン</li></a>
        <a href="main/order-reg"><li value="false">注文登録</li></a>
        <a href="main/order-confirm"><li value="false">注文確認</li></a>
        <a href="main/customer-manage"><li value="false">顧客管理</li></a>
        
        <c:if test="${bean.employee.managerFlag}"> 
          <a href="main/employee-manage"><li value="false">従業員管理</li></a>
          <a href="main/product-manage"><li value="false">商品管理</li></a>
          <a href="main/discount-manage"><li value="false">割引設定</li></a>
          <a href="main/money-received"><li value="false">入金</li></a>
        </c:if>
        
      </ol>
    </nav>
    <p id="employee">
      <c:choose>
        <c:when test="${bean.employee.managerFlag}">
    	    マネージャ
  		  </c:when>
  		  <c:otherwise>
    	    従業員
  		  </c:otherwise>
      </c:choose>
      ：${bean.employee.name}さん
    </p>
    <a href="logout" id="logout"><p>ログアウト</p></a>
  </header>

  <main>
    <div>
      <a href="main/order-reg" class="button3 text_c">注文登録</a>
      <a href="main/order-confirm" class="button3 text_c">注文確認</a>
      <a href="main/customer-manage" class="button3 text_c">顧客管理</a>
    </div>
    
    <c:if test="${bean.employee.managerFlag}">
      <div>
        <a href="main/employee-manage" class="button3 text_c">従業員管理</a>
        <a href="main/product-manage" class="button3 text_c">商品管理</a>
        <a href="main/discount-manage" class="button3 text_c">割引設定</a>
      </div>

      <div>
        <a href="main/money-received" class="button3 text_c">入金</a>
      </div>
    </c:if>
    
  </main>

  <footer>
    <div>
      <small>&copy; G10 v0.8</small>
    </div>
  </footer>

</body>
</html>