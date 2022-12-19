/**
 * order_reg.jspファイル独自のjsファイル
 */
(function(){
  "use strict";

  const MAX_QUANTITY = 50;

  // 顧客
  let customerId = 0;
  const SearchTel = document.querySelector("#search_tel");
  const TelErrorMsg = document.querySelector("#tel_error_msg");
  const SearchButton = document.querySelector("#search_button");
  const CustomerName = document.querySelector("#customer_name");
  const CustomerAddress = document.querySelector("#customer_address");
  const CheckTemp = document.querySelector("#check_temp");
  const TempAddress = document.querySelector("#temp_address");
  const CustomerProduct = document.querySelector("#customer_product");
  const SideProduct = document.querySelector("#s_p");
  const DrinkProduct = document.querySelector("#d_p");
  const CustomerPopup = document.querySelector("#customer_popup");
  // 注文
  const CategorySelect = document.querySelector("#category_select");
  const ProductSelect = document.querySelector("#product_select");
  const QuantityInput = document.querySelector("#quantity_input");
  const PlusMinusButton = document.querySelectorAll(".p_m_buttons");
  const AddErrorMsg = document.querySelector("#add_error_msg");
  const AddButton = document.querySelector("#add_button");
  const TotalPrice = document.querySelector("#total_price");
  const DiscountPrice = document.querySelector("#discount_price");
  const TaxPrice = document.querySelector("#tax_price");
  const BillingPrice = document.querySelector("#billing_price");
  const OrderTable = document.querySelector("#order_table");
  let productTr = document.querySelectorAll(".product_tr");
  const DeleteButton = document.querySelector("#delete_button");


  /**
   *
   * @param {*} i
   */
  function setMenu(i) {
    while (ProductSelect.firstChild) {
      ProductSelect.removeChild(ProductSelect.firstChild);
    }
    Object.keys(ProductObject[i]["product"]).forEach(elementId => {
      let option = document.createElement("option");
      option.value = elementId;
      option.innerHTML = ProductObject[i]["product"][elementId]["name"] + " : " + (String)(ProductObject[i]["product"][elementId]["price"]) + "円";
      ProductSelect.appendChild(option);
    });
  };

  function price_calculation() {
    let tempPrice = 0;
    productTr.forEach(element => {
      tempPrice += (Number)(element.querySelector(".sub_total").innerHTML);
    });
    TotalPrice.innerHTML = tempPrice;
    DiscountPrice.innerHTML = Math.floor(tempPrice * DiscountRate);
    tempPrice -= Math.floor(tempPrice * DiscountRate);
    TaxPrice.innerHTML = Math.floor(tempPrice * TaxRate);
    tempPrice += Math.floor(tempPrice * TaxRate);
    BillingPrice.innerHTML = tempPrice;
  };

  SearchButton.addEventListener("click", () => {
    SearchButton.disabled = true;
    let tempTel = SearchTel.value;
    if (SearchTel.value != "") {
      fetch("../api/search-customer?tel=" + tempTel, {
        method: "GET",
        headers: {
          "Content-Type": "application/json"
        }
      })
      .then(response => response.json())
      .then(data => {
        // console.log(data);
        if (data["result"]) {
          customerId = data["customer"]["id"];
          CustomerName.querySelector(".out1").innerHTML = data["customer"]["name"];
          CustomerName.classList.remove("d_n");
          CustomerAddress.querySelector(".out1").innerHTML = data["customer"]["address"];
          CustomerAddress.classList.remove("d_n");
          if (data["customer"]["product1"] != "") {
            SideProduct.innerHTML = data["customer"]["product1"];
          }
          else {
            SideProduct.innerHTML = "なし";
          }
          if (data["customer"]["product2"] != "") {
            DrinkProduct.innerHTML = data["customer"]["product2"];
          }
          else {
            DrinkProduct.innerHTML = "なし";
          }
          CustomerProduct.classList.remove("d_n");
          CheckTemp.checked = false;
          TempAddress.classList.add("d_n");
        }
        else {
          customerId = 0;
          CustomerName.classList.add("d_n");
          CustomerAddress.classList.add("d_n");
          CustomerProduct.classList.add("d_n");

          CustomerPopup.querySelector(".out1").innerHTML = tempTel;
          CustomerPopup.classList.remove("d_n");
        }

        TelErrorMsg.innerHTML = "";
        SearchButton.disabled = false;
      });
    }
    else {
      customerId = 0;
      CustomerName.classList.add("d_n");
      CustomerAddress.classList.add("d_n");
      CustomerProduct.classList.add("d_n");
      TelErrorMsg.innerHTML = "電話番号を入力してください";
      SearchButton.disabled = false;
    }
  });

  CheckTemp.addEventListener("click", () => {
    if (CheckTemp.checked) {
      TempAddress.classList.remove("d_n");
    }
    else {
      TempAddress.classList.add("d_n");
    }
  });

  CategorySelect.addEventListener("change", () => {
    setMenu(CategorySelect.value);
  });

  AddButton.addEventListener("click", () => {
    let errorTxet = "";
    if (QuantityInput.value > 0) {
      let bool = true;
      productTr.forEach(element => {
        if (element.querySelector(".product_id").value == ProductSelect.value) {
          bool = false;
          if ((Number)(element.querySelector(".quantity").value) + (Number)(QuantityInput.value) <= MAX_QUANTITY) {
            element.querySelector(".quantity").value = (Number)(element.querySelector(".quantity").value) + (Number)(QuantityInput.value);
            element.querySelector(".sub_total").innerHTML = ProductObject[CategorySelect.value]["product"][ProductSelect.value]["price"] * element.querySelector(".quantity").value;

            price_calculation();
          }
          else {
            errorTxet = "一つの商品の注文上限数は" + (String)(MAX_QUANTITY) + "個です";
          }
        }
      });

      if (bool) {
        let tr_element = document.createElement("tr");
        tr_element.classList.add("product_tr");

        let td0_element = document.createElement("td");
        td0_element.classList.add("d_n");
        let input_n_element = document.createElement("input");
        input_n_element.classList.add("product_id");
        input_n_element.type = "number";
        input_n_element.value = ProductSelect.value;
        td0_element.appendChild(input_n_element);
        tr_element.appendChild(td0_element);

        let td1_element = document.createElement("td");
        let input_c_element = document.createElement("input");
        input_c_element.classList.add("x_c");
        input_c_element.type = "checkbox";
        td1_element.appendChild(input_c_element);
        tr_element.appendChild(td1_element);

        let td2_element = document.createElement("td");
        td2_element.innerHTML = ProductObject[CategorySelect.value]["name"];
        tr_element.appendChild(td2_element);

        let td3_element = document.createElement("td");
        td3_element.innerHTML = ProductObject[CategorySelect.value]["product"][ProductSelect.value]["name"];
        tr_element.appendChild(td3_element);

        let td4_element = document.createElement("td");
        td4_element.classList.add("price");
        td4_element.classList.add("num_r");
        td4_element.innerHTML = ProductObject[CategorySelect.value]["product"][ProductSelect.value]["price"];
        tr_element.appendChild(td4_element);

        let td5_element = document.createElement("td");
        td5_element.classList.add("p_m_buttons");
        td5_element.classList.add("text_c");
        let input_n2_element = document.createElement("input");
        input_n2_element.classList.add("quantity");
        input_n2_element.classList.add("p_m_input");
        input_n2_element.classList.add("in1");
        input_n2_element.classList.add("w40");
        input_n2_element.classList.add("num_r");
        input_n2_element.type = "number";
        input_n2_element.min = 1;
        input_n2_element.max = MAX_QUANTITY;
        input_n2_element.value = (Number)(QuantityInput.value);
        td5_element.appendChild(input_n2_element);
        let button_p_element = document.createElement("button");
        button_p_element.type = "button";
        button_p_element.classList.add("button4");
        button_p_element.classList.add("p_b");
        button_p_element.classList.add("x4");
        button_p_element.innerHTML = "+";
        td5_element.appendChild(button_p_element);
        let button_m_element = document.createElement("button");
        button_m_element.type = "button";
        button_m_element.classList.add("button4");
        button_m_element.classList.add("m_b");
        button_m_element.innerHTML = "-";
        td5_element.appendChild(button_m_element);
        tr_element.appendChild(td5_element);

        let td6_element = document.createElement("td");
        td6_element.classList.add("sub_total");
        td6_element.classList.add("num_r");
        td6_element.innerHTML = ProductObject[CategorySelect.value]["product"][ProductSelect.value]["price"] * QuantityInput.value;
        tr_element.appendChild(td6_element);

        td5_element.querySelector(".p_b").addEventListener("click", () => {
          if (td5_element.querySelector(".p_m_input").value < MAX_QUANTITY) {
            td5_element.querySelector(".p_m_input").value = (Number)(td5_element.querySelector(".p_m_input").value) + 1;
            td6_element.innerHTML = ProductObject[CategorySelect.value]["product"][ProductSelect.value]["price"] * td5_element.querySelector(".quantity").value;
            price_calculation();
          }
        });
        td5_element.querySelector(".m_b").addEventListener("click", () => {
          if (td5_element.querySelector(".p_m_input").value > 1) {
            td5_element.querySelector(".p_m_input").value = (Number)(td5_element.querySelector(".p_m_input").value) - 1;
            td6_element.innerHTML = ProductObject[CategorySelect.value]["product"][ProductSelect.value]["price"] * td5_element.querySelector(".quantity").value;
            price_calculation();
          }
        });
        td5_element.querySelector(".quantity").addEventListener("change", () => {
          if (td5_element.querySelector(".quantity").value > MAX_QUANTITY) {
            td5_element.querySelector(".quantity").value = MAX_QUANTITY;
          }
          else if (td5_element.querySelector(".quantity").value < 1) {
            td5_element.querySelector(".quantity").value = 1;
          }
          td6_element.innerHTML = ProductObject[CategorySelect.value]["product"][ProductSelect.value]["price"] * td5_element.querySelector(".quantity").value;
          price_calculation();
        });

        OrderTable.appendChild(tr_element);
        productTr = document.querySelectorAll(".product_tr");

        price_calculation();
      }
    }
    else {
      errorTxet = "数量を１以上にしてください";
    }
    QuantityInput.value = 1;
    AddErrorMsg.innerHTML = errorTxet;
  });

  DeleteButton.addEventListener("click", () => {
    productTr.forEach(element => {
      if (element.querySelector("input[type=checkbox]").checked) {
        OrderTable.removeChild(element);
      }
    });
    productTr = document.querySelectorAll(".product_tr");
    price_calculation();
  });

  PlusMinusButton.forEach(element => {
    element.querySelector(".p_b").addEventListener("click", () => {
      if (element.querySelector(".p_m_input").value < MAX_QUANTITY) {
        element.querySelector(".p_m_input").value = (Number)(element.querySelector(".p_m_input").value) + 1;
      }
    });
    element.querySelector(".m_b").addEventListener("click", () => {
      if (element.querySelector(".p_m_input").value > 1) {
        element.querySelector(".p_m_input").value = (Number)(element.querySelector(".p_m_input").value) - 1;
      }
    });
  });

  CustomerPopup.querySelector(".g_c_off").addEventListener("click", () => {
    CustomerPopup.classList.add("d_n");
  });
  CustomerPopup.querySelector(".no").addEventListener("click", () => {
    CustomerPopup.classList.add("d_n");
  });

  setMenu(CategorySelect.value);
})();