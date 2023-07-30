$(document).ready(function (message) {
    getAllCustomers();
    //Regex
    let idRegex = /^(C00-)([(0-9)]{3,3})$/;
    let nameRegex = /^[\w'\-,.][^0-9_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~<>;:[\]]{2,}$/;
    let addressRegex = /[A-Za-z0-9'\.\-\s\,]/;
    let salaryRegex = /^\d{1,6}(?:\.\d{0,2})?$/;

    //    Validation
    $("#customer-id").keyup(function () {
        checkInputs(idRegex, $(this));
    });
    $("#customer-name").keyup(function () {
        checkInputs(nameRegex, $(this));
    });
    $("#customer-address").keyup(function () {
        checkInputs(addressRegex, $(this));
    });
    $("#customer-salary").keyup(function () {
        checkInputs(salaryRegex, $(this));
    });

    $(".js-input-search-customer").keyup(function () {
        checkInputs(idRegex, $(this));
    });

    function checkInputs(regexPatterm, input) {
        console.log(input);
        let regex = regexPatterm;
        let b = regex.test(input.val());
        console.log(b)
        if (b) {
            input.removeClass("is-invalid");
            input.addClass("is-valid");
        } else {
            input.removeClass("is-invalid");
            input.addClass("is-invalid");
        }
    }

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
                    $(".js-tbl-body-customer").append
                    (`<tr><td>${custId}</td></td><td>${custName}</td><td>${custAddress}</td><td>${custSalary}</tr>`);
                    clearForm();
                    removeValidationClasses();
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
                    clearForm();
                    removeValidationClasses();
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
        deleteCustomer();
    });

//    Delete function
    function deleteCustomer() {
        let custId = $("#customer-id").val();
        $.ajax({
            url: "http://localhost:8080/Back_end_war/customer?custId=".concat(custId),
            contentType: "application/json",
            method: "DELETE",
            success: function (resp) {
                console.log(resp);
                if (resp.code === 200) {
                    alert("Customer Deleted Successfully");
                    clearForm();
                    removeValidationClasses();
                    getAllCustomers();
                } else {
                    alert("Customer Delete Failed : ".concat(resp.message));
                }
            },
            error: function (err) {
                console.log(err);
            },
        });
    }

//    Search
    $(".js-btn-search-customer").click(function () {
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
                    $("#customer-name").val(resp.data.custName);
                    $("#customer-address").val(resp.data.custAddress);
                    $("#customer-salary").val(resp.data.custSalary);

                    $("#customer-id").prop('disabled', true);

                } else {
                    alert("Customer not founded : ".concat(resp.message));
                }
            },
            error: function (err) {
                console.log(err);
            },
        });
    });
//   GetALl
    $(".js-btn-all-customer").click(getAllCustomers);

//   GetALl function
    function getAllCustomers() {
        console.log("Get all function");
        $.ajax({
            url: "http://localhost:8080/Back_end_war/customer?method=GETALL",
            contentType: "application/json",
            method: "GET",
            success: function (resp) {
                console.log(resp);
                if (resp.code === 200) {
                    // alert("Customer Founded Successfully ", resp.data);
                    $(".js-tbl-body-customer").empty();
                    for (let i = 0; i < resp.data.length; i++) {
                        $(".js-tbl-body-customer").append
                        (`<tr><td>${resp.data[i].custID}</td><td>${resp.data[i].custName}</td><td>${resp.data[i].custAddress}</td><td>${resp.data[i].custSalary}</td></tr>`);
                    }
                    bindDataTbl();
                    removeRow();
                } else {
                    alert("Customer not founded : ".concat(resp.message));
                }
            },
            error: function (err) {
                console.log(err);
            },
        });
    }

    $(".js-input-search-customer").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $(".js-tbl-body-customer > tr").each(function () {
            var customerName = $(this).find('td:first').text().toLowerCase();
            var isVisible = customerName.indexOf(value) > -1;
            $(this).toggle(isVisible);
        });
    });


// Clear event
    $(".js-btn-clear-customer").click(function () {
        clearForm();
        removeValidationClasses();
    });

//Form input clear function
    function clearForm() {
        $("#customer-id").prop('disabled', false);
        $("#customer-id").val("");
        $("#customer-name").val("");
        $("#customer-address").val("");
        $("#customer-salary").val("");
        $(".js-input-search-customer").val("");
        getAllCustomers();
    }

//Remove validation classes
    function removeValidationClasses() {
        $("#customer-id").removeClass("is-valid is-invalid");
        $("#customer-name").removeClass("is-valid is-invalid");
        $("#customer-address").removeClass("is-valid is-invalid");
        $("#customer-salary").removeClass("is-valid is-invalid");
        $(".js-input-search-customer").removeClass("is-valid is-invalid");
    }

//  Bind table data to inputs with click
    function bindDataTbl() {
        $(".js-tbl-body-customer>tr").click(function () {
            let id = $(this).children().eq(0).text();
            let name = $(this).children().eq(1).text();
            let address = $(this).children().eq(2).text();
            let salary = $(this).children().eq(3).text();

            console.log(id, name, address, salary);
            $("#customer-id").prop("disabled", true);
            $("#customer-id").val(id);
            $("#customer-name").val(name);
            $("#customer-address").val(address);
            $("#customer-salary").val(salary);
        });
        $(".js-tbl-body-customer>tr").dblclick(function () {
            let id = $(this).children().eq(0).text();
            let name = $(this).children().eq(1).text();
            let address = $(this).children().eq(2).text();
            let salary = $(this).children().eq(3).text();

            console.log(id, name, address, salary);
            $("#customer-id").prop("disabled", true);
            $("#customer-id").val(id);
            $("#customer-name").val(name);
            $("#customer-address").val(address);
            $("#customer-salary").val(salary);
        });
    }

//  Delete customer with double click
    function removeRow() {
        $('.js-tbl-body-customer>tr').dblclick(function () {
            deleteCustomer();
            $(this).remove();
        });
    }


    // Function to handle the search



});
