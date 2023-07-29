$(document).ready(function () {
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
});
