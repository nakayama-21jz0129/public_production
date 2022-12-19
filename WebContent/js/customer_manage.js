/**
 *
 */
(function(){
    "use strict";

    const Tel = document.querySelector("#tel");
    const Name = document.querySelector("#name");
    const Address = document.querySelector("#address");
    const ErrorMsg = document.querySelector("#error_msg");
    const RegButton = document.querySelector("#reg_button");
    let current_page = 1;
    let max_page = 1;
    let word = "";
    const Parameter = document.querySelector("#parameter");
    const SelectButton = document.querySelector("#select_button");
    const CustomerTable = document.querySelector("#customer_table");
    let customerTr = document.querySelectorAll(".customer_tr");
    const Page = document.querySelector("#page");
    const ResultPopup = document.querySelector("#result_popup");
    const CustomerPopup = document.querySelector("#customer_popup");
    const DelPopup = document.querySelector("#del_popup");

    /**
   * リストを更新する。
   * @param {*} data
   */
  function listNew(data) {
    customerTr.forEach(element => {
      element.parentNode.removeChild(element);
    });

    data["customer_list"].forEach(customer => {
      let tr_element = document.createElement("tr");
      tr_element.classList.add("customer_tr");

      let td0_element = document.createElement("td");
      td0_element.classList.add("d_n");
      let input_element = document.createElement("input");
      input_element.classList.add("customer_id");
      input_element.type = "number";
      input_element.value = customer["id"];
      td0_element.appendChild(input_element);
      tr_element.appendChild(td0_element);

      let td1_element = document.createElement("td");
      td1_element.classList.add("customer_tel");
      td1_element.innerHTML = customer["tel"];
      tr_element.appendChild(td1_element);

      let td2_element = document.createElement("td");
      td2_element.classList.add("customer_name");
      td2_element.innerHTML = customer["name"];
      tr_element.appendChild(td2_element);

      let td3_element = document.createElement("td");
      td3_element.classList.add("customer_address");
      td3_element.innerHTML = customer["address"];
      tr_element.appendChild(td3_element);

      CustomerTable.appendChild(tr_element);
    });

    customerTr = document.querySelectorAll(".customer_tr");
    clickCustomer();
    current_page = 1;
    if (word != "") {
      selctStr();
    }
    else {
      customerTr.forEach(element => {
        element.classList.remove("d_n");
      });
    }
    setPage();
  };

  /**
   * 顧客ポップアップを表示可能にする。
   */
   function clickCustomer() {
    customerTr.forEach(element => {
      element.addEventListener("click", () => {
        CustomerPopup.querySelector(".out_id").value = element.querySelector(".customer_id").value;
        CustomerPopup.querySelector(".out_name").innerHTML = element.querySelector(".customer_name").innerHTML;
        CustomerPopup.querySelector(".out_tel").innerHTML = element.querySelector(".customer_tel").innerHTML;
        CustomerPopup.querySelector(".out_address").innerHTML = element.querySelector(".customer_address").innerHTML;

        CustomerPopup.classList.remove("d_n");
      });
    });
  };

  /**
   * 顧客の電話番号・名前の検索を行う。
   * 部分一致も検索対象とする。
   */
   function selctStr() {
    customerTr.forEach(element => {
      element.classList.remove("d_n");
      if (!(element.querySelector(".customer_name").innerHTML.includes(word) ||
            element.querySelector(".customer_tel").innerHTML.includes(word))) {
        element.classList.add("d_n");
      }
    });
  };

  /**
   * 10行を一ページとして表示する。
   */
   function setPage() {
    let page = 0;
    customerTr.forEach(element => {
      if (!element.classList.contains("d_n")) {
        if (((page + 1) % 2) == 0) {
          element.classList.add("odd");
        }
        else {
          element.classList.remove("odd");
        }

        if ((Math.floor(page / 10) + 1) != current_page) {
          element.classList.add("d_n");
        }
        page++;

      }
    });
    max_page = (Math.floor((page - 1) / 10) + 1);
    Page.querySelector(".msg").innerHTML = current_page + " / " + max_page;
  };


  RegButton.addEventListener("click", () => {
    RegButton.disabled = true;
    if (Tel.value != "" && Name.value != "" && Address.value != "") {
      let json = {
        "tel": Tel.value,
        "name": Name.value,
        "address": Address.value
      };
      fetch("../api/reg-customer", {
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

          Tel.value = "";
          Name.value = "";
          Address.value = "";
          ErrorMsg.innerHTML = "";
        }
        else {
          ErrorMsg.innerHTML = data["msg"];
        }
        RegButton.disabled = false;
      });
    }
    else {
      ErrorMsg.innerHTML = "電話番号・名前・住所を入力してください";
      RegButton.disabled = false;
    }
  });

  ResultPopup.querySelector(".g_c_off").addEventListener("click", () => {
    ResultPopup.classList.add("d_n");
  });

  CustomerPopup.querySelector(".g_c_off").addEventListener("click", () => {
    CustomerPopup.classList.add("d_n");
  });

  CustomerPopup.querySelector(".del").addEventListener("click", () => {
    CustomerPopup.classList.add("d_n");

    DelPopup.classList.remove("d_n");
  });

  DelPopup.querySelector(".g_c_off").addEventListener("click", () => {
    DelPopup.classList.add("d_n");

    CustomerPopup.classList.remove("d_n");
  });
  DelPopup.querySelector(".no").addEventListener("click", () => {
    DelPopup.classList.add("d_n");

    CustomerPopup.classList.remove("d_n");
  });

  SelectButton.addEventListener("click", () => {
    current_page = 1;
    if (Parameter.value != null) {
      word = Parameter.value
      selctStr();
    }
    else {
      word = "";
    }
    setPage();
  });

  DelPopup.querySelector(".yes").addEventListener("click", () => {
    DelPopup.querySelector(".yes").disabled = true;

    let json = {
      "id": CustomerPopup.querySelector(".out_id").value
    };
    fetch("../api/del-customer", {
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

  Page.querySelector(".m").addEventListener("click", () => {
    Page.querySelector(".m").disabled = true;
    if (current_page > 1) {
      current_page--;
      if (word != "") {
        selctStr();
      }
      else {
        customerTr.forEach(element => {
          element.classList.remove("d_n");
        });
      }
      setPage();
    }

    Page.querySelector(".m").disabled = false;
  });
  Page.querySelector(".p").addEventListener("click", () => {
    Page.querySelector(".p").disabled = true;
    if (current_page < max_page) {
      current_page++;
      if (word != "") {
        selctStr();
      }
      else {
        customerTr.forEach(element => {
          element.classList.remove("d_n");
        });
      }
      setPage();
    }

    Page.querySelector(".p").disabled = false;
  });

  clickCustomer();
  setPage();
})();