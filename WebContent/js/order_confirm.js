/**
 * order_confirm.jspファイル独自のjsファイル
 */
(function(){
  "use strict";

  const OrderTr = document.querySelectorAll(".order_tr");
  const Page = document.querySelector("#page");
  let current_page = 1;
  let max_page = 1;

  /**
   * 20行を一ページとして表示する。
   */
   function setPage() {
    let page = 0;
    OrderTr.forEach(element => {
      if (!element.classList.contains("d_n")) {
        if (((page + 1) % 2) == 0) {
          element.classList.add("odd");
        }
        else {
          element.classList.remove("odd");
        }

        if ((Math.floor(page / 20) + 1) != current_page) {
          element.classList.add("d_n");
        }
        page++;

      }
    });
    max_page = (Math.floor((page - 1) / 20) + 1);
    Page.querySelector(".msg").innerHTML = current_page + " / " + max_page;
  };


  setPage()
})();