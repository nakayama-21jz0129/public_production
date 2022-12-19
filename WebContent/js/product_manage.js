/**
 * product_manage.jspファイル独自のjsファイル
 */
(function(){
  "use strict";

  const Category = document.querySelector("#category");
  const CategoryDataList = document.querySelector("#category_datalist");
  const Name = document.querySelector("#name");
  const Price = document.querySelector("#price");
  const UseFlag = document.querySelector("#check_use");
  const PlusMinusButton = document.querySelectorAll(".p_m_buttons");
  const ErrorMsg = document.querySelector("#error_msg");
  const RegButton = document.querySelector("#reg_button");
  let current_page = 1;
  let max_page = 1;
  const ProductTable = document.querySelector("#product_table");
  let productTr = document.querySelectorAll(".product_tr");
  const Page = document.querySelector("#page");
  const ResultPopup = document.querySelector("#result_popup");
  const ProductPopup = document.querySelector("#product_popup");
  const DelPopup = document.querySelector("#del_popup");

  /**
   * 10行を一ページとして表示する。
   */
  function setPage() {
    let page = 0;
    productTr.forEach(element => {
      if (((page + 1) % 2) == 0) {
        element.classList.add("odd");
      }
      else {
        element.classList.remove("odd");
      }

      if ((Math.floor(page / 10) + 1) != current_page) {
        element.classList.add("d_n");
      }
      else {
        element.classList.remove("d_n");
      }
      page++;

    });
    categoryJoin();
    max_page = (Math.floor((page - 1) / 10) + 1);
    Page.querySelector(".msg").innerHTML = current_page + " / " + max_page;
  };

  /**
   * 商品ポップアップを表示可能にする。
   */
  function clickProduct() {
    productTr.forEach(element => {
      element.addEventListener("click", () => {
        ProductPopup.querySelector(".out_id").value = element.querySelector(".product_id").value;
        ProductPopup.querySelector(".out_category").innerHTML = element.querySelector(".product_category").innerHTML;
        ProductPopup.querySelector(".out_name").innerHTML = element.querySelector(".product_name").innerHTML;
        ProductPopup.querySelector(".out_price").innerHTML = element.querySelector(".product_price").innerHTML;
        ProductPopup.querySelector(".out_use").innerHTML = element.querySelector(".use_flag").innerHTML;

        ProductPopup.classList.remove("d_n");
      });
    });
  };

  /**
   * リストを更新する。
   * @param {*} data
   */
  function listNew(data) {
    productTr.forEach(element => {
      element.parentNode.removeChild(element);
    });
    while (CategoryDataList.firstChild) {
      CategoryDataList.removeChild(CategoryDataList.firstChild);
    }

    Object.keys(data["product_map"]).forEach(category_key => {

      let option_element = document.createElement("option");
      option_element.value = data["product_map"][category_key]["name"];
      CategoryDataList.appendChild(option_element);

      Object.keys(data["product_map"][category_key]["product"]).forEach(product_id => {
        let tr_element = document.createElement("tr");
        tr_element.classList.add("product_tr");

        let td0_element = document.createElement("td");
        td0_element.classList.add("d_n");
        let input_element = document.createElement("input");
        input_element.classList.add("product_id");
        input_element.type = "number";
        input_element.value = product_id;
        td0_element.appendChild(input_element);
        tr_element.appendChild(td0_element);

        let td1_element = document.createElement("td");
        td1_element.classList.add("product_category");
        td1_element.innerHTML = data["product_map"][category_key]["name"];
        tr_element.appendChild(td1_element);

        let td2_element = document.createElement("td");
        td2_element.classList.add("product_name");
        td2_element.innerHTML = data["product_map"][category_key]["product"][product_id]["name"];
        tr_element.appendChild(td2_element);

        let td3_element = document.createElement("td");
        td3_element.classList.add("product_price");
        td3_element.innerHTML = data["product_map"][category_key]["product"][product_id]["price"];
        tr_element.appendChild(td3_element);

        let td4_element = document.createElement("td");
        td4_element.classList.add("use_flag");
        if (data["product_map"][category_key]["product"][product_id]["use_flag"]) {
          td4_element.classList.add("t");
          td4_element.innerHTML = "可";
        }
        else {
          td4_element.classList.add("f");
          td4_element.innerHTML = "不可";
        }
        tr_element.appendChild(td4_element);

        ProductTable.appendChild(tr_element);
      });
    });

    productTr = document.querySelectorAll(".product_tr");
    clickProduct();
    current_page = 1;
    setPage();
  };

  /**
   *
   */
  function categoryJoin() {
    productTr.forEach(element => {
      if (element.querySelector(".product_category").classList.contains("border_top")) {
        element.querySelector(".product_category").classList.remove("border_top");
      }
      if (element.querySelector(".product_category").classList.contains("border_bottom")) {
        element.querySelector(".product_category").classList.remove("border_bottom");
      }
    });

    let categoryName = "";
    for (let startIndex = 0, index = 0; index < productTr.length; index++) {
      if (!productTr[index].classList.contains("d_n")) {
        if (categoryName == "") {
          categoryName = productTr[index].querySelector(".product_category").innerHTML;
          startIndex = index;
        }
        else if (categoryName != productTr[index].querySelector(".product_category").innerHTML) {
          if (startIndex == index - 1) {
            categoryName = productTr[index].querySelector(".product_category").innerHTML;
            startIndex = index;
          }
          else {
            for (let i = startIndex, endIndex = index - 1; i <= endIndex; i++) {
              if (i == startIndex) {
                productTr[i].querySelector(".product_category").classList.add("border_bottom");
              }
              else if (i == endIndex) {
                productTr[i].querySelector(".product_category").classList.add("border_top");
              }
              else {
                productTr[i].querySelector(".product_category").classList.add("border_top");
                productTr[i].querySelector(".product_category").classList.add("border_bottom");
              }
            }
            categoryName = productTr[index].querySelector(".product_category").innerHTML;
            startIndex = index;
            index++;
          }
        }
      }
      else if (categoryName != "") {
        if (startIndex == index - 1) {
          categoryName = "";
        }
        else {
          for (let i = startIndex, endIndex = index - 1; i <= endIndex; i++) {
            if (i == startIndex) {
              productTr[i].querySelector(".product_category").classList.add("border_bottom");
            }
            else if (i == endIndex) {
              productTr[i].querySelector(".product_category").classList.add("border_top");
            }
            else {
              productTr[i].querySelector(".product_category").classList.add("border_top");
              productTr[i].querySelector(".product_category").classList.add("border_bottom");
            }
          }
          categoryName = "";
        }
      }

      if (index == productTr.length - 1 && !productTr[index].classList.contains("d_n")) {
        if (startIndex != index) {
          for (let i = startIndex, endIndex = index; i <= endIndex; i++) {
            if (i == startIndex) {
              productTr[i].querySelector(".product_category").classList.add("border_bottom");
            }
            else if (i == endIndex) {
              productTr[i].querySelector(".product_category").classList.add("border_top");
            }
            else {
              productTr[i].querySelector(".product_category").classList.add("border_top");
              productTr[i].querySelector(".product_category").classList.add("border_bottom");
            }
          }
          categoryName = "";
        }
      }

    }
  };

  PlusMinusButton.forEach(element => {
    element.querySelector(".p_b").addEventListener("click", () => {
      element.querySelector(".p_m_input").value = (Number)(element.querySelector(".p_m_input").value) + 10;
    });
    element.querySelector(".m_b").addEventListener("click", () => {
      if (element.querySelector(".p_m_input").value > 9) {
        element.querySelector(".p_m_input").value = (Number)(element.querySelector(".p_m_input").value) - 10;
      }
    });
  });

  RegButton.addEventListener("click", () => {
    RegButton.disabled = true;
    if (Category.value != "" && Name.value != "" && Price.value != "") {
      let json = {
        "product_class_name": Category.value,
        "name": Name.value,
        "price": Price.value,
        "use_flag": UseFlag.checked
      };
      fetch("../api/reg-product", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(json)
      })
      .then(response => response.json())
      .then(data => {
        // console.log(data);
        if (data["result"]) {
          ResultPopup.querySelector(".msg").innerHTML = data["msg"];
          ResultPopup.classList.remove("d_n");

          listNew(data);

          Category.value = "";
          Name.value = "";
          Price.value = 0;
          UseFlag.checked = true;
          ErrorMsg.innerHTML = "";

        }
        else {
          ErrorMsg.innerHTML = data["msg"];
        }
        RegButton.disabled = false;
      });

    }
    else {
      ErrorMsg.innerHTML = "カテゴリ・商品名・金額を入力してください";
      RegButton.disabled = false;
    }
  });

  ResultPopup.querySelector(".g_c_off").addEventListener("click", () => {
    ResultPopup.classList.add("d_n");
  });

  ProductPopup.querySelector(".g_c_off").addEventListener("click", () => {
    ProductPopup.classList.add("d_n");
  });

  ProductPopup.querySelector(".del").addEventListener("click", () => {
    ProductPopup.classList.add("d_n");

    DelPopup.classList.remove("d_n");
  });

  Page.querySelector(".m").addEventListener("click", () => {
    Page.querySelector(".m").disabled = true;
    if (current_page > 1) {
      current_page--;
      setPage();
    }

    Page.querySelector(".m").disabled = false;
  });
  Page.querySelector(".p").addEventListener("click", () => {
    Page.querySelector(".p").disabled = true;
    if (current_page < max_page) {
      current_page++;
      setPage();

    }

    Page.querySelector(".p").disabled = false;
  });

  DelPopup.querySelector(".g_c_off").addEventListener("click", () => {
    DelPopup.classList.add("d_n");

    ProductPopup.classList.remove("d_n");
  });
  DelPopup.querySelector(".no").addEventListener("click", () => {
    DelPopup.classList.add("d_n");

    ProductPopup.classList.remove("d_n");
  });

  DelPopup.querySelector(".yes").addEventListener("click", () => {
    DelPopup.querySelector(".yes").disabled = true;

    let json = {
      "id": ProductPopup.querySelector(".out_id").value
    };
    fetch("../api/del-product", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(json)
    })
    .then(response => response.json())
    .then(data => {
      if (data["result"]) {
        ResultPopup.querySelector(".error_msg").innerHTML = "";
        ResultPopup.querySelector(".msg").innerHTML = data["msg"];
        listNew(data);
      }
      else {
        ResultPopup.querySelector(".msg").innerHTML = "";
        ResultPopup.querySelector(".error_msg").innerHTML = data["msg"];
      }
      DelPopup.classList.add("d_n");
      ResultPopup.classList.remove("d_n");
      DelPopup.querySelector(".yes").disabled = false;
    });
  });

  clickProduct();
  // showList();
  setPage();
})();