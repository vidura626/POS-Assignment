// function orderModal(orderId, orderDate, customerName, discount, subTotal) {
//     var order = {
//         orId: orderId,
//         orDate: orderDate,
//         orCusName: customerName,
//         orDis: discount,
//         orSubTotal: subTotal
//     }
//     orders.push(order);
// }

class OrderModal {
    constructor(orderId, orderDate, customerName, discount, subTotal, orderDetails) {
        this.orId = orderId;
        this.orDate = orderDate;
        this.orCusId = customerName;
        this.orDis = discount;
        this.orSubTotal = subTotal;
        this.orderDetails = orderDetails;
    }
}