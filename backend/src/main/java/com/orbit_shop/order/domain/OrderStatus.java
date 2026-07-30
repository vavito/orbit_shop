package com.orbit_shop.order.domain;

public enum OrderStatus {
    PENDING,    // Pedido criado, aguardando pagamento
    PAID,       // Pagamento confirmado
    SHIPPED,    //  Pedido enviado
    DELIVERED,  // Pedido entregue
    CANCELED    // Pedido cancelado
}