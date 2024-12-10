package com.challange.orderjavachallange.order.service;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.item.repository.ItemRepository;
import com.challange.orderjavachallange.order.entity.Order;
import com.challange.orderjavachallange.config.EmailService;
import com.challange.orderjavachallange.stockmovement.entity.StockMovement;
import com.challange.orderjavachallange.order.repository.OrderRepository;
import com.challange.orderjavachallange.stockmovement.repository.StockMovementRepository;
import com.challange.orderjavachallange.user.entity.User;
import com.challange.orderjavachallange.user.repository.UserRepository;
import com.challange.orderjavachallange.order.dto.OrderRequestDTO;
import com.challange.orderjavachallange.order.dto.OrderResponseDTO;
import com.challange.orderjavachallange.order.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final StockMovementRepository stockMovementRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, StockMovementRepository stockMovementRepository, UserRepository userRepository, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderResponseDTO create(OrderRequestDTO dto) {
        logger.info("Creating order for Item ID: {}", dto.getSkuCode());

        ItemEntity item = itemRepository.findBySkuCode(dto.getSkuCode())
                .orElseGet(() -> createItem(dto.getItemName(), dto.getSkuCode()));

        User user = userRepository.findUserByEmail(dto.getUserEmail())
                .orElseGet(() -> createUser(dto.getUserName(), dto.getUserEmail()));

        Order order = OrderMapper.toEntity(dto, item, user);
        order = orderRepository.save(order);
        fulfillOrder(order);

        return OrderMapper.toDTO(order);
    }

    public OrderResponseDTO update(OrderRequestDTO dto) throws Exception {
        logger.info("Updating order with ID: {}", dto.getId());

        Order existingOrder = findOrderById(dto.getId());
        updateUser(existingOrder, dto);
        updateQuantity(existingOrder, dto);
        updateItem(existingOrder, dto);

        existingOrder = orderRepository.save(existingOrder);
        return OrderMapper.toDTO(existingOrder);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    private void updateUser(Order order, OrderRequestDTO dto) throws Exception {
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));
            order.setUser(user);
        } else if (dto.getUserEmail() != null) {
            User user = userRepository.findUserByEmail(dto.getUserEmail())
                    .orElseGet(() -> createUser(dto.getUserName(), dto.getUserEmail()));
            order.setUser(user);
        }
    }

    private void updateQuantity(Order order, OrderRequestDTO dto) {
        if (dto.getQuantity() > 0) {
            order.setQuantity(dto.getQuantity());
        }
    }

    private void updateItem(Order order, OrderRequestDTO dto) {
        if (dto.getSkuCode() != null) {
            ItemEntity item = itemRepository.findBySkuCode(dto.getSkuCode())
                    .orElseThrow(() -> new IllegalArgumentException("Item with SKU already exists and does not match the current order."));

            validateItem(item, order);

            order.setItem(item);
        }
    }

    private void validateItem(ItemEntity item, Order order) {
        if (!item.getName().equals(order.getItem().getName()) && !item.getId().equals(order.getItem().getId())) {
            throw new IllegalArgumentException("Item with SKU already exists and does not match the current order.");
        }
    }

    private void fulfillOrder(Order order) {
        List<StockMovement> availableStocks = stockMovementRepository.findAvailableStocksForItem(order.getItem().getId());

        int quantityToFulfill = order.getQuantity();
        for (StockMovement stock : availableStocks) {
            if (quantityToFulfill <= 0) break;

            int usedQuantity = Math.min(stock.getQuantity(), quantityToFulfill);
            stock.setQuantity(stock.getQuantity() - usedQuantity);
            quantityToFulfill -= usedQuantity;

            stockMovementRepository.save(stock);

            StockMovement allocatedMovement = new StockMovement();
            allocatedMovement.setItem(stock.getItem());
            allocatedMovement.setQuantity(usedQuantity);
            allocatedMovement.setOrder(order);

            stockMovementRepository.save(allocatedMovement);
            order.getStockMovements().add(allocatedMovement);

            if (quantityToFulfill == 0) {
                order.setComplete(true);
                orderRepository.save(order);
                emailService.sendOrderCompletionEmail(order.getUser().getEmail(), "Order Completed",
                        "Your order #" + order.getId() + " has been fulfilled.");
                logger.info("Order #{} completed.", order.getId());
            }
        }
    }

    public OrderResponseDTO findById(Long id) {
        logger.info("Finding order by id: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return OrderMapper.toDTO(order);
    }

    public List<OrderResponseDTO> findAll() {
        logger.info("Finding all orders");
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderMapper::toDTO).toList();
    }

    public void delete(Long id) {
        logger.info("Deleting order by id: {}", id);
        orderRepository.deleteById(id);
    }

    private ItemEntity createItem(String itemName, String skuCode) {
        logger.info("Creating new Item with name: {}", itemName);
        ItemEntity newItem = new ItemEntity();
        newItem.setName(itemName);
        newItem.setSkuCode(skuCode);
        return itemRepository.save(newItem);
    }

    private User createUser(String userName, String userEmail) {
        logger.info("Creating new user with name: {} and email: {}", userName, userEmail);

        if (userName == null || userName.isBlank() || userEmail == null || userEmail.isBlank()) {
            throw new IllegalArgumentException("User name and email must be provided to create a new user.");
        }

        User newUser = new User();
        newUser.setName(userName);
        newUser.setEmail(userEmail);
        return userRepository.save(newUser);
    }


}
