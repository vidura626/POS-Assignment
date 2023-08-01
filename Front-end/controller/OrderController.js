var tempOrderCartAr = [];
var orders = [];

getAllOrders();

function loadAllCustomerId() {
    $('#customerIdOrd').empty();
    for (let customerArElement of customerAr) {
        $('#customerIdOrd').append(`<option>${customerArElement.custID}</option>`);
    }
}

function loadAllItemId() {
    $('#itemIdOrd').empty();
    for (let itemArElement of itemAr) {
        $('#itemIdOrd').append(`<option>${itemArElement.itemCode}</option>`);
    }
}

const searchCustomer = (custIdToFind) => customerAr.find(customer => customer.custID === custIdToFind);

/*Listener fir the Customer Combo*/
$('#customerIdOrd').on('change', function () {
    console.log("Clicked")
    /*get Customer*/
    let customer = searchCustomer($('#customerIdOrd').val());
    console.log(customerAr);
    console.log(customer);

    $('#customerNameOrd').val(customer.custName);
    $('#salaryOrd').val(customer.custSalary);
    $('#addressOrd').val(customer.custAddress);


});

const searchItem = (itemCodeToFind) => itemAr.find((item) => item.itemCode === itemCodeToFind);

/*Listener fir the Item Combo*/
$('#itemIdOrd').on('change', function () {
    console.log($('#itemIdOrd').val());

    let item = searchItem($('#itemIdOrd').val());

    $('#item').val(item.itemName);
    $('#priceOrd').val(item.itemUnitPrice);
    $('#qtyOnHandOrd').val(item.qtyOnHand);

});


$('#btnAddToCart').click(function () {

    let itemCode = $('#itemIdOrd').val();
    let itmName = $('#item').val();
    let itmPrice = $('#priceOrd').val();
    let itemOrderQty = $('#orderQty').val();

    let total = itmPrice * itemOrderQty;


    let rowExists = searchRowExists(itemCode);
    if (rowExists != null) {
        let newQty = ((parseInt(rowExists.orItemQTY)) + (parseInt(itemOrderQty)));

        // rowExists.orItemQTY.val(newQty);
        rowExists.orItemQTY = newQty;
        rowExists.orItemTotal = parseFloat(itmPrice) * newQty;
        addCartData();

    } else {
        new TempCartModal()
        let tempCartModal = new TempCartModal(itemCode, itmName, itmPrice, itemOrderQty, total);
        console.log(tempCartModal);
        tempOrderCartAr.push(tempCartModal);
        addCartData();
    }

    minQty(itemCode, itemOrderQty);

})

/*Add Table*/
function addCartData() {
    $("#tblCart> tr").detach();

    for (var tc of tempOrderCartAr) {
        var row = "<tr><td>" + tc.orItemCOde + "</td><td>" + tc.orItemName + "</td><td>" + tc.orItemPrice + "</td><td>" + tc.orItemQTY + "</td><td>" + tc.orItemTotal + "</td></tr>";
        $('#tblCart').append(row);
    }
    bindDataTbl();
    getTotal();
}

function getTotal() {
    let tempTot = 0;
    for (let tempOrderCartArElement of tempOrderCartAr) {
        tempTot = tempTot + tempOrderCartArElement.orItemTotal;
    }
    $('#total').val(tempTot);

}

/*discount*/
let disTOGave = 0;
$('#discount').on('keyup', function () {
    let dis = $('#discount').val();
    let tot = $('#total').val();
    var totMin = 0;
    let subTot = 0;

    console.log(dis + "==" + tot);
    totMin = parseFloat(tot) * (dis / 100);
    console.log("dis Dis: " + totMin)

    subTot = tot - totMin;
    disTOGave = totMin;

    $('#subTotal').val(subTot);
})

/*Cash*/
$('#cash').on('keyup', function () {
    let cash = $('#cash').val();
    let subT = $('#subTotal').val();

    $('#balance').val((parseFloat(cash)) - parseFloat(subT));
})

/*Remove Duplicate Row*/
function searchRowExists(itemCode) {
    for (let tempOr of tempOrderCartAr) {
        console.log(tempOr.orItemCOde + "-----" + itemCode);
        if (tempOr.orItemCOde === itemCode) {
            return tempOr
        }
    }
    return null;
}

/*Min QTY*/
function minQty(itemCode, orderQty) {
    for (let itemArElement of itemAr) {
        if (itemArElement.itemCode === itemCode) {
            itemArElement.qtyOnHand = parseInt(itemArElement.qtyOnHand) - parseInt(orderQty);
        }
    }
    bindDataTbl();
    clearData();
}

function clearData() {
    $('#qtyOnHandOrd').val("");
    $('#item').val("");
    $('#priceOrd').val("");
    $('#orderQty').val("");
}

/*Purchase Order*/
$('#purchaseOrder').click(function () {
    let orderId = $('#orderId').val();
    let orderDate = $('#OrderDate').val();
    let customerId = $('#customerIdOrd').val();
    let discount = disTOGave;
    let subTotal = $('#subTotal').val();

    if (orderDate === '') {
        alert("Please check the form");
        return;
    }

    let orderModal = new OrderModal(orderId, orderDate, customerId, discount, subTotal, tempOrderCartAr);
    // orders.push(orderModal);
    console.log(orderModal);

    $.ajax({
        url: 'http://localhost:8080/Back_end_war/placeorder',
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(orderModal),
        success: function (resp) {
            console.log(resp);
            blindOrderRowClickEvent();
            clearOrderTexts();

            for (var tempOrder of tempOrderCartAr) {
                tempOrderCartAr.pop();
            }
            tempOrderCartAr.pop();

            addCartData();
            getAllOrders();
        },
        error: function (err) {
            console.log("Error :", err);
        }
    });


    // console.log(orderArray);
});

/*FUNCTIONS*/
function blindOrderRowClickEvent() {

    $('#tblOrder>tr').click(function () {
        let ordId = $(this).children(':eq(0)').text();
        $('#orderIdDash').val(ordId);
        let ordDate = $(this).children(':eq(1)').text();
        $('#OrderDateDash').val(ordDate);
        let ordName = $(this).children(':eq(2)').text();
        $('#customerNameDash').val(ordName);
        let ordDis = $(this).children(':eq(3)').text();
        $('#discountDash').val(ordDis);
        let ordCost = $(this).children(':eq(4)').text();
        $('#subTotDash').val(ordCost);
    });
}

function clearOrderTexts() {
    $('#orderId').val("");
    $('#OrderDate').val("");
    $('#customerNameOrd').val("");
    $('#salaryOrd').val("");
    $('#addressOrd').val("");

    $('#item').val("");
    $('#priceOrd').val("");
    $('#qtyOnHandOrd').val(0);
    $('#orderQty').val("");

    $('#cash').val("");
    $('#discount').val(0);
    $('#balance').val("");
    $('#subTotal').val(0);
}

function loadAllOrder() {
    $("#tblOrder> tr").detach();
    for (var i of orders) {
        $('#tblOrder').append('<tr><td>' + i.orId + '</td>' + '<td>' + i.orDate + '</td>' + '<td>' + i.orCusName + '</td>' + '<td>' + i.orDis + '</td>' + '<td>' + i.orSubTotal + '</td></tr>');
    }
}

function getAllOrders() {
    console.log("Get all orders method called");
    $.ajax({
        url: "http://localhost:8080/Back_end_war/placeorder?method=GETALL",
        contentType: "application/json",
        method: "GET",
        success: function (resp) {
            orders = resp.data;
            loadAllOrder();
            blindOrderRowClickEvent();
        },
        error: function (err) {

        }
    });
}