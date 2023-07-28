$(document).ready(function () {

//    Validation

//    Add
    $(".js-btn-add-customer").click(function () {
        console.log("clicked");

        let custId = $("#customer-id").val();
        let custName = $("#customer-name").val();
        let custAddress = $("#customer-address").val();
        let custSalary = $("#customer-salary").val();


        let customerModel = new CustomerModel(custId, custName, custAddress, custSalary);
        console.log(customerModel)

        $.ajax({
            url: "http://localhost:8080/pos/customer",
            contentType: "application/json",
            data: JSON.stringify(customerModel),
            method: 'POST',
            success: function (resp) {
                if (resp.status === 200) {
                    alert("Customer Added Successfully");
                } else {
                    alert("Customer Added Failed".concat(resp.message));
                }
            },
            error: function (err) {

            }
        });
        // console.log(id,name,address,salary)

    });
//    Update

//    Delete

//    Search

})