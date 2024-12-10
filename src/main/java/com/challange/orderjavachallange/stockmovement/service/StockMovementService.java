package com.challange.orderjavachallange.stockmovement.service;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.item.repository.ItemRepository;
import com.challange.orderjavachallange.item.service.ItemService;
import com.challange.orderjavachallange.order.entity.Order;
import com.challange.orderjavachallange.order.repository.OrderRepository;
import com.challange.orderjavachallange.stockmovement.dto.StockMovementDto;
import com.challange.orderjavachallange.stockmovement.entity.StockMovement;
import com.challange.orderjavachallange.stockmovement.mapper.StockMovementMapper;
import com.challange.orderjavachallange.stockmovement.repository.StockMovementRepository;
import com.challange.orderjavachallange.config.EmailService;
import com.challange.orderjavachallange.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final EmailService emailService;

    public StockMovementService(StockMovementRepository stockMovementRepository, ItemService itemService, OrderRepository orderRepository, ItemRepository itemRepository, UserRepository userRepository, EmailService emailService) {
        this.stockMovementRepository = stockMovementRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.emailService = emailService;
    }

    public List<StockMovement> getAllStockMovements() {
        return stockMovementRepository.findAll();
    }

    private static final Logger logger = LoggerFactory.getLogger(StockMovementService.class);

    public StockMovementDto createStockMovement(StockMovementDto dto) {
        logger.info("Creating stock movement for item ID: {}", dto.getItemId());

        ItemEntity item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + dto.getItemId()));

        StockMovement stockMovement = StockMovementMapper.toEntity(dto);
        stockMovement.setItem(item);

        stockMovement = stockMovementRepository.save(stockMovement);

        assignStockToPendingOrders(stockMovement);
        return StockMovementMapper.toDto(stockMovement);
    }

    private void assignStockToPendingOrders(StockMovement stockMovement) {
        List<Order> pendingOrders = orderRepository.findPendingOrdersByItem(stockMovement.getItem());

        for (Order order : pendingOrders) {
            int remainingQuantity = order.getQuantity() - order.getStockMovements().stream()
                    .mapToInt(StockMovement::getQuantity)
                    .sum();

            if (remainingQuantity > 0) {
                int allocatedQuantity = Math.min(remainingQuantity, stockMovement.getQuantity());
                stockMovement.setQuantity(stockMovement.getQuantity() - allocatedQuantity);

                StockMovement allocatedMovement = new StockMovement();
                allocatedMovement.setItem(stockMovement.getItem());
                allocatedMovement.setQuantity(allocatedQuantity);
                allocatedMovement.setOrder(order);

                stockMovementRepository.save(allocatedMovement);

                order.getStockMovements().add(allocatedMovement);

                if (remainingQuantity == allocatedQuantity) {
                    if (order.getUser() == null || order.getUser().getEmail() == null) {
                        throw new RuntimeException("Order user or email is not defined for order ID: " + order.getId());
                    }
                    order.setComplete(true);
                    orderRepository.save(order);

                    emailService.sendOrderCompletionEmail(
                            order.getUser().getEmail(),
                            "Order Completed",
                            "Your order has been completed."
                    );
                }

                if (stockMovement.getQuantity() <= 0) {
                    break;
                }
            }
        }
    }


}
