// function tempCartModal(itemCode, itmName, itmPrice, itemOrderQty, total) {
//     var tempOrder = {
//         orItemCOde: itemCode,
//         orItemName: itmName,
//         orItemPrice: itmPrice,
//         orItemQTY: itemOrderQty,
//         orItemTotal: total
//     }
//     tempOrderCartAr.push(tempOrder);
// }

class TempCartModal {
    constructor(itemCode, itmName, itmPrice, itemOrderQty, total) {
        this.orItemCOde = itemCode;
        this.orItemName = itmName;
        this.orItemPrice = itmPrice;
        this.orItemQTY = itemOrderQty;
        this.orItemTotal = total;
    }
}