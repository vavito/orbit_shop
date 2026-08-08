package com.orbit_shop.order.service;

import com.orbit_shop.customer.domain.Customer;
import com.orbit_shop.customer.service.CustomerService;
import com.orbit_shop.order.domain.Order;
import com.orbit_shop.order.domain.OrderItem;
import com.orbit_shop.order.domain.OrderStatus;
import com.orbit_shop.order.dto.OrderItemRequestDTO;
import com.orbit_shop.order.dto.OrderRequestDTO;
import com.orbit_shop.order.dto.OrderResponseDTO;
import com.orbit_shop.order.mapper.OrderMapper;
import com.orbit_shop.order.repository.OrderRepository;
import com.orbit_shop.product.domain.Product;
import com.orbit_shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final CustomerService customerService;

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {

        Customer customer =
                customerService.findById(dto.customerId());

        Order order =
                orderMapper.toOrderEntity(customer, dto);

        for (OrderItemRequestDTO itemDTO : dto.items()) {

            Product product =
                    productService.findById(itemDTO.productId());

            OrderItem item =
                    orderMapper.toOrderItemEntity(
                            itemDTO,
                            product
                    );

            order.addItem(item);
        }

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toOrderResponseDTO(savedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = findOrderById(id);
        orderRepository.delete(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO findOrder(Long id) {
        return orderMapper.toOrderResponseDTO(
                findOrderById(id)
        );
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> listAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponseDTO)
                .toList();
    }

    @Transactional
    public OrderResponseDTO addItem(
            Long orderId,
            OrderItemRequestDTO dto
    ) {

        Order order = findOrderById(orderId);

        Product product =
                productService.findById(dto.productId());

        OrderItem item =
                orderMapper.toOrderItemEntity(dto, product);

        order.addItem(item);

        return orderMapper.toOrderResponseDTO(order);
    }

    @Transactional
    public OrderResponseDTO removeItem(
            Long orderId,
            Long itemId
    ) {

        Order order = findOrderById(orderId);
        OrderItem item = findItemInOrder(order, itemId);

        order.removeItem(item);

        return orderMapper.toOrderResponseDTO(order);
    }

    @Transactional
    public OrderResponseDTO updateItemQuantity(
            Long orderId,
            Long itemId,
            Integer quantity
    ) {

        Order order = findOrderById(orderId);
        OrderItem item = findItemInOrder(order, itemId);

        order.redefineItemQuantity(item, quantity);

        return orderMapper.toOrderResponseDTO(order);
    }

    @Transactional
    public OrderResponseDTO updateStatus(
            Long orderId,
            OrderStatus status
    ) {

        Order order = findOrderById(orderId);

        order.updateStatus(status);

        return orderMapper.toOrderResponseDTO(order);
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Pedido não encontrado."
                        )
                );
    }

    private OrderItem findItemInOrder(
            Order order,
            Long itemId
    ) {

        return order.getItems()
                .stream()
                .filter(
                        item -> item.getId().equals(itemId)
                )
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(
                                "Item não encontrado neste pedido."
                        )
                );
    }
}