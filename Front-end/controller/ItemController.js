$(document).ready(function (message) {
    getAllItems();
    //Regex
    let code = /^(I00-)([(0-9)]{3,3})$/;
    let nameRegex = /^[\w'\-,.][^0-9_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~<>;:[\]]{2,}$/;
    let qtyRegex = /[A-Za-z0-9'\.\-\s\,]/;
    let unitPriceRegex = /^\d{1,6}(?:\.\d{0,2})?$/;

    //    Validation
    $("#item-code").keyup(function () {
        checkInputs(code, $(this));
    });
    $("#item-name").keyup(function () {
        checkInputs(nameRegex, $(this));
    });
    $("#item-qty").keyup(function () {
        checkInputs(qtyRegex, $(this));
    });
    $("#item-unit-price").keyup(function () {
        checkInputs(unitPriceRegex, $(this));
    });

    $(".js-input-search-item").keyup(function () {
        checkInputs(code, $(this));
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
    $(".js-btn-add-item").click(function () {
        console.log("add clicked");

        let itemCode = $("#item-code").val();
        let itemName = $("#item-name").val();
        let itemQty = $("#item-qty").val();
        let itemUnitPrice = $("#item-unit-price").val();

        let itemModel = new ItemModel(
            itemCode,
            itemName,
            itemQty,
            itemUnitPrice
        );
        console.log(itemModel);

        $.ajax({
            url: "http://localhost:8080/Back_end_war/item",
            contentType: "application/json",
            data: JSON.stringify(itemModel),
            method: "POST",
            success: function (resp) {
                console.log(resp);
                if (resp.code === 200) {
                    Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Item Added Successfully',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    $(".js-tbl-body-customer").append
                    (`<tr><td>${itemCode}</td></td><td>${itemName}</td><td>${itemQty}</td><td>${itemUnitPrice}</tr>`);
                    clearForm();
                    removeValidationClasses();
                } else {
                    Swal.fire({
                        position: 'top-end',
                        icon: 'warning',
                        title: resp.message,
                        showConfirmButton: false,
                        timer: 1500
                    })
                }
            },
            error: function (err) {
                console.log(err);
            },
        });
        // console.log(id,name,address,salary)
    });
//    Update
    $(".js-btn-update-item").click(function () {
        console.log("update clicked");

        let itemCode = $("#item-code").val();
        let itemName = $("#item-name").val();
        let itemQty = $("#item-qty").val();
        let itemUnitPrice = $("#item-unit-price").val();

        let itemModel = new ItemModel(
            itemCode,
            itemName,
            itemQty,
            itemUnitPrice
        );
        console.log(itemModel);

        $.ajax({
            url: "http://localhost:8080/Back_end_war/item",
            contentType: "application/json",
            data: JSON.stringify(itemModel),
            method: "PUT",
            success: function (resp) {
                console.log(resp);
                if (resp.code === 200) {
                    alert("Item Updated Successfully");
                    clearForm();
                    removeValidationClasses();
                } else {
                    alert("Item Updated Failed : ".concat(resp.message));
                }
            },
            error: function (err) {
                console.log(err);
            },
        });
        // console.log(id,name,address,salary)
    });
//    Delete
    $(".js-btn-delete-item").click(function () {
        deleteItem();
    });

//    Delete function
    function deleteItem() {
        let itemCode = $("#item-code").val();
        $.ajax({
            url: "http://localhost:8080/Back_end_war/item?itemCode=".concat(itemCode),
            contentType: "application/json",
            method: "DELETE",
            success: function (resp) {
                console.log(resp);
                if (resp.code === 200) {
                    alert("Item Deleted Successfully");
                    clearForm();
                    removeValidationClasses();
                    getAllItems();
                } else {
                    alert("Item Delete Failed : ".concat(resp.message));
                }
            },
            error: function (err) {
                console.log(err);
            },
        });
    }

//    Search
    $(".js-btn-search-item").click(function () {
        console.log("search clicked");
        let itemCode = $(".js-input-search-item").val();

        $.ajax({
            url: "http://localhost:8080/Back_end_war/item?method=SEARCH&itemCode=".concat(
                itemCode
            ),
            contentType: "application/json",
            method: "GET",
            success: function (resp) {
                console.log(resp);
                if (resp.code === 200) {
                    // alert("Customer Founded Successfully ", resp.data);

                    $("#item-code").val(resp.data.itemCode);
                    $("#item-name").val(resp.data.itemName);
                    $("#item-qty").val(resp.data.itemQty);
                    $("#item-unit-price").val(resp.data.itemUnitPrice);
                    $("#item-code").prop('disabled', true);

                } else {
                    alert("Item not founded : ".concat(resp.message));
                }
            },
            error: function (err) {
                console.log(err);
            },
        });
    });
//   GetALl
    $(".js-btn-all-item").click(getAllItems);

//   GetALl function
    function getAllItems() {
        console.log("Get all function");
        $.ajax({
            url: "http://localhost:8080/Back_end_war/item?method=GETALL",
            contentType: "application/json",
            method: "GET",
            success: function (resp) {
                console.log(resp);
                if (resp.code === 200) {
                    // alert("Customer Founded Successfully ", resp.data);
                    $(".js-tbl-body-item").empty();
                    for (let i = 0; i < resp.data.length; i++) {
                        $(".js-tbl-body-item").append
                        (`<tr><td>${resp.data[i].itemCode}</td><td>${resp.data[i].itemName}</td><td>${resp.data[i].itemQty}</td><td>${resp.data[i].itemUnitPrice}</td></tr>`);
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

    $(".js-input-search-item").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $(".js-tbl-body-item > tr").each(function () {
            var customerName = $(this).find('td:nth-child(2)').text().toLowerCase();
            var isVisible = customerName.indexOf(value) > -1;
            $(this).toggle(isVisible);
        });
    });


// Clear event
    $(".js-btn-clear-item").click(function () {
        clearForm();
        removeValidationClasses();
    });

//Form input clear function
    function clearForm() {
        $("#item-code").prop('disabled', false);
        $("#item-code").val("");
        $("#item-name").val("");
        $("#item-qty").val("");
        $("#item-unit-price").val("");
        $(".js-input-search-item").val("");
        getAllItems();
    }

//Remove validation classes
    function removeValidationClasses() {
        $("#item-code").removeClass("is-valid is-invalid");
        $("#item-name").removeClass("is-valid is-invalid");
        $("#item-qty").removeClass("is-valid is-invalid");
        $("#item-unit-price").removeClass("is-valid is-invalid");
        $(".js-input-search-item").removeClass("is-valid is-invalid");
    }

//  Bind table data to inputs with click
    function bindDataTbl() {
        $(".js-tbl-body-item>tr").click(function () {
            let itemCode = $(this).children().eq(0).text();
            let itemName = $(this).children().eq(1).text();
            let itemQty = $(this).children().eq(2).text();
            let itemUnitPrice = $(this).children().eq(3).text();

            console.log(itemCode, itemName, itemQty, itemUnitPrice);
            $("#item-code").prop("disabled", true);
            $("#item-code").val(itemCode);
            $("#item-name").val(itemName);
            $("#item-qty").val(itemQty);
            $("#item-unit-price").val(itemUnitPrice);
        });
        $(".js-tbl-body-customer>tr").dblclick(function () {
            let itemCode = $(this).children().eq(0).text();
            let itemName = $(this).children().eq(1).text();
            let itemQty = $(this).children().eq(2).text();
            let itemUnitPrice = $(this).children().eq(3).text();

            console.log(itemCode, itemName, itemQty, itemUnitPrice);
            $("#item-code").prop("disabled", true);
            $("#item-code").val(itemCode);
            $("#item-name").val(itemName);
            $("#item-qty").val(itemQty);
            $("#item-unit-price").val(itemUnitPrice);
        });
    }

//  Delete customer with double click
    function removeRow() {
        $('.js-tbl-body-item>tr').dblclick(function () {
            deleteItem();
            $(this).remove();
        });
    }


    // Function to handle the search


});
