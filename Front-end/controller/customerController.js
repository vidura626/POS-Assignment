$(document).ready(function (message) {
  //    Validation

  //    Add
  $(".js-btn-add-customer").click(function () {
    console.log("add clicked");

    let custId = $("#customer-id").val();
    let custName = $("#customer-name").val();
    let custAddress = $("#customer-address").val();
    let custSalary = $("#customer-salary").val();

    let customerModel = new CustomerModel(
      custId,
      custName,
      custAddress,
      custSalary
    );
    console.log(customerModel);

    $.ajax({
      url: "http://localhost:8080/Back_end_war/customer",
      contentType: "application/json",
      data: JSON.stringify(customerModel),
      method: "POST",
      success: function (resp) {
        console.log(resp);
        if (resp.code === 200) {
          alert("Customer Added Successfully");
        } else {
          alert("Customer Added Failed : ".concat(resp.message));
        }
      },
      error: function (err) {
        console.log(err);
      },
    });
    // console.log(id,name,address,salary)
  });
  //    Update
  $(".js-btn-update-customer").click(function () {
    console.log("update clicked");

    let custId = $("#customer-id").val();
    let custName = $("#customer-name").val();
    let custAddress = $("#customer-address").val();
    let custSalary = $("#customer-salary").val();

    let customerModel = new CustomerModel(
      custId,
      custName,
      custAddress,
      custSalary
    );
    console.log(customerModel);

    $.ajax({
      url: "http://localhost:8080/Back_end_war/customer",
      contentType: "application/json",
      data: JSON.stringify(customerModel),
      method: "PUT",
      success: function (resp) {
        console.log(resp);
        if (resp.code === 200) {
          alert("Customer Updated Successfully");
        } else {
          alert("Customer Updated Failed : ".concat(resp.message));
        }
      },
      error: function (err) {
        console.log(err);
      },
    });
    // console.log(id,name,address,salary)
  });
  //    Delete
  $(".js-btn-delete-customer").click(function () {
    console.log("delete clicked");
    let custId = $("#customer-id").val();

    $.ajax({
      url: "http://localhost:8080/Back_end_war/customer?custId=".concat(custId),
      contentType: "application/json",
      method: "DELETE",
      success: function (resp) {
        console.log(resp);
        if (resp.code === 200) {
          alert("Customer Deleted Successfully");
        } else {
          alert("Customer Delete Failed : ".concat(resp.message));
        }
      },
      error: function (err) {
        console.log(err);
      },
    });
  });
  //    Search
  $(".js-btn-search-customer").click(function (message) {
    console.log("search clicked");
    let custId = $(".js-input-search-customer").val();

    $.ajax({
      url: "http://localhost:8080/Back_end_war/customer?method=SEARCH&custId=".concat(
        custId
      ),
      contentType: "application/json",
      method: "GET",
      success: function (resp) {
        console.log(resp);
        if (resp.code === 200) {
          // alert("Customer Founded Successfully ", resp.data);

          $("#customer-id").val(resp.data.custID);
          // $("#customer-name").val(name);
          // $("#customer-address").val(address);
          // $("#customer-salary").val(salary);
        } else {
          alert("Customer not founded : ".concat(resp.message));
        }
      },
      error: function (err) {
        console.log(err);
      },
    });
  });
  // Clear event
  $(".js-btn-clear-customer").click(function () {
    clearTable();
  });
  //Clear function
  function clearTable() {
    $("#customer-id").val("");
    $("#customer-name").val("");
    $("#customer-address").val("");
    $("#customer-salary").val("");
  }

  //  Bind table data to inputs with click
  $(".js-tbl-body-customer>tr").click(function () {
    let id = $(this).children().eq(0).text();
    let name = $(this).children().eq(1).text();
    let address = $(this).children().eq(2).text();
    let salary = $(this).children().eq(3).text();

    console.log(id, name, address, salary);
    $("#customer-id").val(id);
    $("#customer-name").val(name);
    $("#customer-address").val(address);
    $("#customer-salary").val(salary);
  });
});
