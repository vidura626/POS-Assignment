$('#btnSearchOrder').click(function () {

    for (var search of orders) {

        let searchOrder = $('#searchOrder').val();
        let chooseOrderType = $('#chooseOrderType').val();
        if (chooseOrderType === "ID") {
            console.log("ID : " + searchOrder + "===" + search.orId)

            if (searchOrder === search.orId) {
                $('#orderIdDash').val(search.orId);
                $('#OrderDateDash').val(search.orDate);
                $('#customerNameDash').val(search.orCusName);
                $('#discountDash').val(search.orDis);
                $('#subTotDash').val(search.orSubTotal);

            }
        } else if (chooseOrderType === "1") {
            console.log("1 : " + searchOrder + "===" + search.cusName)
            if (searchOrder === search.orCusName) {
                $('#orderIdDash').val(search.orId);
                $('#OrderDateDash').val(search.orDate);
                $('#customerNameDash').val(search.orCusName);
                $('#discountDash').val(search.orDis);
                $('#subTotDash').val(search.orSubTotal);
            }
        } else if (chooseOrderType === "2") {
            console.log("2 : " + searchOrder + "===" + search.ordDate)

            if (searchOrder === search.orDate) {
                $('#orderIdDash').val(search.orId);
                $('#OrderDateDash').val(search.orDate);
                $('#customerNameDash').val(search.orCusName);
                $('#discountDash').val(search.orDis);
                $('#subTotDash').val(search.orSubTotal);
            }
        }

    }
});

$('#btnClearOrd').click(function () {
    $('#orderIdDash').val("");
    $('#OrderDateDash').val("");
    $('#customerNameDash').val("");
    $('#discountDash').val("");
    $('#subTotDash').val("");
    $('#searchOrder').val("");
});

$('#btnDeleteOrd').click(function () {
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            let deleteOrderId = $('#orderIdDash').val();
            console.log($("#orderIdDash"));
            if (deleteOrder(deleteOrderId)) {
                setOrderTextfieldValues("", "", "", "", "");
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Your file has been deleted.!',
                    showConfirmButton: false,
                    timer: 1500
                });

            } else {
                Swal.fire({
                    position: 'top-end',
                    icon: 'warning',
                    title: 'No such Order to delete. please check the id !',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        }
    })

});


/*FUNCTIONS*/

function searchOrder(orderId) {
    for (let i of orders) {
        if (i.orId === orderId) {
            return i;
        }
    }
    return null;
}

function deleteOrderRequest(orderId) {
    $.ajax({
        url: "http://localhost:8080/Back_end_war/placeorder?orderId=".concat(orderId),
        method: "DELETE",
        success:function (resp) {
            console.log(resp);
            let indexNumber = orders.indexOf(orderId);
            orders.splice(indexNumber, 1);
            loadAllOrder();
        },
        error:function (err) {

        }
    });
}

function deleteOrder(orderId) {
    let ordObj = searchOrder(orderId);

    if (ordObj != null) {
        deleteOrderRequest(orderId);
        return true;
    } else {
        return false;
    }
}

function setOrderTextfieldValues(orderId, date, name, dis, cost) {

    $('#orderIdDash').val(orderId);
    $('#OrderDateDash').val(date);
    $('#customerNameDash').val(name);
    $('#discountDash').val(dis);
    $('#subTotDash').val(cost);
}