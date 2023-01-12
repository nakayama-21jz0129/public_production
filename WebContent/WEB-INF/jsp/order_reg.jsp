<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>注文登録</title>

<c:choose>
	<c:when test="${bean.employee.managerFlag}">
		<link rel="stylesheet" href="../../css/color_pattern2.css">
	</c:when>
	<c:otherwise>
		<link rel="stylesheet" href="../../css/color_pattern1.css">
	</c:otherwise>
</c:choose>

<link rel="stylesheet" href="../../css/default.css">
<link rel="stylesheet" href="../../css/order_reg.css">
</head>
<body>

	<header>
		<h1>
			<img src="../../image/logo.png" alt="炭火焼鳥山澤">
		</h1>
		<nav>
			<ol>
				<a href="../main"><li value="false">メイン</li></a>
				<a href="order-reg"><li value="true">注文登録</li></a>
				<a href="order-confirm"><li value="false">注文確認</li></a>
				<a href="customer-manage"><li value="false">顧客管理</li></a>

				<c:if test="${bean.employee.managerFlag}">
					<a href="employee-manage"><li value="false">従業員管理</li></a>
					<a href="product-manage"><li value="false">商品管理</li></a>
					<a href="discount-manage"><li value="false">割引設定</li></a>
					<a href="money-received"><li value="false">入金</li></a>
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
		<a href="../logout" id="logout"><p>ログアウト</p></a>
	</header>

	<main>

		<article class="generic_contents" id="customer">
			<h2 class="g_c_header">顧客</h2>

			<div class="g_c_inner">
				<div class="g_c_i_flex1">
					<h3 class="w80">顧客電話番号</h3>
					<input type="text" class="in1 w80" id="search_tel" maxlength="15"	placeholder="xxx-xxxx-xxxx" autocomplete="off">
					<button type="button" class="button1 y_c" id="search_button">検索</button>
				</div>
				<div class="error_msg" id="tel_error_msg"></div>
			</div>

			<div class="g_c_inner d_n" id="customer_name">
				<div class="g_c_i_flex1">
					<h3 class="out_h w80">顧客名前</h3>
					<p class="out1 w80"></p>
					<button type="button" class="button1 y_c y_top4">変更</button>
				</div>
			</div>

			<div class="g_c_inner d_n" id="customer_address">
				<div class="g_c_i_flex1">
					<h3 class="out_h w80">顧客住所</h3>
					<p class="out1 w80"></p>
					<button type="button" class="button1 y_c y_top4">変更</button>
				</div>
				<div class="g_c_i_flex2">
					<div class="checkArea">
						<input value="true" type="checkbox" id="check_temp" class="d_n check">
						<label for="check_temp">
							<div class="box"></div>
						</label>
					</div>
					<p class="lh24">入力住所に配達</p>
					<input type="text" class="in1 d_n" id="temp_address">
				</div>
			</div>

			<div class="g_c_inner d_n" id="customer_product">
				<h3>顧客好み</h3>
				<div class="g_c_inner">
					<h4 class="w100 out_h">サイドメニュー</h4>
					<p class="out1" id="s_p"></p>
				</div>
				<div class="g_c_inner">
					<h4 class="w100 out_h">ドリンク</h4>
					<p class="out1" id="d_p"></p>
				</div>
			</div>
		</article>

		<article id="order">
			<div class="generic_contents">
				<h2 class="g_c_header">注文追加</h2>

				<div class="g_c_inner">
					<div class="g_c_i_flex1">
						<div class="w30">
							<h3>カテゴリ</h3>
							<select class="in1" id="category_select">
								<c:forEach var="obj" items="${bean.productMapExt}">
									<option value="${obj.key}">${obj.value.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="w30">
							<h3>商品</h3>
							<select name="" class="in1" id="product_select">
							</select>
						</div>
						<div class="w30 p_m_buttons">
							<h3>数量</h3>
							<input type="number" name="" class="in1 num_r w65 p_m_input"
								value="1" min="1" max="50" id="quantity_input">
							<button type="button" class="button4 p_b">+</button>
							<button type="button" class="button4 m_b">-</button>
						</div>
					</div>
					<div class="error_msg" id="add_error_msg"></div>
					<button type="button" class="button2 x_c y12" id="add_button">追加</button>
				</div>
			</div>

			<div class="generic_contents">
				<h2 class="g_c_header">注文内容</h2>
				<div class="g_c_inner g_c_i_flex1 w90">
					<div class="w48">
						<h3>適応時間帯割引</h3>
						<div class="g_c_inner g_c_i_flex1 w90">
							<div class="w80">${bean.timePeriodText}</div>
							<div class="w20">${bean.percentRate}%</div>
						</div>
					</div>

					<div class="w48">
						<h3>金額</h3>
						<div class="g_c_inner">
							<div class="g_c_i_flex1">
								<p class="w30">合計金額</p>
								<p class="w30 num_r"><span id="total_price">0</span>円</p>
							</div>
							<div class="g_c_i_flex1">
								<p class="w30">割引金額</p>
								<p class="w30 num_r"><span id="discount_price">0</span>円</p>
							</div>
							<div class="g_c_i_flex1">
								<p class="w30">消費税額</p>
								<p class="w30 num_r"><span id="tax_price">0</span>円</p>
							</div>
							<div class="g_c_i_flex1">
								<p class="w30 fw">請求金額</p>
								<p class="w30 num_r fw"><span id="billing_price">0</span>円</p>
							</div>
						</div>
					</div>
				</div>

				<div class="g_c_inner">
					<h3>注文商品一覧</h3>
					<table class="g_c_inner" id="order_table">
						<tr>
							<th class="w5">選択</th>
							<th class="w25">カテゴリ</th>
							<th class="w30">商品名</th>
							<th class="w10">単価</th>
							<th class="w20">数量</th>
							<th class="w10">小計</th>
						</tr>
					</table>
				</div>

				<div class="g_c_inner g_c_i_flex1">
					<button type="button" class="button2 x_c y12 b_red" id="delete_button">注文削除</button>
					<button type="button" class="button2 x_c y12" id="commit_button">注文確定</button>
				</div>
			</div>

		</article>
	</main>

	<footer>
		<div>
			<small>${bean.copyright}&ensp;&ensp;${bean.version}</small>
		</div>
	</footer>

	<div class="p_background d_n" id="customer_popup">
    <article class="generic_contents popup w30">
      <h2 class="g_c_header">確認</h2>
      <div class="g_c_off">×</div>
			<div class="g_c_inner w80">
				<h3 class="out_h text_c">電話番号</h3>
				<p class="out1 text_c"></p>
			</div>
      <div class="g_c_inner msg">この電話番号の顧客は登録されていません</div>
      <div class="g_c_inner msg">新規顧客登録を行いますか？</div>
      <div class="g_c_inner g_c_i_flex1">
        <button type="button" class="button2 x_c y12 b_red no">いいえ</button>
        <button type="button" class="button2 x_c y12 yes">はい</button>
      </div>
    </article>
  </div>

	<div class="p_background d_n" id="result_popup">
    <article class="generic_contents popup w30">
      <h2 class="g_c_header">確認</h2>
      <div class="g_c_off">×</div>
      <div class="g_c_inner msg"></div>
      <div class="g_c_inner error_msg"></div>
    </article>
  </div>

	<div class="p_background d_n" id="order_popup">
    <article class="generic_contents popup w25">
      <h2 class="g_c_header">確認</h2>
      <div class="g_c_off">×</div>
      <div class="g_c_inner msg">注文を確定します</div>
      <div class="g_c_inner g_c_i_flex1">
        <button type="button" class="button2 x_c y12 b_red no">いいえ</button>
        <button type="button" class="button2 x_c y12 yes">はい</button>
      </div>
    </article>
  </div>

	<div class="p_background d_n" id="slip_popup">
    <article class="generic_contents popup w35">
      <h2 class="g_c_header">注文伝票</h2>
      <button type="button" onclick="location.href='../main'" class="g_c_off">×</button>
			<div class="scroll">
				<div class="g_c_inner">
					<h4 class="out1">■ お届け先</h4>
					<p class="out1">
						ご住所：<span id="slip_address"></span>
					</p>
					<p class="out1">
						お名前：<span id="slip_name"></span>
					</p>
					<p class="out1">
						電話番号：<span id="slip_tel"></span>
					</p>
				</div>

				<div class="w100" id="slip_order"></div>

				<div class="g_c_inner">
					<div class="out1 g_c_i_flex1">
						<p>合計金額</p>
						<p><span id="slip_total_price"></span>円</p>
					</div>
					<div class="out1 g_c_i_flex1">
						<p>割引金額</p>
						<p><span id="slip_discount_price"></span>円</p>
					</div>
					<div class="out1 g_c_i_flex1">
						<p>消費税</p>
						<p><span id="slip_tax_price"></span>円</p>
					</div>
				</div>

				<div class="g_c_inner">
					<h4 class="out1 g_c_i_flex1 bill">
						<p>ご請求金額</p>
						<p><span id="slip_billing_price"></span>円</p>
					</h4>
				</div>

				<div class="g_c_inner">
					<p class="out1">
						注文日時&ensp;<span id="slip_time"></span>
					</p>
					<p class="out1">
						注文番号&ensp;<span id="slip_id"></span>
					</p>
				</div>

				<div class="g_c_inner g_c_i_flex1">
					<button type="button" onclick="location.href='../main'" class="button2 x_c y12">閉じる</button>
				</div>
			</div>

    </article>
  </div>

	<script type="text/javascript">
		const ProductObject = JSON.parse('<%=(String)request.getAttribute("productJson")%>');
		const DiscountRate = <%=request.getAttribute("discountRate")%>;
		const TaxRate = <%=request.getAttribute("taxRate")%>;
	</script>
	<script src="../../js/order_reg.js"></script>
</body>
</html>